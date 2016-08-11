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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.services.AlarmConfigurationServices;
import com.pcs.alpine.services.dto.DomainListDTO;
import com.pcs.alpine.services.dto.TemplateListDTO;


/**
 * This class exposes REST APIs for Alarm Management.
 * 
 * @author pcseg369
 *
 */
@Path("/")
@Component
public class AlarmConfgurationResource {

	@Autowired
	private AlarmConfigurationServices alarmConfigurationServices;
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("domains")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDomains() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		DomainListDTO domainListDTO = alarmConfigurationServices.getDomains();
		responseBuilder.entity(domainListDTO);
		return responseBuilder.build(); 
	}
	
	/**
	 * 
	 * @param domainname
	 * @return
	 */
	@GET
	@Path("domains/{domainname}/templates")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTemplates(@PathParam("domainname") String domainname) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		TemplateListDTO domainListDTO = alarmConfigurationServices.getTemplates(domainname);
		responseBuilder.entity(domainListDTO);
		return responseBuilder.build(); 
	}
	
	/**
	 * 
	 * @param domainname
	 * @param templatename
	 * @return
	 */
	@POST
	@Path("domain/{domainname}/template/{templatename}/configuration")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addConfiguration(@PathParam("domainname") String domainname, @PathParam("templatename") String templatename) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.NO_CONTENT);
		
		return responseBuilder.build(); 
	}
	
	
	/**
	 * 
	 * @param domainname
	 * @param configname
	 * @return
	 */
	@GET
	@Path("domain/{domainname}/configuration/{configname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConfiguration(@PathParam("domainname") String domainname, @PathParam("configname") String configname) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		TemplateListDTO domainListDTO = alarmConfigurationServices.getTemplates(domainname);
		responseBuilder.entity(domainListDTO);
		return responseBuilder.build(); 
	}
	
	/**
	 * 
	 * @param domainname
	 * @param templatename
	 * @return
	 */
	@PUT
	@Path("domain/{domainname}/configuration/{configname}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateConfiguration(@PathParam("domainname") String domainname, @PathParam("configname") String configname) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.NO_CONTENT);
		
		return responseBuilder.build(); 
	}
	

	/**
	 * 
	 * @param domainname
	 * @return
	 */
	@GET
	@Path("domain/{domainname}/configurations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllConfigurations(@PathParam("domainname") String domainname) {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		
		return responseBuilder.build(); 
	}
}
