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
package com.pcs.alpine.is.client.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.pcs.alpine.services.dto.EntityDTO;

/**
 * DTO for Domain
 *
 * @author pcseg292 Renjith P
 * @author pcseg288 Deepak Dinakaran
 */
@XmlRootElement(name = "domain")
public class DomainDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String domainLabel;
	private String domainName;
	private String type;
	private String typeId;
	private String domainUrl;
	private String maxConcurrentUsers;
	private String maxUsers;
	private String status;
	//private List<FeatureGroupDTO> featureGroups;
	//private List<MenuDTO> menu;
	private String remainingMaxUsers;
	private String uniqueUserId;
	private EntityDTO entity;
	private String parentDomain;
	private String loggedInUserDomain;

	private String entityId;
	
	//tenant info required
//	private Tenant userDTO;

	public DomainDTO() {
	}

	public String getDomainLabel() {
		return domainLabel;
	}

	public void setDomainLabel(String domainLabel) {
		this.domainLabel = domainLabel;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

//	public List<FeatureGroupDTO> getFeatureGroups() {
//		return featureGroups;
//	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public String getMaxConcurrentUsers() {
		return maxConcurrentUsers;
	}

	public void setMaxConcurrentUsers(String maxConcurrentUsers) {
		this.maxConcurrentUsers = maxConcurrentUsers;
	}

	public String getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(String maxUsers) {
		this.maxUsers = maxUsers;
	}

//	public void setFeatureGroups(List<FeatureGroupDTO> featureGroups) {
//		this.featureGroups = featureGroups;
//	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

//	public List<MenuDTO> getMenu() {
//		return menu;
//	}

//	public void setMenu(List<MenuDTO> menu) {
//		this.menu = menu;
//	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getRemainingMaxUsers() {
		return remainingMaxUsers;
	}

	public void setRemainingMaxUsers(String remainingMaxUsers) {
		this.remainingMaxUsers = remainingMaxUsers;
	}

	public String getUniqueUserId() {
		return uniqueUserId;
	}

	public void setUniqueUserId(String uniqueUserId) {
		this.uniqueUserId = uniqueUserId;
	}



	public String getParentDomain() {
	    return parentDomain;
    }

	public void setParentDomain(String parentDomain) {
	    this.parentDomain = parentDomain;
    }

	public EntityDTO getEntity() {
	    return entity;
    }

	public void setEntity(EntityDTO entity) {
	    this.entity = entity;
    }

	public String getLoggedInUserDomain() {
	    return loggedInUserDomain;
    }

	public void setLoggedInUserDomain(String loggedInUserDomain) {
	    this.loggedInUserDomain = loggedInUserDomain;
    }

}
