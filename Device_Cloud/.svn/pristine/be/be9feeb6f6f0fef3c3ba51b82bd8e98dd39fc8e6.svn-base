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

import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.ConfigureDevice;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.GeneralBatchResponse;
import com.pcs.datasource.dto.GeneralResponse;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.dto.TemplateAssign;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for all the services related to device point
 * configuration
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 02 Jul 2015
 */
public interface DeviceConfigService {

	/**
	 * Method to check a device config template name already exists or not
	 * 
	 * @param tempName
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO isDeviceConfigTempExist(String subId,
			String tempName);

	/**
	 * Method to save device configuration template
	 * 
	 * @param configTemp
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO createDeviceConfigTemp(String subId,
			DeviceConfigTemplate configTemp);

	/**
	 * Method to update device configuration template
	 * 
	 * @param configTemp
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO updateDeviceConfigTemp(String subId,
			DeviceConfigTemplate configTemp);

	/**
	 * Service method to fetch all the device configuration template of a
	 * subscription with an optional filter on protocol version
	 * 
	 * @param subId
	 * @param conSearch
	 * @return
	 */
	public List<DeviceConfigTemplate> getAllConfTemplates(String subId,
			ConfigurationSearch conSearch);

	/**
	 * Service method to fetch details of a device configuration template
	 * 
	 * @param subId
	 * @param templateName
	 * @return
	 */
	public DeviceConfigTemplate getConfTemplate(String subId,
			String templateName);

	/**
	 * Service method to inActivate device configuration templates
	 * 
	 * @param subId
	 * @param confTemplates
	 */
	public StatusMessageDTO inActivateConfigTemplates(String subId,
			List<String> confTemplates);

	/**
	 * Service method to update configuration of a device without passing subscription
	 * 
	 * @param sourceId
	 * @param configPoints
	 * @param subscription
	 * @return
	 */
	public GeneralResponse updateDeviceConfiguration(String sourceId,
			List<ConfigPoint> configPoints);
	
	/**
	 * Service method to update configuration of a device
	 * 
	 * @param sourceId
	 * @param configPoints
	 * @param subscription
	 * @return
	 */
	public GeneralResponse updateDeviceConfiguration(Subscription subscription,String sourceId,
			List<ConfigPoint> configPoints);

	/**
	 * Service method to assign configured points to multiple devices
	 * 
	 * @param configTemplate
	 * @param sourceIds
	 * @return
	 */
	public GeneralBatchResponse assignConfPointsToDevices(
			ConfigureDevice configureDevice, Subscription subscription);
	
	public GeneralBatchResponse assignTemplateToDevices(
			TemplateAssign templateAssign , Subscription subscription);

}
