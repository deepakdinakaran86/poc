/**
 * 
 */
package com.pcs.datasource.enums;

/**
 * @author pcseg129(Seena Jyothish)
 * Mar 02, 2016
 *
 */
public enum AlarmStatus {
	
	TRUE("True", 1), FALSE("False", 2);
	
	private String alarmStatus;
	private Integer alarmStatusId;
	
	
	public String getAlarmStatus() {
		return alarmStatus;
	}

	public Integer getAlarmStatusId() {
		return alarmStatusId;
	}

	private AlarmStatus(String alarmStatus,Integer alarmStatusId){
		this.alarmStatus = alarmStatus;
		this.alarmStatusId = alarmStatusId;
	}

}
