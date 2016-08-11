/**
 *
 */
package com.pcs.alpine.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.alpine.commons.validation.DataFields;

/**
 * DataFields Enum
 * 
 * DataFields present in the DB should be placed here
 * 
 * @description Enum defining the data fields in the DB.
 * @author Daniela(PCSEG191)
 * @date 12 Mar 2016
 * @since galaxy-1.0.0
 */
public enum TagDataFields implements DataFields {

	ENTITY_TEMPLATE("", "entityTemplate", "Entity Template"),
	PLATFORM_ENTITY("", "platformEntity", "Platform Entity"),
	PLATFORM_ENTITY_TYPE("", "platformEntityType", "Platform Entity Type"),
	ENTITY_TEMPLATE_NAME("", "entityTemplateName", "Entity Template Name"),
	FIELD_VALUES("fieldValues", "fieldValues", "Field Values"),

	DOMAIN_NAME("", "domainName", "Domain Name"),
	DOMAIN("", "domain", "Domain"),
	NAME("", "name", "Name"),
	TYPE("", "type", "Type"),
	IDENTIFIER("identifier", "identifier", "Identifier"),
	IDENTIFIER_KEY("key", "key", "Identifier Key"),
	IDENTIFIER_VALUE("value", "value", "Identifier Value"),

	TAG("", "tag", "Tag"),
	TAGS("", "tags", "Tags"),
	ENTITY("", "entity", "Entity"),
	TEMPLATES("", "templates", "Templates"),
	ENTITIES("", "entities", "Entities"),
	TAG_TYPE_NAME("", "tagTypeName", "Tag Type Name"),
	TAG_TYPE("", "tagType", "Tag Type"),
	TAG_TYPE_IDENTIFIER("", "tagTypeIdentifier", "Tag Type Identifier"),
	TAG_IDENTIFIER("", "tagIdentifier", "Tag Identifier"),
	TAG_TYPE_VALUES("", "tagTypeValues", "Tag Type Values"),
	STATUS("status", "status", "Status"),
	SUB_ENTITY("", "", "Subject Entity"),
	SUB_TEMPLATE("", "", "Subject Template"),
	;

	private String fieldName;
	private String variableName;
	private String description;

	private TagDataFields(String fieldName, String variableName,
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

	private static final Map<String, TagDataFields> map;
	static {
		map = new HashMap<String, TagDataFields>();
		for (TagDataFields v : TagDataFields.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static TagDataFields getDataField(String fieldName) {
		TagDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}

}
