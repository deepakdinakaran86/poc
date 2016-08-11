package com.pcs.analytics.flinktests.kafka.source.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class DataIngestionBean implements Serializable,Comparable<DataIngestionBean>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 426692736913580355L;
	private String deviceId;
	private String sourceId;
	private long deviceTime;
	private long deviceDate;
	private long insertTime;
	private String displayName;
	private Object data;
	private String datastore;
	private List<PointExtension> extensions;
	private String dataType;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private Boolean isLatest;
	private String systemTag;
	private String unit;
	
	
	public String getDeviceId() {
		return deviceId;
	}
	public long getDeviceTime() {
		return deviceTime;
	}
	public long getDeviceDate() {
		return deviceDate;
	}
	public long getInsertTime() {
		return insertTime;
	}
	public String getDisplayName() {
		return displayName;
	}
	public Object getData() {
		return data;
	}
	public String getDatastore() {
		return datastore;
	}
	public List<PointExtension> getExtensions() {
		return extensions;
	}
	public String getDataType() {
		return dataType;
	}
	public Boolean getIsLatest() {
		return isLatest;
	}
	public String getSystemTag() {
		return systemTag;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public void setDeviceTime(long deviceTime) {
		this.deviceTime = deviceTime;
	}
	public void setDeviceDate(long deviceDate) {
		this.deviceDate = deviceDate;
	}
	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void setDatastore(String datastore) {
		this.datastore = datastore;
	}
	public void setExtensions(List<PointExtension> extensions) {
		this.extensions = extensions;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public void setIsLatest(Boolean isLatest) {
		this.isLatest = isLatest;
	}
	public void setSystemTag(String systemTag) {
		this.systemTag = systemTag;
	}
	
	public void addExtension(PointExtension extension){
		if(CollectionUtils.isEmpty(this.extensions)){
			this.extensions = new ArrayList<PointExtension>();
		}
		this.extensions.add(extension);		
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
	@Override
	public int compareTo(DataIngestionBean obj) {
		if(obj.getDeviceTime()>obj.getDeviceTime()){
			return 1;
		}
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataIngestionBean other = (DataIngestionBean) obj;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		
		return true;
	}
	
	
}
