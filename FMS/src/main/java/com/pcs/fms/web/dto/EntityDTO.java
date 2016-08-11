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
package com.pcs.fms.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@XmlRootElement(name = "entity")
public class EntityDTO implements Serializable {

	private static final long serialVersionUID = -2049990530877806985L;

	private String entityId;

	private PlatformEntityDTO platformEntity;

	private DomainDTO domain;

	private StatusDTO entityStatus;

	private String parentEntityId;

	private EntityTemplateDTO entityTemplate;

	private List<FieldMapDTO> fieldValues;

	private List<FieldMapDTO> dataprovider;

	private List<FieldMapDTO> returnFields;

	private FieldMapDTO identifier;

	private FieldMapDTO tree;

	private String uniqueUserId;

	private IdentityDTO hierarchy;

	private String dcSyncFlag;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public PlatformEntityDTO getPlatformEntity() {
		return platformEntity;
	}

	public void setPlatformEntity(PlatformEntityDTO platformEntity) {
		this.platformEntity = platformEntity;
	}

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public StatusDTO getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(StatusDTO entityStatus) {
		this.entityStatus = entityStatus;
	}

	public String getParentEntityId() {
		return parentEntityId;
	}

	public void setParentEntityId(String parentEntityId) {
		this.parentEntityId = parentEntityId;
	}

	public EntityTemplateDTO getEntityTemplate() {
		return entityTemplate;
	}

	public void setEntityTemplate(EntityTemplateDTO entityTemplate) {
		this.entityTemplate = entityTemplate;
	}

	public List<FieldMapDTO> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<FieldMapDTO> fiedValues) {
		this.fieldValues = fiedValues;
	}

	public List<FieldMapDTO> getDataprovider() {
		return dataprovider;
	}

	public void setDataprovider(List<FieldMapDTO> dataprovider) {
		this.dataprovider = dataprovider;
	}

	public List<FieldMapDTO> getReturnFields() {
		return returnFields;
	}

	public void setReturnFields(List<FieldMapDTO> returnFields) {
		this.returnFields = returnFields;
	}

	public FieldMapDTO getIdentifier() {
		return identifier;
	}

	public void setIdentifier(FieldMapDTO identifier) {
		this.identifier = identifier;
	}

	public FieldMapDTO getTree() {
		return tree;
	}

	public void setTree(FieldMapDTO tree) {
		this.tree = tree;
	}

	public String getUniqueUserId() {
		return uniqueUserId;
	}

	public void setUniqueUserId(String uniqueUserId) {
		this.uniqueUserId = uniqueUserId;
	}

	public IdentityDTO getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(IdentityDTO hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getDcSyncFlag() {
		return dcSyncFlag;
	}

	public void setDcSyncFlag(String dcSyncFlag) {
		this.dcSyncFlag = dcSyncFlag;
	}

}
