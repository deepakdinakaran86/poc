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
package com.pcs.fms.web.services;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.RoleDTO;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class RoleService extends BaseService {

	@Value("${fms.services.createRole}")
	private String createRoleEndpointUri;
	
	@Value("${fms.services.findRole}")
	private String findRoleEndpointUri;
	

	/**
	 * Method to create role
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> createRole(RoleDTO roleDTO) {
		String createRoleServiceURI = getServiceURI(createRoleEndpointUri);
		return getPlatformClient().postResource(createRoleServiceURI, roleDTO,
				EntityDTO.class);
	}

	/**
	 * Method to create role
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<List<RoleDTO>> getRoles(String domainName) {
		String listRolesServiceURI = getServiceURI(createRoleEndpointUri);

		Type entityListType = new TypeToken<List<RoleDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		if (domainName != null) {
			return getPlatformClient().getResource(
					listRolesServiceURI.concat("?domain_name=" + domainName),
					entityListType);
		} else {
			return null;
		}
	}

	public FMSResponse<RoleDTO> getRole(String roleName, String domainName) {
		String findRoleServiceURI = getServiceURI(findRoleEndpointUri);
		
		Type entityListType = new TypeToken<RoleDTO>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		
		findRoleServiceURI = findRoleServiceURI.replace("{role_name}", roleName);
		findRoleServiceURI = findRoleServiceURI.replace("{domain_name}", domainName);
		
		if (domainName != null) {
			return getPlatformClient().getResource(
					findRoleServiceURI,
					entityListType);
		} else {
			return null;
		}
		
		
	}

}
