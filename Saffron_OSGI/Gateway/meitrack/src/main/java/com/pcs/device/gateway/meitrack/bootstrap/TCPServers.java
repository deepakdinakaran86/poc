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
import com.pcs.saffron.connectivity.tcp.TCPConnector;

public class TCPServers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TCPServers.class);
	private Object[] startConfig ;
	TCPConnector meitrackConnector = null;
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
		if(meitrackConnector != null){
			meitrackConnector.disconect();
		}
	}

	class ControlServer implements Runnable{
		public void run() {

			startConfig[2] = "TCP Control Server";
			meitrackConnector = ConnectivityUtil.getTcpConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			//configuration.addDeviceHandlers(frame);
			handler.setName("meitrackTCP");
			handler.setChannelHandlerProvider(MeitrackTCPHandler.class.getName(), null);
			configuration.setModel("meitrackTCP");
			configuration.setName("meitrackTCP");
			configuration.setPort(Integer.parseInt(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.TCP_CONTROL_SERVER_PORT)));
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

			startConfig[2] = "TCP Data Server";
			TCPConnector meitrackListener = new TCPConnector();
			ConnectorConfiguration configuration = new ConnectorConfiguration();
			Handler frame = new Handler();
			Handler handler = new Handler();
			//
			configuration.addDeviceHandler(handler);
			//configuration.addDeviceHandlers(frame);
			frame.setName("meitrackDecoderTCP");
			frame.setChannelHandlerProvider(meitrackFrame.class.getName(), null);
			handler.setName("meitrackHandlerTCP");
			handler.setChannelHandlerProvider(meitrackHandler.class.getName(), startConfig);
			configuration.setModel("meitrackTCP");
			configuration.setName("meitrackTCP");
			configuration.setPort(Integer.parseInt(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.TCP_DATA_SERVER_PORT)));
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
