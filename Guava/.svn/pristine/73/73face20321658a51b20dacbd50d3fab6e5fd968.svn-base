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
package com.pcs.guava.driver.resources;

import static com.pcs.guava.driver.constans.DriverResourceConstants.DRIVER_ENTITY_IDENTITY_FIELDS;
import static com.pcs.guava.driver.constans.DriverResourceConstants.DRIVER_PAYLOAD;
import static com.pcs.guava.driver.constans.DriverResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.guava.driver.constans.DriverResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.guava.driver.constans.DriverResourceConstants.GET_ALL_DRIVER_DESC;
import static com.pcs.guava.driver.constans.DriverResourceConstants.GET_ALL_DRIVER_RESP;
import static com.pcs.guava.driver.constans.DriverResourceConstants.GET_ALL_DRIVER_SUMMARY;
import static com.pcs.guava.driver.constans.DriverResourceConstants.GET_DRIVER_DESC;
import static com.pcs.guava.driver.constans.DriverResourceConstants.GET_DRIVER_RESP;
import static com.pcs.guava.driver.constans.DriverResourceConstants.GET_DRIVER_SUMMARY;
import static com.pcs.guava.driver.constans.DriverResourceConstants.INSERT_DRIVER_DESC;
import static com.pcs.guava.driver.constans.DriverResourceConstants.INSERT_DRIVER_RESP;
import static com.pcs.guava.driver.constans.DriverResourceConstants.INSERT_DRIVER_SUMMARY;
import static com.pcs.guava.driver.constans.DriverResourceConstants.UPDATE_DRIVER_DESC;
import static com.pcs.guava.driver.constans.DriverResourceConstants.UPDATE_DRIVER_SUMMARY;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;
import static org.apache.http.HttpStatus.SC_OK;
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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.guava.commons.dto.IdentityDTO;
import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.constant.CommonConstants;
import com.pcs.guava.driver.dto.Driver;
import com.pcs.guava.driver.services.DriverService;

/**
 * Driver Resource Methods
 * 
 * @author Twinkle (PCSEG297)
 * @date April 2016
 */

@Path("/drivers")
@Component
@Api("Drivers")
public class DriverResource {

	@Autowired
	private DriverService driverService;

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = INSERT_DRIVER_SUMMARY,notes = INSERT_DRIVER_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = INSERT_DRIVER_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response createDriver(@ApiParam(value = DRIVER_PAYLOAD,
	        required = true) Driver driver) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(driverService.createDriver(driver));
		return responseBuilder.build();
	}

	@POST
	@Path(value = "/find")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DRIVER_SUMMARY,notes = GET_DRIVER_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_DRIVER_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getDriver(@ApiParam(value = DRIVER_ENTITY_IDENTITY_FIELDS,
	        required = true) IdentityDTO driverIdentity) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(driverService.getDriver(driverIdentity));
		return responseBuilder.build();
	}

	@GET
	@Path(value = "/findAll")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_DRIVER_SUMMARY,notes = GET_ALL_DRIVER_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_ALL_DRIVER_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response findAll(
	        @ApiParam(value = CommonConstants.DOMAIN_NAME,required = true) @QueryParam(
	                value = "domain_name") String domainName) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(driverService.findAll(domainName));
		return responseBuilder.build();
	}

	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DRIVER_SUMMARY,notes = UPDATE_DRIVER_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response updateDriver(@ApiParam(value = DRIVER_PAYLOAD,
	        required = true) Driver driver) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(driverService.updateDriver(driver));
		return responseBuilder.build();
	}

}
