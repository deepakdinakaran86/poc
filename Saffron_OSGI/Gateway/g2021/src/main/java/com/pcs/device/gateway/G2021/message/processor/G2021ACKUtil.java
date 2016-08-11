package com.pcs.device.gateway.G2021.message.processor;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.connectivity.utils.ConversionUtils;

public class G2021ACKUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021ACKUtil.class);
	
	public static byte[] getServerMessage(Message message, Header header) throws MessageProcessException {

		ByteBuffer ackResponse = ByteBuffer.allocate(5);
		Integer ackLength = 03;
		ackResponse.putShort(ackLength.shortValue());
		ackResponse.put(PacketType.ANONYMOUS_ACK.getType().byteValue());
		ackResponse.put(header.getMessageType().getType().byteValue());
		ackResponse.put(header.getSeqNumber().byteValue());
		byte[] array = ackResponse.array();
		LOGGER.info("Server sending ack {}",ConversionUtils.getHex(array));
		return array;

	}
	
}
