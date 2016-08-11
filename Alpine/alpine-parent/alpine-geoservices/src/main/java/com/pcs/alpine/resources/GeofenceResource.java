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
package com.pcs.alpine.resources;

import static com.pcs.alpine.constants.ApiDocConstant.CREATE_GEOFENCE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_GEOFENCE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_GEOFENCE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_GEOFENCE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_GEOFENCE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_GEOFENCE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_GEOFENCE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_GEOFENCE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_GEOFENCE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_GEOFENCE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_GEOFENCE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.alpine.constants.ApiDocConstant.GEOFENCE_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.PERSISTENCE_ERROR;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_GEOFENCE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_GEOFENCE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_GEOFENCE_SUMMARY;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.dto.GeofenceDTO;
import com.pcs.alpine.service.GeofenceService;
import com.pcs.alpine.services.dto.IdentityDTO;

/**
 * @description Resource for geo services
 * @author Daniela (PCSEG191)
 * @date 10 Mar 2016
 * @since galaxy-1.0.0
 */
@Path("/geofence")
@Component
@Api("Geofence")
public class GeofenceResource {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(GeofenceResource.class);

	@Autowired
	private GeofenceService geoService;

	/**
	 * @description This is a default method to get the header options,all
	 *              resource layers should have this method
	 * 
	 */

	@OPTIONS
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "",hidden = true)
	public Response defaultOptions() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.header("ALLOW", "HEAD,GET,PUT,DELETE,OPTIONS");
		return responseBuilder.build();
	}

	/**
	 * Responsible to create a geofence
	 *
	 * @param geofenceDTO
	 * @return StatusMessageDTO
	 */
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_GEOFENCE_DESC,value = CREATE_GEOFENCE_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = CREATE_GEOFENCE_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 503,message = PERSISTENCE_ERROR),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createGeofence(@ApiParam(required = true,
	        value = GEOFENCE_PAYLOAD) GeofenceDTO geofenceDTO) {
		LOGGER.debug("<<-- Enter createGeofence(GeofenceDTO geofenceDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geoService.createGeofence(geofenceDTO));
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch a geofence
	 *
	 * @param identity
	 * @return Fetched geofence
	 */
	@POST
	@Path("find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_GEOFENCE_DESC,value = FIND_GEOFENCE_SUMMARY,
	        response = GeofenceDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = FIND_GEOFENCE_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response findGeofence(
	        @ApiParam(name = "geofenceIdentityFields",required = true,
	                value = "Geofence Identify Fields") IdentityDTO identity) {
		LOGGER.debug("<<-- Enter findGeofence(IdentityDTO identity) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geoService.findGeofence(identity));
		return responseBuilder.build();
	}

	/**
	 * Responsible to find the list of geofence's for a domain
	 *
	 * @param identityDto
	 * @return List of EntityDTO
	 */
	@GET
	@Path("list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_GEOFENCE_DESC,
	        value = FIND_ALL_GEOFENCE_SUMMARY,response = GeofenceDTO.class,
	        responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = FIND_ALL_GEOFENCE_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response findAllGeofence(@QueryParam("domain_name") String domainName) {
		LOGGER.debug("<<-- Enter findAllGeofences(String domainName) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geoService.findAllGeofences(domainName));
		return responseBuilder.build();
	}

	/**
	 * Responsible to update a geofence
	 *
	 * @param geofence
	 * @return StatusMessageDTO
	 */
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_GEOFENCE_DESC,value = UPDATE_GEOFENCE_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = UPDATE_GEOFENCE_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 503,message = PERSISTENCE_ERROR),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response updateGeofence(@ApiParam(required = true,
	        value = GEOFENCE_PAYLOAD) GeofenceDTO geofence) {
		LOGGER.debug("<<-- Enter updateGeofence(GeofenceDTO geofence) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geoService.updateGeofence(geofence));
		return responseBuilder.build();
	}

	/**
	 * Responsible to delete a geofence
	 *
	 * @param geofence
	 * @return Success message
	 */
	@POST
	@Path("delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_GEOFENCE_DESC,value = DELETE_GEOFENCE_SUMMARY,
	        response = StatusMessageDTO.class)
	public Response deleteGeofence(@ApiParam(required = true,
	        value = GEOFENCE_PAYLOAD) IdentityDTO geofence) {
		LOGGER.debug("<<-- Enter deleteGeofence(IdentityDTO geofence) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geoService.deleteGeofence(geofence));
		return responseBuilder.build();
	}
}