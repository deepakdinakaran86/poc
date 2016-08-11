package com.pcs.device.gateway.jace.bootstrap;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.jace.bundle.utils.ConnectivityUtil;
import com.pcs.device.gateway.jace.config.JaceConfiguration;
import com.pcs.device.gateway.jace.handler.JaceDelimiterHandler;
import com.pcs.device.gateway.jace.handler.JaceMessageHandler;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.config.ConnectorConfiguration;
import com.pcs.saffron.connectivity.config.Handler;
import com.pcs.saffron.connectivity.exception.ConfigurationException;
import com.pcs.saffron.connectivity.tcp.TCPConnector;

public class TCPServers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TCPServers.class);
	private Object[] startConfig ;
	TCPConnector jaceConnector = null;
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
		if(jaceConnector != null){
			jaceConnector.disconect();
		}
	}

	class ControlServer implements Runnable{
		public void run() {

			startConfig[2] = "TCP Control Server";
			jaceConnector = ConnectivityUtil.getTcpConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			
			Handler frameHandler = new Handler();
			configuration.addDeviceHandler(frameHandler);
			frameHandler.setName("JaceCarrierLimitHandler");
			frameHandler.setChannelHandlerProvider(JaceDelimiterHandler.class.getName(), null);
			
			Handler handler = new Handler();
			configuration.addDeviceHandler(handler);
			handler.setName("JACETCP");
			handler.setChannelHandlerProvider(JaceMessageHandler.class.getName(), null);

			configuration.setModel("JACETCP");
			configuration.setName("JACETCP");
			configuration.setPort(Integer.parseInt(JaceConfiguration.getProperty(JaceConfiguration.TCP_CONTROL_SERVER_PORT)));
			configuration.setVendor("PCS");
			jaceConnector.setConfiguration(configuration);
			try {
				jaceConnector.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}
		}
	}
	
}
