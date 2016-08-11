package com.pcs.saffron.manager.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.pcs.saffron.manager.registration.bean.DeviceUser;

/**
 * @author pcseg310
 * 
 */
@JsonAutoDetect
public class Device implements Serializable {

	private static final long serialVersionUID = -2049990530877806985L;
	private String deviceId;
	private String datasourceName;
	private String sourceId;
	private Subscription subscription;
	private DeviceProtocol networkProtocol;
	private List<Tag> tags;
	private Boolean isPublishing;
	private String ip;
	private Integer connectedPort;
	private Integer writeBackPort;
	private Version version;
	private Float latitude;
	private Float longitude;
	private String deviceName;
	private String timezone;
	private Short gmtOffset;
	private Configuration configurations;
	private Integer unitId;
	private String URL;
	private String slot;
	private String userName;
	private String password;
	private String token;
	private String timeZone;
	private DeviceUser deviceUser;
	private Asset asset;

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public DeviceProtocol getNetworkProtocol() {
		return networkProtocol;
	}

	public void setNetworkProtocol(DeviceProtocol deviceProtocol) {
		this.networkProtocol = deviceProtocol;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Boolean getIsPublishing() {
		return isPublishing;
	}

	public void setIsPublishing(Boolean isPublishing) {
		this.isPublishing = isPublishing;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Short getGmtOffset() {
		return gmtOffset;
	}

	public void setGmtOffset(Short gmtOffset) {
		this.gmtOffset = gmtOffset;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getIp() {
		return ip;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
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

	public Configuration getConfigurations() {
		return configurations;
	}

	public void setConfigurations(Configuration configurations) {
		this.configurations = configurations;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public DeviceUser getDeviceUser() {
		return deviceUser;
	}

	public void setDeviceUser(DeviceUser deviceUser) {
		this.deviceUser = deviceUser;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

}