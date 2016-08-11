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

import static com.pcs.datasource.doc.constants.ParameterResourceConstants.GET_PARAMETERS_DESC;
import static com.pcs.datasource.doc.constants.ParameterResourceConstants.GET_PARAMETERS_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.ParameterResourceConstants.GET_PARAMETERS_SUMMARY;
import static com.pcs.datasource.doc.constants.ParameterResourceConstants.IS_PARAMETER_EXIST_DESC;
import static com.pcs.datasource.doc.constants.ParameterResourceConstants.IS_PARAMETER_EXIST_SUMMARY;
import static com.pcs.datasource.doc.constants.ParameterResourceConstants.SAVE_PARAMETER_DESC;
import static com.pcs.datasource.doc.constants.ParameterResourceConstants.SAVE_PARAMETER_SUMMARY;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FAILURE_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.PARAMETER_NAME;
import static com.pcs.datasource.doc.constants.ResourceConstants.PARAMETER_NAME_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.PARAMETER_PAYLOAD;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.services.ParameterService;
import com.pcs.datasource.services.utils.SubscriptionUtility;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for managing all parameters of system
 * 
 * @author pcseg296(Riyas PH)
 * @date 7 july 2015
 */

@Path("/parameters")
@Component
@Api("Parameter")
public class ParameterResource {

	@Autowired
	ParameterService parameterService;

	@Autowired
	SubscriptionUtility subscriptionUtility;
	
	@POST
	@Path("/")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = SAVE_PARAMETER_SUMMARY, notes = SAVE_PARAMETER_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response saveParameter(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE)
	        @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = PARAMETER_PAYLOAD, required = true)
	        ParameterDTO parameterDTO) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		parameterDTO.setSubscription(subscription);
		StatusMessageDTO status = parameterService.saveParameter(parameterDTO);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}

	@GET
	@Path("/")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_PARAMETERS_SUMMARY, notes = GET_PARAMETERS_DESC, response = ParameterDTO.class , responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_PARAMETERS_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getParameters(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE)
	        @HeaderParam("x-jwt-assertion") String jwtObject) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		List<ParameterDTO> parameters = parameterService
		        .getParameters(subscription);
		GenericEntity<List<ParameterDTO>> entity = new GenericEntity<List<ParameterDTO>>(
		        parameters) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@GET
	@Path("/{param_name}/isexist")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = IS_PARAMETER_EXIST_SUMMARY, notes = IS_PARAMETER_EXIST_DESC, response = StatusMessageDTO.class )
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 2001, message = GENERAL_FAILURE_RESP)})
	public Response isParameterExist(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE)
	        @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = PARAMETER_NAME, required = true, defaultValue = PARAMETER_NAME_SAMPLE, example = PARAMETER_NAME_SAMPLE)
	        @PathParam("param_name") String paramName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		StatusMessageDTO status = parameterService.isParameterExist(paramName,
		        subscription);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}

}
