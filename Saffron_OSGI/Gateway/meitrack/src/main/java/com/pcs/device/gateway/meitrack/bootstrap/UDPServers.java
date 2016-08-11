package com.pcs.device.gateway.meitrack.bootstrap;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.meitrack.bundle.utils.ConnectivityUtil;
import com.pcs.device.gateway.meitrack.config.MeitrackGatewayConfiguration;
import com.pcs.device.gateway.meitrack.handler.tcp.MeitrackTCPHandler;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.config.ConnectorConfiguration;
import com.pcs.saffron.connectivity.config.Handler;
import com.pcs.saffron.connectivity.exception.ConfigurationException;
import com.pcs.saffron.connectivity.udp.UDPConnector;

public class UDPServers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UDPServers.class);
	private Object[] startConfig;
	private UDPConnector meitrackConnector = null;
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
		if(meitrackConnector != null){
			meitrackConnector.disconect();
		}
	}
	
	class ControlServer implements Runnable{
		@Override
		public void run() {

			startConfig[2] = "UDP Control Server";
			meitrackConnector = ConnectivityUtil.getUdpConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			handler.setName("meitrackHandlerUDP");
			handler.setChannelHandlerProvider(MeitrackTCPHandler.class.getName(), startConfig);
			configuration.setModel("meitrackUDP");
			configuration.setName("meitrackUDP");
			configuration.setPort(Integer.parseInt(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.UDP_CONTROL_SERVER_PORT)));
			configuration.setVendor("PCS");
			meitrackConnector.setConfiguration(configuration);
			try {
				meitrackConnector.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}
		}
	}
	
	/*class DataServer implements Runnable{

		@Override
		public void run() {

			startConfig[2] = "UDP Data Server";
			UDPConnector meitrackListener = new UDPConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler frame = new Handler();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			//configuration.addDeviceHandlers(frame);
			frame.setName("meitrackDecoderUDP");
			frame.setChannelHandlerProvider(meitrackFrame.class.getName(), null);
			handler.setName("meitrackHandlerUDP");
			handler.setChannelHandlerProvider(meitrackHandler.class.getName(), startConfig);
			configuration.setModel("meitrackUDP");
			configuration.setName("meitrackUDP");
			configuration.setPort(Integer.parseInt(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.UDP_DATA_SERVER_PORT)));
			configuration.setVendor("PCS");
			meitrackListener.setConfiguration(configuration);
			try {
				meitrackListener.connect();
			} catch (ConfigurationException e) {
				LOGGER.error("Error starting TCP control server",e);
			}

		
		}
		
	}*/
	
}
