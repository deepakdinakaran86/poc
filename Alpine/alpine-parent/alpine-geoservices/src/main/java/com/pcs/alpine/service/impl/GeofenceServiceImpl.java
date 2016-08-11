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

import static com.pcs.alpine.constants.GeofenceConstant.GEOFENCE_TEMPLATE;
import static com.pcs.alpine.enums.GeoDataFields.CIRCLE;
import static com.pcs.alpine.enums.GeoDataFields.CO_ORDINATES;
import static com.pcs.alpine.enums.GeoDataFields.DESC;
import static com.pcs.alpine.enums.GeoDataFields.GEOFENCE_FIELDS;
import static com.pcs.alpine.enums.GeoDataFields.GEOFENCE_NAME;
import static com.pcs.alpine.enums.GeoDataFields.GEOFENCE_TYPE;
import static com.pcs.alpine.enums.GeoDataFields.POLYGON;
import static com.pcs.alpine.enums.GeoDataFields.ROUTE;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER_KEY;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER_VALUE;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.dto.StatusMessageErrorDTO;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.dto.CoordinatesDTO;
import com.pcs.alpine.dto.GeofenceDTO;
import com.pcs.alpine.dto.GeofenceESBDTO;
import com.pcs.alpine.enums.GeoErrorCodes;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.service.GeofenceService;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.StatusDTO;

/**
 * 
 * @description This class is responsible for the GeoServiceImpl
 * 
 * @author Daniela (PCSEG191)
 * @author Suraj Sugathan (PCSEG362)
 * @date 10 Mar 2016
 * @since galaxy-1.0.0
 */
@Service
public class GeofenceServiceImpl implements GeofenceService {

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformClient;

	@Value("${platform.esb.geofence}")
	private String geofencePlatformEndpoint;

	@Value("${platform.esb.geofence.delete}")
	private String geofencePlatformDeleteEndpoint;

	@Value("${platform.esb.geofence.type.find}")
	private String findGeofenceTypeEndpoint;

	@Value("${platform.esb.marker.list}")
	private String listMarkerEndpoint;

	@Autowired
	SubscriptionProfileService subscriptionProfileService;

	@Override
	public StatusMessageDTO createGeofence(GeofenceDTO geofence) {
		// Validate fields
		validateFields(geofence);
		Boolean isUpdate = false;

		EntityDTO entity = platformClient.post(geofencePlatformEndpoint,
		        subscriptionProfileService.getJwtToken(),
		        constructGeofenceESBPayload(geofence, isUpdate),
		        EntityDTO.class);
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		if (entity != null) {
			status.setStatus(Status.SUCCESS);
		}
		return status;
	}

	@Override
	public StatusMessageDTO updateGeofence(GeofenceDTO geofence) {
		// Validate fields
		validateFields(geofence);

		// Validate identifier
		ValidationUtils.validateMandatoryFields(geofence.getIdentifier(),
		        IDENTIFIER_KEY, IDENTIFIER_VALUE);

		Boolean isUpdate = true;

		StatusMessageErrorDTO status = platformClient.put(
		        geofencePlatformEndpoint,
		        subscriptionProfileService.getJwtToken(),
		        constructGeofenceESBPayload(geofence, isUpdate),
		        StatusMessageErrorDTO.class);
		if (isNotBlank(status.getErrorCode())
		        && status.getErrorCode().equalsIgnoreCase(
		                GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED
		                        .getCode().toString())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED,
			        status.getField());
		}

		// Set status as success
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public GeofenceDTO findGeofence(IdentityDTO geofence) {
		validateIdentifierFields(geofence);

		// Set geofence template
		setGeofenceTemplate(geofence);

		GeofenceDTO geofenceDto = platformClient.post(findGeofenceTypeEndpoint,
		        subscriptionProfileService.getJwtToken(), geofence,
		        GeofenceDTO.class);

		return geofenceDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDTO> findAllGeofences(String domainName) {

		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getSubscription()
			        .getEndUserDomain();
		}
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);

		IdentityDTO geofenceIdentityDto = new IdentityDTO();
		geofenceIdentityDto.setDomain(domain);

		// Set geofence template
		setGeofenceTemplate(geofenceIdentityDto);

		List<EntityDTO> entityDto = platformClient.post(listMarkerEndpoint,
		        subscriptionProfileService.getJwtToken(), geofenceIdentityDto,
		        List.class, EntityDTO.class);

		return entityDto;
	}

	@Override
	public StatusMessageDTO deleteGeofence(IdentityDTO geofence) {
		// validate identifiers
		validateIdentifierFields(geofence);

		// Set geofence template
		setGeofenceTemplate(geofence);

		EntityDTO entity = platformClient.post(geofencePlatformDeleteEndpoint,
		        subscriptionProfileService.getJwtToken(), geofence,
		        EntityDTO.class);
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		if (entity != null) {
			status.setStatus(Status.SUCCESS);
		}
		return status;
	}

	private void validateFields(GeofenceDTO geofenceDTO) {
		// validate the mandatory fields
		ValidationUtils.validateMandatoryFields(geofenceDTO, GEOFENCE_NAME,
		        GEOFENCE_TYPE, GEOFENCE_FIELDS);

		// validate the size of fieldValues
		ValidationUtils.validateCollection(GEOFENCE_FIELDS,
		        geofenceDTO.getGeofenceFields());

		// Validate that the geofencetype is Circle, Polygon or Route
		String type = geofenceDTO.getType();
		if (!CIRCLE.getFieldName().equals(type)
		        && !POLYGON.getFieldName().equals(type)
		        && !ROUTE.getFieldName().equals(type)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        GEOFENCE_TYPE.getDescription());
		}

		if (geofenceDTO.getDomain() == null
		        || isBlank(geofenceDTO.getDomain().getDomainName())) {
			DomainDTO domainDTO = new DomainDTO();
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
			        .getSubscription().getEndUserDomain());
			geofenceDTO.setDomain(domainDTO);
		}
	}

	private void validateIdentifierFields(IdentityDTO identityDto) {
		ValidationUtils.validateMandatoryFields(identityDto, IDENTIFIER);

		ValidationUtils.validateMandatoryFields(identityDto.getIdentifier(),
		        IDENTIFIER_KEY, IDENTIFIER_VALUE);

		if (identityDto.getDomain() == null
		        || isBlank(identityDto.getDomain().getDomainName())) {
			DomainDTO domainDTO = new DomainDTO();
			// Get logged in user's domain
			domainDTO.setDomainName(subscriptionProfileService
			        .getSubscription().getEndUserDomain());
		}
	}

	private GeofenceESBDTO constructGeofenceESBPayload(GeofenceDTO geofenceDTO,
	        Boolean isUpdate) {
		GeofenceESBDTO geofenceESB = new GeofenceESBDTO();

		// Set geofence entity
		EntityDTO geofenceEntity = new EntityDTO();
		geofenceEntity.setDomain(geofenceDTO.getDomain());
		EntityTemplateDTO geofenceTemplate = new EntityTemplateDTO();
		geofenceTemplate.setEntityTemplateName(GEOFENCE_TEMPLATE);
		geofenceEntity.setEntityTemplate(geofenceTemplate);
		geofenceEntity.setFieldValues(constuctGeofenceFields(geofenceDTO));

		if (geofenceDTO.getGeofenceStatus() != null
		        && isNotBlank(geofenceDTO.getGeofenceStatus().getStatusName())) {
			geofenceEntity.setEntityStatus(geofenceDTO.getGeofenceStatus());
		} else {
			if (!isUpdate) {
				StatusDTO activeStatus = new StatusDTO();
				activeStatus.setStatusName(Status.ACTIVE.name());
				geofenceEntity.setEntityStatus(activeStatus);
			}
		}
		// For update set the identifier
		if (isUpdate) {
			geofenceEntity.setIdentifier(geofenceDTO.getIdentifier());
			geofenceESB.setGeofenceName(geofenceDTO.getGeofenceName());
			geofenceESB.setType(geofenceDTO.getType());
		}
		// Set geofence entity
		geofenceESB.setGeofenceEntity(geofenceEntity);
		// Set geofence type entity
		geofenceESB.setGeofenceTypeEntity(constructGeofenceType(geofenceDTO));

		return geofenceESB;
	}

	private List<FieldMapDTO> constuctGeofenceFields(GeofenceDTO geofenceDTO) {
		List<FieldMapDTO> geofenceFields = new ArrayList<FieldMapDTO>();

		// Construct geofence name map
		FieldMapDTO geofenceName = new FieldMapDTO();
		geofenceName.setKey(GEOFENCE_NAME.getFieldName());
		geofenceName.setValue(geofenceDTO.getGeofenceName());

		// Construct geofence desc map
		FieldMapDTO geofenceDesc = new FieldMapDTO();
		geofenceDesc.setKey(DESC.getFieldName());
		geofenceDesc.setValue(geofenceDTO.getDesc());

		// Construct geofence type map
		FieldMapDTO geofenceType = new FieldMapDTO();
		geofenceType.setKey(GEOFENCE_TYPE.getFieldName());
		geofenceType.setValue(geofenceDTO.getType());

		// add fields to the list
		geofenceFields.add(geofenceName);
		geofenceFields.add(geofenceDesc);
		geofenceFields.add(geofenceType);

		return geofenceFields;
	}

	private EntityDTO constructGeofenceType(GeofenceDTO geofenceDTO) {
		EntityDTO geofenceType = new EntityDTO();
		// Set the domain
		geofenceType.setDomain(geofenceDTO.getDomain());

		// Set template type
		EntityTemplateDTO geofenceTypeTemplate = new EntityTemplateDTO();
		geofenceTypeTemplate.setEntityTemplateName(geofenceDTO.getType());
		geofenceType.setEntityTemplate(geofenceTypeTemplate);

		// For polygon and route order needs to be stored
		if (geofenceDTO.getType().equalsIgnoreCase(POLYGON.getFieldName())
		        || geofenceDTO.getType().equalsIgnoreCase(ROUTE.getFieldName())) {
			// Construct geofence type map
			FieldMapDTO coordinateMap = new FieldMapDTO();
			coordinateMap.setKey(CO_ORDINATES.getFieldName());
			String coordinate = fetchFieldValue(
			        geofenceDTO.getGeofenceFields(), coordinateMap);
			coordinateMap.setValue(coordinate);
			Gson gson = new Gson();
			List<CoordinatesDTO> coordinates = gson.fromJson(coordinate,
			        new TypeToken<List<CoordinatesDTO>>() {
			        }.getType());
			if (geofenceDTO.getType().equalsIgnoreCase(POLYGON.getFieldName())
			        && coordinates.size() < 3) {
				throw new GalaxyException(GeoErrorCodes.LIST_REQUIREMENTS,
				        POLYGON.getDescription());
			}
			if (geofenceDTO.getType().equalsIgnoreCase(ROUTE.getFieldName())
			        && coordinates.size() < 2) {
				throw new GalaxyException(GeoErrorCodes.LIST_REQUIREMENTS,
				        ROUTE.getDescription());
			}
			appendOrder(coordinates);
			geofenceDTO.getGeofenceFields().remove(coordinateMap);
			coordinateMap.setValue(gson.toJson(coordinates));
			geofenceDTO.getGeofenceFields().add(coordinateMap);
		}

		// Set the field values
		geofenceType.setFieldValues(geofenceDTO.getGeofenceFields());

		// set the status
		if (geofenceDTO.getGeofenceStatus() != null
		        && isNotBlank(geofenceDTO.getGeofenceStatus().getStatusName())) {
			geofenceType.setEntityStatus(geofenceDTO.getGeofenceStatus());
		} else {
			StatusDTO activeStatus = new StatusDTO();
			activeStatus.setStatusName(Status.ACTIVE.name());
			geofenceType.setEntityStatus(activeStatus);
		}
		geofenceType.setDomain(geofenceDTO.getDomain());

		return geofenceType;
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

	private void appendOrder(List<CoordinatesDTO> coordinates) {
		for (CoordinatesDTO coordinatesDTO : coordinates) {
			Integer order = coordinates.indexOf(coordinatesDTO);
			coordinatesDTO.setOrder(order.toString());
		}
	}

	private void setGeofenceTemplate(IdentityDTO geofenceIdentifier) {
		EntityTemplateDTO geofenceTemplate = new EntityTemplateDTO();
		geofenceTemplate.setEntityTemplateName(GEOFENCE_TEMPLATE);
		geofenceIdentifier.setEntityTemplate(geofenceTemplate);
	}

}
