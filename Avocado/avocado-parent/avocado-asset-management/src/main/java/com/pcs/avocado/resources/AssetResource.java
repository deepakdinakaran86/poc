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

import static com.pcs.avocado.constans.AssetResourceConstants.ASSET_TYPE;
import static com.pcs.avocado.constans.AssetResourceConstants.ASSET_TYPE_SAMPLE;
import static com.pcs.avocado.constans.AssetResourceConstants.DOMAIN;
import static com.pcs.avocado.constans.AssetResourceConstants.DOMAIN_SAMPLE;
import static com.pcs.avocado.constans.AssetResourceConstants.GET_ALL_ASSETS_SUCCESS_RESP;
import static com.pcs.avocado.constans.AssetResourceConstants.GET_ALL_ASSET_DESC;
import static com.pcs.avocado.constans.AssetResourceConstants.GET_ALL_ASSET_SUMMARY;
import static com.pcs.avocado.constans.AssetResourceConstants.GET_ASSET_LATEST_DATA_SUCC_RESP;
import static com.pcs.avocado.constans.AssetResourceConstants.INSERT_ASSET_DESC;
import static com.pcs.avocado.constans.AssetResourceConstants.INSERT_ASSET_RESP;
import static com.pcs.avocado.constans.AssetResourceConstants.INSERT_ASSET_SUMMARY;
import static com.pcs.avocado.constans.AssetResourceConstants.UPDATE_ASSET_DESC;
import static com.pcs.avocado.constans.AssetResourceConstants.UPDATE_ASSET_SUMMARY;
import static com.pcs.avocado.constans.ResourceConstants.ASSET_PAYLOAD;
import static com.pcs.avocado.constans.ResourceConstants.DEVICE_PAYLOAD;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_SUCCESS_RESP;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.AssetDTO;
import com.pcs.avocado.commons.dto.Device;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.PointRelationship;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.constans.AssetResourceConstants;
import com.pcs.avocado.constans.WebConstants;
import com.pcs.avocado.services.AssetService;

/**
 * Assets Resource Methods
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date March 2016
 * @since Avocado-1.0.0
 */

@Path("/assets")
@Component
@Api("Assets")
public class AssetResource {

	@Autowired
	private AssetService assetService;

	@GET
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_ASSET_SUMMARY,notes = GET_ALL_ASSET_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_ALL_ASSETS_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getAllAssets(
	        @ApiParam(value = DOMAIN,required = true,
	                defaultValue = DOMAIN_SAMPLE,example = DOMAIN_SAMPLE) @QueryParam("domain_name") String domain,
	        @ApiParam(value = ASSET_TYPE,required = true,
	                defaultValue = ASSET_TYPE_SAMPLE,
	                example = ASSET_TYPE_SAMPLE) @QueryParam("asset_type") String assetType) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetService.getAllAssets(domain, assetType));
		return responseBuilder.build();
	}

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = INSERT_ASSET_SUMMARY,notes = INSERT_ASSET_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = INSERT_ASSET_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createAsset(
	        @ApiParam(value = ASSET_PAYLOAD,required = true) AssetDTO assetDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetService.createAsset(assetDTO));
		return responseBuilder.build();
	}

	@PUT
	@Path("/")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_ASSET_SUMMARY,notes = UPDATE_ASSET_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response updateAsset(
	        @ApiParam(value = ASSET_PAYLOAD,required = true) AssetDTO assetDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetService.updateAsset(assetDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("/points")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(
	        value = AssetResourceConstants.CREATE_POINTS_WITH_RELATIONSHIP_SUMMARY,
	        notes = AssetResourceConstants.CREATE_POINTS_WITH_RELATIONSHIP_DESC,
	        response = EntityDTO.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(
	                        code = SC_OK,
	                        message = AssetResourceConstants.CREATE_POINTS_WITH_RELATIONSHIP_SUCC_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response createPoints(@ApiParam(value = ASSET_PAYLOAD,
	        required = true) PointRelationship pointRelationship) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetService
		        .createSelectedPointsAndRelationship(pointRelationship));
		return responseBuilder.build();
	}

	@POST
	@Path("/find")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = AssetResourceConstants.GET_ASSETS_SUMMARY,
	        notes = AssetResourceConstants.GET_ASSETS_DESC,
	        response = EntityDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,
	                message = AssetResourceConstants.GET_ASSETS_SUCC_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response getAsset(
	        @ApiParam(value = ASSET_PAYLOAD,required = true) IdentityDTO asset) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetService.getAssetDetails(asset));
		return responseBuilder.build();
	}

	// @POST
	// @Path("/device/points")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = AssetResourceConstants.GET_POINTS_OF_DEVICE_SUMMARY,
	        notes = AssetResourceConstants.GET_POINTS_OF_DEVICE_DESC,
	        response = EntityDTO.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(
	                        code = SC_OK,
	                        message = AssetResourceConstants.GET_POINTS_OF_DEVICE_SUCC_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response getDeviceDetails(@ApiParam(value = DEVICE_PAYLOAD,
	        required = true) Device device) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetService.getPointsOfADevice(device));
		return responseBuilder.build();
	}

	@POST
	@Path("/device/points")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = AssetResourceConstants.GET_POINTS_OF_DEVICE_SUMMARY,
	        notes = AssetResourceConstants.GET_POINTS_OF_DEVICE_DESC,
	        response = EntityDTO.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(
	                        code = SC_OK,
	                        message = AssetResourceConstants.GET_POINTS_OF_DEVICE_SUCC_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response getDeviceDetailsTemp(@ApiParam(value = DEVICE_PAYLOAD,
	        required = true) Device device) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetService.getPointsOfADeviceTemp(device));
		return responseBuilder.build();
	}

	@POST
	@Path("/data/latest")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = AssetResourceConstants.GET_ASSET_LATEST_DATA_SUMMARY,
	        notes = AssetResourceConstants.GET_ASSET_LATEST_DATA_DESC,
	        response = EntityDTO.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = GET_ASSET_LATEST_DATA_SUCC_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response getAssetLatestData(@ApiParam(value = ASSET_PAYLOAD,
	        required = true) IdentityDTO asset) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<EntityDTO> deviceLatestData = assetService
		        .getAssetLatestData(asset);
		GenericEntity<List<EntityDTO>> entity = new GenericEntity<List<EntityDTO>>(
		        deviceLatestData) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@GET
	@Path("/alarms/{asset_name}/{domain_name}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = AssetResourceConstants.GET_LIVE_ALARMS_SUMMARY,
	        notes = AssetResourceConstants.GET_LIVE_ALARMS_DESC,
	        response = EntityDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = SC_OK,
		        message = AssetResourceConstants.GET_LIVE_ALARMS_SUCC_RESP)})
	public Response getLiveAlarms(
	        @ApiParam(value = WebConstants.DOMAIN,required = false) @PathParam("domain_name") String domainName,
	        @ApiParam(value = WebConstants.ASSET_NAME,required = true) @PathParam("asset_name") String assetName) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetService
		        .getLiveAlarms(domainName, assetName));
		return responseBuilder.build();
	}

}
