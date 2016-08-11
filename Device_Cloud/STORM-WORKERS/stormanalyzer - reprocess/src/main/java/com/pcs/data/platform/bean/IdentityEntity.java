/**
 * 
 */
package com.pcs.data.platform.bean;

import java.io.Serializable;

/**
 * @author pcseg129
 *
 */
public class IdentityEntity implements Serializable {
	
	private static final long serialVersionUID = -5233143255707536403L;
	
	private GlobalEntity globalEntity;
	private Domain domain;
	private EntityTemplate entityTemplate;
	private KeyValueObject identifier;

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

	public EntityTemplate getEntityTemplate() {
		return entityTemplate;
	}

	public void setEntityTemplate(EntityTemplate entityTemplate) {
		this.entityTemplate = entityTemplate;
	}

	public KeyValueObject getIdentifier() {
		return identifier;
	}

	public void setIdentifier(KeyValueObject identifier) {
		this.identifier = identifier;
	}

}
