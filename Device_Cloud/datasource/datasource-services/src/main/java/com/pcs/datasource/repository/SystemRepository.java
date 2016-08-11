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

import java.util.List;

import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DeviceProtocol;
import com.pcs.datasource.dto.DeviceType;
import com.pcs.datasource.dto.Model;
import com.pcs.datasource.dto.Point;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.dto.SystemTag;
import com.pcs.datasource.model.PhysicalQuantity;

/**
 * Repository Interface to define operations on data_type node
 * 
 * @author pcseg199(Javid Ahammed)
 * @date May 19, 2015
 * @since galaxy-1.0.0
 */
public interface SystemRepository {

	/**
	 * Repository method to fetch a type of data available in the system
	 * 
	 * @return
	 */
	public <T> List<T> getAllOfAType(String vertexLabel, Class<T> type);

	/**
	 * Repository method to fetch all the system tags available in the system
	 * with optional filter on {@link PhysicalQuantity}
	 * 
	 * @param physicalQty
	 * @return
	 */
	public List<SystemTag> getAllSystemTags(String physicalQty);

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
	 * Service Method to get device models of a device type
	 * 
	 * @param configuration
	 * @return List<ProtocolVersion>
	 */
	public List<ProtocolVersion> getProtocolVersions(
			ConfigurationSearch configuration);
	
	/**
	 * Repository Method to get device pints of a protocol version
	 * 
	 * @param configuration
	 * @return List<Point>
	 */
	public List<Point> getProtocolVersionPoint(ConfigurationSearch configuration);
	
	/**
	 * Method to check a device make already exists or not
	 * 
	 * @param makeName
	 * @return - true if the make exists else false
	 */
	public boolean isDeviceMakeExist(String makeName);
	
	/**
	 * Repository Method to create device make
	 * 
	 * @param configuration
	 * @return
	 */
	public void createDeviceMake(ConfigurationSearch configuration);
	
	/**
	 * Repository Method to update device make
	 * 
	 * @param uniqueId 
	 * @param configuration
	 */
	public void updateDeviceMake(String uniqueId, ConfigurationSearch configuration);
	
	/**
	 * Method to check a device type already exists or not
	 * 
	 * @param typeName
	 * @return
	 */
	public boolean isDeviceTypeExist(String typeName);
	
	/**
	 * Repository Method to create device type
	 * 
	 * @param configuration
	 */
	public void createDeviceType(ConfigurationSearch configuration);
	
	/**
	 * Repository Method to update device type
	 * 
	 * @param uniqueId
	 * @param configuration
	 */
	public void updateDeviceType(String uniqueId, ConfigurationSearch configuration);
	
	/**
	 * Method to check a device model already exists or not
	 * 
	 * @param configuration
	 * @return
	 */
	public boolean isDeviceModelExist(ConfigurationSearch configuration);
	
	/**
	 * Repository Method to create device model
	 * 
	 * @param configuration
	 */
	public void createDeviceModel(ConfigurationSearch configuration);
	
	/**
	 * Repository Method to update device model
	 * 
	 * @param uniqueId
	 * @param configuration
	 */
	public void updateDeviceModel(String uniqueId, ConfigurationSearch configuration);
	
	/**
	 * Method to check a device protocol already exists or not
	 * 
	 * @param configuration
	 * @return
	 */
	public boolean isDeviceProtocolExist(ConfigurationSearch configuration);
	
	/**
	 * Repository Method to create device protocol
	 * 
	 * @param configuration
	 */
	public void createDeviceProtocol(ConfigurationSearch configuration);
	
	/**
	 * Repository Method to update device protocol
	 * 
	 * @param uniqueId
	 * @param configuration
	 */
	public void updateDeviceProtocol(String uniqueId, ConfigurationSearch configuration);
	
	/**
	 *  Method to check a device protocol version already exists or not
	 *  
	 * @param configuration
	 * @return
	 */
	public boolean isDeviceProtocolVersionExist(ConfigurationSearch configuration);
	
	/**
	 * Repository Method to create device protocol version
	 * 
	 * @param deviceProtocolBean
	 */
	public void createDeviceProtocolVersion(ConfigurationSearch configuration);
	
	/**
	 * Repository Method to update device protocol version
	 * 
	 * @param uniqueId
	 * @param configuration
	 */
	public void updateDeviceProtocolVersion(String uniqueId, ConfigurationSearch configuration);

}
