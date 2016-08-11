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

import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.dto.SMSStatsDTO;
import com.pcs.avocado.dto.UserDTO;

/**
 * 
 * This is an interface for User Service
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 18 January 2016
 * @since avocado-1.0.0
 */
public interface UserService {

	/**
	 * Service for creating a user .This method will create payload for using in
	 * the ESB to create a user in the platform,and an email should be send to
	 * the corresponding user to set the password.
	 * 
	 * @param userDTO
	 * @return
	 */
	public EntityDTO createUser(UserDTO userDTO);

	/**
	 * Service for updating a User
	 * 
	 * @param userName
	 * @param domain
	 * @param userDTO
	 * @return
	 */
	public EntityDTO updateUser(String userName, String domain, UserDTO userDTO);

	/**
	 * Service to delete a User
	 * 
	 * @param userName
	 * @param domain
	 * @return
	 */
	public StatusMessageDTO deleteUser(String userName, String domain);

	/**
	 * Service to get a User
	 * 
	 * @param userName
	 * @param domain
	 * @return
	 */
	public EntityDTO getUser(String userName, String domain);

	/**
	 * Service to get the List of Users in a Domain
	 * 
	 * @param domain
	 * @return
	 */
	public List<EntityDTO> getUsers(String domainName);

	/**
	 * Service to Reset a Users Password providing email id a Mail is sent to
	 * the email address of the user
	 * 
	 * @param user
	 * @return
	 */
	public StatusMessageDTO resetPassword(UserDTO user);

	/**
	 * Service to Reset a Users Password when the user the forgot password
	 * service, by providing email id a Mail is sent to the email address of the
	 * user
	 * 
	 * @param user
	 * @return
	 */
	public StatusMessageDTO forgotPassword(UserDTO user);

	/**
	 * Service to Change a Users Password
	 * 
	 * @param user
	 * @return
	 */
	public StatusMessageDTO changePassword(UserDTO user);
	
	/**
	 * @Description Receive an sms
	 * @param SMSDTO
	 * @return SMSStatsDTO
	 */
	public List<SMSStatsDTO> getSMStatistics();

}
