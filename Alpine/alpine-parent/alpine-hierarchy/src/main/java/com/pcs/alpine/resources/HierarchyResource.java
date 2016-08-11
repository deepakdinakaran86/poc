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

import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_ACTORS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_ACTORS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_ACTORS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_CHILDREN_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_CHILDREN_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_CHILDREN_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_HIERARCHY_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_MARKERS_MULTIPLE_PARENTS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_MARKERS_MULTIPLE_PARENTS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_MARKERS_MULTIPLE_PARENTS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_PARENT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_PARENT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_PARENT__SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_SUBJECTS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_SUBJECTS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.ATTACH_SUBJECTS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.ENTITIES_FROM_WRONG_HIERARCHY;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_ENTITIES_SUBJECTS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_ENTITIES_SUBJECTS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_ENTITIES_SUBJECTS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_TEMPLATES_SUBJECTS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_TEMPLATES_SUBJECTS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_TEMPLATES_SUBJECTS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ASSIGNABLE_MARKER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ASSIGNABLE_MARKER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ASSIGNABLE_MARKER__SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_CHILDREN_COUNT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_CHILDREN_COUNT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_CHILDREN_COUNT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_CHILDREN_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_CHILDREN_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_CHILDREN__SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_DISTRIBUTION_COUNT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_DISTRIBUTION_COUNT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_DISTRIBUTION_COUNT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_DISTRIBUTION_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_DISTRIBUTION_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_DISTRIBUTION_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_IMMEDIATE_CHILDREN_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_IMMEDIATE_CHILDREN_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_IMMEDIATE_CHILDREN__SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_IMMEDIATE_PARENT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_IMMEDIATE_PARENT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_IMMEDIATE_PARENT__SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_IMMEDIATE_ROOTS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_IMMEDIATE_ROOTS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_IMMEDIATE_ROOTS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_MARKERS_WITHIN_TENANTS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_MARKERS_WITHIN_TENANTS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_MARKERS_WITHIN_TENANTS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_OWNED_MARKER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_OWNED_MARKER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_OWNED_MARKER__SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_STATUS_COUNT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_STATUS_COUNT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_STATUS_COUNT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAGGED_ENTITIES_COUNT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAGGED_ENTITIES_COUNT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TAGGED_ENTITIES_COUNT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TEMPLATE_NAMES_OF_ATTACHED_ENTITIES_OF_DOMAIN_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TEMPLATE_NAMES_OF_ATTACHED_ENTITIES_OF_DOMAIN_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TEMPLATE_NAMES_OF_ATTACHED_ENTITIES_OF_DOMAIN_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANTS_WITHIN_RANGE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANTS_WITHIN_RANGE_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_TENANTS_WITHIN_RANGE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.HIERARCHY_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.SUBJECT__FILTERED_ENTITIES_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.SUBJECT__FILTERED_ENTITIES_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.SUBJECT__FILTERED_ENTITIES_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.TAG_ENITIES_BY_RANGE_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.TAG_ENITIES_BY_RANGE_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.TAG_ENITIES_BY_RANGE_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.TENANT_LINK_EXCEPTION;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_BATCH_STATUS_NODE_DEC;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_BATCH_STATUS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_BATCH_STATUS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_NODE_DEC;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_NODE_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_NODE_SUMMARY;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.commons.dto.GeneralBatchResponse;
import com.pcs.alpine.commons.dto.HierarchyTagSearchDTO;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.services.HierarchyService;
import com.pcs.alpine.services.dto.AttachHierarchyDTO;
import com.pcs.alpine.services.dto.AttachHierarchySearchDTO;
import com.pcs.alpine.services.dto.DomainAccessDTO;
import com.pcs.alpine.services.dto.EntitiesByTagsPayload;
import com.pcs.alpine.services.dto.EntityAssignDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityRangeDTO;
import com.pcs.alpine.services.dto.HierarchyDTO;
import com.pcs.alpine.services.dto.HierarchyRelationDTO;
import com.pcs.alpine.services.dto.HierarchyReturnDTO;
import com.pcs.alpine.services.dto.HierarchySearchDTO;
import com.pcs.alpine.services.dto.TagRangePayload;

/**
 * @description Rest Resource used to manage all the services related to custom
 *              entity
 * 
 * @author Daniela (pcseg191)
 * @date 14 Jan 2015
 */

@Path("/hierarchy")
@Component
@Api("Hierarchy")
public class HierarchyResource {

	@Autowired
	private HierarchyService hierarchyService;

	/**
	 * @description This is a default method to get the header options,all
	 *              resource layers should have this method
	 * 
	 */

	@OPTIONS
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "",hidden = true)
	public Response defaultOptions() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.header("ALLOW", "HEAD,GET,PUT,DELETE,OPTIONS");
		return responseBuilder.build();
	}

	@POST
	@Path("attach/children")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ATTACH_CHILDREN_DESC,value = ATTACH_CHILDREN_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = ATTACH_CHILDREN_SUCCESS_RESP),
	        @ApiResponse(code = 753,message = TENANT_LINK_EXCEPTION),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 753,message = ENTITIES_FROM_WRONG_HIERARCHY)})
	public Response attachChildren(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchyDTO hierarchy) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService.attachChildren(hierarchy));
		return responseBuilder.build();
	}

	@POST
	@Path("attach/parents")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ATTACH_PARENT_DESC,value = ATTACH_PARENT_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = ATTACH_PARENT__SUCCESS_RESP),
	        @ApiResponse(code = 753,message = TENANT_LINK_EXCEPTION),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response attachParents(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchyDTO hierarchy) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService.attachParents(hierarchy));
		return responseBuilder.build();
	}

	// Service will not be exposed
	@POST
	@Path("attach/tenant")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "",hidden = true)
	public Response addTenantNode(HierarchyDTO hierarchy) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService.createTenantNode(hierarchy));
		return responseBuilder.build();
	}

	@POST
	@Path("children")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_CHILDREN_DESC,value = GET_CHILDREN_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = GET_CHILDREN__SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getChildEntities(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchySearchDTO hierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService.getChildren(hierarchySearch));
		return responseBuilder.build();
	}

	@POST
	@Path("children/count")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_CHILDREN_COUNT_DESC,
	        value = GET_CHILDREN_COUNT_SUMMARY,
	        response = HierarchyReturnDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_CHILDREN_COUNT_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getChildrenCount(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchySearchDTO hierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getChildEntityTypeCount(hierarchySearch));
		return responseBuilder.build();
	}

	@POST
	@Path("status/count")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_STATUS_COUNT_DESC,
	        value = GET_STATUS_COUNT_SUMMARY,
	        response = HierarchyReturnDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_STATUS_COUNT_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getCountByStatus(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchySearchDTO hierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getCountByStatus(hierarchySearch));
		return responseBuilder.build();
	}

	@PUT
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_NODE_DEC,value = UPDATE_NODE_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,message = UPDATE_NODE_RESP),
	        @ApiResponse(code = 753,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response updateNode(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) EntityDTO entity) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService.updateNode(entity));
		return responseBuilder.build();
	}

	@POST
	@Path("distribution/count")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_DISTRIBUTION_COUNT_DESC,
	        value = GET_DISTRIBUTION_COUNT_SUMMARY,
	        response = HierarchyReturnDTO.class,responseContainer = "List",
	        hidden = true)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_DISTRIBUTION_COUNT_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getDistributionCount(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchySearchDTO hierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getEntityDistributionCount(hierarchySearch));
		return responseBuilder.build();
	}

	@POST
	@Path("distribution")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_DISTRIBUTION_DESC,
	        value = GET_DISTRIBUTION_SUMMARY,response = EntityDTO.class,
	        responseContainer = "List",hidden = true)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_DISTRIBUTION_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getDistribution(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchySearchDTO hierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getEntityDistribution(hierarchySearch));
		return responseBuilder.build();
	}

	@POST
	@Path("children/immediate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_IMMEDIATE_CHILDREN_DESC,
	        value = GET_IMMEDIATE_CHILDREN_SUMMARY,response = EntityDTO.class,
	        responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_IMMEDIATE_CHILDREN__SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getImmediateChildren(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchySearchDTO hierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getImmediateChildren(hierarchySearch));
		return responseBuilder.build();
	}

	@POST
	@Path("parents/immediate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_IMMEDIATE_PARENT_DESC,
	        value = GET_IMMEDIATE_PARENT_SUMMARY,response = EntityDTO.class,
	        responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_IMMEDIATE_PARENT__SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getImmediateParents(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchySearchDTO hierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getImmediateParent(hierarchySearch));
		return responseBuilder.build();
	}

	@GET
	@Path("domain_access/{domain_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = DomainAccessDTO.class,hidden = true,value = "")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,message = ""),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response isDomainAccessible(
	        @PathParam("domain_name") String domainName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService.isDomainAccessible(domainName));
		return responseBuilder.build();
	}

	@POST
	@Path("markers/assign")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_ASSIGNABLE_MARKER_DESC,
	        value = GET_ASSIGNABLE_MARKER_SUMMARY,response = EntityDTO.class,
	        responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_ASSIGNABLE_MARKER__SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getAssignableMarkers(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) EntityAssignDTO entityAssignDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getAssignableMarkers(entityAssignDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("markers/owned")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_OWNED_MARKER_DESC,
	        value = GET_OWNED_MARKER_SUMMARY,response = EntityDTO.class,
	        responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_OWNED_MARKER__SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getAllOwnedMarkersByDomain(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) EntityAssignDTO entityAssignDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getAllOwnedMarkersByDomain(entityAssignDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("range/tenants")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_TENANTS_WITHIN_RANGE_DESC,
	        value = GET_TENANTS_WITHIN_RANGE_SUMMARY,
	        response = EntityDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_TENANTS_WITHIN_RANGE_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getTenantsWithinRange(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) EntityRangeDTO entityRangeDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getTenantsWithinRange(entityRangeDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("attach/subjects")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ATTACH_SUBJECTS_DESC,value = ATTACH_SUBJECTS_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = ATTACH_SUBJECTS_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 753,message = ENTITIES_FROM_WRONG_HIERARCHY)})
	public Response attachSubjectsToActor(@ApiParam(required = true,
	        value = ATTACH_HIERARCHY_PAYLOAD) AttachHierarchyDTO hierarchy) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService.attach(hierarchy));
		return responseBuilder.build();
	}

	@POST
	@Path("attach/actors")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ATTACH_ACTORS_DESC,value = ATTACH_ACTORS_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = ATTACH_ACTORS_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 753,message = ENTITIES_FROM_WRONG_HIERARCHY)})
	public Response attachActorsToASubject(@ApiParam(required = true,
	        value = ATTACH_HIERARCHY_PAYLOAD) AttachHierarchyDTO hierarchy) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .attachActorsToASubject(hierarchy));
		return responseBuilder.build();
	}

	@POST
	@Path("subjects/entities")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_ALL_ENTITIES_SUBJECTS_DESC,
	        value = GET_ALL_ENTITIES_SUBJECTS_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_ALL_ENTITIES_SUBJECTS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 753,message = ENTITIES_FROM_WRONG_HIERARCHY)})
	public Response getAllEntitySubjects(
	        @ApiParam(required = true,value = "") AttachHierarchySearchDTO attachHierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getAllEntitySubjects(attachHierarchySearch));
		return responseBuilder.build();
	}

	@POST
	@Path("subjects/templates")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_ALL_TEMPLATES_SUBJECTS_DESC,
	        value = GET_ALL_TEMPLATES_SUBJECTS_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_ALL_TEMPLATES_SUBJECTS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 753,message = ENTITIES_FROM_WRONG_HIERARCHY)})
	public Response getAllTempalteSubjects(
	        @ApiParam(required = true,value = "") AttachHierarchySearchDTO attachHierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getAllTemplateSubjects(attachHierarchySearch));
		return responseBuilder.build();
	}

	@POST
	@Path("roots/immediate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_IMMEDIATE_ROOTS_DESC,
	        value = GET_IMMEDIATE_ROOTS_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = GET_IMMEDIATE_ROOTS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 753,message = ENTITIES_FROM_WRONG_HIERARCHY)})
	public Response getImmediateRoot(
	        @ApiParam(required = true,value = "") AttachHierarchySearchDTO attachHierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getImmediateRoot(attachHierarchySearch));
		return responseBuilder.build();
	}

	@POST
	@Path("range/tags")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = TAG_ENITIES_BY_RANGE_DESC,
	        value = TAG_ENITIES_BY_RANGE_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = TAG_ENITIES_BY_RANGE_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 753,message = ENTITIES_FROM_WRONG_HIERARCHY)})
	public Response getTagsByRange(TagRangePayload payload) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService.getTagsByRange(payload));
		return responseBuilder.build();
	}

	@POST
	@Path("geotags")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_NODE_DEC,value = UPDATE_NODE_SUMMARY,
	        hidden = true)
	public Response getGeotags(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchySearchDTO hierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getGeotaggedEntities(hierarchySearch));
		return responseBuilder.build();
	}

	@POST
	@Path("attached/templates")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
	        notes = GET_TEMPLATE_NAMES_OF_ATTACHED_ENTITIES_OF_DOMAIN_DESC,
	        value = GET_TEMPLATE_NAMES_OF_ATTACHED_ENTITIES_OF_DOMAIN_SUMMARY,
	        response = String.class,responseContainer = "List")
	@ApiResponses(
	        value = {
	                @ApiResponse(
	                        code = HttpStatus.SC_OK,
	                        message = GET_TEMPLATE_NAMES_OF_ATTACHED_ENTITIES_OF_DOMAIN_SUCCESS_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 500,
	                        message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getTemplateNamesofAttachedEntities(
	        @ApiParam(required = true,value = HIERARCHY_PAYLOAD) HierarchySearchDTO hierarchySearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getTemplateNamesofAttachedEntities(hierarchySearch));
		return responseBuilder.build();
	}

	@PUT
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_NODE_DEC,value = UPDATE_NODE_SUMMARY,
	        response = StatusMessageDTO.class,hidden = true)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,message = UPDATE_NODE_RESP),
	        @ApiResponse(code = 753,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response updateStatus(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) EntityDTO entity) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService.updateStatus(entity));
		return responseBuilder.build();
	}

	@PUT
	@Path("/batch/status/{statusName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_BATCH_STATUS_NODE_DEC,
	        value = UPDATE_BATCH_STATUS_SUMMARY,
	        response = GeneralBatchResponse.class,hidden = true)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = UPDATE_BATCH_STATUS_RESP),
	        @ApiResponse(code = 753,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response updateBatchStatus(
	        @ApiParam(required = true,value = HIERARCHY_PAYLOAD) List<com.pcs.alpine.services.dto.IdentityDTO> entities,
	        @PathParam("statusName") String statusName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService.updateStatus(entities,
		        statusName));
		return responseBuilder.build();
	}

	@POST
	@Path("search/relation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_IMMEDIATE_CHILDREN_DESC,
	        value = GET_IMMEDIATE_CHILDREN_SUMMARY,response = EntityDTO.class,
	        responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_IMMEDIATE_CHILDREN__SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response searchHierarchyRelation(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchyRelationDTO hierarchyRelation) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .searchHierarchyRelation(hierarchyRelation));
		return responseBuilder.build();
	}

	@POST
	@Path("/entities")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = SUBJECT__FILTERED_ENTITIES_DESC,
	        value = SUBJECT__FILTERED_ENTITIES_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = SUBJECT__FILTERED_ENTITIES_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 753,message = ENTITIES_FROM_WRONG_HIERARCHY)})
	public Response getEntitiesByTags(
	        EntitiesByTagsPayload entitiesByTagsPayload) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getEntitiesByTags(entitiesByTagsPayload));
		return responseBuilder.build();
	}

	@POST
	@Path("/attach/markers/multiple")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ATTACH_MARKERS_MULTIPLE_PARENTS_DESC,
	        value = ATTACH_MARKERS_MULTIPLE_PARENTS_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = ATTACH_MARKERS_MULTIPLE_PARENTS_SUCCESS_RESP),
	        @ApiResponse(code = 753,message = TENANT_LINK_EXCEPTION),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response attachParentsToMultipleNodes(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) HierarchyDTO hierarchy) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .attachParentsToMultipleNodes(hierarchy));
		return responseBuilder.build();
	}

	@POST
	@Path("range/tenants/markers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_MARKERS_WITHIN_TENANTS_DESC,
	        value = GET_MARKERS_WITHIN_TENANTS_SUMMARY,
	        response = EntityDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_MARKERS_WITHIN_TENANTS_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getMarkersWithinRange(@ApiParam(required = true,
	        value = HIERARCHY_PAYLOAD) EntityRangeDTO entityRangeDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getEntitiesWithinTenantRange(entityRangeDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("attached/entity/count")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_TAGGED_ENTITIES_COUNT_DESC,
	        value = GET_TAGGED_ENTITIES_COUNT_SUMMARY,
	        response = EntityDTO.class,responseContainer = "List",hidden = true)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = GET_TAGGED_ENTITIES_COUNT_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getTaggedEntityCount(
	        @ApiParam(required = true,value = HIERARCHY_PAYLOAD) HierarchyTagSearchDTO hierarchyTagSearchDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(hierarchyService
		        .getTaggedEntityCount(hierarchyTagSearchDTO));
		return responseBuilder.build();
	}
}
