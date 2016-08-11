/**
 * 
 */
package com.pcs.device.gateway.jace.writeback;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.jace.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.jace.utils.SupportedDevices;
import com.pcs.device.gateway.jace.utils.SupportedDevices.Devices;
import com.pcs.device.gateway.jace.writeback.api.beans.JaceCommand;
import com.pcs.device.gateway.jace.writeback.api.beans.PointCommand;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.writeback.bean.WriteBackCommand;
import com.pcs.saffron.manager.writeback.listener.CommandListener;

/**
 * @author pcseg171
 *
 */
public class JaceWritebackListener implements CommandListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JaceWritebackListener.class);

	/* (non-Javadoc)
	 * @see com.pcs.saffron.manager.writeback.listener.CommandListener#handleCommand(com.pcs.saffron.manager.writeback.bean.WriteBackCommand)
	 */
	@Override
	public void handleCommand(WriteBackCommand command) {
		
		switch (command.getCommandType()) {
		case WRITE_COMMAND:
			DefaultConfiguration configuration = (DefaultConfiguration) DeviceManagerUtil.getJaceDeviceManager().getConfiguration(command.getSourceId(), SupportedDevices.getGateway(Devices.JACE_CONNECTOR));
			if(configuration != null){
				String pointId = command.getPayload().getPointId().toString();
				String pointName = command.getPayload().getPointName();
				Point point = configuration.getPoint(pointId);
				if(point == null){
					LOGGER.error("Invalid point {},{}",pointId,pointName);
				}else{
					Map<String, String> customSpecs = command.getPayload().getCustomSpecs();
					if(customSpecs.get("mode") != null){
						JaceCommand jaceCommand = new JaceCommand();
						jaceCommand.setSourceId(command.getSourceId());
						jaceCommand.setCommandType(Integer.parseInt(customSpecs.get("mode")));
						PointCommand pointCommand = new PointCommand();
						pointCommand.setPointId(pointId);
						pointCommand.setValue(command.getPayload().getValue());
						jaceCommand.addCommand(pointCommand);
					}else{
						LOGGER.error("Invalid command, no mode specified");
					}
					
				}
			}else{
				LOGGER.error("No configuration found !!!");
			}
			break;

		default:
			break;
		}

	}

}
