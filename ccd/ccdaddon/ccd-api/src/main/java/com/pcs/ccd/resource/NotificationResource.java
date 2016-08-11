
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

import static com.pcs.ccd.doc.constants.NotificationResourceConstants.SEND_EMAIL_DESC;
import static com.pcs.ccd.doc.constants.NotificationResourceConstants.SEND_EMAIL_SUMMARY;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_TRUE_RESP;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.ccd.bean.EmailNotification;
import com.pcs.ccd.services.NotificationService;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Feb 3, 2016
 */

@Path("/notify")
@Component
@Api("Event Notification")
public class NotificationResource {
	
	@Autowired
	NotificationService notificationService;
	

	@POST
	@Path("/email")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = SEND_EMAIL_SUMMARY, notes = SEND_EMAIL_DESC, response = Boolean.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_TRUE_RESP)}
			)
	public Response sendEmail(EmailNotification emailData){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO status = notificationService.sendEmail(emailData);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}

}
