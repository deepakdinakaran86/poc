package com.pcs.saffron.manager.writeback.receiver;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.manager.writeback.bean.WriteBackCommand;
import com.pcs.saffron.manager.writeback.notifier.CommandNotifier;


@SuppressWarnings("rawtypes")
public class CommandMessageUtil implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandMessageUtil.class);
	
	private KafkaStream kafkaStream;
	private int threadNo;
	
	public CommandMessageUtil(KafkaStream stream,int threadNo){
		kafkaStream = stream;
		this.threadNo = threadNo;
	} 
	
	public void run() {
		@SuppressWarnings("unchecked")
		ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();

		while(iterator.hasNext()){
			WriteBackCommand command = null;
			try {
				ObjectMapper mapper = new ObjectMapper();
				command = mapper.readValue(new String(iterator.next().message()), WriteBackCommand.class);
				CommandNotifier.getInstance().notifyCommand(command);
				LOGGER.info("Command notified for processing...on thread {}",threadNo);
				mapper = null;
			} catch (Exception e) {
				LOGGER.error("Error in Deserializing",e);
			}
			
		}
	}

}
