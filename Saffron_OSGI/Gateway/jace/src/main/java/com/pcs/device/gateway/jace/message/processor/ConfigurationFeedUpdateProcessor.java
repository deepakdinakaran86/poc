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
import com.pcs.device.gateway.jace.message.ConfigurationFeed;
import com.pcs.device.gateway.jace.message.ConfigurationFeedResponse;
import com.pcs.device.gateway.jace.message.type.DeviceAuthenticationStatus;
import com.pcs.device.gateway.jace.message.type.MessageType;

/**
 * @author pcseg171
 *
 */
public class ConfigurationFeedUpdateProcessor  {


	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationFeedUpdateProcessor.class);

	public static ByteBuf getConfigurationFeedResponse(ConfigurationFeed configurationFeed) throws Exception{
		try {
			ObjectMapper mapper = new ObjectMapper();

			ConfigurationFeedResponse configurationFeedResponse = new ConfigurationFeedResponse();
			configurationFeedResponse.setStatus(DeviceAuthenticationStatus.valueOfType(configurationFeed.getStatus()));
			configurationFeedResponse.setSegmentIndex(configurationFeed.getSegmentIndex());

			configurationFeedResponse.setHandles(configurationFeed.getHandle().getHandleIds());

			String configurationFeedResMessage = mapper.writeValueAsString(configurationFeedResponse);
			LOGGER.info("Response Data :{}",configurationFeedResMessage);
			StringBuffer configFeedResponse = new StringBuffer();

			configFeedResponse.append((char)MessageType.CONFIG_FEED_ACK.getType().intValue());
			configFeedResponse.append(configurationFeedResMessage)
			.append((char)MessageElements.CARRIER_RETURN.getType().intValue());
			return Unpooled.wrappedBuffer(configFeedResponse.toString().getBytes());

		} catch (Exception e) {
			LOGGER.error("Error processing configuration feed response",e);
			throw new Exception("Error processing configuration feed response",e);
		}

	}

}
