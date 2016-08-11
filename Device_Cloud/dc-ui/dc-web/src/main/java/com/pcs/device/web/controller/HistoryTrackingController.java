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
package com.pcs.device.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pcs.device.web.constants.WebConstants;

/**
 * History Tracking Controller
 * 
 * @author pcseg296
 * 
 */
@Controller
public class HistoryTrackingController {

	private static final Logger logger = LoggerFactory
	        .getLogger(HistoryTrackingController.class);

	@RequestMapping(value = WebConstants.HISTORY_TRACKING_PATH_NAME,
	        method = RequestMethod.GET)
	public ModelAndView historyTracking(HttpServletRequest request) {
		logger.info("History Tracking Controller :- Source id : "
		        + request.getParameter("sourceId"));
		ModelAndView mv = new ModelAndView(WebConstants.HISTORY_TRACKING_VIEW);
		mv.addObject("sourceId", request.getParameter("sourceId"));
		mv.addObject("datasourceName", request.getParameter("datasourceName"));
		return mv;
	}
}
