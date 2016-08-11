package com.pcs.device.gateway.ruptela.message.processor;

import java.util.List;

import com.pcs.device.gateway.ruptela.exception.MessageProcessException;
import com.pcs.device.gateway.ruptela.message.header.DeviceMessageHeader;
import com.pcs.saffron.cipher.data.message.Message;

public abstract class RuptelaDeviceMessageProcessor {



	public abstract List<Message> processRuptelaMessage(String ruptelaHexaData, DeviceMessageHeader header) throws MessageProcessException ;
	
	public abstract byte[] getServerMessage(Message message, DeviceMessageHeader header) throws MessageProcessException;
	

}
