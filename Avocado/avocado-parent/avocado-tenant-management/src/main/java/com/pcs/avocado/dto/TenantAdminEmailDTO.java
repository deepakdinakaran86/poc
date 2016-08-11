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
package com.pcs.avocado.dto;

import java.io.Serializable;

/**
 * TenantAdminEmailDTO
 * 
 * @description DTO which sends entity information to the UI.
 * @author Daniela (PCSEG191)
 * @date 11 Jan 2016
 * @since galaxy-1.0.0
 */
public class TenantAdminEmailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8699174607874882305L;

	private String email;
	
	private String createTenantAdminUrl;
	
	private String tenantDomain;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateTenantAdminUrl() {
		return createTenantAdminUrl;
	}

	public void setCreateTenantAdminUrl(String createTenantAdminUrl) {
		this.createTenantAdminUrl = createTenantAdminUrl;
	}

	public String getTenantDomain() {
		return tenantDomain;
	}

	public void setTenantDomain(String tenantDomain) {
		this.tenantDomain = tenantDomain;
	}
}
