package com.pcs.gateway.teltonika.bootstrap;

import com.pcs.gateway.teltonika.config.TeltonikaGatewayConfiguration;




public class Listeners {

	
	TCPServers tcpServers = null;
	UDPServers udpServers = null;
	public static void main(String[] args) throws InterruptedException {
		
		Listeners listeners = new Listeners();
		listeners.startFromConfiguration();
		Thread.sleep(30000);
		listeners.stopServers();
	}

	public void startFromConfiguration(){
		Integer mode = Integer.parseInt(TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.START_MODE));
		String delayed = TeltonikaGatewayConfiguration.getProperty(TeltonikaGatewayConfiguration.START_WITH_DELAY).toUpperCase();
		boolean startWithDelay = false;
		switch (delayed) {
		case "Y":
			startWithDelay = true;
			break;
		case "N":
			startWithDelay = false;
			break;
		default:
			break;
		}
		startServers(startWithDelay, mode);
	}

	private void startServers(boolean startWithDelay, int mode) {
		
		tcpServers = new TCPServers();
		tcpServers.startTCPServers(startWithDelay,mode);

		udpServers = new UDPServers();
		udpServers.startUDPServers(mode);
	}
	
	public void stopServers(){
		if(tcpServers != null){
			tcpServers.stopTCPServers();
		}
		if(udpServers != null){
			udpServers.stopUDPServers();
		}
	}
	
	

}
