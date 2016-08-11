package com.pcs.fms.web.constants;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
public interface FMSWebConstants {

	public final static String REDIRECT = "redirect:";
	public final static String USER_SET_PASSWORD = "set_user_password";

	// FTP Constants
	public final static String FILE_IMAGE_TYPE = ".png";
	public final static String VEHICLE_FOLDER_NAME = "vehicle";
	public final static String VEHICLE_TYPE_FOLDER_NAME = "vehicle_type";
	public final static String VEHICLE_TYPE_ICON_FOLDER_NAME = "vehicle_type_icon";
	public final static String POI_FOLDER_NAME = "poi";
	public final static String POI_TYPE_FOLDER_NAME = "poi_type";
	public final static String POI_TYPE_ICON_FOLDER_NAME = "poi_type_icon";
	public final static String USER_FOLDER_NAME = "user";
	public final static String TENANT_FOLDER_NAME = "tenant";

	// Path names
	public final static String LOGIN_PATH_NAME = "login";
	public final static String HOME_PATH_NAME = "home";
	public final static String SET_PWD = "setpwd";
	public final static String SET_PASSWORD_PATH_NAME = "set_password";
	public final static String LOGOUT_PATH_NAME = "logout";
	public final static String AUTHENTICATE_PATH_NAME = "authenticate";
	public final static String CURRENT_HOME_PATH_NAME = "current_home";
	public final static String MYOWN_HOME_PATH_NAME = "myown_home";
	public final static String OWNER_HOME_PATH_NAME = "owner_home";
	public final static String CLIENT_HOME_PATH_NAME = "client_home";
	public final static String USER_DEFAULT_HOME_PATH_NAME = "user_default";
	public final static String WB_LOGS_PATH_NAME = "wblogs";
	public final static String PT_CONFIGS_PATH_NAME = "ptconfigs";
	public final static String TENANTS_PATH_NAME = "tenants";
	public final static String USERS_PATH_NAME = "users";
	public final static String LIVE_TRACKING_PATH_NAME = "live_track";
	public final static String DEVICE_CONFIG_PATH_NAME = "device_config";
	public final static String CONFIG_TEMP_CREATE_PATH_NAME = "config_template_create";
	public final static String CREATE_TENANT_USER = "clientadmin";
	public final static String ALARM_CONSOLE_PATH_NAME = "alarm_console";
	public final static String FORGOT_PASSWORD = "forgot_password";

	// View names
	final static String LOGIN_VIEW = "login";
	final static String SET_PASSWORD_VIEW = "set_password";
	final static String OWNER_HOME_VIEW = "owner_home";
	final static String CLIENT_HOME_VIEW = "client_home";
	final static String USER_DEFAULT_HOME_VIEW = "user_default";
	public final static String WB_LOGS_VIEW = "wblogs";
	public final static String PT_CONFIGS_VIEW = "ptconfigs";
	public final static String TENANTS_VIEW = "tenants";
	public final static String USERS_VIEW = "users";
	public final static String LIVE_TRACKING = "live_tracking";
	public final static String DEVICE_CONFIG = "device_config_template";
	public final static String ALERT_CONSOLE = "alert_console";
	public final static String ALERT_DEFINITION = "alert_definition";
	public final static String ALERT_LOGS = "alert_logs";

	// DataField names
	final static String CONFIG_TEMP_VIEW = "config_template_view";
	final static String CONFIG_TEMP_CREATE = "config_template_create";
	final static String CONFIG_TEMP_FORM = "config_template_form";

	// DataField names
	final static String ENTITY_NAME = "entityName";
	final static String DEVICE = "device";
	final static String DEVICE_NAME = "deviceName";
	final static String VALID_ASSETS = "validAssets";
	final static String INVALID_ASSETS = "invalidAssets";
	final static String WEB_SOCKET_URL = "webSocketUrl";
	final static String DESTINATION = "destination";
	final static String LATEST_DATA = "latestData";
	final static String ALLOCATED = "allocated";
	final static String TEMPLATE = "TEMPLATE";
	final static String ASSET_ENTITY_TEMP_NAME = "Asset";
	final static String ENTITY = "ENTITY";
	final static String FIELD_KEY = "key";

	final static String PLATFORM_MARKER_TEMPLATE = "platformMarkerTemplate";

	// Devices
	final static String DEVICES_HOME_PATH_NAME = "device_home";
	final static String DEVICE_HOME_VIEW = "device_home";
	final static String DEVICE_CREATE_PATH_NAME = "device_create";
	final static String DEVICE_FORM_VIEW = "device_form";
	final static String ASSIGN_DEVICE_PATH = "device_assign";
	final static String ASSIGN_DEVICE_VIEW = "device_assign";
	final static String VIEW_DEVICE_PATH = "device_view";
	final static String VIEW_DEVICE_VIEW = "device_view";

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
	final static String LATITUDE = "latitude";
	final static String LONGITUDE = "longitude";

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
	final static String SUB_TENANT_VIEW = "sub_tenant_view";
	final static String VERIFY_SESSION = "verify";
	final static String ADD_TO_SESSION = "add";
	final static String CHILD_CLIENT = "CHILD_CLIENT";

	final static String TENANT_NAME = "tenantName";
	final static String FIRST_NAME = "firstName";
	final static String LAST_NAME = "lastName";
	final static String CONTACT_EMAIL = "contactEmail";
	final static String DOMAIN = "domain";
	final static String MY_DOMAIN = "myDomain";
	final static String EMAIL_ID = "emailId";
	final static String TENANT_ID = "tenantId";
	final static String CURRENT_DOMAIN = "currentDomain";
	final static String SELECTED_FEATURES = "selectedFeatures";
	final static String VIEW_CLIENT = "View Client";
	final static String EDIT_CLIENT = "Edit Client";
	final static String CREATE_CLIENT = "Create Client";
	final static String VALID_FIELD = "valid";

	final static String INACTIVE = "inactive";

	// Field Names
	final static String MARKER = "MARKER";
	final static String TENANT = "TENANT";
	final static String USER = "USER";
	public static final String TENANT_TEMPLATE = "DefaultTenant";
	public static final String USER_TEMPLATE = "DefaultUser";
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

	// TAG Types
	public final static String TAG_TYPE_HOME = "tagtype_home";
	public final static String TAG_TYPES_VIEW = "tag_type_view";
	public final static String TAG_TYPES_CREATE = "tagtype_create";
	public final static String TAG_TYPES_CREATE_VIEW = "tagtype_create";
	public final static String TAG_TYPES_CREATE_SERVICE = "tagtype_create_service";

	// TAG
	public final static String TAG_HOME = "tag_home";
	public final static String TAG_VIEW = "tag_view";
	public final static String TAG_CREATE = "tag_create";
	public final static String TAG_CREATE_SERVICE = "tag_create_service";
	public final static String NAME = "name";

	// templates
	final static String RESET_PSWD_TEMPLATE = "ResetPassword";

	// templates
	final static String TENANT_ADMIN_TEMPLATE = "TenantAdminUser";
	final static String TIME_STAMP = "timeStamp";
	final static String VALID = "valid";
	final static String SERVICE_COMPONENT_TEMPLATE = "ServiceComponent";

	final static String CREATE_TENANT_ADMIN = "create_tenant_admin";

	public static final String EVENT_CONF_PATH_NAME = "event_conf";
	public static final String EVENT_CONF_VIEW = "event_conf";

	public static final String POINT_NAME = "pointName";
	public static final String MAX_VALUE = "maxValue";
	public static final String MIN_VALUE = "minValue";
	public static final String COMPARE_VALUE = "compareValue";
	public static final String EVENT_TYPE = "alertType";

	public final static String EVENT_DEFINITION_TEMPLATE = "SaffronAlertDefinition";

	public static final String EVENT_CONF_CREATE_PATH_NAME = "event_conf_create";

	/* GEOFENCE */

	// geofence
	public static final String GEO_FENCE_CREATE = "geofence_create";
	public static final String GEO_FENCE_LIST = "geofence_list";
	public static final String GEO_FENCE_LIST_VIEW = "geofence_list";
	public final static String GEOFENCE_CANCEL = "geofence_cancel";

	public static final String GEO_FENCE_HOME = "geofence_home";
	public static final String GEO_FENCE_HOME_VIEW = "geofence_home";

	public static final String GEO_FENCE_VIEW = "geofence_view";
	public static final String GEO_FENCE_VIEW_PATH = "geofence_home";

	final static String GEOFENCE_LIST = "geofence_list";
	final static String UPDATE = "update";
	final static String EDIT = "edit";

	/* Route */
	final static String ROUTE_HOME = "route_home";
	final static String ROUTE_MAP = "route_map";
	final static String ROUTE_VIEW = "route_view";

	/* POI */
	final static String POI_TYPE_PATH_NAME = "poi_type_home";
	final static String POI_TYPE_HOME_VIEW = "poi_type_home";

	final static String POI_DEF_PATH_NAME = "poi_def_home";
	final static String POI_DEF_HOME_VIEW = "poi_def_home";

	final static String POI_DEF_VIEW_PATH_NAME = "poi_def_view";
	final static String POI_DEF_VIEW_HOME_VIEW = "poi_def_view";

	final static String POI_TYPE_VIEW_PATH_NAME = "poi_type_view";
	final static String POI_TYPE_VIEW_HOME_VIEW = "poi_type_view";

	final static String POI_TYPE_CREATE_PATH_NAME = "poi_type_create";
	final static String POI_TYPE_SAVE_PATH_NAME = "poi_type_save";
	final static String POI_CREATE_PATH_NAME = "poi_create";
	final static String POI_SAVE_PATH_NAME = "poi_save";
	final static String POI_VIEW_HOME_VIEW = "poi_view";
	final static String POI_DELETE_PATH_NAME = "poi_delete";

	/* Vehicle Management */

	final static String VEHICLE_MANAGEMENT_LIST = "vehicle_list";
	final static String VEHICLE_MANAGEMENT_LIST_VIEW = "vehicle_list";
	final static String VEHICLE_TYPE_HOME = "vehicle_type_home";
	final static String VEHICLE_TYPE_VIEW = "vehicle_type_view";

	final static String VEHICLE_HOME = "vehicle_home";
	final static String VEHICLE_HOME_VIEW = "vehicle_home";

	final static String VEHICLE_DASHBOARD = "vehicle_dashboard";
	final static String VEHICLE_TEMPLATE = "VEHICLE";

	final static String SERVICESCHEDULE_ADD = "schedule_add";
	final static String SERVICESCHEDULE_LIST = "schedule_list";
	final static String SERVICESCHEDULE_VIEW = "schedule_view";

	final static String SERVICEITEM_ADD_PATH = "item_add";
	final static String SERVICEITEM_ADD_SERVICE = "item_add";
	final static String SERVICEITEM_LIST = "item_list";
	final static String SERVICEITEM_VIEW_SERVICE = "item_view";
	final static String SERVICEITEM_VIEW_PATH = "item_view";
	final static String SERVICEITEM_UPDATE_SERVICE = "item_update";
	final static String SERVICEITEM_UPDATE_PATH = "item_list";

	final static String SERVICECOMPONENT_ADD = "component_add";
	final static String SERVICECOMPONENT_LIST = "component_list";
	final static String SERVICECOMPONENT_VIEW = "component_view";
	final static String SERVICECOMPONENT_NAME = "serviceComponentName";
	final static String SERVICECOMPONENTS_LIST = "serviceComponentsList";
	final static String SELECTED_COMPONENT_LIST = "selected_component_list";
	final static String COMPONENTS_TO_SAVE = "componentsToSave";

	final static String ADMIN_USER_NAME = "pcsadmin@pcs.galaxy";
	final static String ADMIN_PASSWORD = "password";

	final static String VEHICLE_TYPE_CREATE_PATH_NAME = "vehicle_type_create";
	final static String VEHICLE_TYPE_SAVE_PATH_NAME = "vehicle_type_save";
	final static String VEHICLE_TYPE_VIEW_PATH_NAME = "vehicle_type_view";
	final static String VEHICLE_CANCEL = "vehicle_cancel";
	final static String VEHICLE_TYPE_ERROR_PATH_NAME = "vehicle_type_error";

	final static String POINTS_MAPPING = "points";

	final static String CONFIG_HOME = "config_home";

	final static String VEHICLE_HISTORY = "vehicle_history";
	final static String POINT_LIST = "point_list";
	final static String VEHICLE_VIEW = "vehicle_view";

	/* Tracking */
	final static String TRACKING_VEHICLE_DETAILS = "vehicle_details";
	/* Service Component */
	final static String COMPONENT_NAME = "serviceComponentName";
	final static String DESCRIPTION = "description";
	final static String FREQ_IN_DIST = "frequencyInDistance";
	final static String FREQ_IN_TIME = "frequencyInTime";
	final static String FREQ_IN_HRS = "frequencyInRunningHours";
	final static String NOTIFICATION_IN_DIST = "notificationInDistance";
	final static String NOTIFICATION_IN_TIME = "notificationInTime";
	final static String NOTIFICATION_IN_HRS = "notificationInRunningHours";
	final static String SERVICE_ITEM_NAME = "serviceItemName";

	final static String AUDIT_LOGS = "auditlog";

	// Live Tracking
	final static String LIVE_TRACKING_HOME_PATH = "vehicle_tracking";
	final static String LIVE_TRACKING_HOME_VIEW = "vehicle_tracking";
	final static String VEHICLE_ICONS_HOME_PATH = "vehicle_icons";
	final static String VEHICLE_IMAGE_HOME_PATH = "vehicle_image";

	final static String ACTION = "action";
	final static String CREATE = "Create";
	final static String VIEW = "View";
	final static String PAGE_TITLE = "pageTitle";
	final static String RESPONSE = "response";

}
