package com.pcs.saffron.manager.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.pcs.saffron.cipher.data.point.extension.PointExtension;

public class DataIngestionBean implements Serializable{

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
	
	
}
