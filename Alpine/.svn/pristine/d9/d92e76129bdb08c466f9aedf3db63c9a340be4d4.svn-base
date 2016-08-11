package com.pcs.web.controller;

import static com.pcs.web.constants.WebConstants.LIVE_TRACKING;
import static com.pcs.web.constants.WebConstants.NO_ACCESS;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pcs.alpine.token.Token;
import com.pcs.alpine.token.TokenManager;
import com.pcs.web.client.TokenHolder;
import com.pcs.web.dto.FieldMapDTO;
import com.pcs.web.dto.LiveFieldsDTO;
import com.pcs.web.services.LiveTrackingService;

/**
 * @author pcseg129
 * 
 */
@Controller
public class LiveTrackingController {

	private static final Logger logger = LoggerFactory
	        .getLogger(GensetController.class);

	private static final String PERMISSION_DATA_LIVE = "data/live";

	@Autowired
	LiveTrackingService liveService;

	@RequestMapping(value = LIVE_TRACKING,method = RequestMethod.GET)
	public ModelAndView getLiveTracking(FieldMapDTO fieldMapDTO) {

		if (hasNoAccess(PERMISSION_DATA_LIVE)) {
			ModelAndView mv = new ModelAndView(fieldMapDTO.getKey());
			mv.addObject("response", NO_ACCESS);
			return mv;
		}

		Gson gson1 = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray jArray = parser.parse(fieldMapDTO.getValue())
		        .getAsJsonArray();

		List<LiveFieldsDTO> liveFields = new ArrayList<LiveFieldsDTO>();

		for (JsonElement obj : jArray) {
			LiveFieldsDTO cse = gson1.fromJson(obj, LiveFieldsDTO.class);
			liveFields.add(cse);
		}

		ModelAndView mav = new ModelAndView(LIVE_TRACKING);
		mav = liveService.getLiveTracking(liveFields);
		Gson gson = new Gson();
		String json = gson.toJson(mav.getModel());
		ModelAndView mv = new ModelAndView(LIVE_TRACKING);
		mv.addObject("assets", json);
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
