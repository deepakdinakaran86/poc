package com.pcs.device.gateway.G2021.message.extension.alarm.state;

public enum AlarmState {
	
	NOT_APPLICABLE("0000"),
	UPPER_THRESHOLD_ALARM("0001",true),
	LOWER_THRESHOLD_ALARM("0010",true),
	NORMALIZED("0011",false),
	STATE_CHANGE_ALARM("0100",true);
	
	private String state;
	private Boolean status;
	
	
	AlarmState(String state){
		this.state = state;
	}
	
	private AlarmState(String state,Boolean status) {
		this.state = state;
		this.status = status;
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
	


	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}
}
