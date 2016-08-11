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
package com.pcs.datasource.services;

import java.util.List;

import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.dto.Subscription;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for managing all parameters of system
 * 
 * @author pcseg296(Riyas PH)
 * @date 7 july 2015
 */
public interface ParameterService {

	/**
	 * method to create a parameter to the system under a subscription
	 * 
	 * @param parameterDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO saveParameter(ParameterDTO parameterDTO);

	/**
	 * method to get all parameters of a subscription
	 * 
	 * @param subId
	 * @return List<ParameterDTO>
	 */
	public List<ParameterDTO> getParameters(Subscription subscription);

	/**
	 * method is responsible to confirm parameter is existing in database or
	 * note
	 * 
	 * @param paramName
	 * @param subId
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO isParameterExist(String paramName,
	        Subscription subscription);

}
