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
package com.pcs.ccd.enums;

import com.pcs.avocado.commons.validation.DataFields;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Jan 31, 2016
 */
public enum FaultDataFields implements DataFields {

	SOURCE_ID("sourceId", "source_id", "Source Id"),
	SPN("SPN", "SPN", "SPN"),
	FMI("FMI", "FMI", "FMI"),
	OC("OC", "OC", "Occurrence Count"),
	EVENT_STATUS( "eventStatus", "event_status", "Event Status"),
	EVENT_TIMESTAMP("eventTimestamp", "event_timestamp", "Event Timestamp"),
	ENTITY_STATUS("entityStatus", "entity_status", "Entity Status"),
	EVENT_SEND_STATUS("eventSendStatus", "event_send_status","Event Send Status"),
	RESP_RECEIVE_STATUS("respReceiveStatus","resp_receive_status", "RESPONSE Receive Status"),
	OC_CYCLE("ocCycle", "ocCycle", "Occurrence Count Cycle"),
	NOTIFICATION_VERSION("notificationVersion", "notificationVersion","Notification Version"),
	MESSAGE_TYPE("messageType", "messageType","Message Type"),
	DEVICE_ID("deviceId", "deviceId", "Device Id"),
	MAKE("make", "make", "Make"),
	MODEL("model", "model", "Model"),
	ESN("esn", "serialNumber", "Engine Serial Number"),
	ASSET_NAME("assetName", "assetName", "Asset Name"),
	UNIT_NUMBER("unitId", "unitNumber", "Unit Number"),
	VIN("vin","vidNumber", "VID Number"),
	OCC_DATETIME("occuranceDateTime","occuranceDateTime", "Occurance DateTime"),
	ACTIVE("active","active", "Active"),
	DATALINK_BUS("datalinkBus", "datalinkBus", "Datalink Bus"),
	SOURCE_ADDRESS("sourceAddress", "sourceAddress", "Source Address"),
	LATITUDE("latitude", "latitude", "Latitude"),
	LONGITUDE("longitude","longitude", "Longitude"),
	ALTITUDE("altitude", "altitude","Altitude"),
	DIRECTION("direction", "direction", "Direction"),
	SPEED("speed", "speed", "Speed"),
	SNAPSHOTS("snapshots", "snapshots", "Snapshots"),
	SNAPSHOT_DATETIME("timestamp", "timestamp", "Snapshot DateTime Stamp"),
	SNAPSHOT_PARAMETERS("parameters", "parameters", "Snapshot Parameters"),
	SNAPSHOT_PARAMETER_NAME("name", "name", "Snapshot Parameter Name"),
	SNAPSHOT_PARAMETER_VALUE("value", "value", "Snapshot Parameter Value"),
	RAW_IDENTIFIER("identifier", "identifier","Fault Identifier"),
	FAULT_EVENT( "faultEvent", "faultEvent", "Fault Event");

	String fieldName;
	String variableName;
	String description;

	private FaultDataFields(String fieldName, String variableName,
	        String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public String getVariableName() {
		return variableName;
	}

}
