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
import com.pcs.guava.commons.dto.FieldMapDTO;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.StatusDTO;

/**
 * ServiceComponentDTO
 * 
 * @description This class is responsible for the ServiceComponentServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @date 09 May 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class ServiceComponentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DomainDTO domain;

	private String serviceComponentName;

	private String description;

	private String occuranceType;

	private String frequencyInDistance;

	private String frequencyInTime;

	private String frequencyInRunningHours;

	private String notificationInDistance;

	private String notificationInTime;

	private String notificationInRunningHours;

	private EntityDTO serviceItemEntity;
	
	private List<FieldMapDTO> serviceItemValues;

	private FieldMapDTO serviceComponentIdentifier;

	private StatusDTO serviceComponentStatus;

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public EntityDTO getServiceItemEntity() {
		return serviceItemEntity;
	}

	public void setServiceItemEntity(EntityDTO serviceItemEntity) {
		this.serviceItemEntity = serviceItemEntity;
	}

	public String getServiceComponentName() {
		return serviceComponentName;
	}

	public void setServiceComponentName(String serviceComponentName) {
		this.serviceComponentName = serviceComponentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOccuranceType() {
		return occuranceType;
	}

	public void setOccuranceType(String occuranceType) {
		this.occuranceType = occuranceType;
	}

	public FieldMapDTO getServiceComponentIdentifier() {
		return serviceComponentIdentifier;
	}

	public void setServiceComponentIdentifier(
			FieldMapDTO serviceComponentIdentifier) {
		this.serviceComponentIdentifier = serviceComponentIdentifier;
	}

	public StatusDTO getServiceComponentStatus() {
		return serviceComponentStatus;
	}

	public void setServiceComponentStatus(StatusDTO serviceComponentStatus) {
		this.serviceComponentStatus = serviceComponentStatus;
	}

	public String getFrequencyInDistance() {
		return frequencyInDistance;
	}

	public void setFrequencyInDistance(String frequencyInDistance) {
		this.frequencyInDistance = frequencyInDistance;
	}

	public String getFrequencyInTime() {
		return frequencyInTime;
	}

	public void setFrequencyInTime(String frequencyInTime) {
		this.frequencyInTime = frequencyInTime;
	}

	public String getFrequencyInRunningHours() {
		return frequencyInRunningHours;
	}

	public void setFrequencyInRunningHours(String frequencyInRunningHours) {
		this.frequencyInRunningHours = frequencyInRunningHours;
	}

	public String getNotificationInDistance() {
		return notificationInDistance;
	}

	public void setNotificationInDistance(String notificationInDistance) {
		this.notificationInDistance = notificationInDistance;
	}

	public String getNotificationInTime() {
		return notificationInTime;
	}

	public void setNotificationInTime(String notificationInTime) {
		this.notificationInTime = notificationInTime;
	}

	public String getNotificationInRunningHours() {
		return notificationInRunningHours;
	}

	public void setNotificationInRunningHours(String notificationInRunningHours) {
		this.notificationInRunningHours = notificationInRunningHours;
	}

	public List<FieldMapDTO> getServiceItemValues() {
		return serviceItemValues;
	}

	public void setServiceItemValues(List<FieldMapDTO> serviceItemValues) {
		this.serviceItemValues = serviceItemValues;
	}

}
