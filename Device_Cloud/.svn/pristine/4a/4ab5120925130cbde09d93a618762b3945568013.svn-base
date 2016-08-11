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

import static com.pcs.datasource.doc.constants.DeviceTagResourceConstants.GET_TAGS_OF_SUB_DESC;
import static com.pcs.datasource.doc.constants.DeviceTagResourceConstants.GET_TAGS_OF_SUB_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DeviceTagResourceConstants.GET_TAGS_OF_SUB_SUMMARY;
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

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.DeviceTag;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.services.DeviceTagService;
import com.pcs.datasource.services.utils.SubscriptionUtility;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
@Path("/device_tags")
@Component
@Api("Device Tag")
public class DeviceTagResouce {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DeviceTagResouce.class);

	@Autowired
	private DeviceTagService deviceTagService;

	@Autowired
	private SubscriptionUtility subscriptionUtility;

	@GET
	@Path("/")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_TAGS_OF_SUB_SUMMARY, notes = GET_TAGS_OF_SUB_DESC, response = DeviceTag.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_TAGS_OF_SUB_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 508, message = JWT_HEADER_INVALID) })
	public Response getTagsOfASubscription(
			@ApiParam(value = JWT_ASSERTION, required = true, defaultValue = JWT_ASSERTION_SAMPLE, example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject) {

		LOGGER.debug("getTagsOfASubscription()");
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
				.getSubscription(jwtObject);
		List<DeviceTag> tags = deviceTagService
				.getAllTagsOfSubscription(subscription);
		GenericEntity<List<DeviceTag>> entity = new GenericEntity<List<DeviceTag>>(
				tags) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

}
