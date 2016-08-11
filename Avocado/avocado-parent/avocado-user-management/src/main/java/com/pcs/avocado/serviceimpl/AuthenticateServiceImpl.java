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
package com.pcs.avocado.serviceimpl;

import static com.pcs.avocado.token.TokenManager.getToken;
import static com.pcs.avocado.token.TokenManager.revoke;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.commons.service.SubscriptionProfileService;
import com.pcs.avocado.dto.UserCredentialsDTO;
import com.pcs.avocado.dto.UserToken;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.service.AuthenticateService;
import com.pcs.avocado.token.TokenInfoDTO;

/**
 * 
 * This class is responsible for Role Service Implementation
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 9 January 2016
 * @since avocado-1.0.0
 */
@Service
public class AuthenticateServiceImpl implements AuthenticateService {

	@Value("${apimanager.clientid}")
	private String clientId;

	@Value("${apimanager.clientSecret}")
	private String clientSecret;

	@Autowired
	private SubscriptionProfileService subscriptionProfileService;

	@Override
	public TokenInfoDTO authenticate(UserCredentialsDTO userCredentialsDTO,
	        HttpServletRequest request) {
		boolean credentialsBlank = false;
		TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
		try {
			if (isNotBlank(userCredentialsDTO.getUserName())
			        && isNotBlank(userCredentialsDTO.getPassword())) {
				userCredentialsDTO
				        .setUserName(userCredentialsDTO.getUserName());

				UserToken userToken = new UserToken();
				tokenInfoDTO = getToken(clientId, clientSecret,
				        userCredentialsDTO.getUserName(),
				        userCredentialsDTO.getPassword(), request.getSession()
				                .getId());
				userToken.setAccessToken(tokenInfoDTO.getAccessToken());

			} else {
				credentialsBlank = true;
				throw new Exception();
			}
		} catch (Exception e) {
			if (credentialsBlank) {
				tokenInfoDTO
				        .setErrorMessage("Username / Password not provided");
			} else {
				tokenInfoDTO
				        .setErrorMessage("Invalid credentials. Please try again.");
			}
		}
		return tokenInfoDTO;
	}

	@Override
	public StatusMessageDTO logout(UserCredentialsDTO userCredentialsDTO,
	        HttpServletRequest request) {
		TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
		StatusMessageDTO status = new StatusMessageDTO();
		try {
			if (userCredentialsDTO == null) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INCOMPLETE_REQUEST);
			}
			if (isNotBlank(userCredentialsDTO.getAccessToken())) {

				revoke(clientId, clientSecret,
				        userCredentialsDTO.getAccessToken(), request
				                .getSession().getId());
				status.setStatus(Status.SUCCESS);

			} else {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INCOMPLETE_REQUEST);
			}
		} catch (Exception e) {
			if (e instanceof GalaxyException) {
				throw e;
			}
			e.getMessage();
		}
		return status;
	}

}
