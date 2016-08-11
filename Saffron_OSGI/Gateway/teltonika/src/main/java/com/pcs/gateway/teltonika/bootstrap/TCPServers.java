package com.pcs.gateway.teltonika.bootstrap;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.gateway.teltonika.bundle.utils.ConnectivityUtil;
import com.pcs.gateway.teltonika.config.TeltonikaGatewayConfiguration;
import com.pcs.gateway.teltonika.handler.tcp.TeltonikaTCPHandler;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.config.ConnectorConfiguration;
import com.pcs.saffron.connectivity.config.Handler;
import com.pcs.saffron.connectivity.exception.ConfigurationException;
import com.pcs.saffron.connectivity.tcp.TCPConnector;

public class TCPServers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TCPServers.class);
	private Object[] startConfig ;
	TCPConnector teltonikaConnector = null;
	public TCPServers(){
		startConfig = new Object[3];
		startConfig[1] = Connector.CONNECTOR_MODE_TCP;
	}
	
	public Object[] getStartConfig() {
		return startConfig;
	}

	public void setStartConfig(Object[] startConfig) {
		this.startConfig = startConfig;
	}
	
	public void startTCPServers(boolean startWithDelay, int mode){
		startConfig[0] = startWithDelay;
		
		switch (mode) {
		case 1:
			new Thread(new ControlServer()).start();
			break;

		case 2:
			new Thread(new ControlServer()).start();
			break;

		case 3:
			new Thread(new ControlServer()).start();
			//new Thread(new DataServer()).start();
			break;

		default:
			System.err.println("Invalid option(s) provided!!!");
			break;
		}
		
		
		
	}
	
	public void stopTCPServers(){
		if(teltonikaConnector != null){
			teltonikaConnector.disconect();
		}
	}

	class ControlServer implements Runnable{
		public void run() {

			startConfig[2] = "TCP Control Server";
			teltonikaConnector = ConnectivityUtil.getTcpConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			//configuration.addDeviceHandlers(frame);
			handler.setName("TeltonikaTCP");
			handler.setChannelHandlerProvider(TeltonikaTCPHandler.class.getName(), null);
			configuration.setModel("TeltonikaTCP");
			configuration.setName("TeltonikaTCP");
			configuration.setPort(Integer.parseInt(TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.TCP_CONTROL_SERVER_PORT)));
			configuration.setVendor("PCS");
			teltonikaConnector.setConfiguration(configuration);
			try {
				teltonikaConnector.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}
		}
	}
	
	/*class DataServer implements Runnable{

		@Override
		public void run() {

			startConfig[2] = "TCP Data Server";
			TCPConnector TeltonikaListener = new TCPConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler frame = new Handler();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			//configuration.addDeviceHandlers(frame);
			frame.setName("TeltonikaDecoderTCP");
			frame.setChannelHandlerProvider(TeltonikaFrame.class.getName(), null);
			handler.setName("TeltonikaHandlerTCP");
			handler.setChannelHandlerProvider(TeltonikaHandler.class.getName(), startConfig);
			configuration.setModel("TeltonikaTCP");
			configuration.setName("TeltonikaTCP");
			configuration.setPort(Integer.parseInt(TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.TCP_DATA_SERVER_PORT)));
			configuration.setVendor("PCS");
			TeltonikaListener.setConfiguration(configuration);
			try {
				TeltonikaListener.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}

		
		}
		
	}*/
}
