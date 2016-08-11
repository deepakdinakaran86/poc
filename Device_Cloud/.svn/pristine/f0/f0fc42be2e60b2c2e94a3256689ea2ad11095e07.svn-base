package com.pcs.datasource.doc.constants;

public class DeviceResourceConstants extends ResourceConstants {
	
	public static final String GET_DEVICE_DETAILS_SUMMARY = "Get details of the device";
	public static final String GET_DEVICE_DETAILS_DESC = "This is the service to be used to fetch the detail of the device";
	public static final String GET_DEVICE_DETAILS_SUCCESS_RESP = "{ \"sourceId\": \"12345678901213\", \"unitId\": 6, \"isPublishing\": false, \"ip\": \"172.17.19.150\", \"connectedPort\": 8181, \"writeBackPort\": 8185, \"subscription\": { \"subId\": \"OJar1THxAgCZUk3Gf3GXlKTMHWga\", \"subscriber\": \"Cummins\" }, \"networkProtocol\": {\"name\": \"TCP\"}, \"tags\": [], \"version\": { \"make\": \"PCS\", \"deviceType\": \"Edge Device\", \"model\": \"EDCP\", \"protocol\": \"EDCP\", \"version\": \"1.0.0\" } }";

	public static final String INSERT_DEVICE_SUMMARY = "Create a new device";
	public static final String INSERT_DEVICE_DESC = "This is the service to be used to create a new device ,sample payload :{ \"sourceId\": \"458988654987895\", \"isPublishing\": false, \"networkProtocol\": { \"name\": \"TCP\" }, \"version\": { \"make\": \"PCS\", \"deviceType\": \"Edge Device\", \"model\": \"EDCP\", \"protocol\": \"EDCP\", \"version\": \"1.0.0\" } }";

	public static final String UPDATE_DEVICE_SUMMARY = "Update an existing device";
	public static final String UPDATE_DEVICE_DESC = "This is the service to be used to update an existing device,sample payload :{ \"isPublishing\": false, \"networkProtocol\": { \"name\": \"TCP\" }, \"version\": { \"make\": \"PCS\", \"deviceType\": \"Edge Device\", \"model\": \"EDCP\", \"protocol\": \"EDCP\", \"version\": \"1.0.0\" } }";

	public static final String AUTHENTICATE_DEVICE_SUMMARY = "Authenticate a device";
	public static final String AUTHENTICATE_DEVICE_DESC = "This is the service to be used to authenticate a device";
	public static final String AUTHENTICATE_DEVICE_SUCCESS_RESP = "{ \"generalResponse\": { \"status\": \"SUBSCRIBED\", \"remarks\": \"Device is available\" }, \"device\": { \"deviceId\": \"enerwhere\", \"sourceId\": \"356307045266573\", \"configurations\": { \"deviceMake\": \"Teltonika\", \"deviceType\": \"Telematics\", \"deviceModel\": \"FMS\", \"deviceProtocol\": \"FM4100\", \"deviceProtocolVersion\": \"0.0.1\", \"configPoints\": [ { \"pointId\": \"154\", \"pointName\": \"CAN9\", \"type\": \"STRING\", \"displayName\": \"Reactive Power C\", \"physicalQuantity\": \"generic_quantity\", \"systemTag\": \"Power\", \"precedence\": \"5\", \"expression\": \"$\n{\nREACTIVEPOWER;\nif(!(number:isNumber(PhaseCApparentPower) and number:isNumber(PhaseCPowerFactor)))\nreturn 'ERROR';\n\nif(EngineStatus eq 'OFF')\nreturn 0;\nREACTIVEPOWER=((PhaseCApparentPower)*math:sqrt((1-(math:pow(PhaseCPowerFactor,2)))));\nif(!(REACTIVEPOWER ge 0 and REACTIVEPOWER le 9999999999))\nREACTIVEPOWER='ERROR';\nelse\nREACTIVEPOWER=number:formatDouble(REACTIVEPOWER,2);\nREACTIVEPOWER;\n}\" }, { \"pointId\": \"154\", \"pointName\": \"CAN9\", \"type\": \"STRING\", \"displayName\": \"Phase C Power Factor\", \"physicalQuantity\": \"generic_quantity\", \"systemTag\": \"Power\", \"precedence\": \"4\", \"expression\": \"$\n{\nPOWERFACTOR;\nif(!(number:isNumber(PhaseCActivePower) and number:isNumber(PhaseCApparentPower)))\nreturn 'ERROR';\n\nif(EngineStatus eq 'OFF')\nreturn 0;\nPOWERFACTOR=((PhaseCActivePower)/(PhaseCApparentPower));\nif(!(POWERFACTOR ge 0 and POWERFACTOR le 1))\nPOWERFACTOR='ERROR';\nelse\nPOWERFACTOR=number:formatDouble(POWERFACTOR,2);\nPOWERFACTOR;\n}\" } ] }, \"subscription\": { \"subId\": \"1\", \"subscriber\": \"Galaxy\" }, \"tags\": [], \"version\": { \"make\": \"Teltonika\", \"deviceType\": \"Telematics\", \"model\": \"FMS\", \"protocol\": \"FM4100\", \"version\": \"0.0.1\" } } }";
	public static final String AUTHENTICATE_DEVICE_NOT_FOUND = "{\"generalResponse\": { \"status\": \"NOT_AVAIALABLE\", \"remarks\": \"Device not found anywhere\" }}";
	public static final String AUTHENTICATE_DEVICE_UNSUBSCRIBED = "{ \"generalResponse\": { \"status\": \"UNSUBSCRIBED\", \"remarks\": \"Device is available but unsubscribed\" }, \"device\": { \"deviceId\": \"07ee1ad5-ca83-4976-946d-094f697026e6\", \"sourceId\": \"SourceId_Un_050_012\", \"unitId\": 20, \"networkProtocol\": {\"name\": \"UDP\"}, \"tags\": [], \"version\": { \"make\": \"PCS\", \"deviceType\": \"Edge Device\", \"model\": \"EDCP\", \"protocol\": \"EDCP\", \"version\": \"1.0.0\" } } }";

	public static final String GET_DEVICE_CONF_SUMMARY = "Get point configurations of a device";
	public static final String GET_DEVICE_CONF_DESC = "This is the service to be used to fetch point configurations of a device";
	public static final String GET_DEVICE_CONF_SUCCESS_RESP = "{ \"deviceMake\": \"Teltonika\", \"deviceType\": \"Telematics\", \"deviceModel\": \"FMS\", \"deviceProtocol\": \"FM4100\", \"deviceProtocolVersion\": \"0.0.1\", \"configPoints\": [ { \"pointId\": \"154\", \"pointName\": \"CAN9\", \"type\": \"STRING\", \"displayName\": \"Reactive Power C\", \"physicalQuantity\": \"generic_quantity\", \"systemTag\": \"Power\", \"precedence\": \"5\", \"expression\": \"$\n{\nREACTIVEPOWER;\nif(!(number:isNumber(PhaseCApparentPower) and number:isNumber(PhaseCPowerFactor)))\nreturn 'ERROR';\n\nif(EngineStatus eq 'OFF')\nreturn 0;\nREACTIVEPOWER=((PhaseCApparentPower)*math:sqrt((1-(math:pow(PhaseCPowerFactor,2)))));\nif(!(REACTIVEPOWER ge 0 and REACTIVEPOWER le 9999999999))\nREACTIVEPOWER='ERROR';\nelse\nREACTIVEPOWER=number:formatDouble(REACTIVEPOWER,2);\nREACTIVEPOWER;\n}\" }, { \"pointId\": \"154\", \"pointName\": \"CAN9\", \"type\": \"STRING\", \"displayName\": \"Phase C Power Factor\", \"physicalQuantity\": \"generic_quantity\", \"systemTag\": \"Power\", \"precedence\": \"4\", \"expression\": \"$\n{\nPOWERFACTOR;\nif(!(number:isNumber(PhaseCActivePower) and number:isNumber(PhaseCApparentPower)))\nreturn 'ERROR';\n\nif(EngineStatus eq 'OFF')\nreturn 0;\nPOWERFACTOR=((PhaseCActivePower)/(PhaseCApparentPower));\nif(!(POWERFACTOR ge 0 and POWERFACTOR le 1))\nPOWERFACTOR='ERROR';\nelse\nPOWERFACTOR=number:formatDouble(POWERFACTOR,2);\nPOWERFACTOR;\n}\" } ] }";

	public static final String UPDATE_DEVICE_CONF_SUMMARY = "Update point configurations of a device";
	public static final String UPDATE_DEVICE_CONF_DESC = "This is the service to be used to update point configurations of a device,sample payload :[ { \"pointId\": 1, \"pointName\": \"point name 1\", \"configuredName\": \"configured name 1\", \"dataType\": \"Integer\", \"physicalQuantity\":\"Temperature\", \"unit\":\"kelvin\", \"precedence\":\"1\" } ]";

	public static final String UPDATE_DEVICE_CONF_BATCH_SUMMARY = "Update multiple devices with same point configurations";
	public static final String UPDATE_DEVICE_CONF_BATCH_DESC = "This is the service to be used to update point configurations of multiple devices,sample payload :{ \"sourceIds\": [ \"SourceId_050_011\", \"SourceId_050_012\" ], \"confTemplate\": { \"deviceMake\": \"PCS\", \"deviceType\": \"Edge Device\", \"deviceModel\": \"EDCP\", \"deviceProtocol\": \"EDCP\", \"deviceProtocolVersion\": \"1.0.0\", \"configPoints\": [ { \"pointId\": 1, \"pointName\": \"point name 1\", \"displayName\": \"display name 1\", \"dataType\": \"Integer\", \"physicalQuantity\": \"temperature\", \"precedence\": \"1\" } ] } }";
	public static final String UPDATE_DEVICE_CONF_BATCH_SUCCESS_RESP = "{\"responses\": [{ \"reference\": \"SourceId_Un_050_011\", \"status\": \"SUCCESS\" }]}";
	public static final String UPDATE_DEVICE_CONF_BATCH_INVALID_RESP = "{\"responses\": [{ \"reference\": \"SourceId_Un_050_1011\", \"status\": \"FAILURE\", \"remarks\": \"Source Id is Invalid\" }]}";
	
	public static final String ASSIGN_DEVICE_CONF_TEMPLATE_SUMMARY = "Assign multiple devices with same point configuration template";
	public static final String ASSIGN_DEVICE_CONF_TEMPLATE_DESC = "This is the service to be used to assign or update point configuration template of multiple devices,sample payload :{ \"templateName\":\"Template3\",\"sourceIds\": [\"5300Device2\", \"12312310988\"]}";
	public static final String ASSIGN_DEVICE_CONF_TEMPLATE_SUCCESS_RESP = "{\"responses\": [{ \"reference\": \"5300Device2\", \"status\": \"SUCCESS\" }]}";
	public static final String ASSIGN_DEVICE_CONF_TEMPLATE_INVALID_RESP = "{\"responses\": [{ \"reference\": \"SourceId_Un_050_1011\", \"status\": \"FAILURE\", \"remarks\": \"Source Id is Invalid\" }]}";

	public static final String GET_DEVICES_SUMMARY = "Get all the devices of a subscriber";
	public static final String GET_DEVICES_DESC = "This is the service to be used to fetch all the devices of a subscriber";
	public static final String GET_DEVICES_SUCCESS_RESP = "[ { \"sourceId\": \"1234567890123456\", \"unitId\": 3, \"isPublishing\": true, \"datasourceName\": \"test device 11\", \"ip\": \"10.234.60.39\", \"connectedPort\": 9443, \"writeBackPort\": 9445, \"networkProtocol\": { \"name\": \"TCP\" }, \"tags\": [ { \"name\": \"firmwareupdated\" }, { \"name\": \"testdevice\" } ], \"version\": { \"make\": \"PCS\", \"deviceType\": \"Edge Device\", \"model\": \"EDCP\", \"protocol\": \"EDCP\", \"version\": \"1.2.0\" } }, { \"sourceId\": \"167469062861202\", \"unitId\": 27, \"isPublishing\": false, \"ip\": \"10.236.60.13\", \"connectedPort\": 8976, \"writeBackPort\": 8185, \"networkProtocol\": { \"name\": \"TCP\" }, \"tags\": [], \"version\": { \"make\": \"PCS\", \"deviceType\": \"Edge Device\", \"model\": \"EDCP\", \"protocol\": \"EDCP\", \"version\": \"1.0.0\" } } ]";

	public static final String GET_DEVICES_FILTER_TAGS_SUMMARY = "Get all the devices of a subscriber filtered with tags";
	public static final String GET_DEVICES_FILTER_TAGS_DESC = "This is the service to be used to fetch all the devices of a subscriber filtered with tags(optional), sample payload(optional) :[ \"LogBridge\", \"Apple\" ]";

	public static final String GET_DEVICES_FILTER_PROTOCOL_SUMMARY = "Get all the devices of a subscriber filtered with protocol";
	public static final String GET_DEVICES_FILTER_PROTOCOL_DESC = "This is the service to be used to fetch all the devices of a subscriber filtered with protocol,sample payload :{ \"make\": \"PCS\", \"deviceType\": \"Edge Device\", \"model\": \"EDCP\", \"protocol\": \"EDCP\", \"version\": \"1.0.0\" }";

	public static final String REGISTER_DEVICE_SUMMARY = "Register a new device";
	public static final String REGISTER_DEVICE_DESC = "This is the service to be used to register a new device,sample payload : { \"sourceId\": \"458988654987895\", \"deviceName\": \"BigBus-458988654987895\", \"isPublishing\": false, \"networkProtocol\": { \"name\": \"TCP\" }, \"version\": { \"make\": \"PCS\", \"deviceType\": \"Edge Device\", \"model\": \"EDCP\", \"protocol\": \"EDCP\", \"version\": \"1.0.0\" } }";

	public static final String UPDATE_DEVICE_WB_CONF_SUMMARY = "Update wite back configurations of a device";
	public static final String UPDATE_DEVICE_WB_CONF_DESC = "This is the service to be used to update write back configurations of a device,sample payload :{ \"ip\": \"10.234.31.146\", \"connectedPort\": 1212, \"writeback_port\": 5654 }";

	public static final String GET_DATASOURCE_NAME_SUMMARY = "Get Datasource Name of a device";
	public static final String GET_DATASOURCE_NAME_DESC = "This is the service to be used to fetch the Datasource Name of a device";
	public static final String GET_DATASOURCE_NAME_SUCCESS_RESP = "{\"datasourceName\": \"SourceId_Un_050_013\"}";

	public static final String UPDATE_TAGS_SUMMARY = "Update tags of a device";
	public static final String UPDATE_TAGS_DESC = "This is the service to be used to update tags of a device,,sample payload :[ { \"name\": \"tag1\" }, { \"name\": \"tag2\" } ]";

	public static final String CLAIM_DEVICE_SUMMARY = "Claim a device";
	public static final String CLAIM_DEVICE_DESC = "This is the service to be used to claim an unclaimed device ";

	public static final String GET_UNSUBSCRIBED_DEVICES_SUMMARY = "Get all unclaimed devices";
	public static final String GET_UNSUBSCRIBED_DEVICES_DESC = "This is the service to be used to fetch all the unclaimed devices";
	public static final String GET_UNSUBSCRIBED_SUCCESS_RESP = "[ { \"deviceId \":  \"9f6f6f73-68ed-455c-8511-602604b76456 \", \"sourceId \":  \"897654321012349 \", \"unitId \": 15, \"isPublishing \": false, \"ip \":  \"10.236.60.13 \", \"connectedPort \": 8976, \"writeBackPort \": 8185, \"networkProtocol \": { \"name \":  \"TCP \"},   \"tags \": [], \"version \":{ \"make \":  \"Teltonika \", \"deviceType \":  \"Telematics \", \"model \":  \"FMS \", \"protocol \":  \"FM5300 \", \"version \":  \"1.0.0 \"} ]";
	
	public static final String GET_ALL_DEVICES_SUMMARY = "Get all the devices in DeviceCloud (no subscription)";
	public static final String GET_ALL_DEVICES_DESC = "This is the service to be used to fetch all the devices in DeviceCloud (no subscription)";
	public static final String GET_ALL_DEVICES_SUCCESS_RESP = "[]";

}
