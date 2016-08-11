package com.pcs.device.gateway.jace.enums;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public enum MessageElements {

	CARRIER_RETURN(0x0D,"0x0D","Carrier Return");
	
	public static final ByteBuf CARRIERRETURN = Unpooled.wrappedBuffer(new byte[]{0x0D});
	
	private Integer type;
	private String desc;
	private String indicator;
	private static final MessageElements[] values = values();

	MessageElements(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	MessageElements(Integer type, String indicator, String desc) {
		this.type = type;
		this.desc = desc;
		this.indicator = indicator;
	}
	
	public static MessageElements valueOfType(Integer type) {
		if (type == null) {return null;}
		for (MessageElements g2021MsgType : values) {
			if (type.equals(g2021MsgType.getType()))
			{
				return g2021MsgType;
			}
		}
		return null;
	}
	
	public static MessageElements valueOfIndicator(String indicator) {
		if (indicator == null) {return null;}
		for (MessageElements g2021MsgType : values) {
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
	
	public static void main(String[] args) {
		System.err.println(CARRIER_RETURN.getType());
	}
}
