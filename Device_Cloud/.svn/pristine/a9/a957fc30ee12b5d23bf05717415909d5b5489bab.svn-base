/**
 * 
 */
package com.pcs.analytics.flinktests.kafka.source.schema;

import org.apache.flink.streaming.util.serialization.SerializationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.analytics.flinktests.beans.DeviceSnapshot;

/**
 * @author Aneesh
 *
 */
public class DeviceSnapshotSchema implements SerializationSchema<DeviceSnapshot, byte[]> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSnapshotSchema.class);
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * 
	 */
	private static final long serialVersionUID = -6329467338897823464L;

	/**
	 * 
	 */

	@Override
	public byte[] serialize(DeviceSnapshot snapshot) {
		if(snapshot != null){
			try {
				return OBJECT_MAPPER.writeValueAsBytes(snapshot);
			} catch (Exception e) {
				LOGGER.error("Invalid schema",e);
			}
		}else{
			LOGGER.info("Null object");
		}
		return null;
	}

}
