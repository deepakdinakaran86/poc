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

import static com.pcs.alpine.constants.ApiDocConstant.CREATE_TAG_TYPE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_TAG_TYPE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_TAG_TYPES_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_TAG_TYPES_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_TAG_TYPES_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAG_TYPES_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAG_TYPES_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAG_TYPES_SUMMARY;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.dto.TagTypeTemplate;
import com.pcs.alpine.service.TagTypeTemplateService;

/**
 * TagType Resource Methods
 * 
 * @author Twinkle (PCSEG297)
 * @date May 2016
 * @since galaxy-1.0.0
 */
@Path("/tagTypes")
@Component
@Api("TagType")
public class TagTypeTemplateResource {

	@Autowired
	private TagTypeTemplateService tagTypeTemplateService;

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CREATE_TAG_TYPE_SUMMARY,notes = CREATE_TAG_TYPE_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createTagTypeTemplate(TagTypeTemplate tagTypeTemplate) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagTypeTemplateService
		        .createTagType(tagTypeTemplate));
		return responseBuilder.build();
	}

	@GET
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_TAG_TYPES_SUMMARY,notes = GET_ALL_TAG_TYPES_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_ALL_TAG_TYPES_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getAllTagType(@QueryParam("domain_name") String domainName) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder
		        .entity(tagTypeTemplateService.getAllTagType(domainName));
		return responseBuilder.build();
	}

	@GET
	@Path("/{tag_type_name}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_TAG_TYPES_SUMMARY,notes = GET_TAG_TYPES_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_TAG_TYPES_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getTagType(
	        @PathParam("tag_type_name") String tagTypeName,
	        @QueryParam("domain_name") String domainName) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagTypeTemplateService.getTagType(tagTypeName,
		        domainName));
		return responseBuilder.build();
	}
}
