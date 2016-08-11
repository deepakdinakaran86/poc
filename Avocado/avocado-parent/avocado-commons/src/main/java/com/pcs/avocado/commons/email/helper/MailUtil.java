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
package com.pcs.avocado.commons.email.helper;

/**
 *
 * This class is responsible for sending mails
 *
 * @author Daniela (PCSEG191)
 * @date 22 Oct 2014
 * @since galaxy-1.0.0
 */

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;


public class MailUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MailUtil.class);

	private SimpleMailMessage simpleMailMessage;
	private VelocityEngine velocityEngine;
	private JavaMailSender mailSender;

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void sendMail(String content, String toAddress,
			String subject) {

		final SimpleMailMessage msg = new SimpleMailMessage(simpleMailMessage);
		msg.setTo(toAddress);
		msg.setSubject(subject);
		msg.setText(content);

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(msg.getTo());
				message.setFrom(msg.getFrom());
				message.setSubject(msg.getSubject());
				message.setText(msg.getText(), true);
			}
		};
		try {
			mailSender.send(preparator);

		} catch (Exception e) {
			LOGGER.error("Error sending email", e);
			throw new GalaxyException(
					GalaxyCommonErrorCodes.UNABLE_TO_SEND_EMAIL);
		}
	}

	private void send(final SimpleMailMessage msg,
			final Map<String, Object> hTemplateVariables,
			final String emailTemplate) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(msg.getTo());
				message.setFrom(msg.getFrom());
				message.setSubject(msg.getSubject());
				String body = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, emailTemplate, "utf-8",
						hTemplateVariables);
				message.setText(body, true);
			}
		};
		try {
			mailSender.send(preparator);
		}catch (VelocityException e) {
			LOGGER.error("Error sending email", e);
			throw new GalaxyException(
					GalaxyCommonErrorCodes.UNABLE_TO_SEND_EMAIL);
		}

	}

	public void sendMailWithTemplate(Map<String, Object> hTemplateVariables,String emailTemplate,String subject, String toAddress){
		SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);
		message.setTo(toAddress);
		message.setSubject(subject);
		send(message,hTemplateVariables,emailTemplate);
	}


}
