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
import com.pcs.data.store.bean.PersistData;

/**
 * Scheme for using in KafkaConfig for DeviceMessage
 *
 * @author pcseg199
 * @date Mar 30, 2015
 * @since galaxy-1.0.0
 */
public class CassandraMessagePersistScheme implements Scheme {

	private final Logger LOGGER = LoggerFactory.getLogger(CassandraMessagePersistScheme.class);
	private static final long serialVersionUID = 2746227329079253970L;

	public static final String DEVICE_MESSAGE_KEY = "datastore";
	public static final String PERSIST_DATA = "deviceMessage";
	public static final String SOURCE_ID = "sourceId";
	public static final String DATE = "deviceDate";
	public static final String DISPLAY_NAME = "displayName";


	@Override
	public List<Object> deserialize(byte[] ser) {

		Values values = null;
		String persistDataStr = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			persistDataStr = new String(ser);
			PersistData dataToPersist = mapper.readValue(persistDataStr, PersistData.class);
			values = new Values(dataToPersist.getDatastore(), persistDataStr,dataToPersist.getDeviceId(),dataToPersist.getDeviceDate(),dataToPersist.getDisplayName());
			
		} catch (Exception e) {
			LOGGER.error("Error in Deserializing {}",persistDataStr, e);
		}
		return values;
	}

	@Override
	public Fields getOutputFields() {
		return new Fields(DEVICE_MESSAGE_KEY, PERSIST_DATA,SOURCE_ID,DATE,DISPLAY_NAME);
	}
	
	public static void main(String[] args) {
		String str = "{\"sourceId\":\"fe0c5687-7c6a-4cac-9e6d-9386ae9a2266\",\"deviceTime\":\"1447632000000\",\"deviceDate\":\"1447635636000\",\"insertTime\":\"1449305156423\",\"displayName\":\"SET POINT\",\"data\":\"19.0\",\"datastore\":\"test\",\"extensions\":[{\"extensionName\":\"WRITEABLE\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"CHANGE OF VALUE BASED ACQUISITION, THRESHOLD AT 15.0\",\"extensionType\":\"Acquisition Mode\"},{\"extensionName\":\"OVERRIDEN_IN1\",\"extensionType\":\"STATUS\"}],\"systemTag\":\"fe0c5687-7c6a-4cac-9e6d-9386ae9a2266\"}";
	    new CassandraMessagePersistScheme().deserialize(str.getBytes());
    }
}
