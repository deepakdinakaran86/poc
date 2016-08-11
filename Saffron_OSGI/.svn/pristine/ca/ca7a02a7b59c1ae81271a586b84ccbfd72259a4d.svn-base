package com.pcs.gateway.teltonika.event.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.gateway.teltonika.bundle.utils.DeviceManagerUtil;
import com.pcs.gateway.teltonika.bundle.utils.MessageUtil;
import com.pcs.gateway.teltonika.config.TeltonikaGatewayConfiguration;
import com.pcs.gateway.teltonika.event.DeviceMessageEvent;
import com.pcs.gateway.teltonika.event.listener.adapter.DeviceMessageAdapter;
import com.pcs.gateway.teltonika.message.analyzer.AnalyzedData;
import com.pcs.gateway.teltonika.message.analyzer.PointIdentifier;
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
			LOGGER.info("Message Handler recieves something {}",deviceMessageEvent.getMessages().size());
			for (Message message : deviceMessageEvent.getMessages()) {
				PointIdentifier identifier = new PointIdentifier();
				AnalyzedData analyzedData = identifier.identifyPoints(deviceConfiguration, message);
				LOGGER.info("Data at handler {}",analyzedData.getMessage().getPoints().size());
				DeviceManagerUtil.getTeltonikaDeviceManager().notifyMessage(analyzedData.getMessage(), deviceConfiguration, MessageCompletion.COMPLETE, gateway);
			}
			
			if(MessageUtil.getDiagNotificationHandler() != null && 
					TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.DIAG_ENABLE).equalsIgnoreCase("true")){
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


}
