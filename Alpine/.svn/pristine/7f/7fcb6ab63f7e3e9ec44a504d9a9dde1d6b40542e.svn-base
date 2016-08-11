package com.pcs.alpine.constants;

/**
 * @description Responsible for holding the constants related to Swagger Api
 *              documentation
 * @author Twinkle (PCSEG297)
 * @date Oct 2015
 * @since galaxy-1.0.0
 */
public interface ApiDocConstant {

	public final static String CREATE_TENANT_DESC = "This service is used to create a tenant, this service has a sequence, which will first create a tenant and then create a tenant node and assign features to the tenant, the response returned will be the identifier fields of the tenant, sample request : "
	        + "{\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"fieldValues\":[{\"key\":\"tenantName\",\"value\":\"jll\"},{\"key\":\"tenantId\",\"value\":\"jll\"},{\"key\":\"contactEmail\",\"value\":\"jll@example.com\"},{\"key\":\"firstName\",\"value\":\"jlladmin\"},{\"key\":\"lastName\",\"value\":\"c\"},{\"key\":\"domain\",\"value\":\"jll.galaxy\"}],\"features\":[\"Tenant\",\"Device\",\"Event Management\",\"Device Management\"]}";
	public final static String CREATE_TENANT_SUMMARY = "Create a tenant";
	public final static String CREATE_TENANT_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String FIND_TENANT_DESC = "This service to be used to find a tenant, sample request : "
	        + "{\"domain\":{\"domainName\":\"pcs.galaxy\"},\"identifier\":{\"key\":\"tenantId\",\"value\":\"jll\"}}";
	public final static String FIND_TENANT_SUMMARY = "Find tenant";
	public final static String FIND_TENANT_SUCCESS_RESP = "{\"platformEntity\":{\"platformEntityType\":\"TENANT\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"DefaultTenant\"},\"fieldValues\":[{\"key\":\"contactEmail\",\"value\":\"jll@example.com\"},{\"key\":\"firstName\",\"value\":\"jllAdmin\"},{\"key\":\"lastName\",\"value\":\"c\"},{\"key\":\"tenantName\",\"value\":\"jll\"},{\"key\":\"tenantId\",\"value\":\"jll\"},{\"key\":\"domain\",\"value\":\"jll.galaxy\"}],\"dataprovider\":[],\"identifier\":{\"key\":\"tenantId\",\"value\":\"jll\"}}";

	public final static String FIND_ALL_TENANT_DESC = "This service is used to list tenants, sample request : "
	        + "{\"domain\":{\"domainName\":\"pcs.galaxy\"}}";
	public final static String FIND_ALL_TENANT_SUMMARY = "List tenants";
	public final static String FIND_ALL_TENANT_SUCCESS_RESP = "[{\"platformEntity\":{\"platformEntityType\":\"TENANT\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityStatus\":{\"statusKey\":0,\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityTemplateName\":\"DefaultTenant\",\"platformEntityType\":\"TENANT\"},\"dataprovider\":[],\"identifier\":{\"key\":\"tenantName\",\"value\":\"choco100\"}}]";

	public final static String UPDATE_TENANT_DESC = "This service is used to update an existing tenant, sample request : "
	        + "{\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"fieldValues\":[{\"key\":\"contactEmail\",\"value\":\"jll@example.com\"},{\"key\":\"firstName\",\"value\":\"john\"},{\"key\":\"lastName\",\"value\":\"d\"},{\"key\":\"tenantName\",\"value\":\"jll\"},{\"key\":\"tenantId\",\"value\":\"jll\"},{\"key\":\"domain\",\"value\":\"jll.galaxy\"}],\"identifier\":{\"key\":\"tenantId\",\"value\":\"jll\"}}";
	public final static String UPDATE_TENANT_SUMMARY = "Update tenant";
	public final static String UPDATE_TENANT_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String DELETE_TENANT_NOTES = "This is the service to be used to delete a tenant, sample request : "
	        + "{\"domain\":{\"domainName\":\"pcs.galaxy\"},\"identifier\":{\"key\":\"tenantId\",\"value\":\"jll\"}}";
	public final static String DELETE_TENANT_VALUE = "Delete tenant";

	public final static String VALIDATE_TENANT_DESC = "This service is used check if the specified field is unique across the domain, sample request : "
	        + "{\"fieldValues\":[{\"key\":\"tenantName\",\"value\":\"chocos\"}],\"uniqueAcross\":\"Application\",\"domain\":{\"domainName\":\"chocos.galaxy\"}}";
	public final static String VALIDATE_TENANT_SUMMARY = "Check if marker field is unique";
	public final static String VALIDATE_TENANT_SUCCESS_RESP = "[{\"status\":\"SUCCESS\"},{\"status\":\"FAILURE\"}]";

	public final static String GET_TENANT_INFO = "This service is used to get tenant info by domain name, request payload is not required for this service";
	public final static String GET_TENANT_INFO_SUMMARY = "Fetch tenant info by domain name";
	public final static String GET_TENANT_INFO_SUCCESS_RESP = "{\"platformEntity\":{\"platformEntityType\":\"TENANT\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"DefaultTenant\"},\"fieldValues\":[{\"key\":\"contactEmail\",\"value\":\"jll@example.com\"},{\"key\":\"domain\",\"value\":\"jll.galaxy\"},{\"key\":\"firstName\",\"value\":\"jack\"},{\"key\":\"lastName\",\"value\":\"Smith\"},{\"key\":\"tenantName\",\"value\":\"jll\"},{\"key\":\"tenantId\",\"value\":\"jll\"},{\"key\":\"superTenant\",\"value\":\"false\"}],\"dataprovider\":[],\"identifier\":{\"key\":\"tenantId\",\"value\":\"jll\"},\"hierarchy\":{\"domain\":{\"domainName\":\"pcs.galaxy\"}}}";

	public final static String GET_TENANT_FEATURES = "This service is used to get the features of a tenant by domain name, request payload is not required for this service";
	public final static String GET_TENANT_FEATURES_SUMMARY = "Find tenant's features";
	public final static String GET_TENANT_FEATURES_SUCCESS_RESP = "[\"Tenant\",\"Device\",\"Event Management\"]";

	public final static String ASSIGN_TENANT_FEATURES_DESC = "This service is used to assign a tenant with features, sample request : "
	        + "[\"Tenant\",\"Device\",\"Event Management\"]";
	public final static String ASSIGN_TENANT_FEATURES_SUMMARY = "Assign features to a tenant";
	public final static String ASSIGN_TENANT_FEATURES_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String TENANT_PAYLOAD = "Tenant Payload";
	public final static String TENANT_SEARCH_PAYLOAD = "Tenant Search Payload";

	public static final String GENERAL_FIELD_NOT_SPECIFIED = "{mandatory-field} not specified";
	public static final String GENERAL_FIELD_INVALID = "{field} is invalid";
	public static final String GENERAL_DATA_NOT_AVAILABLE = "Requested data is not available";
	public static final String PERSISTENCE_ERROR = "Data could not be saved";
	public static final String GENERAL_FIELD_NOT_UNIQUE = "{field} is not unique";

	public final static String GET_TENANT_COUNT_DESC = "This service is used get the tenant count, sample request : "
	        + "{\"domain\":{\"domainName\":\"alpine.com\"}}";
	public final static String GET_TENANT_COUNT_SUMMARY = "Get tenant count";
	public final static String GET_TENANT_COUNT_SUCCESS_RESP = "{\"count\": 10}";
}
