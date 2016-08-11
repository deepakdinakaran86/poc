/**
 * 
 */
package com.pcs.device.activity.beans;

import com.pcs.device.activity.enums.DeviceCommStatus;

/**
 * This class is responsible for holding device communication properties
 * 
 * @author pcseg129(Seena Jyothish) Apr 19, 2016
 */
public class DeviceTransition {
	String deviceId;
	Long lastOnlineTime;
	Long lastOfflineTime;
	DeviceCommStatus status;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Long getLastOnlineTime() {
		return lastOnlineTime;
	}

	public void setLastOnlineTime(Long lastOnlineTime) {
		this.lastOnlineTime = lastOnlineTime;
	}

	public Long getLastOfflineTime() {
		return lastOfflineTime;
	}

	public void setLastOfflineTime(Long lastOfflineTime) {
		this.lastOfflineTime = lastOfflineTime;
	}

	public DeviceCommStatus getStatus() {
		return status;
	}

	public void setStatus(DeviceCommStatus status) {
		this.status = status;
	}

}
