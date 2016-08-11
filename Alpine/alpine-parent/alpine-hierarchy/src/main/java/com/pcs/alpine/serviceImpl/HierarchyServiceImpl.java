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

import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.services.enums.HMDataFields.ACTOR;
import static com.pcs.alpine.services.enums.HMDataFields.ACTORS;
import static com.pcs.alpine.services.enums.HMDataFields.ACTOR_TYPE;
import static com.pcs.alpine.services.enums.HMDataFields.CHILD;
import static com.pcs.alpine.services.enums.HMDataFields.CHILD_V;
import static com.pcs.alpine.services.enums.HMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.HMDataFields.DOMAIN_NAME;
import static com.pcs.alpine.services.enums.HMDataFields.END_ENTITY;
import static com.pcs.alpine.services.enums.HMDataFields.END_ENTITY_DOMAIN;
import static com.pcs.alpine.services.enums.HMDataFields.END_ENTITY_DOMAIN_NAME;
import static com.pcs.alpine.services.enums.HMDataFields.END_ENTITY_IDENTIFIER;
import static com.pcs.alpine.services.enums.HMDataFields.END_ENTITY_IDENTIFIER_KEY;
import static com.pcs.alpine.services.enums.HMDataFields.END_ENTITY_IDENTIFIER_VALUE;
import static com.pcs.alpine.services.enums.HMDataFields.END_ENTITY_PLATFORM_ENTITY;
import static com.pcs.alpine.services.enums.HMDataFields.END_ENTITY_PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.HMDataFields.END_ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.HMDataFields.END_TEMPLATE;
import static com.pcs.alpine.services.enums.HMDataFields.ENTITY_STATUS;
import static com.pcs.alpine.services.enums.HMDataFields.ENTITY_TEMPLATE;
import static com.pcs.alpine.services.enums.HMDataFields.ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.HMDataFields.IDENTIFIER;
import static com.pcs.alpine.services.enums.HMDataFields.IDENTIFIER_KEY;
import static com.pcs.alpine.services.enums.HMDataFields.IDENTIFIER_VALUE;
import static com.pcs.alpine.services.enums.HMDataFields.INTERMEDIATE_TEMPLATES;
import static com.pcs.alpine.services.enums.HMDataFields.LATITUDE;
import static com.pcs.alpine.services.enums.HMDataFields.LONGITUDE;
import static com.pcs.alpine.services.enums.HMDataFields.MARKER_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.HMDataFields.MY_DOMAIN;
import static com.pcs.alpine.services.enums.HMDataFields.NAME;
import static com.pcs.alpine.services.enums.HMDataFields.PARENT;
import static com.pcs.alpine.services.enums.HMDataFields.PLATFORM_ENTITY;
import static com.pcs.alpine.services.enums.HMDataFields.PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.HMDataFields.RELATION_ATTACHEDTO;
import static com.pcs.alpine.services.enums.HMDataFields.SEARCH_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.HMDataFields.SEARCH_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.HMDataFields.START_ENTITY_DOMAIN;
import static com.pcs.alpine.services.enums.HMDataFields.START_ENTITY_DOMAIN_NAME;
import static com.pcs.alpine.services.enums.HMDataFields.START_ENTITY_IDENTIFIER;
import static com.pcs.alpine.services.enums.HMDataFields.START_ENTITY_IDENTIFIER_KEY;
import static com.pcs.alpine.services.enums.HMDataFields.START_ENTITY_IDENTIFIER_VALUE;
import static com.pcs.alpine.services.enums.HMDataFields.START_ENTITY_PLATFORM_ENTITY;
import static com.pcs.alpine.services.enums.HMDataFields.START_ENTITY_PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.HMDataFields.START_ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.HMDataFields.STATUS_NAME;
import static com.pcs.alpine.services.enums.HMDataFields.SUBJECT;
import static com.pcs.alpine.services.enums.HMDataFields.SUBJECTS;
import static com.pcs.alpine.services.enums.HMDataFields.SUBJECT_TYPE;
import static com.pcs.alpine.services.enums.HMDataFields.TAG_TYPE;
import static com.pcs.alpine.services.enums.HMDataFields.TENANT;
import static com.pcs.alpine.services.enums.HMDataFields.TENANT_TYPE;
import static com.pcs.alpine.services.enums.HierarchyNodeTypes.ENTITY;
import static com.pcs.alpine.services.enums.HierarchyNodeTypes.TEMPLATE;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pcs.alpine.commons.dto.GeneralBatchResponse;
import com.pcs.alpine.commons.dto.HierarchyTagCountDTO;
import com.pcs.alpine.commons.dto.HierarchyTagSearchDTO;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.constant.CommonConstants;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.model.DistributionEntity;
import com.pcs.alpine.model.HierarchyEntity;
import com.pcs.alpine.model.HierarchyEntityTemplate;
import com.pcs.alpine.model.TaggedEntity;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.HierarchyService;
import com.pcs.alpine.services.PlatformEntityService;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.Actor;
import com.pcs.alpine.services.dto.AttachHierarchyDTO;
import com.pcs.alpine.services.dto.AttachHierarchySearchDTO;
import com.pcs.alpine.services.dto.CoordinatesDTO;
import com.pcs.alpine.services.dto.DomainAccessDTO;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntitiesByTagsPayload;
import com.pcs.alpine.services.dto.EntityAssignDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityRangeDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.GeoTaggedEntitiesDTO;
import com.pcs.alpine.services.dto.GeoTaggedEntityDTO;
import com.pcs.alpine.services.dto.HierarchyDTO;
import com.pcs.alpine.services.dto.HierarchyRelationDTO;
import com.pcs.alpine.services.dto.HierarchyReturnDTO;
import com.pcs.alpine.services.dto.HierarchySearchDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.dto.Subject;
import com.pcs.alpine.services.dto.TagRangePayload;
import com.pcs.alpine.services.enums.EMDataFields;
import com.pcs.alpine.services.enums.HMDataFields;
import com.pcs.alpine.services.enums.HierarchyNodeTypes;
import com.pcs.alpine.services.repository.HierarchyRepository;

/**
 * HierarchyServiceImpl
 * 
 * @description Service Implementation for hierarchy services
 * @author Daniela (PCSEG191)
 * @author Riyas (PCSEG296)
 * @date 18 Oct 2015
 * @since alpine-1.0.0
 */

@Service
public class HierarchyServiceImpl implements HierarchyService {

	@Autowired
	@Qualifier("hierarchyNeo")
	HierarchyRepository hierarchyRepository;

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	private CoreEntityService coreEntityService;

	@Autowired
	private StatusService statusService;

	@Autowired
	private PlatformEntityService platformEntityService;

	@Autowired
	private PlatformEntityTemplateService platformEntityTemplateService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EntityTemplateService entityTemplateService;

	@Override
	public IdentityDTO createTenantNode(HierarchyDTO hierarchyDTO) {
		// validate mandatory fields
		validateTenant(hierarchyDTO);

		// set global entity type as tenant
		setEntityTypeAndDomain(hierarchyDTO);

		// Validate if node with same name exists in the hierarchy
		List<String> existingNode = hierarchyRepository
		        .getHierarchyByTenantDomain(hierarchyDTO.getActor()
		                .getMyDomain().getDomainName(), hierarchyDTO
		                .getTenant().getMyDomain().getDomainName());

		if (!existingNode.isEmpty()) {
			// Invalid parent
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.SPECIFIC_DATA_ALREADY_EXISTS,
			        TENANT.getFieldName());
		}

		// get the parent node details
		HierarchyEntity hierarchyEntity = new HierarchyEntity();
		hierarchyEntity.setActorTenantDomain(hierarchyDTO.getActor()
		        .getMyDomain().getDomainName());
		hierarchyEntity.setDomain(hierarchyDTO.getTenant().getDomain()
		        .getDomainName());
		hierarchyEntity.setEntityType(hierarchyDTO.getTenant()
		        .getPlatformEntity().getPlatformEntityType());
		hierarchyEntity.setIdentifierKey(hierarchyDTO.getTenant()
		        .getIdentifier().getKey());
		hierarchyEntity.setIdentifierValue(hierarchyDTO.getTenant()
		        .getIdentifier().getValue());
		hierarchyEntity.setRelationship(CHILD.getVariableName());

		hierarchyEntity.setStatus(hierarchyDTO.getTenant().getEntityStatus()
		        .getStatusName());
		hierarchyEntity.setTemplateName(hierarchyDTO.getTenant()
		        .getEntityTemplate().getEntityTemplateName());
		hierarchyEntity.setMyDomain(hierarchyDTO.getTenant().getMyDomain()
		        .getDomainName());

		IdentityDTO nodeIdentity = new IdentityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(hierarchyDTO.getActor().getMyDomain()
		        .getDomainName());
		nodeIdentity.setDomain(domain);
		nodeIdentity.setEntityTemplate((hierarchyDTO.getTenant()
		        .getEntityTemplate()));
		nodeIdentity.setPlatformEntity(hierarchyDTO.getTenant()
		        .getPlatformEntity());
		nodeIdentity.setIdentifier(hierarchyDTO.getTenant().getIdentifier());

		// Fetch the dataprovider from entity
		EntityDTO tenantEntity = coreEntityService
		        .getEntityDataProvider(nodeIdentity);
		hierarchyEntity.setDataprovider(getDataProvider(tenantEntity
		        .getDataprovider()));
		hierarchyEntity.setTree(getTree(tenantEntity.getTree()));
		Integer createdNodeId = hierarchyRepository
		        .insertTenantHierarchy(hierarchyEntity);
		if (createdNodeId < 0) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.PERSISTENCE_EXCEPTION);
		} else {
			// Hierarchy cannot be created
			return nodeIdentity;
		}
	}

	@Override
	public StatusMessageDTO attachParents(HierarchyDTO hierarchy) {
		validateHierarchy(hierarchy);
		valdateHierarchyParents(hierarchy);
		getHierarchyDataProvider(hierarchy);
		hierarchyRepository.attachParents(hierarchy);
		StatusMessageDTO status = new StatusMessageDTO(Status.SUCCESS);
		return status;
	}

	@Override
	public StatusMessageDTO attachChildren(HierarchyDTO hierarchy) {
		validateHierarchy(hierarchy);
		valdateHierarchyChild(hierarchy);
		getHierarchyDataProvider(hierarchy);
		hierarchyRepository.attachChildren(hierarchy);
		StatusMessageDTO status = new StatusMessageDTO(Status.SUCCESS);
		return status;
	}

	private void getHierarchyDataProvider(HierarchyDTO hierarchy) {
		hierarchy.setActor(getEntityDataProvider(hierarchy.getActor()));
		for (EntityDTO subject : hierarchy.getSubjects()) {
			EntityDTO entityDataProvider = getEntityDataProvider(subject);
			subject.setDataprovider(entityDataProvider.getDataprovider());
			subject.setTree(entityDataProvider.getTree());
		}
	}

	private EntityDTO getEntityDataProvider(EntityDTO entity) {
		IdentityDTO identityDTO = new IdentityDTO();
		identityDTO.setDomain(entity.getDomain());
		identityDTO.setIdentifier(entity.getIdentifier());
		identityDTO.setPlatformEntity(entity.getPlatformEntity());
		identityDTO.setEntityTemplate(entity.getEntityTemplate());
		EntityDTO entityDataProvider = coreEntityService
		        .getEntityDataProvider(identityDTO);
		return entityDataProvider;
	}

	private void validateEntityDto(EntityDTO entity) {
		validateIdentifiers(entity);

		if (entity.getEntityStatus() != null) {
			ValidationUtils.validateMandatoryField(EMDataFields.STATUS_NAME,
			        entity.getEntityStatus().getStatusName());
			if (!(entity.getEntityStatus().getStatusName()
			        .equalsIgnoreCase(Status.ACTIVE.name()))
			        && !(entity.getEntityStatus().getStatusName()
			                .equalsIgnoreCase(Status.INACTIVE.name()))
			        && !(entity.getEntityStatus().getStatusName()
			                .equalsIgnoreCase(Status.ALLOCATED.name()))) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        CommonConstants.STATUS_NAME);
			}
		} else {
			StatusDTO entityStatus = new StatusDTO();
			entityStatus.setStatusName(Status.ACTIVE.name());
			entity.setEntityStatus(entityStatus);
		}
	}

	private void validateEntities(List<EntityDTO> entities) {
		for (EntityDTO entity : entities) {
			validateEntityDto(entity);
		}
	}

	private void validateHierarchy(HierarchyDTO hierarchy) {
		ValidationUtils.validateMandatoryFields(hierarchy, ACTOR, SUBJECTS);
		validateEntities(hierarchy.getSubjects());
		validateEntityDto(hierarchy.getActor());

		// validate myDomian
		if (hierarchy.getIsParentDomain() != null
		        && hierarchy.getIsParentDomain()) {
			validateMyDomain(hierarchy.getActor().getPlatformEntity()
			        .getPlatformEntityType(), hierarchy.getActor()
			        .getIdentifier().getValue());
		}
	}

	private void validateTenantType(EntityDTO entity) {
		if (TENANT.name().equals(
		        entity.getPlatformEntity().getPlatformEntityType())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.TENANT_LINK_NOT_ALLOWED);
		}
	}

	private void valdateHierarchyChild(HierarchyDTO hierarchy) {
		String parentDomain = hierarchy.getActor().getDomain().getDomainName();
		if (!hierarchyRepository.isTenantExist(parentDomain)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        "Actor Domain");
		}
		Set<String> childDomains = new HashSet<String>();

		// construct hierarchy entity
		HierarchyEntity hierarchyEntity = constructHierarchyEntity(hierarchy
		        .getActor());
		List<HierarchyEntity> hierarchies;
		try {
			hierarchies = hierarchyRepository.getParents(hierarchyEntity, null,
			        null);
		} catch (NoResultException e) {
			hierarchies = null;
		}
		for (EntityDTO entity : hierarchy.getSubjects()) {
			// validateTenantType(entity);
			if (CollectionUtils.isNotEmpty(hierarchies)) {
				HierarchyEntity resultEntity = constructHierarchyEntity(entity);
				if (hierarchies.contains(resultEntity)) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.CYCLIC_ATTACHMENT_NOT_ALLOWED);
				}
			}
			childDomains.add("'" + entity.getDomain().getDomainName() + "'");

		}

		// TODO check here
		if (TENANT.name().equals(
		        hierarchy.getActor().getPlatformEntity()
		                .getPlatformEntityType())) {
			parentDomain = hierarchyRepository.getTenantDomainName(hierarchy
			        .getActor());
			if (StringUtils.isBlank(parentDomain)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        ACTOR.getDescription());
			}
			childDomains.remove("'" + parentDomain + "'");
		} else {
			childDomains.remove("'"
			        + hierarchy.getActor().getDomain().getDomainName() + "'");
		}
		if (CollectionUtils.isNotEmpty(childDomains)) {
			HashSet<String> childrenSet = hierarchyRepository
			        .getChildrenOfEntity(parentDomain, childDomains);

			if (childrenSet.size() != childDomains.size()) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.ENTITIES_FROM_WRONG_HIERARCHY);
			}
		}
	}

	private void valdateAttachHierarchyChild(AttachHierarchyDTO hierarchy) {

		Actor actor = hierarchy.getActor();
		Subject subject = hierarchy.getSubject();

		if (hierarchy.getActorType().equals(ENTITY.name())) {
			EntityDTO entityDTO = actor.getEntity();
		}
		if (hierarchy.getActorType().equals(TEMPLATE.name())) {
			// actor as template is pending
		}

		Set<String> childDomains = new HashSet<String>();

	}

	private HierarchyEntity constructHierarchyEntity(EntityDTO actor) {
		HierarchyEntity hierarchyEntity = new HierarchyEntity();
		hierarchyEntity.setDomain(actor.getDomain().getDomainName());
		hierarchyEntity.setEntityType(actor.getPlatformEntity()
		        .getPlatformEntityType());
		hierarchyEntity.setIdentifierKey(actor.getIdentifier().getKey());
		hierarchyEntity.setIdentifierValue(actor.getIdentifier().getValue());
		hierarchyEntity.setTemplateName(actor.getEntityTemplate()
		        .getEntityTemplateName());
		return hierarchyEntity;
	}

	private void valdateHierarchyParents(HierarchyDTO hierarchy) {
		String childDomain = hierarchy.getActor().getDomain().getDomainName();
		if (!hierarchyRepository.isTenantExist(childDomain)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        "Actor's Domain");
		}
		Set<String> parentDomains = new HashSet<String>();
		List<EntityDTO> tenants = new ArrayList<EntityDTO>();
		// construct hierarchy entity
		HierarchyEntity hierarchyEntity = constructHierarchyEntity(hierarchy
		        .getActor());
		List<HierarchyEntity> hierarchies;
		try {
			hierarchies = hierarchyRepository.getChildren(hierarchyEntity,
			        null, null);
		} catch (NoResultException e) {
			hierarchies = null;
		}
		// TODO check for performance when size of subject is large
		for (EntityDTO entity : hierarchy.getSubjects()) {
			// Removed validation after confirmation from BA
			// if (!hierarchyRepository.isNodeExist(entity)) {
			// throw new GalaxyException(
			// GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			// "Subject");
			// }
			if (CollectionUtils.isNotEmpty(hierarchies)) {
				HierarchyEntity resultEntity = constructHierarchyEntity(entity);
				if (hierarchies.contains(resultEntity)) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.CYCLIC_ATTACHMENT_NOT_ALLOWED);
				}
			}
			if (!TENANT.name().equals(
			        entity.getPlatformEntity().getPlatformEntityType())) {
				if (!entity.getDomain().getDomainName().equals(childDomain)) {
					parentDomains.add("'" + entity.getDomain().getDomainName()
					        + "'");
				}
			} else {
				tenants.add(entity);
			}
		}

		if (CollectionUtils.isNotEmpty(tenants)) {
			List<String> tenantDomains = hierarchyRepository
			        .getTenantsDomainNames(tenants);
			if (tenantDomains.size() != tenants.size()) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
			for (String domain : tenantDomains) {
				if (!domain.equals(childDomain)) {
					parentDomains.add("'" + domain + "'");
				}
			}
		}
		if (CollectionUtils.isNotEmpty(parentDomains)) {
			HashSet<String> childrenSet = hierarchyRepository
			        .getParentsOfEntity(childDomain, parentDomains);
			if (childrenSet.size() != parentDomains.size()) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.ENTITIES_FROM_WRONG_HIERARCHY);
			}
		}
	}

	private void validateTenant(HierarchyDTO hierarchyEntityDTO) {

		ValidationUtils.validateMandatoryFields(hierarchyEntityDTO);

		ValidationUtils.validateMandatoryFields(hierarchyEntityDTO, ACTOR,
		        TENANT);
		ValidationUtils.validateMandatoryFields(hierarchyEntityDTO.getActor(),
		        MY_DOMAIN);
		validateMandatoryFields(hierarchyEntityDTO.getActor().getMyDomain(),
		        DOMAIN_NAME);

		ValidationUtils.validateMandatoryFields(hierarchyEntityDTO.getTenant(),
		        MY_DOMAIN, IDENTIFIER, ENTITY_TEMPLATE);
		validateMandatoryFields(hierarchyEntityDTO.getTenant().getMyDomain(),
		        DOMAIN_NAME);

		ValidationUtils.validateMandatoryFields(hierarchyEntityDTO.getTenant()
		        .getIdentifier(), IDENTIFIER_KEY, IDENTIFIER_VALUE);

		ValidationUtils.validateMandatoryFields(hierarchyEntityDTO.getTenant()
		        .getEntityTemplate(), ENTITY_TEMPLATE_NAME);
	}

	private void setEntityTypeAndDomain(HierarchyDTO hierarchyDTO) {
		PlatformEntityDTO tenantType = new PlatformEntityDTO();
		tenantType.setPlatformEntityType(TENANT_TYPE.getVariableName());

		// The parent (actor) should be of type tenant
		hierarchyDTO.getActor().setPlatformEntity(tenantType);
		// The node being created is of type tenant
		hierarchyDTO.getTenant().setPlatformEntity(tenantType);
		hierarchyDTO.getTenant().setDomain(
		        hierarchyDTO.getActor().getMyDomain());

		if (hierarchyDTO.getTenant().getEntityStatus() == null
		        || isBlank(hierarchyDTO.getTenant().getEntityStatus()
		                .getStatusName())) {
			StatusDTO tenantStatus = new StatusDTO();
			tenantStatus.setStatusName(Status.ACTIVE.name());
			hierarchyDTO.getTenant().setEntityStatus(tenantStatus);
		}
	}

	@Override
	public List<EntityDTO> getChildren(HierarchySearchDTO hierarchySearch) {
		// validate mandatory fields
		validateIdentityFields(hierarchySearch.getParentIdentity());

		validateEntityType(hierarchySearch.getParentIdentity()
		        .getPlatformEntity(), hierarchySearch.getSearchEntityType());

		// validate myDomian
		if (hierarchySearch.getIsParentDomain() != null
		        && hierarchySearch.getIsParentDomain()) {
			validateMyDomain(hierarchySearch.getParentIdentity()
			        .getPlatformEntity().getPlatformEntityType(),
			        hierarchySearch.getParentIdentity().getIdentifier()
			                .getValue());
		}
		// validate status name is provided
		if (isNotBlank(hierarchySearch.getStatusName())) {
			statusService.getStatus(hierarchySearch.getStatusName());
		}

		// if domain not specified take logged in domain
		if (hierarchySearch.getParentIdentity().getDomain() == null
		        || isBlank(hierarchySearch.getParentIdentity().getDomain()
		                .getDomainName())) {
			DomainDTO domain = getDomain(hierarchySearch.getParentIdentity()
			        .getDomain());
			hierarchySearch.getParentIdentity().setDomain(domain);
		}
		// construct hierarchy entity
		HierarchyEntity hierarchyEntity = constructHierarchyEntity(hierarchySearch
		        .getParentIdentity());
		hierarchyEntity.setStatus(hierarchySearch.getStatusName());
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		try {
			hierarchies = hierarchyRepository.getChildren(hierarchyEntity,
			        hierarchySearch.getSearchTemplateName(),
			        hierarchySearch.getSearchEntityType());

		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		List<EntityDTO> entities = new ArrayList<EntityDTO>();

		for (HierarchyEntity hierarchyEntity2 : hierarchies) {
			EntityDTO entity = convertHierarchyToEntity(hierarchyEntity2);
			entities.add(entity);
		}
		return entities;
	}

	/**
	 * @param hierarchies
	 * @return
	 */
	// private List<EntityDTO> getEntities(List<HierarchyEntity> hierarchies) {
	// List<EntityDTO> entities = new ArrayList<EntityDTO>();
	//
	// for (HierarchyEntity childHierarchy : hierarchies) {
	// // Construct identityDTO to fetch details from entity
	// IdentityDTO identityDTO = new IdentityDTO();
	// DomainDTO domain = new DomainDTO();
	// domain.setDomainName(childHierarchy.getDomain());
	// identityDTO.setDomain(domain);
	//
	// EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
	// entityTemplate.setEntityTemplateName(childHierarchy
	// .getTemplateName());
	// identityDTO.setEntityTemplate(entityTemplate);
	// FieldMapDTO identifier = new FieldMapDTO();
	// identifier.setKey(childHierarchy.getIdentifierKey());
	// identifier.setValue(childHierarchy.getIdentifierValue());
	// identityDTO.setIdentifier(identifier);
	//
	// GlobalEntityDTO globalEntity = new GlobalEntityDTO();
	// globalEntity.setGlobalEntityType(childHierarchy.getEntityType());
	// identityDTO.setGlobalEntity(globalEntity);
	//
	// // fetch details from core entity
	// EntityDTO childEntity = coreEntityService
	// .getEntityDataProvider(identityDTO);
	// entities.add(childEntity);
	// }
	// return entities;
	// }

	private List<EntityTemplateDTO> getTemplates(
	        List<HierarchyEntityTemplate> hierarchies) {
		List<EntityTemplateDTO> templates = new ArrayList<EntityTemplateDTO>();

		for (HierarchyEntityTemplate childHierarchy : hierarchies) {

			EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(childHierarchy.getDomain());
			entityTemplate.setDomain(domain);

			entityTemplate.setPlatformEntityType(childHierarchy.getType());

			entityTemplate.setEntityTemplateName(childHierarchy.getName());
			templates.add(entityTemplate);
		}
		return templates;
	}

	@Override
	public HierarchyReturnDTO getChildEntityTypeCount(
	        HierarchySearchDTO hierarchySearch) {
		validateIdentityFields(hierarchySearch.getParentIdentity());

		// validate status name is provided
		if (isNotBlank(hierarchySearch.getStatusName())) {
			statusService.getStatus(hierarchySearch.getStatusName());
		}
		validateEntityType(hierarchySearch.getParentIdentity()
		        .getPlatformEntity(), hierarchySearch.getSearchEntityType());

		// validate myDomian
		if (hierarchySearch.getIsParentDomain() != null
		        && hierarchySearch.getIsParentDomain()) {
			validateMyDomain(hierarchySearch.getParentIdentity()
			        .getPlatformEntity().getPlatformEntityType(),
			        hierarchySearch.getParentIdentity().getIdentifier()
			                .getValue());
		}

		// if domain not specified take logged in domain
		if (hierarchySearch.getParentIdentity().getDomain() == null
		        || isBlank(hierarchySearch.getParentIdentity().getDomain()
		                .getDomainName())) {
			DomainDTO domain = getDomain(hierarchySearch.getParentIdentity()
			        .getDomain());
			hierarchySearch.getParentIdentity().setDomain(domain);
		}

		// construct hierarchy entity
		HierarchyEntity hierarchyEntity = constructHierarchyEntity(hierarchySearch
		        .getParentIdentity());
		hierarchyEntity.setStatus(hierarchySearch.getStatusName());
		Integer count = hierarchyRepository.getChildrenCount(hierarchyEntity,
		        hierarchySearch.getSearchTemplateName(),
		        hierarchySearch.getSearchEntityType());
		HierarchyReturnDTO hierarchyReturnDTO = new HierarchyReturnDTO();
		hierarchyReturnDTO.setCount(count);
		return hierarchyReturnDTO;
	}

	/**
	 * @param hierarchySearch
	 */
	private void validateEntityType(PlatformEntityDTO platformEntity,
	        String searchType) {
		// validate global entity
		PlatformEntityDTO PlatformEntityDTO = isGlobalEntityNameValid(platformEntity
		        .getPlatformEntityType());
		if (PlatformEntityDTO == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.toString());
		}
		if (isNotBlank(searchType)) {
			PlatformEntityDTO searchTypeDto = new PlatformEntityDTO();
			try {
				searchTypeDto = isGlobalEntityNameValid(searchType);
			} catch (GalaxyException galaxyException) {
				if (galaxyException
				        .getErrorMessageDTO()
				        .getErrorCode()
				        .equalsIgnoreCase(
				                GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED
				                        .getCode().toString())) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
					        SEARCH_ENTITY_TYPE.getDescription());
				}
			}
			if (searchTypeDto == null) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        SEARCH_ENTITY_TYPE.getDescription());
			}
		}
	}

	private void validateIdentityFields(IdentityDTO identityDTO) {
		ValidationUtils.validateMandatoryFields(identityDTO, IDENTIFIER,
		        PLATFORM_ENTITY);
		ValidationUtils.validateMandatoryFields(identityDTO.getIdentifier(),
		        IDENTIFIER_KEY, IDENTIFIER_VALUE);
		ValidationUtils.validateMandatoryFields(
		        identityDTO.getPlatformEntity(), PLATFORM_ENTITY_TYPE);

		// validate global entity
		PlatformEntityDTO PlatformEntityDTO = isGlobalEntityNameValid(identityDTO
		        .getPlatformEntity().getPlatformEntityType());
		if (PlatformEntityDTO == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.getDescription());
		}

		// validate template name
		String templateName = getTemplateForDefaultType(PlatformEntityDTO);
		if (isBlank(templateName)
		        && (identityDTO.getEntityTemplate() == null || isBlank(identityDTO
		                .getEntityTemplate().getEntityTemplateName()))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        ENTITY_TEMPLATE_NAME.getDescription());
		} else {
			if (isNotBlank(templateName)) {
				EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
				entityTemplateDTO.setEntityTemplateName(templateName);
				identityDTO.setEntityTemplate(entityTemplateDTO);
			}
		}

	}

	private HierarchyEntity constructHierarchyEntity(IdentityDTO identityDTO) {
		HierarchyEntity hierarchyEntity = new HierarchyEntity();
		hierarchyEntity.setDomain(identityDTO.getDomain().getDomainName());
		hierarchyEntity.setEntityType(identityDTO.getPlatformEntity()
		        .getPlatformEntityType());
		hierarchyEntity.setIdentifierKey(identityDTO.getIdentifier().getKey());
		hierarchyEntity.setIdentifierValue(identityDTO.getIdentifier()
		        .getValue());
		hierarchyEntity.setTemplateName(identityDTO.getEntityTemplate()
		        .getEntityTemplateName());
		return hierarchyEntity;
	}

	private DomainDTO getDomain(DomainDTO domain) {
		if (domain == null) {
			domain = new DomainDTO();
		}
		if (domain.getDomainName() == null || isBlank(domain.getDomainName())) {
			String domainName = subscriptionProfileService.getEndUserDomain();
			domain.setDomainName(domainName);
		}
		return domain;
	}

	@Override
	public HierarchyReturnDTO getCountByStatus(
	        HierarchySearchDTO hierarchySearch) {
		ValidationUtils.validateMandatoryFields(hierarchySearch,
		        SEARCH_TEMPLATE_NAME);
		ValidationUtils.validateMandatoryFields(hierarchySearch,
		        SEARCH_ENTITY_TYPE);
		ValidationUtils.validateMandatoryFields(hierarchySearch, STATUS_NAME);

		// if domain not specified take logged in domain
		if (isBlank(hierarchySearch.getDomain())) {
			hierarchySearch.setDomain(subscriptionProfileService
			        .getEndUserDomain());
		}
		// Validate myDomain
		if (hierarchySearch.getIsParentDomain() != null
		        && hierarchySearch.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_TENANT);
		}
		Integer count = hierarchyRepository.getCountByStatus(
		        hierarchySearch.getDomain(),
		        hierarchySearch.getSearchTemplateName(),
		        hierarchySearch.getStatusName(),
		        hierarchySearch.getSearchEntityType());
		HierarchyReturnDTO hierarchyReturnDTO = new HierarchyReturnDTO();
		hierarchyReturnDTO.setCount(count);
		return hierarchyReturnDTO;
	}

	@Override
	public EntityDTO updateNode(EntityDTO entity) {
		ValidationUtils.validateMandatoryFields(entity, ENTITY_STATUS);
		validateUpdateEntityDto(entity);

		// validate myDomian
		if (entity.getIsParentDomain() != null && entity.getIsParentDomain()) {
			validateMyDomain(
			        entity.getPlatformEntity().getPlatformEntityType(), entity
			                .getIdentifier().getValue());
		}
		// Construct IdentityDTO
		IdentityDTO nodeIdentity = new IdentityDTO();
		nodeIdentity.setDomain(entity.getDomain());
		nodeIdentity.setPlatformEntity(entity.getPlatformEntity());
		nodeIdentity.setIdentifier(entity.getIdentifier());
		nodeIdentity.setEntityTemplate(entity.getEntityTemplate());

		// Fetch the entity dataprovider
		EntityDTO entityToUpdate = coreEntityService
		        .getEntityDataProvider(nodeIdentity);
		return hierarchyRepository.updateNode(entity,
		        getDataProvider(entityToUpdate.getDataprovider()),
		        getTree(entityToUpdate.getTree()));
	}

	private String getDataProvider(List<FieldMapDTO> dataprovider) {

		if (CollectionUtils.isEmpty(dataprovider)) {
			return new String();
		}
		Gson gson = new Gson();
		String dataproviderString = gson.toJson(dataprovider);
		try {
			dataproviderString = URLEncoder.encode(dataproviderString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Error while encoding dataprovider", e);
		}

		return dataproviderString;
	}

	private String getTree(FieldMapDTO tree) {

		if (tree == null) {
			return new String();
		}
		Gson gson = new Gson();
		String treeString = gson.toJson(tree);
		try {
			treeString = URLEncoder.encode(treeString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Error while encoding dataprovider", e);
		}

		return treeString;
	}

	@Override
	public List<HierarchyReturnDTO> getEntityDistributionCount(
	        HierarchySearchDTO hierarchySearchDTO) {
		// validate the input
		validateDistribution(hierarchySearchDTO);

		// if domain not specified fetch domain as the logged in user's domain
		hierarchySearchDTO.getParentIdentity().setDomain(
		        getDomain(hierarchySearchDTO.getParentIdentity().getDomain()));

		// validate the myDomain
		if (hierarchySearchDTO.getIsParentDomain() != null
		        && hierarchySearchDTO.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}

		// Fetch from the db
		List<HierarchyReturnDTO> distributionList = new ArrayList<HierarchyReturnDTO>();
		List<DistributionEntity> distributionEntities = new ArrayList<DistributionEntity>();
		try {
			distributionEntities = hierarchyRepository
			        .getEntityDistributionCount(hierarchySearchDTO
			                .getParentIdentity().getDomain().getDomainName(),
			                hierarchySearchDTO.getParentIdentity()
			                        .getPlatformEntity()
			                        .getPlatformEntityType(),
			                hierarchySearchDTO.getSearchTemplateName(),
			                hierarchySearchDTO.getSearchEntityType(),
			                hierarchySearchDTO.getStatusName());
		} catch (GalaxyException galaxyException) {
			if (galaxyException.getErrorMessageDTO().getErrorCode()
			        .equals("500")) {
				Integer count = 0;
				HierarchyReturnDTO emptyResult = new HierarchyReturnDTO();
				emptyResult.setCount(count);
				distributionList.add(emptyResult);
			}
		}

		// Construct the DTO
		for (DistributionEntity distributionEntity : distributionEntities) {
			HierarchyReturnDTO hierarchyReturnDTO = new HierarchyReturnDTO();
			hierarchyReturnDTO.setCount(distributionEntity.getCount());
			hierarchyReturnDTO.setIdentifierValue(distributionEntity
			        .getIdentifierValue());
			distributionList.add(hierarchyReturnDTO);
		}
		return distributionList;
	}

	private void validateDistribution(HierarchySearchDTO hierarchyEntityDTO) {
		// validate global entity

		validateMandatoryFields(hierarchyEntityDTO.getParentIdentity(),
		        PLATFORM_ENTITY);

		validateMandatoryFields(hierarchyEntityDTO.getParentIdentity()
		        .getPlatformEntity(), PLATFORM_ENTITY_TYPE);

		validateMandatoryFields(hierarchyEntityDTO, SEARCH_TEMPLATE_NAME);

		validateMandatoryFields(hierarchyEntityDTO, SEARCH_ENTITY_TYPE);

		// validate global entity
		PlatformEntityDTO PlatformEntityDTO = isGlobalEntityNameValid(hierarchyEntityDTO
		        .getParentIdentity().getPlatformEntity()
		        .getPlatformEntityType());
		if (PlatformEntityDTO == null) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        PLATFORM_ENTITY_TYPE.toString());
		}
		// validate template name
		String templateName = getTemplateForDefaultType(PlatformEntityDTO);
		if (isBlank(templateName)
		        && (hierarchyEntityDTO.getParentIdentity().getEntityTemplate() == null || isBlank(hierarchyEntityDTO
		                .getParentIdentity().getEntityTemplate()
		                .getEntityTemplateName()))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        ENTITY_TEMPLATE_NAME.toString());
		} else {
			EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
			entityTemplateDTO.setEntityTemplateName(templateName);
			hierarchyEntityDTO.getParentIdentity().setEntityTemplate(
			        entityTemplateDTO);
		}

		// validate status name is provided
		if (isNotBlank(hierarchyEntityDTO.getStatusName())) {
			statusService.getStatus(hierarchyEntityDTO.getStatusName());
		}

	}

	/**
	 * @Description Method for Validating GlobalEntityType
	 * @param globalEntityName
	 * @return boolean
	 */
	private PlatformEntityDTO isGlobalEntityNameValid(String globalEntityName) {

		PlatformEntityDTO globalEntity = platformEntityService
		        .getGlobalEntityWithName(globalEntityName);

		return globalEntity;
	}

	@Override
	public List<EntityDTO> getEntityDistribution(
	        HierarchySearchDTO hierarchySearchDTO) {
		// validate the input
		validateDistribution(hierarchySearchDTO);

		// if domain not specified fetch domain as the logged in user's domain
		hierarchySearchDTO.getParentIdentity().setDomain(
		        getDomain(hierarchySearchDTO.getParentIdentity().getDomain()));

		// validate the myDomain
		if (hierarchySearchDTO.getIsParentDomain() != null
		        && hierarchySearchDTO.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
		// Fetch from db
		List<HierarchyEntity> hierarchies = hierarchyRepository
		        .getEntityDistribution(hierarchySearchDTO.getParentIdentity()
		                .getDomain().getDomainName(), hierarchySearchDTO
		                .getParentIdentity().getPlatformEntity()
		                .getPlatformEntityType(),
		                hierarchySearchDTO.getSearchTemplateName(),
		                hierarchySearchDTO.getSearchEntityType(),
		                hierarchySearchDTO.getStatusName());
		// No data available
		if (CollectionUtils.isEmpty(hierarchies)) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		for (HierarchyEntity hierarchyEntity : hierarchies) {
			EntityDTO entity = convertHierarchyToEntity(hierarchyEntity);
			entities.add(entity);
		}
		return entities;
	}

	private String getTemplateForDefaultType(PlatformEntityDTO PlatformEntityDTO) {
		PlatformEntityTemplateDTO globalEntityTemplateDTO = new PlatformEntityTemplateDTO();
		String templateName = null;
		if (PlatformEntityDTO.getIsDefault()) {
			globalEntityTemplateDTO = platformEntityTemplateService
			        .getPlatformEntityTemplate(PlatformEntityDTO
			                .getPlatformEntityType());
			templateName = globalEntityTemplateDTO
			        .getPlatformEntityTemplateName();
		}
		return templateName;
	}

	@Override
	public List<EntityDTO> getImmediateChildren(
	        HierarchySearchDTO hierarchySearch) {
		// validate mandatory fields
		validateIdentityFields(hierarchySearch.getActor());

		validateEntityType(hierarchySearch.getActor().getPlatformEntity(),
		        hierarchySearch.getSearchEntityType());

		// validate status name is provided
		if (isNotBlank(hierarchySearch.getStatusName())) {
			statusService.getStatus(hierarchySearch.getStatusName());
		}

		// if domain not specified take logged in domain
		if (hierarchySearch.getActor().getDomain() == null
		        || isBlank(hierarchySearch.getActor().getDomain()
		                .getDomainName())) {
			DomainDTO domain = getDomain(null);
			hierarchySearch.getActor().setDomain(domain);
		}

		// validate myDomian
		if (hierarchySearch.getIsParentDomain() != null
		        && hierarchySearch.getIsParentDomain()) {
			validateMyDomain(hierarchySearch.getActor().getPlatformEntity()
			        .getPlatformEntityType(), hierarchySearch.getActor()
			        .getIdentifier().getValue());
		}

		// construct hierarchy entity
		HierarchyEntity hierarchyEntity = constructHierarchyEntity(hierarchySearch
		        .getActor());
		hierarchyEntity.setStatus(hierarchySearch.getStatusName());
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		try {
			hierarchies = hierarchyRepository.getImmediateChildren(
			        hierarchyEntity, hierarchySearch.getSearchTemplateName(),
			        hierarchySearch.getSearchEntityType());

		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		for (HierarchyEntity hierarchyEntity2 : hierarchies) {
			EntityDTO entityDTO = convertHierarchyToEntity(hierarchyEntity2);
			entities.add(entityDTO);
		}
		return entities;
	}

	@Override
	public List<EntityDTO> getImmediateParent(HierarchySearchDTO hierarchySearch) {
		// validate mandatory fields
		validateIdentityFields(hierarchySearch.getActor());

		validateEntityType(hierarchySearch.getActor().getPlatformEntity(),
		        hierarchySearch.getSearchEntityType());

		// validate status name is provided
		if (isNotBlank(hierarchySearch.getStatusName())) {
			statusService.getStatus(hierarchySearch.getStatusName());
		}

		// if domain not specified take logged in domain
		if (hierarchySearch.getActor().getDomain() == null
		        || isBlank(hierarchySearch.getActor().getDomain()
		                .getDomainName())) {
			DomainDTO domain = getDomain(null);
			hierarchySearch.getActor().setDomain(domain);
		}

		// validate myDomian
		if (hierarchySearch.getIsParentDomain() != null
		        && hierarchySearch.getIsParentDomain()) {
			validateMyDomain(hierarchySearch.getActor().getPlatformEntity()
			        .getPlatformEntityType(), hierarchySearch.getActor()
			        .getIdentifier().getValue());
		}

		// construct hierarchy entity
		HierarchyEntity hierarchyEntity = constructHierarchyEntity(hierarchySearch
		        .getActor());
		hierarchyEntity.setStatus(hierarchySearch.getStatusName());
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		try {
			hierarchies = hierarchyRepository.getImmediateParents(
			        hierarchyEntity, hierarchySearch.getSearchTemplateName(),
			        hierarchySearch.getSearchEntityType());

		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		for (HierarchyEntity hierarchyEntity2 : hierarchies) {
			EntityDTO entityDTO = convertHierarchyToEntity(hierarchyEntity2);
			entities.add(entityDTO);
		}
		return entities;
	}

	@Override
	public DomainAccessDTO isDomainAccessible(String domainName) {
		// Validate domain name
		DomainAccessDTO domainAccess = new DomainAccessDTO();
		Boolean boolStatus = false;

		if (isNotBlank(domainName)) {
			// get logged in user's domain
			String loggedInuserDomain = subscriptionProfileService
			        .getEndUserDomain();

			if (domainName.equalsIgnoreCase(loggedInuserDomain)) {
				domainAccess.setIsParentDomain(boolStatus);
				return domainAccess;
			}
			// domainName should be a TENANT below the loggedInuserDomain
			Boolean status = false;
			try {
				status = hierarchyRepository.isChildDomain(loggedInuserDomain,
				        domainName);
			} catch (Exception e) {
				logger.debug("Error while checking is child domain: " + e);
			}

			// Check if domain is parent
			List<String> tenants = new ArrayList<String>();
			if (!status) {
				tenants = hierarchyRepository.getHierarchyByTenantDomain(
				        domainName, loggedInuserDomain);
			}
			// Status is false and it is not parent domain
			if (!status && CollectionUtils.isEmpty(tenants)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
			} else {
				// Set domain accessible
				if (CollectionUtils.isNotEmpty(tenants)) {
					boolStatus = true;
				}
				domainAccess.setIsParentDomain(boolStatus);
			}
		}
		return domainAccess;
	}

	@Override
	public List<EntityDTO> getAssignableMarkers(EntityAssignDTO entityAssignDTO) {
		// validate mandatory fields
		validateIdentityFields(entityAssignDTO.getActor());
		validateMandatoryField(MARKER_TEMPLATE_NAME,
		        entityAssignDTO.getMarkerTemplateName());
		validateMandatoryField(SEARCH_TEMPLATE_NAME,
		        entityAssignDTO.getSearchTemplateName());

		validateEntityType(entityAssignDTO.getActor().getPlatformEntity(), null);

		// validate status name is provided
		if (isNotBlank(entityAssignDTO.getStatusName())) {
			statusService.getStatus(entityAssignDTO.getStatusName());
		}

		// if domain not specified take logged in domain
		if (entityAssignDTO.getActor().getDomain() == null
		        || isBlank(entityAssignDTO.getActor().getDomain()
		                .getDomainName())) {
			DomainDTO domain = getDomain(null);
			entityAssignDTO.getActor().setDomain(domain);
		}
		// validate myDomian
		if (entityAssignDTO.getIsParentDomain() != null
		        && entityAssignDTO.getIsParentDomain()) {
			validateMyDomain(entityAssignDTO.getActor().getPlatformEntity()
			        .getPlatformEntityType(), entityAssignDTO.getActor()
			        .getIdentifier().getValue());
		}
		// construct hierarchy entity
		HierarchyEntity hierarchyEntity = constructHierarchyEntity(entityAssignDTO
		        .getActor());
		hierarchyEntity.setStatus(entityAssignDTO.getStatusName());
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		try {
			hierarchies = hierarchyRepository.getAssignableMarkers(
			        hierarchyEntity, entityAssignDTO.getSearchTemplateName(),
			        entityAssignDTO.getMarkerTemplateName());

		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		for (HierarchyEntity hierarchyEntity2 : hierarchies) {
			EntityDTO entityDTO = convertHierarchyToEntity(hierarchyEntity2);
			entities.add(entityDTO);
		}
		return entities;
	}

	@Override
	public List<EntityDTO> getAllOwnedMarkersByDomain(
	        EntityAssignDTO entityAssignDTO) {
		// validate mandatory fields
		validateIdentityFields(entityAssignDTO.getActor());
		validateMandatoryField(MARKER_TEMPLATE_NAME,
		        entityAssignDTO.getMarkerTemplateName());

		validateEntityType(entityAssignDTO.getActor().getPlatformEntity(), null);

		// validate status name is provided
		if (isNotBlank(entityAssignDTO.getStatusName())) {
			statusService.getStatus(entityAssignDTO.getStatusName());
		}

		// if domain not specified take logged in domain
		if (entityAssignDTO.getActor().getDomain() == null
		        || isBlank(entityAssignDTO.getActor().getDomain()
		                .getDomainName())) {
			DomainDTO domain = getDomain(null);
			entityAssignDTO.getActor().setDomain(domain);
		}
		// validate myDomian
		if (entityAssignDTO.getIsParentDomain() != null
		        && entityAssignDTO.getIsParentDomain()) {
			validateMyDomain(entityAssignDTO.getActor().getPlatformEntity()
			        .getPlatformEntityType(), entityAssignDTO.getActor()
			        .getIdentifier().getValue());
		}

		// construct hierarchy entity
		HierarchyEntity hierarchyEntity = constructHierarchyEntity(entityAssignDTO
		        .getActor());
		hierarchyEntity.setStatus(entityAssignDTO.getStatusName());
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		try {
			hierarchies = hierarchyRepository.getAllOwnedMarkersByDomain(
			        hierarchyEntity, entityAssignDTO.getSearchTemplateName(),
			        entityAssignDTO.getMarkerTemplateName());

		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		for (HierarchyEntity hierarchyEntity2 : hierarchies) {
			EntityDTO entityDTO = convertHierarchyToEntity(hierarchyEntity2);
			entities.add(entityDTO);
		}
		return entities;
	}

	private void validateUpdateEntityDto(EntityDTO entity) {

		validateIdentifiers(entity);

		if (entity.getEntityStatus() != null) {
			ValidationUtils.validateMandatoryField(EMDataFields.STATUS_NAME,
			        entity.getEntityStatus().getStatusName());
			if (!(entity.getEntityStatus().getStatusName()
			        .equalsIgnoreCase(Status.ACTIVE.name()))
			        && !(entity.getEntityStatus().getStatusName()
			                .equalsIgnoreCase(Status.INACTIVE.name()))
			        && !(entity.getEntityStatus().getStatusName()
			                .equalsIgnoreCase(Status.ALLOCATED.name()))
			        && !(entity.getEntityStatus().getStatusName()
			                .equalsIgnoreCase(Status.DELETED.name()))) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        CommonConstants.STATUS_NAME);
			}
		} else {
			StatusDTO entityStatus = new StatusDTO();
			entityStatus.setStatusName(Status.ACTIVE.name());
			entity.setEntityStatus(entityStatus);
		}
	}

	/**
	 * @param entity
	 */
	private void validateIdentifiers(EntityDTO entity) {
		ValidationUtils.validateMandatoryFields(entity, ENTITY_TEMPLATE,
		        IDENTIFIER, DOMAIN, PLATFORM_ENTITY);

		ValidationUtils.validateMandatoryFields(entity.getIdentifier(),
		        IDENTIFIER_KEY, IDENTIFIER_VALUE);
		ValidationUtils
		        .validateMandatoryFields(entity.getDomain(), DOMAIN_NAME);
		ValidationUtils.validateMandatoryFields(entity.getEntityTemplate(),
		        ENTITY_TEMPLATE_NAME);
		ValidationUtils.validateMandatoryFields(entity.getPlatformEntity(),
		        PLATFORM_ENTITY_TYPE);
	}

	// TODO change the identifierKey to clientName
	private void validateMyDomain(String globalEntityType, String tenantName) {
		if (globalEntityType.equalsIgnoreCase(TENANT.getFieldName())) {
			// Get logged in user's tenant
			String domainName = subscriptionProfileService.getEndUserDomain();

			EntityDTO tenant = coreEntityService.getDomainEntity(domainName);

			// Extract the tenant name
			FieldMapDTO tenantNameMap = new FieldMapDTO();
			tenantNameMap.setKey(EMDataFields.TENANT_ID.getVariableName());
			tenantNameMap.setValue(tenant.getFieldValues()
			        .get(tenant.getFieldValues().indexOf(tenantNameMap))
			        .getValue());

			// Validate that both the tenant names are the same
			if (!tenantName.equalsIgnoreCase(tenantNameMap.getValue())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_TENANT);
			}
		} else {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}
	}

	@Override
	public List<IdentityDTO> getTenantsWithinRange(
	        EntityRangeDTO hierarchySearch) {
		ValidationUtils.validateMandatoryFields(hierarchySearch, END_ENTITY);
		Boolean validateDomain = true;
		validateEntities(hierarchySearch, validateDomain);
		String domainName = subscriptionProfileService.getEndUserDomain();
		HierarchyEntity entity = hierarchyRepository.getTenantNode(domainName);
		HierarchyEntity startEntity = new HierarchyEntity();

		// if start node domain not specified take logged in domain
		if (hierarchySearch.getStartEntity() == null) {
			startEntity = entity;
		} else {
			startEntity = constructHierarchyEntity(hierarchySearch
			        .getStartEntity());
			List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
			try {
				hierarchies = hierarchyRepository.getChildren(entity, null,
				        null);
			} catch (NoResultException e) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
			hierarchies.add(entity);
			if (CollectionUtils.isNotEmpty(hierarchies)) {
				if (!hierarchies.contains(startEntity)) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.ENTITIES_FROM_WRONG_HIERARCHY);
				}
			}

		}
		HierarchyEntity endEntity = constructHierarchyEntity(hierarchySearch
		        .getEndEntity());
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		try {
			hierarchies = hierarchyRepository.getTenantsWithinRange(
			        startEntity, endEntity);

		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		List<IdentityDTO> entities = getTenantEntities(hierarchies);

		return entities;
	}

	private void validateEntities(EntityRangeDTO hierarchySearch,
	        Boolean validateDomain) {
		if (hierarchySearch.getStartEntity() != null) {
			// validate Start Node
			validateStartEntity(hierarchySearch.getStartEntity(),
			        validateDomain);
		}
		// validate End Node
		validateEndEntity(hierarchySearch.getEndEntity(), validateDomain);
	}

	private void validateStartEntity(IdentityDTO identityDTO,
	        Boolean validateDomain) {
		ValidationUtils.validateMandatoryFields(identityDTO,
		        START_ENTITY_IDENTIFIER, START_ENTITY_PLATFORM_ENTITY,
		        START_ENTITY_DOMAIN);
		ValidationUtils.validateMandatoryFields(identityDTO.getIdentifier(),
		        START_ENTITY_IDENTIFIER_KEY, START_ENTITY_IDENTIFIER_VALUE);
		ValidationUtils.validateMandatoryFields(
		        identityDTO.getPlatformEntity(),
		        START_ENTITY_PLATFORM_ENTITY_TYPE);
		if (validateDomain) {
			validateMandatoryField(START_ENTITY_DOMAIN_NAME, identityDTO
			        .getDomain().getDomainName());
		}

		// validate global entity
		PlatformEntityDTO PlatformEntityDTO = new PlatformEntityDTO();
		try {
			PlatformEntityDTO = isGlobalEntityNameValid(identityDTO
			        .getPlatformEntity().getPlatformEntityType());
			if (PlatformEntityDTO == null) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        START_ENTITY_PLATFORM_ENTITY_TYPE.getDescription());
			}
		} catch (Exception e) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        START_ENTITY_PLATFORM_ENTITY_TYPE.getDescription());
		}

		// validate template name
		String templateName = getTemplateForDefaultType(PlatformEntityDTO);
		if (isBlank(templateName)
		        && (identityDTO.getEntityTemplate() == null || isBlank(identityDTO
		                .getEntityTemplate().getEntityTemplateName()))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        START_ENTITY_TEMPLATE_NAME.getDescription());
		} else {
			if (isNotBlank(templateName)) {
				EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
				entityTemplateDTO.setEntityTemplateName(templateName);
				identityDTO.setEntityTemplate(entityTemplateDTO);
			}
		}

	}

	private void validateEndEntity(IdentityDTO identityDTO,
	        Boolean validateDomain) {
		ValidationUtils.validateMandatoryFields(identityDTO,
		        END_ENTITY_IDENTIFIER, END_ENTITY_PLATFORM_ENTITY,
		        END_ENTITY_DOMAIN);
		ValidationUtils.validateMandatoryFields(identityDTO.getIdentifier(),
		        END_ENTITY_IDENTIFIER_KEY, END_ENTITY_IDENTIFIER_VALUE);
		ValidationUtils.validateMandatoryFields(
		        identityDTO.getPlatformEntity(),
		        END_ENTITY_PLATFORM_ENTITY_TYPE);
		if (validateDomain) {
			validateMandatoryField(END_ENTITY_DOMAIN_NAME, identityDTO
			        .getDomain().getDomainName());
		}

		// validate global entity
		PlatformEntityDTO PlatformEntityDTO = new PlatformEntityDTO();
		try {
			PlatformEntityDTO = isGlobalEntityNameValid(identityDTO
			        .getPlatformEntity().getPlatformEntityType());
			if (PlatformEntityDTO == null) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        END_ENTITY_PLATFORM_ENTITY_TYPE.getDescription());
			}
		} catch (Exception e) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        END_ENTITY_PLATFORM_ENTITY_TYPE.getDescription());
		}

		// validate template name
		String templateName = getTemplateForDefaultType(PlatformEntityDTO);
		if (isBlank(templateName)
		        && (identityDTO.getEntityTemplate() == null || isBlank(identityDTO
		                .getEntityTemplate().getEntityTemplateName()))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        END_ENTITY_TEMPLATE_NAME.getDescription());
		} else {
			if (isNotBlank(templateName)) {
				EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
				entityTemplateDTO.setEntityTemplateName(templateName);
				identityDTO.setEntityTemplate(entityTemplateDTO);
			}
		}

	}

	/**
	 * @param hierarchies
	 * @return
	 */
	private List<IdentityDTO> getTenantEntities(
	        List<HierarchyEntity> hierarchies) {
		List<IdentityDTO> entities = new ArrayList<IdentityDTO>();

		for (HierarchyEntity childHierarchy : hierarchies) {
			// Construct identityDTO to fetch details from entity
			IdentityDTO identityDTO = new IdentityDTO();
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(childHierarchy.getDomain());
			identityDTO.setDomain(domain);

			EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
			entityTemplate.setEntityTemplateName(childHierarchy
			        .getTemplateName());
			identityDTO.setEntityTemplate(entityTemplate);
			FieldMapDTO identifier = new FieldMapDTO();
			identifier.setKey(childHierarchy.getIdentifierKey());
			identifier.setValue(childHierarchy.getIdentifierValue());
			identityDTO.setIdentifier(identifier);

			PlatformEntityDTO globalEntity = new PlatformEntityDTO();
			globalEntity.setPlatformEntityType(TENANT.name());
			identityDTO.setPlatformEntity(globalEntity);

			entities.add(identityDTO);
		}
		return entities;
	}

	@Override
	public StatusMessageDTO attach(AttachHierarchyDTO attachHierarchy) {

		// Validate isParentDomainSet
		if (attachHierarchy.getIsParentDomain() != null
		        && attachHierarchy.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_TENANT);
		}

		// validate actorType and subjectType
		validateTypes(attachHierarchy);
		validateAttachHierarchyPayload(attachHierarchy);
		if (attachHierarchy.getActorType().equals(ENTITY.name())
		        && attachHierarchy.getSubjectType().equals(ENTITY.name())) {
			valdateAttachHierarchyChild(attachHierarchy); // TODO check cyclic
			// exception
		}

		// set relation attachedTo among actor and subjects
		attachHierarchy.setRelationName(HMDataFields.RELATION_ATTACHEDTO
		        .getFieldName());
		hierarchyRepository.attach(attachHierarchy);
		StatusMessageDTO status = new StatusMessageDTO(Status.SUCCESS);
		return status;
	}

	@Override
	public StatusMessageDTO attachActorsToASubject(
	        AttachHierarchyDTO attachHierarchy) {

		// validate actorType and subjectType
		validateTypes(attachHierarchy);
		validateAttachActorsToASubjectPayload(attachHierarchy);

		// set relation attachedTo among actor and subjects
		attachHierarchy.setRelationName(HMDataFields.RELATION_ATTACHEDTO
		        .getFieldName());
		hierarchyRepository.attachActorsToASubject(attachHierarchy);
		StatusMessageDTO status = new StatusMessageDTO(Status.SUCCESS);
		return status;
	}

	private void validateAttachActorsToASubjectPayload(
	        AttachHierarchyDTO hierarchy) {
		ValidationUtils.validateMandatoryFields(hierarchy, ACTOR, SUBJECT);

		Actor actor = hierarchy.getActor();
		Subject subject = hierarchy.getSubject();

		HierarchyNodeTypes hntActorType = HierarchyNodeTypes.getEnum(hierarchy
		        .getActorType());
		switch (hntActorType) {
			case ENTITY :
				List<EntityDTO> actorentities = new ArrayList<EntityDTO>();
				// validate actor entities
				for (EntityDTO actorEntity : actor.getEntities()) {
					validateEntityDto(actorEntity);
					EntityDTO entityDataProvider = getEntityDataProvider(actorEntity);
					actorEntity.setDataprovider(entityDataProvider
					        .getDataprovider());
					actorEntity.setTree(entityDataProvider.getTree());
					actorentities.add(actorEntity);
				}
				actor.setEntities(null);
				actor.setEntities(actorentities);
				break;
			// case TEMPLATE :
			// break;
			default:
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        ACTOR.getDescription());
		}

		HierarchyNodeTypes hntSubjectType = HierarchyNodeTypes
		        .getEnum(hierarchy.getSubjectType());
		switch (hntSubjectType) {
			case ENTITY :
				// validate all subject entities
				EntityDTO subjectEntity = subject.getEntity();
				validateEntityDto(subjectEntity);
				EntityDTO entityDataProvider = getEntityDataProvider(subjectEntity);
				subjectEntity.setDataprovider(entityDataProvider
				        .getDataprovider());
				subjectEntity.setTree(entityDataProvider.getTree());
				break;
			case TEMPLATE :
				EntityTemplateDTO template = subject.getTemplate();
				validateTemplate(template);
				break;
			default:
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        SUBJECT.getDescription());
		}

	}

	private void validateAttachHierarchyPayload(AttachHierarchyDTO hierarchy) {
		ValidationUtils.validateMandatoryFields(hierarchy, ACTOR, SUBJECT);

		Actor actor = hierarchy.getActor();
		validateActor(actor, hierarchy.getActorType());

		Subject subject = hierarchy.getSubject();
		validateSubject(subject, hierarchy.getSubjectType());
	}

	private void validateActor(Actor actor, String actorType) {
		HierarchyNodeTypes hntActorType = HierarchyNodeTypes.getEnum(actorType);
		switch (hntActorType) {
			case ENTITY :
				// validate actor entity
				EntityDTO actorEntity = actor.getEntity();
				EntityDTO entityDataProvider = getEntityDataProvider(actorEntity);
				actorEntity.setDataprovider(entityDataProvider
				        .getDataprovider());
				actorEntity.setTree(entityDataProvider.getTree());
				List<EntityDTO> actorentities = new ArrayList<EntityDTO>();
				actorentities.add(actorEntity);
				validateEntities(actorentities);

				break;
			// case TEMPLATE :
			// break;
			default:
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        ACTOR.getDescription());
		}
	}

	private void validateSubject(Subject subject, String subjectType) {
		HierarchyNodeTypes hntSubjectType = HierarchyNodeTypes
		        .getEnum(subjectType);
		switch (hntSubjectType) {
			case ENTITY :
				// validate all subject entities
				List<EntityDTO> entities = subject.getEntities();
				validateEntities(entities);
				for (EntityDTO entityDTO : entities) {
					EntityDTO entityDataProvider = getEntityDataProvider(entityDTO);
					entityDTO.setDataprovider(entityDataProvider
					        .getDataprovider());
					entityDTO.setTree(entityDataProvider.getTree());
				}
				break;
			case TEMPLATE :
				List<EntityTemplateDTO> templates = subject.getTemplates();
				// validate all subject templates
				validateTemplates(templates);
				break;
			default:
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        SUBJECT.getDescription());
		}
	}

	private void validateTemplates(List<EntityTemplateDTO> templatesSubjects) {

		// each subject should represent template
		for (EntityTemplateDTO templateSubject : templatesSubjects) {
			validateTemplate(templateSubject);
		}
	}

	private void validateTemplate(EntityTemplateDTO template) {
		ValidationUtils.validateMandatoryFields(template, ENTITY_TEMPLATE_NAME,
		        DOMAIN, PLATFORM_ENTITY_TYPE);
		if (template.getStatus() != null) {
			ValidationUtils.validateMandatoryField(EMDataFields.STATUS_NAME,
			        template.getStatus().getStatusName());
			if (!(template.getStatus().getStatusName()
			        .equalsIgnoreCase(Status.ACTIVE.name()))
			        && !(template.getStatus().getStatusName()
			                .equalsIgnoreCase(Status.INACTIVE.name()))
			        && !(template.getStatus().getStatusName()
			                .equalsIgnoreCase(Status.ALLOCATED.name()))) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        CommonConstants.STATUS_NAME);
			}
		} else {
			StatusDTO templateStatus = new StatusDTO();
			templateStatus.setStatusName(Status.ACTIVE.name());
			template.setStatus(templateStatus);
		}
	}

	private void validateTypes(AttachHierarchyDTO hierarchy) {
		ValidationUtils.validateMandatoryFields(hierarchy, ACTOR_TYPE,
		        SUBJECT_TYPE);
		validateType(hierarchy.getActorType(), hierarchy.getSubjectType());

	}

	private void validateType(String actorType, String subjectType) {
		try {
			HierarchyNodeTypes.getEnum(actorType);
		} catch (IllegalArgumentException iae) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        ACTOR_TYPE.getDescription());
		}
		try {
			HierarchyNodeTypes.getEnum(subjectType);
		} catch (IllegalArgumentException iae) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        SUBJECT_TYPE.getDescription());
		}
	}

	@Override
	public List<EntityDTO> getAllEntitySubjects(
	        AttachHierarchySearchDTO attachHierarchySearch) {

		// Validate isParentDomainSet
		if (attachHierarchySearch.getIsParentDomain() != null
		        && attachHierarchySearch.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_TENANT);
		}

		validateMandatoryFields(attachHierarchySearch, ACTOR_TYPE);
		if (CollectionUtils.isNotEmpty(attachHierarchySearch
		        .getSearchTemplates())) {
			validateTemplates(attachHierarchySearch.getSearchTemplates());
		}
		String actorType = attachHierarchySearch.getActorType();

		try {
			HierarchyNodeTypes.getEnum(actorType);
		} catch (IllegalArgumentException iae) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        SUBJECT_TYPE.getDescription());
		}

		// validate actor
		HierarchyNodeTypes hntActorType = HierarchyNodeTypes.getEnum(actorType);
		switch (hntActorType) {
			case ENTITY :
				// validate actor entity
				List<EntityDTO> actorentities = new ArrayList<EntityDTO>();
				EntityDTO actorEntity = attachHierarchySearch.getActor()
				        .getEntity();
				actorentities.add(actorEntity);
				validateEntities(actorentities);
				// validate myDomian
				if (attachHierarchySearch.getIsParentDomain() != null
				        && attachHierarchySearch.getIsParentDomain()) {
					validateMyDomain(actorEntity.getPlatformEntity()
					        .getPlatformEntityType(), actorEntity
					        .getIdentifier().getValue());
				}
				break;
			case TEMPLATE :
				List<EntityTemplateDTO> templates = new ArrayList<EntityTemplateDTO>();
				EntityTemplateDTO actorisTemplate = attachHierarchySearch
				        .getActor().getTemplate();
				templates.add(actorisTemplate);
				validateTemplates(templates);
				break;
			default:
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        ACTOR.getDescription());
		}

		// fetch all entity subjects attachedTo actor
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		attachHierarchySearch.setRelationName(RELATION_ATTACHEDTO
		        .getFieldName());
		try {
			hierarchies = hierarchyRepository
			        .getAllEntitySubjects(attachHierarchySearch);

		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		for (HierarchyEntity hierarchyEntity : hierarchies) {
			EntityDTO entity = convertHierarchyToEntity(hierarchyEntity);
			entities.add(entity);
		}
		return entities;
	}

	@Override
	public List<EntityDTO> getImmediateRoot(
	        AttachHierarchySearchDTO attachHierarchySearch) {

		// Validate isParentDomainSet
		if (attachHierarchySearch.getIsParentDomain() != null
		        && attachHierarchySearch.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_TENANT);
		}

		validateMandatoryFields(attachHierarchySearch, ACTOR_TYPE, ACTOR);

		String actorType = attachHierarchySearch.getActorType();
		try {
			HierarchyNodeTypes.getEnum(actorType);
		} catch (IllegalArgumentException iae) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        SUBJECT_TYPE.getDescription());
		}
		// validate actor
		HierarchyNodeTypes hntActorType = HierarchyNodeTypes.getEnum(actorType);
		switch (hntActorType) {
			case ENTITY :
				// validate actor entity
				EntityDTO actorEntity = attachHierarchySearch.getActor()
				        .getEntity();
				validateIdentityFields(new IdentityDTO(actorEntity));
				// validate myDomian
				if (attachHierarchySearch.getIsParentDomain() != null
				        && attachHierarchySearch.getIsParentDomain()) {
					validateMyDomain(actorEntity.getPlatformEntity()
					        .getPlatformEntityType(), actorEntity
					        .getIdentifier().getValue());
				}
				break;
			case TEMPLATE :
				List<EntityTemplateDTO> templates = new ArrayList<EntityTemplateDTO>();
				EntityTemplateDTO actorisTemplate = attachHierarchySearch
				        .getActor().getTemplate();
				templates.add(actorisTemplate);
				validateTemplates(templates);
				break;
			default:
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        ACTOR.getDescription());
		}

		// fetch entities by RELATION_ATTACHEDTO
		attachHierarchySearch.setRelationName(RELATION_ATTACHEDTO
		        .getFieldName());
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		try {
			hierarchies = hierarchyRepository
			        .getImmediateRoot(attachHierarchySearch);

		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		for (HierarchyEntity hierarchyEntity : hierarchies) {
			EntityDTO entity = convertHierarchyToEntity(hierarchyEntity);
			entities.add(entity);
		}

		return entities;
	}

	@Override
	public List<EntityTemplateDTO> getAllTemplateSubjects(
	        AttachHierarchySearchDTO attachHierarchySearch) {

		// Validate isParentDomainSet
		if (attachHierarchySearch.getIsParentDomain() != null
		        && attachHierarchySearch.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_TENANT);
		}

		validateMandatoryFields(attachHierarchySearch, ACTOR_TYPE);
		if (CollectionUtils.isNotEmpty(attachHierarchySearch
		        .getSearchTemplates())) {
			validateTemplates(attachHierarchySearch.getSearchTemplates());
		}
		String actorType = attachHierarchySearch.getActorType();

		try {
			HierarchyNodeTypes.getEnum(actorType);
		} catch (IllegalArgumentException iae) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        SUBJECT_TYPE.getDescription());
		}

		// validate actor
		HierarchyNodeTypes hntActorType = HierarchyNodeTypes.getEnum(actorType);
		switch (hntActorType) {
			case ENTITY :
				// validate actor entity
				List<EntityDTO> actorentities = new ArrayList<EntityDTO>();
				EntityDTO actorEntity = attachHierarchySearch.getActor()
				        .getEntity();
				actorentities.add(actorEntity);
				validateEntities(actorentities);
				// validate myDomian
				if (attachHierarchySearch.getIsParentDomain() != null
				        && attachHierarchySearch.getIsParentDomain()) {
					validateMyDomain(actorEntity.getPlatformEntity()
					        .getPlatformEntityType(), actorEntity
					        .getIdentifier().getValue());
				}
				break;
			// case TEMPLATE :
			// List<EntityTemplateDTO> templates = new
			// ArrayList<EntityTemplateDTO>();
			// EntityTemplateDTO actorisTemplate = attachHierarchySearch
			// .getActor().getTemplate();
			// templates.add(actorisTemplate);
			// validateTemplates(templates);
			// break;
			default:
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        ACTOR.getDescription());
		}

		// fetch all template subjects attachedTo actor
		List<HierarchyEntityTemplate> hierarchies = new ArrayList<HierarchyEntityTemplate>();
		attachHierarchySearch.setRelationName(RELATION_ATTACHEDTO
		        .getFieldName());
		try {
			hierarchies = hierarchyRepository
			        .getAllTemplateSubjects(attachHierarchySearch);

		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		List<EntityTemplateDTO> templates = getTemplates(hierarchies);
		return templates;
	}

	private EntityDTO convertHierarchyToEntity(HierarchyEntity hierarchy) {
		EntityDTO entityDTO = new EntityDTO();
		// Set the dataprovider
		if (isNotBlank(hierarchy.getDataprovider())) {
			Gson gson = new Gson();
			String dataproviderString;
			try {
				dataproviderString = URLDecoder.decode(
				        hierarchy.getDataprovider(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Error while decoding dataprovider",
				        e);
			}
			List<FieldMapDTO> dataprovider = gson.fromJson(dataproviderString,
			        new TypeToken<List<FieldMapDTO>>() {
			        }.getType());
			entityDTO.setDataprovider(dataprovider);
		}
		if (isNotBlank(hierarchy.getTree())) {
			Gson gson = new Gson();
			String treeString;
			try {
				treeString = URLDecoder.decode(hierarchy.getTree(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Error while decoding tree : ", e);
			}
			FieldMapDTO tree = gson.fromJson(treeString, FieldMapDTO.class);
			entityDTO.setTree(tree);
		}
		// Set the domain
		if (isNotBlank(hierarchy.getDomain())) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(hierarchy.getDomain());
			entityDTO.setDomain(domain);
		}
		// Set entity type
		if (isNotBlank(hierarchy.getEntityType())) {
			PlatformEntityDTO globalEntityDTO = new PlatformEntityDTO();
			globalEntityDTO.setPlatformEntityType(hierarchy.getEntityType());
			entityDTO.setPlatformEntity(globalEntityDTO);
		}
		// Set template name
		if (isNotBlank(hierarchy.getTemplateName())) {
			EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
			entityTemplateDTO
			        .setEntityTemplateName(hierarchy.getTemplateName());
			entityDTO.setEntityTemplate(entityTemplateDTO);
		}
		// Set identifier
		if (isNotBlank(hierarchy.getIdentifierKey())
		        && isNotBlank(hierarchy.getIdentifierValue())) {
			FieldMapDTO identifierMap = new FieldMapDTO();
			identifierMap.setKey(hierarchy.getIdentifierKey());
			identifierMap.setValue(hierarchy.getIdentifierValue());
			entityDTO.setIdentifier(identifierMap);
		}
		// Set the status
		if (isNotBlank(hierarchy.getStatus())) {
			StatusDTO status = new StatusDTO();
			status.setStatusName(hierarchy.getStatus());
			entityDTO.setEntityStatus(status);
		}
		return entityDTO;
	}

	@Override
	public List<GeoTaggedEntitiesDTO> getGeotaggedEntities(
	        HierarchySearchDTO hierarchySearchDTO) {
		// Get domain from profile if it is empty
		// Validate domain
		ValidationUtils.validateMandatoryField(EMDataFields.DOMAIN,
		        hierarchySearchDTO.getDomain());

		// Validate myDomain
		if (hierarchySearchDTO.getIsParentDomain() != null
		        && hierarchySearchDTO.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_TENANT);
		}

		List<TaggedEntity> taggedEntity = new ArrayList<TaggedEntity>();
		try {
			taggedEntity = hierarchyRepository.getGeoTaggedEntities(
			        hierarchySearchDTO.getDomain(),
			        hierarchySearchDTO.getSearchTemplateName());
		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		// Construct response payload
		return constructGeoTagResponse(taggedEntity);
	}

	private List<GeoTaggedEntitiesDTO> constructGeoTagResponse(
	        List<TaggedEntity> taggedEntityList) {
		HashMap<String, List<GeoTaggedEntityDTO>> tagMap = new HashMap<String, List<GeoTaggedEntityDTO>>();

		for (TaggedEntity taggedEntity : taggedEntityList) {
			GeoTaggedEntityDTO geoTaggedEntityDTO = new GeoTaggedEntityDTO();

			// Set identifier
			FieldMapDTO entityIdentifier = new FieldMapDTO();
			entityIdentifier.setKey(taggedEntity.getEntityIdentifierKey());
			entityIdentifier.setValue(taggedEntity.getEntityIdentifierValue());
			geoTaggedEntityDTO.setIdentifier(entityIdentifier);

			// Set dataprovider for the entity

			if (isNotBlank(taggedEntity.getEntityDataprovider())) {
				String entityDataprovider = null;
				try {
					entityDataprovider = URLDecoder.decode(
					        taggedEntity.getEntityDataprovider(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(
					        "Error while decoding dataprovider", e);
				}

				Gson gson = new Gson();
				List<FieldMapDTO> dataprovider = gson.fromJson(
				        entityDataprovider, new TypeToken<List<FieldMapDTO>>() {
				        }.getType());
				geoTaggedEntityDTO.setDataprovider(dataprovider);
			}

			if (isNotBlank(taggedEntity.getGeotagDataprovider())) {
				String entityDataprovider = null;
				try {
					entityDataprovider = URLDecoder.decode(
					        taggedEntity.getGeotagDataprovider(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(
					        "Error while decoding dataprovider", e);
				}

				Gson gson = new Gson();
				List<FieldMapDTO> dataprovider = gson.fromJson(
				        entityDataprovider, new TypeToken<List<FieldMapDTO>>() {
				        }.getType());
				// Set dataprovider for the geotag
				CoordinatesDTO geoTag = getGeoTag(dataprovider);
				geoTaggedEntityDTO.setGeoTag(geoTag);
			}

			if (isNotBlank(taggedEntity.getTree())) {
				String treeString = null;
				try {
					treeString = URLDecoder.decode(taggedEntity.getTree(),
					        "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException("Error while decoding tree : ",
					        e);
				}

				Gson gson = new Gson();
				FieldMapDTO tree = gson.fromJson(treeString, FieldMapDTO.class);
				geoTaggedEntityDTO.setTree(tree);
			}

			// Set the domain
			if (isNotBlank(taggedEntity.getEntityDomain())) {
				DomainDTO domain = new DomainDTO();
				domain.setDomainName(taggedEntity.getEntityDomain());
				geoTaggedEntityDTO.setDomain(domain);
			}

			// Set entity type
			PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
			platformEntityDTO.setPlatformEntityType(taggedEntity
			        .getEntityType());

			if (tagMap.containsKey(taggedEntity.getEntityTemplateName())) {
				List<GeoTaggedEntityDTO> exitingTaggedEntities = tagMap
				        .get(taggedEntity.getEntityTemplateName());
				exitingTaggedEntities.add(geoTaggedEntityDTO);

			} else {
				List<GeoTaggedEntityDTO> taggedEntities = new ArrayList<GeoTaggedEntityDTO>();
				taggedEntities.add(geoTaggedEntityDTO);
				tagMap.put(taggedEntity.getEntityTemplateName(), taggedEntities);
			}

		}
		return getTaggedEntities(tagMap);

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

	/**
	 * @param hierarchySearchDTO
	 * @return List<String>
	 * 
	 */
	@Override
	public List<String> getTemplateNamesofAttachedEntities(
	        HierarchySearchDTO hierarchySearchDTO) {
		ValidationUtils.validateMandatoryFields(hierarchySearchDTO, DOMAIN,
		        SEARCH_TEMPLATE_NAME);

		if (hierarchySearchDTO.getIsParentDomain() != null
		        && hierarchySearchDTO.getIsParentDomain()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.NO_ACCESS_SELECTED_DOMAIN);
		}

		String searchDomain = hierarchySearchDTO.getDomain();
		String searchTemplateName = hierarchySearchDTO.getSearchTemplateName();

		List<String> entityTemplateNames = hierarchyRepository
		        .getTemplateNamesofAttachedEntities(searchDomain,
		                searchTemplateName);
		return entityTemplateNames;
	}

	@Override
	public EntityDTO updateStatus(EntityDTO entity) {
		ValidationUtils.validateMandatoryFields(entity, ENTITY_STATUS);
		validateUpdateEntityDto(entity);

		// validate myDomian
		if (entity.getIsParentDomain() != null && entity.getIsParentDomain()) {
			validateMyDomain(
			        entity.getPlatformEntity().getPlatformEntityType(), entity
			                .getIdentifier().getValue());
		}

		return hierarchyRepository.updateStatus(entity);
	}

	@Override
	public GeneralBatchResponse updateStatus(List<IdentityDTO> entities,
	        String statusName) {
		// CHeck if status is valid
		statusService.getStatus(statusName);

		for (IdentityDTO identityDTO : entities) {
			// Validate the input
			validateIdentifiers(identityDTO);
			identityDTO.setDomain(getDomain(identityDTO.getDomain()));
		}
		return hierarchyRepository.updateStatus(entities, statusName);
	}

	private void validateIdentifiers(IdentityDTO entity) {
		ValidationUtils.validateMandatoryFields(entity, ENTITY_TEMPLATE,
		        IDENTIFIER, DOMAIN, PLATFORM_ENTITY);

		ValidationUtils.validateMandatoryFields(entity.getIdentifier(),
		        IDENTIFIER_KEY, IDENTIFIER_VALUE);
		ValidationUtils
		        .validateMandatoryFields(entity.getDomain(), DOMAIN_NAME);
		ValidationUtils.validateMandatoryFields(entity.getEntityTemplate(),
		        ENTITY_TEMPLATE_NAME);
		ValidationUtils.validateMandatoryFields(entity.getPlatformEntity(),
		        PLATFORM_ENTITY_TYPE);
	}

	@Override
	public List<HierarchyRelationDTO> searchHierarchyRelation(
	        HierarchyRelationDTO hierarchyRelation) {

		ValidationUtils.validateMandatoryFields(hierarchyRelation, PARENT,
		        CHILD_V);

		validateSearchFields(hierarchyRelation.getParent());
		validateSearchFields(hierarchyRelation.getChild());

		List<HierarchyRelationDTO> hierarchies = null;

		try {
			hierarchies = hierarchyRepository
			        .searchHierarchyRelation(hierarchyRelation);

			if (CollectionUtils.isEmpty(hierarchies)) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return hierarchies;
	}

	private void validateSearchFields(EntityDTO entity) {

		ValidationUtils.validateMandatoryFields(entity, ENTITY_TEMPLATE,
		        PLATFORM_ENTITY);

		ValidationUtils.validateMandatoryFields(entity.getEntityTemplate(),
		        ENTITY_TEMPLATE_NAME);

		ValidationUtils.validateMandatoryFields(entity.getPlatformEntity(),
		        PLATFORM_ENTITY_TYPE);

		if (entity.getDomain() == null
		        || isBlank(entity.getDomain().getDomainName())) {
			DomainDTO domain = getDomain(null);
			entity.setDomain(domain);
		}
	}

	@Override
	public List<com.pcs.alpine.commons.dto.EntityDTO> getTagsByRange(
	        TagRangePayload payload) {

		List<com.pcs.alpine.commons.dto.EntityDTO> entities = new ArrayList<com.pcs.alpine.commons.dto.EntityDTO>();
		try {
			entities = hierarchyRepository.getTagsByRange(payload);
		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return entities;
	}

	@Override
	public Map<String, List<com.pcs.alpine.commons.dto.EntityDTO>> getEntitiesByTags(
	        EntitiesByTagsPayload entitiesByTagsPayload) {
		Map<String, List<com.pcs.alpine.commons.dto.EntityDTO>> map = hierarchyRepository
		        .getEntitiesByTags(entitiesByTagsPayload);
		if (CollectionUtils.isEmpty(map.keySet())) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return map;
	}

	@Override
	public StatusMessageDTO attachParentsToMultipleNodes(
	        HierarchyDTO hierarchyDTO) {

		ValidationUtils.validateMandatoryFields(hierarchyDTO, ACTORS, SUBJECTS);
		validateEntities(hierarchyDTO.getSubjects());
		validateEntities(hierarchyDTO.getActors());

		List<HierarchyEntity> actors = new ArrayList<HierarchyEntity>();

		// Validate actor and subjects
		for (EntityDTO actor : hierarchyDTO.getActors()) {

			// TODO change this once API accepts all platform types
			if (!actor.getPlatformEntity().getPlatformEntityType()
			        .equalsIgnoreCase(EMDataFields.MARKER.getFieldName())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        PLATFORM_ENTITY_TYPE.getDescription());
			}
			HierarchyDTO hierarchyEntity = new HierarchyDTO();
			hierarchyEntity.setActor(actor);
			hierarchyEntity.setSubjects(hierarchyDTO.getSubjects());
			valdateHierarchyParents(hierarchyEntity);

			// Create paylod of hierarchy repo
			HierarchyEntity actorHierarchy = new HierarchyEntity();
			EntityDTO entityFromPlatform = getEntityDataProvider(actor);
			actorHierarchy.setDataprovider(getDataProvider(entityFromPlatform
			        .getDataprovider()));
			actorHierarchy.setDomain(entityFromPlatform.getDomain()
			        .getDomainName());
			actorHierarchy.setIdentifierKey(entityFromPlatform.getIdentifier()
			        .getKey());
			actorHierarchy.setIdentifierValue(entityFromPlatform
			        .getIdentifier().getValue());
			actorHierarchy.setTree(getTree(entityFromPlatform.getTree()));
			actorHierarchy.setStatus(entityFromPlatform.getEntityStatus()
			        .getStatusName());
			actorHierarchy.setTemplateName(entityFromPlatform
			        .getEntityTemplate().getEntityTemplateName());
			actors.add(actorHierarchy);
		}
		List<EntityDTO> subjects = new ArrayList<EntityDTO>();
		// Get dataprovider for the subjects
		for (EntityDTO subject : hierarchyDTO.getSubjects()) {

			// TODO change this once API accepts all platform types
			if (!subject.getPlatformEntity().getPlatformEntityType()
			        .equalsIgnoreCase(EMDataFields.MARKER.getFieldName())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        PLATFORM_ENTITY_TYPE.getDescription());
			}
			EntityDTO subjectFromPlatform = getEntityDataProvider(subject);
			subjects.add(subjectFromPlatform);
		}
		// Invoke repository
		hierarchyRepository.attachParentsToMultipleMarkers(actors, subjects);
		StatusMessageDTO status = new StatusMessageDTO(Status.SUCCESS);
		return status;

	}

	@Override
	public List<EntityDTO> getEntitiesWithinTenantRange(
	        EntityRangeDTO hierarchySearch) {
		ValidationUtils.validateMandatoryFields(hierarchySearch, END_ENTITY);
		ValidationUtils.validateMandatoryFields(hierarchySearch,
		        SEARCH_TEMPLATE_NAME);
		Boolean validateDomain = false;
		validateEntities(hierarchySearch, validateDomain);
		if (hierarchySearch.getEndEntity().getDomain() == null
		        || isBlank(hierarchySearch.getEndEntity().getDomain()
		                .getDomainName())) {
			hierarchySearch.getEndEntity().setDomain(
			        getDomain(hierarchySearch.getEndEntity().getDomain()));
		}

		if (hierarchySearch.getStartEntity().getDomain() == null
		        || isBlank(hierarchySearch.getStartEntity().getDomain()
		                .getDomainName())) {
			hierarchySearch.getStartEntity().setDomain(
			        getDomain(hierarchySearch.getStartEntity().getDomain()));
		}

		String domainName = subscriptionProfileService.getEndUserDomain();
		HierarchyEntity entity = hierarchyRepository.getTenantNode(domainName);
		HierarchyEntity startEntity = new HierarchyEntity();

		// if start node domain not specified take logged in domain
		if (hierarchySearch.getStartEntity() == null) {
			startEntity = entity;
		} else {
			startEntity = constructHierarchyEntity(hierarchySearch
			        .getStartEntity());
			List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
			try {
				hierarchies = hierarchyRepository.getChildren(entity, null,
				        null);
			} catch (NoResultException e) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
			hierarchies.add(entity);
			if (CollectionUtils.isNotEmpty(hierarchies)) {
				if (!hierarchies.contains(startEntity)) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.ENTITIES_FROM_WRONG_HIERARCHY);
				}
			}

		}
		HierarchyEntity endEntity = constructHierarchyEntity(hierarchySearch
		        .getEndEntity());
		List<HierarchyEntity> hierarchies = new ArrayList<HierarchyEntity>();
		try {
			hierarchies = hierarchyRepository.getEntitiesBetweenTenants(
			        startEntity, endEntity,
			        hierarchySearch.getSearchTemplateName());

		} catch (Exception e) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		List<EntityDTO> entities = new ArrayList<EntityDTO>();
		for (HierarchyEntity hierarchyEntity2 : hierarchies) {
			EntityDTO entityDTO = convertHierarchyToEntity(hierarchyEntity2);
			entities.add(entityDTO);
		}

		return entities;
	}

	@Override
	public List<HierarchyTagCountDTO> getTaggedEntityCount(
	        HierarchyTagSearchDTO hierarchyTagSearchDTO) {
		// validate input
		ValidationUtils.validateMandatoryFields(hierarchyTagSearchDTO,
		        TAG_TYPE, END_TEMPLATE);
		ValidationUtils.validateCollection(INTERMEDIATE_TEMPLATES,
		        hierarchyTagSearchDTO.getIntermediateTemplates());
		// If domain is empty fetch from logged in user
		if (isBlank(hierarchyTagSearchDTO.getDomainName())) {
			hierarchyTagSearchDTO.setDomainName(subscriptionProfileService
			        .getEndUserDomain());
		}
		List<DistributionEntity> taggedEntity = hierarchyRepository
		        .getTaggedEntityCount(hierarchyTagSearchDTO);

		List<HierarchyTagCountDTO> hierarchyTagCountDTOs = new ArrayList<HierarchyTagCountDTO>();

		// Construct Field to be fetched
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(NAME.getFieldName());
		Gson gson = new Gson();
		for (DistributionEntity distributionEntity : taggedEntity) {
			// Convert to List<FieldMapDTO>
			List<FieldMapDTO> dataprovider = gson.fromJson(
			        distributionEntity.getDataprovider(),
			        new TypeToken<List<FieldMapDTO>>() {
			        }.getType());
			HierarchyTagCountDTO hierarchyTagCountDTO = new HierarchyTagCountDTO();
			hierarchyTagCountDTO.setCount(distributionEntity.getCount());
			hierarchyTagCountDTO.setTag(fetchFieldValue(dataprovider,
			        fieldMapDTO));

			// add to the list to be returned
			hierarchyTagCountDTOs.add(hierarchyTagCountDTO);
		}
		return hierarchyTagCountDTOs;
	}

}
