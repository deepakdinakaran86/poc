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
package com.pcs.avocado.commons.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * SearchFieldsDTO
 * 
 * @description DTO which sends status message information to the UI.
 * @author Daniela (pcseg191)
 * @date 10 Oct 2015
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class SearchFieldsDTO {

	private EntityTemplateDTO entityTemplate;

	private List<String> returnFields;

	private List<FieldMapDTO> searchFields;

	private DomainDTO domain;

	private StatusDTO status;

	public List<String> getReturnFields() {
		return returnFields;
	}

	public void setReturnFields(List<String> returnFields) {
		this.returnFields = returnFields;
	}

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public List<FieldMapDTO> getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(List<FieldMapDTO> searchFields) {
		this.searchFields = searchFields;
	}

	public EntityTemplateDTO getEntityTemplate() {
		return entityTemplate;
	}

	public void setEntityTemplate(EntityTemplateDTO entityTemplate) {
		this.entityTemplate = entityTemplate;
	}

	public StatusDTO getStatus() {
	    return status;
    }

	public void setStatus(StatusDTO status) {
	    this.status = status;
    }

}
