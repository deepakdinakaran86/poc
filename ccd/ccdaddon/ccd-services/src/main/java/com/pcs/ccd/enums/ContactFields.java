
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
 * Jan 23, 2016
 */
public enum ContactFields implements DataFields{
	
	CONTACTS("contacts", "contacts", "Contacts"),
	NAME("name", "name", "Contact Name"),
	EMAIL("email", "email", "email"),
	CONTACT_NUMBER("contactNumber", "contactNumber", "Contact Number"),
	CONTACT_TYPE("contactType", "contactType", "Tenant Contact Type"),
	ROW_IDENTIFIER("rowIdentifier", "rowIdentifier", "Contact Row Identifier"),
	CONTACT_NAME("contactName", "contactName", "Contact Name"),
	CONTACTS_IDS("contactIds", "contactIds", "Contact Ids"),
	CONTACT_ID("contactId", "contactId", "Contact Id"),
	CONTACT("contact", "contact", "Contact");
	
	String fieldName;
	String variableName;
	String description;
	
	private ContactFields(String fieldName, String variableName,
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
