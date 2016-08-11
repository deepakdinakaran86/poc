package com.pcs.datasource.doc.constants;

public class G2021ResourceConstants extends ResourceConstants {

	public static final String G2021_PROCESS_COMMAND_SUMMARY = "Process G2021 Writeback Commands";
	public static final String G2021_PROCESS_COMMAND_DESC = "This is the service to be used to invoke write back on G2021 devices,sample payload:[ { \"command\": \"Point Write Command\", \"pointId\": 21, \"value\": \"21.5\", \"customSpecs\": { \"priority\": 5 } } ]";
	public static final String G2021_PROCESS_COMMAND_SUCCESS_RESPONSE = "";
	public static final String G2021_PROCESS_COMMAND_FAILURE = "{ \"sourceId\": \"SourceId_Un_050_011\", \"writeBackResponses\": [ { \"command\": \"Point Write Command\", \"status\": \"FAILURE\", \"pointId\": 21, \"value\": \"21.5\", \"remarks\": \"PointId is invalid\", \"customSpecs\": {\"priority\": \"5\"} }] }";
}
