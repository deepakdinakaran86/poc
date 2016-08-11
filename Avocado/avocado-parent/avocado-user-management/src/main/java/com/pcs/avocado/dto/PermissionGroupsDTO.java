
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.avocado.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.pcs.avocado.commons.dto.DomainDTO;



//import com.pcs.alpine.services.dto.DomainDTO;

/**  
 *  ResourceDTO
 * 
 *  @description  DTO which sends status message information to the UI.
 *  @author       Daniela (pcseg191)
 *  @date         25 Oct 2015
 *  @since        avocado-1.0.0
 */
@XmlRootElement
public class PermissionGroupsDTO {
	
	private String resourceName;

	private List<String> permissions;
	
	private DomainDTO domain;
	
	private List<String> resources;
	
	private String newResourceName;

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public List<String> getResources() {
		return resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	public String getNewResourceName() {
		return newResourceName;
	}

	public void setNewResourceName(String newResourceName) {
		this.newResourceName = newResourceName;
	}
	
	

}
