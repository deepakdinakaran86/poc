package com.pcs.galaxy.esb.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.xml.stream.XMLStreamException;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;
import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.MessageContext;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.rest.AbstractHandler;
import org.apache.synapse.transport.passthru.util.RelayUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pcs.galaxy.esb.handler.dto.AuditDTO;

public class AuditHandler extends AbstractHandler implements ManagedLifecycle {

	// ZOOKEEPER IR
	private static final String ZOOKEEPER_URL = "10.234.31.234:9092";
	// ZOOKEEPER INVALID FOR TESTING
	//private static final String ZOOKEEPER_URL = "10.234.31.231:9092";
	// ZOOKEEPER INVALID FOR DATACENTER
	//private static final String ZOOKEEPER_URL = "10.131.5.12:9092";

	// CONSTANTS REQUIRED
	private static final String KEY = "key";
	private static final String IDENTIFIER = "identifier";
	private static final String DOMAIN = "domain";
	private static final String DOMAINNAME = "domainName";
	private static final String ENTITYTEMPLATENAME = "entityTemplateName";
	private static final String ENTITYTEMPLATE = "entityTemplate";
	private static final String DATEFORMAT = "MM/dd/yyyy HH:mm:ss";
	private static final String TIMEZONE = "UTC";
	private static final String VALUE = "value";
	private static final String USER = "USER";
	private static final String NIL = "NIL";
	private static final String NO_IDENTITY = "NO_IDENTITY";
	private static final String JWT_TOKEN_HEADER = "x-jwt-assertion";
	private static final String HTTP_METHOD = "HTTP_METHOD";
	private static final String REMOTE_ADDR = "REMOTE_ADDR";
	private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
	// private static final String API_CONTEXT_CLAIM =
	// "http://wso2.org/claims/apicontext";
	private static final String API_END_USER_CLAIM = "http://wso2.org/claims/enduser";
	private static final String API_NAME = "API_NAME";
	private static final String TOPIC = "platform-audit-log";

	private ConfigurationContext configContext;
	AuditDTO auditDTO = null;

	// private final Base64 decoder = new Base64(true);

	private static final Log logger = LogFactory.getLog(AuditHandler.class);

	public boolean handleRequest(MessageContext messageContext) {
		if (this.getConfigContext() == null) {
			logger.error("Configuration Context is null");
			return false;
		}
		try {

			// Initialize audit DTO for thread local call
			auditDTO = new AuditDTO();

			// Initialize the time stamp
			long currentTime = System.currentTimeMillis();
			/*
			 * Date date = new Date(currentTime); // if you really have long
			 * SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
			 * sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE)); String result =
			 * sdf.format(date.getTime());
			 */

			// Initialize the operation start time
			auditDTO.setStartTime(String.valueOf(currentTime));

			// Set the target identity
			auditDTO.setTargetIdentity(messageContext.getMessageID());

			// Initialize the Actor
			// Only USER for now. Will change implementation later if reqd
			auditDTO.setActor(USER);

			Axis2MessageContext axis2MessageContext = null;
			org.apache.axis2.context.MessageContext msgContext = null;
			axis2MessageContext = (Axis2MessageContext) messageContext;
			msgContext = axis2MessageContext.getAxis2MessageContext();

			RelayUtils.buildMessage(msgContext);

			// Getting the json payload to string
			String jsonPayloadToString = JsonUtil
					.jsonPayloadToString(((Axis2MessageContext) messageContext)
							.getAxis2MessageContext());
			// Make a json object
			
			if(!isJSONArray(jsonPayloadToString)){
			JSONObject jsonBody = new JSONObject(jsonPayloadToString);
			// Extract and initialize Actor identity TBC
			
				auditDTO.setActorIdentity(setActorIdentity(jsonBody));
			}

			// Extract and initialize action i.e. HTTP method initiated by user
			if (msgContext.getProperty(HTTP_METHOD) != null) {
				String httpMethod = (String) msgContext
						.getProperty(HTTP_METHOD);
				auditDTO.setAction(httpMethod);
			} else {
				auditDTO.setAction(NIL);
			}

			org.apache.axis2.context.MessageContext axis2MsgContext = ((Axis2MessageContext) messageContext)
					.getAxis2MessageContext();
			Map<String, String> transportHeaders = (Map) axis2MsgContext
					.getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);

			// Extract headers for IP address of the client
			// Can use this also axis2MessageCtx.getProperty("REMOTE_HOST");
			Axis2MessageContext axis2smc = (Axis2MessageContext) messageContext;
			org.apache.axis2.context.MessageContext axis2MessageCtx = axis2smc
					.getAxis2MessageContext();
			if (axis2MessageCtx.getProperty(HTTP_CLIENT_IP) != null) {
				auditDTO.setIpAddress((String) axis2MessageCtx
						.getProperty(HTTP_CLIENT_IP));
			} else {
				auditDTO.setIpAddress(NIL);
			}

			// Set the URL resource the user requested for
			if (axis2smc.getTo().getAddress() != null) {
				auditDTO.setResourceUrl(axis2smc.getTo().getAddress());
			} else {
				auditDTO.setResourceUrl(NIL);
			}

			// Extract JWT header to fetch API and context which is the target
			if (transportHeaders != null && transportHeaders instanceof Map
					&& transportHeaders.get(JWT_TOKEN_HEADER) != null) {
				String jwtToken = (String) transportHeaders
						.get(JWT_TOKEN_HEADER);
				setTargetAndUserFromJWT(jwtToken);
			} else {
				auditDTO.setTarget(NIL);
				auditDTO.setUserName(NIL);
			}

		} catch (Exception e) {
			logger.error("Error occurred while processing the message", e);
		}
		return true;
	}

	private boolean isJSONArray(String jsonString) {
		try {
			if(jsonString != null){
				new JSONArray(jsonString);
			}
		} catch (JSONException ex1) {
			return false;
		}
		return true;
	}

	private void setTargetAndUserFromJWT(String jwtObject) {
		String[] pieces = jwtObject.split("\\.");
		JSONObject jsonJWTHeader = null;
		jsonJWTHeader = decodeAndParse(pieces[1]);

		if (jsonJWTHeader.get(API_NAME) != null) {
			auditDTO.setTarget(String.valueOf(jsonJWTHeader.get(API_NAME)));
		} else {
			auditDTO.setTarget(NIL);
		}

		if (jsonJWTHeader.get(API_END_USER_CLAIM) != null) {
			auditDTO.setUserName(String.valueOf(jsonJWTHeader
					.get(API_END_USER_CLAIM)));
		} else {
			auditDTO.setUserName(NIL);
		}
	}

	private JSONObject decodeAndParse(String b64String) {
		JSONObject jsonJWTHeader = null;
		try {
			jsonJWTHeader = new JSONObject(new String(java.util.Base64
					.getDecoder().decode(b64String)));
		} catch (Exception e) {
			logger.error("jwtObject parsor exception" + e.getMessage());
		}
		return jsonJWTHeader;
	}

	private String setActorIdentity(JSONObject jsonObject) {
		JSONObject actorIdentityJsonObject = new JSONObject();
		try {
			if (jsonObject.has(IDENTIFIER)) {
				JSONObject identifierJsonObject = new JSONObject();
				identifierJsonObject.put(KEY,
						jsonObject.getJSONObject(IDENTIFIER).getString(KEY));
				identifierJsonObject.put(VALUE,
						jsonObject.getJSONObject(IDENTIFIER).getString(VALUE));
				actorIdentityJsonObject.put(IDENTIFIER,
						jsonObject.getJSONObject(IDENTIFIER));
			}
			if (jsonObject.has(DOMAIN)) {
				JSONObject domainJsonObject = new JSONObject();
				domainJsonObject.put(DOMAINNAME,
						jsonObject.getJSONObject(DOMAIN).getString(DOMAINNAME));
				actorIdentityJsonObject.put(DOMAIN, domainJsonObject);
			}
			if (jsonObject.has(ENTITYTEMPLATE)) {
				JSONObject entityTemplateJsonObject = new JSONObject();
				entityTemplateJsonObject.put(
						ENTITYTEMPLATENAME,
						jsonObject.getJSONObject(ENTITYTEMPLATE).getString(
								ENTITYTEMPLATENAME));
				actorIdentityJsonObject.put(ENTITYTEMPLATE,
						entityTemplateJsonObject);
			}

		} catch (JSONException e) {
			e.printStackTrace();

		}
		return String.valueOf(actorIdentityJsonObject);
	}

	public boolean handleResponse(MessageContext messageContext) {

		if (this.getConfigContext() == null) {
			logger.error("Configuration Context is null");
			return false;
		}
		Axis2MessageContext axis2smc = (Axis2MessageContext) messageContext;
		org.apache.axis2.context.MessageContext axis2MessageCtx = axis2smc
				.getAxis2MessageContext();

		Axis2MessageContext axis2MessageContext = null;
		org.apache.axis2.context.MessageContext msgContext = null;
		axis2MessageContext = (Axis2MessageContext) messageContext;
		msgContext = axis2MessageContext.getAxis2MessageContext();

		try {
			RelayUtils.buildMessage(msgContext);

			// Getting the json payload to string
			String jsonPayloadToString = JsonUtil
					.jsonPayloadToString(((Axis2MessageContext) messageContext)
							.getAxis2MessageContext());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long endTimeInMs = System.currentTimeMillis();
		Date endDateTime = new Date(endTimeInMs); // if you really have long
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
		String endTime = sdf.format(endDateTime.getTime());

		auditDTO.setEndTime(String.valueOf(endTimeInMs));

		long startTimeInMs = Long.valueOf(auditDTO.getStartTime());
		Date startDateTime = new Date(startTimeInMs); // if you really have long
		String startTime = sdf.format(startDateTime.getTime());

		// Time difference in milliseconds
		long timeDifference = endTimeInMs - startTimeInMs;
		auditDTO.setTotalTime(String.valueOf(timeDifference));
		
		String identity = auditDTO.getTargetIdentity() != null
				|| auditDTO.getTargetIdentity().equalsIgnoreCase("{}") ? ", who has an identity " + auditDTO
				.getTargetIdentity() : "";

		String remarks = "The " + auditDTO.getActor() + " "
				+ auditDTO.getUserName() + " requested a "
				+ auditDTO.getAction() + " operation at " + startTime
				+ " lasted till " + endTime + " on " + auditDTO.getTarget()
				+ ", URL being " + auditDTO.getResourceUrl()
				+ ", who has an identity " + identity
				+ " with a request ID " + auditDTO.getTargetIdentity()
				+ " from the IP: " + auditDTO.getIpAddress() + " which took "
				+ auditDTO.getTotalTime() + " milliseconds";

		auditDTO.setRemarks(remarks);

		
		 String newLine = System.getProperty("line.separator"); 
		 /* String auditDTOInString = newLine + "**********AUDIT-LOG************" +
		 * newLine + "Actor:" + auditDTO.getActor() + newLine + newLine +
		 * "Action:" + auditDTO.getAction() + newLine + newLine + "Target:" +
		 * auditDTO.getTarget() + newLine + newLine + "Time:" +
		 * auditDTO.getTimeStamp() + newLine + newLine + "Actor Identity:" +
		 * auditDTO.getActorIdentity() + newLine + newLine + "Target Identity:"
		 * + auditDTO.getTargetIdentity() + newLine + newLine + "IP:" +
		 * auditDTO.getIpAddress() + newLine + newLine + "Operation Start Time:"
		 * + auditDTO.getStartTime() + newLine + newLine + "Operation End Time:"
		 * + auditDTO.getEndTime() + newLine + newLine + "Remarks: " +
		 * auditDTO.getRemarks() + newLine + "*******************************";
		 * logger.error(auditDTOInString);
		 */
		try {
			if (System.getProperty("carbon.home") != null) {
				String carbonHome = System.getProperty("carbon.home");
				String auditLogPath = carbonHome + File.separator
						+ "repository" + File.separator + "logs"
						+ File.separator + "AUDIT-LOG.csv";
				File file = new File(auditLogPath);
				if (!file.exists()) {
					file.createNewFile();
					FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
					StringBuilder header = new StringBuilder();
					header.append(
							"Actor,User_Name,Action,Target,Target_Identity,IP_Address,Start_Time,End_Time,Total_Time(ms),Resource_URL,Remarks")
							.append(newLine);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(header.toString());
					bw.close();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				StringBuilder body = new StringBuilder();
				String comma = new String(",");
				// String escaped =
				// StringEscapeUtils.escapeCsv("I said \"Hey, I am 5'10\".\"");
				body.append(auditDTO.getActor()).append(comma)
						.append(auditDTO.getUserName()).append(comma)
						.append(auditDTO.getAction()).append(comma)
						.append(auditDTO.getTarget()).append(comma)
						.append(auditDTO.getTargetIdentity()).append(comma)
						.append(auditDTO.getIpAddress()).append(comma)
						.append(auditDTO.getStartTime()).append(comma)
						.append(auditDTO.getEndTime()).append(comma)
						.append(auditDTO.getTotalTime()).append(comma)
						.append(auditDTO.getResourceUrl()).append(comma)
						.append("\"" + auditDTO.getRemarks() + "\"")
						.append(newLine);
				bw.write(body.toString());
				bw.close();

				Gson gson = new GsonBuilder().setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

				String json = gson.toJson(auditDTO);
				logger.error(json);
				sendKafka(json);

			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return true;
	}

	private void sendKafka(String body) {
		Properties properties = new Properties();
		properties.put("metadata.broker.list", ZOOKEEPER_URL);
		properties.put("serializer.class", "kafka.serializer.StringEncoder");
		ProducerConfig producerConfig = new ProducerConfig(properties);
		kafka.javaapi.producer.Producer<String, String> producer = new kafka.javaapi.producer.Producer<String, String>(
				producerConfig);
		KeyedMessage<String, String> message = new KeyedMessage<String, String>(
				TOPIC, body);
		producer.send(message);
		producer.close();
	}

	public void init(SynapseEnvironment synapseEnvironment) {
		try {
			this.configContext = ConfigurationContextFactory
					.createConfigurationContextFromFileSystem(null, null);
		} catch (AxisFault axisFault) {
			logger.error(
					"Error occurred while initializing Configuration Context",
					axisFault);
		}
	}

	public void destroy() {
		this.configContext = null;
	}

	private ConfigurationContext getConfigContext() {
		return configContext;
	}
}