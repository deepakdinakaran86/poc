/**
 * 
 */
package com.pcs.jms.jmshelper.consumers.delegates;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;



/**
 * @author PCSEG171
 *
 */
public class AsynchronousConsumer extends ConsumerAdapter{

	private static final Logger LOGGER = Logger.getLogger(AsynchronousConsumer.class);
	private String queue = null;
	private String url = null;
	private String mode = "ASYNC";
	private int queueSize = 100;
	private long delay = 1000;
	private MessageListener messageListener = null;
	MessageConsumer consumer = null;
	private boolean stopListening = false;
	ConnectionFactory connectionFactory = null;
	javax.jms.Connection connection = null;
	@Override
	public void setMessageListener(MessageListener listener) {
		this.messageListener = listener;
	}
	@Override
	public void setQueue(String queue) {
		this.queue = queue;
	}
	@Override
	public void setMode(String mode) {
		this.mode = mode;
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
	public String getMode() {
		return mode;
	}
	@Override
	public Integer getQueueSize() {
		return queueSize;
	}
	@Override
	public Long getDelay() {
		return delay;
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
			connectionFactory = new ActiveMQConnectionFactory(getURL());
			try {
				connection = connectionFactory.createConnection();
				connection.start();

				Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
				Destination destination = session.createQueue(getQueue());
				consumer = session.createConsumer(destination);
				consumer.setMessageListener(messageListener);
				try {
					Thread.sleep(delay);
				} catch (Exception e) {
					e.printStackTrace();
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
	@Override
	public void stopListen() {
		try {
			if(consumer != null)
				consumer.close();
			
		} catch (Exception e) {
			LOGGER.error("Error stoping the consumer",e);
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
