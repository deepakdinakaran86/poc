package com.pcs.ccd.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcs.avocado.enums.Status;
import com.pcs.ccd.enums.EventSendStatus;
import com.pcs.ccd.enums.RespReceiveStatus;

public class FaultData implements Serializable {

	private static final long serialVersionUID = -8569137913665051815L;

	@JsonProperty("SPN")
	private String SPN;

	@JsonProperty("FMI")
	private String FMI;

	@JsonProperty("Occurrence_Count")
	private String OC;
	
	private String assetName;

	private FaultDataWrapper faultDataInfo;

	private int ocCycle;

	private EventSendStatus eventSendStatus;

	private RespReceiveStatus respReceiveStatus;
	
	private Status entityStatus;
	
	private String readOnlyRawIdentifier;
	
	
	public String getSPN() {
		return SPN;
	}

	public void setSPN(String sPN) {
		SPN = sPN;
	}

	public String getFMI() {
		return FMI;
	}

	public void setFMI(String fMI) {
		FMI = fMI;
	}

	public String getOC() {
		return OC;
	}

	public void setOC(String oC) {
		OC = oC;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public FaultDataWrapper getFaultDataInfo() {
		return faultDataInfo;
	}

	public void setFaultDataInfo(FaultDataWrapper faultDataInfo) {
		this.faultDataInfo = faultDataInfo;
	}

	public int getOcCycle() {
		return ocCycle;
	}

	public void setOcCycle(int ocCycle) {
		this.ocCycle = ocCycle;
	}

	public EventSendStatus getEventSendStatus() {
		return eventSendStatus;
	}

	public void setEventSendStatus(EventSendStatus eventSendStatus) {
		this.eventSendStatus = eventSendStatus;
	}

	public RespReceiveStatus getRespReceiveStatus() {
		return respReceiveStatus;
	}

	public void setRespReceiveStatus(RespReceiveStatus respReceiveStatus) {
		this.respReceiveStatus = respReceiveStatus;
	}

	public Status getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(Status entityStatus) {
		this.entityStatus = entityStatus;
	}

	public String getReadOnlyRawIdentifier() {
		return readOnlyRawIdentifier;
	}

	public void setReadOnlyRawIdentifier(String readOnlyRawIdentifier) {
		this.readOnlyRawIdentifier = readOnlyRawIdentifier;
	}
	

}
