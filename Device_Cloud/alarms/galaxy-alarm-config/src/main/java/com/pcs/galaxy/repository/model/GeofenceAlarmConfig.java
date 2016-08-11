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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author pcseg369
 * 
 */
@Entity
@Table(name = "geofencing_configuration")
public class GeofenceAlarmConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long configId;

	private String sourceId;

	private String deviceId;

	private String assetId;

	private String assetName;

	private String geocoordinates;

	private String alarmName;

	private String alarmType;

	private String configType;

	private String alarmmessage;

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

	@Column(name = "alarmname")
	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmname) {
		this.alarmName = alarmname;
	}

	@Column(name = "alarmtype")
	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmtype) {
		this.alarmType = alarmtype;
	}

	@Column(name = "enabled")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "sourceId")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name = "geocoordinates")
	public String getGeocoordinates() {
		return geocoordinates;
	}

	public void setGeocoordinates(String geocoordinates) {
		this.geocoordinates = geocoordinates;
	}

	@Column(name = "alarmmessage")
	public String getAlarmmessage() {
		return alarmmessage;
	}

	public void setAlarmmessage(String alarmmessage) {
		this.alarmmessage = alarmmessage;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "configId")
	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	@Column(name = "configtype")
	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

}