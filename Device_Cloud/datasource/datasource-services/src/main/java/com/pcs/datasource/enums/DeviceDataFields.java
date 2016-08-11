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
package com.pcs.datasource.enums;

import com.pcs.devicecloud.commons.validation.DataFields;

/**
 * DeviceDataFields Enum
 * 
 * DeviceDataFields present in the DB should be placed here
 * 
 * @description Enum defining the device data fields in the DB.
 * @author Greeshma (PCSEG323)
 * @date March 2015
 * @since galaxy-1.0.0
 */

public enum DeviceDataFields implements DataFields {

	ID("source_id", "id", "Device Id"),

	UNIT_ID("unit_id", "unitId", "Unit Id"),

	DATASOURCE_NAME("datasource_name", "datasourceName", "Datasource Name"),

	POINT_CONFIGURATIONS("point_configurations", "pointConfigurations",
	        "Point Configurations"),

	PHYSICAL_QUANTIY_NAME("phy_quan", "physicalQuantity", "Physical Quantity"),

	DEVICE_ID("device", "deviceId", "Device Id"),

	DATE("date", "date", "Date"),

	INSERT_TIME("inserttime", "insertTime", "Insert Time"),

	DEVICE_TIME("devicetime", "deviceTime", "Device Time"),

	DATA("data", "data", "Data"),

	POINTS("points", "points", "Points"),

	EXTENSION("extension", "extension", "Extension"),

	CUSTOMTAG("customtag", "customTag", "Custom Tag"),

//	CUSTOMTAGS("customtag", "customTags", "Custom Tags"),

	DISPLAY_NAMES("displayNames", "displayNames", "Display Names"),

	DISPLAY_NAME("displayName", "displayName", "Display Name"),

	DEVICE_FIELD("device", "device", "Device"),

	SYSTEMTAG("systemtag", "systemTag", "System Tag"),

	START_DATE("startdate", "startDate", "Start Date"),

	END_DATE("enddate", "endDate", "End Date"),

	DATE_RANGE("daterange", "dateRange", "Date Range"),

	PHYSICAL_QUANTIY("physical_quantity", "physicalQuantity",
	        "Physical Quantity"),

	DATA_STORE("datastore", "dataStore", "Data Store"),

	UUID("uuid", "uuid", "UUID"),

	SESSION_ID("session_id", "sessionId", "Session Id"),

	COMMAND_CODE("command_code", "commandCode", "Command Code"),

	WRITEBACK_ID("writeBackId", "writeBackId", "WriteBack Id"),

	LEASE_TIME("lease_time", "leaseTime", "Lease Time"),

	COMMAND_TYPE("command_type", "commandType", "Command Type"),

	POINT_ID("point_id", "pointId", "Point Id"),

	DEVICE_TYPE("device_type", "deviceType", "Device Type"),

	TYPE("type", "type", "Type"),

	DEVICE("device", "device", "Device"),

	DATA_TYPE("data_type", "dataType", "Data Type"),

	PRIORITY("priority", "priority", "Priority"),

	COMMAND("command", "command", "Command"),

	CUSTOM_SPECS("custom_specs", "customSpecs", "Custom Specifications"),

	DEVICE_MESSAGE("device_message", "deviceMessage", "Device Message"),

	SOURCE_ID("source_id", "sourceId", "Source Id"),

	PAYLOAD("payload", "payload", "Payload"),

	SOURCE_IDS("source_ids", "sourceIds", "Source Ids"),

	TIME("time", "time", "Time"),

	FLAG("flag", "flag", "Flag"),

	SUB_ID("sub_id", "subId", "Subscription Id"),

	IS_PUBLISHING("is_publishing", "isPublishing", "Is Publishing"),

	ROW("row", "row", "row"),

	GRAPH("graph", "graph", "Graph"),

	NWPROTOCOL("nw_protocol", "networkProtocol", "Network Protocol"),

	DEVICE_PROTOCOL("device_protocol", "deviceProtocol", "Device Protocol"),

	PROTOCOL("protocol", "protocol", "Protocol"),

	SUBSCRIPTION("subscription", "subscription", "Subscription"),

	NAME("name", "name", "Name"),

	START_TIME("start_time", "startTime", "Start Time"),

	END_TIME("end_time", "endtTime", "End Time"),

	VALUE("value", "value", "Value"),

	COMMANDS("commands", "commands", "Commands"),

	CONFIGURATION("configuration", "configuration", "Configuration"),

	IP("IP", "ip", "IP"),

	MAKE("make", "make", "Device Make"),

	MODEL("model", "model", "Device Model"),

	PARAMETER_NAME("parameter_name", "parameterName", "Parameter Name"),

	VERSION("version", "version", "Protocol Version"),

	TEMPLATE_NAME("template_name", "templateName", "Template Name"),

	JWTHEADER("JWT Header parameter", "JWT Header parameter",
	        "JWT Header parameter"),

	SUBSCRIBER("subscriber", "subscriber", "Subscriber"),

	WRITEBACK_PORT("writeback_port", "writeBackPort", "Writeback Port"),

	CONNECTED_PORT("connected_port", "connectedPort", "Connected Port"),

	DEVICE_NAME("device_name", "deviceName", "Device Name"),

	LATITUDE("latitude", "latitude", "Latitude"),

	LONGITUDE("longitude", "longitude", "Longitude"),

	TIME_ZONE("time_zone", "timezone", "Time Zone"),

	GMT_OFFSET("gmt_offset", "gmtOffset", "GMT Offset"),

	GMT_OFFSET_SIGN("gmt_offset_sign", "gmtOffsetSign", "GMT Offset Sign"),

	URL("URL", "url", "URL"),

	SLOT("slot", "slot", "Slot"),

	USER_NAME("user_name", "userName", "User Name"),

	PASSWORD("password", "password", "Password"),

	TOKEN("token", "token", "Token");

	private String fieldName;
	private String variableName;
	private String description;

	private DeviceDataFields(String fieldName, String variableName,
	        String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getVariableName() {
		return variableName;
	}

	public String getDescription() {
		return description;
	}

}
