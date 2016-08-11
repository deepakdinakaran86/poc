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

import com.pcs.alpine.commons.dto.GeneralBatchResponse;
import com.pcs.alpine.commons.dto.HierarchyTagCountDTO;
import com.pcs.alpine.commons.dto.HierarchyTagSearchDTO;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.services.dto.AttachHierarchyDTO;
import com.pcs.alpine.services.dto.AttachHierarchySearchDTO;
import com.pcs.alpine.services.dto.DomainAccessDTO;
import com.pcs.alpine.services.dto.EntitiesByTagsPayload;
import com.pcs.alpine.services.dto.EntityAssignDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityRangeDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.GeoTaggedEntitiesDTO;
import com.pcs.alpine.services.dto.HierarchyDTO;
import com.pcs.alpine.services.dto.HierarchyRelationDTO;
import com.pcs.alpine.services.dto.HierarchyReturnDTO;
import com.pcs.alpine.services.dto.HierarchySearchDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.TagRangePayload;

/**
 * 
 * This Interface is responsible for bringing Hierarchical relationship between
 * entities.Get Methods are available with multiple filters to satisfy the
 * current use cases
 * 
 * @author Daniela (PCSEG191)
 * @date 17 October 2015
 * @since galaxy-1.0.0
 */

public interface HierarchyService {

	/**
	 * Interface method for creating a tenant node
	 * 
	 * 
	 * @param hierarchyEntityDTO
	 * @return IdentityDTO
	 */
	public IdentityDTO createTenantNode(HierarchyDTO hierarchyEntityDTO);

	/**
	 * Interface method for attaching multiple parents to a child
	 * 
	 * 
	 * @param hierarchyEntityDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO attachParents(HierarchyDTO hierarchy);

	/**
	 * Interface method for attaching multiple children to a parent
	 * 
	 * 
	 * @param hierarchyEntityDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO attachChildren(HierarchyDTO hierarchy);

	/**
	 * Interface method for getting children under a parent having a particular
	 * template
	 * 
	 * 
	 * @param hierarchySearch
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getChildren(HierarchySearchDTO hierarchySearch);

	/**
	 * Interface method for getting the count of children of a particular entity
	 * type
	 * 
	 * 
	 * @param hierarchySearch
	 * @return HierarchyReturnDTO
	 */
	public HierarchyReturnDTO getChildEntityTypeCount(
	        HierarchySearchDTO hierarchySearch);

	/**
	 * Interface method for getting the count of children of a particular entity
	 * type
	 * 
	 * 
	 * @param hierarchySearch
	 * @return HierarchyReturnDTO
	 */
	public HierarchyReturnDTO getCountByStatus(
	        HierarchySearchDTO hierarchySearch);

	/**
	 * Interface method for updating a node, Currently only status is there to
	 * update.
	 * 
	 * @param entity
	 * @return entity
	 */
	public EntityDTO updateNode(EntityDTO entity);

	/**
	 * Interface method to get the distribution count of entities
	 * 
	 * 
	 * @param hierarchySearchDTO
	 * @return List<HierarchyReturnDTO>
	 */
	public List<HierarchyReturnDTO> getEntityDistributionCount(
	        HierarchySearchDTO hierarchySearchDTO);

	/**
	 * Interface method to get the distribution of entities
	 * 
	 * 
	 * @param hierarchySearchDTO
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getEntityDistribution(
	        HierarchySearchDTO hierarchySearchDTO);

	/**
	 * Interface method to get the immediate children
	 * 
	 * 
	 * @param hierarchySearchDTO
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getImmediateChildren(
	        HierarchySearchDTO hierarchySearchDTO);

	/**
	 * Interface method to search parent and child in same pattern
	 * 
	 * 
	 * @param hierarchyRelationDTO
	 * @return List<HierarchyRelationDTO>
	 */
	public List<HierarchyRelationDTO> searchHierarchyRelation(
	        HierarchyRelationDTO hierarchyRelationDTO);

	/**
	 * Interface method to get the immediate parents
	 * 
	 * 
	 * @param hierarchySearchDTO
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getImmediateParent(
	        HierarchySearchDTO hierarchySearchDTO);

	/**
	 * Interface method to check if the logged in user has access to fetch /
	 * create or update domain
	 * 
	 * 
	 * @param domainName
	 * @return DomainAccessDTO
	 */
	public DomainAccessDTO isDomainAccessible(String domainName);

	/**
	 * Interface method to get only assignable markers
	 * 
	 * 
	 * @param domainName
	 * @return StatusMessageDTO
	 */
	public List<EntityDTO> getAssignableMarkers(EntityAssignDTO entityAssignDTO);

	public List<EntityDTO> getAllOwnedMarkersByDomain(
	        EntityAssignDTO entityAssignDTO);

	public List<IdentityDTO> getTenantsWithinRange(EntityRangeDTO entityRangeDTO);

	/**
	 * Interface method for attaching subjects to an actor
	 * 
	 * @param AttachHierarchyDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO attach(AttachHierarchyDTO hierarchy);

	/**
	 * Interface method for attaching actors to a subject
	 * 
	 * @param AttachHierarchyDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO attachActorsToASubject(AttachHierarchyDTO hierarchy);

	public List<EntityDTO> getAllEntitySubjects(
	        AttachHierarchySearchDTO attachHierarchySearch);

	public List<EntityDTO> getImmediateRoot(
	        AttachHierarchySearchDTO attachHierarchySearchDTO);

	public List<com.pcs.alpine.commons.dto.EntityDTO> getTagsByRange(
	        TagRangePayload payload);

	public List<EntityTemplateDTO> getAllTemplateSubjects(
	        AttachHierarchySearchDTO attachHierarchySearch);

	public List<GeoTaggedEntitiesDTO> getGeotaggedEntities(
	        HierarchySearchDTO hierarchySearchDTO);

	/**
	 * Interface method to fetch template names of attached entities
	 * 
	 * 
	 * @param hierarchySearchDTO
	 * @return List<String>
	 */
	public List<String> getTemplateNamesofAttachedEntities(
	        HierarchySearchDTO hierarchySearchDTO);

	/**
	 * Interface method for updating a node, Currently only status is there to
	 * update.
	 * 
	 * @param entity
	 * @return entity
	 */
	public EntityDTO updateStatus(EntityDTO entity);

	/**
	 * Interface method for updating status of a list of nodes
	 * 
	 * 
	 * @param entities
	 *            ,statusName
	 * @return GeneralBatchResponse
	 */
	public GeneralBatchResponse updateStatus(List<IdentityDTO> entities,
	        String statusName);

	public Map<String, List<com.pcs.alpine.commons.dto.EntityDTO>> getEntitiesByTags(
	        EntitiesByTagsPayload entitiesByTagsPayload);

	public StatusMessageDTO attachParentsToMultipleNodes(
	        HierarchyDTO hierarchyDTO);

	public List<EntityDTO> getEntitiesWithinTenantRange(
	        EntityRangeDTO entityRangeDTO);

	public List<HierarchyTagCountDTO> getTaggedEntityCount(
	        HierarchyTagSearchDTO hierarchyTagSearchDTO);

}
