package com.pcs.device.gateway.hobbies.message.type;


public enum HobbiesMessageType {
	RESGISTRATION (0x00,"REGISTRATION","Sent by hobbies devices for registration"),
	REGISTRATION_RESPONSE(0x02,"REGISTRATION_RESPONSE","Sent as response for hobbies device registration"),
	DATA (0x01,"DATA","Sent by hobbies devices for updating data");
	
	private Integer type;
	private String desc;
	private String indicator;
	private static final HobbiesMessageType[] values = values();

	HobbiesMessageType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	HobbiesMessageType(Integer type, String indicator, String desc) {
		this.type = type;
		this.desc = desc;
		this.indicator = indicator;
	}
	
	public static HobbiesMessageType valueOfType(Integer type) {
		if (type == null) {return null;}
		for (HobbiesMessageType g2021MsgType : values) {
			if (type.equals(g2021MsgType.getType()))
			{
				return g2021MsgType;
			}
		}
		return null;
	}
	
	public static HobbiesMessageType valueOfIndicator(String indicator) {
		if (indicator == null) {return null;}
		for (HobbiesMessageType g2021MsgType : values) {
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
