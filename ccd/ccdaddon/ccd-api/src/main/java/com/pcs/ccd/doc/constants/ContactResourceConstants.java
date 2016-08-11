
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
package com.pcs.ccd.doc.constants;

/**
 * This class is responsible for defining all constants for contacts resource
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 23, 2016
 */
public class ContactResourceConstants extends ResourceConstants {
	
	public static final String CREATE_CONTACTS_SUMMARY = "Create tenant contacts";
	
	public static final String CREATE_CONTACTS_DESC = "This is the service to be used to create new contacts for tenants ,sample payload : { \"tenant\": { \"tenantName\": \"Metito\",\"tenantId\": \"metito\", \"contactFName\": \"Jane\", \"contactLName\": \"May\" }, \"contacts\": [ { \"name\": \"seena\", \"email\": \"seena@pacific.com\", \"contactNumber\": \"0508978890\", \"contactType\": \"MANAGER\" }, { \"name\": \"mae\", \"email\": \"mae@pacific.com\", \"contactNumber\": \"0503673231\", \"contactType\": \"MANAGER\" } ] }";
	
	public static final String GET_ALL_CONTACTS_PAYLOAD = "Get All Contacts Payload";
	
	public static final String GET_ALL_CONTACTS_SUMMARY = "Get all tenant contacts";
	
	public static final String  GET_ALL_CONTACTS_DESC = "This is the service to be used to get all contacts of a tenant ,sample payload : { \"tenantId\": \"Metito1\" }";
	
	public static final String GET_ALL_CONTACTS_SUCCESS_RESP = "[ { \"name\": \"front desk\", \"email\": \"frontdesk@pacific.com\", \"contactNumber\": \"043356574\", \"contactType\": \"OPERATOR\", \"rowIdentifier\": \"8c0ca377-fb4e-40de-9129-664fcf54aa0c\" }, { \"name\": \"greeshma\", \"email\": \"greeshma@pacificcontrols.net\", \"contactNumber\": \"0567894561\", \"contactType\": \"MANAGER\", \"rowIdentifier\": \"ccbfd88a-dabd-4c13-a111-e073c110633c\" } ]";
	
	public static final String GET_ALL_EQUIPMENTS_OF_A_CONTACT_SUMMARY = "Get all equipments attached to a contact";
	
	public static final String GET_ALL_EQUIPMENTS_OF_A_CONTACT_DESC = "This is the service to be used to get all equipments attached a contact,sample payload : 06381013-3cfc-4ec6-b79b-19bb4461c46b";
	
	public static final String GET_ALL_EQUIPMENTS_OF_A_CONTACT_SUCCESS_RESP = "[ { \"assetName\": \"E432-E\", \"engineMake\": \"Cummins\", \"engineModel\": \"Westport ISX12 G\", \"assetType\": \"Maintenance\", \"rowIdentifier\": \"04b34f13-cfab-43a8-9fc5-f1d868f642f3\" }, { \"assetName\": \"E432-Ext-13\", \"engineMake\": \"Cummins\", \"engineModel\": \"Westport ISX12 G\", \"assetType\": \"Maintenance\", \"rowIdentifier\": \"3b1721c1-d60c-4b48-9e17-9a784ac22f87\" } ]";
	
	public static final String GET_ALL_EQUIPMENTS_OF_A_CONTACT_PAYLOAD = "Get all equipments attached to a contact payload";
	
	public static final String ATTACH_EQUIPMENTS_TO_CONTACT_SUMMARY = "Attach equipments to contact";
	
	public static final String ATTACH_EQUIPMENTS_TO_CONTACT_DESC = "This is the service to be used to attach equipments to contact,sample payload : { \"contactId\": \"06381013-3cfc-4ec6-b79b-19bb4461c46b\", \"equipmentIds\": [ \"e6ccba94-7abe-4dff-867d-202ff8467960\", \"280b3b05-f998-491a-b3ec-5a0448313139\" ], \"tenantId\": \"Oasis\" }";
}
