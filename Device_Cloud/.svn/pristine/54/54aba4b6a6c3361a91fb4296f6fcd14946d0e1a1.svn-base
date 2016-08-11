
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
package com.pcs.deviceframework.datadist.consumer.delegates;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.datadist.consumer.JmsBaseConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListener;
import com.pcs.deviceframework.datadist.enums.DestinationType;

/**
 * This class is responsible for asynchronous data consumption using JMS implementation
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 19 2015
 */
public class JmsAsynchronousConsumer extends JmsBaseConsumer{

    private static final long serialVersionUID = -5316073053635518465L;
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsAsynchronousConsumer.class);
	private String queue = null;
	private String url = null;
	private int queueSize = 100;
	private long delay = 1000;
	private DestinationType destinationType;
	private CoreListener messageListener = null;
	private boolean stopListening = false;

	@Override
	public void setQueue(String queue) {
		this.queue = queue;
	}
	
	@Override
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public void setQueueSize(Integer size) {
		this.queueSize = size;
	}
	
	@Override
	public void setDelay(Long delay) {
		this.delay = delay;
	}

	@Override
	public String getQueue() {
		return queue;
	}
	
	@Override
	public String getURL() {
		return url;
	}
	
	@Override
	public Integer getQueueSize() {
		return queueSize;
	}
	
	@Override
	public Long getDelay() {
		return delay;
	}
	
	@Override
	public DestinationType getDestinationType() {
		return destinationType;
	}
	
	@Override
	public void setDestinationType(DestinationType destinationType) {
		this.destinationType = destinationType;
	}
	
	@Override
	public void setMessageListener(CoreListener listener) {
		this.messageListener = listener;
	}
	
	public void stopListening(boolean status){
		if(!stopListening && status){
			stopListening = status;
		}else{
			LOGGER.error("Invalid stop request");
		}
	}

	@Override
	public void listen() {
		while(!stopListening){
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(getURL());
			javax.jms.Connection connection = null;
			try {
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
				consumer.setMessageListener(messageListener);
				try {
					Thread.sleep(delay);
				} catch (Exception e) {
					LOGGER.error("Async Consumer Wokeup..."+getQueue(),e);
				}
			}catch (Exception e) {
				LOGGER.error("Error Listening Queue "+getQueue(),e);
			}finally{
				if(connection!=null){
					try {connection.close();}catch (Exception e) {
						LOGGER.error("Error Closing Connection!!! "+getQueue(),e);
					}
					connection = null;
				}
			}
		}

	}
}
