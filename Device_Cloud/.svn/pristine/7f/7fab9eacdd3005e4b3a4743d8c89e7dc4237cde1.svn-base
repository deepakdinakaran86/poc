/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
 *
 * This software is the property of Pacific Controls  Software  Services LLC  and  its
 * suppliers. The intellectual and technical concepts contained herein are proprietary 
 * to PCSS. Dissemination of this information or  reproduction  of  this  material  is
 * strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
 * Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
 * MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.pcs.datasource.subscribe;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import weborb.client.Fault;
import weborb.client.Responder;
import weborb.client.WeborbClient;

import com.pcs.datasource.dto.SubscriptionDTO;
import com.pcs.datasource.enums.WebsocketClient;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for providing connection details to subscribe to a
 * topic
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date 08 Apr 2015
 */

@Component
public class DatasourceSubscriber {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DatasourceSubscriber.class);

	@Value("${web.socket.url}")
	private String webscoketUrl;

	@Value("${weborb.url}")
	private String weborbUrl;

	@Value("${mqtt.url}")
	private String mqttUrl;
	
	@Value("${mqtt.topic}")
	private String mqttTopic;

	public String getWebscoketUrl(WebsocketClient wsClient) {
		switch (wsClient) {
		case WEBORB:
			return webscoketUrl;
		case MQTT:
			return mqttUrl;

		default:
			return webscoketUrl;
		}
	}

	public String getDestination(String destination,WebsocketClient wsClient) {
		switch (wsClient) {
		case WEBORB:
			return destination;
		case MQTT:
			return mqttTopic+destination;

		default:
			return destination;
		}
	}

	public SubscriptionDTO getSubscription(String destination,
			WebsocketClient wsClient) {
		SubscriptionDTO subscription = new SubscriptionDTO();
		String wsUrl = getWebscoketUrl(wsClient);
		if (StringUtils.isBlank(wsUrl)) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.WEB_SOCKET_CONNECTION_NOT_AVAILABLE);
		}
		subscription.setWebSocketUrl(wsUrl);
		String dest = getDestination(destination,wsClient);
		subscription.setDestination(dest);
		createDestination(dest);
		return subscription;
	}

	private void createDestination(String destination) {
		WeborbClient client = new WeborbClient(weborbUrl, "GenericDestination");
		Responder responder = new Responder() {
			@Override
			public void responseHandler(Object arg0) {
				LOGGER.info("dest creation response {}", String.valueOf(arg0));
			}

			@Override
			public void errorHandler(Fault arg0) {
				LOGGER.error(
						"Error occurred in destination creation,error message {} , error detail {}",
						arg0.getMessage(), arg0.getDetail());
			}
		};
		try {
			client.invoke("com.pcs.weborb.messaging.DynamicDestinationManager",
					"createDestination", new Object[] { destination,
							destination }, responder);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(
					"Error occurred in calling weborb create destination service",
					e);
		}

	}
}
