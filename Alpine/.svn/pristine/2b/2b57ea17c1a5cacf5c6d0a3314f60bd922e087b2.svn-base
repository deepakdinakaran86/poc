package com.pcs.alpine.constants;

/**
 * @description Responsible for holding the constants related to Swagger Api
 *              documentation
 * @author Daniela (PCSEG191)
 * @date 10 Mar 2016
 * @since galaxy-1.0.0
 */
public interface ApiDocConstant {

	public final static String TAG_PAYLOAD = "Tag Payload";
	public final static String PAYLOAD = "Payload";
	public final static String TAG_CONFIGURATION_PAYLOAD = "Tag Configuration Payload";
	public final static String HIERARCHY_ENTITIES_PAYLOAD = "Payload To fetch entities";
	public final static String HIERARCHY_TEMPLATES_PAYLOAD = "Payload To fetch templates";
	public final static String MAP_TEMPLATES_TAG_PAYLOAD = "Map templates to tag payload";
	public final static String GET_TAGS_PAYLOAD = "Get Tags to entity payload";
	public final static String DOMAIN_NAME = "Domain Name";
	public final static String SAMPLE_DOMAIN_NAME = "ibn.galaxy";
	public final static String TAG_TYPE = "Tag Type";

	public final static String CREATE_TAG_DESC = "This is the service to be used to create tag entity, sample request"
	        + "{\"domain\":{\"domainName\":\"tekken.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"truckTest01Tag\"},\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"fieldValues\":[{\"key\":\"name\",\"value\":\"truck06\"}]}";
	public final static String CREATE_TAG_SUMMARY = "Create a new Tag";
	public final static String CREATE_TAG_RESP = "{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"tekken.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"truckTest01Tag\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"2eb42042-7705-4aa7-92cf-c524467e3b9a\"}}";

	public final static String GET_ALL_TAGS_DESC = "This is the service to be used to fetch all tag entities";
	public final static String GET_ALL_TAGS_SUMMARY = "fetch all tag entities";
	public final static String GET_ALL_TAGS_RESP = "[{\"name\":\"elec\",\"tagType\":\"pointTag\",\"domain\":{\"domainName\":\"tekken.galaxy\"}},{\"name\":\"mech\",\"tagType\":\"pointTag\",\"domain\":{\"domainName\":\"tekken.galaxy\"}}]";

	public final static String GET_TAG_DESC = "This is the service to be used to fetch a tag entity, sample payload : "
	        + "{\"name\":\"truck04\",\"tagType\":\"truckTest01Tag\",\"domain\":{\"domainName\":\"tekken.galaxy\"}}";
	public final static String GET_TAG_SUMMARY = "fetch a tag entity";
	public final static String GET_TAG_RESP = "{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"test01Tag\"},\"fieldValues\":[{\"key\":\"identifier\",\"value\":\"4f988879-9bfc-4178-9d8d-ccd3d0f927aa\"},{\"key\":\"name\",\"value\":\"mech\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"4f988879-9bfc-4178-9d8d-ccd3d0f927aa\"}}";

	public final static String MAP_SUBJECTS_TO_TAG_DESC = "This is the service to be used to map subjects to tag, sample request to map templates : "
	        + "{\"tag\":{\"name\":\"mech\",\"tagType\":\"test01Tag\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"}},\"templates\":[{\"platformEntityType\":\"MARKER\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplateName\":\"Asset\"},{\"platformEntityType\":\"MARKER\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplateName\":\"Point\"}]}"
	        + ", sample request to map entities : "
	        + "{\"tag\":{\"name\":\"mech\",\"tagType\":\"test01Tag\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"}},\"entities\":[{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Asset\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"043a6558-c2d5-452b-83be-d557e44c1811\"}}]}";
	public final static String MAP_SUBJECTS_TO_TAG_SUMMARY = "map subjects(entities or tags) to tag";

	public final static String MAP_TAGS_TO_ENTITY_DESC = "This is the service to be used to map tags to an entity, sample request : "
	        + "{\"tags\":[{\"name\":\"mech\",\"tagType\":\"test01Tag\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"}},{\"name\":\"demo\",\"tagType\":\"DEMOTAG\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"}}],\"entity\":{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Asset\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"58d8e777-cad6-4162-9f07-b3dc832cf024\"}}}";
	public final static String MAP_TAGS_TO_ENTITY_SUMMARY = "map tags to an entity";

	public final static String TAGGED_SUBJECT_ENTITIES_DESC = "This is the service to be used to fetch all entities tagged to a tag entity, sample request"
	        + "{\"name\":\"mech\",\"tagType\":\"test01Tag\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"}}";
	public final static String TAGGED_SUBJECT_ENTITIES_SUMMARY = "fetch all entities tagged to an Actor";
	public final static String TAGGED_SUBJECT_ENTITIES_RESP = "[{\"platformEntity\":{\"platformEntityType\":\"TENANT\"},\"domain\":{\"domainName\":\"demod.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"DefaultTenant\"},\"dataprovider\":[{\"key\":\"contactEmail\",\"value\":\"twinkle@pacificcontrols.net\"},{\"key\":\"domain\",\"value\":\"subdemod.galaxy\"},{\"key\":\"firstName\",\"value\":\"subdemod\"},{\"key\":\"lastName\",\"value\":\"subdemod\"},{\"key\":\"tenantId\",\"value\":\"subdemod\"},{\"key\":\"tenantName\",\"value\":\"subdemod\"}],\"identifier\":{\"key\":\"tenantId\",\"value\":\"subdemod\"},\"tree\":{\"key\":\"tenantName\",\"value\":\"subdemod\"}}]";

	public final static String TAGGED_SUBJECT_TEMPLATES_DESC = "This is the service to be used to fetch all templates tagged to a tag entity, sample request"
	        + "{\"name\":\"mech\",\"tagType\":\"test01Tag\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"}}";
	public final static String TAGGED_SUBJECT_TEMPLATES_SUMMARY = "fetch all templates tagged to an Actor";
	public final static String TAGGED_SUBJECT_TEMPLATES_RESP = "[{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplateName\":\"DefaultTenant\",\"platformEntityType\":\"TENANT\"}]";

	public final static String ALL_TAGS_DESC = "This is the service to be used to fetch all tags an entity or template is having, sample request to get all tags for an entity : "
	        + "{\"actorType\":\"Entity\",\"actor\":{\"entity\":{\"platformEntity\":{\"platformEntityType\":\"TENANT\"},\"domain\":{\"domainName\":\"demod.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"DefaultTenant\"},\"identifier\":{\"key\":\"tenantId\",\"value\":\"subdemod\"}}}}"
	        + " sample request to get all tags for a template : "
	        + "{\"actorType\":\"TEMPLATE\",\"actor\":{\"template\":{\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityTemplateName\":\"Device\",\"platformEntityType\":\"MARKER\"}}}";
	public final static String ALL_TAGS_SUMMARY = "fetch all tags an entity or template is having";
	public final static String ALL_TAGS_RESP = "[{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"test03Tag\"},\"dataprovider\":[{\"key\":\"name\",\"value\":\"mech\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"aff1920c-902d-4b9c-a213-0e0b5712b937\"}},{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"pcs.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"test02Tag\"},\"dataprovider\":[{\"key\":\"name\",\"value\":\"elec\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"a2956520-5b72-4952-ac48-ce49f4af6fbe\"}}]";

	public final static String CREATE_TAG_TYPE_DESC = "This is the service to be used to create tag type template, sample request"
	        + "{\"domainName\":\"srsinfotech.galaxy\",\"tagType\":\"test06Tag\",\"tagTypeValues\":[\"name\"],\"status\":\"ACTIVE\"}";
	public final static String CREATE_TAG_TYPE_SUMMARY = "Create a new Tag type template";

	public final static String GET_TAG_TYPES_DESC = "This is the service to be used to fetch details of tag type";
	public final static String GET_TAG_TYPES_SUMMARY = "fetch details of tag type";
	public final static String GET_TAG_TYPES_RESP = "{\"tagType\":\"pointTag\",\"tagTypeValues\":[\"name\"]}";

	public final static String GET_ALL_TAG_TYPES_DESC = "This is the service to be used to fetch all tag type names";
	public final static String GET_ALL_TAG_TYPES_SUMMARY = "fetch all tag type names";
	public final static String GET_ALL_TAG_TYPES_RESP = "[{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplateName\":\"POINT\",\"platformEntityType\":\"MARKER\",\"status\":{\"statusName\":\"ACTIVE\"},\"parentTemplate\":\"Tag\"},{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplateName\":\"BOOL\",\"platformEntityType\":\"MARKER\",\"status\":{\"statusName\":\"ACTIVE\"},\"parentTemplate\":\"Tag\"}]";

	public final static String TAG_VALIDATE_DESC = "This is the service to be used to validate ESB payload for attach subjects service, sample request to validate entities subjects : "
	        + "{\"entities\":[{\"identifier\":{\"value\":\"teslademodemo\",\"key\":\"userName\"},\"entityTemplate\":{\"entityTemplateName\":\"DefaultUser\"},\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"platformEntity\":{\"platformEntityType\":\"USER\"}},{\"identifier\":{\"value\":\"testa\",\"key\":\"userName\"},\"entityTemplate\":{\"entityTemplateName\":\"DefaultUser\"},\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"platformEntity\":{\"platformEntityType\":\"USER\"}}]}"
	        + "  sample request to validate templates : "
	        + "{\"templates\":[{\"entityTemplateName\":\"DefaultUser\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"platformEntityType\":\"USER\"},{\"entityTemplateName\":\"Asset\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"platformEntityType\":\"MARKER\"}]}";
	public final static String TAG_VALIDATE_SUMMARY = "validates ESB payload for attach subjects service";
	public final static String TAG_VALIDATE_RESP = "{\"status\": \"SUCCESS\"}";

	public final static String TAGS_BY_RANGE_DESC = "This is the service to be used to get tag entities by nodes range, sample requests"
	        + "1. when startNodeType:TEMPLATE and endNodeType:TEMPLATE : "
	        + "{\"startNodeType\":\"TEMPLATE\",\"endNodeType\":\"TEMPLATE\",\"startNode\":{\"template\":{\"platformEntityType\":\"MARKER\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplateName\":\"Asset\"}},\"endNode\":{\"template\":{\"platformEntityType\":\"MARKER\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplateName\":\"Point\"}},\"tagTypes\":[{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"tagType\":\"test09Tag\"},{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"tagType\":\"test08Tag\"}]}"
	        + " 2. when startNodeType:ENTITY and endNodeType:ENTITY : "
	        + "{\"startNodeType\":\"ENTITY\",\"endNodeType\":\"ENTITY\",\"startNode\":{\"entity\":{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Asset\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"2ce054e3-e2f3-4daa-9fd2-78fc0489f03e\"}}},\"endNode\":{\"entity\":{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Point\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"e48bc246-deb4-4b57-ad8b-cfcf46def759\"}}},\"tagTypes\":[{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"tagType\":\"test09Tag\"},{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"tagType\":\"test08Tag\"}]}"
	        + " 3. when startNodeType:TEMPLATE and endNodeType:ENTITY : "
	        + "{\"startNodeType\":\"TEMPLATE\",\"endNodeType\":\"ENTITY\",\"startNode\":{\"template\":{\"platformEntityType\":\"MARKER\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplateName\":\"Asset\"}},\"endNode\":{\"entity\":{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Point\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"e48bc246-deb4-4b57-ad8b-cfcf46def759\"}}},\"tagTypes\":[{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"tagType\":\"test09Tag\"},{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"tagType\":\"test08Tag\"}]}"
	        + " 4. when startNodeType:ENTITY and endNodeType:TEMPLATE : "
	        + "{\"startNodeType\":\"ENTITY\",\"endNodeType\":\"TEMPLATE\",\"startNode\":{\"entity\":{\"platformEntity\":{\"platformEntityType\":\"MARKER\"},\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplate\":{\"entityTemplateName\":\"Asset\"},\"identifier\":{\"key\":\"identifier\",\"value\":\"2ce054e3-e2f3-4daa-9fd2-78fc0489f03e\"}}},\"endNode\":{\"template\":{\"platformEntityType\":\"MARKER\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityTemplateName\":\"Point\"}},\"tagTypes\":[{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"tagType\":\"test09Tag\"},{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"tagType\":\"test08Tag\"}]}";
	public final static String TAGS_BY_RANGE_SUMMARY = "get tag entities by nodes range";
	public final static String TAGS_BY_RANGE_RESP = "[{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"test09Tag\"},\"dataprovider\":[{\"key\":\"name\",\"value\":\"mech\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"d6ee783c-8ffc-490d-9bbb-a9dce9167a6c\"}}]";

	public final static String SUBJECT__FILTERED_ENTITIES_DESC = "This is the service to be get entities by multiple tags, these entities can be filtered by template types, sample requests"
	        + " 1. When match : ANY is specified : "
	        + "{\"tags\":[{\"name\":\"mech\",\"tagType\":\"test01Tag\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"}},{\"name\":\"demo\",\"tagType\":\"DEMOTAG\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"}}],\"filter\":{\"types\":[\"Asset\",\"Point\"]},\"match\":\"ANY\"}"
	        + " 2. When match : ALL is specified : "
	        + "{\"tags\":[{\"name\":\"mech\",\"tagType\":\"test01Tag\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"}},{\"name\":\"demo\",\"tagType\":\"DEMOTAG\",\"domain\":{\"domainName\":\"srsinfotech.galaxy\"}}],\"filter\":{\"types\":[\"Asset\",\"Point\"]},\"match\":\"ALL\"}";
	public final static String SUBJECT__FILTERED_ENTITIES_SUMMARY = "get entities by multiple tags";
	public final static String SUBJECT__FILTERED_ENTITIES_RESP = "{\"Asset\":[{\"domain\":{\"domainName\":\"srsinfotech.galaxy\"},\"entityStatus\":{\"statusName\":\"ACTIVE\"},\"entityTemplate\":{\"entityTemplateName\":\"Asset\"},\"dataprovider\":[{\"key\":\"assetId\",\"value\":\"demoasset\"},{\"key\":\"assetName\",\"value\":\"demoasset\"},{\"key\":\"assetType\",\"value\":\"PUMP\"},{\"key\":\"description\"},{\"key\":\"identifier\",\"value\":\"44625b6d-19f8-444a-b506-88ad88696a5f\"},{\"key\":\"serialNumber\"}],\"identifier\":{\"key\":\"identifier\",\"value\":\"44625b6d-19f8-444a-b506-88ad88696a5f\"},\"tree\":{\"key\":\"assetId\",\"value\":\"demoasset\"}}],\"Point\":[]}";

	public final static String GET_TAGGED_ENTITIES_COUNT_DESC = "This service is used to get tagged entities count: "
	        + "{\"tagType\":\"FACILITY_TYPE\",\"domainName\":\"cisco.galaxy\",\"endEntityTemplate\":\"METER\",\"intermediateTemplates\":[\"Asset\",\"Device\"]}";
	public final static String GET_TAGGED_ENTITIES_COUNT_SUMMARY = "Get tagged entities count ";
	public final static String GET_TAGGED_ENTITIES_COUNT_SUCCESS_RESP = "[{\"tag\":\"Facility1\",\"count\":2},{\"tag\":\"Facility2\",\"count\":10}]";

	public static final String GENERAL_FIELD_NOT_SPECIFIED = "{mandatory-field} not specified";
	public static final String GENERAL_FIELD_INVALID = "{field} is invalid";
	public static final String GENERAL_DATA_NOT_AVAILABLE = "Requested data is not available";
	public static final String PERSISTENCE_ERROR = "Data could not be saved";
	public static final String GENERAL_FIELD_NOT_UNIQUE = "{field} is not unique";
	public static final String GENERAL_SUCCESS_RESP = "{\"status\": \"SUCCESS\"}";
	public static final String GENERAL_FAILURE_RESP = "{\"status\": \"FAILURE\"}";

}
