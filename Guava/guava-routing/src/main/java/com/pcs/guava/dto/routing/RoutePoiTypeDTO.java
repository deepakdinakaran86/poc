package com.pcs.guava.dto.routing;

public class RoutePoiTypeDTO {
	
	private String address;
	private Integer index;
	private String stopageTime;
	private String duration;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getStopageTime() {
		return stopageTime;
	}

	public void setStopageTime(String stopageTime) {
		this.stopageTime = stopageTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
