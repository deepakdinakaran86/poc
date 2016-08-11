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
package com.pcs.alpine.services;

import java.util.List;

import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;

/**
 * 
 * @description This class is responsible for the TenantService interface
 * @author Twinkle (PCSEG297)
 * @date 22 Sep 2015
 * @since galaxy-1.0.0
 */
public interface TenantService {

	/***
	 * @Description This method is responsible to create a tenant
	 * 
	 * @param TenantDTO
	 * @return IdentityDTO
	 */
	public IdentityDTO createTenant(EntityDTO tenant);

	/***
	 * @Description This method is responsible to update a tenant
	 * 
	 * @param TenantDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO updateTenant(EntityDTO tenant);

	/***
	 * @Description This method is responsible to retrieve a tenant
	 * 
	 * @param tenant
	 *            identifier fields
	 * @return tenantEntityDTO
	 */
	public EntityDTO findTenant(IdentityDTO tenant);

	/***
	 * @Description This method is responsible to retrieve a tenant
	 * 
	 * @param tenant
	 *            identifier fields
	 * @return tenantEntityDTO
	 */
	public List<EntityDTO> findAllTenant(IdentityDTO tenant);

	/***
	 * @Description This method is responsible to delete a tenant
	 * 
	 * @param tenant
	 *            identifier fields
	 * @return Status
	 */
	public StatusMessageDTO deleteTenant(IdentityDTO tenant);

	/***
	 * @Description This method is responsible to validate the tenant fields for
	 *              uniqueness
	 * 
	 * @param EntitySearchDTO
	 * 
	 * @return Status
	 */
	public StatusMessageDTO validateUniqueness(EntitySearchDTO search);

	/***
	 * @Description This method is responsible to get the tenant count
	 * 
	 * @param EntitySearchDTO
	 * 
	 * @return EntityCountDTO
	 */
	public EntityCountDTO getTenantCount(EntitySearchDTO tenantSearch);

	/***
	 * @Description This method is responsible to get tenant and domain info by
	 *              domain name
	 * 
	 * @param domainName
	 * 
	 * @return DomainEntityDTO
	 */
	public EntityDTO getTenantInfo();

	/***
	 * @Description This method is responsible to get all the features
	 *              associated with the domain
	 * 
	 * 
	 * 
	 * @return List<String>
	 */
	public List<String> getAllFeatures(String domain);

	/***
	 * @Description This method is responsible to assign all the features to a
	 *              domain
	 * 
	 * @param domainName
	 *            ,List<String> features
	 * 
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO assignFeatures(List<String> features,
	        String domainName);

}
