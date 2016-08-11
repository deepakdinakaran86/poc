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
package com.pcs.alpine.commons.email.helper;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MD5Hashing {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MD5Hashing.class);

	public String encodeString(String message) {
		String digest = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			byte[] hash = messageDigest.digest(message.getBytes("UTF-8"));
			StringBuilder stringBuilder = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				stringBuilder.append(String.format("%02x", b & 0xff));
			}
			digest = stringBuilder.toString();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occured while encoding message, ErrorMessage : {}",
					e);
		}
		return digest;
	}

}
