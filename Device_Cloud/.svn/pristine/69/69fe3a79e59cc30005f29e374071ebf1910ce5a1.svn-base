package com.pcs.eventescalation.utils;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.commons.beans.RestMessage;
import com.pcs.commons.utils.RestUtils;
import com.pcs.eventescalation.beans.SMSMessage;

public class SMSUtils {

	private static Logger LOGGER = LoggerFactory.getLogger(SMSUtils.class);

	private static ResourceBundle smsPpts = ResourceBundle.getBundle("sms");

	private static String endPoint = smsPpts.getString("sms.rest.api");

	/**
	 * Message Responsible for sending SMS through a Rest based gateway
	 * 
	 * @param smsMessage
	 * @throws Exception
	 */
	public static void send(SMSMessage smsMessage) throws Exception {

		LOGGER.error("Sending SMS");

		if (isNotBlank(smsMessage.getNumber())
		        && isNotBlank(smsMessage.getContent())) {

			String url = endPoint.replace("{number}", smsMessage.getNumber())
			        .replace("{content}", smsMessage.getContent());

			try {
				RestMessage restDTO = new RestMessage();
				restDTO.setEndPoint(url);
				RestUtils.sendGETRequest(restDTO);;
			} catch (Exception e) {
				throw new Exception("Error Sending SMS", e);
			}

		} else {
			throw new Exception("Request is invalid");
		}
		LOGGER.error("Message sent to: {}", smsMessage.getNumber());

	}

	public static void main(String[] args) {
		SMSMessage smsMessage = new SMSMessage();
		smsMessage.setNumber("+971561518203");
		smsMessage.setContent("content");;
		try {
			send(smsMessage);
		} catch (Exception e) {
			LOGGER.error("error while sending sms");
		}
	}
}
