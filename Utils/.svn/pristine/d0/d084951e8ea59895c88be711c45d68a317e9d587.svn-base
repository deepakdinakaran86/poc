package com.stratio.ingestion.sink.cassandra.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IngestionLatestData {

	private String deviceId;
	private Object data;
	private String systemTag;
	private Long deviceTime;
	private String displayName;
	private Long deviceDate;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getSystemTag() {
		return systemTag;
	}
	public void setSystemTag(String systemTag) {
		this.systemTag = systemTag;
	}
	public Long getDeviceTime() {
		return deviceTime;
	}
	public void setDeviceTime(Long deviceTime) {
		this.deviceTime = deviceTime;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Long getDeviceDate() {
		return deviceDate;
	}
	public void setDeviceDate(Long deviceDate) {
		this.deviceDate = deviceDate;
	}
	@JsonProperty("device")
	public String getDevice() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
}
