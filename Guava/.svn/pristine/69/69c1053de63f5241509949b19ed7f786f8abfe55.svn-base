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
package com.pcs.guava.serviceImpl;

import static com.pcs.guava.commons.validation.ValidationUtils.validateResult;
import static com.pcs.guava.constants.PoiTypeResourceConstants.CHECK;
import static com.pcs.guava.constants.PoiTypeResourceConstants.IDENTIFIER;
import static com.pcs.guava.constants.PoiTypeResourceConstants.MARKER;
import static com.pcs.guava.constants.PoiTypeResourceConstants.PLATFORM_MARKER_TEMPLATE;
import static com.pcs.guava.constants.PoiTypeResourceConstants.POI_TYPE_TEMPLATE;
import static com.pcs.guava.enums.PoiTypeDataFields.PARENT_TEMPLATE;
import static com.pcs.guava.enums.PoiTypeDataFields.POI_TYPE;
import static com.pcs.guava.enums.Status.ACTIVE;
import static com.pcs.guava.enums.Status.INACTIVE;
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
import com.pcs.guava.commons.dto.DomainDTO;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.EntityTemplateDTO;
import com.pcs.guava.commons.dto.StatusDTO;
import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.commons.dto.ValidationJsonStringDTO;
import com.pcs.guava.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.guava.commons.exception.GalaxyException;
import com.pcs.guava.commons.service.SubscriptionProfileService;
import com.pcs.guava.commons.util.ObjectBuilderUtil;
import com.pcs.guava.commons.validation.ValidationUtils;
import com.pcs.guava.dto.PoiTypeDTO;
import com.pcs.guava.enums.Status;
import com.pcs.guava.rest.client.BaseClient;
import com.pcs.guava.rest.exception.GalaxyRestException;
import com.pcs.guava.services.PoiTypeService;

/**
 * Service implementation for POI Type
 * 
 * @author Greeshma (PCSEG323)
 * @date April 2016
 * @since Guava-1.0.0
 */

@Service
public class PoiTypeServiceImpl implements PoiTypeService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PoiTypeServiceImpl.class);

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformESBClient;

	@Value("${platform.client.entitytemplate}")
	private String entityTemplatePlatformEndpoint;

	@Value("${platform.client.entitytemplate.list.byparenttemplate}")
	private String entityTemplateListPlatformEndpoint;

	@Value("${platform.client.entitytemplate.find}")
	private String entityTemplateFindPlatformEndpoint;

	@Value("${platform.esb.marker}")
	private String markerPlatformEndpoint;

	@Value("${platform.esb.hierarchy.update.node}")
	private String updateNodeHierarchyPlatformEndpoint;

	@Override
	public StatusMessageDTO createPoiType(PoiTypeDTO poiType) {
		LOGGER.debug("<-- Enter createPoiType -->");
		validatePoiTypeFields(poiType);
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(poiType.getDomainName());

		StatusDTO status = new StatusDTO();
		status.setStatusName(poiType.getStatus());

		List<String> poiTypeValues = poiType.getPoiTypeValues();
		Map<String, String> fieldValidation = new HashMap<String, String>();
		Gson gson = objectBuilderUtil.getGson();
		if (CollectionUtils.isNotEmpty(poiTypeValues)) {
			// adding all the poitypes names to the map
			for (String poiTypeValue : poiTypeValues) {
				fieldValidation.put(poiTypeValue,
						gson.toJson(createfieldValueMap(true, false)));
			}
		}
		// For adding identifier to fieldValidations
		fieldValidation.put(IDENTIFIER,
				gson.toJson(createfieldValueMap(false, false)));

		// Get parent Template details
//		EntityTemplateDTO parentTemplateDTO = new EntityTemplateDTO();
//		try {
//			StatusDTO parentTemplateStatus = new StatusDTO();
//			parentTemplateStatus.setStatusName(ACTIVE.name());
//			parentTemplateDTO.setDomain(domain);
//			parentTemplateDTO.setPlatformEntityType(MARKER);
//			parentTemplateDTO.setStatus(parentTemplateStatus);
//			parentTemplateDTO.setEntityTemplateName(POI_TYPE_TEMPLATE);
//
//			String targetUrl = entityTemplateFindPlatformEndpoint;
//
//			parentTemplateDTO = platformESBClient.post(targetUrl,
//					subscriptionProfileService.getJwtToken(),
//					parentTemplateDTO, EntityTemplateDTO.class);
//		} catch (GalaxyRestException gre) {
//			if (gre.getMessage().equals(
//					GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
//				LOGGER.debug("PoiType Parent Template doesnot exist."
//						+ POI_TYPE_TEMPLATE);
//				throw new GalaxyException(
//						GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
//						PARENT_TEMPLATE.getDescription());
//			}
//		}

		entityTemplateDTO.setFieldValidation(fieldValidation);
		entityTemplateDTO.setDomain(domain);
		entityTemplateDTO.setEntityTemplateName(poiType.getPoiType());
		entityTemplateDTO.setStatus(status);
		entityTemplateDTO
				.setPlatformEntityTemplateName(PLATFORM_MARKER_TEMPLATE);
		entityTemplateDTO.setPlatformEntityType(MARKER);
		entityTemplateDTO.setHtmlPage(CHECK);
		entityTemplateDTO.setIdentifierField(IDENTIFIER);
		entityTemplateDTO.setIsModifiable(false);
		entityTemplateDTO.setIsSharable(true);
		entityTemplateDTO.setParentTemplate(POI_TYPE_TEMPLATE);

		StatusMessageDTO response = platformESBClient.post(
				entityTemplatePlatformEndpoint,
				subscriptionProfileService.getJwtToken(), entityTemplateDTO,
				StatusMessageDTO.class);
		response.setStatus(Status.SUCCESS);
		return response;
	}

	private ValidationJsonStringDTO createfieldValueMap(boolean isShowOnGrid,
			boolean isShowOnTree) {
		ValidationJsonStringDTO dto = new ValidationJsonStringDTO();
		List<String> client = new ArrayList<String>();
		List<String> server = new ArrayList<String>();
		client.add("");
		server.add("");
		dto.setClient(client);
		dto.setServer(server);
		dto.setShowOnGrid(isShowOnGrid);
		dto.setShowOnTree(isShowOnTree);
		return dto;

	}

	private void validatePoiTypeFields(PoiTypeDTO poiType) {
		// validate the mandatory fields
		ValidationUtils.validateMandatoryFields(poiType, POI_TYPE);

//		if (StringUtils.isBlank(poiType.getDomainName())) {
//			// Get logged in user's domain
//			poiType.setDomainName(subscriptionProfileService.getSubscription()
//					.getEndUserDomain());
//		}
		if (poiType.getStatus() == null) {
			poiType.setStatus(ACTIVE.name());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PoiTypeDTO> getAllPoiType(String domainName) {
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}

		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName(Status.ACTIVE.name());
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setDomain(domainDTO);
		entityTemplateDTO.setPlatformEntityType(MARKER);
		entityTemplateDTO.setParentTemplate(POI_TYPE_TEMPLATE);
		entityTemplateDTO.setStatus(statusDTO);

		List<EntityTemplateDTO> entityTemplateDTOs = platformESBClient.post(
				entityTemplateListPlatformEndpoint,
				subscriptionProfileService.getJwtToken(), entityTemplateDTO,
				List.class, EntityTemplateDTO.class);
		List<PoiTypeDTO> poiTypeDTOs = new ArrayList<PoiTypeDTO>();
		if (CollectionUtils.isNotEmpty(entityTemplateDTOs)) {

			for (EntityTemplateDTO entityTemplate : entityTemplateDTOs) {
				PoiTypeDTO poiTypeDTO = new PoiTypeDTO();

				poiTypeDTO.setPoiType(entityTemplate.getEntityTemplateName());
				poiTypeDTOs.add(poiTypeDTO);

			}
		}
		validateResult(poiTypeDTOs);
		return poiTypeDTOs;
	}

	@Override
	public PoiTypeDTO getPoiType(String poiType, String domainName) {
		ValidationUtils.validateMandatoryField(POI_TYPE, poiType);

		DomainDTO domainDTO = new DomainDTO();
		if (isBlank(domainName)) {
			domainName = subscriptionProfileService.getEndUserDomain();
		}
		domainDTO.setDomainName(domainName);
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusName(Status.ACTIVE.name());

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setDomain(domainDTO);
		entityTemplateDTO.setPlatformEntityType(MARKER);
		entityTemplateDTO.setStatus(statusDTO);
		entityTemplateDTO.setEntityTemplateName(poiType);
		entityTemplateDTO.setParentTemplate(POI_TYPE_TEMPLATE);

		String targetUrl = entityTemplateFindPlatformEndpoint;

		entityTemplateDTO = platformESBClient.post(targetUrl,
				subscriptionProfileService.getJwtToken(), entityTemplateDTO,
				EntityTemplateDTO.class);

		PoiTypeDTO poiTypeDTO = null;
		if (StringUtils.equals(entityTemplateDTO.getParentTemplate(),
				POI_TYPE_TEMPLATE)) {
			poiTypeDTO = new PoiTypeDTO();
			poiTypeDTO.setPoiType(entityTemplateDTO.getEntityTemplateName());

			List<String> poiTypeValues = new ArrayList<String>();
			Map<String, String> poiTypeKeys = entityTemplateDTO
					.getFieldValidation();
			for (Map.Entry<String, String> mapEntry : poiTypeKeys.entrySet()) {
				if (!mapEntry.getKey().equalsIgnoreCase(IDENTIFIER)) {
					poiTypeValues.add(mapEntry.getKey());
				}
			}
			poiTypeDTO.setPoiTypeValues(poiTypeValues);
		}
//		validateResult(poiTypeDTO);
		return poiTypeDTO;
	}

	@Override
	public EntityDTO createPoiTypeEntity(EntityDTO inPoiTypeEntity) {
		return platformESBClient.post(markerPlatformEndpoint,
				subscriptionProfileService.getJwtToken(), inPoiTypeEntity,
				EntityDTO.class);
	}

	@Override
	public StatusMessageDTO updatePoiType(EntityDTO inPoiTypeEntity) {

		EntityDTO poiTypeNodeUpdate = platformESBClient.put(
				updateNodeHierarchyPlatformEndpoint,
				subscriptionProfileService.getJwtToken(), inPoiTypeEntity,
				EntityDTO.class);

		return platformESBClient.put(markerPlatformEndpoint,
				subscriptionProfileService.getJwtToken(), inPoiTypeEntity,
				StatusMessageDTO.class);
	}

}
