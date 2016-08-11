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
public class RealtimeMessageNotifier  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeMessageNotifier.class);

	public static void publish(Serializable message){
		try {
			LOGGER.info("Realtime Message To Persit(DC) :: "+message.toString());
			Registry registry = LocateRegistry.getRegistry(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATADISTRIBUTOR_IP),
															G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATADISTRIBUTOR_PORT) != null?
																	Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATADISTRIBUTOR_PORT)):1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.DATADISTRIBUTOR_REGISTRY_NAME));
			CorePublisher publisher = brokerManager.getPublisher(DistributorMode.KAFKA);
			List<Serializable> messages = new ArrayList<Serializable>();
			KeyedMessage<Object, Object> keyedMessage = new KeyedMessage<Object, Object>(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.ANALYZED_MESSAGE_STREAM),message);
			messages.add(keyedMessage);
			publisher.publish(messages);
			
			LOGGER.info("Realtime Message To Ingest (EMC) :: ");
			CorePublisher jmsPublisher = brokerManager.getPublisher(DistributorMode.JMS);
			jmsPublisher.publishToQueue(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.REALTIME_DATA_QUEUE), message);
		} catch (Exception e) {
			LOGGER.error("Remote invocation exception",e);
		}
	}
}
