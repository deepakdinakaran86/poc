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
package com.pcs.datasource.enums;

import com.pcs.devicecloud.commons.validation.DataFields;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author PCSEG129
 */
public enum DeviceConfigFields implements DataFields {

	CONFIG_TEMP_NAME("name", "name", "Configuration Template Name"),

	DEVICE_TYPE("device_type", "deviceType", "Device Type"),

	DEVICE_MAKE("make", "deviceMake", "Device Make"),

	DEVICE_MODEL("model", "deviceModel", "Device Model"),

	DEVICE_PROTOCOL("protocol", "deviceProtocol", "Protocol"),

	DEVICE_PROTOCOL_VERSION("device_protocol_version", "deviceProtocolVersion",
			"Device Protocol Version"),
 PROTOCOL_VERSION("version", "version",
					"Device Protocol Version"),

	PARAMETER("parameter", "parameter", "Parameter"),

	CONFIG_TEMPLATE("conf_template", "confTemplate", "Configuration Template"),

	CONFIG_POINTS("config_points", "configPoints", "Config Points"),
	
    POINT_EXT("extensions", "extensions", "Point extesions"),
	
	POINT_ALARM_EXT("alarmExtensions", "alarmExtensions", "Point alarm extesions"),
	
	POINT_ID("pointId", "pointId", "Point Id"),
	
	POINT_NAME("pointName", "pointName", "Point Name"),
	
	POINT_DATA_TYPE("type", "type", "Point Data Type"),
	
	DATA_TYPE("dataType", "dataType", "Data Type"),
	
	UNIT("unit", "unit", "Unit"),
	
	PHYSICAL_QUANTITY("physicalQuantity", "physicalQuantity", "Physical Quantity"),
	
	PRECEDENCE("precedence", "precedence", "Point Precedence"),
	
	POINT_EXPRESSION("expression", "expression", "Point expression"),
	
	DISPLAY_NAME("displayname", "displayName", "Display Name")
	;

	private String fieldName;
	private String variableName;
	private String description;

	private DeviceConfigFields(String fieldName, String variableName,
			String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
