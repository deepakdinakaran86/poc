package com.pcs.device.gateway.meitrack.bundle.utils;

import com.pcs.saffron.manager.DeviceManager;

public final class DeviceManagerUtil {


	private static DeviceManager meitrackDeviceManager;


	/**
	 * @return the meitrackDeviceManager
	 */
	public static DeviceManager getMeitrackDeviceManager() {
		/*if(meitrackDeviceManager == null){
			meitrackDeviceManager = EOSDeviceManager.instance(); 
		}*/
		return meitrackDeviceManager;
	}


	/**
	 * @param meitrackDeviceManager the meitrackDeviceManager to set
	 */
	public static void setMeitrackDeviceManager(
			DeviceManager meitrackDeviceManager) {
		DeviceManagerUtil.meitrackDeviceManager = meitrackDeviceManager;
	}
}
