package com.pcs.gateway.teltonika.bootstrap;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.gateway.teltonika.bundle.utils.ConnectivityUtil;
import com.pcs.gateway.teltonika.config.TeltonikaGatewayConfiguration;
import com.pcs.gateway.teltonika.handler.udp.TeltonikaUDPHandler;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.config.ConnectorConfiguration;
import com.pcs.saffron.connectivity.config.Handler;
import com.pcs.saffron.connectivity.exception.ConfigurationException;
import com.pcs.saffron.connectivity.udp.UDPConnector;

public class UDPServers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UDPServers.class);
	private Object[] startConfig;
	private UDPConnector teltonikaConnector = null;
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
		if(teltonikaConnector != null){
			teltonikaConnector.disconect();
		}
	}
	
	class ControlServer implements Runnable{
		@Override
		public void run() {

			startConfig[2] = "UDP Control Server";
			teltonikaConnector = ConnectivityUtil.getUdpConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			handler.setName("TeltonikaHandlerUDP");
			handler.setChannelHandlerProvider(TeltonikaUDPHandler.class.getName(), startConfig);
			configuration.setModel("TeltonikaUDP");
			configuration.setName("TeltonikaUDP");
			configuration.setPort(Integer.parseInt(TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.UDP_CONTROL_SERVER_PORT)));
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

			startConfig[2] = "UDP Data Server";
			UDPConnector TeltonikaListener = new UDPConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler frame = new Handler();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			//configuration.addDeviceHandlers(frame);
			frame.setName("TeltonikaDecoderUDP");
			frame.setChannelHandlerProvider(TeltonikaFrame.class.getName(), null);
			handler.setName("TeltonikaHandlerUDP");
			handler.setChannelHandlerProvider(TeltonikaHandler.class.getName(), startConfig);
			configuration.setModel("TeltonikaUDP");
			configuration.setName("TeltonikaUDP");
			configuration.setPort(Integer.parseInt(TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.UDP_DATA_SERVER_PORT)));
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
