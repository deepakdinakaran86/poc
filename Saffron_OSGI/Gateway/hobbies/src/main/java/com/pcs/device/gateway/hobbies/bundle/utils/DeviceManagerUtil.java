package com.pcs.device.gateway.hobbies.bundle.utils;

import com.pcs.saffron.manager.DeviceManager;

public final class DeviceManagerUtil {


	private static DeviceManager hobbiesDeviceManager;


	/**
	 * @return the hobbiesDeviceManager
	 */
	public static DeviceManager getHobbiesDeviceManager() {
		return hobbiesDeviceManager;
	}


	/**
	 * @param hobbiesDeviceManager the hobbiesDeviceManager to set
	 */
	public static void setHobbiesDeviceManager(
			DeviceManager hobbiesDeviceManager) {
		DeviceManagerUtil.hobbiesDeviceManager = hobbiesDeviceManager;
	}
}
