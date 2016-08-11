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
package com.pcs.alpine.serviceImpl;

import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.commons.validation.ValidationUtils.validateResult;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_ID;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_STATUS;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_KEY;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALUE;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALUES;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER_KEY;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER_VALUE;
import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY;
import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.EMDataFields.SEARCH_FIELDS_LIST;
import static com.pcs.alpine.services.enums.EMDataFields.STATUS_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.TENANT;
import static com.pcs.alpine.services.enums.Status.DELETED;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.util.DTOUtils;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.model.Entity;
import com.pcs.alpine.model.udt.FieldMap;
import com.pcs.alpine.serviceImpl.repository.EntityRepository;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.PlatformEntityService;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityStatusCountDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;
import com.pcs.alpine.services.enums.EMDataFields;
import com.pcs.alpine.services.enums.Status;
import com.pcs.alpine.services.exception.GalaxyEMErrorCodes;

/**
 * CoreEntityServiceImpl
 * 
 * @description Service Implementation for core entity services
 * @author Daniela (PCSEG191)
 * @date 12 Nov 2014
 * @since galaxy-1.0.0
 */
@Service
public class CoreEntityServiceImpl implements CoreEntityService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(CoreEntityServiceImpl.class);

	@Autowired
	private PlatformEntityService globalEntityService;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private EntityTemplateService templateService;

	@Autowired
	private PlatformEntityTemplateService globalEntityTemplateService;

	@Autowired
	private StatusService statusService;

	/**
	 * @Description Responsible to fetch entities based on domain and type
	 * @param coreEntitySearchDTO
	 * @return List<EntityDTO>
	 */
	@Override
	public List<EntityDTO> getEntitiesByDomain(
	        EntitySearchDTO coreEntitySearchDTO) {
		Boolean domainMandatory = true;
		Boolean globalEntityMandatory = false;
		// validate input, domain and uniqueUser mandatory
		validateGlobalEntityAndDomain(coreEntitySearchDTO, domainMandatory,
		        globalEntityMandatory);
		Boolean isDataprovider = false;
		Boolean returnAll = false;
		return getEntitiesByDomainAndType(coreEntitySearchDTO, isDataprovider,
		        returnAll);
	}

	/**
	 * @Description Responsible to retrieve a specific entity based on domain,
	 *              field name*, field value* and type
	 * @param EntitySearchDTO
	 * @return EntityDTO
	 */
	@Override
	public EntityDTO getEntityByField(EntitySearchDTO coreEntitySearchDTO) {
		if (CollectionUtils.size(coreEntitySearchDTO.getFieldValues()) > 1) {
			throw new GalaxyException(GalaxyEMErrorCodes.MULIPLE_FIELD_VALUES);
		}
		List<Entity> fields = getEntityByFieldAndValue(coreEntitySearchDTO);
		if (fields.size() > 1) {
			LOGGER.debug("<<-- getEntityByField, duplicate records found-->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DUPLICATE_RECORDS);
		}
		if (CollectionUtils.isEmpty(fields)) {
			LOGGER.debug("<<-- getEntityByField, no data available for the filter conditions-->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		Boolean isDataProvider = false;
		Boolean returnAll = false;
		return createEntityDTO(fields.get(0), isDataProvider, returnAll);
	}

	/**
	 * @Description Responsible to fetch entities based on domain, field name*,
	 *              field value* and type
	 * @param EntitySearchDTO
	 * @return List<EntityDTO>
	 */
	@Override
	public List<EntityDTO> getEntitiesByField(
	        EntitySearchDTO coreEntitySearchDTO) {
		List<Entity> fields = getEntityByFieldAndValue(coreEntitySearchDTO);

		validateResult(fields);

		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		Boolean isDataProvider = false;
		Boolean returnAll = false;
		for (Entity entity : fields) {
			entities.add(createEntityDTO(entity, isDataProvider, returnAll));
		}
		return entities;
	}

	/**
	 * @Description Responsible to fetch entities based on domain, field name*,
	 *              field value* and type
	 * @param EntitySearchDTO
	 * @return List<EntityDTO>
	 */
	@Override
	public List<EntityDTO> getDataProvidersByField(
	        EntitySearchDTO coreEntitySearchDTO) {
		List<Entity> fields = getEntityByFieldAndValue(coreEntitySearchDTO);

		if (CollectionUtils.isEmpty(fields)) {
			LOGGER.debug("<<-- getEntitiesByFields, no data for filter condition-->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		Boolean isDataProvider = true;
		Boolean returnAll = false;
		for (Entity entity : fields) {
			entities.add(createEntityDTO(entity, isDataProvider, returnAll));
		}
		return entities;
	}

	/**
	 * @Description Responsible to fetch entities based on List of entity ids
	 *              field value* and type
	 * @param EntitySearchDTO
	 * @return List<EntityDTO>
	 */
	// @Override
	// public List<EntityDTO> getEntitiesByEntityIds(List<String> entityIds) {
	// List<Entity> entities = entityRepository
	// .getEntitiesWithEntityIdsString(entityIds);
	// if (CollectionUtils.isEmpty(entities)) {
	// LOGGER.debug("<<-- getEntitiesByEntityIds, no data available-->>");
	// throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
	// }
	// List<EntityDTO> entitiesDTO = new ArrayList<EntityDTO>();
	// Boolean isDataProvider = true;
	// Boolean returnAll = false;
	// for (Entity entity : entities) {
	// EntityDTO entityDto = createEntityDTO(entity, isDataProvider,
	// returnAll);
	// entitiesDTO.add(entityDto);
	// }
	// return entitiesDTO;
	// }

	/**
	 * @Description Responsible to fetch entities based on domain and type*
	 * @param coreEntitySearchDTO
	 * @return List<EntityDTO>
	 */
	@Override
	public List<EntityDTO> getEntitiesByType(EntitySearchDTO coreEntitySearchDTO) {
		Boolean domainMandatory = false;
		Boolean globalEntityMandatory = true;
		// validate input, domain and uniqueUser mandatory
		validateGlobalEntityAndDomain(coreEntitySearchDTO, domainMandatory,
		        globalEntityMandatory);
		Boolean isDataprovider = false;
		Boolean returnAll = false;
		return getEntitiesByDomainAndType(coreEntitySearchDTO, isDataprovider,
		        returnAll);
	}

	/**
	 * @Description Responsible to fetch entities based on multiple field value
	 *              pairs* , domain and type
	 * @param EntitySearchDTO
	 * @return List<EntityDTO>
	 */
	@Override
	public List<EntityDTO> getEntitiesByMultipleFields(
	        EntitySearchDTO coreEntitySearchDTO) {
		Boolean isEntityTypeMandatory = true;
		validateGetEntitiesByMultipleFields(coreEntitySearchDTO,
		        isEntityTypeMandatory);
		List<Entity> fields = new ArrayList<Entity>();
		String entityType = null;
		if (coreEntitySearchDTO.getPlatformEntity() != null) {
			entityType = coreEntitySearchDTO.getPlatformEntity()
			        .getPlatformEntityType();
		}
		String domain = null;
		if (coreEntitySearchDTO.getDomain() != null) {
			domain = coreEntitySearchDTO.getDomain().getDomainName();
		}

		// Get status key for deleted status
		Integer statusKey = statusService.getStatus(DELETED.name());
		List<FieldMap> fieldList = new ArrayList<FieldMap>();
		for (FieldMapDTO fieldMap : coreEntitySearchDTO.getFieldValues()) {
			FieldMap field = new FieldMap();
			field.setKey(fieldMap.getKey());
			field.setValue(fieldMap.getValue());
			fieldList.add(field);
		}
		// Get all entity types
		List<EntityTemplateDTO> templates = getTemplateName(coreEntitySearchDTO
		        .getDomain().getDomainName(), coreEntitySearchDTO
		        .getPlatformEntity().getPlatformEntityType());

		for (EntityTemplateDTO entityTemplateDTO : templates) {
			fields.addAll(entityRepository.getEntitiesByFields(fieldList,
			        domain, entityType, statusKey,
			        entityTemplateDTO.getEntityTemplateName()));
		}

		List<EntityDTO> fieldsDto = new ArrayList<EntityDTO>();
		Boolean isDataProvider = false;
		Boolean returnAll = false;

		for (Entity entity : fields) {
			EntityDTO entityDTO = createEntityDTO(entity, isDataProvider,
			        returnAll);
			fieldsDto.add(entityDTO);
		}
		if (CollectionUtils.isEmpty(fieldsDto)) {
			LOGGER.debug("<<-- getEntitiesByMultipleFields, no data for filter condition-->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return fieldsDto;
	}

	/**
	 * @Description Responsible to fetch entities based on multiple field value
	 *              pairs* , domain and type and entity template name
	 * @param EntitySearchDTO
	 * @return List<EntityDTO>
	 */
	@Override
	public List<EntityDTO> getEntitiesByEntityTemplateAndMultipleFields(
	        EntitySearchDTO coreEntitySearchDTO) {
		Boolean isEntityTypeMandatory = true;
		validateGetEntitiesByMultipleFields(coreEntitySearchDTO,
		        isEntityTypeMandatory);
		List<Entity> fields = new ArrayList<Entity>();
		String entityType = null;
		if (coreEntitySearchDTO.getPlatformEntity() != null) {
			entityType = coreEntitySearchDTO.getPlatformEntity()
			        .getPlatformEntityType();
		}
		String domain = null;
		if (coreEntitySearchDTO.getDomain() != null) {
			domain = coreEntitySearchDTO.getDomain().getDomainName();
		}

		// Get status key for deleted status
		Integer statusKey = statusService.getStatus(DELETED.name());
		List<FieldMap> fieldList = new ArrayList<FieldMap>();
		for (FieldMapDTO fieldMap : coreEntitySearchDTO.getFieldValues()) {
			FieldMap field = new FieldMap();
			field.setKey(fieldMap.getKey());
			field.setValue(fieldMap.getValue());
			fieldList.add(field);
		}
		String entityTemplateName = "";
		if (coreEntitySearchDTO.getEntityTemplate() != null) {
			if (StringUtils.isNotBlank(coreEntitySearchDTO.getEntityTemplate()
			        .getEntityTemplateName())) {
				entityTemplateName = coreEntitySearchDTO.getEntityTemplate()
				        .getEntityTemplateName();
			}
		}
		fields = entityRepository.getEntitiesByFields(fieldList, domain,
		        entityType, statusKey, entityTemplateName);
		List<EntityDTO> fieldsDto = new ArrayList<EntityDTO>();
		Boolean isDataProvider = true;
		Boolean returnAll = false;

		for (Entity entity : fields) {
			EntityDTO entityDTO = createEntityDTO(entity, isDataProvider,
			        returnAll);
			fieldsDto.add(entityDTO);
		}
		if (CollectionUtils.isEmpty(fieldsDto)) {
			LOGGER.debug("<<-- getEntitiesByMultipleFields, no data for filter condition-->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return fieldsDto;
	}

	/**
	 * @Description Responsible to create an EntityDTO
	 * @param Entity
	 *            <FieldMap>
	 * @return EntityDTO
	 */
	private EntityDTO createEntityDTO(Entity entity, Boolean isDataprovider,
	        Boolean returnAll) {
		EntityDTO entityDTO = new EntityDTO();
		// Appending all Identifier Fields
		appendEntityIdentifier(entityDTO, entity);

		if (returnAll) {
			List<FieldMapDTO> dataproviderMap = new ArrayList<FieldMapDTO>();
			for (FieldMap fieldValue : entity.getDataprovider()) {
				FieldMapDTO fieldMapDTO = new FieldMapDTO();
				DTOUtils.copyProperties(fieldMapDTO, fieldValue);
				dataproviderMap.add(fieldMapDTO);
			}
			entityDTO.setDataprovider(dataproviderMap);

			List<FieldMapDTO> fieldMap = new ArrayList<FieldMapDTO>();
			for (FieldMap fieldValue : entity.getFieldValues()) {
				FieldMapDTO fieldMapDTO = new FieldMapDTO();
				DTOUtils.copyProperties(fieldMapDTO, fieldValue);
				fieldMap.add(fieldMapDTO);
			}
			entityDTO.setFieldValues(fieldMap);
		} else if (isDataprovider && !returnAll) {
			// Set DataProvider if the service is a dataProvider Service
			List<FieldMapDTO> dataproviderMap = new ArrayList<FieldMapDTO>();
			for (FieldMap fieldValue : entity.getDataprovider()) {
				FieldMapDTO fieldMapDTO = new FieldMapDTO();
				DTOUtils.copyProperties(fieldMapDTO, fieldValue);
				dataproviderMap.add(fieldMapDTO);
			}
			entityDTO.setDataprovider(dataproviderMap);

		} else {
			List<FieldMapDTO> fieldMap = new ArrayList<FieldMapDTO>();
			for (FieldMap fieldValue : entity.getFieldValues()) {
				FieldMapDTO fieldMapDTO = new FieldMapDTO();
				DTOUtils.copyProperties(fieldMapDTO, fieldValue);
				fieldMap.add(fieldMapDTO);
			}
			entityDTO.setFieldValues(fieldMap);
		}
		// Status
		StatusDTO status = new StatusDTO();
		status.setStatusKey(entity.getStatusKey());
		status.setStatusName(entity.getStatusName());
		entityDTO.setEntityStatus(status);

		// Domain and Global Entity Template on to Template DTO
		EntityTemplateDTO entityTemplate = entityDTO.getEntityTemplate();
		entityTemplate.setDomain(entityDTO.getDomain());
		entityTemplate.setPlatformEntityType(entity.getPlatformEntityType());

		// Tree on to entityDTO
		FieldMap treeMap = entity.getTree();
		if (treeMap != null) {
			FieldMapDTO treerDTO = new FieldMapDTO();
			treerDTO.setKey(treeMap.getKey());
			treerDTO.setValue(treeMap.getValue());
			entityDTO.setTree(treerDTO);
		}
		return entityDTO;
	}

	/**
	 * @Description Responsible to validate based field name*, field value* and
	 *              type
	 * @param EntitySearchDTO
	 * @return EntityDTO
	 */
	private void validateFetchByFields(EntitySearchDTO coreEntitySearchDTO) {

		validateMandatoryFields(coreEntitySearchDTO, FIELD_VALUES);
		ValidationUtils.validateCollection(FIELD_VALUES,
		        coreEntitySearchDTO.getFieldValues());

		if (coreEntitySearchDTO.getPlatformEntity() != null
		        && !isBlank(coreEntitySearchDTO.getPlatformEntity()
		                .getPlatformEntityType())
		        && !isValidGlobalEntity(coreEntitySearchDTO.getPlatformEntity()
		                .getPlatformEntityType())) {
			LOGGER.debug("<<-- validateFetchByFields, invalid entity type-->>");
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.name());
		}

		Boolean validateFieldsInTemplates = coreEntitySearchDTO.getDomain() != null
		        && isNotBlank(coreEntitySearchDTO.getDomain().getDomainName())
		        && coreEntitySearchDTO.getPlatformEntity() != null
		        && isNotBlank(coreEntitySearchDTO.getPlatformEntity()
		                .getPlatformEntityType());

		Boolean validateAgainstGlobalTemplate = false;
		if (validateFieldsInTemplates) {
			validateAgainstGlobalTemplate = coreEntitySearchDTO
			        .getPlatformEntity().getIsDefault();
		}

		for (FieldMapDTO field : coreEntitySearchDTO.getFieldValues()) {
			validateMandatoryFields(field, FIELD_KEY, FIELD_VALUE);
			if (validateFieldsInTemplates) {
				Boolean isValidField = false;
				if (validateAgainstGlobalTemplate) {
					PlatformEntityTemplateDTO platformEntityTemplate = globalEntityTemplateService
					        .getPlatformEntityTemplate(coreEntitySearchDTO
					                .getPlatformEntity()
					                .getPlatformEntityType());

					isValidField = globalEntityTemplateService
					        .isValidFieldName(coreEntitySearchDTO
					                .getPlatformEntity()
					                .getPlatformEntityType(), field.getKey(),
					                platformEntityTemplate
					                        .getPlatformEntityTemplateName());
					EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
					entityTemplateDTO
					        .setEntityTemplateName(platformEntityTemplate
					                .getPlatformEntityTemplateName());
					coreEntitySearchDTO.setEntityTemplate(entityTemplateDTO);
				} else if (coreEntitySearchDTO.getEntityTemplate() != null
				        && isNotBlank(coreEntitySearchDTO.getEntityTemplate()
				                .getEntityTemplateName())) {
					isValidField = true;
				} else {
					String template = null;
					if (coreEntitySearchDTO.getEntityTemplate() != null
					        && isNotBlank(coreEntitySearchDTO
					                .getEntityTemplate()
					                .getEntityTemplateName())) {
						template = coreEntitySearchDTO.getEntityTemplate()
						        .getEntityTemplateName();
					}
					List<String> templateNames = checkFieldValidaity(
					        coreEntitySearchDTO.getDomain().getDomainName(),
					        coreEntitySearchDTO.getPlatformEntity()
					                .getPlatformEntityType(), field.getKey(),
					        template);
					if (CollectionUtils.isNotEmpty(templateNames)) {
						isValidField = true;
						coreEntitySearchDTO.setTemplates(templateNames);
					}
				}
				if (!isValidField) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
					        SEARCH_FIELDS_LIST.getDescription());
				}
			}
		}

	}

	private void validateGlobalEntityAndDomain(
	        EntitySearchDTO coreEntitySearchDTO, Boolean domainMandatory,
	        Boolean globalEntityTypeMandatory) {

		if (domainMandatory) {
			ValidationUtils.validateMandatoryField(EMDataFields.DOMAIN,
			        coreEntitySearchDTO.getDomain().getDomainName());
		}

		if (globalEntityTypeMandatory) {
			ValidationUtils.validateMandatoryField(
			        EMDataFields.PLATFORM_ENTITY_TYPE, coreEntitySearchDTO
			                .getPlatformEntity().getPlatformEntityType());
		}
		if (coreEntitySearchDTO.getPlatformEntity() != null
		        && !isBlank(coreEntitySearchDTO.getPlatformEntity()
		                .getPlatformEntityType())
		        && !isValidGlobalEntity(coreEntitySearchDTO.getPlatformEntity()
		                .getPlatformEntityType())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.getDescription());
		}
	}

	/**
	 * @Description Responsible to validate a global entity type
	 * @param globalEntity
	 * 
	 * @return true indicates valid entity
	 */
	private Boolean isValidGlobalEntity(String globalEntity) {
		PlatformEntityDTO globalEntityDTO = globalEntityService
		        .getGlobalEntityWithName(globalEntity);
		return globalEntityDTO != null;
	}

	/**
	 * @Description Responsible to get entities by field values
	 * @param EntitySearchDTO
	 * 
	 * @return List<Entity>
	 */
	private List<Entity> getEntityByFieldAndValue(
	        EntitySearchDTO coreEntitySearchDTO) {
		// valid
		validateFetchByFields(coreEntitySearchDTO);

		// TODO check if fields exist in any template
		String entityType = null;
		if (coreEntitySearchDTO.getPlatformEntity() != null) {
			entityType = coreEntitySearchDTO.getPlatformEntity()
			        .getPlatformEntityType();
		}
		String domain = null;
		if (coreEntitySearchDTO.getDomain() != null) {
			domain = coreEntitySearchDTO.getDomain().getDomainName();
		}

		// TODO here
		String templateName = null;
		if (coreEntitySearchDTO.getEntityTemplate() != null
		        && isNotBlank(coreEntitySearchDTO.getEntityTemplate()
		                .getEntityTemplateName())) {
			templateName = coreEntitySearchDTO.getEntityTemplate()
			        .getEntityTemplateName();
			List<String> templates = new ArrayList<String>();
			templates.add(templateName);
			coreEntitySearchDTO.setTemplates(templates);
		}
		List<Entity> fields = new ArrayList<Entity>();

		// Get status key for deleted status
		Integer statusKey = statusService.getStatus(DELETED.name());

		FieldMap fieldMap = new FieldMap();
		DTOUtils.copyProperties(fieldMap, coreEntitySearchDTO.getFieldValues()
		        .get(0));
		// TODO commented as it is assumed all validations will take place in
		// AVOCADO
		// if (userEntityAccess.templates()) {
		if (CollectionUtils.isNotEmpty(coreEntitySearchDTO.getTemplates())) {
			for (String template : coreEntitySearchDTO.getTemplates()) {
				fields.addAll(entityRepository.getEntitiesByField(fieldMap,
				        entityType, domain, statusKey, template));
			}
		}
		return fields;

	}

	/**
	 * @Description Responsible to validate based on multiple field name*, field
	 *              value* and type
	 * @param EntitySearchDTO
	 * @return EntityDTO
	 */
	private void validateGetEntitiesByMultipleFields(
	        EntitySearchDTO coreEntitySearchDTO, Boolean isEntityTypeMandatory) {
		if (isEntityTypeMandatory) {
			validateMandatoryFields(coreEntitySearchDTO, FIELD_VALUES,
			        PLATFORM_ENTITY);
			validateMandatoryFields(coreEntitySearchDTO.getPlatformEntity(),
			        PLATFORM_ENTITY_TYPE);

			if (!isValidGlobalEntity(coreEntitySearchDTO.getPlatformEntity()
			        .getPlatformEntityType())) {
				LOGGER.debug("<<-- validateGetEntitiesByMultipleFields, invalid entity type-->>");
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        PLATFORM_ENTITY_TYPE.name());
			}

			Boolean validateFieldsInTemplates = coreEntitySearchDTO.getDomain() != null
			        && isNotBlank(coreEntitySearchDTO.getDomain()
			                .getDomainName())
			        && isNotBlank(coreEntitySearchDTO.getPlatformEntity()
			                .getPlatformEntityType());
			Boolean validateAgainstGlobalTemplate = coreEntitySearchDTO
			        .getPlatformEntity().getIsDefault();

			for (FieldMapDTO field : coreEntitySearchDTO.getFieldValues()) {
				validateMandatoryFields(field, FIELD_KEY, FIELD_VALUE);
				if (validateFieldsInTemplates) {
					Boolean isValidField = true;
					if (validateAgainstGlobalTemplate) {
						isValidField = globalEntityTemplateService
						        .isValidFieldName(coreEntitySearchDTO
						                .getPlatformEntity()
						                .getPlatformEntityType(), field
						                .getKey(), coreEntitySearchDTO
						                .getEntityTemplate()
						                .getEntityTemplateName());
					} else {
						String template = null;
						if (coreEntitySearchDTO.getEntityTemplate() != null
						        && isNotBlank(coreEntitySearchDTO
						                .getEntityTemplate()
						                .getEntityTemplateName())) {
							template = coreEntitySearchDTO.getEntityTemplate()
							        .getEntityTemplateName();
						}

						List<String> templateNames = checkFieldValidaity(
						        coreEntitySearchDTO.getDomain().getDomainName(),
						        coreEntitySearchDTO.getPlatformEntity()
						                .getPlatformEntityType(), field
						                .getKey(), template);
						if (CollectionUtils.isEmpty(templateNames)) {
							isValidField = false;
						}

						else {

							if (coreEntitySearchDTO.getEntityTemplate() == null
							        || isBlank(coreEntitySearchDTO
							                .getEntityTemplate()
							                .getEntityTemplateName())) {
								coreEntitySearchDTO.setTemplates(templateNames);
							}
						}
					}
					if (!isValidField) {
						throw new GalaxyException(
						        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
						        SEARCH_FIELDS_LIST.getDescription());
					}
				}
			}
		} else {
			validateMandatoryFields(coreEntitySearchDTO, FIELD_VALUES);
		}
	}

	private List<EntityDTO> getEntitiesByDomainAndType(
	        EntitySearchDTO coreEntitySearchDTO, Boolean isDataprovider,
	        Boolean returnAll) {
		List<EntityDTO> entitiesData = new ArrayList<EntityDTO>();

		Integer statusKey = statusService.getStatus(DELETED.name());

		List<Entity> entities = new ArrayList<Entity>();
		List<EntityTemplateDTO> templates = getTemplateName(coreEntitySearchDTO
		        .getDomain().getDomainName(), coreEntitySearchDTO
		        .getPlatformEntity().getPlatformEntityType());

		for (EntityTemplateDTO entityTemplateDTO : templates) {
			entities.addAll(entityRepository.getAllEntities(coreEntitySearchDTO
			        .getDomain().getDomainName(), coreEntitySearchDTO
			        .getPlatformEntity().getPlatformEntityType(),
			        entityTemplateDTO.getEntityTemplateName(), statusKey));
		}

		for (Entity entity : entities) {
			EntityDTO entityDTO = createEntityDTO(entity, isDataprovider,
			        returnAll);
			entitiesData.add(entityDTO);
		}
		return entitiesData;
	}

	/**
	 * 
	 * @Description This method fetches an entity based on the identifier
	 *              fields, which are template name, domain , global entity type
	 *              and identifier
	 * 
	 * @param EntityDTO
	 * @return EntityDTO
	 */
	@Override
	public EntityDTO getEntity(IdentityDTO identityDTO) {
		validateIdentifiers(identityDTO);
		FieldMap fieldMap = new FieldMap();
		DTOUtils.copyProperties(fieldMap, identityDTO.getIdentifier());
		// Get status key for deleted status
		Integer statusKey = statusService.getStatus(DELETED.name());
		Entity entity = entityRepository.getEntityByIdentifiers(identityDTO
		        .getDomain().getDomainName(), identityDTO.getEntityTemplate()
		        .getEntityTemplateName(), identityDTO.getPlatformEntity()
		        .getPlatformEntityType(), fieldMap, statusKey);
		if (entity == null) {
			LOGGER.debug("<<-- getEntity, no data available -->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		EntityDTO uniqueEntity = createEntityDTO(entity, false, true);
		// uniqueEntity
		// .setEntityId(entity.getEntityKey().getEntityId().toString());
		return uniqueEntity;
	}

	/**
	 * @param identityDTO
	 */
	private void validateIdentifiers(IdentityDTO identityDTO) {
		validateMandatoryFields(identityDTO, ENTITY_TEMPLATE, DOMAIN,
		        PLATFORM_ENTITY, IDENTIFIER);
		validateMandatoryFields(identityDTO.getPlatformEntity(),
		        PLATFORM_ENTITY_TYPE);
		validateMandatoryFields(identityDTO.getDomain(), DOMAIN_NAME);
		validateMandatoryFields(identityDTO.getIdentifier(), IDENTIFIER_KEY,
		        IDENTIFIER_VALUE);

		ValidationUtils.validateMandatoryField(
		        EMDataFields.ENTITY_TEMPLATE_NAME, identityDTO
		                .getEntityTemplate().getEntityTemplateName());

		if (!isValidGlobalEntity(identityDTO.getPlatformEntity()
		        .getPlatformEntityType())) {
			LOGGER.debug("<<-- getEntity, invalid entity type-->>");
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.name());
		}
	}

	/**
	 * 
	 * @Description This method fetches an entity based on global entity type
	 *              and identifier
	 * 
	 * @param FieldMapDTO
	 * @param PlatformEntityDTO
	 * @return EntityDTO
	 */
	@Override
	@Deprecated
	public EntityDTO getEntity(FieldMapDTO identifier,
	        PlatformEntityDTO globalEntityDTO) {
		validateMandatoryFields(globalEntityDTO, PLATFORM_ENTITY_TYPE);
		validateMandatoryFields(identifier, IDENTIFIER_KEY, IDENTIFIER_VALUE);

		if (!isValidGlobalEntity(globalEntityDTO.getPlatformEntityType())) {
			LOGGER.debug("<<-- getEntity, invalid entity type-->>");
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.name());
		}
		FieldMap fieldMap = new FieldMap();
		DTOUtils.copyProperties(fieldMap, identifier);
		// Get status key for deleted status
		Integer statusKey = statusService.getStatus(DELETED.name());
		Entity entity = entityRepository.getEntityByIdentifiers(null, null,
		        globalEntityDTO.getPlatformEntityType(), fieldMap, statusKey);
		if (entity == null) {
			LOGGER.debug("<<-- getEntity, no data available -->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		EntityDTO uniqueEntity = createEntityDTO(entity, false, true);
		// uniqueEntity
		// .setEntityId(entity.getEntityKey().getEntityId().toString());
		return uniqueEntity;
	}

	/**
	 * 
	 * @Description This method is responsible to fetch all details of an entity
	 *              except the field values and entity id
	 * 
	 * @param EntitySearchDTO
	 * @return Integer
	 */
	@Override
	public List<EntityDTO> getEntityDetailsWithDataprovider(
	        EntitySearchDTO coreEntitySearchDTO) {
		Boolean domainMandatory = true;
		Boolean globalEntityMandatory = true;
		// validate input, domain and uniqueUser mandatory
		validateGlobalEntityAndDomain(coreEntitySearchDTO, domainMandatory,
		        globalEntityMandatory);
		Boolean isDataprovider = true;
		Boolean returnAll = false;
		return getEntitiesByDomainAndType(coreEntitySearchDTO, isDataprovider,
		        returnAll);
	}

	/**
	 * 
	 * @Description This method is responsible to get the count of entities of a
	 *              type under a domain
	 * 
	 * @param EntitySearchDTO
	 * @return Integer
	 */
	@Override
	public Integer getEntitiesCountByTypeAndDomain(
	        EntitySearchDTO coreEntitySearchDTO) {
		validateTypeAndDomain(coreEntitySearchDTO);
		String templateName = getTemplateName(coreEntitySearchDTO);
		// Get status key for deleted status
		Integer statusKey = -1;
		if (isBlank(coreEntitySearchDTO.getStatusName())) {
			statusKey = statusService.getStatus(DELETED.name());
		} else {
			statusKey = statusService.getStatus(coreEntitySearchDTO
			        .getStatusName());
		}
		// totdo
		Integer entityCount = 0;
		if (isBlank(templateName)) {
			List<EntityTemplateDTO> templates = getTemplateName(
			        coreEntitySearchDTO.getDomain().getDomainName(),
			        coreEntitySearchDTO.getPlatformEntity()
			                .getPlatformEntityType());

			for (EntityTemplateDTO entityTemplateDTO : templates) {
				entityCount = entityCount
				        + entityRepository.getEntitiesCount(coreEntitySearchDTO
				                .getDomain().getDomainName(),
				                coreEntitySearchDTO.getPlatformEntity()
				                        .getPlatformEntityType(), statusKey,
				                entityTemplateDTO.getEntityTemplateName());
			}
		} else {
			entityCount = entityRepository.getEntitiesCount(coreEntitySearchDTO
			        .getDomain().getDomainName(), coreEntitySearchDTO
			        .getPlatformEntity().getPlatformEntityType(), statusKey,
			        templateName);
		}
		return entityCount;

	}

	private String getTemplateName(EntitySearchDTO coreEntitySearchDTO) {
		String templateName = null;

		if (coreEntitySearchDTO.getEntityTemplate() != null
		        && coreEntitySearchDTO.getEntityTemplate()
		                .getEntityTemplateName() != null) {
			templateName = coreEntitySearchDTO.getEntityTemplate()
			        .getEntityTemplateName();
		}
		return templateName;
	}

	private void validateTypeAndDomain(EntitySearchDTO coreEntitySearchDTO) {
		validateMandatoryFields(coreEntitySearchDTO, DOMAIN, PLATFORM_ENTITY);
		validateMandatoryFields(coreEntitySearchDTO.getDomain(), DOMAIN_NAME);
		validateMandatoryFields(coreEntitySearchDTO.getPlatformEntity(),
		        PLATFORM_ENTITY_TYPE);
		if (!isValidGlobalEntity(coreEntitySearchDTO.getPlatformEntity()
		        .getPlatformEntityType())) {
			LOGGER.debug("<<-- getEntitiesCountByTypeAndDomain, invalid entity type-->>");
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.name());
		}

	}

	/**
	 * 
	 * @Description This method is responsible to get the count of entities of a
	 *              type under a domain using a specific entity template
	 * 
	 * @param EntitySearchDTO
	 * @return Integer
	 */
	@Override
	public Integer getEntitiesCountByTemplate(
	        EntitySearchDTO coreEntitySearchDTO) {
		validateTemplateAndDomain(coreEntitySearchDTO);
		// Get status key for deleted status
		Integer statusKey = -1;
		Integer count = 0;
		if (isBlank(coreEntitySearchDTO.getStatusName())) {
			statusKey = statusService.getStatus(DELETED.name());
			count = entityRepository.getEntitiesCount(coreEntitySearchDTO
			        .getDomain().getDomainName(), coreEntitySearchDTO
			        .getPlatformEntity().getPlatformEntityType(), statusKey,
			        coreEntitySearchDTO.getEntityTemplate()
			                .getEntityTemplateName());
		} else {
			statusKey = statusService.getStatus(coreEntitySearchDTO
			        .getStatusName());
			count = entityRepository.getEntitiesCountByStatus(
			        coreEntitySearchDTO.getDomain().getDomainName(),
			        coreEntitySearchDTO.getPlatformEntity()
			                .getPlatformEntityType(), statusKey,
			        coreEntitySearchDTO.getEntityTemplate()
			                .getEntityTemplateName());
		}
		return count;
	}

	private void validateTemplateAndDomain(EntitySearchDTO coreEntitySearchDTO) {
		validateMandatoryFields(coreEntitySearchDTO, ENTITY_TEMPLATE, DOMAIN,
		        PLATFORM_ENTITY);
		validateMandatoryFields(coreEntitySearchDTO.getDomain(), DOMAIN_NAME);
		validateMandatoryFields(coreEntitySearchDTO.getPlatformEntity(),
		        PLATFORM_ENTITY_TYPE);
		validateMandatoryFields(coreEntitySearchDTO.getEntityTemplate(),
		        ENTITY_TEMPLATE_NAME);

		if (!isValidGlobalEntity(coreEntitySearchDTO.getPlatformEntity()
		        .getPlatformEntityType())) {
			LOGGER.debug("<<-- getEntitiesCountByTemplate, invalid entity type-->>");
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.name());
		}
	}

	//
	// @Override
	// public List<EntityDTO> getEntityIdentifiersByEntityIds(
	// List<String> entityIds) {
	// List<Entity> entities = entityRepository
	// .getEntityIdentifiersWithEntityIds(entityIds);
	// if (CollectionUtils.isEmpty(entities)) {
	// LOGGER.debug("<<-- getEntityIdentifiersByEntityIds, no data available-->>");
	// throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
	// }
	// List<EntityDTO> entitiesDTO = new ArrayList<EntityDTO>();
	//
	// for (Entity entity : entities) {
	// EntityDTO entityDTO = new EntityDTO();
	// appendEntityIdentifier(entityDTO, entity);
	// // Tree Data for Showing on hierarchy tree
	// FieldMap treeMap = entity.getTree();
	// if (treeMap != null) {
	// FieldMapDTO treerDTO = new FieldMapDTO();
	// treerDTO.setKey(treeMap.getKey());
	// treerDTO.setValue(treeMap.getValue());
	// entityDTO.setTree(treerDTO);
	// }
	// // Entity status to be used by UI to hide/show create button
	// StatusDTO entityStatus = new StatusDTO();
	// entityStatus.setStatusKey(entity.getStatusKey());
	// entityStatus.setStatusName(entity.getStatusName());
	// entityDTO.setEntityStatus(entityStatus);
	// entitiesDTO.add(entityDTO);
	// }
	// return entitiesDTO;
	// }

	private void appendEntityIdentifier(EntityDTO entityDTO, Entity entity) {
		// Global Entity
		PlatformEntityDTO globalEntityDTO = new PlatformEntityDTO();
		globalEntityDTO.setPlatformEntityType(entity.getPlatformEntityType());
		entityDTO.setPlatformEntity(globalEntityDTO);
		// Domain
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(entity.getDomain());
		entityDTO.setDomain(domain);
		// Entity Template
		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(entity.getEntityTemplateName());
		entityDTO.setEntityTemplate(entityTemplate);
		// Identifiers
		FieldMap identifierMap = entity.getIdentifier();
		FieldMapDTO identifierDTO = new FieldMapDTO();
		identifierDTO.setKey(identifierMap.getKey());
		identifierDTO.setValue(identifierMap.getValue());
		entityDTO.setIdentifier(identifierDTO);
	}

	/**
	 * @Description Responsible for deleting an entity
	 * @param nodeId
	 *            , entityName
	 * @return statusMessageDTO
	 */
	@Override
	public StatusMessageDTO deleteEntity(FieldMapDTO identifier,
	        String templateName, String domain, String platformEntityType) {

		LOGGER.debug("<<--Entry deleteEntity-->>");
		validateEntityIdentifier(identifier);

		StatusMessageDTO deleteStatus = new StatusMessageDTO();
		deleteStatus.setStatus(Status.FAILURE);

		FieldMap field = new FieldMap();
		field.setKey(identifier.getKey());
		field.setValue(identifier.getValue());

		// Get the existing entry
		Entity existingEntity = entityRepository.getEntityByIdentifier(domain,
		        templateName, platformEntityType, field);
		Integer existingStatus = existingEntity.getStatusKey();

		// Get Deleted Status key
		Integer deleteStatusKey = statusService.getStatus(DELETED.name());
		existingEntity.setStatusName(DELETED.name());
		existingEntity.setStatusKey(deleteStatusKey);

		// Update the entity through a batch
		entityRepository.updateStatusBatch(existingEntity, existingStatus);

		deleteStatus.setStatus(Status.SUCCESS);
		return deleteStatus;
	}

	/**
	 * Reusable Validation Method
	 * 
	 * @param entityId
	 */
	private Entity validateEntityIdentifier(FieldMapDTO identifierMap) {

		ValidationUtils.validateMandatoryFields(identifierMap,
		        EMDataFields.IDENTIFIER_KEY, EMDataFields.IDENTIFIER_KEY);

		FieldMap fieldMap = new FieldMap();
		fieldMap.setKey(identifierMap.getKey());
		fieldMap.setValue(identifierMap.getValue());

		Integer statusKey = statusService.getStatus(Status.DELETED.toString());

		Entity entityData = entityRepository.getEntityDetails(fieldMap,
		        statusKey);
		if (entityData == null
		        || entityData.getStatusName().equalsIgnoreCase(
		                DELETED.toString())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        ENTITY_ID.getDescription());
		}
		return entityData;
	}

	@Override
	public EntityDTO updateEntity(EntityDTO entityDTO) {
		LOGGER.debug("<<--Entry updateEntity-->>");
		EntityDTO returnDTO = new EntityDTO();
		Boolean isInsert = false;
		validateEntity(entityDTO, isInsert);
		setGlobalEntity(entityDTO);
		EntityDTO save = update(entityDTO);
		if (save == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.PERSISTENCE_EXCEPTION);
		}
		returnDTO.setDomain(entityDTO.getDomain());
		returnDTO.setEntityTemplate(entityDTO.getEntityTemplate());
		returnDTO.setPlatformEntity(entityDTO.getPlatformEntity());
		returnDTO.setIdentifier(entityDTO.getIdentifier());
		LOGGER.debug("<<--Exit updateEntity-->>");
		return returnDTO;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.galaxy.em.services.CoreEntityService#getEntity(java.lang.String)
	 */
	// @Deprecated
	// @Override
	// public EntityDTO getEntity(String entityId) {
	// ValidationUtils.validateMandatoryField(ENTITY_ID, entityId);
	// UUID uuid = null;
	// try {
	// uuid = UUID.fromString(entityId);
	// } catch (Exception e) {
	// throw new GalaxyException(INVALID_DATA_SPECIFIED,
	// ENTITY_ID.getDescription());
	// }
	// Entity entity = entityRepository.getEntityDetails(uuid);
	// validateResult(entity);
	// Boolean isDataProvider = false;
	// Boolean isReturnAll = true;
	// EntityDTO entityDTO = createEntityDTO(entity, isDataProvider,
	// isReturnAll);
	// // entityDTO.setEntityId(entity.getEntityKey().getEntityId().toString());
	// return entityDTO;
	// }

	/**
	 * @Description Responsible to validate an entity
	 * 
	 * @param entityDTO
	 */
	private void validateEntity(EntityDTO entityDTO, Boolean isInsert) {

		validateMandatoryFields(entityDTO, PLATFORM_ENTITY, IDENTIFIER,
		        ENTITY_TEMPLATE, ENTITY_STATUS);
		// if (isInsert) {
		// validateMandatoryFields(entityDTO, PARENT_ENTITY_ID);
		// }
		validateMandatoryFields(entityDTO.getEntityTemplate(),
		        ENTITY_TEMPLATE_NAME, DOMAIN);
		validateMandatoryFields(entityDTO.getEntityStatus(), STATUS_NAME);
		validateMandatoryFields(entityDTO.getPlatformEntity(),
		        PLATFORM_ENTITY_TYPE);

		// Validating Global Entity Name with DB
		if (!isGlobalEntityNameValid(entityDTO.getPlatformEntity()
		        .getPlatformEntityType())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.toString());
			// Validating Status Name with DB
		} else if (statusService.getStatus(entityDTO.getEntityStatus()
		        .getStatusName()) < 0) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        STATUS_NAME.toString());
		}
	}

	/**
	 * @Description Method for Validating GlobalEntityType
	 * @param globalEntityName
	 * @return boolean
	 */
	private boolean isGlobalEntityNameValid(String globalEntityName) {

		PlatformEntityDTO globalEntity = globalEntityService
		        .getGlobalEntityWithName(globalEntityName);

		return globalEntity != null;
	}

	/**
	 * @Description Responsible for saving an entity
	 * @param entityDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public EntityDTO saveEntity(EntityDTO entityDTO) {
		LOGGER.debug("<<--Entry saveEntity-->>");
		EntityDTO returnDTO = new EntityDTO();
		Boolean isInsert = true;
		validateEntity(entityDTO, isInsert);
		setGlobalEntity(entityDTO);
		EntityDTO save = save(entityDTO);
		if (save == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.PERSISTENCE_EXCEPTION);
		}
		returnDTO.setDomain(entityDTO.getDomain());
		returnDTO.setEntityTemplate(entityDTO.getEntityTemplate());
		returnDTO.setPlatformEntity(entityDTO.getPlatformEntity());
		returnDTO.setIdentifier(entityDTO.getIdentifier());
		LOGGER.debug("<<--Exit saveEntity-->>");
		return returnDTO;
	}

	private void setGlobalEntity(EntityDTO entityDTO) {

		PlatformEntityDTO globalEntityDTO = globalEntityService
		        .getGlobalEntityWithName(entityDTO.getPlatformEntity()
		                .getPlatformEntityType());
		if (globalEntityDTO == null) {

			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.toString());
		}
		entityDTO.setPlatformEntity(globalEntityDTO);
	}

	/**
	 * @Description Responsible to update an entity in cassandra
	 * 
	 * @param entityDTO
	 * @return entityDTO
	 */
	// private EntityDTO update(EntityDTO entityDTO) {
	// Entity entity = createEntityData(entityDTO);
	// entityRepository.updateEntity(entity);
	// return entityDTO;
	// }
	//
	private EntityDTO update(EntityDTO entityDTO) {
		Entity entity = createEntityData(entityDTO);

		// Get the existing entityString domain, String entityTemplate,
		Entity existingEntity = entityRepository.getEntityByIdentifier(
		        entity.getDomain(), entity.getEntityTemplateName(),
		        entity.getPlatformEntityType(), entity.getIdentifier());

		// Check if status update
		if (existingEntity.getStatusName().equalsIgnoreCase(
		        entity.getStatusName())) {
			// Same status
			entityRepository.updateEntity(entity);
		} else {
			entityRepository.updateStatusBatch(entity,
			        existingEntity.getStatusKey());
		}

		return entityDTO;
	}

	/**
	 * @Description Responsible for creating entity object
	 * 
	 * @param EntityDTO
	 * @return Entity
	 */
	private Entity createEntityData(EntityDTO entityDTO) {
		EntityTemplateDTO entityTemplate = entityDTO.getEntityTemplate();
		Entity entityData = new Entity();
		// entityKey.setEntityId(UUID.fromString(entityDTO.getEntityId()));

		entityData
		        .setEntityTemplateName(entityTemplate.getEntityTemplateName());
		entityData.setDomain(entityTemplate.getDomain().getDomainName());

		entityData.setPlatformEntityType(entityDTO.getPlatformEntity()
		        .getPlatformEntityType());

		entityData.setStatusName(entityDTO.getEntityStatus().getStatusName());
		// Get status_key from DB
		Integer status = -1;
		status = statusService.getStatus(entityData.getStatusName());
		entityData.setStatusKey(status);

		List<FieldMapDTO> fiedValues = entityDTO.getFieldValues();
		List<FieldMap> fieldValuesModel = new ArrayList<FieldMap>();
		for (FieldMapDTO fieldMapDTO : fiedValues) {
			FieldMap fieldMap = new FieldMap();
			fieldMap.setKey(fieldMapDTO.getKey());
			fieldMap.setValue(fieldMapDTO.getValue());
			fieldValuesModel.add(fieldMap);
		}
		entityData.setFieldValues(fieldValuesModel);

		if (entityDTO.getDataprovider() != null) {
			List<FieldMapDTO> dataprovider = entityDTO.getDataprovider();
			List<FieldMap> dataproviderModel = new ArrayList<FieldMap>();
			for (FieldMapDTO fieldMapDTO : dataprovider) {
				FieldMap fieldMap = new FieldMap();
				fieldMap.setKey(fieldMapDTO.getKey());
				fieldMap.setValue(fieldMapDTO.getValue());
				dataproviderModel.add(fieldMap);
			}
			entityData.setDataprovider(dataproviderModel);
		}
		if (entityDTO.getIdentifier().getKey() != null) {
			FieldMap identifierModel = new FieldMap();
			identifierModel.setKey(entityDTO.getIdentifier().getKey());
			identifierModel.setValue(entityDTO.getIdentifier().getValue());
			entityData.setIdentifier(identifierModel);
			entityData.setEntityId(entityDTO.getIdentifier().getValue());
		}
		if (entityDTO.getTree() != null) {
			FieldMap treeModel = new FieldMap();
			treeModel.setKey(entityDTO.getTree().getKey());
			treeModel.setValue(entityDTO.getTree().getValue());
			entityData.setTree(treeModel);
		}
		return entityData;
	}

	/**
	 * @Description Responsible to persist entity in cassandra
	 * 
	 * @param entityDTO
	 * @return entityDTO
	 */
	private EntityDTO save(EntityDTO entityDTO) {

		// Generate UUIDs before inserting
		// entityDTO.setEntityId(UUID.randomUUID().toString());

		// Prepare Entity Model from EntityDTO
		Entity entity = createEntityData(entityDTO);
		entityRepository.insertEntity(entity);
		return entityDTO;
	}

	@Override
	public EntityDTO getDomainEntity(String domainName) {
		Integer statusKey = statusService.getStatus(Status.DELETED.toString());
		FieldMap fieldMap = new FieldMap();
		fieldMap.setKey(EMDataFields.DOMAIN.toString());
		fieldMap.setValue(domainName);

		String entityType = TENANT.getFieldName();
		List<Entity> entitiesByField = entityRepository.getEntitiesByField(
		        fieldMap, entityType, statusKey);

		if (CollectionUtils.isEmpty(entitiesByField)) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		if (entitiesByField.size() > 1) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DUPLICATE_RECORDS);
		}
		EntityDTO entityDTO = createEntityDTO(entitiesByField.get(0), false,
		        true);
		return entityDTO;
	}

	@Override
	public List<EntityDTO> getEntities(EntitySearchDTO entitySearchDTO) {
		// Get status key for deleted status
		Integer statusKey = statusService.getStatus(DELETED.name());
		List<Entity> entities = entityRepository.getEntities(entitySearchDTO
		        .getDomain().getDomainName(), entitySearchDTO.getEntityType(),
		        statusKey, entitySearchDTO.getEntityTemplate()
		                .getEntityTemplateName());
		if (CollectionUtils.isEmpty(entities)) {
			LOGGER.debug("<<-- getEntitiesByFields, no data for filter condition-->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		List<EntityDTO> entitiesDtos = new ArrayList<EntityDTO>();
		Boolean isDataProvider = true;
		Boolean returnAll = false;
		for (Entity entity : entities) {
			entitiesDtos
			        .add(createEntityDTO(entity, isDataProvider, returnAll));
		}
		return entitiesDtos;
	}

	@Override
	public EntityDTO getEntityDataProvider(IdentityDTO identityDTO) {
		validateIdentifiers(identityDTO);
		FieldMap fieldMap = new FieldMap();
		DTOUtils.copyProperties(fieldMap, identityDTO.getIdentifier());
		// Get status key for deleted status
		Integer statusKey = statusService.getStatus(DELETED.name());
		Entity entity = entityRepository.getEntityByIdentifiers(identityDTO
		        .getDomain().getDomainName(), identityDTO.getEntityTemplate()
		        .getEntityTemplateName(), identityDTO.getPlatformEntity()
		        .getPlatformEntityType(), fieldMap, statusKey);
		if (entity == null) {
			LOGGER.debug("<<-- getEntity, no data available -->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		Boolean isDataprovider = true;
		Boolean returnAll = false;
		EntityDTO uniqueEntity = createEntityDTO(entity, isDataprovider,
		        returnAll);

		return uniqueEntity;
	}

	@Override
	public List<EntityStatusCountDTO> getEntitiesCountByStatus(
	        EntitySearchDTO coreEntitySearchDTO) {
		validateTypeAndDomain(coreEntitySearchDTO);
		String templateName = getTemplateName(coreEntitySearchDTO);
		// If status is present in input fetch for specific status, else return
		// all
		List<EntityStatusCountDTO> statusCountDTOs = new ArrayList<EntityStatusCountDTO>();

		if (isNotBlank(coreEntitySearchDTO.getStatusName())) {
			EntityStatusCountDTO entityStatusCountDTO = new EntityStatusCountDTO();
			Integer statusKey = statusService.getStatus(coreEntitySearchDTO
			        .getStatusName());
			Integer count = getEntityCount(coreEntitySearchDTO, templateName,
			        statusKey);
			entityStatusCountDTO.setCount(count);
			statusCountDTOs.add(entityStatusCountDTO);
		} else {
			// fetch for all status's
			List<StatusDTO> statusDTOs = statusService.getAllStatus();
			for (StatusDTO statusDTO : statusDTOs) {
				Integer statusCount = 0;
				EntityStatusCountDTO entityStatusCountDTO = new EntityStatusCountDTO();
				statusCount = getEntityCount(coreEntitySearchDTO, templateName,
				        statusDTO.getStatusKey());
				entityStatusCountDTO.setCount(statusCount);
				entityStatusCountDTO.setStatusName(statusDTO.getStatusName());
				statusCountDTOs.add(entityStatusCountDTO);
			}
		}
		return statusCountDTOs;
	}

	/**
	 * @param coreEntitySearchDTO
	 * @param templateName
	 * @param statusKey
	 * @return
	 */
	private Integer getEntityCount(EntitySearchDTO coreEntitySearchDTO,
	        String templateName, Integer statusKey) {
		Integer count = 0;
		if (isNotBlank(templateName)) {
			count = entityRepository.getEntitiesCountByStatus(
			        coreEntitySearchDTO.getDomain().getDomainName(),
			        coreEntitySearchDTO.getPlatformEntity()
			                .getPlatformEntityType(), statusKey, templateName);
		} else {
			List<EntityTemplateDTO> templates = getTemplateName(
			        coreEntitySearchDTO.getDomain().getDomainName(),
			        coreEntitySearchDTO.getPlatformEntity()
			                .getPlatformEntityType());

			for (EntityTemplateDTO entityTemplateDTO : templates) {
				count = count
				        + entityRepository
				                .getEntitiesCountByStatus(coreEntitySearchDTO
				                        .getDomain().getDomainName(),
				                        coreEntitySearchDTO.getPlatformEntity()
				                                .getPlatformEntityType(),
				                        statusKey, entityTemplateDTO
				                                .getEntityTemplateName());
			}
		}
		return count;
	}

	@Override
	public List<EntityDTO> getEntitiesByEntityTemplateAndMultipleFieldsAndStatus(
	        EntitySearchDTO coreEntitySearchDTO) {
		Boolean isEntityTypeMandatory = false;
		validateGetEntitiesByMultipleFields(coreEntitySearchDTO,
		        isEntityTypeMandatory);
		List<Entity> fields = new ArrayList<Entity>();
		String entityType = null;

		if (coreEntitySearchDTO.getPlatformEntity() != null) {
			entityType = coreEntitySearchDTO.getPlatformEntity()
			        .getPlatformEntityType();
		}
		String domain = null;
		if (coreEntitySearchDTO.getDomain() != null) {
			domain = coreEntitySearchDTO.getDomain().getDomainName();
		}

		List<FieldMap> fieldList = new ArrayList<FieldMap>();
		for (FieldMapDTO fieldMap : coreEntitySearchDTO.getFieldValues()) {
			FieldMap field = new FieldMap();
			field.setKey(fieldMap.getKey());
			field.setValue(fieldMap.getValue());
			fieldList.add(field);
		}
		String entityTemplateName = "";
		if (coreEntitySearchDTO.getEntityTemplate() != null) {
			if (StringUtils.isNotBlank(coreEntitySearchDTO.getEntityTemplate()
			        .getEntityTemplateName())) {
				entityTemplateName = coreEntitySearchDTO.getEntityTemplate()
				        .getEntityTemplateName();
			}
		}
		Integer statusKey = -1;

		if (isNotBlank(coreEntitySearchDTO.getStatusName())) {
			statusKey = statusService.getStatus(coreEntitySearchDTO
			        .getStatusName());
			if (isBlank(entityTemplateName)) {
				List<EntityTemplateDTO> templates = getTemplateName(domain,
				        entityType);
				for (EntityTemplateDTO entityTemplateDTO : templates) {
					fields.addAll(entityRepository
					        .getEntitiesByFieldsAndStatus(fieldList, domain,
					                entityType, statusKey,
					                entityTemplateDTO.getEntityTemplateName()));
				}
			} else {
				fields = entityRepository.getEntitiesByFieldsAndStatus(
				        fieldList, domain, entityType, statusKey,
				        entityTemplateName);
			}
		} else {
			// Get status key for deleted status
			statusKey = statusService.getStatus(DELETED.name());
			if (isBlank(entityTemplateName)) {
				List<EntityTemplateDTO> templates = getTemplateName(domain,
				        entityType);
				for (EntityTemplateDTO entityTemplateDTO : templates) {
					fields.addAll(entityRepository.getEntitiesByFields(
					        fieldList, domain, entityType, statusKey,
					        entityTemplateDTO.getEntityTemplateName()));
				}
			} else {
				fields = entityRepository.getEntitiesByFields(fieldList,
				        domain, entityType, statusKey, entityTemplateName);
			}
		}

		List<EntityDTO> fieldsDto = new ArrayList<EntityDTO>();
		Boolean isDataProvider = true;
		Boolean returnAll = false;

		for (Entity entity : fields) {
			EntityDTO entityDTO = createEntityDTO(entity, isDataProvider,
			        returnAll);
			fieldsDto.add(entityDTO);
		}
		if (CollectionUtils.isEmpty(fieldsDto)) {
			LOGGER.debug("<<-- getEntitiesByMultipleFields, no data for filter condition-->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return fieldsDto;
	}

	@Override
	public EntityCountDTO getEntitySearchCount(
	        EntitySearchDTO coreEntitySearchDTO) {
		Boolean isEntityTypeMandatory = false;
		validateGetEntitiesByMultipleFields(coreEntitySearchDTO,
		        isEntityTypeMandatory);
		String entityType = null;

		if (coreEntitySearchDTO.getPlatformEntity() != null) {
			entityType = coreEntitySearchDTO.getPlatformEntity()
			        .getPlatformEntityType();
		}
		String domain = null;
		if (coreEntitySearchDTO.getDomain() != null) {
			domain = coreEntitySearchDTO.getDomain().getDomainName();
		}

		List<FieldMap> fieldList = new ArrayList<FieldMap>();
		for (FieldMapDTO fieldMap : coreEntitySearchDTO.getFieldValues()) {
			FieldMap field = new FieldMap();
			field.setKey(fieldMap.getKey());
			field.setValue(fieldMap.getValue());
			fieldList.add(field);
		}
		String entityTemplateName = "";
		if (coreEntitySearchDTO.getEntityTemplate() != null) {
			if (StringUtils.isNotBlank(coreEntitySearchDTO.getEntityTemplate()
			        .getEntityTemplateName())) {
				entityTemplateName = coreEntitySearchDTO.getEntityTemplate()
				        .getEntityTemplateName();
			}
		}
		Integer statusKey = null;
		Integer count = 0;
		Integer deletedStatus = 0;

		if (isNotBlank(coreEntitySearchDTO.getStatusName())) {
			statusKey = statusService.getStatus(coreEntitySearchDTO
			        .getStatusName());

		} else {
			deletedStatus = statusService.getStatus(DELETED.name());
		}
		if (isNotBlank(entityTemplateName)) {
			if (statusKey == null) {
				count = entityRepository.getEntitiesCountByFieldsNoStatus(
				        domain, entityType, deletedStatus, entityTemplateName,
				        fieldList);
			} else {
				count = entityRepository.getEntitiesCountByFields(domain,
				        entityType, statusKey, entityTemplateName, fieldList);
			}

		} else {
			List<EntityTemplateDTO> templates = getTemplateName(domain,
			        entityType);
			for (EntityTemplateDTO entityTemplateDTO : templates) {
				count = count
				        + entityRepository.getEntitiesCountByFields(domain,
				                entityType, statusKey,
				                entityTemplateDTO.getEntityTemplateName(),
				                fieldList);

				if (statusKey == null) {
					count = count
					        + entityRepository
					                .getEntitiesCountByFieldsNoStatus(domain,
					                        entityType, statusKey,
					                        entityTemplateDTO
					                                .getEntityTemplateName(),
					                        fieldList);
				} else {
					count = count
					        + entityRepository.getEntitiesCountByFields(domain,
					                entityType, statusKey,
					                entityTemplateDTO.getEntityTemplateName(),
					                fieldList);
				}

			}
		}

		EntityCountDTO entityCountDTO = new EntityCountDTO();
		entityCountDTO.setCount(count);
		return entityCountDTO;
	}

	@Override
	public List<IdentityDTO> saveEntities(List<EntityDTO> entitiesDTO) {
		List<IdentityDTO> returnIdentities = new ArrayList<IdentityDTO>();
		Boolean isInsert = true;
		List<Entity> entities = new ArrayList<Entity>();

		for (EntityDTO entityDTO : entitiesDTO) {
			validateEntity(entityDTO, isInsert);
			setGlobalEntity(entityDTO);

			// Generate UUIDs before inserting
			entityDTO.setEntityId(UUID.randomUUID().toString());

			// Prepare Entity Model from EntityDTO
			Entity entity = createEntityData(entityDTO);
			entities.add(entity);

			// Set the return identifier
			IdentityDTO identity = new IdentityDTO();
			identity.setDomain(entityDTO.getDomain());

			PlatformEntityDTO globalEntity = new PlatformEntityDTO();
			globalEntity.setPlatformEntityType(entityDTO.getPlatformEntity()
			        .getPlatformEntityType());
			identity.setPlatformEntity(globalEntity);

			identity.setIdentifier(entityDTO.getIdentifier());

			EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
			entityTemplate.setEntityTemplateName(entityDTO.getEntityTemplate()
			        .getEntityTemplateName());
			identity.setEntityTemplate(entityTemplate);
			returnIdentities.add(identity);
		}
		entityRepository.insertEntities(entities);
		return returnIdentities;
	}

	private List<String> checkFieldValidaity(String domain,
	        String platformEntity, String field, String template) {
		String templateName = null;
		Boolean isValid = false;
		List<String> templatesList = new ArrayList<String>();

		if (isBlank(template)) {
			List<EntityTemplateDTO> templates = templateService
			        .getEntityTemplatesByDomainAndType(domain, platformEntity);

			for (EntityTemplateDTO entityTemplateDTO : templates) {
				isValid = templateService.isValidFieldName(domain,
				        platformEntity, field,
				        entityTemplateDTO.getEntityTemplateName());
				if (isValid) {
					templateName = entityTemplateDTO.getEntityTemplateName();
					templatesList.add(templateName);
				}
			}
			if (isBlank(templateName)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        SEARCH_FIELDS_LIST.getDescription());
			}
		} else {
			isValid = templateService.isValidFieldName(domain, platformEntity,
			        field, template);
			if (isValid) {
				templateName = template;
				templatesList.add(templateName);
			}

		}
		return templatesList;
	}

	private List<EntityTemplateDTO> getTemplateName(String domain,
	        String entityType) {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		List<EntityTemplateDTO> templates = new ArrayList<EntityTemplateDTO>();

		PlatformEntityDTO platformEntityDTO = globalEntityService
		        .getGlobalEntityWithName(entityType);
		if (platformEntityDTO.getIsDefault()) {
			PlatformEntityTemplateDTO platformEntityTemplateDTO = globalEntityTemplateService
			        .getPlatformEntityTemplate(entityType);
			entityTemplateDTO.setEntityTemplateName(platformEntityTemplateDTO
			        .getPlatformEntityTemplateName());
			templates.add(entityTemplateDTO);
		} else {
			templates = templateService.getEntityTemplatesByDomainAndType(
			        domain, entityType);
		}
		return templates;
	}

	@Override
	public StatusMessageDTO updateStatus(IdentityDTO identity, String status) {
		// Validate the input
		validateIdentifiers(identity);
		ValidationUtils.validateMandatoryField(STATUS_NAME, status);

		FieldMap fieldMap = new FieldMap();
		fieldMap.setKey(identity.getIdentifier().getKey());
		fieldMap.setValue(identity.getIdentifier().getValue());

		// Fetch the existing entry
		Entity existingEntity = entityRepository.getEntityByIdentifier(identity
		        .getDomain().getDomainName(), identity.getEntityTemplate()
		        .getEntityTemplateName(), identity.getPlatformEntity()
		        .getPlatformEntityType(), fieldMap);

		// Get status key
		Integer statusKey = statusService.getStatus(status);

		Integer existingStatus = existingEntity.getStatusKey();

		// Set the status to be updated
		existingEntity.setStatusName(status);
		existingEntity.setStatusKey(statusKey);

		StatusMessageDTO updateStatus = new StatusMessageDTO();
		updateStatus.setStatus(Status.FAILURE);

		// Update using batch
		entityRepository.updateStatusBatch(existingEntity, existingStatus);
		updateStatus.setStatus(Status.SUCCESS);

		return updateStatus;
	}
}
