package com.pcs.guava.resources;

import static com.pcs.guava.constants.ApiDocConstant.DOMAIN;
import static com.pcs.guava.constants.ApiDocConstant.DOMAIN_SAMPLE;
import static com.pcs.guava.constants.ApiDocConstant.CHECK_ROUTE_EXIST_DESC;
import static com.pcs.guava.constants.ApiDocConstant.CHECK_ROUTE_EXIST_RESP;
import static com.pcs.guava.constants.ApiDocConstant.CHECK_ROUTE_EXIST_SUMMARY;
import static com.pcs.guava.constants.ApiDocConstant.CREATE_ROUTE_DESC;
import static com.pcs.guava.constants.ApiDocConstant.CREATE_ROUTE_SUMMARY;
import static com.pcs.guava.constants.ApiDocConstant.DELETE_ROUTE_DESC;
import static com.pcs.guava.constants.ApiDocConstant.DELETE_ROUTE_SUMMARY;
import static com.pcs.guava.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.guava.constants.ApiDocConstant.GENERAL_SUCCESS_RESP;
import static com.pcs.guava.constants.ApiDocConstant.LIST_ROUTE_DESC;
import static com.pcs.guava.constants.ApiDocConstant.LIST_ROUTE_SUCC_RESP;
import static com.pcs.guava.constants.ApiDocConstant.LIST_ROUTE_SUMMARY;
import static com.pcs.guava.constants.ApiDocConstant.ROUTE_NAME;
import static com.pcs.guava.constants.ApiDocConstant.UPDATE_ROUTE_DESC;
import static com.pcs.guava.constants.ApiDocConstant.UPDATE_ROUTE_SUMMARY;
import static com.pcs.guava.constants.ApiDocConstant.VIEW_ROUTE_DESC;
import static com.pcs.guava.constants.ApiDocConstant.VIEW_ROUTE_SUCC_RESP;
import static com.pcs.guava.constants.ApiDocConstant.VIEW_ROUTE_SUMMARY;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.dto.routing.RoutingDTO;
import com.pcs.guava.service.RoutingService;

/**
 * @description Rest Resource used to manage all the services related to Route
 * 
 * @author Sekh (pcseg336)
 * @date 1 Jun 2016
 */

@Path("/route")
@Component
@Api("Route")
public class RoutingResource {

	@Autowired
	private RoutingService routingService;

	/**
	 * @description This is a default method to get the header options,all
	 *              resource layers should have this method
	 * 
	 */

	@OPTIONS
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "", hidden = true)
	public Response defaultOptions() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.header("ALLOW", "HEAD,GET,PUT,DELETE,OPTIONS");
		return responseBuilder.build();
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_ROUTE_DESC, value = CREATE_ROUTE_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED) })
	public Response create(RoutingDTO route) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(routingService.createRoute(route, false));
		return responseBuilder.build();
	}

	@GET
	@Path("/{route_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CHECK_ROUTE_EXIST_DESC, value = CHECK_ROUTE_EXIST_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 757, message = CHECK_ROUTE_EXIST_RESP) })
	public Response routeExist(
			@ApiParam(value = ROUTE_NAME, required = true) @PathParam(value = "route_name") String routeName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Boolean routeExistCheck = true;
		responseBuilder.entity(routingService.isRouteExist(routeName,
				routeExistCheck));
		return responseBuilder.build();
	}

	@DELETE
	@Path("/{route_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_ROUTE_DESC, value = DELETE_ROUTE_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED) })
	public Response delete(
			@ApiParam(value = DOMAIN,required = true, defaultValue = DOMAIN_SAMPLE,example = DOMAIN_SAMPLE)@QueryParam("domain_name") String domainName,
			@ApiParam(value = ROUTE_NAME, required = true) @PathParam(value = "route_name") String routeName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Boolean routeExistCheck = false;
		responseBuilder.entity(routingService.deleteRoute(routeName,routeExistCheck,domainName));
		return responseBuilder.build();
	}

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = LIST_ROUTE_DESC, value = LIST_ROUTE_SUMMARY, response = RoutingDTO.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = LIST_ROUTE_SUCC_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED) })
	public Response list(@ApiParam(value = DOMAIN,required = true,
            defaultValue = DOMAIN_SAMPLE,example = DOMAIN_SAMPLE)@QueryParam("domain_name") String domainName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(routingService.listRoute(domainName));
		return responseBuilder.build();
	}

	@GET
	@Path("/view/{route_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = VIEW_ROUTE_DESC, value = VIEW_ROUTE_SUMMARY, response = RoutingDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = VIEW_ROUTE_SUCC_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED) })
	public Response view(@ApiParam(value = DOMAIN,required = true, defaultValue = DOMAIN_SAMPLE,example = DOMAIN_SAMPLE)@QueryParam("domain_name") String domainName,
			@ApiParam(value = ROUTE_NAME, required = true) @PathParam(value = "route_name") String routeName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		RoutingDTO route = new RoutingDTO();
		route = routingService.viewRoute(routeName,domainName);
		responseBuilder.entity(route);
		return responseBuilder.build();
	}

	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_ROUTE_DESC, value = UPDATE_ROUTE_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED) })
	public Response update(RoutingDTO route) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(routingService.updateRoute(route));
		return responseBuilder.build();
	}

}
