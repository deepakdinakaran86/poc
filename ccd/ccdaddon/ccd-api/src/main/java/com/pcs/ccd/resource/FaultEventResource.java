
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

import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_ALL_EQUIPMENTS_PAYLOAD;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.GET_ALL_TENANT_FAULT_EVENT_DATA_DESC;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.GET_ALL_TENANT_FAULT_EVENT_DATA_SUCCESS_RESP;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.GET_ALL_TENANT_FAULT_EVENT_DATA_SUMMARY;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.GET_FAULT_EVENT_DATA_DESC;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.GET_FAULT_EVENT_DATA_SUMMARY;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.GET_LATEST_FAULT_EVENT_IF_EXISTS_DESC;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.GET_LATEST_FAULT_EVENT_IF_EXISTS_SUMMARY;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.PERSIST_FAULT_EVENT_DESC;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.PERSIST_FAULT_EVENT_SUMMARY;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.UPDATE_FAULT_EVENT_DESC;
import static com.pcs.ccd.doc.constants.FaultEventResourceConstants.UPDATE_FAULT_EVENT_SUMMARY;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_TRUE_RESP;
import static com.pcs.ccd.doc.constants.ResourceConstants.GE_LATEST_FAULT_EVENT_PAYLOAD;
import static com.pcs.ccd.doc.constants.ResourceConstants.PERSIST_FAULT_EVENT_PAYLOAD;
import static com.pcs.ccd.doc.constants.ResourceConstants.UPDATE_FAULT_EVENT_PAYLOAD;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.ccd.bean.FaultData;
import com.pcs.ccd.bean.FaultEventData;
import com.pcs.ccd.bean.FaultEventInfoReq;
import com.pcs.ccd.services.FaultEventService;

/**
 * This class is responsible for managing all apis related to fault event
 * 
 * @author pcseg129(Seena Jyothish)
 * Feb 1, 2016
 */

@Path("/faultEvent")
@Component
@Api("Fault Event")
public class FaultEventResource {
	
	@Autowired
	FaultEventService faultEventService;
	
	@POST
	@Path("/latest")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_LATEST_FAULT_EVENT_IF_EXISTS_SUMMARY, notes = GET_LATEST_FAULT_EVENT_IF_EXISTS_DESC, response = FaultEventData.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_TRUE_RESP)}
			)
	public Response getLatestFaultEvent(@ApiParam(value = GE_LATEST_FAULT_EVENT_PAYLOAD, required = true) FaultEventInfoReq eventInfoReq){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		FaultData eventData = faultEventService.getLatestFaultEventIfExists(eventInfoReq);
		responseBuilder.entity(eventData);
		return responseBuilder.build();
	}
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = PERSIST_FAULT_EVENT_SUMMARY, notes = PERSIST_FAULT_EVENT_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP)}
			)
	public Response persistFaultEvent(
			@ApiParam(value = PERSIST_FAULT_EVENT_PAYLOAD, required = true) FaultData faultData){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO status = faultEventService.persistFaultEvent(faultData);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}
	
	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = UPDATE_FAULT_EVENT_SUMMARY, notes = UPDATE_FAULT_EVENT_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP)}
			)
	public Response updateFaultEvent(
			@ApiParam(value = UPDATE_FAULT_EVENT_PAYLOAD, required = true) FaultData faultData){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO status = faultEventService.updateFaultEvent(faultData);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}
	
	@POST
	@Path("/find")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_FAULT_EVENT_DATA_SUMMARY, notes = GET_FAULT_EVENT_DATA_DESC, response = FaultEventData.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_TRUE_RESP)}
			)
	public Response getFaultEventData(FaultEventInfoReq eventInfoReq){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<FaultEventData> faultEventData = faultEventService.findAllFaultEvent(eventInfoReq);
		GenericEntity<List<FaultEventData>> entity = new GenericEntity<List<FaultEventData>>(
				faultEventData) {
		};
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}
	
	@GET
	@Path("/tenant/{tenant_id}/findall")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_TENANT_FAULT_EVENT_DATA_SUMMARY, notes = GET_ALL_TENANT_FAULT_EVENT_DATA_DESC, response = FaultEventData.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_TENANT_FAULT_EVENT_DATA_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response getAllFaultEvents(@ApiParam(value = GET_ALL_EQUIPMENTS_PAYLOAD, required = true)  @PathParam("tenant_id") String tenantId){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<FaultEventData> faultEventDatas = faultEventService.getAllFaultEvent(tenantId);
		responseBuilder.entity(faultEventDatas);
		return responseBuilder.build();
	}
	
}
