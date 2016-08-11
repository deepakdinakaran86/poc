package com.pcs.event.scanner.listener.util;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.message.Message;
@SuppressWarnings("rawtypes")
public class EventScannerUtil implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventScannerUtil.class);
	
	
	private KafkaStream kafkaStream;
	private int threadNo;
	
	public EventScannerUtil(KafkaStream stream,int threadNo){
		kafkaStream = stream;
		this.threadNo = threadNo;
	} 
	
	@Override
	public void run() {
		@SuppressWarnings("unchecked")
		ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();

		while(iterator.hasNext()){
			Message message = null;
			try {
				LOGGER.info("Consumed Message {}",new String(iterator.next().message()));
				System.out.println("Consumed Message {}"+new String(iterator.next().message()));
				//EventHubPublisher.publishMessage(new String(iterator.next().message()));
				LOGGER.info("Published Message");
			} catch (Exception e) {
				LOGGER.error("Error in Deserializing",e);
			}
			
		}
	}

}
