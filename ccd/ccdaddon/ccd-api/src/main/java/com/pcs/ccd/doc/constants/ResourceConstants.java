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
package com.pcs.ccd.doc.constants;

/**
 * @author pcseg199(Javid Ahammed)
 * @date November 2015
 * @since device-cloud-1.0.0
 */
public class ResourceConstants {

	public static final String JWT_ASSERTION = "JWT Token for Authorization";
	public static final String JWT_ASSERTION_SAMPLE = "header.eyJDb25zdW1lcktleSI6Ik9KYXIxVEh4QWdDWlVrM0dmM0dYbEtUTUhXZ2EifQ==.signature";

	public static final String GENERAL_TRUE_RESP = "{\"status\": \"true\"}";
	public static final String GENERAL_SUCCESS_RESP = "{\"status\": \"SUCCESS\"}";
	public static final String GENERAL_FAILURE_RESP = "{\"status\": \"FAILURE\"}";
	public static final String GENERAL_FIELD_NOT_SPECIFIED = "{mandatory-field} not specified";
	public static final String GENERAL_FIELD_NOT_UNIQUE = "{field} is not unique";
	public static final String GENERAL_FIELD_INVALID = "{field} is invalid";
	public static final String GENERAL_DATA_NOT_AVAILABLE = "Requested data is not available";
	public static final String SPECIFIC_DATA_NOT_AVAILABLE = "{mandatory-field} data is not available";
	public static final String FIELD_ALREADY_EXIST = "{mandatory-field} already exists";
	public static final String PERSISTENCE_ERROR = "Data could not be saved";
	public static final String GENERAL_START_END_TIME = "Start date should be after end date";
	public static final String LIMIT_EXCEEDED = "{field} limit exceeded";
	
	public static final String CREATE_CONTACTS_PAYLOAD = "Create Contacts Payload";
	
	public static final String PERSIST_FAULT_EVENT_PAYLOAD = "Persist Fault Event Payload";
	
	public static final String GE_LATEST_FAULT_EVENT_PAYLOAD = "Get Latest Fault Event Payload";
	
	public static final String UPDATE_FAULT_EVENT_PAYLOAD = "Update Fault Event Payload";
	
	public static final String JWT_HEADER_INVALID = "JWT Header parameter is invalid";
	
}
