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
package com.pcs.datasource.enums;

import com.pcs.devicecloud.commons.validation.DataFields;

/**
 * This class is responsible for defining alarm data fields
 * 
 * @author PCSEG129(Seena Jyothish) Jul 16, 2015
 */
public enum AlarmDataFields implements DataFields{
	
	ALARM_MESSAGE("alarmmessage", "alarmMessage", "Alarm Message"),
	ALARM_STATUS("alarmstate", "status", "Alarm Status"),
	ALARM_TYPE("alarmtype", "alarmType", "Alarm Type"),
	ALARM_NAME("alarmname", "alarmname", "Alarm Name"),
	POINT_NAME("pointname", "customTag", "Custom Tag"),
	DISPLAY_NAME("displayname", "displayname", "Display Name"),
	ALARM_DATA("data","data","Alarm Data"),
	ALARM_TIME("alarmtime","time","Alarm Time"),
	UNIT("unit","unit","Alarm point unit"),
	ALARM_POINTS("alarmpoints", "points", "Alarm Points"),
	ALARM_POINT_ID("pointid", "pointid", "Point Id"),
	ALARM_POINT_NAME("pointname", "pointname", "Point Name");
	
	private String fieldName;
	private String variableName;
	private String description;

	private AlarmDataFields(String fieldName, String variableName,
	        String description) {
		this.fieldName = fieldName;
		this.variableName = variableName;
		this.description = description;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getVariableName() {
		return variableName;
	}

	public String getDescription() {
		return description;
	}

}
