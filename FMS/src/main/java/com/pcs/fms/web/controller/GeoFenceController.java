package com.pcs.fms.web.controller;

import static com.pcs.fms.web.constants.FMSWebConstants.EDIT;
import static com.pcs.fms.web.constants.FMSWebConstants.UPDATE;
import static com.pcs.fms.web.constants.FMSWebConstants.GEO_FENCE_HOME;
import static com.pcs.fms.web.constants.FMSWebConstants.GEO_FENCE_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.GEO_FENCE_LIST;
import static com.pcs.fms.web.constants.FMSWebConstants.GEO_FENCE_LIST_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.GEO_FENCE_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.NO_ACCESS;
import static com.pcs.fms.web.constants.FMSWebConstants.GEOFENCE_CANCEL;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_MANAGEMENT_LIST;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.pcs.fms.web.manager.dto.Token;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSResponseManager;
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.GeofenceDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.GeofenceModelDTO;
import com.pcs.fms.web.services.GeoFenceService;

/**
 * @author PCSEG336 Sekh
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class GeoFenceController extends BaseController {
	
	@Value("${geofence.update.success}")
	private String geofenceUpdateSuccess;
	
	@Value("${geofence.update.failed}")
	private String geofenceUpdateFailed;
	
	@Value("${geofence.create.success}")
	private String geofenceCreateSuccess;
	
	@Value("${geofence.create.failed}")
	private String geofenceCreateFailed;
	
	@Value("${geofence.delete.success}")
	private String geofenceDeleteSuccess;
	
	@Value("${geofence.delete.failed}")
	private String geofenceDeleteFailed;
	
	
	

	@Autowired
	GeoFenceService geofenceService;

	@RequestMapping(value = GEO_FENCE_LIST, method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		String domainName = FMSAccessManager.getCurrentDomain();
		FMSResponse<List<EntityDTO>> response=geofenceService.listGeofence(domainName);
		ModelAndView mv = new ModelAndView(GEO_FENCE_LIST_VIEW);
		if(null!=response){
			Gson gson = new Gson();
			mv.addObject("domainName",domainName);
			mv.addObject("geoListresponse", gson.toJson(response.getEntity()));
		}
	
		return mv;
	}
	
	
	@RequestMapping(value = GEO_FENCE_LIST, method = RequestMethod.POST)
	public ModelAndView list(HttpSession session,@RequestParam("geofence_delete_id") String geofence_id,RedirectAttributes redirectAttributes) {
		String domainName = FMSAccessManager.getCurrentDomain();
		FMSResponse<StatusMessageDTO> deleteResponse=geofenceService.deleteGeofence(domainName,geofence_id);
		String response=null;
		ModelAndView mv = new ModelAndView(GEO_FENCE_LIST_VIEW);
		if(null == deleteResponse.getErrorMessage()){
			if (deleteResponse.getEntity() != null) {
				StatusMessageDTO status = deleteResponse.getEntity();
				if ("SUCCESS".equals(status.getStatus().name())) {
					response = FMSResponseManager.success(geofenceDeleteSuccess);
				} else {
					response = FMSResponseManager.error(geofenceDeleteFailed);
				}
			} 		
			
		}else {
			if (deleteResponse.getErrorMessage() != null) {
				response = FMSResponseManager.error(deleteResponse.getErrorMessage()
				        .getErrorMessage());
			}
		}
		RedirectView redirectView = new RedirectView(GEO_FENCE_LIST_VIEW);
		mv.setView(redirectView);
		redirectAttributes.addFlashAttribute("response",
				response);
		return mv;
	}

	@RequestMapping(value = GEO_FENCE_HOME, method = RequestMethod.POST)
	public ModelAndView create1(@ModelAttribute GeofenceModelDTO geofence,RedirectAttributes redirectAttributes) {
		String domainName = FMSAccessManager.getCurrentDomain();
		Gson gson = new Gson();
		ModelAndView mv = new ModelAndView();
		String geofenceResponse=null;
		if(!isBlank(geofence.getGeofence_id()) && geofence.getGeofence_mode().equals(EDIT)){
			FMSResponse<GeofenceDTO> response = geofenceService
					.viewGeofence(geofence.getGeofence_id(),domainName);	
			if(null!=response){	
				mv.addObject("geofenceFields",gson.toJson(response.getEntity().getGeofenceFields()));
				response.getEntity().setGeofenceFields(null);			
				String viewResp=gson.toJson(response.getEntity());		
				mv.addObject("viewResponse", viewResp);
				mv.addObject("geoFenceId", geofence.getGeofence_id());
				mv.addObject("geoFenceEdit", "1");
			}
		}else if(!isBlank(geofence.getGeofence_id()) && geofence.getGeofence_mode().equals(UPDATE)){
			
				FMSResponse<StatusMessageDTO> response = geofenceService
						.updateGeofence(geofence);		
				if (null == response.getErrorMessage()) {
					if (response.getEntity() != null) {
						StatusMessageDTO status = response.getEntity();
						if ("SUCCESS".equals(status.getStatus().name())) {
							geofenceResponse = FMSResponseManager.success(geofenceUpdateSuccess);
						} else {
							geofenceResponse = FMSResponseManager.error(geofenceUpdateFailed);
						}
					} else {
						if (response.getErrorMessage() != null) {
							geofenceResponse = FMSResponseManager.error(response.getErrorMessage()
							        .getErrorMessage());
						}
					}
					
					RedirectView redirectView = new RedirectView(GEO_FENCE_LIST_VIEW);
					mv.setView(redirectView);
					redirectAttributes.addFlashAttribute("response",
							geofenceResponse);
				}
		}else{
			FMSResponse<StatusMessageDTO> response = geofenceService
					.createGeofence(geofence,domainName);				
				if (response.getEntity() != null) {
					StatusMessageDTO status = response.getEntity();
					if ("SUCCESS".equals(status.getStatus().name())) {
						geofenceResponse = FMSResponseManager.success(geofenceCreateSuccess);
					} else {
						geofenceResponse = FMSResponseManager.error(geofenceCreateFailed);
					}
				} else {
					if (response.getErrorMessage() != null) {
						geofenceResponse = FMSResponseManager.error(response.getErrorMessage()
						        .getErrorMessage());
					}
				}
				
				RedirectView redirectView = new RedirectView(GEO_FENCE_LIST_VIEW);
				mv.setView(redirectView);
				redirectAttributes.addFlashAttribute("response",
						geofenceResponse);
			
		}
		return mv;
	}

	@RequestMapping(value = GEO_FENCE_HOME, method = RequestMethod.GET)
	public ModelAndView view() {
			
		ModelAndView mv = new ModelAndView();
		if (FMSAccessManager.isTenantAdmin()) {
			return new ModelAndView(GEO_FENCE_HOME_VIEW);
		} else if (FMSAccessManager.isSuperAdmin()) {
			mv.addObject("geofenceFields","");
			mv.addObject("viewResponse", "");
			return new ModelAndView(GEO_FENCE_HOME_VIEW);
		} else {
			mv.addObject("type", "error");
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
	}

	@RequestMapping(value = GEO_FENCE_VIEW, method = RequestMethod.POST)
	public ModelAndView view(@RequestParam("geofence_id") String geofence_id) {
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		ModelAndView mv = new ModelAndView(GEO_FENCE_HOME_VIEW);
		
	
		return mv;
	}
	
	@RequestMapping(value = GEOFENCE_CANCEL,method = RequestMethod.GET)
	public ModelAndView cancelPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:" + GEO_FENCE_LIST_VIEW);
		return mv;
	}

}
