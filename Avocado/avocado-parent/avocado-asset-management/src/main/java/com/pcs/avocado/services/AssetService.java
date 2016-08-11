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

import com.pcs.avocado.commons.dto.AssetDTO;
import com.pcs.avocado.commons.dto.AssetResponseDTO;
import com.pcs.avocado.commons.dto.Device;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.GensetAlarmHistoryDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.PointRelationship;
import com.pcs.avocado.commons.dto.StatusMessageDTO;

/**
 * Asset Resource Methods
 * 
 * @author Greeshma (PCSEG323)
 * @date March 2016
 * @since Avocado-1.0.0
 */

public interface AssetService {

	/**
	 * Service method to get all the assets
	 * 
	 * @param templateName
	 *            ,domain,assetType
	 * @return {@link AssetDTO}
	 * 
	 */
	public List<AssetDTO> getAllAssets(String domain, String assetType);

	/**
	 * Service to create an asset
	 * 
	 * This service will create an asset and internally would call create
	 * assetType
	 * 
	 * @param equipment
	 * @return
	 */
	public EntityDTO createAsset(AssetDTO assetDTO);

	/**
	 * Service to update an asset
	 * 
	 * This service will update an asset and internally would call update
	 * assetType entity
	 * 
	 * @param equipment
	 * @return
	 */
	public StatusMessageDTO updateAsset(AssetDTO assetDTO);

	/**
	 * Service to create a list of points and attach relationships with a device
	 * and equipment, a point can be created if a device has no other points
	 * attached to it, hence once points are associated only updates on those
	 * points will be allowed, all other sync operations will happen when the
	 * point configuration is updated
	 * 
	 * @param pointRelationship
	 * @return
	 */
	public StatusMessageDTO createPointsAndRelationship(
	        PointRelationship pointRelationship);

	/**
	 * Service to used to list all the asset details
	 * 
	 * @param asset
	 * @return AssetDTO
	 */
	public AssetResponseDTO getAssetDetails(IdentityDTO asset);

	/**
	 * Service to fetch the latest data of a asset.
	 * 
	 * @param identifier
	 * @return
	 */
	public List<EntityDTO> getAssetLatestData(IdentityDTO asset);

	/**
	 * Service to fetch all points of a device with status If device not present
	 * in alpine then get from Saffron
	 * 
	 * @param device
	 * @return
	 */
	public List<EntityDTO> getPointsOfADevice(Device device);

	/**
	 * Service to fetch all points of a device with status If device not present
	 * in alpine then get from Saffron
	 * 
	 * @param device
	 * @return
	 */
	public List<EntityDTO> getPointsOfADeviceTemp(Device device);

	public StatusMessageDTO createSelectedPointsAndRelationship(
	        PointRelationship pointRelationship);

	/**
	 * Service to fetch all the Live alarms(Active) from an Asset
	 * 
	 * @param domainName
	 * @return
	 * 
	 */
	public List<GensetAlarmHistoryDTO> getLiveAlarms(String domainName,
	        String assetName);
}
