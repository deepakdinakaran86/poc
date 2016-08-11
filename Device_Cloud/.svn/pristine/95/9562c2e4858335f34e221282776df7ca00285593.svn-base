
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.deviceframework.datadist.testing;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import kafka.producer.KeyedMessage;

import com.google.gson.Gson;
import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.ConsumerType;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;

/**
 * This class is responsible for testing RMI client
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class RmiClientTesting {
	public static void main(String[] args) {
		/*if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}*/
		//jmsPublish();
		//jmsTesting();
		kafkaHighlevelConsumerTesting();
		kafkaAdvConsumerTesting();
		//jmsDefaultTesting();
		//jmsPublishTesting();
		kafkaPublishTesting();
		jmsAsyncConsumerTesting();
	}
	
	private static void jmsTesting(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CoreConsumer consumer = brokerManager.getConsumer(DistributorMode.JMS, ConsumerType.DEFAULT);
			consumer.setQueue("NEW_QUEUE");
			JmsDefaultListenerTesting listenerTesting = new JmsDefaultListenerTesting();
			listenerTesting.setConsumer(consumer);
			consumer.listen();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private static void jmsAsyncConsumerTesting(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CoreConsumer consumer = brokerManager.getConsumer(DistributorMode.JMS, ConsumerType.ASYNC);
			consumer.setQueue("NEW_QUEUE");
			JmsAsyncListenerTesting listenerTesting = new JmsAsyncListenerTesting();
			listenerTesting.setConsumer(consumer);
			consumer.listen();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void jmsPublish(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CorePublisher corePublisher = brokerManager.getPublisher(DistributorMode.JMS);
		//	corePublisher.setQueue("NEW_QUEUE");
//			JmsPublishProducerTest  jmsPublishProducerTest = new JmsPublishProducerTest();
//			jmsPublishProducerTest.setCorePublisher(corePublisher);
			corePublisher.publish("NEW_QUEUE",getHistoryMessage());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void jmsDefaultTesting(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CoreConsumer consumer = brokerManager.getConsumer(DistributorMode.JMS, ConsumerType.DEFAULT);
			consumer.setQueue("NEW_QUEUE");
			JmsDefaultListenerTesting testing  = new JmsDefaultListenerTesting();
			testing.setConsumer(consumer);
			consumer.listen();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void kafkaHighlevelConsumerTesting(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CoreConsumer consumer = brokerManager.getConsumer(DistributorMode.KAFKA, ConsumerType.HIGH_LEVEL);
			consumer.setTopic("updateTopic");
			consumer.setThreadCount(1);
			Properties props = new Properties();
			props.put("group.id", "newgroup");
			consumer.setProperties(props);
			KafkaHLListenerTesting testing = new KafkaHLListenerTesting();
			testing.setConsumer(consumer);
			testing.init();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static Properties getKafkaProperties(){
		Properties props = new Properties();
		props.put("zookeeper.connect", "localhost:2181");
		props.put("group.id", "newgroup");
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		props.put("autooffset.reset", "smallest");
		return props;
	}
	private static Properties getKafkaPublishProperties(){
		Properties properties = new Properties();
		properties.put("metadata.broker.list", "localhost:9092");
		//properties.put("serializer.class", "kafka.serializer.StringEncoder");
		properties.put("serializer.class", "com.pcs.datadist.testing.EventEncoder");
		properties.put("partitioner.class", "com.pcs.datadist.testing.MessagePartitioner");
		return properties;
	}
	
	
	private static void kafkaAdvConsumerTesting(){
		String name = "distributor";
		Registry registry;
        try {
	        registry = LocateRegistry.getRegistry("localhost",1099);
	        DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CoreConsumer consumer = brokerManager.getConsumer(DistributorMode.KAFKA, ConsumerType.ADVANCED);
			consumer.setTopic("updateTopic");
			consumer.setPartitioner(new MessagePartitioner());
			consumer.setPartitionKey("teltonika");
			consumer.setMaxRead(1000);
			consumer.setOffset(2l);
			KafkaAdvListenerTesting advListenerTesting = new KafkaAdvListenerTesting();
			advListenerTesting.setConsumer(consumer);
			consumer.listen();
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
	private static void jmsPublishTesting(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			DistributionManager distributionManager = (DistributionManager) registry.lookup(name);
			CorePublisher corePublisher = distributionManager.getPublisher(DistributorMode.JMS);
			corePublisher.setUrl("tcp://localhost:61616");
			//corePublisher.setQueue("NEW_QUEUE");
			JmsPublishProducerTest  jmsPublishProducerTest = new JmsPublishProducerTest();
			jmsPublishProducerTest.setCorePublisher(corePublisher);
			//jmsPublishProducerTest.publish(getMessage());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void kafkaPublishTesting(){
		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			DistributionManager distributionManager = (DistributionManager) registry.lookup(name);
			CorePublisher corePublisher = distributionManager.getPublisher(DistributorMode.KAFKA);
			corePublisher.setProperties(getKafkaPublishProperties());
			List<Object> objects = new ArrayList<Object>();
			List<Object> keyedMessages = new ArrayList<Object>();
			for(int i=0;i<=2;i++){
				Object historyMessageObj = getHistoryMessageObj();
				objects.add(historyMessageObj);
			}
			for(int i=0;i<=2;i++){
				KeyedMessage<Object, Object> keyedMessage = new KeyedMessage<Object, Object>("updateTopic",objects.get(i));
				keyedMessages.add(keyedMessage);
			}
			
			
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private static PublishMessage getMessage(){
		PublishMessage msg = new PublishMessage();
		msg.setAssetName("Generator");
		msg.setTemperature("45");
		return msg;
	}
	
	public static String getHistoryMessage(){
		Gson gson = new Gson();
		EventMessage eventMessage = new EventMessage();
		eventMessage.setEventId("154");
		eventMessage.setEventSource("Time");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -3);
		eventMessage.setEventValue(calendar.getTime().toString());

		String jsonString = gson.toJson(eventMessage);
		return jsonString;
	}
	
	public static Object getHistoryMessageObj(){
		EventMessage eventMessage = new EventMessage();
		eventMessage.setEventId("154");
		eventMessage.setEventSource("Time");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -3);
		eventMessage.setEventValue(calendar.getTime().toString());
		return eventMessage;
	}

}
