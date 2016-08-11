package com.pcs.guava.dto.scheduling;

public class SchedulePoiDTO {
	
	private String arrivalTime;
	
	private String timeTolerance;
	
	private String departureTime;
	
	private String poiName;

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getTimeTolerance() {
		return timeTolerance;
	}

	public void setTimeTolerance(String timeTolerance) {
		this.timeTolerance = timeTolerance;
	}

	public String getPoiName() {
		return poiName;
	}

	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	
	

}
