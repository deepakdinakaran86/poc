package com.pcs.saffron.manager.authentication.bean;

import java.io.Serializable;

public class GeneralResponse implements Serializable {

	private static final long serialVersionUID = 1770126271704986280L;
	private String reference;
	private DeviceStatus status;
	private String remarks;
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public DeviceStatus getStatus() {
		return status;
	}
	public void setStatus(DeviceStatus status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
