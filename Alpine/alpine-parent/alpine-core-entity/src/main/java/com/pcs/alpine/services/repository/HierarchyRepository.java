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

import java.util.List;

import com.pcs.alpine.model.HierarchyEntity;
import com.pcs.alpine.model.TaggedEntity;
import com.pcs.alpine.commons.exception.GalaxyException;

/**
 * 
 * Repository Interface for Hierarchy
 * 
 * Responsible for bringing hierarchical relationships to entities in Galaxy
 * 
 * @author Javid Ahammed (pcseg199)
 * @date Nov 27, 2014
 * @since galaxy-1.0.0
 */

public interface HierarchyRepository {

	/**
	 * Repository method for fetching entire hierarchy starting from the
	 * specified startEntityId with the specified relationship(non-mandatory)
	 * and entityTypes
	 * 
	 * @param entityId
	 * @param relationship
	 * @param entityType
	 * @return
	 */
	public List<String> getEntireHierarchyEntityIds(String startEntityId,
			String relationship, List<String> entityTypes);

	/**
	 * Repository method for fetching entire hierarchy starting from the
	 * specified startEntityId with the specified relationship(non-mandatory)
	 * and entityType filtered with specified entityIds
	 * 
	 * @param entityId
	 * @param relationship
	 * @param entityType
	 * @param entityIds
	 * @return
	 * @throws GalaxyException
	 */
	public List<String> getEntireHierarchyEntityIds(String startEntityId,
			String relationship, List<String> entityTypes,
			List<String> entityIds);

	/**
	 * Repository method for for fetching List<HierarchyEntity> under the
	 * specified startEntityId , specified relationship and specified
	 * entityTypes
	 * 
	 * @param entityId
	 * @param relationship
	 * @param entityType
	 * @return
	 */
	public List<HierarchyEntity> getEntireHierarchyEntities(
			String startEntityId, String relationship, List<String> entityType);

	/**
	 * Repository method for for fetching List<HierarchyEntity> under the
	 * specified startEntityId , specified relationship ,specified entityTypes
	 * and filtered with specified accessEntityIds
	 * 
	 * @param entityId
	 * @param relationship
	 * @param entityType
	 * @return
	 */
	public List<HierarchyEntity> getEntireHierarchyEntities(
			String startEntityId, String relationship,
			List<String> entityTypes, List<String> accessEntityIds);

	/**
	 * Repository method for fetching one level hierarchy starting from the
	 * specified entityId with the specified relationShip(non-mandatory) and
	 * entityType
	 * 
	 * @param rootEntityId
	 * @param relationship
	 * @param entityType
	 * @return
	 */
	public List<String> getImmediateHierarchyEntityIds(String startEntityId,
			String relationship, String entityType);

	/**
	 * Repository method for fetching one level hierarchy starting from the
	 * specified entityId with the specified relationShip(non-mandatory) and
	 * entityTypes filtered with specified entityIds
	 * 
	 * @param entityId
	 * @param relationship
	 * @param entityType
	 * @param entityIds
	 * @return
	 */
	public List<String> getImmediateHierarchyEntityIds(String startEntityId,
			String relationship, String entityType, List<String> entityIds);

	/**
	 * Repository method for fetching tree Hierarchy with specified
	 * startEntityId,endEntityId and relationship
	 * 
	 * @param rootEntityId
	 * @param currentEntityId
	 * @param relationship
	 * @deprecated as non-performing
	 * @return
	 */
	public Object getTreeOverAnEntity(String startEntityId, String endEntityId,
			String relationship);

	/**
	 * Repository method for fetching relationship with specified
	 * startEntityId,endEntityId and relationship
	 * 
	 * @param startEntityId
	 * @param endEntityId
	 * @param relationship
	 * @return
	 * @deprecated to use
	 *             com.pcs.galaxy.em.repository.HierarchyRepository.checkRelationShip
	 *             (String, String, String, String, String, boolean)
	 */
	@Deprecated
	public Integer getRelationship(String startEntityId, String endEntityId,
			String relationship);

	/**
	 * Repository method for verifying a specified relationship is available
	 * with the specified nodes
	 * 
	 * @param startEntityId
	 * @param startEntityType
	 * @param endEntityId
	 * @param endEntityType
	 * @param relationship
	 * @param isImmediate
	 * @return
	 */
	public boolean checkRelationship(String startEntityId,
			String startEntityType, String endEntityId, String endEntityType,
			String relationship, boolean isImmediate);

	/**
	 * Repository method for fetching HierarchyEntity with specified entityId
	 * 
	 * @param entityId
	 * @return
	 */
	public HierarchyEntity getHierarchyEntity(String entityId);

	/***
	 * Repository method for inserting HierarchyEntity in to Neo4j
	 * 
	 * @param hierarchyEntity
	 * @return
	 */
	public Integer insertHierarchyEntity(HierarchyEntity hierarchyEntity);

	/**
	 * Repository method for updating HierarchyEntity in Neo4j
	 * 
	 * @param hierarchyEntity
	 * @return
	 */
	public Integer updateHierarchyEntity(HierarchyEntity hierarchyEntity);

	/**
	 * Repository method for removing relationships of the specified entityId
	 * 
	 * @param entityId
	 * @return
	 */
	public Object removeExistingRelationships(String entityId);

	/**
	 * Repository method for updating the status of the specified entityId to
	 * Deleted
	 * 
	 * @param entityId
	 * @return
	 */
	public Object deleteHierarchyEntity(String entityId);

	/**
	 * Repository method for fetching immediate hierarchy starting from the
	 * specified startEntityId with the specified relationship(non-mandatory)
	 * and entityType filtered with specified entityIds
	 * 
	 * @param entityId
	 * @param relationship
	 * @param entityType
	 * @param entityIds
	 * @return
	 * @author pcseg297 (Twinkle)
	 * @date : Dec 2014 (Sprint6)
	 */
	public List<String> getImmediateHierarchyEntityIds(String startEntityId,
			String relationship, List<String> entityTypes,
			List<String> entityIds);
	


}
