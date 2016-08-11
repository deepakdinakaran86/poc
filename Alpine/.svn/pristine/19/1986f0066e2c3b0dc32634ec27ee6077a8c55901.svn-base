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
import static com.pcs.alpine.commons.validation.ValidationUtils.validateResult;
import static com.pcs.alpine.constants.TagConstant.MARKER;
import static com.pcs.alpine.constants.TagConstant.TAG_TEMPLATE;
import static com.pcs.alpine.enums.TagDataFields.IDENTIFIER;
import static com.pcs.alpine.enums.TagDataFields.STATUS;
import static com.pcs.alpine.enums.TagDataFields.TAG_TYPE;
import static com.pcs.alpine.enums.TagDataFields.TAG_TYPE_NAME;
import static com.pcs.alpine.enums.TagDataFields.TAG_TYPE_VALUES;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pcs.alpine.commons.dto.StatusDTO;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.dto.ValidationJsonStringDTO;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.util.ObjectBuilderUtil;
import com.pcs.alpine.dto.TagTypeTemplate;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.enums.TagDataFields;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.rest.exception.GalaxyRestException;
import com.pcs.alpine.service.TagTypeTemplateService;

/**
 * Service Implementation for Asset Related APIs
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date March 2016
 * @since Avocado-1.0.0
 */
@Service
public class TagTypeTemplateServiceImpl implements TagTypeTemplateService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TagTypeTemplateServiceImpl.class);

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformESBClient;

	@Autowired
	private SubscriptionProfileService profileService;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Value("${platform.client.entitytemplate}")
	private String templatePE;

	@Value("${platform.esb.marker}")
	private String markerPE;

	@Value("${platform.client.entitytemplate.list.byParentTemplate}")
	private String templatesByParentTemplatePE;

	@Value("${platform.client.entitytemplate.find}")
	private String templateFindPE;

	final static String PLATFORM_MARKER_TEMPLATE = "platformMarkerTemplate";
	public static final String CHECK = "check";

	@Override
	public StatusMessageDTO createTagType(TagTypeTemplate tagTypeTemplate) {
		validateCreateTagTypePayload(tagTypeTemplate);
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		String domainName = null;

		DomainDTO domain = new DomainDTO();
		// If domain is blank, get the domain from logged in user
		if (isBlank(tagTypeTemplate.getDomainName())) {
			domainName = profileService.getEndUserDomain();
			domain.setDomainName(domainName);
		} else {
			domainName = tagTypeTemplate.getDomainName();
			domain.setDomainName(domainName);
		}

		String entityTemplateName = tagTypeTemplate.getTagType();

		StatusDTO status = new StatusDTO();
		status.setStatusName(tagTypeTemplate.getStatus());

		List<String> tagTypeValues = tagTypeTemplate.getTagTypeValues();
		Map<String, String> fieldValidation = new HashMap<String, String>();

		// fetch TAG template
		EntityTemplateDTO tagTemplateDTO = new EntityTemplateDTO();
		tagTemplateDTO.setDomain(domain);
		tagTemplateDTO.setEntityTemplateName(TAG_TEMPLATE); 
		tagTemplateDTO.setPlatformEntityType(MARKER);

		Map<String, String> jwtToken = profileService.getJwtToken();

		EntityTemplateDTO returnedTagTemplateDto = platformESBClient.post(
				templateFindPE, jwtToken, tagTemplateDTO,
				EntityTemplateDTO.class);
		Map<String, String> fieldValidations = returnedTagTemplateDto
				.getFieldValidation();
		String nameValidations = fieldValidations.get(TagDataFields.NAME
				.getVariableName());
		String identifierValidations = fieldValidations
				.get(TagDataFields.IDENTIFIER.getVariableName());

		Gson gson = objectBuilderUtil.getGson();

		// tagTypeKeys must contains name key
		if (!tagTypeValues.contains(TagDataFields.NAME.getVariableName())) {
			throw new GalaxyException(GalaxyCommonErrorCodes.CUSTOM_ERROR,
					"Tag Type cannot be created.");
		}
		tagTypeValues.remove(TagDataFields.NAME.getVariableName());
		for (String tagTypeValue : tagTypeValues) {
			ValidationJsonStringDTO dto = new ValidationJsonStringDTO();
			List<String> client = new ArrayList<String>();
			List<String> server = new ArrayList<String>();
			client.add("");
			server.add("");
			dto.setClient(client);
			dto.setServer(server);
			dto.setShowOnGrid(true);
			dto.setShowOnTree(false);
			fieldValidation.put(tagTypeValue, gson.toJson(dto));
		}
		fieldValidation.put(TagDataFields.NAME.getVariableName(),
				nameValidations);
		
		// For adding identifier to fieldValidations
		fieldValidation.put(TagDataFields.IDENTIFIER.getVariableName(),
				identifierValidations);

		entityTemplateDTO.setFieldValidation(fieldValidation);
		entityTemplateDTO.setDomain(domain);
		entityTemplateDTO.setEntityTemplateName(entityTemplateName);
		entityTemplateDTO.setStatus(status);
		entityTemplateDTO
				.setPlatformEntityTemplateName(PLATFORM_MARKER_TEMPLATE);
		entityTemplateDTO.setPlatformEntityType(MARKER);
		entityTemplateDTO.setParentTemplate(TAG_TEMPLATE);
		entityTemplateDTO.setHtmlPage(CHECK);
		entityTemplateDTO.setIdentifierField(IDENTIFIER.getFieldName());
		entityTemplateDTO.setIsModifiable(false);
		entityTemplateDTO.setIsSharable(true);

		LOGGER.debug(gson.toJson(entityTemplateDTO));

		StatusMessageDTO response = new StatusMessageDTO(Status.FAILURE);
		try {
			EntityTemplateDTO returnedDto = platformESBClient.post(templatePE,
					jwtToken, entityTemplateDTO, EntityTemplateDTO.class);
			if (returnedDto != null) {
				response.setStatus(Status.SUCCESS);
			}
		} catch (GalaxyRestException gre) {
			if (gre.getMessage().contains(
					TagDataFields.ENTITY_TEMPLATE_NAME.getDescription()
							+ GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST
									.getMessage())) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
						TagDataFields.TAG_TYPE.getDescription());
			}
		}
		return response;
	}

	private void validateCreateTagTypePayload(TagTypeTemplate tagTypeTemplate) {
		validateMandatoryFields(tagTypeTemplate, TAG_TYPE, TAG_TYPE_VALUES,
				STATUS);
		validateCollection(TAG_TYPE_VALUES, tagTypeTemplate.getTagTypeValues());
	}

	@Override
	public List<EntityTemplateDTO> getAllTagType(String domainName) {
		if (isBlank(domainName)) {
			domainName = profileService.getEndUserDomain();
		}

		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName(Status.ACTIVE.toString());

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setDomain(domainDTO);
		entityTemplateDTO.setPlatformEntityType(MARKER);
		entityTemplateDTO.setStatus(statusDTO);
		entityTemplateDTO.setParentTemplate(TAG_TEMPLATE);
		entityTemplateDTO
				.setPlatformEntityTemplateName(PLATFORM_MARKER_TEMPLATE);

		Map<String, String> jwtToken = profileService.getJwtToken();

		Gson gson = objectBuilderUtil.getGson();
		LOGGER.info(gson.toJson(entityTemplateDTO));
		@SuppressWarnings("unchecked")
		List<EntityTemplateDTO> templates = platformESBClient.post(
				templatesByParentTemplatePE, jwtToken, entityTemplateDTO,
				List.class, EntityTemplateDTO.class);
		validateResult(templates);
		return templates;
	}

	@Override
	public TagTypeTemplate getTagType(String tagTypeName, String domainName) {
		if (isBlank(tagTypeName)) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
					TAG_TYPE_NAME.getDescription());
		}
		if (isBlank(domainName)) {
			domainName = profileService.getEndUserDomain();
		}

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName(Status.ACTIVE.name()); // TODO check capitals or
														// small leter

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setDomain(domainDTO);
		entityTemplateDTO.setPlatformEntityType(MARKER);
		entityTemplateDTO.setStatus(statusDTO);
		entityTemplateDTO.setEntityTemplateName(tagTypeName);

		String targetUrl = templateFindPE;
		Map<String, String> jwtToken = profileService.getJwtToken();
		entityTemplateDTO = platformESBClient.post(targetUrl, jwtToken,
				entityTemplateDTO, EntityTemplateDTO.class);

		Map<String, String> keys = entityTemplateDTO.getFieldValidation();
		keys.remove(IDENTIFIER.getFieldName());

		TagTypeTemplate tagTypeTemplate = null;
		tagTypeTemplate = new TagTypeTemplate();
		tagTypeTemplate.setTagType(entityTemplateDTO.getEntityTemplateName());
		List<String> tagTypeValues = new ArrayList<String>();
		tagTypeValues.addAll(keys.keySet());
		tagTypeTemplate.setTagTypeValues(tagTypeValues);
		return tagTypeTemplate;
	}

	@Override
	public EntityDTO createTagType(EntityDTO tagTypeEntity) {

		EntityDTO assetTypeCreatedEntity = null;
		Map<String, String> jwtToken = profileService.getJwtToken();
		assetTypeCreatedEntity = platformESBClient.post(markerPE, jwtToken,
				tagTypeEntity, EntityDTO.class);
		return assetTypeCreatedEntity;
	}
}
