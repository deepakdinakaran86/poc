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
package com.pcs.galaxy.resources;

import static com.pcs.galaxy.constants.ApiDocConstant.ALARM_CONF_PAYLOAD;
import static com.pcs.galaxy.constants.ApiDocConstant.CREATE_ALARM_CONF_DESC;
import static com.pcs.galaxy.constants.ApiDocConstant.CREATE_ALARM_CONF_SUCCESS_RESP;
import static com.pcs.galaxy.constants.ApiDocConstant.CREATE_ALARM_CONF_SUMMARY;
import static com.pcs.galaxy.constants.ApiDocConstant.DELETE_ALARM_CONF_DESC;
import static com.pcs.galaxy.constants.ApiDocConstant.DELETE_ALARM_CONF_SUCCESS_RESP;
import static com.pcs.galaxy.constants.ApiDocConstant.DELETE_ALARM_CONF_SUMMARY;
import static com.pcs.galaxy.constants.ApiDocConstant.FIND_ALARM_CONF_DESC;
import static com.pcs.galaxy.constants.ApiDocConstant.FIND_ALARM_CONF_SUCCESS_RESP;
import static com.pcs.galaxy.constants.ApiDocConstant.FIND_ALARM_CONF_SUMMARY;
import static com.pcs.galaxy.constants.ApiDocConstant.FIND_ALL_ALARM_CONF_DESC;
import static com.pcs.galaxy.constants.ApiDocConstant.FIND_ALL_ALARM_CONF_SUCCESS_RESP;
import static com.pcs.galaxy.constants.ApiDocConstant.FIND_ALL_ALARM_CONF_SUMMARY;
import static com.pcs.galaxy.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.galaxy.constants.ApiDocConstant.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.galaxy.constants.ApiDocConstant.UPDATE_ALARM_CONF_DESC;
import static com.pcs.galaxy.constants.ApiDocConstant.UPDATE_ALARM_CONF_SUCCESS_RESP;
import static com.pcs.galaxy.constants.ApiDocConstant.UPDATE_ALARM_CONF_SUMMARY;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.galaxy.dto.DeviceThresholdDTO;
import com.pcs.galaxy.services.DeviceThresholdService;

/**
 * DeviceThresholdResource
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Path("/threshold")
@Component
@Api("threshold")
public final class DeviceThresholdResource {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DeviceThresholdResource.class);

	@Autowired
	DeviceThresholdService deviceThresholdService;

	/**
	 * @description This is a default method to get the header options,all
	 *              resource layers should have this method
	 * 
	 */
	@OPTIONS
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "",hidden = true)
	public Response defaultOptions() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.header("ALLOW", "HEAD,GET,PUT,DELETE,OPTIONS");
		return responseBuilder.build();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_ALARM_CONF_DESC,
	        value = CREATE_ALARM_CONF_SUMMARY,
	        response = DeviceThresholdDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = CREATE_ALARM_CONF_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 503,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response saveThreshold(@ApiParam(required = true,
	        value = ALARM_CONF_PAYLOAD) DeviceThresholdDTO threshold) {
		LOGGER.info("Enter into saveThreshold");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(deviceThresholdService.saveThreshold(threshold));
		return responseBuilder.build();
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_ALARM_CONF_DESC,
	        value = UPDATE_ALARM_CONF_SUMMARY,response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = UPDATE_ALARM_CONF_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 503,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response updateThreshold(DeviceThresholdDTO threshold) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder
		        .entity(deviceThresholdService.updateThreshold(threshold));
		return responseBuilder.build();
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_ALARM_CONF_DESC,
	        value = DELETE_ALARM_CONF_SUMMARY,response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = DELETE_ALARM_CONF_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 503,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response deleteThreshold(DeviceThresholdDTO threshold) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(deviceThresholdService
		        .deleteThreshold(threshold));
		return responseBuilder.build();
	}

	@GET
	@Path("/list/{assetName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_ALARM_CONF_DESC,
	        value = FIND_ALL_ALARM_CONF_SUMMARY,
	        response = DeviceThresholdDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = FIND_ALL_ALARM_CONF_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 503,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getThresholdForDevice(
	        @PathParam("assetName") String assetName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		DeviceThresholdDTO threshold = new DeviceThresholdDTO();
		threshold.setAssetName(assetName);
		List<DeviceThresholdDTO> thresholds = deviceThresholdService
		        .getThresholdForAsset(threshold);
		GenericEntity<List<DeviceThresholdDTO>> thresholdGE = new GenericEntity<List<DeviceThresholdDTO>>(
		        thresholds) {
		};

		responseBuilder.entity(thresholdGE);
		return responseBuilder.build();
	}

	@POST
	@Path("/find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALARM_CONF_DESC,value = FIND_ALARM_CONF_SUMMARY,
	        response = DeviceThresholdDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = FIND_ALARM_CONF_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 503,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getThreshold(DeviceThresholdDTO threshold) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(deviceThresholdService.getThreshold(threshold));
		return responseBuilder.build();
	}

}
