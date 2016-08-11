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
package com.pcs.ccd.publisher;

import static com.pcs.deviceframework.datadist.enums.DistributorMode.JMS;
import static com.pcs.deviceframework.datadist.enums.DistributorMode.KAFKA;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.util.ObjectBuilderUtil;
import com.pcs.avocado.enums.Status;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;

/**
 * This class is responsible for publishing messages
 *
 * @author pcseg129(Seena Jyothish)
 * @date 26 Jan 2015
 */

@Component
public class MessagePublisher {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessagePublisher.class);

	@Value("${rmi.registry.name}")
	private String registryName;

	@Value("${rmi.port}")
	private String rmiPort;

	@Value("${rmi.server.host}")
	private String rmiServer;

	private static CorePublisher jmsPublisher = null;
	private static CorePublisher kafkaPublisher = null;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	public MessagePublisher() {

	}

	public StatusMessageDTO publishToJMSTopic(String topicName,
			Serializable messageDTO) {
		StatusMessageDTO status = new StatusMessageDTO();
		try {
			if (jmsPublisher == null) {
				jmsPublisher = getPublisher(JMS);
			}
			jmsPublisher.publishToTopic(topicName, objectBuilderUtil.getGson()
					.toJson(messageDTO));
			status.setStatus(Status.SUCCESS);
		} catch (Exception e) {
			status.setStatus(Status.FAILURE);
			LOGGER.error(
					"Error occurred while publishing data from {} with exception {}",
					topicName, e);
		}
		return status;
	}

	public StatusMessageDTO publishToJMSQueue(String queueName, String message) {
		StatusMessageDTO status = new StatusMessageDTO();
		try {
			if (jmsPublisher == null) {
				jmsPublisher = getPublisher(JMS);
			}
			jmsPublisher.publishToQueue(queueName, message);
			status.setStatus(Status.SUCCESS);
		} catch (Exception e) {
			status.setStatus(Status.FAILURE);
			LOGGER.error(
					"Error occurred while publishing data from {} with exception {}",
					queueName, e);
		}
		return status;
	}

	/**
	 * To publish to a kafka topic
	 * 
	 * @param topicName
	 * @param message
	 * @throws RemoteException
	 */
	public StatusMessageDTO publishToKafkaTopic(String topicName, String message)
			throws RemoteException {
		StatusMessageDTO status = new StatusMessageDTO();
		try {
			if (kafkaPublisher == null) {
				kafkaPublisher = getPublisher(KAFKA);
			}

			List<Serializable> keyedMessages = new ArrayList<Serializable>();
			KeyedMessage<Object, Object> keyedMessage = new KeyedMessage<Object, Object>(
					topicName, message);
			keyedMessages.add(keyedMessage);

			kafkaPublisher.publish(keyedMessages);
			status.setStatus(Status.SUCCESS);
		} catch (Exception e) {
			status.setStatus(Status.FAILURE);
			LOGGER.error(
					"Error occurred while publishing data to kafka from {} with exception {}",
					topicName, e);
		}
		return status;
	}

	private CorePublisher getPublisher(DistributorMode distributorMode) {
		try {
			Registry registry = LocateRegistry.getRegistry(rmiServer,
					Integer.parseInt(rmiPort));
			DistributionManager brokerManager = (DistributionManager) registry
					.lookup(registryName);
			jmsPublisher = brokerManager.getPublisher(distributorMode);
			return jmsPublisher;
		} catch (Exception ex) {
			LOGGER.error("Error while accessing remote objects", ex);
		}
		return null;
	}
}
