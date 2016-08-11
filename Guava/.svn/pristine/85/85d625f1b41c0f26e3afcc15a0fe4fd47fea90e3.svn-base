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
package com.pcs.guava.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.IdentityDTO;

/**
 * Service Schedule DTO
 *
 * @description DTO for Service Schedule
 * @author Suraj Sugathan (PCSEG362)
 * @date 10 May 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class ServiceScheduleESBDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityDTO serviceItem;
	
	private EntityDTO serviceComponent;
	
	private EntityDTO serviceSchedule;
	
	private List<IdentityDTO> serviceComponentIdentifiers;
	
	private List<Tag> listOfTags;
	
	public EntityDTO getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(EntityDTO serviceItem) {
		this.serviceItem = serviceItem;
	}

	public EntityDTO getServiceComponent() {
		return serviceComponent;
	}

	public void setServiceComponent(EntityDTO serviceComponent) {
		this.serviceComponent = serviceComponent;
	}

	public EntityDTO getServiceSchedule() {
		return serviceSchedule;
	}

	public void setServiceSchedule(EntityDTO serviceSchedule) {
		this.serviceSchedule = serviceSchedule;
	}

	public List<IdentityDTO> getServiceComponentIdentifiers() {
		return serviceComponentIdentifiers;
	}

	public void setServiceComponentIdentifiers(List<IdentityDTO> serviceComponentIdentifiers) {
		this.serviceComponentIdentifiers = serviceComponentIdentifiers;
	}

	public List<Tag> getListOfTags() {
		return listOfTags;
	}

	public void setListOfTags(List<Tag> listOfTags) {
		this.listOfTags = listOfTags;
	}

}
