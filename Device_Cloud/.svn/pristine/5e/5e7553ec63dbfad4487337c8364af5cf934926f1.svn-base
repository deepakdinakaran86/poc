package com.pcs.device.gateway.teltonika.message.header;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.teltonika.exception.InvalidHeaderException;
import com.pcs.device.gateway.teltonika.message.packet.type.PacketType;


public class ChannelHeader {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelHeader.class);

	private PacketType packetType;
	private Integer packetLength;
	private Integer packetId;
	private Boolean reqAck = false;

	private ChannelHeader(ByteBuf msgData) throws InvalidHeaderException{
		
		if(msgData != null && msgData.readableBytes() < 5){
			throw new InvalidHeaderException("Invalid Message :: "+msgData);
		}else{
			packetLength = msgData.readUnsignedShort();
			packetId = msgData.readUnsignedShort();
			packetType = PacketType.valueOfType((int)msgData.readByte());
			switch (packetType) {
			case IDENTIFIED_WITH_ASSURED_DELIVERY:
				this.reqAck = true;
				break;
			
			case IDENTIFIED:
				this.reqAck = false;
				break;
				
			default:
				throw new InvalidHeaderException("Invalid Message :: "+msgData);
			}
			LOGGER.info("Channel Header Identified");
		}
	}

	public PacketType getPacketType() {
		return packetType;
	}
	
	public void setPacketType(PacketType packetType) {
		this.packetType = packetType;
	}

	public Boolean getReqAck() {
		return reqAck;
	}

	public void setReqAck(Boolean reqAck) {
		this.reqAck = reqAck;
	}
	
	public Integer getPacketLength() {
		return packetLength;
	}

	public void setPacketLength(Integer packetLength) {
		this.packetLength = packetLength;
	}

	public Integer getPacketId() {
		return packetId;
	}

	public void setPacketId(Integer packetId) {
		this.packetId = packetId;
	}

	public static ChannelHeader getInstance(ByteBuf msgData) throws Exception{
		if (msgData instanceof EmptyByteBuf) {
			return null;
		}
		return new ChannelHeader(msgData);
	}

}
