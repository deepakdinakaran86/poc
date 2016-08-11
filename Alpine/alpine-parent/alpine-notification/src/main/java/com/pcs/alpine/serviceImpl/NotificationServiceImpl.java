/**
 Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 Reserved.

 This software is the property of Pacific Controls Software Services LLS and
 its suppliers. The intellectual and technical concepts contained herein are
 proprietary to PCSS. Dissemination of this information or reproduction of
 this material is strictly forbidden unless prior written permission is
 obtained from Pacific Controls Software Services.

 PCSS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 NON-INFRINGEMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 LICENSE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 DERIVATIVES.
 */
package com.pcs.alpine.serviceImpl;

import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.services.enums.EMDataFields.NEWPASSWORD;
import static com.pcs.alpine.services.enums.EMDataFields.PASSWORD;
import static com.pcs.alpine.services.enums.EMDataFields.USER_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.TO_MOBILE_NUMBER;
import static com.pcs.alpine.services.enums.EMDataFields.BODY;
import static com.pcs.alpine.services.enums.NotificationDataFields.TO_ADDRESS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.email.dto.EmailDTO;
import com.pcs.alpine.commons.email.service.EmailService;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.sms.dto.SMSDTO;
import com.pcs.alpine.commons.sms.dto.SMSStatsDTO;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.constants.NameConstants;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.services.NotificationService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.MessageList;

/**
 * @description Implementation Class for Custom Entity Services
 * @author bhupendra (pcseg329)
 * @date 14 Jan 2015
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NotificationServiceImpl.class);

	@Value("${smtp.host}")
	private String smtpHost;

	@Value("${alpine.email.id}")
	private String alpineEmailId;

	@Value("${alpine.email.sender.name}")
	private String alpineEmailSenderName;

	@Value("${alpine.email.user.name}")
	private String alpineEmailUserName;

	@Value("${alpine.email.password}")
	private String alpineEmailPassword;

	@Value("${alpine.email.port}")
	private String alpineSmtpPort;

	@Value("${apiManager.host}")
	private String apiManagerHost;

	@Autowired
	private EmailService emailService;

	@Value("${twilio.account.sid}")
	private String ACCOUNT_SID;

	@Value("${twilio.auth.token}")
	private String AUTH_TOKEN;

	@Value("${twilio.from.number}")
	private String fromMobileNumber;

	@Override
	public StatusMessageDTO sendEmail(EmailDTO emailDTO) {
		// validate input
		validateEmailDto(emailDTO);

		// Add the relevant fields
		emailDTO.setSmtpHost(smtpHost);
		emailDTO.setAlpineEmailId(alpineEmailId);
		emailDTO.setAlpineEmailSenderName(alpineEmailSenderName);
		emailDTO.setAlpineEmailUserName(alpineEmailUserName);
		emailDTO.setAlpineEmailPassword(alpineEmailPassword);
		emailDTO.setAlpineSmtpPort(alpineSmtpPort);

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		try {
			// Invoke the send email common service
			emailService.sendEmail(emailDTO);
			statusMessageDTO.setStatus(Status.SUCCESS);
		} catch (GalaxyException galaxyException) {
			throw galaxyException;
		}
		return statusMessageDTO;
	}

	private void validateEmailDto(EmailDTO emailDTO) {
		ValidationUtils.validateMandatoryField(TO_ADDRESS,
				emailDTO.getToAddresses());
	}

	@Override
	public StatusMessageDTO createUserAPIManager(String userName,
			String password) {
		HttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost("http://" + apiManagerHost
				+ "/store/site/blocks/user/sign-up/ajax/user-add.jag");

		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(
				4);
		nameValuePairs.add(new BasicNameValuePair("action", "addUser")); // you
																			// can
																			// as
																			// many
																			// name
																			// value
																			// pair
																			// as
																			// you
																			// want
																			// in
																			// the
																			// list.
		nameValuePairs.add(new BasicNameValuePair("username", userName));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("allFieldsValues",
				"firstname|lastname|email"));

		String line = "";
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			StringBuilder buffer = new StringBuilder();

			while ((line = rd.readLine()) != null) {
				buffer.append(line);
			}
			line = buffer.toString();
		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.GENERAL_EXCEPTION);
		}
		String jsonIn = "[" + line + "]";
		JsonArray o = (JsonArray) new JsonParser().parse(jsonIn);
		String status = o.get(0).getAsJsonObject().get("error").getAsString();

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		if (status.equalsIgnoreCase("false")) {
			statusMessageDTO.setStatus(Status.SUCCESS);
		}
		return statusMessageDTO;
	}

	// Find your Account Sid and Token at twilio.com/user/account. Stored in
	// alpine-platform.properties

	@Override
	public StatusMessageDTO sendSMS(SMSDTO smsDTO) {
		// Validate input
		validateMandatoryFields(smsDTO, TO_MOBILE_NUMBER, BODY);
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);
		try {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID,
					AUTH_TOKEN);

			// Set the from number as the twilio number
			smsDTO.setFromMobileNumber(fromMobileNumber);

			// Build a filter for the MessageList
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(NameConstants.BODY, smsDTO
					.getBody()));
			params.add(new BasicNameValuePair(NameConstants.TO, smsDTO
					.getToMobileNumber()));
			params.add(new BasicNameValuePair(NameConstants.FROM, smsDTO
					.getFromMobileNumber()));

			MessageFactory messageFactory = client.getAccount()
					.getMessageFactory();
			Message message = messageFactory.create(params);
			System.out.println(message.getSid());

		} catch (TwilioRestException e) {
			LOGGER.info("Twilio Excpetion : " + e.getErrorMessage());
			statusMessageDTO.setStatus(Status.FAILURE);
		}
		return statusMessageDTO;
	}

	@Override
	public StatusMessageDTO subscribe(SMSDTO smsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessageDTO receiveSMS(SMSDTO smsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SMSStatsDTO> getSMStatistics() {
		List<SMSStatsDTO> statsDTOs = new ArrayList<SMSStatsDTO>();
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		MessageList messages = client.getAccount().getMessages();

		statsDTOs = getSMSStatsFromMessageList(messages);

		return statsDTOs;
	}

	private List<SMSStatsDTO> getSMSStatsFromMessageList(MessageList messages) {
		List<SMSStatsDTO> statsDTOs = new ArrayList<SMSStatsDTO>();
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss Z");
		if (CollectionUtils.isNotEmpty(messages.getPageData()))
			for (Message message : messages) {
				SMSStatsDTO smsStatsDTO = new SMSStatsDTO();
				smsStatsDTO.setAccountId(message.getAccountSid());
				smsStatsDTO.setFrom(message.getFrom());
				smsStatsDTO.setTo(message.getTo());
				smsStatsDTO.setStatus(message.getStatus());
				smsStatsDTO.setBody(message.getBody());
				smsStatsDTO.setPrice(message.getPrice());
				smsStatsDTO.setPriceUnit(message.getPriceUnit());
				smsStatsDTO.setErrorMessage(message.getErrorMessage());
				smsStatsDTO.setDirection(message.getDirection());
				try {
					smsStatsDTO.setDate((format.parse(message
							.getProperty("date_created")).toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				statsDTOs.add(smsStatsDTO);
			}
		return statsDTOs;
	}

}
