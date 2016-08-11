package com.pcs.device.gateway.jace.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;

import com.pcs.device.gateway.jace.exception.InvalidHeaderException;
import com.pcs.device.gateway.jace.message.type.MessageType;

public class Header {

	private static final Logger LOGGER = LoggerFactory.getLogger(Header.class);
	
	private MessageType type;
	private Integer unitId;
	private Boolean requireAck = true;
	
	
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Boolean getRequireAck() {
		return requireAck;
	}

	public void setRequireAck(Boolean requireAck) {
		this.requireAck = requireAck;
	}

	private Header(ByteBuf msgData) throws InvalidHeaderException{

		if(msgData != null && msgData.readableBytes() <1){
			throw new InvalidHeaderException("Invalid Message :: "+msgData);
		}else{
			int messageType = (int)msgData.readByte();
			LOGGER.info("Message Type Byte {}",messageType);
			type = MessageType.valueOfType(messageType);
			LOGGER.info("Message Type is {}",type);
		}
	}

	public static Header getInstance(ByteBuf msgData) throws Exception{
		if (msgData instanceof EmptyByteBuf) {
			return null;
		}
		return new Header(msgData);
	}
}
