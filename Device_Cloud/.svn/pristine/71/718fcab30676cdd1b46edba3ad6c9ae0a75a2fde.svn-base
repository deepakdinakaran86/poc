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
package com.pcs.galaxy.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DeviceThreshold
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Entity
@Table(name = "device_threshold")
public class DeviceThreshold implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConfigurationId id;

	private String deviceId;

	private String assetId;
	private String assetName;

	private String unit;

	private String minVal;
	private String maxVal;
	private String lowerRange;
	private String upperRange;

	private String alarmName;
	private String displayName;
	private String alarmAttribute;

	private String message;
	private String minAlarmMsg;
	private String maxAlarmMsg;
	private String rangeAlarmMsg;

	private String status;
	private boolean enabled;

	@Column(name = "deviceId")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "assetId")
	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	@Column(name = "assetName")
	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@Column(name = "unit")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "minval")
	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minval) {
		this.minVal = minval;
	}

	@Column(name = "maxval")
	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxval) {
		this.maxVal = maxval;
	}

	@Column(name = "lowerRange")
	public String getLowerRange() {
		return lowerRange;
	}

	public void setLowerRange(String lowerRange) {
		this.lowerRange = lowerRange;
	}

	@Column(name = "upperRange")
	public String getUpperRange() {
		return upperRange;
	}

	public void setUpperRange(String upperRange) {
		this.upperRange = upperRange;
	}

	@Column(name = "alarmname")
	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmname) {
		this.alarmName = alarmname;
	}

	@Column(name = "displayName")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(name = "alarmattribute")
	public String getAlarmAttribute() {
		return alarmAttribute;
	}

	public void setAlarmAttribute(String alarmattribute) {
		this.alarmAttribute = alarmattribute;
	}

	@Column(name = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "minAlarmMsg")
	public String getMinAlarmMsg() {
		return minAlarmMsg;
	}

	public void setMinAlarmMsg(String minAlarmMsg) {
		this.minAlarmMsg = minAlarmMsg;
	}

	@Column(name = "maxAlarmMsg")
	public String getMaxAlarmMsg() {
		return maxAlarmMsg;
	}

	public void setMaxAlarmMsg(String maxAlarmMsg) {
		this.maxAlarmMsg = maxAlarmMsg;
	}

	@Column(name = "rangeAlarmMsg")
	public String getRangeAlarmMsg() {
		return rangeAlarmMsg;
	}

	public void setRangeAlarmMsg(String rangeAlarmMsg) {
		this.rangeAlarmMsg = rangeAlarmMsg;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "enabled")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ConfigurationId getId() {
		return id;
	}

	public void setId(ConfigurationId id) {
		this.id = id;
	}

}