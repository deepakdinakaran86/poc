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

package com.pcs.datasource.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Aggregation
 * 
 * @description This class is responsible for allowing aggregarion on Data.
 * 
 * @author PCSEG297(Twinkle)
 * @date April 2016
 */
public class Aggregation implements Serializable {

	private static final long serialVersionUID = 3212502744245805099L;

	private Long startDate;
	private Long endDate;
	private List<String> aggregationFunctions;
	private List<DevicePointsMap> devicePointsMap;

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public List<String> getAggregationFunctions() {
	    return aggregationFunctions;
    }

	public void setAggregationFunctions(List<String> aggregationFunctions) {
	    this.aggregationFunctions = aggregationFunctions;
    }

	public List<DevicePointsMap> getDevicePointsMap() {
	    return devicePointsMap;
    }

	public void setDevicePointsMap(List<DevicePointsMap> devicePointsMap) {
	    this.devicePointsMap = devicePointsMap;
    }
}
