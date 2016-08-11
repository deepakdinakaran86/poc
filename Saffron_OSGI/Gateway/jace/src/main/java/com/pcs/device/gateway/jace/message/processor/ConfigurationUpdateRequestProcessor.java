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
import com.pcs.device.gateway.jace.message.ConfigurationUpdateMessage;
import com.pcs.device.gateway.jace.message.type.MessageType;

/**
 * @author pcseg171
 *
 */
public class ConfigurationUpdateRequestProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUpdateRequestProcessor.class);


	public static ByteBuf getConfigurationUpdateResponse(ConfigurationUpdateMessage configurationUpdateMessage) throws Exception{
		try {
			ObjectMapper mapper = new ObjectMapper();
			String configupdateResMessage = mapper.writeValueAsString(configurationUpdateMessage);
			LOGGER.info("Response Data :{}",configupdateResMessage);
			StringBuffer configUpdateResponse = new StringBuffer();
			configUpdateResponse.append((char)MessageType.CONFIG_UPDATE_RESP.getType().intValue());
			configUpdateResponse.append(configupdateResMessage)
			                  .append((char)MessageElements.CARRIER_RETURN.getType().intValue());
			return Unpooled.wrappedBuffer(configUpdateResponse.toString().getBytes());
		} catch (Exception e) {
			LOGGER.error("Error processing configuration update response",e);
			throw new Exception("Error processing configuration update response",e);
		}
		
	}

}
