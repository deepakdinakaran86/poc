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
package com.pcs.eventescaltion.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.eventescaltion.beans.EmailMessage;

/**
 * Utility Class for Email Notifications
 * 
 * @author pcseg199
 * @date Mar 25, 2015
 * @since galaxy-1.0.0
 */
public class MailUtils {

	private static Logger LOGGER = LoggerFactory.getLogger(MailUtils.class);
	private static final String PROPERTY_FILE_NAME = "mail.properties";
	private static final String PROPERTY_USER = "mail.smtp.user";
	private static final String PROPERTY_PWD = "mail.smtp.password";
	private static Properties props = new Properties();

	private static Session session = Session.getInstance(props,
	        new javax.mail.Authenticator() {
		        @Override
		        protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(props
			                .getProperty(PROPERTY_USER), props
			                .getProperty(PROPERTY_PWD));
		        }
	        });

	static {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream resourceStream = loader
		        .getResourceAsStream(PROPERTY_FILE_NAME);
		try {
			props.load(resourceStream);
		} catch (IOException e) {
			LOGGER.error("Error in reading mail.properties");
		}
	}

	public static void send(EmailMessage simpleEmail) throws MessagingException {

		// Create a default MimeMessage object.
		Message message = new MimeMessage(session);

		// Set From: header field of the header.
		message.setFrom(new InternetAddress(props
		        .getProperty("mail.smtp.sender.name")));

		// Set To: header field of the header.
		if (StringUtils.isNotBlank(simpleEmail.getToAddress())) {
			message.setRecipients(Message.RecipientType.TO,
			        InternetAddress.parse(simpleEmail.getToAddress()));
		}

		if (StringUtils.isNotBlank(simpleEmail.getCcAddress())) {
			message.setRecipients(Message.RecipientType.CC,
			        InternetAddress.parse(simpleEmail.getCcAddress()));
		}

		if (StringUtils.isNotBlank(simpleEmail.getBccAddress())) {
			message.setRecipients(Message.RecipientType.BCC,
			        InternetAddress.parse(simpleEmail.getBccAddress()));
		}

		// Set Subject: header field
		message.setSubject(simpleEmail.getSubject());

		// Send the actual HTML message, as big as you like
		message.setContent(simpleEmail.getContent(), "text/html");

		// Send message
		Transport.send(message);

		LOGGER.debug("Email sent {}");

	}

	public static void main(String[] args) {
		EmailMessage email = new EmailMessage();
		email.setToAddress("javid@pacificcontrols.net");
		email.setSubject("Subject");
		email.setContent("Content Sample");
		// Recipient's email ID needs to be mentioned.
		try {
			send(email);

		} catch (MessagingException e) {
			LOGGER.debug("Error while sending mail");
		}

	}

}