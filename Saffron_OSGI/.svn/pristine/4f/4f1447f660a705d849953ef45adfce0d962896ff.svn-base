
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
package com.pcs.saffron.notification.broker.jms.consumer.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.notification.broker.consumer.async.CoreListener;
import com.pcs.saffron.notification.broker.jms.consumer.JmsBaseConsumer;
import com.pcs.saffron.notification.enums.DestinationType;

/**
 * This class is responsible for consuming data using JMS implementation
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 19 2015
 */
public class JmsDefaultConsumer extends JmsBaseConsumer{

	private static final long serialVersionUID = 479128634895442555L;

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsDefaultConsumer.class);

	private String url = null;
	private String queue = null;
	private int queueSize = 100;
	private long delay = 1000;
	private DestinationType destinationType;
	private CoreListener listener;

	@Override
	public void setUrl(String url){
		this.url = url;
	}

	@Override
	public String getURL(){
		return url;
	}
	
	@Override
	public void setQueue(String queue) {
		this.queue = queue;
	}

	@Override
	public String getQueue() {
		return queue;
	}

	@Override
	public void setMessageListener(CoreListener listener){
		this.listener = listener;
	}

	public DestinationType getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(DestinationType destinationType) {
		this.destinationType = destinationType;
	}

	@Override
	public CoreListener getMessageListener() {
		return listener;
	}

	@Override
	public void listen(){
		consumeData(destinationType);
	}

	public void consumeData(DestinationType destinationType) {/*
		ConnectionFactory connectionFactory = null;
		javax.jms.Connection connection = null;
		do{
			List<Message> messages = new ArrayList<Message>();
			try {
				connectionFactory = new ActiveMQConnectionFactory(getURL());
				connection = connectionFactory.createConnection();
				connection.start();
				Destination destination = null;
				Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
				if(destinationType.equals(DestinationType.QUEUE)){
					destination = session.createQueue(getQueue());
				}else if(destinationType.equals(DestinationType.TOPIC)){
					destination = session.createTopic(getQueue());
				}else{
					throw new JMSException("Unknown destinationType ,cannot initialize consumer");
				}
				MessageConsumer consumer = session.createConsumer(destination);

				while(messages.size() <= queueSize){
					javax.jms.Message message = null;
					try {
						message = consumer.receive(100);
					} catch (Exception e) {
						LOGGER.error("No more messages in the Queue");
					}
					if(message == null){
						break;
					}
					messages.add(message);
				}
				listener.consumeData1(messages);
				connection.close();
				connection = null;
				session = null;
				connectionFactory = null;
				Thread.sleep(2000l);
			}catch (Exception e) {
				LOGGER.error("Check ActiveMQ status",e);
			}finally{
			}
		}while(true);
	*/}

	@Override
	public void setQueueSize(Integer size) {
		queueSize = size;
	}

	@Override
	public Integer getQueueSize() {
		return queueSize;
	}

	@Override
	public void setDelay(Long delay) {
		this.delay = delay;
	}

	@Override
	public Long getDelay() {
		return delay;
	}

}
