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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.pcs.fault.monitor.bean.Contact;
import com.pcs.fault.monitor.bean.FaultResponse;
import com.pcs.fault.monitor.enums.FaultMsgType;
import com.pcs.fault.monitor.util.FaultMonitorUtil;

/**
 * This class is responsible for reading fault response and notifying users
 * 
 * @author pcseg129(Seena Jyothish) Jan 24, 2016
 */
public class FaultRespNotifierBolt extends BaseBasicBolt {

	private static final long serialVersionUID = 5334302074877724492L;

	private static Logger LOGGER = LoggerFactory
	        .getLogger(FaultRespNotifierBolt.class);
	
	public static final String FAULT_MSG_TYPE = "faultMsgType";
	public static final String FAULT_EVENT_MESSAGE_KEY = "faultEvent";
	public static final String FAULT_EVENT_STRING_MESSAGE_KEY = "faultEventsString";

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		FaultResponse faultResponse = (FaultResponse)input.getValue(1);
		sendNotification(faultResponse,collector);
	}

	// changing this service to public for testing only ,
	// should be changed back to private after testing
	public void sendNotification(FaultResponse faultResponse, BasicOutputCollector collector) {
		List<Contact> contacts = FaultMonitorUtil.getEquipmentContacts(faultResponse.getEngineSerialNumber());
		if(contacts!=null){
			//ByteArrayOutputStream baos = generateReport(faultResponse);
			LOGGER.info("Sending Fault remedy report to contacts {} ",contacts.toArray());
			//FaultMonitorUtil.sendMail(faultRespData, baos.toByteArray(),contacts);
			collector.emit("FaultResponseNotificationStream", new Values(FaultMsgType.FAULT_RESP_MSG,faultResponse));
		}
	}
	
	private ByteArrayOutputStream generateReport(FaultResponse faultResp) {
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(
		        faultResp.getRelatedObjects());
		JasperPrint jasperPrint = null;
		String jasperPath = "./Configuration/FaultResponseReport.jasper";
		HashMap<String, Object> reportParams = new HashMap<String, Object>();
		reportParams = createReportParams(faultResp, reportParams);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (jrDataSource != null) {
			try {
				jasperPrint = JasperFillManager.fillReport(jasperPath,
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

	private HashMap<String, Object> createReportParams(
	        FaultResponse faultResponse, HashMap<String, Object> reportParams) {
		URL resource = getClass().getResource("/");
		String path = resource.getPath();
		path = path + File.separator + "images" + File.separator
		        + "cumminslogo.png";
		// path =
		//path = "D:\\Cummins Workspace\\FaultNotification\\target\\classes\\images\\cumminslogo.png";
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

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declareStream("FaultResponseNotificationStream", new Fields(FAULT_MSG_TYPE,FAULT_EVENT_MESSAGE_KEY));

	}

	public static void main(String[] args) {

		/*FaultRespNotifierBolt f = new FaultRespNotifierBolt();
		String msg = "{\"responseVersion\":\"2.100.000\",\"messageType\":\"FC\",\"notificationId\":\"AB123456789876\",\"telematicsBoxId\":\"123456789\",\"telematicsPartnerMessageId\":\"1234567\",\"telematicsPartnerName\":\"Telematics-R-us\",\"customerReference\":\"Customer-X\",\"equipmentId\":\"12345\",\"engineSerialNumber\":\"78787878\",\"VIN\":\"1VIN9876543219876\",\"active\":\"1\",\"datalinkBus\":\"J1939\",\"sourceAddress\":\"0\",\"occurrenceCount\":4,\"latitude\":\"-39.3456789\",\"longitude\":\"30.9876543\",\"altitude\":\"+1234.123456\",\"directionHeading\":\"SW\",\"vehicleDistance\":\"650450\",\"locationTextDescription\":\"In Columbus, IN near I-65\",\"faultCode\":\"2448\",\"faultCodeDescription\":\"Coolant Level Low - Least Severe Level\",\"instructionToFleetMgr\":\"Schedule service at your earliest convenience\",\"instructionToOperator\":\"Call dispatch immediately\",\"additionalInfoToOperator\":\"TBD\",\"derateFlag\":\"1\",\"derateValue1\":\"25 MPH vehicle speed limit in 4 operating hours\",\"derateValue2\":\"10 MPH vehicle speed limit in 2 operating hours\",\"derateValue3\":\"5 MPH vehicle speed limit in 1 operating hours\",\"lampColor\":\"Amber\",\"reportType\":\"Immediate\",\"faultRootCause1\":\"Coolant Level Sensor\",\"faultLikelihood1\":\"0.45\",\"faultRootCause2\":\"Wiring Harness\",\"faultLikelihood2\":\"0.23\",\"faultRootCause3\":\"ECM\",\"faultLikelihood3\":\"0.14\",\"faultRootCause4\":\"ECM Calibration\",\"faultLikelihood4\":\"0.12\",\"shutdownFlag\":\"1\",\"shutdownDescription\":\"This fault code indicates your engine may experience an engine protection shutdown\",\"priority\":\"Service Now\",\"faultCodeCategory\":\"DOC/DPF\",\"additionalDiagnosticInfo\":\"Shop talk\",\"primaryFaultCode\":\"197\",\"primarySPN\":\"111\",\"primaryFMI\":\"17\",\"primaryOccurrenceDateTime\":\"27-Aug-2014 14:26:43.456\",\"primaryFaultCodeDescription\":\"Oil Pressure Low - Most Severe Level\",\"serviceLocatorLink\":\"service.cummins.com\",\"serviceModelName\":\"ISX15 CM2250\",\"generalAssistancePhoneNumber\":\"(888) 555-5555\",\"cumminsName\":\"Cummins\",\"serviceLocatorMarketApplication\":\"Automotive\",\"equipmentSubApplication\":\"Truck\",\"relatedObjects\":[{\"relatedFaultCode\":\"451\",\"relatedSPN\":\"542\",\"relatedFMI\":\"11\",\"relatedOccurrenceDateTime\":\"27-Aug-2014 14:26:43.456\",\"relatedFaultCodeDescription\":\"Oil Pressure Sensor Circuit - Voltage Above Normal or Shorted High\"},{\"relatedFaultCode\":\"123\",\"relatedSPN\":\"321\",\"relatedFMI\":\"10\",\"relatedOccurrenceDateTime\":\"27-Aug-2014 14:26:43.456\",\"relatedFaultCodeDescription\":\"Coolant Temperature Circuit - Voltage Below Normal or Shorted Low\"}]}";
		try {
			FaultResponse faultResponse = new ObjectMapper().readValue(msg,
			        FaultResponse.class);
			f.sendNotification(faultResponse);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}

}
