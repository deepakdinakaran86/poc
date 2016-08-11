package com.pcs.device.gateway.G2021.command.executor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.pcs.device.gateway.G2021.command.G2021Command;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.G2021DeviceManager;
import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListenerAdapter;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.ConsumerType;
import com.pcs.deviceframework.datadist.enums.DestinationType;
import com.pcs.deviceframework.datadist.enums.DistributorMode;

public class CommandProcessorEx extends CoreListenerAdapter{

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandProcessorEx.class);
	
	private static final String COMMAND_REGISTER="G2021-commands";
	private static CommandProcessorEx _instance = null;
	private static DistributionManager brokerManager;
	private static final String name = "distributor";
	
	private CommandProcessorEx(){
		
	}
	
	public static CommandProcessorEx getInstance(){
		if(_instance == null){
			try {
				_instance = new CommandProcessorEx();
				Registry registry = LocateRegistry.getRegistry("10.234.31.201",1099);
				brokerManager = (DistributionManager) registry.lookup(name);
			} catch (Exception e) {
				LOGGER.error("Exception encountered",e);
			}
		}
		return _instance;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Illegal Clone Request!!");
	}
	
	public void doProcess(){
		try {
			CoreConsumer consumer = brokerManager.getConsumer(DistributorMode.JMS, ConsumerType.DEFAULT);
			consumer.setQueue(COMMAND_REGISTER);
			consumer.setDestinationType(DestinationType.QUEUE);
			consumer.setMessageListener(this);
			consumer.listen();
		} catch (Exception e) {
			LOGGER.error("Exception connecting to the data distributor",e);
		}
	}

	@Override
	public void onMessage(Message message) {

		try {
			
			G2021Command command = null;
			if (message instanceof TextMessage) {
				TextMessage commandMessage = (TextMessage) message;
				Gson gson = new Gson();
				command = gson.fromJson(commandMessage.getText(), G2021Command.class);
			}
			
			Cache cache = G2021DeviceManager.instance().getCacheProvider().getCache(G2021GatewayConfiguration.DEVICE_COMMAND_CACHE);
			@SuppressWarnings("unchecked")
			List<G2021Command> commands = (List<G2021Command>)cache.get(command.getSourceId().toString(), List.class);
			if(commands == null){
				commands = new ArrayList<G2021Command>();
			}
			commands.add(command);						
			cache.put(command.getSourceId().toString(), commands);
		} catch (Exception e) {
			LOGGER.error("Exception receiving messages from the queue",e);
		}
	
	}
	
	
	
}
