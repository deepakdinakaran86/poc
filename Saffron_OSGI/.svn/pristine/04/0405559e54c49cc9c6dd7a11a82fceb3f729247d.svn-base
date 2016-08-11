package com.pcs.device.gateway.jace.message.type;

public enum DeviceStatus {

	SERVICE_INIT(0x00,"0x00","Service Intialization Request"),
	SERVICE_RESTART (0x01,"0x01","Service Restarted"),
	SERVICE_ACTIVE (0x02,"0x02","Service Active"),
	SERVICE_REJECTED (0x03,"0x03", "Service Rejected"),
	SERVICE_DISCONECTED(0x04,"0x04","Service Disconected");


	private Integer type;
	private String desc;
	private String indicator;
	private static final DeviceStatus[] values = values();

	DeviceStatus(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	DeviceStatus(Integer type, String indicator, String desc) {
		this.type = type;
		this.desc = desc;
		this.indicator = indicator;
	}
	
	public static DeviceStatus valueOfType(Integer type) {
		if (type == null) {return null;}
		for (DeviceStatus g2021MsgType : values) {
			if (type.equals(g2021MsgType.getType()))
			{
				return g2021MsgType;
			}
		}
		return null;
	}
	
	public static DeviceStatus valueOfIndicator(String indicator) {
		if (indicator == null) {return null;}
		for (DeviceStatus g2021MsgType : values) {
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
