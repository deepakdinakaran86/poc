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

import static com.pcs.datasource.doc.constants.DeviceResourceConstants.ASSIGN_DEVICE_CONF_TEMPLATE_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.ASSIGN_DEVICE_CONF_TEMPLATE_INVALID_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.ASSIGN_DEVICE_CONF_TEMPLATE_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.ASSIGN_DEVICE_CONF_TEMPLATE_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.AUTHENTICATE_DEVICE_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.AUTHENTICATE_DEVICE_NOT_FOUND;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.AUTHENTICATE_DEVICE_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.AUTHENTICATE_DEVICE_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.AUTHENTICATE_DEVICE_UNSUBSCRIBED;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.CLAIM_DEVICE_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.CLAIM_DEVICE_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_ALL_DEVICES_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_ALL_DEVICES_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_ALL_DEVICES_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DATASOURCE_NAME_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DATASOURCE_NAME_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DATASOURCE_NAME_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICES_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICES_FILTER_PROTOCOL_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICES_FILTER_PROTOCOL_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICES_FILTER_TAGS_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICES_FILTER_TAGS_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICES_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICES_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICE_CONF_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICE_CONF_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICE_CONF_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICE_DETAILS_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICE_DETAILS_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_DEVICE_DETAILS_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_UNSUBSCRIBED_DEVICES_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_UNSUBSCRIBED_DEVICES_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.GET_UNSUBSCRIBED_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.INSERT_DEVICE_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.INSERT_DEVICE_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.REGISTER_DEVICE_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.REGISTER_DEVICE_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_DEVICE_CONF_BATCH_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_DEVICE_CONF_BATCH_INVALID_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_DEVICE_CONF_BATCH_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_DEVICE_CONF_BATCH_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_DEVICE_CONF_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_DEVICE_CONF_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_DEVICE_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_DEVICE_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_DEVICE_WB_CONF_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_DEVICE_WB_CONF_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_TAGS_DESC;
import static com.pcs.datasource.doc.constants.DeviceResourceConstants.UPDATE_TAGS_SUMMARY;
import static com.pcs.datasource.doc.constants.ResourceConstants.ASSIGN_DEVICE_CONFIG_TEMP_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_CONFIG_BATCH_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_SOURCE_ID;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_SOURCE_ID_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_HEADER_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.LIST_CONFIG_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.LIST_DEVICE_TAGS_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.PROTOCOL_PAYLOAD;
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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.ConfigureDevice;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceAuthentication;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.DeviceTag;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.dto.TemplateAssign;
import com.pcs.datasource.services.DeviceConfigService;
import com.pcs.datasource.services.DeviceService;
import com.pcs.datasource.services.utils.SubscriptionUtility;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This resource class is responsible for defining all the services related to
 * Device communicating to the system. This class is responsible for
 * authenticate device with galaxy, register device once after the successful
 * authentication and manage services related to configuration template
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */
@Path("/devices")
@Component
@Api("Device")
public class DeviceResource {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DeviceResource.class);

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private DeviceConfigService deviceConfigService;

	@Autowired
	private SubscriptionUtility subscriptionUtility;

	@GET
	@Path("/{source_id}")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICE_DETAILS_SUMMARY,
	        notes = GET_DEVICE_DETAILS_DESC,response = Device.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = GET_DEVICE_DETAILS_SUCCESS_RESP),
	                @ApiResponse(code = 500,
	                        message = GENERAL_DATA_NOT_AVAILABLE),
	                @ApiResponse(code = 508,message = JWT_HEADER_INVALID)})
	public Response getDeviceDetails(
	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId) {
		LOGGER.debug("Authenticate device with source Id : ", sourceId);
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		Device device = deviceService.getDevice(sourceId, subscription);
		device.setDeviceId(null);
		responseBuilder.entity(device);
		return responseBuilder.build();
	}

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
	public Response insertDevice(

	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = DEVICE_PAYLOAD,required = true) Device device) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		device.setSubscription(subscription);
		responseBuilder.entity(deviceService.insertDevice(device));
		return responseBuilder.build();
	}

	@PUT
	@Path("/{source_id}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DEVICE_SUMMARY,notes = UPDATE_DEVICE_DESC)
	@ApiResponses(value = {
		@ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP)})
	public Response updateDevice(
	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId,
	        @ApiParam(value = DEVICE_PAYLOAD,required = true) Device device) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		device.setSubscription(subscription);
		responseBuilder.entity(deviceService.updateDevice(sourceId, device));
		return responseBuilder.build();
	}

	@POST
	@Path("/authenticate")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = AUTHENTICATE_DEVICE_SUMMARY,
	        notes = AUTHENTICATE_DEVICE_DESC)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = AUTHENTICATE_DEVICE_SUCCESS_RESP),
	                @ApiResponse(code = 2001,
	                        message = AUTHENTICATE_DEVICE_NOT_FOUND),
	                @ApiResponse(code = 2002,
	                        message = AUTHENTICATE_DEVICE_UNSUBSCRIBED)})
	public Response authenticateDevice(@ApiParam(value = DEVICE_PAYLOAD,
	        required = true) Device device) {
		LOGGER.debug("Authenticate device ");
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		DeviceAuthentication authentication = deviceService
		        .authenticateDevice(device);
		responseBuilder.entity(authentication);
		return responseBuilder.build();
	}

	@GET
	@Path("{source_id}/configurations")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICE_CONF_SUMMARY,notes = GET_DEVICE_CONF_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_DEVICE_CONF_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID)})
	public Response getDeviceConfiguration(
	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId) {
		LOGGER.debug("Get DeviceConfTemplate starts");

		ResponseBuilder responseBuilder = status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		DeviceConfigTemplate configurations = deviceService.getConfigurations(
		        sourceId, subscription);
		responseBuilder.entity(configurations);
		return responseBuilder.build();
	}

	@GET
	@Path("{source_id}/configurationsdefault")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICE_CONF_SUMMARY,notes = GET_DEVICE_CONF_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_DEVICE_CONF_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getDeviceConfigurationDefault(
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId) {
		LOGGER.debug("Get DeviceConfTemplate starts");

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		DeviceConfigTemplate configurations = deviceService
		        .getConfigurations(sourceId);
		responseBuilder.entity(configurations);
		return responseBuilder.build();
	}

	@PUT
	@Path("{source_id}/configurationsdefault")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DEVICE_CONF_SUMMARY,
	        notes = UPDATE_DEVICE_CONF_DESC)
	@ApiResponses(value = {
		@ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP)})
	public Response updateDeviceConfDefault(
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId,
	        @ApiParam(value = LIST_CONFIG_PAYLOAD,required = true) List<ConfigPoint> configPoints) {
		LOGGER.debug("Update DeviceConf starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(deviceConfigService.updateDeviceConfiguration(
		        sourceId, configPoints));
		return responseBuilder.build();
	}

	@PUT
	@Path("{source_id}/configurations")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DEVICE_CONF_SUMMARY,
	        notes = UPDATE_DEVICE_CONF_DESC)
	@ApiResponses(value = {
		@ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP)})
	public Response updateDeviceConf(
	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId,
	        @ApiParam(value = LIST_CONFIG_PAYLOAD,required = true) List<ConfigPoint> configPoints) {
		LOGGER.debug("Update DeviceConf starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		responseBuilder.entity(deviceConfigService.updateDeviceConfiguration(
		        subscription, sourceId, configPoints));
		return responseBuilder.build();
	}

	@PUT
	@Path("/configurations/batch")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DEVICE_CONF_BATCH_SUMMARY,
	        notes = UPDATE_DEVICE_CONF_BATCH_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,
	                message = UPDATE_DEVICE_CONF_BATCH_SUCCESS_RESP),
	        @ApiResponse(code = 2001,
	                message = UPDATE_DEVICE_CONF_BATCH_INVALID_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response updateDeviceConfBatch(

	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = DEVICE_CONFIG_BATCH_PAYLOAD,required = true) ConfigureDevice configureDevice) {
		LOGGER.debug("Update DeviceConf starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		responseBuilder.entity(deviceConfigService.assignConfPointsToDevices(
		        configureDevice, subscription));
		return responseBuilder.build();
	}

	@PUT
	@Path("/configurations/template")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = ASSIGN_DEVICE_CONF_TEMPLATE_SUMMARY,
	        notes = ASSIGN_DEVICE_CONF_TEMPLATE_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,
	                message = ASSIGN_DEVICE_CONF_TEMPLATE_SUCCESS_RESP),
	        @ApiResponse(code = 2001,
	                message = ASSIGN_DEVICE_CONF_TEMPLATE_INVALID_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response assignTemplate(

	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = ASSIGN_DEVICE_CONFIG_TEMP_PAYLOAD,required = true) TemplateAssign templateAssign) {
		LOGGER.debug("Update DeviceConf starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		responseBuilder.entity(deviceConfigService.assignTemplateToDevices(
		        templateAssign, subscription));
		return responseBuilder.build();
	}

	@GET
	@Path("/all")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICES_SUMMARY,notes = GET_DEVICES_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_DEVICES_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID)})
	public Response getAllDevicesOfSubscription(

	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		List<Device> allDevices = deviceService.getAllDevices(subscription,
		        null);
		GenericEntity<List<Device>> entity = new GenericEntity<List<Device>>(
		        allDevices) {
		};
		responseBuilder.entity(entity);
		LOGGER.debug("Get DeviceConfTemplate starts");
		return responseBuilder.build();
	}

	@POST
	@Path("/all")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICES_FILTER_TAGS_SUMMARY,
	        notes = GET_DEVICES_FILTER_TAGS_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_DEVICES_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID)})
	public Response getAllDevicesOfSubscriptionFilteredWithTags(

	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = LIST_DEVICE_TAGS_PAYLOAD,required = false) List<String> tagNames) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		List<Device> allDevices = deviceService.getAllDevices(subscription,
		        tagNames);
		GenericEntity<List<Device>> entity = new GenericEntity<List<Device>>(
		        allDevices) {
		};
		responseBuilder.entity(entity);
		LOGGER.debug("Get DeviceConfTemplate starts");
		return responseBuilder.build();
	}

	@POST
	@Path("/filter")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICES_FILTER_PROTOCOL_SUMMARY,
	        notes = GET_DEVICES_FILTER_PROTOCOL_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_DEVICES_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getAllDevicesOfAProtocol(
	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = PROTOCOL_PAYLOAD,required = true) ConfigurationSearch conSearch) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		List<Device> allDevices = deviceService.getDevicesOfProtocolVersion(
		        subscription, conSearch);
		GenericEntity<List<Device>> entity = new GenericEntity<List<Device>>(
		        allDevices) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@POST
	@Path("/register")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = REGISTER_DEVICE_SUMMARY,notes = REGISTER_DEVICE_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response registerDevice(

	@ApiParam(value = DEVICE_PAYLOAD,required = true) Device device) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		LOGGER.debug("Register device starts");
		responseBuilder.entity(deviceService.registerDevice(device));
		return responseBuilder.build();
	}

	@PUT
	@Path("{source_id}/writebackconf")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DEVICE_WB_CONF_SUMMARY,
	        notes = UPDATE_DEVICE_WB_CONF_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID)})
	public Response updateWritebackConf(

	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId,
	        @ApiParam(value = DEVICE_PAYLOAD,required = true) Device device) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(deviceService.updateWritebackConf(sourceId,
		        device));
		return responseBuilder.build();
	}

	@GET
	@Path("{source_id}/datasourcename")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_DATASOURCE_NAME_SUMMARY,
	        notes = GET_DATASOURCE_NAME_DESC)
	@ApiResponses(value = {
	@ApiResponse(code = SC_OK,message = GET_DATASOURCE_NAME_SUCCESS_RESP),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID)})
	public Response getDatasourceName(

	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		String datasourceName = deviceService.getDatasourceName(subscription,
		        sourceId);
		Device device = new Device();
		device.setDatasourceName(datasourceName);
		responseBuilder.entity(device);
		return responseBuilder.build();
	}

	@PUT
	@Path("{source_id}/tags")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = UPDATE_TAGS_SUMMARY,notes = UPDATE_TAGS_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response updateTags(

	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId,
	        @ApiParam(value = LIST_DEVICE_TAGS_PAYLOAD,required = true) List<DeviceTag> tags) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(deviceService.updateTags(sourceId, tags));
		return responseBuilder.build();
	}

	@PUT
	@Path("{source_id}/claim")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = CLAIM_DEVICE_SUMMARY,notes = CLAIM_DEVICE_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID)})
	public Response claimDevice(

	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		responseBuilder.entity(deviceService
		        .claimDevice(sourceId, subscription));
		return responseBuilder.build();
	}

	@ApiOperation(value = GET_UNSUBSCRIBED_DEVICES_SUMMARY,
	        notes = GET_UNSUBSCRIBED_DEVICES_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_UNSUBSCRIBED_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	@GET
	@Path("/unsubscribed")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUnsubscribed() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(deviceService.getAllUnSubscribed());
		return responseBuilder.build();
	}

	@GET
	@Path("/findall")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_DEVICES_SUMMARY,notes = GET_ALL_DEVICES_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_ALL_DEVICES_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID)})
	public Response getAllDevices() {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<Device> allDevices = deviceService.getAllDevices();
		GenericEntity<List<Device>> entity = new GenericEntity<List<Device>>(
		        allDevices) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}
}
