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
import com.pcs.device.gateway.jace.message.AlarmMessage;
import com.pcs.device.gateway.jace.message.AlarmMessageResponse;
import com.pcs.device.gateway.jace.message.type.MessageType;

/**
 * @author pcseg171
 *
 */
public class AlarmProcessor  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmProcessor.class);
	
	public static ByteBuf getAlarmResponse(AlarmMessage alarmMessage) throws Exception{
		try {
			ObjectMapper mapper = new ObjectMapper();
			AlarmMessageResponse alarmMessageResponse = new AlarmMessageResponse();
			alarmMessageResponse.setAlarmId(alarmMessage.getAlarmId());
			String alarmResMessage = mapper.writeValueAsString(alarmMessageResponse);
			LOGGER.info("Response Data :{}",alarmResMessage);
			StringBuffer alarmResponse = new StringBuffer();
			alarmResponse.append((char)MessageType.ALARM_ACK.getType().intValue());
			alarmResponse.append(alarmResMessage)
			                  .append((char)MessageElements.CARRIER_RETURN.getType().intValue());
			return Unpooled.wrappedBuffer(alarmResponse.toString().getBytes());
		} catch (Exception e) {
			LOGGER.error("Error processing alarm response",e);
			throw new Exception("Error processing alarm response",e);
		}
		
	}

}
