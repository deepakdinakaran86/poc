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
package com.pcs.fms.web.services;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.AttachTags;
import com.pcs.fms.web.dto.RoutingDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.RouteModelDTO;

/**
 * @author PCSEG296 RIYAS PH,PCSEG336 Sekh
 * @date JULY 2016
 * @since FMS-1.0.0
 * 
 */
@Service
public class RouteService extends BaseService {

	@Value("${fms.services.route.list}")
	private String getRoutesUri;
	
	@Value("${fms.services.createRoute}")
	private String routeEndpointUri;
	
	@Value("${fms.services.viewRoute}")
	private String routeViewEndpointUri;
	
	/**
	 * Method to view device details
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<List<RoutingDTO>> getAllRoutes(String domainName) {

		String url = getRoutesUri + "?domain_name=" + domainName;

		Type listRoutes = new TypeToken<List<RoutingDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		String listGeofenceUri = getServiceURI(url);
		return getPlatformClient().getResource(listGeofenceUri, listRoutes);
	}

	public FMSResponse<StatusMessageDTO> createRoute(RouteModelDTO route,
			String domainName) {
		String encodedStart=null;
		String encodedEnd=null;
		Type routeDTO = new TypeToken<RoutingDTO>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		
		Gson gson = new Gson();
		RoutingDTO routing =gson.fromJson(route.getRouteDetails(), routeDTO);
		try {
			 encodedStart = URLEncoder.encode(routing.getStartAddress(), "UTF-8"); // since address have special char at times
			 encodedEnd = URLEncoder.encode(routing.getStartAddress(), "UTF-8");
			 routing.setStartAddress(encodedStart);
			 routing.setEndAddress(encodedEnd);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		routing.setStatus("ACTIVE");
		routing.setDomain(domainName);
		routing.setRouteName(route.getRouteName());
		routing.setRouteDescription(route.getDescription());
		
		
		String routeUri = getServiceURI(routeEndpointUri);
		return getPlatformClient().postResource(routeUri,routing, StatusMessageDTO.class);
	}

	public FMSResponse<RoutingDTO> viewRoute(String route_name,
			String domainName) {
		String routeViewUri = getServiceURI(routeViewEndpointUri);
		routeViewUri=routeViewUri.replace("{route_name}", route_name);
		routeViewUri=routeViewUri.replace("{domain}", domainName);
		return getPlatformClient().getResource(routeViewUri, RoutingDTO.class);
	}
}
