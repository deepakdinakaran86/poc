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

import static com.pcs.alpine.constants.ApiDocConstant.CREATE_ADMIN_USER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_ADMIN_USER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_ADMIN_USER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_USER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_USER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_USER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_USER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_USER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_USER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_USER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_USER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_USER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_USER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_USER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_USER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_USER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_USER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_USER_VALUE;
import static com.pcs.alpine.constants.ApiDocConstant.VALIDATE_USER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.VALIDATE_USER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.VALIDATE_USER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.USER_IDENTIY_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.FIELD_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.USER_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.NO_ACCESS;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.alpine.constants.ApiDocConstant.USER_SEARCH_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.GET_USER_COUNT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_USER_COUNT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_USER_COUNT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.RESET_PASSWORD_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.RESET_PASSWORD_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.RESET_PASSWORD_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_USER_STATUS_COUNT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_USER_STATUS_COUNT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_USER_STATUS_COUNT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FORGOT_PASSWORD_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FORGOT_PASSWORD_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FORGOT_PASSWORD_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CHANGE_PASSWORD_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CHANGE_PASSWORD_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.CHANGE_PASSWORD_SUCCESS_RESP;
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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.dto.UserDTO;
import com.pcs.alpine.service.UserService;
import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityStatusCountDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;

/**
 * UserAdapterResource manages User related services.
 * 
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since alpine-1.0.0
 */
@Path("/users")
@Component
@Api("Users")
public class UserResource {

	@Autowired
	UserService userService;

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
	@Path("find")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_USER_DESC, value = FIND_USER_SUMMARY, response = EntityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = FIND_USER_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response getUser(
			@ApiParam(required = true, value = USER_IDENTIY_PAYLOAD) IdentityDTO userIdentifier) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(userService.getUser(userIdentifier));
		return responseBuilder.build();
	}

	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_USER_DESC, value = FIND_ALL_USER_SUMMARY, response = EntityDTO.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = FIND_ALL_USER_SUCCESS_RESP),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 510, message = FIELD_DATA_NOT_AVAILABLE) })
	public Response getUsers(@QueryParam("domainName") String domainName,
			@QueryParam("isParentDomain") Boolean isParentDomain) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<EntityDTO> users = userService
				.getUsers(domainName, isParentDomain);
		GenericEntity<List<EntityDTO>> entity = new GenericEntity<List<EntityDTO>>(
				users) {
		};
		responseBuilder.entity(entity);
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
			@ApiParam(required = true, value = USER_PAYLOAD) EntityDTO user) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(userService.saveUser(user));
		return responseBuilder.build();
	}

	@PUT
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_USER_DESC, value = UPDATE_USER_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = UPDATE_USER_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 600, message = NO_ACCESS),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID) })
	public Response updateUser(
			@ApiParam(required = true, value = USER_PAYLOAD) EntityDTO user) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(userService.updateUser(user));
		return responseBuilder.build();
	}

	@POST
	@Path("delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_USER_DESC, value = DELETE_USER_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = DELETE_USER_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID) })
	public Response deleteUser(
			@ApiParam(required = true, value = USER_IDENTIY_PAYLOAD) IdentityDTO userIdentifier) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(userService.deleteUser(userIdentifier));
		return responseBuilder.build();
	}

	@POST
	@Path("admin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_ADMIN_USER_DESC, value = CREATE_ADMIN_USER_SUMMARY, response = EntityDTO.class)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = CREATE_ADMIN_USER_SUCCESS_RESP) })
	public Response saveAdminUser(
			@ApiParam(required = true, value = USER_PAYLOAD) EntityDTO user) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(userService.saveAdminUser(user));
		return responseBuilder.build();
	}

	/**
	 * Responsible to validate a user's fields
	 * 
	 * 
	 * @param entitySearchDto
	 * 
	 * @return Success message
	 * 
	 */
	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = VALIDATE_USER_DESC, value = VALIDATE_USER_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = VALIDATE_USER_SUCCESS_RESP) })
	public Response validateMarker(
			@ApiParam(required = true, value = USER_SEARCH_PAYLOAD) EntitySearchDTO entitySearchDto) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = userService
				.validateUniqueness(entitySearchDto);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("/count")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_USER_COUNT_DESC, value = GET_USER_COUNT_SUMMARY, response = EntityCountDTO.class)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = GET_USER_COUNT_SUCCESS_RESP) })
	public Response getUserCount(
			@ApiParam(required = true, value = USER_SEARCH_PAYLOAD) EntitySearchDTO entitySearchDto) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		EntityCountDTO entityCount = userService.getUserCount(entitySearchDto);
		responseBuilder.entity(entityCount);
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

	@POST
	@Path("/status/count")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_USER_STATUS_COUNT_DESC, value = GET_USER_STATUS_COUNT_SUMMARY, response = EntityCountDTO.class)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = GET_USER_STATUS_COUNT_SUCCESS_RESP) })
	public Response getUserStatusCount(
			@ApiParam(required = true, value = USER_SEARCH_PAYLOAD) EntitySearchDTO entitySearchDto) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<EntityStatusCountDTO> entityCount = userService
				.getUserCountByStatus(entitySearchDto);
		responseBuilder.entity(entityCount);
		return responseBuilder.build();
	}

	@POST
	@Path("/forgot_password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FORGOT_PASSWORD_DESC, value = FORGOT_PASSWORD_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = FORGOT_PASSWORD_SUCCESS_RESP) })
	public Response forgotPassword(
			@ApiParam(required = true, value = USER_SEARCH_PAYLOAD) UserDTO userDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(userService.forgotPassword(userDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("/change_password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CHANGE_PASSWORD_DESC, value = CHANGE_PASSWORD_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = CHANGE_PASSWORD_SUCCESS_RESP) })
	public Response changePassword(
			@ApiParam(required = true, value = USER_SEARCH_PAYLOAD) UserDTO userDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessage = userService.changePassword(userDTO);
		responseBuilder.entity(statusMessage);
		return responseBuilder.build();
	}
}
