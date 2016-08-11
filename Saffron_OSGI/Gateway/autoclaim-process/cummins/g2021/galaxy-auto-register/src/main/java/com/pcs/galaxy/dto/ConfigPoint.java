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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.gson.annotations.SerializedName;

/**
 * ConfigPoint
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
public class ConfigPoint extends Point implements Serializable {
	private static final long serialVersionUID = -7406764176806119201L;

	@SerializedName("parameter")
	private String parameter;

	@SerializedName("physical_quantity")
	private String physicalQuantity;

	@SerializedName("system_tag")
	private String systemTag;

	@SerializedName("acquisition")
	private String acquisition;

	@SerializedName("precedence")
	private String precedence;

	private DataTypes dataType;

	private String data;

	@SerializedName("expression")
	private String expression;

	@SerializedName("custom_tags")
	private List<PointTag> customTags;

	private List<DeviceFieldMap> extensions;

	private List<AlarmExtension> alarmExtensions;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getPhysicalQuantity() {
		return physicalQuantity;
	}

	public void setPhysicalQuantity(String physicalQuantity) {
		this.physicalQuantity = physicalQuantity.toLowerCase();
	}

	public DataTypes getDataType() {
		return this.dataType;
	}

	public void setDataType(String type) throws Exception {
		type = type.toUpperCase();
		this.type = type;
		setType(type);
		this.dataType = DataTypes.valueOf(type);
	}

	/*
	 * @Override public void setType(String type) throws Exception { this.type =
	 * type.toUpperCase(); setType(type); try { this.dataType =
	 * DataTypes.valueOf(type); } catch (Exception e) { throw new
	 * DeviceCloudException( DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
	 * POINT_DATA_TYPE.getDescription(),type ); }
	 */
	public String getSystemTag() {
		return systemTag;
	}

	public void setSystemTag(String systemTag) {
		this.systemTag = systemTag;
	}

	public String getAcquisition() {
		return acquisition;
	}

	public void setAcquisition(String acquisition) {
		this.acquisition = acquisition;
	}

	public List<PointTag> getCustomTags() {
		return customTags;
	}

	public void setCustomTags(List<PointTag> customTags) {
		this.customTags = customTags;
	}

	public String getPrecedence() {
		return precedence;
	}

	public void setPrecedence(String precedence) {
		this.precedence = precedence;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public List<DeviceFieldMap> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<DeviceFieldMap> extensions) {
		this.extensions = extensions;
	}

	public List<AlarmExtension> getAlarmExtensions() {
		return alarmExtensions;
	}

	public void setAlarmExtensions(List<AlarmExtension> alarmExtensions) {
		this.alarmExtensions = alarmExtensions;
	}

	public void addExtension(DeviceFieldMap extension) {
		if (CollectionUtils.isEmpty(this.extensions)) {
			this.extensions = new ArrayList<DeviceFieldMap>();
		}
		this.extensions.add(extension);
	}

	public void addAlarmExtension(AlarmExtension alarmExtension) {
		if (CollectionUtils.isEmpty(this.alarmExtensions)) {
			this.alarmExtensions = new ArrayList<AlarmExtension>();
		}
		this.alarmExtensions.add(alarmExtension);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}