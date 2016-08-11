package com.pcs.guava.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.guava.commons.validation.DataFields;

public enum RouteDataFields implements DataFields {
	

			DOMAIN("domain", "domain", "DomainName"), 
			ROUTE_NAME("routeName", "routeName","RouteName"), 
	        ROUTE_DESC("routeDescription", "routeDescription", "RouteDescription"), 
	        TOTAL_DISTANCE("totalDistance", "totalDistance", "Total Distance"), 
	        TOTAL_DURATION("totalDuration", "totalDuration", "Total Duration"), 
	        START_ADDRESS( "startAddress", "startAddress", "Start Address"),
	        END_ADDRESS("endAddress","endAddress", "End Address"), 
	        ROUTE_STRING("routeString", "routeString", "Route String"),
	        DESTN_POINTS("destinationPoints","destinationPoints","Poi points"),
	    	RADIUS("radius", "radius", "Radius"),
	    	LATITUDE("latitude", "latitude", "latitude"),
	    	LONGITUDE("longitude", "longitude", "longitude"),
	    	DURATION("duration", "duration", "Duration"),
	    	CUR_POI_TYPE("poiType", "poiType", "Poi Type"),
	    	POI_ADDRESS("address", "address", "Address"),
	    	POI_NAME("poiName", "poiName", "Poi Name"),
	    	POI_INDEX("index", "index", "Poi Index"),
	    	STATUS_ACTIVE("ACTIVE", "ACTIVE", "Status Active"),
	    	STATUS("status", "status", "Route Status"),
			STATUS_INACTIVE("InActive", "Active", "Status InActive"),
			MAP_PROVIDER("mapProvider", "mapProvider", "Map Provider"),
			IDENTIFIER("identifier", "identifier", "Entity identifier");
	
	

	private String fieldName;
	private String variableName;
	private String description;

	private RouteDataFields(String fieldName, String variableName,
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

	private static final Map<String, RouteDataFields> map;
	static {
		map = new HashMap<String, RouteDataFields>();
		for (RouteDataFields v : RouteDataFields.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static RouteDataFields getDataField(String fieldName) {
		RouteDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}

}