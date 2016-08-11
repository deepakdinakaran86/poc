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
package com.pcs.galaxy.enums;

import com.pcs.avocado.commons.validation.DataFields;

/**
 * ConfigurationId
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
public enum DeviceDataFields implements DataFields {

	CONFIG_ID("configId", "configId", "Config Id"),
	SOURCE_ID("sourceId", "sourceId", "Source Id"),
	POINT_ID("pointId", "pointId", "Point Id"),
	DISPLAY_NAME("displayName", "displayName", "Display Name"),
	POINTNAME("pointName", "pointName", "Point Name"),
	DEVICE_ID("deviceId", "deviceId", "Device Id"),
	ASSET_ID("assetId", "assetId", "Asset Id"),
	
	ASSET_NAME("assetName", "assetName", "Asset Name"),
	
	UNIT("unit", "unit", "Unit"),
	MIN_VAL("minVal", "minVal", "Min Val"),
	MAX_VAL("maxVal", "maxVal", "Max Val"),
	
	LOWER_RANGE("lowerRange", "lowerRange", "Lower Range"),
	UPPER_RANGE("upperRange", "upperRange", "Upper Range"),
	ALARM_NAME("alarmName", "alarmName", "Alarm Name"),
	ALARM_TYPE("alarmType", "alarmType", "Alarm Type"),
	ALARM_ATTRIBUTE("alarmAttribute", "alarmAttribute", "Alarm Attribute"),
	
	
	MESSAGE("message", "message", "Message"),
	MIN_ALARM_MSG("minAlarmMsg", "minAlarmMsg", "Min Alarm Msg"),
	MAX_ALARM_MSG("maxAlarmMsg", "maxAlarmMsg", "Max Alarm Msg"),
	RANGE_ALARM_MSG("rangeAlarmMsg", "rangeAlarmMsg", "Range Alarm Msg"),
	STATUS("status", "status", "Status"),
	ENABLE("enabled", "enabled", "Enabled"),
	TYPE("type", "type", "Type"),
	GEOCOORDINATES("geocoordinates", "geocoordinates", "Geocoordinates"),
	COORDINATES("coordinates", "coordinates", "Coordinates"),
	RADIUS("radius", "radius", "Radius");
	
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
