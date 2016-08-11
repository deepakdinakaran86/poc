/**
 * 
 */
package com.pcs.data.store.bean;

import static com.pcs.data.store.constants.Constants.DEVICE;
import static com.pcs.data.store.constants.Constants.LAST_OFFLINE_TIME;
import static com.pcs.data.store.constants.Constants.LAST_UPDATED_TIME;
import static com.pcs.data.store.constants.Constants.STATUS;

import java.io.Serializable;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.tuple.TridentTuple;
import clojure.set__init;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsonline.trident.cql.mappers.CqlRowMapper;

import static com.datastax.driver.core.querybuilder.QueryBuilder.set;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

/**
 * @author pcseg129
 *
 */
public class DeviceTransitionRowMapper implements CqlRowMapper<Object, Object>,
		Serializable {

	private static final long serialVersionUID = 7325155677201485033L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(DeviceTransitionRowMapper.class);

	private String keySpace = null;

	private static final String COLUMN_FAMILY = "device_transition";

	public DeviceTransitionRowMapper(String keySpace) {
		super();
		this.keySpace = keySpace;
	}

	@Override
	public Statement map(TridentTuple tuple) {
		String persistMessage = tuple.getString(0);
		ObjectMapper mapper = new ObjectMapper();
		try {
			DeviceTransition persistData = mapper.readValue(persistMessage,
					DeviceTransition.class);

			switch (persistData.getStatus()) {
			case ONLINE:
				return updateOnlineDevice(persistData);
			
			case OFFLINE:
				return updateOfflineDevice(persistData);

			default:
				break;
			}
			return null;

		} catch (Exception e) {
			LOGGER.error("Error preparing device transition insert statement",
					e);
			return null;
		}
	}

	private Statement updateOnlineDevice(DeviceTransition deviceTransition) {
		Update updateStatement = QueryBuilder.update(keySpace, COLUMN_FAMILY);
		updateStatement
				.with(set(LAST_UPDATED_TIME,
						deviceTransition.getLastOnlineTime()))
				.and(set(STATUS, deviceTransition.getStatus().toString()))
				.where(eq(DEVICE, UUID.fromString(deviceTransition.getDeviceId())));
		LOGGER.info("--device Online update --");
		LOGGER.info(updateStatement.toString());
		return updateStatement;
	}

	private Statement updateOfflineDevice(DeviceTransition deviceTransition) {
		Update updateStatement = QueryBuilder.update(keySpace, COLUMN_FAMILY);
		updateStatement
				.with(set(LAST_OFFLINE_TIME,
						deviceTransition.getLastOfflineTime()))
				.and(set(STATUS, deviceTransition.getStatus().toString()))
				.where(eq(DEVICE, UUID.fromString(deviceTransition.getDeviceId())));
		LOGGER.info("--device Offline update --");
		LOGGER.info(updateStatement.toString());
		return updateStatement;
	}

	@Override
	public Statement map(Object arg0, Object arg1) {
		return null;
	}

	@Override
	public Statement retrieve(Object arg0) {
		return null;
	}

	@Override
	public Object getValue(Row arg0) {
		return null;
	}

}
