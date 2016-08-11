package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.G2021Message;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.deviceframework.decoder.data.message.Message;

public class AuthenticateProcessor extends G2021Processor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateProcessor.class);

	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException {
		G2021Message message = new G2021Message();
		ByteBuf messageData = (ByteBuf) G2021Data;
		message.setDeviceTimeOut(messageData.readInt());
		Integer subscriptionKeyLength = (int) messageData.readUnsignedByte();
		char[] subscriptionKey = new char[subscriptionKeyLength];
		for (int i = 0; i < subscriptionKeyLength; i++) {
			subscriptionKey[i] = (char) messageData.readUnsignedByte();
		}
		message.setSubscriptionKey(new String(subscriptionKey));
		LOGGER.info("Processed Authenticate Message!!!");
		messageData.release();
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
