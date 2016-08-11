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
package com.pcs.ccd.heartbeat.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * ReturnFieldsDTO
 * 
 * @description DTO which sends the search result response
 * @author Daniela (pcseg191)
 * @date 07 Oct 2015
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class ReturnFieldsDTO {

	private GlobalEntityDTO globalEntity;

	private DomainDTO domain;

	private EntityTemplateDTO entityTemplate;

	private FieldMapDTO identifier;

	private List<FieldMapDTO> returnFields;

	private List<FieldMapDTO> dataprovider;

	private StatusDTO entityStatus;

	public List<FieldMapDTO> getReturnFields() {
		return returnFields;
	}

	public void setReturnFields(List<FieldMapDTO> returnFields) {
		this.returnFields = returnFields;
	}

	public GlobalEntityDTO getGlobalEntity() {
		return globalEntity;
	}

	public void setGlobalEntity(GlobalEntityDTO globalEntity) {
		this.globalEntity = globalEntity;
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

	public List<FieldMapDTO> getDataprovider() {
		return dataprovider;
	}

	public void setDataprovider(List<FieldMapDTO> dataprovider) {
		this.dataprovider = dataprovider;
	}

	public StatusDTO getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(StatusDTO entityStatus) {
		this.entityStatus = entityStatus;
	}

}
