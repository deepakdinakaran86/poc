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
package com.pcs.alpine.service;

import com.pcs.alpine.dto.PermissionGroupsDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author Daniela (PCSEG191)
 * @date 25 Oct 2015
 * @since alpine-1.0.0
 */
public interface PermissionGroupService {

	public StatusMessageDTO createPermissionGroup(PermissionGroupsDTO permissionGroupsDTO);
	
	public StatusMessageDTO updatePermissionGroup(PermissionGroupsDTO permissionGroupsDTO);
	
	public PermissionGroupsDTO getPermissionGroup(String groupName, String domainName, Boolean isParentDomain);
	
	public StatusMessageDTO deletePermissionGroup(String groupName, String domainName);
	
	public PermissionGroupsDTO getResources(String domain, Boolean isParentDomain);

}
