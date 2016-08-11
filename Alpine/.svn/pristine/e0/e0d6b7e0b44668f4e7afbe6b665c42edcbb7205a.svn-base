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

import static com.pcs.alpine.constants.ApiDocConstant.CREATE_MARKERS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_MARKERS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_MARKERS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_MARKER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_MARKER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_MARKER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_MARKER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_MARKER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_MARKER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIELD_ALREADY_EXIST;
import static com.pcs.alpine.constants.ApiDocConstant.FILTER_MARKER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FILTER_MARKER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FILTER_MARKER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_MARKERS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_MARKERS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_MARKERS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_MARKER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_MARKER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_MARKER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_MARKER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_MARKER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_MARKER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.GET_MARKER_COUNT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_MARKER_COUNT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_MARKER_COUNT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.IDENTITY_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.MARKER_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.SEARCH_FIELDS_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.SEARCH_MARKER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.SEARCH_MARKER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.SEARCH_MARKER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.SEARCH_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_MARKERS_STATUS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_MARKERS_STATUS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_MARKERS_STATUS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_MARKER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_MARKER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_MARKER_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.VALIDATE_MARKER_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.VALIDATE_MARKER_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.VALIDATE_MARKER_SUMMARY;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.dto.MarkerBatchDTO;
import com.pcs.alpine.services.MarkerService;
import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.ReturnFieldsDTO;
import com.pcs.alpine.services.dto.SearchFieldsDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;

/**
 * @description Rest Resource used to manage all the services related to custom
 *              entity
 * 
 * @author Bhupendra (pcseg329)
 * @date 14 Jan 2015
 */

@Path("/markers")
@Component
@Api("Marker")
public class MarkerResource {

	@Autowired
	private MarkerService markerEntityService;

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
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_MARKER_DESC,value = CREATE_MARKER_SUMMARY,
	        response = EntityDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = CREATE_MARKER_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 507,message = FIELD_ALREADY_EXIST)})
	public Response saveCustomEntity(@ApiParam(required = true,
	        value = MARKER_PAYLOAD) EntityDTO entityDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		EntityDTO markerEntity = markerEntityService.saveMarker(entityDTO);
		responseBuilder.entity(markerEntity);
		return responseBuilder.build();
	}

	/**
	 * @description Responsible to update existing custom entity
	 * @param markerDTO
	 * @return JSON response { "globalEntity": { "description":
	 *         "entity type is CUSTOM", "globalEntityType": "CUSTOM",
	 *         "isDefault": false }, "domain": {"domainName": "jll.pcs.com"},
	 *         "entityTemplate": { "domain": {"domainName": "jll.pcs.com"},
	 *         "entityTemplateName": "JLLcustomTemplate", "globalEntityType":
	 *         "CUSTOM" }, "identifier": { "key": "identifier", "value":
	 *         "custom entity 3aed7f1f2-8ea1-4cf3-b199-ec364772763d" } }
	 */
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_MARKER_DESC,value = UPDATE_MARKER_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = UPDATE_MARKER_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 507,message = FIELD_ALREADY_EXIST)})
	public Response updateCustomEntity(@ApiParam(required = true,
	        value = MARKER_PAYLOAD) EntityDTO markerDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = markerEntityService
		        .updateMarker(markerDTO);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	/**
	 * @description Responsible to get custom entity details based on unique
	 *              identifier
	 * @param IdentityDTO
	 * @return JSON response { "entityId":
	 *         "2b8f12c6-4d78-4797-b2ef-22f087b77a07", "globalEntity":
	 *         {"globalEntityType": "CUSTOM"}, "domain": {"domainName":
	 *         "jll.pcs.com"}, "entityStatus": { "statusKey": 0, "statusName":
	 *         "ACTIVE" }, "entityTemplate": { "domain": {"domainName":
	 *         "jll.pcs.com"}, "entityTemplateName": "JLLcustomTemplate",
	 *         "globalEntityType": "CUSTOM" }, "fieldValues": [ { "key":
	 *         "entityName", "value": "custom entity 3 name changed" }, { "key":
	 *         "identifier", "value":
	 *         "custom entity 3aed7f1f2-8ea1-4cf3-b199-ec364772763d" } ],
	 *         "dataprovider": [ { "key": "entityName", "value":
	 *         "custom entity 3" }], "identifier": { "key": "identifier",
	 *         "value": "custom entity 3aed7f1f2-8ea1-4cf3-b199-ec364772763d" },
	 *         "tree": { "key": "entityName", "value": "custom entity 3" } }
	 */

	@POST
	@Path("/find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_MARKER_DESC,value = FIND_MARKER_SUMMARY,
	        response = EntityDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = FIND_MARKER_SUCCESS_RESP),
	        @ApiResponse(code = 503,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response getEntityDetails(@ApiParam(required = true,
	        value = IDENTITY_PAYLOAD) IdentityDTO identityDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		EntityDTO entityDTO = markerEntityService.getMarker(identityDTO);
		responseBuilder.entity(entityDTO);
		return responseBuilder.build();
	}

	/**
	 * @description Responsible to get custom entity details based on domain and
	 *              uniqueUserId
	 * @param EntitySearchDTO
	 * @return JSON response of List<EntityDTO>:
	 *         [{"globalEntity":{"globalEntityType"
	 *         :"CUSTOM"},"domain":{"domainName"
	 *         :"jll.pcs.com"},"entityStatus":{"statusKey"
	 *         :0,"statusName":"ACTIVE"
	 *         },"entityTemplate":{"domain":{"domainName"
	 *         :"jll.pcs.com"},"entityTemplateName"
	 *         :"JLLcustomTemplate","globalEntityType"
	 *         :"CUSTOM"},"dataprovider":[
	 *         {"key":"entityName","value":"ORGANIZATION"
	 *         }],"identifier":{"key":"identifier"
	 *         ,"value":"ORGANIZATION167a3049-c96c-48b1-8ee0-3dff0a27caa6"
	 *         },"tree"
	 *         :{"key":"entityName","value":"ORGANIZATION"}},{"globalEntity"
	 *         :{"globalEntityType"
	 *         :"CUSTOM"},"domain":{"domainName":"jll.pcs.com"
	 *         },"entityStatus":{"statusKey"
	 *         :0,"statusName":"ACTIVE"},"entityTemplate"
	 *         :{"domain":{"domainName"
	 *         :"jll.pcs.com"},"entityTemplateName":"JLLcustomTemplate"
	 *         ,"globalEntityType"
	 *         :"CUSTOM"},"dataprovider":[{"key":"entityName",
	 *         "value":"USA"}],"identifier"
	 *         :{"key":"identifier","value":"USA"},"tree"
	 *         :{"key":"entityName","value"
	 *         :"USA"}},{"globalEntity":{"globalEntityType"
	 *         :"CUSTOM"},"domain":{"domainName"
	 *         :"jll.pcs.com"},"entityStatus":{"statusKey"
	 *         :0,"statusName":"ACTIVE"
	 *         },"entityTemplate":{"domain":{"domainName"
	 *         :"jll.pcs.com"},"entityTemplateName"
	 *         :"JLLcustomTemplate","globalEntityType"
	 *         :"CUSTOM"},"dataprovider":[
	 *         {"key":"entityName","value":"SA"}],"identifier"
	 *         :{"key":"identifier"
	 *         ,"value":"SA"},"tree":{"key":"entityName","value":"SA"}}]
	 */

	@Deprecated
	@POST
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_MARKER_DESC,value = FIND_ALL_MARKER_SUMMARY,
	        response = EntityDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = FIND_ALL_MARKER_SUCCESS_RESP),
	        @ApiResponse(code = 503,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getEntities(@ApiParam(required = true,
	        value = IDENTITY_PAYLOAD) IdentityDTO identityDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<EntityDTO> markers = markerEntityService
		        .getMarkersByDomain(identityDTO);
		responseBuilder.entity(markers);
		return responseBuilder.build();
	}

	/**
	 * @description Responsible to get custom entity details based unique
	 *              identifier fields
	 * @param IdentityDTO
	 * @return JSON response for StatusMessageDTO: {"status": "SUCCESS"}.
	 */

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_MARKER_DESC,value = DELETE_MARKER_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = DELETE_MARKER_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response deleteEntity(@ApiParam(required = true,
	        value = IDENTITY_PAYLOAD) IdentityDTO identityDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = markerEntityService
		        .deleteMarker(identityDTO);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("/list/filter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FILTER_MARKER_DESC,value = FILTER_MARKER_SUMMARY,
	        response = EntityDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = FILTER_MARKER_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 503,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getEntitiesByTemplate(@ApiParam(required = true,
	        value = IDENTITY_PAYLOAD) IdentityDTO identityDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<EntityDTO> entityDTO = markerEntityService.getMarkers(identityDTO);
		responseBuilder.entity(entityDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = VALIDATE_MARKER_DESC,value = VALIDATE_MARKER_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK,
		        message = VALIDATE_MARKER_SUCCESS_RESP)})
	public Response validateMarker(@ApiParam(required = true,
	        value = SEARCH_PAYLOAD) EntitySearchDTO searchDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = markerEntityService
		        .validateUniqueness(searchDTO);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("/fieldvalue/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = SEARCH_MARKER_DESC,value = SEARCH_MARKER_SUMMARY,
	        response = ReturnFieldsDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = SEARCH_MARKER_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 503,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response searchMarkers(@ApiParam(required = true,
	        value = SEARCH_FIELDS_PAYLOAD) SearchFieldsDTO searchFieldsDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<ReturnFieldsDTO> returnFieldsListDTO = markerEntityService
		        .searchFields(searchFieldsDTO);
		responseBuilder.entity(returnFieldsListDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("/count")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_MARKER_COUNT_DESC,
	        value = GET_MARKER_COUNT_SUMMARY,response = EntityCountDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK,
		        message = GET_MARKER_COUNT_SUCCESS_RESP)})
	public Response getMarkerCount(@ApiParam(required = true,
	        value = SEARCH_PAYLOAD) EntitySearchDTO entitySearchDto) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		EntityCountDTO entityCount = markerEntityService
		        .getMarkerCount(entitySearchDto);
		responseBuilder.entity(entityCount);
		return responseBuilder.build();
	}

	@POST
	@Path("/fieldvalue/search/count")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_MARKER_COUNT_DESC,
	        value = GET_MARKER_COUNT_SUMMARY,response = EntityCountDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK,
		        message = GET_MARKER_COUNT_SUCCESS_RESP)})
	public Response getMarkerSearchCount(@ApiParam(required = true,
	        value = SEARCH_PAYLOAD) SearchFieldsDTO searchFieldsDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		EntityCountDTO entityCount = markerEntityService
		        .getSearchFieldsCount(searchFieldsDTO);
		responseBuilder.entity(entityCount);
		return responseBuilder.build();
	}

	@POST
	@Path("/batch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_MARKERS_DESC,value = CREATE_MARKERS_SUMMARY,
	        response = EntityCountDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK,
		        message = CREATE_MARKERS_SUCCESS_RESP)})
	public Response saveMarkers(@ApiParam(required = true,
	        value = MARKER_PAYLOAD) MarkerBatchDTO entities) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<IdentityDTO> markers = markerEntityService.saveMarkers(entities);
		responseBuilder.entity(markers);
		return responseBuilder.build();
	}

	@POST
	@Path("/status/{statusName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_MARKERS_STATUS_DESC,
	        value = UPDATE_MARKERS_STATUS_SUMMARY,
	        response = EntityCountDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK,
		        message = UPDATE_MARKERS_STATUS_SUCCESS_RESP)})
	public Response updateMarkersStatus(@ApiParam(required = true,
	        value = SEARCH_PAYLOAD) List<IdentityDTO> entities,
	        @PathParam("statusName") String statusName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = markerEntityService
		        .updateMarkersStatus(entities, statusName);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("/findAll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_MARKERS_DESC,
	        value = FIND_ALL_MARKERS_SUMMARY,response = EntityCountDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK,
		        message = FIND_ALL_MARKERS_SUCCESS_RESP)})
	public Response getMarkers(
	        @ApiParam(required = true,value = SEARCH_PAYLOAD) List<IdentityDTO> entities) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<EntityDTO> markers = markerEntityService.getMarkers(entities);
		responseBuilder.entity(markers);
		return responseBuilder.build();
	}
}
