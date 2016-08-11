package com.pcs.device.gateway.G2021.event.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.bundle.utils.MessageUtil;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.event.DeviceMessageEvent;
import com.pcs.device.gateway.G2021.event.listener.adapter.DeviceMessageAdapter;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.notification.broker.publisher.CorePublisher;
import com.pcs.saffron.notification.enums.DistributorMode;

public class AlarmHandler extends DeviceMessageAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmHandler.class);

	public void alarmReceived(DeviceMessageEvent deviceMessageEvent) {

		try {
			if(MessageUtil.getNotificationHandler() == null){
				LOGGER.error("No message handler found !!");
				return ;
			}
			CorePublisher publisher = MessageUtil.getNotificationHandler().getPublisher(DistributorMode.KAFKA);
			List<Serializable> messages = new ArrayList<Serializable>();
			for (Message message : deviceMessageEvent.getMessages()) {
				LOGGER.info("Publishing {} messages {} to kafka topic {}",messages.size(),message.toString(),G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.ALARM_MESSAGE_STREAM));
				KeyedMessage<Object, Object> keyedMessage = new KeyedMessage<Object, Object>(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.ALARM_MESSAGE_STREAM),message.toString());
				messages.add(keyedMessage);
			}
			publisher.publish(messages);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Remote invocation exception",e);
		}

	}


}
