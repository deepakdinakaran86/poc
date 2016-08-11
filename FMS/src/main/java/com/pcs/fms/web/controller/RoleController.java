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

import static com.pcs.fms.web.constants.FMSWebConstants.ROLE_HOME;
import static com.pcs.fms.web.constants.FMSWebConstants.ROLE_VIEW;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.dto.RoleDTO;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Token;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.RoleService;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class RoleController extends BaseService {

	private static final Logger logger = LoggerFactory
	        .getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	/***
	 * Controller to get roles list of a domain
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ROLE_HOME,method = RequestMethod.POST)
	public ResponseEntity<String> getRoles(HttpServletRequest request) {
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		String domainName = FMSAccessManager.getCurrentDomain();
		request.getSession().setAttribute("domainName", domainName);

		FMSResponse<List<RoleDTO>> response = roleService.getRoles(domainName);
		Gson gson = new Gson();
		String json = gson.toJson(response);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<String>(json, responseHeaders,
		        HttpStatus.CREATED);
	}

	/***
	 * Controller to get users list
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ROLE_HOME,method = RequestMethod.GET)
	public ModelAndView getRoleHome(HttpServletRequest request) {
		if (request.getSession().getAttribute("domainName") == null) {
			Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
			String domainName = FMSAccessManager.getCurrentDomain();
			request.getSession().setAttribute("domainName", domainName);
		}
		return new ModelAndView(ROLE_HOME);
	}

	@RequestMapping(value = ROLE_VIEW,method = RequestMethod.GET)
	public ModelAndView getRoleView(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(ROLE_VIEW);
		String domainName = (String)request.getSession().getAttribute(
		        "domainName");
		String roleName = request.getParameter("role_id");
		if (roleName != null) {
			FMSResponse<RoleDTO> fmsResponse = roleService.getRole(roleName,
			        domainName);
			RoleDTO roleDTO = fmsResponse.getEntity();
			mv.addObject("roleNameValue", roleName);
			mv.setViewName("redirect:" + ROLE_VIEW);
		} else {
			mv.addObject("roleNameValue", "null");
			mv.setViewName("redirect:" + ROLE_VIEW);
		}

		return new ModelAndView(ROLE_VIEW);
	}

}
