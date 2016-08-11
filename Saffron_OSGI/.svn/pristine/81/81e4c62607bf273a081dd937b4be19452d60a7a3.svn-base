package com.pcs.gateway.teltonika.message.packet.type;

public enum PacketType {
	
	IDENTIFIED_WITH_ASSURED_DELIVERY(0X00,"Assured delivery"),
	IDENTIFIED(0X01,"unassured delivery"),
	IDENTIFIED_ACK(0X02,"Assured delivery acknowledgement");
	
	private Integer type;
	private String desc;
	private static final PacketType[] values = values();

	PacketType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static PacketType valueOfType(Integer type) {
		if (type == null) {
			return null;
		}
		for (PacketType packetType : values) {
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
