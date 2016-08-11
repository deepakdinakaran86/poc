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
package com.pcs.data.analyzer.storm.persistence.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author PCSEG129(Seena Jyothish)
 */
public class PersistService {
	private static Logger LOGGER = LoggerFactory
	        .getLogger(PersistService.class);
	private Client httpClient;
	private final PublisherConfig publisherConfig;

	public PersistService(PublisherConfig publisherConfig) {
		this.publisherConfig = publisherConfig;
		try {
			this.httpClient = ApacheRestClient.builder()
			        .host(publisherConfig.getPersistAPIHost())
			        .port(publisherConfig.getPersistAPIPort()).scheme("http")
			        .build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in initializing persist service",
			        e);
		}
	}

	public void persist(AlarmMessage message) throws ClientException {

		LOGGER.info("--------- alarm message to persist " + message.toString());
		JsonNode post = httpClient.post(
		        publisherConfig.getpersistAPIBatchURL(), null, message,
		        JsonNode.class);
		if (post == null) {
			LOGGER.error("Error in alarm persist API/Payload :{}", post);
		} else if (post.get("status").asText().equalsIgnoreCase("SUCCESS")) {
			LOGGER.info("Alarm successfully saved with sourceId :{} ,alarm :{}",
			        message.getSourceId(), message.getAlarmName());
		} else {
			LOGGER.info("Could not save alarm from sourceId :{} ,alarm :{}",
			        message.getSourceId(), message.getAlarmName());
		}
	}
}
