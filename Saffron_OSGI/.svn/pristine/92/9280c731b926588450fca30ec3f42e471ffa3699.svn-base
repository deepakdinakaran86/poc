package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.HelloMessage;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.saffron.cipher.data.message.Message;

public class HelloProcessor extends G2021Processor {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(HelloProcessor.class);

	@Override
	public Message processG2021Message(Object G2021Data)
	        throws MessageProcessException {
		ByteBuf messageData = (ByteBuf)G2021Data;
		HelloMessage message = new HelloMessage();
		extractMessage(messageData, message);
		return message;
	}

	private void extractMessage(ByteBuf messageData, HelloMessage message) {
		try {
			message.setSourceId(messageData.readLong());
			Integer assetIdUnsigned = messageData.readUnsignedShort();
			message.setAssetId(assetIdUnsigned.shortValue());
			message.setVersionId(messageData.readUnsignedByte());
			message.identifyReason((int)messageData.readUnsignedByte());
			message.setCellularRSSi((int)messageData.readUnsignedByte());
			message.setLatitude(messageData.readFloat());
			message.setLongitude(messageData.readFloat());
			Integer timeZone = messageData.readInt();// messageData.readUnsignedShort();
			message.setGmtOffset(timeZone.shortValue());

			int cckeyLength = messageData.readUnsignedByte();
			char[] cckeyArray = new char[cckeyLength];
			for (int i = 0; i < cckeyLength; i++) {
				cckeyArray[i] = (char)messageData.readUnsignedByte();
			}
			LOGGER.info("CC Key " + new String(cckeyArray));
			message.setCcKey(new String(cckeyArray));

			int iccidLength = messageData.readUnsignedByte();
			char[] iccidArray = new char[iccidLength];
			for (int i = 0; i < iccidLength; i++) {
				iccidArray[i] = (char)messageData.readUnsignedByte();
			}
			LOGGER.info("SIM ICCID " + new String(iccidArray));
			message.setSimICCID(new String(iccidArray));

			for (int i = 0; i < 10; i++) {// 10 is the current maximum client
				                          // level detail provided by the G2021
				                          // device.
				int infoLength = messageData.readUnsignedByte();
				processClientInfo(message, messageData.readBytes(infoLength));
			}
			message.addClientInfo("CREATEASSET");

		} catch (Exception e) {
			LOGGER.error("Error decoding Hello", e);
		}
	}

	@Override
	public byte[] getServerMessage(Message message, Header header)
	        throws MessageProcessException {
		return G2021ACKUtil.getServerMessage(message, header);
	}

	/**
	 * @param message
	 * @param clientInfoData
	 */
	private void processClientInfo(HelloMessage message, ByteBuf clientInfoData) {
		int infoLength = clientInfoData.readableBytes();
		if (infoLength == 0) {
			message.addClientInfo("");
		} else {
			char[] infoArray = new char[infoLength];
			for (int i = 0; i < infoLength; i++) {
				infoArray[i] = (char)clientInfoData.readUnsignedByte();
			}
			LOGGER.info("Client Info " + new String(infoArray));
			message.addClientInfo(new String(infoArray));
		}
	}

}
