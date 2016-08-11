package com.pcs.device.gateway.G2021.test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.handler.G2021Frame;
import com.pcs.device.gateway.G2021.handler.G2021SimpleHandler;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.config.ConnectorConfiguration;
import com.pcs.saffron.connectivity.config.Handler;
import com.pcs.saffron.connectivity.exception.ConfigurationException;
import com.pcs.saffron.connectivity.tcp.TCPConnector;

public class TCPServers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TCPServers.class);
	private static final String COMMA = ",";
	private Object[] startConfig ;
	List<TCPConnector> g2021ControlListeners = new ArrayList<TCPConnector>();
	List<TCPConnector> g2021DataListeners = new ArrayList<TCPConnector>();
	
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
		String controlserverPorts = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_CONTROL_SERVER_PORT);
		String dataserverPorts = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_SERVER_PORT);
		String[] controlServers = controlserverPorts.split(COMMA);
		String[] dataServers = dataserverPorts.split(COMMA);
		
		switch (mode) {
		case 1:
			for (String controlServerPort : controlServers) {
				new Thread(new ControlServer(Integer.parseInt(controlServerPort))).start();
			}
			break;

		case 2:
			for (String dataserverPort : dataServers) {
				new Thread(new DataServer(Integer.parseInt(dataserverPort))).start();
			}
			break;

		case 3:
			for (String controlServerPort : controlServers) {
				new Thread(new ControlServer(Integer.parseInt(controlServerPort))).start();
			}
			
			for (String dataserverPort : dataServers) {
				new Thread(new DataServer(Integer.parseInt(dataserverPort))).start();
			}
			break;

		default:
			LOGGER.info("Invalid option(s) provided!!!");
			break;
		}
		
		
		
	}
	
	public void stopTCPServers(){
		if(g2021ControlListeners != null){
			LOGGER.info("Stopping servers");
			for (TCPConnector listener : g2021ControlListeners ) {
				listener.disconect();
			}
		}
		
	}

	class ControlServer implements Runnable{
		
		private Integer controlserverPort = 0;
		
		public ControlServer(){
			
		}
		
		public ControlServer(Integer controlServerPort){
			this.controlserverPort = controlServerPort;
		}
		
		public void run() {

			startConfig[2] = "TCP Control Server";
			TCPConnector controlServer = new TCPConnector();
			g2021ControlListeners.add(controlServer);
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler delimiterHandler = new Handler();
			Handler handler = new Handler();
			
		    configuration.addDeviceHandler(delimiterHandler);
		    delimiterHandler.setName("G2021DelimiterHandlerTCP");
		    delimiterHandler.setChannelHandlerProvider(G2021Frame.class.getName(), null);
			
			configuration.addDeviceHandler(handler);
			handler.setName("G2021HandlerTCP");
			handler.setChannelHandlerProvider(G2021SimpleHandler.class.getName(), startConfig);
			configuration.setModel("G2021TCP");
			configuration.setName("G2021TCP");
			configuration.setPort(controlserverPort);
			configuration.setVendor("PCS");
			controlServer.setConfiguration(configuration);
			try {
				LOGGER.info("TCP control server listening @ {}",controlserverPort);
				controlServer.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}
		}
	}
	
	class DataServer implements Runnable{
		
		private Integer dataserverPort = 0;
		
		public DataServer(){
			
		}
		
		public DataServer(Integer dataserverPort){
			this.dataserverPort = dataserverPort;
		}

		public void run() {

			startConfig[2] = "TCP Data Server";
			TCPConnector dataListener = new TCPConnector();
			g2021DataListeners.add(dataListener);
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			
			Handler delimiterHandler = new Handler();
			Handler handler = new Handler();
			
		    configuration.addDeviceHandler(delimiterHandler);
		    delimiterHandler.setName("G2021DelimiterHandlerTCP");
		    delimiterHandler.setChannelHandlerProvider(G2021Frame.class.getName(), null);
			
			configuration.addDeviceHandler(handler);
			handler.setName("G2021HandlerTCP");
			handler.setChannelHandlerProvider(G2021SimpleHandler.class.getName(), startConfig);
			configuration.setModel("G2021TCP");
			configuration.setName("G2021TCP");
			configuration.setPort(dataserverPort);
			configuration.setVendor("PCS");
			dataListener.setConfiguration(configuration);
			try {
				LOGGER.info("TCP data server listening @ {}",dataserverPort);
				dataListener.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}
			
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		TCPServers tcpServers = new TCPServers();
		tcpServers.startTCPServers(false, 3);
	}
}
