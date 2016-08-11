package com.pcs.device.gateway.G2021.devicemanager.bean;

import java.io.Serializable;
import java.util.List;

import com.pcs.deviceframework.decoder.data.point.Point;

public class Configuration implements Serializable {

	private static final long serialVersionUID = 5033013693634352560L;
	private String deviceMake;
	private String deviceType;
	private String deviceModel;
	private String deviceProtocol;
	private String deviceProtocolVersion;
	private List<Point> configPoints;

	public List<Point> getConfigPoints() {
		return configPoints;
	}

	public void setConfigPoints(List<Point> configPoints) {
		this.configPoints = configPoints;
	}

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
	
}
