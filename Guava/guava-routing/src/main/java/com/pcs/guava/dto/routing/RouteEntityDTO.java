package com.pcs.guava.dto.routing;

import java.util.List;

import com.pcs.guava.commons.dto.EntityDTO;

public class RouteEntityDTO {
	
	
	private EntityDTO route;
	private List<EntityDTO> poi;
	public EntityDTO getRoute() {
		return route;
	}
	public void setRoute(EntityDTO route) {
		this.route = route;
	}
	public List<EntityDTO> getPoi() {
		return poi;
	}
	public void setPoi(List<EntityDTO> poi) {
		this.poi = poi;
	}

	
}
