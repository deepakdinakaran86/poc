package com.pcs.cep.doc.enums;

import com.pcs.devicecloud.commons.validation.DataFields;

public enum CEPDataFields implements DataFields {
	
	SOURCE_ID("source_id", "sourceId", "Source Id"),
	MAX_VALUE("max_value", "maxValue", "Max Value"),
	MIN_VALUE("min_value", "minValue", "Min Value"),
	COMPARE_VALUE("compare_value", "compareValue", "Compare Value"), 
	PARAMETER("parameter", "parameter", "Parameter");

	private String fieldName;
	private String variableName;
	private String description;

	private CEPDataFields(String fieldName, String variableName,
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
