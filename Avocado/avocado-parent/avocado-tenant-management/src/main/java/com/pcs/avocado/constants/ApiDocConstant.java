package com.pcs.avocado.constants;

/**
 * @description Responsible for holding the constants related to Swagger Api
 *              documentation
 * @author Daniela (PCSEG191)
 * @date Jan 2016
 * @since galaxy-1.0.0
 */
public interface ApiDocConstant {

	public final static String CREATE_TENANT_DESC = "This service is used to create a tenant, this service has a sequence, which will first create a tenant and then create a tenant node, the response returned will be from the second sequence, sample request : "
	        + "{\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"fieldValues\":[{\"key\":\"tenantId\",\"value\":\"aquamax25\"},{\"key\":\"tenantName\",\"value\":\"aquamax25\"},{\"key\":\"contactEmail\",\"value\":\"danielac@pacificcontrols.net\"},{\"key\":\"firstName\",\"value\":\"daniela\"},{\"key\":\"lastName\",\"value\":\"c\"},{\"key\":\"domain\",\"value\":\"aquamax25\"}],\"templates\":[{\"entityTemplateName\":\"ResetPassword\",\"platformEntityType\":\"MARKER\"}]}";
	public final static String CREATE_TENANT_SUMMARY = "Create a tenant";
	public final static String CREATE_TENANT_SUCCESS_RESP = "{\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"DefaultTenant\"},\"platformEntity\":{\"platformEntityType\":\"TENANT\"},\"identifier\":{\"key\":\"tenantId\",\"value\":\"aquamax25\"}}";

	public final static String FIND_TENANT_DESC = "This service to be used to find a tenant using tenant ID ";
	public final static String FIND_TENANT_SUMMARY = "Find tenant";
	public final static String FIND_TENANT_SUCCESS_RESP = "{\"platformEntity\":{\"platformEntityType\":\"TENANT\"},\"domain\":{\"domainName\":\"alpine.com\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"DefaultTenant\"},\"fieldValues\":[{\"key\":\"contactEmail\",\"value\":\"choco100@example.com\"},{\"key\":\"firstName\",\"value\":\"choco100\"},{\"key\":\"lastName\",\"value\":\"choco100\"},{\"key\":\"tenantId\",\"value\":\"choco100\"},{\"key\":\"tenantName\",\"value\":\"choco100\"},{\"key\":\"domain\",\"value\":\"example.abc\"}],\"dataprovider\":[],\"identifier\":{\"key\":\"tenantId\",\"value\":\"choco100\"}}";

	public final static String FIND_ALL_TENANT_DESC = "This service is used to list tenants";
	public final static String FIND_ALL_TENANT_SUMMARY = "List tenants";
	public final static String FIND_ALL_TENANT_SUCCESS_RESP = "[{\"platformEntity\":{\"platformEntityType\":\"TENANT\"},\"domain\":{\"domainName\":\"alpine.com\"},\"entityStatus\":{\"statusKey\":0,\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"domain\":{\"domainName\":\"alpine.com\"},\"entityTemplateName\":\"DefaultTenant\",\"platformEntityType\":\"TENANT\"},\"dataprovider\":[],\"identifier\":{\"key\":\"tenantId\",\"value\":\"choco100\"}}]";

	public final static String UPDATE_TENANT_DESC = "This service is used to update an existing tenant, sample request : "
	        + "{\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"fieldValues\":[{\"key\":\"contactEmail\",\"value\":\"choco100@example.com\"},{\"key\":\"firstName\",\"value\":\"aquamax18\"},{\"key\":\"lastName\",\"value\":\"aquamax18\"}]}";
	public final static String UPDATE_TENANT_SUMMARY = "Update tenant";
	public final static String UPDATE_TENANT_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String TENANT_PAYLOAD = "Tenant Payload";
	public final static String TENANT_SEARCH_PAYLOAD = "Tenant Search Payload";
	
	public final static String CREATE_TENANT_ADMIN_DESC = "This service is used to create a tenant admin, sample request : "
	        + "{\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"fieldValues\":[{\"key\":\"contactNumber\",\"value\":\"3332222419241\"},{\"key\":\"firstName\",\"value\":\"daniela\"},{\"key\":\"lastName\",\"value\":\"c\"},{\"key\":\"emailId\",\"value\":\"danielac@pacificcontrols.net\"},{\"key\":\"userName\",\"value\":\"danielacncc\"}],\"tenantAdminLinkIdentifier\":\"15c1a510-d0f1-4e4a-82ff-e2f2ddac7f09\",\"setPasswordUrl\":\"http://localhost:8080/management-console/setpwd\",\"domain\":{\"domainName\":\"aquamax20.pcs.alpine.com\"}}";
	public final static String CREATE_TENANT_ADMIN_SUMMARY = "Create a tenant admin";
	public final static String CREATE_TENANT_ADMIN_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";
	
	public final static String SEND_TENANT_ADMIN_DESC = "This service is used to send a tenant admin email, sample request : "
	        + "{\"email\":\"danielac@pacificcontrols.net\",\"createTenantAdminUrl\":\"http://localhost:8080/management-console/createTenantUser\",\"tenantDomain\":\"aquamax20.pcs.alpine.com\"}";
	public final static String SEND_TENANT_ADMIN_SUMMARY = "Send tenant admin email";
	public final static String SEND_TENANT_ADMIN_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public static final String GENERAL_FIELD_NOT_SPECIFIED = "{mandatory-field} not specified";
	public static final String GENERAL_FIELD_INVALID = "{field} is invalid";
	public static final String GENERAL_DATA_NOT_AVAILABLE = "Requested data is not available";
	public static final String PERSISTENCE_ERROR = "Data could not be saved";
	public static final String GENERAL_FIELD_NOT_UNIQUE = "{field} is not unique";

}
