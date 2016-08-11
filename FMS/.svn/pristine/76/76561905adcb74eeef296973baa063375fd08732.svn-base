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

import static com.pcs.fms.web.constants.FMSWebConstants.REDIRECT;
import static com.pcs.fms.web.constants.FMSWebConstants.RESPONSE;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICEITEM_ADD_PATH;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICEITEM_ADD_SERVICE;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICEITEM_LIST;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICEITEM_UPDATE_SERVICE;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICEITEM_VIEW_PATH;
import static com.pcs.fms.web.constants.FMSWebConstants.SERVICEITEM_VIEW_SERVICE;
import static com.pcs.fms.web.constants.FMSWebConstants.TAG_TYPE_HOME;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSResponseManager;
import com.pcs.fms.web.constants.FMSWebConstants;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.ServiceItemDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.ServiceModel;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.ServiceItemService;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class ServiceItemController extends BaseService {

	private static final Logger logger = LoggerFactory
			.getLogger(ServiceItemController.class);

	@Autowired
	private ServiceItemService serviceItemService;
	
	@Value("${service.item.create.success}")
	private String serviceItemCreateSuccess;
	
	@Value("${service.item.update.success}")
	private String serviceItemUpdateSuccess;

	@RequestMapping(value = SERVICEITEM_ADD_PATH, method = RequestMethod.GET)
	public ModelAndView addPage(HttpServletRequest request) {
		return new ModelAndView(SERVICEITEM_ADD_PATH);
	}

	@RequestMapping(value = SERVICEITEM_LIST, method = RequestMethod.GET)
	public ModelAndView listServiceItems(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(SERVICEITEM_LIST);
		String currDomain = FMSAccessManager.getCurrentDomain();
		Gson gson = new Gson();
		FMSResponse<List<EntityDTO>> listOfServiceItems = serviceItemService
				.listServiceItems(currDomain);
		String serviceItemsJson = gson.toJson(listOfServiceItems.getEntity());
		mv.addObject("serviceItemsResp", serviceItemsJson);
		return mv;
	}

	@RequestMapping(value = SERVICEITEM_VIEW_SERVICE, method = RequestMethod.POST)
	public ModelAndView viewServiceItem(
			@ModelAttribute ServiceModel serviceModel,
			HttpServletRequest request) {
		FMSResponse<ServiceItemDTO> serviceItemDto = serviceItemService
				.viewServiceItem(serviceModel);
		Gson gson = new Gson();	
		String serviceItemJson = null;
		serviceItemJson = gson.toJson(serviceItemDto.getEntity());
		
		ModelAndView mv = new ModelAndView(SERVICEITEM_VIEW_PATH);
		mv.addObject("serviceItemResp", serviceItemJson);
		return mv;
	}

	@RequestMapping(value = SERVICEITEM_ADD_SERVICE, method = RequestMethod.POST)
	public ModelAndView createServiceItem(FieldMapDTO field,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		ModelAndView mv = new ModelAndView();
		Gson gson = new Gson();
		ServiceItemDTO serviceItemDTO = gson.fromJson(field.getValue(),
				ServiceItemDTO.class);
		FMSResponse<StatusMessageDTO> status = serviceItemService
				.createServiceItem(serviceItemDTO);
		String response = null;
		String serviceItemJson = null;
		if (status.getEntity() != null) {
			response = FMSResponseManager.success(serviceItemCreateSuccess);
			serviceItemJson = gson.toJson(status.getEntity());
			
		} else {
			if (status.getErrorMessage() != null) {
				response = FMSResponseManager.error(status
						.getErrorMessage().getErrorMessage());
			}
		}
		mv = new ModelAndView(REDIRECT + SERVICEITEM_LIST);
		redirectAttributes.addFlashAttribute("serviceItemCreateResp",
				serviceItemJson);
		redirectAttributes.addFlashAttribute(RESPONSE, response);
		return mv;
	}

	@RequestMapping(value = SERVICEITEM_UPDATE_SERVICE, method = RequestMethod.POST)
	public ModelAndView updateServiceItem(FieldMapDTO field,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		Gson gson = new Gson();
		ServiceItemDTO serviceItemDTO = gson.fromJson(field.getValue(),
				ServiceItemDTO.class);
		FMSResponse<StatusMessageDTO> updateResp = serviceItemService
				.updateServiceItem(serviceItemDTO);
		String response = null;
		String sIUpdateJson = null;
		if (updateResp.getEntity() != null) {
			response = FMSResponseManager.success(serviceItemUpdateSuccess);
			sIUpdateJson = gson.toJson(updateResp.getEntity());
			
		} else {
			if (updateResp.getErrorMessage() != null) {
				response = FMSResponseManager.error(updateResp
						.getErrorMessage().getErrorMessage());
			}
		}
		mv = new ModelAndView(REDIRECT + SERVICEITEM_LIST);
		redirectAttributes.addFlashAttribute("serviceItemUpdateResp",
				sIUpdateJson);
		redirectAttributes.addFlashAttribute(RESPONSE, response);
		return mv;
	}
}
