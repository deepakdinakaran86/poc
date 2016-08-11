/**
 * 
 */
package com.pcs.data.store.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.trident.tuple.TridentTuple;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmsonline.trident.cql.MapConfiguredCqlClientFactory;
import com.hmsonline.trident.cql.mappers.CqlRowMapper;
import com.pcs.data.store.utils.PointDataTypes;

/**
 * @author pcseg129
 *
 */
public class PhysicalQuantityMapper implements CqlRowMapper<Object, Object>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -107125559639303004L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PhysicalQuantityMapper.class);
	private String keyspace = "devicecloud_test";
	private String udtName = "fieldmap";
	private static final String KEY = "key";
	private static final String VALUE = "value";

	public PhysicalQuantityMapper(){

	}


	private static final MapConfiguredCqlClientFactory createConnectionFactory() {
		try {
			Map<Object, Object> cassandraMap = new HashMap<Object, Object>();
			
			LOGGER.info("Client Factory Is Requested For {}",CassandraConfig.TRIDENT_CASSANDRA_CLUSTER_NAME);
			
			cassandraMap.put(MapConfiguredCqlClientFactory.TRIDENT_CASSANDRA_CLUSTER_NAME, CassandraConfig.TRIDENT_CASSANDRA_CLUSTER_NAME);
			cassandraMap.put(MapConfiguredCqlClientFactory.TRIDENT_CASSANDRA_LOCAL_DATA_CENTER_NAME, CassandraConfig.TRIDENT_CASSANDRA_LOCAL_DATA_CENTER_NAME);
			cassandraMap.put(MapConfiguredCqlClientFactory.TRIDENT_CASSANDRA_CQL_HOSTS, CassandraConfig.TRIDENT_CASSANDRA_CQL_HOSTS);
			
			MapConfiguredCqlClientFactory mapConfiguredCqlClientFactory = new MapConfiguredCqlClientFactory(cassandraMap);
			LOGGER.info("Client Factory Is Set {}",mapConfiguredCqlClientFactory);
			return mapConfiguredCqlClientFactory;
		} catch (Exception e) {
			LOGGER.error("Setting Client Factory Interrupted",e);
			return null;
		}
	}
	

	public PhysicalQuantityMapper(String keyspace,String udtName){
		this.keyspace = keyspace;
		this.udtName = udtName;
	}




	/* (non-Javadoc)
	 * @see com.hmsonline.trident.cql.mappers.CqlTupleMapper#map(storm.trident.tuple.TridentTuple)
	 */
	@Override
	public Statement map(TridentTuple tuple) {
		/*
		 * tuple[0] - sourceId
		 * tuple[1] - message
		 * tupe[2] - points collection
		 * 
		 */
		String persistMessage = tuple.getString(0);
		ObjectMapper mapper = new ObjectMapper();
		try {
			PersistData persistData = mapper.readValue(persistMessage, PersistData.class);
			String datastore = persistData.getDatastore();
			if(datastore == null){
				LOGGER.info("Null Datastore received {}",persistData.toString());
				datastore = interpolateDatastore(persistData);
			}

			Insert pqInsertStatement = QueryBuilder.insertInto(keyspace,datastore);
			pqInsertStatement.value("device", UUID.fromString(persistData.getDeviceId()));
			pqInsertStatement.value("date", persistData.getDeviceDate());
			pqInsertStatement.value("inserttime", persistData.getInsertTime());
			pqInsertStatement.value("displayname", persistData.getDisplayName());

			switch (PointDataTypes.valueOf(persistData.getDataType())) {
			case STRING:
			case BOOLEAN:
				pqInsertStatement.value("data", persistData.getData());
				break;
			case SHORT:
			case INTEGER:
			case LONG:
			case DOUBLE:
			case FLOAT:
			case LATITUDE:
			case LONGITUDE:
				pqInsertStatement.value("data", Double.parseDouble(persistData.getData()!=null
				&&
				persistData.getData().toString().trim().length()>0?
						persistData.getData().toString():"0"));
				break;
			default:
				break;
			}

			pqInsertStatement.value("devicetime", persistData.getDeviceTime());
			pqInsertStatement.value("systemtag", persistData.getSystemTag());
			/*MapConfiguredCqlClientFactory clientFactory = createConnectionFactory();
			if(persistData.getExtensions()!= null && clientFactory != null){
				UserType extensionType = clientFactory.getSession().getCluster()
						.getMetadata().getKeyspace(keyspace).getUserType(udtName);
				List<UDTValue> extensions = new ArrayList<UDTValue>();

				for (PersistDataExtension extension : persistData.getExtensions()) {
					UDTValue extensionUDT = extensionType.newValue().setString(KEY, extension.getExtensionType()).setString(VALUE, extension.getExtensionName());
					extensions.add(extensionUDT);
				}

				pqInsertStatement.value("extension", extensions);
			}else{
				LOGGER.info("Extensions {} or Factory Is Null {}",persistData.getExtensions(),clientFactory);
			}*/
			LOGGER.info("--Statement --");
			LOGGER.info(pqInsertStatement.toString());
			LOGGER.info("--Statement End--");
			return pqInsertStatement;
		} catch (Exception e) {
			LOGGER.error("Error preparing statements",e);
			return null;
		}

	}

	/* (non-Javadoc)
	 * @see com.hmsonline.trident.cql.mappers.CqlTupleMapper#map(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Statement map(Object key, Object value) {
		LOGGER.info("Prepearing tuple in map(tuple)");
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hmsonline.trident.cql.mappers.CqlTupleMapper#retrieve(java.lang.Object)
	 */
	@Override
	public Statement retrieve(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hmsonline.trident.cql.mappers.CqlRowMapper#getValue(com.datastax.driver.core.Row)
	 */
	@Override
	public Object getValue(Row row) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String interpolateDatastore(PersistData persistData){
		String datastore = "status_string";
		switch (PointDataTypes.valueOf(persistData.getDataType())) {
		case BOOLEAN:
			datastore = "status_boolean";
			break;
		
		default:
			datastore = "status_float";
			break;
		}
		return datastore;
	}
}
