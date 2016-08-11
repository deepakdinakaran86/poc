package com.pcs.device.gateway.G2021.message.type;

public enum MessageType {

	DISCOVER (0x00,"0x00","Sent by server during device discovery/identification process"),
	HELLO (0x01,"0x01","Sent by client i.e. edge device to initiate handshake"),
	CHALLENGE (0x02,"0x02", "Response to client's registration message"),
	AUTHENTICATE(0x03,"0x03","Sent by client to provide authentication credentials"),
	WELCOME(0x04,"0x04","Response to client's authenticate message if successful"),
	POINTDISCOVERYREQUEST (0x05,"0x05","Point Discovery Request"),
	POINTDISCOVERYRESPONSE (0x06,"0x06","Point Discovery Response"),
	POINTDISCOVERYACK (0x07,"0x07","Point Discovery ACK from the server"),
	POINTDISCOVERYSCORECARD (0x08,"0x08","Point Discovery Score Card"),
	EXCEPTIONMESSAGE(0x0F,"0x0F","Communication exception message."),
	SUPERVISION (0x10,"0x10", "Device Supervision for its status update"),
	POINTREALTIMEDATA (0x11,"0x11","Point real time data"),
	POINTALARM (0x12,"0x12","Point Alarm Report"),
	//CLOSE(0x14, "Close Session"),
	POINTSNAPSHOTUPLOAD (0x13,"0x13","Point Snapshot (Instant data) upload"),
	POINTWRITERESPONSE(0x14,"0x14","Device response for the point write command received"),
	LOCATION(0X15,"0X15","Location"), 
	SERVERCOMMAND(0X20,"0X20","Server Command"),
	POINTINSTANTSNAPSHOTREQUEST(0x21,"0x21","Point instant snapshot request"),
	POINTWRITECOMMAND(0x22,"0x22","Point write command");


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
