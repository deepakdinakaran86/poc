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

import com.pcs.datasource.dto.AccessType;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DataType;
import com.pcs.datasource.dto.DeviceProtocol;
import com.pcs.datasource.dto.DeviceType;
import com.pcs.datasource.dto.Make;
import com.pcs.datasource.dto.Model;
import com.pcs.datasource.dto.NetworkProtocol;
import com.pcs.datasource.dto.Point;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.dto.SystemTag;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * Service Interface to define operations on data_type node
 * 
 * @author pcseg199(Javid Ahammed)
 * @date May 19, 2015
 * @since galaxy-1.0.0
 */
public interface SystemService {

	/**
	 * Service method to fetch all the data types available in the system
	 * 
	 * @return
	 */
	public List<DataType> getAllDataTypes();

	/**
	 * Service method to fetch all the access types available in the system
	 * 
	 * @return
	 */
	public List<AccessType> getAllAccessTypes();

	/**
	 * Service method to fetch all the network protocols available in the system
	 * 
	 * @return
	 */
	public List<NetworkProtocol> getAllNwProtocols();

	/**
	 * Service method to fetch all the system tags available in the system
	 * ,optionally filtered with physical quantity
	 * 
	 * @param physicalQty
	 * @return
	 */
	public List<SystemTag> getAllSystemTags(String physicalQty);

	/**
	 * Service Method to get all device makes
	 * 
	 * @return List<Make>
	 */
	public List<Make> getDeviceMakes();

	/**
	 * Service Method to get device types of a device make.
	 * 
	 * @param makeName
	 * @return List<DeviceType>
	 */
	public List<DeviceType> getDeviceTypes(String makeName);

	/**
	 * Service Method to get device models of a device type
	 * 
	 * @param configuration
	 * @return List<Model>
	 */
	public List<Model> getDeviceModels(ConfigurationSearch configuration);

	/**
	 * Service Method to get device models of a device type
	 * 
	 * @param configuration
	 * @return List<DeviceProtocol>
	 */
	public List<DeviceProtocol> getDeviceProtocols(
			ConfigurationSearch configuration);

	/**
	 * Service Method to get protocol versions of device protocol
	 * 
	 * @param configuration
	 * @return List<ProtocolVersion>
	 */
	public List<ProtocolVersion> getProtocolVersions(
			ConfigurationSearch configuration);

	/**
	 * Service Method to get device pints of a protocol version
	 * 
	 * @param configuration
	 * @return List<Point>
	 */
	public List<Point> getProtocolVersionPoint(ConfigurationSearch configuration);
	
	/**
	 * Service Method to create device make
	 * 
	 * @param configuration
	 * @return
	 */
	public StatusMessageDTO createDeviceMake(ConfigurationSearch configuration);
	
	/**
	 * Service Method to update device make
	 * 
	 * @param existingMakeName TODO
	 * @param configuration
	 * 
	 * @return
	 */
	public StatusMessageDTO updateDeviceMake(String existingMakeName, ConfigurationSearch configuration);
	
	/**
	 * Service Method to create device type
	 * 
	 * @param configuration
	 * @return
	 */
	public StatusMessageDTO createDeviceType(ConfigurationSearch configuration);
	
	/**
	 * Service Method to update device type
	 * 
	 * @param existingDeviceTypeName
	 * @param configuration
	 * @return
	 */
	public StatusMessageDTO updateDeviceType(String existingDeviceTypeName, ConfigurationSearch configuration);
	
	/**
	 * Service Method to create device model
	 * 
	 * @param configuration
	 * @return
	 */
	public StatusMessageDTO createDeviceModel(ConfigurationSearch configuration);
	
	/**
	 * Service Method to update device model
	 * 
	 * @param existingModelName
	 * @param configuration
	 * @return
	 */
	public StatusMessageDTO updateDeviceModel(String existingModelName, ConfigurationSearch configuration);
	
	/**
	 * Service Method to create device protocol
	 * 
	 * @param configuration
	 * @return
	 */
	public StatusMessageDTO createDeviceProtocol(ConfigurationSearch configuration);
	
	/**
	 * Service Method to update device protocol
	 * 
	 * @param existingDeviceProtocolName
	 * @param configuration
	 * @return
	 */
	public StatusMessageDTO updateDeviceProtocol(String existingDeviceProtocolName, ConfigurationSearch configuration);
	
	/**
	 * Service Method to create device protocol version
	 * 
	 * @param configuration
	 * @return
	 */
	public StatusMessageDTO createDeviceProtocolVersion(ConfigurationSearch configuration);
	
	/**
	 * Service Method to update device protocol version
	 * 
	 * @param existingDeviceProtocolVersion
	 * @param configuration
	 * @return
	 */
	public StatusMessageDTO updateDeviceProtocolVersion(String existingDeviceProtocolVersion, ConfigurationSearch configuration);

}
