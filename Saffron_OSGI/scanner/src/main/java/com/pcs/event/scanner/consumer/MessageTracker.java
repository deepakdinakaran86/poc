package com.pcs.event.scanner.consumer;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.event.scanner.config.ScannerConfiguration;
import com.pcs.event.scanner.osgi.util.MessageUtil;
import com.pcs.saffron.notification.broker.consumer.CoreConsumer;
import com.pcs.saffron.notification.enums.ConsumerType;
import com.pcs.saffron.notification.enums.DistributorMode;

public class MessageTracker {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageTracker.class);

	
	public static void listen() {

		try {
			CoreConsumer commandConsumer = MessageUtil.getNotificationHandler().getConsumer(DistributorMode.KAFKA, ConsumerType.HIGH_LEVEL);
			commandConsumer.setTopic(ScannerConfiguration.MESSAGE_SOURCE);
			commandConsumer.setThreadCount(1);
			Properties props = new Properties();
			props.put("group.id", "MessageScanner");
			commandConsumer.setProperties(props);
			LOGGER.info("Consumer topic set as {}",ScannerConfiguration.getProperty(ScannerConfiguration.MESSAGE_SOURCE));
			
			MessageAcceptor messageAcceptor = new MessageAcceptor();
			messageAcceptor.setConsumer(commandConsumer);
			messageAcceptor.consumeMessages();
		} catch (Exception e) {
			LOGGER.error("Exception scanning to events !!!",e);
		}

	}


}
