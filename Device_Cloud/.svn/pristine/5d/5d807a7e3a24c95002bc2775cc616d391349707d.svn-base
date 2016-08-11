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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.datasource.dto.PointTag;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.services.PointTagService;
import com.pcs.datasource.services.utils.SubscriptionUtility;

/**
 * This class is responsible for managing all services related to device point
 * custom tag
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 08 Jul 2015
 */
@Path("/point_tag")
@Component
public class PointTagResource {

	@Autowired
	private PointTagService pointTagService;

	@Autowired
	private SubscriptionUtility subscriptionUtility;

	/**
	 * Method to get all point custom tags of a subscription
	 * 
	 * @param subId
	 * @return Produces:JSON Sample JSON : [ {"name": "HarshMove"}, {"name":
	 *         "StatusPoint"} ]
	 */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPointTags(
	        @HeaderParam("x-jwt-assertion") String jwtObject) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		Subscription subscription = subscriptionUtility
		        .getSubscription(jwtObject);
		List<PointTag> customTags = pointTagService.getAllTags(subscription);
		responseBuilder.entity(customTags);
		return responseBuilder.build();
	}

}
