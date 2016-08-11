
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.ccd.resource;

import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.FETCH_FAULT_EVENT_RESPONSE_PAYLOAD;
import static com.pcs.ccd.doc.constants.FCResourceConstants.GET_FAULT_EVENT_RESPONSE_SUCCESS_RESP;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.GET_FAULT_EVENT_RESPONSE_DESC;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.GET_FAULT_EVENT_RESPONSE_SUMMARY;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.ccd.bean.FaultResponse;
import com.pcs.ccd.services.FCResponseService;

/**
 * This class is responsible for all apis related to external response to be received from CCD
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 24, 2016
 */

@Path("receiveData")
@Component
@Api("Receive FC Response")
public class FCResponseResource {
	
	@Autowired
	FCResponseService fcResponseService;
	
	@POST
	@Consumes(APPLICATION_JSON)
	public Response getFCResponse(FaultResponse faultResponse){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO status = fcResponseService.publishResponse(faultResponse);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}
	
	@GET
	@Path("/{fault_event_id}/response")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_FAULT_EVENT_RESPONSE_SUMMARY, notes = GET_FAULT_EVENT_RESPONSE_DESC, response = FaultResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_FAULT_EVENT_RESPONSE_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response getFaultEventResponse(@ApiParam(value = FETCH_FAULT_EVENT_RESPONSE_PAYLOAD, required = true) @PathParam("fault_event_id") String faultEventId){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		FaultResponse faultResponse = fcResponseService.getFaultEventResponse(faultEventId);
		responseBuilder.entity(faultResponse);
		return responseBuilder.build();
	}
	

}
