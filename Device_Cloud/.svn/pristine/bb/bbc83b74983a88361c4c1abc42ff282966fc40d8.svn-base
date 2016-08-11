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

/**
 * This repository class is responsible for defining all the services related to
 * data of a Device communicating to the system. This class is responsible for
 * persisting (single persist , batch persist) and fetching the device data
 * details from the communicating devices.
 *
 * @author pcseg199 (Javid Ahammed)
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;

import com.pcs.datasource.dto.BatchPersistResponse;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.TimeSeriesData;
import com.pcs.datasource.dto.DeviceMessage;
import com.pcs.datasource.dto.LatestDeviceData;
import com.pcs.datasource.model.Data;

public interface DataRepo {

	/**
	 * Method to fetch details of the device data communicating to the system
	 * @param uid
	 * @param startDateValue
	 * @param endDateValue
	 * @param customTag
	 * @return List<PhysicalQuantity>
	 *
	 */

	public List<TimeSeriesData> getDeviceData(UUID uid,
	        DateTime startDateValue, DateTime endDateValue, List<ConfigPoint> fetchPoints);
	
	/**
	 * Method to fetch details of the device data communicating to the system with
	 * aggregated data
	 * @param uid
	 * @param startDateValue
	 * @param endDateValue
	 * @param customTag
	 * @return List<PhysicalQuantity>
	 *
	 */
	public TimeSeriesData getDeviceAndAggregatedData(UUID uid,
	        DateTime startDateValue, DateTime endDateValue, ConfigPoint fetchPoints, List<String> aggregationFunctions);


	/**
	 * @param physicalQtyName
	 * @param customTags
	 * @return
	 */
	public LatestDeviceData getLatestData(String deviceId,String physicalQtyName,
	        String pointDisplayName);
	
	/**
	 * Method to fetch aggregated data of the device data communicating to the system
	 * @param uid
	 * @param startDateValue
	 * @param endDateValue
	 * @param customTag
	 * @return List<PhysicalQuantity>
	 *
	 */

	public Map<String, String> getAggregatedData(UUID uid,
	        DateTime startDateValue, DateTime endDateValue,
	        ConfigPoint configPoint, List<String> aggregationFunctions);
}
