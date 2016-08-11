package com.pcs.device.gateway.ruptela.message.type;

public enum ServerMessageType {



	


	DATARESPONSE (0x64,"0x64","Device data record"),
	CONFIGURATION (0x66,"0x66","Message for configuration update"),
	DEVICEVERSION (0x67,"0x67", "Device version"),
	DEVICEFIRMWAREUPDATE(0x68,"0x68","Firmware update"),
	SMARTCARDDATARESPONSE(0x6B,"0x6B","Smart card data, fragments of DDD file (Response)"),
	SMARTCARDDATASIZEANDTIMERESPONSE (0x6B,"0x6B","Size and datetime of creation of smart card (Response)"),
	DIAGNOSTICTROUBLECODESRESPONSE (0x6D,"0x6D","Vehicle diagnostic trouble codes (Response)"),
	TACHOGRAPHCOMMUNICATIONRESPONSE (0x6E,"0x6E","Tachograph data response"),
	TACHOGRAPHDATAPACKETRESPONSE (0x6F,"0x6F","Tachograph data packet for the DDD file"),
	TACHOGRAPHINFORMATIONRESPONSE(0x6F,"0x6F","Information for tachograph data"),
	GARMINSTATUS (0x82,"0x82", "Garmin device status"),
	GARMINDATA (0x83,"0x82","Garmin device data");


	private Integer type;
	private String desc;
	private String indicator;
	private static final ServerMessageType[] values = values();

	ServerMessageType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	ServerMessageType(Integer type, String indicator, String desc) {
		this.type = type;
		this.desc = desc;
		this.indicator = indicator;
	}
	
	public static ServerMessageType valueOfType(Integer type) {
		if (type == null) {return null;}
		for (ServerMessageType g2021MsgType : values) {
			if (type.equals(g2021MsgType.getType()))
			{
				return g2021MsgType;
			}
		}
		return null;
	}
	
	public static ServerMessageType valueOfIndicator(String indicator) {
		if (indicator == null) {return null;}
		for (ServerMessageType g2021MsgType : values) {
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
