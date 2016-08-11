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
package com.pcs.datasource.resource;

import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_DEVICE_DATA_AGGREGATED_DATA_DESC;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_DEVICE_DATA_AGGREGATED_DATA_RESP;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_DEVICE_DATA_AGGREGATED_DATA_SUMMARY;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_DEVICE_DATA_DESC;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_DEVICE_DATA_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_DEVICE_DATA_SUMMARY;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_LATEST_DATA_DESC;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_LATEST_DATA_DEVICES_DESC;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_LATEST_DATA_DEVICES_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_LATEST_DATA_DEVICES_SUMMARY;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_LATEST_DATA_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DataResourceConstants.GET_LATEST_DATA_SUMMARY;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_LATEST_DATA_SEARCH_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_SOURCE_ID;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_SOURCE_ID_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.datasource.doc.constants.ResourceConstants.SEARCH_PAYLOAD;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.DeviceMessageResponse;
import com.pcs.datasource.dto.LatestDataResultDTO;
import com.pcs.datasource.dto.LatestDataSearchDTO;
import com.pcs.datasource.dto.LatestDeviceData;
import com.pcs.datasource.dto.SearchDTO;
import com.pcs.datasource.model.Data;
import com.pcs.datasource.services.DataService;

/**
 * This resource class is responsible for defining all the services related to
 * data of a Device communicating to the system. This class is responsible for
 * persisting (single persist , batch persist) and fetching the device data
 * details from the communicating devices.
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */
@Path("/data")
@Component
@Api("Device Data")
public class DataResource {

	@Autowired
	private DataService dataService;

	@Path("/find")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICE_DATA_SUMMARY,notes = GET_DEVICE_DATA_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_DEVICE_DATA_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getDeviceData(@ApiParam(value = SEARCH_PAYLOAD,
	        required = true) SearchDTO searchDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		DeviceMessageResponse deviceMessageResponse = dataService
		        .getDeviceData(searchDTO);
		responseBuilder.entity(deviceMessageResponse);
		return responseBuilder.build();
	}

	@Path("/findAndAggregate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICE_DATA_AGGREGATED_DATA_SUMMARY,
	        notes = GET_DEVICE_DATA_AGGREGATED_DATA_DESC)
	@ApiResponses(value = {
	@ApiResponse(code = SC_OK,message = GET_DEVICE_DATA_AGGREGATED_DATA_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getDeviceAndAggregatedData(@ApiParam(
	        value = SEARCH_PAYLOAD,required = true) SearchDTO searchDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(dataService
		        .getDeviceAndAggregatedData(searchDTO));
		return responseBuilder.build();
	}

	@GET
	@Path("/{source_id}/latest")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_LATEST_DATA_SUMMARY,notes = GET_LATEST_DATA_DESC,
	        response = Data.class,responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_LATEST_DATA_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getDeviceLatestData(
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<LatestDeviceData> deviceLatestData = dataService.getDeviceLatestData(sourceId);
		GenericEntity<List<LatestDeviceData>> entity = new GenericEntity<List<LatestDeviceData>>(
		        deviceLatestData) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@POST
	@Path("/latest")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_LATEST_DATA_DEVICES_SUMMARY,
	        notes = GET_LATEST_DATA_DEVICES_DESC,response = Data.class,
	        responseContainer = "List")
	@ApiResponses(value = {
	@ApiResponse(code = SC_OK,message = GET_LATEST_DATA_DEVICES_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getDevicesLatestData(

	        @ApiParam(value = DEVICE_LATEST_DATA_SEARCH_PAYLOAD,required = true) List<LatestDataSearchDTO> LatestDataSearch) {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<LatestDataResultDTO> deviceLatestData = dataService
		        .getDevicesLatestData(LatestDataSearch);
		GenericEntity<List<LatestDataResultDTO>> entity = new GenericEntity<List<LatestDataResultDTO>>(
		        deviceLatestData) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}
}
