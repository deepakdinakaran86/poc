package com.pcs.device.gateway.G2021.command.transmitter;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.G2021.command.G2021Command;
import com.pcs.device.gateway.G2021.utils.SupportedDevices;
import com.pcs.device.gateway.G2021.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.writeback.bean.WriteBackCommand;
import com.pcs.saffron.manager.writeback.listener.CommandListener;

public class G2021CommandListener implements CommandListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021CommandListener.class);
	
	@Override
	public void handleCommand(WriteBackCommand writebackCommand) {
		DeviceManager g2021Manager = DeviceManagerUtil.getG2021DeviceManager();
		Gateway gateway = SupportedDevices.getGateway(Devices.EDCP);
		DefaultConfiguration configuration = (DefaultConfiguration )g2021Manager.getConfiguration(writebackCommand.getSourceId(), gateway);
		if(configuration == null){
			LOGGER.info("No matching device configuration found !!!");
			return;
		}
		try {
			G2021Command command = new G2021Command();
			command.setSourceId(writebackCommand.getSourceId());
			command.setCommand(writebackCommand.getPayload().getCommand());
			command.setPointId(writebackCommand.getPayload().getPointId());
			command.setDataType(writebackCommand.getPayload().getDataType());
			command.setData(writebackCommand.getPayload().getValue());
			//command.setLeaseTime(leaseTime);
			Map<String, String> customSpecs = writebackCommand.getPayload().getCustomSpecs();
			LOGGER.info("Custom Specs {}",customSpecs);
			if(customSpecs != null && !customSpecs.isEmpty()){
				if(customSpecs.get("priority") != null){
					try {
						command.setPriority(Short.parseShort(customSpecs.get("priority")));
					} catch (Exception e) {
						LOGGER.error("Invalid priority value");
					}
				}
				if(customSpecs.get("leaseTime") != null){
					try {
						command.setLeaseTime(Short.parseShort(customSpecs.get("leaseTime")));
					} catch (Exception e) {
						LOGGER.error("Invalid priority value");
					}
				}
			}
			command.setRequestId(writebackCommand.getWriteBackId());
			LOGGER.info("Sending commands to executer !!");
			new G2021CommandExecutor().executeCommand(command, configuration);
		} catch (Exception e) {
			LOGGER.error("Error handling commands",e);
		}
		
	}
}
