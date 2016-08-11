package com.pcs.datasource.doc.constants;

public class SystemResourceConstants extends ResourceConstants {

	public static final String GET_DATA_TYPES_SUMMARY = "Get all the data types available in the system";
	public static final String GET_DATA_TYPES_DESC = "This is the service to be used to fetch all the data types available in the system";
	public static final String GET_DATA_TYPES_SUCCESS_RESPONSE = "[ {\"name\": \"Boolean\"}, {\"name\": \"Long\"}, {\"name\": \"Float\"}, {\"name\": \"Double\"}, {\"name\": \"Short\"}, {\"name\": \"Integer\"} ]";

	public static final String GET_ACCESS_TYPES_SUMMARY = "Get all the access types available in the system";
	public static final String GET_ACCESS_TYPES_DESC = "This is the service to be used to fetch all the access types available in the system";
	public static final String GET_ACCESS_TYPES_SUCCESS_RESPONSE = "[ { \"name\": \"WRITEABLE\", \"writeable\": false }, { \"name\": \"READ_ONLY\", \"writeable\": false } ]";

	public static final String GET_NW_PROTOCOLS_SUMMARY = "Get all the network protocols available in the system";
	public static final String GET_NW_PROTOCOLS_DESC = "This is the service to be used to fetch all the network protocols available in the system";
	public static final String GET_NW_PROTOCOLS_SUCCESS_RESPONSE = "[ {\"name\": \"TCP\"}, {\"name\": \"UDP\"} ]";

	public static final String GET_SYS_TAGS_SUMMARY = "Get all the system tags available in the system";
	public static final String GET_SYS_TAGS_DESC = "This is the service to be used to fetch all the system tags available in the system,you may filter it with a physical quantity";
	public static final String GET_SYS_TAGS_SUCCESS_RESPONSE = "[ { \"name\": \"Room Temperature\", \"pqName\": \"temperature\" }, { \"name\": \"Wind Velocity\", \"pqName\": \"velocity\" } ]";

	public static final String GET_MAKES_SUMMARY = "Get all the makes available in the system";
	public static final String GET_MAKES_DESC = "This is the service to be used to fetch all the makes in the system";
	public static final String GET_MAKES_SUCCESS_RESPONSE = "[ {\"name\": \"PCS\"}, {\"name\": \"Teltonika\"} ]";

	public static final String GET_DEVICE_TYPES_SUMMARY = "Get all the device types of a make";
	public static final String GET_DEVICE_TYPES_DESC = "This is the service to be used to fetch all the device types of a make";
	public static final String GET_DEVICE_TYPES_SUCCESS_RESPONSE = "[ {\"name\": \"Telematic\"}, {\"name\": \"BMS\"} ]";

	public static final String GET_MODELS_SUMMARY = "Get all the models of a device type";
	public static final String GET_MODELS_DESC = "This is the service to be used to fetch all the models of a device type,sample payload:{ \"make\":\"PCS\", \"deviceType\":\"Edge Device\" }";
	public static final String GET_MODELS_SUCCESS_RESPONSE = "[{\"name\": \"EDCP\"}]";

	public static final String GET_PROTOCOLS_SUMMARY = "Get all the protocols of a model";
	public static final String GET_PROTOCOLS_DESC = "This is the service to be used to fetch all the protocols of a model,sample payload:{ \"make\":\"PCS\", \"deviceType\":\"Edge Device\", \"model\":\"EDCP\" }";
	public static final String GET_PROTOCOLS_SUCCESS_RESPONSE = "[{\"name\": \"EDCP\"}]";

	public static final String GET_PROTOCOLS_VERSIONS_SUMMARY = "Get all the versions of a protocol";
	public static final String GET_PROTOCOLS_VERSIONS_DESC = "This is the service to be used to fetch all the versions of a protocol,sample payload:{ \"make\":\"PCS\", \"deviceType\":\"Edge Device\", \"model\":\"EDCP\", \"protocol\":\"EDCP\" }";
	public static final String GET_PROTOCOLS_VERSIONS_SUCCESS_RESPONSE = "[ {\"name\": \"1.2.0\"}, {\"name\": \"1.0.0\"} ]";

	public static final String GET_PROTOCOLS_POINTS_SUMMARY = "Get all the points of a protocol version";
	public static final String GET_PROTOCOLS_POINTS_DESC = "This is the service to be used to fetch all the points of a versions of a protocol,sample payload:{ \"make\":\"Teltonika\", \"deviceType\":\"Telematics\", \"model\":\"FMS\", \"protocol\":\"FM5300\", \"version\":\"1.0.0\" }";
	public static final String GET_PROTOCOLS_POINTS_SUCCESS_RESPONSE = "{ \"points\": [ { \"pointId\": \"Visible Satellites\", \"pointName\": \"Visible Satellites\", \"type\": \"INTEGER\", \"pointAccessType\": \"READONLY\", \"displayName\": \"Visible Satellites\" }, { \"pointId\": \"Speed\", \"pointName\": \"Speed\", \"type\": \"INTEGER\", \"pointAccessType\": \"READONLY\", \"displayName\": \"Speed\" } ] }";
	
	public static final String GET_PROTOCOLS_COMMANDS_SUMMARY = "Get all the commands of a protocol version";
	public static final String GET_PROTOCOLS_COMMANDS_DESC = "This is the service to be used to fetch all the commands supported by a versions of a protocol,sample payload:{ \"make\":\"Teltonika\", \"deviceType\":\"Telematics\", \"model\":\"FMS\", \"protocol\":\"FM5300\", \"version\":\"1.0.0\" }";
	public static final String GET_PROTOCOLS_COMMANDS_SUCCESS_RESPONSE = "[ { \"name\": \"Point Write Command\", \"systemCmd\": false }, { \"name\": \"Server Command\", \"systemCmd\": true } ]";
	
	public static final String CREATE_SUBSRIPTION_SUMMARY = "Create new subscription";
	public static final String CREATE_SUBSRIPTION_DESC =  "This is the service to be used to create a new subscription,Payload is not required";
	
	public static final String CREATE_MAKE_SUMMARY = "Create device make";
	public static final String CREATE_MAKE_DESC = "This is the service to be used to create device make in the system,sample payload: {\"name\":\"PCS\"}";
	
	public static final String CREATE_DEVICE_TYPE_SUMMARY = "Create device type";
	public static final String CREATE_DEVICE_TYPE_DESC = "This is the service to be used to create device type in the system,sample payload: {\"name\":\"Telematics\"}";
	
	public static final String UPDATE_DEVICE_TYPE_SUMMARY = "Update device type";
	public static final String UPDATE_DEVICE_TYPE_DESC = "This is the service to be used to update device type in the system,sample payload: {\"name\":\"TelematicsNEW\"}";
	
	public static final String UPDATE_MAKE_SUMMARY = "Update device make";
	public static final String UPDATE_MAKE_DESC = "This is the service to be used to update device make in the system,sample payload: {\"name\":\"PCSNEW\"}";
	
	public static final String CREATE_MODEL_SUMMARY = "Create device model";
	public static final String CREATE_MODEL_DESC = "This is the service to be used to create device model in the system,sample payload: {\"name\":\"FMS\"}";
	
	public static final String UPDATE_MODEL_SUMMARY = "Update device model";
	public static final String UPDATE_MODEL_DESC = "This is the service to be used to update device model in the system,sample payload: {\"name\":\"FMSNEW\"}";
	
	public static final String CREATE_DEVICE_PROTOCOL_SUMMARY = "Create device protocol";
	public static final String CREATE_DEVICE_PROTOCOL_DESC = "This is the service to be used to create device protocol in the system,sample payload: ";
	
	public static final String UPDATE_DEVICE_PROTOCOL_SUMMARY = "Update device protocol";
	public static final String UPDATE_DEVICE_PROTOCOL_DESC = "This is the service to be used to update device protocol in the system,sample payload: ";
	
	public static final String CREATE_DEVICE_PROTOCOL_VERSION_SUMMARY = "Create device protocol version";
	public static final String CREATE_DEVICE_PROTOCOL_VERSION_DESC = "This is the service to be used to create device protocol version in the system,sample payload: ";
	
	public static final String UPDATE_DEVICE_PROTOCOL_VERSION_SUMMARY = "Update device protocol version";
	public static final String UPDATE_DEVICE_PROTOCOL_VERSION_DESC = "This is the service to be used to update device protocol version in the system,sample payload: ";

}
