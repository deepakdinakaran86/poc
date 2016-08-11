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
package com.pcs.devicecloud.commons.email.serviceimpl;

import static com.pcs.devicecloud.enums.CommonFields.CONTENT;
import static com.pcs.devicecloud.enums.CommonFields.DOMAIN_NAME;
import static com.pcs.devicecloud.enums.CommonFields.EMAIL_TEMPLATE;
import static com.pcs.devicecloud.enums.CommonFields.SUBJECT;
import static com.pcs.devicecloud.enums.CommonFields.TO;
import static com.pcs.devicecloud.enums.CommonFields.TO_ADDRESS;
import static com.pcs.devicecloud.enums.CommonFields.URL;
import static com.pcs.devicecloud.enums.CommonFields.USER_NAME;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.devicecloud.commons.email.dto.EmailDTO;
import com.pcs.devicecloud.commons.email.helper.MailUtil;
import com.pcs.devicecloud.commons.email.service.EmailService;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * 
 * This class is responsible for reading mail properties and
 * 
 * @author Riyas PH
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
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        TO_ADDRESS.getDescription());
		} else if (isBlank(emailDTO.getContent())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        CONTENT.getDescription());
		} else if (isBlank(emailDTO.getEmailTemplate())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        EMAIL_TEMPLATE.getDescription());
		} else if (isBlank(emailDTO.getSubject())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
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
			model.put(TO.getFieldName(), ((emailDTO.getTo().substring(0, 1)
			        .toUpperCase()) + (emailDTO.getTo().substring(1)
			        .toLowerCase())));
			model.put(CONTENT.getFieldName(), emailDTO.getContent());
			model.put(URL.getFieldName(), url);
			model.put(USER_NAME.getFieldName(), userName);
			model.put(DOMAIN_NAME.getFieldName(), domain);

			mail.sendMailWithTemplate(model, emailDTO.getEmailTemplate(),
			        emailDTO.getSubject(), emailDTO.getToAddresses());

		} catch (Exception e) {
			LOGGER.error(
			        "Exception occured while sending email, ErrorMessage : {}",
			        e);
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.UNABLE_TO_SEND_EMAIL);
		}
	}
}
