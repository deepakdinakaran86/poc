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

import static com.pcs.datasource.doc.constants.AnalyticsResourceConstants.GET_AGGREGATED_DATA_DESC;
import static com.pcs.datasource.doc.constants.AnalyticsResourceConstants.GET_AGGREGATED_DATA_SUCCESS_RESP;
import static com.pcs.datasource.doc.constants.AnalyticsResourceConstants.GET_AGGREGATED_DATA_SUMMARY;
import static com.pcs.datasource.doc.constants.ResourceConstants.ANALYTICS_AGGREGATED_DATA_PAYLOAD;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.datasource.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.Aggregation;
import com.pcs.datasource.services.AnalyticsService;

/**
 * This class is responsible for performing analytics
 * 
 * @author pcseg297(Twinkle)
 * @date April 2016
 */
@Path("/analytics")
@Component
@Api("Analytics")
public class AnalyticsResource {

	@Autowired
	AnalyticsService analyticsService;

	/**
	 * Resource method to get aggregated data for row data
	 * 
	 * @param datasourceDTO
	 *            , JSON Sample JSON
	 *            :{"startDate":1458968100000,"endDate":1461041400000
	 *            ,"aggregationFunctions"
	 *            :["ALL"],"devicePointsMap":[{"sourceId":
	 *            "259642460471175","displayNames"
	 *            :["Engine Crankcase Pressure-23"]}]}
	 * @return Produces, JSON Sample JSON
	 *         :{"data":[{"sourceId":"259642460471175",
	 *         "rows":[{"displayName":"Engine Crankcase Pressure-23"
	 *         ,"aggregatedData"
	 *         :{"AVG":"350.01802110133895","MIN":"300.07812","MAX"
	 *         :"399.92078","COUNT"
	 *         :"7391","SUM":"2586983.193959996","RANGE":"99.84265999999997"
	 *         }}]}]}
	 */
	@Path("/aggregation")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = GET_AGGREGATED_DATA_SUMMARY,notes = GET_AGGREGATED_DATA_DESC)
	@ApiResponses(value = {
	        @ApiResponse(code = SC_OK,message = GET_AGGREGATED_DATA_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)})
	public Response getDeviceData(@ApiParam(value = ANALYTICS_AGGREGATED_DATA_PAYLOAD,
	        required = true) Aggregation aggregation) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(analyticsService.getAggregatedData(aggregation));
		return responseBuilder.build();
	}
}
