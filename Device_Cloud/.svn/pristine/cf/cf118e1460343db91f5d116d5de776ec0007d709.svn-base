/**
 * 
 */
package com.pcs.datasource.resource;

import static com.pcs.datasource.doc.constants.DeviceActivityUpdateResource.GET_ALL_ACTIVE_DEVICES_DESC;
import static com.pcs.datasource.doc.constants.DeviceActivityUpdateResource.GET_ALL_ACTIVE_DEVICES_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceActivityUpdateResource.GET_ALL_ACTIVE_DEVICES_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceActivityUpdateResource.GET_DEVICE_STATUS_DESC;
import static com.pcs.datasource.doc.constants.DeviceActivityUpdateResource.GET_DEVICE_STATUS_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceActivityUpdateResource.GET_DEVICE_STATUS_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceActivityUpdateResource.PERSIST_DEVICE_TRANSITION_DESC;
import static com.pcs.datasource.doc.constants.DeviceActivityUpdateResource.PERSIST_DEVICE_TRANSITION_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceActivityUpdateResource.PERSIST_DEVICE_TRANSITION_SUMMARY;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_IDS_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_TRANSITION_PERSIST_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_HEADER_INVALID;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.DeviceLatestUpdate;
import com.pcs.datasource.dto.DeviceStatus;
import com.pcs.datasource.dto.DeviceStatusNew;
import com.pcs.datasource.dto.DeviceStatusResponse;
import com.pcs.datasource.services.DeviceActivityService;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * @author pcseg129(Seena Jyothish)
 * @date 18 Apr 2016
 *
 */
@Path("/device_activity")
@Component
@Api("Device Activity")
public class DeviceActivityResource {

	@Autowired
	private DeviceActivityService deviceActivityService;

	@GET
	@Path("/active")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_ACTIVE_DEVICES_SUMMARY, notes = GET_ALL_ACTIVE_DEVICES_DESC)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_ACTIVE_DEVICES_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 508, message = JWT_HEADER_INVALID) })
	public Response getAllActiveDevices() {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<DeviceLatestUpdate> allDevices = deviceActivityService
				.getAllActiveDevices();
		GenericEntity<List<DeviceLatestUpdate>> entity = new GenericEntity<List<DeviceLatestUpdate>>(
				allDevices) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@POST
	@Path("/status")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICE_STATUS_SUMMARY, notes = GET_DEVICE_STATUS_DESC)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_DEVICE_STATUS_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 508, message = JWT_HEADER_INVALID) })
	public Response getDevicesStatus(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
			@ApiParam(value = DEVICE_IDS_PAYLOAD, required = true) Set<String> sourceIds) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		DeviceStatusResponse statusResp = deviceActivityService
				.getDeviceStatus(sourceIds);
		responseBuilder.entity(statusResp);
		return responseBuilder.build();
	}

	@POST
	@Path("/transition")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = PERSIST_DEVICE_TRANSITION_SUMMARY, notes = PERSIST_DEVICE_TRANSITION_DESC)
	@ApiResponses(value = { @ApiResponse(code = SC_OK, message = PERSIST_DEVICE_TRANSITION_SUCCESS_RESP)})
	public Response persistDevicesTransition(
			@ApiParam(value = DEVICE_TRANSITION_PERSIST_PAYLOAD, required = true) List<DeviceStatusNew> deviceStatusList) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO status = deviceActivityService
				.persistDeviceTransition(deviceStatusList);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}

}
