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

import static com.pcs.alpine.constants.ApiDocConstant.ASSIGN_TEMPLATES_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.ASSIGN_TEMPLATES_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.ASSIGN_TEMPLATE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_TEMPLATE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_TEMPLATE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_TEMPLATE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.ENTITY_TEMPLATE_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_TEMPLATES_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_TEMPLATES_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_TEMPLATES_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_TEMPLATE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_TEMPLATE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_TEMPLATE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.LIST_TEMPLATES_BY_PARENT_TEMPLATE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.LIST_TEMPLATES_BY_PARENT_TEMPLATE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.LIST_TEMPLATES_BY_PARENT_TEMPLATE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.SAVE_TEMPLATE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.SAVE_TEMPLATE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_TEMPLATE_SUCCESS_RESP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ws.rs.Consumes;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.constants.ApiDocConstant;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.dto.EntityTemplateDTO;

/**
 * 
 * @description Resource for EntityTemplate services
 * @author Twinkle (PCSEG297)
 * @date October 2015
 * @since galaxy-1.0.0
 */
@Path("/templates")
@Component
@Api("Templates")
public class EntityTemplateResource {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(EntityTemplateResource.class);

	@Autowired
	private EntityTemplateService entityTemplateService;

	@Autowired
	private PlatformEntityTemplateService platformEntityTemplateService;

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
	 * Responsible to save a template
	 * 
	 * 
	 * @param entityTemplateDTO
	 * 
	 * @return Created template
	 * 
	 */
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = SAVE_TEMPLATE_DESC,value = SAVE_TEMPLATE_SUMMARY,
	        response = EntityTemplateDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = ApiDocConstant.SAVE_TEMPLATE_SUCCESS_RESP),
	        @ApiResponse(code = 505,
	                message = ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,
	                message = ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE)})
	public Response saveTemplate(
	        @ApiParam(required = true,value = ENTITY_TEMPLATE_PAYLOAD) EntityTemplateDTO entityTemplateDTO,
	        @QueryParam("isParentDomain") Boolean isParentDomain) {
		LOGGER.debug("<<-- Enter saveTemplate(EntityTemplateDTO entityTemplateDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(entityTemplateService
		        .saveTemplate(entityTemplateDTO));
		return responseBuilder.build();
	}

	/**
	 * Responsible to update a template
	 * 
	 * 
	 * @param entityTemplateDTO
	 * 
	 * @return updated template
	 * 
	 */
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ApiDocConstant.UPDATE_TEMPLATE_DESC,
	        value = ApiDocConstant.UPDATE_TEMPLATE_SUMMARY,
	        response = EntityTemplateDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = UPDATE_TEMPLATE_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response updateTemplate(EntityTemplateDTO entityTemplateDTO) {
		LOGGER.debug("<<-- Enter updateTemplate(EntityTemplateDTO entityTemplateDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(entityTemplateService
		        .updateTemplate(entityTemplateDTO));
		LOGGER.debug("<<-- Exit updateTemplate(EntityTemplateDTO entityTemplateDTO)-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch a template
	 * 
	 * 
	 * @param entityTemplateDTO
	 * 
	 * @return Fetched template
	 * 
	 */
	@POST
	@Path("find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_TEMPLATE_DESC,value = FIND_TEMPLATE_SUMMARY,
	        response = EntityTemplateDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = FIND_TEMPLATE_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response findTemplate(EntityTemplateDTO entityTemplateDTO) {
		LOGGER.debug("<<-- Enter findTemplate(EntityTemplateDTO entityTemplateDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);

		responseBuilder.entity(entityTemplateService
		        .getTemplate(entityTemplateDTO));
		LOGGER.debug("<<-- Exit findTemplate(EntityTemplateDTO entityTemplateDTO)-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch all template by domain and with/without
	 * globalEntityType(by default it would be MARKER)
	 * 
	 * 
	 * @param entityTemplateDTO
	 * 
	 * @return Fetched templates
	 * 
	 */
	@POST
	@Path("list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_TEMPLATES_DESC,
	        value = FIND_ALL_TEMPLATES_SUMMARY,
	        response = EntityTemplateDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = FIND_ALL_TEMPLATES_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response findAllTemplate(EntityTemplateDTO entityTemplateDTO) {
		LOGGER.debug("<<-- Enter findAllTenant(EntityTemplateDTO entityTemplateDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);

		responseBuilder.entity(entityTemplateService
		        .findAllTemplate(entityTemplateDTO));
		LOGGER.debug("<<-- Exit findAllTenant(EntityTemplateDTO entityTemplateDTO) -->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to delete a template
	 * 
	 * 
	 * @param entityTemplateDTO
	 * 
	 * @return Success message
	 * 
	 */
	@POST
	@Path("delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_TEMPLATE_DESC,value = DELETE_TEMPLATE_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = DELETE_TEMPLATE_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response deleteTemplate(EntityTemplateDTO entityTemplateDTO) {
		LOGGER.debug("<<-- Enter updateTenant(TenantDTO tenantDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);

		responseBuilder.entity(entityTemplateService
		        .deleteEntityTemplate(entityTemplateDTO));
		LOGGER.debug("<<-- Exit updateTenant(TenantDTO tenantDTO)-->>");
		return responseBuilder.build();
	}

	@POST
	@Path("assign")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ASSIGN_TEMPLATES_DESC,
	        value = ASSIGN_TEMPLATE_SUMMARY,response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = ASSIGN_TEMPLATES_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response allocateTemplates(
	        List<EntityTemplateDTO> entityTemplateDTOs,
	        @QueryParam("domain") String domain,
	        @QueryParam("isParentDomain") Boolean isParentDomain) {
		LOGGER.debug("<<-- Enter allocate templates-->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(entityTemplateService.allocateTemplates(
		        entityTemplateDTOs, domain, isParentDomain));
		LOGGER.debug("<<-- Exit allocate templates-->>");
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch all template by domain and with/without
	 * globalEntityType(by default it would be MARKER)
	 * 
	 * 
	 * @param entityTemplateDTO
	 * 
	 * @return Fetched templates
	 * 
	 */
	@POST
	@Path("listByParentTemplate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = LIST_TEMPLATES_BY_PARENT_TEMPLATE_DESC,
	        value = LIST_TEMPLATES_BY_PARENT_TEMPLATE_SUMMARY,
	        response = EntityTemplateDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = LIST_TEMPLATES_BY_PARENT_TEMPLATE_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response findAllTemplateByParentTemplate(
	        EntityTemplateDTO entityTemplateDTO) {
		LOGGER.debug("<<-- Enter findAllTenant(EntityTemplateDTO entityTemplateDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);

		responseBuilder.entity(entityTemplateService
		        .findAllTemplateByParentTemplate(entityTemplateDTO));
		LOGGER.debug("<<-- Exit findAllTenant(EntityTemplateDTO entityTemplateDTO) -->>");
		return responseBuilder.build();
	}

	@POST
	@Path("/status/{statusName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatTemplateStatus(EntityTemplateDTO entityTemplate,
	        @PathParam("statusName") String statusName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		com.pcs.alpine.services.dto.StatusMessageDTO statusMessageDTO = entityTemplateService
		        .updateStatus(entityTemplate, statusName);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
}