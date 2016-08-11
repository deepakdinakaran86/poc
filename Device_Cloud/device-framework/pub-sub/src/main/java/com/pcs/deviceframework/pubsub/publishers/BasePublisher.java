/**
 * 
 */
package com.pcs.deviceframework.pubsub.publishers;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author PCSEG171
 *
 */
public abstract class BasePublisher {
	
	private String id = null;
	private String mode = null;
	private ConnectionFactory connectionFactory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageProducer producer = null;
	
	
	public abstract void publish(Object message) throws JMSException ;
	public abstract void setPublisherURL(String url);
	public abstract void setPublisherQueue(String queue);
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	public abstract String getQueue();
	
	public abstract String getURL();
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else if (obj instanceof BasePublisher) {
			BasePublisher publisher = (BasePublisher) obj;
			if((publisher.getQueue()!= null) && (getQueue()!= null) && publisher.getQueue().equalsIgnoreCase(getQueue())
					&& (publisher.getURL()!= null) && (getURL()!= null) && publisher.getURL().equalsIgnoreCase(getURL()) && 
					(publisher.getId()!= null) && (getId()!= null) && publisher.getId().equalsIgnoreCase(getId())){
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getQueue().length()*getURL().length();
	}
	
	
	/**
	 * @return the session
	 * @throws JMSException 
	 */
	public Session getSession() throws JMSException {
		if(session == null)
			init(getURL(), getQueue());
		return session;
	}
	private final void init(String publisherURL,String messageQueue) throws JMSException {
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

}
