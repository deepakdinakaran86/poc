package com.pcs.device.gateway.teltonika.event.handler;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.teltonika.config.TeltonikaGatewayConfiguration;
import com.pcs.device.gateway.teltonika.event.DeviceMessageEvent;
import com.pcs.device.gateway.teltonika.event.listener.adapter.DeviceMessageAdapter;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;
import com.pcs.deviceframework.decoder.data.message.Message;

public class MessageHandler extends DeviceMessageAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);
	
	@Override
	public void messageReady(DeviceMessageEvent deviceMessageEvent) {

		try {
			Registry registry = LocateRegistry.getRegistry(TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.DATADISTRIBUTOR_IP),
															TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.DATADISTRIBUTOR_PORT) != null?
																	Integer.parseInt(TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.DATADISTRIBUTOR_PORT)):1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.DATADISTRIBUTOR_REGISTRY_NAME));
			CorePublisher publisher = brokerManager.getPublisher(DistributorMode.KAFKA);
			List<Serializable> messages = new ArrayList<Serializable>();
			for (Message message : deviceMessageEvent.getMessages()) {
				KeyedMessage<Object, Object> keyedMessage = new KeyedMessage<Object, Object>(TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.DECODED_MESSAGE_STREAM),message);
				messages.add(keyedMessage);
			}
			
			publisher.publish(messages);
		} catch (Exception e) {
			LOGGER.error("Remote invocation exception",e);
		}
	
	}

	
}
