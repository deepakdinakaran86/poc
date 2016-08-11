package com.pcs.device.gateway.G2021.diagnosis.message.processor;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.diagnosis.handler.G2021DiagnosticHandler;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.deviceframework.decoder.data.message.Message;

public class ScorecardProcessor extends G2021Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScorecardProcessor.class);



	public ScorecardProcessor(Integer unitId, Object sourceIdentifier) throws Exception {
		
	}

	@Override
	public Message processG2021Message(Object G2021Data) throws Exception {
		ByteBuf scorecardBuf = (ByteBuf) G2021Data;
		LOGGER.info("Total points in sync : "+scorecardBuf.readShort());
		short scorecardResult = scorecardBuf.readUnsignedByte();
		LOGGER.info("Result : "+scorecardResult);
		if(scorecardResult == 1 && G2021DiagnosticHandler.deviceConfiguration.hasChanged()){
			LOGGER.info("Configuration change detected, update request fired : "+scorecardResult);
			LOGGER.info("New Configuration :- "+G2021DiagnosticHandler.deviceConfiguration.toString());
		}
		return null;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws Exception {

		ByteBuffer ackResponse = ByteBuffer.allocate(3);
		ackResponse.put(PacketType.ANONYMOUS_ACK.getType().byteValue());
		ackResponse.put(header.getMessageType().getType().byteValue());
		ackResponse.put(header.getSeqNumber().byteValue());
		return ackResponse.array();

	}

}
