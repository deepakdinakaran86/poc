package com.pcs.saffron.g2021.SimulatorEngine.CS.processor;

import java.sql.Timestamp;

import com.pcs.saffron.g2021.SimulatorEngine.CS.exceptions.MessageProcessException;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.ServerMessage;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.WelcomeMessage;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.type.MessageType;
import com.pcs.saffron.g2021.SimulatorEngine.CS.packet.type.PacketType;

import io.netty.buffer.ByteBuf;

/**
 * 
 * @author Santhosh
 *
 */
public class WelcomeProcessor extends G2021Processor {
	
	@Override
	public ServerMessage processG2021Message(Object G2021Data) throws MessageProcessException {
		ByteBuf bufferMsg = (ByteBuf) G2021Data;
		WelcomeMessage message = new WelcomeMessage();
		extractMessage(bufferMsg, message);
		return message;
	}

	private void extractMessage(ByteBuf bufferMsg, WelcomeMessage message) {
		try {
			bufferMsg.readShort();
			message.setPacketType(PacketType.valueOfType((int) bufferMsg.readUnsignedByte()));
			message.setMessageType(MessageType.valueOfType((int) bufferMsg.readUnsignedByte()));
			message.setTimestamp(new Timestamp(bufferMsg.readInt()));
			message.setdPort(bufferMsg.readShort());
			message.setDataServerHostType(bufferMsg.readByte());
		    message.setDataServerIp(bufferMsg.readInt());
			Integer domainNameLength = (int) bufferMsg.readUnsignedByte();
			char[] domainName = new char[domainNameLength];
			for (int i = 0; i < domainNameLength; i++) {
				domainName[i] = (char) bufferMsg.readUnsignedByte();
			}
			message.setDataServerDomainName(new String(domainName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
