package com.pcs.device.gateway.G2021.devicemanager.datasource.publish.bean;

public class EntityField {

	private String key;
	private String value;

	
	public EntityField(){
		
	}
	
	public EntityField(String key,String value){
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
