package com.pcs.saffron.deviceregistry.handler;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.deviceregistry.configuration.RegistryConfiguration;
import com.pcs.saffron.deviceregistry.osgi.util.MessageUtil;
import com.pcs.saffron.deviceregistry.processor.RegistrationHandler;
import com.pcs.saffron.notification.broker.consumer.CoreConsumer;
import com.pcs.saffron.notification.enums.ConsumerType;
import com.pcs.saffron.notification.enums.DistributorMode;

public class RegistrationProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationProcessor.class);

	
	public static void listen() {

		try {
			CoreConsumer commandConsumer = MessageUtil.getNotificationHandler().getConsumer(DistributorMode.KAFKA, ConsumerType.HIGH_LEVEL);
			commandConsumer.setTopic(RegistryConfiguration.REGISTRY);
			commandConsumer.setThreadCount(1);
			Properties props = new Properties();
			props.put("group.id", "DeviceRegistry");
			commandConsumer.setProperties(props);
			LOGGER.info("Consumer topic set as {}",RegistryConfiguration.getProperty(RegistryConfiguration.REGISTRY));
			
			RegistrationHandler messageAcceptor = new RegistrationHandler();
			messageAcceptor.setConsumer(commandConsumer);
			messageAcceptor.consumeMessages();
		} catch (Exception e) {
			LOGGER.error("Exception scanning to events !!!",e);
		}

	}


}
