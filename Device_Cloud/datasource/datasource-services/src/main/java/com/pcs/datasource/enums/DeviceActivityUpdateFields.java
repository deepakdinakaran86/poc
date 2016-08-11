/**
 * 
 */
package com.pcs.datasource.enums;

import com.pcs.devicecloud.commons.validation.DataFields;

/**
 * @author pcseg129
 *
 */
public enum DeviceActivityUpdateFields implements DataFields {
	
	DEVICE_ID("device", "deviceId", "Device Id"),
	DATASOURCE_NAME("datasourcename", "datasourceName", "Datasource Name"),
	DEVICE_NAME("devicename", "deviceName", "Device Name"),
	LAST_UPDATED_TIME("lastupdatedtime", "lastupdatedTime", "Last Updated Time"),
	LAST_OFFLINE_TIME("lastofflinetime", "lastofflineTime", "Last Offline Time"),
	LAST_ONLINE_TIME("lastonlinetime", "lastonlineTime", "Last Online Time"),
	STATUS("status", "status", "Device Status"),
	DEVICE_STATUS_LIST("deviceStatusList", "deviceStatusList", "Device Status List");
	
	private String fieldName;
	private String variableName;
	private String description;
	
	private DeviceActivityUpdateFields(String fieldName, String variableName,
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
