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
package com.pcs.datasource.repository.utils;

import static com.pcs.datasource.enums.AlarmDataFields.ALARM_MESSAGE;
import static com.pcs.datasource.enums.AlarmDataFields.ALARM_STATUS;
import static com.pcs.datasource.enums.AlarmDataFields.ALARM_TIME;
import static com.pcs.datasource.enums.AlarmDataFields.DISPLAY_NAME;
import static com.pcs.datasource.enums.DeviceDataFields.DATA;
import static com.pcs.datasource.enums.PhyQuantityFields.CONVERSION;
import static com.pcs.datasource.enums.PhyQuantityFields.DATASTORE;
import static com.pcs.datasource.enums.PhyQuantityFields.ID;
import static com.pcs.datasource.enums.PhyQuantityFields.IS_SI;
import static com.pcs.datasource.enums.PhyQuantityFields.NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.PHY_QUAN;
import static com.pcs.datasource.enums.PhyQuantityFields.P_ID;
import static com.pcs.datasource.enums.PhyQuantityFields.P_NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.STATUS_KEY;
import static com.pcs.datasource.enums.PhyQuantityFields.STATUS_NAME;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pcs.datasource.dto.AlarmMessage;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.DeviceData;
import com.pcs.datasource.dto.DeviceGeoData;
import com.pcs.datasource.dto.DeviceLatestUpdate;
import com.pcs.datasource.dto.DeviceStatus;
import com.pcs.datasource.dto.LatestDeviceData;
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.dto.TimeSeries;
import com.pcs.datasource.dto.Unit;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.datasource.model.Status;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * Conversion Utility class for Physical Quantity
 * 
 * @author pcseg199
 * @date Mar 25, 2015
 * @since galaxy-1.0.0
 */
@Component
public class DataConversion {
	@Autowired
	private CassandraSessionManager cassandraSessionManager;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DataConversion.class);

	public List<TimeSeries> getDeviceDataCollection(List<Row> rows,
	        ConfigPoint point) {
		List<TimeSeries> deviceDatas = new ArrayList<TimeSeries>();
		JsonFactory jsonFactory = new JsonFactory();
		jsonFactory.enable(Feature.ALLOW_NON_NUMERIC_NUMBERS);
		ObjectMapper mapper = new ObjectMapper(jsonFactory);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		try {
			for (Row row : rows) {
				DataTypes dataType = point.getDataType();
				switch (dataType) {
					case GEOPOINT :
						deviceDatas.add(mapper.readValue(row.getString(0),
						        DeviceGeoData.class));
						break;

					default:
						deviceDatas.add(mapper.readValue(row.getString(0),
						        DeviceData.class));
						break;
				}

			}
		} catch (Exception e) {
			LOGGER.error("Error converting device data");
		}
		return deviceDatas;
	}

	public List<LatestDeviceData> getLatestDeviceDataCollection(List<Row> rows) {
		List<LatestDeviceData> latestDatas = new ArrayList<LatestDeviceData>();
		GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder();
		Gson gson = builder.create();
		Type type = new TypeToken<LatestDeviceData>() {
		}.getType();
		for (Row row : rows) {
			try {
				latestDatas.add(gson.fromJson(row.getString(0), type));
			} catch (Exception e) {
				LOGGER.error("Error in converting to latest data bean", e);
			}
		}
		return latestDatas;
	}

	/*
	 * public List<Data> deserializeRowData(ResultSet rs) { List<Data>
	 * deviceDataList = new ArrayList<Data>(); List<Row> rows = rs.all(); if
	 * (CollectionUtils.isNotEmpty(rows)) { for (Row row : rows) { Data data =
	 * new Data(); data.setDeviceTime(row.getDate(DEVICE_TIME.getFieldName()).
	 * getMillisSinceEpoch()); Object value = row .getColumnDefinitions()
	 * .getType(DATA.getFieldName()).
	 * .deserialize(row.getBytesUnsafe(DATA.getFieldName()),
	 * ProtocolVersion.V3); data.setData(value.toString());
	 * data.setSystemTag(row.getString(SYSTEMTAG.getFieldName()));
	 * List<UDTValue> extensionUDTList = row.getList( EXTENSION.getFieldName(),
	 * UDTValue.class);
	 * data.setExtensions(convertUDTtoFieldMap(extensionUDTList));
	 * deviceDataList.add(data); } } return deviceDataList; }
	 */

	/*
	 * public List<Data> deserializeLatestRowData(ResultSet rs) { List<Data>
	 * deviceDataList = new ArrayList<Data>(); List<Row> rows = rs.all(); if
	 * (CollectionUtils.isNotEmpty(rows)) { for (Row row : rows) { Data data =
	 * new Data(); data.setDeviceTime(row.getDate(DEVICE_TIME.getFieldName()).
	 * getMillisSinceEpoch()); Object value = row .getColumnDefinitions()
	 * .getType(DATA.getFieldName())
	 * .deserialize(row.getBytesUnsafe(DATA.getFieldName()),
	 * ProtocolVersion.V3); data.setData(value.toString());
	 * data.setSystemTag(row.getString(SYSTEMTAG.getFieldName()));
	 * deviceDataList.add(data); } } return deviceDataList; }
	 */

	public PhysicalQuantity convertToPhysicalQuantity(ResultSet resultSet) {
		PhysicalQuantity phyQuantity = null;
		List<Row> rows = resultSet.all();
		if (CollectionUtils.isNotEmpty(rows)) {
			phyQuantity = new PhysicalQuantity();
			for (Row row : rows) {
				phyQuantity.setId(row.getUUID(ID.getFieldName()));
				phyQuantity
				        .setDataStore(row.getString(DATASTORE.getFieldName()));
				phyQuantity
				        .setStatus(row.getString(STATUS_NAME.getFieldName()));
				phyQuantity.setStatusKey(row.getInt(STATUS_KEY.getFieldName()));
				phyQuantity.setName(row.getString(PHY_QUAN.getFieldName()));
			}
		}
		return phyQuantity;
	}

	public Unit convertToUnit(ResultSet resultSet) {
		Unit unit = null;
		List<Row> rows = resultSet.all();
		if (CollectionUtils.isNotEmpty(rows)) {
			unit = new Unit();
			for (Row row : rows) {
				unit.setId(row.getUUID(ID.getFieldName()));
				unit.setName(row.getString(NAME.getFieldName()));
				unit.setpName(row.getString(P_NAME.getFieldName()));
				unit.setpUuid(row.getUUID(P_ID.getFieldName()));
				unit.setIsSi(row.getBool(IS_SI.getFieldName()));
			}
		}
		return unit;
	}

	public Status convertToStatus(ResultSet resultSet) {
		Status status = null;
		List<Row> rows = resultSet.all();
		if (CollectionUtils.isNotEmpty(rows)) {
			status = new Status();
			for (Row row : rows) {
				status.setStatusKey(row.getInt(STATUS_KEY.getFieldName()));
				status.setStatusName(row.getString(STATUS_NAME.getFieldName()));;
			}
		}
		return status;
	}

	public List<Unit> convertToUnitList(ResultSet resultSet) {
		List<Unit> units = new ArrayList<Unit>();
		Unit unit = null;
		List<Row> rows = resultSet.all();
		if (CollectionUtils.isNotEmpty(rows)) {
			for (Row row : rows) {
				unit = new Unit();
				unit.setId(row.getUUID(ID.getFieldName()));
				unit.setName(row.getString(NAME.getFieldName()));
				unit.setConversion(row.getString(CONVERSION.getFieldName()));
				unit.setIsSi(row.getBool(IS_SI.getFieldName()));
				unit.setpName(row.getString(P_NAME.getFieldName()));
				unit.setStatus(row.getString(STATUS_NAME.getFieldName()));
				units.add(unit);
			}
		}
		return units;
	}

	public List<AlarmMessage> convertToAlarmMessage(ResultSet resultSet) {
		List<AlarmMessage> alarmMessages = new ArrayList<AlarmMessage>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
		mapper.setDateFormat(df);
		List<Row> rows = resultSet.all();
		try {
			for (Row row : rows) {
				alarmMessages.add(mapper.readValue(row.getString(0),
				        AlarmMessage.class));
			}
		} catch (Exception e) {
			LOGGER.error("Error converting device data", e);
		}
		return alarmMessages;
	}

	public List<AlarmMessage> convertToPointAlarmMessage(ResultSet resultSet) {
		List<AlarmMessage> alarmMessages = new ArrayList<AlarmMessage>();
		AlarmMessage alarmMessage = null;
		List<Row> rows = resultSet.all();
		for (Row row : rows) {
			alarmMessage = new AlarmMessage();
			// alarmMessage.setSourceId(row.getUUID(DEVICE.getFieldName()).toString());
			// alarmMessage.setAlarmType(row.getString(ALARM_TYPE.getFieldName()));
			alarmMessage.setStatus(row.getString(ALARM_STATUS.getFieldName()));
			alarmMessage.setAlarmMessage(row.getString(ALARM_MESSAGE
			        .getFieldName()));
			alarmMessage.setData(row.getString(DATA.getFieldName()));
			alarmMessage.setDisplayName(row.getString(DISPLAY_NAME
			        .getFieldName()));
			alarmMessage.setTime(row.getDate(ALARM_TIME.getFieldName())
			        .getMillisSinceEpoch());
			alarmMessages.add(alarmMessage);
		}
		return alarmMessages;
	}

	public List<AlarmMessage> convertToPointAlarmMessageWithDisplayName(
	        ResultSet resultSet) {
		List<AlarmMessage> alarmMessages = new ArrayList<AlarmMessage>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
		mapper.setDateFormat(df);

		List<Row> rows = resultSet.all();
		try {
			for (Row row : rows) {
				alarmMessages.add(mapper.readValue(row.getString(0),
				        AlarmMessage.class));
			}
		} catch (Exception e) {
			LOGGER.error("Error converting device data");
		}
		return alarmMessages;
	}

	public Map<String, String> convertToAggregatedStringResultSet(
	        ResultSet resultSet, List<String> aggregationFunctions) {
		List<Row> rows = resultSet.all();
		String json = rows.get(0).getString(0);
		Gson gson = new Gson();

		Type typeOfMap = new TypeToken<Map<String, String>>() {
		}.getType();

		@SuppressWarnings("unchecked") Map<String, String> aggregatedValues = gson
		        .fromJson(json, typeOfMap);
		return aggregatedValues;
	}

	public List<DeviceLatestUpdate> convertDeviceLatestUpdate(List<Row> rows) {
		List<DeviceLatestUpdate> deviceLatestUpdates = new ArrayList<DeviceLatestUpdate>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
		mapper.setDateFormat(df);
		try {
			for (Row row : rows) {
				deviceLatestUpdates.add(mapper.readValue(row.getString(0),
				        DeviceLatestUpdate.class));
			}
		} catch (Exception e) {
			LOGGER.error("Error converting device data");
		}
		return deviceLatestUpdates;
	}

	public List<DeviceStatus> convertDeviceStatus(List<Row> rows,
	        Map<String, String> srcIds) {
		List<DeviceStatus> deviceStatusList = new ArrayList<DeviceStatus>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		try {
			for (Row row : rows) {
				DeviceStatus deviceStatus = new DeviceStatus();
				deviceStatus = mapper.readValue(row.getString(0),
				        DeviceStatus.class);
				deviceStatus
				        .setDeviceId(srcIds.get(deviceStatus.getDeviceId()));
				deviceStatusList.add(deviceStatus);
			}
		} catch (Exception e) {
			LOGGER.error("Error converting device data", e);
		}
		return deviceStatusList;
	}

	public List<DeviceStatus> convertDeviceStatus(List<Row> rows) {
		List<DeviceStatus> deviceStatusList = new ArrayList<DeviceStatus>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		try {
			for (Row row : rows) {
				deviceStatusList.add(mapper.readValue(row.getString(0),
				        DeviceStatus.class));
			}
		} catch (Exception e) {
			LOGGER.error("Error converting device data");
		}
		return deviceStatusList;
	}

}
