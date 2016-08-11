/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */

package com.pcs.devicecloud.commons.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Riyas PH
 */

public enum DeviceCloudErrorCodes implements DeviceCloudErrorCode {

	// General 500 - 549
	DATA_NOT_AVAILABLE(500, "Requested data is not available",
			"No result found for the specified query"),

	INCOMPLETE_REQUEST(501, "Request is incomplete",
			"Specifed data is incomplete"),

	TIME_OUT_EXCEPTION(502, "Request timed out", "Request timed out"),

	PERSISTENCE_EXCEPTION(503, "Data could not be saved",
			"Specified data could not be save with unknown reasons"),

	GENERAL_EXCEPTION(504, "The server encountered an internal error",
			"Uncaught exception occurred"),

	FIELD_DATA_NOT_SPECIFIED(505, " not specified",
			"The specified mandatory field is not specified"),

	SERVICE_UNAVAILABLE(506, "Service unavailable",
			"The requested service is not available"),

	NON_EXISTING_DATA_CANNOT_BE_UPDATED(507,
			"Cannot update a non existing data",
			"Cannot find data with the specified identifier"),

	FIELD_IS_NOT_UNIQUE(522, " is not unique",
			"The specified field is not unique in the system"),

	FIELD_ALREADY_EXIST(507, " already exists",
			"The specified field already exists in the system"),

	INVALID_DATA_SPECIFIED(508, " is invalid",
			"The specified value is not available in the system"),

	DUPLICATE_RECORDS(509, "Duplicate records found", "Duplicate records found"),

	SPECIFIC_DATA_NOT_AVAILABLE(510, " data is not available",
			"No result found for the specified query"),

	SPECIFIC_DATA_NOT_ACTIVE(511, " is not active",
			"Specified data is not active"),

	SPECIFIC_DATA_IS_DELETED(512, " is deleted", "Specified data is deleted"),

	USER_INPUT_VIOLATION(513, "Invalid input", "Invalid input"),

	MULTIPLE_DELETE_FAILED(515, " are failed in Delete",
			"The given list is failed in delete"),

	INVALID_ACTION(518, " is an invalid action", "Invalid action invoked"),

	INVALID_LIST_DATA_SPECIFIED(519, " are invalid",
			"The specified value is not available in the system"),

	LIST_DELETE(522, "The following list failed to delete;",
			"The given list is failed in delete"),

	SPECIFIC_DATA_NOT_VALID(525, " is not valid",
			"Specified data is not active"),

	SPECIFIC_DATA_CANT_BE_UPDATED(526, " can't be updated",
			"Specified data can't be updated"),

	SERIALIZATION_FAILED(527, "Data serialization failed",
			"Data serialization failed"),

	DESERIALIZATION_FAILED(528, "Data deserialization failed",
			"Data deserialization failed"),

	// Mail 550 - 559
	UNABLE_TO_SEND_EMAIL(550, "Unable to send email", "Unable to send email"),

	// USER_AUTHENTICATION_FAILED - 610
	USER_AUTHENTICATION_FAILED(610, "Authentication Failed",
			"Authentication Failed"),

	SPECIFIC_DATA_ALREADY_EXISTS(611, " data is available",
			"Data already exists"),

	INVALID_LOGIN_CREDENTIALS(612, "Invalid Login Credentials",
			"Invalid Login Credentials"),

	CUSTOM_ERROR(652, "", ""),

	// Device Date Services - 700
	DEVICE_IS_DISCONTINUED(700, "Device is discontinued",
			"Device status is discontinued"),

	SPECIFIC_LIMIT_EXCEEDED(701, " limit exceeded",
			"Data exceeds predefined limit"),

	// Datasource Services - 750
	DATASOURCE_DOES_NOT_EXIST(750, "Datasource does not exist",
			"Datasource does not exist"),

	DATASOURCE_PARAMETERS_EMPTY(751, "Empty parameter list",
			"Empty parameter list"),

	DAYS_DURATION(753, " !! Exceeds the Start and End Data Range ",
			" !! Exceeds the Start and End Data Range  "), START_END_DATE(754,
			" Start date should be after end date ",
			" Start date should be after end date "),

	WEB_SOCKET_CONNECTION_NOT_AVAILABLE(752,
			"Web Socket connection is not available",
			"Web Socket connection is not available"),

	DEVICE_ALREADY_CLAIMED(754, "Device is already claimed",
			"Device is already claimed");

	private Integer code;
	private String message;
	private String description;

	private DeviceCloudErrorCodes(Integer errorCode, String message,
			String description) {
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

	private static final Map<Integer, DeviceCloudErrorCodes> map;
	static {
		map = new HashMap<Integer, DeviceCloudErrorCodes>();
		for (DeviceCloudErrorCodes v : DeviceCloudErrorCodes.values()) {
			map.put(v.getCode(), v);
		}
	}

	public static DeviceCloudErrorCodes getErrorCode(Integer errorCode) {
		DeviceCloudErrorCodes result = map.get(errorCode);
		if (result == null) {
			throw new IllegalArgumentException("No Error Code Exists");
		}
		return result;
	}
}
