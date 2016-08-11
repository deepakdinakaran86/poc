
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
package com.pcs.ccd.services;

import static com.pcs.avocado.commons.validation.ValidationUtils.validateCollection;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.ccd.enums.ContactFields.CONTACTS;
import static com.pcs.ccd.enums.ContactFields.EMAIL;
import static com.pcs.ccd.enums.EmailFields.CONTENT;
import static com.pcs.ccd.enums.EmailFields.EMAIL_TEMPLATE;
import static com.pcs.ccd.enums.EmailFields.SUBJECT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.ccd.bean.Contact;
import com.pcs.ccd.bean.EmailData;
import com.pcs.ccd.bean.EmailNotification;
import com.pcs.ccd.enums.Integraters;
import com.pcs.ccd.token.TokenProvider;

/**
 * This class is responsible for sending email using alpine notification service
 * 
 * @author pcseg129(Seena Jyothish)
 * Feb 3, 2016
 */

@Service
public class NotificationServiceImpl implements NotificationService{
	
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(NotificationServiceImpl.class);

	@Autowired
	@Qualifier("apiMgrClient")
	private BaseClient apiMgrClient;
	
	@Autowired
	private TokenProvider tokenProvider;

	@Value("${api.mgr.email.send}")
	private String sendEmailEndpoint;

	@Override
    public StatusMessageDTO sendEmail(EmailNotification emailData) {
		
		validateMandatoryFields(emailData, CONTENT,
				SUBJECT, EMAIL_TEMPLATE);
		validateCollection(CONTACTS, emailData.getContacts());
		for(Contact contact : emailData.getContacts()){
			validateMandatoryFields(contact, EMAIL);
		}
		
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		for (Contact contact : emailData.getContacts()){
			status = apiMgrClient.post(
					sendEmailEndpoint, tokenProvider.getAuthToken(Integraters.CCD),
					createEmailPayload(emailData,contact), StatusMessageDTO.class);
			if (status == null || status.getStatus().equals(Status.FAILURE)) {
				LOGGER.info("Error in email service, unable to send");
			}
		}
		return status;
    }
	
	private EmailData createEmailPayload(EmailNotification emailData,Contact contact){
		EmailData emailDTO = new EmailData();
		emailDTO.setContent(emailData.getContent());
		emailDTO.setEmailTemplate(emailData.getEmailTemplate());
		emailDTO.setSubject(emailData.getSubject());
		emailDTO.setEmailTemplate(emailData.getEmailTemplate());
		emailDTO.setToAddresses(contact.getEmail());
		emailDTO.setAttachFile(emailData.getAttachFile());
		emailDTO.setContentType(emailData.getContentType());
		emailDTO.setAttachFileName(emailData.getAttachFileName());
		return emailDTO;
	}

}
