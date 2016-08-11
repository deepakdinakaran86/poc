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

package com.pcs.alpine.resources;

import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.SEND_EMAIL_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.SEND_EMAIL_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.SEND_EMAIL_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.SEND_EMAIL_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.SEND_SMS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.SEND_SMS_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.SEND_SMS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.SEND_SMS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.SMS_STATISTICS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.SMS_STATISTICS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.SMS_STATISTICS_SUMMARY;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.email.dto.EmailDTO;
import com.pcs.alpine.commons.sms.dto.SMSDTO;
import com.pcs.alpine.services.NotificationService;
import com.pcs.alpine.services.dto.EntityDTO;

/**
 * 
 * @description This is the resource class for notification services
 * @author Daniela (PCSEG191)
 * @date 28 Sep 2015
 * @since galaxy-1.0.0
 */
@Path("/notification")
@Component
@Api("Notification")
public class NotificationResource {

	@Autowired
	private NotificationService notificationService;

	/**
	 * @description This is a default method to get the header options,all
	 *              resource layers should have this method
	 * 
	 */
	@OPTIONS
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "", hidden = true)
	public Response defaultOptions() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.header("ALLOW", "HEAD,GET,PUT,DELETE,OPTIONS");
		return responseBuilder.build();
	}

	@POST
	@Path("email")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = SEND_EMAIL_DESC, value = SEND_EMAIL_SUMMARY, response = EntityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = SEND_EMAIL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response sendEmail(
			@ApiParam(required = true, value = SEND_EMAIL_PAYLOAD) EmailDTO emailDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		com.pcs.alpine.commons.dto.StatusMessageDTO statusMessageDTO = notificationService
				.sendEmail(emailDTO);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveUserApiManager(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		com.pcs.alpine.commons.dto.StatusMessageDTO statusMessageDTO = notificationService
				.createUserAPIManager(username, password);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("subscribeSMS")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response subscribe(SMSDTO smsDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = notificationService.sendSMS(smsDTO);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("sendSMS")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = SEND_SMS_DESC, value = SEND_SMS_SUMMARY, response = EntityDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = SEND_SMS_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response sendSMS(
			@ApiParam(required = true, value = SEND_SMS_PAYLOAD) SMSDTO smsDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = notificationService.sendSMS(smsDTO);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	@POST
	@Path("receiveSMS")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response receiveSMS(SMSDTO smsDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = notificationService.sendSMS(smsDTO);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}
	
	@GET
	@Path("SMS-statistics")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = SMS_STATISTICS_DESC, value = SMS_STATISTICS_SUMMARY, response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = SMS_STATISTICS_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE) })
	public Response getSMStatistics() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(notificationService.getSMStatistics());
		return responseBuilder.build();
	}
}
