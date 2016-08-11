
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

import static com.pcs.ccd.doc.constants.ContactResourceConstants.ATTACH_EQUIPMENTS_TO_CONTACT_DESC;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.ATTACH_EQUIPMENTS_TO_CONTACT_SUMMARY;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.CREATE_CONTACTS_DESC;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.CREATE_CONTACTS_SUMMARY;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.GET_ALL_CONTACTS_DESC;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.GET_ALL_CONTACTS_PAYLOAD;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.GET_ALL_CONTACTS_SUCCESS_RESP;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.GET_ALL_CONTACTS_SUMMARY;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.GET_ALL_EQUIPMENTS_OF_A_CONTACT_DESC;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.GET_ALL_EQUIPMENTS_OF_A_CONTACT_PAYLOAD;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.GET_ALL_EQUIPMENTS_OF_A_CONTACT_SUCCESS_RESP;
import static com.pcs.ccd.doc.constants.ContactResourceConstants.GET_ALL_EQUIPMENTS_OF_A_CONTACT_SUMMARY;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.ATTACH_CONTACTS_TO_EQUIP_PAYLOAD;
import static com.pcs.ccd.doc.constants.ResourceConstants.CREATE_CONTACTS_PAYLOAD;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_FIELD_INVALID;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_FIELD_NOT_SPECIFIED;
import static com.pcs.ccd.doc.constants.ResourceConstants.GENERAL_SUCCESS_RESP;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.ccd.bean.AttachEquipment;
import com.pcs.ccd.bean.Contact;
import com.pcs.ccd.bean.Equipment;
import com.pcs.ccd.bean.Tenant;
import com.pcs.ccd.bean.TenantContact;
import com.pcs.ccd.services.ContactsService;
import com.pcs.ccd.services.EquipmentService;
/**
 * This class is responsible for managing all apis related to tenant contacts
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 23, 2016
 */
@Path("/contacts")
@Component
@Api("Tenant Contacts")
public class TenantContactsResource {
	
	@Autowired
	private ContactsService contactsService;
	
	@Autowired
	EquipmentService equipmentService;
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = CREATE_CONTACTS_SUMMARY, notes = CREATE_CONTACTS_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED)}
			)
	public Response createContacts(
			@ApiParam(value = CREATE_CONTACTS_PAYLOAD, required = true) TenantContact tenantContact){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO status = contactsService.createContacts(tenantContact);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}
	
	@POST
	@Path("/findall")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_CONTACTS_SUMMARY, notes = GET_ALL_CONTACTS_DESC, response = Contact.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_CONTACTS_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response getAllContacts(
			@ApiParam(value = GET_ALL_CONTACTS_PAYLOAD, required = true) Tenant tenant){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<Contact> contacts = contactsService.getAllContacts(tenant);
		responseBuilder.entity(contacts);
		return responseBuilder.build();
	}
	
	
	@POST
	@Path("/attach/equipments")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = ATTACH_EQUIPMENTS_TO_CONTACT_SUMMARY, notes = ATTACH_EQUIPMENTS_TO_CONTACT_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response attachEquipments(@ApiParam(value = ATTACH_CONTACTS_TO_EQUIP_PAYLOAD, required = true) AttachEquipment attachEquipment){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO status = equipmentService.attachEquipments(attachEquipment);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}
	
	@GET
	@Path("/{contact_id}/equipments")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_EQUIPMENTS_OF_A_CONTACT_SUMMARY, notes = GET_ALL_EQUIPMENTS_OF_A_CONTACT_DESC, response = Equipment.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_EQUIPMENTS_OF_A_CONTACT_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response getEquipmentsOfAContact(@ApiParam(value = GET_ALL_EQUIPMENTS_OF_A_CONTACT_PAYLOAD, required = true) @PathParam("contact_id") String contactRowId){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<Equipment> equipments = equipmentService.getEquipmentsOfAContact(contactRowId);
		responseBuilder.entity(equipments);
		return responseBuilder.build();
	}

}
