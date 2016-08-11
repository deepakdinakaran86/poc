package com.pcs.datasource.dto;

import java.io.Serializable;

import com.pcs.devicecloud.enums.Status;

/**
 * @author pcseg199
 *
 */
public class GeneralResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6466486140213650452L;
	private String reference;
	private Status status;
	private String remarks;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}