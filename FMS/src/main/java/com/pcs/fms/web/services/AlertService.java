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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.AssetDTO;
import com.pcs.fms.web.dto.AssetTypeTemplateDTO;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class AlertService extends BaseService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AlertService.class);

	@Value("${fms.services.vehicle}")
	private String listAssetsEndpointUri;

	@Autowired
	private VehicleService vehicleService;

	/**
	 * Method to list all Assets
	 * 
	 * @param
	 * @return
	 */
	public List<AssetDTO> getAssets(String domain) {
		FMSResponse<List<AssetTypeTemplateDTO>> vehicleType = vehicleService
		        .listVehicleType(domain);
		List<AssetDTO> vehicles = vehicleService.listVehicles(domain,
		        vehicleType.getEntity());

		// String getAssetsServiceURI = getServiceURI(listAssetsEndpointUri);
		//
		// Type assetListType = new TypeToken<List<AssetDTO>>() {
		// private static final long serialVersionUID = 5936335989523954928L;
		// }.getType();
		//
		// FMSResponse<List<AssetDTO>> assets = null;
		//
		// if (isBlank(domain)) {
		// assets = getPlatformClient().getResource(getAssetsServiceURI,
		// assetListType);
		// } else {
		// assets = getPlatformClient().getResource(
		// getAssetsServiceURI + "?domain_name=" + domain,
		// assetListType);
		// }
		return vehicles;
	}

	/**
	 * Method to list all alerts by fromDate to toDate
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<List<AssetDTO>> getAlerts(String fromDate, String toDate) {
		String getAssetsServiceURI = getServiceURI(listAssetsEndpointUri);
		return null;
	}
}
