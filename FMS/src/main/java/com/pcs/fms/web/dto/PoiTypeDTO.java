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
package com.pcs.fms.web.dto;

import java.util.List;

import com.pcs.fms.web.model.FileUploadForm;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS-1.0.0
 * 
 */
public class PoiTypeDTO extends FileUploadForm {

	private String poiType;
	private String domainName;
	private String status;
	private FieldMapDTO poiTypeIdentifier;
	private List<String> poiTypeValues;

	public List<String> getPoiTypeValues() {
		return poiTypeValues;
	}

	public void setPoiTypeValues(List<String> poiTypeValues) {
		this.poiTypeValues = poiTypeValues;
	}

	public String getPoiType() {
		return poiType;
	}

	public void setPoiType(String poiType) {
		this.poiType = poiType;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public FieldMapDTO getPoiTypeIdentifier() {
		return poiTypeIdentifier;
	}

	public void setPoiTypeIdentifier(FieldMapDTO poiTypeIdentifier) {
		this.poiTypeIdentifier = poiTypeIdentifier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
