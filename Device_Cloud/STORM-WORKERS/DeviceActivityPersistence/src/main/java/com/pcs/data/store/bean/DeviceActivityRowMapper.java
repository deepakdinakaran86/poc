/**
 * 
 */
package com.pcs.data.store.bean;

import java.io.Serializable;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.tuple.TridentTuple;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsonline.trident.cql.mappers.CqlRowMapper;
import com.pcs.data.store.scheme.ActivityMessage;

/**
 * @author pcseg129
 *
 */
public class DeviceActivityRowMapper implements CqlRowMapper<Object, Object>,Serializable {

	private static final long serialVersionUID = 7325155677201485033L;
	
	private static Logger LOGGER = LoggerFactory.getLogger(DeviceActivityRowMapper.class);
	
	private String keySpace = null;
	
	private static final String COLUMN_FAMILY = "device_activity";
	
	private int timeToLive;
	
	public DeviceActivityRowMapper(String keySpace, int timeToLive) {
		super();
		this.keySpace = keySpace;
		this.timeToLive = timeToLive;
	}

	@Override
	public Statement map(TridentTuple tuple) {
		String persistMessage = tuple.getString(0);
		ObjectMapper mapper = new ObjectMapper();
		try {
			ActivityMessage persistData = mapper.readValue(persistMessage, ActivityMessage.class);
			Insert insertStatement = QueryBuilder.insertInto(keySpace, COLUMN_FAMILY);
			insertStatement.using(QueryBuilder.ttl(timeToLive * 60));
			insertStatement.value("device", UUID.fromString(persistData.getSourceId()));
			insertStatement.value("lastupdatedtime", persistData.getLastUpdatedTime());
			insertStatement.value("devicename", persistData.getDeviceName());
			insertStatement.value("datasourcename", persistData.getDatasourceName());
			
			LOGGER.info("****---- device activity insert --- ****");
			LOGGER.info(insertStatement.toString());
			return insertStatement;
		} catch (Exception e) {
			LOGGER.error("DeviceActivityRowMapper | map | Error preparing latest data insert statement",e);
			return null;
		}
	}

	@Override
	public Statement map(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement retrieve(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValue(Row arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
