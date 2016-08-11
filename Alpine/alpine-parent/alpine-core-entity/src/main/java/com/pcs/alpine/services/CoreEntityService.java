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

/**
 * 
 * @description This class is responsible for the CoreEntityService interface
 * @author Daniela (PCSEG191)
 * @date 12 Nov 2014
 * @since galaxy-1.0.0
 */

import java.util.List;

import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityStatusCountDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;

public interface CoreEntityService {

	/**
	 * 
	 * @Description This method fetches a list of entities based on the domain,
	 *              uniqueUserId and entity type mandatory fields to be
	 *              specified are domain and uniqueUserId, uniqueUserId is the
	 *              identifier field which is a unique identifier for a user
	 *              across the application, this field is used to fetch entities
	 *              based on the user's access level
	 * 
	 * @param EntitySearchDTO
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getEntitiesByDomain(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description This method is used to fetch a specific entity based on
	 *              domain,uniqueUserId, field name, field value and
	 *              type,mandatory fields to be specified are field name, field
	 *              value and uniqueUserId, uniqueUserId is the identifier field
	 *              which is a unique identifier for a user across the
	 *              application, this field is used to fetch entities based on
	 *              the user's access level, if multiple entries are retrieve an
	 *              exception will be thrown
	 * 
	 * @param EntitySearchDTO
	 * @return EntityDTO
	 */
	public EntityDTO getEntityByField(EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description This method is used to fetch a list of entities based on
	 *              domain,uniqueUserId, field name, field value and
	 *              type,mandatory fields to be specified are field name, field
	 *              value and uniqueUserId, uniqueUserId is the identifier field
	 *              which is a unique identifier for a user across the
	 *              application, this field is used to fetch entities based on
	 *              the user's access level
	 * 
	 * @param EntitySearchDTO
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getEntitiesByField(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description This method is used to fetch a list of entities based on
	 *              domain,uniqueUserId, field name, field value and
	 *              type,mandatory fields to be specified are field name, field
	 *              value and uniqueUserId, uniqueUserId is the identifier field
	 *              which is a unique identifier for a user across the
	 *              application, this field is used to fetch entities based on
	 *              the user's access level
	 * 
	 * @param EntitySearchDTO
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getDataProvidersByField(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description All entities for selected entity ids*
	 * @return List<EntityDTO>
	 */
	// public List<EntityDTO> getEntitiesByEntityIds(List<String> entityIds);

	/**
	 * 
	 * @Description All entities of a type*,uniqueUserId* and domain
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getEntitiesByType(EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description Find entities matching multiple field value
	 *              pairs,uniqueUserId* , domain and type*
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getEntitiesByMultipleFields(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description Find entities matching multiple field value
	 *              pairs,uniqueUserId* , domain and type* and entity template
	 *              name
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getEntitiesByEntityTemplateAndMultipleFields(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description This method fetches an entity based on the identifier
	 *              fields, which are template name, domain , global entity type
	 *              and identifier
	 * 
	 * @param EntityDTO
	 * @return EntityDTO
	 */
	public EntityDTO getEntity(IdentityDTO identityDTO);

	/**
	 * @Description This method fetches an entity based on the global entity
	 *              type and identifier
	 * @param identifier
	 * @param globalEntityDTO
	 * @return
	 */
	public EntityDTO getEntity(FieldMapDTO identifier,
	        PlatformEntityDTO globalEntityDTO);

	/**
	 * 
	 * @Description This method is responsible to get the entity data provider
	 *              based on domain, entity type and uniqueUserId*
	 * 
	 * @param EntitySearchDTO
	 * @return EntityDTO
	 */

	public List<EntityDTO> getEntityDetailsWithDataprovider(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description This method is responsible to get the count of entities of a
	 *              type under a domain
	 * 
	 * 
	 * @param EntitySearchDTO
	 * @return Integer
	 */

	public Integer getEntitiesCountByTypeAndDomain(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description This method is responsible to get the count of entities of a
	 *              type under a domain using a specific entity template
	 * 
	 * 
	 * @param EntitySearchDTO
	 * @return Integer
	 */

	public Integer getEntitiesCountByTemplate(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * @Description This service is to fetch entity identifier fields for
	 *              selected entity ids*
	 * @return List<EntityDTO>
	 */
	// public List<EntityDTO> getEntityIdentifiersByEntityIds(
	// List<String> entityIds);

	/**
	 * 
	 * @Description This service is responsible to update an existing entity
	 * @param EntityDTO
	 * @return EntityDTO
	 */
	public EntityDTO updateEntity(EntityDTO entityDTO);

	/**
	 * 
	 * @Description Delete an entity based on identifier,templateName and domain
	 * @param identifier
	 *            ,templateName,domain
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO deleteEntity(FieldMapDTO identifier,
	        String templateName, String domain, String platformEntityType);

	/**
	 * 
	 * @Description Persists an entity in Database
	 * @param EntityDTO
	 * @return EntityDTO
	 */
	public EntityDTO saveEntity(EntityDTO entityDTO);

	/**
	 * @Description Fetches entity with entityId if the status is not deleted
	 * @param entityId
	 * @return
	 */
	// public EntityDTO getEntity(String entityId);

	/**
	 * @Description Fetches entity with domainName if the status is not deleted
	 * @param domainName
	 * @return EntityDTO
	 */
	public EntityDTO getDomainEntity(String domainName);

	/**
	 * @Description Fetches entities based on template domain and type
	 * @param entitySearchDTO
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getEntities(EntitySearchDTO entitySearchDTO);

	/**
	 * 
	 * @Description This method fetches an entity based on the identifier
	 *              fields, which are template name, domain , global entity type
	 *              and identifier
	 * 
	 * @param EntityDTO
	 * @return EntityDTO
	 */
	public EntityDTO getEntityDataProvider(IdentityDTO identityDTO);

	/**
	 * 
	 * @Description This method is responsible to get the count of entities of a
	 *              type under a domain and specific status
	 * 
	 * 
	 * @param EntitySearchDTO
	 * @return List<EntityStatusCountDTO>
	 */

	public List<EntityStatusCountDTO> getEntitiesCountByStatus(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description Find entities matching multiple field value pairs, domain
	 *              and type*, status and entity template name
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getEntitiesByEntityTemplateAndMultipleFieldsAndStatus(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description Find entities count matching multiple field value pairs,
	 *              domain and type*, status and entity template name
	 * @return EntityCountDTO
	 */
	public EntityCountDTO getEntitySearchCount(
	        EntitySearchDTO coreEntitySearchDTO);

	/**
	 * 
	 * @Description Persists a list of entities in Database
	 * @param EntityDTO
	 * @return EntityDTO
	 */
	public List<IdentityDTO> saveEntities(List<EntityDTO> entityDTO);

	public StatusMessageDTO updateStatus(IdentityDTO identity, String status);

}
