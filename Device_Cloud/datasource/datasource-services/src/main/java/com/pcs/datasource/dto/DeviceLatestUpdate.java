/**
 * 
 */
package com.pcs.datasource.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pcs.datasource.services.serializer.DateSerializer;

/**
 * @author pcseg129(Seena Jyothish)
 * @date 18 Apr 2016
 *
 */
public class DeviceLatestUpdate {
	
	String deviceId;
	@JsonDeserialize(using = DateSerializer.class)
	Long lastUpdatedTime;
	String datasourceName;
	String deviceName;
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Long getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(Long lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public String getDatasourceName() {
		return datasourceName;
	}
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	

}
