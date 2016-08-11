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
package com.pcs.web.services;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.pcs.alpine.dto.RoleDto;
import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.StatusMessageDTO;

/**
 * AssetService
 * 
 * @author Twinkle
 * @date October 2015
 * @since galaxy-1.0.0
 * 
 */
@Service
public class RoleService extends BaseService {

	@Autowired
	private DeviceService deviceService;

	@Value("${cummins.services.createRole}")
	private String createRoleEnpointUri;

	@Value("${cummins.services.findRole}")
	private String getRoleEnpointUri;

	@Value("${cummins.services.listRole}")
	private String getRolesEnpointUri;

	@Value("${cummins.services.updateRole}")
	private String updateRoleEndpointUri;

	@Value("${cummins.services.deleteRole}")
	private String deleteRoleEndpointUri;

	/**
	 * Method to list Assets
	 */
	public CumminsResponse<List<RoleDto>> viewAllRoles() {
		String getResourcesServiceUri = getServiceURI(getRolesEnpointUri);

		Type rolesType = new TypeToken<List<RoleDto>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		return getPlatformClient().getResource(getResourcesServiceUri,
		        rolesType);
	}

	/**
	 * Method to view Asset details
	 */
	public CumminsResponse<RoleDto> viewRoleDetails(String roleName) {
		String viewRoleServiceURI = getServiceURI(getRoleEnpointUri);
		viewRoleServiceURI = viewRoleServiceURI.replace("{role_name}",
		        roleName);
		return getPlatformClient().getResource(viewRoleServiceURI,
		        RoleDto.class);
	}

	/**
	 * Method to create Resource
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<RoleDto> updateRole(RoleDto role) {
		String roleUpdateServiceURI = getServiceURI(updateRoleEndpointUri);
		return getPlatformClient().putResource(roleUpdateServiceURI, role,
		        RoleDto.class);
	}

	/**
	 * Method to create Resource
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<RoleDto> createRole(RoleDto role) {
		String roleCreateerviceURI = getServiceURI(createRoleEnpointUri);
		return getPlatformClient().postResource(roleCreateerviceURI, role,
		        RoleDto.class);
	}

	/**
	 * Method to view Asset details
	 */
	public CumminsResponse<StatusMessageDTO> deleteRole(String roleName) {
		String deleteRoleServiceURI = getServiceURI(deleteRoleEndpointUri);
		deleteRoleServiceURI = deleteRoleServiceURI.replace("{role_name}",
		        roleName);
		return getPlatformClient().deleteResource(deleteRoleServiceURI,
		        StatusMessageDTO.class);
	}
}
