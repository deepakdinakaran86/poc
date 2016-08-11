package com.pcs.util.faultmonitor.ccd.fault.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"notificationVersion","messageType","Telematics_Box_ID",
	"serialNumber","vidNumber","occuranceDateTime","sentDateTime","active",
	"datalinkBus","sourceAddress","SPN","FMI","OC","latitude","longitude","altitude",
	"direction","speed","snapshots"})
public class FaultData {

	//private String LS;
	
	@JsonProperty("SPN")
	private String SPN;
	
	@JsonProperty("FMI")
	private String FMI;
	
	@JsonProperty("Occurrence_Count")
	private String OC;

	private String assetName;
	
	//@JsonUnwrapped
	private FaultDataWrapper faultDataInfo;
	
	private int ocCycle;
	
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
	
	
	
}
