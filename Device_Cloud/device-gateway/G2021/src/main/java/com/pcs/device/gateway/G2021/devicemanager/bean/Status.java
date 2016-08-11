package com.pcs.device.gateway.G2021.devicemanager.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Status implements Serializable {

    private static final long serialVersionUID = 5088853574137174354L;
	private Integer statusKey;
	private StatusType statusName;
	
	public Status(){
		
	}
	
	public Status(Integer statusKey, StatusType statusName){
		this.statusKey = statusKey;
		this.statusName = statusName;
	}
	
	public Integer getStatusKey() {
		return statusKey;
	}
	public void setStatusKey(Integer statusKey) {
		this.statusKey = statusKey;
	}
	public StatusType getStatusName() {
		return statusName;
	}
	public void setStatusName(StatusType statusName) {
		this.statusName = statusName;
	}
}
