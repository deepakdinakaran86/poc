package com.pcs.alpine.constants;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author Riyas (PCSEG296)
 * @date 12 June 2016
 * @since alpine-1.0.0
 */
public interface ApiDocConstant {

	public static final String GENERAL_FIELD_NOT_SPECIFIED = "{mandatory-field} not specified";
	public static final String GENERAL_FIELD_INVALID = "{field} is invalid";
	public static final String GENERAL_DATA_NOT_AVAILABLE = "Requested data is not available";
	public static final String PERSISTENCE_ERROR = "Data could not be saved";
	public static final String GENERAL_FIELD_NOT_UNIQUE = "{field} is not unique";

	public static final String DOMAIN = "Domain Name";
	public static final String DOMAIN_SAMPLE = "pcs.galaxy";
	public static final String DOCUMENT_TYPE = "Document Name";
	public static final String DOCUMENT_TYPE_SAMPLE = "LICENSE";
	public final static String DOCUMENT_PAYLOAD = "Document Payload";

	public final static String CREATE_DOCUMENT_DESC = "This service is used to create a Document, sample request : "
	        + "{\"document\":{\"fieldValues\":[{\"key\":\"ExpiryDate\",\"value\":\"54875522222\"},{\"key\":\"documentName\",\"value\":\"firstDocument7\"},{\"key\":\"documentType\",\"value\":\"LICENSE\"},{\"key\":\"isExpired\",\"value\":\"true\"},{\"key\":\"notification\",\"value\":\"true\"},{\"key\":\"notifyRecFreq\",\"value\":\"20\"},{\"key\":\"notifyStart\",\"value\":\"54875522222\"},{\"key\":\"notifyType\",\"value\":\"Once\"}],\"entityTemplate\":{\"entityTemplateName\":\"Document\"}},\"documentType\":{\"fieldValues\":[{\"key\":\"country\",\"value\":\"UAE\"},{\"key\":\"expiryDate\",\"value\":\"44444444444444\"},{\"key\":\"issueDate\",\"value\":\"55555555555\"},{\"key\":\"licenseNo\",\"value\":\"12345645\"},{\"key\":\"vehicleType\",\"value\":\"AUTOMATIC\"}],\"entityTemplate\":{\"entityTemplateName\":\"LICENSE\"}}}";
	public final static String CREATE_DOCUMENT_SUMMARY = "Create a Document";
	public final static String CREATE_DOCUMENT_SUCCESS_RESP = "{\"document\":{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Document\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"82f38bbb-c3e4-490c-8b03-b010c2d7a1ae\"}},\"documentType\":{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"LICENSE\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"38ebd127-715b-4c95-a8b2-3ee67d849e91\"}}}";

	public final static String FIND_DOCUMENT_DESC = "This service to be used to Find a Document of a specified Document Type, sample request : "
	        + "{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Document\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"7da8766f-3db5-4c2d-815b-cf411997bb85\"}}";
	public final static String FIND_DOCUMENT_SUMMARY = "Find Document Details";
	public final static String FIND_DOCUMENT_SUCCESS_RESP = "{\"document\":{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"Document\"},\"fieldValues\":[{\"key\":\"ExpiryDate\",\"value\":\"5487552222211\"},{\"key\":\"documentName\",\"value\":\"firstDocument6\"},{\"key\":\"documentType\",\"value\":\"LICENSE\"},{\"key\":\"isExpired\",\"value\":\"true\"},{\"key\":\"notification\",\"value\":\"true\"},{\"key\":\"notifyRecFreq\",\"value\":\"22\"},{\"key\":\"notifyStart\",\"value\":\"54875522222\"},{\"key\":\"notifyType\",\"value\":\"Once\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"7da8766f-3db5-4c2d-815b-cf411997bb85\"},\"tree\":{\"key\":\"documentName\",\"value\":\"firstDocument6\"}},\"documentType\":{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"LICENSE\"},\"fieldValues\":[{\"key\":\"country\",\"value\":\"UAE\"},{\"key\":\"expiryDate\",\"value\":\"44444444444411\"},{\"key\":\"identifier\",\"value\":\"ca256620-84a8-479d-a087-e6b30f9e3ecb\"},{\"key\":\"issueDate\",\"value\":\"55555555511\"},{\"key\":\"licenseNo\",\"value\":\"12345645\"},{\"key\":\"vehicleType\",\"value\":\"AUTOMATIC\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"ca256620-84a8-479d-a087-e6b30f9e3ecb\"}}}";

	public final static String FIND_ALL_DOCUMENT_DESC = "This service is used to get a List of Documents of a tenant by domain and template name, both fields are not mandatory, if domain is not passed the logged in user's domain will be used, if template name is not passed all templates will be returned :";
	public final static String FIND_ALL_DOCUMENT_SUMMARY = "List All Documents";
	public final static String FIND_ALL_DOCUMENT_SUCCESS_RESP = "[{\"document\":{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityStatus\":{\"statusKey\":0,\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityTemplateName\":\"Document\",\"platformEntityType\":\"MARKER\"},\"dataprovider\":[{\"key\":\"ExpiryDate\"},{\"key\":\"documentName\",\"value\":\"firstDocument1\"},{\"key\":\"documentType\"},{\"key\":\"isExpired\"},{\"key\":\"notification\"},{\"key\":\"notifyRecFreq\"},{\"key\":\"notifyStart\"},{\"key\":\"notifyType\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"11dc60d2-7719-433a-a731-2ac0db22194e\"},\"tree\":{\"key\":\"documentName\",\"value\":\"firstDocument1\"}}}]";

	public final static String UPDATE_DOCUMENT_DESC = "This service is used to update an existing document, sample request : "
	        + "{\"document\":{\"fieldValues\":[{\"key\":\"ExpiryDate\",\"value\":\"5487552222211\"},{\"key\":\"documentName\",\"value\":\"firstDocument6\"},{\"key\":\"documentType\",\"value\":\"LICENSE\"},{\"key\":\"isExpired\",\"value\":\"true\"},{\"key\":\"notification\",\"value\":\"true\"},{\"key\":\"notifyRecFreq\",\"value\":\"22\"},{\"key\":\"notifyStart\",\"value\":\"54875522222\"},{\"key\":\"notifyType\",\"value\":\"Once\"}],\"entityTemplate\":{\"entityTemplateName\":\"Document\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"7da8766f-3db5-4c2d-815b-cf411997bb85\"}},\"documentType\":{\"fieldValues\":[{\"key\":\"country\",\"value\":\"UAE\"},{\"key\":\"expiryDate\",\"value\":\"44444444444411\"},{\"key\":\"issueDate\",\"value\":\"55555555511\"},{\"key\":\"licenseNo\",\"value\":\"12345645\"},{\"key\":\"vehicleType\",\"value\":\"AUTOMATIC\"}],\"entityTemplate\":{\"entityTemplateName\":\"LICENSE\"}}}";
	public final static String UPDATE_DOCUMENT_SUMMARY = "Update Document";
	public final static String UPDATE_DOCUMENT_SUCCESS_RESP = "{\"status\":\"SUCCESS\"}";

}
