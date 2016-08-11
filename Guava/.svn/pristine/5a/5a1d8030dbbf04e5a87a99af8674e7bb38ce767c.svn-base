/**
 * Copyright 2016 Pacific Controls Software Services LLC (PCSS). All Rights
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
package com.pcs.guava.services;

import java.util.List;

import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.dto.ServiceItemDTO;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.IdentityDTO;

/**
 * @description This class is responsible for the ServiceItemServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @date 20 Apr 2016
 */
public interface ServiceItemService {
	/***
	 * @Description This method is responsible to create a Service Item
	 * 
	 * @param ServiceItemDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO createServiceItem(
			ServiceItemDTO serviceItemDTO);

	/***
	 * @Description This method is responsible to update a Service Item
	 * 
	 * @param ServiceItemDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO updateServiceItem(
			ServiceItemDTO serviceItemDTO);

	/***
	 * @Description This method is responsible to retrieve a specific Service
	 *              Component
	 * 
	 * @param identity
	 *            identifier fields
	 * @return ServiceItemDTO
	 */
	public ServiceItemDTO findServiceItem(IdentityDTO identity);

	/***
	 * @Description This method is responsible to retrieve all Service
	 *              Items of a domain
	 * 
	 * @param domainName
	 *            ,entityTemplateName
	 * 
	 * @return List<ServiceItemDTO>
	 */
	public List<EntityDTO> findAllServiceItems(String domainName);
	
	/***
	 * @Description This method is responsible to retrieve all Service
	 *              Component's attached to a service Item
	 * 
	 * @param serviceItemIdentity
	 *            
	 * 
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getAttachedServiceComponents(IdentityDTO serviceItemIdentity);

	/***
	 * @Description This method is responsible to delete a Service Item
	 * 
	 * @param serviceComponentIdentifier
	 *            identifier fields
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO deleteServiceItem(
			IdentityDTO serviceComponentIdentifier);
}
