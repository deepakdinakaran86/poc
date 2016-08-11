package com.pcs.saffron.g2021.SimulatorEngine.CS.message.header;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.exceptions.InvalidHeaderException;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.type.MessageType;
import com.pcs.saffron.g2021.SimulatorEngine.CS.packet.type.PacketType;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;

public class Header implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Header.class);

	private MessageType messageType;
	private PacketType packetType;
	private Integer seqNumber;
	private Integer sessionId;
	private Integer unitId;
	private Boolean reqAck = false;

	private Header(ByteBuf msgData) throws InvalidHeaderException{
		
		if(msgData != null && msgData.readableBytes() <3){
			throw new InvalidHeaderException("Invalid Message :: "+msgData);
		}else{
			msgData.readShort();
			packetType = PacketType.valueOfType((int)msgData.readUnsignedByte());
			messageType = MessageType.valueOfType((int)msgData.readUnsignedByte());
			
			if(packetType == PacketType.ANONYMOUS_WITH_ASSURED_DELIVERY || 
					packetType == PacketType.IDENTIFIED_WITH_ASSURED_DELIVERY || packetType == PacketType.IDENTIFIED){
				if( packetType != PacketType.IDENTIFIED){//not assured and hence not sequence number!
					seqNumber = (int) msgData.readUnsignedByte();
				}				
			}
		}
	}
	
	public Header(){
		
	}

	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	public PacketType getPacketType() {
		return packetType;
	}
	public void setPacketType(PacketType packetType) {
		this.packetType = packetType;
	}
	public Integer getSeqNumber() {
		return seqNumber;
	}
	public void setSeqNumber(Integer seqNumber) {
		this.seqNumber = seqNumber;
	}
	public Integer getSessionId() {
		return sessionId;
	}
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	public Integer getUnitId() {
		return unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Boolean getReqAck() {
		return reqAck;
	}

	public void setReqAck(Boolean reqAck) {
		this.reqAck = reqAck;
	}
	
	public static Header getInstance(ByteBuf msgData) throws Exception{
		if (msgData instanceof EmptyByteBuf) {
			return null;
		}
		return new Header(msgData);
	}

}
