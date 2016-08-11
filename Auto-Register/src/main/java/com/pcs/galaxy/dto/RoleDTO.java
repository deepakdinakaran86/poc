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
package com.pcs.galaxy.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date JUNE 2016
 * @since GALAXY-1.0.0
 */
public class RoleDTO {
	private String roleName;
	private String newRoleName;
	private String domainName;
	private List<PermissionGroupsDTO> resources;
	private Boolean isParentDomain;

	public List<PermissionGroupsDTO> getResources() {
		if (resources == null) {
			resources = new ArrayList<PermissionGroupsDTO>();
		}
		return resources;
	}

	public void setResources(List<PermissionGroupsDTO> resources) {
		this.resources = resources;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getNewRoleName() {
		return newRoleName;
	}

	public void setNewRoleName(String newRoleName) {
		this.newRoleName = newRoleName;
	}

	public Boolean getIsParentDomain() {
		return isParentDomain;
	}

	public void setIsParentDomain(Boolean isParentDomain) {
		this.isParentDomain = isParentDomain;
	}

}
