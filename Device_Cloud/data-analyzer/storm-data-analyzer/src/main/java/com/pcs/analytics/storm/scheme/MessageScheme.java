/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.analytics.storm.scheme;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.deviceframework.decoder.data.message.Message;

/**
 * Scheme for using in KafkaConfig for DeviceMessage
 *
 * @author pcseg199
 * @date Mar 30, 2015
 * @since galaxy-1.0.0
 */
public class MessageScheme implements Scheme {

	private final Logger LOGGER = LoggerFactory.getLogger(MessageScheme.class);
	private static final long serialVersionUID = 2746227329079253970L;

	public static final String SOURCE_ID_KEY = "sourceId";

	public static final String DEVICE_MESSAGE_KEY = "deviceMessage";

	// public static ObjectMapper mapper = new ObjectMapper();

	private static long count = 0l;


	@Override
	public List<Object> deserialize(byte[] ser) {

		//LOGGER.info("Consuming Message ################ Count ={}", count++);
		Values values = null;
		String jsonString = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = new String(ser);
			jsonString = jsonString.replaceAll("alarmState", "state");//need to be fixed, workaround
			jsonString = jsonString.replaceAll("alarmType", "type");//need to be fixed, workaround
			LOGGER.debug("Message in deserialize : {}", jsonString);
			Message message = mapper.readValue(jsonString, Message.class);
			LOGGER.debug("Message Object is created from json", message);
			values = new Values(message.getSourceId(), message);

		} catch (Exception e) {
			LOGGER.error("Error in Deserializing", e);
		}
		return values;
	}

	@Override
	public Fields getOutputFields() {
		return new Fields(SOURCE_ID_KEY, DEVICE_MESSAGE_KEY);
	}
	
	public static void main(String[] args) {
		String str = "{\"sourceId\":\"b6bc9394-7ccb-4216-abac-1e3f7e5fbaf9\",\"time\":\"1434881778000\",\"receivedTime\":\"null\",\"points\":[{\"pointId\":\"57\",\"pointName\":\"TestPoint_Analog_Readonly\",\"customTag\":\"TestPoint_Analog_Readonly\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"/0\",\"data\":\"47.38494\",\"status\":\"HEALTHY\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"CHANGE OF VALUE BASED ACQUISITION, THRESHOLD AT 0.0\",\"extensionType\":\"Acquisition Mode\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]}]}";
	    new MessageScheme().deserialize(str.getBytes());
    }
}
