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
package com.pcs.eventescalation.listeners;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.commons.beans.RestMessage;
import com.pcs.commons.utils.RestUtils;
import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListenerAdapter;

/**
 * This event escalation listener class responsible for JMS in ASYNC mode
 * escalation (rest).
 * 
 * @author pcseg323 (Greeshma)
 * @date April 2015
 * @since galaxy-1.0.0
 */

public class JmsAsyncListener extends CoreListenerAdapter {

	private static Logger LOGGER = LoggerFactory
	        .getLogger(JmsAsyncListener.class);

	private CoreConsumer consumer;
	public static Properties props = new Properties();
	private static final String PROPERTY_FILE_NAME = "rest.properties";
	private static final String REST_HOST = "rest.endpoint.host";
	private static final String REST_PORT = "rest.endpoint.port";
	private static final String REST_PROTOCOL = "rest.endpoint.protocol";
	private static final String REST_CONTEXT = "rest.endpoint.context";
	private static final String REST_URL = "rest.endpoint.url";

	public CoreConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(CoreConsumer consumer) {
		try {
			consumer.setMessageListener(this);
			this.consumer = consumer;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage)message;

		RestMessage restMessage = new RestMessage();

		loadRestProperties();

		try {
			restMessage.setEndPoint(props.getProperty(REST_PROTOCOL) + "://"
			        + props.getProperty(REST_HOST) + ":"
			        + props.getProperty(REST_PORT)
			        + props.getProperty(REST_CONTEXT)
			        + props.getProperty(REST_URL));
			// TODO add content
			restMessage.setContent(textMessage.getText());
			RestUtils.sendPOSTRequest(restMessage);
			System.out.println("msg" + textMessage.getText());
		} catch (JMSException e) {
			LOGGER.error("Error in listener for rest notification",
			        e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Error in listener for rest notification",
			        e.getMessage());
		}

	}

	private void loadRestProperties() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream resourceStream = loader
		        .getResourceAsStream(PROPERTY_FILE_NAME);
		try {
			props.load(resourceStream);
		} catch (IOException e) {
			LOGGER.error("Error in reading rest.properties for rest notification");
		}

	}

}
