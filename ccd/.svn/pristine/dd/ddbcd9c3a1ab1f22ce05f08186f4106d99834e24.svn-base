
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
package com.pcs.ccd.services;

import java.util.List;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.ccd.bean.AttachEquipment;
import com.pcs.ccd.bean.Contact;
import com.pcs.ccd.bean.Equipment;
import com.pcs.ccd.bean.Tenant;
import com.pcs.ccd.bean.TenantEquipment;

/**
 * This class is responsible for defining all services related to equipment
 * 
 * @author pcseg129(Seena Jyothish)
 * Feb 7, 2016
 */
public interface EquipmentService {
	
	/**
	 * Method to persist equipments
	 * @param equipment
	 * @return - status 'SUCCESS' if successfully attached else 'FAILURE'
	 */
	public StatusMessageDTO persistEquipment(TenantEquipment equipment);
	
	/**
	 * Method to check an equipment is already existing
	 * @param equipId - source id of the equipment
	 * @return - true if equipment exists else false
	 */
	public boolean isEquipmentExists(String equipId);
	
	/**
	 * Method to get all equipments of a tenant
	 * @param tenant
	 * @return
	 */
	public List<Equipment> getAllEquipments(Tenant tenant);
	
	/**
	 * Method to attach equipments to contact
	 * @param attachEquipment
	 * @return
	 */
	public StatusMessageDTO attachEquipments(AttachEquipment attachEquipment);
	
	/**
	 * Method to get all equipments attached to a contact
	 * @param contactId - read only row id of the contact
	 * @return
	 */
	public List<Equipment> getEquipmentsOfAContact(String contactId);
	
	/**
	 * Method to find an equipment by source id
	 * @param srcId
	 * @return
	 */
	public Equipment getEquipmentBySearch(String srcId);
	
	/**
	 * Method to get all equipments of a tenant
	 * @param tenantDomainName
	 * @return
	 */
	public List<Equipment> getAllTenantEquipments(String tenantDomainName);
	
}
