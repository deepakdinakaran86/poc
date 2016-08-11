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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.GeotagDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;

/**
 * @author PCSEG191 Daniela
 * @date JULY 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class GeotagService extends BaseService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(GeotagService.class);

	@Value("${fms.services.findGeotag}")
	private String findGeotagEndpointUri;

	@Value("${fms.services.createGeotag}")
	private String createGeotagEndpointUri;

	@Value("${fms.services.updateGeotag}")
	private String updateGeotagEndpointUri;

	/**
	 * Method to create a geotag
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<StatusMessageDTO> createGeotag(GeotagDTO geotagDTO) {

		// Invoke tag api
		String createGeotagServiceURI = getServiceURI(createGeotagEndpointUri);
		return getPlatformClient().postResource(createGeotagServiceURI,
		        geotagDTO, StatusMessageDTO.class);
	}

	/**
	 * Method to update a geotag
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<StatusMessageDTO> updateGeotag(GeotagDTO geotagDTO) {

		// Invoke tag api
		String createGeotagServiceURI = getServiceURI(updateGeotagEndpointUri);
		return getPlatformClient().putResource(createGeotagServiceURI,
		        geotagDTO, StatusMessageDTO.class);
	}

	/**
	 * Method to find a geotag
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<GeotagDTO> findGeotag(IdentityDTO geotagDTO) {

		String findGeotagServiceURI = getServiceURI(findGeotagEndpointUri);

		return getPlatformClient().postResource(findGeotagServiceURI,
		        geotagDTO, GeotagDTO.class);
	}

}
