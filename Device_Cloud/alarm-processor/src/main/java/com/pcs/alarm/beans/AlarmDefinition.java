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
import java.util.List;

/**
 * This class is responsible for defining the alarm definition
 * 
 * @author pcseg129(Seena Jyothish)
 * Jul 26, 2016
 */
public class AlarmDefinition implements Serializable{
	private static final long serialVersionUID = 2238652172997265091L;
	
	private String name;
	private String desc;
	private String sourceId;
	private List<AlarmDefinitionPoint> alarmDefinitionPoints;
	private String expression;
	private String alarmMessage;
	private String normalMessage;
	private boolean isPersistent;
	private boolean isDelta;
	private Long timeDuration;
	private Long hopTime;
	private boolean active;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public List<AlarmDefinitionPoint> getAlarmDefinitionPoints() {
		return alarmDefinitionPoints;
	}
	public void setAlarmDefinitionPoints(
			List<AlarmDefinitionPoint> alarmDefinitionPoints) {
		this.alarmDefinitionPoints = alarmDefinitionPoints;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getAlarmMessage() {
		return alarmMessage;
	}
	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}
	public String getNormalMessage() {
		return normalMessage;
	}
	public void setNormalMessage(String normalMessage) {
		this.normalMessage = normalMessage;
	}
	public boolean getIsPersistent() {
		return isPersistent;
	}
	public void setIsPersistent(boolean isPersistent) {
		this.isPersistent = isPersistent;
	}
	public boolean getIsDelta() {
		return isDelta;
	}
	public void setIsDelta(boolean isDelta) {
		this.isDelta = isDelta;
	}
	public Long getTimeDuration() {
		return timeDuration;
	}
	public void setTimeDuration(Long timeDuration) {
		this.timeDuration = timeDuration;
	}
	public Long getHopTime() {
		return hopTime;
	}
	public void setHopTime(Long hopTime) {
		this.hopTime = hopTime;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

}
