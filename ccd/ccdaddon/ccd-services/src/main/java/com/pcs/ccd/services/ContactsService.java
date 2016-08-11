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
package com.pcs.ccd.services;

import java.util.List;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.ccd.bean.Contact;
import com.pcs.ccd.bean.Equipment;
import com.pcs.ccd.bean.EquipmentContacts;
import com.pcs.ccd.bean.SearchContact;
import com.pcs.ccd.bean.Tenant;
import com.pcs.ccd.bean.TenantContact;

/**
 * This class is responsible for defining all services related to contacts
 * 
 * @author pcseg129(Seena Jyothish) Jan 21, 2016
 */
public interface ContactsService {
	
	/**
	 * Method to create contacts for a tenant
	 * @param tenantContact - 
	 * @return - 'SUCCESS' if all contacts get created else 'FAILURE' 
	 */
	public StatusMessageDTO createContacts(TenantContact tenantContact);

	/**
	 * Method to check a contact is already existing for a tenant
	 * @param contactId
	 * @return - true if exists else false
	 */
	public boolean isContactExists(String contactId);
	
	/**
	 * Method to find a particular contact of a tenant 
	 * by passing contact name and tenant name
	 * @param searchContact
	 * @return
	 */
	public Contact getContact(SearchContact searchContact);
	
	/**
	 * Method to get all contacts of a tenant
	 * @param tenant
	 * @return
	 */
	public List<Contact> getAllContacts(Tenant tenant);
	
	/**
	 * Method to attach contacts to equipment
	 * @param equipmentContacts - equipment and contacts
	 * @return - status 'SUCCESS' if successfully attached else 'FAILURE'
	 */
	public StatusMessageDTO attachContacts(EquipmentContacts equipmentContacts);
	
	/**
	 * Method to get all contacts attached to an equipment
	 * @param equiId - source id of the equipment
	 * @return - list of contacts
	 */
	public List<Contact> getEquipmentContacts(String equiId);
	
	/**
	 * Method to get all contacts attached to an equipment
	 * @param searchEquipment
	 * @return
	 */
	public List<Contact> getEquipmentContacts(Equipment equipment);
	
}
