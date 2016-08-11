package com.pcs.device.gateway.ruptela.bundle.utils;

import com.pcs.saffron.manager.DeviceManager;

public final class DeviceManagerUtil {


	private static DeviceManager ruptelaDeviceManager;


	/**
	 * @return the ruptelaDeviceManager
	 */
	public static DeviceManager getRuptelaDeviceManager() {
		return ruptelaDeviceManager;
	}


	/**
	 * @param ruptelaDeviceManager the teltonikaDeviceManager to set
	 */
	public static void setRuptelaDeviceManager(
			DeviceManager ruptelaDeviceManager) {
		DeviceManagerUtil.ruptelaDeviceManager = ruptelaDeviceManager;
	}
}
