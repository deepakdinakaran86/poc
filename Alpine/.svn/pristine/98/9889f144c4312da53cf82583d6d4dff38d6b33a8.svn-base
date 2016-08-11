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
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;

/**
 * 
 * @description Services related to Templates are outlined here
 * @author Twinkle (pcseg297)
 * @date August 2014
 * @since galaxy-1.0.0
 */

public interface EntityTemplateService {

	/**
	 * @Description Persist a template
	 * @return StatusMessageDTO
	 */
	public EntityTemplateDTO saveTemplate(EntityTemplateDTO entityTemplateDTO);

	/**
	 * @Description Allocate list of templates
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO allocateTemplates(
	        List<EntityTemplateDTO> entityTemplateDTOs, String domain,
	        Boolean isParentDomain);

	/**
	 * @Description Update a template
	 * @return StatusMessageDTO
	 */
	public EntityTemplateDTO updateTemplate(EntityTemplateDTO entityTemplateDTO);

	/**
	 * @Description Fetch a template based on entityId of an entity
	 * @return EntityTemplateDTO
	 */
	// public EntityTemplateDTO getTemplate(String entityId);

	/**
	 * @Description Fetch a template based on template uuid
	 * @return EntityTemplateDTO
	 */
	public EntityTemplateDTO getTemplate(UUID uuid);

	/**
	 * @Description Fetch a template based on domainName, globalEntityType and
	 *              entityTemplateName
	 * @return EntityTemplateDTO
	 */
	public EntityTemplateDTO getTemplate(EntityTemplateDTO entityTemplateDTO);

	/**
	 * @Description Fetch all templates based on domainName and globalEntityType
	 * @return List<EntityTemplateDTO>
	 */
	public List<EntityTemplateDTO> findAllTemplate(
	        EntityTemplateDTO entityTemplateDTO);

	/**
	 * @Description Delete a template based on domainName, globalEntityType and
	 *              entityTemplateName
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO deleteEntityTemplate(
	        EntityTemplateDTO entityTemplateDTO);

	/**
	 * @Description Delete a template based on domainName
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO unAllocateEntityTemplate(String domainName);

	/**
	 * @Description Delete a template
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO deleteTemplate(UUID uuid);

	/**
	 * @Description Fetch all the global templates from specified domain
	 * @return List<EntityTemplateDTO>
	 */
	public List<EntityTemplateDTO> getGlobalTemplates(String domain);

	/**
	 * @Description This services will be invoked while retrieving all the
	 *              Templates from Galaxy by providing domainName. It will be
	 *              used in createApp process.
	 * 
	 *              Templates retrieved by this service are the templates which
	 *              are assigned to domain(Client or Organization) during
	 *              onboarding process and custom Templates. Templates for
	 *              globalEntityType isDefault = True property, could't be
	 *              retrieved by using this service.
	 * 
	 *              Based on includeNonShareable(FALSE by default) templates
	 *              will be returned.
	 * @param EntitySearchDTO
	 *            entitySearchDTO
	 * @return List<EntityTemplateDTO> templates.
	 */
	public Map<String, List<EntityTemplateDTO>> getAllTemplatesByDomain(
	        EntitySearchDTO entitySearchDTO, Boolean includeNonShareable);

	/**
	 * @Description Fetch all the global templates from specified domain of
	 *              globalEntityType type
	 * @return List<EntityTemplateDTO>
	 */
	public List<EntityTemplateDTO> getTemplateByEntityAndType(
	        EntitySearchDTO entitySearchDTO);

	/**
	 * @Description Fetch all the templates of a parent and children below
	 * @return List<NodeEntityTemplateDTO>
	 */
	/*
	 * public List<NodeEntityTemplateDTO> fetchTemplatesForNodesWithAccess(
	 * EntitySearchDTO entitySearchDTO);
	 */

	/**
	 * @Description Fetch all the templates of a parent and children below
	 *              nodeIDs irrespective of access
	 * @return List<NodeEntityTemplateDTO>
	 */
	// public List<NodeEntityTemplateDTO> fetchTemplatesForNodesWithoutAccess(
	// EntitySearchDTO entitySearchDTO);

	/**
	 * @Description Responsible to fetch all the default templates
	 * @return List<EntityTemplateDTO>
	 */
	public List<EntityTemplateDTO> getDefaultTemplates();

	/**
	 * 
	 * @Description All entity templates based on domain,uniqueUserId* and type*
	 * @return List<EntityDTO>
	 */
	// public List<NodeEntityTemplateDTO> getEntityTemplates(
	// EntitySearchDTO entitySearchDTO);

	/**
	 * 
	 * @Description All entity templates for selected entity ids*
	 * @return List<EntityTemplateDTO>
	 */
	// public List<NodeEntityTemplateDTO> getEntityTemplatesByEntityIds(
	// EntitySearchDTO entitySearchDTO);

	/**
	 * 
	 * @Description Responsible to indicate if a field is valid
	 * @return Boolean, true indicates valid field
	 */
	public Boolean isValidFieldName(String domain, String globalEntity,
	        String fieldName, String templateName);

	/**
	 * 
	 * @Description Responsible for fetching globalEntityTypes associated with
	 *              specified domain
	 * @return List<GlobalEntityDTO> globalEntityTypes
	 */
	public Set<String> getGlobalEntityTypesByDomain(String domain);

	/**
	 * @Description Fetch all the global templates from specified domain of
	 *              globalEntityType type
	 * @return List<EntityTemplateDTO>
	 */
	public EntityTemplateDTO getDefaultOrgTemplateByDomainAndType(
	        String domain, String globalEntityType);

	public String getEntityType(String domain, String entityTemplateName,
	        Boolean isParentDomain);

	public List<EntityTemplateDTO> getEntityTemplatesByDomainAndType(
	        String domain, String platformType);

	public List<EntityTemplateDTO> findAllTemplateByParentTemplate(
	        EntityTemplateDTO entityTemplateDTO);

	public StatusMessageDTO updateStatus(EntityTemplateDTO entityTemplateDTO,
	        String status);

}
