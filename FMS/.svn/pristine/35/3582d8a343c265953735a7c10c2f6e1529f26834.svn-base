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

import static com.pcs.fms.web.constants.FMSWebConstants.CONTACT_NUMBER;
import static com.pcs.fms.web.constants.FMSWebConstants.EMAIL_ID;
import static com.pcs.fms.web.constants.FMSWebConstants.FILE_IMAGE_TYPE;
import static com.pcs.fms.web.constants.FMSWebConstants.FIRST_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.RESPONSE;
import static com.pcs.fms.web.constants.FMSWebConstants.ROLE_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.USER;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_CREATE;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_DELETE;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_FORM;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_HOME;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_SET_PASSWORD;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_TEMPLATE;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.springframework.http.HttpStatus.OK;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSResponseManager;
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.dto.AttachTags;
import com.pcs.fms.web.dto.CoordinatesDTO;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.GeotagDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.Image;
import com.pcs.fms.web.dto.PlatformEntityDTO;
import com.pcs.fms.web.dto.RoleDTO;
import com.pcs.fms.web.dto.Tag;
import com.pcs.fms.web.dto.UserDTO;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Token;
import com.pcs.fms.web.model.FileUploadForm;
import com.pcs.fms.web.model.User;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.FileService;
import com.pcs.fms.web.services.GeotagService;
import com.pcs.fms.web.services.RoleService;
import com.pcs.fms.web.services.TagService;
import com.pcs.fms.web.services.UserService;

/**
 * @author PCSEG288 DEEPAK DINAKARAN
 * @date JUNE 2016
 * @since FMS 1.0.0
 * 
 */
@Controller
public class UserController extends BaseService {

	private static final Logger logger = LoggerFactory
	        .getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private TagService tagService;

	@Autowired
	private GeotagService geotagService;

	@Autowired
	FileService fileServer;

	@Value("${user.create.success}")
	private String userCreateSuccess;

	@Value("${user.create.failure}")
	private String userCreateFailure;

	@Value("${user.update.success}")
	private String userUpdateSuccess;

	@Value("${user.update.failure}")
	private String userUpdateFailure;
	
	@Value("${user.delete.success}")
	private String deleteUserSuccess;
	
	@Value("${user.delete.failure}")
	private String deleteUserFailure;

	/***
	 * Controller - Direct to User home to list all users based on domain
	 * 
	 * @param request
	 * @param redirect
	 * @return ModelAndView
	 */
	@RequestMapping(value = USER_HOME,method = RequestMethod.GET)
	public ModelAndView goToUserHome(HttpServletRequest request,
	        RedirectAttributes redirect) {
		ModelAndView mv = new ModelAndView(USER_HOME);
		String domainName = FMSAccessManager.getCurrentDomain();
		request.getSession().setAttribute("domainName", domainName);
		return mv;
	}

	/***
	 * Controller - Direct to Create or Edit a user
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = USER_FORM,method = RequestMethod.POST)
	public ModelAndView goToCreateUserForm(HttpServletRequest request,
	        @ModelAttribute User user) {
		// ,@RequestParam("user_id") String userName
		ModelAndView mv = new ModelAndView(USER_FORM);
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		String domainName = FMSAccessManager.getCurrentDomain();
		String userName = request.getParameter("user_id");
		// if for edit else for creating a new user form
		if (userName != null) {
			setUserDetails(mv, userName, domainName);
			/*
			 * FMSResponse<EntityDTO> fmsResponse =
			 * userService.getUser(userName, domainName); EntityDTO
			 * userEntityDTO = fmsResponse.getEntity(); UserDTO userDTO =
			 * createUserDTO(userEntityDTO); User user = getUser(userDTO);
			 * user.setDomain(domainName); getImage(user);
			 * mv.addObject("action", "Edit"); mv.addObject("VarEditUserId",
			 * "Edit"); mv.addObject("user_create", user); Gson gson = new
			 * GsonBuilder().create(); String stringUserEntityDTO =
			 * gson.toJson(userEntityDTO); String base64encodedString = null;
			 * try { base64encodedString = Base64.getEncoder().encodeToString(
			 * stringUserEntityDTO.getBytes("utf-8")); } catch
			 * (UnsupportedEncodingException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); } mv.addObject("userDTO",
			 * base64encodedString); FMSResponse<List<RoleDTO>> roles =
			 * roleService.getRoles(domainName); List<String> roleNames = new
			 * ArrayList<String>(); for (RoleDTO roleDTO : roles.getEntity()) {
			 * roleNames.add(roleDTO.getRoleName()); } mv.addObject("roleList",
			 * roleNames); mv.addObject("varUserName", userName);
			 */

		} else {
			mv.addObject("user_create", new User());
			mv.addObject("action", "Create");
			FMSResponse<List<RoleDTO>> roles = roleService.getRoles(domainName);
			List<String> roleNames = new ArrayList<String>();
			for (RoleDTO roleDTO : roles.getEntity()) {
				roleNames.add(roleDTO.getRoleName());
			}
			mv.addObject("roleList", roleNames);
		}
		return mv;
	}

	private void setUserDetails(ModelAndView mv, String userName,
	        String domainName) {
		FMSResponse<EntityDTO> fmsResponse = userService.getUser(userName,
		        domainName);
		EntityDTO userEntityDTO = fmsResponse.getEntity();
		UserDTO userDTO = createUserDTO(userEntityDTO);
		User user = getUser(userDTO);
		user.setDomain(domainName);
		getImage(user);
		mv.addObject("action", "Edit");
		mv.addObject("VarEditUserId", "Edit");
		mv.addObject("user_create", user);
		Gson gson = new GsonBuilder().create();
		String stringUserEntityDTO = gson.toJson(userEntityDTO);
		String base64encodedString = null;
		try {
			base64encodedString = Base64.getEncoder().encodeToString(
			        stringUserEntityDTO.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mv.addObject("userDTO", base64encodedString);
		FMSResponse<List<RoleDTO>> roles = roleService.getRoles(domainName);
		List<String> roleNames = new ArrayList<String>();
		for (RoleDTO roleDTO : roles.getEntity()) {
			roleNames.add(roleDTO.getRoleName());
		}
		mv.addObject("roleList", roleNames);
		mv.addObject("varUserName", userName);

	}

	/***
	 * Controller - Delete User from grid
	 * 
	 * @param request
	 * @param userName
	 * @return ModelAndView
	 */
	@RequestMapping(value = USER_DELETE,method = RequestMethod.POST)
	public ModelAndView deleteUser(HttpServletRequest request,
	        @RequestParam("delete_user_id") String userName, RedirectAttributes redirect) {
		RedirectView redirectView = new RedirectView(USER_HOME);
		ModelAndView mv = new ModelAndView(redirectView);
		//mv.addObject("user_create", new User());
		//mv.addObject("action", "Create");
		String responseMessage = "";

		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		String domainName = FMSAccessManager.getCurrentDomain();
		FMSResponse deleteResponse = userService.deleteUser(userName,
		        domainName);
		if (deleteResponse.getStatus() == OK) {
			responseMessage = FMSResponseManager.success(deleteUserSuccess);
			redirect.addFlashAttribute(RESPONSE, responseMessage);
			deleteImage(userName, domainName);
		} else {
			responseMessage = FMSResponseManager.error(deleteUserFailure);
			redirect.addFlashAttribute(RESPONSE, responseMessage);
			mv.addObject(USER_HOME, userName);
			
		}

		//mv.addObject("response", responseMessage);
		mv.setViewName("redirect:/" + USER_HOME);

		return mv;
	}

	/***
	 * Controller - Create user
	 * 
	 * @param user
	 * @param request
	 * @param redirect
	 * @return ModelAndView
	 */
	@RequestMapping(value = USER_CREATE,method = RequestMethod.POST)
	public ModelAndView saveOrUpdateUser(@ModelAttribute User user,
	        HttpServletRequest request, RedirectAttributes redirect) {
		RedirectView redirectView = new RedirectView(USER_HOME);
		ModelAndView mv = new ModelAndView(redirectView);

		user.setEmailLink(getURL(request) + USER_SET_PASSWORD);
		UserDTO userDTO = getUserDTO(user);
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		String domainName = FMSAccessManager.getCurrentDomain();
		if (user.getDomain() == null) {
			userDTO.setDomain(domainName);
			user.setDomain(domainName);
		}
		String action = request.getParameter("action");
		String responseMessage = "";
		if (!user.getAction().equals("Update")) {
			FMSResponse<EntityDTO> createUser = userService.createUser(userDTO);
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(createUser.getEntity());
			System.out.println(json);

			if (createUser.getStatus() == OK) {
				saveImage(user);
				responseMessage = FMSResponseManager.success(userCreateSuccess);
			} else {
				redirectView = new RedirectView(USER_FORM);
				responseMessage = FMSResponseManager.error(userCreateFailure);
				redirect.addFlashAttribute(RESPONSE, responseMessage);
				ModelAndView mav = new ModelAndView(USER_FORM);
				FMSResponse<List<RoleDTO>> roles = roleService
				        .getRoles(domainName);
				List<String> roleNames = new ArrayList<String>();
				for (RoleDTO roleDTO : roles.getEntity()) {
					roleNames.add(roleDTO.getRoleName());
				}
				mav.addObject("roleList", user.getRoleName());
				mav.addObject("user_create", user);
				mav.addObject(RESPONSE, responseMessage);
				mav.addObject(USER_FORM, user);
				return mav;
			}
		} else {

			FMSResponse<EntityDTO> updateUser = userService.updateUser(userDTO);
			if (updateUser.getStatus() == OK) {
				saveImage(user);
				responseMessage = FMSResponseManager.success(userCreateSuccess);
			} else {
				mv.addObject(USER_HOME, user);
				responseMessage = FMSResponseManager.success(userUpdateFailure);
			}
			mv.addObject("action", "Update");
			responseMessage = FMSResponseManager.success(userUpdateSuccess);
			checkifGeotagPresent(updateUser, user);
		}
		redirect.addFlashAttribute(RESPONSE, responseMessage);
		saveTags(user, domainName);
		saveGeoTag(user, domainName);
		ModelAndView mav = new ModelAndView(redirectView);
		request.getSession().setAttribute("domainName", domainName);
		return mav;

	}

	@SuppressWarnings("serial")
	private ModelAndView viewComponentOnError(User user,
	        RedirectAttributes redirectAttributes, RedirectView redirectView1,
	        String responseMessage) {
		RedirectView redirectView = new RedirectView(USER_FORM);
		ModelAndView mv = new ModelAndView(redirectView);

		// setUserDetails(mv, user.getUserName(), user.getDomain());

		mv.addObject(RESPONSE, responseMessage);
		mv.addObject(USER_FORM, user);
		mv.addObject("user_create", user);
		return mv;
	}

	private void checkifGeotagPresent(FMSResponse<EntityDTO> updateUser,
	        User user) {
		IdentityDTO userIdentity = new IdentityDTO(updateUser.getEntity());

		FMSResponse<GeotagDTO> userGeotag = geotagService
		        .findGeotag(userIdentity);

		if (userGeotag.getErrorMessage() == null) {
			/*
			 * user.setLatitude(userGeotag.getEntity().getGeotag()
			 * .getLatitude());
			 * user.setLongitude(userGeotag.getEntity().getGeotag()
			 * .getLongitude());
			 */
			user.setGeotagPresent(true);
		} else {
			user.setGeotagPresent(false);
		}

	}

	/***
	 * Function to create UserDTO
	 * 
	 * @param userEntityDTO
	 * @return
	 */
	private UserDTO createUserDTO(EntityDTO userEntityDTO) {
		UserDTO userDTO = new UserDTO();

		userDTO.setActive(userEntityDTO.getEntityStatus().getStatusKey() == 0
		        ? true
		        : false);

		List<FieldMapDTO> fieldMaps = userEntityDTO.getFieldValues();

		for (FieldMapDTO fieldMap : fieldMaps) {

			if (fieldMap.getKey().equalsIgnoreCase(USER_NAME))
				userDTO.setUserName(fieldMap.getValue());

			if (fieldMap.getKey().equalsIgnoreCase(FIRST_NAME))
				userDTO.setFirstName(fieldMap.getValue());

			if (fieldMap.getKey().equalsIgnoreCase(FIRST_NAME))
				userDTO.setLastName(fieldMap.getValue());

			if (fieldMap.getKey().equalsIgnoreCase(CONTACT_NUMBER))
				userDTO.setContactNumber(fieldMap.getValue());

			if (fieldMap.getKey().equalsIgnoreCase(EMAIL_ID))
				userDTO.setEmailId(fieldMap.getValue());

			if (fieldMap.getKey().equalsIgnoreCase(ROLE_NAME))
				userDTO.setRoleName(fieldMap.getValue());

		}

		return userDTO;
	}

	/***
	 * Function to get URL for email Link setting up
	 * 
	 * @param request
	 * @return
	 */
	private String getURL(HttpServletRequest request) {
		String uri = new String();
		if (request != null) {
			uri = request.getScheme() + "://" + request.getServerName() + ":"
			        + request.getServerPort() + request.getContextPath() + "/";
		}
		return uri;
	}

	/***
	 * Function to save Geo Tag
	 * 
	 * @param user
	 * @param domainName
	 */
	private void saveGeoTag(User user, String domainName) {
		if (user.getGeotagPresent() != null && user.getGeotagPresent()) {
			GeotagDTO geotagDTO = getGeotagPayload(user);
			if (isNotBlank(user.getLatitude())
			        && isNotBlank(user.getLongitude())) {
				// Update geotag
				// Invoke update geotag API
				geotagService.updateGeotag(geotagDTO);
			}
		} else if (isNotBlank(user.getLatitude())
		        && isNotBlank(user.getLongitude())) {
			// Create geotag
			GeotagDTO geotagDTO = getGeotagPayload(user);
			geotagService.createGeotag(geotagDTO);
		}

	}

	/***
	 * Function to generate GeotagDTO
	 * 
	 * @param user
	 * @return GeotagDTO
	 */
	private GeotagDTO getGeotagPayload(User user) {
		CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
		coordinatesDTO.setLatitude(user.getLatitude());
		coordinatesDTO.setLongitude(user.getLongitude());

		GeotagDTO geotagDTO = new GeotagDTO();
		IdentityDTO entityDTO = new IdentityDTO();
		FieldMapDTO identifier = new FieldMapDTO();
		identifier.setKey(USER_NAME);
		identifier.setValue(user.getUserName());
		entityDTO.setIdentifier(identifier);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(USER_TEMPLATE);
		entityDTO.setEntityTemplate(entityTemplateDTO);

		geotagDTO.setEntity(entityDTO);
		geotagDTO.setGeotag(coordinatesDTO);
		return geotagDTO;
	}

	private void saveImage(User user) {

		FileUploadForm userImage = new FileUploadForm();
		userImage.setFileDatas(user.getFileDatas());

		Image image = new Image();
		image.setDomain(user.getDomain());
		image.setModule(USER_FOLDER_NAME);
		image.setFilename(user.getUserName() + FILE_IMAGE_TYPE);
		CommonsMultipartFile[] fileDatas = userImage.getFileDatas();
		if (fileDatas.length > 0) {
			fileServer.doUpload(fileDatas[0], image);
		}
	}

	private void getImage(User user) {

		Image image = new Image();
		image.setDomain(user.getDomain());
		image.setModule(USER_FOLDER_NAME);
		image.setFilename(user.getUserName() + FILE_IMAGE_TYPE);
		String userImage = fileServer.doDowload(image);
		user.setImage(userImage);

	}

	private void deleteImage(String userName, String domainName) {

		Image image = new Image();
		image.setDomain(domainName);
		image.setModule(USER_FOLDER_NAME);
		image.setFilename(userName + FILE_IMAGE_TYPE);
		fileServer.doDelete(image);
	}

	/***
	 * Function to save tags
	 * 
	 * @param user
	 * @param domainName
	 */
	private void saveTags(User user, String domainName) {

		List<Tag> tags = new ArrayList<Tag>();
		if (isNotBlank(user.getSelectedTags())) {
			Type tag = new TypeToken<List<Tag>>() {
				private static final long serialVersionUID = 5936335989523954928L;
			}.getType();

			Gson gson = new Gson();
			tags = gson.fromJson(user.getSelectedTags(), tag);
		}

		if (!CollectionUtils.isEmpty(tags)) {
			AttachTags attachTags = new AttachTags();
			attachTags.setTags(tags);

			IdentityDTO entityDTO = new IdentityDTO();
			FieldMapDTO identifier = new FieldMapDTO();
			identifier.setKey(USER_NAME);
			identifier.setValue(user.getUserName());
			entityDTO.setIdentifier(identifier);

			EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
			entityTemplateDTO.setEntityTemplateName(USER_TEMPLATE);
			entityDTO.setEntityTemplate(entityTemplateDTO);

			PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
			platformEntityDTO.setPlatformEntityType(USER);
			entityDTO.setPlatformEntity(platformEntityDTO);

			// TODO change domain here
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(domainName);
			entityDTO.setDomain(domain);

			attachTags.setEntity(entityDTO);
			tagService.attachTagsToEntity(attachTags);
		}
	}

	private UserDTO getUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(user.getUserName());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmailId(user.getEmailId());
		Gson gson = new Gson();
		userDTO.setRoleName(gson.toJson(user.getRoleName()));
		userDTO.setContactNumber(user.getContactNumber());
		userDTO.setEmailLink(user.getEmailLink());
		userDTO.setActive(user.getStatus() == true ? true : false);
		return userDTO;
	}

	private User getUser(UserDTO userDto) {
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmailId(userDto.getEmailId());
		JSONArray jsonArray = new JSONArray(userDto.getRoleName());
		List<String> roleNames = new ArrayList<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			roleNames.add(jsonArray.getString(i));
		}
		user.setRoleName(roleNames);
		user.setContactNumber(userDto.getContactNumber());
		user.setEmailLink(userDto.getEmailLink());
		if (userDto.getActive()) {
			user.setActive("active");
		} else {
			user.setActive("inactive");
		}
		return user;
	}

	/*
	 * @RequestMapping(value = USER_FORM,method = RequestMethod.GET) public
	 * ModelAndView getCreateUserForm(HttpServletRequest request) { ModelAndView
	 * mv = new ModelAndView(USER_FORM); mv.addObject("action", "Create");
	 * return mv; }
	 * @RequestMapping(value = USER_HOME,method = RequestMethod.POST) public
	 * ResponseEntity<String> getAllUsers(HttpServletRequest request) { String
	 * domainName = (String)request.getSession().getAttribute( "domainName");
	 * FMSResponse<List<EntityDTO>> response = userService
	 * .getAllUsers(domainName); Gson gson = new Gson(); String json =
	 * gson.toJson(response); HttpHeaders responseHeaders = new HttpHeaders();
	 * responseHeaders.setContentType(MediaType.APPLICATION_JSON); return new
	 * ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED); }
	 * @RequestMapping(value = USERS_VIEW,method = RequestMethod.GET) public
	 * ModelAndView listUsers(HttpServletRequest request) {
	 * logger.debug("Inside the USERS controller"); String domainName =
	 * request.getParameter("domain_name"); return
	 * getUserViewResponse(domainName); } private ModelAndView
	 * getUserViewResponse(String domainName) { ModelAndView mv = new
	 * ModelAndView(USER_HOME); if (domainName == null) { Token token =
	 * TokenManager.getToken(FMSTokenHolder.getBearer()); domainName =
	 * FMSAccessManager.getCurrentDomain(); } mv.addObject("users",
	 * userService.getAllUsers(domainName)); return mv; } private EntityDTO
	 * insertFieldMapForUser(User user) { EntityDTO resetPwd = new EntityDTO();
	 * EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
	 * entityTemplate.setEntityTemplateName("ResetPassword");
	 * resetPwd.setEntityTemplate(entityTemplate); List<FieldMapDTO> fieldMaps =
	 * new ArrayList<FieldMapDTO>(); FieldMapDTO entityName = new FieldMapDTO();
	 * entityName.setKey("entityName"); entityName.setValue(user.getUserName());
	 * fieldMaps.add(entityName); FieldMapDTO timeStamp = new FieldMapDTO();
	 * timeStamp.setKey("timeStamp"); Date date = new Date(); Long longTime =
	 * date.getTime(); timeStamp.setValue(longTime.toString());
	 * fieldMaps.add(timeStamp); FieldMapDTO valid = new FieldMapDTO();
	 * valid.setKey("valid"); valid.setValue("true"); fieldMaps.add(valid);
	 * resetPwd.setFieldValues(fieldMaps); return resetPwd; }
	 */

}
