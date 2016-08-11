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
package com.pcs.eventescaltion.listeners;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListenerAdapter;
import com.pcs.eventescaltion.beans.EmailMessage;
import com.pcs.eventescaltion.utils.MailUtils;

/**
 * This event escalation listener class responsible for JMS in ASYNC mode
 * escalation (email).
 * 
 * @author pcseg323 (Greeshma)
 * @date April 2015
 * @since galaxy-1.0.0
 */

public class JmsAsyncListener extends CoreListenerAdapter {

	private static Logger LOGGER = LoggerFactory
	        .getLogger(JmsAsyncListener.class);

	public static Properties props = new Properties();
	private static final String PROPERTY_FILE_NAME = "receiver.properties";
	private static final String RECEIVER_ADRS = "mail.receive.address";
	private static final String RECEIVER_SUBJECT = "mail.receive.subject";

	private CoreConsumer consumer;

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
		EmailMessage emailMessage = new EmailMessage();
		loadReceiverProperties();

		try {
			String latestMessage = textMessage.getText();

			emailMessage.setToAddress(props.getProperty(RECEIVER_ADRS));
			// TODO send email using template
			emailMessage.setSubject(props.getProperty(RECEIVER_SUBJECT));
			emailMessage.setContent("Dear Admin,<BR><BR>" + latestMessage
			        + "<BR><BR>Regards,<BR><BR>Galaxy 2021");
			MailUtils.send(emailMessage);
		} catch (JMSException e) {
			LOGGER.error("Error in listener for email notification",
			        e.getMessage());
		} catch (MessagingException e) {
			LOGGER.error("Error in listener for email notification",
			        e.getMessage());
		}

	}

	private void loadReceiverProperties() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream resourceStream = loader
		        .getResourceAsStream(PROPERTY_FILE_NAME);
		try {
			props.load(resourceStream);
		} catch (IOException e) {
			LOGGER.error("Error in reading receiver.properties for email notification");
		}

	}

}
