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

import java.util.List;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.dto.RoleDTO;
import com.pcs.avocado.dto.UserPermissionsDTO;



/**
 * 
 * This is an interface for Role Service
 * 
 * @author DEEPAK DINAKARN (PCSEG288)
 * @date 11 January 2016
 * @since avocado-1.0.0
 */
public interface RoleService {

	/**
	 * Service to Get Role permission of a role for a domain
	 * @param roleName
	 * @param domainName
	 * @return
	 */
	public RoleDTO getRole(String roleName, String domainName);

	/**
	 * SErvice to get Roles of a Domain
	 * @param domainName
	 * @return
	 */
	public List<RoleDTO> getRoles(String domainName);

	/**
	 * @param role
	 * @return
	 */
	public StatusMessageDTO saveRole(RoleDTO role);

	/**
	 * Service to Update a role of a domain
	 * @param roleName
	 * @param domainName
	 * @param roleDTO
	 * @return
	 */
	public StatusMessageDTO updateRole(String roleName, String domainName, RoleDTO roleDTO);

	/**
	 * Service to Delete a Role in a domain
	 * @param roleName
	 * @param domainName
	 * @return
	 */
	public StatusMessageDTO deleteRole(String roleName, String domainName);

	/**
	 * Service to Get Permission of Current User
	 * @return
	 */
	public UserPermissionsDTO getPermissionsOfCurrentUser();

}
