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
package com.pcs.alpine.repo.utils;

import static com.pcs.alpine.services.enums.EMDataFields.TENANT;
import static com.pcs.alpine.services.enums.EMDataFields.USER;
import static org.apache.commons.lang.StringUtils.isBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;

/**
 * 
 * @description This class is responsible for the CoreEntityUtil
 * 
 * @author Daniela (PCSEG191)
 * @date 20 Apr 2016
 * @since galaxy-1.0.0
 */
@Component
public class CoreEntityUtil {

	@Autowired
	PlatformEntityTemplateService globalEntityTemplateService;

	@Autowired
	EntityTemplateService entityTemplateService;

	/**
	 * @Description Responsible to get global entity entityDTO
	 * 
	 * @param templateName
	 * @param domain
	 * @return GlobalEntityDTO
	 */
	public PlatformEntityDTO getGlobalEntityType(String templateName,
	        String domain) {
		// fetch from entity template
		Boolean isParentDomain = false;
		String entityType = null;
		try {
			entityType = entityTemplateService.getEntityType(domain,
			        templateName, isParentDomain);
		} catch (GalaxyException e) {
			if (!e.getErrorMessageDTO()
			        .getErrorCode()
			        .equalsIgnoreCase(
			                GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode()
			                        .toString())) {
				throw e;
			}
		}
		// fetch from entity template
		if (isBlank(entityType)) {
			// TODO fetch for each type here, TENANT and USER
			PlatformEntityTemplateDTO platformTenantTemplate = null;
			try {
				platformTenantTemplate = globalEntityTemplateService
				        .getPlatformEntityTemplate(TENANT.getFieldName(),
				                templateName);
			} catch (GalaxyException e) {
				if (!e.getErrorMessageDTO()
				        .getErrorCode()
				        .equalsIgnoreCase(
				                GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE
				                        .getCode().toString())) {
					throw e;
				} else {
					// CHeck for USER
					PlatformEntityTemplateDTO platformUserTemplate = globalEntityTemplateService
					        .getPlatformEntityTemplate(USER.getFieldName(),
					                templateName);
					if (platformUserTemplate.getPlatformEntityTemplateName()
					        .equalsIgnoreCase(templateName)) {
						entityType = platformUserTemplate
						        .getPlatformEntityType();
					}
				}
			}
			if (platformTenantTemplate != null
			        && platformTenantTemplate.getPlatformEntityTemplateName()
			                .equalsIgnoreCase(templateName)) {
				entityType = platformTenantTemplate.getPlatformEntityType();
			}
		}
		PlatformEntityDTO globalEntity = new PlatformEntityDTO();
		globalEntity.setPlatformEntityType(entityType);
		return globalEntity;
	}

}
