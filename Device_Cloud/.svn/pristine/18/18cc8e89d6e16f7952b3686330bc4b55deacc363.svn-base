package com.pcs.device.gateway.G2021.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.devicemanager.bean.DeviceConfiguration;

public class Configuration extends DeviceConfiguration {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -482870097498805740L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
	
	private String sourceIdentifier;
	private Integer unitId;
	private Integer sessionId;
	private String ccKey;
	private Boolean registered;
	
	
	
	public Configuration(String sourceIdentifier,Integer unitId,Integer sessionId,String ccKey){
		this.sourceIdentifier = sourceIdentifier;
		this.unitId = unitId;
		this.sessionId = sessionId;
		this.ccKey = ccKey;
		LOGGER.info("Configuration Message Found");
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
	
	public Point getPoint(String pointId){
		return getPointMapping().get(pointId);
	}

}
