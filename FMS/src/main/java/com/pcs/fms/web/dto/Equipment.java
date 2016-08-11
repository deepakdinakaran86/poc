package com.pcs.fms.web.dto;

import java.io.Serializable;

public class Equipment implements Serializable {

	private static final long serialVersionUID = -6764529651188105693L;

	private DomainDTO domain;

	private EntityTemplateDTO entityTemplate;

	private PlatformEntityDTO platformEntity;

	private FieldMapDTO identifier;

	private String equipName;

	public DomainDTO getDomain() {
	    return domain;
    }

	public void setDomain(DomainDTO domain) {
	    this.domain = domain;
    }

	public EntityTemplateDTO getEntityTemplate() {
	    return entityTemplate;
    }

	public void setEntityTemplate(EntityTemplateDTO entityTemplate) {
	    this.entityTemplate = entityTemplate;
    }

	public PlatformEntityDTO getPlatformEntity() {
	    return platformEntity;
    }

	public void setPlatformEntity(PlatformEntityDTO platformEntity) {
	    this.platformEntity = platformEntity;
    }

	public FieldMapDTO getIdentifier() {
	    return identifier;
    }

	public void setIdentifier(FieldMapDTO identifier) {
	    this.identifier = identifier;
    }

	public String getEquipName() {
	    return equipName;
    }

	public void setEquipName(String equipName) {
	    this.equipName = equipName;
    }

}
