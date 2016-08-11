/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 *
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.alarm.beans;

import java.io.Serializable;

import com.pcs.alarm.enums.AlarmStatus;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Jul 28, 2016
 */
public class AlarmBean implements Serializable {

	private static final long serialVersionUID = -5621755755909812357L;
	
	private String sourceId;
	private String alarmDefinitionName;
	private AlarmStatus alarmStatus = AlarmStatus.NOALARM;
	private long alarmGeneratedTime;
	private String alarmData;
	private String alarmMessage;
	private Short criticality;
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getAlarmDefinitionName() {
		return alarmDefinitionName;
	}
	public void setAlarmDefinitionName(String alarmDefinitionName) {
		this.alarmDefinitionName = alarmDefinitionName;
	}
	public AlarmStatus getAlarmStatus() {
		return alarmStatus;
	}
	public void setAlarmStatus(AlarmStatus alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
	public long getAlarmGeneratedTime() {
		return alarmGeneratedTime;
	}
	public void setAlarmGeneratedTime(long alarmGeneratedTime) {
		this.alarmGeneratedTime = alarmGeneratedTime;
	}
	public String getAlarmData() {
		return alarmData;
	}
	public void setAlarmData(String alarmData) {
		this.alarmData = alarmData;
	}
	public String getAlarmMessage() {
		return alarmMessage;
	}
	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}
	public Short getCriticality() {
		return criticality;
	}
	public void setCriticality(Short criticality) {
		this.criticality = criticality;
	}
	
	@Override
	public int hashCode(){
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		AlarmBean alertBean = (AlarmBean) obj;
		if(alertBean.getAlarmDefinitionName()==null || alertBean.getSourceId()==null) {
			return false;
		}
		if(alertBean.getAlarmDefinitionName().toString().equals(alarmDefinitionName) 
				&& alertBean.getSourceId().toString().equals(sourceId)) {
			return true;
		}

		return false;
	}
	

}
