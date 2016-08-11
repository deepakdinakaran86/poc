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
package com.pcs.datasource.repository.cassandra;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static com.pcs.datasource.enums.AlarmDataFields.ALARM_MESSAGE;
import static com.pcs.datasource.enums.AlarmDataFields.ALARM_NAME;
import static com.pcs.datasource.enums.AlarmDataFields.ALARM_STATUS;
import static com.pcs.datasource.enums.AlarmDataFields.ALARM_TIME;
import static com.pcs.datasource.enums.AlarmDataFields.ALARM_TYPE;
import static com.pcs.datasource.enums.AlarmDataFields.DISPLAY_NAME;
import static com.pcs.datasource.enums.AlarmDataFields.UNIT;
import static com.pcs.datasource.enums.DeviceDataFields.DATA;
import static com.pcs.datasource.enums.DeviceDataFields.DATE;
import static com.pcs.datasource.enums.DeviceDataFields.DEVICE_ID;
import static com.pcs.datasource.enums.DeviceDataFields.INSERT_TIME;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.google.common.base.Joiner;
import com.pcs.datasource.dto.AlarmDataResponse;
import com.pcs.datasource.dto.AlarmDateData;
import com.pcs.datasource.dto.AlarmMessage;
import com.pcs.datasource.dto.AlarmPointData;
import com.pcs.datasource.repository.AlarmDataRepo;
import com.pcs.datasource.repository.utils.CassandraSessionManager;
import com.pcs.datasource.repository.utils.DataConversion;
import com.pcs.datasource.services.DeviceService;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for defining services related to alarm repo
 * 
 * @author PCSEG129(Seena Jyothish) Jul 16, 2015
 */
@Repository
public class AlarmDataRepoImpl implements AlarmDataRepo {

	@Autowired
	private DataConversion dataConversion;

	@Autowired
	private CassandraSessionManager cassandraSessionManager;

	@Autowired
	private DeviceService deviceService;

	String alarmDatastore = "alarm_trail";
	Statement statement = null;
	Where selectClause = null;

	@Override
	public StatusMessageDTO insert(AlarmMessage alarmMessage) {
		DateTime msgDate = new DateTime(alarmMessage.getTime())
				.toDateTime(DateTimeZone.UTC);
		long msgDateMs = msgDate.withTimeAtStartOfDay().getMillis();
		long insertTimeMs = new DateTime(DateTimeZone.UTC).getMillis();

		statement = QueryBuilder
				.insertInto(alarmDatastore)
				.value(DEVICE_ID.getFieldName(),
						UUID.fromString(alarmMessage.getSourceId()))
				.value(DATE.getFieldName(), msgDateMs)
				.value(ALARM_MESSAGE.getFieldName(),
						alarmMessage.getAlarmMessage())
				.value(ALARM_STATUS.getFieldName(), alarmMessage.getStatus())
				.value(ALARM_NAME.getFieldName(), alarmMessage.getAlarmName())
				.value(ALARM_TYPE.getFieldName(), alarmMessage.getAlarmType())
				.value(DISPLAY_NAME.getFieldName(),
						alarmMessage.getDisplayName())
				.value(DATA.getFieldName(), alarmMessage.getData())
				.value(UNIT.getFieldName(), alarmMessage.getUnit())
				.value(INSERT_TIME.getFieldName(), insertTimeMs)
				.value(ALARM_TIME.getFieldName(), alarmMessage.getTime());

		ResultSet execute = this.cassandraSessionManager.getSession(
				"dataSourceCassandra").execute(statement);
		return null;
	}

	@Override
	public StatusMessageDTO batchInsert(List<AlarmMessage> alarmMessages) {
		return null;
	}

	@Override
	public AlarmDataResponse getAlarms(String sourceId, DateTime startDatetime,
			DateTime endDatetime, List<String> pointNames) {
		AlarmDataResponse alarmDataResponse = new AlarmDataResponse();
		List<AlarmPointData> alarmPointsDatas = new ArrayList<AlarmPointData>();

		List<AlarmMessage> alarmMessages;
		AlarmPointData alarmPointData;
		AlarmDateData alarmDateData;
		DateTime startDate = startDatetime;
		for (String pointName : pointNames) {
			List<AlarmDateData> alarmDateDatas = new ArrayList<AlarmDateData>();

			alarmPointData = new AlarmPointData();
			alarmPointData.setPointName(pointName);
			startDatetime = startDate;

			alarmDateData = new AlarmDateData();
			alarmDateData.setDate(startDatetime.getMillis());

			alarmMessages = new ArrayList<AlarmMessage>();

			/*
			 * selectClause = select() .all() .from(alarmDatastore)
			 * .allowFiltering() .where(QueryBuilder.gte(
			 * AlarmDataFields.ALARM_TIME.getFieldName(),
			 * startDatetime.getMillis())) .and(QueryBuilder.lte(
			 * AlarmDataFields.ALARM_TIME.getFieldName(),
			 * endDatetime.getMillis())) .and(eq(DEVICE_ID.getFieldName(),
			 * UUID.fromString(sourceId))) .and(eq(DISPLAY_NAME.getFieldName(),
			 * pointName));
			 */

			String query = "SELECT json alarmstate as status,alarmmessage as alarm_message,data,"
					+ "displayname as display_name,"
					+ "blobAsBigint(timestampAsBlob(alarmtime)) as time"
					+ " FROM "
					+ alarmDatastore
					+ " WHERE alarmtime>='"
					+ startDatetime.getMillis()
					+ "' AND alarmtime<='"
					+ endDatetime.getMillis()
					+ "' AND device="
					+ sourceId
					+ " AND displayname='" + pointName + "' ALLOW FILTERING;";
			ResultSet resultSet = this.cassandraSessionManager.getSession(
					"dataSourceCassandra").execute(query);

			alarmMessages.addAll(dataConversion
					.convertToAlarmMessage(resultSet));

			if (CollectionUtils.isNotEmpty(alarmMessages)) {
				alarmDateData.setAlarmMessages(alarmMessages);
				alarmDateDatas.add(alarmDateData);
			}
			startDatetime = startDatetime.plusDays(1);

			if (CollectionUtils.isNotEmpty(alarmDateDatas)) {
				alarmPointData.setDates(alarmDateDatas);
				alarmPointsDatas.add(alarmPointData);
			}
		}
		if (CollectionUtils.isNotEmpty(alarmPointsDatas)) {
			// alarmDataResponse.setSourceId(sourceId);
			alarmDataResponse.setAlarms(alarmPointsDatas);
			return alarmDataResponse;
		}
		return null;
	}

	@Override
	public AlarmDataResponse getAlarms(String sourceId, DateTime startDatetime,
			DateTime endDatetime) {
		AlarmDataResponse alarmDataResponse = new AlarmDataResponse();
		AlarmPointData alarmPointData = new AlarmPointData();
		List<AlarmPointData> alarmPointsDatas = new ArrayList<AlarmPointData>();
		AlarmDateData alarmDateData;
		List<AlarmDateData> alarmDateDatas = new ArrayList<AlarmDateData>();

		List<AlarmMessage> alarmMessages;
		while ((startDatetime.isBefore(endDatetime))
				|| (startDatetime.isEqual(endDatetime))) {

			alarmDateData = new AlarmDateData();
			alarmDateData.setDate(startDatetime.getMillis());

			alarmMessages = new ArrayList<AlarmMessage>();
			selectClause = select()
					.all()
					.from(alarmDatastore)
					.allowFiltering()
					.where(eq(DATE.getFieldName(), startDatetime.getMillis()))
					.and(eq(DEVICE_ID.getFieldName(), UUID.fromString(sourceId)));
			ResultSet resultSet = this.cassandraSessionManager.getSession(
					"dataSourceCassandra").execute(selectClause);
			alarmMessages.addAll(dataConversion
					.convertToPointAlarmMessage(resultSet));

			if (CollectionUtils.isNotEmpty(alarmMessages)) {
				alarmDateData.setAlarmMessages(alarmMessages);
				alarmDateDatas.add(alarmDateData);
			}
			startDatetime = startDatetime.plusDays(1);
		}
		if (CollectionUtils.isNotEmpty(alarmDateDatas)) {
			alarmPointData.setDates(alarmDateDatas);
			alarmPointsDatas.add(alarmPointData);
		}
		if (CollectionUtils.isNotEmpty(alarmPointsDatas)) {
			// alarmDataResponse.setSourceId(sourceId);
			alarmDataResponse.setAlarms(alarmPointsDatas);
			return alarmDataResponse;
		}
		return null;
	}

	@Override
	public AlarmDataResponse getAlarmsHistory(String deviceId,
			DateTime startDateValue, DateTime endDateValue,
			List<String> pointsDisplayNames) {

		/*selectClause = select()
				.all()
				.from(alarmDatastore)
				.allowFiltering()
				.where(QueryBuilder.gte(
						AlarmDataFields.ALARM_TIME.getFieldName(),
						startDateValue.getMillis()))
				.and(QueryBuilder.lte(
						AlarmDataFields.ALARM_TIME.getFieldName(),
						endDateValue.getMillis()))
				.and(eq(DEVICE_ID.getFieldName(), UUID.fromString(deviceId)))
				.and(QueryBuilder.in(
						AlarmDataFields.DISPLAY_NAME.getFieldName(),
						pointsDisplayNames.toArray()));*/
		String displayNameFilter = Joiner.on("','").join(pointsDisplayNames);
		displayNameFilter = "'" + displayNameFilter + "'";

		String query = "SELECT json alarmname as alarm_name,"
				+ "alarmstate as status,alarmtype as alarm_type,"
				+ "alarmmessage as alarm_message,data,"
				+ "displayname as display_name,"
				+ "blobAsBigint(timestampAsBlob(alarmtime)) as time FROM "
				+ alarmDatastore
				+ " WHERE alarmtime>='"
				+ startDateValue
				+ "' AND alarmtime<='"
				+ endDateValue
				+ "' AND device="
				+ deviceId
				+ " AND displayname IN ("
				+ displayNameFilter
				+ ") ALLOW FILTERING;";

		ResultSet resultSet = this.cassandraSessionManager.getSession(
				"dataSourceCassandra").execute(query);

		List<AlarmMessage> alarmMessages = new ArrayList<AlarmMessage>();
		alarmMessages.addAll(dataConversion
				.convertToPointAlarmMessageWithDisplayName(resultSet));
		AlarmDataResponse alarmDataResponse = new AlarmDataResponse();
		alarmDataResponse.setAlarmMessages(alarmMessages);
		return alarmDataResponse;
	}

	@Override
	public AlarmDataResponse getLiveAlarms(String device,
			List<String> customTags) {
		// select * from alarm_trail_test where device =
		// 7c23b6f0-8102-4f2d-bf3b-fd7751c5875e and displayname in
		// ('aest','test') and alarmtime <= '2016-02-26 04:00:09+0400'

		List<AlarmMessage> alarmMessages = new ArrayList<AlarmMessage>();
		for (String displayName : customTags) {

			/*
			 * Select selectClause = select() .all() .from(alarmDatastore)
			 * .allowFiltering()
			 * .where(QueryBuilder.eq(DEVICE_ID.getFieldName(),
			 * UUID.fromString(device))) .and(QueryBuilder.eq(
			 * AlarmDataFields.DISPLAY_NAME.getFieldName(),
			 * displayName)).limit(1);
			 */

			String query = " SELECT json alarmname as alarm_name,"
					+ "blobAsBigint(timestampAsBlob(alarmtime)) as time,"
					+ "alarmstate as status,alarmtype as alarm_type,"
					+ "alarmmessage as alarm_message,"
					+ "data,displayname as display_name from "
					+ alarmDatastore
					+ " where device= "
					+ device
					+ " and displayname='" + displayName + "' limit 1";

			ResultSet resultSet = this.cassandraSessionManager.getSession(
					"dataSourceCassandra").execute(query);
			alarmMessages.addAll(dataConversion
					.convertToPointAlarmMessageWithDisplayName(resultSet));
		}
		AlarmDataResponse alarmDataResponse = new AlarmDataResponse();
		alarmDataResponse.setAlarmMessages(alarmMessages);
		return alarmDataResponse;
	}

}
