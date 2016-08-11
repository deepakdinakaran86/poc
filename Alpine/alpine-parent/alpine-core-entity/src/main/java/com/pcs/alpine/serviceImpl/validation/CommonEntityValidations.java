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
package com.pcs.alpine.serviceImpl.validation;

import static com.pcs.alpine.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN;
import static com.pcs.alpine.services.enums.EMDataFields.DOMAIN_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE;
import static com.pcs.alpine.services.enums.EMDataFields.ENTITY_TEMPLATE_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.FIELD_VALUES;
import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY;
import static com.pcs.alpine.services.enums.EMDataFields.PLATFORM_ENTITY_TYPE;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER_KEY;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER_VALUE;
import static com.pcs.alpine.services.enums.EMDataFields.STATUS;
import static com.pcs.alpine.services.enums.Status.ACTIVE;
import static com.pcs.alpine.services.enums.Status.INACTIVE;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import org.springframework.stereotype.Component;

import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.enums.EMDataFields;

/**
 * CommonEntityValidations
 * 
 * @description Common business level validations of Entity course grained
 *              layer.
 * @author RIYAS PH (PCSEG296)
 * @date 14 Mar 2015
 * @since galaxy-1.0.0
 */

@Component
public class CommonEntityValidations {

	/**
	 * Method is responsible to do common entity mandatory file validation
	 * 
	 * @param entityDto
	 */
	public void validateMandatoryForEntity(EntityDTO entityDto) {

		validateEntity(entityDto);

		validateMandatoryFields(entityDto.getEntityTemplate(), DOMAIN,
		        ENTITY_TEMPLATE_NAME);

		validateMandatoryFields(entityDto.getEntityTemplate().getDomain(),
		        DOMAIN_NAME);
	}

	private void validateEntity(EntityDTO entityDto) {
		validateMandatoryFields(entityDto, FIELD_VALUES, ENTITY_TEMPLATE);

		validateMandatoryFields(entityDto.getHierarchy(), PLATFORM_ENTITY,
		        DOMAIN, IDENTIFIER, ENTITY_TEMPLATE);

		validateMandatoryFields(entityDto.getHierarchy().getDomain(),
		        DOMAIN_NAME);

		validateMandatoryFields(entityDto.getHierarchy().getPlatformEntity(),
		        PLATFORM_ENTITY_TYPE);

		validateMandatoryFields(entityDto.getHierarchy().getEntityTemplate(),
		        ENTITY_TEMPLATE_NAME);

		if (entityDto.getEntityStatus() != null
		        && isNotBlank(entityDto.getEntityStatus().getStatusName())
		        && !(entityDto.getEntityStatus().getStatusName()
		                .equalsIgnoreCase(ACTIVE.getStatus()))
		        && !(entityDto.getEntityStatus().getStatusName()
		                .equalsIgnoreCase(INACTIVE.name()))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        STATUS.getDescription());
		}
	}

	/**
	 * Method is responsible to do validate the identifier fields without the
	 * global entity type
	 * 
	 * @param identityDto
	 */
	public void validateIdentifierFields(IdentityDTO identityDto) {

		validateMandatoryFields(identityDto, ENTITY_TEMPLATE, DOMAIN,
		        IDENTIFIER);

		validateMandatoryFields(identityDto.getDomain(), DOMAIN_NAME);

		validateMandatoryFields(identityDto.getIdentifier(), IDENTIFIER_KEY,
		        IDENTIFIER_VALUE);

		validateMandatoryFields(identityDto.getEntityTemplate(),
		        EMDataFields.ENTITY_TEMPLATE_NAME);

	}

	/**
	 * Method is responsible to do common entity update mandatory file
	 * validation
	 * 
	 * @param entityDto
	 */
	public void validateMandatoryForEntityUpdate(EntityDTO entityDto) {

		validateMandatoryFields(entityDto, FIELD_VALUES, ENTITY_TEMPLATE,
		        IDENTIFIER);

		validateMandatoryFields(entityDto.getEntityTemplate(), DOMAIN,
		        ENTITY_TEMPLATE_NAME);

		validateMandatoryFields(entityDto.getEntityTemplate().getDomain(),
		        DOMAIN_NAME);

		if (entityDto.getEntityStatus() != null
		        && isNotBlank(entityDto.getEntityStatus().getStatusName())
		        && !(entityDto.getEntityStatus().getStatusName()
		                .equalsIgnoreCase(INACTIVE.name()))
		        && !(entityDto.getEntityStatus().getStatusName()
		                .equalsIgnoreCase(ACTIVE.name()))
		        && !(entityDto.getEntityStatus().getStatusName()
		                .equalsIgnoreCase(Status.ALLOCATED.name()))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        STATUS.getDescription());
		}
	}

	/**
	 * Method is responsible to do validate the identifier fields for the global
	 * entity type
	 * 
	 * @param identityDto
	 */
	public void validateIdentifierGlobalEntity(IdentityDTO identityDto) {
		validateMandatoryFields(identityDto, PLATFORM_ENTITY);

		validateMandatoryFields(identityDto.getPlatformEntity(),
		        PLATFORM_ENTITY_TYPE);

	}

	public void validateEntityWithoutTemplateDomain(EntityDTO entityDto) {

		validateEntity(entityDto);

		validateMandatoryFields(entityDto.getEntityTemplate(),
		        ENTITY_TEMPLATE_NAME);
	}
}
