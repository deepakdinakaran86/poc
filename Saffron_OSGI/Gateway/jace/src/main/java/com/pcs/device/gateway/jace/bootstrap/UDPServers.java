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
import com.pcs.saffron.connectivity.udp.UDPConnector;

public class UDPServers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UDPServers.class);
	private Object[] startConfig;
	private UDPConnector jaceConnector = null;
	public UDPServers(){
		startConfig = new Object[3];
		startConfig[0] = false;
		startConfig[1] = Connector.CONNECTOR_MODE_UDP;
	}
	
	
	public Object[] getStartConfig() {
		return startConfig;
	}

	public void setStartConfig(Object[] startConfig) {
		this.startConfig = startConfig;
	}
	
	
	
	public void startUDPServers(int mode){
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
	
	
	public void stopUDPServers(){
		if(jaceConnector != null){
			jaceConnector.disconect();
		}
	}
	
	class ControlServer implements Runnable{
		public void run() {

			startConfig[2] = "UDP Control Server";
			jaceConnector = ConnectivityUtil.getUdpConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			
			Handler frameHandler = new Handler();
			configuration.addDeviceHandler(frameHandler);
			frameHandler.setName("JaceCarrierLimitHandler");
			frameHandler.setChannelHandlerProvider(JaceDelimiterHandler.class.getName(), null);
			
			Handler handler = new Handler();
			configuration.addDeviceHandler(handler);
			handler.setName("JACEUDP");
			handler.setChannelHandlerProvider(JaceMessageHandler.class.getName(), null);

			configuration.setModel("JACEUDP");
			configuration.setName("JACEUDP");
			configuration.setPort(Integer.parseInt(JaceConfiguration.getProperty(JaceConfiguration.UDP_CONTROL_SERVER_PORT)));
			configuration.setVendor("PCS");
			
			try {
				jaceConnector.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}
		}
	}
	
}
