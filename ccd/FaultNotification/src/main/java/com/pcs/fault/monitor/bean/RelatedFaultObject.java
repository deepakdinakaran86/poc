
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.fault.monitor.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is responsible for holding related fault code objects
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 24, 2016
 */
public class RelatedFaultObject implements Serializable{

    private static final long serialVersionUID = -6870067908848901202L;

	private String relatedFaultCode;
	
	private String relatedSPN;
	
	private String relatedFMI;
	
	private String relatedOccurrenceDateTime;
	
	private String relatedFaultCodeDescription;

	public String getRelatedFaultCode() {
		return relatedFaultCode;
	}

	public void setRelatedFaultCode(String relatedFaultCode) {
		this.relatedFaultCode = relatedFaultCode;
	}

	public String getRelatedSPN() {
		return relatedSPN;
	}

	public void setRelatedSPN(String relatedSPN) {
		this.relatedSPN = relatedSPN;
	}

	public String getRelatedFMI() {
		return relatedFMI;
	}

	public void setRelatedFMI(String relatedFMI) {
		this.relatedFMI = relatedFMI;
	}

	public String getRelatedOccurrenceDateTime() {
		return relatedOccurrenceDateTime;
	}

	public void setRelatedOccurrenceDateTime(String relatedOccurrenceDateTime) {
		this.relatedOccurrenceDateTime = relatedOccurrenceDateTime;
	}

	public String getRelatedFaultCodeDescription() {
		return relatedFaultCodeDescription;
	}

	public void setRelatedFaultCodeDescription(String relatedFaultCodeDescription) {
		this.relatedFaultCodeDescription = relatedFaultCodeDescription;
	}
	
	
}
