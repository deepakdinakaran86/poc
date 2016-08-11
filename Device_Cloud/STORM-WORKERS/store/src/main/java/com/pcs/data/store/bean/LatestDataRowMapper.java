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

/**
 * @author pcseg129
 *
 */
public class LatestDataRowMapper implements CqlRowMapper<Object, Object>,Serializable {

	private static final long serialVersionUID = 7325144677201485033L;
	private static Logger LOGGER = LoggerFactory.getLogger(LatestDataRowMapper.class);
	private String KEYSPACE = "devicecloud_test";
	private static final String COLUMN_FAMILY = "latestdata";
	
	public LatestDataRowMapper(String keyspace){
		this.KEYSPACE = keyspace;
	}
	
	@Override
	public Statement map(TridentTuple tuple) {
		String persistMessage = tuple.getString(0);
		ObjectMapper mapper = new ObjectMapper();
		try {
			PersistData persistData = mapper.readValue(persistMessage, PersistData.class);
			Insert insertStatement = QueryBuilder.insertInto(KEYSPACE,COLUMN_FAMILY);
			insertStatement.value("device", UUID.fromString(persistData.getDeviceId()));
			insertStatement.value("displayname", persistData.getDisplayName());
			insertStatement.value("data", persistData.getData().toString());
			insertStatement.value("devicedate", persistData.getDeviceDate());
			insertStatement.value("devicetime", persistData.getDeviceTime());
			LOGGER.info("--Statement --");
			LOGGER.info(insertStatement.toString());
			LOGGER.info("--Statement End--");
			return insertStatement;
		} catch (Exception e) {
			LOGGER.error("Error preparing latest data insert statement",e);
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
