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

import static com.pcs.fms.web.constants.FMSWebConstants.GEO_FENCE_LIST_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.ROUTE_HOME;
import static com.pcs.fms.web.constants.FMSWebConstants.ROUTE_MAP;
import static com.pcs.fms.web.constants.FMSWebConstants.ROUTE_VIEW;






import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.hazelcast.util.CollectionUtil;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSResponseManager;
import com.pcs.fms.web.dto.RoutingDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.RouteModelDTO;
import com.pcs.fms.web.model.VehicleModelDTO;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.RouteService;

/**
 * @author PCSEG296 RIYAS PH, PCSEG336 Sekh
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class RouteController extends BaseService {
	
	
	@Value("${route.create.success}")
	private String routeCreatesuccess;
	
	@Value("${route.create.failed}")
	private String routeCreateFailed;

	private static final Logger logger = LoggerFactory
	        .getLogger(RouteController.class);

	@Autowired
	RouteService routeService;

	@RequestMapping(value = ROUTE_HOME,method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {

		String domainName = FMSAccessManager.getCurrentDomain();
		FMSResponse<List<RoutingDTO>> routes = routeService
		        .getAllRoutes(domainName);
		ModelAndView mv = new ModelAndView(ROUTE_HOME);
		if (CollectionUtil.isNotEmpty(routes.getEntity())) {
			Gson gson = new Gson();
			mv.addObject("routelist", gson.toJson(routes.getEntity()));
		} else {
			mv.addObject("routelist", new JSONObject());
		}
		return mv;
	}

	@RequestMapping(value = ROUTE_MAP,method = RequestMethod.GET)
	public ModelAndView login1(HttpServletRequest request) {
		return new ModelAndView(ROUTE_MAP);
	}
	
	
	@RequestMapping(value = ROUTE_MAP,method = RequestMethod.POST)
	public ModelAndView createRoute(@ModelAttribute RouteModelDTO route,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String domainName = FMSAccessManager.getCurrentDomain();
		String response=null;
		FMSResponse<StatusMessageDTO> routeResponse = routeService
		        .createRoute(route, domainName);
		ModelAndView mv = new ModelAndView(ROUTE_HOME);
		
		if(null == routeResponse.getErrorMessage()){
			if (routeResponse.getEntity() != null) {
				StatusMessageDTO status = routeResponse.getEntity();
				if ("SUCCESS".equals(status.getStatus().name())) {
					response = FMSResponseManager.success(routeCreatesuccess);
				} else {
					response = FMSResponseManager.error(routeCreateFailed);
				}
			} 		
			
		}else {
			if (routeResponse.getErrorMessage() != null) {
				response = FMSResponseManager.error(routeResponse.getErrorMessage()
				        .getErrorMessage());
			}
		}
		RedirectView redirectView = new RedirectView(ROUTE_HOME);
		mv.setView(redirectView);
		redirectAttributes.addFlashAttribute("response",
				response);
		return mv;

	}
	
	@RequestMapping(value = ROUTE_VIEW,method = RequestMethod.POST)
	public ModelAndView viewRoute(@RequestParam("route_name") String route_name) {
		String domainName = FMSAccessManager.getCurrentDomain();
		FMSResponse<RoutingDTO> routeResponse = routeService
		        .viewRoute(route_name, domainName);
		ModelAndView mv = new ModelAndView(ROUTE_MAP);
		Gson gson = new Gson();
		mv.addObject("routeResponse", gson.toJson(routeResponse.getEntity()));
		mv.addObject("routeView","1");
		return mv;
	}
	
}
