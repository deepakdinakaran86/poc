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
package com.pcs.fms.web.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.EntityDTO;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS-1.0.0
 * 
 */
@Service
public class DeviceService extends BaseService {

	@Value("${fms.device.find}")
	private String findDeviceEndpointUri;

	/**
	 * Method to view device details
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<EntityDTO> getDeviceDetails(String identifier) {
		String url = findDeviceEndpointUri.replace("{identifier}", identifier);
		String findDeviceDetailsServiceURI = getServiceURI(url);
		return getPlatformClient().getResource(findDeviceDetailsServiceURI,
		        EntityDTO.class);
	}
}
