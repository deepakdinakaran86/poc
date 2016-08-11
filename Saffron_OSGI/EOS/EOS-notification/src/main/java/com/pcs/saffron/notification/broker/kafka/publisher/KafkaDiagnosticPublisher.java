
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
package com.pcs.saffron.notification.broker.kafka.publisher;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.notification.broker.publisher.CorePublisherAdapter;
import com.pcs.saffron.notification.broker.util.DiagnosisDistributionUtil;

/**
 * This class is responsible for publishing data to Apache Kafka
 * Publish the data to topic(s), partitioned by key, using either the
 * synchronous or the asynchronous producer
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 23 2015
 */
public class KafkaDiagnosticPublisher extends CorePublisherAdapter{

	private static final long serialVersionUID = -8318316434070289980L;

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDiagnosticPublisher.class);

	private Properties properties;
	private Producer<Object, Object> producer;

	/*
	 * The supplied properties should include
	 * metadata.broker.list,serializer.class,partitioner.class(optional)
	 */
	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param message - message object that encapsulate the topic, key and message data
	 */
	@Override
	public void publish(Serializable message) {
		publishData(message);
		producer.close();
		producer = null;
	}
	
	/**
	 * @param messageList - list of message objects that encapsulate the topic, key and message data
	 */
	@Override
	public void publish(List<Serializable> messageList) {
		publishData(messageList);
		producer.close();
		producer = null;
	}

	/*
	 * Creating a producer configuration
	 * first this method will get the default properties for a kafka publisher
	 * then it will append the properties passed by the object 
	 */
	private ProducerConfig createProducerConfig(){
		Properties defaultProps = DiagnosisDistributionUtil.getDiagKafkaPublishConfig();
		LOGGER.info("Properties found {},{}",defaultProps,defaultProps.getProperty("metadata.broker.list"));
		if(this.properties != null){
			defaultProps.putAll(this.properties);
		}else{
		}
		return new ProducerConfig(defaultProps);
	}

	private void init(){
		if(producer == null){
			ProducerConfig producerConfig = createProducerConfig();
			producer = new Producer<Object, Object>(producerConfig);
		}
	}

	/**
	 * Sends the data to a single topic
	 * 
	 * @param message - message to send
	 */
	private void publishData(Object message){
		init();
		KeyedMessage<Object, Object> keyedMessage = null;
		try{
			keyedMessage = getKeyedMessage(message);
			producer.send(keyedMessage);
		}catch(Exception ex){
			LOGGER.error("Error occurred while publishing data ",ex);
			ex.printStackTrace();
		}finally{
			keyedMessage = null;
			message = null;
		}
	}

	/**
	 * send data to multiple topics
	 * 
	 * @param objects - list of message objects to send
	 */
	private void publishData(List<Serializable> objects){
		init();
		List<KeyedMessage<Object, Object>> keyedMessages = null;
		try{
			keyedMessages = getKeyedMessageList(objects);
			producer.send(keyedMessages);
		}catch(Exception ex){
			LOGGER.error("Error occurred while publishing data ",ex);
		}finally{
			keyedMessages = null;
			objects = null;
		}
	}

	/**
	 * In order to send to kafka, one need to construct a KeyedMessage
	 * 
	 * @param message - message object
	 */
	@SuppressWarnings("unchecked")
	private KeyedMessage<Object, Object> getKeyedMessage(Object message){
		//KeyedMessage<Object, Object> keyedMessage = new KeyedMessage<Object, Object>(topicName,message);
		KeyedMessage<Object, Object> keyedMessage = (KeyedMessage<Object, Object>) message;
		return keyedMessage;
	}

	/**
	 * In order to send to kafka, one need to construct a KeyedMessage
	 * 
	 * @param messageList - list of message objects
	 */
	private List<KeyedMessage<Object, Object>> getKeyedMessageList(List<Serializable> messageList){
		List<KeyedMessage<Object, Object>> keyedMessages = new ArrayList<KeyedMessage<Object, Object>>();
		for(Object message : messageList){
			keyedMessages.add(getKeyedMessage(message));
		}
		return keyedMessages;
	}
	
	public static void main(String[] args) {
		try {
			new KafkaDiagnosticPublisher().publish("data-store","This is another test data");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
