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

import static com.pcs.avocado.constans.AssetResourceConstants.GET_ALL_ASSET_TYPE_DESC;
import static com.pcs.avocado.constans.AssetResourceConstants.GET_ALL_ASSET_TYPE_SUCC_RESP;
import static com.pcs.avocado.constans.AssetResourceConstants.GET_ALL_ASSET_TYPE_SUMMARY;
import static com.pcs.avocado.constans.AssetResourceConstants.GET_ASSET_TYPE_DESC;
import static com.pcs.avocado.constans.AssetResourceConstants.GET_ASSET_TYPE_SUCC_RESP;
import static com.pcs.avocado.constans.AssetResourceConstants.GET_ASSET_TYPE_SUMMARY;
import static com.pcs.avocado.constans.ResourceConstants.ASSET_PAYLOAD;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.avocado.constans.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.avocado.constans.WebConstants.ASSET_TYPE_NAME;
import static com.pcs.avocado.constans.WebConstants.PARENT_ASSET_TYPE;
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

import com.pcs.avocado.commons.dto.AssetTypeTemplateDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.constans.AssetResourceConstants;
import com.pcs.avocado.services.AssetTypeTemplateService;

/**
 * AssetType Resource Methods
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date March 2016
 * @since Avocado-1.0.0
 */

@Path("/assetType")
@Component
@Api("AssetType")
public class AssetTypeTemplateResource {

	@Autowired
	AssetTypeTemplateService assetTypeTemplateService;

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = AssetResourceConstants.CREATE_ASSET_TYPE_SUMMARY,
	        notes = AssetResourceConstants.CREATE_ASSET_TYPE_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createAssetType(
	        @ApiParam(value = PARENT_ASSET_TYPE,required = true) @QueryParam("parent_type") String parentType,
	        @ApiParam(value = ASSET_PAYLOAD,required = true) AssetTypeTemplateDTO assetTypeTemplateDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetTypeTemplateService.createAssetType(
		        parentType, assetTypeTemplateDTO));
		return responseBuilder.build();
	}

	@GET
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_ASSET_TYPE_SUMMARY,
	        notes = GET_ALL_ASSET_TYPE_DESC,response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_ALL_ASSET_TYPE_SUCC_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getAllAssetType(
	        @QueryParam("domain_name") String domainName,
	        @ApiParam(value = PARENT_ASSET_TYPE,required = true) @QueryParam("parent_type") String parentType) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetTypeTemplateService.getAllAssetType(
		        domainName, parentType));
		return responseBuilder.build();
	}

	@GET
	@Path("/{asset_type_name}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ASSET_TYPE_SUMMARY,notes = GET_ASSET_TYPE_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_ASSET_TYPE_SUCC_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response getAssetType(
	        @ApiParam(value = ASSET_TYPE_NAME,required = true) @PathParam("asset_type_name") String assetTypeName,
	        @QueryParam("domain_name") String domainName,
	        @ApiParam(value = PARENT_ASSET_TYPE,required = true) @QueryParam("parent_type") String parentType) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(assetTypeTemplateService.getAssetType(
		        assetTypeName, domainName, parentType));
		return responseBuilder.build();
	}

}
