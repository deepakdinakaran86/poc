package com.pcs.device.gateway.jace.message.type;

public enum DeviceAuthenticationStatus {

	ONHOLD(0x00,"0x00","Device Put On Hold"),
	ACCEPTED (0x01,"0x01","Device Authenticated"),
	REJECTED (0x02,"0x02","Device Rejected");


	private Integer type;
	private String desc;
	private String indicator;
	private static final DeviceAuthenticationStatus[] values = values();

	DeviceAuthenticationStatus(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	DeviceAuthenticationStatus(Integer type, String indicator, String desc) {
		this.type = type;
		this.desc = desc;
		this.indicator = indicator;
	}
	
	public static DeviceAuthenticationStatus valueOfType(Integer type) {
		if (type == null) {return null;}
		for (DeviceAuthenticationStatus g2021MsgType : values) {
			if (type.equals(g2021MsgType.getType()))
			{
				return g2021MsgType;
			}
		}
		return null;
	}
	
	public static DeviceAuthenticationStatus valueOfIndicator(String indicator) {
		if (indicator == null) {return null;}
		for (DeviceAuthenticationStatus g2021MsgType : values) {
			if (indicator.equalsIgnoreCase(g2021MsgType.getIndicator()))
			{
				return g2021MsgType;
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

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
