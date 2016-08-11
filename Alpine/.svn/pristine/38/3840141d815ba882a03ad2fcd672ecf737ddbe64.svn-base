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

import java.util.List;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcs.alpine.model.udt.FieldMap;

/**
 * Entity
 * 
 * @description POJO for entity
 * @author Daniela (pcseg191)
 * @param <FieldMap>
 * @date 11 Aug 2014
 * @updated 10 May 2016
 * @since galaxy-1.0.0
 */

@Table(name = "entity")
public class Entity {

	@Column(name = "domain")
	private String domain;

	@JsonProperty("entity_template_name")
	@Column(name = "entity_template_name")
	private String entityTemplateName;

	@JsonProperty("platform_entity_type")
	@Column(name = "platform_entity_type")
	private String platformEntityType;

	@JsonProperty("status_key")
	@Column(name = "status_key")
	private Integer statusKey;

	@JsonProperty("entity_id")
	@Column(name = "entity_id")
	private String entityId;

	@Column(name = "dataprovider")
	private List<FieldMap> dataprovider;

	@JsonProperty("domain_type")
	@Column(name = "domain_type")
	private String domainType;

	@JsonProperty("field_values")
	@Column(name = "field_values")
	private List<FieldMap> fieldValues;

	@Column(name = "identifier")
	private FieldMap identifier;

	@Column(name = "status_name")
	@JsonProperty("status_name")
	private String statusName;

	@Column(name = "tree")
	private FieldMap tree;

	@Column(name = "access_scope")
	@JsonProperty("access_scope")
	private String accessScope;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEntityTemplateName() {
		return entityTemplateName;
	}

	public void setEntityTemplateName(String entityTemplateName) {
		this.entityTemplateName = entityTemplateName;
	}

	public String getPlatformEntityType() {
		return platformEntityType;
	}

	public void setPlatformEntityType(String platformEntityType) {
		this.platformEntityType = platformEntityType;
	}

	public Integer getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(Integer statusKey) {
		this.statusKey = statusKey;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public List<FieldMap> getDataprovider() {
		return dataprovider;
	}

	public void setDataprovider(List<FieldMap> dataprovider) {
		this.dataprovider = dataprovider;
	}

	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	public List<FieldMap> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<FieldMap> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public FieldMap getIdentifier() {
		return identifier;
	}

	public void setIdentifier(FieldMap identifier) {
		this.identifier = identifier;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public FieldMap getTree() {
		return tree;
	}

	public void setTree(FieldMap tree) {
		this.tree = tree;
	}

	public String getAccessScope() {
		return accessScope;
	}

	public void setAccessScope(String accessScope) {
		this.accessScope = accessScope;
	}

}
