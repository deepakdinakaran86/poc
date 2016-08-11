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

import com.google.gson.annotations.SerializedName;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 5, 2015
 * @since galaxy-1.0.0
 */
public class Point implements Serializable {

	private static final long serialVersionUID = -6134366232373786085L;

	@SerializedName("point_id")
	private String pointId;
	@SerializedName("point_name")
	private String pointName;
	@SerializedName("type")
	protected String type;
	@SerializedName("unit")
	private String unit;
	@SerializedName("access_type")
	private String pointAccessType;
	@SerializedName("created_at")
	private Long createdAt;
	@SerializedName("display_name")
	private String displayName;
	@SerializedName("description")
	private String description;

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getType() {
		return type.toUpperCase();
	}

	public void setType(String dataType) {
		this.type = dataType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPointAccessType() {
		return pointAccessType;
	}

	public void setPointAccessType(String accessType) {
		this.pointAccessType = accessType;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
