package com.pcs.saffron.deviceutil.bean;

import java.util.List;

public class ConfigurationBean {

	private String deviceMake;
	private String deviceType;
	private String deviceModel;
	private String deviceProtocol;
	private String deviceProtocolVersion;
	
	private List<ConfigPoint> configPoints;
	
	
	public String getDeviceMake() {
		return deviceMake;
	}
	public void setDeviceMake(String deviceMake) {
		this.deviceMake = deviceMake;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceProtocol() {
		return deviceProtocol;
	}
	public void setDeviceProtocol(String deviceProtocol) {
		this.deviceProtocol = deviceProtocol;
	}
	public String getDeviceProtocolVersion() {
		return deviceProtocolVersion;
	}
	public void setDeviceProtocolVersion(String deviceProtocolVersion) {
		this.deviceProtocolVersion = deviceProtocolVersion;
	}
	public List<ConfigPoint> getConfigPoints() {
		return configPoints;
	}
	public void setConfigPoints(List<ConfigPoint> configPoints) {
		this.configPoints = configPoints;
	}
}
