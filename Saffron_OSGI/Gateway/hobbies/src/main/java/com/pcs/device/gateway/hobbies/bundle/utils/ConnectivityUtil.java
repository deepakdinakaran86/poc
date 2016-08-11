package com.pcs.device.gateway.hobbies.bundle.utils;

import com.pcs.saffron.connectivity.tcp.TCPConnector;
import com.pcs.saffron.connectivity.udp.UDPConnector;

public final class ConnectivityUtil {


	
	
	/**
	 * @return the tcpConnector
	 */
	public static TCPConnector getTcpConnector() {
		return new TCPConnector();
	}
	
	/**
	 * @param tcpConnector the tcpConnector to set
	 */
	public static void setTcpConnector(TCPConnector tcpConnector) {
		
	}
	
	/**
	 * @return the udpConnector
	 */
	public static UDPConnector getUdpConnector() {
		return new UDPConnector();
	}
	
	/**
	 * @param udpConnector the udpConnector to set
	 */
	public static void setUdpConnector(UDPConnector udpConnector) {
		
	}
	
	


}
