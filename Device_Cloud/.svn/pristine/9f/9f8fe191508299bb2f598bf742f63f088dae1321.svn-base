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

import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.DEREGISTER_DS_DESC;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.DEREGISTER_DS_SUMMARY;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.DS_EXISTS_DESC;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.DS_EXISTS_SUMMARY;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_ALL_DS_DESC;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_ALL_DS_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_ALL_DS_SUMMARY;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_PARAMETERS_DESC;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_PARAMETERS_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_PARAMETERS_SUMMARY;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_SUBSCRIPTION_CONTEXT_DESC;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_SUBSCRIPTION_CONTEXT_SUCCESS_RES;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_SUBSCRIPTION_CONTEXT_SUMMARY;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_SUBSCRIPTION_DESC;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_SUBSCRIPTION_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.GET_SUBSCRIPTION_SUMMARY;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.PUBLISH_DESC;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.PUBLISH_SUMMARY;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.REGISTER_DS_DESC;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.REGISTER_DS_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.REGISTER_DS_SUMMARY;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.UPDATE_REGISTER_DS_DESC;
import static com.pcs.datasource.doc.constants.DataSourceResourceConstants.UPDATE_REGISTER_DS_SUMMARY;
import static com.pcs.datasource.doc.constants.ResourceConstants.DATASOURCE_DOES_NOT_EXIST;
import static com.pcs.datasource.doc.constants.ResourceConstants.DS;
import static com.pcs.datasource.doc.constants.ResourceConstants.DS_NAMES_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DS_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.DS_SAMPLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.ResourceConstants.MESSAGE_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.PERSISTENCE_ERROR;
import static com.pcs.datasource.doc.constants.ResourceConstants.WEBSOCKET_CLIENT;
import static com.pcs.datasource.doc.constants.ResourceConstants.WEBSOCKET_CLIENT_SAMPLE;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.ws.rs.Consumes;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.DatasourceDTO;
import com.pcs.datasource.dto.DatasourceStatusDTO;
import com.pcs.datasource.dto.MessageDTO;
import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.dto.SubscriptionContext;
import com.pcs.datasource.dto.SubscriptionDTO;
import com.pcs.datasource.services.DatasourceRegistrationService;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.enums.Status;

/**
 * This class is responsible for managing all services related to Dataservices
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 24 Jan 2015
 */
@Path("/ds")
@Component
@Api("Data Source")
public class DataSourceResource {

	@Autowired
	DatasourceRegistrationService datasourceRegistrationService;

	/**
	 * method to register a data source name
	 * 
	 * @param datasourceDTO
	 *            :JSON Sample JSON :{ "datasourceName": "G2021_Device",
	 *            "parameters": [ { "name": "lat", "dataType": "number" }, {
	 *            "name": "lng", "dataType": "number"} ] }
	 * @return Produces:JSON Sample JSON :{ "status": "SUCCESS",
	 *         "uniqueDatasourceName": "G2021_Device_51" }
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = REGISTER_DS_SUMMARY, notes = REGISTER_DS_DESC, response = DatasourceStatusDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = REGISTER_DS_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR),
			@ApiResponse(code = 503, message = GENERAL_DATA_NOT_AVAILABLE)})
	public Response registerDatasource(@ApiParam(value = DS_PAYLOAD, required = true) DatasourceDTO datasourceDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		DatasourceStatusDTO datasourceStatusDTO = datasourceRegistrationService
		        .registerDatasource(datasourceDTO);
		responseBuilder.entity(datasourceStatusDTO);
		return responseBuilder.build();
	}

	/**
	 * method to update a data source
	 * 
	 * @param datasourceDTO
	 *            :JSON Sample JSON :{ "datasourceName": "G2021_Device",
	 *            "parameters": [ { "name": "lat", "dataType": "number" }, {
	 *            "name": "lng", "dataType": "number"} ] }
	 * @return Produces:JSON Sample JSON :{"status": "SUCCESS"}
	 */
	@PUT
	@Path("/{datasource_name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = UPDATE_REGISTER_DS_SUMMARY, notes = UPDATE_REGISTER_DS_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 503, message = PERSISTENCE_ERROR),
			@ApiResponse(code = 750, message = DATASOURCE_DOES_NOT_EXIST)})
	public Response updateDatasource(@ApiParam(value = DS_PAYLOAD, required = true) DatasourceDTO datasourceDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = datasourceRegistrationService
		        .updateDatasource(datasourceDTO);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	/**
	 * method to get all the datasource names registered with Galaxy
	 * 
	 * @return Produces:JSON Sample JSON : [ { "id": 1, "datasourceName":
	 *         "newName" }, { "id": 2, "datasourceName": "Sensor" }, { "id": 3,
	 *         "datasourceName": "G2021" } ]
	 */
	@GET
	@Path("/datasources")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_DS_SUMMARY, notes = GET_ALL_DS_DESC, response = DatasourceDTO.class, responseContainer="List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_DS_SUCCESS_RESP)})
	public Response getAllDatasource() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<DatasourceDTO> datasourceDTOs = datasourceRegistrationService
		        .getAllDatasource();
		responseBuilder.entity(datasourceDTOs);
		return responseBuilder.build();
	}

	/**
	 * method to return parameters of a datasource These parameters are stored
	 * into Galaxy at the time of the datasource registration
	 * 
	 * @param datasource_name
	 *            - unique datasource name
	 * @return Produces:JSON Sample JSON :[ { "id": 47, "name": "lat",
	 *         "dataType": "number" }, { "id": 48, "name": "lng", "dataType":
	 *         "number" } ]
	 */
	@GET
	@Path("/{datasource_name}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_PARAMETERS_SUMMARY, notes = GET_PARAMETERS_DESC, response = ParameterDTO.class, responseContainer="List")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_PARAMETERS_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 750, message = DATASOURCE_DOES_NOT_EXIST)})
	public List<ParameterDTO> getParameters(
			@ApiParam(value = DS, required = true, defaultValue = DS_SAMPLE, example = DS_SAMPLE)
			@PathParam("datasource_name") String datasource_name) {
		List<ParameterDTO> parameterDTOs = datasourceRegistrationService
		        .getDatasourceParameters(datasource_name);
		return parameterDTOs;
	}

	/**
	 * method to get a subscription to a datasource destination this method will
	 * return a webscoket connection string and the destination for the
	 * datasource name
	 * 
	 * @param datasourceNames
	 *            - list of unique datasource names :JSON Sample JSON :[
	 *            "location_45", "Temperature-Sensor_12", "newName_48",
	 *            "Temperature-Sensor_10", "Temperature-Sensor_15" ]
	 * @return Produces:JSON Sample JSON : { "webSocketUrl":
	 *         "ws://10.234.60.12:8001/jms", "destination":
	 *         "//topic//Temperature-Sensor_20" }
	 */
	@POST
	@Path("/subscribe")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_SUBSCRIPTION_SUMMARY, notes = GET_SUBSCRIPTION_DESC, response = SubscriptionDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_SUBSCRIPTION_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 500, message = GENERAL_DATA_NOT_AVAILABLE),
			@ApiResponse(code = 750, message = DATASOURCE_DOES_NOT_EXIST)})
	public Response getSubscription(@ApiParam(value = DS_NAMES_PAYLOAD, required = true) SortedSet<String> datasourceNames,
									@ApiParam(value = WEBSOCKET_CLIENT, required = true, defaultValue = WEBSOCKET_CLIENT_SAMPLE, example = WEBSOCKET_CLIENT_SAMPLE)
									@QueryParam("websocketclient") String websocketClient) {
		SortedSet<String> dsNames = new TreeSet<String>(
		        String.CASE_INSENSITIVE_ORDER);
		dsNames.addAll(datasourceNames);

		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		SubscriptionDTO subscriptionDTO = datasourceRegistrationService
		        .subscribe(dsNames, websocketClient);
		responseBuilder.entity(subscriptionDTO);
		return responseBuilder.build();
	}

	/**
	 * method to publish data to a topic
	 */
	@POST
	@Path("/publish/{datasource_name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = PUBLISH_SUMMARY, notes = PUBLISH_DESC)
	@ApiResponses(value = {
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508, message = GENERAL_FIELD_INVALID),
			@ApiResponse(code = 750, message = DATASOURCE_DOES_NOT_EXIST)})
	public Response publish(@ApiParam(value = DS, required = true, defaultValue = DS_SAMPLE, example = DS_SAMPLE)
	@PathParam("datasource_name") String datasourceName,
	@ApiParam(value = MESSAGE_PAYLOAD, required = true) MessageDTO messageDTO) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO status = datasourceRegistrationService.publish(datasourceName, messageDTO);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}

	@GET
	@Path("/context/{datasource_name}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_SUBSCRIPTION_CONTEXT_SUMMARY, notes = GET_SUBSCRIPTION_CONTEXT_DESC, response=SubscriptionContext.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_SUBSCRIPTION_CONTEXT_SUCCESS_RES),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 750, message = DATASOURCE_DOES_NOT_EXIST)})
	public Response getSubscriptionContexts(
			@ApiParam(value = DS, required = true, defaultValue = DS_SAMPLE, example = DS_SAMPLE)
			@PathParam("datasource_name") String datasourceName) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		SubscriptionContext context = datasourceRegistrationService
		        .getSubscriptionContexts(datasourceName);
		responseBuilder.entity(context);
		return responseBuilder.build();
	}

	/**
	 * method to deregister a datasource
	 * 
	 * @param datasource_name
	 *            - unique datasource name
	 * @return Produces:JSON Sample JSON :{"status": "SUCCESS"}
	 */
	@POST
	@Path("/deregister/{datasource_name}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = DEREGISTER_DS_SUMMARY , notes = DEREGISTER_DS_DESC, response=StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 750, message = DATASOURCE_DOES_NOT_EXIST)})
	public Response deRegister(
			@ApiParam(value = DS, required = true, defaultValue = DS_SAMPLE, example = DS_SAMPLE)
			@PathParam("datasource_name") String datasource_name) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO statusMessageDTO = datasourceRegistrationService
		        .deRegister(datasource_name);
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

	/**
	 * method to check the datasource name is exist or not.
	 * 
	 * @param datasource_name
	 * @return StatusMessageDTO
	 */
	@GET
	@Path("/{datasource_name}/isexist")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = DS_EXISTS_SUMMARY , notes = DS_EXISTS_DESC, response=StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505, message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response isDatasourceExist(
			@ApiParam(value = DS, required = true, defaultValue = DS_SAMPLE, example = DS_SAMPLE)
			@PathParam("datasource_name") String datasource_name) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Boolean isExist = datasourceRegistrationService
		        .isDatasourceExist(datasource_name);
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		if (isExist) {
			statusMessageDTO.setStatus(Status.SUCCESS);
		}
		responseBuilder.entity(statusMessageDTO);
		return responseBuilder.build();
	}

}
