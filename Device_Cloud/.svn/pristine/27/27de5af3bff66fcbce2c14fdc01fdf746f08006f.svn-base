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

import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceAuthentication;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.DeviceTag;
import com.pcs.datasource.dto.Subscription;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This service interface is responsible for defining all the services related
 * to Device communicating to the system. This class is responsible for
 * authenticate device with galaxy, register device once after the successful
 * authentication and manage services related to configuration template
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */
public interface DeviceService {

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
	 * @param Subscription
	 * @return
	 */
	public Device getDevice(String sourceId, Subscription subscription);

	/**
	 * Service method to register device once after the successful
	 * authentication
	 * 
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO registerDevice(Device device);

	/**
	 * Service Method to fetch point configuration of a device
	 * 
	 * @param entityId
	 * @return List<DeviceConfigTemplate>
	 */
	public DeviceConfigTemplate getConfigurations(String sourceId,
	        Subscription subscription);

	/**
	 * Service Method to fetch point configuration of a device without passing
	 * subscription
	 * 
	 * @param sourceId
	 * @return
	 */
	public DeviceConfigTemplate getConfigurations(String sourceId);

	/**
	 * Service Method to fetch all the devices in DeviceCloud
	 * 
	 * @return
	 */
	public List<Device> getAllDevices();

	/**
	 * Service Method to fetch all the devices based on a subId
	 * 
	 * @param subId
	 * @return
	 */
	public List<Device> getAllDevices(Subscription subscription,
	        List<String> tagNames);

	/**
	 * Service Method to fetch all the devices based on a subId and specified
	 * protocol version
	 * 
	 * @param subId
	 * @return
	 */
	public List<Device> getDevicesOfProtocolVersion(Subscription subscription,
	        ConfigurationSearch conSearch);

	/**
	 * Service Method for inserting a new device
	 * 
	 * @param device
	 * @return
	 */
	public StatusMessageDTO insertDevice(Device device);

	/**
	 * Service Method for updating a device
	 * 
	 * @param device
	 * @return
	 */
	public StatusMessageDTO updateDevice(String sourceId, Device device);

	/**
	 * Service Method for update write back configuration of a device
	 * 
	 * @param sourceId
	 * @param device
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO updateWritebackConf(String sourceId, Device device);

	/**
	 * Service Method to get datasource name of a device
	 * 
	 * @param sourceId
	 * @return dataSourceName
	 */
	public String getDatasourceName(Subscription subscription, String sourceId);

	/**
	 * Service Method to update tags of device
	 * 
	 * @param sourceId
	 * @param tags
	 */
	public StatusMessageDTO updateTags(String sourceId, List<DeviceTag> tags);

	/**
	 * Service Method to claim a device
	 * 
	 * Claiming is giving ownership of the device with the specified sourceId to
	 * the specified subscription
	 * 
	 * @param sourceId
	 * @param subscription
	 * @return
	 */
	public StatusMessageDTO claimDevice(String sourceId,
	        Subscription subscription);

	/**
	 * Service method to get all unsubscribed devices
	 * 
	 * @return - list of devices
	 */
	public List<Device> getAllUnSubscribed();

	/**
	 * Service method to authenticate a device
	 * 
	 * @param sourceId
	 * @return
	 */
	public DeviceAuthentication authenticateDevice(Device device);
}
