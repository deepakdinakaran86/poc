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
package com.pcs.web.dto;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 5, 2015
 * @since galaxy-1.0.0
 */
public class Device implements Serializable {

	private static final long serialVersionUID = -6134366232373786085L;

	@SerializedName("device_id")
	private String deviceId;
	@SerializedName("source_id")
	private String sourceId;
	@SerializedName("unit_id")
	private Integer unitId;
	@SerializedName("is_publishing")
	private Boolean isPublishing;
	@SerializedName("datasource_name")
	private String datasourceName;
	@SerializedName("configurations")
	private Object configurations;

	@SerializedName("time_zone")
	private String timeZone;

	@SerializedName("ip")
	private String ip;
	@SerializedName("connected_port")
	private Integer connectedPort;
	@SerializedName("writeback_port")
	private Integer writeBackPort;

	@SerializedName("subscription")
	private Subscription subscription;
	@SerializedName("nw_protocol")
	private NetworkProtocol networkProtocol;
	@SerializedName("tags")
	private List<DeviceTag> tags;
	@SerializedName("version")
	private ConfigurationSearch version;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Boolean getIsPublishing() {
		return isPublishing;
	}

	public void setIsPublishing(Boolean isPublishing) {
		this.isPublishing = isPublishing;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public Object getConfigurations() {
		return configurations;
	}

	public void setConfigurations(Object configurations) {
		this.configurations = configurations;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getConnectedPort() {
		return connectedPort;
	}

	public void setConnectedPort(Integer connectedPort) {
		this.connectedPort = connectedPort;
	}

	public Integer getWriteBackPort() {
		return writeBackPort;
	}

	public void setWriteBackPort(Integer writeBackPort) {
		this.writeBackPort = writeBackPort;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public NetworkProtocol getNetworkProtocol() {
		return networkProtocol;
	}

	public void setNetworkProtocol(NetworkProtocol networkProtocol) {
		this.networkProtocol = networkProtocol;
	}

	public List<DeviceTag> getTags() {
		return tags;
	}

	public void setTags(List<DeviceTag> tags) {
		this.tags = tags;
	}

	public ConfigurationSearch getVersion() {
		return version;
	}

	public void setVersion(ConfigurationSearch version) {
		this.version = version;
	}

}
