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

import static com.pcs.avocado.constants.ApiDocConstant.CHANGE_PASSWORD_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.CHANGE_PASSWORD_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.CHANGE_PASSWORD_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.CREATE_USER_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.CREATE_USER_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.CREATE_USER_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.DELETE_USER_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.DELETE_USER_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.DELETE_USER_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.FIELD_DATA_NOT_AVAILABLE;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_ALL_USER_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_ALL_USER_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_ALL_USER_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_USER_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_USER_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_USER_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.FORGOT_PASSWORD_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.FORGOT_PASSWORD_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.FORGOT_PASSWORD_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.avocado.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.avocado.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.avocado.constants.ApiDocConstant.NO_ACCESS;
import static com.pcs.avocado.constants.ApiDocConstant.RESET_PASSWORD_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.RESET_PASSWORD_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.RESET_PASSWORD_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.UPDATE_USER_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.UPDATE_USER_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.UPDATE_USER_VALUE;
import static com.pcs.avocado.constants.ApiDocConstant.USER_PAYLOAD;
import static com.pcs.avocado.constants.ApiDocConstant.USER_SEARCH_PAYLOAD;
import static com.pcs.avocado.constants.ApiDocConstant.SMS_STATISTICS_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.SMS_STATISTICS_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.SMS_STATISTICS_SUMMARY;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.constants.ApiDocConstant;
import com.pcs.avocado.dto.UserDTO;
import com.pcs.avocado.service.UserService;

/**
 * UserAdapterResource manages User related services.
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 18 January 2016
 * @since avocado-1.0.0
 */
@Path("/users")
@Component
@Api("Users")
public class UserResource {

	@Autowired
	private UserService userService;

	/**
	 * @description This is a default method to get the header options,all
	 *              resource layers should have this method
	 * 
	 */
	@OPTIONS
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "", hidden = true)
	public Response defaultOptions() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.header("ALLOW", "HEAD,GET,PUT,DELETE,OPTIONS");
		return responseBuilder.build();
	}

	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_USER_DESC, value = CREATE_USER_SUMMARY, response = EntityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = CREATE_USER_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 600, message = NO_ACCESS),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID) })
	public Response saveUser(
			@ApiParam(required = true, value = USER_PAYLOAD) UserDTO userDTO) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		EntityDTO createUser = userService.createUser(userDTO);
		responseBuilder.entity(createUser);
		return responseBuilder.build();
	}

	@PUT
	@Path("/{user_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_USER_DESC, value = UPDATE_USER_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = UPDATE_USER_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 600, message = NO_ACCESS),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID) })
	public Response updateUser(
			@PathParam("user_name") String userName,
			@QueryParam("domain_name") String domain,
			@ApiParam(required = true, value = USER_PAYLOAD) UserDTO userDTO) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		EntityDTO updateUser = userService.updateUser(userName, domain, userDTO);
		responseBuilder.entity(updateUser);
		return responseBuilder.build();
	}

	@DELETE
	@Path("/{user_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_USER_DESC, value = DELETE_USER_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = DELETE_USER_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID) })
	public Response deleteUser(@PathParam("user_name") String userName,
			@QueryParam("domain_name") String domain) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO deleteUser = userService.deleteUser(userName, domain);
		responseBuilder.entity(deleteUser);
		return responseBuilder.build();
	}

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_USER_DESC, value = FIND_ALL_USER_SUMMARY, response = EntityDTO.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = FIND_ALL_USER_SUCCESS_RESP),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 510, message = FIELD_DATA_NOT_AVAILABLE) })
	public Response getUsers(@QueryParam("domain_name") String domainName) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<EntityDTO> users = userService.getUsers(domainName);
		GenericEntity<List<EntityDTO>> entity = new GenericEntity<List<EntityDTO>>(
				users) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@POST
	@Path("/reset_password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = RESET_PASSWORD_DESC, value = RESET_PASSWORD_SUMMARY, response = UserDTO.class)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = RESET_PASSWORD_SUCCESS_RESP) })
	public Response resetPassword(
			@ApiParam(required = true, value = USER_SEARCH_PAYLOAD) UserDTO userDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessage = userService.resetPassword(userDTO);
		responseBuilder.entity(statusMessage);
		return responseBuilder.build();
	}

	@GET
	@Path("/{user_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_USER_DESC, value = FIND_USER_SUMMARY, response = EntityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = FIND_USER_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response getUser(@PathParam("user_name") String userName,
			@QueryParam("domain_name") String domain) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(userService.getUser(userName, domain));
		return responseBuilder.build();
	}
	
	@POST
	@Path("/forgot_password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FORGOT_PASSWORD_DESC, value = FORGOT_PASSWORD_SUMMARY, response = UserDTO.class)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = FORGOT_PASSWORD_SUCCESS_RESP) })
	public Response forgotPassword(
			@ApiParam(required = true, value = USER_SEARCH_PAYLOAD) UserDTO userDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessage = userService.forgotPassword(userDTO);
		responseBuilder.entity(statusMessage);
		return responseBuilder.build();
	}
	
	@POST
	@Path("/change_password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CHANGE_PASSWORD_DESC, value = CHANGE_PASSWORD_SUMMARY, response = UserDTO.class)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = CHANGE_PASSWORD_SUCCESS_RESP) })
	public Response changePassword(
			@ApiParam(required = true, value = USER_SEARCH_PAYLOAD) UserDTO userDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessage = userService.changePassword(userDTO);
		responseBuilder.entity(statusMessage);
		return responseBuilder.build();
	}
	
	@GET
	@Path("/SMS-statistics")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = SMS_STATISTICS_DESC, value = SMS_STATISTICS_SUMMARY, response = EntityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = SMS_STATISTICS_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response getSMStatistics() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(userService.getSMStatistics());
		return responseBuilder.build();
	}
}
