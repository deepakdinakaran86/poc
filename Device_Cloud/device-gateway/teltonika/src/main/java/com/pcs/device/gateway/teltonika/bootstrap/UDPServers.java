package com.pcs.device.gateway.teltonika.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.teltonika.config.TeltonikaGatewayConfiguration;
import com.pcs.device.gateway.teltonika.handler.udp.TeltonikaUDPHandler;
import com.pcs.deviceframework.connection.Connector;
import com.pcs.deviceframework.connection.config.ConnectorConfiguration;
import com.pcs.deviceframework.connection.config.Handler;
import com.pcs.deviceframework.connection.exception.ConfigurationException;
import com.pcs.deviceframework.connection.udp.UDPConnector;

public class UDPServers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UDPServers.class);
	private Object[] startConfig;
	
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
	
	
	class ControlServer implements Runnable{
		@Override
		public void run() {

			startConfig[2] = "UDP Control Server";
			UDPConnector g2021Listener = new UDPConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			handler.setName("G2021HandlerUDP");
			handler.setChannelHandlerProvider(TeltonikaUDPHandler.class.getName(), startConfig);
			configuration.setModel("G2021UDP");
			configuration.setName("G2021UDP");
			configuration.setPort(Integer.parseInt(TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.UDP_CONTROL_SERVER_PORT)));
			configuration.setVendor("PCS");
			g2021Listener.setConfiguration(configuration);
			try {
				g2021Listener.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}
		}
	}
	
	/*class DataServer implements Runnable{

		@Override
		public void run() {

			startConfig[2] = "UDP Data Server";
			UDPConnector g2021Listener = new UDPConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler frame = new Handler();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			//configuration.addDeviceHandlers(frame);
			frame.setName("G2021DecoderUDP");
			frame.setChannelHandlerProvider(G2021Frame.class.getName(), null);
			handler.setName("G2021HandlerUDP");
			handler.setChannelHandlerProvider(G2021Handler.class.getName(), startConfig);
			configuration.setModel("G2021UDP");
			configuration.setName("G2021UDP");
			configuration.setPort(Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_SERVER_PORT)));
			configuration.setVendor("PCS");
			g2021Listener.setConfiguration(configuration);
			try {
				g2021Listener.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}

		
		}
		
	}*/
	
}
