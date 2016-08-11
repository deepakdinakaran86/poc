package com.pcs.alpine.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.alpine.commons.exception.GalaxyErrorCode;

public enum GeoErrorCodes implements GalaxyErrorCode {

	// Geofence Error Codes 800
	LIST_REQUIREMENTS(800, " does not have minimum number of cordinates",
			"Cannot be created as it does not meet requirements"),
	
	GEO_TAG_EXISTS(801, " already has a Geotag  mapped to it",
			"Geotag already exists");
	private Integer code;
	private String message;
	private String description;

	private GeoErrorCodes(Integer errorCode, String message, String description) {
		this.code = errorCode;
		this.message = message;
		this.description = description;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getDescription() {
		return description;
	}

	private static final Map<Integer, GeoErrorCodes> map;
	static {
		map = new HashMap<Integer, GeoErrorCodes>();
		for (GeoErrorCodes v : GeoErrorCodes.values()) {
			map.put(v.getCode(), v);
		}
	}

	public static GeoErrorCodes getErrorCode(Integer errorCode) {
		GeoErrorCodes result = map.get(errorCode);
		if (result == null) {
			throw new IllegalArgumentException("No Error Code Exists");
		}
		return result;
	}
}
