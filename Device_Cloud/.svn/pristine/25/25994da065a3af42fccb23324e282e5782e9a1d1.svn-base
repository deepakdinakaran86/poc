/**
 * 
 */
package com.pcs.device.gateway.meitrack.devicemanager.device.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.pcs.device.gateway.meitrack.devicemanager.auth.RemoteAuthenticationResponse;
import com.pcs.device.gateway.meitrack.devicemanager.bean.DeviceComnProtocol;
import com.pcs.device.gateway.meitrack.devicemanager.bean.DeviceProtocol;
import com.pcs.device.gateway.meitrack.devicemanager.bean.DeviceType;
import com.pcs.device.gateway.meitrack.devicemanager.bean.Subscription;
import com.pcs.device.gateway.meitrack.devicemanager.bean.Tag;
import com.pcs.deviceframework.decoder.data.point.Point;

/**
 * @author pcseg310
 *
 */
@JsonAutoDetect
@JsonInclude(value = Include.NON_NULL)
public class ProtocolPointResponse implements RemoteAuthenticationResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 799008560234231600L;

	private Integer unitId;
	private String datasourceName;
	private List<Point> configurations;
	private String deviceId;
	private String sourceId;
	private Boolean isPublishing;
	private Subscription subscription;
	private DeviceType type;
	private DeviceProtocol protocol;
	private DeviceComnProtocol networkProtocol;
	private List<Tag> tags;

	public List<Point> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<Point> configurations) {
		this.configurations = configurations;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

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

	public Boolean getIsPublishing() {
		return isPublishing;
	}

	public void setIsPublishing(Boolean isPublishing) {
		this.isPublishing = isPublishing;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public DeviceType getType() {
		return type;
	}

	public void setType(DeviceType type) {
		this.type = type;
	}

	public DeviceProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(DeviceProtocol protocol) {
		this.protocol = protocol;
	}

	public DeviceComnProtocol getNetworkProtocol() {
		return networkProtocol;
	}

	public void setNetworkProtocol(DeviceComnProtocol networkProtocol) {
		this.networkProtocol = networkProtocol;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
