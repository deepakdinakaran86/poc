package com.pcs.device.gateway.G2021.command;

import java.util.ArrayList;
import java.util.List;

import com.pcs.device.gateway.G2021.command.executor.G2021CommandExecutor;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.G2021DeviceManager;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.message.type.MessageType;
import com.pcs.deviceframework.cache.Cache;

public class SyncCommandDispatcher {

	public static void releaseSyncCommand(G2021DeviceConfiguration configuration) {
		G2021Command command = new G2021Command();
		command.setCommand(MessageType.SERVERCOMMAND.name());
		command.setCommandCode(OperationCommandType.SYNC.name());
		command.setUnitId(configuration.getUnitId());
		command.setSessionId(configuration.getSessionId());
		
		G2021CommandExecutor commandExecutor = new G2021CommandExecutor();
		commandExecutor.executeCommand(command, configuration);
	}
}
