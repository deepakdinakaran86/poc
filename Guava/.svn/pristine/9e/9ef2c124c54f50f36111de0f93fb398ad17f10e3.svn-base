/**
 * Copyright 2016 Pacific Controls Software Services LLC (PCSS). All Rights
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
package com.pcs.guava.resources;

import static com.pcs.guava.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.guava.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.guava.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.guava.constants.ApiDocConstant.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.guava.constants.ApiDocConstant.PERSISTENCE_ERROR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.constants.ApiDocConstant;
import com.pcs.guava.dto.ServiceItemDTO;
import com.pcs.guava.services.ServiceItemService;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.IdentityDTO;

/**
 * @description This class is responsible for the ServiceItemServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @date 20 Apr 2016
 * 
 */

@Path("/item")
@Component
@Api("Service-Item")
public class ServiceItemResource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServiceItemResource.class);

	@Autowired
	private ServiceItemService serviceItemService;
	
	/**
	 * @description This is a default method to get the header options,all
	 *              resource layers should have this method
	 * 
	 */

	@OPTIONS
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "",hidden = true)
	public Response defaultOptions() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.header("ALLOW", "HEAD,GET,PUT,DELETE,OPTIONS");
		return responseBuilder.build();
	}
	

	/**
	 * Responsible to create a ServiceItem
	 * 
	 * @param serviceItemDTO
	 * @return StatusMessageDTO
	 */
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ApiDocConstant.CREATE_SERVICE_ITEM_DESC, value = ApiDocConstant.CREATE_SERVICE_ITEM_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = ApiDocConstant.CREATE_SERVICE_ITEM_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR),
			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE) })
	public Response createServiceItem(
			@ApiParam(required = true, value = ApiDocConstant.SERVICE_ITEM_PAYLOAD) ServiceItemDTO serviceItemDTO) {
		LOGGER.debug("<<-- Enter createServiceItem(ServiceItemDTO serviceItemDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(serviceItemService
				.createServiceItem(serviceItemDTO));
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch a ServiceItem
	 * 
	 * @param identity
	 * @return Fetched ServiceItem
	 */
	@POST
	@Path("find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ApiDocConstant.FIND_SERVICE_ITEM_DESC, value = ApiDocConstant.FIND_SERVICE_ITEM_SUMMARY, response = ServiceItemDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = ApiDocConstant.FIND_SERVICE_ITEM_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID) })
	public Response findServiceItem(
			@ApiParam(name = "serviceItemIdentityFields", required = true, value = "ServiceItem Identify Fields") IdentityDTO identity) {
		LOGGER.debug("<<-- Enter findServiceItem(IdentityDTO identity) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(serviceItemService
				.findServiceItem(identity));
		return responseBuilder.build();
	}

	/**
	 * Responsible to find the list of ServiceItem's for a domain
	 * 
	 * @param domainName
	 *            , entityTemplateName
	 * @return List of ServiceItemDTO
	 */
	@GET
	@Path("list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ApiDocConstant.FIND_ALL_SERVICE_ITEM_DESC, value = ApiDocConstant.FIND_ALL_SERVICE_ITEM_SUMMARY, response = EntityDTO.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = ApiDocConstant.FIND_ALL_SERVICE_ITEM_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID) })
	public Response findAllServiceItems(
			@QueryParam("domain_name") String domainName) {
		LOGGER.debug("<<-- Enter findAllServiceItems(String domainName) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(serviceItemService
				.findAllServiceItems(domainName));
		return responseBuilder.build();
	}

	/**
	 * Responsible to update a ServiceItem
	 * 
	 * @param ServiceItem
	 * @return StatusMessageDTO
	 */
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ApiDocConstant.UPDATE_SERVICE_ITEM_DESC, value = ApiDocConstant.UPDATE_SERVICE_ITEM_SUMMARY, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = ApiDocConstant.UPDATE_SERVICE_ITEM_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR),
			@ApiResponse(code = 522, message = GENERAL_FIELD_NOT_UNIQUE) })
	public Response updateServiceItem(
			@ApiParam(required = true, value = ApiDocConstant.SERVICE_ITEM_PAYLOAD) ServiceItemDTO serviceItemDTO) {
		LOGGER.debug("<<-- Enter updateServiceItem(ServiceItemDTO serviceItemDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(serviceItemService
				.updateServiceItem(serviceItemDTO));
		return responseBuilder.build();
	}
	
	/**
	 * Responsible to fetch all Service Components attached to a ServiceItem
	 * 
	 * @param identity
	 * @return Fetched ServiceComponents
	 */
	@POST
	@Path("attached-service-components")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ApiDocConstant.GET_ATTACHED_SERVICE_COMPONENTS_DESC, value = ApiDocConstant.GET_ATTACHED_SERVICE_COMPONENTS_SUMMARY, response = EntityDTO.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = ApiDocConstant.GET_ATTACHED_SERVICE_COMPONENTS_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID) })
	public Response getAttachedServiceComponents(
			@ApiParam(name = "serviceItemIdentityFields", required = true, value = "ServiceItem Identify Fields") IdentityDTO serviceItemIdentity) {
		LOGGER.debug("<<-- Enter getAttachedServiceComponents(IdentityDTO serviceItemIdentity) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(serviceItemService
				.getAttachedServiceComponents(serviceItemIdentity));
		return responseBuilder.build();
	}

	/**
	 * Responsible to delete a ServiceItem
	 * 
	 * @param ServiceItem
	 * @return Success message
	 */
	//@DELETE
	//@Path("delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = ApiDocConstant.DELETE_SERVICE_ITEM_DESC, value = ApiDocConstant.DELETE_SERVICE_ITEM_SUMMARY, response = StatusMessageDTO.class)
	public Response deleteServiceItem(
			@ApiParam(required = true, value = ApiDocConstant.SERVICE_ITEM_PAYLOAD) IdentityDTO serviceItemIdentifier) {
		LOGGER.debug("<<-- Enter deleteServiceItem(IdentityDTO serviceItem) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(serviceItemService
				.deleteServiceItem(serviceItemIdentifier));
		return responseBuilder.build();
	}
}
