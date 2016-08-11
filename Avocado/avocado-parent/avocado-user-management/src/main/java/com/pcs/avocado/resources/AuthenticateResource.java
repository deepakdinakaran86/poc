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

import static com.pcs.avocado.constants.ApiDocConstant.AUTHENTICATE_NOTES;
import static com.pcs.avocado.constants.ApiDocConstant.AUTHENTICATE_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.AUTHENTICATE_VALUE;
import static com.pcs.avocado.constants.ApiDocConstant.LOGIN_PAYLOAD;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.dto.UserCredentialsDTO;
import com.pcs.avocado.service.AuthenticateService;
import com.pcs.avocado.token.TokenInfoDTO;

/**
 * AuthenticateResource
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 13 January 2016
 * @since avocado-1.0.0
 */
@Path("/authenticate")
@Component
@Api("Login")
public class AuthenticateResource {

	@Autowired
	private AuthenticateService authenticateService;

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
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = AUTHENTICATE_NOTES,value = AUTHENTICATE_VALUE)
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK,
		        message = AUTHENTICATE_SUCCESS_RESP)})
	public Response authenticate(@ApiParam(name = "login",required = true,
	        value = LOGIN_PAYLOAD) UserCredentialsDTO userCredentialsDTO,
	        @Context HttpServletRequest request) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		TokenInfoDTO authenticate = authenticateService.authenticate(
		        userCredentialsDTO, request);
		responseBuilder.entity(authenticate);
		return responseBuilder.build();
	}

	@DELETE
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response refresh(UserCredentialsDTO userCredentialsDTO,
	        @Context HttpServletRequest request) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO status = authenticateService.logout(
		        userCredentialsDTO, request);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}

}
