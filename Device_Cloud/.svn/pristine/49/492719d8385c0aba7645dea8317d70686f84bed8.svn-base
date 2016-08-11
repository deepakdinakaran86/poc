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

import com.pcs.datasource.dto.Aggregation;
import com.pcs.datasource.dto.AggregationResponse;
import com.pcs.datasource.dto.SearchDTO;

/**
 * This service class is responsible for defining all the services related to
 * analytics which can be perform on data of a Device communicating to the
 * system.
 * 
 * @author pcseg297 (Twinkle)
 * @date April 2016
 * @since galaxy-1.0.0
 */
public interface AnalyticsService {

	/**
	 * Method to fetch aggregation (MIN, MAX, AVG, SUM, RANGE and COUNT) of the device
	 * data communicating to the system
	 * 
	 * @param {@link SearchDTO}
	 * @return List<PhysicalQuantity>
	 */
	public AggregationResponse getAggregatedData(Aggregation aggregation);
}
