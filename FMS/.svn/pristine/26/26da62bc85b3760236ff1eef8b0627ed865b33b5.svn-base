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

import static com.pcs.fms.web.constants.FMSWebConstants.ADMIN_PASSWORD;
import static com.pcs.fms.web.constants.FMSWebConstants.ADMIN_USER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.AUTHENTICATE_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.CLIENT_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.CLIENT_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.FILE_IMAGE_TYPE;
import static com.pcs.fms.web.constants.FMSWebConstants.FORGOT_PASSWORD;
import static com.pcs.fms.web.constants.FMSWebConstants.HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.IDENTIFIER;
import static com.pcs.fms.web.constants.FMSWebConstants.LOGIN_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.LOGIN_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.LOGOUT_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.OWNER_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.OWNER_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.RESET_PSWD_TEMPLATE;
import static com.pcs.fms.web.constants.FMSWebConstants.RESPONSE;
import static com.pcs.fms.web.constants.FMSWebConstants.SET_PASSWORD_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.SET_PASSWORD_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_USER_CREATED;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_DEFAULT_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_SET_PASSWORD;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSResponseManager;
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.constants.FMSWebConstants;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.ErrorDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.Image;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.dto.UserDTO;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Tenant;
import com.pcs.fms.web.manager.dto.Token;
import com.pcs.fms.web.manager.dto.UserPermissionsDTO;
import com.pcs.fms.web.model.User;
import com.pcs.fms.web.model.UserCredentials;
import com.pcs.fms.web.model.UserToken;
import com.pcs.fms.web.services.AuthService;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.FileService;
import com.pcs.fms.web.services.UserService;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
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

	private static String MY_DOMAIN_NAME = ".galaxy";

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@Autowired
	private FileService fileServer;

	@Value("${user.password.failure}")
	private String userPasswordLinkFailure;

	@Value("${forgot.password.success}")
	private String forgotPasswordSuccess;

	@Value("${forgot.password.failure}")
	private String forgotPasswordFailure;

	@Value("${set.password.success}")
	private String setPasswordSuccess;

	@Value("${set.password.failure}")
	private String setPasswordFailure;

	private static final String ERROR = "error";

	@RequestMapping(value = LOGIN_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		FMSTokenHolder.setBearer(request.getSession().getId());
		if (FMSAccessManager.isTenantAdmin() || FMSAccessManager.isSuperAdmin()) {
			return handleUser(request);
		} else {
			ModelAndView mv = new ModelAndView(LOGIN_VIEW);
			mv.addObject("VarAPPServerPath", getURL(request));
			mv.addObject("forgot_password", new User());
			return mv;
		}
	}

	@RequestMapping(value = AUTHENTICATE_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView loginInvalid(HttpServletRequest request) {
		FMSTokenHolder.setBearer(request.getSession().getId());
		if (FMSAccessManager.isTenantAdmin() || FMSAccessManager.isSuperAdmin()) {
			return handleUser(request);
		} else {
			return new ModelAndView(LOGIN_VIEW);
		}
	}

	@RequestMapping(value = HOME_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		FMSTokenHolder.setBearer(request.getSession().getId());
		return handleUser(request);
	}

	@RequestMapping(value = LOGOUT_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView logOut(HttpSession httpSession) {

		if (httpSession != null) {
			TokenManager.invalidateToken(FMSTokenHolder.getBearer());
			httpSession.invalidate();
		}
		return new ModelAndView("redirect:" + FMSWebConstants.LOGIN_VIEW);
	}

	@RequestMapping(value = AUTHENTICATE_PATH_NAME,method = RequestMethod.POST)
	public ModelAndView authenticate(UserCredentials credentials,
	        RedirectAttributes redirectAttributes, HttpSession httpSession,
	        HttpServletRequest request) {
		logger.debug("Inside the login method");
		ModelAndView mv = null;
		boolean credentialsBlank = false;
		try {
			if (StringUtils.isNotBlank(credentials.getUserName())
			        && StringUtils.isNotBlank(credentials.getPassword())) {

				configureUserName(credentials);

				Token token = authService
				        .authenticate(credentials, httpSession);

				if (token != null
				        && StringUtils.isNotEmpty(token.getAccessToken())) {

					UserPermissionsDTO userInfo = authService.getUserInfo();
					if (userInfo != null) {
						token.setUserName(credentials.getUserName().substring(
						        0, credentials.getUserName().indexOf('@')));
						List<String> roleNames = new ArrayList<String>();
						roleNames.addAll(userInfo.getRoleNames());
						token.setRoleNames(roleNames);
						List<String> permissions = new ArrayList<String>();
						permissions.addAll(userInfo.getPermissions());
						token.setPremissions(itratePermission(permissions));
						EntityDTO tenantInfo = authService.getTenantInfo();
						Tenant tenant = new Tenant();
						if (tenantInfo != null) {
							tenant.setPlatformEntityType(tenantInfo
							        .getPlatformEntity()
							        .getPlatformEntityType());
							tenant.setDomainName(tenantInfo.getDomain()
							        .getDomainName());
							tenant.setEntityTemplateName(tenantInfo
							        .getEntityTemplate()
							        .getEntityTemplateName());
							tenant.setTenantId(tenantInfo.getIdentifier()
							        .getValue());
							tenant.setCurrentDomain(tenantInfo.getHierarchy()
							        .getDomain().getDomainName());

							List<FieldMapDTO> fieldValues = tenantInfo
							        .getFieldValues();

							FieldMapDTO fieldMapDTO = new FieldMapDTO();
							fieldMapDTO.setKey("tenantName");
							fieldMapDTO = fetchField(fieldValues, fieldMapDTO);
							tenant.setTenantName(fieldMapDTO.getValue());

							tenant.setTenantImage(getTenantImage(
							        fieldMapDTO.getValue(),
							        tenant.getDomainName()));

							token.setTenant(tenant);
							token.setUserImage(getImage(token.getUserName(),
							        tenant.getCurrentDomain()));
							TokenManager.setToken(token, httpSession.getId());
						} else {
							throw new Exception();
						}
					} else {
						throw new Exception();
					}

				} else {
					throw new Exception();
				}

				FMSTokenHolder.setBearer(httpSession.getId());
				request.getSession().setAttribute("token", token);
				mv = handleUser(request);
			} else {
				credentialsBlank = true;
				throw new Exception();
			}
		} catch (Exception e) {
			ErrorDTO error = new ErrorDTO();
			if (credentialsBlank)
				error.setErrorMessage("Username / Password not provided");
			else error
			        .setErrorMessage("Invalid credentials. Please try again.");

			mv = new ModelAndView(LOGIN_PATH_NAME);
			mv.addObject(ERROR, error);
		}
		return mv;
	}

	private ModelAndView handleUser(HttpServletRequest request) {
		ModelAndView mv = null;
		try {
			if (FMSAccessManager.isSuperAdmin()) {
				mv = new ModelAndView("redirect:" + OWNER_HOME_PATH_NAME);
			} else if (FMSAccessManager.isTenantAdmin()) {
				mv = new ModelAndView("redirect:" + CLIENT_HOME_PATH_NAME);
			} else {
				mv = new ModelAndView("redirect:" + USER_DEFAULT_HOME_PATH_NAME);
			}

			Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
			request.getSession()
			        .setAttribute("nameofUser", token.getUserName());

			request.getSession().setAttribute("homePage", getHomePage());
			logger.debug("login sucessful!! Re directing to home page");
		} catch (Exception e) {
			ErrorDTO error = new ErrorDTO();
			error.setErrorMessage("Invalid credentials. Please try again.");
			mv = new ModelAndView(LOGIN_PATH_NAME);
			mv.addObject(ERROR, error);
		}
		return mv;
	}

	@RequestMapping(value = USER_SET_PASSWORD + "/{identifier}",
	        method = RequestMethod.GET)
	public ModelAndView setPassword(
	        @PathVariable("identifier") String identifier,
	        HttpSession httpSession, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(SET_PASSWORD_VIEW);
		String jsonIdentifier = org.apache.commons.codec.binary.StringUtils
		        .newStringUtf8(Base64.decodeBase64(identifier));

		String[] parts = jsonIdentifier.split(Pattern.quote(":"));

		// Fetch the marker to get email
		IdentityDTO identityDto = new IdentityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(parts[1]);
		identityDto.setDomain(domain);

		// Set template name
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(RESET_PSWD_TEMPLATE);
		identityDto.setEntityTemplate(entityTemplateDTO);

		// Set identifier
		FieldMapDTO userFieldMap = new FieldMapDTO();
		userFieldMap.setKey(IDENTIFIER);
		userFieldMap.setValue((parts[0]));
		identityDto.setIdentifier(userFieldMap);

		// Generate client credentials token
		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setUserName(ADMIN_USER_NAME);
		userCredentials.setPassword(ADMIN_PASSWORD);
		Token token = authService.authenticate(httpSession);
		TokenManager.setToken(token, httpSession.getId());

		String userName = userService.getUserName(identityDto);
		String response = null;
		if (isBlank(userName)) {
			if (httpSession != null) {
				TokenManager.invalidateToken(FMSTokenHolder.getBearer());
				httpSession.invalidate();
			}
			ModelAndView mav = new ModelAndView(TENANT_USER_CREATED);
			response = FMSResponseManager.error(userPasswordLinkFailure);
			mav.addObject(RESPONSE, response);
			return mav;
		}
		User user = new User();
		user.setIdentifier(userFieldMap.getValue());
		user.setDomain(domain.getDomainName());

		mv.addObject("varDomainName", domain.getDomainName());
		mv.addObject("varUserName", userName);
		mv.addObject("VarIdentifier", parts[0]);
		mv.addObject("varContextPath", getURL(request));
		mv.addObject(SET_PASSWORD_VIEW, user);

		return mv;
	}

	private String getURL(HttpServletRequest request) {
		String uri = new String();
		if (request != null) {
			uri = request.getScheme() + "://" + request.getServerName() + ":"
			        + request.getServerPort() + request.getContextPath() + "/";
		}
		return uri;
	}

	@RequestMapping(value = USER_SET_PASSWORD + "/{identifier}",
	        method = RequestMethod.POST)
	public ModelAndView setUserPassword(@ModelAttribute User user,
	        RedirectAttributes redirectAttributes, HttpSession httpSession,
	        HttpServletRequest request) {
		RedirectView redirectView = new RedirectView(LOGIN_VIEW);
		ModelAndView mv = new ModelAndView(redirectView);

		if (httpSession != null) {
			TokenManager.invalidateToken(FMSTokenHolder.getBearer());
			httpSession.invalidate();
		}

		Token token = authService.authenticate(httpSession);
		TokenManager.setToken(token, httpSession.getId());

		FMSResponse<StatusMessageDTO> statusMessageDTO = userService
		        .setUserPassword(user);

		String responseMessage = "";
		if (statusMessageDTO.getEntity().getStatus().getStatus()
		        .equalsIgnoreCase("SUCCESS")) {
			responseMessage = FMSResponseManager.success(setPasswordSuccess);
			redirectAttributes.addFlashAttribute(RESPONSE, responseMessage);

		} else {
			responseMessage = FMSResponseManager.success(setPasswordFailure);
			redirectAttributes.addFlashAttribute(RESPONSE, responseMessage);

		}
		mv.setViewName("redirect:/" + LOGIN_VIEW);

		/*
		 * if (httpSession != null) {
		 * TokenManager.invalidateToken(FMSTokenHolder.getBearer());
		 * httpSession.invalidate(); }
		 */

		return mv;

	}

	@RequestMapping(value = FORGOT_PASSWORD,method = RequestMethod.POST)
	public ModelAndView forgotPassword(@ModelAttribute User user,
	        RedirectAttributes redirectAttributes, HttpServletRequest request,
	        HttpSession httpSession) {
		RedirectView redirectView = new RedirectView(LOGIN_VIEW);
		ModelAndView mv = new ModelAndView(redirectView);
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(user.getUserName() + ".galaxy");
		userDTO.setEmailId(user.getEmailId());
		userDTO.setEmailLink(getURL(request) + "resetpassword");

		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setUserName(ADMIN_USER_NAME);
		userCredentials.setPassword(ADMIN_PASSWORD);
		Token token = authService.authenticate(httpSession);
		TokenManager.setToken(token, httpSession.getId());

		String responseMessage = "";
		FMSResponse forgotPasswordResponse = userService
		        .forgotPassword(userDTO);
		if (forgotPasswordResponse.getStatus() == OK) {
			responseMessage = FMSResponseManager.success(forgotPasswordSuccess);
			redirectAttributes.addFlashAttribute(RESPONSE, responseMessage);
		} else {
			responseMessage = FMSResponseManager.success(forgotPasswordFailure);
			redirectAttributes.addFlashAttribute(RESPONSE, responseMessage);
		}
		if (httpSession != null) {
			TokenManager.invalidateToken(FMSTokenHolder.getBearer());
			httpSession.invalidate();
		}
		mv.setViewName("redirect:/" + LOGIN_VIEW);
		return mv;
	}

	@RequestMapping(value = SET_PASSWORD_PATH_NAME,method = RequestMethod.POST)
	public ModelAndView changePasswordSubmit(UserCredentials userCredentials,
	        RedirectAttributes redirectAttributes, HttpSession httpSession,
	        HttpServletRequest request) {
		logger.debug("Inside the change password method");
		ModelAndView mv = null;
		mv = new ModelAndView("redirect:" + LOGIN_VIEW);
		try {
			if (StringUtils.isNotBlank(userCredentials.getUserName())
			        && StringUtils.isNotBlank(userCredentials.getPassword())) {
				UserToken userToken = new UserToken();

				Token token = authService.authenticate(httpSession);
				TokenManager.setToken(token, httpSession.getId());

				userToken.setAccessToken(token.getAccessToken());
				FMSTokenHolder.setBearer(httpSession.getId());

				FMSResponse<StatusMessageDTO> statusMessageDTO = userService
				        .updatePassword(userCredentials);

				IdentityDTO identity = setIdentityDto(userCredentials);
				FMSResponse<EntityDTO> resetPwdEntity = userService
				        .getResetPwdEntity(identity);

				EntityDTO entity = resetPwdEntity.getEntity();
				List<FieldMapDTO> fieldValues = entity.getFieldValues();

				FieldMapDTO fieldMapDTO = new FieldMapDTO();
				fieldMapDTO.setKey("valid");
				fieldMapDTO = fetchField(fieldValues, fieldMapDTO);
				fieldValues.remove(fieldMapDTO);
				fieldMapDTO.setValue("false");
				fieldValues.add(fieldMapDTO);

				userService.updateEntryResetPwd(entity);

				if (statusMessageDTO.getEntity().getStatus().getStatus()
				        .equalsIgnoreCase("SUCCESS")) {
					if (httpSession != null) {
						TokenManager
						        .invalidateToken(FMSTokenHolder.getBearer());
						httpSession.invalidate();
					}

				} else {

					throw new Exception();
				}

			}
		} catch (Exception e) {
			ErrorDTO error = new ErrorDTO();
			error.setErrorMessage("Change Password failed.");
			mv = new ModelAndView(SET_PASSWORD_PATH_NAME);
			mv.addObject(ERROR, error);
		}
		mv.addObject("response", "Password of " + userCredentials.getUserName()
		        + " is changed");
		return mv;
	}

	private IdentityDTO setIdentityDto(UserCredentials userCredentials) {
		IdentityDTO identity = new IdentityDTO();

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(userCredentials.getDomain());
		identity.setDomain(domain);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName("ResetPassword");
		identity.setEntityTemplate(entityTemplate);

		FieldMapDTO field = new FieldMapDTO();
		field.setKey("identifier");
		field.setValue(userCredentials.getIdentifier());
		identity.setIdentifier(field);

		return identity;
	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {

		FieldMapDTO field = new FieldMapDTO();
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	private String getHomePage() {
		String homePage = "";
		if (FMSAccessManager.isSuperAdmin()) {
			homePage = OWNER_HOME_VIEW;
		} else if (FMSAccessManager.isTenantAdmin()) {
			homePage = CLIENT_HOME_VIEW;
		}
		return homePage;
	}

	private void configureUserName(UserCredentials credentials) {
		String userName = credentials.getUserName();
		userName += MY_DOMAIN_NAME;
		credentials.setUserName(userName);
	}

	private Map<String, List<String>> itratePermission(
	        List<String> permissionList) {

		Map<String, List<String>> permissionGroup = new HashMap<>();
		for (String permission : permissionList) {

			String[] split = permission.split("/");
			if (split.length > 1) {
				if (permissionGroup.containsKey(split[0])) {
					List<String> permissionArray = permissionGroup
					        .get(split[0]);
					permissionArray.add(split[1]);
				} else {
					List<String> permissionArray = new ArrayList<String>();
					permissionArray.add(split[1]);
					permissionGroup.put(split[0], permissionArray);
				}
			}
		}
		return permissionGroup;
	}

	private String getImage(String userName, String domainName) {

		Image image = new Image();
		image.setDomain(domainName);
		image.setModule(USER_FOLDER_NAME);
		image.setFilename(userName + FILE_IMAGE_TYPE);
		String userImage = fileServer.doDowload(image);
		return userImage;

	}

	private String getTenantImage(String tenantName, String domainName) {

		Image image = new Image();
		image.setDomain(domainName);
		image.setModule(TENANT_FOLDER_NAME);
		image.setFilename(tenantName + FILE_IMAGE_TYPE);
		String userImage = fileServer.doDowloadNoDefault(image);
		return userImage;

	}
}