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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.cipher.data.message.Message;

/**
 * Scheme for using in KafkaConfig for DeviceMessage
 *
 * @author pcseg199
 * @date Mar 30, 2015
 * @since galaxy-1.0.0
 */
public class MessageAnalyzerScheme implements Scheme {

	private final Logger LOGGER = LoggerFactory.getLogger(MessageAnalyzerScheme.class);
	private static final long serialVersionUID = 2746227329079253970L;

	public static final String SOURCE_ID_KEY = "sourceId";

	public static final String DEVICE_MESSAGE_KEY = "deviceMessage";

	private String spoutName;

	public MessageAnalyzerScheme(String spoutName) {
		this.spoutName = spoutName;
	}

	@Override
	public List<Object> deserialize(byte[] ser) {

		Values values = null;
		String jsonString = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = new String(ser);
			jsonString = jsonString.replaceAll("alarmState", "state");// need to be fixed,workaround
			jsonString = jsonString.replaceAll("alarmType", "type");// need to be fixed,workaround
			
			List<Message> messages = new ArrayList<Message>();
			if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
				 messages = mapper.readValue(jsonString,
						new TypeReference<List<Message>>() {
						});
			} else {
				Message message = mapper.readValue(jsonString, Message.class);
				messages.add(message);
			}
			LOGGER.info("{} Messages desrialized for {}",messages.size(),spoutName);
			
			values = new Values(messages,"");
				
		} catch (Exception e) {
			LOGGER.error("Error in Deserializing in spout {} for data {}",
					spoutName, jsonString, e);
			values = new Values(null,null);
		}
		return values;
	}

	@Override
	public Fields getOutputFields() {
		return new Fields(DEVICE_MESSAGE_KEY,SOURCE_ID_KEY);
	}

	public static void main(String[] args) {
		String a = "{\"sourceId\":\"Win-687E-06BE-E9C1-B372\",\"time\":\"1465244858861\",\"receivedTime\":\"1465244894581\",\"points\":[{\"pointId\":\"be\",\"pointName\":\" NumericWritable\",\"displayName\":\" NumericWritable\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status double\",\"unit\":\"unitless\",\"data\":\"98.19\",\"status\":\"HEALTHY\",\"type\":\"DOUBLE\",\"extensions\":[{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]}]}";
		new MessageAnalyzerScheme("test").deserialize(a.getBytes());
	}
}
