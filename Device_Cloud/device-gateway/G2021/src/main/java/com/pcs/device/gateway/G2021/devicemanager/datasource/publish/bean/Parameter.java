package com.pcs.device.gateway.G2021.devicemanager.datasource.publish.bean;

public class Parameter {

	private String name;
	private String dataType;
	private Object value;
	private Long time;
	public Parameter(){
		
	}
	
	public Parameter(String name,String dataType){
		this.name = name;
		this.dataType = dataType;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
}
