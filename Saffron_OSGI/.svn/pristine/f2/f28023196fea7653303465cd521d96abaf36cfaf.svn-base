package com.pcs.device.gateway.jace.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcs.device.gateway.jace.enums.DeviceType;


public class ConnectionMessage extends JaceMessage{

	/**
	 * 
	 */
	private String key;
	@JsonProperty("devtyp")
	private DeviceType deviceType;
	@JsonProperty("url")
	private String URL;
	

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = DeviceType.valueOfType(deviceType);
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}

}
