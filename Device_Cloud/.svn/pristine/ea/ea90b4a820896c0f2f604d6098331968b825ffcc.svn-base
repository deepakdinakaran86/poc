package com.pcs.saffron.g2021.SimulatorEngine.CS.tcpClient;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.config.ConnectorConfiguration;
import com.pcs.saffron.g2021.SimulatorEngine.CS.config.Handler;
import com.pcs.saffron.g2021.SimulatorEngine.CS.exceptions.ConfigurationException;
import com.pcs.saffron.g2021.SimulatorEngine.CS.tcpClientHandler.TCPClientHandler;

/**
 * 
 * @author Santhosh
 *
 */

public class TCPClient {

	public static Boolean authorized = false;
	private static TCPClientConnector clientEx;
	private static boolean connectionAvailable = false;

	public static boolean openClientConnection(String host,int port) throws Exception{
		
		clientEx = new TCPClientConnector(host,port, null);

		ConnectorConfiguration configuration = new ConnectorConfiguration();
		configuration.setModel("FMXXX");
		configuration.setName("G2021-Simulator");
		configuration.setVendor("Teltonika");
		Handler handler = new Handler();
		handler.setChannelHandlerProvider(TCPClientHandler.class.getName(), null);
			handler.setName("Teltonika Simulator - ");

		configuration.addDeviceHandler(handler);
		clientEx.setConfiguration(configuration);
		connectionAvailable = clientEx.connect();
		return connectionAvailable;
	}

	public static void stopSimulator() throws ConfigurationException{
		clientEx.disconnect();
	}

	public static void sendMessageToServer(byte[] clientMessage) throws InterruptedException,IOException {
		if(connectionAvailable){
			clientEx.sendMessage(clientMessage);
		}
	}
}
