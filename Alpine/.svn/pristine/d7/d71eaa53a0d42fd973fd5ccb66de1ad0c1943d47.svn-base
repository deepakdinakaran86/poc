package com.pcs.galaxy.esb.handler;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;
import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.core.axis2.Axis2Sender;
import org.apache.synapse.message.senders.blocking.BlockingMsgSender;
import org.apache.synapse.rest.AbstractHandler;
import org.apache.synapse.util.MessageHelper;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.wso2.carbon.identity.oauth2.stub.OAuth2TokenValidationServiceStub;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationRequestDTO;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationRequestDTO_OAuth2AccessToken;

public class SimpleOauthHandler extends AbstractHandler implements
		ManagedLifecycle {

	private static final String CONSUMER_KEY_HEADER = "Bearer";
	private static final String OAUTH_HEADER_SPLITTER = ",";
	private static final String CONSUMER_KEY_SEGMENT_DELIMITER = " ";
	private static final String OAUTH_TOKEN_VALIDATOR_SERVICE = "oauth2TokenValidationService";
	private static final String IDP_LOGIN_USERNAME = "identityServerUserName";
	private static final String IDP_LOGIN_PASSWORD = "identityServerPw";
	private ConfigurationContext configContext;
	private static final Log log = LogFactory.getLog(SimpleOauthHandler.class);
	private static final String ACCESS_TOKEN_NAME = "accessToken";
	private static final String EXPIRED_ACCESS_TOKEN = "expired";
	String refreshToken = "blank";
	String accessToken = "blank";
	int flag = 0;
	long elapsedTimeLimit = (long) 900;
	boolean isTokenValid = false;
	boolean refreshNow = false;
	long tokenValidityInIS = (long) 3300;

	/*
	 * Axis2MessageContext axis2smc = null;
	 * 
	 * org.apache.axis2.context.MessageContext axis2MessageCtx = null;
	 */
	public boolean handleRequest(MessageContext msgCtx) {
		if (this.getConfigContext() == null) {
			log.error("Configuration Context is null");
			return false;
		}
		try {
			Axis2MessageContext axis2Msgcontext = null;  
			org.apache.axis2.context.MessageContext msgContext  = null;  
			axis2Msgcontext = (Axis2MessageContext) msgCtx;  
			msgContext = axis2Msgcontext.getAxis2MessageContext(); 
			String httpMethod = (String) msgContext.getProperty("HTTP_METHOD");  
			org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) msgCtx).getAxis2MessageContext();
	        Map<String, String> transportHeaders  = (Map) axis2MessageContext.getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
			// Read parameters from axis2.xml
			flag = 0;
			String identityServerUrl = msgCtx.getConfiguration()
					.getAxisConfiguration()
					.getParameter(OAUTH_TOKEN_VALIDATOR_SERVICE).getValue()
					.toString();
			String username = msgCtx.getConfiguration().getAxisConfiguration()
					.getParameter(IDP_LOGIN_USERNAME).getValue().toString();
			String password = msgCtx.getConfiguration().getAxisConfiguration()
					.getParameter(IDP_LOGIN_PASSWORD).getValue().toString();
			OAuth2TokenValidationServiceStub stub = new OAuth2TokenValidationServiceStub(
					this.getConfigContext(), identityServerUrl);
			ServiceClient client = stub._getServiceClient();
			Options options = client.getOptions();
			HttpTransportProperties.Authenticator authenticator = new HttpTransportProperties.Authenticator();
			authenticator.setUsername(username);
			authenticator.setPassword(password);
			authenticator.setPreemptiveAuthentication(true);
			options.setProperty(HTTPConstants.AUTHENTICATE, authenticator);
			client.setOptions(options);
			OAuth2TokenValidationRequestDTO dto = this
					.createOAuthValidatorDTO(msgCtx);
			isTokenValid = stub.validate(dto).getValid();
			Long expiresInTime = stub.validate(dto).getExpiryTime();
			Long elapsedTime = tokenValidityInIS - expiresInTime;
			refreshNow = false;
			String errorMessage = stub.validate(dto).getErrorMsg();
			if (elapsedTime > elapsedTimeLimit) {
				refreshNow = true;
			}
			System.out.println(expiresInTime);
			System.out.println(errorMessage);

			if (refreshNow && isTokenValid) {
				flag = 1;
				log.error("token needs to be refreshed");

				MessageContext newCtx = MessageHelper
						.cloneMessageContext(msgCtx);

				// Creating a Block mediator
				BlockingMsgSender bms = new BlockingMsgSender();
				bms.init();

				// removing the postfix url and adding a query parameter
				newCtx.setProperty("REST_URL_POSTFIX", "");
				newCtx.setProperty("BLOCKING_SENDER_PRESERVE_REQ_HEADERS",
						"false");

				newCtx.setProperty("uri.var.location", "Colombo,LK");
				newCtx.setProperty("query.param.access_token", dto
						.getAccessToken().getIdentifier());

				MessageContext returnedCtx = bms.send(
						newCtx.getEndpoint("Refresh_Platform_EP"), newCtx);

				OMElement returnedBody = returnedCtx.getEnvelope().getBody();
				Map<String, String> returnedHeaders = (Map) ((Axis2MessageContext) returnedCtx)
						.getAxis2MessageContext().getProperty(
								"TRANSPORT_HEADERS");

				

				JSONObject jsonObj = null;
				try {
					jsonObj = XML.toJSONObject(returnedBody.toString());
				} catch (JSONException e) {

					e.printStackTrace();
				}
				accessToken = jsonObj.getJSONObject("soapenv:Body")
						.getJSONObject("jsonObject")
						.getString(ACCESS_TOKEN_NAME);

				if (!accessToken.equalsIgnoreCase(EXPIRED_ACCESS_TOKEN)
						&& accessToken != null) {
					Axis2MessageContext axis2smc = (Axis2MessageContext) msgCtx;
					org.apache.axis2.context.MessageContext axis2MessageCtx = axis2smc
							.getAxis2MessageContext();
					Object headers = axis2MessageCtx
							.getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);

					TreeMap headersMap = new TreeMap<String, String>();

					if (headers != null && headers instanceof Map) {
						headersMap = (TreeMap) headers;
						headersMap.put(ACCESS_TOKEN_NAME, accessToken);
					}
					if (headers == null) {
						headersMap.put(ACCESS_TOKEN_NAME, accessToken);
						axis2MessageCtx
								.setProperty(
										org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS,
										headersMap);
					}

					msgCtx.setTo(null);
					msgCtx.setProperty(
							org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS,
							headersMap);
					//Code to replace the new access token for the API request
					Map<String, String> actualPayloadHeaders = (Map) ((Axis2MessageContext) msgCtx)
							.getAxis2MessageContext().getProperty(
									"TRANSPORT_HEADERS");
					TreeMap headerMap = new TreeMap<String, String>();

					if (actualPayloadHeaders != null && actualPayloadHeaders instanceof Map) {
						headerMap = (TreeMap) actualPayloadHeaders;
						headerMap.put("Authorization", "Bearer "+ accessToken.trim());
					}
					msgCtx.setProperty(
							org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS,
							headerMap);
					
					Map<String, String> actualPayloadHeader = (Map) ((Axis2MessageContext) msgCtx)
							.getAxis2MessageContext().getProperty(
									"TRANSPORT_HEADERS");
					return true;
				} else {
					sendBackUnauthorized(msgCtx);
					return false;
				}
			} else if (!isTokenValid) {
				log.error("is not valid token");

				Axis2MessageContext axis2smc = (Axis2MessageContext) msgCtx;
				org.apache.axis2.context.MessageContext axis2MessageCtx = axis2smc
						.getAxis2MessageContext();
				axis2MessageCtx.setProperty("HTTP_SC", "401");
				axis2MessageCtx.setProperty("DISABLE_CHUNKING", true);
				axis2MessageCtx.setProperty("NO_ENTITY_BODY", new Boolean(
						"true"));
				msgCtx.setProperty("RESPONSE", "true");
				msgCtx.setTo(null);

				// Trying to flush response body with dummy tags, but this does
				// not work
				// However it works in Custom mediator implemention.

				SOAPBody body = msgCtx.getEnvelope().getBody();
				body.setFirstChild(AXIOMUtil.stringToOM("<p></p>"));
				Axis2Sender.sendBack(msgCtx);
				return false;

			}
			return isTokenValid;
		} catch (Exception e) {
			log.error("Error occurred while processing the message", e);
			return false;
		}
	}

	private void sendBackUnauthorized(MessageContext msgCtx)
			throws XMLStreamException {
		Axis2MessageContext axis2smc = (Axis2MessageContext) msgCtx;
		org.apache.axis2.context.MessageContext axis2MessageCtx = axis2smc
				.getAxis2MessageContext();
		axis2MessageCtx.setProperty("HTTP_SC", "401");
		axis2MessageCtx.setProperty("DISABLE_CHUNKING", true);
		axis2MessageCtx.setProperty("NO_ENTITY_BODY", new Boolean("true"));
		msgCtx.setProperty("RESPONSE", "true");
		msgCtx.setTo(null);

		// Trying to flush response body with dummy tags, but this does
		// not work
		// However it works in Custom mediator implemention.

		SOAPBody body = msgCtx.getEnvelope().getBody();
		body.setFirstChild(AXIOMUtil.stringToOM("<p></p>"));
		Axis2Sender.sendBack(msgCtx);
	}

	private OAuth2TokenValidationRequestDTO createOAuthValidatorDTO(
			MessageContext msgCtx) {
		OAuth2TokenValidationRequestDTO dto = new OAuth2TokenValidationRequestDTO();
		Map headers = (Map) ((Axis2MessageContext) msgCtx)
				.getAxis2MessageContext()
				.getProperty(
						org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
		String apiKey = null;
		if (headers != null) {
			apiKey = extractCustomerKeyFromAuthHeader(headers);
		}
		OAuth2TokenValidationRequestDTO_OAuth2AccessToken token = new OAuth2TokenValidationRequestDTO_OAuth2AccessToken();
		token.setTokenType("bearer");
		token.setIdentifier(apiKey);
		dto.setAccessToken(token);
		return dto;
	}

	private String extractCustomerKeyFromAuthHeader(Map headersMap) {
		// From 1.0.7 version of this component onwards remove the OAuth
		// authorization header from
		// the message is configurable. So we dont need to remove headers at
		// this point.
		String authHeader = (String) headersMap.get(HttpHeaders.AUTHORIZATION);
		if (authHeader == null) {
			return null;
		}
		if (authHeader.startsWith("OAuth ") || authHeader.startsWith("oauth ")) {
			authHeader = authHeader.substring(authHeader.indexOf("o"));
		}
		String[] headers = authHeader.split(OAUTH_HEADER_SPLITTER);
		if (headers != null) {
			for (String header : headers) {
				String[] elements = header
						.split(CONSUMER_KEY_SEGMENT_DELIMITER);
				if (elements != null && elements.length > 1) {
					boolean isConsumerKeyHeaderAvailable = false;
					for (String element : elements) {
						if (!"".equals(element.trim())) {
							if (CONSUMER_KEY_HEADER.equals(element.trim())) {
								isConsumerKeyHeaderAvailable = true;
							} else if (isConsumerKeyHeaderAvailable) {
								return removeLeadingAndTrailing(element.trim());
							}
						}
					}
				}
			}
		}
		return null;
	}

	private String removeLeadingAndTrailing(String base) {
		String result = base;
		if (base.startsWith("\"") || base.endsWith("\"")) {
			result = base.replace("\"", "");
		}
		return result.trim();
	}

	public boolean handleResponse(MessageContext messageContext) {

		if (this.getConfigContext() == null) {
			log.error("Configuration Context is null");
			return false;
		}

		if (flag == 1) {
			try {

				Axis2MessageContext axis2smc = (Axis2MessageContext) messageContext;
				org.apache.axis2.context.MessageContext axis2MessageCtx = axis2smc
						.getAxis2MessageContext();

				Object headers = axis2MessageCtx
						.getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);

				TreeMap headersMap = new TreeMap<String, String>();

				if (headers != null && headers instanceof Map) {
					headersMap = (TreeMap) headers;
					headersMap.put(ACCESS_TOKEN_NAME, accessToken);
				}
				if (headers == null) {
					headersMap.put(ACCESS_TOKEN_NAME, accessToken);
					axis2MessageCtx
							.setProperty(
									org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS,
									headersMap);
				}
				// axis2MessageCtx.setProperty("HTTP_SC", "401");
				axis2MessageCtx.setProperty("DISABLE_CHUNKING", true);
				axis2MessageCtx.setProperty("NO_ENTITY_BODY", new Boolean(
						"true"));
				messageContext
						.setProperty(
								org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS,
								headersMap);
				messageContext.setProperty("RESPONSE", "true");
				messageContext.setTo(null);

				// Axis2Sender.sendBack(messageContext);
				return true;

			} catch (Exception e) {
				log.error("Error occurred while processing the message", e);
				return false;
			}
		}
		return true;
	}

	public void init(SynapseEnvironment synapseEnvironment) {
		try {
			this.configContext = ConfigurationContextFactory
					.createConfigurationContextFromFileSystem(null, null);
		} catch (AxisFault axisFault) {
			log.error(
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