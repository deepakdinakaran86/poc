package com.pcs.device.gateway.G2021.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.utils.SupportedDevices;
import com.pcs.device.gateway.G2021.utils.SupportedDevices.Devices;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.DeviceConfiguration;
import com.pcs.saffron.manager.provider.EOSDeviceManager;


public class Listeners {

	TCPServers tcpServers = null;
	UDPServers udpServers = null;
	
	public static void main(String[] args) throws JsonProcessingException {
		
		EOSDeviceManager.instance().invalidateSession(2, SupportedDevices.getGateway(Devices.EDCP));
		DefaultConfiguration configuration = (DefaultConfiguration) EOSDeviceManager.instance().getConfiguration(2, SupportedDevices.getGateway(Devices.EDCP));
		ObjectMapper mapper = new ObjectMapper();
		
		System.err.println(mapper.writeValueAsString(configuration.getConfigPoints()));
		//EOSDeviceManager.instance().setDeviceConfiguration(4, configuration, SupportedDevices.getGateway(Devices.EDCP));
		///172.20.16.246
		//G2021DeviceManager.instance().getCacheProvider().getCache("datasource.cache.device.config").clear();
		//G2021DeviceManager.instance().getCacheProvider().getCache("datasource.cache.device").clear();
		//G2021DeviceManager.instance().getCacheProvider().getCache("g2021.devicemanager.cache.keys").clear();
		//G2021DeviceConfiguration deviceConfiguration = G2021DeviceManager.instance().getDeviceConfiguration(20);
//		G2021DeviceManager.instance().evictDeviceConfiguration(29);
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
		
		
		new Listeners().startFromConfiguration();
	}

	public void startFromConfiguration(){
		Integer mode = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.START_MODE));
		String delayed = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.START_WITH_DELAY).toUpperCase();
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
		//CommandProcessorDefaultAsync.getInstance().doProcess();
	}
	
	public void stopServers(){
		if(tcpServers != null)
			tcpServers.stopTCPServers();
		if(udpServers != null)
			udpServers.stopUDPServers();
	}

	private void startServers(boolean startWithDelay, int mode) {
		
		tcpServers = new TCPServers();
		tcpServers.startTCPServers(startWithDelay,mode);

		udpServers = new UDPServers();
		udpServers.startUDPServers(mode);
	}

}
