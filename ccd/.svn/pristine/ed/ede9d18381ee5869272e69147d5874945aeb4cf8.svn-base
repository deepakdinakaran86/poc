package com.pcs.util.faultmonitor.ccd.fault.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;



public class FaultData implements Serializable{

    private static final long serialVersionUID = 8427380163704132786L;

	//private String LS;
	
	@JsonProperty("SPN")
	private String SPN;
	
	@JsonProperty("FMI")
	private String FMI;
	
	@JsonProperty("Occurrence_Count")
	private String OC;

	private String assetName;
	
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

	public int getOcCycle() {
		return ocCycle;
	}

	public void setOcCycle(int ocCycle) {
		this.ocCycle = ocCycle;
	}
	
	
	
}
