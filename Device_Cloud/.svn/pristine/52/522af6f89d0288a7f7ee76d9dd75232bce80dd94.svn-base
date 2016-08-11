/**
 * 
 */
package com.pcs.device.activity.publisher;

import static com.pcs.device.activity.utils.ActivityUpdaterUtil.getServicesConfig;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Apr 19, 2016
 */
public class Publisher {
	
	private static Logger logger = LoggerFactory.getLogger(Publisher.class);

	private CorePublisher kafkaPublisher;

	public Publisher(){
		initializeRegistry();
	}
	
	public void initializeRegistry() {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(getServicesConfig()
					.getRegistryHost(), getServicesConfig().getRegistryPort());
			DistributionManager brokerManager = (DistributionManager) registry
					.lookup(getServicesConfig().getRegistryName());
			this.kafkaPublisher = brokerManager
					.getPublisher(DistributorMode.KAFKA);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in Locating Remote Registry", e);
		}
	}

	public void publishToKafkaTopic(String topicName, String message) {
		try {
			KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(
					topicName, message);
			kafkaPublisher.publish(keyedMessage);
		} catch (Exception e) {
			logger.info("Error in publishing to kafka", e);
		}
	}
}
