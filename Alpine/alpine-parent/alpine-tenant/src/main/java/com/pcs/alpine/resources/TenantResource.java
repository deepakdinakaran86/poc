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

import static com.pcs.alpine.constants.ApiDocConstant.ASSIGN_TENANT_FEATURES_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.ASSIGN_TENANT_FEATURES_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.ASSIGN_TENANT_FEATURES_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_TENANT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_TENANT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_TENANT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_TENANT_NOTES;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_TENANT_VALUE;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_TENANT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_TENANT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_TENANT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_TENANT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_TENANT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_TENANT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANT_COUNT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANT_COUNT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANT_COUNT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANT_FEATURES;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANT_FEATURES_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANT_FEATURES_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANT_INFO;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANT_INFO_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANT_INFO_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.PERSISTENCE_ERROR;
import static com.pcs.alpine.constants.ApiDocConstant.TENANT_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.TENANT_SEARCH_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_TENANT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_TENANT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_TENANT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.VALIDATE_TENANT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.VALIDATE_TENANT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.VALIDATE_TENANT_SUMMARY;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

import com.pcs.alpine.services.TenantService;
import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;

/**
 * @description Resource for Tenant services
 * @author Twinkle (PCSEG297)
 * @date 22 Sep 2015
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
	@Path("")
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
	        value = TENANT_PAYLOAD) EntityDTO tenantDTO) {
		LOGGER.debug("<<-- Enter createTenant(TenantDTO tenantDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.createTenant(tenantDTO));
		LOGGER.debug("<<-- Exit createTenant(TenantDTO tenantDTO)-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch a tenant
	 * 
	 * @param tenantDTO
	 * @return Fetched tenant entity
	 */
	@POST
	@Path("find")
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
	        @ApiParam(name = "tenantIdentityFields",required = true,
	                value = "Tenant Identify Fields") IdentityDTO entityDTO) {
		LOGGER.debug("<<-- Enter updateTenant(TenantDTO tenantDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);

		responseBuilder.entity(tenantService.findTenant(entityDTO));
		LOGGER.debug("<<-- Exit updateTenant(TenantDTO tenantDTO)-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch a tenant
	 * 
	 * @param tenantDTO
	 * @return Fetched tenant entity
	 */
	@POST
	@Path("list")
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
	public Response findAllTenant(@ApiParam(required = true,
	        value = TENANT_PAYLOAD) IdentityDTO entityDTO) {
		LOGGER.debug("<<-- Enter updateTenant(TenantDTO tenantDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);

		responseBuilder.entity(tenantService.findAllTenant(entityDTO));
		LOGGER.debug("<<-- Exit updateTenant(TenantDTO tenantDTO)-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to update a tenant
	 * 
	 * @param tenantDTO
	 * @return Updated tenant entity
	 */
	@PUT
	@Path("")
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
	        value = TENANT_PAYLOAD) EntityDTO tenantDTO) {
		LOGGER.debug("<<-- Enter updateTenant(TenantDTO tenantDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.updateTenant(tenantDTO));
		LOGGER.debug("<<-- Exit updateTenant(TenantDTO tenantDTO)-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to delete a tenant
	 * 
	 * @param tenantDTO
	 * @return Success message
	 */
	// @POST
	// @Path("delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_TENANT_NOTES,value = DELETE_TENANT_VALUE,
	        response = StatusMessageDTO.class)
	public Response deleteTenant(
	        @ApiParam(name = "tenantEntity",required = true,
	                value = "Tenant entity to be deleted") IdentityDTO entityDTO) {
		LOGGER.debug("<<-- Enter updateTenant(TenantDTO tenantDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);

		responseBuilder.entity(tenantService.deleteTenant(entityDTO));
		LOGGER.debug("<<-- Exit updateTenant(TenantDTO tenantDTO)-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to validate a tenants fields
	 * 
	 * @param entitySearchDto
	 * @return Success message
	 */
	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = VALIDATE_TENANT_DESC,value = VALIDATE_TENANT_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK,
		        message = VALIDATE_TENANT_SUCCESS_RESP)})
	public Response validateTenant(@ApiParam(required = true,
	        value = TENANT_SEARCH_PAYLOAD) EntitySearchDTO entitySearchDto) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = tenantService
		        .validateUniqueness(entitySearchDto);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("/count")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_TENANT_COUNT_DESC,
	        value = GET_TENANT_COUNT_SUMMARY,response = EntityCountDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK,
		        message = GET_TENANT_COUNT_SUCCESS_RESP)})
	public Response getTenantCount(@ApiParam(required = true,
	        value = TENANT_SEARCH_PAYLOAD) EntitySearchDTO entitySearchDto) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		EntityCountDTO entityCount = tenantService
		        .getTenantCount(entitySearchDto);
		responseBuilder.entity(entityCount);
		return responseBuilder.build();
	}

	/**
	 * Responsible to get tenant and domain info by domain name
	 * 
	 * @param entitySearchDto
	 * @return Success message
	 */
	@GET
	@Path("/domain/info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_TENANT_INFO,value = GET_TENANT_INFO_SUMMARY,
	        response = EntityDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_TENANT_INFO_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getTenantInfo() {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.getTenantInfo());
		return responseBuilder.build();
	}

	@GET
	@Path("/features")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_TENANT_FEATURES,
	        value = GET_TENANT_FEATURES_SUMMARY,response = EntityDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_TENANT_FEATURES_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getTenantFeaturs(@QueryParam("domain") String domainName) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.getAllFeatures(domainName));
		return responseBuilder.build();
	}

	@POST
	@Path("/features/assign")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ASSIGN_TENANT_FEATURES_DESC,
	        value = ASSIGN_TENANT_FEATURES_SUMMARY,response = EntityDTO.class,
	        hidden = true)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = ASSIGN_TENANT_FEATURES_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response assignTenantFeaturs(@ApiParam(required = true,
	        value = TENANT_SEARCH_PAYLOAD) List<String> features,
	        @QueryParam("domain") String domainName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tenantService.assignFeatures(features,
		        domainName));
		return responseBuilder.build();
	}
}