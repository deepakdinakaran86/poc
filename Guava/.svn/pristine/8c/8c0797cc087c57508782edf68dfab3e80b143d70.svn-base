/**
 *
 */
package com.pcs.guava.services.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.guava.commons.validation.DataFields;

/**
 * DataFields Enum
 * 
 * DataFields present in the DB should be placed here
 * 
 * @description Enum defining the data fields in the DB.
 * @author Daniela(PCSEG271)
 * @date 18th Oct 2015
 * @since galaxy-1.0.0
 */
public enum HMDataFields implements DataFields {

	// /
	CHILD("Child", "Child", "Child"),
	ORPHAN("Orphan", "Orphan", "Orphan"),
	ROW("row", "row", "row"),
	GRAPH("graph", "graph", "graph"),
	DATA("data", "graph", "graph"),
	RESULTS("results", "results", "results"),
	ACTOR("actor", "actor", "Actor"),
	SUBJECTS("subjects", "subjects", "subjects"),
	SUBJECT("subject", "subject", "subject"),
	DOMAIN_NAME("domain_name", "domainName", "Domain Name"),
	ENTITY_TEMPLATE("entityTemplate", "entityTemplate", "Entity Template"),
	ENTITY_TEMPLATE_NAME("entity_template_name", "entityTemplateName",
	        "Entity Template Name"),
	PLATFORM_ENTITY("platform_entity", "platformEntity", "Platform Entity"),
	PLATFORM_ENTITY_TYPE("platform_entity_type", "platformEntityType",
	        "Platform Entity Type"),
	IDENTIFIER("identifier", "identifier", "Identifier"),
	IDENTIFIER_VALUE("value", "value", "Identifier Value"),
	IDENTIFIER_KEY("key", "key", "Identifier Key"),
	DOMAIN("domain", "domain", "Domain"),
	TENANT("tenant", "tenant", "tenant"),
	MY_DOMAIN("myDomain", "myDomain", "My Domain"),
	TENANT_TYPE("TENANT", "TENANT", "TENANT"),
	IDENTITY_VALUE("identifierValue", "identifierValue", "Identifier Value"),
	IDENTITY_KEY("identifierKey", "identifierKey", "Identifier Key"),
	TEMPLATE_NAME("templateName", "templateName", "Template Name"),
	FIELD_VALUES("field_values", "fieldValues", "Field Values"),
	SEARCH_TEMPLATE_NAME("searchTemplateName", "searchTemplateName",
	        "Search Template Name"),
	SEARCH_ENTITY_TYPE("", "searchEntityType", "Search Entity Type"),
	STATUS_NAME("", "statusName", "Status Name"),
	ENTITY_STATUS("entityStatus", "entityStatus", "Entity Status"),
	MARKER_TEMPLATE_NAME("markerTemplateName", "markerTemplateName",
	        "Marker Template Name"),
	START_ENTITY_IDENTIFIER("identifier", "identifier",
	        "Start Entity Identifier"),
	START_ENTITY_PLATFORM_ENTITY("platform_entity", "platformEntity",
	        "Start Entity Platform Entity"),
	START_ENTITY_IDENTIFIER_KEY("key", "key", "Start Entity Identifier Key"),
	START_ENTITY_IDENTIFIER_VALUE("value", "value",
	        "Start Entity Identifier Value"),
	START_ENTITY_PLATFORM_ENTITY_TYPE("platform_entity_type",
	        "platformEntityType", "Start Entity Platform Entity Type"),
	START_ENTITY_TEMPLATE_NAME("entity_template_name", "entityTemplateName",
	        "Start Entity Template Name"),
	START_ENTITY_DOMAIN("domain", "domain", "End Entity Domain"),
	START_ENTITY_DOMAIN_NAME("domain_name", "domainName",
	        "Start Entity Domain Name"),
	END_ENTITY_IDENTIFIER("identifier", "identifier", "Start Entity Identifier"),
	END_ENTITY_PLATFORM_ENTITY("platform_entity", "platformEntity",
	        "End Entity Platform Entity"), END_ENTITY_IDENTIFIER_KEY("key",
	        "key", "End Entity Identifier Key"), END_ENTITY_IDENTIFIER_VALUE(
	        "value", "value", "End Entity Identifier Value"),
	END_ENTITY_PLATFORM_ENTITY_TYPE("platform_entity_type",
	        "platformEntityType", "End Entity Platform Entity Type"),
	END_ENTITY_TEMPLATE_NAME("entity_template_name", "entityTemplateName",
	        "End Entity Template Name"), END_ENTITY_DOMAIN("domain", "domain",
	        "End Entity Domain"), END_ENTITY_DOMAIN_NAME("domain_name",
	        "domainName", "End Entity Domain Name"), START_ENTITY(
	        "startEntity", "startEntity", "Start Entity"), END_ENTITY(
	        "endEntity", "endEntity", "End Entity"), RELATION_ATTACHEDTO(
	        "attachedTo", "attachedTo", "AttachedTo Relationship"), ACTOR_TYPE(
	        "actorType", "actorType", "Actor Type"), SUBJECT_TYPE(
	        "subjectType", "subjectType", "Subject Type"), DATAPROVIDER(
	        "dataprovider", "dataprovider", "Dataprovider"), TREE("tree",
	        "tree", "Tree"), LATITUDE("latitude", "latitude", "Latitude"),
	LONGITUDE("longitude", "longitude", "Longitude");

	private String fieldName;
	private String variableName;
	private String description;

	private HMDataFields(String fieldName, String variableName,
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

	private static final Map<String, HMDataFields> map;
	static {
		map = new HashMap<String, HMDataFields>();
		for (HMDataFields v : HMDataFields.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static HMDataFields getDataField(String fieldName) {
		HMDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}

}
