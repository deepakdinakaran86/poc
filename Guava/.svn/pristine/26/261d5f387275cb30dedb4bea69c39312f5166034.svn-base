/**
 *
 */
package com.pcs.guava.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.guava.commons.validation.DataFields;

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
public enum ServiceSchedulingDataFields implements DataFields {

	SERVICE_ITEM_NAME("serviceItemName", "serviceItemName", "Service Item Name"), SERVICE_COMPONENT_NAME(
			"serviceComponentName", "serviceComponentName",
			"Service Component Name"), SERVICE_SCHEDULE_NAME(
			"serviceScheduleName", "serviceScheduleName",
			"Service Schedule Name"), SERVICE_ITEM_FIELDS("serviceItemFields",
			"serviceItemFields", "Service Item Fields"), SERVICE_COMPONENT_ENTITIES(
			"serviceComponentEntities", "serviceComponentEntities",
			"Service Component Entities"), SERVICE_COMPONENT_IDENTIFIERS(
			"serviceComponentIdentifiers", "serviceComponentIdentifiers",
			"Service Component Identifiers"), SERVICE_SCHEDULE_FIELDS(
			"serviceScheduleFields", "serviceScheduleFields",
			"Service Schedule Fields"), DESCRIPTION("description",
			"description", "Description"), ENTITY("entity", "entity", "Entity"), SERVICE_ITEM_IDENTIFIER(
			"serviceItemIdentifier", "serviceItemIdentifier",
			"Service Item Identifier"), SERVICE_COMPONENT_IDENTIFIER(
			"serviceComponentIdentifier", "serviceComponentIdentifier",
			"Service Component Identifier"), SERVICE_SCHEDULE_IDENTIFIER(
			"serviceScheduleIdentifier", "serviceScheduleIdentifier",
			"Service Component Identifier"), SERVICE_ITEM_TEMPLATE(
			"serviceItemTemplate", "serviceItemTemplate",
			"Service Item Template"), SERVICE_COMPONENT_TEMPLATE(
			"serviceComponentTemplate", "serviceComponentTemplate",
			"Service Item Template"), SERVICE_SCHEDULE_TEMPLATE(
			"serviceScheduleTemplate", "serviceScheduleTemplate",
			"Service Schedule Template"), SERVICE_SCHEDULE_OCCURANCE_TYPE(
			"occuranceType", "occuranceType", "Service Schedule Occurance Type"), SERVICE_COMPONENT_FREQUENCY(
			"frequency", "frequency", "Service Component Frequency"), SERVICE_COMPONENT_FREQUENCY_DISTANCE(
			"frequencyInDistance", "frequencyInDistance",
			"Service Component Frequency in Distance"), SERVICE_COMPONENT_FREQUENCY_TIME(
			"frequencyInTime", "frequencyInTime",
			"Service Component Frequency in Time"), SERVICE_COMPONENT_FREQUENCY_RUNNING_HOURS(
			"frequencyInRunningHours", "frequencyInRunningHours",
			"Service Component Frequency in Running Hours"), SERVICE_COMPONENT_NOTIFICATION_IN_DISTANCE(
			"notificationInDistance", "notificationInDistance",
			"Service Component Notification in Distance"), SERVICE_COMPONENT_NOTIFICATION_IN_TIME(
			"notificationInTime", "notificationInTime",
			"Service Component Notification in Time"), SERVICE_COMPONENT_NOTIFICATION_IN_RUNNING_HOURS(
			"notificationInRunningHours", "notificationInRunningHours",
			"Service Component Notification in Running Hours"), ONETIME(
			"One Time", "One Time", "One Time"), RECURRING("Recurring",
			"Recurring", "Recurring"), PERIODIC("Periodic", "Periodic",
			"Periodic");

	private String fieldName;
	private String variableName;
	private String description;

	private ServiceSchedulingDataFields(String fieldName, String variableName,
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

	private static final Map<String, ServiceSchedulingDataFields> map;
	static {
		map = new HashMap<String, ServiceSchedulingDataFields>();
		for (ServiceSchedulingDataFields v : ServiceSchedulingDataFields
				.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static ServiceSchedulingDataFields getDataField(String fieldName) {
		ServiceSchedulingDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}

}
