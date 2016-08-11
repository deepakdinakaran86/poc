package com.pcs.device.gateway.ruptela.message.type;


public enum DeviceMessageType {

	


	DATA (0x01,"0x01","Device data record"),
	CONFIGURATIONRESPONSE (0x02,"0x02","Response for configuration update"),
	DEVICEVERSIONRESPONSE (0x03,"0x03", "Response for device version"),
	DEVICEFIRMWAREUPDATERESPONSE(0x04,"0x04","Response for firmware update"),
	SMARTCARDDATA(0x05,"0x05","Smart card data, fragments of DDD file"),
	SMARTCARDDATASIZEANDTIME (0x06,"0x06","Size and datetime of creation of smart card"),
	DIAGNOSTICTROUBLECODES (0x09,"0x09","Vehicle diagnostic trouble codes"),
	TACHOGRAPHCOMMUNICATION (0x0A,"0x0A","Tachograph data"),
	TACHOGRAPHDATAPACKET (0x0B,"0x0B","Tachograph data packet for the DDD file"),
	TACHOGRAPHINFORMATION(0x0C,"0x0C","Information for tachograph data"),
	GARMINSTATUSRESPONSE (0x1E,"0x1E", "Garmin device status"),
	GARMINDATARESPONSE (0x1F,"0x1F","Garmin device data");


	private Integer type;
	private String desc;
	private String indicator;
	private static final DeviceMessageType[] values = values();

	DeviceMessageType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	DeviceMessageType(Integer type, String indicator, String desc) {
		this.type = type;
		this.desc = desc;
		this.indicator = indicator;
	}
	
	public static DeviceMessageType valueOfType(Integer type) {
		if (type == null) {return null;}
		for (DeviceMessageType g2021MsgType : values) {
			if (type.equals(g2021MsgType.getType()))
			{
				return g2021MsgType;
			}
		}
		return null;
	}
	
	public static DeviceMessageType valueOfIndicator(String indicator) {
		if (indicator == null) {return null;}
		for (DeviceMessageType g2021MsgType : values) {
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
