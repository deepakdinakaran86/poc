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

package com.pcs.devicecloud.enums;

import com.pcs.devicecloud.commons.validation.DataFields;

/**
 * MailContent
 * 
 * @description This class defines enums being used for mail content
 * @author Riyas PH (pcseg296)
 * 
 */

public enum CommonFields implements DataFields {

	TO_ADDRESS("toAddress", "toAddress", "To Address"), CONTENT("content",
	        "content", "Content"), SUBJECT("subject", "subject", "Subject"),
	EMAIL_TEMPLATE("emailTemplate", "emailTemplate", "Email Template"), TO(
	        "to", "to", "To"), URL("url", "url", "URL"), USER_NAME("userName",
	        "userName", "User Name"), DOMAIN_NAME("domainName", "domainName",
	        "Domain Name");

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
