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

package com.pcs.avocado.enums;

import com.pcs.avocado.commons.validation.DataFields;

/**
 * MailContent
 * 
 * @description This class defines enums being used for mail content
 * @author Daniela (pcseg191)
 * @date 22 July 2014
 * @since galaxy-1.0.0
 */

public enum CommonFields implements DataFields {

	TO_ADDRESS("toAddress", "toAddress", "To Address"),

	CONTENT("content", "content", "Content"),

	SUBJECT("subject", "subject", "Subject"),

	EMAIL_TEMPLATE("emailTemplate", "emailTemplate", "Email Template"),

	TO("to", "to", "To"), URL("url", "url", "URL"),

	USER_NAME("userName", "userName", "User Name"),

	DOMAIN_NAME("domainName", "domainName", "Domain Name"),

	ACTOR("actor", "actor", "Actor"),

	SUBJECTS("subjects", "subjects", "Subjects"),

	IDENTIFIER("identifier", "identifier", "Identifier"),

	IDENTIFIER_VALUE("value", "value", "Identifier Value"),

	IDENTIFIER_KEY("key", "key", "Identifier Key");

	private String fieldName;
	private String variableName;
	private String description;

	private CommonFields(String fieldName, String variableName,
	        String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	@Override
	public String toString() {
		return this.fieldName;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public String getVariableName() {
		return variableName;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
