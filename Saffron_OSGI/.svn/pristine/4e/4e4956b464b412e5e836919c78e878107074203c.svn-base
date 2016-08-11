package com.pcs.device.gateway.G2021.message.processor;

import java.nio.ByteBuffer;

import com.pcs.device.gateway.G2021.message.PointDiscoveryMessage;
import com.pcs.device.gateway.G2021.message.type.MessageType;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.saffron.cipher.data.message.Message;

public class PointDiscoveryResponseProcessor {

	
	public byte[] getResponse(Message message){

		PointDiscoveryMessage discoveryMessage = (PointDiscoveryMessage) message;
		Integer pointCount = discoveryMessage.getPids().size();//put invalid details to trigger exception
		ByteBuffer ackResponse = ByteBuffer.allocate(10+(pointCount*2)); // pid has to be send as short and not string
		ackResponse.put(PacketType.ANONYMOUS.getType().byteValue());
		ackResponse.put(MessageType.POINTDISCOVERYACK.getType().byteValue());
		ackResponse.put(pointCount.byteValue());
		Integer leaseTime = 60;
		ackResponse.putShort(leaseTime.shortValue());
		Long timesec = System.currentTimeMillis();
		ackResponse.putInt(timesec.intValue());
		Integer pidLength = pointCount.byteValue()*2;
		ackResponse.put(pidLength.byteValue());
		for (int i = 0; i < pointCount; i++) {
			ackResponse.putShort(discoveryMessage.getPids().get(i));
		}
		return ackResponse.array();
	
	}
}
