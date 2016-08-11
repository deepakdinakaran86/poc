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
package com.pcs.alpine.commons.email.serviceimpl;

import static com.pcs.alpine.enums.CommonFields.CONTENT;
import static com.pcs.alpine.enums.CommonFields.DOMAIN_NAME;
import static com.pcs.alpine.enums.CommonFields.EMAIL_TEMPLATE;
import static com.pcs.alpine.enums.CommonFields.SUBJECT;
import static com.pcs.alpine.enums.CommonFields.TO;
import static com.pcs.alpine.enums.CommonFields.TO_ADDRESS;
import static com.pcs.alpine.enums.CommonFields.URL;
import static com.pcs.alpine.enums.CommonFields.USER_NAME;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.email.dto.EmailDTO;
import com.pcs.alpine.commons.email.helper.MailUtil;
import com.pcs.alpine.commons.email.service.EmailService;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;

/**
 * 
 * This class is responsible for reading mail properties and
 * 
 * @author Riyas PH
 * @date September 21 2014
 * @since galaxy-1.0.0
 */
@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EmailServiceImpl.class);

	@Autowired(required = false)
	private MailUtil mail;

	@Override
	public void sendEmail(EmailDTO emailDTO) {

		if (isBlank(emailDTO.getToAddresses())) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					TO_ADDRESS.getDescription());
		} else if (isBlank(emailDTO.getContent())) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					CONTENT.getDescription());
		} else if (isBlank(emailDTO.getEmailTemplate())) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					EMAIL_TEMPLATE.getDescription());
		} else if (isBlank(emailDTO.getSubject())) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					SUBJECT.getDescription());
		}
		try {
			String url = null;
			String userName = null;
			String domain = null;
			if (emailDTO.getUserName() != null
					&& !isBlank(emailDTO.getUserName())) {
				userName = emailDTO.getUserName();
			}
			if (emailDTO.getUrl() != null && !isBlank(emailDTO.getUrl())) {
				url = emailDTO.getUrl();
			}
			if (emailDTO.getDomain() != null && !isBlank(emailDTO.getDomain())) {
				domain = emailDTO.getDomain();
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put(TO.getFieldName(),
					(WordUtils.capitalize(emailDTO.getTo())));
			model.put(CONTENT.getFieldName(), emailDTO.getContent());
			model.put(URL.getFieldName(), url);
			model.put(USER_NAME.getFieldName(), userName);
			model.put(DOMAIN_NAME.getFieldName(), domain);

			LOGGER.info("*****{},{},{}", emailDTO.getTo(),
					emailDTO.getContent(), emailDTO.getToAddresses());

			if (emailDTO.getAttachFile() != null
					&& emailDTO.getContentType() != null
					&& emailDTO.getAttachFileName() != null) {
				mail.sendMailWithAttachment(model, emailDTO);
			} else {
				
				mail.sendMailWithTemplate(model, emailDTO.getEmailTemplate(),
						emailDTO.getSubject(), emailDTO.getToAddresses());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occured while sending email, ErrorMessage : {}",
					e);
			throw new GalaxyException(
					GalaxyCommonErrorCodes.UNABLE_TO_SEND_EMAIL);
		}
	}
}
