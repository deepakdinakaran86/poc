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
import static com.pcs.web.constants.WebConstants.ROLE_CREATE;
import static com.pcs.web.constants.WebConstants.ROLE_DELETE;
import static com.pcs.web.constants.WebConstants.ROLE_FORM;
import static com.pcs.web.constants.WebConstants.ROLE_HOME;
import static com.pcs.web.constants.WebConstants.ROLE_UPDATE;
import static com.pcs.web.constants.WebConstants.ROLE_VIEW;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcs.alpine.dto.RoleDto;
import com.pcs.web.client.AccessManager;
import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.FieldMapDTO;
import com.pcs.web.dto.PermissionGroupsDTO;
import com.pcs.web.dto.StatusMessageDTO;
import com.pcs.web.model.Role;
import com.pcs.web.services.RoleService;

/**
 * @author pcseg296
 * 
 */
@Controller
public class RoleController {

	private static final Logger logger = LoggerFactory
	        .getLogger(RoleController.class);

	@Autowired
	RoleService roleService;

	@RequestMapping(value = ROLE_HOME,method = RequestMethod.GET)
	public ModelAndView getRoleHome() {
		logger.debug("Inside the Role controller {}");
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		return new ModelAndView(ROLE_HOME);
	}

	@RequestMapping(value = ROLE_HOME,method = RequestMethod.POST)
	public ResponseEntity<String> getRoles() {

		logger.debug("Inside get all Roles");
		CumminsResponse<List<RoleDto>> res = roleService.viewAllRoles();
		Gson gson = new Gson();
		String json = gson.toJson(res);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<String>(json, responseHeaders,
		        HttpStatus.CREATED);

	}

	@RequestMapping(value = ROLE_VIEW,method = RequestMethod.POST)
	public ModelAndView getRoleView(FieldMapDTO field) {
		logger.debug("Inside getRoleView");
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", ROLE_HOME);
			return mv;
		}
		ModelAndView mv = new ModelAndView(ROLE_FORM);
		mv.addObject("action", "View");
		CumminsResponse<RoleDto> viewRoleDetails = roleService
		        .viewRoleDetails(field.getValue());
		Role role = new Role();
		role.setRoleName(viewRoleDetails.getEntity().getRoleName());
		Gson gson = new Gson();
		String json = gson.toJson(viewRoleDetails.getEntity().getResources());
		role.setPrivileges(json);
		mv.addObject(ROLE_FORM, role);
		return mv;
	}

	@RequestMapping(value = ROLE_CREATE,method = RequestMethod.POST)
	public ModelAndView saveRole(Role role) {
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", ROLE_HOME);
			return mv;
		}
		logger.debug("Inside save Roles");
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(role.getRoleName());
		Gson gson = new Gson();
		Type pgType = new TypeToken<List<PermissionGroupsDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		ArrayList<PermissionGroupsDTO> privileges = gson.fromJson(
		        role.getPrivileges(), pgType);
		roleDto.setResources(privileges);
		ModelAndView mv = new ModelAndView(ROLE_FORM);
		if ("Update".equals(role.getAction())) {
			roleDto.setRoleName(role.getNewRoleName());
			roleDto.setNewRoleName(role.getRoleName());
			CumminsResponse<RoleDto> updateRole = roleService
			        .updateRole(roleDto);
			mv.addObject("response", getResponseMessage(updateRole));
			mv.addObject("action", "Update");
			mv.addObject(ROLE_FORM, role);
		} else {
			CumminsResponse<RoleDto> updateRole = roleService
			        .createRole(roleDto);
			mv.addObject("response", getResponseMessage(updateRole));
			mv.addObject("action", "Create");
			mv.addObject(ROLE_FORM, new Role());
		}

		return mv;

	}

	@RequestMapping(value = ROLE_CREATE,method = RequestMethod.GET)
	public ModelAndView getRoleCreatePage() {
		logger.debug("Inside getRoleCreatePage");
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", ROLE_HOME);
			return mv;
		}
		ModelAndView mv = new ModelAndView(ROLE_FORM);
		mv.addObject("action", "Create");
		mv.addObject(ROLE_FORM, new Role());
		return mv;
	}

	@RequestMapping(value = ROLE_DELETE,method = RequestMethod.POST)
	public ModelAndView roleDelete(FieldMapDTO field) {
		logger.debug("Inside getRoleDelete");
		ModelAndView mv = new ModelAndView(ROLE_HOME);
		mv.addObject("action", "Delete");
		CumminsResponse<StatusMessageDTO> viewRoleDetails = roleService
		        .deleteRole(field.getValue());
		mv.addObject(ROLE_HOME, viewRoleDetails.getEntity());
		return mv;
	}

	@RequestMapping(value = ROLE_UPDATE,method = RequestMethod.POST)
	public ModelAndView getRoleUpdateView(FieldMapDTO field) {
		logger.debug("Inside getRoleView");
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", ROLE_HOME);
			return mv;
		}
		ModelAndView mv = new ModelAndView(ROLE_FORM);
		mv.addObject("action", "Update");
		CumminsResponse<RoleDto> viewRoleDetails = roleService
		        .viewRoleDetails(field.getValue());
		Role role = new Role();
		role.setRoleName(viewRoleDetails.getEntity().getRoleName());
		role.setNewRoleName(viewRoleDetails.getEntity().getRoleName());
		Gson gson = new Gson();
		String json = gson.toJson(viewRoleDetails.getEntity().getResources());
		role.setPrivileges(json);
		mv.addObject(ROLE_FORM, role);
		return mv;
	}

	private String getResponseMessage(CumminsResponse<RoleDto> response) {
		String errorMessage = "SUCCESS";
		if (response.getErrorMessage() != null) {
			errorMessage = response.getErrorMessage().getErrorMessage();
		}
		return errorMessage;

	}
}
