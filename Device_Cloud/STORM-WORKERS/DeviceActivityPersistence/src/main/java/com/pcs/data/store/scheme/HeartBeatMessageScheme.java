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
package com.pcs.data.store.scheme;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * @author pcseg369
 *
 */
public class HeartBeatMessageScheme implements Scheme {

	private final Logger LOGGER = LoggerFactory.getLogger(HeartBeatMessageScheme.class);
	
	private static final long serialVersionUID = 2746227329079253970L;

	public static final String DEVICE_MESSAGE_KEY = "device-activity-communication";
	public static final String PERSIST_DATA = "deviceMessage";
	public static final String SOURCE_ID = "sourceId";
	public static final String UPDATED_TIME = "lastUpdatedTime";
	public static final String DEVICE_NAME = "deviceName";
	public static final String DATASOURCE_NAME = "datasourceName";


	@Override
	public List<Object> deserialize(byte[] ser) {

		Values values = null;
		String heartBeatMessage = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			heartBeatMessage = new String(ser);
			ActivityMessage dataToPersist = mapper.readValue(heartBeatMessage, ActivityMessage.class);
			values = new Values(DEVICE_MESSAGE_KEY, heartBeatMessage, dataToPersist.getSourceId(), 
					dataToPersist.getLastUpdatedTime(), dataToPersist.getDeviceName(), dataToPersist.getDatasourceName());
			
		} catch (Exception e) {
			LOGGER.error("HeartBeatMessageScheme | Error in Deserializing {}", heartBeatMessage, e);
		}
		return values;
	}

	@Override
	public Fields getOutputFields() {
		return new Fields(DEVICE_MESSAGE_KEY, PERSIST_DATA, SOURCE_ID, UPDATED_TIME, DEVICE_NAME, DATASOURCE_NAME);
	}
}
