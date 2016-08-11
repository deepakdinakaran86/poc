package com.pcs.data.analyzer.storm.model;

import java.io.Serializable;


public class CEPAlarmMessage implements Serializable {

	private CEPAlarmEventMessage event;

	public CEPAlarmMessage() {
		super();
	}

	public CEPAlarmMessage(CEPAlarmEventMessage event) {
		super();
		this.event = event;
	}

	public CEPAlarmEventMessage getEvent() {
		return event;
	}

	public void setEvent(CEPAlarmEventMessage event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "CEPAlarmMessage [event=" + event + ", getEvent()=" + getEvent()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
