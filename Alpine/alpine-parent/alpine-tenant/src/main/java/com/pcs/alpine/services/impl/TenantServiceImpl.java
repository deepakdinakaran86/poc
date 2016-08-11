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
package com.pcs.alpine.services.impl;

import static com.pcs.alpine.commons.dto.SubscriptionContextHolder.getJwtToken;
import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.commons.validation.ValidationUtils.validateResult;
import static com.pcs.alpine.constants.TenantConstants.GEOSERVICE_MANAGEMENT;
import static com.pcs.alpine.constants.TenantConstants.TAG_MANAGEMENT;
import static com.pcs.alpine.constants.TenantConstants.TENANT_MANAGEMENT;
import static com.pcs.alpine.constants.TenantConstants.USER_MANAGEMENT;
import static com.pcs.alpine.services.enums.EMDataFields.APPLICATION;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE;
import static com.pcs.alpine.services.enums.EMDataFields.FEATURE_LIST;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_KEY;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALUE;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALUES;
import static com.pcs.alpine.services.enums.EMDataFields.TENANT;
import static com.pcs.alpine.services.enums.EMDataFields.TENANT_ID;
import static com.pcs.alpine.services.enums.EMDataFields.UNIQUE_ACCESS;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.dto.PermissionGroupsDTO;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.constant.CommonConstants;
import com.pcs.alpine.dto.FeatureDTO;
import com.pcs.alpine.repo.utils.CoreEntityUtil;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.serviceImpl.validation.CommonEntityValidations;
import com.pcs.alpine.serviceImpl.validation.EntityValidation;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.DomainService;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.PlatformEntityService;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.TenantService;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;
import com.pcs.alpine.services.enums.EMDataFields;
import com.pcs.alpine.services.enums.Status;
import com.pcs.alpine.util.Features;

/**
 * 
 * @description This class is responsible for the TenantServiceImpl
 * 
 * @author Twinkle (PCSEG297)
 * @date 22 Sep 2015
 * @since galaxy-1.0.0
 */
@Service
public class TenantServiceImpl implements TenantService {

	@Autowired
	private PlatformEntityService globalEntityService;

	@Autowired
	private DomainService domainService;

	@Autowired
	private PlatformEntityTemplateService globalEntityTemplateService;

	@Autowired
	private EntityTemplateService entityTemplateService;

	@Autowired
	private CoreEntityService coreEntityService;

	@Autowired
	private EntityValidation entityValidation;

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	private CommonEntityValidations commonEntityValidations;

	@Autowired
	private CoreEntityUtil coreEntityUtil;

	@Autowired
	private Features feature;

	@Qualifier("platformEsbClient")
	@Autowired
	private BaseClient platformClient;

	@Value("${platform.esb.domain.resources}")
	private String domainResourcesEndpointUri;

	@Value("${platform.esb.assign.features}")
	private String assignFeaturesResourcesEndpointUri;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/***
	 * @Description This method is responsible to create a tenant
	 * 
	 * @param TenantDTO
	 * @return TenantDTO
	 */
	@Override
	public IdentityDTO createTenant(EntityDTO tenantEntity) {
		/**
		 * 1. validate payload 2. create tenant domain
		 */

		// validate payload
		validateCreateTenantInput(tenantEntity);

		// Validate tenantId
		FieldMapDTO tenantIdMap = new FieldMapDTO();
		tenantIdMap.setKey(TENANT_ID.getFieldName());
		tenantIdMap = fetchField(tenantEntity.getFieldValues(), tenantIdMap);
		if (isBlank(tenantIdMap.getValue())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        TENANT_ID.getDescription());
		}
		// Validate if tenant Id is alphanumeric
		validateAlphaNumericString(tenantIdMap.getValue(),
		        TENANT_ID.getDescription());

		// fetch parent domain either from context or from payload
		// Boolean domainFrmContext = Boolean.FALSE.booleanValue();
		String domain = null;
		if (tenantEntity.getDomain() != null) {
			com.pcs.alpine.services.dto.DomainDTO domainDTO = tenantEntity
			        .getDomain();
			if (StringUtils.isNotEmpty(domainDTO.getDomainName())) {
				domain = domainDTO.getDomainName();
			}
		} else {
			// TODO domain fetched from subscription validity check required
			domain = subscriptionProfileService.getEndUserDomain();
			// domainFrmContext = Boolean.TRUE.booleanValue();
			// create domainString using above domain
		}
		String parentDomainName = domain;
		logger.info("Parent domain : " + parentDomainName);

		// fetch or validate parent entity
		// EntityDTO parentEntity = null;
		// try {
		// parentEntity = coreEntityService.getDomainEntity(parentDomainName);
		// } catch (GalaxyException e) {
		// if (e.getMessage().equalsIgnoreCase(
		// GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
		// throw new GalaxyException(
		// GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
		// EMDataFields.PARENT_DOMAIN.getDescription());
		// } else {
		// throw e;
		// }
		// }

		// validate template (sanityCheck) : (requires domain and
		// globalEntityType), validate template under parent
		EntityTemplateDTO templateDTO = new EntityTemplateDTO();
		com.pcs.alpine.services.dto.DomainDTO domainDTO = new com.pcs.alpine.services.dto.DomainDTO();
		domainDTO.setDomainName(parentDomainName);
		templateDTO.setDomain(domainDTO);
		tenantEntity.setEntityTemplate(templateDTO);
		tenantEntity.setDomain(domainDTO);

		// fetch tenantName to create own domain
		List<FieldMapDTO> fields = tenantEntity.getFieldValues();
		FieldMapDTO tenantId = new FieldMapDTO();
		tenantId.setKey(TENANT_ID.getVariableName());
		tenantId.setValue(fields.get(fields.indexOf(tenantId)).getValue());

		// create own domain
		String ownDomain = createDomainFromTenantId(tenantId.getValue());

		// 7. create TENANT in platform
		FieldMapDTO domainMap = new FieldMapDTO();
		domainMap.setKey(EMDataFields.DOMAIN.getVariableName());
		domainMap.setValue(ownDomain);

		// validate domain
		isValidDomainForIS(domainMap.getValue());
		fields.remove(domainMap);
		fields.add(domainMap);
		tenantEntity.setFieldValues(fields);

		tenantEntity = entityValidation.validateTemplate(tenantEntity, false);

		tenantEntity.getDomain().setDomainName(parentDomainName);

		// create domain in platform and in IS

		// TODO uncomment this
		com.pcs.alpine.is.client.dto.DomainDTO domainPayload = createDomainPayload(
		        tenantEntity, ownDomain);
		try {
			domainService.createDomain(domainPayload);
		} catch (GalaxyException ge) {
			if (ge.getCode().equals(
			        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST.getCode())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
				        EMDataFields.TENANT_ID.getDescription());
			}
		}
		EntityDTO savedTenantEntity = coreEntityService
		        .saveEntity(tenantEntity);
		return createResultDTOForCreateTenantResponse(coreEntityService
		        .getEntity(new IdentityDTO(savedTenantEntity)));
	}

	/***
	 * @Description This method is responsible to retrieve a tenant
	 * 
	 * @param tenant
	 *            identifier fields
	 * @return tenantEntityDTO
	 */
	@Override
	public EntityDTO findTenant(IdentityDTO tenantEntity) {
		/**
		 * 1. validate input 2. set globalEntityType as TENANT 3. fetch entity
		 */

		// 1. validate input and 2. set globalEntityType as TENANT
		tenantEntity.setDomain(setDomain(tenantEntity.getDomain()));
		tenantEntity.setPlatformEntity(setEntityType());
		tenantEntity.setEntityTemplate(getTenantGlobalTemplate());
		commonEntityValidations.validateIdentifierFields(tenantEntity);

		// Validate if logged in tenant is same as the tenant in request
		if (tenantEntity.getIsParentDomain() != null
		        && tenantEntity.getIsParentDomain()) {
			validateMyDomain(tenantEntity.getIdentifier().getValue());
		}

		// 3. fetch entity
		// coreEntityService.getEntity(new IdentityDTO(tenantEntity));
		return createResultDTOForFindTenantResponse(coreEntityService
		        .getEntity(tenantEntity));
	}

	/***
	 * @Description This method is responsible to retrieve a tenant
	 * 
	 * @param tenant
	 *            identifier fields
	 * @return tenantEntityDTO
	 */
	@Override
	public List<EntityDTO> findAllTenant(IdentityDTO tenantEntity) {
		/**
		 * 1. validate input 2. set globalEntityType as TENANT 3. fetch entity
		 */
		// validate if it is parent
		isParentDomainAccessible(tenantEntity.getIsParentDomain());

		// 1. validate input and 2. set globalEntityType as TENANT
		tenantEntity.setDomain(setDomain(tenantEntity.getDomain()));
		tenantEntity.setPlatformEntity(setEntityType());
		tenantEntity.setEntityTemplate(getTenantGlobalTemplate());
		// commonEntityValidations.validateIdentifierFields(tenantEntity);

		// 3. fetch entity
		List<EntityDTO> entities = null;
		EntitySearchDTO coreEntitySearchDTO = new EntitySearchDTO();
		coreEntitySearchDTO.setDomain(tenantEntity.getDomain());
		coreEntitySearchDTO.setPlatformEntity(tenantEntity.getPlatformEntity());
		entities = coreEntityService
		        .getEntityDetailsWithDataprovider(coreEntitySearchDTO);
		validateResult(entities);
		return entities;
	}

	/***
	 * @Description This method is responsible to update a tenant
	 * 
	 * @param TenantDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public StatusMessageDTO updateTenant(EntityDTO tenantEntity) {

		StatusMessageDTO updateStatus = new StatusMessageDTO();
		updateStatus.setStatus(Status.FAILURE);

		// Check for domain name in payload and set type as marker
		tenantEntity.setDomain(setDomain(tenantEntity.getDomain()));
		EntityTemplateDTO entityTemplateDTO = getTenantGlobalTemplate();
		tenantEntity.setEntityTemplate(entityTemplateDTO);
		tenantEntity.getEntityTemplate().setDomain(tenantEntity.getDomain());
		PlatformEntityDTO globalEntity = setEntityType();
		tenantEntity.setPlatformEntity(globalEntity);

		validateUpdateTenant(tenantEntity);

		// Validate if logged in tenant is same as the tenant in request
		if (tenantEntity.getIsParentDomain() != null
		        && tenantEntity.getIsParentDomain()) {
			validateMyDomain(tenantEntity.getIdentifier().getValue());
		}

		// Global entity type is Tenant
		tenantEntity.getEntityTemplate().setPlatformEntityType(
		        globalEntity.getPlatformEntityType());
		tenantEntity.setPlatformEntity(globalEntity);
		// creating IdentityDTO from EntityDTO to get the unique entity from DB
		IdentityDTO identityDTO = new IdentityDTO();
		identityDTO.setDomain(tenantEntity.getEntityTemplate().getDomain());
		identityDTO.setPlatformEntity(globalEntity);
		identityDTO.setEntityTemplate(tenantEntity.getEntityTemplate());
		identityDTO.setIdentifier(tenantEntity.getIdentifier());

		// Get entry to be updated from DB
		EntityDTO entityFromDB = coreEntityService.getEntity(identityDTO);

		// domain and tenantName, couldnot be updated
		List<String> fieldNames = new ArrayList<String>();
		fieldNames.add(EMDataFields.TENANT_ID.getVariableName());
		fieldNames.add(EMDataFields.DOMAIN.getVariableName());
		fieldsCannotBeUpdated(tenantEntity, entityFromDB, fieldNames);

		// set the domain(Will be use to construct IdentityDTO in
		// validateTemplate() method)
		tenantEntity.setDomain(identityDTO.getEntityTemplate().getDomain());

		// set the parent entity id to be used in uniqueAcrossHierarchy
		// FieldMapDTO identifierField = new FieldMapDTO();
		// identifierField = entityFromDB.getIdentifier();
		List<FieldMapDTO> fieldValues = tenantEntity.getFieldValues();

		// fieldValues.add(identifierField);
		tenantEntity.setFieldValues(fieldValues);
		Boolean isUpdate = true;
		EntityDTO tenantEntityDTO = entityValidation.validateTemplate(
		        tenantEntity, isUpdate);

		// setting the updated fieldvalues
		entityFromDB.setFieldValues(tenantEntityDTO.getFieldValues());

		// setting the updated status
		if (tenantEntityDTO.getEntityStatus() != null
		        && isNotBlank(tenantEntityDTO.getEntityStatus().getStatusName())) {
			entityFromDB.setEntityStatus(tenantEntityDTO.getEntityStatus());
		}

		// setting the updated data provider
		entityFromDB.setDataprovider(tenantEntityDTO.getDataprovider());

		// setting the updated tree
		EntityDTO returnedTenantEntity = this.coreEntityService
		        .updateEntity(entityFromDB);

		if (returnedTenantEntity != null) {
			updateStatus.setStatus(Status.SUCCESS);
		}
		/*
		 * // 1. validate input and 2. set globalEntityType as TENANT
		 * validateUpdateTenantInput(tenantEntity); // 3. validate template for
		 * update(sanityCheck) entityValidation.validateTemplate(tenantEntity,
		 * true); // tenantName cannot be updated // TODO //tenantEntity =
		 * removeFieldFromReq(tenantEntity,
		 * EMDataFields.TENANT_NAME.getVariableName()); tenantEntity =
		 * removeFieldFromReq(tenantEntity,
		 * EMDataFields.DOMAIN.getVariableName()); // domain update is not
		 * required // 4. update tenant
		 * tenantEntity.setEntityId(coreEntityService.getEntity( new
		 * IdentityDTO(tenantEntity)).getEntityId()); EntityDTO returnedEntity =
		 * coreEntityService.updateEntity(tenantEntity); return
		 * createResultDTOForUpdateTenantResponse(coreEntityService
		 * .getEntity(new IdentityDTO(returnedEntity)));
		 */
		return updateStatus;
	}

	/***
	 * @Description This method is responsible to delete a tenant
	 * 
	 * @param tenant
	 *            identifier fields
	 * @return Status
	 */
	@Override
	public StatusMessageDTO deleteTenant(IdentityDTO tenantEntity) {

		/**
		 * 1. validate input 2. set globalEntityType as TENANT 3. fetch entityId
		 * 4. delete entity by entityId 5. delete domain
		 */

		// 1. validate input and 2. set globalEntityType as TENANT
		tenantEntity.setDomain(setDomain(tenantEntity.getDomain()));
		tenantEntity.setPlatformEntity(setEntityType());
		commonEntityValidations.validateIdentifierFields(tenantEntity);

		// Validate if logged in tenant is same as the tenant in request
		if (tenantEntity.getIsParentDomain() != null
		        && tenantEntity.getIsParentDomain()) {
			validateMyDomain(tenantEntity.getIdentifier().getValue());
		}

		// 3. fetch entityId
		EntityDTO entityDTO = coreEntityService.getEntity(tenantEntity);
		// 4. delete entity by entityId

		String deleteStatus = Status.DELETED.name();
		StatusMessageDTO deleteEntityStatus = coreEntityService.updateStatus(
		        tenantEntity, deleteStatus);

		// 5. delete domain
		com.pcs.alpine.services.dto.DomainDTO domainDTO = new com.pcs.alpine.services.dto.DomainDTO();
		FieldMapDTO domainFieldMap = new FieldMapDTO();
		domainFieldMap.setKey(EMDataFields.DOMAIN.getVariableName());
		domainDTO.setDomainName(fetchField(entityDTO.getFieldValues(),
		        domainFieldMap).getValue());
		StatusMessageDTO deleteDomainStatus = domainService
		        .deleteDomain(domainDTO);

		if (deleteEntityStatus.getStatus().getStatus()
		        .equals(deleteDomainStatus.getStatus().getStatus())) {
			return deleteEntityStatus;
		} else {
			deleteEntityStatus.setStatus(Status.FAILURE);
			return deleteEntityStatus;
		}
	}

	/***
	 * @Description This method is responsible to check the tenants for
	 *              uniqueness
	 * 
	 * @param EntitySearchDTO
	 * 
	 * @return Status
	 */
	@Override
	public StatusMessageDTO validateUniqueness(
	        EntitySearchDTO coreEntitySearchDTO) {
		// set global entity type is marker
		coreEntitySearchDTO.setPlatformEntity(setEntityType());
		// Check for domain name in payload and set type as TENANT
		if (coreEntitySearchDTO.getUniqueAcross() != null) {
			if (isNotBlank(coreEntitySearchDTO.getUniqueAcross())
			        && (!(coreEntitySearchDTO.getUniqueAcross()
			                .equalsIgnoreCase(APPLICATION.getFieldName())) && !(coreEntitySearchDTO
			                .getUniqueAcross().equalsIgnoreCase(DOMAIN
			                .getFieldName())))) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        UNIQUE_ACCESS.getDescription());
			}

		}

		if (coreEntitySearchDTO.getUniqueAcross() != null
		        && coreEntitySearchDTO.getUniqueAcross().equalsIgnoreCase(
		                APPLICATION.getFieldName())) {
			coreEntitySearchDTO.setDomain(null);
		} else {
			coreEntitySearchDTO.setDomain(setDomain(coreEntitySearchDTO
			        .getDomain()));
		}
		validateUniquenessPayload(coreEntitySearchDTO);

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		try {
			coreEntityService.getEntityByField(coreEntitySearchDTO);

		} catch (GalaxyException galaxyException) {
			// 500 error code indicates no data exists
			if (galaxyException.getCode() == 500) {
				statusMessageDTO.setStatus(Status.SUCCESS);
			}
		}
		return statusMessageDTO;
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

	private EntityDTO validateCreateTenantInput(EntityDTO tenantDTO) {

		ValidationUtils.validateMandatoryFields(tenantDTO);
		// validate fieldValues
		ValidationUtils.validateMandatoryFields(tenantDTO,
		        EMDataFields.FIELD_VALUES);
		// validate the size of fieldValues
		ValidationUtils.validateCollection(EMDataFields.FIELD_VALUES,
		        tenantDTO.getFieldValues());

		// validate entityTemplateName
		/*
		 * ValidationUtils.validateMandatoryFields(tenantDTO,
		 * EMDataFields.ENTITY_TEMPLATE);
		 * ValidationUtils.validateMandatoryFields
		 * (tenantDTO.getEntityTemplate(), EMDataFields.ENTITY_TEMPLATE_NAME);
		 */

		// globalEntityType is optional, by default it would be TENANT
		// globalEntityType should be TENANT
		if (tenantDTO.getPlatformEntity() != null) {
			String type = tenantDTO.getPlatformEntity().getPlatformEntityType();
			ValidationUtils.validateMandatoryField(
			        EMDataFields.PLATFORM_ENTITY_TYPE, type);
			if (!EMDataFields.TENANT.name().equals(type)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        EMDataFields.PLATFORM_ENTITY_TYPE.getDescription());
			}
			PlatformEntityDTO globalEntityDTO = globalEntityService
			        .getGlobalEntityWithName(EMDataFields.TENANT.name());
			tenantDTO.setPlatformEntity(globalEntityDTO);
		} else {
			PlatformEntityDTO globalEntityDTO = globalEntityService
			        .getGlobalEntityWithName(EMDataFields.TENANT.name());
			tenantDTO.setPlatformEntity(globalEntityDTO);
		}

		// entityStatus should be ACTIVE if present, else by default it would be
		// ACTIVE
		if (tenantDTO.getEntityStatus() != null
		        && isNotBlank(tenantDTO.getEntityStatus().getStatusName())) {
			// check entityStatus is valid, either ACTIVE or INACTIVE
			if (!(tenantDTO.getEntityStatus().getStatusName()
			        .equalsIgnoreCase(Status.ACTIVE.name()))
			        && !(tenantDTO.getEntityStatus().getStatusName()
			                .equalsIgnoreCase(Status.INACTIVE.name()))) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        CommonConstants.STATUS_NAME);
			}
		} else {
			StatusDTO entityStatus = new StatusDTO();
			entityStatus.setStatusName(Status.ACTIVE.name());
			tenantDTO.setEntityStatus(entityStatus);
		}

		return tenantDTO;
	}

	private EntityDTO validateUpdateTenantInput(EntityDTO tenantDTO) {
		tenantDTO = validateIdentityFieldsInInput(tenantDTO);

		ValidationUtils.validateMandatoryFields(tenantDTO,
		        EMDataFields.FIELD_VALUES);

		// validate the size of fieldValues
		ValidationUtils.validateCollection(EMDataFields.FIELD_VALUES,
		        tenantDTO.getFieldValues());
		EntityTemplateDTO templateDTO = tenantDTO.getEntityTemplate();
		templateDTO.setDomain(tenantDTO.getDomain());

		return tenantDTO;

	}

	private EntityDTO validateIdentityFieldsInInput(EntityDTO tenantDTO) {
		ValidationUtils.validateMandatoryFields(tenantDTO);

		// validate mandatory fields in input
		ValidationUtils.validateMandatoryFields(tenantDTO, EMDataFields.DOMAIN,
		        EMDataFields.ENTITY_TEMPLATE, EMDataFields.IDENTIFIER);

		ValidationUtils.validateMandatoryFields(tenantDTO.getDomain(),
		        EMDataFields.DOMAIN_NAME);

		ValidationUtils.validateMandatoryFields(tenantDTO.getEntityTemplate(),
		        EMDataFields.ENTITY_TEMPLATE_NAME);

		ValidationUtils.validateMandatoryFields(tenantDTO.getIdentifier(),
		        EMDataFields.IDENTIFIER_KEY, EMDataFields.IDENTIFIER_KEY);

		// globalEntityType is optional, by default it would be TENANT
		// globalEntityType should be TENANT
		if (tenantDTO.getPlatformEntity() != null) {
			String type = tenantDTO.getPlatformEntity().getPlatformEntityType();
			ValidationUtils.validateMandatoryField(
			        EMDataFields.PLATFORM_ENTITY_TYPE, type);
			if (!EMDataFields.TENANT.name().equals(type)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        EMDataFields.PLATFORM_ENTITY_TYPE.getDescription());
			}
			PlatformEntityDTO globalEntityDTO = globalEntityService
			        .getGlobalEntityWithName(EMDataFields.TENANT.name());
			tenantDTO.setPlatformEntity(globalEntityDTO);
		} else {
			PlatformEntityDTO globalEntityDTO = globalEntityService
			        .getGlobalEntityWithName(EMDataFields.TENANT.name());
			tenantDTO.setPlatformEntity(globalEntityDTO);
		}

		return tenantDTO;
	}

	private String createDomainFromTenantId(String prefix) {

		String domainName = String.format(CommonConstants.STRING_FORMAT,
		        prefix, CommonConstants.DOT, CommonConstants.GALAXY);
		// IS does not allow domain to be created with & and space, hence
		// replacing it
		// with underscore
		// replace space
		domainName = domainName.replaceAll("\\s+", "");
		return domainName.replace("&", "_");
	}

	// private String createOwnDomain(Boolean domainFrmContext,
	// List<FieldMapDTO> fields, FieldMapDTO tenantNameMap,
	// String parentDomainName) {
	// String ownDomain = null;
	// // if field values contains domainName, using that as own domain
	// // else will be created by prefixing tenantName to parentDomain
	// FieldMapDTO domainNameMap = new FieldMapDTO();
	// domainNameMap.setKey(EMDataFields.DOMAIN.getVariableName());
	// if (domainFrmContext) {
	// if (fields.indexOf(domainNameMap) != -1) {
	// domainNameMap.setValue(fields
	// .get(fields.indexOf(domainNameMap)).getValue());
	// // domain is present in fieldValues, use as it is
	// // //validate for IS allowed char
	// // isValidDomainForIS(domainNameMap.getValue());
	// if (StringUtils.isBlank(domainNameMap.getValue())) {
	// // domain is not present in fieldValues, use tenantName
	// ownDomain = createDomainFromTenantName(tenantNameMap
	// .getValue());
	// } else {
	// ownDomain = createDomainFromTenantName(domainNameMap
	// .getValue());
	// }
	// } else {
	// // domain is not present in fieldValues, use tenantName
	// ownDomain = createDomainFromTenantName(tenantNameMap.getValue());
	// }
	// } else {
	// if (fields.indexOf(domainNameMap) != -1) {
	// domainNameMap.setValue(fields
	// .get(fields.indexOf(domainNameMap)).getValue());
	// // domain is present in fieldValues, use as it is
	//
	// if (StringUtils.isNotBlank(domainNameMap.getValue())) {
	// ownDomain = createDomainFromTenantName(domainNameMap
	// .getValue());
	// } else {
	// // domain is not present in fieldValues, use tenantName
	// ownDomain = createDomainFromTenantName(tenantNameMap
	// .getValue());
	// }
	//
	// } else {
	// // domain is not present in fieldValues, use tenantName
	// ownDomain = createDomainFromTenantName(tenantNameMap.getValue());
	// }
	// }
	//
	// return ownDomain;
	// }

	private com.pcs.alpine.is.client.dto.DomainDTO createDomainPayload(
	        EntityDTO tenantEntity, String ownDomain) {
		com.pcs.alpine.is.client.dto.DomainDTO domainDto = new com.pcs.alpine.is.client.dto.DomainDTO();
		domainDto.setDomainName(ownDomain);
		domainDto.setEntity(tenantEntity);
		return domainDto;
	}

	private IdentityDTO createResultDTOForCreateTenantResponse(
	        EntityDTO tenantEntity) {
		IdentityDTO resultEntity = new IdentityDTO();
		resultEntity.setPlatformEntity(tenantEntity.getPlatformEntity());
		resultEntity.setDomain(tenantEntity.getDomain());
		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName(tenantEntity.getEntityStatus()
		        .getStatusName());
		EntityTemplateDTO template = new EntityTemplateDTO();
		template.setEntityTemplateName(tenantEntity.getEntityTemplate()
		        .getEntityTemplateName());
		resultEntity.setEntityTemplate(template);
		resultEntity.setIdentifier(tenantEntity.getIdentifier());
		return resultEntity;
	}

	private EntityDTO createResultDTOForFindTenantResponse(
	        EntityDTO tenantEntity) {
		EntityDTO resultEntity = new EntityDTO();
		resultEntity.setPlatformEntity(tenantEntity.getPlatformEntity());
		resultEntity.setDomain(tenantEntity.getDomain());
		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName(tenantEntity.getEntityStatus()
		        .getStatusName());
		resultEntity.setEntityStatus(entityStatus);
		EntityTemplateDTO template = new EntityTemplateDTO();
		template.setEntityTemplateName(tenantEntity.getEntityTemplate()
		        .getEntityTemplateName());
		resultEntity.setEntityTemplate(template);
		resultEntity.setFieldValues(tenantEntity.getFieldValues());
		resultEntity.setIdentifier(tenantEntity.getIdentifier());
		return resultEntity;
	}

	private EntityDTO createResultDTOForUpdateTenantResponse(
	        EntityDTO tenantEntity) {
		return createResultDTOForFindTenantResponse(tenantEntity);
	}

	private void validateUniquenessPayload(EntitySearchDTO entitySearchDTO) {

		if (entitySearchDTO.getUniqueAcross() != null
		        && entitySearchDTO.getUniqueAcross().equalsIgnoreCase(
		                APPLICATION.getFieldName())) {
			validateMandatoryFields(entitySearchDTO, FIELD_VALUES);

		} else {
			validateMandatoryFields(entitySearchDTO, DOMAIN, FIELD_VALUES);

			validateMandatoryFields(entitySearchDTO.getDomain(), DOMAIN_NAME);
		}

		ValidationUtils.validateCollection(EMDataFields.FIELD_VALUES,
		        entitySearchDTO.getFieldValues());

		validateMandatoryFields(entitySearchDTO.getFieldValues().get(0),
		        FIELD_KEY, FIELD_VALUE);

	}

	private PlatformEntityDTO setEntityType() {
		PlatformEntityDTO type = globalEntityService
		        .getGlobalEntityWithName(EMDataFields.TENANT.getFieldName());
		return type;
	}

	private DomainDTO setDomain(DomainDTO domain) {
		if (domain == null) {
			domain = new DomainDTO();
		}
		if (domain.getDomainName() == null || domain.getDomainName().isEmpty()) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			domain.setDomainName(domainName);
		}
		return domain;
	}

	private void validateTemplate(EntityDTO entityDTO) {
		validateMandatoryFields(entityDTO, ENTITY_TEMPLATE);
		validateMandatoryFields(entityDTO.getEntityTemplate(),
		        EMDataFields.ENTITY_TEMPLATE_NAME);
	}

	private void validateUpdateTenant(EntityDTO entityDTO) {
		validateTemplate(entityDTO);
		commonEntityValidations.validateMandatoryForEntityUpdate(entityDTO);
	}

	/**
	 * remove tenantName from fieldValues
	 * 
	 * @param tenantEntity
	 */
	private void fieldsCannotBeUpdated(EntityDTO tenantEntity,
	        EntityDTO entityFromDB, List<String> fieldNames) {

		List<FieldMapDTO> fieldValues = tenantEntity.getFieldValues();
		List<FieldMapDTO> dbFieldValues = entityFromDB.getFieldValues();

		for (String fieldName : fieldNames) {
			FieldMapDTO field = new FieldMapDTO();
			field.setKey(fieldName);
			int fieldIndex = fieldValues.indexOf(field);
			if (fieldIndex != -1) {

				if (!fieldValues.get(fieldIndex).getValue()
				        .equals(fetchField(dbFieldValues, field).getValue())) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED,
					        EMDataFields.getDataField(fieldName)
					                .getDescription());
				}
			} else {
				fieldValues.add(fetchField(dbFieldValues, field));
			}
		}
	}

	@Override
	public EntityCountDTO getTenantCount(EntitySearchDTO tenantSearch) {

		// validate if it is parent
		isParentDomainAccessible(tenantSearch.getIsParentDomain());
		// check for domain, if not present fetch from logged in domain
		tenantSearch.setDomain(setDomain(tenantSearch.getDomain()));
		// set entity type as TENANT
		PlatformEntityDTO globalEntityDTO = new PlatformEntityDTO();
		globalEntityDTO.setPlatformEntityType(TENANT.name());
		tenantSearch.setPlatformEntity(globalEntityDTO);
		Integer count = 0;
		count = coreEntityService.getEntitiesCountByTypeAndDomain(tenantSearch);
		EntityCountDTO countDTO = new EntityCountDTO();
		countDTO.setCount(count);
		return countDTO;
	}

	/***
	 * @Description This method is responsible to get tenant and domain info by
	 *              domain name
	 * 
	 * @param domainName
	 * 
	 * @return domainEntityDTO
	 */
	@Override
	public EntityDTO getTenantInfo() {

		String domainName = subscriptionProfileService.getEndUserDomain();

		if (isBlank(domainName)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        CommonConstants.DOMAIN_NAME);
		}
		return createResultDTOForGetTenantInfoResponse(coreEntityService
		        .getDomainEntity(domainName));
	}

	private EntityDTO createResultDTOForGetTenantInfoResponse(
	        EntityDTO domainEntity) {
		EntityDTO resultEntity = new EntityDTO();
		resultEntity.setPlatformEntity(domainEntity.getPlatformEntity());
		resultEntity.setDomain(domainEntity.getDomain());
		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName(domainEntity.getEntityStatus()
		        .getStatusName());
		resultEntity.setEntityStatus(entityStatus);
		EntityTemplateDTO template = new EntityTemplateDTO();
		template.setEntityTemplateName(domainEntity.getEntityTemplate()
		        .getEntityTemplateName());
		resultEntity.setEntityTemplate(template);
		resultEntity.setFieldValues(domainEntity.getFieldValues());
		resultEntity.setDataprovider(domainEntity.getDataprovider());
		resultEntity.setIdentifier(domainEntity.getIdentifier());
		IdentityDTO identityDTO = new IdentityDTO();
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(subscriptionProfileService.getEndUserDomain());
		identityDTO.setDomain(domainDTO);
		resultEntity.setHierarchy(identityDTO);
		return resultEntity;
	}

	private EntityTemplateDTO getTenantGlobalTemplate() {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		PlatformEntityTemplateDTO globalEntityTemplateDTO = globalEntityTemplateService
		        .getPlatformEntityTemplate(TENANT.getVariableName());
		entityTemplateDTO.setEntityTemplateName(globalEntityTemplateDTO
		        .getPlatformEntityTemplateName());
		return entityTemplateDTO;
	}

	private void isValidDomainForIS(String value) {
		if (!value.matches("[a-zA-Z0-9_.+-]+")) {
			// throw exception, The domain only allows letters, numbers, '.',
			// '-' and '_'
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        CommonConstants.DOMAIN_NAME);
		}
	}

	private void isParentDomainAccessible(Boolean isParentDomain) {
		if (isParentDomain != null && isParentDomain) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	// TODO change the identifierKey to clientName
	private void validateMyDomain(String tenantName) {
		// Get logged in user's tenant
		EntityDTO tenant = getTenantInfo();

		// Extract the tenant name
		FieldMapDTO tenantNameMap = new FieldMapDTO();
		tenantNameMap.setKey(TENANT_ID.getVariableName());
		tenantNameMap
		        .setValue(tenant.getFieldValues()
		                .get(tenant.getFieldValues().indexOf(tenantNameMap))
		                .getValue());

		// Validate that both the tenant names are the same
		if (!tenantName.equalsIgnoreCase(tenantNameMap.getValue())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_TENANT);
		}
	}

	private void validateAlphaNumericString(String name, String fieldDesc) {
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED, fieldDesc);
		}
	}

	private void validateUniqueAccess(String uniqueAcross) {
		// Validate the uniqueAcross
		if (isNotBlank(uniqueAcross)
		        && (!(uniqueAcross.equalsIgnoreCase(APPLICATION.getFieldName())) && !(uniqueAcross
		                .equalsIgnoreCase(DOMAIN.getFieldName())))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        UNIQUE_ACCESS.getDescription());
		}
	}

	@Override
	public List<String> getAllFeatures(String domain) {
		PermissionGroupsDTO permissions = new PermissionGroupsDTO();

		if (isBlank(domain)) {
			permissions = platformClient.get(domainResourcesEndpointUri,
			        getJwtToken(), PermissionGroupsDTO.class);
		} else {
			permissions = platformClient.get(domainResourcesEndpointUri
			        + "?domain=" + domain, getJwtToken(),
			        PermissionGroupsDTO.class);
		}

		List<FeatureDTO> features = feature.getAllFeatures();

		HashMap<String, String> feartureMap = new HashMap<String, String>();
		Map<String, String> hm = new HashMap<String, String>();

		for (FeatureDTO featureDTO : features) {

			for (String resource : featureDTO.getResources()) {
				if (!feartureMap.containsKey(resource)) {
					feartureMap.put(resource, featureDTO.getFeature());
				}
			}
		}
		for (String resourceName : permissions.getResources()) {
			if (feartureMap.containsKey(resourceName)) {
				hm.put(feartureMap.get(resourceName), resourceName);
			}
		}
		List<String> featureList = new ArrayList<String>(hm.keySet());
		return featureList;
	}

	@Override
	public StatusMessageDTO assignFeatures(List<String> features, String domain) {

		// Validate the input
		if (isBlank(domain)) {
			domain = subscriptionProfileService.getEndUserDomain();
		}
		if (features == null) {
			features = new ArrayList<String>();
		}
		// Add Tenant Management and User Management, Tag Management and
		// Geo-service Management Features
		if (!features.contains(TENANT_MANAGEMENT)) {
			features.add(TENANT_MANAGEMENT);
		}
		if (!features.contains(USER_MANAGEMENT)) {
			features.add(USER_MANAGEMENT);
		}
		if (!features.contains(TAG_MANAGEMENT)) {
			features.add(TAG_MANAGEMENT);
		}
		if (!features.contains(GEOSERVICE_MANAGEMENT)) {
			features.add(GEOSERVICE_MANAGEMENT);
		}

		// Check if all resources are assigned to the tenant
		List<String> featureAvailable = getAllFeatures(null);

		// Retain only those features which are available to the tenant
		features.retainAll(featureAvailable);

		// Logged in tenant does not have access to specified features
		if (CollectionUtils.isEmpty(features)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        FEATURE_LIST.getDescription());
		}

		// Get the available list
		List<FeatureDTO> availableFeatures = feature.getAllFeatures();

		FeatureDTO featuresToAssign = new FeatureDTO();
		DomainDTO domainDto = new DomainDTO();
		domainDto.setDomainName(domain);
		featuresToAssign.setDomain(domainDto);

		List<String> resources = new ArrayList<String>();
		List<com.pcs.alpine.commons.dto.EntityTemplateDTO> entityTemplates = new ArrayList<com.pcs.alpine.commons.dto.EntityTemplateDTO>();

		// Construct input payload
		for (FeatureDTO featureDTO : availableFeatures) {

			for (String feature : features) {

				if (featureDTO.getFeature().equalsIgnoreCase(feature)) {
					if (CollectionUtils.isNotEmpty(featureDTO.getResources())) {
						resources.addAll(featureDTO.getResources());
					}
					if (CollectionUtils.isNotEmpty(featureDTO.getTemplates())) {
						entityTemplates.addAll(featureDTO.getTemplates());
					}
				}
			}
		}
		featuresToAssign.setResources(resources);
		featuresToAssign.setTemplates(entityTemplates);

		// Invoke ESB endpoint
		StatusMessageDTO assignStatus = platformClient.post(
		        assignFeaturesResourcesEndpointUri, getJwtToken(),
		        featuresToAssign, StatusMessageDTO.class);
		return assignStatus;
	}

}
