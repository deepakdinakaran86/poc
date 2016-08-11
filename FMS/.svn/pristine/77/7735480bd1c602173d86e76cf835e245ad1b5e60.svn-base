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

import static com.pcs.fms.web.constants.FMSWebConstants.EMAIL_ID;
import static com.pcs.fms.web.constants.FMSWebConstants.ENTITY_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.VALID_FIELD;

import java.lang.reflect.Type;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.EmailDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.StatusDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.dto.UserDTO;
import com.pcs.fms.web.model.User;
import com.pcs.fms.web.model.UserCredentials;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class UserService extends BaseService {

	@Value("${fms.services.createUser}")
	private String createUserEndpointUri;

	@Value("${fms.services.updateUser}")
	private String updateUserEndpointUri;

	@Value("${fms.services.findUser}")
	private String findUserEndpointUri;

	@Value("${fms.services.listUser}")
	private String listUserEndpointUri;

	@Value("${fms.services.deleteUser}")
	private String deleteUserEndpointUri;

	@Value("${fms.services.createAdminUser}")
	private String createAdminUserEndpointUri;

	@Value("${fms.services.createAdminUser}")
	private String validateUserEndpointUri;

	@Value("${fms.services.updatePassword}")
	private String updatePasswordEndpointUri;

	@Value("${fms.services.insertUserEntryResetPwd}")
	private String insertEntryResetPwdUri;

	@Value("${fms.services.sendResetPWDEmail}")
	private String sendResetPWDEmailUrl;

	@Value("${fms.services.findDevice}")
	private String findResetPwdEnity;

	@Value("${fms.services.findUserPasswordLink}")
	private String resetPwdEnityUrl;

	@Value("${fms.services.user.reset.password}")
	private String resetPwdUrl;
	
	@Value("${fms.services.forgotPassword}")
	private String forgotPasswordURL;
	

	/**
	 * Method to create user
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> createUser(UserDTO userDTO) {
		String createUserServiceURI = getServiceURI(createUserEndpointUri);
		return getPlatformClient().postResource(createUserServiceURI, userDTO,
				EntityDTO.class);
	}

	/**
	 * Method to update user
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> updateUser(UserDTO userDTO) {
		String updateUserServiceURI = getServiceURI(updateUserEndpointUri);

		updateUserServiceURI = updateUserServiceURI.replace("{user_name}",
				userDTO.getUserName());
		updateUserServiceURI = updateUserServiceURI.replace("{domain_name}",
				userDTO.getDomain());

		return getPlatformClient().putResource(updateUserServiceURI, userDTO,
				EntityDTO.class);
	}

	/**
	 * Method to get all users
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<List<EntityDTO>> getAllUsers(String domainName) {
		String listUserServiceURI = getServiceURI(listUserEndpointUri);

		Type entityListType = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		if (domainName != null) {
			return getPlatformClient().getResource(
					listUserServiceURI.concat("?domain_name=" + domainName),
					entityListType);
		} else {
			return getPlatformClient().getResource(listUserServiceURI,
					entityListType);
		}
	}

	/**
	 * Method to get a user
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> getUser(String userName, String domainName) {
		String findUserServiceURI = getServiceURI(findUserEndpointUri);

		findUserServiceURI = findUserServiceURI
				.replace("{user_name}", userName);
		findUserServiceURI = findUserServiceURI.replace("{domain_name}",
				domainName);

		Type entityType = new TypeToken<EntityDTO>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		return getPlatformClient().getResource(findUserServiceURI, entityType);
	}

	/**
	 * Method to delete user
	 * 
	 * @param domainName2
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> deleteUser(String userName, String domainName) {
		String deleteUserServiceURI = getServiceURI(deleteUserEndpointUri);

		deleteUserServiceURI = deleteUserServiceURI.replace("{user_name}",
				userName);
		deleteUserServiceURI = deleteUserServiceURI.replace("{domain_name}",
				domainName);

		return getPlatformClient().deleteResource(deleteUserServiceURI, null,
				StatusDTO.class);
	}

	/**
	 * Method to create admin user
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> createAdminUser(EntityDTO entityDTO) {
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
	public FMSResponse<EntityDTO> validateUser(IdentityDTO identity) {
		String validateUserServiceURI = getServiceURI(validateUserEndpointUri);

		Type entityListType = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		return getPlatformClient().postResource(validateUserServiceURI,
				identity, entityListType);
	}

	/**
	 * Method to update password
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<StatusMessageDTO> updatePassword(
			UserCredentials userCredentials) {
		String updateUserServiceURI = getServiceURI(updatePasswordEndpointUri);
		return getPlatformClient().postResource(updateUserServiceURI,
				userCredentials, StatusMessageDTO.class);
	}

	/**
	 * Method to insert user entry in ResetPassword template
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> insertEntryResetPwd(EntityDTO entityDTO) {
		String insertEntryResetPwdURI = getServiceURI(insertEntryResetPwdUri);
		return getPlatformClient().postResource(insertEntryResetPwdURI,
				entityDTO, EntityDTO.class);
	}

	/**
	 * Method to insert user entry in ResetPassword template
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<StatusMessageDTO> sendResetPWDEmail(EmailDTO email) {
		String sendResetPWDEmailServerUrl = getServiceURI(sendResetPWDEmailUrl);
		return getPlatformClient().postResource(sendResetPWDEmailServerUrl,
				email, StatusMessageDTO.class);
	}

	/**
	 * Method to get a user
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> getResetPwdEntity(IdentityDTO identity) {
		String findUserServiceURI = getServiceURI(findResetPwdEnity);

		return getPlatformClient().postResource(findUserServiceURI, identity,
				EntityDTO.class);
	}

	/**
	 * Method to insert user entry in ResetPassword template
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> updateEntryResetPwd(EntityDTO entityDTO) {
		String insertEntryResetPwdURI = getServiceURI(insertEntryResetPwdUri);
		return getPlatformClient().putResource(insertEntryResetPwdURI,
				entityDTO, EntityDTO.class);
	}

	public String getUserName(IdentityDTO identityDTO) {
		String createResetPwdURI = getServiceURI(resetPwdEnityUrl);
		FMSResponse<EntityDTO> linkEntity = getPlatformClient().postResource(
				createResetPwdURI, identityDTO, EntityDTO.class);
		String userName = null;
		if (linkEntity.getEntity() != null) {
			FieldMapDTO userNameField = new FieldMapDTO();
			userNameField.setKey(ENTITY_NAME);

			// Check if link is valid
			FieldMapDTO validField = new FieldMapDTO();
			validField.setKey(VALID_FIELD);
			String isLinkValid = fetchField(
					linkEntity.getEntity().getFieldValues(), validField)
					.getValue();

			// If link is valid fetch email
			if (isLinkValid.equalsIgnoreCase("true")) {
				userName = fetchField(linkEntity.getEntity().getFieldValues(),
						userNameField).getValue();
			}
		}
		return userName;
	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
			FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();

		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	public FMSResponse<StatusMessageDTO> setUserPassword(User user) {
		String createResetPwdURL = getServiceURI(resetPwdUrl);
		UserDTO userDTO = new UserDTO();

		// Popoulate values for reset password
		userDTO.setUserName(user.getUserName());
		userDTO.setDomain(user.getDomain());
		userDTO.setResetPasswordIdentifierLink(user
				.getResetPasswordIdentifierLink());
		userDTO.setPassword(user.getPassword());

		return getPlatformClient().postResource(createResetPwdURL, userDTO,
				StatusMessageDTO.class);

	}

	public FMSResponse<StatusMessageDTO> forgotPassword(UserDTO userDTO) {
		String createforgotPasswordURL = getServiceURI(forgotPasswordURL);

		return getPlatformClient().postResource(createforgotPasswordURL, userDTO,
				StatusMessageDTO.class);

	}

}
