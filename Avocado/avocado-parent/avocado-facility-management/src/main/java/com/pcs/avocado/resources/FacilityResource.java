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
package com.pcs.avocado.resources;

import static com.pcs.avocado.constans.ApiDocConstant.CREATE_FACILITY_DESC;
import static com.pcs.avocado.constans.ApiDocConstant.CREATE_FACILITY_SUCCESS_RESP;
import static com.pcs.avocado.constans.ApiDocConstant.CREATE_FACILITY_SUMMARY;
import static com.pcs.avocado.constans.ApiDocConstant.DOMAIN_NAME;
import static com.pcs.avocado.constans.ApiDocConstant.FACILITY_NAME;
import static com.pcs.avocado.constans.ApiDocConstant.FACILITY_PAYLOAD;
import static com.pcs.avocado.constans.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.avocado.constans.ApiDocConstant.GENERAL_FIELD_ALREADY_EXISTING;
import static com.pcs.avocado.constans.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.avocado.constans.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.avocado.constans.ApiDocConstant.GET_ALL_FACILITY_DESC;
import static com.pcs.avocado.constans.ApiDocConstant.GET_ALL_FACILITY_SUMMARY;
import static com.pcs.avocado.constans.ApiDocConstant.GET_FACILITY_DESC;
import static com.pcs.avocado.constans.ApiDocConstant.GET_FACILITY_SUCCESS_RESP;
import static com.pcs.avocado.constans.ApiDocConstant.GET_FACILITY_SUMMARY;
import static com.pcs.avocado.constans.ApiDocConstant.TEMPLATE_NAME;
import static com.pcs.avocado.constans.ApiDocConstant.UPDATE_FACILITY_DESC;
import static com.pcs.avocado.constans.ApiDocConstant.UPDATE_FACILITY_SUCCESS_RESP;
import static com.pcs.avocado.constans.ApiDocConstant.UPDATE_FACILITY_SUMMARY;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.services.FacilityService;

/**
 * @description Resource for Facility services
 * @author Twinkle (PCSEG297)
 * @date January 2016
 * @since Avocado-1.0.0
 */
@Path("/facilities")
@Component
@Api("Facility")
public class FacilityResource {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(FacilityResource.class);

	@Autowired
	private FacilityService facilityService;

	/**
	 * Responsible to create a facility
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_FACILITY_DESC,value = CREATE_FACILITY_SUMMARY,
	        response = IdentityDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = CREATE_FACILITY_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_ALREADY_EXISTING)})
	public Response createFacility(@ApiParam(value = FACILITY_PAYLOAD,
	        required = true) EntityDTO facilityEntity) {
		LOGGER.debug("<<-- Enter createFacility() -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(facilityService.createFacility(facilityEntity));
		LOGGER.debug("<<-- Exit createFacility()-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch a facility
	 */
	@GET
	@Path("/{facility_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_FACILITY_DESC,value = GET_FACILITY_SUMMARY,
	        response = IdentityDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = GET_FACILITY_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 522,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getFacility(
			@ApiParam(value = FACILITY_NAME, required = true) @PathParam("facility_name") String facilityName,
			@ApiParam(value = TEMPLATE_NAME, required = true) @QueryParam("template_name") String templateName,
			@ApiParam(value = DOMAIN_NAME, required = false) @QueryParam("domain_name") String domainName) {
		LOGGER.debug("<<-- Enter createFacility() -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(facilityService.getFacility(templateName,
		        facilityName, domainName));
		LOGGER.debug("<<-- Exit createFacility()-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch all facilities
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_ALL_FACILITY_DESC,
	        value = GET_ALL_FACILITY_SUMMARY,response = IdentityDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = GET_ALL_FACILITY_DESC),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 522,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getAllFacilities(
			@ApiParam(value = TEMPLATE_NAME, required = true) @QueryParam("template_name") String templateName,
			@ApiParam(value = DOMAIN_NAME, required = false) @QueryParam("domain_name") String domainName) {
		LOGGER.debug("<<-- Enter createFacility() -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(facilityService.getAllFacility(templateName,
		        domainName));
		LOGGER.debug("<<-- Exit createFacility()-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to update a facility
	 */
	@PUT
	@Path("/{facility_name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_FACILITY_DESC,value = UPDATE_FACILITY_SUMMARY,
	        response = IdentityDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = UPDATE_FACILITY_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response updateFacility(
			@ApiParam(value = FACILITY_NAME, required = true) @PathParam("facility_name") String facilityName,
			@ApiParam(value = DOMAIN_NAME, required = false) @QueryParam("domain_name") String domainName,
			@ApiParam(value = FACILITY_PAYLOAD, required = true) EntityDTO facilityEntity) {
		LOGGER.debug("<<-- Enter createFacility() -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(facilityService.updateFacility(facilityName,
		        domainName, facilityEntity));
		LOGGER.debug("<<-- Exit createFacility()-->>");
		return responseBuilder.build();
	}
}