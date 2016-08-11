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

import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_FIND_ALL_DESC;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_FIND_ALL_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_FIND_ALL_SUMMARY;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_FIND_DESC;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_FIND_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_FIND_SUMMARY;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_HISTORY_DESC;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_HISTORY_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_HISTORY_SUMMARY;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_LATEST_DESC;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_LATEST_SUMMARY;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_PERSIST_DESC;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_PERSIST_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.AlarmResourceConstants.ALARM_PERSIST_SUMMARY;
import static com.pcs.datasource.doc.constants.ResourceConstants.ALARM_HISTORY_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.ALARM_LATEST_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.JWT_HEADER_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.SPECIFIC_DATA_NOT_AVAILABLE;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.AlarmDataResponse;
import com.pcs.datasource.dto.AlarmMessage;
import com.pcs.datasource.dto.SearchDTO;
import com.pcs.datasource.services.AlarmService;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for managing all services related to alarms
 * 
 * @author pcseg129(Seena Jyothish) Jul 19, 2015
 */
@Path("/alarm")
@Component
@Api("/Alarm")
public class AlarmResource {
	@Autowired
	AlarmService alarmService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = ALARM_PERSIST_SUMMARY,notes = ALARM_PERSIST_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = ALARM_PERSIST_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 510,message = SPECIFIC_DATA_NOT_AVAILABLE)})
	public Response persistAlarm(AlarmMessage alarmMessage) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO messageDTO = alarmService.saveAlarm(alarmMessage);
		responseBuilder.entity(messageDTO);
		return responseBuilder.build();
	}

	@Path("/find")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = ALARM_FIND_SUMMARY,notes = ALARM_FIND_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = ALARM_FIND_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 510,message = SPECIFIC_DATA_NOT_AVAILABLE)})
	public Response getAlarms(SearchDTO searchDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		AlarmDataResponse alarmDataResponse = alarmService.getAlarms(searchDTO);
		responseBuilder.entity(alarmDataResponse);
		return responseBuilder.build();
	}

	@Path("/findall")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = ALARM_FIND_ALL_SUMMARY,notes = ALARM_FIND_ALL_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = ALARM_FIND_ALL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID),
	        @ApiResponse(code = 510,message = SPECIFIC_DATA_NOT_AVAILABLE)})
	public Response getAllAlarms(SearchDTO searchDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		AlarmDataResponse alarmDataResponse = alarmService
		        .getAllAlarms(searchDTO);
		responseBuilder.entity(alarmDataResponse);
		return responseBuilder.build();
	}

	@Path("/history")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = ALARM_HISTORY_SUMMARY,notes = ALARM_HISTORY_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = ALARM_HISTORY_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID),
	        @ApiResponse(code = 510,message = SPECIFIC_DATA_NOT_AVAILABLE)})
	public Response getAlarmsHistory(@ApiParam(value = ALARM_HISTORY_PAYLOAD,
	        required = true) List<SearchDTO> searchDTOList) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<AlarmDataResponse> alarmDataResponses = alarmService
		        .getAlarmsHistory(searchDTOList);
		responseBuilder.entity(alarmDataResponses);
		return responseBuilder.build();
	}

	@Path("/live")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = ALARM_LATEST_SUMMARY,notes = ALARM_LATEST_DESC,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GENERAL_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = JWT_HEADER_INVALID),
	        @ApiResponse(code = 510,message = SPECIFIC_DATA_NOT_AVAILABLE)})
	public Response getLatestAlarms(@ApiParam(value = ALARM_LATEST_PAYLOAD,
	        required = true) List<SearchDTO> searchDTOList) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<AlarmDataResponse> alarmDataResponses = alarmService
		        .getLatestAlarms(searchDTOList);
		responseBuilder.entity(alarmDataResponses);
		return responseBuilder.build();
	}

}
