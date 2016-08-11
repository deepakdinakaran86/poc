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
package com.pcs.avocado.service;

import java.util.List;

import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.dto.TenantAdminDTO;
import com.pcs.avocado.dto.TenantAdminEmailDTO;
import com.pcs.avocado.dto.TenantDTO;

/**
 * 
 * @description This class is responsible for the TenantService interface
 * @author Daniela (PCSEG191)
 * @date 09 Jan 2016
 * @since galaxy-1.0.0
 */
public interface TenantService {

	/***
	 * @Description This method is responsible to create a tenant
	 * 
	 * @param TenantDTO
	 * @return StatusMessageDTO
	 */
	public IdentityDTO createTenant(TenantDTO tenant);

	/***
	 * @Description This method is responsible to create a tenant admin user
	 * 
	 * @param TenantAdminDTO
	 * 
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO createTenantAdmin(TenantAdminDTO tenantAdmin);

	/***
	 * @Description This method is responsible to send tenant admin creation
	 *              mail
	 * 
	 * @param TenantAdminEmailDTO
	 * 
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO sendCreateTenantAdminMail(
	        TenantAdminEmailDTO tenantSearch);

	/***
	 * @Description This method is responsible to get all tenants of a domain
	 * 
	 * @param domain
	 * 
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> findAllTenants(String domain);

	/***
	 * @Description This method is responsible to retrieve a tenant
	 * 
	 * @param tenantId
	 *            , domain
	 * @return tenantEntityDTO
	 */
	public EntityDTO findTenant(String tenantId, String domain);

	/***
	 * @Description This method is responsible to update a tenant
	 * 
	 * @param TenantDTO
	 *            , tenantName,domain
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO updateTenant(TenantDTO tenant, String tenantId,
	        String domain);

}
