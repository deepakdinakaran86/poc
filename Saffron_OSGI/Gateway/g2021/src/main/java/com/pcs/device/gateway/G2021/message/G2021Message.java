package com.pcs.device.gateway.G2021.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.message.Message;

public class G2021Message extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8990822571343212183L;
	private static final Logger LOGGER = LoggerFactory.getLogger(G2021Message.class);
	
	private Integer unitId;
	private Integer sessionId;
	private Integer serverTimeOut;
	private Integer deviceTimeOut;
	private String subscriptionKey;
	private String deviceIP;
	private Short assetId;
	private int versionId;
	private Float latitude;
	private Float longitude;
	private String timezone;
	private Short gmtOffset;
	private String deviceName;
	private Boolean authenticationStatus = true;
	
	
	/**
	 * @return the unitId
	 */
	public Integer getUnitId() {
		return unitId;
	}
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	/**
	 * @return the sessionId
	 */
	public Integer getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	/**
	 * @return the serverTimeOut
	 */
	public Integer getServerTimeOut() {
		return serverTimeOut;
	}
	/**
	 * @param serverTimeOut the serverTimeOut to set
	 */
	public void setServerTimeOut(Integer serverTimeOut) {
		this.serverTimeOut = serverTimeOut;
	}
	/**
	 * @return the deviceTimeOut
	 */
	public Integer getDeviceTimeOut() {
		return deviceTimeOut;
	}
	/**
	 * @param deviceTimeOut the deviceTimeOut to set
	 */
	public void setDeviceTimeOut(Integer deviceTimeOut) {
		this.deviceTimeOut = deviceTimeOut;
	}
	/**
	 * @return the subscriptionKey
	 */
	public String getSubscriptionKey() {
		return subscriptionKey;
	}
	/**
	 * @param subscriptionKey the subscriptionKey to set
	 */
	public void setSubscriptionKey(String subscriptionKey) {
		this.subscriptionKey = subscriptionKey;
	}
	/**
	 * @return the assetId
	 */
	public Short getAssetId() {
		return assetId;
	}
	/**
	 * @param assetId the assetId to set
	 */
	public void setAssetId(Short assetId) {
		this.assetId = assetId;
	}
	/**
	 * @return the versionId
	 */
	public int getVersionId() {
		return versionId;
	}
	/**
	 * @param versionId the versionId to set
	 */
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
	/**
	 * @return the latitude
	 */
	public Float getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public Float getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the authenticationStatus
	 */
	public Boolean getAuthenticationStatus() {
		return authenticationStatus;
	}
	/**
	 * @param authenticationStatus the authenticationStatus to set
	 */
	public void setAuthenticationStatus(Boolean authenticationStatus) {
		LOGGER.info("Authentication Response : "+authenticationStatus);
		this.authenticationStatus = authenticationStatus;
	}
	public String getDeviceIP() {
		return deviceIP;
	}
	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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
	

}
