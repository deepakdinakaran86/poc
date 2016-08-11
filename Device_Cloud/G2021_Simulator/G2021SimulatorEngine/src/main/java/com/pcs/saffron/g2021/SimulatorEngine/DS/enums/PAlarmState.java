package com.pcs.saffron.g2021.SimulatorEngine.DS.enums;

public enum PAlarmState {
	NA("0000"),
	ALARM_UT("0001"),
	ALARM_LT("0010"),
	NORMALIZED("0011"),
	STATECHANGE("0100");
	
	private String value;

	private PAlarmState(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

	public static PAlarmState getDataType(String value) {
		if (value == null) {
			return null;
		}
		for (PAlarmState packetType : values()) {
			if (value.equals(packetType.getValue())) {
				return packetType;
			}
		}

		return null;
	}
	
}
