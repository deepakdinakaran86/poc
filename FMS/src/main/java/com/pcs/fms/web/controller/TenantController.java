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

import static com.pcs.fms.web.constants.FMSWebConstants.ACTION;
import static com.pcs.fms.web.constants.FMSWebConstants.ADMIN_PASSWORD;
import static com.pcs.fms.web.constants.FMSWebConstants.ADMIN_USER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.CLIENT_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.CREATE;
import static com.pcs.fms.web.constants.FMSWebConstants.CREATE_CLIENT;
import static com.pcs.fms.web.constants.FMSWebConstants.CREATE_TENANT_ADMIN;
import static com.pcs.fms.web.constants.FMSWebConstants.CREATE_TENANT_USER;
import static com.pcs.fms.web.constants.FMSWebConstants.CURRENT_DOMAIN;
import static com.pcs.fms.web.constants.FMSWebConstants.FILE_IMAGE_TYPE;
import static com.pcs.fms.web.constants.FMSWebConstants.IDENTIFIER;
import static com.pcs.fms.web.constants.FMSWebConstants.MYOWN_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.PAGE_TITLE;
import static com.pcs.fms.web.constants.FMSWebConstants.RESPONSE;
import static com.pcs.fms.web.constants.FMSWebConstants.SELECTED_FEATURES;
import static com.pcs.fms.web.constants.FMSWebConstants.SUB_TENANT_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_ADMIN_TEMPLATE;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_CREATE;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_HOME;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_SEND_USER_CREATE;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_SEND_USER_EMAIL;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_SEND_USER_EMAIL_ACTION;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_SEND_USER_EMAIL_FORM;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_USER_CREATED;
import static com.pcs.fms.web.constants.FMSWebConstants.TENANT_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.USER_SET_PASSWORD;
import static com.pcs.fms.web.constants.FMSWebConstants.VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.VIEW_CLIENT;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSResponseManager;
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.Image;
import com.pcs.fms.web.dto.Status;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.dto.Tag;
import com.pcs.fms.web.dto.TenantAdminEmailDTO;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Tenant;
import com.pcs.fms.web.manager.dto.Token;
import com.pcs.fms.web.model.FileUploadForm;
import com.pcs.fms.web.model.Tenants;
import com.pcs.fms.web.model.User;
import com.pcs.fms.web.model.UserCredentials;
import com.pcs.fms.web.services.AuthService;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.FileService;
import com.pcs.fms.web.services.TenantService;

/**
 * @author PCSEG191 Daniela
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class TenantController extends BaseService {

	private static final Logger logger = LoggerFactory
	        .getLogger(TenantController.class);

	@Value("${apimanager.clientid}")
	private String clientId;

	@Value("${apimanager.clientSecret}")
	private String clientSecret;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private AuthService authService;

	@Autowired
	FileService fileServer;

	@Value("${client.create.success}")
	private String clientCreateSuccess;

	@Value("${client.create.failure}")
	private String clientCreateFailure;

	@Value("${client.update.success}")
	private String clientUpdateSuccess;

	@Value("${client.update.failure}")
	private String clientUpdateFailure;

	@Value("${client.admin.link.failure}")
	private String adminLinkFailure;

	@Value("${client.admin.success}")
	private String clientAdminSuccess;

	@Value("${client.admin.failure}")
	private String clientAdminFailure;

	@Value("${client.session.exists}")
	private String clientSessionExists;

	@Value("${client.send.email.success}")
	private String sendEmailSuccess;

	@Value("${client.send.email.failure}")
	private String sendEmailFailure;

	@Value("${client.admin.success.link}")
	private String clientAdminSuccessLink;

	@RequestMapping(value = TENANT_HOME,method = RequestMethod.GET)
	public ModelAndView viewTenantPage(HttpServletRequest request) {
		Boolean hasPermission = FMSAccessManager.hasPermissionAccess(
		        "client_management", "list");
		ModelAndView mv = null;
		if (hasPermission) {
			mv = new ModelAndView(TENANT_HOME);
			mv.addObject(CURRENT_DOMAIN, getCurrentDomain());
		} else {
			mv = new ModelAndView("redirect:" + MYOWN_HOME_PATH_NAME);
		}
		return mv;
	}

	/**
	 * This controller will return view and List<entityDto> as response of
	 * viewAllTenants service
	 * 
	 * @param identityDto
	 * @return ModelAndView
	 */
	@RequestMapping(value = TENANT_HOME,method = RequestMethod.POST)
	public ModelAndView viewAllTenants(@ModelAttribute FieldMapDTO domainMap,
	        RedirectAttributes redirect) {
		String response = null;
		String domain = domainMap.getValue();
		// Invoke the service
		List<Tenants> tenants = tenantService.getTenants(domain);

		redirect.addFlashAttribute(RESPONSE, response);
		// Construct response payload
		Gson gson = new Gson();
		String json = gson.toJson(tenants);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		ModelAndView mv = new ModelAndView(TENANT_HOME);
		mv.addObject(ACTION, VIEW);
		mv.addObject(TENANT_HOME, json);
		mv.addObject(CURRENT_DOMAIN, getCurrentDomain());
		return mv;
	}

	@RequestMapping(value = TENANT_VIEW,method = RequestMethod.POST)
	public ModelAndView getTenantView(FieldMapDTO field) {
		ModelAndView mv = new ModelAndView(TENANT_CREATE);
		mv.addObject(ACTION, VIEW);

		// fetch the identifiers from the input
		Gson gson = new Gson();
		Tenants selectedTenant = gson.fromJson(field.getValue(), Tenants.class);

		// Invoke find service
		Tenants tenant = tenantService.getTenantDetails(
		        selectedTenant.getIdentifier(),
		        selectedTenant.getParentDomain(), selectedTenant.getDomain());

		// Construct response payload
		// Extract the selected features of the client
		String featureList = gson.toJson(tenant.getTenantFeatures());
		getImage(tenant);
		mv.addObject(SELECTED_FEATURES, featureList);
		// mv.addObject("availableFeatures", tenant.getAvailableFeatures());
		mv.addObject(TENANT_CREATE, tenant);
		mv.addObject(CURRENT_DOMAIN, getCurrentDomain());
		mv.addObject(PAGE_TITLE, VIEW_CLIENT);
		return mv;
	}

	@RequestMapping(value = TENANT_SEND_USER_EMAIL,method = RequestMethod.POST)
	public ModelAndView getSendEmail(FieldMapDTO field) {
		// Construct the model and view for send email
		ModelAndView mv = new ModelAndView(TENANT_SEND_USER_EMAIL_FORM);
		mv.addObject(ACTION, VIEW);
		Gson gson = new Gson();
		Tenants tenants = gson.fromJson(field.getValue(), Tenants.class);
		tenants.setContactEmail("");
		mv.addObject(TENANT_SEND_USER_EMAIL_FORM, tenants);
		mv.addObject(CURRENT_DOMAIN, getCurrentDomain());
		return mv;
	}

	@RequestMapping(value = TENANT_SEND_USER_EMAIL_ACTION,
	        method = RequestMethod.POST)
	public ModelAndView sendEmail(@ModelAttribute Tenants user,
	        HttpServletRequest request, RedirectAttributes redirect) {

		// Get url for create admin user
		String notifyUrl = getNotifyURl(request);
		String response = null;
		// Construct the payload
		TenantAdminEmailDTO tenantAdminEmailDTO = new TenantAdminEmailDTO();
		tenantAdminEmailDTO.setEmail(user.getContactEmail());
		tenantAdminEmailDTO.setTenantDomain(user.getDomain());
		tenantAdminEmailDTO.setCreateTenantAdminUrl(notifyUrl
		        + CREATE_TENANT_USER);

		// Send the email
		FMSResponse<StatusMessageDTO> emailStatus = tenantService
		        .sendTenantAdminEmail(tenantAdminEmailDTO);
		if (emailStatus.getEntity() != null
		        && emailStatus.getEntity().getStatus().getStatus()
		                .equalsIgnoreCase(Status.SUCCESS.toString())) {

			RedirectView redirectView = new RedirectView(TENANT_HOME);
			ModelAndView mav = new ModelAndView(redirectView);
			response = FMSResponseManager.success(sendEmailSuccess);
			mav.addObject(TENANT_HOME);
			mav.addObject(ACTION, CREATE);
			redirect.addFlashAttribute(RESPONSE, response);
			return mav;

		} else {
			RedirectView redirectView = new RedirectView(
			        TENANT_SEND_USER_EMAIL_FORM);
			response = FMSResponseManager.error(sendEmailFailure);
			redirect.addFlashAttribute(RESPONSE, response);
			response = FMSResponseManager.error(emailStatus.getErrorMessage()
			        .getErrorMessage());
			return viewSendEmailOnError(user, redirect, redirectView, response);
		}
	}

	private String getNotifyURl(HttpServletRequest request) {
		String notifyUrl = request.getRequestURL().toString();
		notifyUrl = notifyUrl.replace(TENANT_SEND_USER_EMAIL_ACTION, "");
		notifyUrl = notifyUrl.replace(CREATE_TENANT_ADMIN, "");
		return notifyUrl;

	}

	@RequestMapping(value = TENANT_CREATE,method = RequestMethod.POST)
	public ModelAndView updateTenant(@ModelAttribute Tenants tenant,
	        RedirectAttributes redirect) {
		logger.debug("Inside tenant Update");
		RedirectView redirectView = new RedirectView(TENANT_HOME);
		ModelAndView mav = new ModelAndView(redirectView);
		tenant.setParentDomain(getCurrentDomain());
		// Invoke service
		FMSResponse<StatusMessageDTO> status = tenantService
		        .updateTenant(tenant);
		String response = null;
		// Construct response
		if (status.getEntity().getStatus().getStatus()
		        .equalsIgnoreCase(Status.SUCCESS.toString())) {
			if (tenant.getAction().equalsIgnoreCase("Update")) {
				response = FMSResponseManager.success(clientUpdateSuccess);
			} else {
				response = FMSResponseManager.success(clientCreateSuccess);
			}

			saveImage(tenant);
		} else {
			redirect.addFlashAttribute(RESPONSE, response);
			response = FMSResponseManager.error(status.getErrorMessage()
			        .getErrorMessage());
			redirectView = new RedirectView(TENANT_CREATE);
			return viewTenantOnError(tenant, redirect, redirectView, response);
		}
		redirect.addFlashAttribute(RESPONSE, response);
		String domain = tenant.getParentDomain();
		// Redirect to list page , hence fetch tenant list
		List<Tenants> res = tenantService.getTenants(domain);
		Gson gson = new Gson();
		String json = gson.toJson(res);

		// Construct response payload
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		ModelAndView mv = new ModelAndView(TENANT_HOME);
		mv.addObject(ACTION, VIEW);
		mv.addObject(TENANT_HOME, json);
		mv.addObject(CURRENT_DOMAIN, getCurrentDomain());

		return mav;
	}

	@RequestMapping(value = TENANT_CREATE,method = RequestMethod.GET)
	public ModelAndView getTenantCreate() {
		Boolean hasPermission = FMSAccessManager.hasPermissionAccess(
		        "client_management", "create");
		ModelAndView mav = null;
		if (hasPermission) {
			mav = new ModelAndView(TENANT_CREATE);
			// Construct the model
			Tenants tenant = new Tenants();
			tenant.setStatus("ACTIVE");
			mav.addObject(ACTION, CREATE);
			mav.addObject(CURRENT_DOMAIN, getCurrentDomain());
			List<String> selectedFeatures = new ArrayList<String>();
			mav.addObject(SELECTED_FEATURES, selectedFeatures);
			mav.addObject(TENANT_CREATE, tenant);
			mav.addObject(PAGE_TITLE, CREATE_CLIENT);
		} else {
			mav = new ModelAndView("redirect:" + MYOWN_HOME_PATH_NAME);
		}
		return mav;
	}

	@RequestMapping(value = CREATE_TENANT_USER + "/{identifier}",
	        method = RequestMethod.GET)
	public ModelAndView getTenantUserCreationLink(
	        @PathVariable("identifier") String identifier,
	        HttpSession httpSession, HttpServletRequest request,
	        RedirectAttributes redirect) {
		// if (httpSession != null) {
		// TokenManager.invalidateToken(FMSTokenHolder.getBearer());
		// httpSession.invalidate();
		// }

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
		entityTemplateDTO.setEntityTemplateName(TENANT_ADMIN_TEMPLATE);
		identityDto.setEntityTemplate(entityTemplateDTO);

		// Set identifier
		FieldMapDTO tenantAdminId = new FieldMapDTO();
		tenantAdminId.setKey(IDENTIFIER);
		tenantAdminId.setValue((parts[0]));
		identityDto.setIdentifier(tenantAdminId);

		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setUserName(ADMIN_USER_NAME);
		userCredentials.setPassword(ADMIN_PASSWORD);
		Token token = authService.authenticate(userCredentials, httpSession);
		TokenManager.setToken(token, httpSession.getId());

		String email = tenantService.getAdminEmail(identityDto);
		String response = null;
		if (isBlank(email)) {
			ModelAndView mav = new ModelAndView(TENANT_USER_CREATED);
			mav.addObject(TENANT_USER_CREATED, new User());
			mav.addObject(ACTION, CREATE);
			response = FMSResponseManager.error(adminLinkFailure);
			mav.addObject(RESPONSE, response);
			return mav;
		}
		ModelAndView mv = new ModelAndView(TENANT_SEND_USER_CREATE);
		User user = new User();
		user.setEmailId(email);
		user.setTenantAdminEmail(email);
		user.setIdentifier(tenantAdminId.getValue());
		user.setDomain(domain.getDomainName());
		mv.addObject(TENANT_SEND_USER_CREATE, user);
		return mv;
	}

	@RequestMapping(value = CREATE_TENANT_ADMIN,method = RequestMethod.POST)
	public ModelAndView createTenantAdmin(@ModelAttribute User user,
	        RedirectAttributes redirectAttributes, HttpSession httpSession,
	        HttpServletRequest request, RedirectAttributes redirect) {

		if (httpSession != null) {
			TokenManager.invalidateToken(FMSTokenHolder.getBearer());
			httpSession.invalidate();
		}
		// Get set pswd url
		String resetPswdLink = getNotifyURl(request) + USER_SET_PASSWORD;
		Token token = authService.authenticate(httpSession);
		TokenManager.setToken(token, httpSession.getId());
		String response = null;
		FMSResponse<StatusMessageDTO> status = tenantService.createTenantAdmin(
		        user, resetPswdLink);
		ModelAndView mv = new ModelAndView(TENANT_USER_CREATED);
		if (status.getEntity() != null
		        && status.getEntity().getStatus().getStatus()
		                .equalsIgnoreCase(Status.SUCCESS.toString())) {
			response = FMSResponseManager.success(clientAdminSuccess);
		} else {
			RedirectView redirectView = new RedirectView(CREATE_TENANT_USER);
			response = FMSResponseManager.error(status.getErrorMessage()
			        .getErrorMessage());
			redirect.addFlashAttribute(RESPONSE, response);
			redirectView = new RedirectView(CREATE_TENANT_USER);
			return viewCreateAdminOnError(user, redirect, redirectView,
			        response);
		}
		mv.addObject(RESPONSE, response);
		mv.addObject(TENANT_USER_CREATED, new User());
		mv.addObject(ACTION, CREATE);
		return mv;
	}

	@RequestMapping(value = SUB_TENANT_VIEW,method = RequestMethod.POST)
	public ModelAndView getSubTenantView(FieldMapDTO field,
	        HttpSession httpSession, RedirectAttributes redirect) {
		// Always redirect to client home
		Gson gson = new Gson();
		Tenants selectedSubTenant = gson.fromJson(field.getValue(),
		        Tenants.class);

		Tenant currentTenant = new Tenant();

		currentTenant.setTenantId(selectedSubTenant.getTenantId());
		currentTenant.setCurrentDomain(selectedSubTenant.getCurrentDomain());
		currentTenant.setDomainName(selectedSubTenant.getParentDomain());
		currentTenant.setTenantName(selectedSubTenant.getTenantName());
		currentTenant.setPlatformEntityType("TENANT");

		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());

		currentTenant.setTenantImage(getTenantImage(
		        selectedSubTenant.getTenantId(),
		        selectedSubTenant.getParentDomain()));

		token.setCurrentTenant(currentTenant);
		token.setIsSubClientSelected(Boolean.TRUE);

		TokenManager.setToken(token, FMSTokenHolder.getBearer());

		ModelAndView mv = new ModelAndView("redirect:" + CLIENT_HOME_PATH_NAME);
		return mv;
	}

	/**
	 * @param httpSession
	 * @param request
	 * @return
	 */
	private String getCurrentDomain() {
		// Get the current token
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());

		String domain = null;
		// Check if a subclient is selected
		if (token.getIsSubClientSelected() != null
		        && token.getIsSubClientSelected()) {
			// get sub tenant selected domain
			domain = token.getCurrentTenant().getCurrentDomain();
		} else {
			domain = token.getTenant().getCurrentDomain();
		}
		return domain;
	}

	private void saveImage(Tenants tenant) {

		FileUploadForm tenantImage = new FileUploadForm();
		tenantImage.setFileDatas(tenant.getFileDatas());

		Image image = new Image();
		image.setDomain(tenant.getParentDomain());
		image.setModule(TENANT_FOLDER_NAME);
		image.setFilename(tenant.getTenantId() + FILE_IMAGE_TYPE);
		CommonsMultipartFile[] fileDatas = tenantImage.getFileDatas();
		if (fileDatas.length > 0) {
			fileServer.doUpload(fileDatas[0], image);
		}

	}

	private void getImage(Tenants tenant) {

		Image image = new Image();
		image.setDomain(tenant.getParentDomain());
		image.setModule(TENANT_FOLDER_NAME);
		image.setFilename(tenant.getTenantId() + FILE_IMAGE_TYPE);
		String tenantImage = fileServer.doDowload(image);
		tenant.setImage(tenantImage);

	}

	private String getTenantImage(String tenantName, String domainName) {

		Image image = new Image();
		image.setDomain(domainName);
		image.setModule(TENANT_FOLDER_NAME);
		image.setFilename(tenantName + FILE_IMAGE_TYPE);
		String userImage = fileServer.doDowloadNoDefault(image);
		return userImage;

	}

	@SuppressWarnings("serial")
	private ModelAndView viewTenantOnError(Tenants tenants,
	        RedirectAttributes redirectAttributes, RedirectView redirectView,
	        String response) {
		ModelAndView mv = new ModelAndView(TENANT_CREATE);
		mv.addObject(RESPONSE, response);
		mv.addObject(TENANT_CREATE, tenants);
		Gson gson = new Gson();
		mv.addObject(SELECTED_FEATURES,
		        gson.toJson(tenants.getTenantFeatures()));
		mv.addObject("pageError", "true");
		if (tenants.getAction().equalsIgnoreCase("Update")) {
			mv.addObject("operation", tenants.getAction());
		} else {
			mv.addObject("operation", "create");
		}
		Type type = new TypeToken<List<Tag>>() {
		}.getType();
		List<Tag> myTags = gson.fromJson(tenants.getSelectedTags(), type);
		mv.addObject("tagsOnError", tenants.getSelectedTags());

		return mv;
	}

	@SuppressWarnings("serial")
	private ModelAndView viewSendEmailOnError(Tenants tenant,
	        RedirectAttributes redirectAttributes, RedirectView redirectView,
	        String response) {
		ModelAndView mv = new ModelAndView(TENANT_SEND_USER_EMAIL_FORM);
		mv.addObject(RESPONSE, response);
		mv.addObject(TENANT_SEND_USER_EMAIL_FORM, tenant);
		return mv;
	}

	@SuppressWarnings("serial")
	private ModelAndView viewCreateAdminOnError(User user,
	        RedirectAttributes redirectAttributes, RedirectView redirectView,
	        String response) {
		ModelAndView mv = new ModelAndView(CREATE_TENANT_USER);
		mv.addObject(RESPONSE, response);
		mv.addObject(CREATE_TENANT_USER, user);
		return new ModelAndView(CREATE_TENANT_USER);
		// return mv;
	}

}
