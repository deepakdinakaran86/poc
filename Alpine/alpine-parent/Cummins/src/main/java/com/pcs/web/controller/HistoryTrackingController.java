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

import static com.pcs.web.constants.WebConstants.HISTORY_TRACKING_PATH_NAME;
import static com.pcs.web.constants.WebConstants.HISTORY_TRACKING_VIEW;
import static com.pcs.web.constants.WebConstants.NO_ACCESS;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pcs.alpine.token.Token;
import com.pcs.alpine.token.TokenManager;
import com.pcs.web.client.TokenHolder;
import com.pcs.web.dto.FieldMapDTO;

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

	private static final String PERMISSION_DATA_HISTORY = "data/history";

	@RequestMapping(value = HISTORY_TRACKING_PATH_NAME,
	        method = RequestMethod.GET)
	public ModelAndView historyTracking(FieldMapDTO field,
	        HttpServletRequest httpServletRequest) {
		logger.info("History Tracking Controller :- Source id : "
		        + field.getValue());
		if (hasNoAccess(PERMISSION_DATA_HISTORY)) {
			ModelAndView mv = new ModelAndView(field.getKey());
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(HISTORY_TRACKING_VIEW);
		// Get the sourceId
		mv.addObject("sourceId", field.getValue());
		return mv;
	}

	private boolean hasNoAccess(String permission) {
		boolean flag = true;
		Token token = TokenManager.getToken(TokenHolder.getBearer());
		if (token.getPremissions().contains(permission)) {
			flag = false;
		}
		return flag;
	}

}
