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
package com.pcs.guava.resources;

import static com.pcs.guava.constants.PoiTypeResourceConstants.CREATE_LIST_OF_POI_ENTITIES_DESC;
import static com.pcs.guava.constants.PoiTypeResourceConstants.CREATE_LIST_OF_POI_ENTITIES_SUMMARY;
import static com.pcs.guava.constants.PoiTypeResourceConstants.DELETE_POI_SUMMARY;
import static com.pcs.guava.constants.PoiTypeResourceConstants.DELETE_POI_DESC;
import static com.pcs.guava.constants.PoiTypeResourceConstants.DOMAIN;
import static com.pcs.guava.constants.PoiTypeResourceConstants.DOMAIN_SAMPLE;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_ALL_POI_DESC;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_ALL_POI_SUCC_RESP;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_ALL_POI_SUMMARY;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_POI_BY_POI_TYPE_DESC;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_POI_BY_POI_TYPE_SUCC_RESP;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_POI_BY_POI_TYPE_SUMMARY;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_POI_DESC;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_POI_SUCC_RESP;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_POI_SUMMARY;
import static com.pcs.guava.constants.PoiTypeResourceConstants.INSERT_POI_DESC;
import static com.pcs.guava.constants.PoiTypeResourceConstants.INSERT_POI_SUMMARY;
import static com.pcs.guava.constants.PoiTypeResourceConstants.POI_TYPE;
import static com.pcs.guava.constants.PoiTypeResourceConstants.POI_TYPE_SAMPLE;
import static com.pcs.guava.constants.PoiTypeResourceConstants.UPDATE_POI_DESC;
import static com.pcs.guava.constants.PoiTypeResourceConstants.UPDATE_POI_SUMMARY;
import static com.pcs.guava.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.guava.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.guava.constants.ResourceConstants.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.guava.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.guava.constants.ResourceConstants.POI_NAME;
import static com.pcs.guava.constants.ResourceConstants.POI_PAYLOAD;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;
import static org.apache.http.HttpStatus.SC_OK;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.IdentityDTO;
import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.dto.PoiDTO;
import com.pcs.guava.services.PoiService;

/**
 * Poi Resource Methods
 * 
 * @author Greeshma (PCSEG323)
 * @date April 2016
 * @since Guava-1.0.0
 */
@Path("/poi")
@Component
@Api("POI")
public class PoiResource {

	@Autowired
	private PoiService poiService;

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = INSERT_POI_SUMMARY, notes = INSERT_POI_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE) })
	public Response createPoi(
			@ApiParam(value = POI_PAYLOAD, required = true) PoiDTO poiDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(poiService.createPoi(poiDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("/createBatch")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CREATE_LIST_OF_POI_ENTITIES_SUMMARY, notes = CREATE_LIST_OF_POI_ENTITIES_DESC, response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE) })
	public Response createPois(
			@ApiParam(value = POI_PAYLOAD, required = true) List<PoiDTO> poiDTOs) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(poiService.createPois(poiDTOs));
		return responseBuilder.build();
	}

	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_POI_SUMMARY, notes = UPDATE_POI_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE) })
	public Response updatePoi(
			@ApiParam(value = POI_PAYLOAD, required = true) PoiDTO poiDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(poiService.updatePoi1(poiDTO));
		return responseBuilder.build();
	}

	// @PUT
	// @Path("/{poi_name}")
	// @Consumes(APPLICATION_JSON)
	// @Produces(APPLICATION_JSON)
	// @ApiOperation(value = UPDATE_POI_SUMMARY, notes = UPDATE_POI_DESC,
	// response = StatusMessageDTO.class)
	// @ApiResponses(value = {
	// @ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
	// @ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
	// @ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
	// @ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE) })
	// public Response updatePoi(
	// @ApiParam(value = POI_NAME, required = true) @PathParam(value =
	// "poi_name") String poiName,
	// @ApiParam(value = POI_PAYLOAD, required = true) PoiDTO poiDTO) {
	// ResponseBuilder responseBuilder = status(Response.Status.OK);
	// responseBuilder.entity(poiService.updatePoi(poiName, poiDTO));
	// return responseBuilder.build();
	// }

	@GET
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_POI_SUMMARY, notes = GET_ALL_POI_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_POI_SUCC_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED) })
	public Response getAllPois(
			@ApiParam(value = DOMAIN, required = true, defaultValue = DOMAIN_SAMPLE, example = DOMAIN_SAMPLE) @QueryParam("domain_name") String domain,
			@ApiParam(value = POI_TYPE, required = true, defaultValue = POI_TYPE_SAMPLE, example = POI_TYPE_SAMPLE) @QueryParam("poi_type") String poiType) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(poiService.getAllPois(domain, poiType));
		return responseBuilder.build();
	}

	@POST
	@Path("/find")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_POI_SUMMARY, notes = GET_POI_DESC, response = EntityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_POI_SUCC_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID) })
	public Response getPoiDetails(
			@ApiParam(value = POI_PAYLOAD, required = true) IdentityDTO poi) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(poiService.getPoiDetails1(poi));
		return responseBuilder.build();
	}
	
//	@POST
//	@Path("/find")
//	@Consumes(APPLICATION_JSON)
//	@Produces(APPLICATION_JSON)
//	@ApiOperation(value = GET_POI_SUMMARY, notes = GET_POI_DESC, response = EntityDTO.class)
//	@ApiResponses(value = {
//			@ApiResponse(code = SC_OK, message = GET_POI_SUCC_RESP),
//			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
//			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID) })
//	public Response getPoiDetails(
//			@ApiParam(value = POI_PAYLOAD, required = true) IdentityDTO poi) {
//		ResponseBuilder responseBuilder = status(Response.Status.OK);
//		responseBuilder.entity(poiService.getPoiDetails(poi));
//		return responseBuilder.build();
//	}

	@POST
	@Path("search/list")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_POI_BY_POI_TYPE_SUMMARY, notes = GET_POI_BY_POI_TYPE_DESC, response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_POI_BY_POI_TYPE_SUCC_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED) })
	public Response getPois(
			@ApiParam(value = DOMAIN, required = true, defaultValue = DOMAIN_SAMPLE, example = DOMAIN_SAMPLE) @QueryParam("domain_name") String domain,
			@ApiParam(value = POI_PAYLOAD, required = true) List<String> poiTypes) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(poiService.getPois(poiTypes, domain));
		return responseBuilder.build();
	}

	/**
	 * Responsible to delete a poi
	 * 
	 * @param geofence
	 * @return Success message
	 */
	@POST
	@Path("delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_POI_DESC, value = DELETE_POI_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED) })
	public Response deletePoi(
			@ApiParam(required = true, value = POI_PAYLOAD) IdentityDTO poi) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(poiService.deletePoi(poi));
		return responseBuilder.build();
	}

}
