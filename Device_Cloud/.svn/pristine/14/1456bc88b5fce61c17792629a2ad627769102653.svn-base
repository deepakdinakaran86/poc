/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.devicemanager.bean.DeviceConfiguration;

/**
 * @author pcseg310
 *
 */
@JsonAutoDetect
@JsonInclude(value=Include.NON_NULL)
public class G2021DeviceConfiguration extends DeviceConfiguration {


	private static final long serialVersionUID = -482870097498805740L;
	private String sourceIdentifier;
	private Integer unitId;
	private Integer sessionId;
	private String ccKey;
	private Boolean registered;
	private Device device;
	private List<Point> pointConfigurations;
	private List<Point> updatedPointConfigurations;
	private String clientIp;
	private Integer clientDataserverPort;
	private Integer clientControlserverPort;
	private String communicationMode;
	private Boolean configured;
	private Boolean appChanged = false;

	public G2021DeviceConfiguration(String sourceIdentifier,Integer unitId,Integer sessionId,String ccKey){
		this.sourceIdentifier = sourceIdentifier;
		this.unitId = unitId;
		this.sessionId = sessionId;
		this.ccKey = ccKey;
	}

	public G2021DeviceConfiguration() {
	}

	public String getSourceIdentifier() {
		return sourceIdentifier;
	}
	public void setSourceIdentifier(String sourceIdentifier) {
		this.sourceIdentifier = sourceIdentifier;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public String getCcKey() {
		return ccKey;
	}

	public void setCcKey(String ccKey) {
		this.ccKey = ccKey;
	}

	public Boolean checkValidity(Integer unitId,Integer sessionId,String ccKey){
		return (unitId >0 && sessionId >0)&&(this.unitId==unitId && this.sessionId == sessionId && this.ccKey.equalsIgnoreCase(ccKey));
	}

	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}

	public Boolean isRegistered(){
		return this.registered;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public List<Point> getPointConfigurations() {
		pointConfigurations = pointConfigurations == null ? new ArrayList<Point>() :  pointConfigurations;
		return pointConfigurations;
	}

	public void setPointConfigurations(List<Point> pointConfigurations) {
		this.pointConfigurations = pointConfigurations;
	}

	public List<Point> getUpdatedPointConfigurations() {
		return updatedPointConfigurations;
	}

	public void setUpdatedPointConfigurations(List<Point> updatedPointConfigurations) {
		this.updatedPointConfigurations = updatedPointConfigurations;
	}
	
	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	
	public Integer getClientDataserverPort() {
		return clientDataserverPort;
	}

	public void setClientDataserverPort(Integer clientDataserverPort) {
		this.clientDataserverPort = clientDataserverPort;
	}

	public Integer getClientControlserverPort() {
		return clientControlserverPort;
	}

	public void setClientControlserverPort(Integer clientControlserverPort) {
		this.clientControlserverPort = clientControlserverPort;
	}
	
	public String getCommunicationMode() {
		return communicationMode;
	}

	public void setCommunicationMode(String communicationMode) {
		this.communicationMode = communicationMode;
	}

	public Boolean isConfigured() {
		return configured;
	}

	public void setConfigured(Boolean configured) {
		this.configured = configured;
	}
	
	public void addPointConfiguration(Point pointConfiguration) {
		if (pointConfiguration != null) {
			if(this.pointConfigurations == null){
				this.pointConfigurations = new ArrayList<Point>();
			}
			this.pointConfigurations.add(pointConfiguration);
		}
	}

	public void addUpdatedPointConfiguration(Point pointConfiguration) {
		if (pointConfiguration != null) {
			if(updatedPointConfigurations == null){
				updatedPointConfigurations = new ArrayList<Point>();
			}
			updatedPointConfigurations.add(pointConfiguration);
		}
	}

	public Boolean appChanged() {
		return appChanged;
	}

	public void setAppChanged(Boolean appChanged) {
		this.appChanged = appChanged;
	}
}
