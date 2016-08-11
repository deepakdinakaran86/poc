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
package com.pcs.alpine.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;

/**
 * MarkerBatchDTO
 * 
 * @description DTO which sends entity information to the UI.
 * @author Daniela (PCSEG191)
 * @date 13 Apr 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement(name = "entity")
public class MarkerBatchDTO implements Serializable {

	private static final long serialVersionUID = -2049990530877806985L;

	private Boolean isParentDomain;

	private DomainDTO domain;

	private List<EntityDTO> entities;

	public Boolean getIsParentDomain() {
		return isParentDomain;
	}

	public void setIsParentDomain(Boolean isParentDomain) {
		this.isParentDomain = isParentDomain;
	}

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public List<EntityDTO> getEntities() {
		return entities;
	}

	public void setEntities(List<EntityDTO> entities) {
		this.entities = entities;
	}

}
