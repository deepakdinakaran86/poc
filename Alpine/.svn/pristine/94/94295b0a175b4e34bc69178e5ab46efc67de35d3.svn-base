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

import static com.pcs.alpine.constants.ApiDocConstant.ALL_TAGS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.ALL_TAGS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.ALL_TAGS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_TAG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_TAG_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_TAG_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.DOMAIN_NAME;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_TAGS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_TAGS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_TAGS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAGGED_ENTITIES_COUNT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAGGED_ENTITIES_COUNT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAGGED_ENTITIES_COUNT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAGS_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAG_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAG_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.MAP_SUBJECTS_TO_TAG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.MAP_SUBJECTS_TO_TAG_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.MAP_TAGS_TO_ENTITY_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.MAP_TAGS_TO_ENTITY_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.SUBJECT__FILTERED_ENTITIES_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.SUBJECT__FILTERED_ENTITIES_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.SUBJECT__FILTERED_ENTITIES_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.TAGGED_SUBJECT_ENTITIES_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.TAGGED_SUBJECT_ENTITIES_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.TAGGED_SUBJECT_ENTITIES_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.TAGGED_SUBJECT_TEMPLATES_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.TAGGED_SUBJECT_TEMPLATES_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.TAGGED_SUBJECT_TEMPLATES_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.TAGS_BY_RANGE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.TAGS_BY_RANGE_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.TAGS_BY_RANGE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.TAG_CONFIGURATION_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.TAG_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.TAG_TYPE;
import static com.pcs.alpine.constants.ApiDocConstant.TAG_VALIDATE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.TAG_VALIDATE_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.TAG_VALIDATE_SUMMARY;
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

import com.pcs.alpine.commons.dto.EntityDTO;
import com.pcs.alpine.commons.dto.HierarchyTagSearchDTO;
import com.pcs.alpine.dto.AttachHierarchySearchDTO;
import com.pcs.alpine.dto.SubjectEntitiesByTags;
import com.pcs.alpine.dto.Tag;
import com.pcs.alpine.dto.TagConfiguration;
import com.pcs.alpine.dto.ValidatePayload;
import com.pcs.alpine.service.TagService;

/**
 * Tags Resource Methods
 * 
 * @author Twinkle (PCSEG297)
 * @date May 2016
 * @since galaxy-1.0.0
 */

@Path("/tags")
@Component
@Api("Tags")
public class TagResource {

	@Autowired
	private TagService tagService;

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CREATE_TAG_SUMMARY,notes = CREATE_TAG_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = CREATE_TAG_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createTag(
	        @ApiParam(value = TAG_PAYLOAD,required = true) EntityDTO tag) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagService.createTag(tag));
		return responseBuilder.build();
	}

	@GET
	@Path(value = "/{tag_type}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_TAGS_SUMMARY,notes = GET_ALL_TAGS_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_ALL_TAGS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getAllTags(
	        @ApiParam(value = DOMAIN_NAME,required = false) @QueryParam("domain_name") String domain,
	        @ApiParam(value = TAG_TYPE,required = true) @PathParam("tag_type") String tagType) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagService.getAllTagsByType(tagType, domain));
		return responseBuilder.build();
	}

	@POST
	@Path("/find")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_TAG_SUMMARY,notes = GET_TAG_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_TAG_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getTag(Tag tag) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagService.getTagDetails(tag));
		return responseBuilder.build();
	}

	@POST
	@Path(value = "/subjects")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = MAP_SUBJECTS_TO_TAG_SUMMARY,
	        notes = MAP_SUBJECTS_TO_TAG_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response mapSubjects(@ApiParam(value = TAG_CONFIGURATION_PAYLOAD,
	        required = true) TagConfiguration configuration) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder
		        .entity(tagService.mapSubjectsToTagEntity(configuration));
		return responseBuilder.build();
	}

	@POST
	@Path(value = "/actors")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = MAP_TAGS_TO_ENTITY_SUMMARY,
	        notes = MAP_TAGS_TO_ENTITY_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response mapTags(@ApiParam(value = TAG_CONFIGURATION_PAYLOAD,
	        required = true) TagConfiguration configuration) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagService.mapTagsToAnEntity(configuration));
		return responseBuilder.build();
	}

	@POST
	@Path(value = "/subjects/entities")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = TAGGED_SUBJECT_ENTITIES_SUMMARY,
	        notes = TAGGED_SUBJECT_ENTITIES_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = TAGGED_SUBJECT_ENTITIES_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getAllSubjectEntities(EntityDTO entity) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagService.getAllEntitySubjects(entity));
		return responseBuilder.build();
	}

	@POST
	@Path(value = "/subjects/templates")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = TAGGED_SUBJECT_TEMPLATES_SUMMARY,
	        notes = TAGGED_SUBJECT_TEMPLATES_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = TAGGED_SUBJECT_TEMPLATES_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getAllSubjectTemplates(EntityDTO entity) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagService.getAllTemplateSubjects(entity));
		return responseBuilder.build();
	}

	@POST
	@Path(value = "/entities")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = ALL_TAGS_SUMMARY,notes = ALL_TAGS_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = ALL_TAGS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getAllTagsOfAnActor(@ApiParam(value = GET_TAGS_PAYLOAD,
	        required = true) AttachHierarchySearchDTO attachHierarchySearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagService
		        .getAllTagsOfActor(attachHierarchySearch));
		return responseBuilder.build();
	}

	@POST
	@Path(value = "/validatePayload")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = TAG_VALIDATE_SUMMARY,notes = TAG_VALIDATE_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = TAG_VALIDATE_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response validate(ValidatePayload payload) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagService.validatePayload(payload));
		return responseBuilder.build();
	}

	@POST
	@Path(value = "/range")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = TAGS_BY_RANGE_SUMMARY,notes = TAGS_BY_RANGE_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = TAGS_BY_RANGE_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response tagsByRangeOfNodes() {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		return responseBuilder.build();
	}

	@POST
	@Path(value = "/subjects/filter/entities")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = SUBJECT__FILTERED_ENTITIES_SUMMARY,
	        notes = SUBJECT__FILTERED_ENTITIES_DESC)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = SUBJECT__FILTERED_ENTITIES_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	                @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getFilteredSubjectEntities(@ApiParam(value = PAYLOAD,
	        required = true) SubjectEntitiesByTags subjectEntitiesByTags) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(tagService
		        .getFilteredSubjectEntities(subjectEntitiesByTags));
		return responseBuilder.build();
	}

	@POST
	@Path("attached/entity/count")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_TAGGED_ENTITIES_COUNT_DESC,
	        value = GET_TAGGED_ENTITIES_COUNT_SUMMARY,
	        response = HierarchyTagSearchDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_TAGGED_ENTITIES_COUNT_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getTaggedEntityCount(@ApiParam(required = true,
	        value = PAYLOAD) HierarchyTagSearchDTO hierarchyTagSearchDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(tagService
		        .getTaggedEntityCount(hierarchyTagSearchDTO));
		return responseBuilder.build();
	}

}
