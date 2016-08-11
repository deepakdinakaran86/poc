package com.pcs.avocado.constans;

/**
 * @description Responsible for holding the constants related to Swagger Api
 *              documentation
 * @author Twinkle (PCSEG297)
 * @date January 2016
 * @since Avocado-1.0.0
 */
public interface ApiDocConstant {

	public static final String FACILITY_PAYLOAD = "Facility Payload";
	public static final String FACILITY_NAME = "Facility Name";
	public static final String TEMPLATE_NAME = "Template Name";
	public static final String DOMAIN_NAME = "Domain Name";
	
	public final static String CREATE_FACILITY_DESC = "This service is used to create a Facility, sample request : "
	        + "{\"domain\":{\"domainName\":\"pacificcontrols.alpine.com\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"AvocadoFacilityTemplate\"},\"fieldValues\":[{\"key\":\"facilityName\",\"value\":\"BuildNo101\"},{\"key\":\"emirate\",\"value\":\"Dubai\"},{\"key\":\"clientName\",\"value\":\"Pacificcontrols\"},{\"key\":\"buildingType\",\"value\":\"Residential\"},{\"key\":\"buildingStructure\",\"value\":\"structure\"},{\"key\":\"weatherStation\",\"value\":\"dubai\"},{\"key\":\"preEcmEndDate\",\"value\":1451851200000},{\"key\":\"savingsTarget\",\"value\":\"34\"},{\"key\":\"country\",\"value\":\"United Arab Emirates\"},{\"key\":\"city\",\"value\":\"Dubai\"},{\"key\":\"vertical\",\"value\":\"Banking And Finance\"},{\"key\":\"squareFeet\",\"value\":\"100\"},{\"key\":\"rfsDate\",\"value\":1451678400000},{\"key\":\"startDate\",\"value\":1452283200000},{\"key\":\"timeZone\",\"value\":\"Muscat\"},{\"key\":\"maxValue\",\"value\":\"35\"}]}";
	public final static String CREATE_FACILITY_SUMMARY = "Create a facility";
	public final static String CREATE_FACILITY_SUCCESS_RESP = "{\"domain\":{\"domainName\":\"pacificcontrols.alpine.com\"},\"entityTemplate\":{\"entityTemplateName\":\"AvocadoFacilityTemplate\"},\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"fb348e60-ae17-49fd-a8f7-22d3103f72e3\"}}";

	public final static String UPDATE_FACILITY_DESC = "This service is used to update a Facility by FacilityName and DomainName(if provided), sample request : "
	        + "{\"domain\":{\"domainName\":\"pacificcontrols.alpine.com\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"AvocadoFacilityTemplate\"},\"fieldValues\":[{\"key\":\"buildingStructure\",\"value\":\"structure\"},{\"key\":\"buildingType\",\"value\":\"Residential\"},{\"key\":\"city\",\"value\":\"Dubai\"},{\"key\":\"clientName\",\"value\":\"Pacificcontrols\"},{\"key\":\"country\",\"value\":\"United Arab Emirates\"},{\"key\":\"emirate\",\"value\":\"Dubai\"},{\"key\":\"maxValue\",\"value\":\"35\"},{\"key\":\"preEcmEndDate\",\"value\":\"1451851200000\"},{\"key\":\"rfsDate\",\"value\":\"1451678400000\"},{\"key\":\"savingsTarget\",\"value\":\"34\"},{\"key\":\"squareFeet\",\"value\":\"100\"},{\"key\":\"startDate\",\"value\":\"1452283200000\"},{\"key\":\"timeZone\",\"value\":\"Muscat\"},{\"key\":\"vertical\",\"value\":\"Banking And Finance\"},{\"key\":\"weatherStation\",\"value\":\"dubai\"},{\"key\":\"zone\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"fb348e60-ae17-49fd-a8f7-22d3103f72e3\"}}";
	public final static String UPDATE_FACILITY_SUMMARY = "Update a facility";
	public final static String UPDATE_FACILITY_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";
	
	public final static String GET_FACILITY_DESC = "This service is used to fetch a Facility by TemplateName, FacilityName and domainName(if provided)";
	public final static String GET_FACILITY_SUMMARY = "Fetch a facility";
	public final static String GET_FACILITY_SUCCESS_RESP = "{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pacificcontrols.alpine.com\"},\"entityStatus\":{\"statusKey\":0,\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"domain\":{\"domainName\":\"pacificcontrols.alpine.com\"},\"entityTemplateName\":\"AvocadoFacilityTemplate\",\"platformEntityType\":\"MARKER\"},\"dataprovider\":[{\"key\":\"buildingStructure\",\"value\":\"structure\"},{\"key\":\"buildingType\",\"value\":\"Residential\"},{\"key\":\"city\",\"value\":\"Dubai\"},{\"key\":\"clientName\",\"value\":\"Pacificcontrols\"},{\"key\":\"country\",\"value\":\"United Arab Emirates\"},{\"key\":\"emirate\",\"value\":\"Dubai\"},{\"key\":\"facilityName\",\"value\":\"BuildNo101\"},{\"key\":\"maxValue\",\"value\":\"35\"},{\"key\":\"preEcmEndDate\",\"value\":\"1451851200000\"},{\"key\":\"rfsDate\",\"value\":\"1451678400000\"},{\"key\":\"savingsTarget\",\"value\":\"34\"},{\"key\":\"squareFeet\",\"value\":\"100\"},{\"key\":\"startDate\",\"value\":\"1452283200000\"},{\"key\":\"timeZone\",\"value\":\"Muscat\"},{\"key\":\"vertical\",\"value\":\"Banking And Finance\"},{\"key\":\"weatherStation\",\"value\":\"dubai\"},{\"key\":\"zone\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"fb348e60-ae17-49fd-a8f7-22d3103f72e3\"}}";

	public final static String GET_ALL_FACILITY_DESC = "This service is used to fetch all the  Facilities from by TemplateName, and domainName(if provided)";
	public final static String GET_ALL_FACILITY_SUMMARY = "Fetch list of facilities";
	public final static String GET_ALL_FACILITY_SUCCESS_RESP = "[{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pacificcontrols.alpine.com\"},\"entityStatus\":{\"statusKey\":0,\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"domain\":{\"domainName\":\"pacificcontrols.alpine.com\"},\"entityTemplateName\":\"AvocadoFacilityTemplate\",\"platformEntityType\":\"MARKER\"},\"dataprovider\":[{\"key\":\"buildingStructure\",\"value\":\"structure\"},{\"key\":\"buildingType\",\"value\":\"Residential\"},{\"key\":\"city\",\"value\":\"Dubai\"},{\"key\":\"clientName\",\"value\":\"Pacificcontrols\"},{\"key\":\"country\",\"value\":\"United Arab Emirates\"},{\"key\":\"emirate\",\"value\":\"Dubai\"},{\"key\":\"facilityName\",\"value\":\"BuildNo97\"},{\"key\":\"maxValue\",\"value\":\"35\"},{\"key\":\"preEcmEndDate\",\"value\":\"1451851200000\"},{\"key\":\"rfsDate\",\"value\":\"1451678400000\"},{\"key\":\"savingsTarget\",\"value\":\"34\"},{\"key\":\"squareFeet\",\"value\":\"100\"},{\"key\":\"startDate\",\"value\":\"1452283200000\"},{\"key\":\"timeZone\",\"value\":\"Muscat\"},{\"key\":\"vertical\",\"value\":\"Banking And Finance\"},{\"key\":\"weatherStation\",\"value\":\"dubai\"},{\"key\":\"zone\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"b7f783bf-f313-42a8-861b-4771c1e48f24\"}}]";

	public static final String GENERAL_FIELD_NOT_SPECIFIED = "{mandatory-field} not specified";
	public static final String GENERAL_FIELD_INVALID = "{field} is invalid";
	public static final String GENERAL_DATA_NOT_AVAILABLE = "Requested data is not available";
	public static final String PERSISTENCE_ERROR = "Data could not be saved";
	public static final String GENERAL_FIELD_NOT_UNIQUE = "{field} is not unique";
	public static final String GENERAL_FIELD_ALREADY_EXISTING = "{field} already exists";
	
}
