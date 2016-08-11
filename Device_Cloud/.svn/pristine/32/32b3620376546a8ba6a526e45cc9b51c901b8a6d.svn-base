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
package com.pcs.device.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcs.alpine.token.Token;
import com.pcs.alpine.token.TokenManager;
import com.pcs.device.web.constants.WebConstants;
import com.pcs.device.web.dto.ErrorDTO;
import com.pcs.device.web.model.UserCredentials;
import com.pcs.device.web.model.UserToken;
import com.pcs.device.web.services.BaseService;

/**
 * Controller for login screen
 * 
 * @author Riyas
 * 
 */
@Controller
public class LoginController extends BaseService {

	private static final Logger logger = LoggerFactory
	        .getLogger(LoginController.class);

	@Value("${apimanager.clientid}")
	private String clientId;

	@Value("${apimanager.clientSecret}")
	private String clientSecret;

	private static final String ERROR = "error";

	@RequestMapping(value = WebConstants.LOGIN_PATH_NAME,
	        method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView(WebConstants.LOGIN_VIEW);
	}

	@RequestMapping(value = WebConstants.LOGOUT_PATH_NAME,
	        method = RequestMethod.GET)
	public ModelAndView logOut(HttpServletRequest request) {
		request.getSession().removeAttribute("token");
		return new ModelAndView(WebConstants.LOGIN_VIEW);
	}

	@RequestMapping(value = WebConstants.AUTHENTICATE_PATH_NAME,
	        method = RequestMethod.POST)
	public ModelAndView authenticate(UserCredentials credentials,
	        RedirectAttributes redirectAttributes, HttpSession httpSession,
	        HttpServletRequest request) {
		logger.debug("Inside the login method");
		ModelAndView mv = null;
		try {
			if (StringUtils.isNotBlank(credentials.getUserName())
			        && StringUtils.isNotBlank(credentials.getPassword())) {
				credentials.setUserName(credentials.getUserName());
				UserToken userToken = getHeaderParam(credentials,
				        httpSession.getId());
				request.getSession().setAttribute("token", userToken);
				mv = new ModelAndView("redirect:"
				        + WebConstants.DEVICE_PATH_NAME);
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

	private UserToken getHeaderParam(UserCredentials credentials,
	        String sessionId) {
		UserToken userToken = new UserToken();
		Token token = TokenManager
		        .getToken(clientId, clientSecret, credentials.getUserName(),
		                credentials.getPassword(), sessionId);
		userToken.setAccessToken(token.getAccessToken());
		return userToken;
	}

}
