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

import static com.pcs.avocado.constans.DeviceResourceConstants.ASSIGN_BULK_DEVICES_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.ASSIGN_BULK_DEVICES_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.ASSIGN_DEVICES_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.ASSIGN_DEVICES_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.CLAIM_DEVICES_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.CLAIM_DEVICES_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.CLAIM_DEVICE_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.CLAIM_DEVICE_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_DEVICES_BY_DS_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_DEVICES_BY_DS_SUCCESS_RESP;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_DEVICES_BY_DS_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_DEVICE_DETAILS_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_DEVICE_DETAILS_SUCCESS_RESP;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_DEVICE_DETAILS_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_DEVICE_LOC_DETAILS_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_DEVICE_LOC_DETAILS_SUCCESS_RESP;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_DEVICE_LOC_DETAILS_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_OWNED_DEVICES_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_OWNED_DEVICES_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.GET_OWNED_DEVICES__SUCCESS_RESP;
import static com.pcs.avocado.constans.DeviceResourceConstants.INSERT_DEVICE_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.INSERT_DEVICE_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.LIST_ALARM_HISTORY_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.LIST_ALARM_HISTORY_RESP;
import static com.pcs.avocado.constans.DeviceResourceConstants.LIST_ALARM_HISTORY_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.LIST_DEVICES_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.LIST_DEVICES_SUCCESS_RESP;
import static com.pcs.avocado.constans.DeviceResourceConstants.LIST_DEVICES_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.LIST_LIVE_ALARMS_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.LIST_LIVE_ALARMS_RESP;
import static com.pcs.avocado.constans.DeviceResourceConstants.LIST_LIVE_ALARMS_SUMMARY;
import static com.pcs.avocado.constans.DeviceResourceConstants.UPDATE_DEVICE_DESC;
import static com.pcs.avocado.constans.DeviceResourceConstants.UPDATE_DEVICE_SUMMARY;
import static com.pcs.avocado.constans.ResourceConstants.DEVICE_CLAIM_DTO_PAYLOAD;
import static com.pcs.avocado.constans.ResourceConstants.DEVICE_IDENTIFIER;
import static com.pcs.avocado.constans.ResourceConstants.DEVICE_IDENTIFIER_SAMPLE;
import static com.pcs.avocado.constans.ResourceConstants.DEVICE_PAYLOAD;
import static com.pcs.avocado.constans.ResourceConstants.DEVICE_SOURCE_ID;
import static com.pcs.avocado.constans.ResourceConstants.DEVICE_SOURCE_ID_SAMPLE;
import static com.pcs.avocado.constans.ResourceConstants.DOMAIN;
import static com.pcs.avocado.constans.ResourceConstants.DOMAIN_SAMPLE;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.HierarchyDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.constans.ResourceConstants;
import com.pcs.avocado.constans.WebConstants;
import com.pcs.avocado.dto.ClaimDeviceDTO;
import com.pcs.avocado.dto.Device;
import com.pcs.avocado.dto.OwnerDeviceDTO;
import com.pcs.avocado.services.DeviceService;

/**
 * Device Resource Methods
 * 
 * @author pcseg199 (Javid Ahammed)
 * @date January 2016
 * @since Avocado-1.0.0
 */

@Path("/devices")
@Component
@Api("Devices")
public class DeviceResource {

	@Autowired
	DeviceService deviceService;

	@POST
	@Path("/")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = INSERT_DEVICE_SUMMARY,notes = INSERT_DEVICE_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createDevice(@ApiParam(value = DEVICE_PAYLOAD,
	        required = true) Device device) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.createDevice(device));
		return responseBuilder.build();
	}

	@PUT
	@Path("/{sourceId}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DEVICE_SUMMARY,notes = UPDATE_DEVICE_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response updateDevice(
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("sourceId") String sourceId,
	        @ApiParam(value = DEVICE_PAYLOAD,required = true) Device device) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.updateDevice(sourceId, device));
		return responseBuilder.build();
	}

	@GET
	@Path("/{sourceId}")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICE_DETAILS_SUMMARY,
	        notes = GET_DEVICE_DETAILS_DESC,response = EntityDTO.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = GET_DEVICE_DETAILS_SUCCESS_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response getDeviceDetails(
	        @ApiParam(value = DEVICE_IDENTIFIER,required = true,
	                defaultValue = DEVICE_IDENTIFIER_SAMPLE,
	                example = DEVICE_IDENTIFIER_SAMPLE) @PathParam("sourceId") String identifier) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.getDeviceDetails(identifier));
		return responseBuilder.build();
	}

	@PUT
	@Path("/{sourceId}/claim")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CLAIM_DEVICE_SUMMARY,notes = CLAIM_DEVICE_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response claimDevice(
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("sourceId") String sourceId,
	        @ApiParam(value = DEVICE_PAYLOAD,required = true) Device device) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.claimDevice(sourceId, device));
		return responseBuilder.build();
	}

	@PUT
	@Path("/claim")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CLAIM_DEVICES_SUMMARY,notes = CLAIM_DEVICES_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response claimDevices(@ApiParam(value = DEVICE_CLAIM_DTO_PAYLOAD,
	        required = true) ClaimDeviceDTO claimDevice) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.claimDevices(claimDevice));
		return responseBuilder.build();
	}

	@POST
	@Path("/assign")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = ASSIGN_DEVICES_SUMMARY,notes = ASSIGN_DEVICES_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response assignDevices(
	        @ApiParam(value = ResourceConstants.HIERARCHY_PAYLOAD,
	                required = true) HierarchyDTO hierarchyDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.assignDevice(hierarchyDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("/assignbulk")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = ASSIGN_BULK_DEVICES_SUMMARY,
	        notes = ASSIGN_BULK_DEVICES_DESC,response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response assignBulkDevices(@ApiParam(
	        value = ResourceConstants.ASSIGN_CLIENT_DEVICE_PAYLOAD,
	        required = true) OwnerDeviceDTO ownerDevice) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.assignBulkDevice(ownerDevice));
		return responseBuilder.build();
	}

	@POST
	@Path("/list")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = LIST_DEVICES_SUMMARY,notes = LIST_DEVICES_DESC,
	        response = List.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = LIST_DEVICES_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getAllDevices(
	        @ApiParam(value = ResourceConstants.TENANT_IDENTITY_PAYLOAD,
	                required = true) IdentityDTO identityDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.getAllDevices(identityDTO));
		return responseBuilder.build();
	}

	@GET
	@Path("/location")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICE_LOC_DETAILS_SUMMARY,
	        notes = GET_DEVICE_LOC_DETAILS_DESC,response = EntityDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = SC_OK,message = GET_DEVICE_LOC_DETAILS_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response getDevicesLocation(@ApiParam(value = DOMAIN,
	        required = true,defaultValue = DOMAIN_SAMPLE,
	        example = DOMAIN_SAMPLE) @QueryParam("domain_name") String domain,
	        @QueryParam("mode") String mode) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.getDevicesLocation(domain, mode));
		return responseBuilder.build();
	}

	@GET
	@Path("/alarms/{domain_name}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = LIST_LIVE_ALARMS_SUMMARY,
	        notes = LIST_LIVE_ALARMS_DESC,response = List.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = LIST_LIVE_ALARMS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getLiveAlarms(@ApiParam(value = WebConstants.DOMAIN,
	        required = false) @PathParam("domain_name") String domainName) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.getLiveAlarms(domainName));
		return responseBuilder.build();
	}

	@GET
	@Path("/alarms/history/{domain_name}/{start_date}/{end_date}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = LIST_ALARM_HISTORY_SUMMARY,
	        notes = LIST_ALARM_HISTORY_DESC,response = List.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = LIST_ALARM_HISTORY_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getAlarmHistory(
	        @ApiParam(value = WebConstants.DOMAIN,required = false) @PathParam("domain_name") String domainName,
	        @ApiParam(value = ResourceConstants.START_TIME_MS,required = true) @PathParam("start_date") Long startDate,
	        @ApiParam(value = ResourceConstants.END_TIME_MS,required = true) @PathParam("end_date") Long endDate) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.getAlarmHistory(domainName,
		        startDate, endDate));
		return responseBuilder.build();
	}

	@POST
	@Path("owned/list")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_OWNED_DEVICES_SUMMARY,
	        notes = GET_OWNED_DEVICES_DESC,response = List.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = GET_OWNED_DEVICES__SUCCESS_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getAllOwnedDeviceByDomain(
	        @ApiParam(value = ResourceConstants.TENANT_IDENTITY_PAYLOAD,
	                required = true) IdentityDTO identityDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService
		        .getAllOwnedDeviceByDomain(identityDTO));
		return responseBuilder.build();
	}

	@POST
	@Path("search/list")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICES_BY_DS_SUMMARY,
	        notes = GET_DEVICES_BY_DS_DESC,response = List.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = GET_DEVICES_BY_DS_SUCCESS_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getDevices(
	        @ApiParam(value = ResourceConstants.TENANT_IDENTITY_PAYLOAD,
	                required = true) List<String> datasources) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.getDevices(datasources));
		return responseBuilder.build();
	}

	@POST
	@Path("delete/{sourceId}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICES_BY_DS_SUMMARY,
	        notes = GET_DEVICES_BY_DS_DESC,response = List.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = GET_DEVICES_BY_DS_SUCCESS_RESP),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response deleteDevice(
	        @ApiParam(value = ResourceConstants.TENANT_IDENTITY_PAYLOAD,
	                required = true) IdentityDTO deviceIdentifier,
	        @ApiParam(value = ResourceConstants.END_TIME_MS,required = true) @PathParam("sourceId") String datasourceName) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(deviceService.deleteDevice(deviceIdentifier,
		        datasourceName));
		return responseBuilder.build();
	}
}
