package com.pcs.device.gateway.G2021.message.processor;

import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.deviceframework.decoder.data.message.Message;


public abstract class G2021Processor {

	public abstract Message processG2021Message(Object G2021Data) throws MessageProcessException ;
	
	public abstract byte[] getServerMessage(Message message, Header header) throws MessageProcessException;
	
}
