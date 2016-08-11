package com.pcs.ccd.heartbeat.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Parameter {

	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("Value")
	private String value;
	
	public Parameter(){
		
	}
	
	public Parameter(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
