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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.PermissionGroupsDTO;

/**
 * AssetService
 * 
 * @author Twinkle
 * @date October 2015
 * @since galaxy-1.0.0
 * 
 */
@Service
public class ResourceService extends BaseService {

	@Autowired
	private DeviceService deviceService;

	@Value("${cummins.services.createResouce}")
	private String createResourceEnpointUri;

	@Value("${cummins.services.findResouce}")
	private String getResourceEnpointUri;

	@Value("${cummins.services.listResouce}")
	private String getResourcesEnpointUri;

	@Value("${cummins.services.listUser}")
	private String listUserEndpointUri;

	public CumminsResponse<PermissionGroupsDTO> viewAllResources() {
		String getResourcesServiceUri = getServiceURI(getResourcesEnpointUri);

		return getPlatformClient().getResource(getResourcesServiceUri,
		        PermissionGroupsDTO.class);
	}

	/**
	 * Method to view Asset details
	 */
	public CumminsResponse<PermissionGroupsDTO> viewResourceDetails(
	        String groupName) {
		String viewResourceServiceURI = getServiceURI(getResourceEnpointUri);
		viewResourceServiceURI = viewResourceServiceURI.replace("{group_name}",
		        groupName);
		return getPlatformClient().getResource(viewResourceServiceURI,
		        PermissionGroupsDTO.class);
	}

	/**
	 * Method to create Resource
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<PermissionGroupsDTO> createPermissionGroup(
	        PermissionGroupsDTO permissionGroup) {
		String createPGServiceURI = getServiceURI(createResourceEnpointUri);
		return getPlatformClient().postResource(createPGServiceURI,
		        permissionGroup, PermissionGroupsDTO.class);
	}

}
