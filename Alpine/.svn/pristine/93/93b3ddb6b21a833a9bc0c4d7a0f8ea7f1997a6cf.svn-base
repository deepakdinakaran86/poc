/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLS and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.alpine.serviceImpl;

import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.commons.validation.ValidationUtils.validateResult;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_KEY;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALUE;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALUES;
import static com.pcs.alpine.services.enums.EMDataFields.MARKER;
import static com.pcs.alpine.services.enums.EMDataFields.STATUS;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.dto.MarkerBatchDTO;
import com.pcs.alpine.serviceImpl.validation.CommonEntityValidations;
import com.pcs.alpine.serviceImpl.validation.EntityValidation;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.MarkerService;
import com.pcs.alpine.services.PlatformEntityService;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;
import com.pcs.alpine.services.dto.ReturnFieldsDTO;
import com.pcs.alpine.services.dto.SearchFieldsDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;
import com.pcs.alpine.services.enums.EMDataFields;
import com.pcs.alpine.services.enums.Status;

/**
 * @description Implementation Class for Marker Entity Services
 * @author Daniela (PCSEG191)
 * @date 14 Jan 2015
 */
@Service
public class MarkerServiceImpl implements MarkerService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(MarkerServiceImpl.class);

	@Autowired
	private PlatformEntityService globalEntityService;

	@Autowired
	private PlatformEntityTemplateService globalEntityTemplateService;

	@Autowired
	private StatusService statusService;

	@Autowired
	private EntityTemplateService templateService;

	@Autowired
	CoreEntityService coreEntityService;

	@Autowired
	private EntityValidation entityTemplateValidation;

	@Autowired
	private CommonEntityValidations commonEntityValidations;

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	/**
	 * @Description Responsible to fetch an entity details based on the unique
	 *              identifier fields
	 * @param IdentityDTO
	 * @return EntityDTO
	 */
	@Override
	public EntityDTO getMarker(IdentityDTO identityDTO) {
		LOGGER.debug("<<--Entry getMarkerEntity by unique Identifier->>");
		// Check for domain name in payload and set type as marker
		identityDTO.setDomain(setDomain(identityDTO.getDomain()));
		identityDTO.setPlatformEntity(setEntityType());
		commonEntityValidations.validateIdentifierFields(identityDTO);
		isParentDomain(identityDTO);

		return generateGetResponse(coreEntityService.getEntity(identityDTO));

	}

	private void isParentDomain(IdentityDTO identityDTO) {
		if (identityDTO.getIsParentDomain() != null
		        && identityDTO.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	/**
	 * @description Responsible to fetch all entities details based on domain
	 *              and the uniqueUserId
	 * @param IdentityDTO
	 * @return List<EntityDTO>
	 */
	@Override
	public List<EntityDTO> getMarkersByDomain(IdentityDTO identity) {
		LOGGER.debug("<<--Entry getMarkersByDomain -->>");

		// Check for domain name in payload and set type as marker
		identity.setDomain(setDomain(identity.getDomain()));

		EntitySearchDTO entitySearchDTO = new EntitySearchDTO();
		entitySearchDTO.setPlatformEntity(setEntityType());
		entitySearchDTO.setDomain(identity.getDomain());
		isParentDomain(identity);

		List<EntityDTO> entityDTOs = coreEntityService
		        .getEntitiesByDomain(entitySearchDTO);
		validateResult(entityDTOs);
		return entityDTOs;
	}

	/**
	 * @Description Responsible for creating a Marker entity
	 * @param entityDTO
	 * @return entityDTO
	 */

	@Override
	public EntityDTO saveMarker(EntityDTO entityDTO) {
		LOGGER.debug("<<--Entry saveMarkerEntity-->>");

		validateSaveMarker(entityDTO);
		isParentDomain(entityDTO);
		// Check for domain name in payload and set type as marker
		entityDTO.setDomain(setDomain(entityDTO.getDomain()));
		EntityDTO markerEntityDTO = createSaveMarkerPayload(entityDTO);
		EntityDTO MarkerEntity = this.coreEntityService
		        .saveEntity(markerEntityDTO);
		return constructSavedDTO(MarkerEntity);
	}

	/**
	 * @param entityDTO
	 * @return
	 */
	private EntityDTO createSaveMarkerPayload(EntityDTO entityDTO) {

		entityDTO.getEntityTemplate().setDomain(entityDTO.getDomain());
		entityDTO.setPlatformEntity(setEntityType());

		// Set global entity type as Marker
		// TODO check this
		// setDomainAndEntityType(entityDTO);

		List<FieldMapDTO> fieldMapDTOs = entityDTO.getFieldValues();

		// check if fieldMapDTOs is not null
		ValidationUtils.validateCollection(FIELD_VALUES, fieldMapDTOs);
		FieldMapDTO identifierFieldValue = new FieldMapDTO();
		EntityTemplateDTO entityTemplateDTO = fetchTemplate(entityDTO);

		// getting the identifier field from entity template
		String identifierField = entityTemplateDTO.getIdentifierField();
		identifierFieldValue.setKey(identifierField);

		// to remove null identifiers values and invalid identifier values
		fieldMapDTOs.remove(identifierFieldValue);

		// setting the random value of identifier field
		identifierFieldValue.setValue(UUID.randomUUID().toString());
		fieldMapDTOs.add(identifierFieldValue);
		entityDTO.setFieldValues(fieldMapDTOs);
		Boolean isUpdate = false;
		EntityDTO markerEntityDTO = entityTemplateValidation.validateTemplate(
		        entityDTO, isUpdate);

		markerEntityDTO.getEntityTemplate().setDomain(
		        markerEntityDTO.getDomain());
		return markerEntityDTO;
	}

	private void isParentDomain(EntityDTO entityDTO) {
		if (entityDTO.getIsParentDomain() != null
		        && entityDTO.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	/**
	 * @Description Responsible for updating a Marker entity
	 * @param entityDTO
	 * @return entityDTO
	 */
	@Override
	public StatusMessageDTO updateMarker(EntityDTO entityDTO) {
		LOGGER.debug("<<--Entry updateMarkerEntity-->>");

		validateTemplate(entityDTO);
		isParentDomain(entityDTO);

		// Check for domain name in payload and set type as marker
		entityDTO.setDomain(setDomain(entityDTO.getDomain()));
		entityDTO.getEntityTemplate().setDomain(entityDTO.getDomain());

		validateUpdateMarker(entityDTO);

		entityDTO.setPlatformEntity(setEntityType());

		StatusMessageDTO updateStatus = new StatusMessageDTO();
		updateStatus.setStatus(Status.FAILURE);

		// Global entity type is Marker
		PlatformEntityDTO markerEntityType = setEntityType();
		entityDTO.getEntityTemplate().setPlatformEntityType(
		        setEntityType().getPlatformEntityType());
		entityDTO.setPlatformEntity(markerEntityType);
		// creating IdentityDTO from EntityDTO to get the unique entity from DB
		IdentityDTO identityDTO = new IdentityDTO();
		identityDTO.setDomain(entityDTO.getEntityTemplate().getDomain());
		identityDTO.setPlatformEntity(markerEntityType);
		identityDTO.setEntityTemplate(entityDTO.getEntityTemplate());
		identityDTO.setIdentifier(entityDTO.getIdentifier());

		// Get entry to be updated from DB
		EntityDTO entityFromDB = coreEntityService.getEntity(identityDTO);

		// set the domain(Will be use to construct IdentityDTO in
		// validateTemplate() method)
		entityDTO.setDomain(identityDTO.getEntityTemplate().getDomain());

		// set the parent entity id to be used in uniqueAcrossHierarchy
		FieldMapDTO identifierField = new FieldMapDTO();
		identifierField = entityFromDB.getIdentifier();
		List<FieldMapDTO> fieldValues = entityDTO.getFieldValues();
		fieldValues.add(identifierField);
		entityDTO.setFieldValues(fieldValues);
		Boolean isUpdate = true;
		EntityDTO markerEntityDTO = entityTemplateValidation.validateTemplate(
		        entityDTO, isUpdate);

		// setting the updated fieldvalues
		entityFromDB.setFieldValues(markerEntityDTO.getFieldValues());

		// TODO check thisIf status is not passed consider the old status
		// if (markerEntityDTO.getEntityStatus() != null
		// && isNotBlank(markerEntityDTO.getEntityStatus().getStatusName())) {
		// entityFromDB.setEntityStatus(markerEntityDTO.getEntityStatus());
		// }

		// setting the updated status
		if (markerEntityDTO.getEntityStatus() != null
		        && isNotBlank(markerEntityDTO.getEntityStatus().getStatusName())) {
			entityFromDB.setEntityStatus(markerEntityDTO.getEntityStatus());
		}

		// setting the updated data provider
		entityFromDB.setDataprovider(markerEntityDTO.getDataprovider());

		// setting the updated tree
		entityFromDB.setTree(markerEntityDTO.getTree());
		EntityDTO markerEntity = this.coreEntityService
		        .updateEntity(entityFromDB);
		if (markerEntity != null) {
			updateStatus.setStatus(Status.SUCCESS);
		}
		return updateStatus;
	}

	/**
	 * @Description Responsible for delete a Marker entity
	 * @param IdentityDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public StatusMessageDTO deleteMarker(IdentityDTO identityDTO) {

		LOGGER.debug("<<--Entry deleteMarkerEntity -->>");

		// Check for domain name in payload and set type as marker
		identityDTO.setDomain(setDomain(identityDTO.getDomain()));
		identityDTO.setPlatformEntity(setEntityType());

		commonEntityValidations.validateIdentifierFields(identityDTO);
		isParentDomain(identityDTO);

		StatusMessageDTO entitySmDTO = new StatusMessageDTO();

		String deletedStatus = Status.DELETED.toString();
		entitySmDTO = this.coreEntityService.updateStatus(identityDTO,
		        deletedStatus);

		return entitySmDTO;
	}

	@Override
	public List<EntityDTO> getMarkers(IdentityDTO identity) {
		LOGGER.debug("<<--Entry getMarkers -->>");

		// Check for domain name in payload and set type as marker
		identity.setDomain(setDomain(identity.getDomain()));

		// validate the template name
		validateMandatoryFields(identity, ENTITY_TEMPLATE);
		validateMandatoryFields(identity.getEntityTemplate(),
		        EMDataFields.ENTITY_TEMPLATE_NAME);
		isParentDomain(identity);

		EntitySearchDTO entitySearchDTO = new EntitySearchDTO();

		PlatformEntityDTO globalEntityDTO = globalEntityService
		        .getGlobalEntityWithName(EMDataFields.MARKER.getFieldName());
		entitySearchDTO.setPlatformEntity(globalEntityDTO);
		entitySearchDTO.setEntityType(globalEntityDTO.getPlatformEntityType());

		entitySearchDTO.setDomain(identity.getDomain());
		entitySearchDTO.setEntityTemplate(identity.getEntityTemplate());

		List<EntityDTO> entityDTOs = coreEntityService
		        .getEntities(entitySearchDTO);
		return entityDTOs;
	}

	@Override
	public StatusMessageDTO validateUniqueness(
	        EntitySearchDTO coreEntitySearchDTO) {
		// set global entity type is marker
		coreEntitySearchDTO.setPlatformEntity(setEntityType());
		// Check for domain name in payload and set type as marker
		coreEntitySearchDTO
		        .setDomain(setDomain(coreEntitySearchDTO.getDomain()));
		validateUniquenessPayload(coreEntitySearchDTO);
		isParentDomain(coreEntitySearchDTO);

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		try {
			coreEntityService.getEntityByField(coreEntitySearchDTO);

		} catch (GalaxyException galaxyException) {
			// 500 error code indicates no data exists
			if (galaxyException.getCode() == 500) {
				statusMessageDTO.setStatus(Status.SUCCESS);
			} else if (galaxyException.getCode() == 509) {
				return statusMessageDTO;
			} else {
				throw galaxyException;
			}
		}
		return statusMessageDTO;
	}

	private void isParentDomain(EntitySearchDTO coreEntitySearchDTO) {
		if (coreEntitySearchDTO.getIsParentDomain() != null
		        && coreEntitySearchDTO.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	@Override
	public List<ReturnFieldsDTO> searchFields(SearchFieldsDTO searchFieldsDTO) {
		// validate the input
		validateSearchFields(searchFieldsDTO);
		isParentDomain(searchFieldsDTO);

		EntitySearchDTO entitySearchDTO = constructEntitySearch(searchFieldsDTO);
		List<EntityDTO> entities = new ArrayList<EntityDTO>();

		if (isBlank(entitySearchDTO.getStatusName())) {
			entities = coreEntityService
			        .getEntitiesByEntityTemplateAndMultipleFields(entitySearchDTO);
		} else {
			entities = coreEntityService
			        .getEntitiesByEntityTemplateAndMultipleFieldsAndStatus(entitySearchDTO);
		}
		List<ReturnFieldsDTO> returnList = new ArrayList<ReturnFieldsDTO>();

		//
		// For each entity find the return field
		for (EntityDTO entityDTO : entities) {
			ReturnFieldsDTO returnFieldsDTO = new ReturnFieldsDTO();
			List<FieldMapDTO> returnFields = new ArrayList<FieldMapDTO>();
			FieldMapDTO returnFieldMap = new FieldMapDTO();
			if (!CollectionUtils.isEmpty(searchFieldsDTO.getReturnFields())) {
				for (String returnField : searchFieldsDTO.getReturnFields()) {
					returnFieldMap = fetchField(entityDTO.getDataprovider(),
					        returnField);
					// check if return field has show on grid property in
					// template,
					// only if present it should be returned
					if (isNotBlank(returnFieldMap.getValue())) {
						returnFields.add(returnFieldMap);
					}
				}
				validateResult(returnFields);
				returnFieldsDTO.setReturnFields(returnFields);
			} else {
				returnFieldsDTO.setDataprovider(entityDTO.getDataprovider());
			}
			returnFieldsDTO.setDomain(entityDTO.getDomain());
			returnFieldsDTO.setIdentifier(entityDTO.getIdentifier());
			returnFieldsDTO.setPlatformEntity(entityDTO.getPlatformEntity());
			returnFieldsDTO.setEntityTemplate(entityDTO.getEntityTemplate());
			returnFieldsDTO.setEntityStatus(entityDTO.getEntityStatus());
			returnList.add(returnFieldsDTO);
		}
		return returnList;
	}

	private void isParentDomain(SearchFieldsDTO searchFieldsDTO) {
		if (searchFieldsDTO.getIsParentDomain() != null
		        && searchFieldsDTO.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	@Override
	public EntityCountDTO getMarkerCount(EntitySearchDTO markerSearch) {
		// check for domain, if not present fetch from logged in domain
		markerSearch.setDomain(setDomain(markerSearch.getDomain()));
		// set entity type as MARKER
		PlatformEntityDTO globalEntityDTO = new PlatformEntityDTO();
		globalEntityDTO.setPlatformEntityType(MARKER.name());
		markerSearch.setPlatformEntity(globalEntityDTO);
		isParentDomain(markerSearch);

		Integer count = 0;
		if (markerSearch.getEntityTemplate() == null
		        || isBlank(markerSearch.getEntityTemplate()
		                .getEntityTemplateName())) {
			count = coreEntityService
			        .getEntitiesCountByTypeAndDomain(markerSearch);
		} else {
			count = coreEntityService.getEntitiesCountByTemplate(markerSearch);
		}
		EntityCountDTO countDTO = new EntityCountDTO();
		countDTO.setCount(count);
		return countDTO;
	}

	@Override
	public EntityCountDTO getSearchFieldsCount(SearchFieldsDTO searchFieldsDTO) {
		// validate the input
		validateSearchFields(searchFieldsDTO);
		isParentDomain(searchFieldsDTO);

		EntitySearchDTO entitySearchDTO = constructEntitySearch(searchFieldsDTO);
		EntityCountDTO entityCountDTO = coreEntityService
		        .getEntitySearchCount(entitySearchDTO);

		return entityCountDTO;
	}

	/**
	 * @param searchFieldsDTO
	 * @return
	 */
	private EntitySearchDTO constructEntitySearch(
	        SearchFieldsDTO searchFieldsDTO) {
		// For each item in field values fetch the entities
		EntitySearchDTO entitySearchDTO = new EntitySearchDTO();

		// Set the domain and global entity type
		entitySearchDTO.setPlatformEntity(setEntityType());
		entitySearchDTO.setDomain(setDomain(searchFieldsDTO.getDomain()));

		// Set the field name
		entitySearchDTO.setFieldValues(searchFieldsDTO.getSearchFields());
		entitySearchDTO.setEntityTemplate(searchFieldsDTO.getEntityTemplate());
		if (searchFieldsDTO.getStatus() != null
		        && isNotBlank(searchFieldsDTO.getStatus().getStatusName())) {
			entitySearchDTO.setStatusName(searchFieldsDTO.getStatus()
			        .getStatusName());
		}
		return entitySearchDTO;
	}

	private void validateSearchFields(SearchFieldsDTO searchFieldsDTO) {
		// validate the mandatory fields
		ValidationUtils.validateCollection(EMDataFields.SEARCH_FIELDS,
		        searchFieldsDTO.getSearchFields());

	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs, String key) {

		FieldMapDTO field = new FieldMapDTO();
		field.setKey(key);
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	private void validateSaveMarker(EntityDTO entityDTO) {
		validateTemplate(entityDTO);
		if (entityDTO.getEntityStatus() != null
		        && isNotBlank(entityDTO.getEntityStatus().getStatusName())) {
			validateStatus(entityDTO.getEntityStatus().getStatusName());
		}
	}

	private void validateTemplate(EntityDTO entityDTO) {
		validateMandatoryFields(entityDTO, ENTITY_TEMPLATE);
		validateMandatoryFields(entityDTO.getEntityTemplate(),
		        EMDataFields.ENTITY_TEMPLATE_NAME);
	}

	private void validateUniquenessPayload(EntitySearchDTO entitySearchDTO) {
		validateMandatoryFields(entitySearchDTO, DOMAIN, FIELD_VALUES);

		validateMandatoryFields(entitySearchDTO.getDomain(), DOMAIN_NAME);

		ValidationUtils.validateCollection(EMDataFields.FIELD_VALUES,
		        entitySearchDTO.getFieldValues());

		validateMandatoryFields(entitySearchDTO.getFieldValues().get(0),
		        FIELD_KEY, FIELD_VALUE);
	}

	private void validateUpdateMarker(EntityDTO entityDTO) {
		commonEntityValidations.validateMandatoryForEntityUpdate(entityDTO);
	}

	/********************************* All the private methods goes under this line **************************************/

	private EntityTemplateDTO fetchTemplate(EntityDTO entityDTO) {

		if (entityDTO.getEntityTemplate() == null) {
			EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
			entityDTO.setEntityTemplate(entityTemplateDTO);
		}
		EntityTemplateDTO entityTemplate = entityDTO.getEntityTemplate();
		if (Boolean.TRUE.equals(entityDTO.getPlatformEntity().getIsDefault())) {
			// fetch template based on globalEntityType
			PlatformEntityTemplateDTO globalEntityTemplateDTO = globalEntityTemplateService
			        .getPlatformEntityTemplate(entityDTO.getPlatformEntity()
			                .getPlatformEntityType());
			entityTemplate.setEntityTemplateName(globalEntityTemplateDTO
			        .getPlatformEntityTemplateName());
			entityTemplate.setDomain(entityDTO.getEntityTemplate().getDomain());
			entityTemplate.setFieldValidation(globalEntityTemplateDTO
			        .getFieldValidation());
			entityTemplate.setIdentifierField(globalEntityTemplateDTO
			        .getIdentifierField());
		} else {
			// fetch Template based on Domain, globalEntityType and templateName
			entityTemplate.setPlatformEntityType(entityDTO.getPlatformEntity()
			        .getPlatformEntityType());
			entityTemplate = templateService.getTemplate(entityTemplate);
		}
		return entityTemplate;
	}

	/**
	 * @Description This method is responsible to set the domain and global
	 *              entity type to be used during save Marker entity
	 * @param MarkerEntity
	 */
	private void setDomainAndEntityType(EntityDTO MarkerEntity) {

		EntityDTO domainEntity = coreEntityService.getDomainEntity(MarkerEntity
		        .getDomain().getDomainName());

		// set parent entity
		MarkerEntity.setParentEntityId(domainEntity.getEntityId());
	}

	/**
	 * @Description This method is responsible to generate the response of get
	 *              method, after removing not required fields from response
	 * 
	 * @param IdentityDTO
	 */
	private EntityDTO generateGetResponse(EntityDTO MarkerEntity) {
		EntityDTO responseEntity = new EntityDTO();
		responseEntity.setDomain(MarkerEntity.getDomain());
		responseEntity.setIdentifier(MarkerEntity.getIdentifier());
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(MarkerEntity
		        .getEntityTemplate().getEntityTemplateName());
		responseEntity.setEntityTemplate(entityTemplateDTO);
		PlatformEntityDTO gloabalEntityDTO = new PlatformEntityDTO();
		gloabalEntityDTO.setPlatformEntityType(MarkerEntity.getPlatformEntity()
		        .getPlatformEntityType());
		responseEntity.setPlatformEntity(gloabalEntityDTO);
		StatusDTO smDTO = new StatusDTO();
		responseEntity.setEntityStatus(smDTO);
		smDTO.setStatusName(MarkerEntity.getEntityStatus().getStatusName());
		responseEntity.setTree(MarkerEntity.getTree());
		responseEntity.setFieldValues(MarkerEntity.getFieldValues());
		return responseEntity;
	}

	/**
	 * @Description This method is responsible to construct the save response,
	 *              which will only include the identifier fields
	 * 
	 * 
	 * @param MarkerBatchDTO
	 */
	private EntityDTO constructSavedDTO(EntityDTO savedMarker) {
		EntityDTO savedMarkerDTO = new EntityDTO();
		PlatformEntityDTO globalEntity = new PlatformEntityDTO();
		globalEntity.setPlatformEntityType(savedMarker.getPlatformEntity()
		        .getPlatformEntityType());
		savedMarkerDTO.setPlatformEntity(globalEntity);

		DomainDTO MarkerDomain = new DomainDTO();
		MarkerDomain.setDomainName(savedMarker.getDomain().getDomainName());
		savedMarkerDTO.setDomain(MarkerDomain);

		savedMarkerDTO.setIdentifier(savedMarker.getIdentifier());

		EntityTemplateDTO templateDTO = new EntityTemplateDTO();
		templateDTO.setEntityTemplateName(savedMarker.getEntityTemplate()
		        .getEntityTemplateName());
		savedMarkerDTO.setEntityTemplate(templateDTO);
		return savedMarkerDTO;
	}

	private DomainDTO setDomain(DomainDTO domain) {
		if (domain == null) {
			domain = new DomainDTO();
		}
		if (domain.getDomainName() == null || isBlank(domain.getDomainName())) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			domain.setDomainName(domainName);
		}
		return domain;
	}

	private PlatformEntityDTO setEntityType() {
		PlatformEntityDTO markerType = globalEntityService
		        .getGlobalEntityWithName(MARKER.getFieldName());
		return markerType;
	}

	private void validateStatus(String statusName) {
		Integer status = statusService.getStatus(statusName);
		if (status == null) {
			throw new GalaxyException(GalaxyCommonErrorCodes.INVALID_ACTION,
			        STATUS.getDescription());
		}
	}

	@Override
	public List<IdentityDTO> saveMarkers(MarkerBatchDTO markers) {

		// validate for parent domain
		if (markers.getIsParentDomain() != null && markers.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
		// check if fieldMapDTOs is not null
		ValidationUtils.validateCollection(EMDataFields.ENTITY_LIST,
		        markers.getEntities());

		List<EntityDTO> entities = new ArrayList<EntityDTO>();

		for (EntityDTO entityDTO : markers.getEntities()) {
			// validate the input
			validateSaveMarker(entityDTO);

			entityDTO.setDomain(setDomain(markers.getDomain()));
			EntityDTO markerEntityDTO = createSaveMarkerPayload(entityDTO);

			entities.add(markerEntityDTO);
		}
		return this.coreEntityService.saveEntities(entities);
	}

	@Override
	public StatusMessageDTO updateMarkersStatus(List<IdentityDTO> markers,
	        String statusName) {

		StatusMessageDTO updateStatus = new StatusMessageDTO();
		updateStatus.setStatus(Status.SUCCESS);
		for (IdentityDTO marker : markers) {
			try {
				marker.setPlatformEntity(setEntityType());
				marker.setDomain(setDomain(marker.getDomain()));
				coreEntityService.updateStatus(marker, statusName);
			} catch (Exception galaxyException) {
				LOGGER.info(galaxyException.getMessage());
			}
		}
		return updateStatus;
	}

	@Override
	public List<EntityDTO> getMarkers(List<IdentityDTO> markers) {
		List<EntityDTO> markerEntities = new ArrayList<>();
		// fetch marker entity for each identity
		for (IdentityDTO marker : markers) {
			marker.setDomain(setDomain(marker.getDomain()));
			marker.setPlatformEntity(setEntityType());
			try {
				EntityDTO markerEntity = coreEntityService
				        .getEntityDataProvider(marker);
				markerEntities.add(markerEntity);
			} catch (GalaxyException galaxyException) {
				if (!galaxyException
				        .getErrorMessageDTO()
				        .getErrorCode()
				        .equalsIgnoreCase(
				                GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE
				                        .getCode().toString())) {
					LOGGER.debug("Error:"
					        + galaxyException.getErrorMessageDTO()
					                .getErrorMessage());
					throw galaxyException;
				}
			}
		}
		validateResult(markerEntities);
		return markerEntities;
	}
}
