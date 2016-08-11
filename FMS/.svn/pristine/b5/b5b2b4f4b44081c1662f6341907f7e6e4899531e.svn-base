/**
 * s * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
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
import static com.pcs.fms.web.constants.FMSWebConstants.IDENTIFIER;
import static com.pcs.fms.web.constants.FMSWebConstants.MYOWN_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.RESPONSE;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICECOMPONENT_ADD;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICECOMPONENT_LIST;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICECOMPONENT_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICE_ITEM_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.UPDATE;
import static com.pcs.fms.web.constants.FMSWebConstants.VIEW;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSResponseManager;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.Status;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.ServiceComponent;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.ServiceComponentService;
import com.pcs.fms.web.services.ServiceItemService;

/**
 * @author PCSEG191 Daniela
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class ServiceComponentController extends BaseService {

	private static final Logger logger = LoggerFactory
	        .getLogger(ServiceComponentController.class);

	@Autowired
	ServiceComponentService serviceComponentService;

	@Autowired
	ServiceItemService serviceItemService;

	@Value("${service.component.create.success}")
	private String createComponentSuccess;

	@Value("${service.component.create.failure}")
	private String createComponentFailure;

	@Value("${service.component.update.success}")
	private String updateComponentSuccess;

	@Value("${service.component.update.failure}")
	private String updateComponentFailure;

	@RequestMapping(value = SERVICECOMPONENT_ADD,method = RequestMethod.GET)
	public ModelAndView getServiceCompPage(HttpServletRequest request) {
		Boolean hasPermission = FMSAccessManager.hasPermissionAccess(
		        "service_components", "create");
		ModelAndView mav = null;
		if (hasPermission) {
			mav = new ModelAndView(SERVICECOMPONENT_ADD);
			ServiceComponent serviceComponent = new ServiceComponent();
			serviceComponent.setStatus("ACTIVE");
			mav.addObject(ACTION, "Create");

			Map<String, String> serviceList = new LinkedHashMap<String, String>();

			FMSResponse<List<EntityDTO>> serviceItems = serviceItemService
			        .listServiceItems(FMSAccessManager.getCurrentDomain());

			if (serviceItems.getEntity() != null) {
				FieldMapDTO map = new FieldMapDTO();
				map.setKey(SERVICE_ITEM_NAME);
				Map<String, EntityDTO> serviceListObj = new LinkedHashMap<String, EntityDTO>();

				for (EntityDTO item : serviceItems.getEntity()) {

					FieldMapDTO serviceItemMap = fetchField(
					        item.getDataprovider(), map);
					serviceListObj.put(serviceItemMap.getValue(), item);

					serviceList.put(serviceItemMap.getValue(),
					        serviceItemMap.getValue());

					mav.addObject("serviceItems", serviceList);
				}
				Gson gson = new Gson();
				String json = gson.toJson(serviceListObj);
				serviceComponent.setServiceComponentList(json);
				mav.addObject(SERVICECOMPONENT_ADD, serviceComponent);
			}
		} else {
			mav = new ModelAndView("redirect:" + MYOWN_HOME_PATH_NAME);
		}
		return mav;
	}

	@RequestMapping(value = SERVICECOMPONENT_LIST,method = RequestMethod.GET)
	public ModelAndView getServiceComp(HttpServletRequest request) {
		Boolean hasPermission = FMSAccessManager.hasPermissionAccess(
		        "service_components", "list");
		ModelAndView mv = null;
		if (hasPermission) {
			Gson gson = new Gson();
			mv = new ModelAndView(SERVICECOMPONENT_LIST);
			List<ServiceComponent> res = serviceComponentService
			        .getServiceComponents();
			String json = gson.toJson(res);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(MediaType.APPLICATION_JSON);
			mv.addObject(ACTION, VIEW);
			mv.addObject(SERVICECOMPONENT_LIST, json);
		} else {
			mv = new ModelAndView("redirect:" + MYOWN_HOME_PATH_NAME);
		}
		return mv;
	}

	@RequestMapping(value = SERVICECOMPONENT_VIEW,method = RequestMethod.POST)
	public ModelAndView getServiceComponentView(FieldMapDTO field) {
		ModelAndView mv = new ModelAndView(SERVICECOMPONENT_ADD);
		mv.addObject(ACTION, VIEW);
		Gson gson = new Gson();
		ServiceComponent serviceComponent = gson.fromJson(field.getValue(),
		        ServiceComponent.class);

		IdentityDTO identityDTO = new IdentityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(FMSAccessManager.getCurrentDomain());
		identityDTO.setDomain(domain);
		FieldMapDTO identifier = new FieldMapDTO();
		identifier.setKey(IDENTIFIER);
		identifier.setValue(serviceComponent.getIdentifier());
		identityDTO.setIdentifier(identifier);
		ServiceComponent serviceComponentDTO = serviceComponentService
		        .getServiceComponentDetails(identityDTO);

		FieldMapDTO map = new FieldMapDTO();
		map.setKey(SERVICE_ITEM_NAME);

		Map<String, String> serviceList = new LinkedHashMap<String, String>();
		serviceList.put(serviceComponentDTO.getServiceItemName(),
		        serviceComponentDTO.getServiceItemName());
		mv.addObject("serviceItems", serviceList);
		mv.addObject(SERVICECOMPONENT_ADD, serviceComponentDTO);
		return mv;
	}

	@RequestMapping(value = SERVICECOMPONENT_ADD,method = RequestMethod.POST)
	public ModelAndView saveServiceComponent(
	        @ModelAttribute ServiceComponent serviceComponent,
	        RedirectAttributes redirect) {
		FMSResponse<StatusMessageDTO> status = new FMSResponse<StatusMessageDTO>();
		if (isNotBlank(serviceComponent.getAction())
		        && serviceComponent.getAction().equalsIgnoreCase(UPDATE)) {
			// Update service component
			status = serviceComponentService
			        .updateServiceComponent(serviceComponent);
		} else {
			// Create service component
			Gson gson = new Gson();
			Type stringStringMap = new TypeToken<Map<String, EntityDTO>>() {
				private static final long serialVersionUID = 5936335989523954928L;
			}.getType();

			Map<String, EntityDTO> map = gson
			        .fromJson(serviceComponent.getServiceComponentList(),
			                stringStringMap);
			// Get identifier fields for the selected serviceItem
			EntityDTO selectedServiceItem = map.get(serviceComponent
			        .getServiceItemName());
			status = serviceComponentService.createServiceComponent(
			        serviceComponent, selectedServiceItem);
		}

		Gson gson = new Gson();
		RedirectView redirectView = new RedirectView(SERVICECOMPONENT_LIST);
		ModelAndView mv = new ModelAndView(redirectView);
		String response = null;
		if (isNotBlank(serviceComponent.getAction())
		        && serviceComponent.getAction().equalsIgnoreCase(UPDATE)) {
			if (status.getEntity() != null
			        && status.getEntity().getStatus().getStatus()
			                .equalsIgnoreCase(Status.SUCCESS.toString())) {
				response = FMSResponseManager.success(updateComponentSuccess);
			} else {
				redirectView = new RedirectView(SERVICECOMPONENT_ADD);
				response = FMSResponseManager.error(status.getErrorMessage()
				        .getErrorMessage());
				redirect.addFlashAttribute(RESPONSE, response);
				return viewComponentOnError(serviceComponent, redirect,
				        redirectView, response);
			}
		} else {
			if (status.getEntity() != null
			        && status.getEntity().getStatus().getStatus()
			                .equalsIgnoreCase(Status.SUCCESS.toString())) {
				response = FMSResponseManager.success(createComponentSuccess);
			} else {
				redirectView = new RedirectView(SERVICECOMPONENT_ADD);
				response = FMSResponseManager.error(status.getErrorMessage()
				        .getErrorMessage());
				redirect.addFlashAttribute(RESPONSE, response);
				return viewComponentOnError(serviceComponent, redirect,
				        redirectView, response);
			}
		}
		redirect.addFlashAttribute(RESPONSE, response);

		return mv;
	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		// populate field<FieldMapDTO> from input EntityDto
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	@SuppressWarnings("serial")
	private ModelAndView viewComponentOnError(ServiceComponent serviceComp,
	        RedirectAttributes redirectAttributes, RedirectView redirectView,
	        String response) {
		ModelAndView mv = new ModelAndView(SERVICECOMPONENT_ADD);

		Gson gson = new Gson();
		Map<String, String> items = new HashMap<String, String>();

		Type type = new TypeToken<Map<String, EntityDTO>>() {
		}.getType();
		Map<String, EntityDTO> myMap = gson.fromJson(
		        serviceComp.getServiceComponentList(), type);
		for (String key : myMap.keySet()) {
			items.put(key, key);
		}
		mv.addObject("serviceItems", items);
		mv.addObject(RESPONSE, response);
		mv.addObject(SERVICECOMPONENT_ADD, serviceComp);
		return mv;
	}

}
