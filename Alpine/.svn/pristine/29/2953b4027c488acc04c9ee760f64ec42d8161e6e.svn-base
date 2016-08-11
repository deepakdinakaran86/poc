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
package com.pcs.alpine.util;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.alpine.commons.dto.Subscription;

/**
 * This utility class to process cache
 * 
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since alpine-1.0.0
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
		jwtPayload = decodeAndParse(pieces[1]);
		Subscription subscription = new Subscription();

		if (isNotBlank(jwtPayload.get("endUserDomain").toString())) {
			subscription.setEndUserDomain(jwtPayload.get("endUserDomain")
			        .asText());
		}
		if (isNotBlank(jwtPayload.get("endUserName").toString())) {
			subscription.setEndUserName(jwtPayload.get("endUserName").asText());
		}
		if (isNotBlank(jwtPayload.get("subscriberDomain").toString())) {
			subscription.setSubscriberDomain(jwtPayload.get("subscriberDomain")
			        .asText());
		}
		if (isNotBlank(jwtPayload.get("subscriberName").toString())) {
			subscription.setSubscriberName(jwtPayload.get("subscriberName")
			        .asText());
		}
		if (isNotBlank(jwtPayload.get("subscriberApp").toString())) {
			subscription.setSubscriberApp(jwtPayload.get("subscriberApp")
			        .asText());
		}
		HashMap<String, String> jwtToken = new HashMap<String, String>();
		jwtToken.put("x-jwt-assertion", jwtObject);
		subscription.setJwtToken(jwtToken);
		return subscription;
	}

	// public static void main(String[] args) {
	// String token =
	// "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ill6aGxOelJqWkRZek1tSXpZV0U1WldReE9HUm1ZMkl6WVRCaE5UTTBPR05tTW1RM09HVm1PQT09In0=.eyJpc3MiOiJ3c28yLm9yZy9wcm9kdWN0cy9hbSIsImV4cCI6MTQ2NjYwNTc0NjU4NSwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9zdWJzY3JpYmVyIjoicGNzc3Vic2NyaWJlciIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvYXBwbGljYXRpb25pZCI6IjQiLCJodHRwOi8vd3NvMi5vcmcvY2xhaW1zL2FwcGxpY2F0aW9ubmFtZSI6InBjcyIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvYXBwbGljYXRpb250aWVyIjoiVW5saW1pdGVkIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcGljb250ZXh0IjoiL2dhbGF4eS1hbS8xLjAuMCIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdmVyc2lvbiI6IjEuMC4wIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy90aWVyIjoiVW5saW1pdGVkIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9rZXl0eXBlIjoiUFJPRFVDVElPTiIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdXNlcnR5cGUiOiJBUFBMSUNBVElPTl9VU0VSIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9lbmR1c2VyIjoicGNzYWRtaW5AcGNzLmdhbGF4eSIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvZW5kdXNlclRlbmFudElkIjoiMSIsIkNvbnN1bWVyS2V5IjoicEhFekF3aE1MMXRhRnFSbXJXWnNpMEN6MlZRYSIsImVuZFVzZXJEb21haW4iOiJwY3MuZ2FsYXh5IiwiZW5kVXNlck5hbWUiOiJwY3NhZG1pbiIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvZW1haWxhZGRyZXNzIjoiZGVlcGFrZEBwYWNpZmljY29udHJvbHMubmV0IiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9mdWxsbmFtZSI6InBjc2FkbWluIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9naXZlbm5hbWUiOiJEZWVwYWsiLCJodHRwOi8vd3NvMi5vcmcvY2xhaW1zL2xhc3RuYW1lIjoiRGluYWthcmFuIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9tb2JpbGUiOiIwNTU0NDY1ODUyIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9wZXJtaXNzaW9uIjoibnVsbCIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvcm9sZSI6IlN1cGVyQWRtaW4sSW50ZXJuYWwvZXZlcnlvbmUiLCJpc1N1cGVyQWRtaW4iOiJ0cnVlIiwic3Vic2NyaWJlckFwcCI6InBjcyIsInN1YnNjcmliZXJEb21haW4iOiJudWxsIiwic3Vic2NyaWJlck5hbWUiOiJwY3NzdWJzY3JpYmVyIiwidGVuYW50SWQiOiJwY3MifQ==.cpGxW33S2vckVud2mZoD3DTOEGUn9JnZq89ET8Yv8JLQCacDrJCvk08HgfqFqxQOTFpFJiYOrZLWkSuIzC1Wkydhgk0pO923AzhvRgBp2Lze7SXvtKnDDiE90yvedo8mTcAWbrXko5eD4eZtBAJFqcxeCJ6DLdb5dUQjIuQRXP4=";
	// // String alphaAndDigits = token.replaceAll("[^a-zA-Z0-9]+", "");
	// SubscriptionUtility s = new SubscriptionUtility();
	//
	// // String str =
	// //
	// "yJpc3MiOiJ3c28yLm9yZy9wcm9kdWN0cy9hbSIsImV4cCI6MTQ2NjYwODQzMzY2MSwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9zdWJzY3JpYmVyIjoicGNzc3Vic2NyaWJlciIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvYXBwbGljYXRpb25pZCI6IjQiLCJodHRwOi8vd3NvMi5vcmcvY2xhaW1zL2FwcGxpY2F0aW9ubmFtZSI6InBjcyIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvYXBwbGljYXRpb250aWVyIjoiVW5saW1pdGVkIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcGljb250ZXh0IjoiL2dhbGF4eS1hbS8xLjAuMCIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdmVyc2lvbiI6IjEuMC4wIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy90aWVyIjoiVW5saW1pdGVkIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9rZXl0eXBlIjoiUFJPRFVDVElPTiIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdXNlcnR5cGUiOiJBUFBMSUNBVElPTl9VU0VSIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9lbmR1c2VyIjoicGNzYWRtaW5AcGNzLmdhbGF4eSIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvZW5kdXNlclRlbmFudElkIjoiMSIsIkNvbnN1bWVyS2V5IjoicEhFekF3aE1MMXRhRnFSbXJXWnNpMEN6MlZRYSIsImVuZFVzZXJEb21haW4iOiJwY3MuZ2FsYXh5IiwiZW5kVXNlck5hbWUiOiJwY3NhZG1pbiIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvZW1haWxhZGRyZXNzIjoiZGVlcGFrZEBwYWNpZmljY29udHJvbHMubmV0IiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9mdWxsbmFtZSI6InBjc2FkbWluIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9naXZlbm5hbWUiOiJEZWVwYWsiLCJodHRwOi8vd3NvMi5vcmcvY2xhaW1zL2xhc3RuYW1lIjoiRGluYWthcmFuIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9tb2JpbGUiOiIwNTU0NDY1ODUyIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9wZXJtaXNzaW9uIjoibnVsbCIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvcm9sZSI6IlN1cGVyQWRtaW4sSW50ZXJuYWwvZXZlcnlvbmUiLCJpc1N1cGVyQWRtaW4iOiJ0cnVlIiwic3Vic2NyaWJlckFwcCI6InBjcyIsInN1YnNjcmliZXJEb21haW4iOiJudWxsIiwic3Vic2NyaWJlck5hbWUiOiJwY3NzdWJzY3JpYmVyIiwidGVuYW50SWQiOiJwY3MifQ==";
	// // String specialCharacters = "[" + "-/@#!*$%^&.'_+={}()" + "]+";
	// //
	// // if (str.matches(specialCharacters)) {
	// // System.out.println("string '" + str
	// // + "' contains only special character");
	// // } else {
	// // System.out.println("string '" + str
	// // + "' doesn't contains only special character");
	// // }
	//
	// // s.getSubscription(token);
	//
	// String str = "abc$def^ghi#jkl";
	//
	// Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
	// Matcher m = p.matcher(str);
	//
	// System.out.println(str);
	// int count = 0;
	// while (m.find()) {
	// count = count + 1;
	// System.out.println("position " + m.start() + ": "
	// + str.charAt(m.start()));
	// }
	// System.out.println("There are " + count + " special characters");
	// }
}
