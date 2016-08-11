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
import static com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes.SPECIFIC_DATA_CANNOT_BE_SAVED;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateResult;
import static com.pcs.avocado.constans.WebConstants.ACTIVE;
import static com.pcs.avocado.constans.WebConstants.COMMA;
import static com.pcs.avocado.constans.WebConstants.DATATYPE;
import static com.pcs.avocado.constans.WebConstants.DEVICE_TEMPLATE;
import static com.pcs.avocado.constans.WebConstants.DISPLAY_NAME;
import static com.pcs.avocado.constans.WebConstants.EXPRESSION;
import static com.pcs.avocado.constans.WebConstants.FALSE;
import static com.pcs.avocado.constans.WebConstants.IDENTIFIER;
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
import static com.pcs.avocado.enums.EquipmentDataFields.DEVICE_IDENTIFIER;
import static com.pcs.avocado.enums.EquipmentDataFields.DEVICE_IDENTIFIER_KEY;
import static com.pcs.avocado.enums.EquipmentDataFields.DEVICE_IDENTIFIER_VALUE;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIPMENT;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_ENTITY_TEMPLATE;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_ENTITY_TEMPLATE_NAME;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_ID;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_IDENTIFIER;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_IDENTIFIER_KEY;
import static com.pcs.avocado.enums.EquipmentDataFields.EQUIP_IDENTIFIER_VALUE;
import static com.pcs.avocado.enums.EquipmentDataFields.FACILITY;
import static com.pcs.avocado.enums.EquipmentDataFields.FACILITY_NAME;
import static com.pcs.avocado.enums.EquipmentDataFields.FIELD_VALUES;
import static com.pcs.avocado.enums.EquipmentDataFields.IDENTITY;
import static com.pcs.avocado.enums.EquipmentDataFields.POINTS;
import static com.pcs.avocado.enums.EquipmentDataFields.POINT_IDENTIFIER_KEY;
import static com.pcs.avocado.enums.EquipmentDataFields.POINT_IDENTIFIER_VALUE;
import static com.pcs.avocado.enums.EquipmentDataFields.SOURCE_ID;
import static com.pcs.avocado.enums.EquipmentDataFields.TENANT_NAME;
import static com.pcs.avocado.enums.Status.ALLOCATED;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

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

import com.pcs.avocado.commons.dto.Device;
import com.pcs.avocado.commons.dto.DeviceConfigTemplate;
import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.EntityTemplateDTO;
import com.pcs.avocado.commons.dto.EquipmentDTO;
import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.PlatformEntityDTO;
import com.pcs.avocado.commons.dto.HierarchyDTO;
import com.pcs.avocado.commons.dto.HierarchySearchDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.Point;
import com.pcs.avocado.commons.dto.PointRelationship;
import com.pcs.avocado.commons.dto.SkySparkPayload;
import com.pcs.avocado.commons.dto.SkySparkPoint;
import com.pcs.avocado.commons.dto.SkySparkPointCreate;
import com.pcs.avocado.commons.dto.StatusDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.commons.validation.ValidationUtils;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.avocado.rest.exception.GalaxyRestException;
import com.pcs.avocado.services.EquipmentService;

/**
 * Service Implementation for Air Handler Related APIs
 * 
 * @author pcseg199 (Javid Ahammed)
 * @author PCSEG191 (Daniela)
 * @date January 2016
 * @since Avocado-1.0.0
 */
@Service
public class AirHandlerServiceImpl implements EquipmentService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EquipmentService.class);

	@Autowired
	@Qualifier("avocadoEsbClient")
	private BaseClient avocadoESBClient;

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformESBClient;

	@Autowired
	@Qualifier("saffronClient")
	private BaseClient saffronClient;

	@Autowired
	@Qualifier("skySparkClient")
	private BaseClient skySparkESBClient;

	@Value("${platform.esb.marker}")
	private String markerPlatformEndpoint;

	@Value("${platform.esb.hierarchy.attach}")
	private String attachHierarchyPlatformEndpoint;

	@Value("${platform.esb.marker.find}")
	private String findMarkerPlatformEndpoint;

	@Value("${platform.esb.hierarchy.parents.immediate}")
	private String hierarchyParentPlatformEndpoint;

	@Value("${platform.esb.hierarchy.children}")
	private String hierarchyChildrenPlatformEndpoint;

	@Value("${saffron.device.configurations}")
	private String deviceConfigurations;

	@Value("${platform.esb.hierarchy.children.immediate}")
	private String hierarchyImmediateChildrenPlatformEndpoint;

	@Value("${avocado.esb.points.create}")
	private String createPointsESBEndpoint;

	@Value("${skySpark.equipment.create}")
	private String equipmentSkySparkEndpoint;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.avocado.services.EquipmentService#createEquipment(com.pcs.avocado
	 * .commons.dto.EquipmentDTO)
	 */
	@Override
	public EntityDTO createEquipment(EquipmentDTO equipmentDTO) {

		validateMandatoryFields(equipmentDTO, EQUIPMENT, FACILITY, FIELD_VALUES);

		EntityDTO equipmentEntity = null;

		equipmentEntity = platformESBClient.post(
				markerPlatformEndpoint,
				getJwtToken(),
				createAlpineEquipment(equipmentDTO, equipmentDTO.getFacility()
						.getDomain()), EntityDTO.class);

		if (equipmentEntity != null) {
			// Create hierarchy
			IdentityDTO facility = equipmentDTO.getFacility();
			HierarchyDTO hierarchyDTO = new HierarchyDTO();
			hierarchyDTO.setActor(getEntityFromIdentity(facility));
			List<EntityDTO> subjects = new ArrayList<EntityDTO>();

			subjects.add(equipmentEntity);
			hierarchyDTO.setSubjects(subjects);

			try {
				platformESBClient.post(attachHierarchyPlatformEndpoint,
						getJwtToken(), hierarchyDTO, StatusMessageDTO.class);
			} catch (GalaxyRestException e) {
				LOGGER.error("Error in attaching equipment to facility", e);
				throw new GalaxyException(GalaxyCommonErrorCodes.CUSTOM_ERROR,
						"Error in attaching equipment to facility");
			}

		}

		// Invoking SkySpark Endpoint
		if (!isEmpty(equipmentDTO.getSkySparkEquipmentTags())) {
			Map<?, ?> post = skySparkESBClient.post(equipmentSkySparkEndpoint,
					getJwtToken(), equipmentDTO.getSkySparkEquipmentTags(),
					Map.class);
			if (post == null || post.get("id") == null) {
				LOGGER.error("Error in creating equipment in SkySpark");
				throw new GalaxyException(SPECIFIC_DATA_CANNOT_BE_SAVED,
						"SkySpark data");
			}
		}
		return equipmentEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.avocado.services.EquipmentService#updateEquipment(com.pcs.avocado
	 * .commons.dto.EquipmentDTO)
	 */
	@Override
	public StatusMessageDTO updateEquipment(EquipmentDTO equipmentDTO) {
		validateMandatoryFields(equipmentDTO, EQUIPMENT, FIELD_VALUES);

		StatusMessageDTO statusMessageDTO = null;

		DomainDTO domain = equipmentDTO.getEquipment().getDomain();
		EntityDTO alpineEquipment = createAlpineEquipment(equipmentDTO, domain);

		statusMessageDTO = platformESBClient.put(markerPlatformEndpoint,
				getJwtToken(), alpineEquipment, StatusMessageDTO.class);

		Map<String, Object> skySparkEquipmentTags = equipmentDTO
				.getSkySparkEquipmentTags();
		List<SkySparkPoint> pointsList = equipmentDTO.getSkySparkPointTags();

		List<Map<String, Object>> pointsToSkySpark = new ArrayList<Map<String, Object>>();
		skySparkEquipmentTags.put(POINTS.getVariableName(), pointsToSkySpark);

		if (isNotEmpty(pointsList)) {
			for (SkySparkPoint skySparkPoint : pointsList) {

				try {

					String identifierValue = skySparkPoint.getIdentifier();
					String pointId = skySparkPoint.getPointId();
					String tags = skySparkPoint.getTags();
					if (isEmpty(identifierValue) || isEmpty(pointId)) {
						LOGGER.debug("Discarding point ");
						continue;
					}
					IdentityDTO pointIdentity = getPointIdentity(
							identifierValue, domain);

					EntityDTO pointEntity = platformESBClient.post(
							findMarkerPlatformEndpoint, getJwtToken(),
							pointIdentity, EntityDTO.class);
					if (pointEntity != null) {
						List<FieldMapDTO> fieldValues = pointEntity
								.getFieldValues();
						FieldMapDTO tagField = new FieldMapDTO(TAGS);

						int indexOfTag = fieldValues.indexOf(tagField);
						tagField.setValue(tags);
						if (indexOfTag > -1) {
							fieldValues.add(indexOfTag, tagField);
						} else {
							fieldValues.add(tagField);
						}
						statusMessageDTO = platformESBClient.put(
								markerPlatformEndpoint, getJwtToken(),
								pointEntity, StatusMessageDTO.class);

						Map<String, Object> pointSkySparkMap = new HashMap<String, Object>();
						pointSkySparkMap.put(POINT_ID, pointId);
						if (StringUtils.isNotEmpty(tags)) {
							String[] tagsArray = tags.split(COMMA);
							if (tagsArray.length > 0) {
								for (String tag : tagsArray) {
									pointSkySparkMap.put(tag.trim(), true);
								}
							}
						}
						pointsToSkySpark.add(pointSkySparkMap);
					} else {
						LOGGER.debug("Discarding point Update with pointId:{}",
								pointId);
					}
				} catch (Exception e) {
					LOGGER.error(
							"Error in Updating point Tag in alpine,will discard and continue to next point",
							e);
				}
			}
		}

		if (statusMessageDTO != null && skySparkEquipmentTags != null) {
			Map<?, ?> post = skySparkESBClient.put(equipmentSkySparkEndpoint,
					getJwtToken(), skySparkEquipmentTags, Map.class);
			if (post == null || post.get("status").equals("FAILURE")) {
				LOGGER.error("Error in updating equipment in SkySpark");
				throw new GalaxyException(SPECIFIC_DATA_CANNOT_BE_SAVED,
						"SkySpark data");
			}
			LOGGER.debug("Response from SkySpark : {}", post.toString());
		}

		return statusMessageDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.avocado.services.EquipmentService#getEquipmentDetails(com.pcs
	 * .avocado.commons.dto.IdentityDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EquipmentDTO getEquipmentDetails(IdentityDTO identityDTO) {

		EntityDTO equipmentEntity = platformESBClient.post(
				findMarkerPlatformEndpoint, getJwtToken(), identityDTO,
				EntityDTO.class);

		validateResult(equipmentEntity);

		EquipmentDTO equipmentDTO = new EquipmentDTO();
		equipmentDTO.setEquipment(identityDTO);
		equipmentDTO.setFieldValues(equipmentEntity.getFieldValues());
		HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();
		hierarchySearchDTO.setActor(identityDTO);
		hierarchySearchDTO.setSearchEntityType(MARKER);
		hierarchySearchDTO.setSearchTemplateName(POINT_TEMPLATE);

		try {
			List<EntityDTO> points = platformESBClient.post(
					hierarchyImmediateChildrenPlatformEndpoint, getJwtToken(),
					hierarchySearchDTO, List.class, EntityDTO.class);

			if (isNotEmpty(points)) {
				equipmentDTO.setPoints(points);
			}
		} catch (GalaxyRestException e) {
			LOGGER.info("No Points found for the Equipment");
		}
		return equipmentDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.avocado.services.EquipmentService#getPointsOfADevice(com.pcs.
	 * avocado.commons.dto.Device)
	 */
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

		// Try to fetch points of a device from alpine
		try {
			points = platformESBClient.post(
					hierarchyImmediateChildrenPlatformEndpoint, getJwtToken(),
					hierarchySearchDTO, List.class, EntityDTO.class);

		} catch (GalaxyRestException e) {
			LOGGER.info("No points found from alpine");
		}

		// Try Saffron If no points are found in alpine
		if (isEmpty(points)) {
			try {
				DeviceConfigTemplate deviceConfigTemplate = saffronClient.get(
						deviceConfigurations.replace("{sourceId}",
								device.getSourceId()), getJwtToken(),
								DeviceConfigTemplate.class);
				validateResult(deviceConfigTemplate);
				List<Point> configPoints = deviceConfigTemplate
						.getConfigPoints();
				points = getEntitiesFromPoints(configPoints);

			} catch (GalaxyRestException e) {
				LOGGER.info("No points found in saffron ", e);
			}
		}
		validateResult(points);
		return points;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.avocado.services.EquipmentService#createPointsAndRelationship
	 * (com.pcs.avocado.commons.dto.PointRelationship)
	 */
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

		// validate if point exists
		SkySparkPayload skysparkPoint = new SkySparkPayload();
		List<SkySparkPointCreate> skysparkPointList = new ArrayList<SkySparkPointCreate>();
		List<EntityDTO> pointList = new ArrayList<EntityDTO>();

		// Set facility and equipment Id
		skysparkPoint.setFacilityName(pointRelationship.getFacilityName());
		skysparkPoint.setEquipmentId(pointRelationship.getEquipmentId());
		skysparkPoint.setClient(pointRelationship.getTenantName());

		for (EntityDTO entityDTO : pointRelationship.getPoints()) {

			String selected = FALSE;
			if (entityDTO.getIsSelected() == null || !entityDTO.getIsSelected()) {
				entityDTO.setIsSelected(false);
			} else {
				selected = TRUE;
				entityDTO.setIsSelected(true);
			}

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
				String value = (String) validIdentities.get(entityDTO
						.getIdentifier().getValue());
				if (!entityDTO.getIsSelected()
						&& value.equalsIgnoreCase(ALLOCATED.getStatus())) {
					throw new GalaxyException(POINTS_SELECTION);
				}
			}

			// Construct point entity payload for ESB
			EntityDTO pointEntity = updatePointEntity(entityDTO, selected,
					pointRelationship.getEquipment().getDomain());
			pointList.add(pointEntity);

			// Construct point for skyspark
			SkySparkPointCreate point = constructSkysparkPoint(entityDTO,
					selected);
			if (point != null) {
				skysparkPointList.add(point);
			}
		}
		// Set point entities
		pointRelationship.setPoints(pointList);
		pointRelationship.setPointTemplate(POINT_TEMPLATE);
		if (isNotEmpty(skysparkPointList)) {
			skysparkPoint.setPoints(skysparkPointList);
			pointRelationship.setSkysparkPayload(skysparkPoint);
		}

		StatusMessageDTO messageDTO = avocadoESBClient.post(
				createPointsESBEndpoint, getJwtToken(), pointRelationship,
				StatusMessageDTO.class);
		return messageDTO;
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
			pointEntitiesFromAlpine = platformESBClient.post(
					hierarchyImmediateChildrenPlatformEndpoint, getJwtToken(),
					hierarchySearchDTO, List.class, EntityDTO.class);
			pointRelationship.setPointsExist(true);

		} catch (GalaxyRestException galaxyException) {
			LOGGER.info("No points found from alpine");
			if (galaxyException.getCode() == 500) {
				pointRelationship.setPointsExist(false);
			}
		}
		return pointEntitiesFromAlpine;
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
	 * Method to convert List<Point> to List<EntityDTO>
	 * 
	 * @param points
	 * @return
	 */
	private List<EntityDTO> getEntitiesFromPoints(List<Point> points) {
		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		for (Point point : points) {

			EntityDTO entityDTO = new EntityDTO();
			List<FieldMapDTO> dataprovider = new ArrayList<FieldMapDTO>();
			entityDTO.setDataprovider(dataprovider);
			if (isNotBlank(point.getPointId())) {
				dataprovider.add(new FieldMapDTO(POINT_ID, point.getPointId()));
			}

			if (isNotBlank(point.getDataType())) {
				dataprovider
				.add(new FieldMapDTO(DATATYPE, point.getDataType()));
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
		return entities;
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

		validateMandatoryFields(pointRelationship, EQUIP_ID, FACILITY_NAME,
				TENANT_NAME);
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

	private EntityDTO updatePointEntity(EntityDTO point, String selected,
			DomainDTO domain) {

		// If field values has isSelected, then status should be ALLOCATED
		if (selected.equalsIgnoreCase(TRUE)) {
			StatusDTO entityStatus = new StatusDTO();
			entityStatus.setStatusName(ALLOCATED.getStatus());
			point.setEntityStatus(entityStatus);
		}
		// Domain passed is the domain of device
		point.setDomain(domain);
		// Set the template name
		EntityTemplateDTO pointTemplate = new EntityTemplateDTO();
		pointTemplate.setEntityTemplateName(POINT_TEMPLATE);
		point.setEntityTemplate(pointTemplate);

		return point;
	}

	private SkySparkPointCreate constructSkysparkPoint(EntityDTO pointDto,
			String selected) {
		// Set the point id
		SkySparkPointCreate point = null;
		// Construct payload using only the selected points
		if (selected.equalsIgnoreCase(TRUE)) {
			// Set the point id
			point = new SkySparkPointCreate();
			FieldMapDTO pointIdMap = new FieldMapDTO();
			pointIdMap.setKey(POINT_ID);
			point.setPointId(fetchField(pointDto.getFieldValues(), pointIdMap));

			// Set the point name
			FieldMapDTO pointNameMap = new FieldMapDTO();
			pointNameMap.setKey(POINT_NAME);
			point.setPointName(fetchField(pointDto.getFieldValues(),
					pointNameMap));

			// Set the dataType
			FieldMapDTO dataTypeMap = new FieldMapDTO();
			dataTypeMap.setKey(DATATYPE);
			point.setDataType(fetchField(pointDto.getFieldValues(), dataTypeMap));

			// Set the unit
			FieldMapDTO unitMap = new FieldMapDTO();
			unitMap.setKey(UNIT);
			point.setUnit(fetchField(pointDto.getFieldValues(), unitMap));

			// Set the displayName
			FieldMapDTO displayNameMap = new FieldMapDTO();
			displayNameMap.setKey(DISPLAY_NAME);
			point.setDisplayName(fetchField(pointDto.getFieldValues(), displayNameMap));
		}
		return point;
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

	private EntityDTO getEntityFromIdentity(IdentityDTO facility) {
		EntityDTO facilityEntity = new EntityDTO();
		facilityEntity.setDomain(facility.getDomain());
		facilityEntity.setEntityTemplate(facility.getEntityTemplate());
		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType(MARKER);
		facilityEntity.setPlatformEntity(platformEntity);
		facilityEntity.setIdentifier(facility.getIdentifier());
		return facilityEntity;
	}

	private EntityDTO createAlpineEquipment(EquipmentDTO equipmentDTO,
			DomainDTO domain) {
		EntityDTO alpineEquip = new EntityDTO();
		alpineEquip.setFieldValues(equipmentDTO.getFieldValues());
		IdentityDTO equipmentIdentity = equipmentDTO.getEquipment();
		alpineEquip.setEntityTemplate(equipmentIdentity.getEntityTemplate());
		alpineEquip.setDomain(domain);
		alpineEquip.setIdentifier(equipmentIdentity.getIdentifier());
		StatusDTO status = new StatusDTO();
		status.setStatusName(ACTIVE);
		alpineEquip.setEntityStatus(status);
		return alpineEquip;
	}

	private IdentityDTO getPointIdentity(String identifierValue,
			DomainDTO domain) {
		IdentityDTO identityDTO = new IdentityDTO();

		identityDTO.setIdentifier(new FieldMapDTO(IDENTIFIER, identifierValue));

		identityDTO.setDomain(domain);
		EntityTemplateDTO pointTemplate = new EntityTemplateDTO();
		pointTemplate.setEntityTemplateName(POINT_TEMPLATE);
		identityDTO.setEntityTemplate(pointTemplate);

		return identityDTO;
	}
}
