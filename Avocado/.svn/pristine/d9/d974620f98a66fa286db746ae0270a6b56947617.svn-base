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

import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getJwtToken;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateResult;
import static com.pcs.avocado.constans.WebConstants.ASSET_TYPE_TEMPLATE;
import static com.pcs.avocado.constans.WebConstants.CHECK;
import static com.pcs.avocado.constans.WebConstants.MARKER;
import static com.pcs.avocado.constans.WebConstants.PLATFORM_MARKER_TEMPLATE;
import static com.pcs.avocado.enums.AssetDataFields.STATUS;
import static org.apache.commons.lang.StringUtils.isBlank;

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

import com.google.gson.Gson;
import com.pcs.avocado.commons.dto.AssetTypeTemplateDTO;
import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.EntityTemplateDTO;
import com.pcs.avocado.commons.dto.PlatformEntityDTO;
import com.pcs.avocado.commons.dto.StatusDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.dto.SubscriptionContextHolder;
import com.pcs.avocado.commons.dto.ValidationJsonStringDTO;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.commons.service.SubscriptionProfileService;
import com.pcs.avocado.commons.util.ObjectBuilderUtil;
import com.pcs.avocado.commons.validation.ValidationUtils;
import com.pcs.avocado.constans.WebConstants;
import com.pcs.avocado.enums.AssetDataFields;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.avocado.rest.exception.GalaxyRestException;
import com.pcs.avocado.services.AssetTypeTemplateService;

/**
 * Service Implementation for Asset Related APIs
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date March 2016
 * @since Avocado-1.0.0
 */
@Service
public class AssetTypeTemplateServiceImpl implements AssetTypeTemplateService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AssetTypeTemplateService.class);

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformESBClient;

	@Value("${platform.client.entitytemplate}")
	private String entityTemplatePlatformEndpoint;

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Value("${platform.esb.marker}")
	private String markerPlatformEndpoint;

	@Value("${platform.client.entitytemplate.list.byparenttemplate}")
	private String entityTemplateListAllPlatformEndpoint;

	@Value("${platform.client.entitytemplate.find}")
	private String entityTemplateFindPlatformEndpoint;

	@Value("${platform.esb.hierarchy}")
	private String hierarchyPlatformEndpoint;

	@Override
	public StatusMessageDTO createAssetType(String parentType,
	        AssetTypeTemplateDTO assetTypeTemplateDTO) {
		// {"assetType":"Vehicle","assetTypeValues":["namefield"],"domainName":"fms.galaxy","status":"ACTIVE"}

		validateCreateAssetTypePayload(assetTypeTemplateDTO);

		String domainName = null;
		// If domain is blank, get the domain from logged in user
		if (StringUtils.isBlank(assetTypeTemplateDTO.getDomainName())) {
			domainName = subscriptionProfileService.getEndUserDomain();
		} else domainName = assetTypeTemplateDTO.getDomainName();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);

		StatusDTO status = new StatusDTO();
		status.setStatusName(assetTypeTemplateDTO.getStatus());

		if (StringUtils.isBlank(parentType)) {
			parentType = ASSET_TYPE_TEMPLATE;
		}

		EntityTemplateDTO returnedTemplate = fetchTemplate(parentType, domain);
		Map<String, String> returnedfieldValidations = returnedTemplate
		        .getFieldValidation();

		List<String> assetTypeFieldValues = assetTypeTemplateDTO
		        .getAssetTypeValues();

		Gson gson = objectBuilderUtil.getGson();

		Map<String, String> fieldValidations = new HashMap<String, String>();
		// copy all the key from existing template
		fieldValidations.putAll(returnedfieldValidations);

		// check if any foreign key specified, add in map
		if (CollectionUtils.isNotEmpty(assetTypeFieldValues)) {
			for (String assetTypeValue : assetTypeFieldValues) {
				ValidationJsonStringDTO dto = new ValidationJsonStringDTO();

				// map does not contains foreign, add it
				if (!fieldValidations.containsKey(assetTypeValue)) {
					List<String> client = new ArrayList<String>();
					List<String> server = new ArrayList<String>();
					client.add("");
					server.add("");
					dto.setClient(client);
					dto.setServer(server);
					dto.setShowOnGrid(true);
					dto.setShowOnTree(false);
					fieldValidations.put(assetTypeValue, gson.toJson(dto));
				}
			}
		}

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setFieldValidation(fieldValidations);
		entityTemplateDTO.setDomain(domain);
		entityTemplateDTO.setEntityTemplateName(assetTypeTemplateDTO
		        .getAssetType());
		entityTemplateDTO.setStatus(status);
		entityTemplateDTO
		        .setPlatformEntityTemplateName(PLATFORM_MARKER_TEMPLATE);
		entityTemplateDTO.setPlatformEntityType(MARKER);
		entityTemplateDTO.setParentTemplate(parentType);
		entityTemplateDTO.setHtmlPage(CHECK);
		entityTemplateDTO.setIdentifierField(returnedTemplate
		        .getIdentifierField());
		entityTemplateDTO.setIsModifiable(returnedTemplate.getIsModifiable());
		entityTemplateDTO.setIsSharable(returnedTemplate.getIsSharable());

		LOGGER.debug(gson.toJson(entityTemplateDTO));

		StatusMessageDTO response = new StatusMessageDTO(Status.FAILURE);

		try {
			EntityTemplateDTO template = platformESBClient.post(
			        entityTemplatePlatformEndpoint, getJwtToken(),
			        entityTemplateDTO, EntityTemplateDTO.class);
			if (template != null) {
				response.setStatus(Status.SUCCESS);
			}

		} catch (GalaxyRestException gre) {
			if (gre.getMessage().contains(
			        AssetDataFields.ENTITY_TEMPLATE_NAME.getDescription()
			                + GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST
			                        .getMessage())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
				        AssetDataFields.CATEGORY_NAME.getDescription());
			}
		}
		return response;
	}

	private EntityTemplateDTO fetchTemplate(String type, DomainDTO domain) {
		// fetch AssetType template
		EntityTemplateDTO assetTypeTemplateDTO = new EntityTemplateDTO();
		assetTypeTemplateDTO.setDomain(domain);
		assetTypeTemplateDTO.setEntityTemplateName(type);
		assetTypeTemplateDTO.setPlatformEntityType(MARKER);

		Map<String, String> jwtToken = subscriptionProfileService.getJwtToken();

		EntityTemplateDTO returnedTemplateDto = null;
		try {
			returnedTemplateDto = platformESBClient.post(
			        entityTemplateFindPlatformEndpoint, jwtToken,
			        assetTypeTemplateDTO, EntityTemplateDTO.class);
		} catch (GalaxyRestException gre) {
			// catch template doest exists
		}
		return returnedTemplateDto;
	}

	private void validateCreateAssetTypePayload(
	        AssetTypeTemplateDTO assetTypeTemplateDTO) {
		ValidationUtils.validateMandatoryFields(assetTypeTemplateDTO,
		        AssetDataFields.ASSET_TYPE, STATUS);

	}

	@Override
	public List<AssetTypeTemplateDTO> getAllAssetType(String domainName,
	        String parentType) {
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}

		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName(Status.ACTIVE.toString());

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setDomain(domainDTO);
		entityTemplateDTO.setPlatformEntityType(WebConstants.MARKER);

		if (StringUtils.isNotBlank(parentType)) {
			entityTemplateDTO.setParentTemplate(parentType);
		} else {
			entityTemplateDTO.setParentTemplate(ASSET_TYPE_TEMPLATE);
		}

		entityTemplateDTO.setStatus(statusDTO);

		String targetUrl = entityTemplateListAllPlatformEndpoint;

		// ArrayList<EntityTemplateDTO> entityTemplateDTOs = new
		// ArrayList<EntityTemplateDTO>();

		@SuppressWarnings("unchecked") List<EntityTemplateDTO> entityTemplateDTOs = platformESBClient
		        .post(targetUrl, getJwtToken(), entityTemplateDTO, List.class,
		                EntityTemplateDTO.class);
		List<AssetTypeTemplateDTO> assetTypeDTOs = new ArrayList<AssetTypeTemplateDTO>();
		if (CollectionUtils.isNotEmpty(entityTemplateDTOs)) {

			for (EntityTemplateDTO entityTemplateDTO2 : entityTemplateDTOs) {
				AssetTypeTemplateDTO assetTypeDTO = new AssetTypeTemplateDTO();

				assetTypeDTO.setAssetType(entityTemplateDTO2
				        .getEntityTemplateName());
				assetTypeDTOs.add(assetTypeDTO);

			}
		}
		validateResult(assetTypeDTOs);
		return assetTypeDTOs;
	}

	@Override
	public AssetTypeTemplateDTO getAssetType(String assetTypeName,
	        String domainName, String parentType) {
		if (isBlank(assetTypeName)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        WebConstants.ASSET_TYPE_NAME);
		}
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName(WebConstants.ACTIVE);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setDomain(domainDTO);
		entityTemplateDTO.setPlatformEntityType(WebConstants.MARKER);
		entityTemplateDTO.setStatus(statusDTO);
		entityTemplateDTO.setEntityTemplateName(assetTypeName);

		entityTemplateDTO
		        .setPlatformEntityTemplateName(PLATFORM_MARKER_TEMPLATE);

		if (StringUtils.isNotBlank(parentType)) {
			entityTemplateDTO.setParentTemplate(parentType);
		} else {
			entityTemplateDTO.setParentTemplate(ASSET_TYPE_TEMPLATE);
		}

		String targetUrl = entityTemplateFindPlatformEndpoint;

		Gson gson = objectBuilderUtil.getGson();
		LOGGER.debug(gson.toJson(entityTemplateDTO));

		EntityTemplateDTO template = platformESBClient.post(targetUrl,
		        getJwtToken(), entityTemplateDTO, EntityTemplateDTO.class);

		AssetTypeTemplateDTO assetTypeTemplateDTO = null;
		if (null != template) {
			assetTypeTemplateDTO = new AssetTypeTemplateDTO();
			assetTypeTemplateDTO.setAssetType(template.getEntityTemplateName());

			List<String> assetTypeValues = new ArrayList<String>();
			Map<String, String> assetTypeKeys = template.getFieldValidation();
			for (Map.Entry<String, String> mapEntry : assetTypeKeys.entrySet()) {
				if (!mapEntry.getKey().equalsIgnoreCase("identifier")) {
					assetTypeValues.add(mapEntry.getKey());
				}
			}
			assetTypeTemplateDTO.setAssetTypeValues(assetTypeValues);
		}
		validateResult(assetTypeTemplateDTO);
		return assetTypeTemplateDTO;
	}

	@Override
	public EntityDTO createAssetType(EntityDTO assetTypeEntity) {

		EntityDTO assetTypeCreatedEntity = null;
		assetTypeCreatedEntity = platformESBClient.post(markerPlatformEndpoint,
		        SubscriptionContextHolder.getJwtToken(), assetTypeEntity,
		        EntityDTO.class);
		return assetTypeCreatedEntity;
	}

	@Override
	public StatusMessageDTO updateAssetType(EntityDTO assetTypeEntity) {

		StatusMessageDTO statusMessageDTO = null;
		statusMessageDTO = platformESBClient.put(markerPlatformEndpoint,
		        getJwtToken(), assetTypeEntity, StatusMessageDTO.class);

		EntityDTO nodeEntityDTO = null;
		if (null != statusMessageDTO
		        && statusMessageDTO.getStatus().equals(Status.SUCCESS)) {

			try {
				PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
				platformEntityDTO.setPlatformEntityType(MARKER);
				assetTypeEntity.setPlatformEntity(platformEntityDTO);
				nodeEntityDTO = platformESBClient.put(
				        hierarchyPlatformEndpoint, getJwtToken(),
				        assetTypeEntity, EntityDTO.class);
			} catch (GalaxyRestException gre) {
				if (gre.getMessage().equals(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
					LOGGER.debug("Asset Type Node cannot be updated");
					statusMessageDTO.setStatus(Status.FAILURE);
				}
			}
			if (null == nodeEntityDTO) {
				statusMessageDTO.setStatus(Status.FAILURE);
			}
		}
		return statusMessageDTO;
	}

}
