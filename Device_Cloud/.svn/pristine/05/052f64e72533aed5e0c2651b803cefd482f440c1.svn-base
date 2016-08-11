package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.command.SyncCommandDispatcher;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.extension.point.status.type.StatusType;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.point.type.G2021DataTypes;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.data.point.extension.PointExtension;

public class RealTimeDataProcessor extends G2021Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RealTimeDataProcessor.class);

	private static final String STATUS = "STATUS";
	
	private G2021DeviceConfiguration configuration;
	
	public RealTimeDataProcessor(G2021DeviceConfiguration configuration) throws MessageProcessException{
		if(configuration == null){
			throw new MessageProcessException("Configuration cannot be null !!");
		}
		this.configuration = configuration;
	}

	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException {

		ByteBuf realTimeDataBuf = (ByteBuf) G2021Data;
		Integer recordCount = (int) realTimeDataBuf.readUnsignedByte();
		
		Message message = new Message();
		
		for (int i = 0; i < recordCount; i++) {
			Point point = null;
				Integer pid = realTimeDataBuf.readUnsignedShort();
				point = configuration.getPoint(pid.toString());
				if(point == null){
					SyncCommandDispatcher.releaseSyncCommand(configuration);
					throw new MessageProcessException("Invalid point id : "+pid+". Command to SYNC has be queued!!");
				}
				Integer snapshotTime = realTimeDataBuf.readInt();
				message.setTime(snapshotTime*1000l);
				int flag = realTimeDataBuf.readUnsignedByte();
				String binaryRepresentation = ConversionUtils.convertToBinaryExt(Integer.toHexString(flag),8).toString();
				String dataType = binaryRepresentation.substring(0,4);
				
				try {
					
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
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				StatusType pointstatus = StatusType.valueOfType(binaryRepresentation.substring(4));
				if(pointstatus != null){
					PointExtension statusExtension = point.getExtensionByType(STATUS);
					if(statusExtension != null){
						point.getExtensions().remove(statusExtension);
					}
					point.setStatus(pointstatus.name());
				}else{
					LOGGER.info("Invalid point type fetched :"+binaryRepresentation.substring(4));
				}
		}
		return message;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		ByteBuffer ackResponse = ByteBuffer.allocate(3);
		ackResponse.put(PacketType.ANONYMOUS_ACK.getType().byteValue());
		ackResponse.put(header.getMessageType().getType().byteValue());
		ackResponse.put(header.getSeqNumber().byteValue());
		return ackResponse.array();
	}

}
