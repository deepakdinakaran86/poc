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
package com.pcs.datasource.repository.titan;

import static com.pcs.datasource.repository.utils.CypherConstants.prepareCypherArray;
import static com.pcs.datasource.repository.utils.GremlinConstants.BATCH;
import static com.pcs.datasource.repository.utils.GremlinConstants.COMMAND;
import static com.pcs.datasource.repository.utils.GremlinConstants.DEVICE;
import static com.pcs.datasource.repository.utils.GremlinConstants.EXECUTED;
import static com.pcs.datasource.repository.utils.GremlinConstants.INCLUDES;
import static com.pcs.datasource.repository.utils.GremlinConstants.OWNS;
import static com.pcs.datasource.repository.utils.GremlinConstants.REQUESTED_AT;
import static com.pcs.datasource.repository.utils.GremlinConstants.REQUEST_ID;
import static com.pcs.datasource.repository.utils.GremlinConstants.SOURCE_ID;
import static com.pcs.datasource.repository.utils.GremlinConstants.SOURCE_IDS;
import static com.pcs.datasource.repository.utils.GremlinConstants.STATUS;
import static com.pcs.datasource.repository.utils.GremlinConstants.SUBSCRIPTION;
import static com.pcs.datasource.repository.utils.GremlinConstants.SUB_ID;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResult;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResults;
import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.writeback.BatchCommand;
import com.pcs.datasource.dto.writeback.Command;
import com.pcs.datasource.dto.writeback.WriteBackLog;
import com.pcs.datasource.dto.writeback.WriteBackPointResponse;
import com.pcs.datasource.repository.WriteBackRepository;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.datasource.repository.utils.VertexMapper;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * Repository Implementation for Write Back Related Services
 * 
 * TODO : Union of command and batch command
 * 
 * @author Javid Ahammed (pcseg199)
 * @date Dec, 2015
 * @since saffron-1.0.0
 */
@Repository("writeBackRepoTitan")
public class WriteBackRepositoryImpl implements WriteBackRepository {

	@Autowired
	private TitanSessionManager titanSessionManager;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final String FILTER_DEVICE = "{filterDevice}";

	private static final String ADD_VERTEX_QUERY = "{addVertexQuery}";

	private static final String UPDATE_WB = "{updateWB}";

	private static final String BLANK = "";

	private static final String ID = "{id}";

	private static String getBatchCommandsMax = "g.V().hasLabel(deviceLabel).has(sourceIdField,sourceId).out(commandExecutedRel).hasLabel(batchLabel).out(includesRel).hasLabel(commandLabel).values(requestIdField).max();";

	private static String getCommandsCount = "g.V().hasLabel(deviceLabel).has(sourceIdField,sourceId).out(commandExecutedRel).hasLabel(commandLabel).values(requestIdField).max()";

	private static String createCommandWOBatch = "device =  g.V().hasLabel(deviceLabel).has(sourceIdField,sourceId).next();"
			+ "command = {addVertexQuery}"
			+ "device.addEdge(commandExecutedRel,command);";

	private static String createCommandWithBatch = "device =  g.V().hasLabel(deviceLabel).has(sourceIdField,sourceId).next();"
			+ "batch = g.V(batchId).next();"
			+ "command = {addVertexQuery}"
			+ "batch.addEdge(includesRel,command);";

	private static String insertBatch = "device = g.V().hasLabel(deviceLabel).has(sourceIdField,sourceId).next(); batch = "
			+ "graph.addVertex(label,batchLabel); device.addEdge(commandExecutedRel,batch) ; [['id':batch.id()]]";

	// private static String insertBatch =
	// "device =  g.V().hasLabel(deviceLabel).has(sourceIdField,sourceId).next(); "
	// +
	// "batch = graph.addVertex(label,batchLabel); device.addEdge('executed',batch) ; batch.valueMap(true)";

	private static String getLogsFromBatch = "g.V().hasLabel(subscriptionLabel).has(subIdField,subId).out(ownsRel)."
			+ "hasLabel(deviceLabel){filterDevice}.as('device').out(commandExecutedRel).hasLabel(batchLabel).as('batch')."
			+ "out(includesRel).hasLabel(commandLabel).has(requestedAtField,gte(startTime)).has(requestedAtField,lte(endTime)).as('command')."
			+ "select('device','batch','command').by(valueMap()).by(valueMap(true)).by(valueMap())";

	private static String getLogsFromCommands = "g.V().hasLabel(subscriptionLabel).has(subIdField,subId).out(ownsRel)."
			+ "hasLabel(deviceLabel){filterDevice}.as('device').out(commandExecutedRel).hasLabel(commandLabel).has(requestedAtField,gte(startTime)).has(requestedAtField,lte(endTime)).as('command')."
			+ "select('device','command').by(valueMap())";

	private static String filterDevice = ".has(sourceIdField,within(sourceIds))";

	private static String getCommandWithReqId = "g.V().hasLabel(deviceLabel).has(sourceIdField,sourceId).out(commandExecutedRel).hasLabel(commandLabel).has(requestIdField,requestId).valueMap(true,'id')";

	private static String getBatchCommandWithReqId = "g.V().hasLabel(deviceLabel).has(sourceIdField,sourceId).out(commandExecutedRel).hasLabel(batchLabel).out(includesRel).hasLabel(commandLabel).has(requestIdField,requestId).valueMap(true,'id')";

	private static String updateCommand = "updateWB = {updateWB} ; v = g.V({id}).next() ; updateWB.each{ key, value -> v.property(key, value) } ; g.V({id}).valueMap();";

	private final String deleteBatch = "g.V({id}).drop()";

	private final String queuedCommands = "g.V().hasLabel(deviceLabel).has(sourceIdField,sourceId).out(commandExecutedRel).hasLabel(commandLabel).has(statusField,'QUEUED').valueMap()";
	private final String queuedBatchCommands = "g.V().hasLabel(deviceLabel).has(sourceIdField,sourceId).out(commandExecutedRel).hasLabel(batchLabel).out(includesRel).hasLabel(commandLabel).has(statusField,'QUEUED').valueMap()";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#getAllLogs(java.lang
	 * .String, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<WriteBackLog> getAllLogs(String subId, Long startTime,
			Long endTime) {

		Client client = titanSessionManager.getClient();
		List<WriteBackLog> allLogs = null;
		try {
			Map<String, Object> params = buildGetLogsParams(subId, startTime,
					endTime);

			ResultSet batchResultSet = client.submit(
					getLogsFromBatch.replace(FILTER_DEVICE, BLANK), params);
			List<Result> batchResults = new ArrayList<>();
			if (batchResultSet != null) {
				batchResults = batchResultSet.all().get();
			}
			ResultSet commresultSet = client.submit(
					getLogsFromCommands.replace(FILTER_DEVICE, BLANK), params);
			List<Result> commResults = new ArrayList<>();
			if (commresultSet != null) {
				commResults = commresultSet.all().get();
			}
			batchResults.addAll(commResults);

			allLogs = VertexMapper
					.fromResults(batchResults, WriteBackLog.class);

		} catch (NoResultException nre) {
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return allLogs;
	}

	// Not Implementing as not in use
	@Override
	public JSONArray getAllLogsWithRelation(String subId, Long startTime,
			Long endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#getLogsOfSelectedDevices
	 * (java.util.List, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<WriteBackLog> getLogsOfSelectedDevices(List<String> sourceIds,
			String subId, Long startTime, Long endTime) {
		Client client = titanSessionManager.getClient();
		List<WriteBackLog> allLogs = null;
		try {
			Map<String, Object> params = buildGetLogsParams(subId, startTime,
					endTime);

			String filterDeviceQuery = filterDevice.replace(SOURCE_IDS,
					prepareCypherArray(sourceIds));
			ResultSet batchResultSet = client.submit(
					getLogsFromBatch.replace(FILTER_DEVICE, filterDeviceQuery),
					params);
			List<Result> batchResults = new ArrayList<>();
			if (batchResultSet != null) {
				batchResults = batchResultSet.all().get();
			}
			ResultSet commresultSet = client.submit(getLogsFromCommands
					.replace(FILTER_DEVICE, filterDeviceQuery), params);
			List<Result> commResults = new ArrayList<>();
			if (commresultSet != null) {
				commResults = commresultSet.all().get();
			}
			batchResults.addAll(commResults);

			allLogs = VertexMapper
					.fromResults(batchResults, WriteBackLog.class);

		} catch (NoResultException nre) {
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return allLogs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#insertWriteBackLog(
	 * com.pcs.datasource.dto.writeback.WriteBackLog)
	 */
	@Override
	public Command insertWriteBackLog(WriteBackLog writeBackLog) {

		// Generate RequestId

		short cMax = -1;
		short bMax = -1;

		BatchCommand batch = writeBackLog.getBatch();
		Device device = writeBackLog.getDevice();
		Command command = writeBackLog.getCommand();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceLabel", DEVICE);
		params.put("sourceIdField", SOURCE_ID);
		params.put("commandExecutedRel", EXECUTED);
		params.put("commandLabel", COMMAND);
		params.put("requestIdField", REQUEST_ID);

		params.put(SOURCE_ID, device.getSourceId());

		Client client = titanSessionManager.getClient();
		try {
			Result result = client.submit(getCommandsCount, params).one();
			if (result != null) {
				cMax = result.getShort();
			}
		} catch (NoResultException nre) {
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		params.put("batchLabel", BATCH);
		params.put("includesRel", INCLUDES);
		try {
			Result result = client.submit(getBatchCommandsMax, params).one();
			if (result != null) {
				bMax = result.getShort();
			}
		} catch (NoResultException nre) {
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		short requestId = -1;

		if (bMax == cMax) {
			requestId = 1;
		} else if (bMax > cMax) {
			requestId = (short) (bMax + 1);
		} else {
			requestId = (short) (cMax + 1);
		}
		command.setRequestId(requestId);
		command.setRequestedAt(currentTimeMillis());
		command.setStatus("QUEUED");

		Map<String, String> customSpecs = command.getCustomSpecs();
		if (customSpecs != null && customSpecs.size() > 0) {
			command.setCustomSpecsJSON(objectBuilderUtil.getGson().toJson(
					customSpecs));
			command.setCustomSpecs(null);
		}

		if (batch == null) {
			try {
				String createVertex = createCommandWOBatch.replace(
						ADD_VERTEX_QUERY, getAddVertexQuery(COMMAND, command));
				client.submit(createVertex, params);
			} catch (NoResultException nre) {
			} catch (Exception e) {
				throw new DeviceCloudException(e);
			}
		} else {
			params.put("batchId", batch.getId());
			try {
				String createVertex = createCommandWithBatch.replace(
						ADD_VERTEX_QUERY, getAddVertexQuery(COMMAND, command));
				client.submit(createVertex, params);
			} catch (NoResultException nre) {
			} catch (Exception e) {
				throw new DeviceCloudException(e);
			}
		}

		return command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#updateWriteBack(java
	 * .lang.String, com.pcs.datasource.dto.writeback.WriteBackPointResponse)
	 */
	@Override
	public void updateWriteBack(String sourceId,
			WriteBackPointResponse writeBackResponse) {

		Client client = titanSessionManager.getClient();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceLabel", DEVICE);
		params.put("sourceIdField", SOURCE_ID);
		params.put(SOURCE_ID, sourceId);
		params.put("commandExecutedRel", EXECUTED);
		params.put("commandLabel", COMMAND);
		params.put("requestIdField", REQUEST_ID);
		params.put(REQUEST_ID, writeBackResponse.getRequestId());

		BatchCommand batchCommand = null;
		try {
			Result result = client.submit(getCommandWithReqId, params).one();
			if (result != null) {
				batchCommand = fromResult(result, BatchCommand.class);
			}
		} catch (NoResultException nre) {
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		if (batchCommand == null) {
			params.put("batchLabel", BATCH);
			params.put("includesRel", INCLUDES);
			try {
				Result result = client.submit(getBatchCommandWithReqId, params)
						.one();
				if (result != null) {
					batchCommand = fromResult(result, BatchCommand.class);
				}
			} catch (NoResultException nre) {
			} catch (Exception e) {
				throw new DeviceCloudException(e);
			}
		}
		if (batchCommand == null) {
			throw new NoResultException();
		}

		try {
			StringBuilder stringBuilder = getUpdateWBMap(writeBackResponse);
			String query = updateCommand.replace(UPDATE_WB,
					stringBuilder.toString()).replace(ID,
					valueOf(batchCommand.getId()));
			Result result = client.submit(query, params).one();
			if (result == null) {
				throw new PersistenceException("Error in Updating COMMAND");
			}
		} catch (NoResultException nre) {
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.WriteBackRepository#insertBatch(java.lang
	 * .String)
	 */
	@Override
	public BatchCommand insertBatch(String sourceId) {

		Client client = titanSessionManager.getClient();
		BatchCommand batchCommand = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("deviceLabel", DEVICE);
			params.put("sourceIdField", SOURCE_ID);
			params.put(SOURCE_ID, sourceId);
			params.put("batchLabel", BATCH);
			params.put("commandExecutedRel", EXECUTED);
			Result result = client.submit(insertBatch, params).one();
			if (result != null) {
				batchCommand = fromResult(result, BatchCommand.class);
			}
		} catch (NoResultException nre) {
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return batchCommand;
	}

	@Override
	public void deleteBatch(BatchCommand batchCommand) {
		Client client = titanSessionManager.getClient();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			client.submit(
					deleteBatch.replace(ID, valueOf(batchCommand.getId())),
					params).one();
		} catch (NoResultException nre) {
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	@Override
	public List<Command> getCurrentExecuting(String sourceId) {
		Client client = titanSessionManager.getClient();
		List<Command> commands = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("deviceLabel", DEVICE);
			params.put("sourceIdField", SOURCE_ID);
			params.put(SOURCE_ID, sourceId);
			params.put("batchLabel", BATCH);
			params.put("commandExecutedRel", EXECUTED);
			params.put("includesRel", INCLUDES);
			params.put("commandLabel", COMMAND);
			params.put("statusField", STATUS);

			List<Result> list1 = client.submit(queuedCommands, params).all()
					.get();
			List<Result> list2 = client.submit(queuedBatchCommands, params)
					.all().get();

			list1.addAll(list2);
			if (isNotEmpty(list1)) {
				commands = fromResults(list1, Command.class);
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return commands;
	}

	private static String getAddVertexQuery(String label, Object obj) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("graph.addVertex(label,").append(addQoutes(label));
		Map<String, String> fields = null;
		try {
			fields = (Map<String, String>) BeanUtils.describe(obj);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e1) {
			e1.printStackTrace();
		}

		for (String key : fields.keySet()) {
			Object value = null;
			Class<?> propertyType = null;
			try {
				value = fields.get(key);
				propertyType = PropertyUtils.getPropertyType(obj, key);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				e.printStackTrace();
			}
			if (key.equals("class") || value == null) {
				continue;
			} else {
				stringBuilder.append("," + addQoutes(key));
			}
			if (propertyType.isPrimitive()
					|| propertyType.getSuperclass().equals(Number.class)) {
				stringBuilder.append("," + valueOf(value));
			} else {
				stringBuilder.append("," + addQoutes(value.toString()));
			}
		}
		stringBuilder.append(");");
		return stringBuilder.toString();
	}

	private static String addQoutes(String value) {
		return "'" + value + "'";
	}

	private Map<String, Object> buildGetLogsParams(String subId,
			Long startTime, Long endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subscriptionLabel", SUBSCRIPTION);
		params.put("subIdField", SUB_ID);
		params.put(SUB_ID, subId);
		params.put("deviceLabel", DEVICE);
		params.put("commandExecutedRel", EXECUTED);
		params.put("commandLabel", COMMAND);
		params.put("batchLabel", BATCH);
		params.put("includesRel", INCLUDES);
		params.put("ownsRel", OWNS);
		params.put("sourceIdField", SOURCE_ID);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("requestedAtField", REQUESTED_AT);
		return params;
	}

	private StringBuilder getUpdateWBMap(
			WriteBackPointResponse writeBackResponse) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("['status':'");
		stringBuilder.append(writeBackResponse.getStatus().toString());
		stringBuilder.append("','updatedAt':");
		stringBuilder.append(System.currentTimeMillis());
		String remarks = writeBackResponse.getRemarks();
		if (remarks != null) {
			stringBuilder.append(",'remarks':'");
			stringBuilder.append(remarks);
			stringBuilder.append("'");
		}
		stringBuilder.append("]");
		return stringBuilder;
	}

}
