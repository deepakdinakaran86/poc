package com.pcs.datasource.doc.constants;

public class AlarmResourceConstants extends ResourceConstants {

	public static final String ALARM_PERSIST_SUMMARY = "Persist alarm";
	public static final String ALARM_PERSIST_DESC = "This is the service to be used to persist alarm, sample payload : {\"sourceId\":\"259642460472496\",\"status\":\"true\",\"alarmMessage\":\"a\",\"time\":\"1954-08-14 09:22:08.000\"}";
	public final static String ALARM_PERSIST_SUCCESS_RESP = "{\"status\": \"SUCCESS\"}";

	public static final String ALARM_FIND_SUMMARY = "Fetch alarms for device points by startdate and enddate";
	public static final String ALARM_FIND_DESC = "This is the service to be used to fetch alarms history for device points by startdate and enddate, sample payload : "
	        + "{\"sourceId\":\"testDev\",\"startDate\":1451606400000,\"endDate\":1462060800000,\"displayNames\":[\"Fuel Level 1:n49-873\"]}";
	public final static String ALARM_FIND_SUCCESS_RESP = "[{\"sourceId\":\"sayirdevice2001\",\"alarmMessages\":[{\"alarmType\":\"OUT OF RANGE ALARM\",\"status\":\"true\",\"statusMessage\":\"LongitudeHighlimitAlarmActive\",\"data\":\"44.321266\",\"displayNames\":\"Longitude\",\"time\":1439913707000},{\"alarmType\":\"OUT OF RANGE ALARM\",\"status\":\"true\",\"statusMessage\":\"AO1HighlimitAlarmActive\",\"data\":\"49.009827\",\"displayNames\":\"Latitude\",\"time\":1439913423000}]}]";

	public static final String ALARM_FIND_ALL_SUMMARY = "Fetch all alarms for device points by startdate and enddate";
	public static final String ALARM_FIND_ALL_DESC = "This is the service to be used to fetch alarms history for device points by startdate and enddate, sample payload : "
	        + "[{\"sourceId\":\"testDev\",\"startDate\":1451606400000,\"endDate\":1462060800000,\"displayNames\":[\"Fuel Level 1:n49-873\"]}]";
	public final static String ALARM_FIND_ALL_SUCCESS_RESP = "{\"sourceId\":\"testDev\",\"alarms\":[{\"pointName\":\"Fuel Level 1:n49-873\",\"dates\":[{\"date\":1451606400000,\"alarmMessages\":[{\"time\":1459668775000,\"alarmMessage\":\"AO1_LowlimitAlarmActive\",\"displayName\":\"Fuel Level 1:n49-873\",\"data\":\"9.999645\",\"status\":\"true\"}]}]}]}";

	public static final String ALARM_HISTORY_SUMMARY = "Fetch alarm history for device points by startdate and enddate";
	public static final String ALARM_HISTORY_DESC = "This is the service to be used to fetch alarms history for device points by startdate and enddate, sample payload : "
	        + "[{\"sourceId\":\"sayirdevice2001\",\"startDate\":1439856000000,\"endDate\":1440028800000,\"displayNames\":[\"Longitude\",\"Latitude\"]}]";
	public final static String ALARM_HISTORY_SUCCESS_RESP = "[{\"sourceId\":\"testDev\",\"alarmMessages\":[{\"time\":1459668775000,\"alarmMessage\":\"AO1_LowlimitAlarmActive\",\"alarmName\":\"OUT OF RANGE ALARM\",\"alarmType\":\"OUT OF RANGE ALARM\",\"displayName\":\"Fuel Level 1:n49-873\",\"data\":\"9.999645\",\"status\":\"true\"}]}]";

	public static final String ALARM_LATEST_SUMMARY = "Fetch latest alarm for device points by startdate and enddate";
	public static final String ALARM_LATEST_DESC = "This is the service to be used to fetch latest alarms for device points, sample payload : "
	        + "[{\"sourceId\":\"testDev\",\"displayNames\":[\"Fuel Level 1:n49-873\"]}]";
	public final static String ALARM_LATEST_SUCCESS_RESP = "[{\"sourceId\":\"testDev\",\"alarmMessages\":[{\"time\":1459668775000,\"alarmMessage\":\"AO1_LowlimitAlarmActive\",\"alarmName\":\"OUT OF RANGE ALARM\",\"alarmType\":\"OUT OF RANGE ALARM\",\"displayName\":\"Fuel Level 1:n49-873\",\"data\":\"9.999645\",\"status\":\"true\"}]}]";

}
