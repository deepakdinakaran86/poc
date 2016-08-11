package com.pcs.device.gateway.G2021.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.handler.G2021SimpleHandler;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.config.ConnectorConfiguration;
import com.pcs.saffron.connectivity.config.Handler;
import com.pcs.saffron.connectivity.exception.ConfigurationException;
import com.pcs.saffron.connectivity.udp.UDPConnector;

public class UDPServers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UDPServers.class);
	private Object[] startConfig;
	private UDPConnector g2021ControlListener = null;
	private UDPConnector g2021DataListener = null;
	
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
			new Thread(new DataServer()).start();
			break;

		default:
			System.err.println("Invalid option(s) provided!!!");
			break;
		}
		
	}
	
	public void stopUDPServers(){
		if(g2021ControlListener != null){
			g2021ControlListener.disconect();
		}
		if(g2021DataListener != null){
			g2021DataListener.disconect();
		}
	}
	
	
	class ControlServer implements Runnable{
		public void run() {

			startConfig[2] = "UDP Control Server";
			g2021ControlListener = new UDPConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			handler.setName("G2021HandlerUDP");
			handler.setChannelHandlerProvider(G2021SimpleHandler.class.getName(), startConfig);
			configuration.setModel("G2021UDP");
			configuration.setName("G2021UDP");
			configuration.setPort(Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_CONTROL_SERVER_PORT)));
			configuration.setVendor("PCS");
			g2021ControlListener.setConfiguration(configuration);
			try {
				g2021ControlListener.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}
		}
	}
	
	class DataServer implements Runnable{

		public void run() {
			startConfig[2] = "UDP Data Server";
			g2021DataListener = new UDPConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			//configuration.addDeviceHandlers(frame);
			handler.setName("G2021HandlerUDP");
			handler.setChannelHandlerProvider(G2021SimpleHandler.class.getName(), startConfig);
			configuration.setModel("G2021UDP");
			configuration.setName("G2021UDP");
			configuration.setPort(Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_SERVER_PORT)));
			configuration.setVendor("PCS");
			g2021DataListener.setConfiguration(configuration);
			try {
				g2021DataListener.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}
		}
	}
}
