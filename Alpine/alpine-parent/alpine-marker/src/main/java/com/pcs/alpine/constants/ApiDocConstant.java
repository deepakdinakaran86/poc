package com.pcs.alpine.constants;

/**
 * @description Responsible for holding the constants related to Swagger Api
 *              documentation
 * @author Twinkle (PCSEG297)
 * @date Oct 2015
 * @since galaxy-1.0.0
 */
public interface ApiDocConstant {

	public final static String FIND_MARKER_DESC = "This service is used to find a marker, sample request : "
	        + "{\"domain\":{\"domainName\":\"alpine.com\"},\"entityTemplate\":{\"entityTemplateName\":\"GlobalMARKERTemplate\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"Nepal\"}}";
	public final static String FIND_MARKER_SUMMARY = "Find an existing marker";
	public final static String FIND_MARKER_SUCCESS_RESP = "{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"Genset\"},\"fieldValues\":[{\"key\":\"assetType\",\"value\":\"asset_211\"},{\"key\":\"device\",\"value\":\"\"},{\"key\":\"engineModel\",\"value\":\"mosel_211\"},{\"key\":\"entityName\",\"value\":\"entity_211\"},{\"key\":\"esn\",\"value\":\"21121121\"},{\"key\":\"identifier\",\"value\":\"41146e07-cc8a-4351-8a8d-0935a7241ddb\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"41146e07-cc8a-4351-8a8d-0935a7241ddb\"}}";

	public final static String FIND_ALL_MARKER_DESC = "This service is used to get all markers of a domain, sample request : "
	        + "{\"domain\":{\"domainName\":\"alpine.com\"}}";
	public final static String FIND_ALL_MARKER_SUMMARY = "Get markers of a domain";
	public final static String FIND_ALL_MARKER_SUCCESS_RESP = "[{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityStatus\":{\"statusKey\":0,\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityTemplateName\":\"Genset\",\"platformEntityType\":\"MARKER\"},\"dataprovider\":[{\"key\":\"assetType\",\"value\":\"genset\"},{\"key\":\"device\",\"value\":\"412526757173\"},{\"key\":\"engineModel\",\"value\":\"model-data-center\"},{\"key\":\"entityName\",\"value\":\"Datacenter\"},{\"key\":\"esn\",\"value\":\"datacente-1211121\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"3ea151b8-1a74-4596-ae66-73b99754742a\"}}]";

	public final static String CREATE_MARKER_DESC = "This service is used to create a marker, sample request : "
	        + "{\"fieldValues\":[{\"key\":\"entityName\",\"value\":\"Nepal\"},{\"key\":\"identifier\",\"value\":\"Nepal\"}],\"entityTemplate\":{\"entityTemplateName\":\"GlobalMARKERTemplate\"}}";
	public final static String CREATE_MARKER_SUMMARY = "Create a marker";
	public final static String CREATE_MARKER_SUCCESS_RESP = "{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityTemplate\":{\"entityTemplateName\":\"Genset\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"6b9b0336-0314-4fc7-aecc-37087eb4e66d\"}}";

	public final static String UPDATE_MARKER_DESC = "This service is used to update an existing marker, sample request : "
	        + "{\"entityTemplate\":{\"entityTemplateName\":\"GlobalMARKERTemplate\"},\"domain\":{\"domainName\":\"alpine.com\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"fieldValues\":[{\"key\":\"entityName\",\"value\":\"NepalN\"},{\"key\":\"identifier\",\"value\":\"Nepal\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"Nepal\"}}";
	public final static String UPDATE_MARKER_SUMMARY = "Update a marker";
	public final static String UPDATE_MARKER_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String DELETE_MARKER_DESC = "This service is used to delete a marker, sample request : "
	        + "{\"domain\":{\"domainName\":\"alpine.com\"},\"entityTemplate\":{\"entityTemplateName\":\"GlobalCUSTOMTemplate\"},\"platformEntity\":{\"platformEntityType\":\"CUSTOM\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"Sharjah\"}}";
	public final static String DELETE_MARKER_SUMMARY = "Delete a marker";
	public final static String DELETE_MARKER_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String FILTER_MARKER_DESC = "This service is used to get all markers having the specified template for a domain, sample request : "
	        + "{\"domain\":{\"domainName\":\"alpine.com\"},\"entityTemplate\":{\"entityTemplateName\":\"Commuter\"}}";
	public final static String FILTER_MARKER_SUMMARY = "Get markers by domain and template";
	public final static String FILTER_MARKER_SUCCESS_RESP = "[{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityStatus\":{\"statusKey\":0,\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityTemplateName\":\"Genset\",\"platformEntityType\":\"MARKER\"},\"dataprovider\":[{\"key\":\"assetType\",\"value\":\"genset\"},{\"key\":\"device\",\"value\":\"412526757173\"},{\"key\":\"engineModel\",\"value\":\"model-data-center\"},{\"key\":\"entityName\",\"value\":\"Datacenter\"},{\"key\":\"esn\",\"value\":\"datacente-1211121\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"3ea151b8-1a74-4596-ae66-73b99754742a\"}}]";

	public final static String VALIDATE_MARKER_DESC = "This service is used check if the specified field is unique across the domain, sample request : "
	        + "{\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"fieldValues\":[{\"key\":\"entityName\",\"value\":\"sharjah\"}]}";
	public final static String VALIDATE_MARKER_SUMMARY = "Check if marker field is unique";
	public final static String VALIDATE_MARKER_SUCCESS_RESP = "[{\"status\":\"SUCCESS\"},{\"status\":\"FAILURE\"}]";

	public final static String SEARCH_MARKER_DESC = "This is the service to be used to perform a search on markers and return the requested fields, sample request : "
	        + "{\"searchFields\":[{\"key\":\"assetType\",\"value\":\"Commuter\"}],\"returnFields\":[\"engineModel\",\"esn\",\"identifier\"],\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityTemplate\": {\"entityTemplateName\": \"Commuter\"}}";
	public final static String SEARCH_MARKER_SUMMARY = "Search for markers";
	public final static String SEARCH_MARKER_SUCCESS_RESP = "[{\"returnFields\":[{\"key\":\"engineModel\",\"value\":\"srl_2001\"},{\"key\":\"esn\",\"value\":\"231002\"},{\"key\":\"identifier\",\"value\":\"247a93e7-2106-475c-92cb-d21889055a3d\"}]}]";

	public final static String GET_MARKER_COUNT_DESC = "This service is used get the marker count, sample request : "
	        + "{\"searchFields\":[{\"key\":\"assetType\",\"value\":\"Commuter\"}],\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityTemplate\":{\"entityTemplateName\":\"Commuter\"}}";
	public final static String GET_MARKER_COUNT_STATUS_DESC = "Get the marker count"
	        + "{\"entityTemplate\":{\"entityTemplateName\":\"AvocadoFacilityTemplate\"},\"domain\":{\"domainName\":\"pcs.alpine.com\"},\"statusName\":\"ACTIVE\"}";

	public final static String CREATE_MARKERS_DESC = "This service is used create a list of markers sample request : "
	        + "{\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entities\":[{\"fieldValues\":[{\"key\":\"pointId\",\"value\":\"565\"},{\"key\":\"dataType\",\"value\":\"BOOLEAN\"},{\"key\":\"pointName\",\"value\":\"Digital Input 50\"},{\"key\":\"physicalQuantity\",\"value\":\"status boolean\"},{\"key\":\"unit\",\"value\":\"unitless\"},{\"key\":\"dataSourceName\",\"value\":\"\"}],\"entityTemplate\":{\"entityTemplateName\":\"Point\"}}]}";
	public final static String CREATE_MARKERS_SUMMARY = "Create a list of markers ";
	static String CREATE_MARKERS_SUCCESS_RESP = "[{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Point\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"6b9b0336-0314-4fc7-aecc-37087eb4e66d\"}}]";

	public final static String UPDATE_MARKERS_STATUS_DESC = "This is the service to be used to update the status of a list of markers, sample request : "
	        + "[{\"domain\":{\"domainName\":\"man.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Point\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"440ac506-68a0-4f38-a69e-507c19fa2977\"}},{\"domain\":{\"domainName\":\"man.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Point\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"440ac506-68a0-4f38-a69e-507c19fa2976\"}}]";
	public final static String UPDATE_MARKERS_STATUS_SUMMARY = "Update status for markers";
	public final static String UPDATE_MARKERS_STATUS_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

	public final static String FIND_ALL_MARKERS_DESC = "This service is used to find a list of markers by their identifiers, sample request : "
	        + "[{\"domain\":{\"domainName\":\"alpine.com\"},\"entityTemplate\":{\"entityTemplateName\":\"GlobalMARKERTemplate\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"Nepal\"}}]";
	public final static String FIND_ALL_MARKERS_SUMMARY = "Find all markers by identifiers";
	public final static String FIND_ALL_MARKERS_SUCCESS_RESP = "[{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"cummins.alpine.com\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"Genset\"},\"dataprovider\":[{\"key\":\"assetType\",\"value\":\"asset_211\"},{\"key\":\"device\",\"value\":\"\"},{\"key\":\"engineModel\",\"value\":\"mosel_211\"},{\"key\":\"entityName\",\"value\":\"entity_211\"},{\"key\":\"esn\",\"value\":\"21121121\"},{\"key\":\"identifier\",\"value\":\"41146e07-cc8a-4351-8a8d-0935a7241ddb\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"41146e07-cc8a-4351-8a8d-0935a7241ddb\"}}]";

	public final static String GET_MARKER_COUNT_SUMMARY = "Get marker count";
	public final static String GET_MARKER_COUNT_SUCCESS_RESP = "{\"count\": 12}";

	public static final String GENERAL_FIELD_NOT_SPECIFIED = "{mandatory-field} not specified";
	public static final String GENERAL_FIELD_NOT_UNIQUE = "{field} is not unique";
	public static final String GENERAL_FIELD_INVALID = "{field} is invalid";
	public static final String GENERAL_DATA_NOT_AVAILABLE = "Requested data is not available";
	public static final String FIELD_ALREADY_EXIST = "{mandatory-field} already exists";

	public static final String MARKER_PAYLOAD = "Marker Payload";
	public static final String IDENTITY_PAYLOAD = "Marker Identity Payload";
	public static final String SEARCH_PAYLOAD = "Entity Search Payload";
	public static final String SEARCH_FIELDS_PAYLOAD = "Search Fields Payload";
}
