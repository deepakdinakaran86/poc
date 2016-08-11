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

import static com.pcs.datasource.doc.constants.PhysicalQuantityResourceConstants.GET_ALL_PHYSICAL_QUANTITY_DESC;
import static com.pcs.datasource.doc.constants.PhysicalQuantityResourceConstants.GET_ALL_PHYSICAL_QUANTITY_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.PhysicalQuantityResourceConstants.GET_ALL_PHYSICAL_QUANTITY_SUMMARY;
import static com.pcs.datasource.doc.constants.PhysicalQuantityResourceConstants.GET_PHYSICAL_QUANTITY_DESC;
import static com.pcs.datasource.doc.constants.PhysicalQuantityResourceConstants.GET_PHYSICAL_QUANTITY_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.PhysicalQuantityResourceConstants.GET_PHYSICAL_QUANTITY_SUMMARY;
import static com.pcs.datasource.doc.constants.PhysicalQuantityResourceConstants.INSERT_PHYSICAL_QUANTITY_DESC;
import static com.pcs.datasource.doc.constants.PhysicalQuantityResourceConstants.INSERT_PHYSICAL_QUANTITY_SUMMARY;
import static com.pcs.datasource.doc.constants.PhysicalQuantityResourceConstants.UPDATE_PHYSICAL_QUANTITY_DESC;
import static com.pcs.datasource.doc.constants.PhysicalQuantityResourceConstants.UPDATE_PHYSICAL_QUANTITY_SUMMARY;
import static com.pcs.datasource.doc.constants.ResourceConstants.DATA_TYPE;
import static com.pcs.datasource.doc.constants.ResourceConstants.DATA_TYPE_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.FIELD_ALREADY_EXIST;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.PERSISTENCE_ERROR;
import static com.pcs.datasource.doc.constants.ResourceConstants.PHYSICAL_QUANTITY;
import static com.pcs.datasource.doc.constants.ResourceConstants.PHYSICAL_QUANTITY_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.PHYSICAL_QUANTITY_SAMPLE;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.PhyQuantityResponse;
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.services.PhyQuantityService;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This resource class is responsible for defining all the services related to
 * data of a physical quantity details (maps physical quantity with column
 * family name). This class is responsible for persisting,updating,deleting
 * (soft delete) and fetching the physical quantity details.
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */
@Path("/phy_quantity")
@Component
@Api("Physical Quantity")
public class PhyQuantityResource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PhyQuantityResource.class);

	@Autowired
	private PhyQuantityService phyQuantityService;


	/**
	 * Method to get all active physical quantities optional filter by data type
	 * 
	 * @param dataType
	 * @return Produces:JSON Sample JSON : [ { "dataStore": "Temperature",
	 *         "name": "Temperature" }, { "dataStore": "Pressure", "name":
	 *         "Pressure" }, { "dataStore": "Heat", "name": "Heat" } ]
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_PHYSICAL_QUANTITY_SUMMARY, notes = GET_ALL_PHYSICAL_QUANTITY_DESC, response = PhysicalQuantity.class,responseContainer="List" )
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_PHYSICAL_QUANTITY_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE)}
			)
	public Response getAllPhysicalQuantity(
			@ApiParam(value = DATA_TYPE, required = false, defaultValue = DATA_TYPE_SAMPLE, example = DATA_TYPE_SAMPLE)
			@QueryParam("data_type") String dataType) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<PhysicalQuantity> allPhysicalQunatity = phyQuantityService
				.getAllPhyQunatity(dataType);
		responseBuilder.entity(allPhysicalQunatity);
		return responseBuilder.build();
	}

	/**
	 * Method to get ACTIVE physical quantity details
	 * 
	 * @param phyQuantityName
	 * @return Produces:JSON Sample JSON : { "dataStore": "Speed", "name":
	 *         "Speed" }
	 */
	@GET
	@Path("{physical_quantity}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_PHYSICAL_QUANTITY_SUMMARY, notes = GET_PHYSICAL_QUANTITY_DESC, response = PhysicalQuantity.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_PHYSICAL_QUANTITY_SUCCESS_RESP),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED)}
			)
	public Response getPhysicalQuantityDetails(
			@ApiParam(value = PHYSICAL_QUANTITY, required = true, defaultValue = PHYSICAL_QUANTITY_SAMPLE, example = PHYSICAL_QUANTITY_SAMPLE)
			@PathParam("physical_quantity") String phyQuantityName) {
		LOGGER.debug("PhysicalQuantityService.getPhysicalQuantityDetails() Start ");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		PhysicalQuantity phyQuantity = phyQuantityService
				.getPhyQuantity(phyQuantityName);
		responseBuilder.entity(phyQuantity);
		LOGGER.debug("<<-- Exit getPhysicalQuantityDetails-->>");
		return responseBuilder.build();
	}

	/**
	 * Method to persist physical quantities, units are not manadatory
	 * 
	 * @param PhysicalQuantity
	 * @return Produces:JSON Sample JSON : { "status": "SUCCESS" }
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = INSERT_PHYSICAL_QUANTITY_SUMMARY, notes = INSERT_PHYSICAL_QUANTITY_DESC, response = PhyQuantityResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)}
			)
	public Response persistPhyQuantity(@ApiParam(value = PHYSICAL_QUANTITY_PAYLOAD, required = true) PhysicalQuantity phyQuantity) {
		LOGGER.debug("PhysicalQuantityService.savePhysicalQuantity() Starts with physicalQuantity {} ");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		PhyQuantityResponse phyQuantityResponse = phyQuantityService
				.createPhyQuantity(phyQuantity);
		responseBuilder.entity(phyQuantityResponse);
		LOGGER.debug("<<-- Exit persistPhyQuantity-->>");
		return responseBuilder.build();
	}

	/**
	 * Method to update physical quantities, units are not mandatory
	 * 
	 * @param PhysicalQuantity
	 * @return Produces:JSON Sample JSON : { "status": "SUCCESS" }
	 */
	@PUT
	@Path("/{physical_quantity}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = UPDATE_PHYSICAL_QUANTITY_SUMMARY, notes = UPDATE_PHYSICAL_QUANTITY_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)}
			)
	public Response updatePhyQuantity(
			@ApiParam(value = PHYSICAL_QUANTITY, required = true, defaultValue = PHYSICAL_QUANTITY_SAMPLE, example = PHYSICAL_QUANTITY_SAMPLE)
			@PathParam("physical_quantity") String phyQuantityName,
			@ApiParam(value = PHYSICAL_QUANTITY_PAYLOAD, required = true) PhysicalQuantity phyQuantity
			) {
		LOGGER.debug("PhysicalQuantityService.updatePhyQuantity()");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = phyQuantityService
				.updatePhyQuantity(phyQuantityName, phyQuantity);
		responseBuilder.entity(statusMessageDTO);
		LOGGER.debug("<<-- Exit persistPhyQuantity-->>");
		return responseBuilder.build();
	}

	/**
	 * Method to delete (soft delete) physical quantities.
	 * 
	 * @param uuid
	 * @return Produces:JSON Sample JSON : { "status": "SUCCESS" }
	 */
	@DELETE
	@Path("{physical_quantity}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = UPDATE_PHYSICAL_QUANTITY_SUMMARY, notes = UPDATE_PHYSICAL_QUANTITY_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 507, message = FIELD_ALREADY_EXIST),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR)}
			)
	public Response deletePhyQuantity(
			@ApiParam(value = PHYSICAL_QUANTITY, required = true, defaultValue = PHYSICAL_QUANTITY_SAMPLE, example = PHYSICAL_QUANTITY_SAMPLE)
			@PathParam("physical_quantity") String phyQuantity) {
		LOGGER.debug("PhysicalQuantityService.deletePhyQuantity()");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = phyQuantityService
				.deletePhyQuantity(phyQuantity);
		responseBuilder.entity(statusMessageDTO);
		LOGGER.debug("<<-- Exit persistPhyQuantity-->>");
		return responseBuilder.build();
	}

}
