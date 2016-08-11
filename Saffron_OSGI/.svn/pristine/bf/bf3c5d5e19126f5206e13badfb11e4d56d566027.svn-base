package com.pcs.device.gateway.G2021.message.point.type;


public enum WriteStatus {

	
	ACCEPTED(1,"SUCCESS"),
	ERROR(2,"FAILURE"),
	INVALID_PID(3,"FAILURE"),
	UNAUTHORIZED_WRITE_REJECT(4,"FAILURE");
	 
	private Integer status;
	private String statusIndicator;
	
	WriteStatus(Integer status, String indicator){
		this.status = status;
		this.statusIndicator = indicator;
	}

	
	private static final WriteStatus[] values = values();


	public static WriteStatus valueOfType(Integer state) {
		if (state == null) {
			return null;
		}
		for (WriteStatus writeStatus : values) {
			if (state.equals(writeStatus.getStatus())) {
				return writeStatus;
			}
		}

		return null;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getStatusIndicator() {
		return statusIndicator;
	}


	public void setStatusIndicator(String statusIndicator) {
		this.statusIndicator = statusIndicator;
	}


}
