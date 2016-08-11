package com.pcs.saffron.g2021.SimulatorEngine.DS.DTO;

import com.pcs.saffron.g2021.SimulatorEngine.CS.message.Points;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PAlarmState;

public class DataPoints {
	
	private Object pointCurrentValue;
	private Long nextFireTime;
	private Points points;
	private Object generatedValue;	
	private boolean isAllowForProcessing;
	private boolean hasAlarm;
	private PAlarmState currentAlarmState;
	private int retrials;
	
	public Object getPointCurrentValue() {
		return pointCurrentValue;
	}
	public void setPointCurrentValue(Object pointCurrentValue) {
		this.pointCurrentValue = pointCurrentValue;
	}
	public Long getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(Long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public Points getPoints() {
		return points;
	}
	public void setPoints(Points points) {
		this.points = points;
	}
	public Object getGeneratedValue() {
		return generatedValue;
	}
	public void setGeneratedValue(Object generatedValue) {
		this.generatedValue = generatedValue;
	}
	public boolean isAllowForProcessing() {
		return isAllowForProcessing;
	}
	public void setAllowForProcessing(boolean isAllowForProcessing) {
		this.isAllowForProcessing = isAllowForProcessing;
	}
	
	public boolean isHasAlarm() {
		return hasAlarm;
	}
	public void setHasAlarm(boolean hasAlarm) {
		this.hasAlarm = hasAlarm;
	}
	public PAlarmState getCurrentAlarmState() {
		return currentAlarmState;
	}
	public void setCurrentAlarmState(PAlarmState currentAlarmState) {
		this.currentAlarmState = currentAlarmState;
	}
	public int getRetrials() {
		return retrials;
	}
	public void setRetrials(int retrials) {
		this.retrials = retrials;
	}	
	
	
	
}
