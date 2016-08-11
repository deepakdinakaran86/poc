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

import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.Status.ACTIVE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.model.PlatformEntityTemplate;
import com.pcs.alpine.serviceImpl.repository.PlatformEntityTemplateRepository;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;

/**
 * GlobalEntityTemplateServiceImpl
 * 
 * @description Implementation Class for Global Entity Template Services
 * @author Daniela (PCSEG191)
 * @date 30 Nov 2014
 * @since galaxy-1.0.0
 */
@Service
public class PlatformEntityTemplateServiceImpl
        implements
            PlatformEntityTemplateService {

	@Autowired
	private PlatformEntityTemplateRepository platformEntityTemplateRepository;

	@Autowired
	private StatusService statusService;

	@Override
	public PlatformEntityTemplateDTO getPlatformEntityTemplate(
	        String platformEntityType) {
		// Validate the platform type
		ValidationUtils.validateMandatoryField(PLATFORM_ENTITY_TYPE,
		        platformEntityType);

		// Fetch all templates having the specified type
		List<PlatformEntityTemplate> platformTemplates = platformEntityTemplateRepository
		        .getPlatformEntityTemplate(platformEntityType);
		ValidationUtils.validateResult(platformTemplates);

		// Find the integer value corresponding to ACTIVE
		Integer activeStatus = statusService.getStatus(ACTIVE.name());

		PlatformEntityTemplate entityTemplate = null;

		// find the 1st occuring template having status as ACTIVE
		for (PlatformEntityTemplate platformEntityTemplate : platformTemplates) {
			if (platformEntityTemplate.getStatusKey().toString()
			        .equalsIgnoreCase(activeStatus.toString())) {
				entityTemplate = platformEntityTemplate;
				break;
			}
		}
		ValidationUtils.validateResult(entityTemplate);
		return createPlatformEntityTemplateDTO(entityTemplate);
	}

	@Override
	public PlatformEntityTemplateDTO getPlatformEntityTemplate(
	        String globalEntityType, String templateName) {
		// Validate the platform type
		ValidationUtils.validateMandatoryField(PLATFORM_ENTITY_TYPE,
		        globalEntityType);
		ValidationUtils.validateMandatoryField(PLATFORM_ENTITY_TEMPLATE_NAME,
		        templateName);

		Integer statusKey = statusService.getStatus(ACTIVE.name());

		PlatformEntityTemplate globalTemplate = platformEntityTemplateRepository
		        .getGlobalEntityTemplate(globalEntityType, templateName,
		                statusKey);
		if (globalTemplate == null) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return createPlatformEntityTemplateDTO(globalTemplate);
	}

	private PlatformEntityTemplateDTO createPlatformEntityTemplateDTO(
	        PlatformEntityTemplate platformEntityTemplate) {
		PlatformEntityTemplateDTO platformEntityTemplateDTO = new PlatformEntityTemplateDTO();
		platformEntityTemplateDTO.setFieldValidation(platformEntityTemplate
		        .getFieldValidation());
		platformEntityTemplateDTO
		        .setPlatformEntityTemplateName(platformEntityTemplate

		        .getPlatformEntityTemplateName());
		platformEntityTemplateDTO.setPlatformEntityType(platformEntityTemplate
		        .getPlatformEntityType());
		byte[] bytes = new byte[platformEntityTemplate.getHtmlPage()
		        .remaining()];
		platformEntityTemplate.getHtmlPage().duplicate().get(bytes);
		platformEntityTemplateDTO.setHtmlPage(new String(bytes));
		platformEntityTemplateDTO.setIdentifierField(platformEntityTemplate
		        .getIdentifierField());
		platformEntityTemplateDTO.setStatusKey(platformEntityTemplate
		        .getStatusKey());
		return platformEntityTemplateDTO;
	}

	@Override
	public Boolean isValidFieldName(String globalEntity, String fieldName,
	        String templateName) {
		Integer statusKey = statusService.getStatus(ACTIVE.name());
		return platformEntityTemplateRepository.isValidField(globalEntity,
		        statusKey, fieldName, templateName);
	}

	@Deprecated
	@Override
	public List<PlatformEntityTemplateDTO> getPlatformEntityTemplates(
	        String globalEntityType) {
		Integer statusKey = statusService.getStatus(ACTIVE.name());

		List<PlatformEntityTemplate> globalTemplates = platformEntityTemplateRepository
		        .getGlobalEntityTemplates(globalEntityType, statusKey);
		ValidationUtils.validateResult(globalTemplates);

		List<PlatformEntityTemplateDTO> globalEntityTemplateDTOs = new ArrayList<PlatformEntityTemplateDTO>();
		for (PlatformEntityTemplate globalEntityTemplate : globalTemplates) {
			globalEntityTemplateDTOs
			        .add(createPlatformEntityTemplateDTO(globalEntityTemplate));
		}
		return globalEntityTemplateDTOs;
	}

	// @Override
	// public String getPlatformEntityType(String globalEntityTemplateName) {
	//
	// // Validate the input
	// ValidationUtils.validateMandatoryField(
	// EMDataFields.PLATFORM_ENTITY_TEMPLATE_NAME,
	// globalEntityTemplateName);
	// Integer statusKey = statusService.getStatus(DELETED.name());
	// String entityType = platformEntityTemplateRepository
	// .getGlobalEntityTemplateType(globalEntityTemplateName,
	// statusKey);
	// if (isBlank(entityType)) {
	// throw new GalaxyException(
	// GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
	// EMDataFields.ENTITY_TEMPLATE_NAME.getDescription());
	// }
	// return entityType;
	// }

}
