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

import com.pcs.datasource.dto.CommandType;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.ProtocolVersion;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
public interface DeviceProtocolService {

	/**
	 * @param device
	 */
	public void updateDeviceRelation(Device device);

	/**
	 * @param name
	 * @return
	 */
	public ProtocolVersion getDeviceProtocolVersion(ConfigurationSearch version);

	/**
	 * * Method to fetch supported commands for particular device protocol
	 * 
	 * @param deviceType
	 * @param protocolName
	 * @return {@link List<CommandType>}
	 */
	public List<CommandType> getCommandsOfDeviceProtocol(
	        ConfigurationSearch configuration);

	/**
	 * Method to insert the unitId for RECYCLING
	 * 
	 * @param device
	 */
	public void recycleUnitId(Device device);
}
