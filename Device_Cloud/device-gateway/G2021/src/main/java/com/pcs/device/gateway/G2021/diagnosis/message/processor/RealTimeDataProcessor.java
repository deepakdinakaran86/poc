package com.pcs.device.gateway.G2021.diagnosis.message.processor;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.message.extension.point.status.type.StatusType;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.point.type.G2021DataTypes;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;

public class RealTimeDataProcessor extends G2021Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RealTimeDataProcessor.class);
	
	private G2021DeviceConfiguration configuration;
	
	public RealTimeDataProcessor(G2021DeviceConfiguration configuration) throws Exception{
		if(configuration == null){
			throw new NoSuchMethodException("Configuration cannot be null !!");
		}
		this.configuration = configuration;
	}

	@Override
	public Message processG2021Message(Object G2021Data) throws Exception {


		ByteBuf realTimeDataBuf = (ByteBuf) G2021Data;
		Integer recordCount = (int) realTimeDataBuf.readUnsignedByte();
		
		Message message = new Message();
		
		for (int i = 0; i < recordCount; i++) {
			Point point = null;
			try {
				Integer pid = realTimeDataBuf.readUnsignedShort();
				point = configuration.getPoint(pid.toString());
				if(point == null){
					throw new Exception("Invalid point id : "+pid);
				}
				Integer snapshotTime = realTimeDataBuf.readInt();
				message.setTime(snapshotTime*1000l);
				int flag = realTimeDataBuf.readUnsignedByte();
				String binaryRepresentation = ConversionUtils.convertToBinaryExt(Integer.toHexString(flag),8).toString();
				String dataType = binaryRepresentation.substring(0,4);
				
				switch (G2021DataTypes.valueOfType(dataType)) {

				case BOOLEAN:
					point.setData(realTimeDataBuf.readUnsignedByte() ==0 ? false:true);
					message.addPoint(point);
					break;

				case SHORT:
					point.setData(realTimeDataBuf.readShort());
					message.addPoint(point);
					break;

				case INT:
					point.setData(realTimeDataBuf.readInt());
					message.addPoint(point);
					break;

				case LONG:
					point.setData(realTimeDataBuf.readLong());
					message.addPoint(point);
					break;

				case FLOAT:
					point.setData(realTimeDataBuf.readFloat());
					message.addPoint(point);
					break;

				case TIMESTAMP:
					Long timeStamp = realTimeDataBuf.readInt()*1000l;
					point.setData(timeStamp);
					message.addPoint(point);
					break;

				case STRING:
					int stringLength = realTimeDataBuf.readUnsignedByte();
					char[] stringValue = new char[stringLength];
					
					for (int j = 0; j < stringLength; j++) {
						stringValue[j] = (char) realTimeDataBuf.readUnsignedByte();
					}
					point.setData(new String(stringValue));
					message.addPoint(point);
					break;

				case TEXT:
					int textLength = realTimeDataBuf.readUnsignedByte();
					byte[] textValue = new byte[textLength];
					for (int j = 0; j < textLength; j++) {
						textValue[j] = (byte) realTimeDataBuf.readUnsignedByte();
					}
					point.setData(new String(textValue,"UTF-8"));
					message.addPoint(point);
					break;

				default:
					break;
				}
				
				StatusType pointstatus = StatusType.valueOfType(binaryRepresentation.substring(4));
				if(pointstatus != null){
					point.setStatus(pointstatus.name());
				}else{
					LOGGER.info("Invalid point type fetched :"+binaryRepresentation.substring(4));
				}
			} catch (Exception e) {
				LOGGER.error("Exception occured!!!",e);
			}

		}
		LOGGER.info("Realtime Message : "+message.toString());
		return message;
	
	}
	

	@Override
	public byte[] getServerMessage(Message message, Header header) throws Exception {
		ByteBuffer ackResponse = ByteBuffer.allocate(3);
		ackResponse.put(PacketType.ANONYMOUS_ACK.getType().byteValue());
		ackResponse.put(header.getMessageType().getType().byteValue());
		ackResponse.put(header.getSeqNumber().byteValue());
		return ackResponse.array();
	}

}
