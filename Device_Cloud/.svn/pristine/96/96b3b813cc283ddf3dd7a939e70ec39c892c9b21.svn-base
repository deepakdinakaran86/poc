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

import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.CHECK_DEVICE_CONFIG_EXISTS_DESC;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.CHECK_DEVICE_CONFIG_EXISTS_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.GET_ALL_DEVICE_CONFIG_TEMPLATES_DESC;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.GET_ALL_DEVICE_CONFIG_TEMPLATES_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.GET_ALL_DEVICE_CONFIG_TEMPLATES_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.GET_DEVICE_CONF_DESC;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.GET_DEVICE_CONF_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.GET_DEVICE_CONF_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.INACTIVATE_DEVICE_CONFIG_TEMPLATES_DESC;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.INACTIVATE_DEVICE_CONFIG_TEMPLATES_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.INSERT_DEVICE_CONFIG_TEMPLATE_DESC;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.INSERT_DEVICE_CONFIG_TEMPLATE_SUMMARY;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.UPDATE_DEVICE_CONFIG_TEMPLATE_DESC;
import static com.pcs.datasource.doc.constants.DeviceConfigResourceConstants.UPDATE_DEVICE_CONFIG_TEMPLATE_SUMMARY;
import static com.pcs.datasource.doc.constants.ResourceConstants.CONFIG_SEARCH_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_CONFIG_TEMPLATE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.FIELD_ALREADY_EXIST;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_HEADER_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.POINT_DUPLICATE_RECORDS;
import static com.pcs.datasource.doc.constants.ResourceConstants.SPECIFIC_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.TEMPLATE_NAME;
import static com.pcs.datasource.doc.constants.ResourceConstants.TEMPLATE_NAMES_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.TEMPLATE_NAME_SAMPLE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.services.DeviceConfigService;
import com.pcs.datasource.services.utils.SubscriptionUtility;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for managing all services related to device point
 * configuration
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 02 Jul 2015
 */
@Path("/config_templates")
@Component
@Api("Configuration Template")
public class DeviceConfigResource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SystemResource.class);

	@Autowired
	private DeviceConfigService deviceConfigService;

	@Autowired
	SubscriptionUtility subscriptionUtility;

	@GET
	@Path("/{temp_name}/isexist")
	@Consumes(TEXT_PLAIN)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CHECK_DEVICE_CONFIG_EXISTS_SUMMARY, notes = CHECK_DEVICE_CONFIG_EXISTS_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = JWT_HEADER_INVALID) })
	public Response isDeviceConfigTempNameExist(@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE)
	@HeaderParam("x-jwt-assertion") String jwtObject,
	@ApiParam(value = TEMPLATE_NAME, required = true, defaultValue = TEMPLATE_NAME_SAMPLE, example = TEMPLATE_NAME_SAMPLE) @PathParam("temp_name") String tempName
			) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
				.getSubscription(jwtObject);
		StatusMessageDTO statusMessageDTO = deviceConfigService
				.isDeviceConfigTempExist(subscription.getSubId(), tempName);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = INSERT_DEVICE_CONFIG_TEMPLATE_SUMMARY, notes = INSERT_DEVICE_CONFIG_TEMPLATE_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 508, message = JWT_HEADER_INVALID),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 509, message = POINT_DUPLICATE_RECORDS),
			@ApiResponse(code = 510, message = SPECIFIC_DATA_NOT_AVAILABLE)}
			)
	public Response createConfigTemplate(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
			@ApiParam(value = DEVICE_CONFIG_TEMPLATE_PAYLOAD, required = true) DeviceConfigTemplate configTemplate) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
				.getSubscription(jwtObject);
		StatusMessageDTO statusMessageDTO = deviceConfigService
				.createDeviceConfigTemp(subscription.getSubId(), configTemplate);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_DEVICE_CONFIG_TEMPLATE_SUMMARY, notes = UPDATE_DEVICE_CONFIG_TEMPLATE_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 508, message = JWT_HEADER_INVALID),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 509, message = POINT_DUPLICATE_RECORDS),
			@ApiResponse(code = 510, message = SPECIFIC_DATA_NOT_AVAILABLE)}
			)
	public Response updateConfigTemplate(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
			@ApiParam(value = DEVICE_CONFIG_TEMPLATE_PAYLOAD, required = true) DeviceConfigTemplate configTemplate) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
				.getSubscription(jwtObject);
		StatusMessageDTO statusMessageDTO = deviceConfigService
				.updateDeviceConfigTemp(subscription.getSubId(), configTemplate);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}


	/**
	 * @return
	 */
	@POST
	@Path("/all")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_DEVICE_CONFIG_TEMPLATES_SUMMARY, notes = GET_ALL_DEVICE_CONFIG_TEMPLATES_DESC, response = DeviceConfigTemplate.class,responseContainer="List" )
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_DEVICE_CONFIG_TEMPLATES_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 508, message = JWT_HEADER_INVALID)
			}
			)
	public Response getAllDevConfTemplates(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
			@ApiParam(value = CONFIG_SEARCH_PAYLOAD, required = true) ConfigurationSearch conSearch) {
		LOGGER.debug("DataTypeResource.getAllDevConfTemplates() Starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
				.getSubscription(jwtObject);
		List<DeviceConfigTemplate> allConfTemplates = deviceConfigService
				.getAllConfTemplates(subscription.getSubId(), conSearch);
		GenericEntity<List<DeviceConfigTemplate>> entity = new GenericEntity<List<DeviceConfigTemplate>>(
				allConfTemplates) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	/**
	 * @return
	 */
	@GET
	@Path("/{template_name}")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_DEVICE_CONF_SUMMARY, notes = GET_DEVICE_CONF_DESC, response = DeviceConfigTemplate.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_DEVICE_CONF_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = SPECIFIC_DATA_NOT_AVAILABLE)})
	public Response getDevConfTemplate(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE)
			@HeaderParam("x-jwt-assertion") String jwtObject,
			@ApiParam(value = TEMPLATE_NAME, required = true, defaultValue = TEMPLATE_NAME_SAMPLE, example = TEMPLATE_NAME_SAMPLE) @PathParam("template_name") String templateName) {
		LOGGER.debug("DataTypeResource.getAllDevConfTemplate() Starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
				.getSubscription(jwtObject);
		responseBuilder.entity(deviceConfigService.getConfTemplate(
				subscription.getSubId(), templateName));
		return responseBuilder.build();
	}

	/**
	 * @param subId
	 * @param templateNames
	 * @return
	 */
	@PUT
	@Path("/deactivate")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = INACTIVATE_DEVICE_CONFIG_TEMPLATES_SUMMARY, notes = INACTIVATE_DEVICE_CONFIG_TEMPLATES_DESC, response = StatusMessageDTO.class)
	public Response inActivateConfTemplate(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE)
			@HeaderParam("x-jwt-assertion") String jwtObject,
			@ApiParam(value = TEMPLATE_NAMES_PAYLOAD, required = true) List<String> templateNames) {
		LOGGER.debug("DataTypeResource.inActivateConfTemplate() Starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
				.getSubscription(jwtObject);
		responseBuilder.entity(deviceConfigService.inActivateConfigTemplates(
				subscription.getSubId(), templateNames));
		return responseBuilder.build();
	}
}
