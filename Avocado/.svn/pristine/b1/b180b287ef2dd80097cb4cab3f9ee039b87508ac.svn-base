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

import static com.pcs.avocado.constants.ApiDocConstant.CREATE_TENANT_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.CREATE_TENANT_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.CREATE_TENANT_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_ALL_TENANT_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_ALL_TENANT_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_ALL_TENANT_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_TENANT_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_TENANT_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.FIND_TENANT_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.avocado.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.avocado.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.avocado.constants.ApiDocConstant.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.avocado.constants.ApiDocConstant.PERSISTENCE_ERROR;
import static com.pcs.avocado.constants.ApiDocConstant.TENANT_PAYLOAD;
import static com.pcs.avocado.constants.ApiDocConstant.UPDATE_TENANT_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.UPDATE_TENANT_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.UPDATE_TENANT_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.CREATE_TENANT_ADMIN_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.CREATE_TENANT_ADMIN_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.CREATE_TENANT_ADMIN_SUMMARY;
import static com.pcs.avocado.constants.ApiDocConstant.SEND_TENANT_ADMIN_DESC;
import static com.pcs.avocado.constants.ApiDocConstant.SEND_TENANT_ADMIN_SUCCESS_RESP;
import static com.pcs.avocado.constants.ApiDocConstant.SEND_TENANT_ADMIN_SUMMARY;

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
import com.pcs.avocado.dto.TenantAdminDTO;
import com.pcs.avocado.dto.TenantAdminEmailDTO;
import com.pcs.avocado.dto.TenantDTO;
import com.pcs.avocado.service.TenantService;

/**
 * @description Resource for Tenant services
 * @author Daniela (PCSEG191)
 * @date 11 Jan 2016
 * @since galaxy-1.0.0
 */
@Path("/tenants")
@Component
@Api("Tenant")
public class TenantResource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TenantResource.class);

	@Autowired
	private TenantService tenantService;

	/**
	 * Responsible to create a tenant
	 * 
	 * @param tenantDTO
	 * @return Created tenant entity
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_TENANT_DESC,value = CREATE_TENANT_SUMMARY,
	response = IdentityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK,message = CREATE_TENANT_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 503,message = PERSISTENCE_ERROR),
			@ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createTenant(@ApiParam(required = true,
	value = TENANT_PAYLOAD) TenantDTO tenantDTO) {
		LOGGER.debug("<<-- Creating tenant -->>",tenantDTO);
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.createTenant(tenantDTO));
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch a tenant
	 * 
	 * @param tenantDTO
	 * @return Fetched tenant entity
	 */
	@GET
	@Path("/{tenant_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_TENANT_DESC,value = FIND_TENANT_SUMMARY,
	response = EntityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK,message = FIND_TENANT_SUCCESS_RESP),
			@ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response findTenant(
			@PathParam("tenant_id") String tenantId,@QueryParam("domain_name") String domain) {
		LOGGER.debug("<<--Finding tenant -->>",tenantId);
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.findTenant(tenantId,domain));
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch a tenant
	 * 
	 * @param tenantDTO
	 * @return Fetched tenant entity
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_TENANT_DESC,value = FIND_ALL_TENANT_SUMMARY,
	response = EntityDTO.class,responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK,
					message = FIND_ALL_TENANT_SUCCESS_RESP),
					@ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
					@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
					@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response findAllTenants(@QueryParam("domain_name") String domain) {
		LOGGER.debug("<<-- Finding all tenants of a domain -->>",domain);
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.findAllTenants(domain));
		return responseBuilder.build();
	}

	/**
	 * Responsible to update a tenant
	 * 
	 * @param tenantDTO
	 * @return Updated tenant entity
	 */
	@PUT
	@Path("/{tenant_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_TENANT_DESC,value = UPDATE_TENANT_SUMMARY,
	response = EntityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK,message = UPDATE_TENANT_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 503,message = PERSISTENCE_ERROR),
			@ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response updateTenant(@ApiParam(required = true,
	value = TENANT_PAYLOAD) TenantDTO tenantDTO,
	@PathParam("tenant_id") String tenantId,@QueryParam("domain_name") String domain) {
		LOGGER.debug("<<--Updating tenant -->>",tenantId);
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.updateTenant(tenantDTO,tenantId,domain));
		return responseBuilder.build();
	}

	@POST
	@Path("/admin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_TENANT_ADMIN_DESC,value = CREATE_TENANT_ADMIN_SUMMARY,
	response = IdentityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK,message = CREATE_TENANT_ADMIN_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 503,message = PERSISTENCE_ERROR),
			@ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createTenantAdmin(@ApiParam(required = true,
	value = TENANT_PAYLOAD) TenantAdminDTO tenantDTO) {
		LOGGER.debug("<<-- Creating tenant admin -->>",tenantDTO);
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.createTenantAdmin(tenantDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("/admin/email")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = SEND_TENANT_ADMIN_DESC,value = SEND_TENANT_ADMIN_SUMMARY,
	response = IdentityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK,message = SEND_TENANT_ADMIN_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 503,message = PERSISTENCE_ERROR),
			@ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response sendTenantAdminMail(@ApiParam(required = true,
	value = TENANT_PAYLOAD) TenantAdminEmailDTO tenantDTO) {
		LOGGER.debug("<<--Sending email to create tenant admin -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.sendCreateTenantAdminMail(tenantDTO));
		return responseBuilder.build();
	}



}