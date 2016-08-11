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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.EntityDTO;
import com.pcs.web.dto.IdentityDTO;

/**
 * Menu related services
 * 
 * @author Deepak
 * @date 05-Oct-2015
 * @since galaxy-1.0.0
 * 
 */
@Service
public class UserService extends BaseService {

	@Value("${cummins.services.createUser}")
	private String createUserEndpointUri;

	@Value("${cummins.services.updateUser}")
	private String updateUserEndpointUri;

	@Value("${cummins.services.findUser}")
	private String findUserEndpointUri;

	@Value("${cummins.services.listUser}")
	private String listUserEndpointUri;

	@Value("${cummins.services.deleteUser}")
	private String deleteUserEndpointUri;	

	@Value("${cummins.services.createAdminUser}")
	private String createAdminUserEndpointUri;
	
	@Value("${cummins.services.createAdminUser}")
	private String validateUserEndpointUri;

	/**
	 * Method to create user
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> createUser(EntityDTO entityDTO) {
		String createUserServiceURI = getServiceURI(createUserEndpointUri);
		return getPlatformClient().postResource(createUserServiceURI,
		        entityDTO, EntityDTO.class);
	}
	
	/**
	 * Method to update user
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> updateUser(EntityDTO entityDTO) {
		String updateUserServiceURI = getServiceURI(updateUserEndpointUri);
		return getPlatformClient().putResource(updateUserServiceURI,
		        entityDTO, EntityDTO.class);
	}
	
	/**
	 * Method to get all users
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<List<EntityDTO>> getAllUsers() {
		String listUserServiceURI = getServiceURI(listUserEndpointUri);
		
		Type entityListType = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		
		return getPlatformClient().getResource(listUserServiceURI,
				entityListType);
	}

	/**
	 * Method to get a user
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> getUser(IdentityDTO identity) {
		String findUserServiceURI = getServiceURI(findUserEndpointUri);
		
		return getPlatformClient().postResource(findUserServiceURI,
				identity, EntityDTO.class);
	}
	
	/**
	 * Method to delete user
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> deleteUser(EntityDTO entityDTO) {
		String deleteUserServiceURI = getServiceURI(deleteUserEndpointUri);
		return getPlatformClient().postResource(deleteUserServiceURI,
		        entityDTO, EntityDTO.class);
	}
	
	/**
	 * Method to create admin user
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> createAdminUser(EntityDTO entityDTO) {
		String createAdminUserServiceURI = getServiceURI(createAdminUserEndpointUri);
		return getPlatformClient().postResource(createAdminUserServiceURI,
		        entityDTO, EntityDTO.class);
	}
	
	/**
	 * Method to validate a user
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> validateUser(IdentityDTO identity) {
		String validateUserServiceURI = getServiceURI(validateUserEndpointUri);
		
		Type entityListType = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		
		return getPlatformClient().postResource(validateUserServiceURI,
				identity, entityListType);
	}


}
