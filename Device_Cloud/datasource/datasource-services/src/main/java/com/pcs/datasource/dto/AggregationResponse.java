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
 * AggregationResponse
 * 
 * @description Response for Aggregation 
 * 
 * @author PCSEG297(Twinkle)
 * @date April 2016
 */
public class AggregationResponse implements Serializable {

	private static final long serialVersionUID = -3237411511446295752L;

	private List<DisplayNameAggregatedData> data;
	private List<GeneralResponse> generalResponses;

	public List<DisplayNameAggregatedData> getData() {
		return data;
	}

	public void setData(List<DisplayNameAggregatedData> data) {
		this.data = data;
	}

	public List<GeneralResponse> getGeneralResponses() {
		return generalResponses;
	}

	public void setGeneralResponses(List<GeneralResponse> generalResponses) {
		this.generalResponses = generalResponses;
	}
}
