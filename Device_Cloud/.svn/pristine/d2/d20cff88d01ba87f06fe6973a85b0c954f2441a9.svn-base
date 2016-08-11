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
package com.pcs.data.analyzer.storm.scheme;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.analyzer.storm.model.CEPAlarmMessage;

/**
 * 
 * @author pcseg369
 *
 */
public class MessageScheme implements Scheme {

	private final Logger LOGGER = LoggerFactory.getLogger(MessageScheme.class);
	
	private static final long serialVersionUID = 2746227329079253970L;

	public static final String SOURCE_ID_KEY = "sourceId";

	public static final String DEVICE_MESSAGE_KEY = "deviceMessage";

	private String spoutName;

	public MessageScheme(String spoutName) {
		this.spoutName = spoutName;
	}
	
	@Override
	public List<Object> deserialize(byte[] ser) {

		Values values = null;
		String jsonString = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = new String(ser);
			CEPAlarmMessage message = mapper.readValue(jsonString, CEPAlarmMessage.class);
			//LOGGER.debug("Message Object is created from json", message);
			values = new Values(message, message);

		} catch (Exception e) {
			LOGGER.error("Error in Deserializing in spout {} for data {}",spoutName,jsonString,e);
			values = new Values(null, null);
			
		}
		return values;
	}

	@Override
	public Fields getOutputFields() {
		return new Fields(SOURCE_ID_KEY,  DEVICE_MESSAGE_KEY);
	}
	
}
