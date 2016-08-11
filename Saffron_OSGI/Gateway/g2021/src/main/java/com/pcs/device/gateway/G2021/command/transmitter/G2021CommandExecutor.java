package com.pcs.device.gateway.G2021.command.transmitter;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.beans.UnregisteredConfiguration;
import com.pcs.device.gateway.G2021.command.G2021Command;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.tcp.client.TCPClient;
import com.pcs.saffron.connectivity.udp.client.UDPClient;
import com.pcs.saffron.connectivity.utils.ConversionUtils;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

public class G2021CommandExecutor{

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021CommandExecutor.class);

	public G2021CommandExecutor() {
	}

	
	public void executeCommand(G2021Command command, DefaultConfiguration configuration){

		try {
			LOGGER.info("========================***********============================================");
			command.setSessionId(configuration.getSessionId());
			command.setUnitId(configuration.getUnitId());
			byte[] commandData = command.getServerMessage();

			LOGGER.info("Writing {} commands to the device :: {} at {} ",command.getCommand().toString(),ConversionUtils.getHex(commandData),Calendar.getInstance().getTime() );
			int writebackPort = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_COMMAND_PORT));
			switch (configuration.getCommunicationMode()) {
			case Connector.CONNECTOR_MODE_TCP:
				TCPClient tcpClient = new TCPClient(configuration.getDeviceIP(), writebackPort, commandData);
				tcpClient.connect();
				tcpClient = null;
				break;
			case Connector.CONNECTOR_MODE_UDP:
				UDPClient udpClient = new UDPClient(configuration.getDeviceIP(), writebackPort, commandData);
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
	
	
	public void executeCommand(G2021Command command, UnregisteredConfiguration configuration){

		try {
			LOGGER.info("========================***********============================================");
			command.setSessionId(configuration.getSessionId());
			command.setUnitId(configuration.getUnitId());
			byte[] commandData = command.getServerMessage();
			int writebackPort = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_COMMAND_PORT));

			LOGGER.info("Writing {} commands to the unregistered device :: {} at {} on {}:{} ",command.getCommand().toString(),ConversionUtils.getHex(commandData),Calendar.getInstance().getTime(),configuration.getDeviceIP(),writebackPort );
			
			switch (configuration.getDeviceMode()) {
			case Connector.CONNECTOR_MODE_TCP:
				TCPClient tcpClient = new TCPClient(configuration.getDeviceIP(), writebackPort, commandData);
				tcpClient.connect();
				tcpClient = null;
				break;
			case Connector.CONNECTOR_MODE_UDP:
				UDPClient udpClient = new UDPClient(configuration.getDeviceIP(), writebackPort, commandData);
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
