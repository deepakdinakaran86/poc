package com.pcs.device.gateway.G2021.packet.type;

public enum PacketType {
	
	PING(0x00,"Ping Request"),
	PING_RESPONSE(0X01,"Ping Response"),
	ANONYMOUS(0X02,"Packet without {device ID, Session ID} or assured delivery"),
	ANONYMOUS_WITH_ASSURED_DELIVERY(0X03,"Packet without {device ID, Session ID}, but with assured delivery"),
	ANONYMOUS_ACK(0X04,"Assured delivery acknowledgement without {device ID, Session ID}"),
	IDENTIFIED(0X05,"Packet with {device ID, Session ID} but no assured delivery"),
	IDENTIFIED_WITH_ASSURED_DELIVERY(0X06,"Packet with {device ID, Session ID} and assured delivery"),
	IDENTIFIED_ACK(0X07,"Assured delivery acknowledgement with {device ID, Session ID}"),
	END_OF_PACKET(0xAF,"End of stream");
	
	private Integer type;
	private String desc;
	private static final PacketType[] values = values();

	PacketType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	/*public static PacketType valueOfType(Integer type) {
		if (type == null) {
			return null;
		}
		for (PacketType packetType : values) {
			if (type.equals(packetType.getType())) {
				return packetType;
			}
		}

		return null;
	}*/

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
