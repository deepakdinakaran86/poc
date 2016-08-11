package com.pcs.saffron.cipher.data.generic.message;

import java.util.List;

public class GenericDevice {

	private String make;
	private String model;
	private String os;
	private String version;
	private String deviceId;
	private String deviceName;
	private Double latitude;
	private Double longitude;
	private List<String> tags;
	
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDeviceId() {
		return deviceId.toLowerCase();
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId.toLowerCase();
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getDeviceName() {
		return deviceName.toLowerCase();
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName.toLowerCase();
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
