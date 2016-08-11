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

import static com.pcs.web.constants.WebConstants.CONTACT_NUMBER;
import static com.pcs.web.constants.WebConstants.FIRST_NAME;
import static com.pcs.web.constants.WebConstants.HOME_PATH_NAME;
import static com.pcs.web.constants.WebConstants.LAST_NAME;
import static com.pcs.web.constants.WebConstants.NO_ACCESS;
import static com.pcs.web.constants.WebConstants.PRIMARY_EMAIL;
import static com.pcs.web.constants.WebConstants.ROLE_NAME;
import static com.pcs.web.constants.WebConstants.USER_CREATE;
import static com.pcs.web.constants.WebConstants.USER_FORM;
import static com.pcs.web.constants.WebConstants.USER_HOME;
import static com.pcs.web.constants.WebConstants.USER_NAME;
import static com.pcs.web.constants.WebConstants.USER_UPDATE;
import static com.pcs.web.constants.WebConstants.USER_VIEW;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcs.web.client.AccessManager;
import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.EntityDTO;
import com.pcs.web.dto.EntityTemplateDTO;
import com.pcs.web.dto.FieldMapDTO;
import com.pcs.web.dto.IdentityDTO;
import com.pcs.web.dto.StatusDTO;
import com.pcs.web.model.User;
import com.pcs.web.services.UserService;

/**
 * Controller class for User. UserController class extends BaseController .This
 * UserController provide access to the application behavior which is typically
 * defined by a service interface and interpret user input and transform such
 * input into a sensible model which will be represented to the user by the view
 * 
 * @author PCSEG288 Deepak Dinakaran
 * @date October 2015
 * @since Alpine-1.0.0
 * 
 */
@Controller
public class UserController extends BaseController {

	private static final Logger logger = LoggerFactory
	        .getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = USER_HOME,method = RequestMethod.GET)
	public ModelAndView getUserHome() {
		logger.debug("Inside the user controller {}");
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		return new ModelAndView(USER_HOME);
	}

	/**
	 * Controller to create user
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = USER_CREATE,method = RequestMethod.POST)
	public ModelAndView createUser(@ModelAttribute User user) {
		ModelAndView mav = new ModelAndView(USER_FORM);
		EntityDTO userEntity = getEntity(user);
		if (user.getAction().equals("Create")) {
			userService.createUser(userEntity);
			user = new User();
		} else {
			userService.updateUser(userEntity);
			mav.addObject("action", "Update");
		}
		mav.addObject("response", "SUCCESS");
		mav.addObject(USER_FORM, user);
		return mav;
	}

	/**
	 * Controller to get all users
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = USER_HOME,method = RequestMethod.POST)
	public ResponseEntity<String> getAllUsers() {

		logger.debug("Inside view all users ");

		CumminsResponse<List<EntityDTO>> response = userService.getAllUsers();
		Gson gson = new Gson();
		String json = gson.toJson(response);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<String>(json, responseHeaders,
		        HttpStatus.CREATED);
	}

	private User getUserFields(FieldMapDTO field) {

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName("DefaultPCSTemplate_User");
		IdentityDTO identityDto = new IdentityDTO();
		identityDto.setEntityTemplate(entityTemplateDTO);
		identityDto.setIdentifier(field);

		CumminsResponse<EntityDTO> response = userService.getUser(identityDto);

		User user = new User();
		if (response != null) {
			EntityDTO entity = response.getEntity();
			if (entity != null) {
				List<FieldMapDTO> fieldValues = entity.getFieldValues();
				for (FieldMapDTO fieldMapDTO : fieldValues) {
					if (fieldMapDTO.getKey().equals(CONTACT_NUMBER))
						user.setContactNumber(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(FIRST_NAME))
						user.setFirstName(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(LAST_NAME))
						user.setLastName(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(PRIMARY_EMAIL))
						user.setEmailId(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(ROLE_NAME)) {
						Gson gson = new Gson();
						Type pgType = new TypeToken<List<String>>() {
							private static final long serialVersionUID = 5936335989523954928L;
						}.getType();
						List<String> fromJson = gson.fromJson(
						        fieldMapDTO.getValue(), pgType);
						user.setRoleName(fromJson);
					} else if (fieldMapDTO.getKey().equals(USER_NAME))
						user.setUserName(fieldMapDTO.getValue());
				}
			}

			StatusDTO status = entity.getEntityStatus();
			user.setStatus(status.getStatusName());
		}
		return user;
	}

	@RequestMapping(value = USER_VIEW,method = RequestMethod.GET)
	public ModelAndView getUser(FieldMapDTO field) {

		field.setKey("userName");
		logger.debug("Inside view a user ");
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(USER_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(USER_FORM);
		mv.addObject("action", "View");
		User user = getUserFields(field);
		mv.addObject(USER_FORM, user);
		return mv;
	}

	@RequestMapping(value = USER_CREATE,method = RequestMethod.GET)
	public ModelAndView getUserCreate() {
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(USER_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mav = new ModelAndView(USER_FORM);
		mav.addObject("action", "Create");
		User user = new User();
		user.setAction("Create");
		mav.addObject(USER_FORM, user);
		return mav;
	}

	@RequestMapping(value = USER_UPDATE,method = RequestMethod.GET)
	public ModelAndView getUserUpdate(FieldMapDTO field) {
		if (!AccessManager.isAdminUser()) {
			ModelAndView mv = new ModelAndView(USER_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		field.setKey("userName");
		logger.debug("Inside view for update ");
		ModelAndView mv = new ModelAndView(USER_FORM);
		mv.addObject("action", "Update");
		User user = getUserFields(field);
		user.setIdentifier(field.getValue());
		user.setAction("Update");
		mv.addObject(USER_FORM, user);
		return mv;
	}

	private EntityDTO getEntity(User user) {
		Gson gson = new Gson();
		EntityDTO userEntity = new EntityDTO();

		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
		FieldMapDTO field = null;

		field = new FieldMapDTO();
		field.setKey(USER_NAME);
		field.setValue(user.getUserName());
		fields.add(field);

		field = new FieldMapDTO();
		field.setKey(FIRST_NAME);
		field.setValue(user.getFirstName());
		fields.add(field);

		field = new FieldMapDTO();
		field.setKey(LAST_NAME);
		field.setValue(user.getLastName());
		fields.add(field);

		field = new FieldMapDTO();
		field.setKey(ROLE_NAME);
		field.setValue(gson.toJson(user.getRoleName()));
		fields.add(field);

		field = new FieldMapDTO();
		field.setKey(CONTACT_NUMBER);
		field.setValue(user.getContactNumber());
		fields.add(field);

		field = new FieldMapDTO();
		field.setKey(PRIMARY_EMAIL);
		field.setValue(user.getEmailId());
		fields.add(field);

		if (StringUtils.isNotBlank(user.getIdentifier())
		        && user.getAction().equals("Update")) {
			FieldMapDTO identifier = new FieldMapDTO();
			identifier.setKey(USER_NAME);
			identifier.setValue(user.getIdentifier());
			userEntity.setIdentifier(identifier);
		}

		StatusDTO status = new StatusDTO();
		status.setStatusName(user.getStatus());
		userEntity.setEntityStatus(status);

		userEntity.setFieldValues(fields);
		return userEntity;

	}

}
