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
package com.pcs.avocado.enums;

import com.pcs.avocado.commons.validation.DataFields;

/**
 * DeviceDataFields Enum
 * 
 * @author PCSEG199 (Javid Ahammed)
 * 
 */
public enum DeviceDataFields implements DataFields {

	DOMAIN("domain", "domain", "Domain"),

	ENTITY_TEMPLATE("entityTemplate", "entityTemplate", "Entity Template"),

	PLATFORM_ENTITY("platformEntity", "platformEntity", "Platform Entity"),

	IDENTIFIER("identifier", "identifier", "Identifier"),

	MODE("mode", "mode", "Mode"),

	DATA_SOURCES_LIST("", "", "Data Source List"),

	DEVICES("devices", "devices", "Devices"),

	CLIENT("client", "client", "Client"),

	SOURCE_ID("sourceId", "sourceId", "Source Id"),

	DEVICE_NAME("deviceName", "deviceName", "Device Name"),

	DEVICE_MAKE("make", "make", "Make"),

	TYPE("deviceType", "deviceType", "Device Type"),

	DEVICE_MODEL("model", "model", "Model"),

	DEVICE_PROTOCOL("protocol", "protocol", "Protocol"),

	DEVICE_VERSION("version", "version", "Version"),

	PARENT("parent", "parent", "Parent"),

	OWNER("owner", "owner", "Owner"),

	CLINT_DEVICES("clientDevices", "clientDevices", "Client Devices"),

	NW_PROTOCOL("nwProtocol", "nwProtocol", "Network Protocol");

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
