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
package com.pcs.datasource.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.DevicePointData;
import com.pcs.datasource.dto.Subscription;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 5, 2015
 * @since galaxy-1.0.0
 */
public interface DeviceRepository {
	/**
	 * Service method to authenticate the device communicating to the system
	 * 
	 * @param sourceId
	 * @return
	 */
	public Device getDevice(String sourceId);

	/**
	 * Service method to authenticate the device communicating to the system by
	 * unit id and version
	 * 
	 * @param sourceId
	 * @return
	 */
	public Device getDevice(String unitId, ConfigurationSearch version);

	/**
	 * Service method to authenticate the device communicating to the system
	 * 
	 * @param sourceId
	 * @return
	 */
	public Device getDevice(String sourceId, Subscription subscription);

	/**
	 * Service method to register device once after the successful
	 * authentication
	 * 
	 * @return {@link StatusMessageDTO}
	 */
	public void registerDevice(Device device);

	/**
	 * Service Method to update point configuration of a device
	 * 
	 * @param confTemplateDTO
	 * @return {@link StatusMessageDTO}
	 */
	public void updateConfigurations(String sourceId,
	        DeviceConfigTemplate deviceConfigTemplate);

	/**
	 * Service Method to fetch point configuration of a device
	 * 
	 * @param entityId
	 * @return List<PointConfigurationDTO>
	 */
	public List<DevicePointData> getDeviceConfig(String sourceId);

	/**
	 * Repository Method to fetch all the devices in DeviceCloud
	 * 
	 * @return
	 */
	public List<Device> getAllDevices();

	/**
	 * Repository Method to fetch all the devices based on a subId
	 * 
	 * @param subId
	 * @return
	 * @throws Exception
	 */
	public List<Device> getAllDevices(String subId, List<String> tagNames);

	/**
	 * Service Method for inserting a new device
	 * 
	 * @param device
	 * @return
	 */
	public void insertDevice(Device device);

	/**
	 * Service Method for updating a device
	 * 
	 * @param device
	 * @return
	 */
	public void updateDevice(Device device);

	/**
	 * Service Method for fetching max value of unitId
	 * 
	 * @return
	 */
	public Integer generateUnitId(ConfigurationSearch version);

	/**
	 * Service Method for update write back configuration of a device
	 * 
	 * @param sourceId
	 * @param device
	 */
	public void updateWritebackConf(String sourceId, Device device);

	/**
	 * Service Method to get datasource name of a device
	 * 
	 * @param sourceId
	 * @return dataSourceName
	 */
	public String getDatasourceName(Subscription subscription, String sourceId);

	/**
	 * Repository Method to get all devices of the specified protocol version
	 * 
	 * @param subId
	 * @param protcolVersion
	 * @return
	 */
	public List<Device> getAllDeviceOfProtocol(Subscription subscription,
	        ConfigurationSearch searchDTO);

	/**
	 * Repository Method to claim a device
	 * 
	 * @param sourceId
	 * @param subscription
	 */
	public void claimDevice(String sourceId, Subscription subscription);

	/**
	 * Service method to get all unsubscribed devices
	 * 
	 * @return - list of devices
	 */
	public List<Device> getAllUnSubscribed();

	/**
	 * Repository Method to get sourceId-deviceId map of a list of sourceIds
	 * 
	 * @param sourceIds
	 * @return
	 */
	public HashMap<String, String> getDeviceReference(Set<String> sourceIds);

}
