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

import static com.pcs.alpine.constants.ApiDocConstant.CREATE_ROLE_NOTES;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_ROLE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_ROLE_VALUE;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_ROLE_NOTES;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_ROLE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_ROLE_VALUE;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_ROLE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_ROLE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_ROLE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ROLE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ROLE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ROLE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_ROLE_NOTES;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_ROLE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_ROLE_VALUE;
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

import com.pcs.alpine.dto.RoleDTO;
import com.pcs.alpine.service.RoleService;

/**
 * UserAdapterResource manages User related services.
 * 
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since alpine-1.0.0
 */
@Path("/users/roles")
@Component
@Api("Roles")
public class RoleResource {

	@Autowired
	RoleService roleService;

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

	@GET
	@Path("/{role_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ROLE_DESC, value = FIND_ROLE_SUMMARY, response=RoleDTO.class)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = FIND_ROLE_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getRole(@PathParam("role_name") String roleName,
			@QueryParam("domain_name") String domainName,
			@QueryParam("isParentDomain") Boolean isParentDomain) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(roleService.getRole(roleName, domainName, isParentDomain));
		return responseBuilder.build();
	}

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_ROLE_DESC, value = FIND_ALL_ROLE_SUMMARY, response=RoleDTO.class, responseContainer="List")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = FIND_ALL_ROLE_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getRoles(@QueryParam("domain_name") String domainName,
			@QueryParam("isParentDomain") Boolean isParentDomain) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<RoleDTO> roles = roleService.getRoles(domainName, isParentDomain);
		GenericEntity<List<RoleDTO>> entity = new GenericEntity<List<RoleDTO>>(
				roles) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_ROLE_NOTES, value = CREATE_ROLE_VALUE)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = CREATE_ROLE_SUCCESS_RESP) })
	public Response saveRole(
			@ApiParam(name = "role", required = true, value = "Role") RoleDTO role) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(roleService.saveRole(role));
		return responseBuilder.build();
	}

	@PUT
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_ROLE_NOTES, value = UPDATE_ROLE_VALUE)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = UPDATE_ROLE_SUCCESS_RESP) })
	public Response updateRole(
			@ApiParam(name = "role", required = true, value = "Role") RoleDTO role) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(roleService.updateRole(role));
		return responseBuilder.build();
	}

	@DELETE
	@Path("/{role_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_ROLE_NOTES, value = DELETE_ROLE_VALUE, hidden = true)
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = DELETE_ROLE_SUCCESS_RESP) })
	public Response deleteRole(@PathParam("role_name") String roleName,
			@QueryParam("domain_name") String domainName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(roleService.deleteRole(roleName, domainName));
		return responseBuilder.build();
	}
}
