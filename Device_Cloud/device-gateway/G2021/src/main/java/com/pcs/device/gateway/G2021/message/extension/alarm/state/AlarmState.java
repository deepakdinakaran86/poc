package com.pcs.device.gateway.G2021.message.extension.alarm.state;

public enum AlarmState {
	
	NOT_APPLICABLE("0000"),
	UPPER_THRESHOLD_ALARM("0001"),
	LOWER_THRESHOLD_ALARM("0010"),
	NORMALIZED("0011"),
	STATE_CHANGE_ALARM("0100");
	
	private String state;
	
	AlarmState(String state){
		this.state = state;
	}

	
	private static final AlarmState[] values = values();


	public static AlarmState valueOfType(String state) {
		if (state == null) {
			return null;
		}
		for (AlarmState alarmState : values) {
			if (state.equals(alarmState.getState())) {
				return alarmState;
			}
		}

		return null;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}
}
