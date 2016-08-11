
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

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;





import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class PublishData {
	static String  publisherURL = null;
	static String messageQueue = null;
	private Session session = null;
	private String mode = null;
	private ConnectionFactory connectionFactory = null;
	private Connection connection = null;
	private Destination destination = null;
	private MessageProducer producer = null;

	public void publish(Object message) throws JMSException {
		try {
			ObjectMessage pubMessage = getSession().createObjectMessage((PublishMessage)message);
			getProducer(publisherURL, messageQueue).send(pubMessage);
		} catch (Exception e) {
			throw new JMSException("Error Publishing Live Messages");
		}
	}
	
	public Session getSession() throws JMSException {
		if(session == null)
			init("", "");
		return session;
	}
	private void init(String publisherURL,String messageQueue) throws JMSException {
		try {
			connectionFactory = new ActiveMQConnectionFactory(publisherURL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(messageQueue);
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
	public final MessageProducer getProducer(String publisherURL,String messageQueue) throws JMSException{
		if(producer == null)
			init(publisherURL, messageQueue);
		return producer;
	}
	
	public static void main(String[] args) {
	    PublishData data = new PublishData();
	    publisherURL = "tcp://10.234.60.12:61616";
	    messageQueue = "NEW_QUEUE";
	    try {
	        data.init(publisherURL,messageQueue);
	        PublishMessage message = new PublishMessage();
	        message.setAssetName("Generator");
	        message.setTemperature("45");
	        data.publish(message);
        } catch (JMSException e) {
	        e.printStackTrace();
        }
	    
    }

}
