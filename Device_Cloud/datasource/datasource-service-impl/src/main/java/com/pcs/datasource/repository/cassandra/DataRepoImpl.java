/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLS and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.repository.cassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.google.common.base.Joiner;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.LatestDeviceData;
import com.pcs.datasource.dto.TimeSeries;
import com.pcs.datasource.dto.TimeSeriesData;
import com.pcs.datasource.repository.DataRepo;
import com.pcs.datasource.repository.utils.CassandraSessionManager;
import com.pcs.datasource.repository.utils.DataConversion;

/**
 * This repository implementation class is responsible for defining all the
 * services related to data of a Device communicating to the system. This class
 * is responsible for persisting (single persist , batch persist) and fetching
 * the device data details from the communicating devices.
 * 
 * @author pcseg199 (Javid Ahammed)
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

@Service
public class DataRepoImpl implements DataRepo {

	@Autowired
	private CassandraSessionManager cassandraSessionManager;

	@Autowired
	private DataConversion dataConversion;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DataRepoImpl.class);

	Statement statement = null;
	Where selectClause = null;

	/**
	 * Method to fetch details of the device data communicating to the system
	 * 
	 * @param uid
	 * @param startDateValue
	 * @param endDateValue
	 * @param customTag
	 * @return List<PhysicalQuantity>
	 * 
	 */

	@Override
	public List<TimeSeriesData> getDeviceData(UUID uid,
	        DateTime startDateValue, DateTime endDateValue,
	        List<ConfigPoint> fetchPoints) {

		List<TimeSeriesData> customTagList = new ArrayList<TimeSeriesData>();;
		TimeSeriesData customTagData;

		List<Long> datesMilliSec = new ArrayList<Long>();

		long startTime = startDateValue.getMillis();
		long endTime = endDateValue.getMillis();
		int day = 0;
		while (startTime <= endTime) {
			DateTime dateAdded = startDateValue.withFieldAdded(
			        DurationFieldType.days(), day);
			long dateMilliSec = dateAdded.withTimeAtStartOfDay().getMillis();
			datesMilliSec.add(dateMilliSec);
			startTime += 24 * 60 * 60 * 1000;
			day++;
		}

		String dateFilter = Joiner.on("','").join(datesMilliSec);
		dateFilter = "'" + dateFilter + "'";

		for (ConfigPoint configPoint : fetchPoints) {
			List<TimeSeries> deviceDataList = new ArrayList<TimeSeries>();
			customTagData = new TimeSeriesData();
			customTagData.setDisplayName(configPoint.getDisplayName());

			String selectQuery = "select json "
			        + "blobAsBigint(timestampAsBlob(devicetime)) as device_time,"
			        + "blobAsBigint(timestampAsBlob(inserttime)) as insert_time,"
			        + "data,extension as extensions from "
			        + configPoint.getPhysicalQuantity() + " where device="
			        + uid + " and date in (" + dateFilter + ") "
			        + " and devicetime >=" + startDateValue.getMillis()
			        + " and devicetime <=" + endDateValue.getMillis()
			        + " and displayname= '"
			        + configPoint.getDisplayName().replaceAll("'", "''")// fix
			                                                            // for
			                                                            // demo
			        + "' allow filtering;";
			LOGGER.info("query to execute {}", selectQuery);

			ResultSet resultSet = this.cassandraSessionManager.getSession(
			        "dataSourceCassandra").execute(selectQuery);
			deviceDataList.addAll(dataConversion.getDeviceDataCollection(
			        resultSet.all(), configPoint));
			if (CollectionUtils.isNotEmpty(deviceDataList)) {
				customTagData.setValues(deviceDataList);
				customTagList.add(customTagData);
			}
		}
		if (CollectionUtils.isNotEmpty(customTagList)) {
			return customTagList;
		}
		return null;

	}

	@Override
	public LatestDeviceData getLatestData(String deviceId,
	        String physicalQtyName, String customTag) {

		String cql = "SELECT json displayname as display_name,data,"
		        + "blobAsBigint(timestampAsBlob(devicetime)) as device_time "
		        + "FROM latestdata" + " WHERE displayname='"
		        + customTag.replaceAll("'", "''")// fix for demo
		        + "' and device=" + deviceId + ";";

		Session session = cassandraSessionManager
		        .getSession("dataSourceCassandra");
		ResultSet resultSet = session.execute(cql);
		return convertToDevicePointData(resultSet);
	}

	public LatestDeviceData convertToDevicePointData(ResultSet resultSet) {
		List<LatestDeviceData> dataDetails = dataConversion
		        .getLatestDeviceDataCollection(resultSet.all());
		if (CollectionUtils.isNotEmpty(dataDetails)) {
			return dataDetails.get(0);
		}
		return null;
	}

	@Override
	public Map<String, String> getAggregatedData(UUID uid,
	        DateTime startDateValue, DateTime endDateValue,
	        ConfigPoint configPoint, List<String> aggregationFunctions) {

		List<Long> datesMilliSec = new ArrayList<Long>();

		long startTime = startDateValue.getMillis();
		long endTime = endDateValue.getMillis();
		int day = 0;
		while (startTime <= endTime) {
			DateTime dateAdded = startDateValue.withFieldAdded(
			        DurationFieldType.days(), day);
			long dateMilliSec = dateAdded.withTimeAtStartOfDay().getMillis();
			datesMilliSec.add(dateMilliSec);
			startTime += 24 * 60 * 60 * 1000;
			day++;
		}

		String dateFilter = Joiner.on("','").join(datesMilliSec);
		dateFilter = "'" + dateFilter + "'";

		String aggregationFuncFilter = getAggregationFuncFilterStr(aggregationFunctions);

		// aggregationFuncFilter = aggregationFuncFilter.concat("(data)");

		String selectQuery = "select json " + aggregationFuncFilter + " from "
		        + configPoint.getPhysicalQuantity() + " where device=" + uid
		        + " and date in (" + dateFilter + ") " + " and devicetime >="
		        + startDateValue.getMillis() + " and devicetime <="
		        + endDateValue.getMillis() + " and displayname= '"
		        + configPoint.getDisplayName().replaceAll("'", "''")
		        + "' allow filtering;";
		LOGGER.info("query to execute {}", selectQuery);
		ResultSet resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectQuery);
		Map<String, String> aggregatedValues = dataConversion
		        .convertToAggregatedStringResultSet(resultSet,
		                aggregationFunctions);
		return aggregatedValues;

	}

	private static String getAggregationFuncFilterStr(
	        List<String> aggregationFunctions) {
		String filter = new String();
		for (String func : aggregationFunctions) {
			filter = filter + func + "(data) as " + func + ", ";
		}
		return filter.substring(0, filter.lastIndexOf(","));
	}

	@Override
	public TimeSeriesData getDeviceAndAggregatedData(UUID uid,
	        DateTime startDateValue, DateTime endDateValue,
	        ConfigPoint configPoint, List<String> aggregationFunctions) {

		List<TimeSeriesData> customTagList = new ArrayList<TimeSeriesData>();;
		TimeSeriesData customTagData;

		List<Long> datesMilliSec = new ArrayList<Long>();

		long startTime = startDateValue.getMillis();
		long endTime = endDateValue.getMillis();
		int day = 0;
		while (startTime <= endTime) {
			DateTime dateAdded = startDateValue.withFieldAdded(
			        DurationFieldType.days(), day);
			long dateMilliSec = dateAdded.withTimeAtStartOfDay().getMillis();
			datesMilliSec.add(dateMilliSec);
			startTime += 24 * 60 * 60 * 1000;
			day++;
		}

		String dateFilter = Joiner.on("','").join(datesMilliSec);
		dateFilter = "'" + dateFilter + "'";

		List<TimeSeries> deviceDataList = new ArrayList<TimeSeries>();
		customTagData = new TimeSeriesData();
		customTagData.setDisplayName(configPoint.getDisplayName());

		String selectQuery = "select json "
		        + "blobAsBigint(timestampAsBlob(devicetime)) as device_time,data,extension as extensions from "
		        + configPoint.getPhysicalQuantity() + " where device=" + uid
		        + " and date in (" + dateFilter + ") " + " and devicetime >="
		        + startDateValue.getMillis() + " and devicetime <="
		        + endDateValue.getMillis() + " and displayname= '"
		        + configPoint.getDisplayName().replaceAll("'", "''")// fix for
		                                                            // demo
		        + "' allow filtering;";
		LOGGER.info("query to execute {}", selectQuery);

		ResultSet resultSet = this.cassandraSessionManager.getSession(
		        "dataSourceCassandra").execute(selectQuery);
		List<Row> rows = resultSet.all();
		if (CollectionUtils.isEmpty(rows)) {
			// data does not exists
			return null;
		}
		deviceDataList.addAll(dataConversion.getDeviceDataCollection(rows,
		        configPoint));

		Map<String, String> aggregatedValues = null;
		if (CollectionUtils.isNotEmpty(aggregationFunctions)) {

			// String aggregationFuncFilter = Joiner.on("(data), ").join(
			// aggregationFunctions);
			// aggregationFuncFilter = aggregationFuncFilter.concat("(data)");

			String aggregationFuncFilter = getAggregationFuncFilterStr(aggregationFunctions);

			String selectQueryForAggregatedData = "select json "
			        + aggregationFuncFilter + " from "
			        + configPoint.getPhysicalQuantity() + " where device="
			        + uid + " and date in (" + dateFilter + ") "
			        + " and devicetime >=" + startDateValue.getMillis()
			        + " and devicetime <=" + endDateValue.getMillis()
			        + " and displayname= '"
			        + configPoint.getDisplayName().replaceAll("'", "''")
			        + "' allow filtering;";
			LOGGER.info("query to execute {}", selectQueryForAggregatedData);
			ResultSet resultSetAggregated = this.cassandraSessionManager
			        .getSession("dataSourceCassandra").execute(
			                selectQueryForAggregatedData);
			aggregatedValues = dataConversion
			        .convertToAggregatedStringResultSet(resultSetAggregated,
			                aggregationFunctions);
		}

		if (CollectionUtils.isNotEmpty(deviceDataList)) {
			customTagData.setValues(deviceDataList);
			if (aggregatedValues != null) {
				customTagData.setAggregatedData(aggregatedValues);
			}
			customTagList.add(customTagData);
		}
		return customTagData;

	}

}
