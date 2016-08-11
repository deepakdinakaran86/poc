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

import static com.pcs.avocado.constans.GensetResourceConstants.CREATE_POINTS_WITH_RELATIONSHIP_SUCC_RESP;
import static com.pcs.avocado.constans.GensetResourceConstants.GET_DETAILS_OF_AN_EQUIPMENT_SUCC_RESP;
import static com.pcs.avocado.constans.GensetResourceConstants.GET_GENSET_LATEST_DATA_SUCC_RESP;
import static com.pcs.avocado.constans.GensetResourceConstants.GET_POINTS_OF_DEVICE_SUCC_RESP;
import static com.pcs.avocado.constans.GensetResourceConstants.INSERT_GENSET_DESC;
import static com.pcs.avocado.constans.GensetResourceConstants.INSERT_GENSET_SUMMARY;
import static com.pcs.avocado.constans.GensetResourceConstants.UPDATE_GENSET_DESC;
import static com.pcs.avocado.constans.GensetResourceConstants.UPDATE_GENSET_SUMMARY;
import static com.pcs.avocado.constans.ResourceConstants.DEVICE_PAYLOAD;
import static com.pcs.avocado.constans.ResourceConstants.EQUIPMENT_IDENTITY;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.avocado.constans.ResourceConstants.GENSET_PAYLOAD;
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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.Device;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.EquipmentDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.LatestDataResultDTO;
import com.pcs.avocado.commons.dto.PointRelationship;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.constans.GensetResourceConstants;
import com.pcs.avocado.constans.ResourceConstants;
import com.pcs.avocado.services.EquipmentService;

/**
 * Genset Resource Methods
 * 
 * @author pcseg199 (Javid Ahammed)
 * @date January 2016
 * @since Avocado-1.0.0
 */

@Path("/gensets")
@Component
@Api("Gensets")
public class GensetResource {

	@Autowired
	EquipmentService equipmentService;

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = INSERT_GENSET_SUMMARY,notes = INSERT_GENSET_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response insertDevice(@ApiParam(value = GENSET_PAYLOAD,
	        required = true) EquipmentDTO equipmentDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(equipmentService.createEquipment(equipmentDTO));
		return responseBuilder.build();
	}

	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_GENSET_SUMMARY,notes = UPDATE_GENSET_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,
	                message = ResourceConstants.GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response UpdateDevice(@ApiParam(value = GENSET_PAYLOAD,
	        required = true) EquipmentDTO equipmentDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(equipmentService.updateEquipment(equipmentDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("/device/points")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GensetResourceConstants.GET_POINTS_OF_DEVICE_SUMMARY,
	        notes = GensetResourceConstants.GET_POINTS_OF_DEVICE_DESC,
	        response = EntityDTO.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = GET_POINTS_OF_DEVICE_SUCC_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response getDeviceDetails(@ApiParam(value = DEVICE_PAYLOAD,
	        required = true) Device device) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(equipmentService.getPointsOfADevice(device));
		return responseBuilder.build();
	}

	@POST
	@Path("/find")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(
	        value = GensetResourceConstants.GET_DETAILS_OF_AN_EQUIPMENT_SUMMARY,
	        notes = GensetResourceConstants.GET_DETAILS_OF_AN_EQUIPMENT_DESC,
	        response = EntityDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = SC_OK,message = GET_DETAILS_OF_AN_EQUIPMENT_SUCC_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response getEquipmentDetails(@ApiParam(value = EQUIPMENT_IDENTITY,
	        required = true) IdentityDTO identityDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(equipmentService
		        .getEquipmentDetails(identityDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("/points")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(
	        value = GensetResourceConstants.CREATE_POINTS_WITH_RELATIONSHIP_SUMMARY,
	        notes = GensetResourceConstants.CREATE_POINTS_WITH_RELATIONSHIP_DESC,
	        response = EntityDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,
	                message = CREATE_POINTS_WITH_RELATIONSHIP_SUCC_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response createPoints(@ApiParam(value = DEVICE_PAYLOAD,
	        required = true) PointRelationship pointRelationship) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(equipmentService
		        .createPointsAndRelationship(pointRelationship));
		return responseBuilder.build();
	}

	@POST
	@Path("/data/latest")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(
	        value = GensetResourceConstants.GET_GENSET_LATEST_DATA_SUMMARY,
	        notes = GensetResourceConstants.GET_GENSET_LATEST_DATA_DESC,
	        response = EntityDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = SC_OK,message = GET_GENSET_LATEST_DATA_SUCC_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response getGensetLatestData(@ApiParam(value = EQUIPMENT_IDENTITY,
	        required = true) IdentityDTO identityDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<LatestDataResultDTO> deviceLatestData = equipmentService
		        .getGensetLatestData(identityDTO);
		GenericEntity<List<LatestDataResultDTO>> entity = new GenericEntity<List<LatestDataResultDTO>>(
		        deviceLatestData) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}
}
