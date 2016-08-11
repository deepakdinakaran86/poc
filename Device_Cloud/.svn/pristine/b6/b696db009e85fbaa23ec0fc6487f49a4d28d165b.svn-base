package com.pcs.device.gateway.G2021.diagnosis.message.processor;

import io.netty.buffer.ByteBuf;

import com.pcs.device.gateway.G2021.message.WriteResponseMessage;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.point.type.WriteStatus;
import com.pcs.deviceframework.decoder.data.message.Message;

public class WriteResponseProcessor extends G2021Processor {

	@Override
	public Message processG2021Message(Object G2021Data) throws Exception {
		
		ByteBuf writeResponse = (ByteBuf) G2021Data;
		Short pId = writeResponse.readShort();
		Integer snapshotTime = writeResponse.readInt();
		int status = writeResponse.readUnsignedByte();
		WriteResponseMessage message = new WriteResponseMessage();
		message.setpId(pId.toString());
		message.setStatus(WriteStatus.valueOfType(status));
		message.setTime(snapshotTime*1000l);
		return message;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
