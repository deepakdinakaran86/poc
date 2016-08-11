package com.pcs.device.gateway.hobbies.message;

public class HobbiesRegistrationResponse {

	private String messageType;
	private String status;
	private String deviceTopic;
	private String userName;
	private String password;
	private String brokerURL;
	
	public String getMessageType() {
		return messageType;
	}
	public String getStatus() {
		return status;
	}
	public String getDeviceTopic() {
		return deviceTopic;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getBrokerURL() {
		return brokerURL;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setDeviceTopic(String deviceTopic) {
		this.deviceTopic = deviceTopic;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setBrokerURL(String brokerURL) {
		this.brokerURL = brokerURL;
	}
}
