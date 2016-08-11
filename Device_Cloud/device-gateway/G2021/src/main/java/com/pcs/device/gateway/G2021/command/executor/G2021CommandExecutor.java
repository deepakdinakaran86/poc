package com.pcs.device.gateway.G2021.command.executor;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.command.G2021Command;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.deviceframework.connection.Connector;
import com.pcs.deviceframework.connection.client.tcp.TCPClient;
import com.pcs.deviceframework.connection.client.udp.UDPClient;
import com.pcs.deviceframework.connection.utils.ConversionUtils;

public class G2021CommandExecutor {

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021CommandExecutor.class);

	public G2021CommandExecutor() {
	}


	public void executeCommand(G2021Command command, G2021DeviceConfiguration configuration){

		try {
			LOGGER.info("========================***********============================================");
			command.setSessionId(configuration.getSessionId());
			command.setUnitId(configuration.getUnitId());
			byte[] commandData = command.getServerMessage();

			LOGGER.info("Writing {} commands to the device :: {} at {} ",command.getCommand().toString(),ConversionUtils.getHex(commandData),Calendar.getInstance().getTime() );
			
			Integer devicePort = configuration.getClientDataserverPort() != null?configuration.getClientDataserverPort():
				configuration.getClientControlserverPort();
			switch (configuration.getCommunicationMode()) {
			case Connector.CONNECTOR_MODE_TCP:
				TCPClient tcpClient = new TCPClient(configuration.getClientIp(), devicePort, commandData);
				tcpClient.connect();
				tcpClient = null;
				break;
			case Connector.CONNECTOR_MODE_UDP:
				UDPClient udpClient = new UDPClient(configuration.getClientIp(), Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_COMMAND_PORT)), commandData);
				udpClient.connect();
				udpClient = null;
			break;
			default:
				break;
			}
			//TODO command audit log entry to be definedexit here.
			LOGGER.info("========================***********============================================");
		} catch (Exception e) {
			LOGGER.error("Exception processing commands for the device",e);
		}
	
	}
}
