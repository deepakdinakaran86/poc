package com.pcs.saffron.g2021.SimulatorEngine.CS.processor;

import java.sql.Timestamp;

import com.pcs.saffron.g2021.SimulatorEngine.CS.exceptions.MessageProcessException;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.PointDiscoveryAckMessage;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.ServerMessage;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.type.MessageType;
import com.pcs.saffron.g2021.SimulatorEngine.CS.packet.type.PacketType;

import io.netty.buffer.ByteBuf;

/**
 * 
 * @author Santhosh
 *
 */
public class PointDiscoveryAckProcessor extends G2021Processor {
	
	@Override
	public ServerMessage processG2021Message(Object G2021Data) throws MessageProcessException {
		ByteBuf bufferMsg = (ByteBuf) G2021Data;
		PointDiscoveryAckMessage message = new PointDiscoveryAckMessage();
		extractMessage(bufferMsg, message);
		return message;
	}

	private void extractMessage(ByteBuf bufferMsg, PointDiscoveryAckMessage message) {
		try {
			bufferMsg.readShort();
			message.setPacketType(PacketType.valueOfType((int) bufferMsg.readUnsignedByte()));
			message.setMessageType(MessageType.valueOfType((int) bufferMsg.readUnsignedByte()));
			message.setPdRecordCount(bufferMsg.readByte());
			message.setLeaseTime(bufferMsg.readShort());
			message.setTimestamp(new Timestamp(bufferMsg.readInt()));				
			Integer pIdLength = (int) bufferMsg.readUnsignedByte();
			char[] pIds = new char[pIdLength];
			for (int i = 0; i < pIdLength; i++) {
				pIds[i] = (char) bufferMsg.readUnsignedByte();
			}
			message.setpId(new String(pIds));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
