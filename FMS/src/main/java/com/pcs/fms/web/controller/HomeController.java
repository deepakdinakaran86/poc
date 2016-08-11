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
package com.pcs.fms.web.controller;

import static com.pcs.fms.web.constants.FMSWebConstants.CLIENT_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.CLIENT_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.CURRENT_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.MYOWN_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.OWNER_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.OWNER_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_DEFAULT_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_DEFAULT_HOME_VIEW;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Token;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
	        .getLogger(HomeController.class);

	@RequestMapping(value = MYOWN_HOME_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView myownHome(HttpServletRequest request) {

		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());

		token.setCurrentTenant(null);
		token.setIsSubClientSelected(Boolean.FALSE);
		TokenManager.setToken(token, FMSTokenHolder.getBearer());
		ModelAndView mv = null;

		if (FMSAccessManager.isSuperAdmin()) {
			mv = new ModelAndView("redirect:" + OWNER_HOME_PATH_NAME);
		} else if (FMSAccessManager.isTenantAdmin()) {
			mv = new ModelAndView("redirect:" + CLIENT_HOME_PATH_NAME);
		} else {
			mv = new ModelAndView("redirect:" + USER_DEFAULT_HOME_PATH_NAME);
		}

		return mv;
	}

	@RequestMapping(value = CURRENT_HOME_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView currentHome(HttpServletRequest request) {
		ModelAndView mv = null;
		if (FMSAccessManager.isSuperAdmin()) {
			mv = new ModelAndView("redirect:" + OWNER_HOME_PATH_NAME);
		} else if (FMSAccessManager.isTenantAdmin()) {
			mv = new ModelAndView("redirect:" + CLIENT_HOME_PATH_NAME);
		} else {
			mv = new ModelAndView("redirect:" + USER_DEFAULT_HOME_PATH_NAME);
		}
		return mv;
	}

	@RequestMapping(value = OWNER_HOME_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView ownerHome(HttpServletRequest request) {
		logger.debug("Inside the home controller {}");
		return getHomeResponse();

	}

	@RequestMapping(value = CLIENT_HOME_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView teanantHome(HttpServletRequest request) {
		return getHomeResponse();
	}

	@RequestMapping(value = USER_DEFAULT_HOME_PATH_NAME,
	        method = RequestMethod.GET)
	public ModelAndView userHome(HttpServletRequest request) {
		logger.debug("Inside the user home controller {}");
		return getHomeResponse();

	}

	private ModelAndView getHomeResponse() {
		if (FMSAccessManager.isTenantAdmin()) {
			return new ModelAndView(CLIENT_HOME_VIEW);
		} else if (FMSAccessManager.isSuperAdmin()) {
			return new ModelAndView(OWNER_HOME_VIEW);
		} else {
			return new ModelAndView(USER_DEFAULT_HOME_VIEW);
		}
	}
}
