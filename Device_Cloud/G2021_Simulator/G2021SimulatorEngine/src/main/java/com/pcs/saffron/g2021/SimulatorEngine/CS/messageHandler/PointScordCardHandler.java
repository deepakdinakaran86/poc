package com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.message.header.Header;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ConversionUtils;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.HeaderUtil;

/**
 * 
 * @author Santhosh
 *
 */
public class PointScordCardHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PointScordCardHandler.class);

	public static byte[] getPointScordCardMessage(Integer scoreCard,Integer result) {		
	
		Header responseHeader = HeaderUtil.getPointScordCardHeader();
		//LOGGER.info("getPointScordCardMessage :::  header : " + responseHeader);
		
		Integer headerLength = 11;
		Integer bufferLength = headerLength + 3;
		
		ByteBuffer buffer = ByteBuffer.allocate(bufferLength+2);
		//LOGGER.info("Buffer allocated size : {}", bufferLength);
		buffer.putShort(bufferLength.shortValue());
		buffer.put(responseHeader.getPacketType().getType().byteValue());
		buffer.put(responseHeader.getMessageType().getType().byteValue());
		buffer.put(responseHeader.getSeqNumber().byteValue());
		buffer.putInt(responseHeader.getSessionId());
		buffer.putInt(responseHeader.getUnitId());
		
		// scord card
		buffer.putShort(scoreCard.shortValue());
		buffer.put(result.byteValue());
		
		LOGGER.info("Processed PointDiscoveryScordCard Message!!!\n PointDiscoveryScordCard Message : "+ConversionUtils.getHex(buffer.array()));
		return buffer.array();

	}


}
