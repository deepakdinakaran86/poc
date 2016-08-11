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

import static com.pcs.alpine.constants.ApiDocConstant.FIELD_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_LOGS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_LOGS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ALL_LOGS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GET_APP_LOGS_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_APP_LOGS_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GET_APP_LOGS_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.dto.ApplicationAuditDTO;
import com.pcs.alpine.dto.AuditDTO;
import com.pcs.alpine.service.AuditLogService;
import com.pcs.alpine.services.dto.EntityDTO;

/**
 * AuditResource manages Audit related services.
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date July 2016
 * @since alpine-1.0.0
 */
@Path("/audit")
@Component
@Api("Audit-Log")
public class AuditLogResource {

	@Autowired
	AuditLogService auditLogservice;

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_ALL_LOGS_DESC, value = GET_ALL_LOGS_SUMMARY, response = EntityDTO.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = GET_ALL_LOGS_SUCCESS_RESP),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 510, message = FIELD_DATA_NOT_AVAILABLE) })
	public Response getAuditLog() {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<AuditDTO> audits = auditLogservice.getAuditLog();
		GenericEntity<List<AuditDTO>> entity = new GenericEntity<List<AuditDTO>>(
				audits) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}
	
	@GET
	@Path("application")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_APP_LOGS_DESC, value = GET_APP_LOGS_SUMMARY, response = EntityDTO.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = GET_APP_LOGS_SUCCESS_RESP),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 510, message = FIELD_DATA_NOT_AVAILABLE) })
	public Response getAuditLogForApp() {

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<ApplicationAuditDTO> audits = auditLogservice.getAuditLogForApp();
		GenericEntity<List<ApplicationAuditDTO>> entity = new GenericEntity<List<ApplicationAuditDTO>>(
				audits) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

}
