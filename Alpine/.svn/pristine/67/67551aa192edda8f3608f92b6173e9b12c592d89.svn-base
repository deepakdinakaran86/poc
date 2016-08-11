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

package com.pcs.alpine.services.exception;

import com.pcs.alpine.commons.exception.GalaxyErrorCode;

/**
 * Error Codes specific for Galaxy Entity Management
 * 
 * Error codes range:1000-1500
 * 
 * @author Javid Ahammed (pcseg199)
 * @date Nov 2014
 * @since galaxy-1.0.0
 */

public enum GalaxyEMErrorCodes implements GalaxyErrorCode {

	// Entity 1000 - 1049

	ORPHAN_EXISTS_WITH_SAME_IDENTIFIER(1000,
	        "An orphan entity exists with the same identifier ",
	        "An orphan entity exists with the same identifier specified"),
	ENTITY_CANNOT_BE_DELETED(1001,
	        "Child entities exists, entity cannot be unallocated/deleted",
	        "Child entities exists, entity cannot be unallocated/deleted"),
	CLIENT_UNALLOCATION(1002, "Client entities allocation cannot be changed",
	        "Client entities allocation cannot be changed"),
	SIM_CHILD_ERROR(1003, "SIM entities cannot have children",
	        "SIM entities cannot have children"),
	ENTITY_UNIQUENESS_ERROR_ON_UN_ALLOCATE(1004,
	        "Unable to unallocate, an orphan exists with the same identifier",
	        "Unable to unallocate, an orphan exists with the same identifier"),
	MULIPLE_FIELD_VALUES(
	        1005,
	        "Search based on single field value, multiple field values provided",
	        "Search based on single field value, multiple field values provided"),
	DOMAIN_MISMATCH(1006,
	        "Template is not associated with the selected domain",
	        "Selected domain and the templates domain are not the same"),
	UPDATE_ON_ENTITY_IDENTIFIER_ILLEGAL(1007,
	        "Entity identifier cannot be updated",
	        "Entity identifier cannot be updated"),
	UNIT_ID_ERROR(1008, "Unable to generate Unit Id",
	        "The maximum range for unit id has been reached"),

	// Template 1050 - 1074
	TEMPLATE_IN_USE(1050, "This template is in use, so it cannot be deleted",
	        "This template is in use, so it cannot be deleted"),

	NO_ACTIVE_TEMPLATE(1051, "Specified domain does not contain any template",
	        "No templates are fetched with the query"),
	IDENTIFIER_NOT_PRESENT_IN_TEMPLATE(1052,
	        "Identifier field is missing in Template FieldValidation",
	        "Identifier field is missing in Template FieldValidation"),
	UPDATE_ON_IDENTIFIER_ILLEGAL(1053,
	        "Update on Identifier field is not allowed",
	        "Update on Identifier field is not allowed, save Template with new name"),
	IS_MODIFIABLE_UPDATE_NOT_ALLOWED(1054,
	        "Is Modifiable flag update is not allowed",
	        "Is Modifiable flag update is not allowed"),
	IS_SHAREABLE_UPDATE_NOT_ALLOWED(1054,
	        "Is Shareable flag update is not allowed",
	        "Is Shareable flag update is not allowed"),
	TEMPLATE_NOT_SHAREABLE(1055, " Template is not shareable",
	        " Template is not shareable"),
	TEMPLATE_IS_DEFAULT(1056, " Template is Default, cannot be allocated",
	        " Template is Default, cannot be allocated"),
	TEMPLATE_GLOBAL_ENTITY_IS_DEFAULT(1056,
	        " Template with Default globalEntityType cannot be created",
	        " Template with Default globalEntityType cannot be created"),
	DOMAIN_DOES_NOT_CONTAINS_GLOBAL_ENTITY_TYPE(1057,
	        " domain is not associated with any globalEntityType",
	        " domain is not associated with any globalEntityType"),
	GLOBAL_ENTITY_IS_NOT_DEFAULT(1058, " globalEntityType is not default",
	        "GlobalEntityType is not default"),

	// Event Data Store Error Codes 1075 - 1099
	DATA_DECODING_NOT_SUPPORTED(1075, "The data decoding is not supported",
	        "The Data Decoding is not supported"),

	// TODO to validate
	// Cassandra Error Codes 1101-1150
	QUERY_VALIDATION_ERROR(1101, "Query is not syntactically correct",
	        "Query is syntactically incorrect,invalid,unauthorized or any other reason"),
	SERVER_VALIDATION_OBJECT_UUID_NOT_FOUND(1102,
	        "Server Validation Object not found",
	        "No Server Validation object with the specified UUID could be found"),
	SERVER_VALIDATION_OBJECT_TYPE_NOT_FOUND(1103,
	        "Server Validation Object not found",
	        "No Server Validation object with the specified OBJECT_TYPE could be found"),
	UI_VALIDATION_MASTER_OBJECT_UUID_NOT_FOUND(1104,
	        "UI Validation Master Object not found",
	        "No UI Validation Master object with the specified UUID could be found"),
	UI_VALIDATION_MASTER_OBJECT_NAME_NOT_FOUND(1105,
	        "UI Validation Master Object not found",
	        "No UI Validation Master object with the specified name could be found"),
	UI_VALIDATION_OBJECT_UUID_NOT_FOUND(1106,
	        "UI Validation Object not present",
	        "No UI Validation object with the specified UUID could be found"),
	UI_VALIDATION_OBJECT_COMPONENT_TYPE_NOT_FOUND(1107,
	        "UI Validation Object not present",
	        "No UI Validation object with the specified component type could be found"),
	SERVER_VALIDATION_OBJECT_ALREADY_PRESENT(1109,
	        "Duplicate Server Validation Object Name",
	        "Server Validation Object already present with the specified name"),
	UI_VALIDATION_OBJECT_ALREADY_PRESENT(1110,
	        "Duplicate UI Validation Object Component Type",
	        "UI Validation Object already present with the specified component type"),
	UI_VALIDATION__MASTER_OBJECT_ALREADY_PRESENT(1111,
	        "Duplicate UI Validation Master Object Name",
	        "UI Validation Master Object already present with the specified name"),
	INVALID_UUID_SUPPLIED(1112, "Invalid UUID Entered",
	        "Invalid UUID provided in the JSON")

	;

	private Integer code;
	private String message;
	private String description;

	private GalaxyEMErrorCodes(Integer errorCode, String message,
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

}
