package com.pcs.device.gateway.meitrack.bootstrap;

import com.pcs.device.gateway.meitrack.config.MeitrackGatewayConfiguration;





public class Listeners {

	
	TCPServers tcpServers = null;
	UDPServers udpServers = null;
	public static void main(String[] args) throws InterruptedException {
		//G2021DeviceManager.instance().getCacheProvider().getCache("datasource.cache.device").clear();
		/*Map<Object, Point> pointMapping = deviceConfiguration.getPointMapping();
		for (Object key : pointMapping.keySet()) {
			Point point = pointMapping.get(key);
			List<PointExtension> extensions = point.getExtensions();
			for (Iterator iterator = extensions.iterator(); iterator.hasNext();) {
				PointExtension pointExtension = (PointExtension) iterator
						.next();
				if(pointExtension.getExtensionType().equalsIgnoreCase("STATUS")){
					iterator.remove();
				}
			}
		}
		G2021DeviceManager.instance().refreshDeviceConfiguration(21, deviceConfiguration);*/
		Listeners listeners = new Listeners();
		listeners.startFromConfiguration();
		//Thread.sleep(30000);
		//listeners.stopServers();
	}

	public void startFromConfiguration(){
		Integer mode = Integer.parseInt(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.START_MODE));
		String delayed = MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.START_WITH_DELAY).toUpperCase();
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

		//udpServers = new UDPServers();
		//udpServers.startUDPServers(mode);
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
