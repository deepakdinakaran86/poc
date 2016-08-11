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

import static com.pcs.alpine.constants.ApiDocConstant.CREATE_PG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_PG_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_PG_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIELD_ALREADY_EXIST;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_PERMISSIONS_OF_USER_NOTE;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_PERMISSIONS_OF_USER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_PERMISSIONS_OF_USER_VALUE;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_PG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_PG_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_PG_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_RESOURCE_NOTES;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_RESOURCE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_RESOURCE_VALUE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.PERMISSION_DUPLICATED;
import static com.pcs.alpine.constants.ApiDocConstant.PERMISSION_GROUP_CREATION_FAILED;
import static com.pcs.alpine.constants.ApiDocConstant.PERMISSION_GROUP_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.PERMISSION_GROUP_SUBSCRIPTION_FAILED;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_PG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_PG_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_PG_SUMMARY;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.dto.PermissionGroupsDTO;
import com.pcs.alpine.dto.UserPermissionsDTO;
import com.pcs.alpine.service.PermissionGroupService;
import com.pcs.alpine.service.RoleService;

/**
 * PermissionGroupResource manages permission related services.
 * 
 * @author Daniela(PCSEG191)
 * @date 25 October 2015
 * @since alpine-1.0.0
 */
@Path("/users/permissions")
@Component
@Api("Permissions")
public class PermissionGroupResource {

	@Autowired
	PermissionGroupService permissionGroupService;

	@Autowired
	private RoleService roleService;

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

	@GET
	@Path("{resource_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_PG_DESC,value = FIND_PG_SUMMARY,
	        response = PermissionGroupsDTO.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = HttpStatus.SC_OK,
	                        message = FIND_PG_SUCCESS_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 503,
	                        message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getPermissionGroup(
	        @PathParam("resource_name") String groupName,
	        @QueryParam("domain") String domain,
	        @QueryParam("isParentDomain") Boolean isParentDomain) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(permissionGroupService.getPermissionGroup(
		        groupName, domain, isParentDomain));
		return responseBuilder.build();
	}

	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_PG_DESC,value = CREATE_PG_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = HttpStatus.SC_OK,
	                        message = CREATE_PG_SUCCESS_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 507,message = FIELD_ALREADY_EXIST),
	                @ApiResponse(code = 1650,
	                        message = PERMISSION_GROUP_CREATION_FAILED),
	                @ApiResponse(code = 1651,
	                        message = PERMISSION_GROUP_SUBSCRIPTION_FAILED),
	                @ApiResponse(code = 1653,message = PERMISSION_DUPLICATED)})
	public Response savePermissionGroup(
	        @ApiParam(required = true,value = PERMISSION_GROUP_PAYLOAD) PermissionGroupsDTO permissionGroupsDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(permissionGroupService
		        .createPermissionGroup(permissionGroupsDTO));
		return responseBuilder.build();
	}

	@PUT
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_PG_DESC,value = UPDATE_PG_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = HttpStatus.SC_OK,
	                        message = UPDATE_PG_SUCCESS_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 507,message = FIELD_ALREADY_EXIST),
	                @ApiResponse(code = 1650,
	                        message = PERMISSION_GROUP_CREATION_FAILED),
	                @ApiResponse(code = 1651,
	                        message = PERMISSION_GROUP_SUBSCRIPTION_FAILED),
	                @ApiResponse(code = 1653,message = PERMISSION_DUPLICATED)})
	public Response updatePermissionGroup(
	        @ApiParam(required = true,value = PERMISSION_GROUP_PAYLOAD) PermissionGroupsDTO permissionGroupsDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(permissionGroupService
		        .updatePermissionGroup(permissionGroupsDTO));
		return responseBuilder.build();
	}

	// @DELETE
	// @Path("{resource_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "",hidden = true)
	public Response deletePermissionGroup(
	        @PathParam("resource_name") String groupName,
	        @QueryParam("domain") String domain) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(permissionGroupService.deletePermissionGroup(
		        groupName, domain));
		return responseBuilder.build();
	}

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_RESOURCE_NOTES,value = FIND_RESOURCE_VALUE)
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK,
		        message = FIND_RESOURCE_SUCCESS_RESP)})
	public Response getResource(@QueryParam("domain") String domain,
	        @QueryParam("isParentDomain") Boolean isParentDomain) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(permissionGroupService.getResources(domain,
		        isParentDomain));
		return responseBuilder.build();
	}

	@GET
	@Path("/current")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_PERMISSIONS_OF_USER_NOTE,
	        value = FIND_PERMISSIONS_OF_USER_VALUE)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = FIND_PERMISSIONS_OF_USER_SUCCESS_RESP),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 507,message = FIELD_ALREADY_EXIST)})
	public Response getPermissionsOfCurrentUser() {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		UserPermissionsDTO permissionsOfCurrentUser = roleService
		        .getPermissionsOfCurrentUser();
		responseBuilder.entity(permissionsOfCurrentUser);
		return responseBuilder.build();
	}

}
