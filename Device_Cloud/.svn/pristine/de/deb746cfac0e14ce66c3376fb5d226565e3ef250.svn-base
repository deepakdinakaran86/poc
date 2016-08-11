package com.pcs.data.store.bean;

import java.util.List;

public class PersistData {

	private String deviceId;
	private Long deviceTime;
	private Long deviceDate;
	private Long insertTime;
	private String displayName;
	private Object data;
	private String datastore;
	private String systemTag;
	private String dataType;
	private List<PersistDataExtension> extensions;
	
	
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Long getDeviceTime() {
		return deviceTime;
	}
	public void setDeviceTime(Long deviceTime) {
		this.deviceTime = deviceTime;
	}
	public Long getDeviceDate() {
		return deviceDate;
	}
	public void setDeviceDate(Long deviceDate) {
		this.deviceDate = deviceDate;
	}
	public Long getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Long insertTime) {
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDatastore() {
		return datastore;
	}
	public void setDatastore(String datastore) {
		this.datastore = datastore;
	}
	
	public String getSystemTag() {
		return systemTag;
	}
	public void setSystemTag(String systemTag) {
		this.systemTag = systemTag;
	}
	public List<PersistDataExtension> getExtensions() {
		return extensions;
	}
	public void setExtensions(List<PersistDataExtension> extensions) {
		this.extensions = extensions;
	}
	
	public Object getExtensionAsUDT(){
		if(extensions==null)
			return null;
		
		StringBuffer UDTBuffer = new StringBuffer();
		UDTBuffer.append("[");
		for (PersistDataExtension extension : extensions) {
			UDTBuffer.append("{");
			UDTBuffer.append("key: '").append(extension.getExtensionType()).append("',");
			UDTBuffer.append("value: '").append(extension.getExtensionName()).append("'");
			UDTBuffer.append("},");
		}
		UDTBuffer.deleteCharAt(UDTBuffer.length()-1);
		UDTBuffer.append("]");
		return UDTBuffer.toString();
	}
	
	
}
