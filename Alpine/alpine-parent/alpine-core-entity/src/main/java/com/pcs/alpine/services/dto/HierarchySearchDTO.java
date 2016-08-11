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
package com.pcs.alpine.services.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * HierachyDTO
 * 
 * @description DTO for Hierarchy
 * @author Javid Ahammed (PCSEG199)
 * @date 25 August 2014
 * @since galaxy-1.0.0
 */
@XmlRootElement(name = "hierarchySearch")
public class HierarchySearchDTO implements Serializable {

	private static final long serialVersionUID = -5940853661256358097L;

	private String parentEntityId;
	private List<String> accessEntityIds;
	private String entityType;
	private String relationship;
	private String entityName;
	private String currentEntityId;
	private String searchField;
	private DomainDTO domain;
	private PlatformEntityDTO globalEntityDTO;
	private Boolean isParentDomain;
	private IdentityDTO actor;
	private String searchTemplateName;
	private String searchEntityType;
	private String statusName;

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

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
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

	public PlatformEntityDTO getGlobalEntityDTO() {
		return globalEntityDTO;
	}

	public void setGlobalEntityDTO(PlatformEntityDTO globalEntityDTO) {
		this.globalEntityDTO = globalEntityDTO;
	}

	public Boolean getIsParentDomain() {
		return isParentDomain;
	}

	public void setIsParentDomain(Boolean isParentDomain) {
		this.isParentDomain = isParentDomain;
	}

	public IdentityDTO getActor() {
		return actor;
	}

	public void setActor(IdentityDTO actor) {
		this.actor = actor;
	}

	public String getSearchTemplateName() {
		return searchTemplateName;
	}

	public void setSearchTemplateName(String searchTemplateName) {
		this.searchTemplateName = searchTemplateName;
	}

	public String getSearchEntityType() {
		return searchEntityType;
	}

	public void setSearchEntityType(String searchEntityType) {
		this.searchEntityType = searchEntityType;
	}

	public String getStatusName() {
	    return statusName;
    }

	public void setStatusName(String statusName) {
	    this.statusName = statusName;
    }

}
