/**
 * 
 */
package com.pcs.deviceframework.pubsub.consumers.delegates;

import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * @author PCSEG171
 *
 */
public class DefaultConsumer extends ConsumerAdapter {

	private static final Logger LOGGER = Logger.getLogger(DefaultConsumer.class);
	private String queue = null;
	private String url = null;
	private String mode = "DEFAULT";
	private int queueSize = 100;
	private long delay = 1000;


	/* (non-Javadoc)
	 * @see com.pcs.alerts.core.consumers.BaseConsumer#setQueue(java.lang.String)
	 */
	@Override
	public void setQueue(String queue) {
		this.queue = queue;
	}

	/* (non-Javadoc)
	 * @see com.pcs.alerts.core.consumers.BaseConsumer#setMode(java.lang.String)
	 */
	@Override
	public void setMode(String mode) {
		this.mode = mode;
	}

	/* (non-Javadoc)
	 * @see com.pcs.alerts.core.consumers.BaseConsumer#setUrl(java.lang.String)
	 */
	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	/* (non-Javadoc)
	 * @see com.pcs.alerts.core.consumers.BaseConsumer#getQueue()
	 */
	@Override
	public String getQueue() {
		return queue;
	}

	/* (non-Javadoc)
	 * @see com.pcs.alerts.core.consumers.BaseConsumer#getURL()
	 */
	@Override
	public String getURL() {
		return url;
	}

	/* (non-Javadoc)
	 * @see com.pcs.alerts.core.consumers.BaseConsumer#getMode()
	 */
	@Override
	public String getMode() {
		return mode;
	}

	@Override
	public List<Message> receiveMessage() {
		
		List<Message> messages = new ArrayList<Message>();
		ConnectionFactory connectionFactory = null;
		javax.jms.Connection connection = null;
			try {
				connectionFactory = new ActiveMQConnectionFactory(getURL());
				connection = connectionFactory.createConnection();
				connection.start();

				Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
				Destination destination = session.createQueue(getQueue());
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
			}catch (Exception e) {
				LOGGER.error("Check ActiveMQ status",e);
			}finally{
				try {
					connection.close();
					connectionFactory = null;
					/*try {
						Thread.sleep(delay);
					} catch (InterruptedException e1) {
						LOGGER.error("Thread Interrupted...");
					}*/
				} catch (Exception e2) {
					LOGGER.error("Cannot Close connection",e2);
				}
				
			}
			return messages;
	}

	/* (non-Javadoc)
	 * @see com.pcs.analytics.core.consumers.BaseConsumer#setQueueSize(java.lang.Integer)
	 */
	@Override
	public void setQueueSize(Integer size) {
		queueSize = size;
	}

	/* (non-Javadoc)
	 * @see com.pcs.analytics.core.consumers.BaseConsumer#getQueueSize()
	 */
	@Override
	public Integer getQueueSize() {
		return queueSize;
	}

	/* (non-Javadoc)
	 * @see com.pcs.analytics.core.consumers.BaseConsumer#setDelay(java.lang.Long)
	 */
	@Override
	public void setDelay(Long delay) {
		this.delay = delay;
	}

	/* (non-Javadoc)
	 * @see com.pcs.analytics.core.consumers.BaseConsumer#getDelay()
	 */
	@Override
	public Long getDelay() {
		return delay;
	}



}
