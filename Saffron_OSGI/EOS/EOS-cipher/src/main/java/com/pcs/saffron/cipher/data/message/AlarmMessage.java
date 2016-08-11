package com.pcs.saffron.cipher.data.message;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.util.CollectionUtils;

import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.extension.PointExtension;

public final class AlarmMessage extends Message implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4132904739070750221L;
	
	private String alarmName;
	private Boolean status;
	private String alarmMessage;
	private String alarmType;
	private String displayName;
	private String data;
	private String type;
	private String unit;
	
	public String getAlarmName() {
		return alarmName;
	}
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {

		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("{\"sourceId\":\"").append(getSourceId()).append("\",");//"sourceId":"231312312"
		messageBuilder.append("\"time\":\"").append(getTime()).append("\",");
		messageBuilder.append("\"receivedTime\":\"").append(getReceivedTime()!=null?getReceivedTime().getTime():Calendar.getInstance().getTimeInMillis()).append("\",");
		messageBuilder.append("\"alarmMessage\":\"").append(getAlarmMessage()).append("\",");
		messageBuilder.append("\"alarmName\":\"").append(getAlarmName()).append("\",");
		messageBuilder.append("\"alarmType\":\"").append(getAlarmName()).append("\",");
		messageBuilder.append("\"status\":\"").append(getStatus()).append("\",");
		if(getPoints() != null && !getPoints().isEmpty()){
			messageBuilder.append("\"displayName\":\"").append(getPoints().get(0).getDisplayName()).append("\",");
			messageBuilder.append("\"data\":\"").append(getPoints().get(0).getData()).append("\",");
			//messageBuilder.append("\"type\":\"").append(getPoints().get(0).getType()).append("\",");
			messageBuilder.append("\"unit\":\"").append(getPoints().get(0).getUnit()).append("\",");
		}
		
		messageBuilder.append("\"points\":[");//points information
		for (Point point : getPoints()) {
			messageBuilder.append("{\"pointId\":\"").append(point.getPointId()).append("\",");
			messageBuilder.append("\"pointName\":\"").append(point.getPointName()).append("\",");
			messageBuilder.append("\"displayName\":\"").append(point.getDisplayName()).append("\",");
			messageBuilder.append("\"unit\":\"").append(point.getUnit()).append("\",");
			messageBuilder.append("\"data\":\"").append(point.getData()).append("\",");
			messageBuilder.append("\"status\":\"").append(point.getStatus()).append("\",");
			messageBuilder.append("\"type\":\"").append(point.getType()).append("\",");
			
			messageBuilder.append("\"extensions\":").append("[");
			if(!CollectionUtils.isEmpty(point.getExtensions())){
				for (PointExtension extension : point.getExtensions()) {
					messageBuilder.append("{\"extensionName\":\"").append(extension.getExtensionName()).append("\",");
					messageBuilder.append("\"extensionType\":\"").append(extension.getExtensionType()).append("\"},");
				}
				messageBuilder.deleteCharAt(messageBuilder.length()-1);
			}
			messageBuilder.append("]").append("},");
		}
		messageBuilder.deleteCharAt(messageBuilder.length()-1);
		messageBuilder.append("]").append("}");//points information completion
		return messageBuilder.toString();
	
	}
}
