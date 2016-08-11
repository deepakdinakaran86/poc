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
 * 
 * DTO for roleNames and permissions of a user
 * 
 * @author pcseg288 (DEEPAK DINAKARAN)
 * @date November 2015
 * @since alpine-1.0.0
 */
public class Tenant implements Serializable {

	private static final long serialVersionUID = 5857552336377812611L;

	private String platformEntityType;

	private String domainName;

	private String entityTemplateName;

	private String tenantName;
	
	private String tenantId;
	
	private String currentDomain;

	public String getPlatformEntityType() {
		return platformEntityType;
	}

	public void setPlatformEntityType(String platformEntityType) {
		this.platformEntityType = platformEntityType;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getEntityTemplateName() {
		return entityTemplateName;
	}

	public void setEntityTemplateName(String entityTemplateName) {
		this.entityTemplateName = entityTemplateName;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCurrentDomain() {
		return currentDomain;
	}

	public void setCurrentDomain(String currentDomain) {
		this.currentDomain = currentDomain;
	}
	
}
