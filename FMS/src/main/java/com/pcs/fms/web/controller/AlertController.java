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

import static com.pcs.fms.web.constants.FMSWebConstants.ALERT_CONSOLE;
import static com.pcs.fms.web.constants.FMSWebConstants.ALERT_DEFINITION;
import static com.pcs.fms.web.constants.FMSWebConstants.ALERT_LOGS;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.dto.AssetDTO;
import com.pcs.fms.web.services.AlertService;
import com.pcs.fms.web.services.BaseService;

/**
 * @author PCSEG297 Twinkle
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class AlertController extends BaseService {

	@Autowired
	private AlertService alertService;

	/**
	 * This controller will return view and List<entityDto> as response of
	 * getAllAssets service
	 * 
	 * @param identityDto
	 * @return ModelAndView
	 */
	@RequestMapping(value = ALERT_DEFINITION,method = RequestMethod.GET)
	public ModelAndView listAssets(HttpSession session) {

		List<AssetDTO> vehicles = alertService.getAssets(FMSAccessManager
		        .getCurrentDomain());

		ModelAndView mv = new ModelAndView(ALERT_DEFINITION);
		if (null != vehicles) {
			Gson gson = new Gson();
			mv.addObject("payload", getPayloadForGrid());
			mv.addObject("assetListresponse", gson.toJson(vehicles));
		}
		return mv;
	}

	/**
	 * This controller will return view for Alert logs
	 * 
	 * @param identityDto
	 * @return ModelAndView
	 */
	@RequestMapping(value = ALERT_LOGS,method = RequestMethod.GET)
	public ModelAndView listAlerts(@ModelAttribute String fromdate,
	        @ModelAttribute String todate) {
		ModelAndView mv = new ModelAndView(ALERT_LOGS);
		mv.addObject("payload", getPayloadForGrid());
		return mv;
	}

	/**
	 * This controller will return view for Alert Console
	 * 
	 * @param identityDto
	 * @return ModelAndView
	 */
	@RequestMapping(value = ALERT_CONSOLE,method = RequestMethod.GET)
	public ModelAndView lists(@ModelAttribute String fromdate,
	        @ModelAttribute String todate) {
		ModelAndView mv = new ModelAndView(ALERT_CONSOLE);
		mv.addObject("payload", getPayloadForGrid());
		return mv;
	}

	private String getPayloadForGrid() {
		String payload = "{\"currentDomain\":\"{currentDomainName}\",\"tenantId\":\"{currentTenantId}\"}";

		payload = payload.replace("{currentDomainName}",
		        FMSAccessManager.getCurrentDomain());
		payload = payload.replace("{currentTenantId}",
		        FMSAccessManager.getCurrentTenantId());
		return payload;
	}
}
