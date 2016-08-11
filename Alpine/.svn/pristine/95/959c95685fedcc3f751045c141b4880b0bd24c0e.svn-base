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
package com.pcs.alpine.serviceImpl.validation;

import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.Status.ACTIVE;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.util.ObjectBuilderUtil;
import com.pcs.alpine.enums.GalaxyValidationDataFields;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.PlatformEntityService;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.DomainDTO;
// import com.pcs.galaxy.enums.GalaxyValidationDataFields;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.dto.ValidationJsonStringDTO;
import com.pcs.alpine.services.dto.ValidationTestDTO;
import com.pcs.alpine.services.exception.GalaxyEMErrorCodes;

/**
 * 
 * @description This class is responsible for the EntityValidation
 * 
 * @author Daniela (PCSEG191)
 * @date 15 Mar 2015
 * @since galaxy-1.0.0
 */
@Component
public class EntityValidation {

	@Autowired
	private StatusService statusService;

	@Autowired
	private EntityTemplateService entityTemplateService;

	@Autowired
	private CoreEntityService coreEntityService;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Autowired
	private PlatformEntityTemplateService globalEntityTemplateService;

	@Autowired
	private PlatformEntityService globalEntityService;

	// @Autowired
	// private HierarchyService hierarchyService;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(EntityValidation.class);

	/**
	 * @Description Responsible to validate an entity template against the input
	 *              entityDTO
	 * 
	 * @param entity
	 * @param isUpdate
	 * @return entity
	 */
	public EntityDTO validateTemplate(EntityDTO entity, Boolean isUpdate) {
		EntityTemplateDTO template = getEntityTemplateDetails(entity);
		Map<String, String> fieldValidations = template.getFieldValidation();

		// fetch fieldMap from input EntityDto
		List<FieldMapDTO> fieldMapDTOs = entity.getFieldValues();

		List<FieldMapDTO> fieldMaps = new ArrayList<>();
		List<FieldMapDTO> dataproviderMaps = new ArrayList<>();
		FieldMapDTO treeMap = null;
		FieldMapDTO identifierMap = new FieldMapDTO();
		ValidationTestDTO validationTestDTO = new ValidationTestDTO();

		IdentityDTO identityDTO = new IdentityDTO();
		identityDTO.setEntityTemplate(entity.getEntityTemplate());
		identityDTO.setDomain(entity.getDomain());
		identityDTO.setPlatformEntity(entity.getPlatformEntity());

		if (isUpdate) {
			identityDTO.setIdentifier(entity.getIdentifier());
		}

		// iterate over Template fieldValidations
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		for (String fieldValidationKey : fieldValidations.keySet()) {

			// fetch fieldValidation string for fieldValidationKey
			String fieldValidationString = fieldValidations
			        .get(fieldValidationKey);

			fieldMapDTO = new FieldMapDTO();
			fieldMapDTO.setKey(fieldValidationKey);

			// populate field<FieldMapDTO> from input EntityDto
			fieldMapDTO = fetchField(fieldMapDTOs, fieldMapDTO);
			validationTestDTO.setFieldMapDTO(fieldMapDTO);

			// set validationJson String to ValidationTestDTO
			validationTestDTO.setValidationJsonString(fieldValidationString,
			        objectBuilderUtil.getGson());

			fieldMapDTO = getFieldValues(entity, isUpdate, fieldMapDTOs,
			        validationTestDTO, identityDTO, fieldMapDTO,
			        fieldValidationKey, fieldValidationString);
			fieldMaps.add(fieldMapDTO);

			// set dataprovider here
			if (validationTestDTO.getValidationJsonStringDTO().getShowOnGrid()) {
				dataproviderMaps.add(fieldMapDTO);
			}
			// set show on tree
			if (validationTestDTO.getValidationJsonStringDTO().getShowOnTree() != null
			        && validationTestDTO.getValidationJsonStringDTO()
			                .getShowOnTree()) {
				treeMap = new FieldMapDTO();
				treeMap = fieldMapDTO;

			}
		}
		// Set the identifier field
		String identifierField = template.getIdentifierField();
		identifierMap.setKey(identifierField);
		identifierMap.setValue(fetchField(fieldMapDTOs, identifierMap)
		        .getValue());

		// replace input dto fieldMap with new field map
		entity.setFieldValues(fieldMaps);
		entity.setEntityTemplate(template);
		entity.setDataprovider(dataproviderMaps);
		entity.setTree(treeMap);
		entity.setIdentifier(identifierMap);

		// By default the device status should be active
		StatusDTO entityStatus = new StatusDTO();

		if (!isUpdate
		        && (entity.getEntityStatus() == null || isBlank(entity
		                .getEntityStatus().getStatusName()))) {
			entityStatus.setStatusName(ACTIVE.name());
			entity.setEntityStatus(entityStatus);

		}
		return entity;

	}

	/**
	 * @Description Responsible to validate if the logged in user has access to
	 *              the selected domain entityDTO
	 * 
	 * @param selectedDomain
	 * @param loggedInuserDomain
	 * @param loggedInUserName
	 * @param userType
	 */
	// public void isSelectedDomainAccessAvailable(String selectedDomain,
	// String loggedInuserDomain, String loggedInUserName, String userType) {
	//
	// // check if logged in user is super admin or org admin or client admin
	// if (userType.equalsIgnoreCase(ADMIN.getVariableName())) {
	// // Get super tenant entry
	// EntityDTO superTenantEntity = getSuperTenant();
	//
	// FieldMapDTO domainMap = new FieldMapDTO();
	// domainMap.setKey(DOMAIN.getVariableName());
	// String superTenantDomain = getFieldValue(DOMAIN.getDescription(),
	// domainMap, superTenantEntity.getFieldValues());
	//
	// if (!loggedInuserDomain.equalsIgnoreCase(superTenantDomain)) {
	// // user is organization/client admin
	// EntityDTO tenantDomain = coreEntityService
	// .getDomainEntity(loggedInuserDomain);
	// EntitySearchDTO tenantSearch = new EntitySearchDTO();
	// tenantSearch.setParentEntityId(tenantDomain.getEntityId());
	// tenantSearch.setParentEntityType(tenantDomain.getGlobalEntity()
	// .getGlobalEntityType());
	//
	// // This call is repeated as for superTenant it will not be able
	// // to find domain entry
	// EntityDTO selectedEntityDTO = coreEntityService
	// .getDomainEntity(selectedDomain);
	// tenantSearch
	// .setCurrentEntityId(selectedEntityDTO.getEntityId());
	// tenantSearch.setEntityType(selectedEntityDTO.getGlobalEntity()
	// .getGlobalEntityType());
	// if (!tenantDomain.getEntityId().equalsIgnoreCase(
	// selectedEntityDTO.getEntityId())) {
	// Boolean isValidTree = hierarchyService
	// .isValidHierarchyTree(tenantSearch);
	// if (!isValidTree) {
	// throw new GalaxyException(
	// GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
	// }
	// }
	// }
	// } else {
	// // normal user
	// EntityAccessDTO access = entityService.getEntityAccess(
	// loggedInUserName, loggedInuserDomain);
	// EntityDTO selectedEntityDTO = entityService
	// .getDomainEntity(selectedDomain);
	// for (String entityId : access.getEntities()) {
	// if (entityId.equalsIgnoreCase(selectedEntityDTO.getEntityId())) {
	// break;
	// }
	// }
	// throw new GalaxyException(
	// GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
	// }
	//
	// }

	/**
	 * @description This method is responsible to validate that the selected
	 *              entity domain and the templates domain are the same
	 * 
	 * @param childEntityDto
	 * @param parentEnityDto
	 */
	public void validateSelectedDomainAndTemplateDomain(
	        EntityDTO childEntityDto, EntityDTO parentEnityDto) {
		FieldMapDTO domainMap = new FieldMapDTO();
		domainMap.setKey(DOMAIN.getFieldName());

		List<FieldMapDTO> parentFieldValues = parentEnityDto.getFieldValues();
		domainMap = getParentDomain(parentFieldValues, domainMap,
		        parentEnityDto.getDomain().getDomainName());

		String templateDomain = childEntityDto.getEntityTemplate().getDomain()
		        .getDomainName();
		if (!domainMap.getValue().equalsIgnoreCase(templateDomain)) {
			throw new GalaxyException(GalaxyEMErrorCodes.DOMAIN_MISMATCH);
		}
	}

	/**
	 * @Description Responsible to validate if the identifier has been updated
	 * 
	 * @param EntityDTO
	 * 
	 */
	public void validateIdentifier(EntityDTO entity) {
		// Fetch the template for the identifier key
		EntityTemplateDTO entityTemplate = getEntityTemplateDetails(entity);
		FieldMapDTO identifier = new FieldMapDTO();
		identifier.setKey(entityTemplate.getIdentifierField());

		String identifierValue = getFieldValue(
		        entityTemplate.getIdentifierField(), identifier,
		        entity.getFieldValues());

		if (!identifierValue
		        .equalsIgnoreCase(entity.getIdentifier().getValue())) {
			throw new GalaxyException(
			        GalaxyEMErrorCodes.UPDATE_ON_ENTITY_IDENTIFIER_ILLEGAL);
		}

	}

	/**
	 * @description Method to fetch fieldMap from input EntityDto and perform
	 *              server side validations
	 * @param entity
	 * @param isUpdate
	 * @param fieldMapDTOs
	 * @param fieldMaps
	 * @param validationTestDTO
	 * @param identityDTO
	 * @param fieldMapDTO
	 * @param fieldValidationKey
	 * @param fieldValidationString
	 * @return
	 */
	private FieldMapDTO getFieldValues(EntityDTO entity, Boolean isUpdate,
	        List<FieldMapDTO> fieldMapDTOs,
	        ValidationTestDTO validationTestDTO, IdentityDTO identityDTO,
	        FieldMapDTO fieldMapDTO, String fieldValidationKey,
	        String fieldValidationString) {
		if (!isBlank(fieldValidationString)
		        && validationStringCheck(fieldValidationString)) {

			// do the validation (Mandatory or Unique)
			// TODO validation to happen from validation tables
			checkValidation(validationTestDTO, isUpdate, identityDTO);

		} else {
			fieldMapDTO = new FieldMapDTO();
			// fieldValidationString for server validation is null: copy
			// value from input EntityDTO if input EntityDTO is having value
			fieldMapDTO.setKey(fieldValidationKey);
			fieldMapDTO = fetchField(fieldMapDTOs, fieldMapDTO);
			if (isNotBlank(fieldMapDTO.getValue())) {
				return fieldMapDTO;
			}
		}
		return fieldMapDTO;
	}

	/**
	 * @Description Responsible for fetch fieldMapDto from a list
	 * @param List
	 *            <FieldMapDTO> fieldMapDTOs
	 * @param FieldMapDTO
	 *            fieldMapDTO
	 * @return fieldMapDTO
	 */
	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		// populate field<FieldMapDTO> from input EntityDto
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	private FieldMapDTO getParentDomain(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO, String parentDomain) {

		FieldMapDTO field = fetchField(fieldMapDTOs, fieldMapDTO);
		if (isBlank(field.getValue())) {
			field.setValue(parentDomain);
		}
		return field;

	}

	/**
	 * @Description Responsible for fetching template
	 * @param EntityDTO
	 *            entityDTO
	 * @param EntityTemplateDTO
	 *            template
	 */
	private EntityTemplateDTO getEntityTemplateDetails(EntityDTO entityDTO) {
		EntityTemplateDTO entityTemplate = entityDTO.getEntityTemplate();
		if (entityDTO.getPlatformEntity().getIsDefault().equals(Boolean.TRUE)) {

			PlatformEntityTemplateDTO globalEntityTemplateDTO = new PlatformEntityTemplateDTO();

			if (isBlank(entityTemplate.getEntityTemplateName())) {
				// There will be a single global entity template for user,
				// template name will not be specified
				globalEntityTemplateDTO = globalEntityTemplateService
				        .getPlatformEntityTemplate(entityDTO
				                .getPlatformEntity().getPlatformEntityType());
			} else {
				// fetch template based on globalEntityType
				globalEntityTemplateDTO = globalEntityTemplateService
				        .getPlatformEntityTemplate(entityDTO
				                .getPlatformEntity().getPlatformEntityType(),
				                entityTemplate.getEntityTemplateName());
			}
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
			entityTemplate = entityTemplateService.getTemplate(entityTemplate);
		}
		return entityTemplate;
	}

	/**
	 * This method is responsible for mandatory/uniqueness validation of fields
	 * 
	 * @param validationTestDTO
	 *            , domainName
	 * @return void
	 */
	private void checkValidation(ValidationTestDTO validationTestDTO,
	        Boolean isUpdate, IdentityDTO identity) {
		List<String> serverValidationArray = validationTestDTO
		        .getValidationJsonStringDTO().getServer();
		if (CollectionUtils.isNotEmpty(serverValidationArray)) {
			for (String serverValidation : serverValidationArray) {
				if (StringUtils.isNotBlank(serverValidation)) {
					GalaxyValidationDataFields validationType = GalaxyValidationDataFields
					        .getEnum(serverValidation);

					switch (validationType) {
						case MANDATORY :
							valdateMandatory(validationTestDTO);
							break;
						case UNIQUE_ACROSS_DOMAIN :
							if (isNotBlank(validationTestDTO.getFieldMapDTO()
							        .getValue())) {

								uniquinessValidation(validationTestDTO,
								        identity, isUpdate, null);
							}
							break;
						case UNIQUE_ACROSS_APPLICATION :
							if (isNotBlank(validationTestDTO.getFieldMapDTO()
							        .getValue())) {
								// uniqueness is across domain, hence set domain
								// name as null
								IdentityDTO uniqueIdentityDTO = new IdentityDTO();
								uniqueIdentityDTO.setPlatformEntity(identity
								        .getPlatformEntity());
								uniqueIdentityDTO.setDomain(new DomainDTO());
								uniqueIdentityDTO.setEntityTemplate(identity
								        .getEntityTemplate());
								String templateName = null;
								if (identity.getEntityTemplate() != null
								        && isNotBlank(identity
								                .getEntityTemplate()
								                .getEntityTemplateName())) {
									templateName = identity.getEntityTemplate()
									        .getEntityTemplateName();
								}
								uniqueIdentityDTO.setIdentifier(identity
								        .getIdentifier());
								uniquinessValidation(validationTestDTO,
								        uniqueIdentityDTO, isUpdate,
								        templateName);
							}
							break;

						case UNIQUE_ACROSS_TEMPLATE :
							if (isNotBlank(validationTestDTO.getFieldMapDTO()
							        .getValue())) {
								String templateName = identity
								        .getEntityTemplate()
								        .getEntityTemplateName();
								uniquinessValidation(validationTestDTO,
								        identity, isUpdate, templateName);
							}
							break;
						default:
							break;
					}
				}
			}
		}
	}

	/**
	 * @description This method is responsible to check if a field is
	 *              uniqueAcrossDomain
	 * 
	 * @param validationTestDTO
	 *            , identity, isUpdate, globalEntityType
	 */
	private void uniquinessValidation(ValidationTestDTO validationTestDTO,
	        IdentityDTO identity, Boolean isUpdate, String templateName) {
		EntityDTO entity = null;
		Boolean isUniqueAcrossDomain = false;

		valdateMandatory(validationTestDTO);

		try {
			EntitySearchDTO coreEntitySearchDTO = new EntitySearchDTO();
			coreEntitySearchDTO.setPlatformEntity(identity.getPlatformEntity());
			coreEntitySearchDTO.setDomain(identity.getDomain());
			FieldMapDTO fieldMapDTO = new FieldMapDTO();
			fieldMapDTO.setKey(validationTestDTO.getFieldMapDTO().getKey());
			fieldMapDTO.setValue(validationTestDTO.getFieldMapDTO().getValue());
			List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
			fields.add(fieldMapDTO);
			coreEntitySearchDTO.setFieldValues(fields);

			if (isNotBlank(templateName)) {
				coreEntitySearchDTO.setEntityTemplate(identity
				        .getEntityTemplate());
			}

			entity = coreEntityService.getEntityByField(coreEntitySearchDTO);
		} catch (GalaxyException e) {
			LOGGER.debug("<<-- unique across domain-->>");
			if (e.getCode() == GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE
			        .getCode()) {
				isUniqueAcrossDomain = true;
			}
		}
		if (entity != null && isUpdate) {
			identity.setDomain(entity.getDomain());
			EntityDTO updateEntity = coreEntityService.getEntity(identity);
			if (updateEntity != null
			        && updateEntity
			                .getIdentifier()
			                .getValue()
			                .equalsIgnoreCase(entity.getIdentifier().getValue())) {
				isUniqueAcrossDomain = true;
			}
		}
		if (!isUniqueAcrossDomain) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
			        validationTestDTO.getFieldMapDTO().getKey());
		}
	}

	//
	// private void uniqueAcrossHierarchy(ValidationTestDTO validationTestDTO,
	// com.pcs.alpine.em.services.dto.IdentityDTO identity, Boolean isUpdate,
	// String parentEntityId) {
	// EntityDTO entity = null;
	// String entityType = CUSTOM.getFieldName();
	// List<EntityDTO> entityDTOs = null;
	// Boolean isUnique = true;
	// Boolean isUpdatable = false;
	// try {
	// entity = entityService.getEntity(validationTestDTO.getFieldMapDTO()
	// .getKey(), validationTestDTO.getFieldMapDTO().getValue(),
	// identity.getDomain().getDomainName(), identity
	// .getGlobalEntity().getGlobalEntityType(),
	// Status.DELETED.name());
	// } catch (GalaxyException e) {
	// if (e.getErrorMessageDTO()
	// .getErrorCode()
	// .equalsIgnoreCase(
	// GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode()
	// .toString())) {
	// isUnique = true;
	// }
	// }
	//
	// // Preparing EntitySearchDTO
	// EntitySearchDTO entitySearchDTO = new EntitySearchDTO();
	// entitySearchDTO.setParentEntityId(parentEntityId);
	// entitySearchDTO.setEntityType(entityType);
	//
	// // Calling hierarchy service to get the list of all child entity IDs
	// List<String> entityIdList = hierarchyService
	// .getImmediateHierarchyEntities(entitySearchDTO);
	// if (CollectionUtils.isEmpty(entityIdList)) {
	// isUnique = true;
	// }
	// LOGGER.info("Entities id list get from hierarchyService="
	// + new Gson().toJson(entityIdList));
	//
	// // Calling core entity service to get the list of entities under the
	// // parent domain
	// try {
	// entityDTOs = entityService.getEntities(validationTestDTO
	// .getFieldMapDTO().getKey(), validationTestDTO
	// .getFieldMapDTO().getValue(), identity.getDomain()
	// .getDomainName(), entityType);
	// } catch (GalaxyException e) {
	// isUnique = true;
	// }
	//
	// if (entity != null && isUpdate) {
	// EntityDTO updateEntity = coreEntityService.getEntity(identity);
	// if (updateEntity != null
	// && updateEntity.getIdentifier().getValue()
	// .equals(identity.getIdentifier().getValue())) {
	// isUpdatable = true;
	// }
	// }
	//
	// if (CollectionUtils.isNotEmpty(entityDTOs) && !isUpdatable) {
	// for (String entityID : entityIdList) {
	// for (EntityDTO entityDTO : entityDTOs) {
	// if (entityID.equals(entityDTO.getEntityId())) {
	// isUnique = false;
	// break;
	// }
	// }
	//
	// }
	// }
	// if (!isUnique) {
	// throw new GalaxyException(
	// GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
	// EMDataFields.getDataField(
	// validationTestDTO.getFieldMapDTO().getKey())
	// .getDescription());
	// }
	// }

	/**
	 * @description This method is responsible to check for mandatory fields
	 * 
	 * @param validationTestDTO
	 *            , identity, isUpdate, globalEntityType
	 * @return void
	 */
	private void valdateMandatory(ValidationTestDTO validationTestDTO) {
		if (isBlank(validationTestDTO.getFieldMapDTO().getValue())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        validationTestDTO.getFieldMapDTO().getKey());
		}
	}

	/**
	 * @description This method is responsible to check if a field is
	 *              uniqueAcrossApplication
	 * 
	 * @param validationTestDTO
	 *            , identity, isUpdate, globalEntityType
	 * @return void
	 */
	// private void uniqueAcrossApplication(ValidationTestDTO validationTestDTO,
	// com.pcs.alpine.em.services.dto.IdentityDTO identity, Boolean isUpdate) {
	//
	// valdateMandatory(validationTestDTO);
	//
	// EntityDTO entity = null;
	// Boolean isUniqueAcrossApp = false;
	// try {
	// entity = entityService.getEntity(validationTestDTO.getFieldMapDTO()
	// .getKey(), validationTestDTO.getFieldMapDTO().getValue(),
	// null, identity.getGlobalEntity().getGlobalEntityType(),
	// Status.DELETED.name());
	// } catch (GalaxyException e) {
	// if (e.getErrorMessageDTO()
	// .getErrorCode()
	// .equalsIgnoreCase(
	// GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode()
	// .toString())) {
	// isUniqueAcrossApp = true;
	// }
	// }
	// if (entity != null && isUpdate) {
	// EntityDTO updateEntity = coreEntityService.getEntity(identity);
	// if (updateEntity != null
	// && updateEntity.getIdentifier().getValue()
	// .equals(entity.getIdentifier().getValue())) {
	// isUniqueAcrossApp = true;
	// }
	// }
	// if (!isUniqueAcrossApp) {
	// throw new GalaxyException(
	// GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
	// EMDataFields.getDataField(
	// validationTestDTO.getFieldMapDTO().getKey())
	// .getDescription());
	// }
	// }

	/**
	 * @Description Responsible for deserializing and checking validationg
	 *              against key specified in template
	 * @param String
	 *            fieldValidationString
	 * @param Boolean
	 *            resultFlag
	 */
	private Boolean validationStringCheck(String fieldValidationString) {
		Boolean resultFlag = false;
		Gson gson = objectBuilderUtil.getGson();
		ValidationJsonStringDTO validationJsonStringDTO = gson.fromJson(
		        fieldValidationString, ValidationJsonStringDTO.class);
		if (CollectionUtils.isNotEmpty(validationJsonStringDTO.getServer())) {
			resultFlag = true;
		}
		return resultFlag;
	}

	/**
	 * @Description Responsible to fetch the value of the FieldMapDTO from the
	 *              list passed against key specified in template
	 * @param fieldName
	 * @param fieldMap
	 * @param fieldValues
	 * @return value
	 */
	private String getFieldValue(String fieldName, FieldMapDTO fieldMap,
	        List<FieldMapDTO> fieldValues) {
		Integer index = fieldValues.indexOf(fieldMap);

		if (index < 0) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED, fieldName);
		}
		String value = fieldValues.get(index).getValue();
		if (isBlank(value)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED, fieldName);
		}
		return value;
	}

	/**
	 * @Description Responsible to fetch the superTenant entry
	 * 
	 * @return superTenantEntity
	 */
	// private EntityDTO getSuperTenant() {
	// String superTenant = "true";
	// String domain = null;
	// String entityType = EMDataFields.ORGANIZATION.getFieldName();
	// String status = Status.DELETED.name();
	// EntityDTO superTenantEntity = entityService.getEntity(
	// EMDataFields.SUPER_TENANT.getVariableName(), superTenant,
	// domain, entityType, status);
	// return superTenantEntity;
	// }

}
