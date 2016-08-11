package com.pcs.device.gateway.G2021.command;

import com.pcs.device.gateway.G2021.beans.UnregisteredConfiguration;
import com.pcs.device.gateway.G2021.command.transmitter.G2021CommandExecutor;
import com.pcs.device.gateway.G2021.message.type.MessageType;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

public class SyncCommandDispatcher {

	public static void releaseSyncCommand(DefaultConfiguration configuration) {
		G2021Command command = new G2021Command();
		command.setCommand(MessageType.SYSTEM_COMMAND.name());
		command.setCommandCode(OperationCommandType.SYNC.name());
		command.setUnitId(configuration.getUnitId());
		command.setSessionId(configuration.getSessionId());
		Double random = Math.random();
		command.setRequestId(random.shortValue());
		Short leaseTime = 60;
		command.setLeaseTime(leaseTime);
		G2021CommandExecutor commandExecutor = new G2021CommandExecutor();
		commandExecutor.executeCommand(command, configuration);
	}
	
	public static void releaseSyncCommand(UnregisteredConfiguration configuration) {
		G2021Command command = new G2021Command();
		command.setCommand(MessageType.SYSTEM_COMMAND.name());
		command.setCommandCode(OperationCommandType.SYNC.name());
		command.setUnitId(configuration.getUnitId());
		command.setSessionId(configuration.getSessionId());
		Double random = Math.random();
		command.setRequestId(random.shortValue());
		Short leaseTime = 60;
		command.setLeaseTime(leaseTime);
		G2021CommandExecutor commandExecutor = new G2021CommandExecutor();
		commandExecutor.executeCommand(command, configuration);
	}
}
