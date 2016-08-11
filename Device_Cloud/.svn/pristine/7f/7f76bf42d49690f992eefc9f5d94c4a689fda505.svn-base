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

import com.pcs.datasource.dto.BatchPersistResponse;
import com.pcs.datasource.dto.DeviceMessage;
import com.pcs.datasource.dto.DeviceMessageAggregatedResponse;
import com.pcs.datasource.dto.DeviceMessageResponse;
import com.pcs.datasource.dto.LatestDataResultDTO;
import com.pcs.datasource.dto.LatestDataSearchDTO;
import com.pcs.datasource.dto.LatestDeviceData;
import com.pcs.datasource.dto.SearchDTO;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This service class is responsible for defining all the services related to
 * data of a Device communicating to the system. This class is responsible for
 * persisting (single persist , batch persist) and fetching the device data
 * details from the communicating devices.
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */
public interface DataService {

	/**
	 * Method to fetch details of the device data communicating to the system
	 * 
	 * @param {@link SearchDTO}
	 * @return List<PhysicalQuantity>
	 */

	public DeviceMessageResponse getDeviceData(SearchDTO searchDTO);

	/**
	 * Method to fetch details of the device data communicating to the system
	 * plus aggregated data for fetched data
	 * 
	 * @param {@link SearchDTO}
	 * @return List<PhysicalQuantity>
	 */

	public DeviceMessageAggregatedResponse getDeviceAndAggregatedData(SearchDTO searchDTO);

	/**
	 * Method to fetch latest received data of a device
	 * 
	 * @param sourceId
	 * @return
	 */
	public List<LatestDeviceData> getDeviceLatestData(String sourceId);

	/**
	 * Method to fetch latest received data of multiple devices and its specific
	 * points.
	 * 
	 * @param LatestDataSearch
	 * @return
	 */
	public List<LatestDataResultDTO> getDevicesLatestData(
	        List<LatestDataSearchDTO> LatestDataSearch);

}
