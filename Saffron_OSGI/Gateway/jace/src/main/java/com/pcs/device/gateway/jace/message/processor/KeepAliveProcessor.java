/**
 * 
 */
package com.pcs.device.gateway.jace.message.processor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.jace.enums.MessageElements;
import com.pcs.device.gateway.jace.message.KeepAlive;
import com.pcs.device.gateway.jace.message.type.MessageType;

/**
 * @author pcseg171
 *
 */
public class KeepAliveProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(KeepAliveProcessor.class);


	public static ByteBuf getKeepAliveResponse(KeepAlive keepAliveMessage) throws Exception{
		try {
			ObjectMapper mapper = new ObjectMapper();
			String pingResMessage = mapper.writeValueAsString(keepAliveMessage);
			LOGGER.info("Response Data :{}",pingResMessage);
			StringBuffer pingResponse = new StringBuffer();
			pingResponse.append((char)MessageType.KEEP_ALIVE_ACK.getType().intValue());
			pingResponse.append(pingResMessage)
			                  .append((char)MessageElements.CARRIER_RETURN.getType().intValue());
			return Unpooled.wrappedBuffer(pingResponse.toString().getBytes());
		} catch (Exception e) {
			LOGGER.error("Error processing connection response",e);
			throw new Exception("Error processing connection response",e);
		}
		
	}

}
