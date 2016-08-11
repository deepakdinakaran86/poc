package com.pcs.device.gateway.G2021.message.processor;

import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.saffron.cipher.data.message.Message;

public class WriteCommandProcessor extends G2021Processor {

	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		return G2021ACKUtil.getServerMessage(message, header);
	}

}
