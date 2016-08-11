package com.pcs.device.gateway.jace.writeback.api.beans;

public class PointCommand {

	private String pointId;
	private Object value;
	
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
