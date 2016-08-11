
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.fault.monitor.scheme;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.fault.monitor.bean.FaultData1;

/**
 * This class is responsible for deserializing the data read from kafka topic
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 20, 2016
 */
public class MessageScheme implements Scheme {

	private final Logger LOGGER = LoggerFactory.getLogger(MessageScheme.class);
	private static final long serialVersionUID = 2746227329079253970L;

	public static final String SOURCE_ID_KEY = "sourceId";

	public static final String FAULT_DATA = "faultData";
	
	public static final String FAULT_DATA_STRING = "faultDataString";

	private String spoutName;


	public MessageScheme(String spoutName){
		this.spoutName = spoutName;
	}

	public List<Object> deserialize(byte[] ser) {
		Values values = null;
		String jsonString = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = new String(ser);
			FaultData1 faultData1 = mapper.readValue(jsonString, FaultData1.class);
			values = new Values(faultData1.getFaultDataInfo().getDeviceId(), faultData1,jsonString);
		} catch (Exception e) {
			LOGGER.error("Error in Deserializing in spout {} for data {}",spoutName,jsonString,e);
			values = new Values(null, null,null);
		}
		return values;
    }

	public Fields getOutputFields() {
		return new Fields(SOURCE_ID_KEY, FAULT_DATA,FAULT_DATA_STRING);
    }

}
