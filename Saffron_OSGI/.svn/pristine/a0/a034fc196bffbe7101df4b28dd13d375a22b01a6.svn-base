package com.pcs.saffron.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.tcp.TCPConnector;
import com.pcs.saffron.connectivity.udp.UDPConnector;

public class ConnectivityActivator implements BundleActivator{

	ServiceRegistration tcpConnectorService = null;
	ServiceRegistration udpConnectorService = null;
	
	public void start(BundleContext context) throws Exception {

		System.err.println("Registering Connectivity Service...");
		Connector tcpConnector = new TCPConnector();
		Connector udpConnector = new UDPConnector();
		tcpConnectorService = context.registerService(TCPConnector.class.getName(), tcpConnector, null);
		udpConnectorService = context.registerService(UDPConnector.class.getName(), udpConnector, null);
		//LOGGER.info("Notification Service Registered Successfully");
		System.err.println("Connectivity Service Registered Successfully");

	
	}

	public void stop(BundleContext context) throws Exception {

		//LOGGER.info("Shutting down Notification Service...");
		System.err.println("Shutting down Connectivity Service...");
		tcpConnectorService.unregister();
		udpConnectorService.unregister();
		//LOGGER.info("Notification Service Is Terminated !!");
		System.err.println("Connectivity Service Is Terminated !!");
	
	}

}
