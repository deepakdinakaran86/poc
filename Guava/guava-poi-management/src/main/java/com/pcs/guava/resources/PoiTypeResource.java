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

import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_ALL_POI_TYPE_DESC;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_ALL_POI_TYPE_SUCC_RESP;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_ALL_POI_TYPE_SUMMARY;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_POI_TYPE_DESC;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_POI_TYPE_SUCC_RESP;
import static com.pcs.guava.constants.PoiTypeResourceConstants.GET_POI_TYPE_SUMMARY;
import static com.pcs.guava.constants.PoiTypeResourceConstants.INSERT_POI_TYPE_DESC;
import static com.pcs.guava.constants.PoiTypeResourceConstants.INSERT_POI_TYPE_SUMMARY;
import static com.pcs.guava.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.guava.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.guava.constants.ResourceConstants.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.guava.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.guava.constants.ResourceConstants.POI_TYPE_PAYLOAD;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.dto.PoiTypeDTO;
import com.pcs.guava.services.PoiTypeService;

/**
 * PoiType Resource Methods
 * 
 * @author Greeshma (PCSEG323)
 * @date April 2016
 * @since Avocado-1.0.0
 */

@Path("/poiType")
@Component
@Api("POI Types")
public class PoiTypeResource {

	@Autowired
	private PoiTypeService poiTypeService;

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = INSERT_POI_TYPE_SUMMARY, notes = INSERT_POI_TYPE_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE) })
	public Response createPoiType(
			@ApiParam(value = POI_TYPE_PAYLOAD, required = true) PoiTypeDTO poiTypeDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(poiTypeService.createPoiType(poiTypeDTO));
		return responseBuilder.build();
	}

//	@POST
//	@Path("/rootTemplate")
//	@Consumes(APPLICATION_JSON)
//	@Produces(APPLICATION_JSON)
//	@ApiOperation(value = INSERT_POI_TYPE_SUMMARY, notes = INSERT_POI_TYPE_DESC, response = StatusMessageDTO.class)
//	@ApiResponses(value = {
//			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
//			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
//			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
//			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE) })
//	public Response createRootPoiType(
//			@ApiParam(value = POI_TYPE_PAYLOAD, required = false) PoiTypeDTO poiTypeDTO) {
//		ResponseBuilder responseBuilder = status(Response.Status.OK);
//		responseBuilder.entity(poiTypeService.createRootPoiType(poiTypeDTO));
//		return responseBuilder.build();
//	}

	@GET
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_POI_TYPE_SUMMARY, notes = GET_ALL_POI_TYPE_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_POI_TYPE_SUCC_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE) })
	public Response getAllAssetType(@QueryParam("domain_name") String domainName) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(poiTypeService.getAllPoiType(domainName));
		return responseBuilder.build();
	}

	@GET
	@Path("{poi_type_name}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_POI_TYPE_SUMMARY, notes = GET_POI_TYPE_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_POI_TYPE_SUCC_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE) })
	public Response getAssetType(
			@ApiParam(value = POI_TYPE_PAYLOAD, required = true) @PathParam("poi_type_name") String poiType,
			@QueryParam("domain_name") String domainName) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(poiTypeService.getPoiType(poiType, domainName));
		return responseBuilder.build();
	}

}
