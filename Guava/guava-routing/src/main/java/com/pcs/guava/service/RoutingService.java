package com.pcs.guava.service;

import java.util.List;

import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.dto.routing.RoutingDTO;

public interface RoutingService {

	public RoutingDTO viewRoute(String route, String domainName);

	public StatusMessageDTO createRoute(RoutingDTO route,boolean update);
	
	public StatusMessageDTO isRouteExist(String route , boolean check);

	public List<RoutingDTO> listRoute(String domainName);

	public StatusMessageDTO deleteRoute(String routeName, Boolean routeExistCheck,String domain);

	public StatusMessageDTO updateRoute(RoutingDTO route);

}