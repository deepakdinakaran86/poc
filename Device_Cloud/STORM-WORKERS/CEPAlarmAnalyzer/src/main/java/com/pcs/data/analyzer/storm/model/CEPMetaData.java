package com.pcs.data.analyzer.storm.model;

import java.io.Serializable;

public class CEPMetaData implements Serializable {
	
	private String alarmName;
	
	private String alarmMessage;
	
	private String alarmType;
	
	private String data;
	
	private String sourceId;
	
	private long time;
	
	private long receivedTime;
	
	private String deviceId;
	
	private String unit;
	
	private String pointId;
	
	private String pointName;
	
	private boolean active;
	
	public CEPMetaData() {
		super();
	}
	

	public CEPMetaData(String alarmName, String alarmMessage, String alarmType,
			String data, String sourceId, long time, long receivedTime,
			String deviceId, String unit, String pointId, String pointName,
			boolean active) {
		super();
		this.alarmName = alarmName;
		this.alarmMessage = alarmMessage;
		this.alarmType = alarmType;
		this.data = data;
		this.sourceId = sourceId;
		this.time = time;
		this.receivedTime = receivedTime;
		this.deviceId = deviceId;
		this.unit = unit;
		this.pointId = pointId;
		this.pointName = pointName;
		this.active = active;
	}



	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public String getAlarmMessage() {
		return alarmMessage;
	}

	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getReceivedTime() {
		return receivedTime;
	}


	public void setReceivedTime(long receivedTime) {
		this.receivedTime = receivedTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
	

}
