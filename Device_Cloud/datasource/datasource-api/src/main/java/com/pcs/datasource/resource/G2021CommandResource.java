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

import static com.pcs.datasource.doc.constants.G2021ResourceConstants.G2021_PROCESS_COMMAND_DESC;
import static com.pcs.datasource.doc.constants.G2021ResourceConstants.G2021_PROCESS_COMMAND_FAILURE;
import static com.pcs.datasource.doc.constants.G2021ResourceConstants.G2021_PROCESS_COMMAND_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.G2021ResourceConstants.G2021_PROCESS_COMMAND_SUMMARY;
import static com.pcs.datasource.doc.constants.ResourceConstants.COMMAND_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_SOURCE_ID;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_SOURCE_ID_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.writeback.Command;
import com.pcs.datasource.dto.writeback.WriteBackDeviceResponse;
import com.pcs.datasource.services.G2021CommandService;

/**
 * This resource class is responsible for defining all the services related to
 * CmdExecutor of a Device communicating to the system. This class is
 * responsible for process command request from the system.
 *
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

@Path("/g2021")
@Component
@Api("G2021 Write Back Invocation")
public class G2021CommandResource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(G2021CommandResource.class);

	@Autowired
	private G2021CommandService g2021CommandService;

	/**
	 * Method to process command request from the system
	 *
	 * @param CommandExecutorDTO
	 * @return Produces:JSON Sample JSON : {"id":
	 *         "599a2ec0-d697-11e4-8562-49965a098945"}
	 */

	@POST
	@Path("/commands/{source_id}")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	@ApiOperation(value = G2021_PROCESS_COMMAND_SUMMARY, notes = G2021_PROCESS_COMMAND_DESC, response = WriteBackDeviceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = G2021_PROCESS_COMMAND_SUCCESS_RESPONSE),
			@ApiResponse(code = 500, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 2001, message = G2021_PROCESS_COMMAND_FAILURE) })
	public Response processCommand(
			@ApiParam(value = DEVICE_SOURCE_ID, required = true, defaultValue = DEVICE_SOURCE_ID_SAMPLE, example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId,
			@ApiParam(value = COMMAND_PAYLOAD, required = true) List<Command> commands) {
		LOGGER.debug("processCommand starts ");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		WriteBackDeviceResponse response = g2021CommandService.processCommands(
				sourceId, commands);
		responseBuilder.entity(response);
		LOGGER.debug("<<-- Exit processCommand-->>");
		return responseBuilder.build();
	}
}
