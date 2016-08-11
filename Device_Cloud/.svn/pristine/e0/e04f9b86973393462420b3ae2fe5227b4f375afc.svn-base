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
package com.pcs.data.transformer.storm.scheme;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.transformer.bean.DeviceData;

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

	private String spoutName;


	public MessageScheme(String spoutName){
		this.spoutName = spoutName;
	}
	
	@Override
	public List<Object> deserialize(byte[] ser) {

		Values values = null;
		String jsonString = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = new String(ser);
			DeviceData message = mapper.readValue(jsonString, DeviceData.class);
			values = new Values(message.getSourceId(), message);
			
			LOGGER.info("Deserialized device data in spout for data {}",spoutName,jsonString);

		} catch (Exception e) {
			LOGGER.error("Error in Deserializing in spout {} for data {}",spoutName,jsonString,e);
			values = new Values(null, null);
			
		}
		return values;
	}

	@Override
	public Fields getOutputFields() {
		return new Fields(SOURCE_ID_KEY, DEVICE_MESSAGE_KEY);
	}
	
	public static void main(String[] args) {
		String a = "{ \"sourceId\":\"1343\", \"parameterName\": \"e5scmt30n3rpghh2imq1v6kljd\", \"value\": \"0.5390588858758046\", \"timestamp\": \"1464438986919\" }";
	    new MessageScheme("test").deserialize(a.getBytes());
    }
}
