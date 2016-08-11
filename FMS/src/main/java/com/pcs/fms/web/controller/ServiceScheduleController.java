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
import static com.pcs.fms.web.constants.FMSWebConstants.COMPONENTS_TO_SAVE;
import static com.pcs.fms.web.constants.FMSWebConstants.CREATE;
import static com.pcs.fms.web.constants.FMSWebConstants.IDENTIFIER;
import static com.pcs.fms.web.constants.FMSWebConstants.MARKER;
import static com.pcs.fms.web.constants.FMSWebConstants.MYOWN_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.RESPONSE;
import static com.pcs.fms.web.constants.FMSWebConstants.SELECTED_COMPONENT_LIST;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICECOMPONENT_LIST;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICESCHEDULE_ADD;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICESCHEDULE_LIST;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICESCHEDULE_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICE_COMPONENT_TEMPLATE;
import static com.pcs.fms.web.constants.FMSWebConstants.UPDATE;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.PlatformEntityDTO;
import com.pcs.fms.web.dto.Status;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.ServiceComponent;
import com.pcs.fms.web.model.ServiceSchedule;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.ServiceComponentService;
import com.pcs.fms.web.services.ServiceScheduleService;

/**
 * @author PCSEG191 Daniela
 * @date JULY 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class ServiceScheduleController extends BaseService {

	private static final Logger logger = LoggerFactory
	        .getLogger(ServiceScheduleController.class);

	@Autowired
	ServiceComponentService serviceComponent;

	@Autowired
	ServiceScheduleService scheduleService;

	@Value("${service.schedule.create.success}")
	private String createScheduleSuccess;

	@Value("${service.schedule.create.failure}")
	private String createScheduleFailure;

	@Value("${service.schedule.update.success}")
	private String updateScheduleSuccess;

	@Value("${service.schedule.update.failure}")
	private String updateScheduleFailure;

	@RequestMapping(value = SERVICESCHEDULE_ADD,method = RequestMethod.GET)
	public ModelAndView getServiceSchedulePage(HttpServletRequest request) {
		Boolean hasPermission = FMSAccessManager.hasPermissionAccess(
		        "service_scheduling", "create");
		ModelAndView mav = null;
		if (hasPermission) {
			mav = new ModelAndView(SERVICESCHEDULE_ADD);
			ServiceSchedule schedule = new ServiceSchedule();
			mav.addObject(ACTION, CREATE);

			// Get the service comp
			List<ServiceComponent> components = serviceComponent
			        .getServiceComponents();

			Map<String, IdentityDTO> serviceCompObj = new LinkedHashMap<String, IdentityDTO>();
			List<String> serviceComp = new ArrayList<String>();

			getComponents(components, serviceCompObj, serviceComp);
			Gson gson = new Gson();
			String json = gson.toJson(serviceCompObj);
			schedule.setServiceComponentList(json);
			String jsonList = gson.toJson(serviceComp);

			List<String> selList = new ArrayList<String>();
			String selComp = gson.toJson(selList);
			mav.addObject(SELECTED_COMPONENT_LIST, selComp);
			mav.addObject(SERVICECOMPONENT_LIST, jsonList);
			mav.addObject(SERVICESCHEDULE_ADD, schedule);
		} else {
			mav = new ModelAndView("redirect:" + MYOWN_HOME_PATH_NAME);
		}
		return mav;
	}

	/**
	 * @param components
	 * @param serviceCompObj
	 * @param serviceComp
	 */
	private void getComponents(List<ServiceComponent> components,
	        Map<String, IdentityDTO> serviceCompObj, List<String> serviceComp) {
		for (ServiceComponent serviceComponent : components) {
			serviceComp.add(serviceComponent.getServiceComponentName());
			IdentityDTO entityDTO = new IdentityDTO();
			// Set template
			EntityTemplateDTO compTemplate = new EntityTemplateDTO();
			compTemplate.setEntityTemplateName(SERVICE_COMPONENT_TEMPLATE);
			entityDTO.setEntityTemplate(compTemplate);
			// Construct entity type
			PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
			platformEntityDTO.setPlatformEntityType(MARKER);
			entityDTO.setPlatformEntity(platformEntityDTO);
			// Set domain
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(serviceComponent.getDomain());
			entityDTO.setDomain(domain);
			// Set identifier
			FieldMapDTO identifier = new FieldMapDTO();
			identifier.setKey(IDENTIFIER);
			identifier.setValue(serviceComponent.getIdentifier());
			entityDTO.setIdentifier(identifier);
			serviceCompObj.put(serviceComponent.getServiceComponentName(),
			        entityDTO);
		}
	}

	@RequestMapping(value = SERVICESCHEDULE_ADD,method = RequestMethod.POST)
	public ModelAndView saveServiceSchedulePage(HttpServletRequest request,
	        ServiceSchedule serviceSchedule, RedirectAttributes redirect) {
		FMSResponse<StatusMessageDTO> status = new FMSResponse<StatusMessageDTO>();
		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<Map<String, IdentityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		// Get the saved objects
		Map<String, IdentityDTO> map = gson.fromJson(
		        serviceSchedule.getServiceComponentList(), stringStringMap);

		List<IdentityDTO> selectedComponents = new ArrayList<IdentityDTO>();
		// Get selected components
		for (String item : serviceSchedule.getComponents()) {
			IdentityDTO selectedServiceItem = map.get(item);
			selectedComponents.add(selectedServiceItem);
		}

		if (isNotBlank(serviceSchedule.getAction())
		        && serviceSchedule.getAction().equalsIgnoreCase(UPDATE)) {
			status = scheduleService.updateServiceSchedule(serviceSchedule,
			        selectedComponents);
		} else {
			status = scheduleService.createServiceSchedule(serviceSchedule,
			        selectedComponents);
		}
		String response = null;
		RedirectView redirectViewError = new RedirectView(SERVICESCHEDULE_ADD);
		if (isNotBlank(serviceSchedule.getAction())
		        && serviceSchedule.getAction().equalsIgnoreCase(UPDATE)) {
			if (status.getEntity() != null
			        && status.getEntity().getStatus().getStatus()
			                .equalsIgnoreCase(Status.SUCCESS.toString())) {
				response = FMSResponseManager.success(updateScheduleSuccess);
			} else {
				response = FMSResponseManager.error(status.getErrorMessage()
				        .getErrorMessage());
				redirect.addFlashAttribute(RESPONSE, response);
				return viewScheduleOnError(serviceSchedule, redirect,
				        redirectViewError, response);

			}
		} else {
			if (status.getEntity() != null
			        && status.getEntity().getStatus().getStatus()
			                .equalsIgnoreCase(Status.SUCCESS.toString())) {
				response = FMSResponseManager.success(createScheduleSuccess);
			} else {
				response = FMSResponseManager.error(status.getErrorMessage()
				        .getErrorMessage());
				redirect.addFlashAttribute(RESPONSE, response);
				return viewScheduleOnError(serviceSchedule, redirect,
				        redirectViewError, response);
			}
		}
		redirect.addFlashAttribute(RESPONSE, response);

		// Redirect to list page
		RedirectView redirectView = new RedirectView(SERVICESCHEDULE_LIST);
		ModelAndView mav = new ModelAndView(redirectView);
		return mav;
	}

	@RequestMapping(value = SERVICESCHEDULE_LIST,method = RequestMethod.GET)
	public ModelAndView listServiceSchedules(HttpServletRequest request,
	        RedirectAttributes redirect) {
		Boolean hasPermission = FMSAccessManager.hasPermissionAccess(
		        "service_scheduling", "list");
		ModelAndView mv = null;
		if (hasPermission) {
			mv = new ModelAndView(SERVICESCHEDULE_LIST);
			String currDomain = FMSAccessManager.getCurrentDomain();
			Gson gson = new Gson();
			FMSResponse<List<EntityDTO>> listOfServiceSchedules = scheduleService
			        .listServiceSchedules(currDomain);
			String serviceSchedulesJson = gson.toJson(listOfServiceSchedules
			        .getEntity());
			mv.addObject("serviceSchedulesResp", serviceSchedulesJson);
		} else {
			mv = new ModelAndView("redirect:" + MYOWN_HOME_PATH_NAME);
		}
		return mv;
	}

	@RequestMapping(value = SERVICESCHEDULE_VIEW,method = RequestMethod.POST)
	public ModelAndView getServiceScheduleView(FieldMapDTO field) {
		ModelAndView mv = new ModelAndView(SERVICESCHEDULE_ADD);
		mv.addObject(ACTION, "View");
		Gson gson = new Gson();
		ServiceSchedule schedule = gson.fromJson(field.getValue(),
		        ServiceSchedule.class);
		// Construct identifier
		IdentityDTO identityDTO = new IdentityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(FMSAccessManager.getCurrentDomain());
		identityDTO.setDomain(domain);
		FieldMapDTO identifier = new FieldMapDTO();
		identifier.setKey(IDENTIFIER);
		identifier.setValue(schedule.getIdentifier());
		identityDTO.setIdentifier(identifier);
		ServiceSchedule scheduleDetail = scheduleService
		        .findServiceSchedule(identityDTO);
		// Get available comp
		// Get the service comp
		List<ServiceComponent> components = serviceComponent
		        .getServiceComponents();

		Map<String, IdentityDTO> serviceCompObj = new LinkedHashMap<String, IdentityDTO>();
		List<String> serviceComp = new ArrayList<String>();

		getComponents(components, serviceCompObj, serviceComp);
		String json = gson.toJson(serviceCompObj);
		scheduleDetail.setServiceComponentList(json);
		// Set the full list of comp available
		String jsonList = gson.toJson(serviceComp);
		mv.addObject(SERVICECOMPONENT_LIST, jsonList);

		// Get the selected list
		scheduleDetail.setSelectedListOnEdit(scheduleDetail.getSelectedList());
		String selList = gson.toJson(scheduleDetail.getSelectedList());
		mv.addObject(SELECTED_COMPONENT_LIST, selList);
		mv.addObject(SERVICESCHEDULE_ADD, scheduleDetail);
		return mv;

	}

	@SuppressWarnings("serial")
	private ModelAndView viewScheduleOnError(ServiceSchedule serviceComp,
	        RedirectAttributes redirectAttributes, RedirectView redirectView,
	        String response) {
		ModelAndView mv = new ModelAndView(SERVICESCHEDULE_ADD);
		mv.addObject(RESPONSE, response);
		mv.addObject(SERVICESCHEDULE_ADD, serviceComp);

		List<String> serviceCompList = new ArrayList<String>();
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, IdentityDTO>>() {
		}.getType();
		Map<String, IdentityDTO> myMap = gson.fromJson(
		        serviceComp.getServiceComponentList(), type);
		for (String key : myMap.keySet()) {
			serviceCompList.add(key);
		}

		// Check if update
		List<String> listToAdd = new ArrayList<String>();
		if (isNotBlank(serviceComp.getIdentifier())) {
			mv.addObject(SELECTED_COMPONENT_LIST,
			        gson.toJson(serviceComp.getSelectedListOnEdit()));
			List<String> savedList = serviceComp.getSelectedListOnEdit();
			listToAdd = serviceComp.getComponents();
			listToAdd.removeAll(savedList);
		} else {
			listToAdd = serviceComp.getComponents();
		}
		mv.addObject(COMPONENTS_TO_SAVE, gson.toJson(listToAdd));
		mv.addObject(SERVICECOMPONENT_LIST, gson.toJson(serviceCompList));
		mv.addObject("pageError", "true");

		return mv;
	}

}
