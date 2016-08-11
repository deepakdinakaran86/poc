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

import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.PERSISTENCE_ERROR;
import static com.pcs.datasource.doc.constants.ResourceConstants.PHYSICAL_QUANTITY;
import static com.pcs.datasource.doc.constants.ResourceConstants.PHYSICAL_QUANTITY_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.UNIT_NAME;
import static com.pcs.datasource.doc.constants.ResourceConstants.UNIT_NAME_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.UNIT_PAYLOAD;
import static com.pcs.datasource.doc.constants.UnitResourceConstants.DELETE_UNIT_DESC;
import static com.pcs.datasource.doc.constants.UnitResourceConstants.DELETE_UNIT_SUMMARY;
import static com.pcs.datasource.doc.constants.UnitResourceConstants.GET_UNITS_DESC;
import static com.pcs.datasource.doc.constants.UnitResourceConstants.GET_UNITS_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.UnitResourceConstants.GET_UNITS_SUMMARY;
import static com.pcs.datasource.doc.constants.UnitResourceConstants.INSERT_UNIT_DESC;
import static com.pcs.datasource.doc.constants.UnitResourceConstants.INSERT_UNIT_SUMMARY;
import static com.pcs.datasource.doc.constants.UnitResourceConstants.UPDATE_UNIT_DESC;
import static com.pcs.datasource.doc.constants.UnitResourceConstants.UPDATE_UNIT_SUMMARY;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.Unit;
import com.pcs.datasource.services.UnitService;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This resource class is responsible for defining all the services related to
 * units of physical quantity. This class is responsible for
 * persisting,updating,deleting (soft delete) and fetching units the physical
 * quantity details.
 *
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */
@Path("/units")
@Component
@Api("Unit")
public class UnitResource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UnitResource.class);

	@Autowired
	private UnitService unitService;

	/**
	 * method to save unit information of particular physical quantity
	 *
	 * @param Unit
	 * @return Produces:JSON Sample JSON : { "status": "SUCCESS" }
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = INSERT_UNIT_SUMMARY, notes = INSERT_UNIT_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE)
	})
	public Response createUnit(@ApiParam(value = UNIT_PAYLOAD, required = true) Unit unit) {
		LOGGER.debug("UnitResource.createUnit() Starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = unitService.createUnit(unit);
		responseBuilder.entity(statusMessageDTO);
		LOGGER.debug("<<-- Exit createUnit-->>");
		return responseBuilder.build();
	}

	/**
	 * method to get unit information of particular physical quantity
	 *
	 * @param Unit
	 * @return Produces:JSON Sample JSON : [ { "id":
	 *         "3f7466f0-e69b-11e4-99ea-49965a098945", "name": "Seconds",
	 *         "status": 0 }, { "id": "3eb4d020-e74e-11e4-8c37-49965a098945",
	 *         "name": "Hour", "status": 0 }, { "id":
	 *         "3f7466f1-e69b-11e4-99ea-49965a098945", "name": "Minutes",
	 *         "status": 0 } ]
	 */
	@GET
	@Path("{physical_quantity}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_UNITS_SUMMARY, notes = GET_UNITS_DESC, response = Unit.class, responseContainer="List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_UNITS_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getUnitsOfPhyQty(@ApiParam(value = PHYSICAL_QUANTITY, required = true, defaultValue = PHYSICAL_QUANTITY_SAMPLE, example = PHYSICAL_QUANTITY_SAMPLE)
	@PathParam("physical_quantity") String phyQuantityName) {
		LOGGER.debug("UnitResource.getUnitDeatils() Starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<Unit> units = unitService.getUnitDeatils(phyQuantityName);
		GenericEntity<List<Unit>> unitList = new GenericEntity<List<Unit>>(
				units) {
		};
		responseBuilder.entity(unitList);
		LOGGER.debug("<<-- Exit getUnitDeatils-->>");
		return responseBuilder.build();
	}

	/**
	 * method to update unit information of particular physical quantity
	 *
	 * @param Unit
	 * @return Produces:JSON Sample JSON : { "status": "SUCCESS" }
	 */
	@PUT
	@Path("{physical_quantity}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = UPDATE_UNIT_SUMMARY, notes = UPDATE_UNIT_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE)
	})
	public Response updateUnit(
			@ApiParam(value = PHYSICAL_QUANTITY, required = true, defaultValue = PHYSICAL_QUANTITY_SAMPLE, example = PHYSICAL_QUANTITY_SAMPLE)
			@PathParam("physical_quantity") String PhyQuantityName, 
			@ApiParam(value = UNIT_PAYLOAD, required = true)Unit unit,
			@ApiParam(value = UNIT_NAME, required = true, defaultValue = UNIT_NAME_SAMPLE, example = UNIT_NAME_SAMPLE)
			@QueryParam("unit") String unitName
			) {
		LOGGER.debug("UnitResource.createUnit() Starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = unitService.updateUnit(unitName,
				PhyQuantityName, unit);
		responseBuilder.entity(statusMessageDTO);
		LOGGER.debug("<<-- Exit createUnit-->>");
		return responseBuilder.build();
	}

	/**
	 * method to delete unit information (soft delete) of particular physical
	 * quantity
	 *
	 * @param Unit
	 * @return Produces:JSON Sample JSON : { "status": "SUCCESS" }
	 */
	@DELETE
	@Path("{physical_quantity}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = DELETE_UNIT_SUMMARY, notes = DELETE_UNIT_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE)
	})
	public Response deleteUnit(
			@ApiParam(value = PHYSICAL_QUANTITY, required = true, defaultValue = PHYSICAL_QUANTITY_SAMPLE, example = PHYSICAL_QUANTITY_SAMPLE)
			@PathParam("physical_quantity") String PhyQuantityName,
			@ApiParam(value = UNIT_NAME, required = true, defaultValue = UNIT_NAME_SAMPLE, example = UNIT_NAME_SAMPLE)
			@QueryParam("unit") String unitName
			) {
		LOGGER.debug("UnitResource.deleteUnit() Starts");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = unitService.deleteUnit(unitName,
				PhyQuantityName);
		responseBuilder.entity(statusMessageDTO);
		LOGGER.debug("<<-- Exit deleteUnit-->>");
		return responseBuilder.build();
	}

}
