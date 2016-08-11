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
package com.pcs.galaxy.dto;

import java.io.Serializable;
import java.util.List;

/**
 * AssetDTO
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
public class AssetDTO implements Serializable {

	private static final long serialVersionUID = -775482194445563861L;
	private String domainName;
	private String assetType;
	private String assetName;
	private String description;
	private FieldMapDTO assetIdentifier;
	private FieldMapDTO assetTypeIdentifier;
	private List<FieldMapDTO> assetTypeValues;
	private List<EntityDTO> points;

	private String assetId;
	private String serialNumber;

	private List<String> tags;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<FieldMapDTO> getAssetTypeValues() {
		return assetTypeValues;
	}

	public void setAssetTypeValues(List<FieldMapDTO> assetTypeValues) {
		this.assetTypeValues = assetTypeValues;
	}

	public FieldMapDTO getAssetIdentifier() {
		return assetIdentifier;
	}

	public void setAssetIdentifier(FieldMapDTO assetIdentifier) {
		this.assetIdentifier = assetIdentifier;
	}

	public FieldMapDTO getAssetTypeIdentifier() {
		return assetTypeIdentifier;
	}

	public void setAssetTypeIdentifier(FieldMapDTO assetTypeIdentifier) {
		this.assetTypeIdentifier = assetTypeIdentifier;
	}

	public List<EntityDTO> getPoints() {
		return points;
	}

	public void setPoints(List<EntityDTO> points) {
		this.points = points;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
