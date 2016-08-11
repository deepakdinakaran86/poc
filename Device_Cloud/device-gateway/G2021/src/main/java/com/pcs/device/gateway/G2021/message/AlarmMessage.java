/**
 * 
 */
package com.pcs.device.gateway.G2021.message;

/**
 * @author pcseg171
 * 
 */
public class AlarmMessage extends G2021Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8715173003406990426L;

	private Boolean status;
	private String statusMessage;
	private String alarmType;
	private Object data;
	private String customTag;
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	
	public String getCustomTag() {
		return customTag;
	}

	public void setCustomTag(String customTag) {
		this.customTag = customTag;
	}

	@Override
	public String toString() {
		StringBuffer jsonData = new StringBuffer();
		jsonData.append("{\"sourceId\":\"").append(getSourceId()).append("\",");
		jsonData.append("\"time\":\"").append(getTime()).append("\",");
		jsonData.append("\"alarmType\":\"").append(alarmType).append("\",");
		jsonData.append("\"status\":\"").append(status).append("\",");
		jsonData.append("\"message\":\"").append(statusMessage).append("\",");
		jsonData.append("\"data\":\"").append(data).append("\",");
		jsonData.append("\"customTag\":\"").append(customTag).append("\"}");
		return jsonData.toString();
	}
}
