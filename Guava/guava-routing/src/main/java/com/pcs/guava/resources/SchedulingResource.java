package com.pcs.guava.resources;

import static org.apache.http.HttpStatus.SC_OK;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.dto.scheduling.SchedulingDTO;
import com.pcs.guava.service.SchedulingService;

import static com.pcs.guava.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.guava.constants.ApiDocConstant.GENERAL_SUCCESS_RESP;
import static com.pcs.guava.constants.ApiDocConstant.CREATE_SCHEDULE_DESC;
import static com.pcs.guava.constants.ApiDocConstant.CREATE_SCHEDULE_SUMMARY;
import static com.pcs.guava.constants.ApiDocConstant.VIEW_SCHEDULE_DESC;
import static com.pcs.guava.constants.ApiDocConstant.VIEW_SCHEDULE_SUMMARY;
import static com.pcs.guava.constants.ApiDocConstant.VIEW_SCHEDULE_SUCC_RESP;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/schedule")
@Component
@Api("RouteScheduling")
/**
 * @description Rest Resource used to manage all the services related to Route-Scheduling
 * 
 * @author Sekh (pcseg336)
 * @date 21 Jun 2016
 */
public class SchedulingResource {

	@Autowired
	private SchedulingService schedulingService;

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
	@ApiOperation(notes = CREATE_SCHEDULE_DESC, value = CREATE_SCHEDULE_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED) })
	public Response create(SchedulingDTO schedule) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(schedulingService.createSchedule(schedule));
		return responseBuilder.build();
	}

	@GET
	@Path("/view/{schedule_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = VIEW_SCHEDULE_DESC, value = VIEW_SCHEDULE_SUMMARY, response = SchedulingDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = VIEW_SCHEDULE_SUCC_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED) })
	public Response create(
			@PathParam(value = "schedule_name") String scheduleName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(schedulingService.viewSchedule(scheduleName));
		return responseBuilder.build();
	}

}
