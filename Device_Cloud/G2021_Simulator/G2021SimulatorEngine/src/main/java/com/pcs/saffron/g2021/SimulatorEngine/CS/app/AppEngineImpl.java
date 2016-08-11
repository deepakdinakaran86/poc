package com.pcs.saffron.g2021.SimulatorEngine.CS.app;

import com.pcs.saffron.g2021.SimulatorEngine.CS.config.SimulatorConfiguration;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.Points;
import com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler.AuthenticateHandler;
import com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler.HelloHandler;
import com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler.PointDiscovreyResponseHandler;
import com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler.PointScordCardHandler;
import com.pcs.saffron.g2021.SimulatorEngine.CS.tcpClient.TCPClient;

/**
 * 
 * @author Santhosh
 *
 */

public class AppEngineImpl {	

	public static byte[] getHelloMsg(SimulatorConfiguration config) {
		byte[] helloMsg = null;
		try {
			helloMsg = HelloHandler.getHello(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return helloMsg;
	}
	
	public static byte[] getDeviceAuthenticate() {
		byte[] authenticate = AuthenticateHandler.getAuthenticatedMsg();
		return authenticate;
	}

	public static byte[] getPointDiscoveryResponse(Points[] object, int startPosition, int endPosition) {
		byte[] response = PointDiscovreyResponseHandler.getPointDiscoveryResponseMessage(object, startPosition,
				endPosition);
		return response;
	}

	public static byte[] getPointDiscoveryScordCard(Integer scoreCard,Integer result) {
		byte[] response = PointScordCardHandler.getPointScordCardMessage(scoreCard,result);
		return response;
	}

	public static boolean openClientConnection(String host, int port) throws Exception {
		return TCPClient.openClientConnection(host, port);
	}	
	
}
