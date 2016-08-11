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
package com.pcs.guava.dto;

import java.util.List;

import com.pcs.guava.commons.dto.FieldMapDTO;

/**
 * DTO for POI
 * 
 * @author Greeshma (PCSEG323)
 * @date April 2016
 * @since Guava-1.0.0
 */
public class PoiDTO {
	private String domainName;
	private String poiType;
	private String poiName;
	private String description;
	private FieldMapDTO poiIdentifier;
	private FieldMapDTO poiTypeIdentifier;
	private List<FieldMapDTO> poiTypeValues;
	private String status;
	private String latitude;
	private String longitude;
	private String radius;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getPoiType() {
		return poiType;
	}

	public void setPoiType(String poiType) {
		this.poiType = poiType;
	}

	public String getPoiName() {
		return poiName;
	}

	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FieldMapDTO getPoiIdentifier() {
		return poiIdentifier;
	}

	public void setPoiIdentifier(FieldMapDTO poiIdentifier) {
		this.poiIdentifier = poiIdentifier;
	}

	public FieldMapDTO getPoiTypeIdentifier() {
		return poiTypeIdentifier;
	}

	public void setPoiTypeIdentifier(FieldMapDTO poiTypeIdentifier) {
		this.poiTypeIdentifier = poiTypeIdentifier;
	}

	public List<FieldMapDTO> getPoiTypeValues() {
		return poiTypeValues;
	}

	public void setPoiTypeValues(List<FieldMapDTO> poiTypeValues) {
		this.poiTypeValues = poiTypeValues;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

}
