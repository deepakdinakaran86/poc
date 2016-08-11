/**
 * 
 */
package com.pcs.saffron.manager.bean;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pcseg310
 *
 */
public class DefaultConfiguration extends DeviceConfiguration {


	private static final long serialVersionUID = -482870097498805740L;
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultConfiguration.class);
	private String sourceIdentifier;
	private Integer unitId;
	private Integer sessionId;
	private String ccKey;
	private Boolean registered;
	private Device device;
	private List<ConfigPoint> updatedPointConfigurations;
	private String communicationMode;
	private Boolean configured;
	private Boolean configurationUpdateStatus;
	
	
	public DefaultConfiguration(String sourceIdentifier,Integer unitId,Integer sessionId,String ccKey){
		this.sourceIdentifier = sourceIdentifier;
		this.unitId = unitId;
		this.sessionId = sessionId;
		this.ccKey = ccKey;
	}

	public DefaultConfiguration() {
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
		boolean validity = (unitId >0 && sessionId >0)&&(this.unitId==unitId && this.sessionId == sessionId && this.ccKey.equalsIgnoreCase(ccKey));
		if(!validity){
			LOGGER.info("Recieved connection seems to be a new session request !!");
		}
		return validity;
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


	public List<ConfigPoint> getUpdatedPointConfigurations() {
		return updatedPointConfigurations;
	}

	public void setUpdatedPointConfigurations(List<ConfigPoint> updatedPointConfigurations) {
		this.updatedPointConfigurations = updatedPointConfigurations;
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
	
	public void addPointConfiguration(ConfigPoint pointConfiguration) {
		if (configPoints != null) {
			if(this.configPoints == null){
				this.configPoints = new ArrayList<ConfigPoint>();
			}
			this.configPoints.add(pointConfiguration);
		}
	}

	public void addUpdatedPointConfiguration(ConfigPoint pointConfiguration) {
		if (pointConfiguration != null) {
			if(updatedPointConfigurations == null){
				updatedPointConfigurations = new ArrayList<ConfigPoint>();
			}
			updatedPointConfigurations.add(pointConfiguration);
		}
	}

	public Boolean getConfigurationUpdateStatus() {
		return configurationUpdateStatus;
	}

	public void setConfigurationUpdateStatus(Boolean configurationUpdateStatus) {
		this.configurationUpdateStatus = configurationUpdateStatus;
	}

	
}
