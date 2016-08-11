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
package com.pcs.guava.driver.enums;

import com.pcs.guava.commons.validation.DataFields;

/**
 * DriverDataFields Enum
 * 
 * @author Twinkle (PCSEG297)
 * 
 */
public enum DriverDataFields implements DataFields {

	DOMAIN("domain", "domain", "Domain"),
	DOMAIN_NAME("domainName", "domainName", "Domain Name"),
	IDENTIFIER("identifier", "identifier", "Identifier"),
	KEY("key", "key", "Key"),
	VALUE("value", "value", "Value"),
	
	IDENTIFIER_KEY("", "", "Identifier Key"),
	IDENTIFIER_VALUE("value", "value", "Identifier Value"),
	
	ENTITY_TEMPLATE("entityTemplate", "entityTemplate", "Entity Template"),
	ENTITY_TEMPLATE_NAME("entityTemplateName", "entityTemplateName", "Entity Template Name"),
	
	DRIVER_ID("driverId", "driverId", "Driver Id"),
	NAME("name", "name", "Name"),
	EMPLOYEE_CODE("employeeCode", "employeeCode", "Employee Code"),
	NATIONALITY("nationality", "nationality", "Nationality"),
	DATE_OF_BIRTH("dateOfBirth", "dateOfBirth", "Date Of Birth"),
	MOBILE_NUMBER("mobileNumber", "mobileNumber", "Mobile Number"),
	STATUS("status", "status", "Status"),
	DRIVER_IDENTIFIER("driverIdentifier", "driverIdentifier", "Driver Identifier"),
	SEARCH_FIELDS("", "searchFields", "Search Fields"),

	;


	private String fieldName;
	private String variableName;
	private String description;

	private DriverDataFields(String fieldName, String variableName,
	        String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getVariableName() {
		return variableName;
	}

	public String getDescription() {
		return description;
	}

	public static DriverDataFields getEnum(String fieldName) {
		for (DriverDataFields v : values()) {
			if (v.getFieldName().equalsIgnoreCase(fieldName)) {
				return v;
			}
		}
		throw new IllegalArgumentException();
	}
	
}
