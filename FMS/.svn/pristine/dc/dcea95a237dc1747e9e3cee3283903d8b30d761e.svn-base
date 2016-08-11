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
 * Service Item DTO
 * 
 * @description DTO for Service Item
 * @author Suraj Sugathan (PCSEG362)
 * @date 09 May 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class ServiceItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DomainDTO domain;

	private String serviceItemName;

	private String description;

	private StatusDTO serviceItemStatus;

	private FieldMapDTO serviceItemIdentifier;
	
	private List<Tag> listOfTags;

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public String getServiceItemName() {
		return serviceItemName;
	}

	public void setServiceItemName(String serviceItemName) {
		this.serviceItemName = serviceItemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StatusDTO getServiceItemStatus() {
		return serviceItemStatus;
	}

	public void setServiceItemStatus(StatusDTO serviceItemStatus) {
		this.serviceItemStatus = serviceItemStatus;
	}

	public FieldMapDTO getServiceItemIdentifier() {
		return serviceItemIdentifier;
	}

	public void setServiceItemIdentifier(FieldMapDTO serviceItemIdentifier) {
		this.serviceItemIdentifier = serviceItemIdentifier;
	}

	public List<Tag> getListOfTags() {
		return listOfTags;
	}

	public void setListOfTags(List<Tag> listOfTags) {
		this.listOfTags = listOfTags;
	}

}
