
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

import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.ccd.doc.constants.TenantResourceConstants.GET_ALL_TENANTS_DESC;
import static com.pcs.ccd.doc.constants.TenantResourceConstants.GET_ALL_TENANTS_PAYLOAD;
import static com.pcs.ccd.doc.constants.TenantResourceConstants.GET_ALL_TENANTS_SUCCESS_RESP;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.http.HttpStatus.SC_OK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.ccd.bean.Tenant;
import com.pcs.ccd.services.TenantService;
/**
 * This class is responsible for managing all apis related to tenant contacts
 * 
 * @author pcseg129(Seena Jyothish)
 * Mar 31, 2016
 */
@Path("/tenants")
@Component
@Api("Tenants")
public class TenantResource {
	
	@Autowired
	private TenantService tenantService;
	
	
	@GET
	@Path("/findall")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_TENANTS_PAYLOAD, notes = GET_ALL_TENANTS_DESC, response = Tenant.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_TENANTS_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response getAllTenants(){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<Tenant> tenants = tenantService.getAllTenants();
		responseBuilder.entity(tenants);
		return responseBuilder.build();
	}
	
}
