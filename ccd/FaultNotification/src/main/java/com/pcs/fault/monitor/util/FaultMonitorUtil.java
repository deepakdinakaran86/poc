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
package com.pcs.fault.monitor.util;

import static com.pcs.fault.monitor.constants.FaultEventConstants.ASSET_NAME;
import static com.pcs.fault.monitor.constants.FaultEventConstants.ENTITY_STATUS;
import static com.pcs.fault.monitor.constants.FaultEventConstants.EVENT_SEND_STATUS;
import static com.pcs.fault.monitor.constants.FaultEventConstants.EVENT_STATUS;
import static com.pcs.fault.monitor.constants.FaultEventConstants.EVENT_TIMESTAMP;
import static com.pcs.fault.monitor.constants.FaultEventConstants.FMI;
import static com.pcs.fault.monitor.constants.FaultEventConstants.IDENTIFIER;
import static com.pcs.fault.monitor.constants.FaultEventConstants.OC;
import static com.pcs.fault.monitor.constants.FaultEventConstants.OC_CYCLE;
import static com.pcs.fault.monitor.constants.FaultEventConstants.RESP_RECEIVE_STATUS;
import static com.pcs.fault.monitor.constants.FaultEventConstants.SOURCE_ID;
import static com.pcs.fault.monitor.constants.FaultEventConstants.SPN;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.avocado.token.TokenInfoDTO;
import com.pcs.avocado.token.TokenManager;
import com.pcs.fault.monitor.bean.AnalyticsPlatform;
import com.pcs.fault.monitor.bean.Contact;
import com.pcs.fault.monitor.bean.EmailNotification;
import com.pcs.fault.monitor.bean.EmailTemplate;
import com.pcs.fault.monitor.bean.Equipment;
import com.pcs.fault.monitor.bean.EvtProcessStatus;
import com.pcs.fault.monitor.bean.FaultData1;
import com.pcs.fault.monitor.bean.FaultEventData;
import com.pcs.fault.monitor.bean.FaultEventInfoReq;
import com.pcs.fault.monitor.bean.KeyValueObject;
import com.pcs.fault.monitor.bean.ServicesConfig;
import com.pcs.fault.monitor.bean.Status;
import com.pcs.fault.monitor.bean.StatusMessage;
import com.pcs.fault.monitor.enums.EntityStatus;
import com.pcs.fault.monitor.enums.EventSendStatus;
import com.pcs.fault.monitor.enums.EventStatus;
import com.pcs.fault.monitor.enums.RespReceiveStatus;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;

/**
 * This class is responsible for providing different utilities
 * 
 * @author pcseg129(Seena Jyothish) Jan 20, 2016
 */
public class FaultMonitorUtil {
	private static final Logger logger = LoggerFactory
	        .getLogger(FaultMonitorUtil.class);

	private static boolean initzd = false;
	private static AnalyticsPlatform aPltform;
	private static ServicesConfig servicesConfig;
	private static EmailTemplate emailTemplate;

	public static void readConfig() {
		try {

			/*
			 * String filePath = System.getProperty("user.dir") + File.separator
			 * + "Configuration" + File.separator + "ccdplatform.yaml";
			 */

			String filePath = "/ccdplatform.yaml";
			aPltform = YamlUtils.copyYamlFromClassPath(AnalyticsPlatform.class,
			        filePath);
			filePath = "/services.yaml";
			servicesConfig = YamlUtils.copyYamlFromClassPath(
			        ServicesConfig.class, filePath);
			filePath = "/emailTemplate.yaml";
			emailTemplate = YamlUtils.copyYamlFromClassPath(
			        EmailTemplate.class, filePath);
			initzd = true;
		} catch (Exception e) {
			logger.error(
			        "Error while reading remote platform configuration or services config or emailTemplate yaml files",
			        e);
		}
	}

	public static AnalyticsPlatform getExtPlatformInfo() {
		if (!initzd) {
			readConfig();
		}
		return aPltform;
	}

	public static ServicesConfig getServicesConfig() {
		if (!initzd) {
			readConfig();
		}
		return servicesConfig;
	}

	public static EmailTemplate getEmailTemplate() {
		if (!initzd) {
			readConfig();
		}
		return emailTemplate;
	}

	/*
	 * public static SimpleRestClient buildSimpleRestClient() { SimpleRestClient
	 * httpClient = new SimpleRestClient();
	 * SimpleRestClient.requestInfo.setHostIp(getExtPlatformInfo()
	 * .getPlatformHost()); SimpleRestClient.requestInfo.setPort(Integer
	 * .parseInt(getExtPlatformInfo().getPlatformPort()));
	 * SimpleRestClient.requestInfo.setScheme(getExtPlatformInfo()
	 * .getPlatformScheme()); return httpClient; }
	 */

	public static void sendMail(String faultRespData, byte[] attachFile,
	        List<Contact> contacts) {
		EmailNotification emailNotification = createNotificationObj(
		        faultRespData, emailTemplate.getAddressee(), attachFile);
		emailNotification.setContacts(contacts);
		Client httpClient = buildRestClient();
		try {
			StatusMessage status = httpClient.post(getServicesConfig()
			        .getEmailApi(), setHeader(), emailNotification,
			        StatusMessage.class);
			if (status == null || status.equals(Status.FAILURE)) {
				logger.info("Error in email service, unable to send email(s)");
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	public static FaultData1 getLatestFaultEvt(FaultData1 faultData1) {
		Client httpClient = buildRestClient();
		FaultEventInfoReq eventInfoReq = createFaultEvtReq(faultData1);
		FaultData1 eventData = null;
		try {
			eventData = httpClient.post(
			        getServicesConfig().getGetFaultEvtApi(), setHeader(),
			        eventInfoReq, FaultData1.class);
			if (eventData == null) {
				logger.info(
				        "latest fault event for device {} is null, possible a new fault event",
				        faultData1.getFaultDataInfo().getDeviceId());
			}
		} catch (ClientException e) {
			logger.error("Error in fetching latest fault event for device {} ",
			        faultData1.getFaultDataInfo().getDeviceId());
		}
		return eventData;
	}

	public static StatusMessage persistFaultEvt(FaultData1 faultData1) {
		logger.info("Reached safe : persistFaultEvt ");
		Client httpClient = buildRestClient();
		faultData1 = createPersistFaultEvtReq(faultData1);
		StatusMessage status = null;
		try {
			logger.info("URL : " + getServicesConfig().getPersistFaultEvtApi());
			logger.info("Object : " + faultData1);
			status = httpClient.post(getServicesConfig()
			        .getPersistFaultEvtApi(), setHeader(), faultData1,
			        StatusMessage.class);
			if (status == null || status.getStatus().equals(Status.FAILURE)) {
				logger.info(
				        "Error in persist fault event service for device {} ",
				        faultData1.getFaultDataInfo().getDeviceId());
			} else if (status.getStatus().equals(Status.SUCCESS)) {
				logger.info("fault event persisted for device {} ", faultData1
				        .getFaultDataInfo().getDeviceId());
			}
		} catch (ClientException e) {
			logger.error("Error in persist fault event service for device {} ",
			        faultData1.getFaultDataInfo().getDeviceId(), e);
		}
		return status;
	}

	public static StatusMessage updateExistingFaultEvt(FaultData1 faultData1) {
		Client httpClient = buildRestClient();
		FaultData1 faultData2 = updateFaultEvtReq(faultData1);
		StatusMessage status = null;
		try {
			status = httpClient.put(getServicesConfig().getUpdateFaultEvtApi(),
			        setHeader(), faultData2, StatusMessage.class);
			if (status == null || status.getStatus().equals(Status.FAILURE)) {
				logger.info(
				        "Error in update fault event service for device {} ",
				        faultData2.getFaultDataInfo().getDeviceId());
			}
		} catch (ClientException e) {
			logger.error("Error in update fault event service for device {} ",
			        faultData2.getFaultDataInfo().getDeviceId());
		}
		if (status.getStatus().equals(Status.SUCCESS)) {
			logger.info("fault event updated for device {} ", faultData2
			        .getFaultDataInfo().getDeviceId());
		}
		return status;
	}

	public static List<Contact> getEquipmentContacts(String esn) {
		logger.info("Get Contacts for Equipment {}", esn);
		Equipment equipment = new Equipment();
		equipment.setAssetName(esn);
		Client httpClient = buildRestClient();
		List<Contact> contacts = null;
		try {
			contacts = httpClient.post(getServicesConfig()
			        .getGetAttachedContactsApi(), setHeader(), equipment,
			        List.class, Contact.class);
		} catch (ClientException e) {
			logger.error(
			        "Error in get attached contacts service for device {} ",
			        esn);
		}
		return contacts;
	}

	public static Map<String, String> setHeader() {
		Map<String, String> httpHeaders = new HashMap<String, String>();
		httpHeaders.put(HttpHeaders.AUTHORIZATION, "Bearer " + getToken());
		return httpHeaders;
	}

	private static String getToken() {
		logger.info("Client id : " + getServicesConfig().getApiMgrClientId());
		logger.info("Client secret : "
		        + getServicesConfig().getApiMgrClientSecret());

		/*
		 * TokenInfoDTO token = TokenManager.getToken(getServicesConfig()
		 * .getApiMgrClientId(), getServicesConfig() .getApiMgrClientSecret(),
		 * "ccd-storm");
		 */
		TokenInfoDTO token = TokenManager.getToken(getServicesConfig()
		        .getApiMgrClientId(), getServicesConfig()
		        .getApiMgrClientSecret(), getServicesConfig().getCcdUser(),
		        getServicesConfig().getCcdUserPwd(), "ccd-storm");

		logger.info("Token : " + token.getAccessToken());

		return token.getAccessToken();
	}

	public static ApacheRestClient buildRestClient() {
		ApacheRestClient httpClient;
		httpClient = (ApacheRestClient)ApacheRestClient.builder()
		        .host(getServicesConfig().getHost())
		        .port(Integer.parseInt(getServicesConfig().getPort()))
		        .scheme(getServicesConfig().getScheme()).build();
		return httpClient;
	}

	private static EmailNotification createNotificationObj(String content,
	        String addressee, byte[] attachFile) {
		EmailNotification notification = new EmailNotification();
		notification.setContent(content);
		notification.setEmailTemplate(emailTemplate.getEmailTemplate());
		notification.setSubject(emailTemplate.getSubject());
		if (attachFile != null) {
			notification.setAttachFile(attachFile);
			notification.setContentType("application/pdf");
			notification.setAttachFileName("Fault Remedy");
		}
		return notification;

	}

	private static FaultEventInfoReq createFaultEvtReq(FaultData1 faultData1) {
		FaultEventInfoReq eventInfoReq = new FaultEventInfoReq();
		List<KeyValueObject> kvObjects = new ArrayList<KeyValueObject>();
		kvObjects.add(createKeyValuePair(SOURCE_ID, faultData1
		        .getFaultDataInfo().getDeviceId()));
		kvObjects.add(createKeyValuePair(ASSET_NAME, faultData1
		        .getFaultDataInfo().getSerialNumber()));
		kvObjects.add(createKeyValuePair(SPN, faultData1.getSPN()));
		kvObjects.add(createKeyValuePair(FMI, faultData1.getFMI()));
		kvObjects.add(createKeyValuePair(
		        EVENT_STATUS,
		        EventStatus.valueOfType(
		                Integer.parseInt(faultData1.getFaultDataInfo()
		                        .getActive())).toString()));
		/*
		 * kvObjects.add(createKeyValuePair(ENTITY_STATUS,
		 * EntityStatus.ACTIVE.toString()));
		 */
		eventInfoReq.setEntityStatus(new EvtProcessStatus(EntityStatus.ACTIVE
		        .toString()));

		/*
		 * String snapshotJson = ""; try {
		 * @SuppressWarnings("serial") Type type = new
		 * TypeToken<List<FaultSnapshot>>() { }.getType(); GsonBuilder
		 * gsonBuilder = new GsonBuilder(); Gson gson = gsonBuilder.create();
		 * snapshotJson = gson.toJson(faultData.getFaultDataInfo()
		 * .getSnapshots(), type);
		 * kvObjects.add(createKeyValuePair(SNAPSHOT_DATA, snapshotJson)); }
		 * catch (Exception e) { logger.error(
		 * "Error in deserializing snap shot data for device {} ",
		 * faultData.getFaultDataInfo().getDeviceId()); }
		 */

		eventInfoReq.setKeyValues(kvObjects);

		return eventInfoReq;
	}

	private static FaultData1 createPersistFaultEvtReq(FaultData1 faultData1) {
		DateFormat df = new SimpleDateFormat("MMM/dd/yyyy HH:mm:ss");
		try {
			Date ocDatetime = df.parse(faultData1.getFaultDataInfo()
			        .getOccuranceDateTime());
			faultData1.getFaultDataInfo().setOccuranceDateTime(
			        String.valueOf(ocDatetime.getTime()));
		} catch (ParseException e) {
			logger.info("error parsing occ datetime for device {} ", faultData1
			        .getFaultDataInfo().getDeviceId());
		}

		faultData1
		        .setAssetName(faultData1.getFaultDataInfo().getSerialNumber());

		faultData1.getFaultDataInfo().setActive(
		        EventStatus.valueOfType(
		                Integer.parseInt(faultData1.getFaultDataInfo()
		                        .getActive())).toString());
		faultData1.setEventSendStatus(EventSendStatus.SENT);
		faultData1.setRespReceiveStatus(RespReceiveStatus.NOT_RECEIVED);
		return faultData1;
	}

	private static FaultData1 updateFaultEvtReq(FaultData1 faultData1) {
		DateFormat df = new SimpleDateFormat("MMM/dd/yyyy HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		faultData1.getFaultDataInfo().setSentDateTime(cal.getTime().toString());
		if (faultData1.getFaultDataInfo().getDirection() == null) {
			faultData1.getFaultDataInfo().setDirection("  ");
		}

		// faultData1.setAssetName(faultData1.getFaultDataInfo().getSerialNumber());

		/*
		 * faultData1.getFaultDataInfo().setActive( EventStatus.valueOfType(
		 * Integer.parseInt(faultData1.getFaultDataInfo()
		 * .getActive())).toString());
		 */
		faultData1.setEntityStatus(Status.ALLOCATED);
		faultData1.setEventSendStatus(EventSendStatus.SENT);
		faultData1.setRespReceiveStatus(RespReceiveStatus.NOT_RECEIVED);
		// faultData1.setReadOnlyRawIdentifier(rowIdentifier);
		return faultData1;
	}

	private static FaultEventInfoReq createPrevFaultData(
	        FaultEventData eventData, String rowIdentifier) {
		FaultEventInfoReq eventInfoReq = new FaultEventInfoReq();
		eventInfoReq.setEntityStatus(new EvtProcessStatus(
		        EntityStatus.ALLOCATED.toString()));
		eventInfoReq
		        .setIdentifier(createKeyValuePair(IDENTIFIER, rowIdentifier));
		List<KeyValueObject> kvObjects = new ArrayList<KeyValueObject>();
		kvObjects.add(createKeyValuePair(SOURCE_ID, eventData.getSourceId()));
		kvObjects
		        .add(createKeyValuePair(OC, String.valueOf(eventData.getOC())));
		kvObjects.add(createKeyValuePair(SPN, eventData.getSPN()));
		kvObjects.add(createKeyValuePair(FMI, eventData.getFMI()));
		kvObjects.add(createKeyValuePair(EVENT_STATUS,
		        (eventData.getEventStatus()).toString()));
		kvObjects.add(createKeyValuePair(ENTITY_STATUS,
		        eventData.getEntityStatus()));
		kvObjects.add(createKeyValuePair(EVENT_SEND_STATUS,
		        EventSendStatus.SENT.toString()));
		kvObjects.add(createKeyValuePair(EVENT_TIMESTAMP,
		        String.valueOf(eventData.getEventTimestamp())));
		kvObjects.add(createKeyValuePair(OC_CYCLE,
		        String.valueOf(eventData.getOcCylcle())));
		kvObjects.add(createKeyValuePair(RESP_RECEIVE_STATUS, eventData
		        .getRespReceiveStatus().toString()));
		eventInfoReq.setKeyValues(kvObjects);
		return eventInfoReq;
	}

	private static KeyValueObject createKeyValuePair(String key, String value) {
		return new KeyValueObject(key, value);
	}

	public static void main(String[] args) {
		TokenManager.invalidateToken("ccd-storm");
		System.out.println("cahce cleared");
	}
}
