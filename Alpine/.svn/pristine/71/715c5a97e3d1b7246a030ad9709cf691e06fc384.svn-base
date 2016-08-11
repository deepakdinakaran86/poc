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

import static com.pcs.alpine.commons.validation.ValidationUtils.validateCollection;
import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.constants.TagConstant.ENTITY;
import static com.pcs.alpine.constants.TagConstant.MARKER;
import static com.pcs.alpine.constants.TagConstant.TAG_TEMPLATE;
import static com.pcs.alpine.constants.TagConstant.TEMPLATE;
import static com.pcs.alpine.constants.TagConstant.TENANT;
import static com.pcs.alpine.constants.TagConstant.TENANT_TEMPLATE;
import static com.pcs.alpine.constants.TagConstant.USER;
import static com.pcs.alpine.constants.TagConstant.USER_TEMPLATE;
import static com.pcs.alpine.enums.TagDataFields.ENTITY_TEMPLATE;
import static com.pcs.alpine.enums.TagDataFields.ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.enums.TagDataFields.FIELD_VALUES;
import static com.pcs.alpine.enums.TagDataFields.IDENTIFIER;
import static com.pcs.alpine.enums.TagDataFields.IDENTIFIER_KEY;
import static com.pcs.alpine.enums.TagDataFields.IDENTIFIER_VALUE;
import static com.pcs.alpine.enums.TagDataFields.PLATFORM_ENTITY;
import static com.pcs.alpine.enums.TagDataFields.PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.enums.TagDataFields.TAG;
import static com.pcs.alpine.enums.TagDataFields.TAG_TYPE;
import static com.pcs.alpine.enums.TagDataFields.TAG_TYPE_VALUES;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pcs.alpine.commons.dto.DomainDTO;
import com.pcs.alpine.commons.dto.EntityDTO;
import com.pcs.alpine.commons.dto.EntityTemplateDTO;
import com.pcs.alpine.commons.dto.FieldMapDTO;
import com.pcs.alpine.commons.dto.HierarchyTagCountDTO;
import com.pcs.alpine.commons.dto.HierarchyTagSearchDTO;
import com.pcs.alpine.commons.dto.IdentityDTO;
import com.pcs.alpine.commons.dto.PlatformEntityDTO;
import com.pcs.alpine.commons.dto.SearchFieldsDTO;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.dto.Subscription;
import com.pcs.alpine.commons.dto.SubscriptionContextHolder;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.rest.dto.ErrorMessageDTO;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.util.ObjectBuilderUtil;
import com.pcs.alpine.dto.Actor;
import com.pcs.alpine.dto.AttachHierarchyDTO;
import com.pcs.alpine.dto.AttachHierarchySearchDTO;
import com.pcs.alpine.dto.EntitiesByTagsFilter;
import com.pcs.alpine.dto.EntitiesByTagsPayload;
import com.pcs.alpine.dto.GeneralTagResponse;
import com.pcs.alpine.dto.Subject;
import com.pcs.alpine.dto.SubjectEntitiesByTags;
import com.pcs.alpine.dto.Tag;
import com.pcs.alpine.dto.TagConfiguration;
import com.pcs.alpine.dto.TagTypeTemplate;
import com.pcs.alpine.dto.ValidatePayload;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.enums.TagDataFields;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.rest.exception.GalaxyRestException;
import com.pcs.alpine.service.TagService;
import com.pcs.alpine.service.TagTypeTemplateService;

/**
 * Service Implementation for tag Related APIs
 * 
 * @author Greeshma (PCSEG323)
 * @date March 2016
 * @since Avocado-1.0.0
 */
@Service
public class TagServiceImpl implements TagService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TagServiceImpl.class);

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformClient;

	@Autowired
	private SubscriptionProfileService profileService;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Autowired
	private TagTypeTemplateService tagTypeService;

	@Value("${platform.esb.marker.search}")
	private String markerSearchEP;

	@Value("${platform.esb.marker.list.filter}")
	private String markerListFilterEP;

	@Value("${platform.esb.marker}")
	private String markerSaveEP;

	@Value("${platform.esb.marker.find}")
	private String markerFindEP;

	@Value("${platform.esb.template.find}")
	private String templateFindEP;

	@Value("${platform.esb.hierarchy.children.immediate}")
	private String hierarchyChildrenImmediateEP;

	@Value("${platform.esb.hierarchy.attach.children}")
	private String hierarchAttachChildrenyEP;

	@Value("${platform.esb.hierarchy.subjects.entities}")
	private String hierarchySubjectEntitiesEP;

	@Value("${platform.esb.hierarchy.subjects.filter.entities}")
	private String hierarchySubjectFilterEntitiesEP;

	@Value("${platform.esb.hierarchy.subjects.templates}")
	private String hierarchySubjectTemplatesEP;

	@Value("${platform.esb.hierarchy.root.immediate}")
	private String hierarchyRootImmediateEP;

	@Value("${platform.esb.hierarchy.attach.subjects}")
	private String hierarchyAttachSubjectsEP;

	@Value("${platform.esb.hierarchy.attach.actors}")
	private String hierarchyAttachActorsEP;

	@Value("${platform.esb.user.find}")
	private String userFindEP;

	@Value("${platform.esb.tenant.find}")
	private String tenantFindEP;

	@Value("${platform.esb.hierarchy.attached.entity.count}")
	private String attachedEntityCountEP;

	List<GeneralTagResponse> generalResponses = null;

	/**
	 * Service method to get all the tags
	 * 
	 * @param templateName
	 *            ,domain,tagType
	 * @return {@link tagDTO}
	 * 
	 */

	@Override
	public List<Tag> getAllTagsByType(String tagType, String domain) {

		DomainDTO domainDTO = new DomainDTO();
		if (StringUtils.isEmpty(domain)) {
			Subscription subscription = SubscriptionContextHolder.getContext()
			        .getSubscription();
			domain = subscription.getEndUserDomain();
		}
		domainDTO.setDomainName(domain);

		if (StringUtils.isNotBlank(tagType)) {
			List<EntityDTO> tagDTOs = getAllTagsByType(domainDTO, tagType);

			List<Tag> tags = new ArrayList<Tag>();
			for (EntityDTO tagEntityDTO : tagDTOs) {
				Tag tag = new Tag();
				tag.setDomain(tagEntityDTO.getDomain());

				List<FieldMapDTO> dataprovider = new ArrayList<FieldMapDTO>();
				dataprovider = tagEntityDTO.getDataprovider();
				FieldMapDTO nameFM = new FieldMapDTO(
				        TagDataFields.NAME.getVariableName());
				String name = fetchFieldValue(dataprovider, nameFM);

				tag.setName(name);
				tag.setTagType(tagEntityDTO.getEntityTemplate()
				        .getEntityTemplateName());

				tags.add(tag);
			}
			return tags;

		} else {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        TAG_TYPE.getDescription());
		}
	}

	@Override
	public EntityDTO createTag(EntityDTO tagEntity) {
		validateCreateTag(tagEntity);

		// validate tagType before creating tag entity
		TagTypeTemplate tagTypeTemplate = null;
		try {

			tagTypeTemplate = tagTypeService.getTagType(tagEntity
			        .getEntityTemplate().getEntityTemplateName(), tagEntity
			        .getDomain().getDomainName());
		} catch (GalaxyRestException gre) {
			if (gre.getMessage().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				LOGGER.debug("tagType {} doesnot exist."
				        + tagEntity.getEntityTemplate().getEntityTemplateName());
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        TAG_TYPE.getDescription());
			}
		}
		// if key-values specified are different from keys in tagTypeTemplate,
		// dont proceed
		validateTagFieldValues(tagEntity.getFieldValues(),
		        tagTypeTemplate.getTagTypeValues());

		// create payload for tag save entity service
		EntityDTO createdTagEntity = null;
		Map<String, String> jwtToken = profileService.getJwtToken();
		createdTagEntity = platformClient.post(markerSaveEP, jwtToken,
		        tagEntity, EntityDTO.class);
		if (createdTagEntity != null
		        & createdTagEntity.getIdentifier() != null
		        & StringUtils.isNotBlank(createdTagEntity.getIdentifier()
		                .getValue())) {
			return createdTagEntity;
		} else {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.PERSISTENCE_EXCEPTION);
		}
	}

	private void validateCreateTag(EntityDTO tagEntity) {
		validateMandatoryFields(tagEntity, ENTITY_TEMPLATE, FIELD_VALUES);
		validateMandatoryFields(tagEntity.getEntityTemplate(),
		        ENTITY_TEMPLATE_NAME);
		validateCollection(FIELD_VALUES, tagEntity.getFieldValues());

		if (tagEntity.getDomain() != null) {
			if (StringUtils.isBlank(tagEntity.getDomain().getDomainName())) {
				String domainName = profileService.getEndUserDomain();
				DomainDTO domainDTO = new DomainDTO();
				domainDTO.setDomainName(domainName);
				tagEntity.setDomain(domainDTO);
			}
		} else {
			String domainName = profileService.getEndUserDomain();
			DomainDTO domainDTO = new DomainDTO();
			domainDTO.setDomainName(domainName);
			tagEntity.setDomain(domainDTO);
		}

		if (tagEntity.getPlatformEntity() != null) {

			if (StringUtils.isNotBlank(tagEntity.getPlatformEntity()
			        .getPlatformEntityType())) {
				String platformEntityType = tagEntity.getPlatformEntity()
				        .getPlatformEntityType();
				if (!platformEntityType.equals(MARKER)) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
					        PLATFORM_ENTITY_TYPE.getDescription());
				}
			} else {
				String platformEntityType = MARKER;
				PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
				platformEntityDTO.setPlatformEntityType(platformEntityType);
				tagEntity.setPlatformEntity(platformEntityDTO);
			}
		} else {
			String platformEntityType = MARKER;
			PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
			platformEntityDTO.setPlatformEntityType(platformEntityType);
			tagEntity.setPlatformEntity(platformEntityDTO);
		}
	}

	@Override
	public StatusMessageDTO mapSubjectsToTagEntity(TagConfiguration config) {
		validateMapSubjectsPayload(config);

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		generalResponses = new ArrayList<GeneralTagResponse>();

		statusMessageDTO = mapSubjectsToTag(config);
		return statusMessageDTO;
	}

	private void validateMapSubjectsPayload(TagConfiguration config) {
		validateMandatoryFields(config, TAG);
	}

	@Override
	public StatusMessageDTO mapTagsToAnEntity(TagConfiguration config) {

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		generalResponses = new ArrayList<GeneralTagResponse>();

		statusMessageDTO = mapTagsToEntity(config);
		return statusMessageDTO;
	}

	@Override
	public List<EntityDTO> getAllEntitySubjects(EntityDTO entity) {

		AttachHierarchySearchDTO attachHierarchySearch = new AttachHierarchySearchDTO();
		// actorType must be ENTITY, because tag is an entity
		attachHierarchySearch.setActorType(ENTITY);
		Actor actor = new Actor();
		actor.setEntity(entity);

		attachHierarchySearch.setActor(actor);

		Gson gson = objectBuilderUtil.getGson();
		LOGGER.info(gson.toJson(attachHierarchySearch));
		Map<String, String> jwtToken = profileService.getJwtToken();
		@SuppressWarnings("unchecked") List<EntityDTO> entities = platformClient
		        .post(hierarchySubjectEntitiesEP, jwtToken,
		                attachHierarchySearch, List.class, EntityDTO.class);

		return entities;
	}

	@Override
	public List<Tag> getAllTagsOfActor(
	        AttachHierarchySearchDTO attachHierarchySearch) {

		String actorType = attachHierarchySearch.getActorType();
		if (actorType.equals(ENTITY)) {

			Actor actor = attachHierarchySearch.getActor();
			EntityDTO entityDTO = actor.getEntity();

			DomainDTO domain = new DomainDTO();
			if (attachHierarchySearch.getActor().getEntity().getDomain() != null) {
				if (StringUtils.isEmpty(attachHierarchySearch.getActor()
				        .getEntity().getDomain().getDomainName())) {
					domain.setDomainName(profileService.getEndUserDomain());
					entityDTO.setDomain(domain);
				}
			} else {
				domain.setDomainName(profileService.getEndUserDomain());
				entityDTO.setDomain(domain);
			}

			actor.setEntity(entityDTO);
			attachHierarchySearch.setActor(actor);

		} else if (actorType.equals(TEMPLATE)) {
			Actor actor = attachHierarchySearch.getActor();
			EntityTemplateDTO templateDTO = actor.getTemplate();

			DomainDTO domain = new DomainDTO();
			if (attachHierarchySearch.getActor().getTemplate().getDomain() != null) {
				if (StringUtils.isEmpty(attachHierarchySearch.getActor()
				        .getTemplate().getDomain().getDomainName())) {
					domain.setDomainName(profileService.getEndUserDomain());
					templateDTO.setDomain(domain);
				}
			} else {
				domain.setDomainName(profileService.getEndUserDomain());
				templateDTO.setDomain(domain);
			}
			actor.setTemplate(templateDTO);
			attachHierarchySearch.setActor(actor);
		}

		Gson gson = objectBuilderUtil.getGson();
		LOGGER.info(gson.toJson(attachHierarchySearch));
		Map<String, String> jwtToken = profileService.getJwtToken();
		@SuppressWarnings("unchecked") List<EntityDTO> entities = platformClient
		        .post(hierarchyRootImmediateEP, jwtToken,
		                attachHierarchySearch, List.class, EntityDTO.class);

		List<Tag> tags = new ArrayList<Tag>();
		for (EntityDTO entityDTO : entities) {
			Tag tag = new Tag();
			tag.setDomain(entityDTO.getDomain());

			List<FieldMapDTO> dataprovider = new ArrayList<FieldMapDTO>();
			dataprovider = entityDTO.getDataprovider();
			FieldMapDTO nameFM = new FieldMapDTO(
			        TagDataFields.NAME.getVariableName());
			String name = fetchFieldValue(dataprovider, nameFM);

			tag.setName(name);
			tag.setTagType(entityDTO.getEntityTemplate()
			        .getEntityTemplateName());

			tags.add(tag);
		}
		return tags;
	}

	@Override
	public List<EntityTemplateDTO> getAllTemplateSubjects(EntityDTO entity) {

		AttachHierarchySearchDTO attachHierarchySearch = new AttachHierarchySearchDTO();
		// actorType must be ENTITY, because tag is an entity
		attachHierarchySearch.setActorType(ENTITY);
		Actor actor = new Actor();
		actor.setEntity(entity);
		attachHierarchySearch.setActor(actor);

		List<EntityTemplateDTO> templates = getAllTemplatesFromPlatform(attachHierarchySearch);
		return templates;
	}

	private void validateTagFieldValues(List<FieldMapDTO> tagFieldValues,
	        List<String> keys) {
		List<FieldMapDTO> validkeyValues = new ArrayList<FieldMapDTO>();
		for (FieldMapDTO keyValueFM : tagFieldValues) {
			if (keys.contains(keyValueFM.getKey())) {
				validkeyValues.add(keyValueFM);
			}
		}
		if (CollectionUtils.isEmpty(validkeyValues)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        TAG_TYPE_VALUES.getDescription());
		}
	}

	private List<EntityTemplateDTO> getAllTemplatesFromPlatform(
	        AttachHierarchySearchDTO attachHierarchySearch) {
		Gson gson = objectBuilderUtil.getGson();
		LOGGER.info(gson.toJson(attachHierarchySearch));
		Map<String, String> jwtToken = profileService.getJwtToken();
		@SuppressWarnings("unchecked") List<EntityTemplateDTO> templates = platformClient
		        .post(hierarchySubjectTemplatesEP, jwtToken,
		                attachHierarchySearch, List.class,
		                EntityTemplateDTO.class);
		return templates;
	}

	private List<EntityTemplateDTO> getTaggedTemplates(TagConfiguration config) {
		AttachHierarchySearchDTO attachHierarchySearch = new AttachHierarchySearchDTO();
		EntityDTO actorEntity = constructActorEntity(config.getTag());
		Actor actor = new Actor();
		actor.setEntity(actorEntity);
		attachHierarchySearch.setActor(actor);
		attachHierarchySearch.setActorType(ENTITY);

		List<EntityTemplateDTO> taggedTemplates = null;
		try {
			taggedTemplates = new ArrayList<EntityTemplateDTO>();
			taggedTemplates = getAllTemplatesFromPlatform(attachHierarchySearch);
			return taggedTemplates;
		} catch (GalaxyRestException gre) {
			if (gre.getMessage().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				return null;
			} else {
				throw gre;
			}
		}

	}

	private StatusMessageDTO mapSubjectsToTag(TagConfiguration config) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO(Status.FAILURE);

		List<EntityTemplateDTO> templates = config.getTemplates();
		if (CollectionUtils.isNotEmpty(templates)) {
			List<EntityTemplateDTO> taggedTemplates = getTaggedTemplates(config);
			statusMessageDTO = mapTemplatesToTag(config.getTag(), templates,
			        taggedTemplates);
		} else {
			List<EntityDTO> entities = config.getEntities();
			if (CollectionUtils.isNotEmpty(entities)) {
				List<EntityTemplateDTO> taggedTemplates = getTaggedTemplates(config);
				statusMessageDTO = mapEntitiesToTag(config.getTag(), entities,
				        taggedTemplates);
			} else {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INCOMPLETE_REQUEST);
			}
		}
		return statusMessageDTO;
	}

	private StatusMessageDTO mapEntitiesToTag(IdentityDTO tag,
	        List<EntityDTO> entities, List<EntityTemplateDTO> taggedTemplates) {

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO(Status.FAILURE);

		// List<EntityTemplateDTO> templatesToBeMapped = new
		// ArrayList<EntityTemplateDTO>();

		for (EntityDTO entity : entities) {
			EntityTemplateDTO template = entity.getEntityTemplate();
			template.setDomain(entity.getDomain());
			template.setPlatformEntityType(entity.getPlatformEntity()
			        .getPlatformEntityType());

			DomainDTO domainDTO = new DomainDTO();
			if (template.getDomain() != null) {
				if (StringUtils.isEmpty(template.getDomain().getDomainName())) {
					domainDTO.setDomainName(profileService.getEndUserDomain());
					template.setDomain(domainDTO);
				}
			} else {
				domainDTO.setDomainName(profileService.getEndUserDomain());
				template.setDomain(domainDTO);
			}

			if (templateIsTagged(template, taggedTemplates)) {
				continue;
			} else {
				/**
				 * if tagEntity doesnot tagged to entity.getEntityTemplate(),
				 * first tag template then entity to tagEntity. Code will be
				 * used later
				 */
				// EntityTemplateDTO templateDTO = entity.getEntityTemplate();
				// templateDTO.setDomain(entity.getDomain());
				// templateDTO.setPlatformEntityType(entity.getPlatformEntity()
				// .getPlatformEntityType());
				// templatesToBeMapped.add(templateDTO);
				// continue;

				throw new GalaxyException(GalaxyCommonErrorCodes.CUSTOM_ERROR,
				        entity.getEntityTemplate().getEntityTemplateName()
				                + " template is not tagged to tag entity.");
			}
		}
		AttachHierarchyDTO hierarchy = new AttachHierarchyDTO();

		EntityDTO actorEntity = constructActorEntity(tag);
		Actor actor = new Actor();
		actor.setEntity(actorEntity);

		hierarchy.setActor(actor);
		hierarchy.setActorType(ENTITY);

		Subject subject = new Subject();
		subject.setEntities(entities);
		hierarchy.setSubjectType(ENTITY);
		hierarchy.setSubject(subject);

		Gson gson = objectBuilderUtil.getGson();
		LOGGER.info(gson.toJson(hierarchy));
		Map<String, String> jwtToken = profileService.getJwtToken();
		statusMessageDTO = platformClient.post(hierarchyAttachSubjectsEP,
		        jwtToken, hierarchy, StatusMessageDTO.class);

		return statusMessageDTO;
	}

	private StatusMessageDTO mapTemplatesToTag(IdentityDTO tag,
	        List<EntityTemplateDTO> templates,
	        List<EntityTemplateDTO> taggedTemplates) {

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO(Status.FAILURE);
		List<EntityTemplateDTO> templatesToBeMapped = new ArrayList<EntityTemplateDTO>();

		Boolean taggedTemplatesFlag = false;
		if (CollectionUtils.isNotEmpty(taggedTemplates)) {
			taggedTemplatesFlag = true;
		}
		for (EntityTemplateDTO subTemplate : templates) {
			// Tag template cannot be tagged to Tag entity
			if (subTemplate.getEntityTemplateName().equals(TAG_TEMPLATE)) {
				throw new GalaxyException(GalaxyCommonErrorCodes.CUSTOM_ERROR,
				        "Tag template cannot be tagged");
			}
			if (taggedTemplatesFlag) {

				DomainDTO domainDTO = new DomainDTO();
				if (subTemplate.getDomain() != null) {
					if (StringUtils.isEmpty(subTemplate.getDomain()
					        .getDomainName())) {
						domainDTO.setDomainName(profileService
						        .getEndUserDomain());
						subTemplate.setDomain(domainDTO);
					}
				} else {
					domainDTO.setDomainName(profileService.getEndUserDomain());
					subTemplate.setDomain(domainDTO);
				}

				// check if template is already mapped
				if (templateIsTagged(subTemplate, taggedTemplates)) {
					continue;
				} else {
					templatesToBeMapped.add(subTemplate);
					continue;
				}
			} else {
				templatesToBeMapped.add(subTemplate);
				continue;
			}

		}
		if (CollectionUtils.isEmpty(templatesToBeMapped)) {
			statusMessageDTO.setStatus(Status.SUCCESS);
			return statusMessageDTO;
		} else {
			AttachHierarchyDTO hierarchy = new AttachHierarchyDTO();

			EntityDTO actorEntity = constructActorEntity(tag);
			Actor actor = new Actor();
			actor.setEntity(actorEntity);

			hierarchy.setActor(actor);
			hierarchy.setActorType(ENTITY);

			Subject subject = new Subject();
			subject.setTemplates(templatesToBeMapped);
			hierarchy.setSubjectType(TEMPLATE);
			hierarchy.setSubject(subject);

			Gson gson = objectBuilderUtil.getGson();
			LOGGER.info(gson.toJson(hierarchy));
			Map<String, String> jwtToken = profileService.getJwtToken();
			statusMessageDTO = platformClient.post(hierarchyAttachSubjectsEP,
			        jwtToken, hierarchy, StatusMessageDTO.class);

			return statusMessageDTO;
		}
	}

	private StatusMessageDTO mapTagsToEntity(TagConfiguration config) {

		List<IdentityDTO> tags = config.getTags();

		IdentityDTO identity = config.getEntity();
		List<EntityDTO> validTagEntities = new ArrayList<EntityDTO>();

		// 2. validate tag entities
		for (IdentityDTO tagIdentity : tags) {
			EntityDTO tagEntity = new EntityDTO();
			DomainDTO domainDTO = new DomainDTO();
			if (tagIdentity.getDomain() != null) {
				if (StringUtils
				        .isBlank(tagIdentity.getDomain().getDomainName())) {
					domainDTO.setDomainName(profileService.getEndUserDomain());
					tagEntity.setDomain(domainDTO);
				} else {
					tagEntity.setDomain(tagIdentity.getDomain());
				}
			} else {
				domainDTO.setDomainName(profileService.getEndUserDomain());
				tagEntity.setDomain(domainDTO);
			}
			tagEntity.setIdentifier(tagIdentity.getIdentifier());
			tagEntity.setPlatformEntity(tagIdentity.getPlatformEntity());
			tagEntity.setEntityTemplate(tagIdentity.getEntityTemplate());

			// tag entity must be tagged to template of an entity
			List<EntityTemplateDTO> taggedTemplates = getTaggedTemplates(tagEntity);

			if (CollectionUtils.isEmpty(taggedTemplates)) {
				// template is not mapped TODO map template first
				throw new GalaxyException(GalaxyCommonErrorCodes.CUSTOM_ERROR,
				        "Tag entity does not have any tagged template.");
			} else {
				String templateName = identity.getEntityTemplate()
				        .getEntityTemplateName();
				Boolean result = false;
				for (EntityTemplateDTO template : taggedTemplates) {
					if (template.getEntityTemplateName().equals(templateName)) {
						result = true;
						break;
					}
				}
				if (result) {
					validTagEntities.add(tagEntity);
				} else {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.CUSTOM_ERROR,
					        "Tag entity does not have template :"
					                + templateName + " tagged.");
					// template is not mapped TODO map template first
				}
			}
		}

		AttachHierarchyDTO hierarchy = new AttachHierarchyDTO();
		Actor actor = new Actor();
		actor.setEntities(validTagEntities);
		hierarchy.setActor(actor);

		Subject subject = new Subject();

		EntityDTO entity = new EntityDTO();
		DomainDTO domainOfEntity = new DomainDTO();
		if (config.getEntity().getDomain() != null) {
			if (StringUtils.isBlank(config.getEntity().getDomain()
			        .getDomainName())) {
				domainOfEntity.setDomainName(profileService.getEndUserDomain());
				entity.setDomain(domainOfEntity);
			} else {
				entity.setDomain(config.getEntity().getDomain());
			}
		} else {
			domainOfEntity.setDomainName(profileService.getEndUserDomain());
			entity.setDomain(domainOfEntity);
		}
		entity.setIdentifier(config.getEntity().getIdentifier());
		entity.setPlatformEntity(config.getEntity().getPlatformEntity());
		entity.setEntityTemplate(config.getEntity().getEntityTemplate());

		subject.setEntity(entity);
		hierarchy.setSubject(subject);

		hierarchy.setActorType(ENTITY);
		hierarchy.setSubjectType(ENTITY);
		Gson gson = objectBuilderUtil.getGson();
		LOGGER.info(gson.toJson(hierarchy));
		Map<String, String> jwtToken = profileService.getJwtToken();
		StatusMessageDTO status = platformClient.post(hierarchyAttachActorsEP,
		        jwtToken, hierarchy, StatusMessageDTO.class);
		return status;
	}

	private List<EntityTemplateDTO> getTaggedTemplates(EntityDTO tagEntity) {
		AttachHierarchySearchDTO attachHierarchySearch = new AttachHierarchySearchDTO();
		Actor actor = new Actor();
		actor.setEntity(tagEntity);
		attachHierarchySearch.setActor(actor);
		attachHierarchySearch.setActorType(ENTITY);

		List<EntityTemplateDTO> taggedTemplates = null;
		try {
			taggedTemplates = new ArrayList<EntityTemplateDTO>();
			taggedTemplates = getAllTemplatesFromPlatform(attachHierarchySearch);
			return taggedTemplates;
		} catch (GalaxyRestException gre) {
			if (gre.getMessage().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				return null;
			} else {
				throw gre;
			}
		}

	}

	private Boolean templateIsTagged(EntityTemplateDTO subTemplate,
	        List<EntityTemplateDTO> taggedTemplates) {
		Boolean isTagged = false;
		// check if template is already mapped
		if (CollectionUtils.isNotEmpty(taggedTemplates)) {
			for (EntityTemplateDTO template : taggedTemplates) {
				if (template.getDomain().getDomainName()
				        .equals(subTemplate.getDomain().getDomainName())) {
					// domains are same
					if (template.getEntityTemplateName().equals(
					        subTemplate.getEntityTemplateName())) {
						if (template.getPlatformEntityType().equals(
						        subTemplate.getPlatformEntityType())) {
							return isTagged = true;
						}
					}
				}

			}
		}
		return isTagged;
	}

	private EntityDTO constructActorEntity(IdentityDTO tag) {
		EntityDTO actor = new EntityDTO();

		IdentityDTO tagIdentity = tag;
		DomainDTO domainDTO = tag.getDomain();
		actor.setDomain(domainDTO);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(tagIdentity.getEntityTemplate()
		        .getEntityTemplateName());
		actor.setEntityTemplate(entityTemplateDTO);

		PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType(MARKER);
		actor.setPlatformEntity(platformEntityDTO);

		FieldMapDTO identifierFM = new FieldMapDTO();
		identifierFM.setKey(IDENTIFIER.getFieldName());
		identifierFM.setValue(tagIdentity.getIdentifier().getValue());
		actor.setIdentifier(identifierFM);

		return actor;
	}

	private List<EntityDTO> getAllTagsByType(DomainDTO domainDTO, String tagType) {

		EntityDTO entityDTO = new EntityDTO();
		entityDTO.setEntityTemplate(gettagTemplate());
		entityDTO.setDomain(domainDTO);

		IdentityDTO identityDTO = new IdentityDTO();
		identityDTO.setDomain(domainDTO);

		EntityTemplateDTO template = new EntityTemplateDTO();
		template.setEntityTemplateName(tagType);
		identityDTO.setEntityTemplate(template);
		Map<String, String> jwtToken = profileService.getJwtToken();
		@SuppressWarnings("unchecked") List<EntityDTO> entityDTOs = platformClient
		        .post(markerListFilterEP, jwtToken, identityDTO, List.class,
		                EntityDTO.class);
		if (CollectionUtils.isNotEmpty(entityDTOs)) {
			return entityDTOs;
		} else {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.PERSISTENCE_EXCEPTION);

		}
	}

	private EntityTemplateDTO gettagTemplate() {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(TAG_TEMPLATE);
		return entityTemplateDTO;
	}

	private String fetchFieldValue(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field.getValue();
	}

	@Override
	public EntityDTO getTagDetails(Tag tag) {
		Map<String, String> token = profileService.getJwtToken();
		DomainDTO domainDTO = new DomainDTO();
		if (tag.getDomain() != null) {
			if (isBlank(tag.getDomain().getDomainName())) {
				domainDTO.setDomainName(profileService.getEndUserDomain());
				tag.setDomain(domainDTO);
			}
		} else {

			domainDTO.setDomainName(profileService.getEndUserDomain());
			tag.setDomain(domainDTO);
		}
		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
		searchFieldsDTO.setDomain(tag.getDomain());

		List<FieldMapDTO> searFields = new ArrayList<FieldMapDTO>();
		FieldMapDTO nameFM = new FieldMapDTO(
		        TagDataFields.NAME.getVariableName(), tag.getName());
		searFields.add(nameFM);
		searchFieldsDTO.setSearchFields(searFields);
		EntityTemplateDTO templateDTO = new EntityTemplateDTO();
		templateDTO.setEntityTemplateName(tag.getTagType());
		searchFieldsDTO.setEntityTemplate(templateDTO);

		EntityDTO entity = null;
		try {

			Gson gson = objectBuilderUtil.getGson();
			LOGGER.info(gson.toJson(searchFieldsDTO));
			// Get tags details
			@SuppressWarnings("unchecked") List<EntityDTO> tagEntityDetails = platformClient
			        .post(markerSearchEP, token, searchFieldsDTO, List.class,
			                EntityDTO.class);
			entity = tagEntityDetails.get(0);
		} catch (GalaxyRestException gre) {
			if (gre.getMessage().equals("Search Fields List is invalid")) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			} else {
				throw gre;
			}
		}
		return entity;
	}

	@Override
	public StatusMessageDTO validatePayload(ValidatePayload payload) {

		StatusMessageDTO messageDTO = new StatusMessageDTO(Status.FAILURE);

		List<EntityDTO> entities = payload.getEntities();
		List<EntityTemplateDTO> templates = payload.getTemplates();
		if (CollectionUtils.isNotEmpty(entities)) {
			for (EntityDTO entityDTO : payload.getEntities()) {
				validateMandatoryFields(entityDTO, PLATFORM_ENTITY,
				        ENTITY_TEMPLATE, IDENTIFIER);
				validateMandatoryFields(entityDTO.getPlatformEntity(),
				        PLATFORM_ENTITY_TYPE);
				validateMandatoryFields(entityDTO.getEntityTemplate(),
				        ENTITY_TEMPLATE_NAME);
				validateMandatoryFields(entityDTO.getIdentifier(),
				        IDENTIFIER_KEY, IDENTIFIER_VALUE);
				DomainDTO domainDTO = new DomainDTO();
				if (entityDTO.getDomain() != null) {
					if (isBlank(entityDTO.getDomain().getDomainName())) {
						domainDTO.setDomainName(profileService
						        .getEndUserDomain());
						entityDTO.setDomain(domainDTO);
					}
				} else {

					domainDTO.setDomainName(profileService.getEndUserDomain());
					entityDTO.setDomain(domainDTO);
				}

				String type = entityDTO.getPlatformEntity()
				        .getPlatformEntityType();
				switch (type) {
					case MARKER :
						break;

					case TENANT :
						if (!entityDTO.getEntityTemplate()
						        .getEntityTemplateName()
						        .equals(TENANT_TEMPLATE)) {
							throw new GalaxyException(
							        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
							        ENTITY_TEMPLATE_NAME.getDescription());
						}
						break;

					case USER :
						if (!entityDTO.getEntityTemplate()
						        .getEntityTemplateName().equals(USER_TEMPLATE)) {
							throw new GalaxyException(
							        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
							        ENTITY_TEMPLATE_NAME.getDescription());
						}
						break;

					default:
						throw new GalaxyException(
						        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
						        PLATFORM_ENTITY_TYPE.getDescription());
				}

			}
		} else if (CollectionUtils.isNotEmpty(templates)) {
			for (EntityTemplateDTO entityTemplateDTO : payload.getTemplates()) {
				validateMandatoryFields(entityTemplateDTO,
				        PLATFORM_ENTITY_TYPE, ENTITY_TEMPLATE_NAME);
				DomainDTO domainDTO = new DomainDTO();
				if (entityTemplateDTO.getDomain() != null) {
					if (isBlank(entityTemplateDTO.getDomain().getDomainName())) {
						domainDTO.setDomainName(profileService
						        .getEndUserDomain());
						entityTemplateDTO.setDomain(domainDTO);
					}
				} else {

					domainDTO.setDomainName(profileService.getEndUserDomain());
					entityTemplateDTO.setDomain(domainDTO);
				}

				String type = entityTemplateDTO.getPlatformEntityType();
				switch (type) {
					case MARKER :
						break;

					case TENANT :
						if (!entityTemplateDTO.getEntityTemplateName().equals(
						        TENANT_TEMPLATE)) {
							throw new GalaxyException(
							        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
							        ENTITY_TEMPLATE_NAME.getDescription());
						}
						break;

					case USER :
						if (!entityTemplateDTO.getEntityTemplateName().equals(
						        USER_TEMPLATE)) {
							throw new GalaxyException(
							        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
							        ENTITY_TEMPLATE_NAME.getDescription());
						}
						break;

					default:
						throw new GalaxyException(
						        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
						        PLATFORM_ENTITY_TYPE.getDescription());
				}
			}
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.INCOMPLETE_REQUEST);
		}
		messageDTO.setStatus(Status.SUCCESS);
		return messageDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<EntityDTO>> getFilteredSubjectEntities(
	        SubjectEntitiesByTags subjectEntitiesByTags) {

		EntitiesByTagsPayload entitiesByTagsPayload = new EntitiesByTagsPayload();
		entitiesByTagsPayload = get(subjectEntitiesByTags);

		Gson gson = objectBuilderUtil.getGson();
		LOGGER.info(gson.toJson(entitiesByTagsPayload));

		Map<String, String> jwtToken = profileService.getJwtToken();
		Object object = platformClient.post(hierarchySubjectFilterEntitiesEP,
		        jwtToken, entitiesByTagsPayload, Object.class);
		if (object instanceof ErrorMessageDTO) {}
		Map<String, List<EntityDTO>> map = new HashMap<String, List<EntityDTO>>();
		if (object instanceof Map) {
			map = (Map<String, List<EntityDTO>>)object;
		}
		return map;
	}

	private EntitiesByTagsPayload get(
	        SubjectEntitiesByTags subjectEntitiesByTags) {
		EntitiesByTagsPayload entitiesByTagsPayload = new EntitiesByTagsPayload();

		if (subjectEntitiesByTags.getMatch().equals("ANY")) {
			Set<String> domainsSet = new HashSet<String>();
			Set<String> identifierKeysSet = new HashSet<String>();
			Set<String> identifierValuesSet = new HashSet<String>();
			Set<String> platformEntityTypesSet = new HashSet<String>();
			Set<String> tempalteNamesSet = new HashSet<String>();

			for (EntityDTO tagEntity : subjectEntitiesByTags.getTags()) {

				domainsSet.add(tagEntity.getDomain().getDomainName());
				identifierKeysSet.add(tagEntity.getIdentifier().getKey());
				identifierValuesSet.add(tagEntity.getIdentifier().getValue());
				platformEntityTypesSet.add(tagEntity.getPlatformEntity()
				        .getPlatformEntityType());
				tempalteNamesSet.add(tagEntity.getEntityTemplate()
				        .getEntityTemplateName());
			}

			List<String> domainList = new ArrayList<String>();
			domainList.addAll(domainsSet);
			entitiesByTagsPayload.setDomains(domainList);

			List<String> identifierKeysList = new ArrayList<String>();
			identifierKeysList.addAll(identifierKeysSet);
			entitiesByTagsPayload.setIdentifierKeys(identifierKeysList);

			List<String> identifierValuesList = new ArrayList<String>();
			identifierValuesList.addAll(identifierValuesSet);
			entitiesByTagsPayload.setIdentifierValues(identifierValuesList);

			List<String> platformEntityTypesList = new ArrayList<String>();
			platformEntityTypesList.addAll(platformEntityTypesSet);
			entitiesByTagsPayload
			        .setPlatformEntityTypes(platformEntityTypesList);

			List<String> tempalteNamesList = new ArrayList<String>();
			tempalteNamesList.addAll(tempalteNamesSet);
			entitiesByTagsPayload.setTemplateNames(tempalteNamesList);

			entitiesByTagsPayload.setMatch(subjectEntitiesByTags.getMatch());
			EntitiesByTagsFilter filter = new EntitiesByTagsFilter();
			List<String> templateNames = subjectEntitiesByTags.getFilter()
			        .getTypes();
			templateNames.remove(TAG_TEMPLATE);
			filter.setTemplateNames(templateNames);
			entitiesByTagsPayload.setFilter(filter);
		}
		if (subjectEntitiesByTags.getMatch().equals("ALL")) {
			entitiesByTagsPayload
			        .setStartNodes(subjectEntitiesByTags.getTags());
			entitiesByTagsPayload.setMatch(subjectEntitiesByTags.getMatch());
			entitiesByTagsPayload.setMatch(subjectEntitiesByTags.getMatch());

			EntitiesByTagsFilter filter = new EntitiesByTagsFilter();
			List<String> templateNames = subjectEntitiesByTags.getFilter()
			        .getTypes();
			templateNames.remove(TAG_TEMPLATE);
			filter.setTemplateNames(templateNames);
			entitiesByTagsPayload.setFilter(filter);
		}

		return entitiesByTagsPayload;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HierarchyTagCountDTO> getTaggedEntityCount(
	        HierarchyTagSearchDTO hierarchyTagSearchDTO) {

		// Get the token to be used to invoke API
		Map<String, String> jwtToken = profileService.getJwtToken();

		// Invoke ESB endpoint
		List<HierarchyTagCountDTO> entityCount = platformClient.post(
		        attachedEntityCountEP, jwtToken, hierarchyTagSearchDTO,
		        List.class, HierarchyTagCountDTO.class);
		return entityCount;
	}

}
