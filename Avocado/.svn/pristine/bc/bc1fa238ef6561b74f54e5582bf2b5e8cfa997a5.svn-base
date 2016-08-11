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
package com.pcs.avocado.util;

import static com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.avocado.constant.CommonConstants.X_JWT_ASSERTION;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.avocado.commons.dto.Subscription;
import com.pcs.avocado.commons.exception.GalaxyException;

/**
 * This utility class to process cache
 * 
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since avocado-1.0.0
 */

@Component
public class SubscriptionUtility {

	private static final Logger logger = LoggerFactory
			.getLogger(SubscriptionUtility.class);

	private final Base64 decoder = new Base64(true);
	private final ObjectMapper mapper = new ObjectMapper();

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

	public Subscription getSubscription(String jwtObject) {
		String[] pieces = jwtObject.split("\\.");
		JsonNode jwtPayload = null;
		if (pieces.length < 2) {
			throw new GalaxyException(
					INVALID_DATA_SPECIFIED,
					X_JWT_ASSERTION);
		}
		jwtPayload = decodeAndParse(pieces[1]);
		Subscription subscription = new Subscription();
		subscription.setEndUserDomain(jwtPayload.get("endUserDomain").asText());
		subscription.setEndUserName(jwtPayload.get("endUserName").asText());
		subscription.setSubscriberDomain(jwtPayload.get("subscriberDomain")
				.asText());
		subscription.setSubscriberName(jwtPayload.get("subscriberName")
				.asText());
		HashMap<String, String> jwtToken = new HashMap<String, String>();
		jwtToken.put(X_JWT_ASSERTION, jwtObject);
		subscription.setJwtToken(jwtToken);
		if(jwtPayload.get("isSuperAdmin")!=null){
			subscription.setSuperAdmin(jwtPayload.get("isSuperAdmin").asBoolean());
		}
		subscription.setSubscriberApp(jwtPayload.get("subscriberApp").asText());
		subscription.setTenantId(jwtPayload.get("tenantId").asText());
		subscription.setTenantName(jwtPayload.get("tenantId").asText());
		return subscription;
	}
}
