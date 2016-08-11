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
public enum EquipmentDataFields implements DataFields {

	DOMAIN("domain", "domain", "Domain"),

	ENTITY_TEMPLATE("entityTemplate", "entityTemplate", "Entity Template"),

	PLATFORM_ENTITY("platformEntity", "platformEntity", "Platform Entity"),

	IDENTIFIER("identifier", "identifier", "Identifier"),

	TYPE("type", "type", "Type"),

	TEMPLATE_NAME("templateName", "templateName", "Template Name"),

	FACILITY("facility", "facility", "Facility"),

	POINTS("points", "points", "Points"),

	FIELD_VALUES("fieldValues", "fieldValues", "Field Values"),

	IDENTITY("identity", "identity", "Identity"),

	SOURCE_ID("sourceId", "sourceId", "Source Id"), 
	
	EQUIPMENT("equipment", "equipment", "Equipment"),
	EQUIP_IDENTIFIER_KEY("key", "key", "Equipment Identifier Key"),

	EQUIP_IDENTIFIER_VALUE("value","value", "Equipment Identifier Value"),

	EQUIP_ENTITY_TEMPLATE_NAME("entity_template_name", "entityTemplateName",
					"Equipment Entity Template Name"),
					
	EQUIP_ENTITY_TEMPLATE("entityTemplate", "entityTemplate", "Equipment Entity Template"),
	
	EQUIP_IDENTIFIER("identifier", "identifier", "Equipment Identifier"),
	
	DEVICE_IDENTIFIER_KEY("key", "key", "Device Identifier Key"),

	DEVICE_IDENTIFIER_VALUE("value","value", "Device Identifier Value"),

	DEVICE_ENTITY_TEMPLATE_NAME("entity_template_name", "entityTemplateName",
					"Device Entity Template Name"),
	DEVICE_ENTITY_TEMPLATE("entityTemplate", "entityTemplate", "Device Entity Template"),
	
	DEVICE_IDENTIFIER("identifier", "identifier", "Device Identifier"),
					
	EQUIP_ID("equipmentId","equipmentId", "Equipment Id"),
	
	FACILITY_NAME("facilityName","facilityName", "Facility Name"),
	
	TENANT_NAME("tenantName","tenantName", "Tenant Name"),
	
	POINT_IDENTIFIER_KEY("key", "key", "Point Identifier Key"),

	POINT_IDENTIFIER_VALUE("value","value", "Point Identifier Value");

	private String fieldName;
	private String variableName;
	private String description;

	private EquipmentDataFields(String fieldName, String variableName,
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
