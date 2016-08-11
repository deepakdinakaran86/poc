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
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.PoiDTO;
import com.pcs.fms.web.dto.PoiTypeDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS-1.0.0
 * 
 */
@Service
public class POIService extends BaseService {

	@Value("${fms.poitype.get}")
	private String findPOITypeEndpointUri;
	@Value("${fms.poitype.save}")
	private String savePOITypeEndpointUri;

	@Value("${fms.poi.get}")
	private String getPOIEndpointUri;
	@Value("${fms.poi.save}")
	private String savePOIEndpointUri;
	@Value("${fms.poi.update}")
	private String updatePOIEndpointUri;
	@Value("${fms.poi.delete}")
	private String deletePOIEndpointUri;

	/**
	 * Method to view poi type details
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<PoiTypeDTO> getPOITypeDetails(String poiType) {
		String url = findPOITypeEndpointUri.replace("{poi_type_name}", poiType);
		String findDeviceDetailsServiceURI = getServiceURI(url);
		return getPlatformClient().getResource(findDeviceDetailsServiceURI,
		        PoiTypeDTO.class);
	}

	/**
	 * Method to save poi type
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<StatusMessageDTO> savePOIType(PoiTypeDTO poiType) {
		String findDeviceDetailsServiceURI = getServiceURI(savePOITypeEndpointUri);
		return getPlatformClient().postResource(findDeviceDetailsServiceURI,
		        poiType, StatusMessageDTO.class);
	}

	// POI

	/**
	 * Method to view poi type details
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<PoiDTO> getPOIDetails(IdentityDTO identifier) {
		String findDeviceDetailsServiceURI = getServiceURI(getPOIEndpointUri);
		return getPlatformClient().postResource(findDeviceDetailsServiceURI,
		        identifier, PoiDTO.class);
	}

	/**
	 * Method to view poi type details
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<PoiDTO> savePoi(PoiDTO poiDto) {
		String findDeviceDetailsServiceURI = getServiceURI(savePOIEndpointUri);
		return getPlatformClient().postResource(findDeviceDetailsServiceURI,
		        poiDto, PoiDTO.class);
	}

	/**
	 * Method to view poi type details
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<PoiDTO> updatePoi(PoiDTO poiDto) {
		String findDeviceDetailsServiceURI = getServiceURI(updatePOIEndpointUri);
		return getPlatformClient().putResource(findDeviceDetailsServiceURI,
		        poiDto, PoiDTO.class);
	}

	/**
	 * Method to view poi type details
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<StatusMessageDTO> deletePoi(IdentityDTO identifier) {
		String findDeviceDetailsServiceURI = getServiceURI(deletePOIEndpointUri);
		return getPlatformClient().postResource(findDeviceDetailsServiceURI,
		        identifier, StatusMessageDTO.class);
	}
}
