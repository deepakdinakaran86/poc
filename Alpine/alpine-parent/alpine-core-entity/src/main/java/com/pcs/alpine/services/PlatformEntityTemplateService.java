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
package com.pcs.alpine.services;

import java.util.List;

import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;

/**
 * GlobalEntityTemplateService
 * 
 * @description This class is responsible for creating the
 *              GlobalEntityTemplateService interface
 * @author Daniela (PCSEG191)
 * @date 24 Nov 2014
 * @since galaxy-1.0.0
 */

public interface PlatformEntityTemplateService {

	/**
	 * @Description Responsible to get platform entity template based on
	 *              platform entity type
	 * @param platformEntityType
	 * @return PlatformEntityTemplateDTO
	 */
	public PlatformEntityTemplateDTO getPlatformEntityTemplate(
	        String platformEntityType);

	/**
	 * @Description Responsible to get global entity templates based on global
	 *              entity type and Template name
	 * @param platformEntityType
	 *            , templateName
	 * @return PlatformEntityTemplateDTO
	 */
	public PlatformEntityTemplateDTO getPlatformEntityTemplate(
	        String platformEntityType, String templateName);

	/**
	 * @Description Responsible to get global entity templates based on global
	 *              entity type
	 * @param platformEntityType
	 * @return List<GlobalEntityTemplateDTO>
	 */
	public List<PlatformEntityTemplateDTO> getPlatformEntityTemplates(
	        String platformEntityType);

	public Boolean isValidFieldName(String platformEntityType,
	        String fieldName, String templateName);
	//
	// public String getPlatformEntityType(String globalEntityTemplateName);

}
