package com.pcs.device.gateway.jace.bootstrap;

import com.pcs.device.gateway.jace.config.JaceConfiguration;





public class Listeners {

	
	TCPServers tcpServers = null;
	UDPServers udpServers = null;
	public static void main(String[] args) throws InterruptedException {
		
		Listeners listeners = new Listeners();
		listeners.startFromConfiguration();
		/*Thread.sleep(30000);
		listeners.stopServers();*/
	}

	public void startFromConfiguration(){
		Integer mode = Integer.parseInt(JaceConfiguration.getProperty(JaceConfiguration.START_MODE));
		String delayed = JaceConfiguration.getProperty(JaceConfiguration.START_WITH_DELAY).toUpperCase();
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

		/*udpServers = new UDPServers();
		udpServers.startUDPServers(mode);*/
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
