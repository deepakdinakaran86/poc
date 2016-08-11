package com.pcs.util.faultmonitor.ccd.fault.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Snapshot implements Serializable{
	
    private static final long serialVersionUID = 3848613341853506057L;

	@JsonProperty("Snapshot_DateTimestamp")
	private String timestamp;
	
	@JsonProperty("Parameter")
	private List<Parameter> parameters;
	
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(Parameter parameter) {
		if(parameters == null){
			parameters = new ArrayList<Parameter>();
		}
		parameters.add(parameter);
	}
	
	

}
