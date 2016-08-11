package com.pcs.util.faultmonitor.ccd.fault.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FaultSnapshot {
	
	@JsonProperty("Snapshot_DateTimestamp")
	private String timestamp;
	
	@JsonProperty("Parameter")
	private List<FaultParameter> parameters;
	
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public List<FaultParameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(List<FaultParameter> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(FaultParameter parameter) {
		if(parameters == null){
			parameters = new ArrayList<FaultParameter>();
		}
		parameters.add(parameter);
	}
	
	

}
