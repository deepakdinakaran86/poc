package com.pcs.device.gateway.G2021.message.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.saffron.cipher.data.message.Message;

public class SupervisionProcessor extends G2021Processor{
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SupervisionProcessor.class);

	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException {
		LOGGER.info("Supervision Message Received !!");
		return null;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		return G2021ACKUtil.getServerMessage(message, header);
	}

}
