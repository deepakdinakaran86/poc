/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 *
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.fault.monitor.storm.bolts;

import static com.pcs.fault.monitor.bean.ReportConstants.CUMMINS_CARE_CONTACT;
import static com.pcs.fault.monitor.bean.ReportConstants.CUMMINS_URL;
import static com.pcs.fault.monitor.bean.ReportConstants.DERATE_VALUE_1;
import static com.pcs.fault.monitor.bean.ReportConstants.ESN;
import static com.pcs.fault.monitor.bean.ReportConstants.FAULT_DESC;
import static com.pcs.fault.monitor.bean.ReportConstants.LOGO;
import static com.pcs.fault.monitor.bean.ReportConstants.MODEL;
import static com.pcs.fault.monitor.bean.ReportConstants.NOTIFICATION_ID;
import static com.pcs.fault.monitor.bean.ReportConstants.PRIMARY_FC;
import static com.pcs.fault.monitor.bean.ReportConstants.PRIMARY_FMI;
import static com.pcs.fault.monitor.bean.ReportConstants.PRIMARY_SPN;
import static com.pcs.fault.monitor.bean.ReportConstants.RECOMMENDED_ACTION;
import static com.pcs.fault.monitor.bean.ReportConstants.ROOT_CAUSE_1;
import static com.pcs.fault.monitor.bean.ReportConstants.ROOT_CAUSE_PROBABLITY_1;
import static com.pcs.fault.monitor.bean.ReportConstants.SERVICE_LOCATOR_LINK;
import static com.pcs.fault.monitor.bean.ReportConstants.TIMESTAMP;
import static com.pcs.fault.monitor.bean.ReportConstants.VEHICLE_ID;
import static com.pcs.fault.monitor.bean.ReportConstants.VIN;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.pcs.fault.monitor.bean.Contact;
import com.pcs.fault.monitor.bean.EmailNotification;
import com.pcs.fault.monitor.bean.EmailTemplate;
import com.pcs.fault.monitor.bean.FaultData1;
import com.pcs.fault.monitor.bean.FaultResponse;
import com.pcs.fault.monitor.bean.Status;
import com.pcs.fault.monitor.bean.StatusMessage;
import com.pcs.fault.monitor.enums.FaultMsgType;
import com.pcs.fault.monitor.util.FaultMonitorUtil;
import com.pcs.fault.monitor.util.YamlUtils;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Mar 3, 2016
 */
public class EmailSender extends BaseBasicBolt {

	private static final long serialVersionUID = 8995041872814268896L;

	private static Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

	private static EmailTemplate emailTemplate;

	private static String ATTACH_FILENAME = "Fault Remedy";

	private static String FAULT_EMAIL_SUBJECT = "Fault Event Details";

	private static String FAULT_RESPONSE_EMAIL_SUBJECT = "Fault Recommendations";

	private static String HIPHEN = " - ";
	
	private VelocityEngine ve = null;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		LOGGER.info("in email sender execute");
		FaultMsgType msgType = FaultMsgType.valueOf(input.getValue(0)
		        .toString());
		Object faultInfo = input.getValue(1);

		LOGGER.info("in email sender msgType {} ", msgType);

		sendNotification(msgType, faultInfo, input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

	private void sendNotification(FaultMsgType faultMsgType, Object faultInfo,
	        Tuple input) {
		switch (faultMsgType) {
			case FAULT_MSG :
				LOGGER.info("in email sender fault msg");
				sendFaultEmail(faultInfo, input);
				break;
			case FAULT_RESP_MSG :
				LOGGER.info("in email sender fault response msg");
				sendFaultResponseEmail(faultInfo, input);
				break;
		}
	}

	private void sendFaultEmail(Object faultData, Tuple input) {
		LOGGER.info("in sendFaultEmail");
		FaultData1 faultData1 = (FaultData1)faultData;
		sendFaultNotification(faultData1);
	}

	private void sendFaultResponseEmail(Object faultData, Tuple input) {
		LOGGER.info("in sendFaultResponseEmail");
		FaultResponse faultResponse = (FaultResponse)faultData;
		ByteArrayOutputStream baos = generateReport(faultResponse);
		sendFaultResponseNotification(faultResponse, baos.toByteArray());
	}

	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}

	public void sendFaultNotification(FaultData1 faultData1) {
		LOGGER.info("in sendFaultNotification method");
		String faultData = createContent(faultData1);
		List<Contact> contacts = getEquipmentContacts(faultData1
		        .getFaultDataInfo().getSerialNumber());
		if (contacts != null) {
			LOGGER.info("{} contacts found for equipment {}",contacts.size(),faultData1
			        .getFaultDataInfo().getSerialNumber());
			LOGGER.info("Sending Fault info to contacts {} ",
			        contacts.toArray());
			String subject = faultData1.getFaultDataInfo().getSerialNumber()
			        + HIPHEN + FAULT_EMAIL_SUBJECT;
			sendMail(faultData, subject, null, contacts, null);
		}else{
			LOGGER.info("No contacts found for equipment {}",faultData1
			        .getFaultDataInfo().getSerialNumber());
		}
	}

	public void sendFaultResponseNotification(FaultResponse faultResponse,
	        byte[] attacheFile) {
		String faultRespData = createContent(faultResponse);
		List<Contact> contacts = getEquipmentContacts(faultResponse
		        .getEngineSerialNumber());
		if (contacts != null) {
			LOGGER.info("Sending Fault response info to contacts {} ",
			        contacts.toArray());
			String subject = faultResponse.getEngineSerialNumber() + HIPHEN
			        + FAULT_RESPONSE_EMAIL_SUBJECT;
			sendMail(faultRespData, subject, attacheFile, contacts,
			        ATTACH_FILENAME);
		}
	}

	private List<Contact> getEquipmentContacts(String esn) {
		return FaultMonitorUtil.getEquipmentContacts(esn);
	}

	private String createContent(FaultData1 faultData1) {
		initializeVelocityEngine();

		String filePath = "/ccdFaultNotifierEmailTemplate.vm";
		Template template = ve.getTemplate(filePath);

		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("esn", faultData1.getFaultDataInfo()
		        .getSerialNumber());
		velocityContext.put("occuranceDateTime", faultData1.getFaultDataInfo()
		        .getOccuranceDateTime());
		velocityContext.put("engineModel", faultData1.getFaultDataInfo()
		        .getModel());
		velocityContext.put("SPN", faultData1.getSPN());
		velocityContext.put("FMI", faultData1.getFMI());
		velocityContext.put("OC", faultData1.getOC());

		StringWriter stringWriter = new StringWriter();
		template.merge(velocityContext, stringWriter);
		return stringWriter.toString();
	}

	private String createContent(FaultResponse faultResponse) {
		initializeVelocityEngine();

		String filePath = "/ccdEmailTemplate.vm";
		Template template = ve.getTemplate(filePath);

		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("vehicleId", faultResponse.getEquipmentId());
		velocityContext.put("esn", faultResponse.getEngineSerialNumber());
		velocityContext.put("VIN", faultResponse.getVIN());
		velocityContext.put("action", faultResponse.getInstructionToFleetMgr());

		StringWriter stringWriter = new StringWriter();
		template.merge(velocityContext, stringWriter);
		return stringWriter.toString();
	}

	public static void sendMail(String faultRespData, String subject,
	        byte[] attachFile, List<Contact> contacts, String attachFileName) {
		emailTemplate = FaultMonitorUtil.getEmailTemplate();
		EmailNotification emailNotification = createNotificationObj(
		        faultRespData, subject, attachFile, attachFileName);
		emailNotification.setContacts(contacts);
		Client httpClient = FaultMonitorUtil.buildRestClient();
		try {
			StatusMessage status = httpClient.post(FaultMonitorUtil
			        .getServicesConfig().getEmailApi(), FaultMonitorUtil
			        .setHeader(), emailNotification, StatusMessage.class);
			if (status == null || status.equals(Status.FAILURE)) {
				LOGGER.info("Error in email service, unable to send email(s)");
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	private static EmailNotification createNotificationObj(String content,
	        String subject, byte[] attachFile, String attachFileName) {
		EmailNotification notification = new EmailNotification();
		notification.setContent(content);
		notification.setEmailTemplate(emailTemplate.getEmailTemplate());
		notification.setSubject(subject);
		if (attachFile != null) {
			notification.setAttachFile(attachFile);
			notification.setContentType("application/pdf");
			notification.setAttachFileName(attachFileName);
		}
		return notification;

	}

	private ByteArrayOutputStream generateReport(FaultResponse faultResp) {
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(
		        faultResp.getRelatedObjects());
		JasperPrint jasperPrint = null;
		String jasperPath = "/FaultResponseReport.jasper";
		InputStream inputStream = EmailSender.class.getResourceAsStream(jasperPath); 
		HashMap<String, Object> reportParams = new HashMap<String, Object>();
		reportParams = createReportParams(faultResp, reportParams);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (jrDataSource != null) {
			try {
				jasperPrint = JasperFillManager.fillReport(inputStream,
				        reportParams, jrDataSource);
				JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
				LOGGER.info("Fault remedy report generated");
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error(
				        "Fault report generation failed for notification id {} for device {} ",
				        faultResp.getNotificationId(), faultResp.getVIN());
			}
		}
		return baos;
	}
	
	private void initializeVelocityEngine(){
		ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		//return ve;
	}

	private HashMap<String, Object> createReportParams(
	        FaultResponse faultResponse, HashMap<String, Object> reportParams) {
		URL resource = getClass().getResource("/");
		String path = resource.getPath();
		path = path + File.separator + "images" + File.separator
		        + "cumminslogo.png";
		// path =
		// path =
		// "D:\\Cummins Workspace\\FaultNotification\\target\\classes\\images\\cumminslogo.png";
		System.out.println("path : " + path);
		reportParams.put(LOGO, path);
		reportParams.put(NOTIFICATION_ID, faultResponse.getNotificationId());
		reportParams.put(VEHICLE_ID, faultResponse.getTelematicsBoxId());
		reportParams.put(ESN, faultResponse.getEngineSerialNumber());
		reportParams.put(VIN, faultResponse.getVIN());
		reportParams.put(MODEL, faultResponse.getVIN());
		reportParams.put(RECOMMENDED_ACTION,
		        faultResponse.getInstructionToFleetMgr());
		reportParams.put(PRIMARY_FC, faultResponse.getPrimaryFaultCode());
		reportParams.put(PRIMARY_SPN, faultResponse.getPrimarySPN());
		reportParams.put(PRIMARY_FMI, faultResponse.getPrimaryFMI());
		reportParams.put(TIMESTAMP, faultResponse.getOccurrenceDateTime());
		reportParams.put(FAULT_DESC,
		        faultResponse.getPrimaryFaultCodeDescription());
		reportParams.put(ROOT_CAUSE_1, faultResponse.getFaultRootCause1());
		reportParams.put(ROOT_CAUSE_PROBABLITY_1,
		        faultResponse.getFaultLikelihood1());
		reportParams.put(DERATE_VALUE_1, faultResponse.getDerateValue1());
		reportParams.put(SERVICE_LOCATOR_LINK,
		        faultResponse.getServiceLocatorLink());
		reportParams.put(CUMMINS_CARE_CONTACT,
		        faultResponse.getGeneralAssistancePhoneNumber());
		reportParams.put(CUMMINS_URL, faultResponse.getURLCumminsGeneral());

		return reportParams;
	}

}
