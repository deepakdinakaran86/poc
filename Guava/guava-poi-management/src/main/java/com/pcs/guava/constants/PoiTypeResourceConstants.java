package com.pcs.guava.constants;

/**
 * @description Responsible for holding the constants related to Swagger Api POI
 *              Type
 * @author PCSEG323 (Greeshma)
 * @date April 2016
 * @since avocado-1.0.0
 */
public interface PoiTypeResourceConstants {

	public static final String INSERT_POI_TYPE_SUMMARY = "Save POI Type";
	public static final String INSERT_POI_TYPE_DESC = "This is the service to be used to save POI Type : "
			+ "{\"poiType\":\"PetrolPump\",\"status\":\"ACTIVE\",\"poiTypeValues\":[\"location\"]}";

	public static final String GET_POI_TYPE_SUMMARY = "Get POI Type";
	public static final String GET_POI_TYPE_DESC = "This is the service to be used to get POI Type : ";
	public static final String GET_POI_TYPE_SUCC_RESP = "{\"poiType\":\"School\",\"poiTypeValues\":[\"location\",\"type\"]}";

	public static final String GET_ALL_POI_TYPE_SUMMARY = "Get All POI Type";
	public static final String GET_ALL_POI_TYPE_DESC = "This is the service to be used to get all POI Type : ";
	public static final String GET_ALL_POI_TYPE_SUCC_RESP = "[{\"poiType\":\"School\"},{\"poiType\":\"MetroStation\"},{\"poiType\":\"Bin\"},{\"poiType\":\"Theatre\"},{\"poiType\":\"Hospital\"}]";

	public static final String INSERT_POI_SUMMARY = "Save POI";
	public static final String INSERT_POI_DESC = "This is the service to be used to save POI: "
			+ "{\"poiName\":\"Shrajsh\",\"poiType\":\"Bin\",\"description\":\"\",\"radius\":12,\"latitude\":\"25.2048493\",\"longitude\":\"55.270782\",\"status\":\"ACTIVE\","
			+ "\"poiTypeValues\":[{\"key\":\"location\",\"value\":\"Rashidiya\"},{\"key\":\"type\",\"value\":\"MedicalWaste\"}]}";
	
	public static final String CREATE_LIST_OF_POI_ENTITIES_SUMMARY = "Create List of POI Entities";
	public static final String CREATE_LIST_OF_POI_ENTITIES_DESC = "This is the service to be used to create a list of POI Entities: "
			+ "[{\"poiName\":\"Shrajsh\",\"poiType\":\"Bin\",\"description\":\"\",\"radius\":12,\"latitude\":\"25.2048493\",\"longitude\":\"55.270782\",\"status\":\"ACTIVE\","
			+ "\"poiTypeValues\":[{\"key\":\"location\",\"value\":\"Rashidiya\"},{\"key\":\"type\",\"value\":\"MedicalWaste\"}]},"
			+ "[{\"poiName\":\"Dubai\",\"poiType\":\"Bin\",\"description\":\"\",\"radius\":13,\"latitude\":\"25.2048493\",\"longitude\":\"55.270782\",\"status\":\"ACTIVE\","
			+ "\"poiTypeValues\":[{\"key\":\"location\",\"value\":\"DG\"},{\"key\":\"type\",\"value\":\"GeneralWaste\"}]}]";

	public static final String UPDATE_POI_SUMMARY = "Update POI";
	public static final String UPDATE_POI_DESC = "This is the service to be used to update POI: "
			+ "{\"poiName\":\"DFZASchools\",\"poiType\":\"School\",\"description\":\"Play School\",\"radius\":12,\"latitude\":\"25.2048493\",\"longitude\":\"55.270782\",\"status\":\"ACTIVE\","
			+ "\"poiTypeValues\":[{\"key\":\"location\",\"value\":\"Alnahda\"},{\"key\":\"type\",\"value\":\"UpperPrimary\"}],\"poiIdentifier\":{\"key\":\"key\",\"value\":\"53ad9407-7fb4-49bf-ab38-303498bdb316\"},"
			+ "\"poiTypeIdentifier\":{\"key\":\"key\",\"value\":\"312a0167-a175-44dc-9b0d-89fe25fd5eac\"}}";

	public static final String GET_ALL_POI_SUMMARY = "Get All POI";
	public static final String GET_ALL_POI_DESC = "This is the service to be used to get all POI / get all POI based on POI Type: ";
	public static final String GET_ALL_POI_SUCC_RESP = "[{\"domainName\":\"ibrakomclient.galaxy\",\"poiType\":\"Bin\",\"poiName\":\"Shrajsh\",\"poiIdentifier\":{\"key\":\"identifier\",\"value\":\"b633dc67-86a2-4191-b4f3-9aff751319e9\"},\"poiTypeIdentifier\":{\"key\":\"identifier\","
			+ "\"value\":\"4ca49ec9-f1bd-415d-a039-8c8867e2a20e\"},\"poiTypeValues\":[],\"latitude\":\"25.2048493\",\"longitude\":\"55.270782\",\"radius\":\"12\"}]";

	public static final String GET_POI_SUMMARY = "Get POI";
	public static final String GET_POI_DESC = "This is the service to be used to get POI : "
			+ "{\"domain\":{\"domainName\":\"ibrakomclient.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Poi\"},\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"53ad9407-7fb4-49bf-ab38-303498bdb316\"}}";
	public static final String GET_POI_SUCC_RESP = "{\"domainName\":\"ibrakomclient.galaxy\",\"poiType\":\"School\",\"poiName\":\"DFZASchools\",\"description\":\"Play School\",\"poiIdentifier\":{\"key\":\"identifier\",\"value\":\"53ad9407-7fb4-49bf-ab38-303498bdb316\"},\"poiTypeIdentifier\":{\"key\":\"identifier\",\"value\":\"312a0167-a175-44dc-9b0d-89fe25fd5eac\"},\"poiTypeValues\":[{\"key\":\"location\",\"value\":\"Alnahda\"},"
			+ "{\"key\":\"type\",\"value\":\"UpperPrimary\"}],\"latitude\":\"25.2048493\",\"longitude\":\"55.270782\",\"radius\":\"12\"}";

	public static final String GET_POI_BY_POI_TYPE_SUMMARY = "Get POI By Poi Types";
	public static final String GET_POI_BY_POI_TYPE_DESC = "This is the service to be used to get POI By Poi Types: "
			+ "[\"PetrolPump\",\"Bin\",\"School\"]";
	public static final String GET_POI_BY_POI_TYPE_SUCC_RESP = "[{\"domainName\":\"ibrakomclient.galaxy\",\"poiType\":\"Bin\",\"poiName\":\"Shrajsh\",\"poiIdentifier\":{\"key\":\"identifier\",\"value\":\"b633dc67-86a2-4191-b4f3-9aff751319e9\"},\"poiTypeIdentifier\":{\"key\":\"identifier\","
			+ "\"value\":\"4ca49ec9-f1bd-415d-a039-8c8867e2a20e\"},\"poiTypeValues\":[],\"latitude\":\"25.2048493\",\"longitude\":\"55.270782\",\"radius\":\"12\"}]";

	public static final String DELETE_POI_SUMMARY = "Delete POI";
	public static final String DELETE_POI_DESC = "This is the service to be used to Delete POI (softdelete): "
			+ "{\"domain\":{\"domainName\":\"ibrakomclient.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Poi\"},\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"53ad9407-7fb4-49bf-ab38-303498bdb316\"}}";

	public static final String DOMAIN = "Domain Name";
	public static final String DOMAIN_SAMPLE = "jll.galaxy";

	public static final String POI_TYPE = "POI Type";
	public static final String POI_TYPE_SAMPLE = "School";

	public final static String IDENTIFIER = "identifier";
	public final static String PLATFORM_MARKER_TEMPLATE = "platformMarkerTemplate";
	public final static String MARKER = "MARKER";
	public static final String CHECK = "check";
	public final static String POI_TYPE_TEMPLATE = "PoiType";
	public final static String POI_TYPE_TEMPLATE_NAME = "PoiTypeTemplate";

}
