package com.pcs.fms.web.controller;

import static com.pcs.fms.web.constants.FMSWebConstants.FILE_IMAGE_TYPE;
import static com.pcs.fms.web.constants.FMSWebConstants.TRACKING_VEHICLE_DETAILS;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_FOLDER_NAME;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.DeviceLocationDTO;
import com.pcs.fms.web.dto.Image;
import com.pcs.fms.web.services.FileService;
import com.pcs.fms.web.services.TrackingVehicleDetailsService;
import com.pcs.fms.web.services.VehicleService;

@Controller
public class TrackingVehicleDetailsContoller {

	@Autowired
	VehicleService vehicleService;

	@Autowired
	TrackingVehicleDetailsService trackingVehicleDetailsService;

	@Autowired
	private FileService fileServer;

	@RequestMapping(value = TRACKING_VEHICLE_DETAILS, method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {

		FMSResponse<List<DeviceLocationDTO>> vehiclesList = trackingVehicleDetailsService
				.listVehicles();

		ModelAndView mv = new ModelAndView(TRACKING_VEHICLE_DETAILS);
		mv.addObject("payload", getCurrentTenantInfo());
		Gson gson = new Gson();
		if (null != vehiclesList.getEntity()) {
			mv.addObject("vehicleListresponse",
					gson.toJson(vehiclesList.getEntity()));
		} else {
			mv.addObject("vehicleListresponse",
					gson.toJson(vehiclesList.getErrorMessage()));
		}
		return mv;
	}
	
	@RequestMapping(value = TRACKING_VEHICLE_DETAILS, method = RequestMethod.POST)
	public ModelAndView list(HttpSession session,
			@RequestParam("vehicle_id") String vehicleId) {

		FMSResponse<List<DeviceLocationDTO>> vehiclesList = trackingVehicleDetailsService
				.listVehicles();

		ModelAndView mv = new ModelAndView(TRACKING_VEHICLE_DETAILS);
		mv.addObject("payload", getCurrentTenantInfo());
		Gson gson = new Gson();
		if (null != vehiclesList.getEntity()) {
			mv.addObject("vehicleListresponse",
					gson.toJson(vehiclesList.getEntity()));
			mv.addObject("vehicle_id", vehicleId);
		} else {
			mv.addObject("vehicleListresponse",
					gson.toJson(vehiclesList.getErrorMessage()));
		}
		return mv;
	}

	private String getCurrentTenantInfo() {
		String payload = "{\"currentDomain\":\"{currentDomainName}\",\"tenantId\":\"{currentTenantId}\"}";
		payload = payload.replace("{currentDomainName}",
				FMSAccessManager.getCurrentDomain());
		payload = payload.replace("{currentTenantId}",
				FMSAccessManager.getCurrentTenantId());
		return payload;
	}

	@RequestMapping(value = "/downloadOneFile", method = RequestMethod.GET)
	@ResponseBody
	public String getVImage(@RequestParam("domain_name") String domainName,
			@RequestParam("id") String id) {

		Image image = new Image();
		image.setDomain(domainName);
		image.setModule(VEHICLE_FOLDER_NAME);
		image.setFilename(id + FILE_IMAGE_TYPE);
		String str = "{\"imgString\":\"" + fileServer.doDowload(image) + "\"}";
		return str;
	}
}
