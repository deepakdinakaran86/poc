package com.pcs.avocado.constants;

/**
 * @author PCSEG296 RIYAS PH
 * @date November 2015
 * @since Alpine-1.0.0
 */
public interface NameConstants {

	public final static String REDIRECT = "redirect:";

	// Path names
	public final static String LOGIN_PATH_NAME = "login";
	public final static String HOME_PATH_NAME = "home";
	public final static String SET_PWD = "setpwd";
	public final static String SET_PASSWORD_PATH_NAME = "set_password";
	public final static String LOGOUT_PATH_NAME = "logout";
	public final static String AUTHENTICATE_PATH_NAME = "authenticate";
	public final static String OWNER_HOME_PATH_NAME = "owner_home";
	public final static String CLIENT_HOME_PATH_NAME = "client_home";
	public final static String WB_LOGS_PATH_NAME = "wblogs";
	public final static String PT_CONFIGS_PATH_NAME = "ptconfigs";
	public final static String TENANTS_PATH_NAME = "tenants";
	public final static String USERS_PATH_NAME = "users";
	public final static String LIVE_TRACKING_PATH_NAME = "live_track";
	public final static String DEVICE_CONFIG_PATH_NAME = "device_config";
	public final static String CONFIG_TEMP_CREATE_PATH_NAME = "config_template_create";
	public final static String CREATE_TENANT_USER = "createTenantUser";
	public final static String ALARM_CONSOLE_PATH_NAME = "alarm_console";

	// View names
	final static String LOGIN_VIEW = "login";
	final static String SET_PASSWORD_VIEW = "set_password";
	final static String OWNER_HOME_VIEW = "owner_home";
	final static String CLIENT_HOME_VIEW = "client_home";
	public final static String WB_LOGS_VIEW = "wblogs";
	public final static String PT_CONFIGS_VIEW = "ptconfigs";
	public final static String TENANTS_VIEW = "tenants";
	public final static String USERS_VIEW = "users";
	public final static String LIVE_TRACKING = "live_tracking";
	public final static String DEVICE_CONFIG = "device_config_template";
	public final static String ALARM_CONSOLE = "alarm_console";

	// DataField names
	final static String CONFIG_TEMP_VIEW = "config_template_view";
	final static String CONFIG_TEMP_CREATE = "config_template_create";
	final static String CONFIG_TEMP_FORM = "config_template_form";

	// DataField names
	final static String ENTITY_NAME = "entityName";
	final static String DEVICE = "device";
	final static String DATASOURCE_NAME = "datasourceName";
	final static String VALID_ASSETS = "validAssets";
	final static String INVALID_ASSETS = "invalidAssets";
	final static String WEB_SOCKET_URL = "webSocketUrl";
	final static String DESTINATION = "destination";
	final static String LATEST_DATA = "latestData";
	final static String ALLOCATED = "allocated";

	// Devices
	final static String DEVICES_HOME_PATH_NAME = "device_home";
	final static String DEVICE_HOME_VIEW = "device_home";
	final static String DEVICE_CREATE_PATH_NAME = "device_create";
	final static String DEVICE_VIEW_PATH_NAME = "device_view";
	final static String DEVICE_FORM_VIEW = "device_form";
	final static String ASSIGN_DEVICE_PATH = "assign_device";
	final static String ASSIGN_DEVICE_VIEW = "assign_device";
	final static String ASSIGN_DEVICE_ACTION_PATH = "assign_device_action";

	final static String TRUE = "true";
	final static String FALSE = "false";
	final static String DEVICE_TEMPLATE = "Device";
	final static String ACTIVE = "active";
	final static String PUBLISH = "publish";
	final static String SOURCE_ID = "sourceId";
	final static String CONFIGURATION = "configuration";
	final static String TAGS = "tags";
	final static String MAKE = "make";
	final static String DEVICE_TYPE = "deviceType";
	final static String MODEL = "model";
	final static String PROTOCOL = "protocol";
	final static String VERSION = "version";
	final static String PROTOCOL_VERSION = "protocolVersion";
	final static String NETWORK_PROTOCOL = "networkProtocol";
	final static String DEVICE_IP = "deviceIp";
	final static String WRITEBACK_PORT = "writebackPort";
	final static String DEVICE_PORT = "devicePort";
	final static String IDENTIFIER = "identifier";

	// tenants
	final static String TENANT_HOME = "tenant_home";
	final static String TENANT_FORM = "tenant_form";
	final static String TENANT_VIEW = "tenant_view";
	final static String TENANT_LIST = "tenant_list";
	final static String TENANT_CREATE = "tenant_create";
	final static String TENANT_UPDATE = "tenant_update";
	final static String TENANT_SEND_USER_EMAIL_FORM = "tenant_send_email_form";
	final static String TENANT_SEND_USER_EMAIL = "tenant_send_email_view";
	final static String TENANT_SEND_USER_EMAIL_ACTION = "tenant_send_email";
	final static String TENANT_SEND_USER_CREATE = "tenant_admin_user_form";
	final static String TENANT_USER_CREATED = "tenant_user_created";

	final static String TENANT_NAME = "tenantName";
	final static String FIRST_NAME = "firstName";
	final static String LAST_NAME = "lastName";
	final static String CONTACT_EMAIL = "contactEmail";
	final static String DOMAIN = "domain";
	final static String MY_DOMAIN = "myDomain";
	final static String EMAIL_ID = "emailId";

	final static String INACTIVE = "inactive";

	// Field Names
	final static String MARKER = "MARKER";
	final static String TENANT = "TENANT";
	public static final String TENANT_TEMPLATE = "DefaultTenant";
	public static final String WRITE_BACK_PATH_NAME = "write_back";
	public static final String ROLE_TENANT_ADMIN = "TenantAdmin";
	public static final String ROLE_SUPER_ADMIN = "SuperAdmin";
	public static final String WRITE_BACK_VIEW = "write_back";

	final static String CONTACT_NUMBER = "contactNumber";
	final static String PRIMARY_EMAIL = "emailId";
	final static String ROLE_NAME = "roleName";
	final static String USER_NAME = "userName";
	/* USER_CONSTANTS */
	final static String USER_CREATE = "user_create";
	final static String USER_VIEW = "user_view";
	final static String USER_UPDATE = "user_update";
	final static String USER_DELETE = "user_delete";
	final static String USER_HOME = "user_home";
	final static String USER_FORM = "user_form";
	final static String USER_TEMPLATE = "DefaultUser";
	// Role
	final static String ROLE_HOME = "role_home";
	final static String ROLE_FORM = "role_form";
	final static String ROLE_VIEW = "role_view";
	final static String ROLE_CREATE = "role_create";
	final static String ROLE_UPDATE = "role_update";
	final static String ROLE_DELETE = "role_delete";

	final static String NO_ACCESS = "User has no Access";

	final static String HISTORY_TRACKING_VIEW = "historytracking";
	public final static String HISTORY_TRACKING_PATH_NAME = "historytracking";

	// Claim Device
	final static String CLAIM_HOME_PATH_NAME = "claim_home";
	final static String CLAIM_HOME_VIEW_NAME = "claim_view";
	final static String CLAIM_FORM_PATH_NAME = "claim_form";
	final static String CLAIM_FORM_VIEW_NAME = "claim_form";
	final static String CLAIM_DEVICE_PATH_NAME = "claim_device";

	// templates
	final static String RESET_PSWD_TEMPLATE = "ResetPassword";

	// templates
	final static String TENANT_ADMIN_TEMPLATE = "TenantAdminUser";
	final static String TIME_STAMP = "timeStamp";
	final static String VALID = "valid";

	final static String CREATE_TENANT_ADMIN = "create_tenant_admin";

	public static final String EVENT_CONF_PATH_NAME = "event_conf";
	public static final String EVENT_CONF_VIEW = "event_conf";

	public static final String POINT_NAME = "pointName";
	public static final String MAX_VALUE = "maxValue";
	public static final String MIN_VALUE = "minValue";
	public static final String COMPARE_VALUE = "compareValue";
	public static final String EVENT_TYPE = "alertType";

	public final static String EVENT_DEFINITION_TEMPLATE = "AlertDefinition";

	public static final String EVENT_CONF_CREATE_PATH_NAME = "event_conf_create";

	public static final String RESET_PASSWORD_TEMPLATE = "ResetPassword";
	
	public static final String SET_PWD_EMAIL_TEMPLATE = "setPasswordEmail.vm";

	public static final String SET_PASSWORD_MAIL_SUBJECT = "Set Password";

}
