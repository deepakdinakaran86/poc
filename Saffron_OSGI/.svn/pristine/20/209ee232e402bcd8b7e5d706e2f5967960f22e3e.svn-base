package com.pcs.device.gateway.ruptela.message.header;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.ruptela.exception.InvalidHeaderException;
import com.pcs.device.gateway.ruptela.message.type.DeviceMessageType;


public class DeviceMessageHeader {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceMessageHeader.class);
	
	private String imei;
	private DeviceMessageType messageType;
	private Integer recordsLeft;
	private Integer numberOfRecords;
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public DeviceMessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(DeviceMessageType messageType) {
		this.messageType = messageType;
	}
	public Integer getRecordsLeft() {
		return recordsLeft;
	}
	public void setRecordsLeft(Integer recordsLeft) {
		this.recordsLeft = recordsLeft;
	}
	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}
	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}
	
	private DeviceMessageHeader(ByteBuf msgData) throws InvalidHeaderException{
		try {
			imei = Long.toString(msgData.readLong());
			messageType = DeviceMessageType.valueOfType((int)msgData.readByte());
			recordsLeft = (int)msgData.readByte();
			numberOfRecords = (int)msgData.readByte();
		} catch (Exception e) {
			LOGGER.error("Exception extracting header",e);
			throw new InvalidHeaderException(e);
		}
		
	}
	
	
	public static DeviceMessageHeader getInstance(ByteBuf msgData) throws Exception{
		if (msgData instanceof EmptyByteBuf) {
			LOGGER.error("Empty Buffer");
			return null;
		}
		return new DeviceMessageHeader(msgData);
	}
	
	
	
}
