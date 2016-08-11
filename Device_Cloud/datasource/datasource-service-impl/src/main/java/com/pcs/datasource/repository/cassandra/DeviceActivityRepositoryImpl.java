/**
 * 
 */
package com.pcs.datasource.repository.cassandra;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.set;
import static com.pcs.datasource.enums.DeviceActivityUpdateFields.LAST_OFFLINE_TIME;
import static com.pcs.datasource.enums.DeviceActivityUpdateFields.LAST_UPDATED_TIME;
import static com.pcs.datasource.enums.DeviceActivityUpdateFields.STATUS;
import static com.pcs.datasource.enums.DeviceDataFields.DEVICE_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;
import com.google.common.base.Joiner;
import com.pcs.datasource.dto.DeviceLatestUpdate;
import com.pcs.datasource.dto.DeviceStatus;
import com.pcs.datasource.dto.DeviceStatusNew;
import com.pcs.datasource.repository.DeviceActivityRepository;
import com.pcs.datasource.repository.utils.CassandraSessionManager;
import com.pcs.datasource.repository.utils.DataConversion;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.enums.Status;

/**
 * @author pcseg129(Seena Jyothish)
 * @date 18 Apr 2016
 *
 */
@Repository
public class DeviceActivityRepositoryImpl implements DeviceActivityRepository {

	private static final String ONLINE = "ONLINE";

	private static final String OFFLINE = "OFFLINE";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DeviceActivityRepositoryImpl.class);

	@Autowired
	private CassandraSessionManager cassandraSessionManager;

	@Autowired
	private DataConversion dataConversion;

	String deviceActivityStore = "device_activity";
	String deviceTransStore = "device_transition";

	@Override
	public List<DeviceLatestUpdate> getAllActiveDevices() {

		List<DeviceLatestUpdate> deviceLatestUpdates = new ArrayList<DeviceLatestUpdate>();

		String selectQuery = "select json device as device_id,datasourcename as datasource_name,"
				+ "devicename as device_name,lastupdatedtime as last_updated_time from "
				+ deviceActivityStore + " ;";
		LOGGER.info("query to execute {}", selectQuery);

		ResultSet resultSet = this.cassandraSessionManager.getSession(
				"dataSourceCassandra").execute(selectQuery);
		deviceLatestUpdates.addAll(dataConversion
				.convertDeviceLatestUpdate(resultSet.all()));
		return deviceLatestUpdates;
	}

	@Override
	public List<DeviceStatus> getDeviceStatus(Map<String, String> srcIds) {

		List<DeviceStatus> deviceStatus = new ArrayList<DeviceStatus>();
		String deviceFilter = Joiner.on(",").join(srcIds.keySet());
		String selectQuery = "select json device as device_id,"
				+ "blobAsBigint(timestampAsBlob(lastupdatedtime)) as last_online_time,"
				+ "blobAsBigint(timestampAsBlob(lastofflinetime)) as last_offline_time,"
				+ "status from "
				+ deviceTransStore
				+ " where device in ("
				+ deviceFilter
				+ ") ;";
		LOGGER.info("device status fetch query {}", selectQuery);
		ResultSet resultSet = this.cassandraSessionManager.getSession(
				"dataSourceCassandra").execute(selectQuery);
		deviceStatus.addAll(dataConversion.convertDeviceStatus(resultSet.all(),
				srcIds));

		return deviceStatus;
	}

	@Override
	public StatusMessageDTO persistDeviceTransition(
			List<DeviceStatusNew> deviceStatusList) {
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.SUCCESS);

		List<DeviceStatus> deviceStatusExisting = getDeviceTransitions(deviceStatusList);
		List<DeviceStatusNew> newTransitions = findNewTransitions(
				deviceStatusExisting, deviceStatusList);
		if (newTransitions != null && !newTransitions.isEmpty()) {
			List<Insert> insertStatements = buildInsertStatement(newTransitions);
			BatchStatement batchStatement = new BatchStatement();
			batchStatement.addAll(insertStatements);
			try {
				this.cassandraSessionManager.getSession("dataSourceCassandra")
						.execute(batchStatement);
			} catch (Exception e) {
				status.setStatus(Status.FAILURE);
			}
		}
		if (deviceStatusList != null && !deviceStatusList.isEmpty()) {
			BatchStatement batchStatement = new BatchStatement();
			List<Update> updateStatements = buildUpdateStatement(deviceStatusList);
			batchStatement.addAll(updateStatements);
			try {
				this.cassandraSessionManager.getSession("dataSourceCassandra")
						.execute(batchStatement);
			} catch (Exception e) {
				status.setStatus(Status.FAILURE);
			}
		}

		return status;
	}

	private List<DeviceStatus> getDeviceTransitions(
			List<DeviceStatusNew> deviceStatusList) {
		List<DeviceStatus> deviceStatusResult = new ArrayList<DeviceStatus>();
		List<String> deviceIds = new ArrayList<String>();
		for (DeviceStatusNew deviceStatus : deviceStatusList) {
			deviceIds.add(deviceStatus.getDeviceId());
		}
		String deviceFilter = Joiner.on(",").join(deviceIds);
		String selectQuery = "select json device as device_id,"
				+ "blobAsBigint(timestampAsBlob(lastupdatedtime)) as last_online_time,"
				+ "blobAsBigint(timestampAsBlob(lastofflinetime)) as last_offline_time,"
				+ "status from "
				+ deviceTransStore
				+ " where device in ("
				+ deviceFilter
				+ ") ;";
		ResultSet resultSet = this.cassandraSessionManager.getSession(
				"dataSourceCassandra").execute(selectQuery);
		deviceStatusResult.addAll(dataConversion.convertDeviceStatus(resultSet
				.all()));

		return deviceStatusResult;
	}

	private List<DeviceStatusNew> findNewTransitions(
			List<DeviceStatus> deviceStatusExistingList,
			List<DeviceStatusNew> deviceStatusList) {
		List<DeviceStatusNew> deviceStatusDuplicate = new ArrayList<DeviceStatusNew>();
		List<DeviceStatusNew> deviceStatusNew = new ArrayList<DeviceStatusNew>();
		for (DeviceStatusNew deviceStatus : deviceStatusList) {
			boolean flag = false;
			for (DeviceStatus deviceStatusExisting : deviceStatusExistingList) {
				if (deviceStatus.getDeviceId().equals(
						deviceStatusExisting.getDeviceId())) {
					if (deviceStatus.getStatus().equals(
							deviceStatusExisting.getStatus())) {
						deviceStatusDuplicate.add(deviceStatus);
					}
					flag = true;
					break;
				}
			}
			if (!flag) {
				deviceStatusNew.add(deviceStatus);
			}
		}
		deviceStatusList.removeAll(deviceStatusNew);
		deviceStatusList.removeAll(deviceStatusDuplicate);
		return deviceStatusNew;
	}

	private List<Insert> buildInsertStatement(
			List<DeviceStatusNew> deviceStatusList) {
		List<Insert> insertStatements = new ArrayList<Insert>();
		for (DeviceStatusNew deviceStatus : deviceStatusList) {
			insertStatements.add(buildInsertStatement(deviceStatus));
		}
		return insertStatements;
	}

	private Insert buildInsertStatement(DeviceStatusNew deviceStatus) {
		Insert insertStatement = QueryBuilder.insertInto(deviceTransStore);
		switch (deviceStatus.getStatus()) {
		case ONLINE:
			insertStatement
					.value(DEVICE_ID.getFieldName(),
							UUID.fromString(deviceStatus.getDeviceId()))
					.value(LAST_UPDATED_TIME.getFieldName(),
							deviceStatus.getLastOnlineTime())
					.value(STATUS.getFieldName(), ONLINE);
			break;
		case OFFLINE:
			insertStatement
					.value(DEVICE_ID.getFieldName(),
							UUID.fromString(deviceStatus.getDeviceId()))
					.value(LAST_OFFLINE_TIME.getFieldName(),
							deviceStatus.getLastOfflineTime())
					.value(STATUS.getFieldName(), OFFLINE);
		default:
			break;
		}

		return insertStatement;
	}

	private List<Update> buildUpdateStatement(
			List<DeviceStatusNew> deviceStatusList) {
		List<Update> updateStatements = new ArrayList<Update>();
		for (DeviceStatusNew deviceStatus : deviceStatusList) {
			updateStatements.add(buildTransInsertStatement(deviceStatus));
		}
		return updateStatements;
	}

	private Update buildTransInsertStatement(DeviceStatusNew deviceStatus) {
		Update updateStatement = QueryBuilder.update(deviceTransStore);
		switch (deviceStatus.getStatus()) {
		case ONLINE:
			updateStatement
					.with(set(STATUS.getFieldName(), ONLINE))
					.and(set(LAST_UPDATED_TIME.getFieldName(),
							deviceStatus.getLastOnlineTime()))
					.where(eq(DEVICE_ID.getFieldName(),
							UUID.fromString(deviceStatus.getDeviceId())));
			break;
		case OFFLINE:
			updateStatement
					.with(set(STATUS.getFieldName(), OFFLINE))
					.and(set(LAST_OFFLINE_TIME.getFieldName(),
							deviceStatus.getLastOfflineTime()))
					.where(eq(DEVICE_ID.getFieldName(),
							UUID.fromString(deviceStatus.getDeviceId())));

		default:
			break;
		}
		return updateStatement;
	}

}
