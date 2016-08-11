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
import com.pcs.data.store.bean.DeviceTransition;


/**
 * 
 * @author pcseg369
 *
 */
public class DeviceActivityScheme implements Scheme {

	private final Logger LOGGER = LoggerFactory.getLogger(DeviceActivityScheme.class);
	
	private static final long serialVersionUID = 2746227329079253970L;

	public static final String DEVICE_LATEST_STATUS = "latestStatus";
	public static final String SOURCE_ID = "sourceId";


	@Override
	public List<Object> deserialize(byte[] ser) {

		Values values = null;
		String deviceLatestStatus = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			deviceLatestStatus = new String(ser);
			DeviceTransition dataToPersist = mapper.readValue(deviceLatestStatus, DeviceTransition.class);
			values = new Values(deviceLatestStatus,dataToPersist.getDeviceId());
			
		} catch (Exception e) {
			LOGGER.error("Error in Deserializing {}", deviceLatestStatus, e);
		}
		return values;
	}

	@Override
	public Fields getOutputFields() {
		return new Fields(DEVICE_LATEST_STATUS,SOURCE_ID);
	}
}
