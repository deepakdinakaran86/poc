package com.pcs.gateway.teltonika.message.header;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.gateway.teltonika.constant.ProtocolConstants;
import com.pcs.gateway.teltonika.exception.InvalidHeaderException;
import com.pcs.gateway.teltonika.message.packet.type.PacketType;
import com.pcs.saffron.cipher.exception.InvalidVersion;
import com.pcs.saffron.cipher.exception.MessageDecodeException;
import com.pcs.saffron.connectivity.utils.ConversionUtils;


public class PacketHeader {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PacketHeader.class);

	private PacketType packetType = PacketType.IDENTIFIED_WITH_ASSURED_DELIVERY;
	private Integer sessionId;
	private String sourceId;
	private Integer packetCount;
	private Integer packetId;
	private Integer dataLength;
	private String data;
	private Boolean reqAck = true;

	private PacketHeader(ByteBuf msgData) throws InvalidHeaderException, MessageDecodeException{
		
		if(msgData != null && msgData.readableBytes() < 3){
			throw new InvalidHeaderException("Invalid Packet Header :: "+msgData);
		}else{
			packetId = (int) msgData.readByte();
			Short unitIdLength = msgData.readShort();
			sourceId = new String(msgData.readBytes(unitIdLength).array());
			dataLength = msgData.readableBytes();
			data = ConversionUtils.getHex(msgData.readBytes(dataLength).array()).toString();
			getDataPacketCount();
		}
	}

	public PacketType getPacketType() {
		return packetType;
	}
	
	public void setPacketType(PacketType packetType) {
		this.packetType = packetType;
	}
	
	public Integer getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(String unitId) {
		this.sourceId = unitId;
	}

	public Boolean getReqAck() {
		return reqAck;
	}

	public void setReqAck(Boolean reqAck) {
		this.reqAck = reqAck;
	}
	
	public Integer getPacketCount() {
		return packetCount;
	}

	public void setPacketCount(Integer packetCount) {
		this.packetCount = packetCount;
	}

	public Integer getPacketId() {
		return packetId;
	}

	public void setPacketId(Integer packetId) {
		this.packetId = packetId;
	}

	public Integer getDataLength() {
		return dataLength;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public static PacketHeader getInstance(ByteBuf msgData) throws Exception{
		if (msgData instanceof EmptyByteBuf) {
			return null;
		}
		return new PacketHeader(msgData);
	}
	
	
	private int getDataPacketCount() throws MessageDecodeException {
		int start = 0;
		if (data.length() > 2) {
			String codec = data.substring(start, start += 2);// one byte
			if (!codec.equals(ProtocolConstants.PROTOCOL_VERSION_08) && !codec.equals(ProtocolConstants.PROTOCOL_PING)) {// for FM4XXX and FM2100 codecId=08
				throw new InvalidVersion("Not Valid Data Package " + sourceId + " " + codec);
			}
			try {
				String noOfData = data.substring(start, start += 2);// no of data packets
				Long packets = Long.parseLong(ConversionUtils.convertToLong(noOfData).toString());
				packetCount = packets.intValue();
				LOGGER.info("{} new packets received from device {}",packetCount,sourceId);
				return packetCount;
			} catch (Exception ex) {
				throw new MessageDecodeException("Exception In Getting Packet Count: ", ex);
			}
		} else {
			return 0;
		}

	}

}
