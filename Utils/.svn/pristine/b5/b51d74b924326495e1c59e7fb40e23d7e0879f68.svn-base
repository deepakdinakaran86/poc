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
package com.pcs.alpine.token;

import java.io.Serializable;

/**
 * IdentityDTO
 * 
 * @description IdentityDTO
 * @author Daniela (PCSEG191)
 * @since galaxy-1.0.0
 */
public class IdentityDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DomainDTO domain;
	private EntityTemplateDTO entityTemplate;
	private PlatformEntityDTO platformEntity;
	private FieldMapDTO identifier;

	public IdentityDTO() {

	}

	public IdentityDTO(EntityDTO entityDTO) {
		this.domain = entityDTO.getDomain();
		this.entityTemplate = entityDTO.getEntityTemplate();
		this.platformEntity = entityDTO.getPlatformEntity();
		this.identifier = entityDTO.getIdentifier();
	}

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

	public FieldMapDTO getIdentifier() {
		return identifier;
	}

	public void setIdentifier(FieldMapDTO identifier) {
		this.identifier = identifier;
	}

	public PlatformEntityDTO getPlatformEntity() {
		return platformEntity;
	}

	public void setPlatformEntity(PlatformEntityDTO platformEntity) {
		this.platformEntity = platformEntity;
	}

}