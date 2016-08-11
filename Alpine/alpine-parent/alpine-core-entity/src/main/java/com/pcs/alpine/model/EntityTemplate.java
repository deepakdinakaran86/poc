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

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * EntityTemplate
 * 
 * @description POJO for EntityTemplate
 * @author Anusha Saju (pcseg257)
 * @date Aug 2014
 * @since galaxy-1.0.0
 */

@Table(name = "entity_template")
public class EntityTemplate {

	@PartitionKey(value = 0)
	@Column(name = "domain")
	private String domain;

	@PartitionKey(value = 1)
	@JsonProperty("platform_entity_type")
	@Column(name = "platform_entity_type")
	private String platformEntityType;

	@JsonProperty("status_key")
	@ClusteringColumn(value = 0)
	@Column(name = "status_key")
	private Integer statusKey;

	@ClusteringColumn(value = 1)
	@JsonProperty("entity_template_id")
	@Column(name = "entity_template_id")
	private UUID entityTemplateId;

	@JsonProperty("entity_template_name")
	@Column(name = "entity_template_name")
	private String entityTemplateName;

	@JsonProperty("domain_type")
	@Column(name = "domain_type")
	private String domainType;

	@JsonProperty("field_validation")
	@Column(name = "field_validation")
	private Map<String, String> fieldValidation;

	@JsonProperty("html_page")
	@Column(name = "html_page")
	private ByteBuffer htmlPage;

	@JsonProperty("identifier_field")
	@Column(name = "identifier_field")
	private String identifierField;

	@JsonProperty("is_modifiable")
	@Column(name = "is_modifiable")
	private Boolean isModifiable;

	@JsonProperty("is_sharable")
	@Column(name = "is_sharable")
	private Boolean isSharable;

	@JsonProperty("platform_entity_template_name")
	@Column(name = "platform_entity_template_name")
	private String platformEntityTemplateName;

	@JsonProperty("status_name")
	@Column(name = "status_name")
	private String statusName;

	@JsonProperty("parent_template")
	@Column(name = "parent_template")
	private String parentTemplate;

	@JsonProperty("access_scope")
	@Column(name = "access_scope")
	private String accessScope;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPlatformEntityType() {
		return platformEntityType;
	}

	public void setPlatformEntityType(String platformEntityType) {
		this.platformEntityType = platformEntityType;
	}

	public String getEntityTemplateName() {
		return entityTemplateName;
	}

	public void setEntityTemplateName(String entityTemplateName) {
		this.entityTemplateName = entityTemplateName;
	}

	public Integer getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(Integer statusKey) {
		this.statusKey = statusKey;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
		        * result
		        + ((this.getDomain() == null) ? 0 : this.getDomain().hashCode());
		result = prime
		        * result
		        + ((this.getEntityTemplateName() == null) ? 0 : this
		                .getEntityTemplateName().hashCode());
		result = prime
		        * result
		        + ((this.getPlatformEntityType() == null) ? 0 : this
		                .getPlatformEntityType().hashCode());
		return result;
	}

	/**
	 * EntityTemplates are unique based on domainName, globalEntityType and
	 * entityTemplateName. (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityTemplate other = (EntityTemplate)obj;
		if (this.getDomain() == null) {
			if (other.getDomain() != null)
				return false;
		} else if (!this.getDomain().equals(other.getDomain()))
			return false;
		if (this.getEntityTemplateName() == null) {
			if (other.getEntityTemplateName() != null)
				return false;
		} else if (!this.getEntityTemplateName().equals(
		        other.getEntityTemplateName()))
			return false;
		if (this.getPlatformEntityType() == null) {
			if (other.getPlatformEntityType() != null)
				return false;
		} else if (!this.getPlatformEntityType().equals(
		        other.getPlatformEntityType()))
			return false;
		return true;
	}

	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	public Map<String, String> getFieldValidation() {
		return fieldValidation;
	}

	public void setFieldValidation(Map<String, String> fieldValidation) {
		this.fieldValidation = fieldValidation;
	}

	public String getParentTemplate() {
		return parentTemplate;
	}

	public void setParentTemplate(String parentTemplate) {
		this.parentTemplate = parentTemplate;
	}

	public ByteBuffer getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(ByteBuffer htmlPage) {
		this.htmlPage = htmlPage;
	}

	public String getIdentifierField() {
		return identifierField;
	}

	public void setIdentifierField(String identifierField) {
		this.identifierField = identifierField;
	}

	public Boolean getIsModifiable() {
		return isModifiable;
	}

	public void setIsModifiable(Boolean isModifiable) {
		this.isModifiable = isModifiable;
	}

	public Boolean getIsSharable() {
		return isSharable;
	}

	public void setIsSharable(Boolean isSharable) {
		this.isSharable = isSharable;
	}

	public String getPlatformEntityTemplateName() {
		return platformEntityTemplateName;
	}

	public void setPlatformEntityTemplateName(String platformEntityTemplateName) {
		this.platformEntityTemplateName = platformEntityTemplateName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public UUID getEntityTemplateId() {
		return entityTemplateId;
	}

	public void setEntityTemplateId(UUID entityTemplateId) {
		this.entityTemplateId = entityTemplateId;
	}

	public String getAccessScope() {
		return accessScope;
	}

	public void setAccessScope(String accessScope) {
		this.accessScope = accessScope;
	}

}
