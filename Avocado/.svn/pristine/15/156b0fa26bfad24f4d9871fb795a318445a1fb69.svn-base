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
import java.util.List;

import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.StatusDTO;
import com.pcs.avocado.commons.email.dto.EmailDTO;

/**
 * EntityDTO
 * 
 * @description DTO which sends entity information to the UI.
 * @author Daniela (PCSEG191)
 * @date 11 Jan 2016
 * @since galaxy-1.0.0
 */
public class TenantAdminDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8699174607874882305L;

	private StatusDTO entityStatus;
	
	private List<FieldMapDTO> fieldValues;
	
	private String tenantAdminLinkIdentifier;
	
	private String setPasswordUrl;
	
	private EntityDTO resetPswd;
	
	private IdentityDTO tenantAdminTemplateIdentifier;
	
	private DomainDTO domain;
	
	private EmailDTO email;

	public StatusDTO getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(StatusDTO entityStatus) {
		this.entityStatus = entityStatus;
	}

	public List<FieldMapDTO> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<FieldMapDTO> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public String getTenantAdminLinkIdentifier() {
		return tenantAdminLinkIdentifier;
	}

	public void setTenantAdminLinkIdentifier(String tenantAdminLinkIdentifier) {
		this.tenantAdminLinkIdentifier = tenantAdminLinkIdentifier;
	}

	public String getSetPasswordUrl() {
		return setPasswordUrl;
	}

	public void setSetPasswordUrl(String setPasswordUrl) {
		this.setPasswordUrl = setPasswordUrl;
	}

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public IdentityDTO getTenantAdminTemplateIdentifier() {
		return tenantAdminTemplateIdentifier;
	}

	public void setTenantAdminTemplateIdentifier(
			IdentityDTO tenantAdminTemplateIdentifier) {
		this.tenantAdminTemplateIdentifier = tenantAdminTemplateIdentifier;
	}

	public EntityDTO getResetPswd() {
		return resetPswd;
	}

	public void setResetPswd(EntityDTO resetPswd) {
		this.resetPswd = resetPswd;
	}

	public EmailDTO getEmail() {
		return email;
	}

	public void setEmail(EmailDTO email) {
		this.email = email;
	}
}
