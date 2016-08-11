package com.pcs.analytics.flinktests.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class DeviceSnapshot {

	
	private Long deviceTime;
	private String deviceId;
	private String sourceId;
	private List<Parameter> parameters;
	public Long getDeviceTime() {
		return deviceTime;
	}
	public void setDeviceTime(Long deviceTime) {
		this.deviceTime = deviceTime;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public List<Parameter> getParameters() {
		return parameters;
	}
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(Parameter parameter){
		if(CollectionUtils.isEmpty(this.parameters)){
			this.parameters = new ArrayList<Parameter>();
		}
		this.parameters.add(parameter);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deviceId == null) ? 0 : deviceId.hashCode());
		result = prime * result
				+ ((deviceTime == null) ? 0 : deviceTime.hashCode());
		result = prime * result
				+ ((sourceId == null) ? 0 : sourceId.hashCode());
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
		DeviceSnapshot other = (DeviceSnapshot) obj;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		if (deviceTime == null) {
			if (other.deviceTime != null)
				return false;
		} else if (!deviceTime.equals(other.deviceTime))
			return false;
		if (sourceId == null) {
			if (other.sourceId != null)
				return false;
		} else if (!sourceId.equals(other.sourceId))
			return false;
		return true;
	}
	
	
	
	
}
