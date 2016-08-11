package com.pcs.saffron.deviceutil.bean;

import java.util.List;

public class PointHistoryBean {

	
	private long deviceTime;
	private long insertTime;
	private Object data;
	private List<Extension> extensions;
	private String systemTag;
	public long getDeviceTime() {
		return deviceTime;
	}
	public void setDeviceTime(long deviceTime) {
		this.deviceTime = deviceTime;
	}
	public long getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
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
	public String getSystemTag() {
		return systemTag;
	}
	public void setSystemTag(String systemTag) {
		this.systemTag = systemTag;
	}
}
