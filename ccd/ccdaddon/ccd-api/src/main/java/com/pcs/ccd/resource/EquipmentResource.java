
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

import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.ATTACH_CONTACTS_TO_EQUIP_DESC;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.ATTACH_CONTACTS_TO_EQUIP_PAYLOAD;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.ATTACH_CONTACTS_TO_EQUIP_SUMMARY;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_ALL_EQUIPMENTS_DESC;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_ALL_EQUIPMENTS_FROM_PARENT_SUCCESS_RESP;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_ALL_EQUIPMENTS_PAYLOAD;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_ALL_EQUIPMENTS_SUCCESS_RESP;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_ALL_EQUIPMENTS_SUMMARY;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_ALL_TENANT_EQUIPMENTS_DESC;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_ALL_TENANT_EQUIPMENTS_SUMMARY;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_CONTACTS_ATTACHED_TO_EQUIP_BY_SEARCH_DESC;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_CONTACTS_ATTACHED_TO_EQUIP_BY_SEARCH_SUMMARY;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_CONTACTS_ATTACHED_TO_EQUIP_DESC;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_CONTACTS_ATTACHED_TO_EQUIP_PAYLOAD;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_CONTACTS_ATTACHED_TO_EQUIP_SUCCESS_RESP;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.GET_CONTACTS_ATTACHED_TO_EQUIP_SUMMARY;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.PERSIST_EQUIPMENT_DESC;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.PERSIST_EQUIPMENT_PAYLOAD;
import static com.pcs.ccd.doc.constants.EquipmentResourceConstants.PERSIST_EQUIPMENT_SUMMARY;
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
import com.pcs.ccd.bean.Contact;
import com.pcs.ccd.bean.Equipment;
import com.pcs.ccd.bean.EquipmentContacts;
import com.pcs.ccd.bean.Tenant;
import com.pcs.ccd.bean.TenantEquipment;
import com.pcs.ccd.services.ContactsService;
import com.pcs.ccd.services.EquipmentService;

/**
 * This class is responsible for managing all apis related to equipments
 * 
 * @author pcseg129(Seena Jyothish)
 * Feb 7, 2016
 */

@Path("/equipment")
@Component
@Api("Equipments")
public class EquipmentResource {
	
	@Autowired
	EquipmentService equipmentService;
	
	@Autowired
	ContactsService contactsService;
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = PERSIST_EQUIPMENT_SUMMARY, notes = PERSIST_EQUIPMENT_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response persistEquipment(@ApiParam(value = PERSIST_EQUIPMENT_PAYLOAD, required = true) TenantEquipment tenantEquip ){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO status = equipmentService.persistEquipment(tenantEquip);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}
	
	@POST
	@Path("/attach/contacts")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = ATTACH_CONTACTS_TO_EQUIP_SUMMARY, notes = ATTACH_CONTACTS_TO_EQUIP_DESC, response = StatusMessageDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GENERAL_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response attachContactsToEquipment(@ApiParam(value = ATTACH_CONTACTS_TO_EQUIP_PAYLOAD, required = true) EquipmentContacts equipContacts){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		StatusMessageDTO status = contactsService.attachContacts(equipContacts);
		responseBuilder.entity(status);
		return responseBuilder.build();
	}
	
	@GET
	@Path("/{equip_id}/contacts")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_CONTACTS_ATTACHED_TO_EQUIP_SUMMARY, notes = GET_CONTACTS_ATTACHED_TO_EQUIP_DESC, response = Contact.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_CONTACTS_ATTACHED_TO_EQUIP_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response getAttachedContacts(@ApiParam(value = GET_CONTACTS_ATTACHED_TO_EQUIP_PAYLOAD, required = true) @PathParam("equip_id") String equipId){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<Contact> contacts = contactsService.getEquipmentContacts(equipId);
		responseBuilder.entity(contacts);
		return responseBuilder.build();
	}

	@POST
	@Path("/contacts")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_CONTACTS_ATTACHED_TO_EQUIP_BY_SEARCH_SUMMARY, notes = GET_CONTACTS_ATTACHED_TO_EQUIP_BY_SEARCH_DESC, response = Contact.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_CONTACTS_ATTACHED_TO_EQUIP_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response getAttachedContactsBySearch(@ApiParam(value = GET_CONTACTS_ATTACHED_TO_EQUIP_PAYLOAD, required = true) Equipment equipment){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<Contact> contacts = contactsService.getEquipmentContacts(equipment);
		responseBuilder.entity(contacts);
		return responseBuilder.build();
	}
	
	@POST
	@Path("/findall")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_EQUIPMENTS_SUMMARY, notes = GET_ALL_EQUIPMENTS_DESC, response = Equipment.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_EQUIPMENTS_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response getAllEquipments(@ApiParam(value = GET_ALL_EQUIPMENTS_PAYLOAD, required = true) Tenant tenant){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<Equipment> equipments = equipmentService.getAllEquipments(tenant);
		responseBuilder.entity(equipments);
		return responseBuilder.build();
	}
	
	@GET
	@Path("/tenant/{tenant_name}/findall")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = GET_ALL_TENANT_EQUIPMENTS_SUMMARY, notes = GET_ALL_TENANT_EQUIPMENTS_DESC, response = Equipment.class)
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = GET_ALL_EQUIPMENTS_FROM_PARENT_SUCCESS_RESP),
			@ApiResponse(code = 505,message = GENERAL_FIELD_NOT_SPECIFIED),
			@ApiResponse(code = 508,message = GENERAL_FIELD_INVALID)}
			)
	public Response getAllTenantEquipments(@ApiParam(value = GET_ALL_EQUIPMENTS_PAYLOAD, required = true)  @PathParam("tenant_name") String tenantName){
		ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
		List<Equipment> equipments = equipmentService.getAllTenantEquipments(tenantName);
		responseBuilder.entity(equipments);
		return responseBuilder.build();
	}
	
}
