package com.pcs.datasource.enums;

import com.pcs.devicecloud.commons.validation.DataFields;

public enum PQDataFields implements DataFields {

	PHY_QUANTITY("physical_quantity", "phyQuantity", "Physical Quantity"),

	ID("uuid", "id", "Id"),

	DATASTORE("datastore", "dataStore", "Datastore"),

	FLAG("flag", "flag", "Flag"),

	PHY_QUAN("phy_quan", "phyQuantity", "Physical Quantity"),

	UNITS("units", "units", "Units"),

	UNIT_NAME("units", "unit", "Unit"),

	TYPE("type", "type", "Type"),

	NAME("name", "name", "Name"),

	P_NAME("pname", "pName", "Physical Quantity Name"),

	P_ID("puuid", "pUuid", "Physical Quantity Id"),

	CONVERSION("conversion", "conversion", "Conversion"),

	SI_FLAG("is_si", "isSi", "IsSI Flag"),

	SI_UNIT("is_si", "isSi", "SI Unit"),

	STATUS("status", "status", "Status"),

	STATUS_TABLE("statustable", "statusTable", "Status Table"),

	STATUS_NAME("status_name", "statusName", "Status Name"),

	STATUS_KEY("status_key", "statusKey", "Status Key"),

	UN_SUPPORTED("unsupported", "unSupported", "Unsupported"),

	WARNING("warning", "warning", "Warning"),

	PHYQUANTITY_CONFIG_CACHE("galaxy.datasource.cache.phyquantity",
	        "physicalQuantityCache", "Physical Quantity Cache");

	private String fieldName;
	private String variableName;
	private String description;

	private PQDataFields(String fieldName, String variableName,
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
