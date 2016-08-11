package com.pcs.event.scanner.consumer;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.event.scanner.client.EventHubPublisher;
import com.pcs.saffron.cipher.data.message.Message;
@SuppressWarnings("rawtypes")
public class MessageAcceptorDaemon implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageAcceptorDaemon.class);
	
	
	private KafkaStream kafkaStream;
	private int threadNo;
	
	public MessageAcceptorDaemon(KafkaStream stream,int threadNo){
		kafkaStream = stream;
		this.threadNo = threadNo;
	} 
	
	@Override
	public void run() {
		@SuppressWarnings("unchecked")
		ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();

		while(iterator.hasNext()){
			try {
				ObjectMapper mapper = new ObjectMapper();
				String messageContent = new String(iterator.next().message()).replaceAll("Speed", "speed");
				Message message = mapper.readValue(messageContent, Message.class);
				EventHubPublisher.publishMessage(message);
				mapper = null;
			} catch (Exception e) {
				LOGGER.error("Error in Deserializing",e);
			}
			
		}
	}

}
