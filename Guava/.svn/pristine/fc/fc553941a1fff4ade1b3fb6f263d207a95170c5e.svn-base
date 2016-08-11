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

package com.pcs.guava.rest.exception;

/**
 * @author pcseg314 Errors and their details encountered in Galaxy
 */

public enum GalaxyRestErrorCodes {

	// // General 500 - 549
	GENERAL_EXCEPTION(501, "Application Exception", "Application Exception"),
	DATA_NOT_AVAILABLE(502, "Error in Data fetching",
	        "Requested data is not available"),
	DATA_FETCHING_ERROR(503, "Error in Data fetching",
	        "Error in fetching requested data"),
	AUTHENTICATION_FAILED(504, "Authentication Failed", "Authentication Failed"),
	AUTHORIZATION_FAILED(505, "Authorization Failed", "Authorization Failed"),
	FORBIDDEN_ACCESS(506, "Forbidden Access", "Forbidden Access"),
	INVALID_REQUEST(507, "Invalid Request", "Invalid Request"),
	UN_MARSHAL_ERROR(508, "Error in Un Marshelling Response", "Error in Un Marshelling Response");

	private Integer errorCode;
	private String description;
	private String message;

	private GalaxyRestErrorCodes(int errorCode, String description,
	        String message) {
		this.errorCode = errorCode;
		this.description = description;
		this.message = message;
	}

	private GalaxyRestErrorCodes() {
	}

	public GalaxyRestErrorCodes setErrorCode(int errorCode, String description,
	        String message) {
		this.errorCode = errorCode;
		this.description = description;
		this.message = message;
		return this;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getDescription() {
		return description;
	}

	public String getMessage() {
		return message;
	}

}
