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

import static com.pcs.datasource.doc.constants.ResourceConstants.COMMAND_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_SOURCE_ID;
import static com.pcs.datasource.doc.constants.ResourceConstants.DEVICE_SOURCE_ID_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.END_TIME_MS;
import static com.pcs.datasource.doc.constants.ResourceConstants.END_TIME_MS_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FAILURE_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_START_END_TIME;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_ASSERTION_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.SOURCE_IDS_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.START_TIME_MS;
import static com.pcs.datasource.doc.constants.ResourceConstants.START_TIME_MS_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.WRITE_BACK_RESP_PAYLOAD;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GENERIC_PROCESS_COMMAND_DESC;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GENERIC_PROCESS_COMMAND_FAILURE;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GENERIC_PROCESS_COMMAND_SUCCESS_RESPONSE;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GENERIC_PROCESS_COMMAND_SUMMARY;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GET_ALL_QUEUED_COMMANDS_DESC;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GET_ALL_QUEUED_COMMANDS_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GET_ALL_QUEUED_COMMANDS_SUMMARY;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GET_WB_LOGS_DESC;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GET_WB_LOGS_GRAPH_DESC;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GET_WB_LOGS_GRAPH_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GET_WB_LOGS_GRAPH_SUMMARY;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.GET_WB_LOGS_SUMMARY;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.UPDATE_WB_DESC;
import static com.pcs.datasource.doc.constants.WriteBackResourceConstants.UPDATE_WB_SUMMARY;
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

import com.pcs.datasource.doc.constants.ResourceConstants;
import com.pcs.datasource.doc.constants.WriteBackResourceConstants;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.dto.writeback.DeviceCommand;
import com.pcs.datasource.dto.writeback.WriteBackCommand;
import com.pcs.datasource.dto.writeback.WriteBackLog;
import com.pcs.datasource.services.WriteBackCommandService;
import com.pcs.datasource.services.WriteBackLogService;
import com.pcs.datasource.services.utils.SubscriptionUtility;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * Resource for write back related services
 * 
 * @author pcseg199
 * @date May 19, 2015
 * @since galaxy-1.0.0
 */
@Path("/write_back")
@Component
@Api("Write Back(general)")
public class WriteBackResource {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(WriteBackResource.class);

	@Autowired
	WriteBackLogService writebackLogService;

	@Autowired
	private SubscriptionUtility subscriptionUtility;

	@Autowired
	private WriteBackCommandService wbCommandService;

	@POST
	@Path("/logs")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_WB_LOGS_SUMMARY,notes = GET_WB_LOGS_DESC,
	        response = WriteBackLog.class,responseContainer = "List")
	@ApiResponses(
	        value = {
	                @ApiResponse(
	                        code = SC_OK,
	                        message = WriteBackResourceConstants.GET_WB_LOGS_SUCCESS_RESP),
	                @ApiResponse(code = 500,
	                        message = GENERAL_DATA_NOT_AVAILABLE),
	                @ApiResponse(
	                        code = 505,
	                        message = ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 508,
	                        message = ResourceConstants.GENERAL_FIELD_INVALID),
	                @ApiResponse(code = 754,
	                        message = ResourceConstants.GENERAL_START_END_TIME)})
	public Response getLogsOfSubscription(
	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = START_TIME_MS,required = true,
	                defaultValue = START_TIME_MS_SAMPLE,
	                example = START_TIME_MS_SAMPLE) @QueryParam("start_time") Long startTime,
	        @ApiParam(value = END_TIME_MS,required = true,
	                defaultValue = END_TIME_MS_SAMPLE,
	                example = END_TIME_MS_SAMPLE) @QueryParam("end_time") Long endTime,
	        @ApiParam(value = SOURCE_IDS_PAYLOAD,required = true) List<String> sourceIds) {

		ResponseBuilder responseBuilder = status(Response.Status.OK);

		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);

		List<WriteBackLog> allLogs = writebackLogService.getLogsOfSubscription(
		        sourceIds, subscription, startTime, endTime);

		GenericEntity<List<WriteBackLog>> entity = new GenericEntity<List<WriteBackLog>>(
		        allLogs) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@GET
	@Path("/logs/graph")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_WB_LOGS_GRAPH_SUMMARY,
	        notes = GET_WB_LOGS_GRAPH_DESC,response = WriteBackLog.class,
	        responseContainer = "List")
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = GET_WB_LOGS_GRAPH_SUCCESS_RESP),
	                @ApiResponse(code = 500,
	                        message = GENERAL_DATA_NOT_AVAILABLE),
	                @ApiResponse(code = 505,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	                @ApiResponse(code = 754,message = GENERAL_START_END_TIME)})
	public Response getAllLogsWithRelation(
	        @ApiParam(value = JWT_ASSERTION,required = true,
	                defaultValue = JWT_ASSERTION_SAMPLE,
	                example = JWT_ASSERTION_SAMPLE) @HeaderParam("x-jwt-assertion") String jwtObject,
	        @ApiParam(value = START_TIME_MS,required = true,
	                defaultValue = START_TIME_MS_SAMPLE,
	                example = START_TIME_MS_SAMPLE) @QueryParam("start_time") Long startTime,
	        @ApiParam(value = END_TIME_MS,required = true,
	                defaultValue = END_TIME_MS_SAMPLE,
	                example = END_TIME_MS_SAMPLE) @QueryParam("end_time") Long endTime) {
		ResponseBuilder responseBuilder = status(Response.Status.NOT_IMPLEMENTED);
		// Subscription subscription = subscriptionUtility
		// .getSubscription(jwtObject);
		// JSONArray jsonArray = writebackLogService.getAllLogsWithRelation(
		// subscription.getSubId(), startTime, endTime);
		// responseBuilder.entity(jsonArray.toString());
		return responseBuilder.build();
	}

	@GET
	@Path("/{source_id}/queued_commands")
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_QUEUED_COMMANDS_SUMMARY,
	        notes = GET_ALL_QUEUED_COMMANDS_DESC,
	        response = DeviceCommand.class,responseContainer = "List")
	@ApiResponses(value = {
	@ApiResponse(code = SC_OK,message = GET_ALL_QUEUED_COMMANDS_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response getCommandsQueued(
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = ResourceConstants.DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		List<DeviceCommand> currentExecuting = writebackLogService
		        .getCurrentExecuting(sourceId);
		GenericEntity<List<DeviceCommand>> entity = new GenericEntity<List<DeviceCommand>>(
		        currentExecuting) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	@PUT
	@Path("/{source_id}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_WB_SUMMARY,notes = UPDATE_WB_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 2001,message = GENERAL_FAILURE_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response updateWriteBackResponse(
	        @ApiParam(value = DEVICE_SOURCE_ID,required = true,
	                defaultValue = DEVICE_SOURCE_ID_SAMPLE,
	                example = DEVICE_SOURCE_ID_SAMPLE) @PathParam("source_id") String sourceId,
	        @ApiParam(value = WRITE_BACK_RESP_PAYLOAD,required = true) WriteBackCommand writeBackCommand) {
		ResponseBuilder responseBuilder = status(Response.Status.OK);
		StatusMessageDTO updateWriteBack = writebackLogService.updateWriteBack(
		        sourceId, writeBackCommand);
		responseBuilder.entity(updateWriteBack);
		return responseBuilder.build();
	}

	/**
	 * Method to process command request from the system
	 * 
	 * @param CommandExecutorDTO
	 * @return Produces:JSON Sample JSON : {"id":
	 *         "599a2ec0-d697-11e4-8562-49965a098945"}
	 */

	@POST
	@Path("/commands")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	@ApiOperation(value = GENERIC_PROCESS_COMMAND_SUMMARY,
	        notes = GENERIC_PROCESS_COMMAND_DESC,
	        response = WriteBackCommand.class)
	@ApiResponses(
	        value = {
	                @ApiResponse(code = SC_OK,
	                        message = GENERIC_PROCESS_COMMAND_SUCCESS_RESPONSE),
	                @ApiResponse(code = 500,
	                        message = GENERAL_FIELD_NOT_SPECIFIED),
	                @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	                @ApiResponse(code = 2001,
	                        message = GENERIC_PROCESS_COMMAND_FAILURE)})
	public Response processCommand(@ApiParam(value = COMMAND_PAYLOAD,
	        required = true) List<WriteBackCommand> writeBackCommand) {
		LOGGER.debug("processCommand starts ");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(wbCommandService
		        .processCommands(writeBackCommand));
		LOGGER.debug("<<-- Exit processCommand-->>");
		return responseBuilder.build();
	}

}
