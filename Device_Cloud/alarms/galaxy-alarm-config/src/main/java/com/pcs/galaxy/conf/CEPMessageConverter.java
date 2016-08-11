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
package com.pcs.galaxy.conf;

import java.net.URL;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Component
public class CEPMessageConverter {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(CEPMessageConverter.class);

	private static HashMap<Object, Object> pointsConfiguration;

	/**
	 * 
	 * @param confFilePath
	 */
	CEPMessageConverter() {
		try {

			URL url = YamlUtils.resolveURL("ceppointmapping.yaml");

			pointsConfiguration = YamlUtils
			        .copyYamlFromFile(HashMap.class, url);

		} catch (Exception e) {
			LOGGER.error("CEPMessageConverter | initialize error {}", e);
		}
	}

	public static String getCEPInputMessage(String message) {

		if (message == null) {
			return null;
		}

		LOGGER.info("CEPMessageConverter | getCEPInputMessage {}",
		        message.toString());

		String value = null;
		try {

			value = (String)pointsConfiguration.get(new String(Base64
			        .encodeBase64(message.getBytes())));

		} catch (Exception e) {
			LOGGER.error("CEPMessageConverter | getCEPInputMessage {}", e);

		}
		return value;
	}

}
