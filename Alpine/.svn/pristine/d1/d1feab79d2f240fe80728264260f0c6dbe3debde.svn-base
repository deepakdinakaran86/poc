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

import java.util.List;

import com.pcs.alpine.dto.UserDTO;
import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityStatusCountDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since alpine-1.0.0
 */
public interface UserService {

	public EntityDTO getUser(IdentityDTO userIdentifier);

	public List<EntityDTO> getUsers(String domainName, Boolean isParentDomain);

	public EntityDTO saveUser(EntityDTO user);

	public EntityDTO updateUser(EntityDTO user);

	public StatusMessageDTO deleteUser(IdentityDTO userIdentifier);

	public EntityDTO saveAdminUser(EntityDTO user);
	
	public StatusMessageDTO validateUniqueness(EntitySearchDTO user);
	
	public EntityCountDTO getUserCount(EntitySearchDTO userSearch);
	
	public StatusMessageDTO resetPassword(UserDTO user);
	
	public List<EntityStatusCountDTO> getUserCountByStatus(EntitySearchDTO userSearch);
	
	public UserDTO forgotPassword(UserDTO user);
	
	public StatusMessageDTO changePassword(UserDTO user);

}
