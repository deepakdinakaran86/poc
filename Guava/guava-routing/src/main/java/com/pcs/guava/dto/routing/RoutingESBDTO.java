package com.pcs.guava.dto.routing;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.pcs.guava.commons.dto.EntityDTO;


/**
 * RoutingESBDTO
 *
 * @description DTO for routing esb
 * @author Sekh (PCSEG336)
 * @date 2 Jun 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class RoutingESBDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private EntityDTO routeEntity;
	
	private List<RoutePoiDTO> wayPointsEntity;

	public EntityDTO getRouteEntity() {
		return routeEntity;
	}

	public void setRouteEntity(EntityDTO routeEntity) {
		this.routeEntity = routeEntity;
	}

	public List<RoutePoiDTO> getWayPointsEntity() {
		return wayPointsEntity;
	}

	public void setWayPointsEntity(List<RoutePoiDTO> wayPointsEntity) {
		this.wayPointsEntity = wayPointsEntity;
	}


	
	
	
	

}
