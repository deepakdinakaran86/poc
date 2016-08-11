/**
 *
 */
package com.pcs.avocado.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.avocado.commons.validation.DataFields;

/**
 * TenantFields
 * 
 * @description status and the status codes
 * @author Daniela (PCSEG191)
 * @date 11 Jan 2016
 * @since alpine-1.0.0
 */
public enum TMDataFields implements DataFields {

	TENANT_ADMIN_ROLE("TenantAdmin", "", ""), MARKER("MARKER", "", ""),
	CREATE_ADMIN_USER("Create Admin User", "", ""),
	CREATE_ADMIN_USER_EMAIL_TEMPLATE("/tenantAdminEmail.vm", "", ""),
	SET_PASSWORD_EMAIL_TEMPLATE("/setPasswordEmail.vm", "", ""),
	SET_PASSWORD_EMAIL("Set Password", "", ""), STRING_FORMAT("%1$s%2$s%3$s",
	        "", ""), DOMAIN("domain", "domain", "Domain"), DOT(".", "", ""),
	GALAXY("galaxy", "galaxy", "galaxy"), TENANT_NAME("tenantName", "tenantName", "Tenant Name"), TENANT_ID("tenantId", "tenantId", "Tenant ID"),
	ENTITY_NAME("entityName", "", ""), TIME_STAMP("timeStamp", "", ""), VALID(
	        "valid", "", ""), USER_NAME("userName", "", ""), RESET_PASSWORD(
	        "ResetPassword", "", ""), TENANT_ADMIN_USER_TEMPLATE(
	        "TenantAdminUser", "", ""), IDENTIFIER("identifier", "", ""),
	MY_DOMAIN("myDomain", "", ""), EMAIL_ID("emailId", "", ""),
	TENANT_ADMIN_LINK_ID("tenantAdminLinkIdentifier",
	        "tenantAdminLinkIdentifier", "Tenant Admin Link Identifier"),
	SET_PASSWORD_URL("setPasswordUrl", "setPasswordUrl", "Set Password URL"),
	CREATE_TENANT_ADMIN_URL("createTenantAdminUrl", "createTenantAdminUrl",
	        "Create Tenant Admin URL"), TENANT_DOMAIN("tenantDomain",
	        "tenantDomain", "Tenant Domain"), DOMAIN_NAME("domain_name",
	        "domainName", "Domain Name"), ROLE_NAME("roleName", "roleName",
	        "Role Name"), EMAIL("email", "email", "Email");

	private String fieldName;
	private String variableName;
	private String description;

	private TMDataFields(String fieldName, String variableName,
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

	private static final Map<String, TMDataFields> map;
	static {
		map = new HashMap<String, TMDataFields>();
		for (TMDataFields v : TMDataFields.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static TMDataFields getDataField(String fieldName) {
		TMDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}

}
