/**
 * 
 */
package com.pcs.data.platform.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author pcseg129
 *
 */
public class Entity implements Serializable {
	
	private static final long serialVersionUID = 2680898678885472314L;

	private String entityId;

	private GlobalEntity globalEntity;

	private Domain domain;

	private StatusMessage entityStatus;

	private String parentEntityId;

	private EntityTemplate entityTemplate;

	private List<KeyValueObject> fieldValues;

	private List<KeyValueObject> dataprovider;

	private KeyValueObject identifier;

	private KeyValueObject tree;

	private String uniqueUserId;

	private IdentityEntity hierarchy;

	private String dcSyncFlag;

	private Boolean isSelected;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public GlobalEntity getGlobalEntity() {
		return globalEntity;
	}

	public void setGlobalEntity(GlobalEntity globalEntity) {
		this.globalEntity = globalEntity;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public StatusMessage getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(StatusMessage entityStatus) {
		this.entityStatus = entityStatus;
	}

	public String getParentEntityId() {
		return parentEntityId;
	}

	public void setParentEntityId(String parentEntityId) {
		this.parentEntityId = parentEntityId;
	}

	public EntityTemplate getEntityTemplate() {
		return entityTemplate;
	}

	public void setEntityTemplate(EntityTemplate entityTemplate) {
		this.entityTemplate = entityTemplate;
	}

	public List<KeyValueObject> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<KeyValueObject> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public List<KeyValueObject> getDataprovider() {
		return dataprovider;
	}

	public void setDataprovider(List<KeyValueObject> dataprovider) {
		this.dataprovider = dataprovider;
	}

	public KeyValueObject getIdentifier() {
		return identifier;
	}

	public void setIdentifier(KeyValueObject identifier) {
		this.identifier = identifier;
	}

	public KeyValueObject getTree() {
		return tree;
	}

	public void setTree(KeyValueObject tree) {
		this.tree = tree;
	}

	public String getUniqueUserId() {
		return uniqueUserId;
	}

	public void setUniqueUserId(String uniqueUserId) {
		this.uniqueUserId = uniqueUserId;
	}

	public IdentityEntity getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(IdentityEntity hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getDcSyncFlag() {
		return dcSyncFlag;
	}

	public void setDcSyncFlag(String dcSyncFlag) {
		this.dcSyncFlag = dcSyncFlag;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	
}
