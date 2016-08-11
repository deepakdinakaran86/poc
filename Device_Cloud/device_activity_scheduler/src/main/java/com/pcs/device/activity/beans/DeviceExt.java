/**
 * 
 */
package com.pcs.device.activity.beans;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Apr 19, 2016
 */
public class DeviceExt extends Device {
	private static final long serialVersionUID = 5229526694961343286L;
	
	Long lastUpdatedTime;

	public Long getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Long lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

}
