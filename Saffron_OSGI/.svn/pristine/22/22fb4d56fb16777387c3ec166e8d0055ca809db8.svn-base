package com.pcs.device.gateway.G2021.command.transmitter;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.bundle.utils.MessageUtil;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.event.listener.adapter.DeviceMessageAdapter;
import com.pcs.saffron.notification.broker.consumer.CoreConsumer;
import com.pcs.saffron.notification.enums.ConsumerType;
import com.pcs.saffron.notification.enums.DistributorMode;

public class CommandTracker extends DeviceMessageAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandTracker.class);

	
	public static void listen() {

		try {
			CoreConsumer commandConsumer = MessageUtil.getNotificationHandler().getConsumer(DistributorMode.KAFKA, ConsumerType.HIGH_LEVEL);
			LOGGER.info("Consumer from kafka called {}",commandConsumer);
			commandConsumer.setTopic(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.COMMAND_REGISTER));
			commandConsumer.setThreadCount(1);
			Properties props = new Properties();
			props.put("group.id", "commandTracker");
			commandConsumer.setProperties(props);
			LOGGER.info("Consumer topic set as {}",G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.COMMAND_REGISTER));
			CommandListener commandListener = new CommandListener();
			commandListener.setConsumer(commandConsumer);
			commandListener.listenToCommands();
		} catch (Exception e) {
			LOGGER.error("Exception scanning to events !!!",e);
		}

	}


}
