package com.pcs.saffron.manager.authentication.bean;

public enum DeviceStatus {

	SUBSCRIBED("subscribed"), UNSUBSCRIBED("unsubscribed"), NOT_AVAIALABLE("Not Available");

	private String status;

	DeviceStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
