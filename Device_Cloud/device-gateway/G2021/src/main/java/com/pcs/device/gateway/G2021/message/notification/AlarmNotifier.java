/**
 * 
 */
package com.pcs.device.gateway.G2021.message.notification;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;


/**
 * @author pcseg171
 *
 */
public class AlarmNotifier {
	
	private static String ALARM_MESSAGE_QUEUE = "ALARM_MESSAGE_QUEUE";
	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmNotifier.class);
	
	public static void publish(Serializable message){

		try {
			LOGGER.info("Alarm Message To Ingest :: "+message.toString());
			Registry registry = LocateRegistry.getRegistry(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATADISTRIBUTOR_IP),
															G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATADISTRIBUTOR_PORT) != null?
																	Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATADISTRIBUTOR_PORT)):1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATADISTRIBUTOR_REGISTRY_NAME));
			CorePublisher publisher = brokerManager.getPublisher(DistributorMode.KAFKA);
			List<Serializable> messages = new ArrayList<Serializable>();
			KeyedMessage<Object, Object> keyedMessage = new KeyedMessage<Object, Object>(ALARM_MESSAGE_QUEUE,message);
			messages.add(keyedMessage);
			
			publisher.publish(messages);
		} catch (Exception e) {
			LOGGER.error("Remote invocation exception",e);
		}
	
	}
	

}
