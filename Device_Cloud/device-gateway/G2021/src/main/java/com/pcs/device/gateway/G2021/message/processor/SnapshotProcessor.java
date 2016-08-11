package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.command.SyncCommandDispatcher;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.extension.point.status.type.StatusType;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.point.type.G2021DataTypes;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.data.point.extension.PointExtension;

public class SnapshotProcessor extends G2021Processor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SnapshotProcessor.class);


	private static final String STATUS = "STATUS";
	
	
	private G2021DeviceConfiguration configuration;
	
	public SnapshotProcessor(G2021DeviceConfiguration configuration) throws MessageProcessException{
		if(configuration == null){
			throw new MessageProcessException("Configuration cannot be null !!");
		}
		this.configuration = configuration;
	}

	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException {
		
		ByteBuf snapshot = (ByteBuf) G2021Data;
		Message message = null;
		
		try {
			String flag = ConversionUtils.convertToBinaryExt(Integer.toBinaryString(snapshot.readUnsignedByte()),8).toString();
			Short pid = snapshot.readShort();
			
			Integer snapshotTime = snapshot.readInt();
			Point point;
			message = new Message();
			message.setTime(snapshotTime*1000l);
			if(configuration.getPoint(pid.toString()) == null){
				SyncCommandDispatcher.releaseSyncCommand(configuration);
				throw new MessageProcessException("Invalid point id : "+pid+" Command to SYNC has be queued!!");
			}else{
				point = configuration.getPoint(pid.toString());
			}
			
			point.setPointId(pid.toString());
			switch (G2021DataTypes.valueOfType(flag.substring(0,4))) {

			case BOOLEAN:
				point.setData(snapshot.readUnsignedByte() ==0 ? false:true);
				break;

			case SHORT:
				point.setData(snapshot.readShort());
				break;

			case INT:
				point.setData(snapshot.readInt());
				break;

			case LONG:
				point.setData(snapshot.readLong());
				break;

			case FLOAT:
				point.setData(snapshot.readFloat());
				break;

			case TIMESTAMP:
				Long timeStamp = snapshot.readInt()*1000l;
				point.setData(timeStamp);
				break;

			case STRING:
				int stringLength = snapshot.readUnsignedByte();
				char[] stringValue = new char[stringLength];
				
				for (int j = 0; j < stringLength; j++) {
					stringValue[j] = (char) snapshot.readUnsignedByte();
				}
				point.setData(new String(stringValue));
				break;

			case TEXT:
				int textLength = snapshot.readUnsignedByte();
				byte[] textValue = new byte[textLength];
				for (int j = 0; j < textLength; j++) {
					textValue[j] = (byte)snapshot.readUnsignedByte();
				}
				point.setData(new String(textValue,"UTF-8"));
				snapshot.readUnsignedByte();//reading the termination string.
				break;

			default:
				return null;
			}
			
			StatusType pointstatus = StatusType.valueOfType(flag.substring(4));
			if(pointstatus != null){
				PointExtension statusExtension = point.getExtensionByType(STATUS);
				if(statusExtension != null){
					point.getExtensions().remove(statusExtension);
				}
				point.setStatus(pointstatus.name());
			}else{
				LOGGER.info("Invalid point type fetched :"+flag.substring(4));
			}
			message.addPoint(point);
			
		} catch (Exception e) {
			LOGGER.error("Error processing message!!!",e);
		}
		
		return message;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		// TODO Auto-generated method stub
		return null;
	}

}
