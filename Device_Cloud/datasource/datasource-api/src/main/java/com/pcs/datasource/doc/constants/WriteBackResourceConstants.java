package com.pcs.datasource.doc.constants;

public class WriteBackResourceConstants extends ResourceConstants {

	public static final String GET_WB_LOGS_SUMMARY = "Get write back logs of a subscriber for a specific period";
	public static final String GET_WB_LOGS_DESC = "This is the service to be used to fetch all write back logs of a subscriber for a specific period with an optional filter on devices , sample payload :[\"F25978BB-8C86-4B3B-B2BB-36E9F5E6DFBE\"]";
	public static final String GET_WB_LOGS_SUCCESS_RESP = "[{\"command\":{\"command\":\"SYSTEM_COMMAND\",\"writeBackId\":1,\"requestedAt\":1465201536463,\"customSpecsJSON\":\"{\\\"commandCode\\\":\\\"Reboot\\\"}\",\"status\":\"QUEUED\"},\"device\":{\"sourceId\":\"F25978BB-8C86-4B3B-B2BB-36E9F5E6DFBE\"}},{\"command\":{\"command\":\"WRITE_COMMAND\",\"writeBackId\":2,\"requestedAt\":1465201537191,\"value\":\"21.5\",\"dataType\":\"FLOAT\",\"pointId\":2,\"pointName\":\"FreeSpace\",\"customSpecsJSON\":\"{\\\"priority\\\":\\\"5\\\"}\",\"status\":\"QUEUED\"},\"device\":{\"sourceId\":\"F25978BB-8C86-4B3B-B2BB-36E9F5E6DFBE\"}}]";

	public static final String GET_WB_LOGS_GRAPH_SUMMARY = "Get write back logs of a subscriber for a specific period(graph)";
	public static final String GET_WB_LOGS_GRAPH_DESC = "This is the service to be used to fetch all write back logs of a subscriber for a specific period,this will be in the form of a graph,ie.,with relations";
	public static final String GET_WB_LOGS_GRAPH_SUCCESS_RESP = "[{\"graph\":{\"nodes\":[{\"id\":\"176\",\"labels\":[\"BATCH\"],\"properties\":{\"batch_id\":176,\"requested_at\":1432808718163}},{\"id\":\"178\",\"labels\":[\"COMMAND\"],\"properties\":{\"custom_specs\":\"{\\\"commandCode\\\":\\\"Reboot\\\"}\",\"request_id\":3,\"requested_at\":1432808718318,\"status\":\"QUEUED\",\"command\":\"Server Command\",\"custom_info\":\"{\\\"user\\\":\\\"javid\\\"}\"}},{\"id\":\"15\",\"labels\":[\"DEVICE\"],\"properties\":{\"is_publishing\":true,\"source_id\":\"351756051523999\",\"unit_id\":1,\"device_id\":\"025bdbc8-dfde-4090-9ea5-9431c591655f\",\"datasource_name\":\"351756051523999\"}}],\"relationships\":[{\"id\":\"189\",\"startNode\":\"15\",\"properties\":{},\"type\":\"executed\",\"endNode\":\"176\"},{\"id\":\"191\",\"startNode\":\"176\",\"properties\":{},\"type\":\"includes\",\"endNode\":\"178\"}]}},{\"graph\":{\"nodes\":[{\"id\":\"176\",\"labels\":[\"BATCH\"],\"properties\":{\"batch_id\":176,\"requested_at\":1432808718163}},{\"id\":\"177\",\"labels\":[\"COMMAND\"],\"properties\":{\"custom_specs\":\"{\\\"priority\\\":\\\"5\\\"}\",\"request_id\":2,\"requested_at\":1432808718247,\"status\":\"QUEUED\",\"data_type\":\"BOOLEAN\",\"value\":\"true\",\"command\":\"WRITE_COMMAND\",\"point_id\":18}},{\"id\":\"15\",\"labels\":[\"DEVICE\"],\"properties\":{\"is_publishing\":true,\"source_id\":\"351756051523999\",\"unit_id\":1,\"device_id\":\"025bdbc8-dfde-4090-9ea5-9431c591655f\",\"datasource_name\":\"351756051523999\"}}],\"relationships\":[{\"id\":\"189\",\"startNode\":\"15\",\"properties\":{},\"type\":\"executed\",\"endNode\":\"176\"},{\"id\":\"190\",\"startNode\":\"176\",\"properties\":{},\"type\":\"includes\",\"endNode\":\"177\"}]}},{\"graph\":{\"nodes\":[{\"id\":\"175\",\"labels\":[\"COMMAND\"],\"properties\":{\"custom_specs\":\"{\\\"commandCode\\\":\\\"App Save\\\"}\",\"request_id\":1,\"requested_at\":1432808681293,\"status\":\"QUEUED\",\"command\":\"Server Command\",\"custom_info\":\"{\\\"user\\\":\\\"javid\\\"}\"}},{\"id\":\"15\",\"labels\":[\"DEVICE\"],\"properties\":{\"is_publishing\":true,\"source_id\":\"351756051523999\",\"unit_id\":1,\"device_id\":\"025bdbc8-dfde-4090-9ea5-9431c591655f\",\"datasource_name\":\"351756051523999\"}}],\"relationships\":[{\"id\":\"188\",\"startNode\":\"15\",\"properties\":{},\"type\":\"executed\",\"endNode\":\"175\"}]}}]";

	public static final String GET_ALL_QUEUED_COMMANDS_SUMMARY = "Get all the queued commands of a device";
	public static final String GET_ALL_QUEUED_COMMANDS_DESC = "This is the service to be used to fetch all the queued commands of a device";
	public static final String GET_ALL_QUEUED_COMMANDS_SUCCESS_RESP = "[{\"command\":\"WRITE_COMMAND\",\"writeBackId\":2,\"requestedAt\":1465201537191,\"value\":\"21.5\",\"dataType\":\"FLOAT\",\"pointId\":2,\"pointName\":\"FreeSpace\",\"customSpecsJSON\":\"{\\\"priority\\\":\\\"5\\\"}\",\"status\":\"QUEUED\"}]";

	public static final String UPDATE_WB_SUMMARY = "Update Writeback Response";
	public static final String UPDATE_WB_DESC = "This is the service to be used to update writeback response,sample payload:{ \"requestId\":4, \"status\" :\"FAILURE\", \"remarks\":\"Error in connecting to write back port\", \"time\":1435689898559 }";

	public static final String GENERIC_PROCESS_COMMAND_SUMMARY = "Process generic Writeback Commands";
	public static final String GENERIC_PROCESS_COMMAND_DESC = "This is the service to be used to invoke write back on devices,sample payload:[{\"sourceId\":\"F25978BB-8C86-4B3B-B2BB-36E9F5E6DFBE\",\"payload\":{\"command\":\"SYSTEM_COMMAND\",\"customSpecs\":{\"commandCode\":\"Reboot\"}}},{\"sourceId\":\"F25978BB-8C86-4B3B-B2BB-36E9F5E6DFBE\",\"payload\":{\"command\":\"WRITE_COMMAND\",\"pointId\":2,\"pointName\":\"FreeSpace\",\"value\":\"21.5\",\"customSpecs\":{\"priority\":5}}}]";
	public static final String GENERIC_PROCESS_COMMAND_SUCCESS_RESPONSE = "[{\"sourceId\":\"F25978BB-8C86-4B3B-B2BB-36E9F5E6DFBE\",\"writeBackId\":1,\"payload\":{\"command\":\"SYSTEM_COMMAND\",\"customSpecs\":{\"commandCode\":\"Reboot\"}},\"status\":\"QUEUED\",\"requestedAt\":1465201536463},{\"sourceId\":\"F25978BB-8C86-4B3B-B2BB-36E9F5E6DFBE\",\"writeBackId\":2,\"payload\":{\"command\":\"WRITE_COMMAND\",\"value\":\"21.5\",\"dataType\":\"FLOAT\",\"pointId\":2,\"pointName\":\"FreeSpace\",\"customSpecsJSON\":\"{\\\"priority\\\":\\\"5\\\"}\"},\"status\":\"QUEUED\",\"requestedAt\":1465201537191}]";
	public static final String GENERIC_PROCESS_COMMAND_FAILURE = "[{\"sourceId\":\"F25978BB-8C86-4B3B-B2BB-36E9F5E6DFBE1\",\"payload\":{\"command\":\"SYSTEM_COMMAND\",\"customSpecs\":{\"commandCode\":\"Reboot\"}},\"status\":\"FAILURE\",\"remarks\":\"Source Id is invalid\"},{\"sourceId\":\"F25978BB-8C86-4B3B-B2BB-36E9F5E6DFBE\",\"payload\":{\"command\":\"WRITE_COMMAND\",\"value\":\"21.5\",\"pointId\":2,\"pointName\":\"FreeSpace\",\"customSpecs\":{\"priority\":\"50\"}},\"status\":\"FAILURE\",\"remarks\":\"Prioriry is invalid\"}]";

}
