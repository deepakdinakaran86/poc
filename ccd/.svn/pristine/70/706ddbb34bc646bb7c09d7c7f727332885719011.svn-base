package com.pcs.util.faultmonitor.ccd.fault.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Parameter implements Serializable{

    private static final long serialVersionUID = -3083818426241433618L;
    
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
