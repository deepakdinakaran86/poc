package com.pcs.device.gateway.G2021.test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pcs.device.gateway.G2021.command.executor.CommandProcessorDefault;
import com.pcs.device.gateway.G2021.command.executor.CommandProcessorDefaultAsync;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.G2021DeviceManager;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.data.point.extension.PointExtension;



public class Listeners {

	public static void main(String[] args) {
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

	private void startFromConfiguration(){
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

	private void startServers(boolean startWithDelay, int mode) {
		
		TCPServers tcpServers = new TCPServers();
		tcpServers.startTCPServers(startWithDelay,mode);

		UDPServers udpServers = new UDPServers();
		udpServers.startUDPServers(mode);
	}

}
