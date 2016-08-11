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
 * AssetDataFields Enum
 * 
 * @author Twinkle (PCSEG297)
 * 
 */
public enum AssetDataFields implements DataFields {

	CATEGORY_NAME("", "", "Category Name"),

	ENTITY_TEMPLATE_NAME("", "", "Entity Template Name"),

	DOMAIN_NAME("domainName", "domainName", "Domain Name"),

	ASSET_TYPE("assetType", "assetType", "Asset Type"),

	STATUS("status", "status", "Status"),

	ASSET_NAME("assetName", "assetName", "Asset_Name"),

	DESCRIPTION("description", "description", "Description"),

	IDENTIFIER("identifier", "identifier", "Identifier"),

	KEY("key", "key", "Key"),

	ASSET_TYPE_VALUES("assetTypeValues", "assetTypeValues", "Asset Type Values"),

	ASSET_IDENTIFIER("assetIdentifier", "assetIdentifier", "Asset Identifier"),

	ASSET_TYPE_IDENTIFIER("assetTypeIdentifier", "assetTypeIdentifier",
	        "Asset Type Identifier"),

	ASSET_TEMPLATE("Asset", "Asset", "Asset"), ASSET_ID("assetId", "assetId",
	        "Asset Id"), SERIAL_NUMBER("serialNumber", "serialNumber",
	        "Serial Number"), EQUIP_IDENTIFIER("equipIdentifier",
	        "equipIdentifier", "Equip Identifier"), EQUIP_TEMPLATE(
	        "equipTemplate", "equipTemplate", "Equip Template"), DS_POINT_NAME(
	        "", "", "Datasource and display name");

	private String fieldName;
	private String variableName;
	private String description;

	private AssetDataFields(String fieldName, String variableName,
	        String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public String getVariableName() {
		return variableName;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
