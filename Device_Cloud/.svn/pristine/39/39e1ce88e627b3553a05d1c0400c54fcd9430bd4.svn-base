package com.pcs.device.gateway.G2021.command;

import java.nio.ByteBuffer;

import com.pcs.device.gateway.G2021.exception.CommandExecutionException;
import com.pcs.device.gateway.G2021.message.type.MessageType;
import com.pcs.device.gateway.G2021.packet.type.PacketType;

public class OperationCommand {
	
	private static final PacketType PACKET_TYPE = PacketType.ANONYMOUS;
	private static final MessageType MESSAGE_TYPE = MessageType.SERVERCOMMAND;

	public static byte[] getServerMessage(G2021Command g2021Command) throws Exception {
		Byte commandCode = g2021Command.getCommandCode();
		if(commandCode != null){
			ByteBuffer commandBuffer = ByteBuffer.allocate(11);
			commandBuffer.put(PACKET_TYPE.getType().byteValue());
			commandBuffer.put(MESSAGE_TYPE.getType().byteValue());
			commandBuffer.putInt(g2021Command.getSessionId());
			commandBuffer.putInt(g2021Command.getUnitId());
			commandBuffer.put(commandCode);
			return commandBuffer.array();
		}else{
			throw new CommandExecutionException("Invalid Command Exception");
		}
	}



}
