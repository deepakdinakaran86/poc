/**
 * 
 */
package com.pcs.alpine.resources;

import static com.pcs.alpine.constants.ApiDocConstant.CREATE_GEOTAG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_GEOTAG_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_GEOTAG_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_GEOTAG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.DELETE_GEOTAG_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_GEOTAG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_GEOTAG_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_GEOTAG_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_GEOTAG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_GEOTAG_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_GEOTAG_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.alpine.constants.ApiDocConstant.GEOTAG_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ATTACHED_GEOTAGGED_TEMPLATES_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.GET_ATTACHED_GEOTAGGED_TEMPLATES_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.PERSISTENCE_ERROR;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_GEOTAG_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_GEOTAG_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_GEOTAG_SUMMARY;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.constants.ApiDocConstant;
import com.pcs.alpine.dto.GeotagDTO;
import com.pcs.alpine.service.GeotagService;
import com.pcs.alpine.services.dto.IdentityDTO;

/**
 * @description This class is responsible for the GeotagServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @date 20 Apr 2016
 *
 */

@Path("/geotags")
@Component
@Api("Geotag")
public class GeotagResource {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(GeotagResource.class);

	@Autowired
	private GeotagService geotagService;

	/**
	 * @description This is a default method to get the header options,all
	 *              resource layers should have this method
	 * 
	 */

	@OPTIONS
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "",hidden = true)
	public Response defaultOptions() {
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.header("ALLOW", "HEAD,GET,PUT,DELETE,OPTIONS");
		return responseBuilder.build();
	}

	/**
	 * Responsible to create a geotag
	 *
	 * @param geotagDTO
	 * @return StatusMessageDTO
	 */
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_GEOTAG_DESC,value = CREATE_GEOTAG_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = CREATE_GEOTAG_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 503,message = PERSISTENCE_ERROR),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createGeotag(@ApiParam(required = true,
	        value = GEOTAG_PAYLOAD) GeotagDTO geotagDTO) {
		LOGGER.debug("<<-- Enter createGeotag(GeotagDTO geotagDTO) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geotagService.createGeotag(geotagDTO));
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch a Geotag
	 *
	 * @param identity
	 * @return Fetched Geotag
	 */
	@POST
	@Path("find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_GEOTAG_DESC,value = FIND_GEOTAG_SUMMARY,
	        response = GeotagDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = FIND_GEOTAG_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response findGeotag(
	        @ApiParam(name = "geotagIdentityFields",required = true,
	                value = "Geotag Identify Fields") IdentityDTO identity) {
		LOGGER.debug("<<-- Enter findGeotag(IdentityDTO identity) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geotagService.findGeotag(identity));
		return responseBuilder.build();
	}

	/**
	 * Responsible to find the list of Geotag's for a domain
	 *
	 * @param domainName
	 *            , entityTemplateName
	 * @return List of GeoTaggedEntitiesDTO
	 */
	@GET
	@Path("list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_GEOTAG_DESC,value = FIND_ALL_GEOTAG_SUMMARY,
	        response = GeotagDTO.class,responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = FIND_ALL_GEOTAG_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response findAllGeotag(@QueryParam("domain_name") String domainName,
	        @QueryParam("entity_template_name") String entityTemplateName) {
		LOGGER.debug("<<-- Enter findAllGeotags(String domainName) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geotagService.findAllGeotags(domainName,
		        entityTemplateName));
		return responseBuilder.build();
	}

	/**
	 * Responsible to update a Geotag
	 *
	 * @param Geotag
	 * @return StatusMessageDTO
	 */
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_GEOTAG_DESC,value = UPDATE_GEOTAG_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = UPDATE_GEOTAG_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 503,message = PERSISTENCE_ERROR),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response updateGeotag(@ApiParam(required = true,
	        value = GEOTAG_PAYLOAD) GeotagDTO geotag) {
		LOGGER.debug("<<-- Enter updateGeotag(GeotagDTO geotag) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geotagService.updateGeotag(geotag));
		return responseBuilder.build();
	}

	/**
	 * Responsible to delete a Geotag
	 *
	 * @param Geotag
	 * @return Success message
	 */
	@POST
	@Path("delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = DELETE_GEOTAG_DESC,value = DELETE_GEOTAG_SUMMARY,
	        response = StatusMessageDTO.class)
	public Response deleteGeotag(@ApiParam(required = true,
	        value = GEOTAG_PAYLOAD) IdentityDTO geotag) {
		LOGGER.debug("<<-- Enter deleteGeotag(IdentityDTO geotag) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geotagService.deleteGeotag(geotag));
		return responseBuilder.build();
	}
	
	/**
	 * Responsible to fetch template names of Geotagged entities
	 * 
	 * @param IdentityDTO
	 * @return List<String>
	 */
	@GET
	@Path("attached/templates")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = GET_ATTACHED_GEOTAGGED_TEMPLATES_DESC, value = GET_ATTACHED_GEOTAGGED_TEMPLATES_SUMMARY, response = String.class, responseContainer = "List")
	public Response getAttachedGeotaggedTemplates(
			@QueryParam("domain_name") String domainName) {
		LOGGER.debug("<<-- Enter getAttachedGeotaggedTemplates(String domainName) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(geotagService
				.getAttachedGeotaggedTemplates(domainName));
		return responseBuilder.build();
		}
}
