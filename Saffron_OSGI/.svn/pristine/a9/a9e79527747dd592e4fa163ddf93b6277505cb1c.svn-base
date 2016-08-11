
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
package com.pcs.saffron.notification.broker.jms.publisher;

import com.pcs.saffron.notification.broker.publisher.CorePublisherAdapter;

/**
 * This class is responsible for data publish using JMS implementation
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 19 2015
 */
public class JmsBasePublisher extends CorePublisherAdapter{/*

	private static final long serialVersionUID = 9051762889268753166L;
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsBasePublisher.class);

	private String id = null;
	private String url;
	private Session session = null;
	private ConnectionFactory connectionFactory = null;
	private Connection connection = null;
	private Destination destination = null;
	private MessageProducer producer = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getUrl() {
		return url;
	}

	*//**
	 * @return the session
	 *//*
	public Session getSession(String topicName,DestinationType destinationType) {
		if(session == null)
			try {
				init(getUrl(), topicName,destinationType);
			} catch (JMSException e) {
				LOGGER.error("Error occurred while getting the session ",e);
			}
		return session;
	}

	private final void init(String publisherURL,String messageQueue,DestinationType destinationType) throws JMSException {
		try {
			String jmsHost = DistributionUtil.getJmsHost();
			System.out.println("JMS Host Resolved : "+jmsHost);
			connectionFactory = new ActiveMQConnectionFactory(jmsHost);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			if(destinationType.equals(DestinationType.QUEUE)){
				destination = session.createQueue(messageQueue);
			}else if(destinationType.equals(DestinationType.TOPIC)){
				destination = session.createTopic(messageQueue);
			}else{
				throw new JMSException("Unknown destinationType ,cannot initialize publisher");
			}
			producer = session.createProducer(destination);
		} catch (Exception e) {
			producer = null;
			destination = null;
			session = null;
			connection = null;
			connectionFactory = null;
			throw new JMSException("Cannot Initialize Publisher");
		}
	}

	@Override
	public void publishToQueue(String queueName,Serializable serializable) {
		publishData(queueName,serializable,DestinationType.QUEUE);
	}

	@Override
	public void publishToTopic(String topicName,Serializable serializable) {
		publishData(topicName,serializable,DestinationType.TOPIC);
	}

	private void publishData(String destinationName,Serializable serializable,DestinationType destinationType){
		Message pubMessage = null;
		try{
			if(producer == null){
				init(getUrl(),destinationName,destinationType);
			}
			System.err.println("About to publish...");
			//kaazing unable to consume data , so we change the message to string
			if (serializable instanceof String) {
		        String content = (String)serializable;
		        pubMessage = getSession(destinationName,destinationType).createTextMessage(content);
		        producer.send(pubMessage);
	        }else{
	        	pubMessage = getSession(destinationName,destinationType).createObjectMessage(serializable);
		        producer.send(pubMessage);
	        }
			System.err.println("Published!!");
			closeSession();
		}catch(Exception ex){
			LOGGER.error("Error occurred while publishing data ",ex);
			ex.printStackTrace();
		}finally{
			pubMessage = null;
		}
	}

	private void closeSession(){
		try {
			producer.close();
			producer = null;
			session.close();
			session = null;
			connection.close();
			connection = null;
			connectionFactory = null;
		} catch (Exception e) {
			LOGGER.error("Error occurred while closing session ",e);
		}
	}
	
	public static void main(String[] args) {
	    new JmsBasePublisher().publishData("Temperature-Sensor_83", "testdata", DestinationType.QUEUE);
    }
*/}
