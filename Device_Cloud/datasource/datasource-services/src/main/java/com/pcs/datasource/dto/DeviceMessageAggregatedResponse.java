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
import java.util.UUID;

/**
 * DeviceMessageResponse
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */
public class DeviceMessageAggregatedResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private UUID sourceId;
	private List<TimeSeriesData> displayNames;
	
	private List<GeneralResponse> generalResponses;
	
	public UUID getSourceId() {
		return sourceId;
	}

	public void setSourceId(UUID sourceId) {
		this.sourceId = sourceId;
	}

	public List<GeneralResponse> getGeneralResponses() {
	    return generalResponses;
    }

	public void setGeneralResponses(List<GeneralResponse> generalResponses) {
	    this.generalResponses = generalResponses;
    }

	public List<TimeSeriesData> getDisplayNames() {
		return displayNames;
	}

	public void setDisplayNames(List<TimeSeriesData> displayNames) {
		this.displayNames = displayNames;
	}

}
