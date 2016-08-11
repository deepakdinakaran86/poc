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
package com.pcs.datasource.repository.neo4j;

import static com.pcs.datasource.enums.DeviceDataFields.GRAPH;
import static com.pcs.datasource.enums.DeviceDataFields.ROW;
import static com.pcs.datasource.repository.utils.CypherConstants.BATCH_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.END_TIME;
import static com.pcs.datasource.repository.utils.CypherConstants.REMARKS;
import static com.pcs.datasource.repository.utils.CypherConstants.SOURCE_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.SOURCE_IDs;
import static com.pcs.datasource.repository.utils.CypherConstants.START_TIME;
import static com.pcs.datasource.repository.utils.CypherConstants.STATUS;
import static com.pcs.datasource.repository.utils.CypherConstants.SUB_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.WRITEBACK_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.prepareCypherArray;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;
import static java.lang.String.valueOf;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.common.reflect.TypeToken;
import com.hazelcast.query.QueryException;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.writeback.BatchCommand;
import com.pcs.datasource.dto.writeback.DeviceCommand;
import com.pcs.datasource.dto.writeback.WriteBackCommand;
import com.pcs.datasource.dto.writeback.WriteBackLog;
import com.pcs.datasource.repository.WriteBackRepository;
import com.pcs.datasource.repository.utils.CypherConstants;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * Repository Implementation for Write Back Related Services
 * 
 * @author Javid Ahammed (pcseg199)
 * @date May 19, 2015
 * @since galaxy-1.0.0
 */
@Repository("writeBackRepoNeo4j")
public class WriteBackRepositoryImpl implements WriteBackRepository {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private final String getAllLogsOfSub = "MATCH (a:COMMAND)<-[*]-(sub:SUBSCRIPTION {sub_id:'{sub_id}'}) where a.requested_at >= {start_time} and a.requested_at <={end_time} "
	        + "OPTIONAL MATCH a<-[r1:executed]-b "
	        + "OPTIONAL MATCH a<-[:includes]-c , c<-[:executed]-d "
	        + "return collect({command:a,batch: CASE c:BATCH when true then c else null end , "
	        + "device: CASE WHEN d.source_id IS NULL THEN {source_id:b.source_id} ELSE {source_id:d.source_id} END}) as device;";

	private final String getAllLogsWithRelation = "MATCH (a:COMMAND)<-[*]-(sub:SUBSCRIPTION {sub_id:'{sub_id}'}) where a.requested_at >= {start_time} and a.requested_at <={end_time} "
	        + "OPTIONAL MATCH a<-[r1:executed]-b OPTIONAL MATCH a<-[r2:includes]-c , c<-[r3:executed]-d return a,r1,r2,r3;";

	private final String getLogsOfSelectedDevices = "MATCH (sub:SUBSCRIPTION {sub_id:'{sub_id}'})-[:owns]->(dev:DEVICE)-[*]->(a:COMMAND) where dev.source_id in {source_ids} and a.requested_at >= {start_time} and a.requested_at <= {end_time} OPTIONAL MATCH a<-[r1:executed]-b OPTIONAL MATCH a<-[:includes]-c , c<-[:executed]-d return collect({command:a,batch: CASE c:BATCH when true then c else null end , device: CASE WHEN d.source_id IS NULL THEN {source_id:b.source_id} ELSE {source_id:d.source_id} END}) as device;";

	private final String updateWriteBackResponse = "MATCH (a:DEVICE {source_id:'{source_id}'})-[*]->(b:COMMAND {writeback_id:{writeback_id}}) SET b.status='{status}',b.updated_at=timestamp(),b.remarks='{remarks}' return b;";

	private final String insertBatch = "MATCH (a:DEVICE {source_id:'{source_id}'}) CREATE (b:BATCH )<-[:executed]-a SET b.requested_at=timestamp(),b.batch_id=id(b) RETURN b;";

	private final String insertCommandWOBatch = "OPTIONAL MATCH (d:DEVICE {source_id:'{source_id}'})-[*]->(w:COMMAND) WITH max(w.writeback_id) as maxId MATCH (a:DEVICE {source_id:'{source_id}'})  "
	        + "CREATE (b:COMMAND {props})<-[:executed]-a SET b.requested_at=timestamp(),b.writeback_id=(CASE WHEN maxId is null then 1 ELSE maxId+1 END) RETURN b;";

	private final String insertCommandWithBatch = "OPTIONAL MATCH (d:DEVICE {source_id:'{source_id}'})-[*]->(w:COMMAND) WITH max(w.writeback_id) as maxId "
	        + "MATCH (a:DEVICE {source_id:'{source_id}'})-[:executed]->(b:BATCH {batch_id:{batch_id}}) "
	        + "CREATE (c:COMMAND {props})<-[:includes]-b SET c.requested_at=timestamp(),c.writeback_id=(CASE WHEN maxId is null then 1 ELSE maxId+1 END) RETURN c";

	private final String deleteBatch = "MATCH (a:BATCH {batch_id:{batch_id}})<-[r:executed]-b delete r,a;";

	private final String getCurrentExecutingPointWriteCommand = "MATCH (a:DEVICE {source_id:'{source_id}'})-[*]->(b:COMMAND {status:'QUEUED'}) where b.command in ['Point Write Command','0x22'] return collect(b);";

	/**
	 * Service Method for fetching all the writeBack Logs of a subscription
	 * (fetch data specific to Grid)
	 * 
	 * @param subId
	 * @param startTime
	 * @param endTime
	 * @return {@link List<WriteBackLog>}
	 */
	@Override
	public List<WriteBackLog> getAllLogs(String subId, Long startTime,
	        Long endTime) {
		String query = getAllLogsOfSub.replace(SUB_ID, subId)
		        .replace(START_TIME, startTime.toString())
		        .replace(CypherConstants.END_TIME, endTime.toString());

		JSONArray jsonArray = null;
		try {
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException("Error in getAllLogs", e);
		}
		if (jsonArray.length() < 0 || jsonArray.getJSONObject(0) == null) {
			return null;
		}

		@SuppressWarnings("serial") Type type = new TypeToken<List<WriteBackLog>>() {
		}.getType();

		List<WriteBackLog> fromJson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson().fromJson(
		                jsonArray.getJSONObject(0)
		                        .getJSONArray(ROW.getFieldName())
		                        .getJSONArray(0).toString(), type);

		return fromJson;
	}

	/**
	 * Service Method for fetching all the writeBack Logs of a subscription
	 * (fetch data with relationship)
	 * 
	 * @param subId
	 * @param startTime
	 * @param endTime
	 * @return {@link JSONArray}
	 */
	@Override
	public JSONArray getAllLogsWithRelation(String subId, Long startTime,
	        Long endTime) {
		String query = getAllLogsWithRelation.replace(SUB_ID, subId)
		        .replace(START_TIME, startTime.toString())
		        .replace(CypherConstants.END_TIME, endTime.toString());

		JSONArray jsonArray = null;
		try {
			jsonArray = exexcuteQuery(neo4jURI, query, null,
			        GRAPH.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException("Error in getAllLogs", e);
		}

		return jsonArray;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#getLogsOfDevices(java
	 * .util.List, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<WriteBackLog> getLogsOfSelectedDevices(List<String> sourceIds,
	        String subId, Long startTime, Long endTime) {

		String query = getLogsOfSelectedDevices.replace(SUB_ID, subId)
		        .replace(SOURCE_IDs, prepareCypherArray(sourceIds))
		        .replace(START_TIME, startTime.toString())
		        .replace(END_TIME, endTime.toString());

		JSONArray jsonArray = null;
		try {
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException("Error in getAllLogs", e);
		}
		if (jsonArray.length() < 0 || jsonArray.getJSONObject(0) == null) {
			return null;
		}

		@SuppressWarnings("serial") Type type = new TypeToken<List<WriteBackLog>>() {
		}.getType();

		List<WriteBackLog> fromJson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson().fromJson(
		                jsonArray.getJSONObject(0)
		                        .getJSONArray(ROW.getFieldName())
		                        .getJSONArray(0).toString(), type);

		return fromJson;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#insertWriteBackLog(
	 * com.pcs.datasource.dto.writeback.WriteBackLog)
	 */
	@Override
	public DeviceCommand insertWriteBackLog(WriteBackLog writeBackLog) {

		Device device = writeBackLog.getDevice();
		DeviceCommand command = writeBackLog.getCommand();
		command.setStatus(writeBackLog.getStatus());

		String query = null;

		query = insertCommandWOBatch.replace(SOURCE_ID, device.getSourceId());

		String json = objectBuilderUtil.getLowerCaseUnderScoreGson().toJson(
		        command);
		JSONArray jsonResponse = null;
		try {
			jsonResponse = exexcuteQuery(neo4jURI, query, json,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in Inserting WriteBack Log",
			        e);
		}
		if (jsonResponse == null) {
			throw new PersistenceException("Error in insertWriteBackLog");
		}

		if (jsonResponse.length() < 0 || jsonResponse.getJSONObject(0) == null) {
			return null;
		}

		return objectBuilderUtil.getLowerCaseUnderScoreGson().fromJson(
		        jsonResponse.getJSONObject(0).getJSONArray(ROW.getFieldName())
		                .getJSONObject(0).toString(), DeviceCommand.class);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#updateWriteBackStatus
	 * (com.pcs.datasource.dto.writeback.Command)
	 */
	@Override
	public void updateWriteBack(String sourceId,
	        WriteBackCommand writeBackCommand) {
		String query = updateWriteBackResponse
		        .replace(CypherConstants.SOURCE_ID, sourceId)
		        .replace(WRITEBACK_ID,
		                String.valueOf(writeBackCommand.getWriteBackId()))
		        .replace(STATUS, writeBackCommand.getStatus().toString());
		String remarks = writeBackCommand.getRemarks();
		if (StringUtils.isEmpty(remarks)) {
			query = query.replace(REMARKS, "No Remarks");
		} else {
			query = query.replace(REMARKS, remarks);
		}
		JSONArray jsonResponse = null;
		try {
			jsonResponse = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in updating writeBackStatus",
			        e);
		}
		if (jsonResponse == null) {
			throw new PersistenceException(
			        "Could not update not existing command");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#insertBatch(com.pcs
	 * .datasource.dto.writeback.WriteBackLog)
	 */
	@Override
	public BatchCommand insertBatch(String sourceId) {

		String query = insertBatch.replace(SOURCE_ID, sourceId);

		JSONArray jsonResponse = null;
		try {
			jsonResponse = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in insertBatch", e);
		}
		if (jsonResponse == null) {
			throw new PersistenceException("Error in insertBatch");
		}

		if (jsonResponse.length() < 0 || jsonResponse.getJSONObject(0) == null) {
			return null;
		}

		return objectBuilderUtil.getLowerCaseUnderScoreGson().fromJson(
		        jsonResponse.getJSONObject(0).getJSONArray(ROW.getFieldName())
		                .getJSONObject(0).toString(), BatchCommand.class);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#deleteBatch(com.pcs
	 * .datasource.dto.writeback.BatchCommand)
	 */
	@Override
	public void deleteBatch(BatchCommand batchCommand) {
		String query = deleteBatch.replace(BATCH_ID,
		        valueOf(batchCommand.getId()));
		try {
			exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in insertBatch", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#getCurrentExecuting
	 * (java.lang.String)
	 */
	@Override
	public List<DeviceCommand> getCurrentExecuting(String sourceId) {
		String query = getCurrentExecutingPointWriteCommand.replace(
		        CypherConstants.SOURCE_ID, sourceId);

		JSONArray jsonArray = null;
		try {
			jsonArray = exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
		} catch (JSONException | IOException e) {
			throw new QueryException("Error in getCurrentExecuting", e);
		}
		if (jsonArray.length() < 0 || jsonArray.getJSONObject(0) == null) {
			return null;
		}

		@SuppressWarnings("serial") Type type = new TypeToken<List<DeviceCommand>>() {
		}.getType();

		List<DeviceCommand> fromJson = objectBuilderUtil
		        .getLowerCaseUnderScoreGson().fromJson(
		                jsonArray.getJSONObject(0)
		                        .getJSONArray(ROW.getFieldName())
		                        .getJSONArray(0).toString(), type);

		return fromJson;
	}

	public static void main(String[] args) {
		WriteBackRepositoryImpl writeBackRepositoryImpl = new WriteBackRepositoryImpl();
		writeBackRepositoryImpl.objectBuilderUtil = new ObjectBuilderUtil();
		writeBackRepositoryImpl.neo4jURI = "http://10.234.31.170:7474/";

		List<String> sourceIds = new ArrayList<String>();

		sourceIds.add("SourceId_00002");
		sourceIds.add("SourceId_001");

		List<WriteBackLog> logsOfSelectedDevices = writeBackRepositoryImpl
		        .getLogsOfSelectedDevices(sourceIds, "1", 1447243178800l,
		                1447650848721l);

		System.out.println(logsOfSelectedDevices);
	}

}
