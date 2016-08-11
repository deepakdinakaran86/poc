package com.pcs.util.faultmonitor.osgi.util;

import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.provider.EOSDeviceManager;

public final class DeviceManagerUtil {


	private static DeviceManager g2021DeviceManager;


	/**
	 * @return the teltonikaDeviceManager
	 */
	public static DeviceManager getG2021DeviceManager() {
		if(g2021DeviceManager == null){
			g2021DeviceManager = EOSDeviceManager.instance();
		}
		return g2021DeviceManager;
	}


	/**
	 * @param g2021DeviceManager the teltonikaDeviceManager to set
	 */
	public static void setG2021DeviceManager(
			DeviceManager g2021DeviceManager) {
		DeviceManagerUtil.g2021DeviceManager = g2021DeviceManager;
	}
}
