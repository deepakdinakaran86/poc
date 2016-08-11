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
package com.pcs.avocado.constans;

/**
 * @author pcseg199(Javid Ahammed)
 * @date November 2015
 * @since device-cloud-1.0.0
 */
public class ResourceConstants {

	public static final String JWT_ASSERTION = "JWT Token for Authorization";
	public static final String JWT_ASSERTION_SAMPLE = "header.eyJDb25zdW1lcktleSI6Ik9KYXIxVEh4QWdDWlVrM0dmM0dYbEtUTUhXZ2EifQ==.signature";

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

	public static final String JWT_HEADER_INVALID = "JWT Header parameter is invalid";

	public static final String DEVICE_SOURCE_ID = "SourceId of the device";
	public static final String DEVICE_SOURCE_ID_SAMPLE = "34562789456124";
	public static final String TEMPLATE_NAME = "Device Configuration Template name";
	public static final String TEMPLATE_NAME_SAMPLE = "Hyster3p0TX";
	public static final String DATA_TYPE = "Data Type";
	public static final String DATA_TYPE_SAMPLE = "Double";
	public static final String PHYSICAL_QUANTITY = "Physical Quantity";
	public static final String PHYSICAL_QUANTITY_SAMPLE = "current";
	public static final String PARAMETER_NAME = "Name of the parameter";
	public static final String PARAMETER_NAME_SAMPLE = "Heat Exchange";
	public static final String PQ_QUANTITY_NAME = "Name of the physical quantity";
	public static final String PQ_QUANTITY_NAME_SAMPLE = "Temperature";
	public static final String MAKE = "Device Manufacturer/Vendor";
	public static final String MAKE_SAMPLE = "Teltonika";
	public static final String SUBSCRIBER = "Name of the Subscriber";
	public static final String SUBSCRIBER_SAMPLE = "Cummins";
	public static final String DS = "Datasource name";
	public static final String DS_SAMPLE = "App_Demo";

	public static final String START_TIME_MS = "Start Time in milliseconds";
	public static final String START_TIME_MS_SAMPLE = "1447110879183";

	public static final String END_TIME_MS = "End Time in milliseconds";
	public static final String END_TIME_MS_SAMPLE = "1447151468901";

	public static final String DEVICE_CLAIM_DTO_PAYLOAD = "Claim Device DTO Payload";
	public static final String DEVICE_PAYLOAD = "Device Payload";
	public static final String LIST_CONFIG_PAYLOAD = "List of Configuration Points";
	public static final String DEVICE_CONFIG_BATCH_PAYLOAD = "List of sourceIds and a Configuration Template";
	public static final String ASSIGN_DEVICE_CONFIG_TEMP_PAYLOAD = "List of sourceIds and a Configuration Template Name";
	public static final String LIST_DEVICE_TAGS_PAYLOAD = "List of device tags";
	public static final String PROTOCOL_PAYLOAD = "Payload to mention protocol version";
	public static final String DEVICE_CONFIG_TEMPLATE_PAYLOAD = "Device Configuration Template Payload";
	public static final String CONFIG_SEARCH_PAYLOAD = "Configuration Search Payload";
	public static final String TEMPLATE_NAMES_PAYLOAD = "Template Names Payload";
	public static final String PHYSICAL_QUANTITY_PAYLOAD = "Physical Quantity Payload";
	public static final String COMMAND_PAYLOAD = "Command Payload";
	public static final String PARAMETER_PAYLOAD = "Parameter Payload";
	public static final String CONFIGURATION_SEARCH_PAYLOAD = "Configuration Search Payload";
	public static final String WRITE_BACK_RESP_PAYLOAD = "Write Back Response Payload";
	public static final String UNIT_PAYLOAD = "Unit Payload";
	public static final String DATA_PAYLOAD = "Data Payload";
	public static final String SEARCH_PAYLOAD = "Search Payload";
	public static final String LIST_DEVICE_DATA_PAYLOAD = "List of Device data";
	public static final String DS_PAYLOAD = "Data source Payload";
	public static final String DS_NAMES_PAYLOAD = "Data source names Payload";
	public static final String MESSAGE_PAYLOAD = "Message Payload";
	public static final String SOURCE_IDS_PAYLOAD = "Array of Source Ids";
	public static final String DEVICE_IDENTIFIER = "Identifier value";
	public static final String DEVICE_IDENTIFIER_SAMPLE = "054ca3ea-a59e-4c13-ad23-6c239106b5ac";
	public static final String HIERARCHY_PAYLOAD = "Hierarchy Payload";
	public static final String TENANT_IDENTITY_PAYLOAD = "Tenant Identity Payload";
	public static final String ASSIGN_PAYLOAD = "Assign Device Payload";
	public static final String ASSIGN_CLIENT_DEVICE_PAYLOAD = "Assign Client Device Payload";
	public static final String GET_DEVICES_PAYLOAD = "Get Devices Payload";
	public static final String DOMAIN = "Domain Name";
	public static final String DOMAIN_SAMPLE = "hsbc.pcs.alpine.com";
	public static final String LIVE_ALARMS_PAYLOAD = "Live Alarms Payload";
	public static final String ALARMS_PAYLOAD = "Alarms History Payload";

}
