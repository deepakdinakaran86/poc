/**
 * 
 */
package com.pcs.analytics.flinktests.kafka.source.schema;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.streaming.util.serialization.DeserializationSchema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.analytics.flinktests.kafka.source.bean.DataIngestionBean;

/**
 * @author Aneesh
 *
 */
public class IngestionSchema implements DeserializationSchema<DataIngestionBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -37987760988255669L;

	@Override
	public TypeInformation<DataIngestionBean> getProducedType() {
		return TypeExtractor.getForClass(DataIngestionBean.class);
	}

	@Override
	public DataIngestionBean deserialize(byte[] message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(message, DataIngestionBean.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isEndOfStream(DataIngestionBean nextElement) {
		// TODO Auto-generated method stub
		return false;
	}

}
