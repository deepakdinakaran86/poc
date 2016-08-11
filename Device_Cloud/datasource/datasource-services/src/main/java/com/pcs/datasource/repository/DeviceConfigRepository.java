/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
 *
 * This software is the property of Pacific Controls  Software  Services LLC  and  its
 * suppliers. The intellectual and technical concepts contained herein are proprietary 
 * to PCSS. Dissemination of this information or  reproduction  of  this  material  is
 * strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
 * Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
 * MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.pcs.datasource.repository;

import java.util.List;
import java.util.Set;

import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.GeneralBatchResponse;
import com.pcs.datasource.dto.Subscription;

/**
 * This class is responsible for 'C R U D' operations on the device point
 * configuration
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 02 Jul 2015
 */
public interface DeviceConfigRepository {

	/**
	 * Method to check a device config template name already exists or not
	 * 
	 * @param tempName
	 * @return - true if the template name exists for the subscription else
	 *         false
	 */
	public boolean isDeviceConfigTempNameExist(String subId, String tempName);

	/**
	 * Method to get device configuration template's parent by traversing
	 * through device make,device type,device model,device protocol and protocol
	 * version.
	 * 
	 * @param configSearch
	 *            - configuration template
	 * @return - a reference string of the parent
	 */
	public String findParentData(ConfigurationSearch configSearch);

	/**
	 * Method to check a config point is valid - config point should be a valid
	 * point of protocol version
	 * 
	 * @param configSearch
	 *            - ConfigurationSearch
	 * @param configPoint
	 *            - Configuration point
	 * @return - true if point is a valid point of protocol version else false
	 */
	public boolean isValidPoint(ConfigurationSearch configSearch,
			ConfigPoint configPoint);

	/**
	 * Method to check a config point is valid - config point should be a valid
	 * point of protocol version
	 * 
	 * @param parentRef
	 *            - parent reference
	 * @param configPoint
	 *            - Configuration point
	 * @return - true if point is a valid point of protocol version else false
	 */
	public boolean isValidPoint(String parentRef, ConfigPoint configPoint);

	/**
	 * Method to check a parameter is valid - parameter of the subscription with
	 * a valid parameter name and physical quantity
	 * 
	 * @param subId
	 *            - subscriptionId
	 * @param configPoint
	 *            - Configure point with parameter info
	 * @return - true if parameter is a valid parameter of subscription else
	 *         false
	 */
	public boolean isValidParameter(String subId, ConfigPoint configPoint);

	/**
	 * Method to check if a device configuration template is valid - by checking
	 * the device make,device type,device model,device protocol and protocol
	 * version . Also the template should be in an hierarchy of
	 * device_make->device_type->device_model->device_protocol->protocol_version
	 * ->config_template
	 * 
	 * @param configTemp
	 *            - configuration template
	 * @return - true if configuration is valid else false
	 */
	public boolean isValidDeviceConfigTemp(DeviceConfigTemplate configTemp);

	/**
	 * Method to save device configuration template
	 * 
	 * @param configTemp
	 */
	public void saveDeviceConfigTemp(String subId,
			DeviceConfigTemplate configTemp, boolean isWithPointRef);

	/**
	 * Method to update device configuration template
	 * 
	 * @param configTemp
	 */
	public void updateDeviceConfigTemp(String subId,
			DeviceConfigTemplate configTemp, boolean isWithPointRef);

	/**
	 * Repository method to fetch all the device configuration template of a
	 * subscripotion with an optional filtere on protocol version
	 * 
	 * @param subId
	 * @param conSearch
	 * @return
	 */
	public List<DeviceConfigTemplate> getAllConfTemplates(String subId,
			ConfigurationSearch conSearch);

	/**
	 * Repository method to fetch device configuration template of the specified
	 * subId and templateName
	 * 
	 * @param subId
	 * @param templateName
	 * @return
	 */
	public DeviceConfigTemplate getConfTemplate(String subId,
			String templateName);

	/**
	 * Repository method to detach all the devices from the specified
	 * confTemplates
	 * 
	 * @param confTemplates
	 */
	public void detachAllDevice(String subId, List<String> confTemplateNames);

	/**
	 * Service method to fetch details of a device configuration template
	 * 
	 * @param subId
	 * @param sourceId
	 * @return
	 */
	public DeviceConfigTemplate getDeviceConfiguration(String subId,
			String sourceId);

	/**
	 * Repository method to assign configured points to devices
	 * 
	 * @param configTemplate
	 * @param sourceIds
	 */
	public GeneralBatchResponse assignConfigPointToDevices(
			DeviceConfigTemplate configTemplate, Set<String> sourceIds,
			boolean isDump);

	/**
	 * Repository method for fetching all the points under a protocol version
	 * 
	 * @param configurationSearch
	 * @return
	 */
	public List<ConfigPoint> getPointsOfAProtocolVersion(
			ConfigurationSearch configurationSearch);
	
	public GeneralBatchResponse assignTemplateToDevices(
			String templateName, Set<String> sourceIds, Subscription subscription);

}
