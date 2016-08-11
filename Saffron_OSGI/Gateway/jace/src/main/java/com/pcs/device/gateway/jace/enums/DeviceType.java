package com.pcs.device.gateway.jace.enums;


public enum DeviceType {

	JACE(0x01,"0x00","Device Put On Hold"),
	SUPERVISOR (0x02,"0x01","Device Authenticated");


	private Integer type;
	private String desc;
	private String indicator;
	private static final DeviceType[] values = values();

	DeviceType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	DeviceType(Integer type, String indicator, String desc) {
		this.type = type;
		this.desc = desc;
		this.indicator = indicator;
	}
	
	public static DeviceType valueOfType(Integer type) {
		if (type == null) {return null;}
		for (DeviceType g2021MsgType : values) {
			if (type.equals(g2021MsgType.getType()))
			{
				return g2021MsgType;
			}
		}
		return null;
	}
	
	public static DeviceType valueOfIndicator(String indicator) {
		if (indicator == null) {return null;}
		for (DeviceType g2021MsgType : values) {
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
