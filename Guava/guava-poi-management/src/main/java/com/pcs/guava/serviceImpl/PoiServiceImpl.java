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
package com.pcs.guava.serviceImpl;

import static com.pcs.guava.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.guava.commons.validation.ValidationUtils.validateResult;
import static com.pcs.guava.constants.PoiTypeResourceConstants.MARKER;
import static com.pcs.guava.enums.PoiDataFields.DESCRIPTION;
import static com.pcs.guava.enums.PoiDataFields.IDENTIFIER;
import static com.pcs.guava.enums.PoiDataFields.LATITUDE;
import static com.pcs.guava.enums.PoiDataFields.LONGITUDE;
import static com.pcs.guava.enums.PoiDataFields.POI;
import static com.pcs.guava.enums.PoiDataFields.POI_ENTITY_TEMPLATE;
import static com.pcs.guava.enums.PoiDataFields.POI_ENTITY_TEMPLATE_NAME;
import static com.pcs.guava.enums.PoiDataFields.POI_IDENTIFIER;
import static com.pcs.guava.enums.PoiDataFields.POI_IDENTIFIER_KEY;
import static com.pcs.guava.enums.PoiDataFields.POI_IDENTIFIER_VALUE;
import static com.pcs.guava.enums.PoiDataFields.POI_NAME;
import static com.pcs.guava.enums.PoiDataFields.POI_TEMPLATE;
import static com.pcs.guava.enums.PoiDataFields.RADIUS;
import static com.pcs.guava.enums.PoiTypeDataFields.POI_TYPE;
import static com.pcs.guava.enums.PoiTypeDataFields.POI_TYPES;
import static com.pcs.guava.enums.Status.ACTIVE;
import static com.pcs.guava.enums.Status.INACTIVE;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.guava.commons.dto.DomainDTO;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.EntityTemplateDTO;
import com.pcs.guava.commons.dto.FieldMapDTO;
import com.pcs.guava.commons.dto.HierarchySearchDTO;
import com.pcs.guava.commons.dto.HierarchyRelationDTO;
import com.pcs.guava.commons.dto.IdentityDTO;
import com.pcs.guava.commons.dto.PlatformEntityDTO;
import com.pcs.guava.commons.dto.ReturnFieldsDTO;
import com.pcs.guava.commons.dto.SearchFieldsDTO;
import com.pcs.guava.commons.dto.StatusDTO;
import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.commons.dto.Subscription;
import com.pcs.guava.commons.dto.SubscriptionContextHolder;
import com.pcs.guava.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.guava.commons.exception.GalaxyException;
import com.pcs.guava.commons.service.SubscriptionProfileService;
import com.pcs.guava.commons.validation.ValidationUtils;
import com.pcs.guava.dto.PoiDTO;
import com.pcs.guava.dto.PoiESBDTO;
import com.pcs.guava.dto.PoiESBResponseDTO;
import com.pcs.guava.enums.Status;
import com.pcs.guava.rest.client.BaseClient;
import com.pcs.guava.rest.exception.GalaxyRestException;
import com.pcs.guava.services.PoiService;
import com.pcs.guava.services.PoiTypeService;

/**
 * Service implementation for POI Type
 * 
 * @author Greeshma (PCSEG323)
 * @date April 2016
 * @since Guava-1.0.0
 */

@Service
public class PoiServiceImpl implements PoiService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PoiServiceImpl.class);

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	private PoiTypeService poiTypeService;

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformClient;

	@Autowired
	@Qualifier("guavaEsbClient")
	private BaseClient guavaEsbClient;

	@Value("${platform.esb.marker}")
	private String markerPlatformEndpoint;

	@Value("${platform.esb.hierarchy.attach}")
	private String attachHierarchyPlatformEndpoint;

	@Value("${platform.esb.marker.search}")
	private String listMarkersByTypePlatformEndpoint;

	@Value("${platform.esb.marker.list}")
	private String listMarkersPlatformEndpoint;

	@Value("${platform.esb.hierarchy.children.immediate}")
	private String hierarchyChildrenPlatformEndpoint;

	@Value("${platform.esb.marker.find}")
	private String findMarkerPlatformEndpoint;

	@Value("${platform.esb.hierarchy.parents.immediate}")
	private String hierarchyParentsPlatformEndpoint;

	@Value("${platform.esb.marker.delete}")
	private String deleteMarkersPlatformEndpoint;

	@Value("${platform.esb.hierarchy.update.status}")
	private String hierarchyUpdateStatusPlatformEndpoint;

	@Value("${platform.esb.hierarchy.update.node}")
	private String updateNodeHierarchyPlatformEndpoint;

	@Value("${guava.esb.poi.create}")
	private String createPoiESBEndpoint;

	@Value("${guava.esb.poi.update}")
	private String updatePoiESBEndpoint;

	@Value("${guava.esb.poi.batchCreate}")
	private String createListOfPoiEntitiesESBEndpoint;

	@Value("${guava.esb.poi.find}")
	private String findPoiDetailsESBEndpoint;

	@Value("${platform.esb.hierarchy.search.relation}")
	private String listAllPoisByTypeEndpoint;

	@Override
	public StatusMessageDTO createPoi(PoiDTO poiDTO) {

		ValidationUtils.validateMandatoryFields(poiDTO, POI_TYPE, POI_NAME,
				RADIUS, LATITUDE, LONGITUDE);

		StatusMessageDTO status = new StatusMessageDTO(Status.FAILURE);

		PoiESBDTO poiESBDTO = new PoiESBDTO();
		poiESBDTO = constructPoiESBPayload(poiDTO, false);

		try {
			status = guavaEsbClient.post(createPoiESBEndpoint,
					subscriptionProfileService.getJwtToken(), poiESBDTO,
					StatusMessageDTO.class);
		} catch (GalaxyRestException e) {
			if (e.getCode().equals(GalaxyCommonErrorCodes.CUSTOM_ERROR)) {
				throw new GalaxyException(GalaxyCommonErrorCodes.CUSTOM_ERROR,
						"Error in creating POI");
			}
			if (e.getCode().equals(
					GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST.getCode())) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST, "poiName");
			}
		}
		return status;
	}

	@Override
	public List<IdentityDTO> createPois(List<PoiDTO> poiDTOs) {
		List<IdentityDTO> poiIdentifiers = new ArrayList<IdentityDTO>();
		PoiESBResponseDTO poiESBResponse = new PoiESBResponseDTO();
		List<PoiESBDTO> poiESBDTOs = new ArrayList<PoiESBDTO>();

		// Construct POI Entities Payload for POIs Creation
		for (PoiDTO poiDTO : poiDTOs) {
			// Check POI fieldValidations
			ValidationUtils.validateMandatoryFields(poiDTO, POI_TYPE, POI_NAME,
					RADIUS, LATITUDE, LONGITUDE);
			poiESBDTOs.add(constructPoiESBPayload(poiDTO, false));
		}

		poiESBResponse = guavaEsbClient.post(
				createListOfPoiEntitiesESBEndpoint,
				subscriptionProfileService.getJwtToken(), poiESBDTOs,
				PoiESBResponseDTO.class);

		// Check for Error Messages
		if (isNotBlank(poiESBResponse.getErrorCode())) {
			throw new GalaxyException(GalaxyCommonErrorCodes.CUSTOM_ERROR,
					poiESBResponse.getErrorMessage());
		}

		// Get POIO Identifiers from POI ESB Response
		poiIdentifiers = poiESBResponse.getPoiIdentifiers();

		return poiIdentifiers;
	}

	@Override
	public StatusMessageDTO updatePoi(String poiName, PoiDTO poiDTO) {
		StatusMessageDTO updateStatusMessage = new StatusMessageDTO(
				Status.FAILURE);
		validateUpdatePoiPayload(poiDTO);
		poiDTO.setPoiName(poiName);

		if (isBlank(poiDTO.getDomainName())) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			poiDTO.setDomainName(domainName);
		}

		// validate poiType before creating poi entity
		try {
			poiTypeService.getPoiType(poiDTO.getPoiType(),
					poiDTO.getDomainName());
		} catch (GalaxyRestException gre) {
			if (gre.getMessage().equals(
					GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				LOGGER.debug("PoiType {} doesnot exist." + poiDTO.getPoiType());
				throw new GalaxyException(
						GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
						POI_TYPE.getDescription());
			}
		}
		// update poi
		StatusMessageDTO poiEntityUpdateStatusMessage = updatePoiEntity(poiDTO);
		LOGGER.debug("Response for poi update : {}",
				poiEntityUpdateStatusMessage.toString());

		// if poiType fieldvalues are present in payload, then only update
		// poiType
		StatusMessageDTO poiTypeUpdateStatusMessage = new StatusMessageDTO(
				Status.FAILURE);
		if (CollectionUtils.isNotEmpty(poiDTO.getPoiTypeValues())) {
			poiTypeUpdateStatusMessage = updatePoiTypeEntity(poiDTO);
		} else {
			poiTypeUpdateStatusMessage.setStatus(Status.SUCCESS);
		}
		LOGGER.debug("Response for poiType update : {}",
				poiTypeUpdateStatusMessage.toString());
		if (!(poiEntityUpdateStatusMessage.getStatus().getStatus()
				.equals(Status.SUCCESS.getStatus()) && poiTypeUpdateStatusMessage
				.getStatus().getStatus().equals(Status.SUCCESS.getStatus()))) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.SPECIFIC_DATA_CANNOT_BE_UPDATED,
					"Poi data");
		} else {
			updateStatusMessage = poiEntityUpdateStatusMessage;
		}
		return updateStatusMessage;
	}

	@Override
	public StatusMessageDTO updatePoi1(PoiDTO poiDTO) {
		StatusMessageDTO updateStatusMessage = new StatusMessageDTO(
				Status.FAILURE);
		validateUpdatePoiPayload(poiDTO);

		PoiESBResponseDTO poiESBResponse = new PoiESBResponseDTO();

		poiESBResponse = guavaEsbClient.put(updatePoiESBEndpoint,
				subscriptionProfileService.getJwtToken(),
				constructPoiESBPayload(poiDTO, true), PoiESBResponseDTO.class);

		// Check for Error Messages
		if (isNotBlank(poiESBResponse.getErrorCode())
				&& poiESBResponse.getErrorCode().equalsIgnoreCase(
						GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED
								.getCode().toString())) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED,
					poiESBResponse.getField());
		}

		updateStatusMessage.setStatus(Status.SUCCESS);
		return updateStatusMessage;
	}

	private PoiESBDTO constructPoiESBPayload(PoiDTO poiDTO, Boolean isUpdate) {
		PoiESBDTO poiESB = new PoiESBDTO();

		// Set poi entity
		EntityDTO poiEntity = new EntityDTO();

		// Set POI Domain
		DomainDTO poiDomain = new DomainDTO();
		poiDomain.setDomainName(poiDTO.getDomainName());
		poiEntity.setDomain(poiDomain);

		// Set PlatformEntityDTO as MARKER
		PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType(MARKER);
		poiEntity.setPlatformEntity(platformEntityDTO);

		// Set POI Template
		poiEntity.setEntityTemplate(getPoiTemplate());

		// Set POI FieldValues
		poiEntity.setFieldValues(constructPoiFieldValues(poiDTO));

		if (StringUtils.isNotBlank(poiDTO.getStatus())) {
			StatusDTO poiStatus = new StatusDTO();
			poiStatus.setStatusName(poiDTO.getStatus());
			poiEntity.setEntityStatus(poiStatus);
		} else {
			StatusDTO poiStatus = new StatusDTO();
			poiStatus.setStatusName(Status.ACTIVE.name());
			poiEntity.setEntityStatus(poiStatus);
		}

		// For update set the identifier
		if (isUpdate) {
			poiEntity.setIdentifier(poiDTO.getPoiIdentifier());
		}

		poiESB.setPoiName(poiDTO.getPoiName());
		poiESB.setPoiType(poiDTO.getPoiType());

		// Set POI entity
		poiESB.setPoiEntity(poiEntity);

		// Set POI type entity
		poiESB.setPoiTypeEntity(constructPoiType(poiDTO));

		return poiESB;
	}

	private List<FieldMapDTO> constructPoiFieldValues(PoiDTO poiDTO) {
		List<FieldMapDTO> fieldValues = new ArrayList<FieldMapDTO>();
		fieldValues.add(new FieldMapDTO(POI_NAME.getFieldName(), poiDTO
				.getPoiName()));
		fieldValues.add(new FieldMapDTO(POI_TYPE.getFieldName(), poiDTO
				.getPoiType()));
		fieldValues.add(new FieldMapDTO(RADIUS.getFieldName(), poiDTO
				.getRadius()));
		fieldValues.add(new FieldMapDTO(LATITUDE.getFieldName(), poiDTO
				.getLatitude()));
		fieldValues.add(new FieldMapDTO(LONGITUDE.getFieldName(), poiDTO
				.getLongitude()));
		if (StringUtils.isNotBlank(poiDTO.getDescription())) {
			fieldValues.add(new FieldMapDTO(DESCRIPTION.getFieldName(), poiDTO
					.getDescription()));
		} else {
			fieldValues.add(new FieldMapDTO(DESCRIPTION.getFieldName(), ""));
		}
		return fieldValues;
	}

	@Override
	public List<PoiDTO> getAllPois(String domain, String poiType) {
		LOGGER.debug("--getAllPois--");
		DomainDTO domainDTO = new DomainDTO();
		List<PoiDTO> poiDTOs = new ArrayList<PoiDTO>();
		if (isBlank(domain)) {
			Subscription subscription = SubscriptionContextHolder.getContext()
					.getSubscription();
			domain = subscription.getEndUserDomain();
		}
		domainDTO.setDomainName(domain);
		if (isBlank(poiType)) {
			poiDTOs = getAllPois(domainDTO);
		} else {
			// poiDTOs = getAllPoisByType(domainDTO, poiType);
			poiDTOs = getAllPoisByType1(domainDTO, poiType);
		}
		return poiDTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StatusMessageDTO deletePoi(IdentityDTO poi) {

		StatusMessageDTO statusMessageDTO = null;
		StatusMessageDTO poiEntityDeletStatusMessage = new StatusMessageDTO();
		StatusMessageDTO poiTypeDeleteStatusMessage = new StatusMessageDTO();
		EntityDTO resultEntityDTO = new EntityDTO();
		// validate mandatory fields
		validatePoiIdentifier(poi);
		if (isBlank(poi.getDomain().getDomainName())) {
			Subscription subscription = SubscriptionContextHolder.getContext()
					.getSubscription();
			DomainDTO domainDTO = new DomainDTO();
			domainDTO.setDomainName(subscription.getEndUserDomain());
			poi.setDomain(domainDTO);
		}
		// Get poi details
		EntityDTO poiDetails = new EntityDTO();
		try {
			poiDetails = platformClient.post(findMarkerPlatformEndpoint,
					subscriptionProfileService.getJwtToken(), poi,
					EntityDTO.class);
		} catch (GalaxyRestException gre) {
			if (gre.getMessage().equals(
					GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				LOGGER.debug("Poi {} doesnot exist.");
				throw new GalaxyException(
						GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
						POI.getDescription());
			}
		}
		if (poiDetails != null) {
			FieldMapDTO poiTypeMap = new FieldMapDTO();
			poiTypeMap.setKey(POI_TYPE.getFieldName());

			HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();

			// set actor
			IdentityDTO actor = new IdentityDTO();
			actor.setEntityTemplate(poiDetails.getEntityTemplate());
			actor.setPlatformEntity(poiDetails.getPlatformEntity());
			actor.setDomain(poiDetails.getDomain());
			actor.setIdentifier(poiDetails.getIdentifier());
			hierarchySearchDTO.setActor(actor);

			// set search template name
			hierarchySearchDTO.setSearchTemplateName(fetchField(
					poiDetails.getFieldValues(), poiTypeMap));
			hierarchySearchDTO.setSearchEntityType(MARKER);

			// fetch the children of poi to get the poi type
			List<EntityDTO> poiTypes = platformClient.post(
					hierarchyChildrenPlatformEndpoint,
					subscriptionProfileService.getJwtToken(),
					hierarchySearchDTO, List.class, EntityDTO.class);

			if (CollectionUtils.isNotEmpty(poiTypes)) {

				// Delete Poi Entity
				poiEntityDeletStatusMessage = platformClient.post(
						deleteMarkersPlatformEndpoint,
						subscriptionProfileService.getJwtToken(), poi,
						StatusMessageDTO.class);
				EntityDTO poiEntityDTO = new EntityDTO();
				poiEntityDTO.setDomain(poi.getDomain());
				poiEntityDTO.setEntityTemplate(poi.getEntityTemplate());
				poiEntityDTO.setPlatformEntity(poi.getPlatformEntity());
				poiEntityDTO.setIdentifier(poi.getIdentifier());
				StatusDTO statusDTO = new StatusDTO();
				statusDTO.setStatusName(Status.DELETED.name());
				poiEntityDTO.setEntityStatus(statusDTO);

				if ((poiEntityDeletStatusMessage.getStatus().getStatus()
						.equals(Status.SUCCESS.getStatus()))) {
					// Update poi node in Neo4j
					resultEntityDTO = platformClient.put(
							hierarchyUpdateStatusPlatformEndpoint,
							subscriptionProfileService.getJwtToken(),
							poiEntityDTO, EntityDTO.class);
				}
				if ((resultEntityDTO.getEntityStatus().getStatusName()
						.equals(Status.DELETED.name()))) {
					for (EntityDTO entityDTO : poiTypes) {
						IdentityDTO poiTypeIdentity = new IdentityDTO();
						poiTypeIdentity.setEntityTemplate(entityDTO
								.getEntityTemplate());
						poiTypeIdentity.setPlatformEntity(entityDTO
								.getPlatformEntity());
						poiTypeIdentity.setDomain(entityDTO.getDomain());
						poiTypeIdentity
								.setIdentifier(entityDTO.getIdentifier());
						entityDTO.setEntityStatus(statusDTO);
						// Delete PoiType Entity
						poiTypeDeleteStatusMessage = platformClient.post(
								deleteMarkersPlatformEndpoint,
								subscriptionProfileService.getJwtToken(),
								poiTypeIdentity, StatusMessageDTO.class);
						if ((poiTypeDeleteStatusMessage.getStatus().getStatus()
								.equals(Status.SUCCESS.getStatus()))) {
							// Update poi type node in Neo4j
							resultEntityDTO = platformClient.put(
									hierarchyUpdateStatusPlatformEndpoint,
									subscriptionProfileService.getJwtToken(),
									entityDTO, EntityDTO.class);
						}
					}
				}
			}
			if (!(resultEntityDTO.getEntityStatus().getStatusName()
					.equals(Status.DELETED.name()))) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.ENTITY_STATUS_UPDATE,
						POI.getDescription());
			} else {
				statusMessageDTO = poiEntityDeletStatusMessage;
			}
		}
		return statusMessageDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PoiDTO getPoiDetails1(IdentityDTO poi) {
		// validate mandatory fields
		validatePoiIdentifier(poi);

		// Get poi details
		PoiDTO poiDetails = guavaEsbClient.post(findPoiDetailsESBEndpoint,
				subscriptionProfileService.getJwtToken(), poi, PoiDTO.class);

		validateResult(poiDetails);

		return poiDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PoiDTO getPoiDetails(IdentityDTO poi) {
		// validate mandatory fields
		validatePoiIdentifier(poi);

		// Get poi details
		EntityDTO poiDetails = platformClient.post(findMarkerPlatformEndpoint,
				subscriptionProfileService.getJwtToken(), poi, EntityDTO.class);
		PoiDTO poiDTO = new PoiDTO();
		if (poiDetails != null) {
			// Get details from fetched poi
			FieldMapDTO poiTypeMap = new FieldMapDTO();
			poiTypeMap.setKey(POI_TYPE.getFieldName());
			poiDTO.setPoiType(fetchField(poiDetails.getFieldValues(),
					poiTypeMap));

			// Get poi name
			FieldMapDTO poiNameMap = new FieldMapDTO();
			poiNameMap.setKey(POI_NAME.getFieldName());
			poiDTO.setPoiName(fetchField(poiDetails.getFieldValues(),
					poiNameMap));

			// Get Radius
			FieldMapDTO radiusMap = new FieldMapDTO();
			radiusMap.setKey(RADIUS.getFieldName());
			poiDTO.setRadius(fetchField(poiDetails.getFieldValues(), radiusMap));

			// Get Latitude
			FieldMapDTO latitudeMap = new FieldMapDTO();
			latitudeMap.setKey(LATITUDE.getFieldName());
			poiDTO.setLatitude(fetchField(poiDetails.getFieldValues(),
					latitudeMap));

			// Get Longitude
			FieldMapDTO longitudeMap = new FieldMapDTO();
			longitudeMap.setKey(LONGITUDE.getFieldName());
			poiDTO.setLongitude(fetchField(poiDetails.getFieldValues(),
					longitudeMap));

			// Get description
			FieldMapDTO poiDescMap = new FieldMapDTO();
			poiDescMap.setKey(DESCRIPTION.getFieldName());
			try {
				poiDTO.setDescription((fetchField(poiDetails.getFieldValues(),
						poiDescMap)));
			} catch (GalaxyException ge) {
				if (!ge.getMessage().equals(
						GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED
								.getMessage())) {
					throw ge;
				}
			}

			// Set Status from poiDetails entity response
			String statusName = poiDetails.getEntityStatus().getStatusName();
			if (statusName.equalsIgnoreCase(ACTIVE.name())) {
				poiDTO.setStatus(ACTIVE.name());
			} else {
				poiDTO.setStatus(INACTIVE.name());
			}

			// Set domain name
			poiDTO.setDomainName(poiDetails.getDomain().getDomainName());

			// Set the identifier
			poiDTO.setPoiIdentifier(poiDetails.getIdentifier());

			HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();

			// set actor
			IdentityDTO actor = new IdentityDTO();
			actor.setEntityTemplate(poiDetails.getEntityTemplate());
			actor.setPlatformEntity(poiDetails.getPlatformEntity());
			actor.setDomain(poiDetails.getDomain());
			actor.setIdentifier(poiDetails.getIdentifier());
			hierarchySearchDTO.setActor(actor);

			// set search template name
			hierarchySearchDTO.setSearchTemplateName(poiDTO.getPoiType());
			hierarchySearchDTO.setSearchEntityType(MARKER);

			// fetch the children of poi to get the poi type
			List<EntityDTO> poiTypes = platformClient.post(
					hierarchyChildrenPlatformEndpoint,
					subscriptionProfileService.getJwtToken(),
					hierarchySearchDTO, List.class, EntityDTO.class);

			if (CollectionUtils.isNotEmpty(poiTypes)) {
				for (EntityDTO entityDTO : poiTypes) {
					poiDTO.setPoiTypeValues(entityDTO.getDataprovider());
					poiDTO.setPoiTypeIdentifier(entityDTO.getIdentifier());

				}
			}

			poiDTO.setDomainName(poiDetails.getDomain().getDomainName());
		}
		validateResult(poiDTO);
		return poiDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PoiDTO> getPois(List<String> poiTypes, String domain) {
		ValidationUtils.validateCollection(POI_TYPES, poiTypes);
		DomainDTO domainDTO = new DomainDTO();
		if (isBlank(domain)) {
			Subscription subscription = SubscriptionContextHolder.getContext()
					.getSubscription();
			domain = subscription.getEndUserDomain();
		}
		domainDTO.setDomainName(domain);
		List<PoiDTO> poiDTOs = new ArrayList<PoiDTO>();
		List<ReturnFieldsDTO> poiList = new ArrayList<ReturnFieldsDTO>();
		for (String poiType : poiTypes) {
			try {

				SearchFieldsDTO searchFieldsDTO = getSearchFieldsDTO(domainDTO,
						poiType);
				poiList = platformClient.post(
						listMarkersByTypePlatformEndpoint,
						subscriptionProfileService.getJwtToken(),
						searchFieldsDTO, List.class, ReturnFieldsDTO.class);
			} catch (GalaxyRestException e) {
				LOGGER.debug("--No Data--");
			}
			// }
			if (CollectionUtils.isNotEmpty(poiList)) {
				for (ReturnFieldsDTO poi : poiList) {
					PoiDTO poiDTO = new PoiDTO();
					for (FieldMapDTO fieldMapDTO : poi.getDataprovider()) {
						if (fieldMapDTO.getKey()
								.equals(POI_NAME.getFieldName())) {
							poiDTO.setPoiName(fieldMapDTO.getValue());
						}
						if (fieldMapDTO.getKey()
								.equals(POI_TYPE.getFieldName())) {
							poiDTO.setPoiType(fieldMapDTO.getValue());
						}
						if (fieldMapDTO.getKey().equals(RADIUS.getFieldName())) {
							poiDTO.setRadius(fieldMapDTO.getValue());
						}
						if (fieldMapDTO.getKey()
								.equals(LATITUDE.getFieldName())) {
							poiDTO.setLatitude(fieldMapDTO.getValue());
						}
						if (fieldMapDTO.getKey().equals(
								LONGITUDE.getFieldName())) {
							poiDTO.setLongitude(fieldMapDTO.getValue());
						}
						if (fieldMapDTO.getKey().equals(
								DESCRIPTION.getFieldName())) {
							poiDTO.setDescription(fieldMapDTO.getValue());
						}

					}
					poiDTO.setPoiIdentifier(poi.getIdentifier());
					poiDTO.setDomainName(poi.getDomain().getDomainName());
					poiDTOs.add(poiDTO);
				}
			}
		}
		validateResult(poiDTOs);
		return poiDTOs;
	}

	private void validatePoiIdentifier(IdentityDTO identityDTO) {
		validateMandatoryFields(identityDTO, POI_ENTITY_TEMPLATE,
				POI_IDENTIFIER);
		validateMandatoryFields(identityDTO.getIdentifier(),
				POI_IDENTIFIER_KEY, POI_IDENTIFIER_VALUE);
		validateMandatoryFields(identityDTO.getEntityTemplate(),
				POI_ENTITY_TEMPLATE_NAME);
	}

	@SuppressWarnings("unchecked")
	private List<PoiDTO> getAllPoisByType1(DomainDTO domainDTO, String poiType) {
		List<PoiDTO> poiDTOs = new ArrayList<PoiDTO>();

		HierarchyRelationDTO hierarchyRelationDTO = new HierarchyRelationDTO();
		hierarchyRelationDTO.setParent(getEntity(domainDTO.getDomainName(),
				POI_TEMPLATE.getFieldName()));
		hierarchyRelationDTO.setChild(getEntity(domainDTO.getDomainName(),
				poiType));

		List<HierarchyRelationDTO> entityDTOs = platformClient.post(
				listAllPoisByTypeEndpoint,
				subscriptionProfileService.getJwtToken(), hierarchyRelationDTO,
				List.class, HierarchyRelationDTO.class);

		for (HierarchyRelationDTO entity : entityDTOs) {
			PoiDTO poiDTO = new PoiDTO();
			for (FieldMapDTO fieldMapDTO : entity.getParent().getDataprovider()) {
				if (fieldMapDTO.getKey().equals(POI_NAME.getFieldName())) {
					poiDTO.setPoiName(fieldMapDTO.getValue());
				}
				if (fieldMapDTO.getKey().equals(POI_TYPE.getFieldName())) {
					poiDTO.setPoiType(fieldMapDTO.getValue());
				}
				if (fieldMapDTO.getKey().equals(RADIUS.getFieldName())) {
					poiDTO.setRadius(fieldMapDTO.getValue());
				}
				if (fieldMapDTO.getKey().equals(LATITUDE.getFieldName())) {
					poiDTO.setLatitude(fieldMapDTO.getValue());
				}
				if (fieldMapDTO.getKey().equals(LONGITUDE.getFieldName())) {
					poiDTO.setLongitude(fieldMapDTO.getValue());
				}
				if (fieldMapDTO.getKey().equals(DESCRIPTION.getFieldName())) {
					poiDTO.setDescription(fieldMapDTO.getValue());
				}
			}
			//Set POI Identifier
			poiDTO.setPoiIdentifier(entity.getParent().getIdentifier());
			
			//Set POI Type Values
			poiDTO.setPoiTypeValues(entity.getChild().getDataprovider());
			
			poiDTOs.add(poiDTO);
		}
		return poiDTOs;
	}

	private EntityDTO getEntity(String domain, String entityTemplate) {

		EntityDTO entity = new EntityDTO();
		DomainDTO domainDto = new DomainDTO();
		domainDto.setDomainName(domain);
		entity.setDomain(domainDto);

		PlatformEntityDTO platform = new PlatformEntityDTO();
		platform.setPlatformEntityType("MARKER");
		entity.setPlatformEntity(platform);

		EntityTemplateDTO template = new EntityTemplateDTO();
		template.setEntityTemplateName(entityTemplate);
		entity.setEntityTemplate(template);

		return entity;
	}

	@SuppressWarnings("unchecked")
	private List<PoiDTO> getAllPoisByType(DomainDTO domainDTO, String poiType) {
		List<PoiDTO> poiDTOs = new ArrayList<PoiDTO>();
		SearchFieldsDTO searchFieldsDTO = getSearchFieldsDTO(domainDTO, poiType);
		List<ReturnFieldsDTO> poiList = platformClient.post(
				listMarkersByTypePlatformEndpoint,
				subscriptionProfileService.getJwtToken(), searchFieldsDTO,
				List.class, ReturnFieldsDTO.class);

		if (CollectionUtils.isNotEmpty(poiList)) {

			for (ReturnFieldsDTO poi : poiList) {
				PoiDTO poiDTO = new PoiDTO();
				for (FieldMapDTO fieldMapDTO : poi.getDataprovider()) {
					if (fieldMapDTO.getKey().equals(POI_NAME.getFieldName())) {
						poiDTO.setPoiName(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(POI_TYPE.getFieldName())) {
						poiDTO.setPoiType(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(RADIUS.getFieldName())) {
						poiDTO.setRadius(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(LATITUDE.getFieldName())) {
						poiDTO.setLatitude(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(LONGITUDE.getFieldName())) {
						poiDTO.setLongitude(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(DESCRIPTION.getFieldName())) {
						poiDTO.setDescription(fieldMapDTO.getValue());
					}
				}
				// Set the identifier
				poiDTO.setPoiIdentifier(poi.getIdentifier());
				IdentityDTO poiIdentity = new IdentityDTO();

				poiDTO.setDomainName(poi.getDomain().getDomainName());

				HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();
				hierarchySearchDTO.setSearchEntityType(MARKER);
				hierarchySearchDTO.setSearchTemplateName(poiDTO.getPoiType());

				poiIdentity.setDomain(domainDTO);
				poiIdentity.setEntityTemplate(getPoiTemplate());
				PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
				platformEntityDTO.setPlatformEntityType(MARKER);
				poiIdentity.setPlatformEntity(platformEntityDTO);
				poiIdentity.setIdentifier(poi.getIdentifier());

				hierarchySearchDTO.setActor(poiIdentity);
				List<EntityDTO> entityDTOs = new ArrayList<EntityDTO>();

				try {
					entityDTOs = platformClient.post(
							hierarchyChildrenPlatformEndpoint,
							subscriptionProfileService.getJwtToken(),
							hierarchySearchDTO, List.class, EntityDTO.class);
				} catch (GalaxyRestException e) {
					LOGGER.debug("--No Data--");
				}

				if (CollectionUtils.isNotEmpty(entityDTOs)) {
					for (EntityDTO entityDTO : entityDTOs) {
						poiDTO.setPoiTypeValues(entityDTO.getDataprovider());
						poiDTO.setPoiTypeIdentifier(entityDTO.getIdentifier());

					}
				}

				poiDTOs.add(poiDTO);
			}
		}
		validateResult(poiDTOs);
		return poiDTOs;
	}

	private SearchFieldsDTO getSearchFieldsDTO(DomainDTO domainDTO,
			String poiType) {

		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(POI_TYPE.getFieldName());
		fieldMapDTO.setValue(poiType);
		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		searchFields.add(fieldMapDTO);
		searchFieldsDTO.setDomain(domainDTO);
		searchFieldsDTO.setSearchFields(searchFields);
		searchFieldsDTO.setEntityTemplate(getPoiTemplate());
		return searchFieldsDTO;

	}

	@SuppressWarnings("unchecked")
	private List<PoiDTO> getAllPois(DomainDTO domainDTO) {
		List<PoiDTO> poiDTOs = new ArrayList<PoiDTO>();
		EntityDTO entityDTO = new EntityDTO();
		entityDTO.setEntityTemplate(getPoiTemplate());
		entityDTO.setDomain(domainDTO);
		List<EntityDTO> entityDTOs = platformClient.post(
				listMarkersPlatformEndpoint,
				subscriptionProfileService.getJwtToken(), entityDTO,
				List.class, EntityDTO.class);

		if (CollectionUtils.isNotEmpty(entityDTOs)) {
			for (EntityDTO entityDTOResult : entityDTOs) {
				PoiDTO poiDTO = new PoiDTO();
				for (FieldMapDTO fieldMapDTO : entityDTOResult
						.getDataprovider()) {
					if (fieldMapDTO.getKey().equals(POI_NAME.getFieldName())) {
						poiDTO.setPoiName(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(POI_TYPE.getFieldName())) {
						poiDTO.setPoiType(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(RADIUS.getFieldName())) {
						poiDTO.setRadius(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(LATITUDE.getFieldName())) {
						poiDTO.setLatitude(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(LONGITUDE.getFieldName())) {
						poiDTO.setLongitude(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(DESCRIPTION.getFieldName())) {
						poiDTO.setDescription(fieldMapDTO.getValue());
					}
				}
				// Set the identifier
				poiDTO.setPoiIdentifier(entityDTOResult.getIdentifier());
				poiDTO.setDomainName(entityDTOResult.getDomain()
						.getDomainName());
				poiDTOs.add(poiDTO);
			}
		}
		return poiDTOs;
	}

	private StatusMessageDTO updatePoiTypeEntity(PoiDTO poiDTO) {
		EntityDTO inPoiTypeEntity = new EntityDTO();
		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(poiDTO.getPoiType());
		inPoiTypeEntity.setEntityTemplate(entityTemplate);

		PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType(MARKER);
		inPoiTypeEntity.setPlatformEntity(platformEntityDTO);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(poiDTO.getDomainName());
		inPoiTypeEntity.setDomain(domain);

		List<FieldMapDTO> poiTypeFieldValues = poiDTO.getPoiTypeValues();
		poiTypeFieldValues.add(new FieldMapDTO(IDENTIFIER.getFieldName(),
				poiDTO.getPoiTypeIdentifier().getValue()));
		inPoiTypeEntity.setFieldValues(poiTypeFieldValues);

		inPoiTypeEntity.setIdentifier(new FieldMapDTO(
				IDENTIFIER.getFieldName(), poiDTO.getPoiTypeIdentifier()
						.getValue()));
		// TODO only updates to Active
		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName(Status.ACTIVE.name());
		inPoiTypeEntity.setEntityStatus(entityStatus);

		return poiTypeService.updatePoiType(inPoiTypeEntity);

	}

	private EntityDTO getPoiTypeEntityFromPoiDTO(PoiDTO poiDTO) {
		EntityDTO poiTypeEntity = new EntityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(poiDTO.getDomainName());
		poiTypeEntity.setDomain(domain);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(poiDTO.getPoiType());
		poiTypeEntity.setEntityTemplate(entityTemplate);
		entityTemplate.setPlatformEntityType(MARKER);
		List<FieldMapDTO> fiedValues = new ArrayList<FieldMapDTO>();

		// added to create poi type even without fiedValues
		if (CollectionUtils.isNotEmpty(poiDTO.getPoiTypeValues())) {
			fiedValues = poiDTO.getPoiTypeValues();
		} else {
			fiedValues.add(new FieldMapDTO(IDENTIFIER.getFieldName()));
		}
		poiTypeEntity.setFieldValues(fiedValues);

		return poiTypeEntity;
	}

	private EntityDTO constructPoiType(PoiDTO poiDTO) {
		EntityDTO poiTypeEntity = new EntityDTO();

		// Set Domain of POI Type
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(poiDTO.getDomainName());
		poiTypeEntity.setDomain(domain);

		// Set POI Type Template
		EntityTemplateDTO poiTypeEntityTemplate = new EntityTemplateDTO();
		poiTypeEntityTemplate.setEntityTemplateName(poiDTO.getPoiType());
		poiTypeEntity.setEntityTemplate(poiTypeEntityTemplate);

		// Set PlatformEntityDTO as MARKER
		PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType(MARKER);
		poiTypeEntity.setPlatformEntity(platformEntityDTO);

		List<FieldMapDTO> poiTypeFieldValues = new ArrayList<FieldMapDTO>();

		// Check if POI Type has fieldValues, if not add identifier
		if (CollectionUtils.isNotEmpty(poiDTO.getPoiTypeValues())) {
			poiTypeFieldValues = poiDTO.getPoiTypeValues();
		} else {
			poiTypeFieldValues.add(new FieldMapDTO(IDENTIFIER.getFieldName()));
		}
		poiTypeEntity.setFieldValues(poiTypeFieldValues);

		if (StringUtils.isNotBlank(poiDTO.getStatus())) {
			StatusDTO poiStatus = new StatusDTO();
			poiStatus.setStatusName(poiDTO.getStatus());
			poiTypeEntity.setEntityStatus(poiStatus);
		} else {
			StatusDTO poiStatus = new StatusDTO();
			poiStatus.setStatusName(Status.ACTIVE.name());
			poiTypeEntity.setEntityStatus(poiStatus);
		}

		return poiTypeEntity;
	}

	private EntityDTO getPoiEntityFromPoiDTO(PoiDTO poiDTO, boolean isUpdate) {
		EntityDTO poitEntity = new EntityDTO();

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(poiDTO.getDomainName());
		poitEntity.setDomain(domain);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(POI_TEMPLATE.getDescription());
		poitEntity.setEntityTemplate(entityTemplate);

		PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType(MARKER);
		poitEntity.setPlatformEntity(platformEntityDTO);

		List<FieldMapDTO> fieldValues = constructPoiFieldValues(poiDTO);
		// Only updates to Active
		StatusDTO entityStatus = new StatusDTO();
		if (isUpdate) {
			fieldValues.add(new FieldMapDTO(IDENTIFIER.getFieldName(), poiDTO
					.getPoiIdentifier().getValue()));
			poitEntity.setIdentifier(new FieldMapDTO(IDENTIFIER.getFieldName(),
					poiDTO.getPoiIdentifier().getValue()));
			// entityStatus.setStatusName(Status.ACTIVE.name());
		}

		if (poiDTO.getStatus().equalsIgnoreCase(Status.ACTIVE.name())) {
			entityStatus.setStatusName(Status.ACTIVE.name());
		} else if (poiDTO.getStatus().equalsIgnoreCase(Status.INACTIVE.name())) {
			entityStatus.setStatusName(Status.INACTIVE.name());
		}

		poitEntity.setEntityStatus(entityStatus);
		poitEntity.setFieldValues(fieldValues);
		return poitEntity;
	}

	private void validateUpdatePoiPayload(PoiDTO poiDTO) {
		ValidationUtils.validateMandatoryFields(poiDTO, POI_TYPE);
	}

	private StatusMessageDTO updatePoiEntity(PoiDTO poiDTO) {

		List<FieldMapDTO> returnFieldsList = getExistingPoi(poiDTO);

		FieldMapDTO poiTypeFieldMap = new FieldMapDTO(POI_TYPE.getFieldName(),
				poiDTO.getPoiType());
		FieldMapDTO resultPoiTypeFieldMap = new FieldMapDTO(
				POI_TYPE.getFieldName());
		if (!poiTypeFieldMap.getValue().equals(
				fetchField(returnFieldsList, resultPoiTypeFieldMap))) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED,
					POI_TYPE.getDescription());
		}

		FieldMapDTO poiNameFieldMap = new FieldMapDTO(POI_NAME.getFieldName(),
				poiDTO.getPoiName());
		FieldMapDTO resultPoiNameFieldMap = new FieldMapDTO(
				POI_NAME.getFieldName());
		if (!poiNameFieldMap.getValue().equals(
				fetchField(returnFieldsList, resultPoiNameFieldMap))) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED,
					POI_NAME.getDescription());
		}
		EntityDTO inPoiEntity = getPoiEntityFromPoiDTO(poiDTO, true);

		// Bug fix - status will always be set to true - Incorrect
		// StatusDTO entityStatus = new StatusDTO();
		// entityStatus.setStatusName(Status.ACTIVE.name());
		// inPoiEntity.setEntityStatus(entityStatus);

		EntityDTO poiNodeUpdate = platformClient.put(
				updateNodeHierarchyPlatformEndpoint,
				subscriptionProfileService.getJwtToken(), inPoiEntity,
				EntityDTO.class);

		return platformClient.put(markerPlatformEndpoint,
				subscriptionProfileService.getJwtToken(), inPoiEntity,
				StatusMessageDTO.class);
	}

	@SuppressWarnings("unchecked")
	private List<FieldMapDTO> getExistingPoi(PoiDTO poiDTO) {
		// fetch already existing POI by poiName and poiType

		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
		FieldMapDTO poiTypeFiledMap = new FieldMapDTO(POI_TYPE.getFieldName(),
				poiDTO.getPoiType());
		FieldMapDTO poiNameFiledMap = new FieldMapDTO(POI_NAME.getFieldName(),
				poiDTO.getPoiName());
		FieldMapDTO identifierFiledMap = new FieldMapDTO(
				IDENTIFIER.getFieldName(), poiDTO.getPoiIdentifier().getValue());
		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		searchFields.add(poiTypeFiledMap);
		searchFields.add(poiNameFiledMap);
		searchFields.add(identifierFiledMap);

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(poiDTO.getDomainName());
		searchFieldsDTO.setDomain(domainDTO);

		searchFieldsDTO.setSearchFields(searchFields);
		searchFieldsDTO.setEntityTemplate(getPoiTemplate());
		List<String> returnFields = new ArrayList<String>();
		returnFields.add(0, POI_NAME.getFieldName());
		returnFields.add(1, POI_TYPE.getFieldName());
		returnFields.add(2, DESCRIPTION.getFieldName());
		searchFieldsDTO.setReturnFields(returnFields);

		List<ReturnFieldsDTO> returnedFields = platformClient.post(
				listMarkersByTypePlatformEndpoint,
				subscriptionProfileService.getJwtToken(), searchFieldsDTO,
				List.class, ReturnFieldsDTO.class);

		List<FieldMapDTO> returnFieldsList = null;
		if (CollectionUtils.isNotEmpty(returnedFields)) {
			ReturnFieldsDTO returnFieldsDTO = returnedFields.get(0);
			returnFieldsList = returnFieldsDTO.getReturnFields();
		}

		return returnFieldsList;
	}

	private EntityTemplateDTO getPoiTemplate() {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(POI_TEMPLATE.getFieldName());
		return entityTemplateDTO;
	}

	private String fetchField(List<FieldMapDTO> fieldMapDTOs,
			FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		// populate field<FieldMapDTO> from input EntityDto
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

}
