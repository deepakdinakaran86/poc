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

package com.pcs.avocado.commons.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Riyas PH
 * @date September 21 2014
 */

public enum GalaxyCommonErrorCodes implements GalaxyErrorCode {

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

	CREATE_TENANT_FAILURE(515, "Create tenant has failed. ",
			" Create tenant has failed. "),

	UPDATE_TENANT_FAILURE(516, " Update tenant has failed. ",
			" Update tenant has failed. "),

	ROLLBACK_FAILURE(517, "Roll-back has failed. ", " Roll-back has failed. "),

	INVALID_ACTION(518, " is an invalid action", "Invalid action invoked"),

	INVALID_LIST_DATA_SPECIFIED(519, " are invalid",
			"The specified value is not available in the system"),

	CHILD_EXISTS(520, " has a child entity associated",
			"The specified entity already has a child allocated in the system"),

	ENTITY_STATUS_UPDATE(521, " entity status update failed",
			"The specified entity status update failed"),

	LIST_DELETE(522, "The following list failed to delete;",
			"The given list is failed in delete"),

	MENU_IS_LIMITED_TO_ADMIN(523, " menu is limited to admin role",
			"The specified menu is limited to admin role"),

	MENU_IS_LIMITED_TO_SUPER_ADMIN(524, " menu is limited to super tenant",
			"The specified menu is limited to to super tenant"),

	SPECIFIC_DATA_NOT_VALID(525, " is not valid",
			"Specified data is not active"),

	FIELD_CANNOT_BE_UPDATED(526, " cannot be updated",
			"The specified field cannot be updated"),

	// Mail 550 - 559
	UNABLE_TO_SEND_EMAIL(550, "Unable to send email", "Unable to send email"),

	// Date 560-599
	INVALID_DATE_FORMAT(560, "Date format should be yyyy-MM-dd HH:mm:ss",
			"Invalid date format"),

	// Authorization 600-649
	NO_ACCESS(600, "User has no access", "User has no access"),

	INVALID_USER_PROFILE(602, "Invalid User Profile", "Invalid User Profile"),

	NOT_A_ORG_SUPER_ADMIN_USER(603,
			"User is not a  super admin or organization admin",
			"Access restricted only to super admin and organization admin users"),

	NO_ACCESS_SELECTED_DOMAIN(605, "User has no access to the selected domain",
			"User has no access to the selected domain"),

	// USER_AUTHENTICATION_FAILED - 610
	USER_AUTHENTICATION_FAILED(610, "Authentication Failed",
			"Authentication Failed"),

	SPECIFIC_DATA_ALREADY_EXISTS(611, " data is available",
			"Data already exists"),

	INVALID_LOGIN_CREDENTIALS(612, "Invalid Login Credentials",
			"Invalid Login Credentials"),

	// REGISTRATION - 650
	REG_NOTIFICATION_RECEIVED(650, "Notification already received",
			"Notification already received"),

	LINK_NOT_PRESENT(651, "Link does not exist", "URL does not exist"),

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
			" !! Exceeds the Start and End Data Range  "),

	START_END_DATE(754, " Start date should be after end date ",
			" Start date should be after end date "),

	WEB_SOCKET_CONNECTION_NOT_AVAILABLE(752,
			"Web Socket connection is not available",
			"Web Socket connection is not available"),

	TENANT_LINK_NOT_ALLOWED(753, "Tenant cant be linked with any parent",
			"Tenant cant be linked with any parent"),

	ENTITIES_FROM_WRONG_HIERARCHY(753, "Entities are with wrong hierarchy",
			"Entities are with wrong hierarchy"),

	ROLE_NAME_CANNOT_BE_UPDATED(754, "Role name cannot be updated",
			"Role name cannot be updated"),

	ROLE_NAMES_SHOULD_BE_JSON_ARRAY(754, "Role name should be json array",
			"Role name should be json array"),

	SPECIFIC_DATA_CANNOT_BE_SAVED(755, " cannot be saved",
			"Specific data cannot be saved"),
			
	SPECIFIC_DATA_CANNOT_BE_UPDATED(755, " cannot be updated",
					"Specific data cannot be updated"),
	POINTS_SELECTION(756, "Points cannot be unselected",
			"Points attached cannot be updated");

	;

	private Integer code;
	private String message;
	private String description;

	private GalaxyCommonErrorCodes(Integer errorCode, String message,
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

	private static final Map<Integer, GalaxyCommonErrorCodes> map;
	static {
		map = new HashMap<Integer, GalaxyCommonErrorCodes>();
		for (GalaxyCommonErrorCodes v : GalaxyCommonErrorCodes.values()) {
			map.put(v.getCode(), v);
		}
	}

	public static GalaxyCommonErrorCodes getErrorCode(Integer errorCode) {
		GalaxyCommonErrorCodes result = map.get(errorCode);
		if (result == null) {
			throw new IllegalArgumentException("No Error Code Exists");
		}
		return result;
	}
}
