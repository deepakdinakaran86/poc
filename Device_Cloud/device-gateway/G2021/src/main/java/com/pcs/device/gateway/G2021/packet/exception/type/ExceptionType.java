package com.pcs.device.gateway.G2021.packet.exception.type;

public enum ExceptionType {
	
	INCOMPATIBLE_VERSION(0xE1,"Server doesn't support version specified by registration"),
	BUSY(0xE2,"Server is busy"),
	INVALID_SUBSCRIPTION_KEY(0xE3,"Client does not support digest algorithm in authorization"),
	NOT_AUTHENTICATED(0xE4,"Client supplied invalid credentials"),
	SUBSCRIPTION_FAILED(0xE5,"Device subscription or registration failed"),
	POINT_DISCOVERY_FAILED(0xE6,"Point Discovery Failed"),
	POINT_DISCOVERY_DISABLED(0xE7,"Point Discovery Disabled (in the device)"),
	INVALID_SESSION_ID(0xE8,"Invalid Session ID"),
	MANUAL_SESSION_TERMINATIION(0xE9,"Close the session and terminate the communication. This could be initiated from both the ends.");
	
	private Integer type;
	private String desc;
	private static final ExceptionType[] values = values();

	ExceptionType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static ExceptionType valueOfType(Integer type) {
		if (type == null) {
			return null;
		}
		for (ExceptionType packetType : values) {
			if (type.equals(packetType.getType())) {
				return packetType;
			}
		}

		return null;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
