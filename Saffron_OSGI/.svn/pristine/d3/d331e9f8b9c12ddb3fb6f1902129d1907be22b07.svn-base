package com.pcs.device.gateway.jace.message.processor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.jace.enums.MessageElements;
import com.pcs.device.gateway.jace.message.ConnectionMessage;
import com.pcs.device.gateway.jace.message.type.MessageType;

public class ConnectionRequestProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionRequestProcessor.class);


	public static ByteBuf getConnectionResponse(ConnectionMessage connectionMessage) throws Exception{
		try {
			ObjectMapper mapper = new ObjectMapper();
			String connectionResMessage = mapper.writeValueAsString(connectionMessage);
			StringBuffer connectionResponse = new StringBuffer();
			
			connectionResponse.append((char)MessageType.CONNECTION_RESP.getType().intValue());
			connectionResponse.append(connectionResMessage)
			                  .append((char)MessageElements.CARRIER_RETURN.getType().intValue());
			String finalResponse = connectionResponse.toString();
			LOGGER.info("Response Data :{}",finalResponse);
			return Unpooled.wrappedBuffer(finalResponse.getBytes());
		} catch (Exception e) {
			LOGGER.error("Error processing connection response",e);
			throw new Exception("Error processing connection response",e);
		}
		
	}

}
