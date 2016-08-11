package com.pcs.alpine.constants;

/**
 * @description Responsible for holding the constants related to Swagger Api
 *              documentation
 * @author Twinkle (PCSEG297)
 * @date Oct 2015
 * @since galaxy-1.0.0
 */
public interface ApiDocConstant {

	public final static String FIND_USER_DESC = "This service is used to find an user, sample request : "
	        + "{\"identifier\":{\"key\":\"userName\",\"value\":\"riyassayir\"},\"entityTemplate\":{\"entityTemplateName\":\"DefaultUser\"}}";
	public final static String FIND_USER_SUMMARY = "Find user";
	public final static String FIND_USER_SUCCESS_RESP = "{\"platformEntity\":{\"platformEntityType\":\"USER\"},\"domain\":{\"domainName\":\"alpine.com\"},\"entityStatus\":{\"statusKey\":1,\"statusName\":\"INACTIVE\"},\"entityTemplate\":{\"domain\":{\"domainName\":\"alpine.com\"},\"entityTemplateName\":\"DefaultUser\",\"platformEntityType\":\"USER\"},\"fieldValues\":[{\"key\":\"userName\",\"value\":\"bestuser\"},{\"key\":\"password\",\"value\":\"bestuser\"},{\"key\":\"contactNumber\",\"value\":\"123123123123\"},{\"key\":\"lastName\",\"value\":\"bestuser\"},{\"key\":\"emailId\",\"value\":\"bestuser@gmail.com\"},{\"key\":\"roleName\",\"value\":\"['admin']\"},{\"key\":\"firstName\",\"value\":\"bestuserq\"}],\"dataprovider\":[{\"key\":\"contactNumber\",\"value\":\"123123123123\"},{\"key\":\"firstName\",\"value\":\"bestuserq\"},{\"key\":\"lastName\",\"value\":\"bestuser\"},{\"key\":\"emailId\",\"value\":\"bestuser@gmail.com\"},{\"key\":\"userName\",\"value\":\"bestuser\"}],\"identifier\":{\"key\":\"userName\",\"value\":\"bestuser\"}}";

	public final static String FIND_ALL_USER_DESC = "This service is used to get all the users of a domain, request payload is not required for this service";
	public final static String FIND_ALL_USER_SUMMARY = "List users";
	public final static String FIND_ALL_USER_SUCCESS_RESP = "[{\"platformEntity\":{\"platformEntityType\":\"USER\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityTemplateName\":\"DefaultUser\"},\"dataprovider\":[{\"key\":\"contactNumber\",\"value\":\"2154545454\"},{\"key\":\"firstName\",\"value\":\"renjith\"},{\"key\":\"lastName\",\"value\":\"renjith\"},{\"key\":\"emailId\",\"value\":\"renjithp@pacificcontrols.net\"},{\"key\":\"userName\",\"value\":\"renjithk\"}],\"identifier\":{\"key\":\"userName\",\"value\":\"renjithk\"}}]";

	public final static String CREATE_USER_DESC = "This service is used to create a user, sample request : "
	        + "{\"entityStatus\":{\"statusName\":\"INACTIVE\"},\"fieldValues\":[{\"key\":\"contactNumber\",\"value\":\"3332222419241\"},{\"key\":\"firstName\",\"value\":\"Riyas241\"},{\"key\":\"lastName\",\"value\":\"sayir241\"},{\"key\":\"emailId\",\"value\":\"riyassayir241@pacificcontrols.net\"},{\"key\":\"roleName\",\"value\":\"['admin']\"},{\"key\":\"userName\",\"value\":\"riyassayir241\"}]}";
	public final static String CREATE_USER_SUMMARY = "Create user";
	public final static String CREATE_USER_SUCCESS_RESP = "{\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityStatus\":{\"statusName\":\"INACTIVE\"},\"fieldValues\":[{\"key\":\"contactNumber\",\"value\":\"1231231231221\"},{\"key\":\"firstName\",\"value\":\"bestuserq\"},{\"key\":\"lastName\",\"value\":\"bestuser\"},{\"key\":\"emailId\",\"value\":\"bestuser1@gmail.com\"},{\"key\":\"roleName\",\"value\":\"['admin']\"},{\"key\":\"userName\",\"value\":\"bestuser1\"},{\"key\":\"password\",\"value\":\"bestuser1\"}],\"dataprovider\":[{\"key\":\"contactNumber\",\"value\":\"1231231231221\"},{\"key\":\"firstName\",\"value\":\"bestuserq\"},{\"key\":\"lastName\",\"value\":\"bestuser\"},{\"key\":\"emailId\",\"value\":\"bestuser1@gmail.com\"},{\"key\":\"userName\",\"value\":\"bestuser1\"}],\"identifier\":{\"key\":\"userName\",\"value\":\"bestuser1\"}}";

	public final static String UPDATE_USER_DESC = "This service is used to update a user, sample request : "
	        + "{\"entityStatus\":{\"statusName\":\"INACTIVE\"},\"fieldValues\":[{\"key\":\"contactNumber\",\"value\":\"3332222419241\"},{\"key\":\"firstName\",\"value\":\"Riyas241\"},{\"key\":\"lastName\",\"value\":\"sayir241\"},{\"key\":\"emailId\",\"value\":\"riyassayir241@pacificcontrols.net\"},{\"key\":\"roleName\",\"value\":\"['admin']\"},{\"key\":\"userName\",\"value\":\"riyassayir241\"}],\"identifier\":{\"key\":\"userName\",\"value\":\"riyassayir241\"}}";
	public final static String UPDATE_USER_VALUE = "Update user";
	public final static String UPDATE_USER_SUCCESS_RESP = "{\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityStatus\":{\"statusName\":\"INACTIVE\"},\"fieldValues\":[{\"key\":\"contactNumber\",\"value\":\"1231231231221\"},{\"key\":\"firstName\",\"value\":\"bestuserq\"},{\"key\":\"lastName\",\"value\":\"bestuser\"},{\"key\":\"emailId\",\"value\":\"bestuser1@gmail.com\"},{\"key\":\"roleName\",\"value\":\"['admin']\"},{\"key\":\"userName\",\"value\":\"bestuser1\"},{\"key\":\"password\",\"value\":\"bestuser1\"}],\"dataprovider\":[{\"key\":\"contactNumber\",\"value\":\"1231231231221\"},{\"key\":\"firstName\",\"value\":\"bestuserq\"},{\"key\":\"lastName\",\"value\":\"bestuser\"},{\"key\":\"emailId\",\"value\":\"bestuser1@gmail.com\"},{\"key\":\"userName\",\"value\":\"bestuser1\"}],\"identifier\":{\"key\":\"userName\",\"value\":\"bestuser1\"}}";

	public final static String DELETE_USER_DESC = "This service is used to delete an existing user, sample request : "
	        + "{\"identifier\":{\"key\":\"userName\",\"value\":\"riyassayir\"},\"entityTemplate\":{\"entityTemplateName\":\"DefaultUser\"}}";
	public final static String DELETE_USER_SUMMARY = "Delete user";
	public final static String DELETE_USER_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String CREATE_ADMIN_USER_DESC = "This service is used to createan admin user, sample request : "
	        + "{\"entityStatus\":{\"statusName\":\"INACTIVE\"},\"fieldValues\":[{\"key\":\"contactNumber\",\"value\":\"3332222419241\"},{\"key\":\"firstName\",\"value\":\"Riyas241\"},{\"key\":\"lastName\",\"value\":\"sayir241\"},{\"key\":\"emailId\",\"value\":\"riyassayir241@pacificcontrols.net\"},{\"key\":\"roleName\",\"value\":\"['admin']\"},{\"key\":\"userName\",\"value\":\"riyassayir241\"}]}";
	public final static String CREATE_ADMIN_USER_SUMMARY = "Create admin user";
	public final static String CREATE_ADMIN_USER_SUCCESS_RESP = "{\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityStatus\":{\"statusName\":\"INACTIVE\"},\"fieldValues\":[{\"key\":\"contactNumber\",\"value\":\"1231231231221\"},{\"key\":\"firstName\",\"value\":\"bestuserq\"},{\"key\":\"lastName\",\"value\":\"bestuser\"},{\"key\":\"emailId\",\"value\":\"bestuser1@gmail.com\"},{\"key\":\"roleName\",\"value\":\"['admin']\"},{\"key\":\"userName\",\"value\":\"bestuser1\"},{\"key\":\"password\",\"value\":\"bestuser1\"}],\"dataprovider\":[{\"key\":\"contactNumber\",\"value\":\"1231231231221\"},{\"key\":\"firstName\",\"value\":\"bestuserq\"},{\"key\":\"lastName\",\"value\":\"bestuser\"},{\"key\":\"emailId\",\"value\":\"bestuser1@gmail.com\"},{\"key\":\"userName\",\"value\":\"bestuser1\"}],\"identifier\":{\"key\":\"userName\",\"value\":\"bestuser1\"}}";

	public final static String VALIDATE_USER_DESC = "This service is used validate if the specified user field is unique, sample request : "
	        + "{\"domain\":{\"domainName\":\"alpine.com\"},\"fieldValues\":[{\"key\":\"userName\",\"value\":\"cummins\"}]}";
	public final static String VALIDATE_USER_SUMMARY = "Check if user field is unique";
	public final static String VALIDATE_USER_SUCCESS_RESP = "[{\"status\":\"SUCCESS\"},{\"status\":\"FAILURE\"}]";

	// Permission Group constants
	public final static String FIND_PG_DESC = "This service is used to find a resource within a domain, request payload is not required, Domain is not mandatory for this service. If the domain is not provided, then resource & its permissions will be fetched based on the logged in user's domain name ";
	public final static String FIND_PG_SUMMARY = "Find Resource";
	public final static String FIND_PG_SUCCESS_RESP = "{\"resourceName\":\"bin mgmnt\",\"permissions\":[\"Create\",\"Delete\",\"Edit\",\"View\"]}";

	public final static String CREATE_PG_DESC = "This service is used to create a resource and its permisions, sample request : "
	        + "{\"resourceName\":\"bin mgmnt\",\"permissions\":[\"View\",\"Edit\",\"Create\",\"Delete\"],\"domain\":{\"domainName\":\"cummins.alpine.com\"}}";
	public final static String CREATE_PG_SUMMARY = "Create Resource & Permissions";
	public final static String CREATE_PG_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String UPDATE_PG_DESC = "This service is used to add more permisions to the resource group, sample request : "
	        + "{\"resourceName\":\"bin mgmnt\",\"permissions\":[\"View\",\"Edit\",\"Create\",\"Delete\"],\"domain\":{\"domainName\":\"cummins.alpine.com\"}}";
	public final static String UPDATE_PG_SUMMARY = "Update Resource & Permissions : New permissions can be added.";
	public final static String UPDATE_PG_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String FIND_RESOURCE_NOTES = "This service is used to get all the Resources of a domain, request payload is not required,Domain is not mandatory for this service. If the domain is not provided, then resource will be fetched based on the logged in user's domain name ";
	public final static String FIND_RESOURCE_VALUE = "List all Resources of a domain";
	public final static String FIND_RESOURCE_SUCCESS_RESP = "{\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"resources\":[\"bin mgmnt\",\"template mgmnt\"]}";

	// Role constants
	public final static String CREATE_ROLE_NOTES = "This is the service to be used to create a role, sample request : "
	        + "{\"roleName\":\"A_MyRole001-002\",\"domainName\":\"cummins.alpine.com\",\"resources\":[{\"resourceName\":\"template mgmnt\",\"permissions\":[\"Create\"]}]}";
	public final static String CREATE_ROLE_VALUE = "Create role";
	public final static String CREATE_ROLE_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String UPDATE_ROLE_NOTES = "This is the service to be used to update a role, sample request : "
	        + "{\"roleName\":\"A_MyRole 001-001\",\"newRoleName\":\"A_MyRole 001-00121\",\"domainName\":\"dan1.example.com\",\"resources\":[{\"resourceName\":\"geofence mgmnt\",\"permissions\":[\"Create\"]}]}";
	public final static String UPDATE_ROLE_VALUE = "Update role";
	public final static String UPDATE_ROLE_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String FIND_ROLE_DESC = "This service is used to find a role, request payload is not required ";
	public final static String FIND_ROLE_SUMMARY = "Find role";
	public final static String FIND_ROLE_SUCCESS_RESP = "{\"roleName\":\"A_MyRole001-004\",\"domainName\":\"cummins.alpine.com\",\"resources\":[{\"resourceName\":\"template mgmnt\",\"permissions\":[\"create\"]}]}";

	public final static String FIND_ALL_ROLE_DESC = "This service is used to get all roles of a domain, request payload is not required ";
	public final static String FIND_ALL_ROLE_SUMMARY = "Get roles for domain";
	public final static String FIND_ALL_ROLE_SUCCESS_RESP = "[{\"roleName\":\"A_MyRole001-004\",\"domainName\":\"cummins.alpine.com\",\"resources\":[{\"resourceName\":\"template mgmnt\",\"permissions\":[\"create\"]}]}]";

	public final static String DELETE_ROLE_NOTES = "This is the service to be used to create a role, request payload is not required ";
	public final static String DELETE_ROLE_VALUE = "Delete role";
	public final static String DELETE_ROLE_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String FIND_PERMISSIONS_OF_USER_NOTE = "This service is used to get all the Resources & its permissions of the current user,Request payload is not required";
	public final static String FIND_PERMISSIONS_OF_USER_VALUE = "List permissions of the current user";
	public final static String FIND_PERMISSIONS_OF_USER_SUCCESS_RESP = "[ { \"resourceName\": \"alert mgmnt\", \"permissions\": [ \"edit\", \"view\", \"create\", \"delete\" ] }, { \"resourceName\": \"user_tech\", \"permissions\": [ \"edit\", \"create\" ] } ]";

	public static final String GENERAL_FIELD_NOT_SPECIFIED = "{mandatory-field} not specified";
	public static final String GENERAL_FIELD_NOT_UNIQUE = "{field} is not unique";
	public static final String GENERAL_FIELD_INVALID = "{field} is invalid";
	public static final String GENERAL_DATA_NOT_AVAILABLE = "Requested data is not available";
	public static final String FIELD_ALREADY_EXIST = "{mandatory-field} already exists";
	public static final String PERSISTENCE_ERROR = "Data could not be saved";
	public static final String PERMISSION_GROUP_CREATION_FAILED = "Error creating permission group";
	public static final String PERMISSION_GROUP_SUBSCRIPTION_FAILED = "Error while subscribing to permission group";
	public static final String PERMISSION_DUPLICATED = "Duplicates found in the permissions";
	public static final String FIELD_DATA_NOT_AVAILABLE = "{field} data is not available";
	public static final String NO_ACCESS = "User has no access";
	// (600, "User has no access", "User has no access"),

	public static final String PERMISSION_GROUP_PAYLOAD = "Permission Group Payload";
	public static final String USER_IDENTIY_PAYLOAD = "User Identity Payload";
	public static final String USER_PAYLOAD = "User Payload";
	public static final String USER_SEARCH_PAYLOAD = "User Search Payload";

	public final static String GET_USER_COUNT_DESC = "This service is used get the user count, sample request : "
	        + "{\"entityTemplate\":{\"entityTemplateName\":\"DefaultUser\"},\"domain\":{\"domainName\":\"cummins.alpine.com\"}}";
	public final static String GET_USER_COUNT_SUMMARY = "Get user count";
	public final static String GET_USER_COUNT_SUCCESS_RESP = "{\"count\": 10}";

	public final static String RESET_PASSWORD_DESC = "This service is used reset a password, sample request : "
	        + "{\"userName\":\"rakeshn\",\"password\":\"rakeshn\",\"domain\":\"cummins.alpine.com\"}";
	public final static String RESET_PASSWORD_SUMMARY = "Reset password";
	public final static String RESET_PASSWORD_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String GET_USER_STATUS_COUNT_DESC = "This service is used get the user count by status, if a specific status is needed specify the status name in the payload, sample request : "
	        + "{\"domain\":{\"domainName\":\"cummins.alpine.com\"}}";
	public final static String GET_USER_STATUS_COUNT_SUMMARY = "Get user count by status";
	public final static String GET_USER_STATUS_COUNT_SUCCESS_RESP = "[{\"count\":0,\"statusName\":\"UNALLOCATED\"},{\"count\":8,\"statusName\":\"DELETED\"},{\"count\":1,\"statusName\":\"INACTIVE\"},{\"count\":9,\"statusName\":\"ACTIVE\"}]";

	public final static String FORGOT_PASSWORD_DESC = "This service is used for reseting a password in the case a user forgets it so he gets a mail to start the reset password, sample request : "
	        + "{\"userName\":\"pcsdemo@pcs.galaxy\",\"emailId\":\"deepakd@pacificcontrols.net\",\"emailLink\":\"BASE_URL\"}";
	public final static String FORGOT_PASSWORD_SUMMARY = "Retrieve lost/forgotten password";
	public final static String FORGOT_PASSWORD_SUCCESS_RESP = "{\"entityStatus\":{\"statusName\":\"INACTIVE\"},\"fieldValues\":[{\"key\":\"contactNumber\",\"value\":\"3332222419241\"},{\"key\":\"firstName\",\"value\":\"Riyas241\"},{\"key\":\"lastName\",\"value\":\"sayir241\"},{\"key\":\"emailId\",\"value\":\"riyassayir241@pacificcontrols.net\"},{\"key\":\"roleName\",\"value\":\"['admin']\"},{\"key\":\"userName\",\"value\":\"riyassayir241\"}]}";

	public final static String CHANGE_PASSWORD_DESC = "This service is used for changing a password for a user when he wishes to use a new password, sample request : "
	        + "{\"userName\":\"pcsdemo@pcs.galaxy\",\"password\":\"pcsdemo1\",\"newPassword\":\"pcsdemo\"}";
	public final static String CHANGE_PASSWORD_SUMMARY = "Change a users password";
	public final static String CHANGE_PASSWORD_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

}
