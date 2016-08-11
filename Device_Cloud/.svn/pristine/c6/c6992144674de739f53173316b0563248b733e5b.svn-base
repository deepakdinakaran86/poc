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

import static com.pcs.datasource.doc.constants.ResourceConstants.CONFIGURATION_SEARCH_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_MAKE_CREATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_MAKE_UPDATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_MODEL_CREATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_MODEL_UPDATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_PROTOCOL;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_PROTOCOL_CREATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_PROTOCOL_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_PROTOCOL_UPDATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_PROTOCOL_VERSION;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_PROTOCOL_VERSION_CREATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_PROTOCOL_VERSION_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_PROTOCOL_VERSION_UPDATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_TYPE;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_TYPE_CREATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_TYPE_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_TYPE_UPDATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.FIELD_ALREADY_EXIST;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.MAKE;
import static com.pcs.datasource.doc.constants.ResourceConstants.MAKE_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.MODEL;
import static com.pcs.datasource.doc.constants.ResourceConstants.MODEL_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.PERSISTENCE_ERROR;
import static com.pcs.datasource.doc.constants.ResourceConstants.PQ_QUANTITY_NAME;
import static com.pcs.datasource.doc.constants.ResourceConstants.PQ_QUANTITY_NAME_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.SUBSCRIBER;
import static com.pcs.datasource.doc.constants.ResourceConstants.SUBSCRIBER_SAMPLE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_DEVICE_PROTOCOL_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_DEVICE_PROTOCOL_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_DEVICE_PROTOCOL_VERSION_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_DEVICE_PROTOCOL_VERSION_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_DEVICE_TYPE_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_DEVICE_TYPE_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_MAKE_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_MAKE_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_MODEL_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_MODEL_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_SUBSRIPTION_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.CREATE_SUBSRIPTION_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_ACCESS_TYPES_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_ACCESS_TYPES_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_ACCESS_TYPES_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_DATA_TYPES_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_DATA_TYPES_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_DATA_TYPES_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_DEVICE_TYPES_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_DEVICE_TYPES_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_DEVICE_TYPES_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_MAKES_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_MAKES_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_MAKES_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_MODELS_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_MODELS_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_MODELS_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_NW_PROTOCOLS_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_NW_PROTOCOLS_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_NW_PROTOCOLS_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_COMMANDS_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_COMMANDS_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_COMMANDS_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_POINTS_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_POINTS_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_POINTS_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_VERSIONS_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_VERSIONS_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_PROTOCOLS_VERSIONS_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_SYS_TAGS_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_SYS_TAGS_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.GET_SYS_TAGS_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.UPDATE_DEVICE_PROTOCOL_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.UPDATE_DEVICE_PROTOCOL_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.UPDATE_DEVICE_PROTOCOL_VERSION_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.UPDATE_DEVICE_PROTOCOL_VERSION_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.UPDATE_DEVICE_TYPE_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.UPDATE_DEVICE_TYPE_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.UPDATE_MAKE_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.UPDATE_MAKE_SUMMARY;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.UPDATE_MODEL_DESC;
import static com.pcs.datasource.doc.constants.SystemResourceConstants.UPDATE_MODEL_SUMMARY;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.AccessType;
import com.pcs.datasource.dto.CommandType;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DataType;
import com.pcs.datasource.dto.DeviceProtocol;
import com.pcs.datasource.dto.DeviceType;
import com.pcs.datasource.dto.Make;
import com.pcs.datasource.dto.Model;
import com.pcs.datasource.dto.NetworkProtocol;
import com.pcs.datasource.dto.Point;
import com.pcs.datasource.dto.ProtocolPoints;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.dto.SystemTag;
import com.pcs.datasource.services.DeviceProtocolService;
import com.pcs.datasource.services.SubscriptionService;
import com.pcs.datasource.services.SystemService;
import com.pcs.datasource.services.utils.SubscriptionUtility;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This resource class is responsible for defining all the services related to
 * master data
 * 
 * @author PCSEG199 (Javid Ahammed)
 * @date July 2015
 * @since device-cloud-1.0.0
 */
@Path("/system")
@Component
@Api("System")
public class SystemResource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SystemResource.class);

	@Autowired
	private SystemService systemService;

	@Autowired
	DeviceProtocolService deviceProtocolService;

	@Autowired
	SubscriptionService subscriptionService;

	@Autowired
	private SubscriptionUtility subscriptionUtility;

	@Path("/data_types")
	@GET
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DATA_TYPES_SUMMARY, notes = GET_DATA_TYPES_DESC, response = DataType.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_DATA_TYPES_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response getAllDataTypes() {
		LOGGER.debug("DataTypeResource.getAllDataTypes() Starts");
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<DataType> allDataTypes = systemService.getAllDataTypes();
		GenericEntity<List<DataType>> entity = new GenericEntity<List<DataType>>(
				allDataTypes) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}
	@Path("/access_types")
	@GET
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ACCESS_TYPES_SUMMARY, notes = GET_ACCESS_TYPES_DESC, response = AccessType.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ACCESS_TYPES_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response getAllAccessTypes() {
		LOGGER.debug("DataTypeResource.getAllAccessTypes() Starts");
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<AccessType> allAccessTypes = systemService.getAllAccessTypes();
		GenericEntity<List<AccessType>> entity = new GenericEntity<List<AccessType>>(
				allAccessTypes) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@GET
	@Path("/network_protocols")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_NW_PROTOCOLS_SUMMARY, notes = GET_NW_PROTOCOLS_DESC, response = NetworkProtocol.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_NW_PROTOCOLS_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response getAllNwProtocols() {
		LOGGER.debug("getAllDeviceTypes()");
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<NetworkProtocol> allNwProtocols = systemService
				.getAllNwProtocols();
		GenericEntity<List<NetworkProtocol>> entity = new GenericEntity<List<NetworkProtocol>>(
				allNwProtocols) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@Path("/system_tags")
	@GET
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_SYS_TAGS_SUMMARY, notes = GET_SYS_TAGS_DESC, response = SystemTag.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_SYS_TAGS_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response getAllSystemTags(
			 @ApiParam(value = PQ_QUANTITY_NAME, required = false, defaultValue = PQ_QUANTITY_NAME_SAMPLE, example = PQ_QUANTITY_NAME_SAMPLE)
			@QueryParam("phy_qty") String physicalQty) {
		LOGGER.debug("DataTypeResource.getAllSystemTags() Starts");
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<SystemTag> allSystemTags = systemService
				.getAllSystemTags(physicalQty);
		GenericEntity<List<SystemTag>> entity = new GenericEntity<List<SystemTag>>(
				allSystemTags) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@POST
	@Path("/make")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CREATE_MAKE_SUMMARY, notes = CREATE_MAKE_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)})
	public Response createDeviceMake(@ApiParam(value = DEVICE_MAKE_CREATE_PAYLOAD, required = true)
									ConfigurationSearch configurationSearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = systemService.createDeviceMake(configurationSearch);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
	
	@PUT
	@Path("/make/{make_name}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_MAKE_SUMMARY, notes = UPDATE_MAKE_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)})
	public Response updateDeviceMake(@ApiParam(value = MAKE, required = true, defaultValue = MAKE_SAMPLE, example = MAKE_SAMPLE)
									 @PathParam("make_name") String makeName,
									 @ApiParam(value = DEVICE_MAKE_UPDATE_PAYLOAD, required = true) ConfigurationSearch configurationSearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = systemService.updateDeviceMake(makeName,configurationSearch);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
	
	@GET
	@Path("/makes")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_MAKES_SUMMARY, notes = GET_MAKES_DESC, response = Make.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_MAKES_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response getDeviceMakes() {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<Make> makes = systemService.getDeviceMakes();
		GenericEntity<List<Make>> entity = new GenericEntity<List<Make>>(makes) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@GET
	@Path("/makes/{make_name}/device_types")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICE_TYPES_SUMMARY, notes = GET_DEVICE_TYPES_DESC, response = DeviceType.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_DEVICE_TYPES_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response getDeviceTypes(
			@ApiParam(value = MAKE, required = true, defaultValue = MAKE_SAMPLE, example = MAKE_SAMPLE)
			@PathParam("make_name") String makeName) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<DeviceType> deviceTypes = systemService.getDeviceTypes(makeName);
		GenericEntity<List<DeviceType>> entity = new GenericEntity<List<DeviceType>>(
				deviceTypes) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}
	
	@POST
	@Path("/device_type")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CREATE_DEVICE_TYPE_SUMMARY, notes = CREATE_DEVICE_TYPE_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)})
	public Response createDeviceType(@ApiParam(value = DEVICE_TYPE_CREATE_PAYLOAD, required = true)
									ConfigurationSearch configurationSearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = systemService.createDeviceType(configurationSearch);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
	
	@PUT
	@Path("/device_type/{device_type_name}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DEVICE_TYPE_SUMMARY, notes = UPDATE_DEVICE_TYPE_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)})
	public Response updateDeviceType(@ApiParam(value = DEVICE_TYPE, required = true, defaultValue = DEVICE_TYPE_SAMPLE, example = DEVICE_TYPE_SAMPLE)
									 @PathParam("device_type_name") String deviceTypeName,
									 @ApiParam(value = DEVICE_TYPE_UPDATE_PAYLOAD, required = true) ConfigurationSearch configurationSearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = systemService.updateDeviceType(deviceTypeName,configurationSearch);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
	
	@POST
	@Path("/model")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CREATE_MODEL_SUMMARY, notes = CREATE_MODEL_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)})
	public Response createDeviceModel(@ApiParam(value = DEVICE_MODEL_CREATE_PAYLOAD, required = true)
									ConfigurationSearch configurationSearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = systemService.createDeviceModel(configurationSearch);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
	
	@PUT
	@Path("/model/{model_name}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_MODEL_SUMMARY, notes = UPDATE_MODEL_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)})
	public Response updateDeviceModel(@ApiParam(value = MODEL, required = true, defaultValue = MODEL_SAMPLE, example = MODEL_SAMPLE)
									 @PathParam("model_name") String modelName,
									 @ApiParam(value = DEVICE_MODEL_UPDATE_PAYLOAD, required = true) ConfigurationSearch configurationSearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = systemService.updateDeviceModel(modelName,configurationSearch);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
	
	@POST
	@Path("/models")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_MODELS_SUMMARY, notes = GET_MODELS_DESC, response = Model.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_MODELS_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getDeviceModels(
			@ApiParam(value = CONFIGURATION_SEARCH_PAYLOAD, required = true)
			ConfigurationSearch configuration) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<Model> models = systemService.getDeviceModels(configuration);
		GenericEntity<List<Model>> entity = new GenericEntity<List<Model>>(
				models) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@POST
	@Path("/protocol")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CREATE_DEVICE_PROTOCOL_SUMMARY, notes = CREATE_DEVICE_PROTOCOL_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)})
	public Response createDeviceProtocol(@ApiParam(value = DEVICE_PROTOCOL_CREATE_PAYLOAD, required = true)
										ConfigurationSearch configurationSearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = systemService.createDeviceProtocol(configurationSearch);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
	
	@PUT
	@Path("/protocol/{protocol_name}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DEVICE_PROTOCOL_SUMMARY, notes = UPDATE_DEVICE_PROTOCOL_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)})
	public Response updateDeviceProtocol(@ApiParam(value = DEVICE_PROTOCOL, required = true, defaultValue = DEVICE_PROTOCOL_SAMPLE, example = DEVICE_PROTOCOL_SAMPLE)
									 @PathParam("protocol_name") String protocolName,
									 @ApiParam(value = DEVICE_PROTOCOL_UPDATE_PAYLOAD, required = true) ConfigurationSearch configurationSearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = systemService.updateDeviceProtocol(protocolName,configurationSearch);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
	
	@POST
	@Path("/protocols")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_PROTOCOLS_SUMMARY, notes = GET_PROTOCOLS_DESC, response = DeviceProtocol.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_PROTOCOLS_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getDeviceProtocols(
			@ApiParam(value = CONFIGURATION_SEARCH_PAYLOAD, required = true)
			ConfigurationSearch configuration) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<DeviceProtocol> protocols = systemService
				.getDeviceProtocols(configuration);
		GenericEntity<List<DeviceProtocol>> entity = new GenericEntity<List<DeviceProtocol>>(
				protocols) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}
	
	@POST
	@Path("/protocol/version")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CREATE_DEVICE_PROTOCOL_VERSION_SUMMARY, notes = CREATE_DEVICE_PROTOCOL_VERSION_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)})
	public Response createDeviceProtocolVersion(@ApiParam(value = DEVICE_PROTOCOL_VERSION_CREATE_PAYLOAD, required = true)
												ConfigurationSearch configurationSearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = systemService.createDeviceProtocolVersion(configurationSearch);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
	
	@PUT
	@Path("/protocol/version/{version_name}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DEVICE_PROTOCOL_VERSION_SUMMARY, notes = UPDATE_DEVICE_PROTOCOL_VERSION_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)})
	public Response updateDeviceProtocolVersion(@ApiParam(value = DEVICE_PROTOCOL_VERSION, required = true, defaultValue = DEVICE_PROTOCOL_VERSION_SAMPLE, example = DEVICE_PROTOCOL_VERSION_SAMPLE)
									 @PathParam("version_name") String versionName,
									 @ApiParam(value = DEVICE_PROTOCOL_VERSION_UPDATE_PAYLOAD, required = true) ConfigurationSearch configurationSearch) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = systemService.updateDeviceProtocolVersion(versionName,configurationSearch);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("/protocols/versions")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_PROTOCOLS_VERSIONS_SUMMARY, notes = GET_PROTOCOLS_VERSIONS_DESC, response = ProtocolVersion.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_PROTOCOLS_VERSIONS_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getProtocolVersions(
			@ApiParam(value = CONFIGURATION_SEARCH_PAYLOAD, required = true)
			ConfigurationSearch configuration) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<ProtocolVersion> versions = systemService
				.getProtocolVersions(configuration);
		GenericEntity<List<ProtocolVersion>> entity = new GenericEntity<List<ProtocolVersion>>(
				versions) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@POST
	@Path("/protocols/points")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_PROTOCOLS_POINTS_SUMMARY, notes = GET_PROTOCOLS_POINTS_DESC, response = Point.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_PROTOCOLS_POINTS_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getProtocolVersionPoint(
			@ApiParam(value = CONFIGURATION_SEARCH_PAYLOAD, required = true)
			ConfigurationSearch configuration) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<Point> points = systemService
				.getProtocolVersionPoint(configuration);

		ProtocolPoints protocolPoints = new ProtocolPoints();
		protocolPoints.setPoints(points);
		GenericEntity<ProtocolPoints> entity = new GenericEntity<ProtocolPoints>(
				protocolPoints) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@POST
	@Path("/protocols/commands")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_PROTOCOLS_COMMANDS_SUMMARY, notes = GET_PROTOCOLS_COMMANDS_DESC, response = CommandType.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_PROTOCOLS_COMMANDS_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getCommandsOfDeviceProtocol(
			@ApiParam(value = CONFIGURATION_SEARCH_PAYLOAD, required = true)
			ConfigurationSearch configuration) {
		LOGGER.debug("getCommandsOfDeviceProtocol()");
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<CommandType> deviceCommands = deviceProtocolService
				.getCommandsOfDeviceProtocol(configuration);
		GenericEntity<List<CommandType>> entity = new GenericEntity<List<CommandType>>(
				deviceCommands) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@POST
	@Path("/subscriptions/{subscriber}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CREATE_SUBSRIPTION_SUMMARY, notes = CREATE_SUBSRIPTION_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createSubscription(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE)
			@HeaderParam("x-jwt-assertion") String jwtObject,
			@ApiParam(value = SUBSCRIBER, required = true, defaultValue = SUBSCRIBER_SAMPLE, example = SUBSCRIBER_SAMPLE)
			@PathParam("subscriber") String subscriber) {
		Subscription subscription = subscriptionUtility
				.createSubscription(jwtObject);
		subscription.setsubscriber(subscriber);
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		responseBuilder.entity(subscriptionService
				.createSubscription(subscription));
		return responseBuilder.build();
	}
	
	
}
