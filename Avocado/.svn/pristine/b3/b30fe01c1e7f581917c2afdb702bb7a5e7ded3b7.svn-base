/**
 *
 */
package com.pcs.avocado.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.avocado.commons.validation.DataFields;


/**
 * DataFields Enum
 * 
 * DataFields present in the DB should be placed here
 * 
 * @description Enum defining the data fields in the DB.
 * @author Twinkle PCSEG297
 * @date January 2016
 */
public enum FacilityDataFields implements DataFields {

	IDENTIFIER("identifier", "identifier", "Identifier"),
	IDENTIFIER_KEY("", "", "Identifier Key"),
	KEY("", "", "Key"),
	DOMAIN_NAME("domainName", "domainName", "Domain Name"),
	IDENTIFIER_VALUE("", "", "Identifier Value"),
	MARKER("MARKER","MARKER","MARKER"),
	FACILITY("facility","facility","Facility"),
	FACILITY_NAME("facilityName","facilityName","Facility Name"),
	CLIENT_NAME("clientName","clientName","Client Name"),
	BUILDING_TYPE("buildingType","buildingType","Building Type"),
	VERTICAL("vertical","vertical","Vertical"),
	ZONE("zone","zone","Zone"),
	TIME_ZONE("timeZone","timeZone","Time Zone"),
	WEATHER_STATION("weatherStation","weatherStation","Weather Station"),
	CITY("city","city","City"),
	SQUARE_FEET("squareFeet","squareFeet","Square Feet"),
	COUNTRY("country","country","Country"),
	EMIRATE("emirate","emirate","Emirate"),
	BUILDING_STRUCTURE("buildingStructure","buildingStructure","Building Structure"),
	RFS_DATE("rfsDate","rfsDate","Rfs Date"),
	PRE_ECM_END_DATE("preEcmEndDate","preEcmEndDate","PreEcm End Date"),
	START_DATE("startDate","startDate","Start Date"),
	SAVINGS_TARGET("savingsTarget","savingsTarget","Savings Target"),
	MAX_VALUE("maxValue","maxValue","Max Value"),
	MAX_VALUE_ENERGY_PROFILE("maxValueEnergyProfile","maxValueEnergyProfile","Max Value Energy Profile"),
	ENTITY_TEMPLATE("entityTemplate", "entityTemplate", "Entity Template"),
	ENTITY_TEMPLATE_NAME("entityTemplateName", "entityTemplateName", "Entity Template Name"),
	TEMPLATE("", "", "Template")
	;
	
	

	private String fieldName;
	private String variableName;
	private String description;

	private FacilityDataFields(String fieldName, String variableName,
	        String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	@Override
	public String toString() {
		return this.fieldName;
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
	
	private static final Map<String, FacilityDataFields> map;
	static {
		map = new HashMap<String, FacilityDataFields>();
		for (FacilityDataFields v : FacilityDataFields.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static FacilityDataFields getDataField(String fieldName) {
		FacilityDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}
	
	public static FacilityDataFields getEnum(String value) {
		for (FacilityDataFields v : values()) {
			if (v.getFieldName().equalsIgnoreCase(value)) {
				return v;
			}
		}
		throw new IllegalArgumentException();
	}
}
