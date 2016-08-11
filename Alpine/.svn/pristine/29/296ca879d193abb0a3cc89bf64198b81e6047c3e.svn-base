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
public enum GeoDataFields implements DataFields {

	POLYGON("Polygon", "Polygon", "Polygon"), CIRCLE("Circle", "Circle",
	        "Circle"), ROUTE("Route", "Route", "Route"), GEOFENCE_NAME(
	        "geofenceName", "geofenceName", "Geofence Name"), GEOFENCE_TYPE(
	        "type", "type", "Geofence Type"), GEOFENCE_FIELDS("geofenceFields",
	        "geofenceFields", "Geofence Fields"), DESC("desc", "desc",
	        "Description"), CO_ORDINATES("coordinates", "coordinates",
	        "Coordinates"), ORDER("order", "order", "Order"), ENTITY("entity",
	        "entity", "Entity"), TAG("tag","tag","Tag"), 
	        TAG_TYPE("", "tagType", "Tag Type"), NAME("", "name", "Name"),
	IDENTIFIER_KEY("key", "key", "Identifier Key"), IDENTIFIER_VALUE("value",
	        "value", "Identifier Value"), ENTITY_TEMPLATE_NAME(
	        "entity_template_name", "entityTemplateName",
	        "Entity Template Name"), GEOTAG("geotag", "geotag", "Geotag"),
	LATITUDE("latitude", "latitude", "Latitude"), LONGITUDE("longitude",
	        "longitude", "Longitude"), ;

	private String fieldName;
	private String variableName;
	private String description;

	private GeoDataFields(String fieldName, String variableName,
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

	private static final Map<String, GeoDataFields> map;
	static {
		map = new HashMap<String, GeoDataFields>();
		for (GeoDataFields v : GeoDataFields.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static GeoDataFields getDataField(String fieldName) {
		GeoDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}

}
