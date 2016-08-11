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
package com.pcs.alpine.services.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pcs.alpine.commons.dto.GeneralBatchResponse;
import com.pcs.alpine.commons.dto.HierarchyTagSearchDTO;
import com.pcs.alpine.model.DistributionEntity;
import com.pcs.alpine.model.HierarchyEntity;
import com.pcs.alpine.model.HierarchyEntityTemplate;
import com.pcs.alpine.model.TaggedEntity;
import com.pcs.alpine.services.dto.AttachHierarchyDTO;
import com.pcs.alpine.services.dto.AttachHierarchySearchDTO;
import com.pcs.alpine.services.dto.EntitiesByTagsPayload;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.HierarchyDTO;
import com.pcs.alpine.services.dto.HierarchyRelationDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.TagRangePayload;

/**
 * 
 * Repository Interface for Hierarchy
 * 
 * @description Service Implementation for hierarchy services
 * @author Daniela (PCSEG191)
 * @author Riyas (PCSEG296)
 * @date 18 Oct 2015
 * @since alpine-1.0.0
 */

public interface HierarchyRepository {

	/**
	 * /** Interface method for attaching multiple parents to a child
	 * 
	 * 
	 * @param hierarchyEntityDTO
	 */
	public void attachParents(HierarchyDTO hierarchy);

	/**
	 * Interface method for attaching multiple children to a parent
	 * 
	 * 
	 * @param hierarchyEntityDTO
	 */
	public void attachChildren(HierarchyDTO hierarchy);

	/**
	 * /** Interface method for attaching multiple parents to a child
	 * 
	 * @param entityId
	 * @param relationship
	 * @param entityType
	 * @return
	 */
	public Integer insertTenantHierarchy(HierarchyEntity hierarchyEntity);

	public HashSet<String> getChildrenOfEntity(String parent,
	        Set<String> children);

	public HashSet<String> getParentsOfEntity(String parent,
	        Set<String> children);

	/**
	 * /** Interface method for attaching multiple parents to a child
	 * 
	 * @param tenantDomain
	 * @param parentDomain
	 * @param myDomain
	 * @return
	 */
	public List<String> getHierarchyByTenantDomain(String parentDomain,
	        String myDomain);

	public boolean isTenantExist(String domain);

	public String getTenantDomainName(EntityDTO entity);

	public List<String> getTenantsDomainNames(List<EntityDTO> tenants);

	public List<HierarchyEntity> getChildren(HierarchyEntity hierarchyEntity,
	        String searchTemplate, String searchEntityType);

	public Integer getChildrenCount(HierarchyEntity hierarchyEntity,
	        String searchTemplate, String searchEntityType);

	public Integer getCountByStatus(String domain, String templateName,
	        String status, String entityType);

	public EntityDTO updateNode(EntityDTO entity, String dataprovider,
	        String tree);

	public List<DistributionEntity> getEntityDistributionCount(
	        String parentDomain, String parentEntityType,
	        String searchTemplateName, String searchEntityType, String status);

	public List<HierarchyEntity> getEntityDistribution(String parentDomain,
	        String parentEntityType, String searchTemplateName,
	        String searchEntityType, String status);

	public List<HierarchyEntity> getImmediateChildren(
	        HierarchyEntity hierarchyEntity, String searchTemplate,
	        String searchEntityType);

	public List<HierarchyEntity> getImmediateParents(
	        HierarchyEntity hierarchyEntity, String searchTemplate,
	        String searchEntityType);

	public boolean isChildDomain(String parentDomain, String domain);

	boolean isNodeExist(EntityDTO entityDTO);

	public List<HierarchyEntity> getParents(HierarchyEntity hierarchyEntity,
	        String searchTemplate, String searchEntityType);

	public List<HierarchyEntity> getAssignableMarkers(
	        HierarchyEntity hierarchyEntity, String searchTemplateName,
	        String markerTemplateName);

	public List<HierarchyEntity> getAllOwnedMarkersByDomain(
	        HierarchyEntity hierarchyEntity, String searchTemplateName,
	        String markerTemplateName);

	public List<HierarchyEntity> getTenantsWithinRange(
	        HierarchyEntity hierarchyEntity, HierarchyEntity endEntity);

	public HierarchyEntity getTenantNode(String domain);

	/**
	 * Interface method for attaching subjects to an actor
	 * 
	 * 
	 * @param hierarchyEntityDTO
	 */
	public void attach(AttachHierarchyDTO hierarchy);

	public void attachActorsToASubject(AttachHierarchyDTO hierarchy);

	public List<HierarchyEntity> getAllEntitySubjects(
	        AttachHierarchySearchDTO attachHierarchySearch);

	public List<HierarchyEntityTemplate> getAllTemplateSubjects(
	        AttachHierarchySearchDTO attachHierarchySearch);

	public List<HierarchyEntity> getImmediateRoot(
	        AttachHierarchySearchDTO attachHierarchySearch);

	public List<com.pcs.alpine.commons.dto.EntityDTO> getTagsByRange(
	        TagRangePayload payload);

	public List<TaggedEntity> getGeoTaggedEntities(String domain,
	        String templateName);

	/**
	 * Interface method to fetch template names of attached entities
	 * 
	 * @param searchDomain
	 *            , searchTemplateName
	 * @return List<String>
	 * 
	 */
	public List<String> getTemplateNamesofAttachedEntities(String searchDomain,
	        String searchTemplateName);

	public EntityDTO updateStatus(EntityDTO entity);

	public GeneralBatchResponse updateStatus(List<IdentityDTO> entities,
	        String statusName);

	public List<HierarchyRelationDTO> searchHierarchyRelation(
	        HierarchyRelationDTO hierarchyRelation);

	public Map<String, List<com.pcs.alpine.commons.dto.EntityDTO>> getEntitiesByTags(
	        EntitiesByTagsPayload entitiesByTagsPayload);

	public void attachParentsToMultipleMarkers(
	        List<HierarchyEntity> childrenNodes, List<EntityDTO> parents);

	public List<HierarchyEntity> getEntitiesBetweenTenants(
	        HierarchyEntity startEntity, HierarchyEntity endEntity,
	        String searchTemplate);

	public List<DistributionEntity> getTaggedEntityCount(
	        HierarchyTagSearchDTO hierarchyTagSearchDTO);
}
