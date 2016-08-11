package com.pcs.web.constants;

/**
 * @author PCSEG296 RIYAS PH
 * @date October 2015
 * @since Alpine-1.0.0
 */
public interface WebConstants {

	public final static String REDIRECT = "redirect:";

	// Path names
	public final static String LOGIN_PATH_NAME = "login";
	public final static String LOGOUT_PATH_NAME = "logout";
	public final static String AUTHENTICATE_PATH_NAME = "authenticate";
	public final static String HOME_PATH_NAME = "home";
	
	public final static String DEVICE_PATH_NAME = "device";
	public final static String VIEW_ALL_DEVICE_PATH_NAME = "device/list/filter";
	public final static String VIEW_DEVICE_DETAILS_PATH_NAME = "device/find";
	public final static String CREATE_DEVICE_PATH_NAME = "device";
	public final static String UPDATE_DEVICE_PATH_NAME = "device/update";

	// View names
	final static String LOGIN_VIEW = "login";
	final static String HOME_VIEW = "home";
	
	
	//DataField names
	final static String GENSET = "genset";
	final static String GENERATOR = "generator";
	final static String COMMUTER = "commuter";
	
	
	final static String ENTITY_NAME = "entityName";
	final static String DEVICE = "device";
	final static String ASSET_TYPE = "assetType";
	final static String ENGINE_MODEL = "engineModel";
	final static String ESN = "esn";
	
	final static String CONTACT_NUMBER = "contactNumber";
	final static String FIRST_NAME = "firstName";
	final static String LAST_NAME = "lastName";
	final static String PRIMARY_EMAIL = "primaryEmail";
	final static String ROLE_NAME = "roleName";
	final static String USER_NAME = "userName";
	
	final static String SOURCE_ID = "sourceId";
	final static String CONFIGURATION = "configuration";
	final static String TAGS = "tags";
	final static String DEVICE_TYPE = "deviceType";
	final static String PROTOCOL_TYPE = "protocolType";
	final static String PROTOCOL_VERSION = "protocolVersion";
	final static String PROTOCOL = "protocol";
	
	final static String NETWORK_PROTOCOL = "networkProtocol";
	final static String DEVICE_IP = "deviceIp";
	final static String WRITEBACK_PORT = "writebackPort";
	final static String DATASOURCE_NAME = "datasourceName";
	final static String PUBLISH = "publish";
	final static String DEVICE_PORT = "devicePort";
	final static String IDENTIFIER = "identifier";
	
	final static String DEVICE_HOME = "device_home";
	final static String DEVICE_FORM = "device_form";
	final static String DEVICE_VIEW = "device_view";
	final static String DEVICE_CREATE = "device_create";
	final static String DEVICE_UPDATE = "device_update";
	
	final static String VALID_ASSETS = "validAssets";
	final static String INVALID_ASSETS = "invalidAssets";
	final static String WEB_SOCKET_URL = "webSocketUrl";
	final static String DESTINATION = "destination";

	/*USER_CONSTANTS*/
	final static String USER_CREATE = "user_create";
	final static String USER_VIEW = "user_view";
	final static String USER_UPDATE = "user_update";
	final static String USER_HOME = "user_home";
	final static String USER_FORM = "user_form";

	final static String TRUE = "true";
	final static String FALSE = "false";
	
	final static String DEVICE_TEMPLATE = "G2021Device";
	
	
	final static String ACTIVE ="active";
	
	final static String LIVE_TRACKING = "live_tracking";
	
	final static String HISTORY_TRACKING_VIEW = "historytracking";
	public final static String HISTORY_TRACKING_PATH_NAME = "historytracking";
	
	//Genset
	final static String GENSET_HOME = "genset_home";
	final static String GENSET_FORM = "genset_form";
	
	final static String GENSET_VIEW = "genset_view";
	final static String GENSET_CREATE = "genset_create";
	final static String GENSET_UPDATE = "genset_update";
	
	final static String GENSET_TEMPLATE = "Genset";
	
	
	//Generator
	final static String GENERATOR_HOME = "generator_home";
	final static String GENERATOR_FORM = "generator_form";
		
	final static String GENERATOR_VIEW = "generator_view";
	final static String GENERATOR_CREATE = "generator_create";
	final static String GENERATOR_UPDATE = "generator_update";
	
	final static String GENERATOR_TEMPLATE = "Generator";
	
	//Commuter
	final static String COMMUTER_HOME = "commuter_home";
	final static String COMMUTER_FORM = "commuter_form";
	
	final static String COMMUTER_VIEW = "commuter_view";
	final static String COMMUTER_CREATE = "commuter_create";
	final static String COMMUTER_UPDATE = "commuter_update";
	
	final static String COMMUTER_TEMPLATE = "Commuter";
}
