/**
 * 
 */
package com.pcs.datasource.dto;

import java.io.Serializable;
import java.util.List;

import com.pcs.datasource.model.udt.DeviceFieldMap;

/**
 * This class is responsible for device data(for mapping from cassandra db)
 * 
 * @author pcseg129(Seena Jyothish) May 21, 2016
 */
public class TimeSeries implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6788380099690420799L;
	String device;
	String date;
	String displayName;
	long deviceTime;
	long insertTime;
	Object data;
	private List<DeviceFieldMap> extensions;

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

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

	public List<DeviceFieldMap> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<DeviceFieldMap> extensions) {
		this.extensions = extensions;
	}

}
