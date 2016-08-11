package com.pcs.device.gateway.G2021.command;

import java.nio.ByteBuffer;

import com.pcs.device.gateway.G2021.message.type.MessageType;
import com.pcs.device.gateway.G2021.packet.type.PacketType;

public class PointDiscoveryRequest {
	
	
	private static final MessageType messageType = MessageType.POINTDISCOVERYREQUEST;
	private static final PacketType packetType = PacketType.IDENTIFIED;
	
	
	public static byte[] getServerMessage(G2021Command command) throws Exception{
		ByteBuffer commandBuffer = ByteBuffer.allocate(14);
		commandBuffer.put(packetType.getType().byteValue());
		commandBuffer.put(messageType.getType().byteValue());
		commandBuffer.putInt(command.getSessionId());
		commandBuffer.putInt(command.getUnitId());
		commandBuffer.putShort(command.getRequestId().shortValue());
		commandBuffer.putShort(command.getLeaseTime().shortValue());
		return commandBuffer.array();
	}
}
