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
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATES;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALIDATION;
import static com.pcs.alpine.services.enums.EMDataFields.HTML_PAGE;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER_FIELD;
import static com.pcs.alpine.services.enums.EMDataFields.IS_MODIFIABLE;
import static com.pcs.alpine.services.enums.EMDataFields.IS_SHARABLE;
import static com.pcs.alpine.services.enums.EMDataFields.MARKER;
import static com.pcs.alpine.services.enums.EMDataFields.PARENT_TEMPLATE;
import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY;
import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.EMDataFields.STATUS;
import static com.pcs.alpine.services.enums.EMDataFields.USER;
import static com.pcs.alpine.services.enums.Status.ACTIVE;
import static com.pcs.alpine.services.enums.Status.DELETED;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.utils.UUIDs;
import com.google.gson.Gson;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.util.ObjectBuilderUtil;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.constant.CommonConstants;
import com.pcs.alpine.enums.GalaxyValidationDataFields;
import com.pcs.alpine.model.EntityTemplate;
import com.pcs.alpine.repo.utils.CoreEntityUtil;
import com.pcs.alpine.serviceImpl.repository.EntityTemplateRepository;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.PlatformEntityService;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;
import com.pcs.alpine.services.dto.ValidationJsonStringDTO;
import com.pcs.alpine.services.dto.ValidationTestDTO;
import com.pcs.alpine.services.enums.EMDataFields;
import com.pcs.alpine.services.enums.Status;
import com.pcs.alpine.services.exception.GalaxyEMErrorCodes;

/**
 * TemplateServiceImpl
 * 
 * @description Implementation Class for Template Services
 * @author Anusha Saju (pcseg257)
 * @date 10 Aug 2014
 * @since galaxy-1.0.0
 */
@Service
public class EntityTemplateServiceImpl implements EntityTemplateService {

	@Autowired
	private EntityTemplateRepository entityTemplateRepository;

	@Autowired
	private StatusService statusService;

	@Autowired
	private PlatformEntityService globalEntityService;

	@Autowired
	private PlatformEntityTemplateService globalEntityTemplateService;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	private CoreEntityService coreEntityService;

	@Autowired
	CoreEntityUtil coreEntityUtil;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @Description Responsible for inserting a template
	 * @param entityDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public EntityTemplateDTO saveTemplate(EntityTemplateDTO entityTemplateDTO) {

		entityTemplateDTO = validateTemplate(entityTemplateDTO);

		isParentDomain(entityTemplateDTO.getIsParentDomain());

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		Integer statusKey = null;
		EntityTemplate entityTemplate = null;
		EntityTemplateDTO saveDTO = null;

		statusKey = statusService.getStatus(entityTemplateDTO.getStatus()
		        .getStatusName());

		// if parent template is null check for the entry in global entity type

		// check globalEntityType is valid and isDefault flag is false
		checkGlobalEntityType(entityTemplateDTO.getPlatformEntityType());

		// set statusKey
		StatusDTO statusDTO = entityTemplateDTO.getStatus();
		statusDTO.setStatusKey(statusKey);
		entityTemplateDTO.setStatus(statusDTO);

		// check globalEntityTemplateName is correct
		// checkGlobalEntityTemplateName(entityTemplateDTO);

		parentTemplateCheck(entityTemplateDTO);

		checkTemplateNameExists(entityTemplateDTO);

		entityTemplate = getEntityTemplateModel(entityTemplateDTO);
		entityTemplate.setEntityTemplateId(getTimeBasedUUID());

		EntityTemplate enTemplate = this.entityTemplateRepository
		        .saveTemplate(entityTemplate);
		saveDTO = new EntityTemplateDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(enTemplate.getDomain());
		saveDTO.setDomain(domain);
		saveDTO.setEntityTemplateName(enTemplate.getEntityTemplateName());
		return saveDTO;
	}

	private void parentTemplateCheck(EntityTemplateDTO entityTemplateDTO) {

		// validate parent template
		if (StringUtils.isNotBlank(entityTemplateDTO.getParentTemplate())) {

			EntityTemplate parentEntityTemplate = new EntityTemplate();
			parentEntityTemplate.setDomain(entityTemplateDTO.getDomain()
			        .getDomainName());
			parentEntityTemplate.setEntityTemplateName(entityTemplateDTO
			        .getParentTemplate());
			parentEntityTemplate.setPlatformEntityType(MARKER.name());

			// temporary fix : parent template status is assumed to be
			// ACTIVE.
			int parentTemplateStatusKey = statusService.getStatus(Status.ACTIVE
			        .name());
			parentEntityTemplate = this.entityTemplateRepository.getTemplate(
			        parentEntityTemplate, parentTemplateStatusKey);
			if (parentEntityTemplate == null) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        PARENT_TEMPLATE.getDescription());
			}
		}
	}

	private void isParentDomain(Boolean isParentDomain) {
		if (isParentDomain != null && isParentDomain) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	private UUID getTimeBasedUUID() {
		Random random = new Random();
		UUID uuid = new UUID(UUIDs.startOf(System.currentTimeMillis())
		        .getMostSignificantBits(), random.nextLong());
		return uuid;
	}

	/**
	 * @Description Responsible for updating a template
	 * @param entityDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public EntityTemplateDTO updateTemplate(EntityTemplateDTO entityTemplateDTO) {
		EntityTemplateDTO templateDTOFromDB = null;
		// validate input EntityTemplateDTO
		validateUpdateTemplate(entityTemplateDTO);

		templateDTOFromDB = updateCheck(entityTemplateDTO);

		StatusDTO statusDTO = new StatusDTO();
		statusDTO
		        .setStatusKey(statusService.getStatus(Status.ACTIVE.toString()));
		statusDTO.setStatusName(Status.ACTIVE.toString());
		EntityTemplate entityTemplate = getEntityTemplateModel(templateDTOFromDB);

		// uuid should be same as input DTO
		entityTemplate.setDomain(templateDTOFromDB.getDomain().getDomainName());
		entityTemplate.setEntityTemplateName(templateDTOFromDB
		        .getEntityTemplateName());
		this.entityTemplateRepository.updateTemplate(entityTemplate);

		return getEntityTemplateDTO(entityTemplate);
	}

	/**
	 * @Description Responsible for fetching a template with uuid
	 * @param entityDTO
	 * @return List<EntityTemplateDTO>
	 */
	@Deprecated
	@Override
	public EntityTemplateDTO getTemplate(UUID uuid) {
		Integer statusKey = null;
		EntityTemplate entityTemplate = null;
		EntityTemplateDTO customTemplate = null;
		// EntityTemplateKey entityTemplateKey = new EntityTemplateKey();
		if (uuid != null) {
			statusKey = statusService.getStatus(Status.ACTIVE.name());
			entityTemplate = this.entityTemplateRepository.getTemplate(uuid);
			if ((entityTemplate != null)
			        && (entityTemplate.getStatusKey().intValue() == statusKey
			                .intValue())) {
				customTemplate = getEntityTemplateDTO(entityTemplate);
			} else {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
		} else {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        EMDataFields.UUID.getDescription());
		}
		return customTemplate;
	}

	/**
	 * @Description Responsible for fetching a template based on domainName,
	 *              globalEntityType and entityTemplateName
	 * @param entityDTO
	 * @return List<EntityTemplateDTO>
	 */
	@Override
	public EntityTemplateDTO getTemplate(EntityTemplateDTO entityTemplateDTO) {

		EntityTemplate entityTemplate = null;
		EntityTemplateDTO customTemplate = null;

		validateMandatoryFields(entityTemplateDTO, PLATFORM_ENTITY_TYPE,
		        ENTITY_TEMPLATE_NAME);

		DomainDTO domainDTO = new DomainDTO();
		if (entityTemplateDTO.getDomain() != null) {
			if (StringUtils.isEmpty(entityTemplateDTO.getDomain()
			        .getDomainName())) {
				domainDTO.setDomainName(subscriptionProfileService
				        .getEndUserDomain());
				entityTemplateDTO.setDomain(domainDTO);
			}
		} else {
			domainDTO.setDomainName(subscriptionProfileService
			        .getEndUserDomain());
			entityTemplateDTO.setDomain(domainDTO);
		}
		isParentDomain(entityTemplateDTO.getIsParentDomain());
		entityTemplate = getEntityTemplate(entityTemplateDTO);
		if (entityTemplate != null) {
			customTemplate = getEntityTemplateDTO(entityTemplate);
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return customTemplate;
	}

	/**
	 * @param entityTemplateDTO
	 * @return
	 */
	private EntityTemplate getEntityTemplate(EntityTemplateDTO entityTemplateDTO) {
		EntityTemplate entityTemplate;
		Integer statusKey = null;
		statusKey = statusService.getStatus(Status.ACTIVE.name());
		EntityTemplate enTemplate = new EntityTemplate();
		enTemplate.setDomain(entityTemplateDTO.getDomain().getDomainName());
		enTemplate.setPlatformEntityType(entityTemplateDTO
		        .getPlatformEntityType());
		enTemplate.setEntityTemplateName(entityTemplateDTO
		        .getEntityTemplateName());
		String type = entityTemplateDTO.getParentTemplate();
		if (StringUtils.isNotBlank(type)) {
			enTemplate.setParentTemplate(type);
		}

		entityTemplate = this.entityTemplateRepository.getTemplate(enTemplate,
		        statusKey);
		return entityTemplate;
	}

	@Override
	public List<EntityTemplateDTO> findAllTemplate(
	        EntityTemplateDTO entityTemplateDTO) {
		Integer statusKey = null;
		List<EntityTemplateDTO> templates = null;

		validateMandatoryFields(entityTemplateDTO, DOMAIN, PLATFORM_ENTITY_TYPE);

		isParentDomain(entityTemplateDTO.getIsParentDomain());

		// To check template status *generic*
		/*
		 * if
		 * (entityTemplateDTO.getStatus().getStatusName().equalsIgnoreCase(Status
		 * .ALL.toString())){ //get the highest Status KEY for an EntityTemplate
		 * statusKey = statusService.getStatus(Status.INACTIVE.toString()); }
		 * else { statusKey =
		 * statusService.getStatus(entityTemplateDTO.getStatus
		 * ().getStatusName()); }
		 */

		statusKey = statusService.getStatus(Status.INACTIVE.toString());
		List<EntityTemplate> entityTemplates = null;

		// To get templates if both active and inactive are required
		// if (statusKey == 1 &&
		// (entityTemplateDTO.getStatus().getStatusName().equalsIgnoreCase(Status.ALL.toString())))
		// {
		entityTemplates = this.entityTemplateRepository
		        .getTemplateByDomainAndType(entityTemplateDTO.getDomain()
		                .getDomainName(), entityTemplateDTO
		                .getPlatformEntityType(), statusKey);
		/*
		 * } else { entityTemplates = this.entityTemplateRepository
		 * .getTemplateByDomainAndTypeAndStatus(entityTemplateDTO.getDomain()
		 * .getDomainName(), entityTemplateDTO .getGlobalEntityType(),
		 * statusKey); }
		 */

		if (CollectionUtils.isNotEmpty(entityTemplates)) {
			templates = getEntityTemplateDTOs(entityTemplates);
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return templates;
	}

	/**
	 * @Description Responsible for deleting a template
	 * @param entityDTO
	 * @return StatusMessageDTO
	 */
	@Deprecated
	@Override
	public StatusMessageDTO deleteTemplate(UUID uuid) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		Integer statusKey = null;
		if (uuid == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        EMDataFields.UUID.getDescription());
		}
		statusKey = statusService.getStatus(Status.DELETED.name());
		EntityTemplate entityTemplate = this.entityTemplateRepository
		        .getTemplate(uuid);
		if (entityTemplate != null) {
			// status should be DELETED of each entity associated with this
			// template

			// this.entityTemplateRepository.deleteTemplate(uuid, statusKey,
			// Status.DELETED.name());
			statusMessageDTO.setStatus(Status.SUCCESS);
		}
		return statusMessageDTO;
	}

	/**
	 * @Description Responsible for deleting a template
	 * @param entityDTO
	 * @return StatusMessageDTO
	 */
	@Deprecated
	@Override
	public StatusMessageDTO deleteEntityTemplate(
	        EntityTemplateDTO entityTemplateDTO) {

		validateMandatoryFields(entityTemplateDTO, DOMAIN,
		        PLATFORM_ENTITY_TYPE, ENTITY_TEMPLATE_NAME);
		validateMandatoryFields(entityTemplateDTO.getDomain(), DOMAIN_NAME);

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		Integer deletedStatusKey, activeStatusKey = null;

		deletedStatusKey = statusService.getStatus(Status.DELETED.name());

		EntitySearchDTO coreEntitySearchDTO = new EntitySearchDTO();
		coreEntitySearchDTO.setDomain(entityTemplateDTO.getDomain());
		coreEntitySearchDTO.setEntityType(entityTemplateDTO
		        .getPlatformEntityType());
		coreEntitySearchDTO.setEntityTemplate(entityTemplateDTO);
		Integer count = coreEntityService
		        .getEntitiesCountByTemplate(coreEntitySearchDTO);

		if (count >= 1) {
			// Template cannot be deleted... atleast one Entity is present
			// which is not DELETED
			throw new GalaxyException(GalaxyEMErrorCodes.TEMPLATE_IN_USE);
		} else {

			// entity is either deleted or not present in
			// node_entity_template table with given template
			// delete this template from entity_template
			EntityTemplate entityTemplate = new EntityTemplate();
			entityTemplate.setDomain(entityTemplateDTO.getDomain()
			        .getDomainName());
			entityTemplate.setEntityTemplateName(entityTemplateDTO
			        .getEntityTemplateName());
			entityTemplate.setPlatformEntityType(entityTemplateDTO
			        .getPlatformEntityType());
			activeStatusKey = statusService.getStatus(Status.ACTIVE.name());

			EntityTemplate enTemplate = this.entityTemplateRepository
			        .getTemplate(entityTemplate, activeStatusKey);
			// delete that template by uuid
			// this.entityTemplateRepository.deleteTemplate(enTemplate
			// .getUuid(), deletedStatusKey,
			// Status.DELETED.name());
			statusMessageDTO.setStatus(Status.SUCCESS);
		}
		return statusMessageDTO;
	}

	/**
	 * @Description Responsible for creating EntityTemplate object from
	 *              EntityTemplateDTO
	 */
	private EntityTemplate createEntityTemplate(
	        EntityTemplateDTO entityTemplateDTO) {
		EntityTemplate entityTemplate = new EntityTemplate();
		entityTemplate.setDomain(entityTemplateDTO.getDomain().getDomainName());
		entityTemplate.setPlatformEntityType(entityTemplateDTO
		        .getPlatformEntityType());
		entityTemplate.setEntityTemplateName(entityTemplateDTO
		        .getEntityTemplateName());
		return entityTemplate;

	}

	/**
	 * @Description Responsible for allocating templates from parent to child
	 * @param entityDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public StatusMessageDTO allocateTemplates(
	        List<EntityTemplateDTO> entityTemplateDTOs, String domain,
	        Boolean isParentDomain) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		Integer statusKey = null;

		isParentDomain(isParentDomain);

		Set<EntityTemplate> validTemplatesFetchedFromDB = new HashSet<EntityTemplate>();

		// if domain is not passed get from logged in user's domain
		if (isBlank(domain)) {
			domain = subscriptionProfileService.getEndUserDomain();
		}
		ValidationUtils.validateMandatoryField(DOMAIN, domain);
		ValidationUtils
		        .validateCollection(ENTITY_TEMPLATES, entityTemplateDTOs);
		statusKey = statusService.getStatus(Status.ACTIVE.name());

		// Get parent domain, to ensure that templates belonging to its
		// immediate parent are assigned
		EntityDTO domainEntity = coreEntityService.getDomainEntity(domain);

		for (EntityTemplateDTO entityTemplateDTO : entityTemplateDTOs) {
			entityTemplateDTO.setDomain(domainEntity.getDomain());
			// Validate the input
			validateTemplateFields(entityTemplateDTO);
			EntityTemplate entityTemplate = this.entityTemplateRepository
			        .getTemplate(createEntityTemplate(entityTemplateDTO),
			                statusKey);

			if (entityTemplate != null)
				entityTemplate.setHtmlPage(null);
			validTemplatesFetchedFromDB.add(entityTemplate);

		}
		if (CollectionUtils.isEmpty(validTemplatesFetchedFromDB)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        EMDataFields.ENTITY_TEMPLATE_NAME.getDescription());
		}
		List<EntityTemplate> templatesToBeSaved = new ArrayList<EntityTemplate>();

		// check destination domain already contains New Templates
		// if Yes, don't copy them again.
		for (EntityTemplate entityTemplate : validTemplatesFetchedFromDB) {

			// copy templates having isShareable TRUE
			if (entityTemplate.getIsSharable().equals(Boolean.FALSE)) {
				throw new GalaxyException(
				        GalaxyEMErrorCodes.TEMPLATE_NOT_SHAREABLE,
				        entityTemplate.getEntityTemplateName());
			}
			// if globalEntityType having isDefault TRUE cannot
			// be copied
			if (Boolean.TRUE.equals(globalEntityService
			        .getGlobalEntityWithName(
			                entityTemplate.getPlatformEntityType())
			        .getIsDefault())) {
				throw new GalaxyException(
				        GalaxyEMErrorCodes.TEMPLATE_IS_DEFAULT, entityTemplate
				                .getEntityTemplateName().toString());
			}
			entityTemplate.setDomain(domain);
			entityTemplate.setPlatformEntityType(entityTemplate
			        .getPlatformEntityType());
			entityTemplate.setEntityTemplateName(entityTemplate
			        .getEntityTemplateName());
			entityTemplate.setStatusKey(entityTemplate.getStatusKey());
			if (this.entityTemplateRepository.getTemplate(entityTemplate,
			        statusKey) == null) {
				entityTemplate.setEntityTemplateId(getTimeBasedUUID());
				templatesToBeSaved.add(entityTemplate);
			}
		}
		if (!CollectionUtils.isEmpty(templatesToBeSaved)) {
			this.entityTemplateRepository.saveTemplates(templatesToBeSaved);
		}
		statusMessageDTO.setStatus(Status.SUCCESS);
		return statusMessageDTO;
	}

	/**
	 * @Description Responsible for fetching global templates from given domain
	 * @param entityDTO
	 * @deprecated
	 * @return List<EntityTemplateDTO>
	 */
	@Deprecated
	@Override
	public List<EntityTemplateDTO> getGlobalTemplates(String domain) {
		Integer statusKey = null;
		List<EntityTemplate> entityTemplates = null;
		List<EntityTemplateDTO> customTemplates = null;
		try {
			if (isBlank(domain)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
				        DOMAIN.getDescription());
			}
			statusKey = statusService.getStatus(Status.ACTIVE.name());
			entityTemplates = this.entityTemplateRepository
			        .getTemplatesByDomain(domain, statusKey);
			if (!CollectionUtils.isEmpty(entityTemplates)) {
				customTemplates = getEntityTemplateDTOs(entityTemplates);
			} else {
				throw new GalaxyException(GalaxyEMErrorCodes.NO_ACTIVE_TEMPLATE);
			}
		} catch (GalaxyException ex) {
			throw ex;
		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.GENERAL_EXCEPTION,
			        e);
		}
		return customTemplates;
	}

	/**
	 * @Description Responsible for retrieving shareable templates from Galaxy
	 * @param entitySearchDTO
	 * @return templates
	 */
	@Override
	public Map<String, List<EntityTemplateDTO>> getAllTemplatesByDomain(
	        EntitySearchDTO entitySearchDTO, Boolean includeNonShareable) {

		ValidationUtils.validateMandatoryFields(entitySearchDTO, ENTITY);
		/**
		 * validate entity
		 */
		try {
			entityFormat(entitySearchDTO.getEntity());
		} catch (GalaxyException e) {
			if (GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED.getCode()
			        .equals(e.getCode())) {
				if (e.getFieldName().equals(
				        EMDataFields.FIELD_KEY.getDescription())) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					        EMDataFields.IDENTIFIER_KEY.getDescription());
				} else if (e.getFieldName().equals(
				        EMDataFields.FIELD_VALUE.getDescription())) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					        EMDataFields.IDENTIFIER_VALUE.getDescription());
				}
			}
			throw e;
		}

		DomainDTO domainDTO = new DomainDTO();

		String domainName = null;
		// TODO check if this validation needs to be in AVOCADO
		/*
		 * try { domainName = domainService.getDomainOfEntity(identityDTO)
		 * .getDomainName(); } catch (GalaxyException e) { if
		 * (e.getCode().equals(
		 * GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED.getCode())) { throw new
		 * GalaxyException( GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
		 * EMDataFields.DOMAIN.getDescription()); } }
		 */
		domainDTO.setDomainName(domainName);

		entitySearchDTO.setDomain(domainDTO);
		return getAllTemplatesByDomain(entitySearchDTO.getDomain()
		        .getDomainName(), includeNonShareable);
	}

	/**
	 * @Description Responsible for retrieving templates from Galaxy
	 * @param entitySearchDTO
	 * @return templates
	 */
	public Map<String, List<EntityTemplateDTO>> getAllTemplatesByDomain(
	        String domain, Boolean includeNonShareable) {
		ValidationUtils.validateMandatoryField(EMDataFields.DOMAIN, domain);
		/**
		 * check domain is valid
		 */
		// TODO check this, domain validation should be in AVOCADO
		/*
		 * if (!isValidDomain(domain)) { throw new GalaxyException(
		 * GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
		 * EMDataFields.DOMAIN_NAME.getDescription()); }
		 */
		/**
		 * CLIENT and ORGANIZATION templates should be wrapped under CLIENT
		 * globalEntityType
		 */
		Integer statusKey = statusService.getStatus(Status.ACTIVE.name());
		List<EntityTemplate> entityTemplates = this.entityTemplateRepository
		        .getAllTemplatesByDomain(domain, includeNonShareable, statusKey);
		if (CollectionUtils.isEmpty(entityTemplates)) {
			throw new GalaxyException(GalaxyEMErrorCodes.NO_ACTIVE_TEMPLATE);
		}
		String type = null;
		Map<String, List<EntityTemplateDTO>> templatesMap = new HashMap<String, List<EntityTemplateDTO>>();

		List<EntityTemplateDTO> typedTemplates = new ArrayList<EntityTemplateDTO>();
		for (EntityTemplateDTO entityTemplateDTO : getEntityTemplateDTOs(entityTemplates)) {

			type = entityTemplateDTO.getPlatformEntityType();
			FieldMapDTO field = new FieldMapDTO();
			field.setKey(type);
			/**
			 * check if templatesMap contains type
			 */
			if (!templatesMap.containsKey(type)) {
				// map does not contains key
				List<EntityTemplateDTO> entry = new ArrayList<EntityTemplateDTO>();
				entry.add(entityTemplateDTO);
				templatesMap.remove(type);
				templatesMap.put(type, entry);
			} else {
				// map contains key
				// fetch that entry and update the value(list)
				typedTemplates = templatesMap.get(type);
				typedTemplates.add(entityTemplateDTO);
				templatesMap.put(type, typedTemplates);
			}
		}
		return templatesMap;
	}

	/**
	 * @Description Responsible for retrieving templates from Galaxy
	 * @param entitySearchDTO
	 * @return templates
	 */
	@Override
	public List<EntityTemplateDTO> getTemplateByEntityAndType(
	        EntitySearchDTO entitySearchDTO) {
		// service can be invoked by admin users
		// TODO business validation removed, it should be part of AVACADO
		/*
		 * if (!securityContextService.get(
		 * UserProfileDataFields.USER_TYPE.getName()).equals(
		 * GalaxyAdminRoles.ADMIN.getValue())) { throw new
		 * GalaxyException(GalaxyCommonErrorCodes.NO_ACCESS); }
		 */
		validategetTemplateInput(entitySearchDTO);

		String type = entitySearchDTO.getEntity().getPlatformEntity()
		        .getPlatformEntityType();
		DomainDTO domainDTO = new DomainDTO();
		if (type.equals(EMDataFields.CLIENT.getFieldName())
		        || type.equals(EMDataFields.ORGANIZATION.getFieldName())) {
			IdentityDTO identityDTO = new IdentityDTO(
			        entitySearchDTO.getEntity());
			String domainName = null;
			try {
				// TODO check here, domain should be part of AVOCADO
				// domainName = domainService.getDomainOfEntity(identityDTO)
				// .getDomainName();
			} catch (GalaxyException e) {
				if (e.getCode()
				        .equals(GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED
				                .getCode())) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
					        EMDataFields.DOMAIN.getDescription());
				}
			}
			domainDTO.setDomainName(domainName);
			entitySearchDTO.setDomain(domainDTO);
		} else {
			domainDTO.setDomainName(entitySearchDTO.getEntity().getDomain()
			        .getDomainName());
		}

		// check domain of an entity is valid
		// TODO check this , if domain validation should be in AVOCADO
		/*
		 * if (!isValidDomain(domainDTO.getDomainName())) { throw new
		 * GalaxyException( GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
		 * ENTITY.getDescription() + "/" +
		 * EMDataFields.DOMAIN_NAME.getDescription()); }
		 */
		return getTemplateByEntityAndType(domainDTO.getDomainName(),
		        entitySearchDTO.getPlatformEntity().getPlatformEntityType());
	}

	/**
	 * @Description Responsible for fetching templates from given domain and of
	 *              globalEntityType
	 * @param entityDTO
	 * @return StatusMessageDTO
	 */
	// @Override
	private List<EntityTemplateDTO> getTemplateByEntityAndType(String domain,
	        String globalEntityType) {
		Integer statusKey = null;
		List<EntityTemplate> entityTemplates = null;
		List<EntityTemplateDTO> customTemplates = null;
		ValidationUtils.validateMandatoryField(DOMAIN, domain);
		ValidationUtils.validateMandatoryField(PLATFORM_ENTITY_TYPE,
		        globalEntityType);
		statusKey = statusService.getStatus(Status.ACTIVE.name());

		/**
		 * retrieving templates for CLIENT and ORGANIZATION both, if
		 * globalEntityType = CLIENT
		 */
		entityTemplates = new ArrayList<EntityTemplate>();
		// TODO only when it's client
		if ((EMDataFields.CLIENT.getFieldName().equals(globalEntityType))
		        || (EMDataFields.ORGANIZATION.getFieldName()
		                .equals(globalEntityType))) {
			entityTemplates.addAll(entityTemplateRepository
			        .getTemplateByDomainAndType(domain,
			                EMDataFields.CLIENT.getFieldName(), statusKey));
			entityTemplates
			        .addAll(entityTemplateRepository
			                .getTemplateByDomainAndType(domain,
			                        EMDataFields.ORGANIZATION.getFieldName(),
			                        statusKey));
		} else {
			entityTemplates.addAll(entityTemplateRepository
			        .getTemplateByDomainAndType(domain, globalEntityType,
			                statusKey));
		}
		if (!CollectionUtils.isEmpty(entityTemplates)) {
			customTemplates = getEntityTemplateDTOs(entityTemplates);
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return customTemplates;
	}

	private void validategetTemplateInput(EntitySearchDTO entitySearchDTO) {
		ValidationUtils.validateMandatoryFields(entitySearchDTO, ENTITY,
		        PLATFORM_ENTITY);
		ValidationUtils.validateMandatoryField(
		        EMDataFields.PLATFORM_ENTITY_TYPE, entitySearchDTO
		                .getPlatformEntity().getPlatformEntityType());

		/**
		 * check globalEntityType is valid
		 */
		String type = entitySearchDTO.getPlatformEntity()
		        .getPlatformEntityType();
		if ((type.equals(USER.getFieldName()))
		        || (type.equals(EMDataFields.COMMUNICATION_GATEWAY
		                .getFieldName()))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        ENTITY.getDescription() + "/"
			                + PLATFORM_ENTITY_TYPE.getDescription());
		}
		checkGlobalEntityType(entitySearchDTO.getPlatformEntity());

		/**
		 * validate entity
		 */
		try {
			entityFormat(entitySearchDTO.getEntity());
		} catch (GalaxyException e) {
			if (GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED.getCode()
			        .equals(e.getCode())) {
				if (e.getFieldName().equals(
				        EMDataFields.FIELD_KEY.getDescription())) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					        ENTITY.getDescription()
					                + "/"
					                + EMDataFields.IDENTIFIER_KEY
					                        .getDescription());
				} else if (e.getFieldName().equals(
				        EMDataFields.FIELD_VALUE.getDescription())) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					        ENTITY.getDescription()
					                + "/"
					                + EMDataFields.IDENTIFIER_VALUE
					                        .getDescription());
				} else {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					        ENTITY.getDescription() + "/" + e.getFieldName());
				}
			}
			throw e;
		}

		/**
		 * if globalEntityType is CLIENT or ORGANIZATION, fetch domain from
		 * fieldValues.
		 */
		checkGlobalEntityType(entitySearchDTO.getEntity().getPlatformEntity());
	}

	/**
	 * @Description Responsible for checking entity data format
	 * @param EntityDTO
	 *            entityDTO
	 */
	private void entityFormat(EntityDTO entityDTO) {

		ValidationUtils.validateMandatoryFields(entityDTO,
		        EMDataFields.ENTITY_TEMPLATE, DOMAIN, PLATFORM_ENTITY,
		        IDENTIFIER);
		ValidationUtils.validateMandatoryFields(entityDTO.getPlatformEntity(),
		        PLATFORM_ENTITY_TYPE);
		ValidationUtils.validateMandatoryFields(entityDTO.getEntityTemplate(),
		        ENTITY_TEMPLATE_NAME);
		ValidationUtils.validateMandatoryFields(entityDTO.getDomain(),
		        EMDataFields.DOMAIN_NAME);
		ValidationUtils.validateMandatoryFields(entityDTO.getIdentifier(),
		        EMDataFields.FIELD_KEY, EMDataFields.FIELD_VALUE);

	}

	/**
	 * @Description Responsible to check if a domain is valid
	 * @param domain
	 * @return true indicates validDomain
	 */
	/*
	 * private Boolean isValidDomain(String domain) { Boolean isValidDomain =
	 * false; if (isBlank(domain)) { throw new GalaxyException(
	 * GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
	 * EMDataFields.DOMAIN.getDescription()); } com.pcs.galaxy.um.dto.DomainDTO
	 * domainDTO = new com.pcs.galaxy.um.dto.DomainDTO();
	 * domainDTO.setDomainName(domain); com.pcs.galaxy.um.dto.StatusMessageDTO
	 * statusMessageDTO = domainService .domainExists(domainDTO); if
	 * (statusMessageDTO.getStatus().equalsIgnoreCase(
	 * GalaxyCoreStatus.SUCCESS.toString())) { isValidDomain = true; } return
	 * isValidDomain; }
	 */

	/**
	 * @Description Responsible to check if a globalEntityType is valid
	 * @param globalEntityDTO
	 * @return globalEntityDTO
	 */
	private PlatformEntityDTO checkGlobalEntityType(
	        PlatformEntityDTO globalEntityDTO) {
		PlatformEntityDTO dto = null;
		dto = globalEntityService.getGlobalEntityWithName(globalEntityDTO
		        .getPlatformEntityType());
		return dto;
	}

	/**
	 * @Description Responsible to fetch all default templates by domain
	 * @param domain
	 * @return List<EntityTemplateDTO>
	 */
	@Override
	public List<EntityTemplateDTO> getDefaultTemplates() {

		// Find the list of global entities
		List<PlatformEntityDTO> defaultGlobalEntities = globalEntityService
		        .getDefaultGlobalEntities();
		List<EntityTemplateDTO> defaultTemplates = new ArrayList<EntityTemplateDTO>();
		for (PlatformEntityDTO globalEntity : defaultGlobalEntities) {
			// For each default global entity in the above list fetch templates
			// based on entity type
			PlatformEntityTemplateDTO globalEntityTemplate = globalEntityTemplateService
			        .getPlatformEntityTemplate(globalEntity
			                .getPlatformEntityType());
			EntityTemplateDTO defaultTemplate = createEntityTemplate(globalEntityTemplate);
			defaultTemplates.add(defaultTemplate);
		}
		if (CollectionUtils.isEmpty(defaultTemplates)) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return defaultTemplates;
	}

	/**
	 * @Description Delete a template based on domainName
	 * @return StatusMessageDTO
	 */
	@Deprecated
	@Override
	public StatusMessageDTO unAllocateEntityTemplate(String domainName) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		Integer deletedStatusKey, activeStatusKey = null;
		if (isBlank(domainName)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        DOMAIN.getDescription());
		}
		deletedStatusKey = statusService.getStatus(Status.DELETED.name());
		activeStatusKey = statusService.getStatus(Status.ACTIVE.name());
		List<EntityTemplate> templatesByDomain = this.entityTemplateRepository
		        .getTemplatesByDomain(domainName, activeStatusKey);
		List<UUID> entityTemplateIds = new ArrayList<UUID>();
		for (EntityTemplate entityTemplate : templatesByDomain) {
			// entityTemplateIds.add(entityTemplate
			// .getUuid());
		}
		this.entityTemplateRepository.unAllocateTemplate(entityTemplateIds,
		        deletedStatusKey);
		statusMessageDTO.setStatus(Status.SUCCESS);
		return statusMessageDTO;
	}

	/*
	 * @Override public List<NodeEntityTemplateDTO>
	 * getEntityTemplatesByEntityIds( EntitySearchDTO coreEntitySearchDTO) { if
	 * (CollectionUtils.isEmpty(coreEntitySearchDTO.getAccessEntityIds())) {
	 * throw new GalaxyException(
	 * GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
	 * ACCESS_ENTITY_IDS.getDescription()); } Set<NodeEntityTemplate>
	 * templatesSet = null; List<UUID> entities = new ArrayList<UUID>(); for
	 * (String nodeEntityTemplate : coreEntitySearchDTO .getAccessEntityIds()) {
	 * entities.add(UUID.fromString(nodeEntityTemplate)); }
	 * List<NodeEntityTemplate> entityTemplates = nodeEntityTemplateRepository
	 * .getTemplatesByIds(entities); if
	 * (CollectionUtils.isEmpty(entityTemplates)) { throw new
	 * GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE); }
	 * templatesSet = new HashSet<NodeEntityTemplate>();
	 * templatesSet.addAll(entityTemplates); List<NodeEntityTemplateDTO>
	 * templates = getNodeEntityTemplateDTOs(templatesSet); return templates; }
	 */

	/**
	 * @Description Check if valid field is passed
	 * @param domain
	 *            , global entity type and field name
	 * @return boolean, true indicates valid field
	 * 
	 */
	@Override
	public Boolean isValidFieldName(String domain, String globalEntity,
	        String fieldName, String templateName) {
		Integer statusKey = statusService.getStatus(DELETED.name());

		Boolean fieldValidity = entityTemplateRepository.isValidField(domain,
		        globalEntity, statusKey, fieldName, templateName);
		return fieldValidity;
	}

	/**
	 * @Description Responsible for fetching globalEntityTypes associated with
	 *              domain
	 * @param String
	 *            domain
	 * @return List<GlobalEntityDTO>
	 */
	@Override
	public Set<String> getGlobalEntityTypesByDomain(String domain) {
		// Get status key for active status
		Integer statusKey = statusService.getStatus(ACTIVE.name());
		// globalEntityTypes.addAll();

		List<EntityTemplate> entityTemplates = entityTemplateRepository
		        .getGlobalEntityTypesByDomain(domain, statusKey);

		if (CollectionUtils.isEmpty(entityTemplates)) {
			throw new GalaxyException(
			        GalaxyEMErrorCodes.DOMAIN_DOES_NOT_CONTAINS_GLOBAL_ENTITY_TYPE,
			        domain);
		}
		Set<String> types = new HashSet<String>();
		for (EntityTemplate entityTemplate : entityTemplates) {
			types.add(entityTemplate.getPlatformEntityType());
		}
		return types;
	}

	private void checkGlobalEntityTemplateName(
	        EntityTemplateDTO entityTemplateDTO) {
		try {
			PlatformEntityTemplateDTO globalEntityTemplateDTO = globalEntityTemplateService
			        .getPlatformEntityTemplate(entityTemplateDTO
			                .getPlatformEntityType());
			if (entityTemplateDTO.getPlatformEntityTemplateName().equals(
			        globalEntityTemplateDTO.getPlatformEntityTemplateName())) {
				return;
			} else {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        entityTemplateDTO.getPlatformEntityTemplateName());
			}
		} catch (GalaxyException e) {
			if (e.getCode().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        entityTemplateDTO.getPlatformEntityTemplateName());
			}
			throw e;
		}
	}

	private void checkGlobalEntityType(String globalEntityType) {
		PlatformEntityDTO globalEntityDTO = globalEntityService
		        .getGlobalEntityWithName(globalEntityType);

		if (globalEntityDTO == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.getDescription());
		} else {
			// globalEntityType with isDefault False can only be created
			if (globalEntityDTO.getIsDefault().equals(Boolean.TRUE)) {
				throw new GalaxyException(
				        GalaxyEMErrorCodes.TEMPLATE_GLOBAL_ENTITY_IS_DEFAULT);
			}
		}
	}

	private EntityTemplateDTO flagsCheck(EntityTemplateDTO entityTemplateDTO) {
		if (entityTemplateDTO.getIsModifiable() == null) {
			// Bydefault IsModifiable is TRUE
			entityTemplateDTO.setIsModifiable(Boolean.TRUE);
		}
		if (entityTemplateDTO.getIsSharable() == null) {
			// Bydefault IsSharable is TRUE
			entityTemplateDTO.setIsSharable(Boolean.TRUE);
		}
		return entityTemplateDTO;
	}

	private EntityTemplateDTO validateTemplate(
	        EntityTemplateDTO entityTemplateDTO) {

		validateMandatoryFields(entityTemplateDTO, ENTITY_TEMPLATE_NAME,
		        FIELD_VALIDATION, PLATFORM_ENTITY_TEMPLATE_NAME,
		        PLATFORM_ENTITY_TYPE, IDENTIFIER_FIELD);

		// status should be ACTIVE if present, else by default it would be
		// ACTIVE
		if (entityTemplateDTO.getStatus() != null) {
			// check entityStatus is valid, either ACTIVE or INACTIVE
			ValidationUtils.validateMandatoryField(EMDataFields.STATUS_NAME,
			        entityTemplateDTO.getStatus().getStatusName());
			if (!(entityTemplateDTO.getStatus().getStatusName()
			        .equalsIgnoreCase(Status.ACTIVE.name()))
			        && !(entityTemplateDTO.getStatus().getStatusName()
			                .equalsIgnoreCase(Status.INACTIVE.name()))) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        CommonConstants.STATUS_NAME);
			}
		} else {
			StatusDTO status = new StatusDTO();
			status.setStatusName(Status.ACTIVE.name());
			entityTemplateDTO.setStatus(status);
		}

		// set isModifiable and isSharable if present, else by default both
		// would be true
		entityTemplateDTO = flagsCheck(entityTemplateDTO);

		// domain null check
		// If domain is null get domain from subscription profile filter
		entityTemplateDTO.setDomain(setDomain(entityTemplateDTO.getDomain()));

		// domain validity
		try {
			coreEntityService.getDomainEntity(entityTemplateDTO.getDomain()
			        .getDomainName());
		} catch (GalaxyException ge) {
			if (ge.getErrorMessageDTO()
			        .getErrorMessage()
			        .equalsIgnoreCase(
			                GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE
			                        .getMessage())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
				        DOMAIN.getDescription());

			}
		}
		return entityTemplateDTO;
	}

	private void checkTemplateNameExists(EntityTemplateDTO entityTemplateDTO) {
		// check entityTemplateName is unique across domain for respective
		// entityType and parentTemplate
		EntityTemplate entityTemplate = new EntityTemplate();
		entityTemplate.setDomain(entityTemplateDTO.getDomain().getDomainName());
		entityTemplate.setPlatformEntityType(entityTemplateDTO
		        .getPlatformEntityType());
		entityTemplate.setEntityTemplateName(entityTemplateDTO
		        .getEntityTemplateName());
		entityTemplate.setParentTemplate(entityTemplateDTO.getParentTemplate());

		entityTemplate.setStatusKey(entityTemplateDTO.getStatus()
		        .getStatusKey());

		int deletedStatus = statusService.getStatus(Status.DELETED.name());
		// check entityTemplateName is unique across domain and
		// globalEntityType
		Integer count = this.entityTemplateRepository.getTemplateCounts(
		        entityTemplate, deletedStatus);
		if (count != null && count > 0) {
			// entityTemplateName is not unique
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
			        ENTITY_TEMPLATE_NAME.getDescription());
		}
	}

	private EntityTemplate getEntityTemplateModel(
	        EntityTemplateDTO entityTemplateDTO) {
		EntityTemplate entityTemplate = new EntityTemplate();

		// entityTemplate.setDomainType(entityTemplateDTO.getDomain().getType());
		entityTemplate.setDomain(entityTemplateDTO.getDomain().getDomainName());
		entityTemplate.setPlatformEntityType(entityTemplateDTO
		        .getPlatformEntityType());
		entityTemplate.setEntityTemplateName(entityTemplateDTO
		        .getEntityTemplateName());
		// Get status_key from DB
		entityTemplate.setStatusKey(entityTemplateDTO.getStatus()
		        .getStatusKey());

		Map<String, String> fieldValidation = entityTemplateDTO
		        .getFieldValidation();

		// map fieldvalidations and check identifier field is present in
		// FieldValidation map
		entityTemplate.setFieldValidation(mapFieldValidations(
		        entityTemplateDTO.getIdentifierField(), fieldValidation));
		entityTemplate.setPlatformEntityTemplateName(entityTemplateDTO
		        .getPlatformEntityTemplateName());

		entityTemplate.setHtmlPage(null);
		entityTemplate.setIsSharable(entityTemplateDTO.getIsSharable());
		// entityTemplate.setBeginning(1);
		// entityTemplate.setRevisionTime(UUIDs.timeBased());

		entityTemplate.setIdentifierField(entityTemplateDTO
		        .getIdentifierField());
		entityTemplate.setIsModifiable(entityTemplateDTO.getIsModifiable());

		entityTemplate.setStatusName(entityTemplateDTO.getStatus()
		        .getStatusName());
		entityTemplate.setParentTemplate(entityTemplateDTO.getParentTemplate());
		return entityTemplate;
	}

	private Map<String, String> mapFieldValidations(String identifier,
	        Map<String, String> fieldValidation) {
		Map<String, String> map = new HashMap<String, String>();

		ValidationTestDTO validationTestDTO = new ValidationTestDTO();
		for (Entry<String, String> entry : fieldValidation.entrySet()) {
			// ---
			/*
			 * Client side validation : save as it is Server side validation :
			 * check if they are correct and save. example: {"userName":
			 * "{\"client\":[],\"server\":[\"MANDATORY\"]}"}
			 */
			if (isNotBlank(entry.getValue())) {
				validationTestDTO.setValidationJsonString(entry.getValue(),
				        objectBuilderUtil.getGson());
				map.put(entry.getKey(), validationTestDTO
				        .getValidationJsonString(objectBuilderUtil.getGson()));
				continue;
			} else {
				// save empty client and server validation
				List<String> client = new ArrayList<String>();
				List<String> server = new ArrayList<String>();
				ValidationJsonStringDTO dto = new ValidationJsonStringDTO();
				dto.setClient(client);
				dto.setServer(server);
				Gson gson = objectBuilderUtil.getGson();
				map.put(entry.getKey(), gson.toJson(dto));
				continue;
			}
		}
		String identifierValidation = map.get(identifier);
		if (isBlank(identifierValidation)) {
			// identifier field not present in template
			throw new GalaxyException(
			        GalaxyEMErrorCodes.IDENTIFIER_NOT_PRESENT_IN_TEMPLATE);
		} else {

			// Identifier field byDefault will get Mandatory validation
			validationTestDTO.setValidationJsonString(identifierValidation,
			        objectBuilderUtil.getGson());
			ValidationJsonStringDTO jsonStringDTO = validationTestDTO
			        .getValidationJsonStringDTO();
			if (CollectionUtils.isEmpty(jsonStringDTO.getServer())) {
				List<String> server = new ArrayList<String>();
				server.add(GalaxyValidationDataFields.MANDATORY.toString());
				jsonStringDTO.setServer(server);
				validationTestDTO.setValidationJsonStringDTO(jsonStringDTO);
				map.put(identifier, validationTestDTO
				        .getValidationJsonString(objectBuilderUtil.getGson()));
			}
		}
		return map;

	}

	private EntityTemplateDTO updateCheck(EntityTemplateDTO templateInput) {
		EntityTemplate templateFromDB = null;
		// fetch Template , based on Domain, entityTemplateName,
		// globalEntityType and status = ACTIVE
		try {
			Integer statusKey = statusService.getStatus(Status.ACTIVE.name());

			EntityTemplate enTemplate = new EntityTemplate();
			enTemplate.setDomain(templateInput.getDomain().getDomainName());
			enTemplate.setPlatformEntityType(templateInput
			        .getPlatformEntityType());
			enTemplate.setEntityTemplateName(templateInput
			        .getEntityTemplateName());

			templateFromDB = this.entityTemplateRepository.getTemplate(
			        enTemplate, statusKey);

		} catch (GalaxyException e) {
			if (!e.getCode().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode())) {
				throw e;
			} else {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        ENTITY_TEMPLATE.getDescription());
			}
		}
		// Template exists in DB.

		// check for identifier field, it should not be updated
		checkIdentifierField(templateFromDB.getIdentifierField(),
		        templateInput.getIdentifierField());

		// copy new htmlPage and FieldValidations as it is, validation will be
		// done while creating model for update
		templateFromDB.setHtmlPage(null);
		templateFromDB.setFieldValidation(templateInput.getFieldValidation());
		// update on IsModifiable and IsShareable not allowed
		if (templateInput.getIsModifiable().booleanValue() != templateFromDB
		        .getIsModifiable().booleanValue()) {
			throw new GalaxyException(
			        GalaxyEMErrorCodes.IS_MODIFIABLE_UPDATE_NOT_ALLOWED);
		}
		if (templateInput.getIsSharable().booleanValue() != templateFromDB
		        .getIsSharable().booleanValue()) {
			throw new GalaxyException(
			        GalaxyEMErrorCodes.IS_SHAREABLE_UPDATE_NOT_ALLOWED);
		}

		EntityTemplateDTO templateDTO = getEntityTemplateDTO(templateFromDB);
		// templateDTO.setUuid(templateFromDB.getUuid());
		return templateDTO;
	}

	private ByteBuffer createHtmlPageString(String htmlPage) {
		ByteBuffer ret = ByteBuffer.wrap(new byte[htmlPage.getBytes().length]);
		ret.put(htmlPage.getBytes());
		ret.flip();
		return ret;
	}

	private void validateUpdateTemplate(EntityTemplateDTO entityTemplateDTO) {

		validateMandatoryFields(entityTemplateDTO, DOMAIN,
		        ENTITY_TEMPLATE_NAME, FIELD_VALIDATION,
		        PLATFORM_ENTITY_TEMPLATE_NAME, PLATFORM_ENTITY_TYPE, HTML_PAGE,
		        IDENTIFIER_FIELD, IS_MODIFIABLE, IS_SHARABLE);

		validateMandatoryFields(entityTemplateDTO.getDomain(), DOMAIN_NAME);
	}

	private void checkIdentifierField(String oldIdentifier, String newIdentifier) {
		if (!newIdentifier.trim().equals(oldIdentifier.trim())) {
			/*
			 * identifier field is not same as in DB. update on identifier is
			 * not allowed.
			 */
			throw new GalaxyException(
			        GalaxyEMErrorCodes.UPDATE_ON_IDENTIFIER_ILLEGAL);
		}
	}

	private EntityTemplateDTO getEntityTemplateDTO(EntityTemplate entityTemplate) {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(entityTemplate
		        .getEntityTemplateName());
		entityTemplateDTO.setFieldValidation(entityTemplate
		        .getFieldValidation());

		entityTemplateDTO.setPlatformEntityTemplateName(entityTemplate
		        .getPlatformEntityTemplateName());

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(entityTemplate.getDomain());
		entityTemplateDTO.setDomain(domain);
		entityTemplateDTO.setPlatformEntityType(entityTemplate
		        .getPlatformEntityType());
		// byte[] bytes = new byte[entityTemplate.getHtmlPage().remaining()];
		// .getHtmlPage().duplicate().get(bytes);
		entityTemplateDTO.setHtmlPage(null);

		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName(entityTemplate.getStatusName());
		entityTemplateDTO.setStatus(statusDTO);

		entityTemplateDTO.setIdentifierField(entityTemplate
		        .getIdentifierField());
		entityTemplateDTO.setIsModifiable(entityTemplate.getIsModifiable());
		entityTemplateDTO.setIsSharable(entityTemplate.getIsSharable());
		if (StringUtils.isNotBlank(entityTemplate.getParentTemplate())) {
			entityTemplateDTO.setParentTemplate(entityTemplate
			        .getParentTemplate());
		}
		return entityTemplateDTO;
	}

	private List<EntityTemplateDTO> getEntityTemplateDTOs(
	        List<EntityTemplate> entityTemplates) {
		List<EntityTemplateDTO> entityTemplateDTOs = new ArrayList<EntityTemplateDTO>();
		for (EntityTemplate entityTemplate : entityTemplates) {
			EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
			entityTemplateDTO.setEntityTemplateName(entityTemplate
			        .getEntityTemplateName());
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(entityTemplate.getDomain());
			entityTemplateDTO.setDomain(domain);
			entityTemplateDTO.setPlatformEntityType(entityTemplate
			        .getPlatformEntityType());
			StatusDTO statusDTO = new StatusDTO();
			// statusDTO.setStatusKey(entityTemplate.getStatusKey());
			statusDTO.setStatusName(entityTemplate.getStatusName());
			entityTemplateDTO.setStatus(statusDTO);
			if (StringUtils.isNotBlank(entityTemplate.getParentTemplate())) {
				entityTemplateDTO.setParentTemplate(entityTemplate
				        .getParentTemplate());
			}
			entityTemplateDTOs.add(entityTemplateDTO);
		}
		return entityTemplateDTOs;
	}

	private EntityTemplateDTO createEntityTemplate(
	        PlatformEntityTemplateDTO globalEntityTemplate) {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setFieldValidation(globalEntityTemplate
		        .getFieldValidation());
		entityTemplateDTO.setPlatformEntityTemplateName(globalEntityTemplate
		        .getPlatformEntityTemplateName());
		entityTemplateDTO.setPlatformEntityType(globalEntityTemplate
		        .getPlatformEntityType());
		entityTemplateDTO.setHtmlPage(null);
		entityTemplateDTO.setIdentifierField(globalEntityTemplate
		        .getIdentifierField());
		StatusDTO entityTemplateStatus = new StatusDTO();
		entityTemplateStatus.setStatusName(Status.ACTIVE.name());
		entityTemplateDTO.setStatus(entityTemplateStatus);
		entityTemplateDTO.setEntityTemplateName(globalEntityTemplate
		        .getPlatformEntityTemplateName());
		return entityTemplateDTO;
	}

	@Override
	public EntityTemplateDTO getDefaultOrgTemplateByDomainAndType(
	        String domain, String globalEntityType) {
		Integer statusKey = null;
		List<EntityTemplate> entityTemplates = null;
		ValidationUtils.validateMandatoryField(DOMAIN, domain);
		ValidationUtils.validateMandatoryField(PLATFORM_ENTITY_TYPE,
		        globalEntityType);
		statusKey = statusService.getStatus(Status.ACTIVE.name());

		entityTemplates = entityTemplateRepository.getTemplateByDomainAndType(
		        domain, globalEntityType, statusKey);
		EntityTemplateDTO templateDTO = new EntityTemplateDTO();

		if (!CollectionUtils.isEmpty(entityTemplates)) {
			if (CollectionUtils.size(entityTemplates) > 1) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DUPLICATE_RECORDS);
			} else {

			}
			templateDTO = getEntityTemplateDTO(entityTemplates.get(0));
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return templateDTO;
	}

	private DomainDTO setDomain(DomainDTO domain) {
		if (domain.getDomainName() == null || domain.getDomainName().isEmpty()) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			domain.setDomainName(domainName);
		}
		return domain;
	}

	private void validateTemplateFields(EntityTemplateDTO entityTemplateDTO) {
		validateMandatoryFields(entityTemplateDTO, ENTITY_TEMPLATE_NAME,
		        PLATFORM_ENTITY_TYPE);
		// Validate global entity type
		globalEntityService.getGlobalEntityWithName(entityTemplateDTO
		        .getPlatformEntityType());
	}

	@Override
	public String getEntityType(String domain, String entityTemplateName,
	        Boolean isParentDomain) {

		// Validate if parent domain
		isParentDomain(isParentDomain);

		// Validate the input
		ValidationUtils.validateMandatoryField(ENTITY_TEMPLATE_NAME,
		        entityTemplateName);

		// If domain not passed, fetch from logged in user's domain
		if (isBlank(domain)) {
			domain = subscriptionProfileService.getEndUserDomain();
		}

		Integer statusKey = statusService.getStatus(Status.DELETED.name());

		// Fetch from entity template
		String entityType = entityTemplateRepository.getPlatformEntityType(
		        domain, entityTemplateName, statusKey);

		if (isBlank(entityType)) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return entityType;
	}

	@Override
	public List<EntityTemplateDTO> getEntityTemplatesByDomainAndType(
	        String domain, String platformType) {
		// TODO validate the input
		List<EntityTemplate> templates = entityTemplateRepository
		        .getTemplateByDomainAndPlatformEntityType(domain, platformType);
		return getEntityTemplateDTOs(templates);
	}

	@Override
	public List<EntityTemplateDTO> findAllTemplateByParentTemplate(
	        EntityTemplateDTO entityTemplateDTO) {
		Integer statusKey = null;
		List<EntityTemplateDTO> templates = null;

		DomainDTO domainDTO = new DomainDTO();
		if (entityTemplateDTO.getDomain() != null) {
			if (StringUtils.isBlank(entityTemplateDTO.getDomain()
			        .getDomainName())) {
				String domain = subscriptionProfileService.getEndUserDomain();
				domainDTO.setDomainName(domain);
				entityTemplateDTO.setDomain(domainDTO);
			}
		} else {
			String domain = subscriptionProfileService.getEndUserDomain();
			domainDTO.setDomainName(domain);
			entityTemplateDTO.setDomain(domainDTO);
		}

		validateMandatoryFields(entityTemplateDTO, DOMAIN,
		        PLATFORM_ENTITY_TYPE, PARENT_TEMPLATE);

		isParentDomain(entityTemplateDTO.getIsParentDomain());

		// To check template status *generic*
		/*
		 * if
		 * (entityTemplateDTO.getStatus().getStatusName().equalsIgnoreCase(Status
		 * .ALL.toString())){ //get the highest Status KEY for an EntityTemplate
		 * statusKey = statusService.getStatus(Status.INACTIVE.toString()); }
		 * else { statusKey =
		 * statusService.getStatus(entityTemplateDTO.getStatus
		 * ().getStatusName()); }
		 */

		statusKey = statusService.getStatus(Status.INACTIVE.toString());
		List<EntityTemplate> entityTemplates = null;

		// To get templates if both active and inactive are required
		// if (statusKey == 1 &&
		// (entityTemplateDTO.getStatus().getStatusName().equalsIgnoreCase(Status.ALL.toString())))
		// {
		entityTemplates = this.entityTemplateRepository
		        .findAllTemplateByParentTemplate(entityTemplateDTO.getDomain()
		                .getDomainName(), entityTemplateDTO
		                .getPlatformEntityType(), entityTemplateDTO
		                .getParentTemplate(), statusKey);
		/*
		 * } else { entityTemplates = this.entityTemplateRepository
		 * .getTemplateByDomainAndTypeAndStatus(entityTemplateDTO.getDomain()
		 * .getDomainName(), entityTemplateDTO .getGlobalEntityType(),
		 * statusKey); }
		 */

		if (CollectionUtils.isNotEmpty(entityTemplates)) {
			templates = getEntityTemplateDTOs(entityTemplates);
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return templates;
	}

	@Override
	public StatusMessageDTO updateStatus(EntityTemplateDTO entityTemplateDTO,
	        String status) {
		// Validate the input
		ValidationUtils.validateMandatoryFields(entityTemplateDTO,
		        ENTITY_TEMPLATE_NAME, DOMAIN);
		validateMandatoryFields(entityTemplateDTO.getDomain(), DOMAIN_NAME);
		ValidationUtils.validateMandatoryField(STATUS, status);
		isParentDomain(entityTemplateDTO.getIsParentDomain());

		// Check if status is valid
		Integer statusKey = statusService.getStatus(status);

		// Get platform entity type
		PlatformEntityDTO platformType = coreEntityUtil.getGlobalEntityType(
		        entityTemplateDTO.getEntityTemplateName(), entityTemplateDTO
		                .getDomain().getDomainName());
		entityTemplateDTO.setPlatformEntityType(platformType
		        .getPlatformEntityType());

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);

		// Find the template to update
		EntityTemplate entityTemplate = getEntityTemplate(entityTemplateDTO);

		if (entityTemplate == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        ENTITY_TEMPLATE.getDescription());
		}
		// Existing status key
		Integer existingStatusKey = entityTemplate.getStatusKey();
		String existingStatus = entityTemplate.getStatusName();

		if (existingStatus.equalsIgnoreCase(status)) {
			return statusMessageDTO;
		}

		// Set the new status
		entityTemplate.setStatusName(status);
		entityTemplate.setStatusKey(statusKey);

		try {
			entityTemplateRepository.updateStatusBatch(entityTemplate,
			        existingStatusKey);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			statusMessageDTO.setStatus(Status.FAILURE);
		}

		return statusMessageDTO;
	}
}
