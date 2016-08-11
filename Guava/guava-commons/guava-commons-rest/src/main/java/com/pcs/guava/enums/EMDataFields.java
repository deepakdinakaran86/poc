/**
 *
 */
package com.pcs.guava.enums;

import java.util.HashMap;
import java.util.Map;

import com.pcs.guava.commons.validation.DataFields;

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
public enum EMDataFields implements DataFields {

	UUID("uuid", "uuid", "UUID"),
	VALIDATION_TYPE("validation_type", "", ""),
	NAME("name", "", ""),
	STATUS_KEY("status_key", "statusKey", "Status Key"),
	STATUS_NAME("status_name", "statusName", "Status Name"),
	END_POINT("end_point", "", ""),
	IN_PAYLOAD("in_payload", "", ""),
	OUT_PAYLOAD("out_payload", "", ""),
	VALIDATION("validation", "", ""),
	COMPONENT_TYPE("component_type", "", ""),
	GLOBAL_ENTITY_NAME("global_entity_name", "", ""),
	STATUS("status", "status", "Status"),
	PLATFORM_ENTITY("platformEntity", "platformEntity", "Platform Entity"),
	GLOBAL_ENTITIES("global_entities", "globalEntities", "Global Entities"),
	ENTITY("entity", "entity", "Entity"),
	ORG("org", "", ""),
	FIELD_VALUES("field_values", "fieldValues", "Field Values"),
	VALIDATION_TYPE_ID("validation_type_id", "", ""),
	ENTITY_TEMPLATE_NAME("entity_template_name", "entityTemplateName",
	        "Entity Template Name"),
	PLATFORM_ENTITY_TEMPLATE_NAME("platform_entity_template_name",
	        "platformEntityTemplateName", "Platform Entity Template Name"),
	PLATFORM_ENTITY_TYPE("platform_entity_type", "platformEntityType",
	        "Platform Entity Type"),
	PARENT_TEMPLATE("parent_template", "parentTemplate", "Parent Template"),
	DOMAIN("domain", "domain", "Domain"),
	SELECTED_DOMAIN("selectedDomain", "selectedDomain", "Selected Domain"),
	DOMAIN_TYPE("domain_type", "domainType", "Domain Type"),
	ENTITY_ID("entity_id", "entityId", "Entity Id"),
	FIELD_VALIDATION("field_validation", "fieldValidation", "Field Validation"),
	HTML_PAGE("html_page", "htmlPage", "Html page"),
	LAST_SEQ_TIME("last_seq_time", "", ""),
	INSERTED_TIME("inserted_time", "", ""),
	REVISION_TIME("revision_time", "", ""),
	TYPE_CODE("type_code", "", ""),
	BEGINNING("begining", "", ""),

	RECEIVED_DATA("received_data", "receivedData", "Received Data"),
	RECEIVED_TIME("received_time", "receivedTime", "Received Time"),
	RECEIVED_DATE("received_date", "receivedDate", "Received Date"),
	RECEIVED_CAUSE("received_cause", "receivedCause", "Received Cause"),
	IS_DEFAULT("is_default", "", ""),
	// /
	CLIENT("CLIENT", "CLIENT", "CLIENT"),
	ORGANIZATION("ORGANIZATION", "ORGANIZATION", "ORGANIZATION"),
	SIM("SIM", "SIM", "SIM"),
	MARKER("MARKER", "MARKER", "MARKER"),
	DEVICE("DEVICE", "device", "DEVICE"),
	ASSET("ASSET", "ASSET", "ASSET"),
	USER("USER", "USER", "USER"),
	USER_NAME("userName", "userName", "User Name"),
	TO_MOBILE_NUMBER("toMobileNumber", "toMobileNumber", "To Mobile Number"),
	BODY("body", "body", "Body"),
	EMAIL_ID("emailId", "emailId", "email ID"),
	TENANT_NAME("tenantName", "tenantName", "Tenant Name"),
	CHILD("Child", "Child", "Child"),
	ORPHAN("Orphan", "Orphan", "Orphan"),
	ROW("row", "row", "row"),
	GRAPH("graph", "graph", "graph"),
	DATA("data", "graph", "graph"),
	RESULTS("results", "results", "results"),

	//
	ALARM_DATA("alarm_data", "alarmData", "Alarm Data"),
	ENTITY_NAME("entityName", "entityName", "Entity name"),
	PARENT_ENTITY_ID("parentEntityId", "parentEntityId", "Parent Entity Id"),

	//
	ENTITY_TEMPLATE("entityTemplate", "entityTemplate", "Entity Template"),
	ENTITY_TEMPLATES("entityTemplates", "entityTemplates", "Entity Templates"),
	ACCESS_ENTITY_IDS("accessEntityIds", "accessEntityIds", "Access Entity Ids"),

	//
	START_DATE("start_date", "startDate", "Start date"),
	END_DATE("end_date", "endDate", "End date"),

	//
	ENTITY_DATA("entity_data", "entityData", "Entity data"),

	ENTITY_DATA_STORE("", "", "Entity data store"),
	ENTITY_LIVE_DATA("", "", "Entity live data"),
	ENTITY_LIST("", "", "Entity list"),
	//
	SEARCH_FIELD("search_field", "searchField", "Search Field"),
	//
	USER_TYPE("userType", "userType", "User Type"),
	//
	FIELD_MAP("field_map", "fieldMap", "Field Map"),
	FIELD_KEY("key", "key", "Key"),
	FIELD_VALUE("value", "value", "Value"),
	//
	FILENAME("filename", "fileName", "File Name"),
	FILELOCATION("file_location", "fileLocation", "File Location"),
	FILECONTENT("file_content", "fileContent", "File Content"),
	MODIFEDDATE("modifed_date", "modifedDate", "Modifed Date "),
	//
	//
	ACCESS("access", "access", "Access"),
	ADMIN("admin", "admin", "Admin"),
	TRUE("true", "true", "True"),

	// Menu
	LIMITED_TO_ADMIN("admin", "admin", "Admin"),
	LIMITED_TO_SUPER_ADMIN("superadmin", "superadmin", "Super Admin"),

	//

	IS_MODIFIABLE("is_modifiable", "isModifiable", "Is Modifiable"),
	IS_SHARABLE("is_sharable", "isSharable", "Is Sharable"),
	IDENTIFIER("identifier", "identifier", "Identifier"),
	UNIQUE_USER_ID("uniqueUserId", "uniqueUserId", "Unique User Id"),
	LOGGED_IN_USER_DOMAIN("loggedInUserDomain", "loggedInUserDomain",
	        "Logged In User Domain"),

	//
	RELATIONSHIP("relationship", "relationship", "Relationship"),
	ENTITY_TYPES("entity_types", "entityTypes", "Entity Types"),
	CURRENT_ENTITY_ID("current_entity_id", "currentEntityId",
	        "Current Entity Id"),
	ENTITY_TYPE("entity_type", "entityType", "Entity Type"),
	//
	DATAPROVIDER("dataprovider", "dataprovider", "Dataprovider"),
	TREE("tree", "tree", "Tree"),

	PARENT_ENTITY_TYPE("globalEntityType", "globalEntityType",
	        "Parent Entity Type"),

	HIERARCHY("hierarchy", "hierarchy", "Hierarchy"),
	ENTITY_STATUS("entityStatus", "entityStatus", "Entity Status"),
	IDENTIFIER_VALUE("value", "value", "Identifier Value"),
	PARENT_DOMAIN("parentDomain", "parentDomain", "Parent Domain"),
	SUPER_TENANT("superTenant", "superTenant", "Super Tenant"),
	MAX_NO_USERS("maxUsers", "maxUsers", "Max Users"),
	MAX_CONCURRENT_USERS("maxConcurrentUsers", "maxConcurrentUsers",
	        "Max Concurrent Users"),
	ENTITY_TEMPLATE_LIST("", "", "Entity Templates"),
	TENANT("TENANT", "TENANT", "Tenant"),
	USER_ENTITY("user", "user", "User"),

	MENU("menu", "menu", "Menu"),
	MENU_LIST("", "", "Menus"),
	FEATURE_GROUPS("", "", "Feature Groups"),
	ORG_NAME("", "orgName", "Organization Name"),
	ORG_FULL_NAME("", "orgFullName", "Organization Fullname"),
	CLIENT_NAME("clientName", "clientName", "Client Name"),
	CLIENT_FULL_NAME("clientFullName", "clientFullName", "Client Full Name"),
	MENUS("menus", "menus", "Menus"),
	DOMAIN_NAME("domain_name", "domainName", "Domain Name"),
	FEATURE_GROUP("feature_group", "featureGroup", "Feature Group"),
	MAX_USERS("maxNoOfUsers", "maxNoOfUsers", "Max Users"),
	CONCURRENT_USERS("concurrentNoOfUsers", "concurrentNoOfUsers",
	        "Concurrent No of Users"),
	GALAXY_URL("galaxyURL", "galaxyURL", "Galaxy URL"),
	ROLE_NAME("roleName", "roleName", "Role Name"),
	DEVICE_MENU("device_feature", "devicefeature", "device feature"),
	DEVICE_SYNC_FLAG("devicesyncflag", "devicesyncflag", "Device Sync Flag"),
	PASSWORD("password", "password", "Password"),
	NEWPASSWORD("newPassword", "newPassword", "newPassword"),
	FIRST_NAME("firstName", "firstName", "First Name"),
	LAST_NAME("lastName", "lastName", "Last Name"),
	DESIGNATION("designation", "designation", "Designation"),
	CONTACT_NUMBER("contactNumber", "contactNumber", "Contact Number"),
	PRIMARY_EMAIL("primaryEmail", "primaryEmail", "Primary Email"),
	SECONDARY_EMAIL("secondaryEmail", "secondaryEmail", "Secondary Email"),
	LOCATION("location", "location", "location"),
	RESET_PSWD_URL("resetPwdUrl", "resetPwdUrl", "Reset Password Url"),
	ROLE("role", "role", "Role"),
	IDENTIFIER_KEY("key", "key", "Identifier Key"),
	RESET_PWD_URL("resetPwdUrl", "resetPwdUrl", "Reset Password Url"),
	MENU_NAME("menuName", "menuName", "Menu Name"),
	CATEGORY_NAME("categoryName", "categoryName", "Category Name"),
	URL_LINK("urlLink", "urlLink", "Url Link"),
	IS_ADMIN("isAdmin", "isAdmin", "Is Admin"),
	NOTIFICATION_CODE("notificationCode", "notificationCode",
	        "notificationCode"),
	ACCESS_TOKEN("accessToken", "accessToken", "Access Token"),
	REFRESH_TOKEN("refreshToken", "refreshToken", "Refresh Token"),
	CLIENT_ID("clientId", "clientId", "Client Id"),
	CLIENT_SECRET("clientSecret", "clientSecret", "Client Secret"),
	GRANT_TYPE("grantType", "grantType", "Grant Type"),
	SCOPE("scope", "scope", "Scope"),
	IMEI("imei", "imei", "IMEI"),
	DEVICE_NUMBER("deviceNumber", "deviceNumber", "Device Number"),
	MAKE("make", "make", "Make"),
	MODEL("model", "model", "Model"),
	COMMUNICATION_GATEWAY("COMMUNICATIONGATEWAY", "communicationGateway",
	        "Communication Gateway"),
	HOST_ID("hostId", "hostId", "Host Id"),
	PROTOCOL("protocol", "protocol", "Protocol"),
	SERIAL_NUMBER("serialNumber", "serialNumber", "Serial Number"),
	VENDOR_NAME("vendorName", "vendorName", "Vendor Name"),
	VEHICLE_NUMBER("vehicleNumber", "vehicleNumber", "Vehicle Number"),
	VEHICLE_TRANSMISSION_TYPE("transmissionType", "Transmission Type",
	        "Transmission Type"),
	EQUIPMENT_NAME("equipmentName", "equipmentName", "Equipment Name"),
	EQUIPMENT_TYPE("equipmentType", "equipmentType", "Equipment Type"),
	SIM_NUMBER("simNumber", "simNumber", "SIM Number"),
	IP("ip", "ip", "IP"),
	SERVICE_PROVIDER("serviceProvider", "serviceProvider", "Service Provider"),
	SIM_TYPE("simType", "simType", "SIM Type"),
	PIN("pin", "pin", "PIN"),
	PUK("puk", "puk", "PUK"),
	ACTIVATION_DATE("activationDate", "activationDate", "Activation Date"),
	NOTES("notes", "notes", "Notes"),
	WIFI_NAME("wifiName", "wifiName", "WIFI Name"),
	CHASSIS_NUMBER("chassisNumber", "chassisNumber", "Chassis Number"),
	PARENT_ENTITY("parentEntity", "parentEntity", "Parent Entity"),
	DEVICE_ASSIGNED("deviceAssigned", "deviceAssigned", "Device Assigned"),
	CONF_TEMPLATE("confTemplate", "confTemplate", "Configuration Template"),
	UNIT_ID("unit_id", "unitId", "Unit Id"),
	HIERARCHY_DOMAIN("", "", "Parent Domain"),
	HIERARCHY_DOMAIN_NAME("domainName", "domainName", "Parent Domain Name"),
	HIERARCHY_GLOBAL_ENTITY("global_entity", "globalEntity",
	        "Parent Global Entity"),
	HIERARCHY_ENTITY_TEMPLATE_NAME("entity_template_name",
	        "entityTemplateName", "Parent Entity Template Name"),
	HIERARCHY_ENTITY_TEMPLATE("entityTemplate", "entityTemplate",
	        "Parent Entity Template"),
	HIERARCHY_IDENTIFIER("identifier", "identifier", "Parent Identifier"),
	HIERARCHY_GLOBAL_ENTITY_TYPE("global_entity_type", "globalEntityType",
	        "Parent Global Entity Type"),
	COUNTER_REF("counter_ref", "unitIDCounter", ""),
	G21_UNIT_ID_COUNTER("g21unitIDCounter", "g21unitIDCounter", ""),
	INVALID_TEMPLATE_DOMAIN("", "", "Entity Template Domain"),
	ADMINISTRATOR("administrator", "administrator", "administrator"),
	LIMIT("limit", "limit", "Limit"),
	DATASOURCE("datasourceName", "datasourceName", "Datasource Name"),
	REQUEST_TIME("requestTime", "request_time", "Request Time"),
	DEVICE_ID("device_id", "deviceid", "Device Id"),
	WRITE_BACK_ID("writeback_id", "writebackId", "Write Back Id"),
	REQUEST_ID("request_id", "requestId", "Request Id"),
	POINT_ID("point_id", "pointId", "Point Id"),
	ATTEMPTS("attempts", "attempts", "Attempts"),
	REMARKS("remarks", "remarks", "Remarks"),
	UPDATED_TIME("updated_time", "updatedTime", "Updated Time"),
	LAST_EDITED_TIME("lastEditedTime", "last_edited_time", "last edited time"),
	JWTHEADER("JWT Header parameter", "JWT Header parameter",
	        "JWT Header parameter"),
	CONTACT_EMAIL("contactEmail", "contactEmail", "Contact Email"),
	RETURN_FIELD("returnField", "returnField", "Return Field"),
	FIELD_NAME("fieldName", "fieldName", "Field Name"),
	RETURN_FIELDS("returnFields", "returnFields", "Return Fields"),
	SEARCH_FIELDS("searchFields", "searchFields", "Search Fields"),

	IDENTIFIER_FIELD("identifier_field", "identifierField", "Identifier Field"),
	TENANT_ID("tenantId", "tenantId", "Tenant Id"), APPLICATION("application",
	        "application", "application"), UNIQUE_ACCESS("uniqueAccess",
	        "uniqueAccess", "Unique Access"), EMAIL_LINK("emailLink",
	        "emailLink", "Email Link"), TIME_STAMP("timeStamp", "timeStamp",
	        "Time Stamp"), VALID("valid", "valid", "Valid"), MY_DOMAIN(
	        "mydomain", "mydomain", "My Domain"), RESET_PASSWORD_TEMPLATE(
	        "ResetPassword", "ResetPassword", "ResetPassword"), FEATURE_LIST(
	        "feature_list", "featureList", "Feature List"), ;

	private String fieldName;
	private String variableName;
	private String description;

	private EMDataFields(String fieldName, String variableName,
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

	private static final Map<String, EMDataFields> map;
	static {
		map = new HashMap<String, EMDataFields>();
		for (EMDataFields v : EMDataFields.values()) {
			map.put(v.getFieldName(), v);
		}
	}

	public static EMDataFields getDataField(String fieldName) {
		EMDataFields result = map.get(fieldName);
		if (result == null) {
			throw new IllegalArgumentException("No Category Exists");
		}
		return result;
	}

}
