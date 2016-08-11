/**
 *
 */
package com.pcs.alpine.services.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.alpine.commons.validation.DataFields;

/**
 * DataFields Enum
 * 
 * DataFields present in the DB should be placed here
 * 
 * @description Enum defining the data fields in the DB.
 * @author Anish Prabhakaran(PCSEG271)
 * @date 25th Aug 2014
 * @since galaxy-1.0.0
 */
public enum NotificationDataFields implements DataFields {

	TO_ADDRESS("", "toAddresses", "To Address"),
	CONTENT("", "content", "Content"),
	SMTP_HOST("", "smtpHost", "SmtpHost"),
	TO("", "to", "To"),
	EMAIL_TEMPLATE("", "emailTemplate", "Email Template"),
	USER_NAME("", "userName", "userName")
	;

	private String fieldName;
	private String variableName;
	private String description;

	private NotificationDataFields(String fieldName, String variableName,
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

	private static final Map<String, NotificationDataFields> map;
	static {
		map = new HashMap<String, NotificationDataFields>();
		for (NotificationDataFields v : NotificationDataFields.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static NotificationDataFields getDataField(String fieldName) {
		NotificationDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}

}
