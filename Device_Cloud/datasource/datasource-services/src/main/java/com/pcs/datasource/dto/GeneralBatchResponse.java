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
 * GeneralBatchResponse
 * 
 * @description DTO which Comprises response for batch persist
 * 
 * @author Javid Ahammmed (pcseg199)
 * @date July 2015
 * @since device-cloud
 */
public class GeneralBatchResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	List<GeneralResponse> responses;

	public List<GeneralResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<GeneralResponse> responses) {
		this.responses = responses;
	}

}
