
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.ccd.doc.constants;


/**
 * This class is responsible for defining all constants for fault event resource
 * 
 * @author pcseg129(Seena Jyothish)
 * Feb 1, 2016
 */
public class FaultEventResourceConstants extends ResourceConstants {

	public static final String IS_FAULT_EVENT_EXISTS_SUMMARY = "Fault event already exists or not";
	
	public static final String IS_FAULT_EVENT_EXISTS_DESC = "This is the service to be used to check a fault event is already exists or not,sample payload :{}";
	
	public static final String GET_LATEST_FAULT_EVENT_IF_EXISTS_SUMMARY = "Return the latest fault event if the fault event already exists";
	
	public static final String GET_LATEST_FAULT_EVENT_IF_EXISTS_DESC = "This is the service to be used to get the latest fault event data if the fault event already exists ,sample payload :{\"keyValues\":[{\"key\":\"sourceId\",\"value\":\"103\"},{\"key\":\"assetName\",\"value\":\"jeep\"},{\"key\":\"SPN\",\"value\":\"3667\"},{\"key\":\"FMI\",\"value\":\"31\"},{\"key\":\"eventStatus\",\"value\":\"NORMAL\"}],\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"identifier\":null}";
	
	public static final String PERSIST_FAULT_EVENT_PAYLOAD = "Persist Fault Event Payload";
	
	public static final String PERSIST_FAULT_EVENT_SUMMARY = "Persist fault event";
	
	public static final String PERSIST_FAULT_EVENT_DESC = "This is the service to be used to persist fault event ,sample payload :{ \"SPN\": \"3667\", \"FMI\": \"31\", \"Occurrence_Count\": \"5\", \"assetName\": \"jeep\", \"faultDataInfo\": { \"make\": \"CMMNS\", \"model\": \"ISX15 CM2350 X101\", \"unitNumber\": \"0000000000\", \"Notification_Version\": \"1.00.001\", \"Message_Type\": \"SS\", \"Telematics_Box_ID\": \"103\", \"Engine_Serial_Number\": \"jeep\", \"VIN\": \"TESTBENCH56VIN \", \"Occurrence_Date_Time\": \"1444917999000\", \"Sent_Date_Time\": null, \"Active\": \"NORMAL\", \"Datalink_Bus\": \"J1939\", \"Source_Address\": \"0\", \"Latitude\": \"0.000000\", \"Longitude\": \"0.000000\", \"Altitude\": \"0.000000\", \"Direction_Heading\": null, \"GPS_Vehicle_Speed\": \"100.000000\", \"Snapshots\": [ { \"Snapshot_DateTimestamp\": \"Oct/15/2015 18:06:25\", \"Parameter\": [ { \"Name\": \"SPN969\", \"Value\": \"3.00\" }, { \"Name\": \"SPN91\", \"Value\": \"0.00\" }, { \"Name\": \"SPN97\", \"Value\": \"0.00\" }, { \"Name\": \"SPN3301\", \"Value\": \"0.00\" }, { \"Name\": \"SPN544\", \"Value\": \"3085.00\" } ] } ] }, \"ocCycle\": 0, \"readOnlyRawIdentifier\": null, \"eventSendStatus\": \"SEND\", \"respReceiveStatus\": \"NOT_RECEIVED\", \"entityStatus\": null, \"spn\": \"3667\", \"fmi\": \"31\", \"oc\": \"5\" }";
	
	public static final String UPDATE_FAULT_EVENT_SUMMARY = "Update fault event";
	
	public static final String UPDATE_FAULT_EVENT_DESC = "This is the service to be used to update fault event transient status,sample payload :{ \"SPN\": \"3667\", \"FMI\": \"31\", \"Occurrence_Count\": \"5\", \"assetName\": \"jeep\", \"faultDataInfo\": { \"make\": \"CMMNS\", \"model\": \"ISX15 CM2350 X101\", \"unitNumber\": \"0000000000\", \"Notification_Version\": \"1.00.001\", \"Message_Type\": \"SS\", \"Telematics_Box_ID\": \"103\", \"Engine_Serial_Number\": \"jeep\", \"VIN\": \"TESTBENCH56VIN \", \"Occurrence_Date_Time\": \"1444917999000\", \"Sent_Date_Time\": null, \"Active\": \"NORMAL\", \"Datalink_Bus\": \"J1939\", \"Source_Address\": \"0\", \"Latitude\": \"0.000000\", \"Longitude\": \"0.000000\", \"Altitude\": \"0.000000\", \"Direction_Heading\": null, \"GPS_Vehicle_Speed\": \"100.000000\", \"Snapshots\": [ { \"Snapshot_DateTimestamp\": \"Oct/15/2015 18:06:25\", \"Parameter\": [ { \"Name\": \"SPN969\", \"Value\": \"3.00\" }, { \"Name\": \"SPN3251\", \"Value\": \"6527.90\" }, { \"Name\": \"SPN3246\", \"Value\": \"0.00\" }, { \"Name\": \"SPN3301\", \"Value\": \"0.00\" }, { \"Name\": \"SPN544\", \"Value\": \"3085.00\" } ] } ] }, \"ocCycle\": 0, \"readOnlyRawIdentifier\": \"8e4b240f-01f2-469b-b33f-ad5d282c8d45\", \"eventSendStatus\": \"SEND\", \"respReceiveStatus\": \"NOT_RECEIVED\", \"entityStatus\": \"ALLOCATED\", \"spn\": \"3667\", \"fmi\": \"31\", \"oc\": \"5\" }";
	
	public static final String GET_FAULT_EVENT_DATA_SUMMARY = "Get fault event data";
	
	public static final String GET_FAULT_EVENT_DATA_DESC = "This is the service to be used to get fault events data ,sample payload :[{ \"sourceId\": \"103\", \"assetName\": \"jeep\", \"eventStatus\": \"NORMAL\", \"eventTimestamp\": 1444917999000, \"eventSendStatus\": \"SEND\", \"respReceiveStatus\": \"NOT_RECEIVED\", \"ocCylcle\": 0, \"identifier\": \"f015e6cb-f720-43a8-8486-8aa0f1fda8f6\", \"fmi\": \"31\", \"spn\": \"3667\", \"oc\": 1 }]";
	
	public static final String GET_ALL_TENANT_FAULT_EVENT_DATA_SUMMARY = "Get all fault events of a tenant ";
	
	public static final String GET_ALL_TENANT_FAULT_EVENT_DATA_DESC = "This is the service to be used to get all fault events of a tenant ,sample payload : { \"tenantName\": \"Metito1\" }";
	
	public static final String GET_ALL_TENANT_FAULT_EVENT_DATA_SUCCESS_RESP = "[ { \"sourceId\": \"103\", \"assetName\": \"E432-E\", \"eventStatus\": \"NORMAL\", \"eventTimestamp\": 1444917999000, \"eventSendStatus\": \"SEND\", \"respReceiveStatus\": \"NOT_RECEIVED\", \"ocCylcle\": 1, \"identifier\": \"f7630c0d-06d2-4f04-81a8-678236158166\", \"fmi\": \"31\", \"spn\": \"3667\", \"oc\": 1 }, { \"sourceId\": \"412526763919\", \"assetName\": \"E432-Ext-13\", \"eventStatus\": \"NORMAL\", \"eventTimestamp\": 1444917999000, \"eventSendStatus\": \"SEND\", \"respReceiveStatus\": \"NOT_RECEIVED\", \"ocCylcle\": 0, \"identifier\": \"1d82fbac-a7b3-4577-934f-b937467b57af\", \"fmi\": \"31\", \"spn\": \"3667\", \"oc\": 1 } ]";
	
	public static final String GET_FAULT_EVENT_RESPONSE_SUMMARY = "Get fault event response received from CCD";
	
	public static final String GET_FAULT_EVENT_RESPONSE_DESC = "This is the service to be used to get fault event's response send from CCD ,sample payload : { \"fault_event_id\": \"cd5af73f-2779-484b-a043-52b43abcfbd7\" }";

}
