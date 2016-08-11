
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.ccd.enums;

import com.pcs.avocado.commons.validation.DataFields;


/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 21, 2016
 */
public enum TenantFields implements DataFields{
	
	TENANT("tenant", "tenant", "Tenant"),
	TENANT_NAME("name", "tenantName", "Tenant Name"),
	TENANT_ID("id", "tenantId", "Tenant Id"),
	TENANT_CONTACT_FNAME("firstName", "contactFName", "Tenant Contact First Name"),
	TENANT_CONTACT_LNAME("lastName", "contactLName", "Tenant Contact Last Name"),
	TENANT_DOMAIN_NAME("tenantDomainName", "tenantDomainName", "Tenant Domain Name");
	
	String fieldName;
	String variableName;
	String description;
	
	private TenantFields(String fieldName, String variableName,
			String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
