/**
 * 
 */
package com.pcs.datasource.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is responsible for latest device data (to map from cassandra data store)
 * 
 * @author pcseg129(Seena Jyothish) May 21, 2016
 */
public class LatestDeviceData implements Serializable {
	private static final long serialVersionUID = 8144839783518072956L;
	
	String device;
	String displayName;
	String data;
	String deviceDate;
	long deviceTime;
	String unit;
	
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDeviceDate() {
		return deviceDate;
	}

	public void setDeviceDate(String deviceDate) {
		this.deviceDate = deviceDate;
	}

	public long getDeviceTime() {
		return deviceTime;
	}

	public void setDeviceTime(long deviceTime) {
		this.deviceTime = deviceTime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
