package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.LocationMessage;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.deviceframework.decoder.data.message.Message;

public class LocationProcessor extends G2021Processor {

	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException {

		ByteBuf locationData = (ByteBuf) G2021Data;
		Integer snapshotTime = locationData.readInt();
		LocationMessage message = new LocationMessage();
		message.setTime(snapshotTime*1000l);
		message.setLatitude(locationData.readFloat());
		message.setLongitude(locationData.readFloat());
		int reason = locationData.readUnsignedByte();
		
		switch (reason) {
		case 0:
			message.setReason(LocationMessage.REASON_UNKNOWN);
			break;
		case 1:
			message.setReason(LocationMessage.MOVING);
			break;
		case 2:
			message.setReason(LocationMessage.SERVICE_FLAG_RESET);
			break;
		case 3:
			message.setReason(LocationMessage.GEOFENCE_ENTRANCE);
			break;
		case 4:
			message.setReason(LocationMessage.GEOFENCE_EXIT);
			break;

		default:
			message.setReason(LocationMessage.REASON_UNKNOWN);
			break;
		}
		message.setGeofenceId(locationData.readShort());
		return message;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		// TODO Auto-generated method stub
		return null;
	}

}
