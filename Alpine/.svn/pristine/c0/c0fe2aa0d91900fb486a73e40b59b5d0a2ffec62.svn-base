/**
 * 
 */
package com.pcs.alpine.resources;

import static com.pcs.alpine.constants.ApiDocConstant.CREATE_DOCUMENT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_DOCUMENT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.CREATE_DOCUMENT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.DOCUMENT_PAYLOAD;
import static com.pcs.alpine.constants.ApiDocConstant.DOCUMENT_TYPE;
import static com.pcs.alpine.constants.ApiDocConstant.DOCUMENT_TYPE_SAMPLE;
import static com.pcs.alpine.constants.ApiDocConstant.DOMAIN;
import static com.pcs.alpine.constants.ApiDocConstant.DOMAIN_SAMPLE;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_DOCUMENT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_DOCUMENT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_ALL_DOCUMENT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_DOCUMENT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_DOCUMENT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.FIND_DOCUMENT_SUMMARY;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_DATA_NOT_AVAILABLE;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_INVALID;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.alpine.constants.ApiDocConstant.GENERAL_FIELD_NOT_UNIQUE;
import static com.pcs.alpine.constants.ApiDocConstant.PERSISTENCE_ERROR;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_DOCUMENT_DESC;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_DOCUMENT_SUCCESS_RESP;
import static com.pcs.alpine.constants.ApiDocConstant.UPDATE_DOCUMENT_SUMMARY;
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

import com.pcs.alpine.commons.dto.IdentityDTO;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.dto.DocumentDTO;
import com.pcs.alpine.service.DocumentService;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author Riyas (PCSEG296)
 * @date 12 June 2016
 * @since alpine-1.0.0
 */

@Path("/documents")
@Component
@Api("Documents")
public class DocumentResource {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DocumentResource.class);

	@Autowired
	private DocumentService documentService;

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
	 * Responsible to create a Document
	 * 
	 * @param DocumentDTO
	 * @return DocumentDTO
	 */
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = CREATE_DOCUMENT_DESC,value = CREATE_DOCUMENT_SUMMARY,
	        response = DocumentDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = CREATE_DOCUMENT_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 503,message = PERSISTENCE_ERROR),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response createDocument(@ApiParam(required = true,
	        value = DOCUMENT_PAYLOAD) DocumentDTO document) {
		LOGGER.debug("<<-- Enter createDocument(DocumentDTO document) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(documentService.createDocument(document));
		return responseBuilder.build();
	}

	/**
	 * Responsible to fetch a Document
	 * 
	 * @param identity
	 * @return Fetched Document
	 */
	@POST
	@Path("find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_DOCUMENT_DESC,value = FIND_DOCUMENT_SUMMARY,
	        response = DocumentDTO.class)
	@ApiResponses(value = {
	@ApiResponse(code = HttpStatus.SC_OK,message = FIND_DOCUMENT_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response findDocument(@ApiParam(required = true) IdentityDTO identity) {
		LOGGER.debug("<<-- Enter findDocument(IdentityDTO identity) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(documentService.findDocument(identity));
		return responseBuilder.build();
	}

	/**
	 * Responsible to find the list of documents for a domain
	 * 
	 * @param domainName
	 *            , entityTemplateName
	 * @return List of Document
	 */
	@GET
	@Path("list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = FIND_ALL_DOCUMENT_DESC,
	        value = FIND_ALL_DOCUMENT_SUMMARY,response = DocumentDTO.class,
	        responseContainer = "List")
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = FIND_ALL_DOCUMENT_SUCCESS_RESP),
	        @ApiResponse(code = 500,message = GENERAL_DATA_NOT_AVAILABLE),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)})
	public Response listDocuments(
	        @ApiParam(value = DOMAIN,required = true,
	                defaultValue = DOMAIN_SAMPLE,example = DOMAIN_SAMPLE) @QueryParam("domain_name") String domain,
	        @ApiParam(value = DOCUMENT_TYPE,required = true,
	                defaultValue = DOCUMENT_TYPE_SAMPLE,
	                example = DOCUMENT_TYPE_SAMPLE) @QueryParam("document_type") String documentType) {
		LOGGER.debug("<<-- Enter listDocuments(String domainName, String documentType) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(documentService.listDocuments(domain,
		        documentType));
		return responseBuilder.build();
	}

	/**
	 * Responsible to update a document
	 * 
	 * @param Geotag
	 * @return StatusMessageDTO
	 */
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(notes = UPDATE_DOCUMENT_DESC,value = UPDATE_DOCUMENT_SUMMARY,
	        response = StatusMessageDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = HttpStatus.SC_OK,
	                message = UPDATE_DOCUMENT_SUCCESS_RESP),
	        @ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
	        @ApiResponse(code = 508,message = GENERAL_FIELD_INVALID),
	        @ApiResponse(code = 503,message = PERSISTENCE_ERROR),
	        @ApiResponse(code = 522,message = GENERAL_FIELD_NOT_UNIQUE)})
	public Response updateDocument(@ApiParam(required = true,
	        value = DOCUMENT_PAYLOAD) DocumentDTO document) {
		LOGGER.debug("<<-- Enter  updateDocument(DocumentDTO document) -->>");
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		responseBuilder.entity(documentService.updateDocument(document));
		return responseBuilder.build();
	}

}
