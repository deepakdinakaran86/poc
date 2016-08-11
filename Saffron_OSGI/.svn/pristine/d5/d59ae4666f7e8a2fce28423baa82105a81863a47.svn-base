package com.pcs.device.gateway.G2021.command.utils;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.G2021.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.G2021.command.G2021Command;
import com.pcs.device.gateway.G2021.command.transmitter.G2021CommandExecutor;
import com.pcs.device.gateway.G2021.utils.SupportedDevices;
import com.pcs.device.gateway.G2021.utils.SupportedDevices.Devices;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
@SuppressWarnings("rawtypes")
public class CommandMessageUtil implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandMessageUtil.class);
	
	
	private KafkaStream kafkaStream;
	private int threadNo;
	
	public CommandMessageUtil(KafkaStream stream,int threadNo){
		kafkaStream = stream;
		this.threadNo = threadNo;
	} 
	
	@Override
	public void run() {
		@SuppressWarnings("unchecked")
		ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();

		while(iterator.hasNext()){
			G2021Command command = null;
			try {
				ObjectMapper mapper = new ObjectMapper();
				command = mapper.readValue(new String(iterator.next().message()), G2021Command.class);
				
				DefaultConfiguration configuration = (DefaultConfiguration) DeviceManagerUtil.getG2021DeviceManager().getConfiguration(command.getSourceId(), SupportedDevices.getGateway(Devices.EDCP));
				G2021CommandExecutor executor = new G2021CommandExecutor();
				command.setSessionId(configuration.getSessionId());
				command.setUnitId(configuration.getUnitId());
				executor.executeCommand(command, configuration);	
				LOGGER.info("Command fired for processing...on thread {}",threadNo);
				Thread.sleep(900);
				LOGGER.info("Awaking after short delay of {} milli seconds",900);
				mapper = null;
			} catch (Exception e) {
				LOGGER.error("Error in Deserializing",e);
			}
			
		}
	}

}
