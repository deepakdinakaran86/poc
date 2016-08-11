package com.pcs.guava.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.guava.commons.validation.DataFields;

public enum ScheduleDataFields implements DataFields{
	
	SCHEDULE_NAME("scheduleName", "scheduleName", "ScheduleName"), 
	SCHEDULE_DESC("description", "description","ScheduleDescription"), 
	ARRIVAL_TIME("arrivalTime", "arrivalTime", "ArrivalTime"),
	TOLERANCE_TIME("timeTolerance", "timeTolerance", "TimeTolerance"),
	START_TIME("startTime", "startTime", "ScheduleStartTime"),
	END_TIME("endTime", "endTime", "ScheduleEndTime"),	
	DAY_SPAN("daySpan", "daySpan", "DaySpan"),
	POI_STOPPAGE_TIME("stopageTime", "stopageTime", "StopageTime"),
	DEPARTURE_TIME("departureTime", "departureTime", "DepartureTime")
	;

	private String fieldName;
	private String variableName;
	private String description;

	private ScheduleDataFields(String fieldName, String variableName,
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

	private static final Map<String, ScheduleDataFields> map;
	static {
		map = new HashMap<String, ScheduleDataFields>();
		for (ScheduleDataFields v : ScheduleDataFields.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static ScheduleDataFields getDataField(String fieldName) {
		ScheduleDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}

}
