/**
 *
 */
package com.pcs.avocado.enums;

import com.pcs.avocado.commons.validation.DataFields;

/**
 * DataFields Enum
 * 
 * DataFields present in the DB should be placed here
 * 
 * @description Enum defining the data fields in the DB.
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since avocado-1.0.0
 */
public enum UMDataFields implements DataFields {

	MENU("menu", "menu", "Menu"), MENU_LIST("", "", "Menu List"),
	IS_FROM_REGISTRATION("", "", "Is from registration"), FEATURE_GROUPS("",
	        "", "Feature Groups List"), ENTITY_TEMPLATES("", "",
	        "Entity Templates List"), ORG_NAME("", "orgName",
	        "Organization Name"), ORG_FULL_NAME("", "orgFullName",
	        "Organization Fullname"), CLIENT_NAME("", "clientName",
	        "Client Name"), CLIENT_FULL_NAME("", "clientFullName",
	        "Client Fullname"), MENUS("menus", "menus", "Menus"), DOMAIN_NAME(
	        "domain_name", "domainName", "Domain Name"), FEATURE_GROUP(
	        "feature_group", "featureGroup", "Feature Group"), MAX_USERS(
	        "maxNoOfUsers", "maxNoOfUsers", "Max Users"), CONCURRENT_USERS(
	        "concurrentNoOfUsers", "concurrentNoOfUsers",
	        "Concurrent No of Users"), GALAXY_URL("galaxyURL", "galaxyURL",
	        "Galaxy URL"), ROLE_NAME("roleName", "roleName", "Role Name"),
	NEW_ROLE_NAME("newRoleName", "newRoleName", "New Role Name"), USER_NAME(
	        "userName", "userName", "User Name"), PASSWORD("password",
	        "password", "Password"), USER_TYPE("userType", "userType",
	        "User Type"), FIRST_NAME("firstName", "firstName", "First Name"),
	LAST_NAME("lastName", "lastName", "Last Name"), DESIGNATION("designation",
	        "designation", "Designation"), CONTACT_NUMBER("contactNumber",
	        "contactNumber", "Contact Number"), EMAIL_ID("emailId",
	        "emailId", "Email Id"), SECONDARY_EMAIL("secondaryEmail",
	        "secondaryEmail", "Secondary Email"), LOCATION("location",
	        "location", "location"), RESET_PSWD_URL("resetPwdUrl",
	        "resetPwdUrl", "Reset Password Url"), DOMAIN("domain", "domain",
	        "Domain"), ENTITY_TEMPLATE("entityTemplate", "entityTemplate",
	        "Entity Template"), FIELD_KEY("key", "key", "Key"), FIELD_VALUE(
	        "value", "value", "Value"), PLATFORM_ENTITY_DTO("platformEntityDTO",
	        "platformEntityDTO", "Platform Entity DTO"), PLATFORM_ENTITY_TYPE(
	        "platform_entity_type", "platformEntityType", "Platform Entity Type"),
	PLATFORM_ENTITY("platform_entity", "platformEntity", "Platform Entity"),
	IDENTIFIER("identifier", "identifier", "identifier"), PARENT_ENTITY_ID(
	        "parentEntityId", "parentEntityId", "Parent Entity Id"), ROLE(
	        "role", "role", "Role"), USER("USER", "USER", "USER"),
	ENTITY_STATUS("entityStatus", "entityStatus", "Entity Status"),
	FIELD_VALUES("field_values", "fieldValues", "Field Values"), STATUS_NAME(
	        "status_name", "statusName", "Status Name"), IDENTIFIER_KEY("key",
	        "key", "Identifier Key"), IDENTIFIER_VALUE("value", "value",
	        "Identifier Value"), ENTITY_TEMPLATE_NAME("entity_template_name",
	        "entityTemplateName", "Entity Template Name"), ACCESS("access",
	        "access", "Access"), ADMIN("admin", "admin", "Admin"),
	ORGANIZATION("ORGANIZATION", "ORGANIZATION", "ORGANIZATION"),
	RESET_PWD_URL("resetPwdUrl", "resetPwdUrl", "Reset Password Url"), CLIENT(
	        "CLIENT", "CLIENT", "CLIENT"), RESOURCE_NAME(
	    	        "resourceName", "resourceName", "Resource Name"),
	 PERMISSIONS("permissions", "permissions", "Permissions"),
	 FAILED("failed", "failed", "Failed"),SUBSCRIBE("subscribe", "subscribe", "Subscribe"),
	 RESOURCE_NEW_NAME(
 	        "newResourceName", "newResourceName", "New Resource Name"), 
 	        
 	 EMAIL_LINK("emailLink","emailLink","Email Link");
	
	

	private String fieldName;
	private String variableName;
	private String description;

	private UMDataFields(String fieldName, String variableName,
	        String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	@Override
	public String toString() {
		return this.fieldName;
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
}
