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
package com.pcs.alpine.commons.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * HierachyDTO
 *
 * @description DTO for Searching Entities/Hierarchy
 * @author Javid Ahammed (PCSEG199)
 * @date 25 August 2014
 * @since galaxy-1.0.0
 */
@XmlRootElement(name = "entitySearch")
public class EntitySearchDTO implements Serializable {

	private static final long serialVersionUID = -5940853661256358097L;

	private String parentEntityType;
	private String parentEntityId;
	private List<String> accessEntityIds;
	private String entityType;
	private String relationship;
	private String currentEntityId;
	private String searchField;
	private DomainDTO domain;
	private PlatformEntityDTO platformEntity;
	private List<FieldMapDTO> fieldValues;
	private String uniqueUserId;
	private List<EntityTemplateDTO> entityTemplates;
	private List<PlatformEntityDTO> globalEntities;
	private EntityTemplateDTO entityTemplate;
	private EntityDTO entity;
	private String loggedInUserDomain;
	private String statusName;
	private Boolean isParentDomain;
	private String uniqueAcross;

	public String getParentEntityType() {
		return parentEntityType;
	}

	public void setParentEntityType(String parentEntityType) {
		this.parentEntityType = parentEntityType;
	}

	public String getParentEntityId() {
		return parentEntityId;
	}

	public void setParentEntityId(String parentEntityId) {
		this.parentEntityId = parentEntityId;
	}

	public List<String> getAccessEntityIds() {
		return accessEntityIds;
	}

	public void setAccessEntityIds(List<String> accessEntityIds) {
		this.accessEntityIds = accessEntityIds;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getCurrentEntityId() {
		return currentEntityId;
	}

	public void setCurrentEntityId(String currentEntityId) {
		this.currentEntityId = currentEntityId;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public PlatformEntityDTO getPlatformEntity() {
		return platformEntity;
	}

	public void setPlatformEntity(PlatformEntityDTO platformEntity) {
		this.platformEntity = platformEntity;
	}

	public List<FieldMapDTO> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<FieldMapDTO> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public String getUniqueUserId() {
		return uniqueUserId;
	}

	public void setUniqueUserId(String uniqueUserId) {
		this.uniqueUserId = uniqueUserId;
	}

	public List<EntityTemplateDTO> getEntityTemplates() {
		return entityTemplates;
	}

	public void setEntityTemplates(List<EntityTemplateDTO> entityTemplates) {
		this.entityTemplates = entityTemplates;
	}

	public List<PlatformEntityDTO> getGlobalEntities() {
		return globalEntities;
	}

	public void setGlobalEntities(List<PlatformEntityDTO> globalEntities) {
		this.globalEntities = globalEntities;
	}

	public EntityTemplateDTO getEntityTemplate() {
		return entityTemplate;
	}

	public void setEntityTemplate(EntityTemplateDTO entityTemplate) {
		this.entityTemplate = entityTemplate;
	}

	public EntityDTO getEntity() {
		return entity;
	}

	public void setEntity(EntityDTO entity) {
		this.entity = entity;
	}

	public String getLoggedInUserDomain() {
		return loggedInUserDomain;
	}

	public void setLoggedInUserDomain(String userDomain) {
		this.loggedInUserDomain = userDomain;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Boolean getIsParentDomain() {
		return isParentDomain;
	}

	public void setIsParentDomain(Boolean isParentDomain) {
		this.isParentDomain = isParentDomain;
	}

	public String getUniqueAcross() {
		return uniqueAcross;
	}

	public void setUniqueAcross(String uniqueAcross) {
		this.uniqueAcross = uniqueAcross;
	}

}
