package com.stratio.ingestion.sink.cassandra.bean;

import java.util.List;

public class IngestionDetails {
	
	private String datastore;
	private String sourceId;
	private String deviceId;
	private long deviceTime;
	private long deviceDate;
	private long insertTime;
	private String displayName;
	private Object data;
	private List<Extension>extensions;
	private String dataType;
	private Boolean isLatest;
	private String systemTag;
	private String unit;
	
	
	public String getDatastore() {
		return datastore;
	}
	public void setDatastore(String datastore) {
		this.datastore = datastore;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public long getDeviceTime() {
		return deviceTime;
	}
	public void setDeviceTime(long deviceTime) {
		this.deviceTime = deviceTime;
	}
	public long getDeviceDate() {
		return deviceDate;
	}
	public void setDeviceDate(long deviceDate) {
		this.deviceDate = deviceDate;
	}
	public long getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public List<Extension> getExtensions() {
		return extensions;
	}
	public void setExtensions(List<Extension> extensions) {
		this.extensions = extensions;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getSystemTag() {
		return systemTag;
	}
	public void setSystemTag(String systemTag) {
		this.systemTag = systemTag;
	}
	public Boolean getIsLatest() {
		return isLatest;
	}
	public void setIsLatest(Boolean isLatest) {
		this.isLatest = isLatest;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

}
