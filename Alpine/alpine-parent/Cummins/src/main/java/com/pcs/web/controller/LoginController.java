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
package com.pcs.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcs.alpine.token.Token;
import com.pcs.alpine.token.TokenManager;
import com.pcs.web.client.TokenHolder;
import com.pcs.web.constants.WebConstants;
import com.pcs.web.dto.ErrorDTO;
import com.pcs.web.model.UserCredentials;

/**
 * Controller for login screen
 * 
 * @author PCSEG296 RIYAS PH
 * @date October 2015
 * @since Alpine-1.0.0
 * 
 */
@Controller
public class LoginController {

	@Value("${apimanager.clientid}")
	private String clientId;

	@Value("${apimanager.clientSecret}")
	private String clientSecret;

	private static final Logger logger = LoggerFactory
	        .getLogger(LoginController.class);
	private static final String ERROR = "error";
	private static final String DOMAIN_NAME = "cummins.alpine.com";
	private static final String AT = "@";

	@RequestMapping(value = WebConstants.LOGIN_PATH_NAME,
	        method = RequestMethod.GET)
	public ModelAndView login(HttpSession httpSession) {
		return new ModelAndView(WebConstants.LOGIN_VIEW);
	}

	@RequestMapping(value = WebConstants.LOGOUT_PATH_NAME,
	        method = RequestMethod.POST)
	public ModelAndView logout(HttpSession httpSession) {
		if (httpSession != null) {
			TokenManager.invalidateToken(TokenHolder.getBearer());
			httpSession.invalidate();
		}
		return new ModelAndView(WebConstants.LOGIN_VIEW);
	}

	@RequestMapping(value = WebConstants.AUTHENTICATE_PATH_NAME,
	        method = RequestMethod.POST)
	public ModelAndView authenticate(UserCredentials credentials,
	        RedirectAttributes redirectAttributes, HttpSession httpSession) {
		logger.debug("Inside the login method");
		ModelAndView mv = null;
		try {
			if (StringUtils.isNotBlank(credentials.getUserName())
			        && StringUtils.isNotBlank(credentials.getPassword())) {
				credentials.setUserName(credentials.getUserName() + AT
				        + DOMAIN_NAME);
				MultiValueMap map = getHeaderParam(credentials,
				        httpSession.getId());
				mv = new ModelAndView("redirect:" + WebConstants.HOME_PATH_NAME);
				logger.debug("login sucessful!! Re directing to home page");
			} else {

				throw new Exception();
			}
		} catch (Exception e) {
			ErrorDTO error = new ErrorDTO();
			error.setErrorMessage("Authentication Failed.");
			mv = new ModelAndView(WebConstants.LOGIN_PATH_NAME);
			mv.addObject(ERROR, error);
		}
		return mv;
	}

	private MultiValueMap getHeaderParam(UserCredentials credentials,
	        String sessionId) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		Token token = TokenManager
		        .getToken(clientId, clientSecret, credentials.getUserName(),
		                credentials.getPassword(), sessionId);
		headers.set("Authorization", "Bearer " + token.getAccessToken());
		return headers;
	}
}
