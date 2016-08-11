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
package com.pcs.analytics.distributors;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pcs.device.gateway.commons.http.ApacheRestClient;
import com.pcs.device.gateway.commons.http.Client;
import com.pcs.device.gateway.commons.http.ClientException;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;
import com.pcs.deviceframework.decoder.data.message.Message;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date Mar 31, 2015
 * @since galaxy-1.0.0
 */
public class Publisher {

	private static Logger LOGGER = LoggerFactory.getLogger(Publisher.class);
	private static Double POINT_INSERT_COUNT = 0d;

	private Client httpClient;

	private CorePublisher jmsPublisher;

	private CorePublisher kafkaPublisher;
	
	private static final Gson GSON = new GsonBuilder().create();

	private final PublisherConfig publisherConfig;

	/**
	 *
	 */
	public Publisher(PublisherConfig publisherConfig) {
		this.publisherConfig = publisherConfig;
		try {
			
			this.httpClient = ApacheRestClient.builder()
					.host(publisherConfig.getPersistAPIHost())
					.port(publisherConfig.getPersistAPIPort()).scheme("http")
					.build();

			Registry registry = LocateRegistry.getRegistry(
			        publisherConfig.getRegistryHost(),
			        publisherConfig.getRegistryPort());
			DistributionManager brokerManager = (DistributionManager)registry
			        .lookup(publisherConfig.getRegistryName());
			this.jmsPublisher = brokerManager.getPublisher(DistributorMode.JMS);
			this.kafkaPublisher = brokerManager
			        .getPublisher(DistributorMode.KAFKA);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in Locating Remote Registry", e);
		}
	}

	public void publishToJMSQueue(String queueName, String message) {
		try {
			LOGGER.info("Publishing to JMS queue : " + queueName);
			jmsPublisher.publishToQueue(queueName, message);
		} catch (Exception e) {
			LOGGER.info("Error in publishing data to JMS", e);
		}
	}

	public void publishToJMSTopic(String topicName, String message) {
		try {
			LOGGER.info("Publishing to JMS topicName : " + topicName);
			jmsPublisher.publishToTopic(topicName, message);
		} catch (Exception e) {
			LOGGER.info("Error in publishing data to JMS", e);
		}
	}

	public void publishToKafkaTopic(String topicName, Message object) {
		String json = null;
		try {
			json = GSON.toJson(object);
			KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(
					topicName, json);
			LOGGER.info("Publishing to {} with object {}", topicName, json);
			kafkaPublisher.publish(keyedMessage);
		} catch (Exception e) {
			LOGGER.info("Error in Error in Remote Invocation", e);
		}

	}

	public void persist(Message message) throws ClientException {
		
		JsonNode post = httpClient.post(
				publisherConfig.getpersistAPIBatchURL(), null, message,
				JsonNode.class);
		if (post == null){
			LOGGER.error("Error in Persist API/Payload :{}", post);
		}else if(post.get("status").asText().equalsIgnoreCase("SUCCESS")) {
			LOGGER.info("Successfully Saved with sourceId :{} ,time :{}",message.getSourceId(), message.getTime());
			POINT_INSERT_COUNT += message.getPoints().size();
		} else if(post.get("status").asText().equalsIgnoreCase("PARTIAL")) {
			LOGGER.info("Partialy Saved with sourceId :{} ,time :{}",message.getSourceId(), message.getTime());
			int failedCount = post.get("failedDeviceMsgs") != null? post.get("failedDeviceMsgs").asInt():0;
			POINT_INSERT_COUNT += (message.getPoints().size()-failedCount);
		}else{
			LOGGER.info("Could Not Save data from sourceId :{} ,time :{}",message.getSourceId(), message.getTime());
		}
		LOGGER.info("Total Points Persisted So Far := {}",POINT_INSERT_COUNT);
	}
}
