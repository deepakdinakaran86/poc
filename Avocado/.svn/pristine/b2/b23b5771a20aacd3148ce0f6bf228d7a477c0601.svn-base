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

package com.pcs.avocado.exception;

import com.pcs.avocado.commons.exception.GalaxyErrorCode;

/**
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since alpine-1.0.0
 * 
 */

public enum AvocadoUMErrorCodes implements GalaxyErrorCode {

	// General 1500 - 2000

	// General 1500 - 1549
	OPERATION_UNAVAILABLE(1501,
	        "Sorry, this operation is currently unavailable.",
	        "Sorry, this operation is currently unavailable."),

	// User 1550 - 1599
	USER_ADMIN_CANT_BE_DELETED(1550, " It is an admin user",
	        "Admin user can't be deleted"),
	USER_COUNT_EXCEEDS_DOMAIN_LIMIT(1551, "User count exceeds domain limit",
	        "User count exceeds domain limit"),
	USER_DOMAIN_INVALID(1552, "Username/ domain name is incorrect",
	        "No Entity access for the user"),
	USER_MAX_USER_INTEGER(1553, "Maximum user count should be a number",
	        "Maximum user count should be a number"),
	USER_MAX_CONCURRENT_USER_INTEGER(1554,
	        "Maximum concurrent user count should be a number",
	        "Maximum concurrent user count should be a number"),
	USER_MAX_USER_GREATER_THAN_CONCURRENT(
	        1555,
	        "Maximum concurrent user count cannot be greater than max user count",
	        "Maximum concurrent user count cannot be greater than max user count"),
	USER_MAX_CONCURRENT_USER_ZERO(1556,
	        "Maximum concurrent user count cannot be zero",
	        "Maximum concurrent user count cannot be zero"),
	USER_MAX_USER_ZERO(1557, "Maximum user count cannot be zero",
	        "Maximum concurrent user cannot be zero"),
	UPDATE_USER_COUNT_ROLE_EXCEPTION(1558,
	        "Exception while updating role user count",
	        "Exception while updating role user count"),
	USER_ROLE_CANT_UPDATED(1559, "Tenant Admin not allowed to update his role",
	        "Tenant Admin not allowed to update his role"),
	USER_STATUS_CANT_UPDATED(1560,
	        "Tenant Admin not allowed to update his status",
	        "Tenant Admin not allowed to update his status"),
	USER_DOES_NOT_HAVE_ACCESS(1561, "User does not have any access",
	        "User does not have any access"),
	USER_PARENT_MAX_USER_COUNT_EXCEEDED(1562,
	        "Parent's Maximum user count exceeded the limit",
	        "Parent's Maximum user count exceeded the limit"),
	USER_MAX_USER_COUNT_PARENT_MAX_USER_COUNT(
	        1563,
	        "Maximum user count cannot be greater than remaining maximum user count of parent.",
	        "Maximum user count cannot be greater than remaining maximum user count of parent."),
	USER_MAX_CON_USER_COUNT_PARENT_MAX_CON_USER_COUNT(
	        1564,
	        "Maximum concurrent user count cannot be greater than maximum concurrent user count of parent.",
	        "Maximum concurrent user count cannot be greater than maximum concurrent user count of parent."),
	USER_NO_PRIVILEGE(1565,
	        "User doesn't have permission to do this operation",
	        "User doesn't have permission to do this operation"),
	USER_NOT_ALLOWED_TO_UPDATE_ROLE(1566,
	        "User is not allowed to updated his own role",
	        "User is not allowed to updated his own role"),
	USER_NOT_ALLOWED_TO_UPDATE_STATUS(1567,
	        "User is not allowed to updated his own status",
	        "User is not allowed to updated his own status"),
	USER_NAME_CANT_UPDATED(1568, "User Name cannot be updated",
	        "User Name cannot be updated"),
	INVALID_ENTITY_SPECIFIED_IN_ACCESS(1569,
	        "Invalid entity is specified in access", "Entity is Invalid, "),
	MIN_ONE_ADMIN_IS_REQUIRED(1570, "Minimum one admin user is required",
	        "Minimum one admin user is required"),
	MIN_ONE_ACTIVE_ADMIN_IS_REQUIRED(1571,
	        "Minimum one active admin user is required",
	        "Minimum one active admin user is required"),
	ENTITY_CONTACT_CANT_BE_DELETED(1572,
	        " it is an Organization/Client contact",
	        "Organization/Client contact cannot be deleted"),
	DOMAIN_USER_COUNT_REDUCED(
	        1573,
	        "Domain Max user count cannot be reduced, as user's created exceed the new max user count",
	        "Domain Max user count cannot be reduced, as user's created exceed the new max user count"),

	// Role 1600 - 1649
	ROLE_DEFAULT_CANT_BE_DELETED(1600, "Default role can't be deleted",
	        "Default role can't be deleted"), ROLE_DEFAULT_CANT_BE_EDITED(1601,
	        "Default role can't be Edited", "Default role can't be Edited"),
	ROLE_USERS_EXIST_CANT_DELETE(1602, "Role with users can't be deleted",
	        "Role with users can't be deleted"),
	ROLE_USER_COUNT_UPDATE_INVALID(1603, "Invalid Operation",
	        "Invalid Operation"), USER_IS_ADMIN(1604,
	        "Admin users have full access", "Admin users have full access"),
	ENTITY_CONTACT_NOT_ALLOWED_TO_UPDATE_ROLE(1605,
	        "Organization/Client contact user's role cannot be updated",
	        "Organization/Client contact user's role cannot be updated"),
	ENTITY_CONTACT_NOT_ALLOWED_TO_UPDATE_STATUS(1606,
	        "Organization/Client contact user's status cannot be updated",
	        "Organization/Client contact user's status cannot be updated"),
	        
	 //Permission  1650- 1699 
	 PERMISSION_GROUP_CREATION_FAILED(1650, "Error creating permission group",
	        "Error creating permission group"), PERMISSION_GROUP_SUBSCRIPTION_FAILED(1651, "Error while subscribing to permission group",
	    	 "Error while subscribing to permission group"),PERMISSION_GROUP_UPDATION_FAILED(1652, "Error updating permission group",
	    		        "Error updating permission group"),PERMISSION_DUPLICATED(1653, "Duplicates found in the permissions",
	    	    		        "Duplicates found in the permissions"),RESOURCE_UPDATE_ERROR(1654, "Resource name cannot be updated",
	    	    	    		        "Resource name cannot be updated")
	        ;

	private Integer code;
	private String message;
	private String description;

	private AvocadoUMErrorCodes(Integer errorCode, String message,
	        String description) {
		this.code = errorCode;
		this.message = message;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

}
