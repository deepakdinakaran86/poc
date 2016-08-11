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
package com.pcs.avocado.service;

import com.pcs.avocado.dto.PermissionGroupsDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;

/**
 * 
 * This is an interface for PermissionGroupService
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 13 Jan 2016
 * @since avocado-1.0.0
 */
public interface PermissionGroupService {

	/**
	 * Service used to create a permission group and subscribe to the permission groups
	 * 
	 * @param PermissionGroupsDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO createPermissionGroup(PermissionGroupsDTO permissionGroupsDTO);
	
	/**
	 * Service to update a permission group
	 * 
	 * @param PermissionGroupsDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO updatePermissionGroup(PermissionGroupsDTO permissionGroupsDTO);
	
	/**
	 * Service used to get a permission group
	 * 
	 * @param groupName
	 *            , domainName
	 * 
	 * @return StatusMessageDTO
	 */
	public PermissionGroupsDTO getPermissionGroup(String groupName, String domainName);
	
	/**
	 * Service to Delete Permissions of a group in a domain 
	 * @param groupName
	 * @param domainName
	 * @return
	 */
	public StatusMessageDTO deletePermissionGroup(String groupName, String domainName);
	
	/**
	 * Service to get Resources of a domain
	 * @param domain
	 * @return
	 */
	public PermissionGroupsDTO getResources(String domain);

}
