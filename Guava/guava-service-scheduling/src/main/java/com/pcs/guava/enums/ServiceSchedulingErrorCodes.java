package com.pcs.guava.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.guava.commons.exception.GalaxyErrorCode;

public enum ServiceSchedulingErrorCodes implements GalaxyErrorCode {

	// ServiceSchedulingErrorCodes Error Codes 800
	LIST_REQUIREMENTS(800, " does not have minimum number of cordinates",
			"Cannot be created as it does not meet requirements");
	private Integer code;
	private String message;
	private String description;

	private ServiceSchedulingErrorCodes(Integer errorCode, String message, String description) {
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

	private static final Map<Integer, ServiceSchedulingErrorCodes> map;
	static {
		map = new HashMap<Integer, ServiceSchedulingErrorCodes>();
		for (ServiceSchedulingErrorCodes v : ServiceSchedulingErrorCodes.values()) {
			map.put(v.getCode(), v);
		}
	}

	public static ServiceSchedulingErrorCodes getErrorCode(Integer errorCode) {
		ServiceSchedulingErrorCodes result = map.get(errorCode);
		if (result == null) {
			throw new IllegalArgumentException("No Error Code Exists");
		}
		return result;
	}
}
