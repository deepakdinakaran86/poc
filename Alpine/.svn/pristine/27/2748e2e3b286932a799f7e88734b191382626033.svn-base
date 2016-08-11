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
package com.pcs.alpine.model;

/**
 * Model Class for Entity Hierarchy Metadata
 * 
 * @author Daniela (pcseg191)
 * @date 18 Oct 2015
 * @since galaxy-1.0.0
 */
public class HierarchyEntity {

	private String identifierKey;
	private String identifierValue;
	private String entityType;
	private String templateName;
	private String domain;
	private String myDomain;
	private String status;
	private String relationship;
	private String actorTenantDomain;
	private String dataprovider;
	private String tree;

	public String getIdentifierKey() {
		return identifierKey;
	}

	public void setIdentifierKey(String identifierKey) {
		this.identifierKey = identifierKey;
	}

	public String getIdentifierValue() {
		return identifierValue;
	}

	public void setIdentifierValue(String identifierValue) {
		this.identifierValue = identifierValue;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getMyDomain() {
		return myDomain;
	}

	public void setMyDomain(String myDomain) {
		this.myDomain = myDomain;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getActorTenantDomain() {
		return actorTenantDomain;
	}

	public void setActorTenantDomain(String actorTenantDomain) {
		this.actorTenantDomain = actorTenantDomain;
	}

	@Override
	public int hashCode() {
		int result = ((identifierValue == null) ? 0 : identifierValue
		        .hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			HierarchyEntity hierarchyEntity = (HierarchyEntity)obj;
			if (hierarchyEntity.getDomain().equals(this.domain)
			        && hierarchyEntity.getEntityType().equals(this.entityType)
			        && hierarchyEntity.getIdentifierKey().equals(
			                this.identifierKey)
			        && hierarchyEntity.getIdentifierValue().equals(
			                this.identifierValue)
			        && hierarchyEntity.getTemplateName().equals(
			                this.templateName)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public String getDataprovider() {
		return dataprovider;
	}

	public void setDataprovider(String dataprovider) {
		this.dataprovider = dataprovider;
	}

	public String getTree() {
		return tree;
	}

	public void setTree(String tree) {
		this.tree = tree;
	}

}