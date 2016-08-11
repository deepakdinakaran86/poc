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

import static com.pcs.fms.web.constants.FMSWebConstants.FILE_IMAGE_TYPE;
import static com.pcs.fms.web.constants.FMSWebConstants.LIVE_TRACKING_HOME_PATH;
import static com.pcs.fms.web.constants.FMSWebConstants.LIVE_TRACKING_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_ICONS_HOME_PATH;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_IMAGE_HOME_PATH;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_TYPE_ICON_FOLDER_NAME;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.Image;
import com.pcs.fms.web.services.FileService;
import com.pcs.fms.web.services.TagService;
import com.pcs.fms.web.services.VehicleService;

/**
 * @author PCSEG296 RIYAS PH
 * @date JULY 2016
 * @since FMS1.0.0
 */
@Controller
public class LiveTrackingContoller {

	@Autowired
	VehicleService vehicleService;

	@Autowired
	TagService tagService;

	@Autowired
	private FileService fileServer;

	@RequestMapping(value = LIVE_TRACKING_HOME_PATH,method = RequestMethod.GET)
	public ModelAndView viewHome(HttpSession session) {
		return new ModelAndView(LIVE_TRACKING_HOME_VIEW);
	}

	@RequestMapping(value = VEHICLE_ICONS_HOME_PATH,method = RequestMethod.GET)
	@ResponseBody
	public String allVehicleIconss() {

		Image image = new Image();
		image.setDomain(FMSAccessManager.getCurrentDomain());
		image.setModule(VEHICLE_TYPE_ICON_FOLDER_NAME);
		Map<String, String> fileMap = fileServer.downloadAllFileFTP(image);
		Gson gson = new Gson();
		return gson.toJson(fileMap);
	}

	@RequestMapping(value = VEHICLE_IMAGE_HOME_PATH,method = RequestMethod.POST)
	@ResponseBody
	public String allVehicleImage(@RequestBody FieldMapDTO filed) {

		Image image = new Image();
		image.setDomain(FMSAccessManager.getCurrentDomain());
		image.setModule(VEHICLE_FOLDER_NAME);
		image.setFilename(filed.getKey() + FILE_IMAGE_TYPE);
		filed.setValue(fileServer.doDowload(image));
		Gson gson = new Gson();
		return gson.toJson(filed);
	}

}
