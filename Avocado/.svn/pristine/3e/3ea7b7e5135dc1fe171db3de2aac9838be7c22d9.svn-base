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
package com.pcs.avocado.serviceImpl;

import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getContext;
import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getJwtToken;
import static com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes.INVALID_LIST_DATA_SPECIFIED;
import static com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes.POINTS_SELECTION;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateResult;
import static com.pcs.avocado.constans.WebConstants.ASSET_TYPE_TEMPLATE;
import static com.pcs.avocado.constans.WebConstants.DATASOURCE_NAME_FIELD;
import static com.pcs.avocado.constans.WebConstants.DATATYPE;
import static com.pcs.avocado.constans.WebConstants.DEVICE_TEMPLATE;
import static com.pcs.avocado.constans.WebConstants.DISPLAY_NAME;
import static com.pcs.avocado.constans.WebConstants.EQUIP_NAME;
import static com.pcs.avocado.constans.WebConstants.EQUIP_TEMPLATE;
import static com.pcs.avocado.constans.WebConstants.EXPRESSION;
import static com.pcs.avocado.constans.WebConstants.FALSE;
import static com.pcs.avocado.constans.WebConstants.IS_SELECTED;
import static com.pcs.avocado.constans.WebConstants.MARKER;
import static com.pcs.avocado.constans.WebConstants.PHYSICAL_QUANTITY;
import static com.pcs.avocado.constans.WebConstants.POINT_ID;
import static com.pcs.avocado.constans.WebConstants.POINT_NAME;
import static com.pcs.avocado.constans.WebConstants.POINT_TEMPLATE;
import static com.pcs.avocado.constans.WebConstants.PRECEDENCE;
import static com.pcs.avocado.constans.WebConstants.TAGS;
import static com.pcs.avocado.constans.WebConstants.TRUE;
import static com.pcs.avocado.constans.WebConstants.TYPE;
import static com.pcs.avocado.constans.WebConstants.UNIT;
import static com.pcs.avocado.enums.AssetDataFields.ASSET_ID;
import static com.pcs.avocado.enums.AssetDataFields.ASSET_IDENTIFIER;
import static com.pcs.avocado.enums.AssetDataFields.ASSET_NAME;
import static com.pcs.avocado.enums.AssetDataFields.ASSET_TEMPLATE;
import static com.pcs.avocado.enums.AssetDataFields.ASSET_TYPE;
import static com.pcs.avocado.enums.AssetDataFields.ASSET_TYPE_IDENTIFIER;
import static com.pcs.avocado.enums.AssetDataFields.DESCRIPTION;
import static com.pcs.avocado.enums.AssetDataFields.IDENTIFIER;
import static com.pcs.avocado.enums.AssetDataFields.SERIAL_NUMBER;
import static com.pcs.avocado.enums.EquipmentDataFields.DEVICE_IDENTIFIER;
import static com.pcs.avocado.enums.EquipmentDataFields.DEVICE_IDENTIFIER_KEY;
import static com.pcs.avocado.enums.EquipmentDataFields.DEVICE_IDENTIFIER_VALUE;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIPMENT;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_ENTITY_TEMPLATE;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_ENTITY_TEMPLATE_NAME;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_IDENTIFIER;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_IDENTIFIER_KEY;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_IDENTIFIER_VALUE;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_IDENTITY;
import static com.pcs.avocado.enums.EquipmentDataFields.FIELD_VALUES;
import static com.pcs.avocado.enums.EquipmentDataFields.IDENTITY;
import static com.pcs.avocado.enums.EquipmentDataFields.PLATFORM_ENTITY;
import static com.pcs.avocado.enums.EquipmentDataFields.PLATFORM_ENTITY_TYPE;
import static com.pcs.avocado.enums.EquipmentDataFields.POINTS;
import static com.pcs.avocado.enums.EquipmentDataFields.POINT_IDENTIFIER_KEY;
import static com.pcs.avocado.enums.EquipmentDataFields.POINT_IDENTIFIER_VALUE;
import static com.pcs.avocado.enums.EquipmentDataFields.SOURCE_ID;
import static com.pcs.avocado.enums.Status.ALLOCATED;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.commons.dto.AlarmDataResponse;
import com.pcs.avocado.commons.dto.AssetDTO;
import com.pcs.avocado.commons.dto.AssetResponseDTO;
import com.pcs.avocado.commons.dto.Data;
import com.pcs.avocado.commons.dto.Device;
import com.pcs.avocado.commons.dto.DeviceConfigTemplate;
import com.pcs.avocado.commons.dto.DeviceLocationParam;
import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.EntityTemplateDTO;
import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.GensetAlarmHistoryDTO;
import com.pcs.avocado.commons.dto.HierarchyDTO;
import com.pcs.avocado.commons.dto.HierarchyRelationDTO;
import com.pcs.avocado.commons.dto.HierarchySearchDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.LatestDataResultDTO;
import com.pcs.avocado.commons.dto.LatestDataSearchDTO;
import com.pcs.avocado.commons.dto.PlatformEntityDTO;
import com.pcs.avocado.commons.dto.Point;
import com.pcs.avocado.commons.dto.PointRelationship;
import com.pcs.avocado.commons.dto.ReturnFieldsDTO;
import com.pcs.avocado.commons.dto.SearchDTO;
import com.pcs.avocado.commons.dto.SearchFieldsDTO;
import com.pcs.avocado.commons.dto.StatusDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.dto.StatusMessageErrorDTO;
import com.pcs.avocado.commons.dto.Subscription;
import com.pcs.avocado.commons.dto.SubscriptionContextHolder;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.commons.service.SubscriptionProfileService;
import com.pcs.avocado.commons.util.ObjectBuilderUtil;
import com.pcs.avocado.commons.validation.ValidationUtils;
import com.pcs.avocado.constans.ResourceConstants;
import com.pcs.avocado.constans.WebConstants;
import com.pcs.avocado.enums.AssetDataFields;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.avocado.rest.exception.GalaxyRestException;
import com.pcs.avocado.services.AssetService;
import com.pcs.avocado.services.AssetTypeTemplateService;

/**
 * Service Implementation for Asset Related APIs
 * 
 * @author Greeshma (PCSEG323)
 * @date March 2016
 * @since Avocado-1.0.0
 */
@Service
public class AssetServiceImpl implements AssetService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AssetTypeTemplateService.class);

	@Autowired
	@Qualifier("avocadoEsbClient")
	private BaseClient esbClient;

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformClient;

	@Autowired
	@Qualifier("saffronEsbClient")
	private BaseClient saffronClient;

	@Value("${platform.esb.marker.list}")
	private String listMarkersPlatformEndpoint;

	@Value("${platform.esb.marker.search}")
	private String listMarkersByTypePlatformEndpoint;

	@Value("${platform.esb.hierarchy.children.immediate}")
	private String hierarchyChildrenEndpoint;

	@Value("${saffron.device.configurations}")
	private String deviceConfigurations;

	@Autowired
	private AssetTypeTemplateService assetTypeService;

	@Value("${platform.esb.marker}")
	private String markerPlatformEndpoint;

	@Value("${platform.esb.hierarchy.attach}")
	private String attachHierarchyPlatformEndpoint;

	@Value("${avocado.esb.points.create}")
	private String createPointsESBEndpoint;

	@Value("${platform.esb.hierarchy.children.immediate}")
	private String hierarchyImmediateChildrenPlatformEndpoint;

	@Value("${platform.esb.marker.find}")
	private String findMarkerPlatformEndpoint;

	@Value("${platform.esb.hierarchy.children.immediate}")
	private String hierarchyChildrenPlatformEndpoint;

	@Value("${platform.esb.hierarchy}")
	private String hierarchyPlatformEndpoint;

	@Value("${platform.esb.hierarchy.search.relation}")
	private String listAllAssetsAndItsType;

	@Value("${saffron.data.latest}")
	private String saffronDataLatest;

	@Value("${datasource.alarms.latest}")
	private String alarmsLiveDatasourceEndpoint;

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	/**
	 * Service method to get all the assets
	 * 
	 * @param templateName
	 *            ,domain,assetType
	 * @return {@link AssetDTO}
	 * 
	 */

	@Override
	public List<AssetDTO> getAllAssets(String domain, String assetType) {
		LOGGER.debug("--getAllAssets--");
		DomainDTO domainDTO = new DomainDTO();
		List<AssetDTO> assetDTOs = new ArrayList<AssetDTO>();
		if (StringUtils.isEmpty(domain)) {
			Subscription subscription = SubscriptionContextHolder.getContext()
			        .getSubscription();
			domain = subscription.getEndUserDomain();
		}
		domainDTO.setDomainName(domain);
		if (StringUtils.isEmpty(assetType)) {
			assetDTOs = getAllAssets(domainDTO);
		} else {
			assetDTOs = getAllAssetsByType(domainDTO, assetType);
		}
		return assetDTOs;
	}

	/**
	 * Service method to get all the assets based on assetType
	 * 
	 * @param templateName
	 *            ,domain,assetType
	 * @return {@link AssetDTO}
	 * 
	 */

	@SuppressWarnings("unchecked")
	private List<AssetDTO> getAllAssetsByType(DomainDTO domain, String assetType) {

		HierarchyRelationDTO hierarchyRelationDTO = new HierarchyRelationDTO();
		hierarchyRelationDTO.setParent(getEntity(domain.getDomainName(),
		        ASSET_TEMPLATE.getFieldName()));
		hierarchyRelationDTO.setChild(getEntity(domain.getDomainName(),
		        assetType));

		List<AssetDTO> assets = new ArrayList<AssetDTO>();
		List<HierarchyRelationDTO> entityDTOs = platformClient.post(
		        listAllAssetsAndItsType,
		        subscriptionProfileService.getJwtToken(), hierarchyRelationDTO,
		        List.class, HierarchyRelationDTO.class);
		for (HierarchyRelationDTO entity : entityDTOs) {
			AssetDTO assetDTO = new AssetDTO();
			for (FieldMapDTO fieldMapDTO : entity.getParent().getDataprovider()) {
				if (fieldMapDTO.getKey().equals(ASSET_NAME.getFieldName())) {
					assetDTO.setAssetName(fieldMapDTO.getValue());
					continue;
				}
				if (fieldMapDTO.getKey().equals(ASSET_TYPE.getFieldName())) {
					assetDTO.setAssetType(fieldMapDTO.getValue());
					continue;
				}
				if (fieldMapDTO.getKey().equals(DESCRIPTION.getFieldName())) {
					assetDTO.setDescription(fieldMapDTO.getValue());
					continue;
				}
				if (fieldMapDTO.getKey().equals(IDENTIFIER.getFieldName())) {
					FieldMapDTO assetFieldMapDTO = new FieldMapDTO();
					assetFieldMapDTO.setKey(IDENTIFIER.getFieldName());
					assetFieldMapDTO.setValue(fieldMapDTO.getValue());
					assetDTO.setAssetIdentifier(assetFieldMapDTO);
					continue;
				}
				if (fieldMapDTO.getKey().equals(ASSET_ID.getFieldName())) {
					assetDTO.setAssetId(fieldMapDTO.getValue());
					continue;
				}
				if (fieldMapDTO.getKey().equals(SERIAL_NUMBER.getFieldName())) {
					assetDTO.setSerialNumber(fieldMapDTO.getValue());
					continue;
				}
			}
			assetDTO.setAssetTypeValues(entity.getChild().getDataprovider());

			// Show on tree values for each Asset
			assetDTO.setTree(entity.getParent().getTree());
			assets.add(assetDTO);
		}
		return assets;
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

	/**
	 * Service method to get all the assets for all assetType
	 * 
	 * @param templateName
	 *            ,domain,assetType
	 * @return {@link AssetDTO}
	 * 
	 */
	@SuppressWarnings("unchecked")
	private List<AssetDTO> getAllAssets(DomainDTO domainDTO) {
		List<AssetDTO> assetDTOs = new ArrayList<AssetDTO>();
		EntityDTO entityDTO = new EntityDTO();
		entityDTO.setEntityTemplate(getAssetTemplate());
		entityDTO.setDomain(domainDTO);
		List<EntityDTO> entityDTOs = platformClient.post(
		        listMarkersPlatformEndpoint, getJwtToken(), entityDTO,
		        List.class, EntityDTO.class);

		if (CollectionUtils.isNotEmpty(entityDTOs)) {
			for (EntityDTO entityDTOResult : entityDTOs) {
				AssetDTO assetDTO = new AssetDTO();
				for (FieldMapDTO fieldMapDTO : entityDTOResult
				        .getDataprovider()) {
					if (fieldMapDTO.getKey().equals(ASSET_NAME.getFieldName())) {
						assetDTO.setAssetName(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(ASSET_TYPE.getFieldName())) {
						assetDTO.setAssetType(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(DESCRIPTION.getFieldName())) {
						assetDTO.setDescription(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(IDENTIFIER.getFieldName())) {
						FieldMapDTO assetFieldMapDTO = new FieldMapDTO();
						assetFieldMapDTO.setKey(IDENTIFIER.getFieldName());
						assetFieldMapDTO.setValue(fieldMapDTO.getValue());
						assetDTO.setAssetIdentifier(assetFieldMapDTO);
					}
					if (fieldMapDTO.getKey().equals(ASSET_ID.getFieldName())) {
						assetDTO.setAssetId(fieldMapDTO.getValue());
					}
					if (fieldMapDTO.getKey().equals(
					        SERIAL_NUMBER.getFieldName())) {
						assetDTO.setSerialNumber(fieldMapDTO.getValue());
					}
				}

				// Show on tree values for each Asset
				assetDTO.setTree(entityDTOResult.getTree());

				assetDTOs.add(assetDTO);
			}
		}
		return assetDTOs;

	}

	private EntityTemplateDTO getAssetTemplate() {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(ASSET_TEMPLATE.getFieldName());
		return entityTemplateDTO;
	}

	@Override
	public EntityDTO createAsset(AssetDTO assetDTO) {

		ValidationUtils.validateMandatoryFields(assetDTO, ASSET_TYPE,
		        ASSET_NAME);

		if (isBlank(assetDTO.getDomainName())) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			assetDTO.setDomainName(domainName);
		}

		StatusMessageDTO status = new StatusMessageDTO(Status.FAILURE);

		// validate assetType before creating asset entity
		try {

			String type = assetDTO.getParentType();
			if (StringUtils.isBlank(type)) {
				type = ASSET_TYPE_TEMPLATE;
			}
			assetTypeService.getAssetType(assetDTO.getAssetType(),
			        assetDTO.getDomainName(), type);
		} catch (GalaxyRestException gre) {
			if (gre.getMessage().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				LOGGER.debug("AssetType {} doesnot exist."
				        + assetDTO.getAssetType());
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        ASSET_TYPE.getDescription());
			}
		}

		// create payload for Asset save entity service
		EntityDTO assetEntity = null;
		EntityDTO assetEntityToBeCreated = createEntityDTOForUpdateSaveAssetEntity(
		        assetDTO, false);
		assetEntity = platformClient.post(markerPlatformEndpoint,
		        getJwtToken(), assetEntityToBeCreated, EntityDTO.class);
		if (assetEntity != null) {
			// create assetType entity
			EntityDTO assetTypeEntity = null;
			EntityDTO assetTypeEntityToBeCreated = createEntityDTOForSaveAssetTypeEntity(assetDTO);
			if (!CollectionUtils.isEmpty(assetTypeEntityToBeCreated
			        .getFieldValues())) {
				// if assetType fieldValues are present then only create the
				// assetType entity
				assetTypeEntity = assetTypeService
				        .createAssetType(assetTypeEntityToBeCreated);
			} else {
				status.setStatus(Status.SUCCESS);
			}
			if (assetTypeEntity != null) {
				// create hierarchy
				HierarchyDTO hierarchyDTO = new HierarchyDTO();
				hierarchyDTO.setActor(assetEntity);
				List<EntityDTO> subjects = new ArrayList<EntityDTO>();

				subjects.add(assetTypeEntity);
				hierarchyDTO.setSubjects(subjects);

				try {
					LOGGER.debug(objectBuilderUtil.getGson().toJson(
					        hierarchyDTO));
					status = platformClient.post(
					        attachHierarchyPlatformEndpoint, getJwtToken(),
					        hierarchyDTO, StatusMessageDTO.class);

				} catch (GalaxyRestException e) {
					LOGGER.error(
					        "Error in attaching assetTypeEntity to assetEntity",
					        e);
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.CUSTOM_ERROR,
					        "Error in attaching assetTypeEntity to assetEntity");
				}
			}
			if (status.getStatus().equals(Status.SUCCESS)) {
				return assetEntity;
			} else {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.PERSISTENCE_EXCEPTION);
			}
		} else {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.PERSISTENCE_EXCEPTION);
		}
	}

	private EntityDTO createEntityDTOForUpdateSaveAssetEntity(
	        AssetDTO assetDTO, Boolean isUpdate) {
		EntityDTO assetEntityToBeCreated = new EntityDTO();

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(assetDTO.getDomainName());
		assetEntityToBeCreated.setDomain(domain);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(AssetDataFields.ASSET_TEMPLATE
		        .getDescription());
		assetEntityToBeCreated.setEntityTemplate(entityTemplate);

		List<FieldMapDTO> fiedValues = new ArrayList<FieldMapDTO>();
		if (StringUtils.isNotBlank(assetDTO.getAssetName())) {
			fiedValues.add(new FieldMapDTO(AssetDataFields.ASSET_NAME
			        .getFieldName(), assetDTO.getAssetName()));
		}
		fiedValues.add(new FieldMapDTO(AssetDataFields.ASSET_TYPE
		        .getFieldName(), assetDTO.getAssetType()));
		if (StringUtils.isNotBlank(assetDTO.getDescription())) {
			fiedValues.add(new FieldMapDTO(AssetDataFields.DESCRIPTION
			        .getFieldName(), assetDTO.getDescription()));
		}/*
		 * else{ fiedValues.add(new FieldMapDTO(AssetDataFields.DESCRIPTION
		 * .getFieldName(), StringUtils.EMPTY)); }
		 */
		if (StringUtils.isNotBlank(assetDTO.getAssetId())) {
			fiedValues.add(new FieldMapDTO(AssetDataFields.ASSET_ID
			        .getFieldName(), assetDTO.getAssetId()));
		}/*
		 * else{ fiedValues.add(new FieldMapDTO(AssetDataFields.ASSET_ID
		 * .getFieldName(), StringUtils.EMPTY)); }
		 */
		if (StringUtils.isNotBlank(assetDTO.getSerialNumber())) {
			fiedValues.add(new FieldMapDTO(AssetDataFields.SERIAL_NUMBER
			        .getFieldName(), assetDTO.getSerialNumber()));
		}/*
		 * else{ fiedValues.add(new FieldMapDTO(AssetDataFields.SERIAL_NUMBER
		 * .getFieldName(), StringUtils.EMPTY)); }
		 */
		if (isUpdate) {
			// assetEntityToBeCreated.setIdentifier(assetDTO.getAssetIdentifier());
			fiedValues.add(new FieldMapDTO(AssetDataFields.IDENTIFIER
			        .getFieldName(), assetDTO.getAssetIdentifier().getValue()));
			assetEntityToBeCreated.setIdentifier(new FieldMapDTO(
			        AssetDataFields.IDENTIFIER.getFieldName(), assetDTO
			                .getAssetIdentifier().getValue()));
		}
		assetEntityToBeCreated.setFieldValues(fiedValues);
		return assetEntityToBeCreated;
	}

	private EntityDTO createEntityDTOForSaveAssetTypeEntity(AssetDTO assetDTO) {

		EntityDTO assetTypeEntityToBeCreated = new EntityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(assetDTO.getDomainName());
		assetTypeEntityToBeCreated.setDomain(domain);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(assetDTO.getAssetType());
		assetTypeEntityToBeCreated.setEntityTemplate(entityTemplate);
		List<FieldMapDTO> fiedValues = new ArrayList<FieldMapDTO>();

		// added to create Asset type even without fiedValues
		if (CollectionUtils.isNotEmpty(assetDTO.getAssetTypeValues())) {
			fiedValues = assetDTO.getAssetTypeValues();
		} else {
			fiedValues.add(new FieldMapDTO(AssetDataFields.IDENTIFIER
			        .getFieldName()));
		}
		assetTypeEntityToBeCreated.setFieldValues(fiedValues);

		return assetTypeEntityToBeCreated;
	}

	@Override
	public StatusMessageDTO createPointsAndRelationship(
	        PointRelationship pointRelationship) {
		// Validate input payload
		validateCreatePoint(pointRelationship);

		HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();

		// Set device entity type as MARKER
		PlatformEntityDTO markerEntity = new PlatformEntityDTO();
		markerEntity.setPlatformEntityType(MARKER);

		// Set domain
		setEquipDeviceDomain(pointRelationship);

		// Set device template name
		EntityTemplateDTO deviceTemplate = new EntityTemplateDTO();
		deviceTemplate.setEntityTemplateName(DEVICE_TEMPLATE);
		pointRelationship.getDevice().setEntityTemplate(deviceTemplate);

		List<EntityDTO> pointEntitiesFromAlpine = getDevicePoints(
		        pointRelationship, hierarchySearchDTO, markerEntity,
		        deviceTemplate);

		Map<String, String> validIdentities = new HashMap<>();
		for (EntityDTO entityDTO : pointEntitiesFromAlpine) {
			validIdentities.put(entityDTO.getIdentifier().getValue(), entityDTO
			        .getEntityStatus().getStatusName());
		}
		if (CollectionUtils.isEmpty(pointEntitiesFromAlpine)) {
			pointRelationship.setPointsExist(false);
		} else {
			pointRelationship.setPointsExist(true);
		}

		List<EntityDTO> pointList = new ArrayList<EntityDTO>();

		for (EntityDTO entityDTO : pointRelationship.getPoints()) {

			// String selected = FALSE;
			// if (entityDTO.getIsSelected() == null ||
			// !entityDTO.getIsSelected()) {
			// entityDTO.setIsSelected(false);
			// } else {
			// selected = TRUE;
			// entityDTO.setIsSelected(true);
			// }

			validateMandatoryFields(entityDTO, FIELD_VALUES);

			// If points exist ensure identifier is passed in input
			if (pointRelationship.getPointsExist()) {
				validateMandatoryFields(entityDTO.getIdentifier(),
				        POINT_IDENTIFIER_KEY, POINT_IDENTIFIER_VALUE);

				if (!validIdentities.containsKey(entityDTO.getIdentifier()
				        .getValue())) {
					throw new GalaxyException(INVALID_LIST_DATA_SPECIFIED,
					        POINTS.getDescription());
				}
				// Once attached points cannot be unselected
				String value = validIdentities.get(entityDTO.getIdentifier()
				        .getValue());
				if (!entityDTO.getIsSelected()
				        && value.equalsIgnoreCase(ALLOCATED.getStatus())) {
					throw new GalaxyException(POINTS_SELECTION);
				}
			}
			// Construct point entity payload for ESB
			EntityDTO pointEntity = updatePointEntity(entityDTO,
			        pointRelationship.getEquipment().getDomain());
			pointList.add(pointEntity);
		}
		// Set point entities
		pointRelationship.setPoints(pointList);
		pointRelationship.setPointTemplate(POINT_TEMPLATE);

		StatusMessageDTO messageDTO = esbClient.post(createPointsESBEndpoint,
		        getJwtToken(), pointRelationship, StatusMessageDTO.class);
		return messageDTO;
	}

	/**
	 * @param pointRelationship
	 */
	private void validateCreatePoint(PointRelationship pointRelationship) {
		// validate equipment identifier
		validateDeviceIdentifier(pointRelationship.getDevice());

		// validate device identifiers
		validateEquipIdentifier(pointRelationship.getEquipment());

		// validate points
		ValidationUtils.validateCollection(POINTS,
		        pointRelationship.getPoints());
	}

	/**
	 * @param pointRelationship
	 */
	private void setEquipDeviceDomain(PointRelationship pointRelationship) {
		DomainDTO contextDomain = new DomainDTO();
		contextDomain.setDomainName(getContext().getSubscription()
		        .getEndUserDomain());

		// If domain not passed fetch from context
		if (pointRelationship.getEquipment().getDomain() == null
		        || isBlank(pointRelationship.getEquipment().getDomain()
		                .getDomainName())) {
			pointRelationship.getEquipment().setDomain(contextDomain);
		}

		if (pointRelationship.getDevice().getDomain() == null
		        || isBlank(pointRelationship.getDevice().getDomain()
		                .getDomainName())) {
			pointRelationship.getDevice().setDomain(contextDomain);
		}
	}

	/**
	 * @param pointRelationship
	 * @param hierarchySearchDTO
	 * @param markerEntity
	 * @param deviceTemplate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<EntityDTO> getDevicePoints(
	        PointRelationship pointRelationship,
	        HierarchySearchDTO hierarchySearchDTO,
	        PlatformEntityDTO markerEntity, EntityTemplateDTO deviceTemplate) {
		IdentityDTO actor = new IdentityDTO();
		actor.setPlatformEntity(markerEntity);
		actor.setDomain(pointRelationship.getDevice().getDomain());

		actor.setEntityTemplate(deviceTemplate);
		actor.setIdentifier(pointRelationship.getDevice().getIdentifier());

		// construct hierarchy payload
		hierarchySearchDTO.setActor(actor);
		hierarchySearchDTO.setSearchEntityType(MARKER);
		hierarchySearchDTO.setSearchTemplateName(POINT_TEMPLATE);

		List<EntityDTO> pointEntitiesFromAlpine = new ArrayList<EntityDTO>();
		try {
			pointEntitiesFromAlpine = platformClient.post(
			        hierarchyImmediateChildrenPlatformEndpoint, getJwtToken(),
			        hierarchySearchDTO, List.class, EntityDTO.class);
			pointRelationship.setPointsExist(true);

		} catch (GalaxyRestException galaxyException) {
			LOGGER.info("No points found from alpine");
			if (galaxyException.getCode() == GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE
			        .getCode()) {
				pointRelationship.setPointsExist(false);
			}
		}
		return pointEntitiesFromAlpine;
	}

	private EntityDTO updatePointEntity(EntityDTO point, DomainDTO domain) {

		// If field values has isSelected, then status should be ALLOCATED
		// if (selected.equalsIgnoreCase(TRUE)) {
		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName(ALLOCATED.getStatus());
		point.setEntityStatus(entityStatus);
		// }
		// Domain passed is the domain of device
		point.setDomain(domain);
		// Set the template name
		EntityTemplateDTO pointTemplate = new EntityTemplateDTO();
		pointTemplate.setEntityTemplateName(POINT_TEMPLATE);
		point.setEntityTemplate(pointTemplate);

		return point;
	}

	private void validateDeviceIdentifier(IdentityDTO identityDTO) {
		validateMandatoryFields(identityDTO, DEVICE_IDENTIFIER);
		validateMandatoryFields(identityDTO.getIdentifier(),
		        DEVICE_IDENTIFIER_KEY, DEVICE_IDENTIFIER_VALUE);
	}

	private void validateEquipIdentifier(IdentityDTO identityDTO) {
		validateMandatoryFields(identityDTO, EQUIP_ENTITY_TEMPLATE,
		        EQUIP_IDENTIFIER);
		validateMandatoryFields(identityDTO.getIdentifier(),
		        EQUIP_IDENTIFIER_KEY, EQUIP_IDENTIFIER_VALUE);
		validateMandatoryFields(identityDTO.getEntityTemplate(),
		        EQUIP_ENTITY_TEMPLATE_NAME);
	}

	private void validateAssetIdentifier(IdentityDTO asset) {
		validateEquipIdentifier(asset);
		validateMandatoryFields(asset, PLATFORM_ENTITY);
		validateMandatoryFields(asset.getPlatformEntity(), PLATFORM_ENTITY_TYPE);
		if (asset.getDomain() == null
		        || isBlank(asset.getDomain().getDomainName())) {
			DomainDTO contextDomain = new DomainDTO();
			contextDomain.setDomainName(getContext().getSubscription()
			        .getEndUserDomain());
			asset.setDomain(contextDomain);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.avocado.services.EquipmentService#getEquipmentDetails(com.pcs
	 * .avocado.commons.dto.IdentityDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDTO> getAssetLatestData(IdentityDTO asset) {
		validateAssetIdentifier(asset);
		HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();
		hierarchySearchDTO.setActor(asset);
		hierarchySearchDTO.setSearchEntityType(MARKER);
		hierarchySearchDTO.setSearchTemplateName(POINT_TEMPLATE);
		HashMap<String, EntityDTO> returnPoints = new HashMap<>();

		List<LatestDataResultDTO> latestDataResults = null;
		try {
			List<EntityDTO> points = esbClient.post(
			        hierarchyImmediateChildrenPlatformEndpoint, getJwtToken(),
			        hierarchySearchDTO, List.class, EntityDTO.class);

			HashMap<String, List<String>> devicePoints = new HashMap<>();
			if (CollectionUtils.isNotEmpty(points)) {

				for (EntityDTO entityDTO : points) {

					if (CollectionUtils.isNotEmpty(entityDTO.getDataprovider())) {

						List<FieldMapDTO> fieldValues = entityDTO
						        .getDataprovider();

						FieldMapDTO dataSourceNameMap = new FieldMapDTO();
						dataSourceNameMap.setKey(DATASOURCE_NAME_FIELD);
						String dataSourceName = fetchFieldValue(fieldValues,
						        dataSourceNameMap);

						FieldMapDTO pointNameMap = new FieldMapDTO();
						pointNameMap.setKey(DISPLAY_NAME);
						String pointName = fetchFieldValue(fieldValues,
						        pointNameMap);

						if (StringUtils.isNotBlank(dataSourceName)
						        && StringUtils.isNotBlank(pointName)) {
							returnPoints.put(dataSourceName + pointName,
							        entityDTO);
							if (devicePoints.containsKey(dataSourceName)) {
								List<String> list = devicePoints
								        .get(dataSourceName);
								list.add(pointName);
							} else {
								List<String> list = new ArrayList<String>();
								list.add(pointName);
								devicePoints.put(dataSourceName, list);
							}
						}

					}

				}
			}
			List<LatestDataSearchDTO> latestDataSearches = new ArrayList<LatestDataSearchDTO>();
			for (String dataSourceName : devicePoints.keySet()) {
				LatestDataSearchDTO latestDataSearchDTO = new LatestDataSearchDTO();
				latestDataSearchDTO.setSourceId(dataSourceName);
				latestDataSearchDTO.setPoints(devicePoints.get(dataSourceName));
				latestDataSearches.add(latestDataSearchDTO);
			}

			latestDataResults = saffronClient.post(saffronDataLatest,
			        getJwtToken(), latestDataSearches, List.class,
			        LatestDataResultDTO.class);

			for (LatestDataResultDTO latestDataResultDTO : latestDataResults) {
				String dsName = latestDataResultDTO.getSourceId();
				for (Data data : latestDataResultDTO.getData()) {
					if (returnPoints
					        .containsKey(dsName + data.getDisplayName())) {
						EntityDTO point = returnPoints.get(dsName
						        + data.getDisplayName());
						List<FieldMapDTO> pointData = point.getDataprovider();
						FieldMapDTO deviceTime = new FieldMapDTO();
						deviceTime.setKey("deviceTime");
						deviceTime.setValue(data.getDeviceTime().toString());
						pointData.add(deviceTime);

						FieldMapDTO dataVal = new FieldMapDTO();
						dataVal.setKey("data");
						dataVal.setValue(data.getData());
						pointData.add(dataVal);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.info("No Points found for the Equipment");
		}
		validateResult(returnPoints.values());
		return new ArrayList<EntityDTO>(returnPoints.values());
	}

	private String fetchFieldValue(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public AssetResponseDTO getAssetDetails(IdentityDTO asset) {
		AssetResponseDTO assetResponse = null;
		// validate mandatory fields
		validateEquipIdentifier(asset);

		// Get assets details
		EntityDTO assetEntityDetails = platformClient.post(
		        findMarkerPlatformEndpoint, getJwtToken(), asset,
		        EntityDTO.class);
		assetResponse = new AssetResponseDTO();
		assetResponse.setAsset(assetEntityDetails);

		// Get details from fetched asset
		FieldMapDTO assetTypeMap = new FieldMapDTO();
		assetTypeMap.setKey(ASSET_TYPE.getFieldName());
		String assetTypeTemplate = fetchField(
		        assetEntityDetails.getFieldValues(), assetTypeMap);

		HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();

		// set actor
		IdentityDTO actor = new IdentityDTO();
		actor.setEntityTemplate(assetEntityDetails.getEntityTemplate());
		actor.setPlatformEntity(assetEntityDetails.getPlatformEntity());
		actor.setDomain(assetEntityDetails.getDomain());
		actor.setIdentifier(assetEntityDetails.getIdentifier());

		hierarchySearchDTO.setActor(actor);

		// set search template name
		hierarchySearchDTO.setSearchTemplateName(assetTypeTemplate);
		hierarchySearchDTO.setSearchEntityType(MARKER);

		// fetch the children of assets to get the asset type
		List<EntityDTO> assetTypes = platformClient.post(
		        hierarchyChildrenPlatformEndpoint, getJwtToken(),
		        hierarchySearchDTO, List.class, EntityDTO.class);

		IdentityDTO identityDTO = new IdentityDTO();
		identityDTO.setDomain(assetTypes.get(0).getDomain());
		identityDTO.setPlatformEntity(assetTypes.get(0).getPlatformEntity());
		identityDTO.setIdentifier(assetTypes.get(0).getIdentifier());
		identityDTO.setEntityTemplate(assetTypes.get(0).getEntityTemplate());
		EntityDTO assetEntityTypeDetails = platformClient.post(
		        findMarkerPlatformEndpoint, getJwtToken(), identityDTO,
		        EntityDTO.class);
		assetResponse.setAssetType(assetEntityTypeDetails);
		assetResponse.setPoints(getPoints(actor));
		return assetResponse;
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

	@SuppressWarnings("unchecked")
	private List<EntityDTO> getPoints(IdentityDTO identityDTO) {
		SearchFieldsDTO searchFields = new SearchFieldsDTO();

		// Set template name
		EntityTemplateDTO pointTemplate = new EntityTemplateDTO();
		pointTemplate.setEntityTemplateName(POINT_TEMPLATE);
		searchFields.setEntityTemplate(pointTemplate);

		// Set domain
		searchFields.setDomain(identityDTO.getDomain());

		// Add search fields
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
		FieldMapDTO equipIdMap = new FieldMapDTO();
		equipIdMap.setKey(EQUIP_IDENTITY.getFieldName());
		equipIdMap.setValue(identityDTO.getIdentifier().getValue());
		fields.add(equipIdMap);
		searchFields.setSearchFields(fields);

		// fetch the children of assets to get the asset type
		List<EntityDTO> assetTypes = new ArrayList<EntityDTO>();
		List<ReturnFieldsDTO> assetList = new ArrayList<ReturnFieldsDTO>();
		try {
			assetList = platformClient.post(listMarkersByTypePlatformEndpoint,
			        getJwtToken(), searchFields, List.class,
			        ReturnFieldsDTO.class);
		} catch (GalaxyRestException e) {
			LOGGER.info("No points found from alpine");
		}

		for (ReturnFieldsDTO returnFieldsDTO : assetList) {
			EntityDTO entityDTO = new EntityDTO();
			entityDTO.setDataprovider(returnFieldsDTO.getDataprovider());
			entityDTO.setDomain(returnFieldsDTO.getDomain());
			entityDTO.setIdentifier(returnFieldsDTO.getIdentifier());
			entityDTO.setPlatformEntity(returnFieldsDTO.getPlatformEntity());

			// Set status
			StatusDTO entityStatus = new StatusDTO();
			entityStatus.setStatusName(returnFieldsDTO.getEntityStatus()
			        .getStatusName());
			entityDTO.setEntityStatus(entityStatus);
			assetTypes.add(entityDTO);
		}
		return assetTypes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDTO> getPointsOfADevice(Device device) {

		validateMandatoryFields(device, IDENTITY, SOURCE_ID);

		IdentityDTO identityDTO = device.getIdentity();
		List<EntityDTO> points = new ArrayList<EntityDTO>();
		HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();
		hierarchySearchDTO.setActor(identityDTO);
		hierarchySearchDTO.setSearchEntityType(MARKER);
		hierarchySearchDTO.setSearchTemplateName(POINT_TEMPLATE);

		try {
			points = platformClient.post(
			        hierarchyImmediateChildrenPlatformEndpoint, getJwtToken(),
			        hierarchySearchDTO, List.class, EntityDTO.class);
		} catch (GalaxyRestException e) {
			LOGGER.info("No points found from alpine");
		}

		if (isEmpty(points)) {
			try {
				DeviceConfigTemplate deviceConfigTemplate = saffronClient.get(
				        deviceConfigurations.replace("{sourceId}",
				                device.getSourceId()), getJwtToken(),
				        DeviceConfigTemplate.class);
				validateResult(deviceConfigTemplate);
				/* List<Point> configPoints = */deviceConfigTemplate
				        .getConfigPoints();
				points = null;// getEntitiesFromPoints(configPoints);

			} catch (GalaxyRestException e) {
				LOGGER.info("No points found in saffron ");
			}
		}
		validateResult(points);
		return points;
	}

	/**
	 * Method to convert List<Point> to List<EntityDTO>
	 * 
	 * @param points
	 * @return
	 */
	private List<EntityDTO> getEntitiesFromPoints(List<Point> saffronPoints,
	        List<EntityDTO> alpinePoints) {
		List<EntityDTO> entities = new ArrayList<EntityDTO>();

		HashMap<String, EntityDTO> alpineMap = new HashMap<String, EntityDTO>();
		for (EntityDTO point : alpinePoints) {
			List<FieldMapDTO> fieldValues = point.getDataprovider();
			FieldMapDTO displayMap = new FieldMapDTO();
			displayMap.setKey(DISPLAY_NAME);
			String displayName = fetchFieldValue(fieldValues, displayMap);
			alpineMap.put(displayName, point);
		}

		for (Point point : saffronPoints) {

			if (!alpineMap.containsKey(point.getDisplayName())) {

				EntityDTO entityDTO = new EntityDTO();
				List<FieldMapDTO> dataprovider = new ArrayList<FieldMapDTO>();
				entityDTO.setDataprovider(dataprovider);
				if (isNotBlank(point.getPointId())) {
					dataprovider.add(new FieldMapDTO(POINT_ID, point
					        .getPointId()));
				}

				if (isNotBlank(point.getDataType())) {
					dataprovider.add(new FieldMapDTO(DATATYPE, point
					        .getDataType()));
				}

				if (isNotBlank(point.getDisplayName())) {
					dataprovider.add(new FieldMapDTO(DISPLAY_NAME, point
					        .getDisplayName()));
				}

				if (isNotBlank(point.getPointName())) {
					dataprovider.add(new FieldMapDTO(POINT_NAME, point
					        .getPointName()));
				}

				if (isNotBlank(point.getExpression())) {
					dataprovider.add(new FieldMapDTO(EXPRESSION, point
					        .getExpression()));
				}

				if (isNotBlank(point.getPhysicalQuantity())) {
					dataprovider.add(new FieldMapDTO(PHYSICAL_QUANTITY, point
					        .getPhysicalQuantity()));
				}

				if (isNotBlank(point.getPrecedence())) {
					dataprovider.add(new FieldMapDTO(PRECEDENCE, point
					        .getPrecedence()));
				}

				if (isNotBlank(point.getTags())) {
					dataprovider.add(new FieldMapDTO(TAGS, point.getTags()));
				}

				if (isNotBlank(point.getType())) {
					dataprovider.add(new FieldMapDTO(TYPE, point.getType()));
				}

				if (isNotBlank(point.getUnit())) {
					dataprovider.add(new FieldMapDTO(UNIT, point.getUnit()));
				}

				if (point.isSelected()) {
					dataprovider.add(new FieldMapDTO(IS_SELECTED, TRUE));
				} else {
					dataprovider.add(new FieldMapDTO(IS_SELECTED, FALSE));
				}
				entities.add(entityDTO);
			}
		}
		entities.addAll(alpinePoints);
		return entities;
	}

	@Override
	public StatusMessageDTO updateAsset(AssetDTO asset) {
		StatusMessageDTO assetUpdateStatusMessage = new StatusMessageDTO(
		        Status.FAILURE);

		if (isBlank(asset.getDomainName())) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			asset.setDomainName(domainName);
		}

		validateUpdatePayload(asset);

		// validate assetType before creating asset entity
		try {
			String type = asset.getParentType();
			if (StringUtils.isBlank(type)) {
				type = ASSET_TYPE_TEMPLATE;
			}
			assetTypeService.getAssetType(asset.getAssetType(),
			        asset.getDomainName(), type);
		} catch (GalaxyRestException gre) {
			if (gre.getMessage().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				LOGGER.debug("AssetType {} doesnot exist."
				        + asset.getAssetType());
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        ASSET_TYPE.getDescription());
			}
		}

		// update asset
		StatusMessageDTO assetUpdateStatusMessageDTO = updateAssetEntity(asset);
		LOGGER.debug("Response for asset update : {}",
		        assetUpdateStatusMessageDTO.toString());

		// if assetType fieldvalues are present in payload, then only update
		// assetType
		StatusMessageDTO assetTypeUpdateStatusMessageDTO = new StatusMessageDTO(
		        Status.FAILURE);
		if (CollectionUtils.isNotEmpty(asset.getAssetTypeValues())) {
			assetTypeUpdateStatusMessageDTO = updateAssetTypeEntity(asset);
		} else {
			assetTypeUpdateStatusMessageDTO.setStatus(Status.SUCCESS);
		}
		LOGGER.debug("Response for assetType update : {}",
		        assetTypeUpdateStatusMessageDTO.toString());
		if (!(assetUpdateStatusMessageDTO.getStatus().getStatus()
		        .equals(Status.SUCCESS.getStatus()) && assetTypeUpdateStatusMessageDTO
		        .getStatus().getStatus().equals(Status.SUCCESS.getStatus()))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.SPECIFIC_DATA_CANNOT_BE_UPDATED,
			        "Asset data");
		} else {
			assetUpdateStatusMessage = assetUpdateStatusMessageDTO;
		}
		return assetUpdateStatusMessage;
	}

	private StatusMessageDTO updateAssetEntity(AssetDTO asset) {
		List<FieldMapDTO> returnFieldsList = fetchExistingAsset(asset);

		FieldMapDTO assetTypeFM = new FieldMapDTO(ASSET_TYPE.getFieldName(),
		        asset.getAssetType());
		FieldMapDTO assetTypeReturnedFM = new FieldMapDTO(
		        ASSET_TYPE.getFieldName());
		if (!assetTypeFM.getValue().equals(
		        fetchField(returnFieldsList, assetTypeReturnedFM))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED,
			        ASSET_TYPE.getDescription());
		}

		// description is different, asset needs to be updated
		EntityDTO assetEntityToBeUpdate = createEntityDTOForUpdateSaveAssetEntity(
		        asset, true);
		// repla

		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName(Status.ACTIVE.name());
		assetEntityToBeUpdate.setEntityStatus(entityStatus);

		StatusMessageDTO statusMessageDTO = platformClient.put(
		        markerPlatformEndpoint, getJwtToken(), assetEntityToBeUpdate,
		        StatusMessageDTO.class);
		EntityDTO nodeEntityDTO = null;
		if (null != statusMessageDTO
		        && statusMessageDTO.getStatus().equals(Status.SUCCESS)) {

			try {
				assetEntityToBeUpdate.setFieldValues(null);
				PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
				platformEntityDTO.setPlatformEntityType(MARKER);
				assetEntityToBeUpdate.setPlatformEntity(platformEntityDTO);
				nodeEntityDTO = platformClient.put(hierarchyPlatformEndpoint,
				        getJwtToken(), assetEntityToBeUpdate, EntityDTO.class);
			} catch (GalaxyRestException gre) {
				if (gre.getMessage().equals(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
					LOGGER.debug("Asset Node cannot be updated");
					statusMessageDTO.setStatus(Status.FAILURE);
				}
			}
			if (null == nodeEntityDTO) {
				statusMessageDTO.setStatus(Status.FAILURE);
			}
		}

		return statusMessageDTO;

	}

	private StatusMessageDTO updateAssetTypeEntity(AssetDTO asset) {

		EntityDTO assetTypeEntityToBeUpdated = new EntityDTO();
		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(asset.getAssetType());
		assetTypeEntityToBeUpdated.setEntityTemplate(entityTemplate);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(asset.getDomainName());
		assetTypeEntityToBeUpdated.setDomain(domain);

		List<FieldMapDTO> assetTypeFieldValues = asset.getAssetTypeValues();
		assetTypeFieldValues.add(new FieldMapDTO(AssetDataFields.IDENTIFIER
		        .getFieldName(), asset.getAssetTypeIdentifier().getValue()));
		assetTypeEntityToBeUpdated.setFieldValues(assetTypeFieldValues);

		assetTypeEntityToBeUpdated.setIdentifier(new FieldMapDTO(
		        AssetDataFields.IDENTIFIER.getFieldName(), asset
		                .getAssetTypeIdentifier().getValue()));
		// TODO status
		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName(Status.ACTIVE.name());
		assetTypeEntityToBeUpdated.setEntityStatus(entityStatus);

		StatusMessageDTO assetTypeUpdateStatusMessageDTO = assetTypeService
		        .updateAssetType(assetTypeEntityToBeUpdated);

		return assetTypeUpdateStatusMessageDTO;
	}

	@SuppressWarnings("unchecked")
	private List<FieldMapDTO> fetchExistingAsset(AssetDTO assetDTO) {
		// fetch already existing asset by assetName and assetType

		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
		FieldMapDTO assetTypePayloadFM = new FieldMapDTO(
		        ASSET_TYPE.getFieldName(), assetDTO.getAssetType());

		FieldMapDTO identifierPayloadFM = new FieldMapDTO(
		        IDENTIFIER.getFieldName(), assetDTO.getAssetIdentifier()
		                .getValue());
		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		searchFields.add(assetTypePayloadFM);
		searchFields.add(identifierPayloadFM);

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(assetDTO.getDomainName());
		searchFieldsDTO.setDomain(domainDTO);

		searchFieldsDTO.setSearchFields(searchFields);
		searchFieldsDTO.setEntityTemplate(getAssetTemplate());

		List<ReturnFieldsDTO> returnedFields = platformClient.post(
		        listMarkersByTypePlatformEndpoint, getJwtToken(),
		        searchFieldsDTO, List.class, ReturnFieldsDTO.class);

		List<FieldMapDTO> returnFieldsList = null;
		if (CollectionUtils.isNotEmpty(returnedFields)) {
			ReturnFieldsDTO returnFieldsDTO = returnedFields.get(0);
			returnFieldsList = returnFieldsDTO.getDataprovider();
		}

		return returnFieldsList;
	}

	private void validateUpdatePayload(AssetDTO asset) {
		ValidationUtils.validateMandatoryFields(asset, ASSET_TYPE,
		        ASSET_IDENTIFIER, ASSET_TYPE_IDENTIFIER, ASSET_ID, ASSET_NAME);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDTO> getPointsOfADeviceTemp(Device device) {
		validateMandatoryFields(device, EQUIPMENT, SOURCE_ID);

		List<EntityDTO> alpinePoints = new ArrayList<EntityDTO>();
		List<EntityDTO> saffronPoints = new ArrayList<EntityDTO>();

		SearchFieldsDTO searchFields = new SearchFieldsDTO();
		searchFields = getSearchFieldsForPoints(device);

		try {
			alpinePoints = platformClient.post(
			        listMarkersByTypePlatformEndpoint, getJwtToken(),
			        searchFields, List.class, EntityDTO.class);
		} catch (GalaxyRestException e) {
			LOGGER.info("No points found from alpine");
		}
		List<Point> configPointsSaffron = new ArrayList<Point>();
		try {
			DeviceConfigTemplate deviceConfigTemplate = saffronClient.get(
			        deviceConfigurations.replace("{sourceId}",
			                device.getSourceId()), getJwtToken(),
			        DeviceConfigTemplate.class);
			validateResult(deviceConfigTemplate);
			configPointsSaffron = deviceConfigTemplate.getConfigPoints();

		} catch (GalaxyRestException e) {
			LOGGER.info("No points found in saffron ");
		}
		saffronPoints = getEntitiesFromPoints(configPointsSaffron, alpinePoints);
		validateResult(saffronPoints);
		return saffronPoints;
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// public List<EntityDTO> getPointsOfADeviceTemp(Device device) {
	// validateMandatoryFields(device, IDENTITY, SOURCE_ID);
	//
	// IdentityDTO identityDTO = device.getIdentity();
	// List<EntityDTO> alpinePoints = new ArrayList<EntityDTO>();
	// List<EntityDTO> saffronPoints = new ArrayList<EntityDTO>();
	// HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();
	// hierarchySearchDTO.setActor(identityDTO);
	// hierarchySearchDTO.setSearchEntityType(MARKER);
	// hierarchySearchDTO.setSearchTemplateName(POINT_TEMPLATE);
	//
	// try {
	// alpinePoints = platformClient.post(
	// hierarchyImmediateChildrenPlatformEndpoint, getJwtToken(),
	// hierarchySearchDTO, List.class, EntityDTO.class);
	// } catch (GalaxyRestException e) {
	// LOGGER.info("No points found from alpine");
	// }
	// List<Point> configPointsSaffron = new ArrayList<Point>();
	// try {
	// DeviceConfigTemplate deviceConfigTemplate = saffronClient.get(
	// deviceConfigurations.replace("{sourceId}",
	// device.getSourceId()), getJwtToken(),
	// DeviceConfigTemplate.class);
	// validateResult(deviceConfigTemplate);
	// configPointsSaffron = deviceConfigTemplate.getConfigPoints();
	//
	// } catch (GalaxyRestException e) {
	// LOGGER.info("No points found in saffron ");
	// }
	// saffronPoints = getEntitiesFromPoints(configPointsSaffron, alpinePoints);
	// validateResult(saffronPoints);
	// return saffronPoints;
	// }

	@Override
	public StatusMessageDTO createSelectedPointsAndRelationship(
	        PointRelationship pointRelationship) {
		// Validate input payload
		validateCreatePoint(pointRelationship);

		// Set device entity type as MARKER
		PlatformEntityDTO markerEntity = new PlatformEntityDTO();
		markerEntity.setPlatformEntityType(MARKER);

		// Set domain
		setEquipDeviceDomain(pointRelationship);

		// Set device template name
		EntityTemplateDTO deviceTemplate = new EntityTemplateDTO();
		deviceTemplate.setEntityTemplateName(DEVICE_TEMPLATE);
		pointRelationship.getDevice().setEntityTemplate(deviceTemplate);

		// Set point exist as false, points and relationships need to be created
		// always
		pointRelationship.setPointsExist(false);
		List<EntityDTO> pointList = new ArrayList<EntityDTO>();

		for (EntityDTO entityDTO : pointRelationship.getPoints()) {

			// String selected = FALSE;
			// if (entityDTO.getIsSelected() == null ||
			// !entityDTO.getIsSelected()) {
			// entityDTO.setIsSelected(false);
			// } else {
			// selected = TRUE;
			// entityDTO.setIsSelected(true);
			// }

			validateMandatoryFields(entityDTO, FIELD_VALUES);

			// Construct point entity payload for ESB
			// if (entityDTO.getIsSelected()) {
			EntityDTO pointEntity = updatePointEntity(entityDTO,
			        pointRelationship.getEquipment().getDomain());
			pointList.add(pointEntity);
			// }
		}
		// Set point entities
		pointRelationship.setPoints(pointList);
		pointRelationship.setPointTemplate(POINT_TEMPLATE);

		StatusMessageErrorDTO messageDTO = esbClient.post(
		        createPointsESBEndpoint, getJwtToken(), pointRelationship,
		        StatusMessageErrorDTO.class);
		if (messageDTO.getErrorMessage() != null
		        && messageDTO.getErrorMessage().equalsIgnoreCase(
		                ResourceConstants.POINTS_NOT_UNIQUE)) {

			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
			        POINTS.getDescription());
		}

		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(messageDTO.getStatus());
		return status;
	}

	private SearchFieldsDTO getSearchFieldsForPoints(Device device) {

		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();

		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(DATASOURCE_NAME_FIELD);
		fieldMapDTO.setValue(device.getSourceId());

		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		searchFields.add(fieldMapDTO);

		searchFieldsDTO.setDomain(device.getEquipment().getDomain());
		searchFieldsDTO.setSearchFields(searchFields);

		EntityTemplateDTO pointTemplate = new EntityTemplateDTO();
		pointTemplate.setEntityTemplateName(POINT_TEMPLATE);
		searchFieldsDTO.setEntityTemplate(pointTemplate);

		return searchFieldsDTO;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GensetAlarmHistoryDTO> getLiveAlarms(String domainName,
	        String assetName) {

		// If domain is blank, get the domain from logged in user
		if (StringUtils.isBlank(domainName)) {
			domainName = getContext().getSubscription().getEndUserDomain();
		}

		// fetch points of assetName
		SearchFieldsDTO searchFields = getSearchFieldsForPoints(assetName,
		        domainName);

		List<EntityDTO> pointEntityList = new ArrayList<EntityDTO>();
		try {
			pointEntityList = platformClient.post(
			        listMarkersByTypePlatformEndpoint, getJwtToken(),
			        searchFields, List.class, EntityDTO.class);
		} catch (GalaxyRestException e) {
			LOGGER.info("No points found from alpine");
		}
		Map<String, List<String>> equipMap = new HashMap<String, List<String>>();
		HashMap<String, DeviceLocationParam> deviceParamMap = new HashMap<String, DeviceLocationParam>();

		for (EntityDTO pointEntity : pointEntityList) {
			List<FieldMapDTO> dataprovider = pointEntity.getDataprovider();
			FieldMapDTO dataSourceFieldMap = new FieldMapDTO();
			FieldMapDTO displayNameFieldMap = new FieldMapDTO();
			FieldMapDTO sourceId = null;
			FieldMapDTO pointId = null;

			dataSourceFieldMap.setKey(WebConstants.DATASOURCE_NAME_FIELD);
			displayNameFieldMap.setKey(WebConstants.DISPLAY_NAME);

			// Extract the equip name
			FieldMapDTO equipNameMap = new FieldMapDTO();
			equipNameMap.setKey(EQUIP_NAME);
			equipNameMap.setValue(fetchField(dataprovider, equipNameMap));

			// Extract the equip template
			FieldMapDTO equipTemplateMap = new FieldMapDTO();
			equipTemplateMap.setKey(EQUIP_TEMPLATE);
			equipTemplateMap
			        .setValue(fetchField(dataprovider, equipTemplateMap));

			// Extract the equip Id
			FieldMapDTO equipIdMap = new FieldMapDTO();
			equipIdMap.setKey(WebConstants.EQUIP_IDENTIFIER);
			equipIdMap.setValue(fetchField(dataprovider, equipIdMap));

			List<String> pointList = null;

			sourceId = new FieldMapDTO(WebConstants.SOURCE_ID, fetchField(
			        dataprovider, dataSourceFieldMap));
			pointId = new FieldMapDTO(WebConstants.POINT_ID, fetchField(
			        dataprovider, displayNameFieldMap));
			if (isBlank(sourceId.getValue()) || isBlank(pointId.getValue())) {
				continue;
			}

			if (isNotBlank(sourceId.getValue())
			        && equipMap.containsKey(sourceId.getValue())) {
				pointList = new ArrayList<String>();
				pointList = equipMap.get(sourceId.getValue());
				pointList.add(pointId.getValue());

				equipMap.put(sourceId.getValue(), pointList);
			} else {

				pointList = new ArrayList<String>();
				pointList.add(pointId.getValue());

				// add new sourceId, then add new pointId
				equipMap.put(sourceId.getValue(), pointList);
			}
			// Construct deviceparam payload
			@SuppressWarnings("unused") DeviceLocationParam deviceParam = constructDeviceParamPayload(
			        deviceParamMap, sourceId, equipNameMap, equipTemplateMap,
			        equipIdMap);
		}

		if (equipMap.isEmpty()) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		List<SearchDTO> alarmSearchDTOs = new ArrayList<>();

		for (String sourceId : equipMap.keySet()) {
			SearchDTO alarmSearchDTO = new SearchDTO();
			alarmSearchDTO.setSourceId(sourceId);
			alarmSearchDTO.setDisplayNames(equipMap.get(sourceId));
			alarmSearchDTOs.add(alarmSearchDTO);
		}

		// call saffron alarm history service
		List<AlarmDataResponse> alarmLatestResponse = saffronClient.post(
		        alarmsLiveDatasourceEndpoint, getJwtToken(), alarmSearchDTOs,
		        List.class, AlarmDataResponse.class);

		List<GensetAlarmHistoryDTO> gensetLatestAlarmDTOs = getAssetAlarms(
		        deviceParamMap, alarmLatestResponse);
		return gensetLatestAlarmDTOs;
	}

	private List<GensetAlarmHistoryDTO> getAssetAlarms(
	        HashMap<String, DeviceLocationParam> deviceParamMap,
	        List<AlarmDataResponse> alarmHistoryResponse) {

		List<GensetAlarmHistoryDTO> gensetAlarmHistoryDTOs = new ArrayList<GensetAlarmHistoryDTO>();
		for (AlarmDataResponse alarmDataResponse : alarmHistoryResponse) {
			if (deviceParamMap.containsKey(alarmDataResponse.getSourceId())) {
				DeviceLocationParam deviceLocationParams = deviceParamMap
				        .get(alarmDataResponse.getSourceId());

				GensetAlarmHistoryDTO gensetAlarmHistoryDTO = new GensetAlarmHistoryDTO();
				gensetAlarmHistoryDTO.setSourceId(deviceLocationParams
				        .getDatasourceName());
				gensetAlarmHistoryDTO.setAlarmMessages(alarmDataResponse
				        .getAlarmMessages());
				gensetAlarmHistoryDTO.setEquipIdentifier(deviceLocationParams
				        .getEquipIdentifier());
				gensetAlarmHistoryDTO.setEquipName(deviceLocationParams
				        .getEquipName());
				gensetAlarmHistoryDTO.setEquipTemplate(deviceLocationParams
				        .getEquipTemplate());
				gensetAlarmHistoryDTOs.add(gensetAlarmHistoryDTO);
			}

		}

		return gensetAlarmHistoryDTOs;
	}

	private DeviceLocationParam constructDeviceParamPayload(
	        HashMap<String, DeviceLocationParam> deviceParamMap,
	        FieldMapDTO sourceId, FieldMapDTO equipNameMap,
	        FieldMapDTO equipTemplateMap, FieldMapDTO equipIdMap) {
		DeviceLocationParam deviceParam = new DeviceLocationParam();
		deviceParam.setDatasourceName(sourceId.getValue());
		deviceParam.setEquipIdentifier(equipIdMap);
		deviceParam.setEquipName(equipNameMap.getValue());
		deviceParam.setEquipTemplate(equipTemplateMap.getValue());
		deviceParamMap.put(sourceId.getValue(), deviceParam);
		return deviceParam;
	}

	private SearchFieldsDTO getSearchFieldsForPoints(String assetName,
	        String domainName) {

		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);
		searchFieldsDTO.setDomain(domainDTO);

		EntityTemplateDTO pointTemplate = new EntityTemplateDTO();
		pointTemplate.setEntityTemplateName(POINT_TEMPLATE);
		searchFieldsDTO.setEntityTemplate(pointTemplate);

		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(EQUIP_NAME);
		fieldMapDTO.setValue(assetName);
		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		searchFields.add(fieldMapDTO);

		searchFieldsDTO.setSearchFields(searchFields);

		return searchFieldsDTO;

	}
}
