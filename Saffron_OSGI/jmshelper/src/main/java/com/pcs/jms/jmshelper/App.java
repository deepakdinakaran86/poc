package com.pcs.jms.jmshelper;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.jms.jmshelper.consumers.BaseConsumer;
import com.pcs.jms.jmshelper.consumers.delegates.DefaultConsumer;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	static boolean keepConsuming = true;
	
    public static void main( String[] args ) throws JMSException
    {
       DefaultConsumer consumer = new DefaultConsumer();
       consumer.setUrl("tcp://10.236.166.120:61616");
       consumer.setQueue("G2021-commands");
       consumer.receiveMessage();
       consumeMessage(consumer);
    }
    
    
    private static void consumeMessage(BaseConsumer batchConsumer) throws JMSException {
		LOGGER.info("Listening status {}",keepConsuming);
		while(keepConsuming){
			LOGGER.info("Checking new Messages");
			try {
				List<javax.jms.Message> consumeMessages = batchConsumer.receiveMessage();
				LOGGER.info("Received {} new Messages !!",consumeMessages.size());
				for (javax.jms.Message message : consumeMessages) {
					if (message instanceof TextMessage) {
						try {
							TextMessage textMessage = (TextMessage) message;
							LOGGER.info("Command Received :-> {}",textMessage.getText());
							LOGGER.info("Command sent !!");
						}catch (Exception e) {
							LOGGER.error("Error in message conversion to Message",e);
						}
					}
				}
				Thread.sleep(500);
			}catch (Exception e) {
				LOGGER.error("Check ActiveMQ status",e);
			}finally{
				
			}
		}
		LOGGER.info("Consumption terminated");
	}
}
