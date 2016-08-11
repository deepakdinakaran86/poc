package com.pcs.event.scanner.consumer;
import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

/**
 * This class is responsible for consume data from JMS queue
 * 
 * @author pcseg129
 */
public class CEPEventConsumer {

	private String jmsHost;
	private String queueName;
	private int queueSize = 10;

	private static Logger LOGGER = LoggerFactory
			.getLogger(CEPEventConsumer.class);


	public String getJmsHost() {
		return jmsHost;
	}

	public void setJmsHost(String jmsHost) {
		this.jmsHost = jmsHost;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public List<Message> receiveMessage() {
		List<Message> messages = new ArrayList<Message>();
		ConnectionFactory connectionFactory = null;
		javax.jms.Connection connection = null;
		try {
			connectionFactory = new ActiveMQConnectionFactory(jmsHost);
			connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			MessageConsumer consumer = session.createConsumer(destination);
			while(messages.size() <= queueSize){
				javax.jms.Message message = null;
				try {
					message = consumer.receive(100);//Receives the next message that arrives within the specified timeout interval. 
				} catch (Exception e) {
					LOGGER.error("No more messages in the Queue");
				}
				if(message == null){
					break;
				}
				messages.add(message);
			}
		}catch (Exception e) {
			LOGGER.error("Check ActiveMQ status",e);
		}finally{
			try {
				if(connection != null)
					connection.close();
				connectionFactory = null;
			} catch (Exception e2) {
				LOGGER.error("Cannot Close connection",e2);
			}

		}
		return messages;
	}

}
