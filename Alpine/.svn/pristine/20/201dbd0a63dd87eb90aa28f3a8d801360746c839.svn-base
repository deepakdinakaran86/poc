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

import static com.pcs.web.constants.WebConstants.HOME_PATH_NAME;
import static com.pcs.web.constants.WebConstants.NO_ACCESS;
import static com.pcs.web.constants.WebConstants.RESOURCE_CREATE;
import static com.pcs.web.constants.WebConstants.RESOURCE_FORM;
import static com.pcs.web.constants.WebConstants.RESOURCE_HOME;
import static com.pcs.web.constants.WebConstants.RESOURCE_VIEW;

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
import com.pcs.web.client.AccessManager;
import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.FieldMapDTO;
import com.pcs.web.dto.PermissionGroupsDTO;
import com.pcs.web.services.ResourceService;

/**
 * @author pcseg296
 * 
 */
@Controller
public class ResourceController {

	private static final Logger logger = LoggerFactory
	        .getLogger(ResourceController.class);

	@Autowired
	ResourceService resourceService;

	@RequestMapping(value = RESOURCE_HOME,method = RequestMethod.GET)
	public ModelAndView getResourceHome() {
		logger.debug("Inside the Resource controller {}");
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		return new ModelAndView(RESOURCE_HOME);
	}

	@RequestMapping(value = RESOURCE_HOME,method = RequestMethod.POST)
	public ResponseEntity<String> getAllResource() {

		logger.debug("Inside get all Resources");
		CumminsResponse<PermissionGroupsDTO> res = resourceService
		        .viewAllResources();
		Gson gson = new Gson();
		String json = gson.toJson(res);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<String>(json, responseHeaders,
		        HttpStatus.CREATED);

	}

	@RequestMapping(value = RESOURCE_VIEW,method = RequestMethod.POST)
	public ModelAndView getResourceView(FieldMapDTO field) {
		logger.debug("Inside getResourceView");
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(RESOURCE_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(RESOURCE_FORM);
		mv.addObject("action", "View");
		CumminsResponse<PermissionGroupsDTO> viewResourceDetails = resourceService
		        .viewResourceDetails(field.getValue());
		mv.addObject(RESOURCE_FORM, viewResourceDetails.getEntity());
		return mv;
	}

	@RequestMapping(value = RESOURCE_CREATE,method = RequestMethod.POST)
	public ModelAndView saveResource(PermissionGroupsDTO permissionGroup) {
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(RESOURCE_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		logger.debug("Inside save Resources");
		CumminsResponse<PermissionGroupsDTO> res = resourceService
		        .createPermissionGroup(permissionGroup);

		ModelAndView mv = new ModelAndView(RESOURCE_FORM);
		mv.addObject("response", getResponseMessage(res));
		mv.addObject("action", "Create");
		mv.addObject(RESOURCE_FORM, new PermissionGroupsDTO());
		return mv;

	}

	@RequestMapping(value = RESOURCE_CREATE,method = RequestMethod.GET)
	public ModelAndView getResourceCreatePage() {
		logger.debug("Inside getResourceView");
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(RESOURCE_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(RESOURCE_FORM);
		mv.addObject("action", "Create");
		mv.addObject(RESOURCE_FORM, new PermissionGroupsDTO());
		return mv;
	}

	private String getResponseMessage(
	        CumminsResponse<PermissionGroupsDTO> response) {
		String errorMessage = "SUCCESS";
		if (response.getErrorMessage() != null) {
			errorMessage = response.getErrorMessage().getErrorMessage();
		}
		return errorMessage;

	}
}
