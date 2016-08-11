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

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129
 */
public class LiveMessageScheme implements Scheme {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8399066102515144885L;

	private final Logger LOGGER = LoggerFactory.getLogger(LiveMessageScheme.class);
	
	public static final String SOURCE_ID_KEY = "sourceId";

	public static final String DEVICE_MESSAGE_KEY = "deviceMessage";

	@Override
	public List<Object> deserialize(byte[] ser) {

		Values values = null;
		String jsonString = null;
		try {
			jsonString = new String(ser);
			LOGGER.info("Live message deserialize : {}", jsonString);
			values = new Values(jsonString);
		} catch (Exception e) {
			LOGGER.error("Error in Deserializing", e);
		}
		return values;

	}

	@Override
	public Fields getOutputFields() {
		return new Fields(DEVICE_MESSAGE_KEY);
	}
	
	public static void main(String[] args) {
		String test = "[{\"deviceId\":\"e778a36c-d2a0-44be-a2cf-777c1327835f\",\"sourceId\":\"895632102365475\",\"deviceTime\":1468253779355,\"deviceDate\":1468195200000,\"insertTime\":1468253796746,\"displayName\":\"Motion Status\",\"data\":\"ON\",\"datastore\":\"status_string\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"dataType\":\"STRING\",\"isLatest\":true,\"systemTag\":\"\",\"unit\":\"unitless\"}]";
		new LiveMessageScheme().deserialize(test.getBytes());
	}

}
