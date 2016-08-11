package com.pcs.fms.web.client;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Component
public final class FMSResponseManager {

	public final static String SUCCESS = "success";
	public final static String FAILURE = "failure";
	public final static String ERRROR = "error";
	public final static String STATUS = "status";
	public final static String MESSAGE = "message";

	public static String success(String message) {
		JSONObject response = new JSONObject();
		response.put(STATUS, SUCCESS);
		response.put(MESSAGE, message);
		return response.toString();
	}

	public static String error(String message) {
		JSONObject response = new JSONObject();
		response.put(STATUS, ERRROR);
		response.put(MESSAGE, message);
		return response.toString();
	}
}
