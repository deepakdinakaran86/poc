
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
package com.pcs.ccd.enums;

import com.pcs.avocado.commons.validation.DataFields;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Feb 8, 2016
 */
public enum EquipmentFields implements DataFields{
	
	EQUIPMENTS("equipments", "equipments", "Equipments"),
	EQUIPMENT("equipment", "equipment", "Equipment"),
	DEVICE_ID("device_id", "deviceId", "Device Id"),
	SOURCE_ID("source_id", "sourceId", "Source Id"),
	ASSET_NAME("assetName", "assetName", "Asset Name"),
	ASSET_TYPE("assetType", "assetType", "Asset Type"),
	ENGINE_MAKE("engineMake", "engineMake", "Engine Make"),
	ENGINE_MODEL("engineModel", "engineModel", "Engine Model"),
	ROW_IDENTIFIER("rowIdentifier", "rowIdentifier", "Equipment Row Identifier"),
	EQUIPMENT_ID("equipmentId", "equipmentId", "Equipment Id"),
	EQUIPMENT_IDS("equipmentIds", "equipmentIds", "Equipment Ids"),
	ASSET_ID("assetId", "assetId", "Asset Id"),
	ASSET_SERIAL_NO("serialNumber", "serialNumber", "Asset Serial Number");
	
	String fieldName;
	String variableName;
	String description;
	
	private EquipmentFields(String fieldName, String variableName,
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
