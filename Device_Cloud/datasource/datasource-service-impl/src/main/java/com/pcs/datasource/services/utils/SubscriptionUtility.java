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
package com.pcs.datasource.services.utils;

import static com.pcs.datasource.enums.DeviceDataFields.JWTHEADER;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.services.SubscriptionService;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This utility class to process cache
 * 
 * @author pcseg296 (RIYAS PH)
 * @date July 2015
 * @since galaxy-1.0.0
 */

@Component
public class SubscriptionUtility {

	private static final Logger logger = LoggerFactory
			.getLogger(SubscriptionUtility.class);

	private final Base64 decoder = new Base64(true);
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private SubscriptionService subscriptionService;

	public JsonNode decodeAndParse(String b64String) {

		JsonNode jwtHeader = null;
		try {
			String jsonString = new String(decoder.decode(b64String), "UTF-8");
			jwtHeader = mapper.readValue(jsonString, JsonNode.class);
		} catch (IOException e) {
			logger.error("jwtObject parsor exception" + e.getMessage());
		}
		return jwtHeader;
	}
	
	public Subscription createSubscription(String jwtObject) {
		if (StringUtils.isEmpty(jwtObject)) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					JWTHEADER.getDescription());
		}
		String[] pieces = jwtObject.split("\\.");
		JsonNode jwtPayload = null;
		jwtPayload = decodeAndParse(pieces[1]);
		if (jwtPayload == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					JWTHEADER.getDescription());
		}
		Subscription subscription = new Subscription();
		JsonNode consumerKey = jwtPayload.get("ConsumerKey");
		if (consumerKey == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					JWTHEADER.getDescription());
		}
		subscription.setSubId(consumerKey.asText());
		return subscription;
	}

	public Subscription getSubscription(String jwtObject) {
		if (StringUtils.isEmpty(jwtObject)) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					JWTHEADER.getDescription());
		}
		String[] pieces = jwtObject.split("\\.");
		JsonNode jwtPayload = null;
		jwtPayload = decodeAndParse(pieces[1]);
		if (jwtPayload == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					JWTHEADER.getDescription());
		}
		
		JsonNode consumerKey = jwtPayload.get("ConsumerKey");
		if (consumerKey == null) {
			throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
					JWTHEADER.getDescription());
		}
		String subIdFromJWT = consumerKey.asText();
		if(subscriptionService.isSubscriptionIdExist(subIdFromJWT)){
			Subscription subscription = new Subscription();
			subscription.setSubId(subIdFromJWT);
			return subscription;
		}
		throw new DeviceCloudException(INVALID_DATA_SPECIFIED,
				JWTHEADER.getDescription());
	}
}
