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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author pcseg199
 * 
 */
@XmlRootElement
public class HierarchySearchDTO {

	private IdentityDTO parentIdentity;

	private IdentityDTO actor;

	private String searchTemplateName;

	private String searchEntityType;

	public IdentityDTO getParentIdentity() {
		return parentIdentity;
	}

	public void setParentIdentity(IdentityDTO parentIdentity) {
		this.parentIdentity = parentIdentity;
	}

	public IdentityDTO getActor() {
		return actor;
	}

	public void setActor(IdentityDTO actor) {
		this.actor = actor;
	}

	public String getSearchTemplateName() {
		return searchTemplateName;
	}

	public void setSearchTemplateName(String searchTemplateName) {
		this.searchTemplateName = searchTemplateName;
	}

	public String getSearchEntityType() {
		return searchEntityType;
	}

	public void setSearchEntityType(String searchEntityType) {
		this.searchEntityType = searchEntityType;
	}

}
