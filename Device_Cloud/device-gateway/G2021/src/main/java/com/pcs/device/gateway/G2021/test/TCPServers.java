package com.pcs.device.gateway.G2021.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.decoder.frame.G2021Frame;
import com.pcs.device.gateway.G2021.handler.G2021Handler;
import com.pcs.deviceframework.connection.Connector;
import com.pcs.deviceframework.connection.config.ConnectorConfiguration;
import com.pcs.deviceframework.connection.config.Handler;
import com.pcs.deviceframework.connection.exception.ConfigurationException;
import com.pcs.deviceframework.connection.tcp.TCPConnector;

public class TCPServers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TCPServers.class);
	private Object[] startConfig ;
	
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
			new Thread(new DataServer()).start();
			break;

		default:
			System.err.println("Invalid option(s) provided!!!");
			break;
		}
		
		
		
	}

	class ControlServer implements Runnable{
		@Override
		public void run() {

			startConfig[2] = "TCP Control Server";
			TCPConnector g2021Listener = new TCPConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler frame = new Handler();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			//configuration.addDeviceHandlers(frame);
			frame.setName("G2021DecoderTCP");
			frame.setChannelHandlerProvider(G2021Frame.class.getName(), null);
			handler.setName("G2021HandlerTCP");
			handler.setChannelHandlerProvider(G2021Handler.class.getName(), startConfig);
			configuration.setModel("G2021TCP");
			configuration.setName("G2021TCP");
			configuration.setPort(Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_CONTROL_SERVER_PORT)));
			configuration.setVendor("PCS");
			g2021Listener.setConfiguration(configuration);
			try {
				g2021Listener.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}
		}
	}
	
	class DataServer implements Runnable{

		@Override
		public void run() {

			startConfig[2] = "TCP Data Server";
			TCPConnector g2021Listener = new TCPConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler frame = new Handler();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			//configuration.addDeviceHandlers(frame);
			frame.setName("G2021DecoderTCP");
			frame.setChannelHandlerProvider(G2021Frame.class.getName(), null);
			handler.setName("G2021HandlerTCP");
			handler.setChannelHandlerProvider(G2021Handler.class.getName(), startConfig);
			configuration.setModel("G2021TCP");
			configuration.setName("G2021TCP");
			configuration.setPort(Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_SERVER_PORT)));
			configuration.setVendor("PCS");
			g2021Listener.setConfiguration(configuration);
			try {
				g2021Listener.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}

		
		}
		
	}
}
