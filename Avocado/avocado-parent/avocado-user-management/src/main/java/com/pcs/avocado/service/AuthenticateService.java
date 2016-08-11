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

import javax.servlet.http.HttpServletRequest;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.dto.UserCredentialsDTO;
import com.pcs.avocado.token.TokenInfoDTO;

/**
 * 
 * This is an interface for Authentication Service
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 13 January 2016
 * @since avocado-1.0.0
 */

public interface AuthenticateService {

	/**
	 * Service to Authenticate User Login Credentials
	 * 
	 * @param usercredentialsDTO
	 *            and request
	 */
	public TokenInfoDTO authenticate(UserCredentialsDTO userCredentialsDTO,
	        HttpServletRequest request);

	public StatusMessageDTO logout(UserCredentialsDTO userCredentialsDTO,
	        HttpServletRequest request);

}
