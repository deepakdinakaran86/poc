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
package com.pcs.data.analyzer.distributors;

import java.io.Serializable;

import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.notification.broker.publisher.CorePublisher;
import com.pcs.saffron.notification.configuration.ApplicationConfiguration;
import com.pcs.saffron.notification.enums.DistributorMode;
import com.pcs.saffron.notification.handler.implementation.NotificationHelper;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date Mar 31, 2015
 * @since galaxy-1.0.0
 */
public class Publisher implements Serializable{

	private static final long serialVersionUID = -8895156645609060202L;
	
	private static Logger LOGGER = LoggerFactory.getLogger(Publisher.class);

	private CorePublisher jmsPublisher;

	private CorePublisher kafkaPublisher;
	
	public Publisher(PublisherConfig publisherConfig) {
		try {
			LOGGER.info("RegistryHost {}", publisherConfig.getRegistryHost() );
			ApplicationConfiguration.init("config.yaml");
			NotificationHelper brokerManager = new NotificationHelper();
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

	public void publishToKafkaTopic(String topicName, Message message) {
		try {
			KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(
					topicName, message.toString());
			kafkaPublisher.publish(keyedMessage);
		} catch (Exception e) {
			LOGGER.info("Error in Error in Remote Invocation", e);
		}
	}
	
	public void publishToKafkaTopic(String topicName, String message) {
		try {
			KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(
					topicName, message);
			kafkaPublisher.publish(keyedMessage);
		} catch (Exception e) {
			LOGGER.info("Error in Error in Remote Invocation", e);
		}
	}
}
