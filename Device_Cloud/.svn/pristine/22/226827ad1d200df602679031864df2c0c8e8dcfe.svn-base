package com.pcs.saffron.g2021.SimulatorEngine.DS.enums;

public enum PAlarmType {
	NO_ALARM(0,"No Alaram"),
	STATE_CHANGE(1,"State Change"),
	OUT_OF_RANGE(2,"Out of range");
	
	private int value;
	private String type;
	
	private PAlarmType(int value,String type){
		this.value = value;
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public static PAlarmType valueOfType(Integer type) {
		if (type == null) {
			return null;
		}
		for (PAlarmType packetType : values()) {
			if (type.equals(packetType.getValue())) {
				return packetType;
			}
		}

		return null;
	}
}
