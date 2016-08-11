/**
 * Copyright 2016 Pacific Controls Software Services LLC (PCSS). All Rights
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

import com.pcs.guava.commons.dto.DomainDTO;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.FieldMapDTO;
import com.pcs.guava.commons.dto.IdentityDTO;
import com.pcs.guava.commons.dto.StatusDTO;

/**
 * ServiceScheduleDTO
 * 
 * @description This class is responsible for the ServiceScheduleServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @date 09 May 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class ServiceScheduleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DomainDTO domain;

	private String serviceScheduleName;

	private String description;

	private String occuranceType;

	private List<IdentityDTO> serviceComponentIdentifiers;
	
	private List<EntityDTO> serviceComponentEntities;

	private FieldMapDTO serviceScheduleIdentifier;

	private StatusDTO serviceScheduleStatus;
	
	private List<String> serviceComponentNames;

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public String getServiceScheduleName() {
		return serviceScheduleName;
	}

	public void setServiceScheduleName(String serviceScheduleName) {
		this.serviceScheduleName = serviceScheduleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<IdentityDTO> getServiceComponentIdentifiers() {
		return serviceComponentIdentifiers;
	}

	public void setServiceComponentIdentifiers(List<IdentityDTO> serviceComponentIdentifiers) {
		this.serviceComponentIdentifiers = serviceComponentIdentifiers;
	}

	public FieldMapDTO getServiceScheduleIdentifier() {
		return serviceScheduleIdentifier;
	}

	public void setServiceScheduleIdentifier(
			FieldMapDTO serviceScheduleIdentifier) {
		this.serviceScheduleIdentifier = serviceScheduleIdentifier;
	}

	public StatusDTO getServiceScheduleStatus() {
		return serviceScheduleStatus;
	}

	public void setServiceScheduleStatus(StatusDTO serviceScheduleStatus) {
		this.serviceScheduleStatus = serviceScheduleStatus;
	}

	public String getOccuranceType() {
		return occuranceType;
	}

	public void setOccuranceType(String occuranceType) {
		this.occuranceType = occuranceType;
	}

	public List<String> getServiceComponentNames() {
		return serviceComponentNames;
	}

	public void setServiceComponentNames(List<String> serviceComponentNames) {
		this.serviceComponentNames = serviceComponentNames;
	}

	public List<EntityDTO> getServiceComponentEntities() {
		return serviceComponentEntities;
	}

	public void setServiceComponentEntities(List<EntityDTO> serviceComponentEntities) {
		this.serviceComponentEntities = serviceComponentEntities;
	}

}
