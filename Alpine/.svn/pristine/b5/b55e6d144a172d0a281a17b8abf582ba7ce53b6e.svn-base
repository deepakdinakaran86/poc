/**
 *
 */
package com.pcs.alpine.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.alpine.commons.validation.DataFields;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author Riyas (PCSEG296)
 * @date 12 June 2016
 * @since alpine-1.0.0
 */
public enum DocumentDataFields implements DataFields {

	DOCUMENT_TEMPLATE("Document", "Document", "Document"), DOCUMENT("document",
	        "document", "Document"), DOCUMENT_TYPE("documentType",
	        "documentType", "Document Type");

	private String fieldName;
	private String variableName;
	private String description;

	private DocumentDataFields(String fieldName, String variableName,
	        String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	@Override
	public String toString() {
		return this.fieldName;
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

	private static final Map<String, DocumentDataFields> map;
	static {
		map = new HashMap<String, DocumentDataFields>();
		for (DocumentDataFields v : DocumentDataFields.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static DocumentDataFields getDataField(String fieldName) {
		DocumentDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}

}
