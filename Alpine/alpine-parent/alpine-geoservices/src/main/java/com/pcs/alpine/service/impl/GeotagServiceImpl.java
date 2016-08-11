/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.alpine.service.impl;

import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.constants.GeotagConstant.GEOTAG_TEMPLATE;
import static com.pcs.alpine.enums.GeoDataFields.ENTITY;
import static com.pcs.alpine.enums.GeoDataFields.GEOTAG;
import static com.pcs.alpine.enums.GeoDataFields.IDENTIFIER_KEY;
import static com.pcs.alpine.enums.GeoDataFields.IDENTIFIER_VALUE;
import static com.pcs.alpine.enums.GeoDataFields.LATITUDE;
import static com.pcs.alpine.enums.GeoDataFields.LONGITUDE;
import static com.pcs.alpine.enums.GeoDataFields.NAME;
import static com.pcs.alpine.enums.GeoDataFields.TAG;
import static com.pcs.alpine.enums.GeoDataFields.TAG_TYPE;
import static com.pcs.alpine.enums.GeoErrorCodes.GEO_TAG_EXISTS;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.MARKER;
import static com.pcs.alpine.services.enums.EMDataFields.TENANT;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.Status.ACTIVE;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.util.IStructureModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.dto.StatusMessageErrorDTO;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.dto.CoordinatesDTO;
import com.pcs.alpine.dto.GeoTagSearchDTO;
import com.pcs.alpine.dto.GeoTaggedEntitiesDTO;
import com.pcs.alpine.dto.GeoTaggedEntityDTO;
import com.pcs.alpine.dto.GeotagDTO;
import com.pcs.alpine.dto.GeotagESBDTO;
import com.pcs.alpine.dto.TagInfoESBDTO;
import com.pcs.alpine.dto.TagsESBDTO;
import com.pcs.alpine.enums.GeoDataFields;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.repo.utils.CoreEntityUtil;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.service.GeotagService;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.HierarchySearchDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.StatusDTO;

/**
 * @description This class is responsible for the GeotagServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @author Daniela (PCSEG191)
 * @date 20 Apr 2016
 */
@Service
public class GeotagServiceImpl implements GeotagService {

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformClient;

	@Value("${platform.esb.geotag}")
	private String geotagPlatformEndpoint;

	@Value("${platform.esb.geotag.list}")
	private String listGeoTagsEndpoint;

	@Value("${platform.esb.hierarchy.immediate.children}")
	private String immediateChildrenEndpoint;

	@Value("${platform.esb.hierarchy.attached.templates}")
	private String attachedTemplatesOfGeotagsEndpoint;
	
	@Value("${platform.esb.geotag.tag}")
	private String geotagaTagPlatformEndpoint;

	@Autowired
	SubscriptionProfileService subscriptionProfileService;

	@Autowired
	CoreEntityUtil coreEntityUtil;

	@Autowired
	CoreEntityService coreEntityService;

	@Override
	public StatusMessageDTO createGeotag(GeotagDTO geotag) {
		
		Boolean typeOfGeotag = false;
		String entityTemplateName = null;
		StatusMessageErrorDTO status = null;
		
		// validate required fields
		ValidationUtils.validateMandatoryFields(geotag);
		
		//Check if any of the required entity in input payload is specified, also throw error if both are specified
		if(geotag.getEntity()==null && geotag.getTag()==null){
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED, ENTITY.getDescription()+"/"+TAG.getDescription());
		} else if(geotag.getEntity()!=null && geotag.getTag()!=null){
			throw new GalaxyException(
					GalaxyCommonErrorCodes.CUSTOM_ERROR, "Specify any one: "+ENTITY.getDescription()+"/"+TAG.getDescription());
		}
		if(geotag.getEntity() != null) {
			validateEntityGeotagDTO(geotag);	
			entityTemplateName = geotag.getEntity().getEntityTemplate()
					.getEntityTemplateName();
		}
		else {
			validateTagGeotagDTO(geotag);
			entityTemplateName = geotag.getTag().getTagType();
			typeOfGeotag = true;
		}
		
		// Check the entity if the template name is GEOTAG
		if (entityTemplateName.equalsIgnoreCase(GEOTAG.getDescription())) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
					GeoDataFields.ENTITY_TEMPLATE_NAME.getDescription());
		}
		
		//To Geotag a Tag service call to ESB
		if(typeOfGeotag){
			status = platformClient.post(
					geotagaTagPlatformEndpoint,
					subscriptionProfileService.getJwtToken(),
					constructTagGeotagESBPayload(geotag), StatusMessageErrorDTO.class);
			if (isNotBlank(status.getErrorCode())
					&& status.getErrorCode().equalsIgnoreCase(
							GEO_TAG_EXISTS.getCode().toString())) {
				throw new GalaxyException(GEO_TAG_EXISTS, entityTemplateName);
			}
		}
		//To Geotag an Entity service call to ESB 
		else {
			status = platformClient.post(
					geotagPlatformEndpoint,
					subscriptionProfileService.getJwtToken(),
					constructEntityGeotagESBPayload(geotag), StatusMessageErrorDTO.class);
			if (isNotBlank(status.getErrorCode())
					&& status.getErrorCode().equalsIgnoreCase(
							GEO_TAG_EXISTS.getCode().toString())) {
				throw new GalaxyException(GEO_TAG_EXISTS, entityTemplateName);
			}
		}

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(status.getStatus());
		return statusMessageDTO;
	}

	private GeotagESBDTO constructTagGeotagESBPayload(GeotagDTO geotag) {
		GeotagESBDTO geotagESBDTO = new GeotagESBDTO();
		EntityDTO geotagEntity = new EntityDTO();
		geotagEntity.setDomain(geotag.getTag().getDomain());
		
		EntityTemplateDTO geotagTemplate = new EntityTemplateDTO();
		geotagTemplate.setEntityTemplateName(GEOTAG_TEMPLATE);
		geotagEntity.setEntityTemplate(geotagTemplate);

		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType(MARKER.getFieldName());
		geotagEntity.setPlatformEntity(platformEntity);

		List<FieldMapDTO> coordinates = setCoordinatesFieldMapDTO(geotag);
		geotagEntity.setFieldValues(coordinates);

		// Geotag always has Status as Active
		StatusDTO activeStatus = new StatusDTO();
		activeStatus.setStatusName(Status.ACTIVE.name());
		geotagEntity.setEntityStatus(activeStatus);

		geotagESBDTO.setGeotagEntity(geotagEntity);
		
		geotagESBDTO.setTagIdentity(geotag.getTag());

		return geotagESBDTO;
	}
	
	private GeotagESBDTO constructEntityGeotagESBPayload(GeotagDTO geotag) {
		GeotagESBDTO geotagESBDTO = new GeotagESBDTO();
		EntityDTO geotagEntity = new EntityDTO();
		geotagEntity.setDomain(geotag.getEntity().getDomain());
		EntityTemplateDTO geotagTemplate = new EntityTemplateDTO();
		geotagTemplate.setEntityTemplateName(GEOTAG_TEMPLATE);
		geotagEntity.setEntityTemplate(geotagTemplate);

		// Set GlobalEntityDTO of the Entity using domain name and template name
		// of Entity
		PlatformEntityDTO globalEntityDTO = coreEntityUtil.getGlobalEntityType(
				geotag.getEntity().getEntityTemplate().getEntityTemplateName(),
				geotag.getEntity().getDomain().getDomainName());
		geotag.getEntity().setPlatformEntity(globalEntityDTO);

		// For tenant fetch the parents domain
		if (globalEntityDTO.getPlatformEntityType().equalsIgnoreCase(
				TENANT.getFieldName())) {
			IdentityDTO identitydto = geotag.getEntity();
			identitydto.setPlatformEntity(globalEntityDTO);

			EntityDTO domainEntity = coreEntityService.getEntity(identitydto);

			List<FieldMapDTO> fields = domainEntity.getDataprovider();

			String domain = null;
			FieldMapDTO mydomainMap = new FieldMapDTO();
			mydomainMap.setKey(DOMAIN.getFieldName());
			domain = fetchFieldValue(fields, mydomainMap);

			DomainDTO domainDTO = new DomainDTO();
			domainDTO.setDomainName(domain);
			geotagEntity.setDomain(domainDTO);
		}

		PlatformEntityDTO markerType = new PlatformEntityDTO();
		markerType.setPlatformEntityType(MARKER.getFieldName());
		geotagEntity.setPlatformEntity(markerType);

		List<FieldMapDTO> coordinates = setCoordinatesFieldMapDTO(geotag);
		geotagEntity.setFieldValues(coordinates);

		// Geotag always has Status as Active
		StatusDTO activeStatus = new StatusDTO();
		activeStatus.setStatusName(Status.ACTIVE.name());
		geotagEntity.setEntityStatus(activeStatus);

		geotagESBDTO.setGeotagEntity(geotagEntity);

		// Check if entity type is TENANT
		geotagESBDTO.setParentIdentity(geotag.getEntity());

		// Set the global entity type
		geotagESBDTO.getParentIdentity().setPlatformEntity(globalEntityDTO);

		return geotagESBDTO;
	}

	private List<FieldMapDTO> setCoordinatesFieldMapDTO(GeotagDTO geotag) {
		List<FieldMapDTO> coordinates = new ArrayList<FieldMapDTO>();
		FieldMapDTO latitudeMap = new FieldMapDTO();
		latitudeMap.setKey(GeoDataFields.LATITUDE.getFieldName());
		latitudeMap.setValue(geotag.getGeotag().getLatitude());
		coordinates.add(latitudeMap);
		FieldMapDTO longitudeMap = new FieldMapDTO();
		longitudeMap.setKey(GeoDataFields.LONGITUDE.getFieldName());
		longitudeMap.setValue(geotag.getGeotag().getLongitude());
		coordinates.add(longitudeMap);
		return coordinates;
	}

	private void validateEntityGeotagDTO(GeotagDTO geotag) {
		
		ValidationUtils.validateMandatoryFields(geotag, ENTITY, GEOTAG);
		ValidationUtils.validateMandatoryFields(geotag.getEntity()
				.getIdentifier(), IDENTIFIER_KEY, IDENTIFIER_VALUE);
		ValidationUtils.validateMandatoryFields(geotag.getGeotag(), LATITUDE,
				LONGITUDE);
		ValidationUtils.validateMandatoryFields(geotag.getEntity(),
				ENTITY_TEMPLATE);

		// If domain is null or Blank, get logged in Users Domain
		if (geotag.getEntity().getDomain() == null
				|| isBlank(geotag.getEntity().getDomain().getDomainName())) {
			DomainDTO domainDTO = new DomainDTO();
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
					.getSubscription().getEndUserDomain());
			geotag.getEntity().setDomain(domainDTO);
		}
		
		

	}
	
	private void validateTagGeotagDTO(GeotagDTO geotag) {
		ValidationUtils.validateMandatoryFields(geotag, TAG, GEOTAG);
		ValidationUtils.validateMandatoryFields(geotag.getTag()
				, NAME, TAG_TYPE);
		ValidationUtils.validateMandatoryFields(geotag.getGeotag(), LATITUDE,
				LONGITUDE);

		// If domain is null or Blank, get logged in Users Domain
		if (geotag.getTag().getDomain() == null
				|| isBlank(geotag.getTag().getDomain().getDomainName())) {
			DomainDTO domainDTO = new DomainDTO();
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
					.getSubscription().getEndUserDomain());
			geotag.getTag().setDomain(domainDTO);
		}

	}

	@Override
	public StatusMessageDTO updateGeotag(GeotagDTO geotag) {
		// validate required fields
		validateEntityGeotagDTO(geotag);

		StatusMessageDTO status = platformClient.put(geotagPlatformEndpoint,
				subscriptionProfileService.getJwtToken(),
				constructEntityGeotagESBPayload(geotag), StatusMessageDTO.class);

		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GeotagDTO findGeotag(IdentityDTO geotaggedEntity) {
		// validate template name
		validateMandatoryFields(geotaggedEntity, ENTITY_TEMPLATE);
		validateMandatoryFields(geotaggedEntity.getEntityTemplate(),
				ENTITY_TEMPLATE_NAME);

		String domainName = null;
		if (geotaggedEntity.getDomain() != null
				&& isNotBlank(geotaggedEntity.getDomain().getDomainName())) {
			domainName = geotaggedEntity.getDomain().getDomainName();
		}
		// Fetch entity type for the geotaggedEntity
		PlatformEntityDTO taggedEntityType = new PlatformEntityDTO();
		taggedEntityType.setPlatformEntityType(coreEntityUtil
				.getGlobalEntityType(
						geotaggedEntity.getEntityTemplate()
								.getEntityTemplateName(), domainName)
				.getPlatformEntityType());
		geotaggedEntity.setPlatformEntity(taggedEntityType);

		// Construct payload for fetching geotag
		HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();
		hierarchySearchDTO.setActor(geotaggedEntity);
		hierarchySearchDTO.setSearchEntityType(MARKER.getFieldName());
		hierarchySearchDTO.setSearchTemplateName(GEOTAG_TEMPLATE);
		hierarchySearchDTO.setStatusName(ACTIVE.name());

		List<EntityDTO> geoTags = platformClient.post(
				immediateChildrenEndpoint,
				subscriptionProfileService.getJwtToken(), hierarchySearchDTO,
				List.class, EntityDTO.class);

		// Construct response payload
		GeotagDTO geotagDTO = new GeotagDTO();
		CoordinatesDTO geoTag = getGeoTag(geoTags.get(0).getDataprovider());
		geotagDTO.setGeotag(geoTag);

		return geotagDTO;
	}

	@Override
	public List<GeoTaggedEntitiesDTO> findAllGeotags(String domainName,
			String entityTemplateName) {
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}
		// Set search template
		com.pcs.alpine.commons.dto.HierarchySearchDTO hierarchySearchDTO = new com.pcs.alpine.commons.dto.HierarchySearchDTO();
		hierarchySearchDTO.setDomain(domainName);
		hierarchySearchDTO.setSearchTemplateName(entityTemplateName);

		List<GeoTaggedEntitiesDTO> geoTaggedEntities = platformClient.post(
				listGeoTagsEndpoint, subscriptionProfileService.getJwtToken(),
				hierarchySearchDTO, List.class, GeoTaggedEntitiesDTO.class);

		// Construct response, with entities grouped by template name
		return geoTaggedEntities;
	}

	@Override
	public StatusMessageDTO deleteGeotag(IdentityDTO geotag) {
		return null;
	}

	private List<GeoTaggedEntitiesDTO> constructFindAllGeotagsResp(
			TagsESBDTO tagsESBDTO) {

		HashMap<String, List<GeoTaggedEntityDTO>> tagMap = new HashMap<String, List<GeoTaggedEntityDTO>>();

		for (TagInfoESBDTO geoTagInfo : tagsESBDTO.getTagInfo()) {

			// Set the fields for response
			GeoTaggedEntityDTO geoTaggedEntityDTO = new GeoTaggedEntityDTO();
			geoTaggedEntityDTO.setIdentifier(geoTagInfo.getEntity()
					.getIdentifier());
			geoTaggedEntityDTO.setDataprovider(geoTagInfo.getEntity()
					.getDataprovider());
			geoTaggedEntityDTO.setDomain(geoTagInfo.getEntity().getDomain());

			CoordinatesDTO geoTag = getGeoTag(geoTagInfo.getGeoTag()
					.getDataprovider());
			geoTaggedEntityDTO.setGeoTag(geoTag);

			if (tagMap.containsKey(geoTagInfo.getEntity().getEntityTemplate()
					.getEntityTemplateName())) {
				List<GeoTaggedEntityDTO> exitingTaggedEntities = tagMap
						.get(geoTagInfo.getEntity().getEntityTemplate()
								.getEntityTemplateName());
				exitingTaggedEntities.add(geoTaggedEntityDTO);

			} else {
				List<GeoTaggedEntityDTO> taggedEntities = new ArrayList<GeoTaggedEntityDTO>();
				taggedEntities.add(geoTaggedEntityDTO);
				tagMap.put(geoTagInfo.getEntity().getEntityTemplate()
						.getEntityTemplateName(), taggedEntities);
			}
		}
		return getTaggedEntities(tagMap);

	}

	/**
	 * @param geoTagInfo
	 * @return
	 */
	private CoordinatesDTO getGeoTag(List<FieldMapDTO> fields) {
		// Set the co-ordinates
		CoordinatesDTO geoTag = new CoordinatesDTO();
		FieldMapDTO latitudeMap = new FieldMapDTO();
		latitudeMap.setKey(LATITUDE.getFieldName());
		geoTag.setLatitude(fetchFieldValue(fields, latitudeMap));

		FieldMapDTO longitudeMap = new FieldMapDTO();
		longitudeMap.setKey(LONGITUDE.getFieldName());
		geoTag.setLongitude(fetchFieldValue(fields, longitudeMap));
		return geoTag;
	}

	private String fetchFieldValue(List<FieldMapDTO> fieldMapDTOs,
			FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		if (isBlank(field.getValue())) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					field.getKey());
		}
		return field.getValue();
	}

	private List<GeoTaggedEntitiesDTO> getTaggedEntities(
			HashMap<String, List<GeoTaggedEntityDTO>> tagMap) {
		List<GeoTaggedEntitiesDTO> taggedEntities = new ArrayList<GeoTaggedEntitiesDTO>();
		for (Map.Entry<String, List<GeoTaggedEntityDTO>> entry : tagMap
				.entrySet()) {
			GeoTaggedEntitiesDTO geoTaggedEntitiesDTO = new GeoTaggedEntitiesDTO();
			geoTaggedEntitiesDTO.setEntityTemplateName(entry.getKey());
			geoTaggedEntitiesDTO.setEntities(entry.getValue());
			taggedEntities.add(geoTaggedEntitiesDTO);
		}
		return taggedEntities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAttachedGeotaggedTemplates(String domainName) {

		com.pcs.alpine.commons.dto.HierarchySearchDTO hierarchySearchDTO = new com.pcs.alpine.commons.dto.HierarchySearchDTO();
		if (isBlank(domainName)) {
			// Get logged in user's domain
			hierarchySearchDTO.setDomain(subscriptionProfileService
					.getSubscription().getEndUserDomain());
		} else {
			hierarchySearchDTO.setDomain(domainName);
		}

		hierarchySearchDTO.setSearchTemplateName(GEOTAG_TEMPLATE);

		List<String> geotaggedTemplates = null;

		geotaggedTemplates = platformClient.post(
				attachedTemplatesOfGeotagsEndpoint,
				subscriptionProfileService.getJwtToken(), hierarchySearchDTO,
				List.class, String.class);

		return geotaggedTemplates;
	}
}
