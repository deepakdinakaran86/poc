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
import com.pcs.guava.dto.ServiceComponentDTO;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.IdentityDTO;

/**
 * @description This class is responsible for the ServiceComponentServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @date 20 Apr 2016
 */
public interface ServiceComponentService {
	/***
	 * @Description This method is responsible to create a Service Component
	 * 
	 * @param ServiceComponentDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO createServiceComponent(
			ServiceComponentDTO serviceComponentDTO);

	/***
	 * @Description This method is responsible to update a Service Component
	 * 
	 * @param ServiceComponentDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO updateServiceComponent(
			ServiceComponentDTO serviceComponentDTO);

	/***
	 * @Description This method is responsible to retrieve a specific Service
	 *              Component
	 * 
	 * @param identity
	 *            identifier fields
	 * @return ServiceComponentDTO
	 */
	public ServiceComponentDTO findServiceComponent(IdentityDTO identity);

	/***
	 * @Description This method is responsible to retrieve all Service
	 *              Component's of a domain
	 * 
	 * @param domainName
	 *            ,entityTemplateName
	 * 
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> findAllServiceComponents(String domainName);

	/***
	 * @Description This method is responsible to delete a Service Component
	 * 
	 * @param serviceComponentIdentifier
	 *            identifier fields
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO deleteServiceComponent(
			IdentityDTO serviceComponentIdentifier);
}
