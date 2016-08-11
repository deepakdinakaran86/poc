package com.pcs.device.gateway.G2021.devicemanager.device.bean;

public class WritebackResponse {

	private String sourceId;
	
	private Short requestId;
	
	private String status;
	
	private Long time;
	
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	private String remarks;

	public Short getRequestId() {
		return requestId;
	}

	public void setRequestId(Short requestId) {
		this.requestId = requestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
