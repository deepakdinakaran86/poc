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
import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListenerAdapter;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.ConsumerType;
import com.pcs.deviceframework.datadist.enums.DestinationType;
import com.pcs.deviceframework.datadist.enums.DistributorMode;

public class CommandProcessorDiagnosisEx extends CoreListenerAdapter{

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandProcessorDiagnosisEx.class);
	
	private static final String COMMAND_REGISTER="G2021-commands";
	private static CommandProcessorDiagnosisEx _instance = null;
	private static DistributionManager brokerManager;
	private static final String name = "distributor";
	private static List<G2021Command> commands  = new ArrayList<G2021Command>();
	
	private CommandProcessorDiagnosisEx(){
		
	}
	
	public static CommandProcessorDiagnosisEx getInstance(){
		if(_instance == null){
			try {
				_instance = new CommandProcessorDiagnosisEx();
				Registry registry = LocateRegistry.getRegistry("localhost",1099);
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
			CoreConsumer consumer = brokerManager.getConsumer(DistributorMode.JMS, ConsumerType.ASYNC);
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
			
			if(commands == null){
				commands = new ArrayList<G2021Command>();
			}
			commands.add(command);						
		} catch (Exception e) {
			LOGGER.error("Exception receiving messages from the queue",e);
		}
	
	}
	
	public static List<G2021Command> getCommands(){
		return commands;
	}
	
}
