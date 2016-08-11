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

import org.springframework.stereotype.Service;

import com.pcs.avocado.commons.dto.AssetTypeTemplateDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;

/**
 * Service Interface for Air Handlers
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date March 2016
 * @since Avocado-1.0.0
 */
@Service
public interface AssetTypeTemplateService {

	/**
	 * Service to Create an AssetType
	 * 
	 * @param AssetTypeTemplateDTO
	 * @return "SUCCESS"
	 */
	public StatusMessageDTO createAssetType(String parentType,
	        AssetTypeTemplateDTO assetTypeTemplateDTO);

	/**
	 * Service to get all asset type
	 * 
	 * @param domainName
	 * @return
	 */
	public List<AssetTypeTemplateDTO> getAllAssetType(String domainName,
	        String parentType);

	/**
	 * Service to get an asset type
	 * 
	 * @param assetTypeName
	 *            , domainName
	 * @return
	 */
	public AssetTypeTemplateDTO getAssetType(String assetTypeName,
	        String domainName, String parentType);

	public EntityDTO createAssetType(EntityDTO assetTypeEntity);

	public StatusMessageDTO updateAssetType(EntityDTO assetTypeEntity);

}
