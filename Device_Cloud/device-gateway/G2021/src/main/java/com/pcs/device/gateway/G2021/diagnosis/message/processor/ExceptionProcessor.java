package com.pcs.device.gateway.G2021.diagnosis.message.processor;

import io.netty.buffer.ByteBuf;

import com.pcs.device.gateway.G2021.message.ExceptionMessage;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.packet.exception.type.ExceptionType;
import com.pcs.deviceframework.decoder.data.message.Message;

public class ExceptionProcessor extends G2021Processor {

	@Override
	public Message processG2021Message(Object G2021Data) throws Exception {
		ByteBuf exceptionBuf = (ByteBuf) G2021Data;
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setExceptionType(ExceptionType.valueOfType((int) exceptionBuf.readUnsignedByte()));
		exceptionMessage.setTime(exceptionBuf.readInt()*1000l);
		return exceptionMessage;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
