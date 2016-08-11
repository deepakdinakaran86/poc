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
package com.pcs.avocado.services;

import java.util.List;

import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;

/**
 * 
 * @description This class is responsible for the FacilityService interface
 * @author Twinkle (PCSEG297)
 * @date January 2015
 */
public interface FacilityService {

	/***
	 * @Description This method is responsible to create a tenant
	 * 
	 * @param EntityDto
	 * @return IdentityDTO
	 */
	public IdentityDTO createFacility(EntityDTO facilityEntity);

	/***
	 * @Description This method is responsible to create a tenant
	 * 
	 * @param EntityDto
	 * @return IdentityDTO
	 */
	public EntityDTO getFacility(String templateName, String facilityName,
	        String domainName);

	/***
	 * @Description This method is responsible to create a tenant
	 * 
	 * @param EntityDto
	 * @return IdentityDTO
	 */
	public List<EntityDTO> getAllFacility(String templateName, String domainName);

	/***
	 * @Description This method is responsible to create a tenant
	 * 
	 * @param EntityDto
	 * @return IdentityDTO
	 */
	public StatusMessageDTO updateFacility(String facilityName,
	        String domainName, EntityDTO facilityEntity);
}
