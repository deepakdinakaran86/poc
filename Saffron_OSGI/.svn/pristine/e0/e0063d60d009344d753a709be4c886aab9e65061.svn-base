package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.G2021WriteResponseMessage;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.point.type.WriteStatus;
import com.pcs.saffron.cipher.data.message.Message;

public class WriteResponseProcessor extends G2021Processor {

	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException {
		
		ByteBuf writeResponse = (ByteBuf) G2021Data;
		Integer pId = writeResponse.readUnsignedShort();
		Integer reqId = writeResponse.readUnsignedShort();
		
		Integer snapshotTime = writeResponse.readInt();
		int status = writeResponse.readUnsignedByte();
		G2021WriteResponseMessage message = new G2021WriteResponseMessage();
		message.setpId(pId.toString());
		message.setReqId(reqId.shortValue());
		message.setStatus(WriteStatus.valueOfType(status));
		message.setReason(message.getStatus().name());
		message.setTime(snapshotTime*1000l);
		return message;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		return G2021ACKUtil.getServerMessage(message, header);
	}


}
