package com.pcs.guava.dto.routing;

import java.util.List;

import com.pcs.guava.commons.dto.IdentityDTO;

public class RoutingIdentityESBDTO {
	
	private IdentityDTO route;
	
	private List<IdentityDTO> poi;

	public IdentityDTO getRoute() {
		return route;
	}

	public void setRoute(IdentityDTO route) {
		this.route = route;
	}

	public List<IdentityDTO> getPoi() {
		return poi;
	}

	public void setPoi(List<IdentityDTO> poi) {
		this.poi = poi;
	}
	
	

}
