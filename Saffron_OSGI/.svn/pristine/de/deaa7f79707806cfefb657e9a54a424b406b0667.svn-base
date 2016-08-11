package com.pcs.device.gateway.jace.message.type;

public enum MessageType {

	CONNECTION_REQ (0x01,"0x01","Connection Request"),
	CONNECTION_RESP (0x02,"0x02","Connection Response"),
	KEEP_ALIVE (0x03,"0x03", "Keep Alive"),
	KEEP_ALIVE_ACK(0x04,"0x04","Keep Alive Ack"),
	CONFIG_UPDATE_REQ(0x05,"0x05","Config Update Request"),
	CONFIG_UPDATE_RESP (0x06,"0x06","Config Update Response"),
	CONFIG_FEED_UPDATE (0x07,"0x07","Config Feed Update"),
	CONFIG_FEED_ACK (0x08,"0x08","Config Feed Ack"),
	ALARM (0x09,"0x09","Alarm"),
	ALARM_ACK(0x0A,"0x0A","Alarm Ack"),
	DATA_FEED_REQ (0x0B,"0x0B", "Data Feed Req"),
	DATA_FEED_ACK (0x0C,"0x0C","Data Feed Ack");


	private Integer type;
	private String desc;
	private String indicator;
	private static final MessageType[] values = values();

	MessageType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	MessageType(Integer type, String indicator, String desc) {
		this.type = type;
		this.desc = desc;
		this.indicator = indicator;
	}
	
	public static MessageType valueOfType(Integer type) {
		if (type == null) {return null;}
		for (MessageType g2021MsgType : values) {
			if (type.equals(g2021MsgType.getType()))
			{
				return g2021MsgType;
			}
		}
		return null;
	}
	
	public static MessageType valueOfIndicator(String indicator) {
		if (indicator == null) {return null;}
		for (MessageType g2021MsgType : values) {
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
