package com.pcs.saffron.deviceregistry.consumer;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.deviceregistry.configuration.RegistryConfiguration;
import com.pcs.saffron.deviceregistry.osgi.util.MessageUtil;
import com.pcs.saffron.deviceregistry.processor.RegistrationHandler;
import com.pcs.saffron.notification.broker.consumer.CoreConsumer;
import com.pcs.saffron.notification.enums.ConsumerType;
import com.pcs.saffron.notification.enums.DistributorMode;

public class RegistrationTracker {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationTracker.class);

	
	public static void listen() {

		try {
			CoreConsumer registrationConsumer = MessageUtil.getNotificationHandler().getConsumer(DistributorMode.KAFKA, ConsumerType.HIGH_LEVEL);
			registrationConsumer.setTopic(RegistryConfiguration.getProperty(RegistryConfiguration.REGISTRY));
			registrationConsumer.setThreadCount(1);
			Properties props = new Properties();
			props.put("group.id", "MessageScanner");
			registrationConsumer.setProperties(props);
			LOGGER.info("Consumer topic set as {}",RegistryConfiguration.getProperty(RegistryConfiguration.REGISTRY));
			
			RegistrationHandler registrationHandler = new RegistrationHandler();
			registrationHandler.setConsumer(registrationConsumer);
			registrationHandler.consumeMessages();
		} catch (Exception e) {
			LOGGER.error("Exception scanning to events !!!",e);
		}

	}


}
