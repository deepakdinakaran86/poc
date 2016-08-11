package com.pcs.device.gateway.jace.event.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.jace.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.jace.bundle.utils.MessageUtil;
import com.pcs.device.gateway.jace.config.JaceConfiguration;
import com.pcs.device.gateway.jace.event.DeviceMessageEvent;
import com.pcs.device.gateway.jace.event.listener.adapter.DeviceMessageAdapter;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.message.status.MessageCompletion;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.notification.broker.publisher.CorePublisher;
import com.pcs.saffron.notification.enums.DistributorMode;

public class MessageHandler extends DeviceMessageAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);

	@Override
	public void messageReady(DeviceMessageEvent deviceMessageEvent,
			DefaultConfiguration deviceConfiguration, Gateway gateway) {

		try {
			for (Message message : deviceMessageEvent.getMessages()) {
				DeviceManagerUtil.getJaceDeviceManager().notifyMessage(message, deviceConfiguration, MessageCompletion.PARTIAL, gateway);
			}
			
			if(MessageUtil.getDiagNotificationHandler() != null && JaceConfiguration.getProperty(JaceConfiguration.DIAG_ENABLE).equalsIgnoreCase("true")){
				CorePublisher publisher = MessageUtil.getDiagNotificationHandler().getPublisher(DistributorMode.KAFKA);
				List<Serializable> messages = new ArrayList<Serializable>();
				KeyedMessage<Object, Object> diagMessage = null;
				for (Message message : deviceMessageEvent.getMessages()) {
					StringBuffer buffer = new StringBuffer();
					long currentTimeMillis = System.currentTimeMillis();
					buffer.append("{")
						.append("\"hexadata\":\"").append(message.getRawData()).append("\",")
						.append("\"sourceId\":\"").append(message.getSourceId()).append("\",")
						.append("\"deviceTime\":\"").append(message.getTime()).append("\",")
						.append("\"outTime\":\"").append(currentTimeMillis).append("\",")
						.append("\"calculatedLatency\":\"").append((currentTimeMillis-message.getTime())).append("\",")
						.append("\"data\":").append(message.toString());
					buffer.append("}");
					diagMessage = new KeyedMessage<Object, Object>(message.getSourceId().toString(),buffer.toString());
					LOGGER.info("Message Diag {}",buffer.toString());
					messages.add(diagMessage);
				}
				publisher.publish(messages);
				publisher = null;
			}
		} catch (Exception e) {
			LOGGER.error("Message exception",e);
		}

	}


	public static void main(String[] args) {
	}
}
