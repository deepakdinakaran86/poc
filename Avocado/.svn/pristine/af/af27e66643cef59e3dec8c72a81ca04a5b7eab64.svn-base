
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.avocado.commons.dto;

import java.io.Serializable;
import java.util.List;

/**
 * This class is responsible for bundling alarm data
 * 
 * @author PCSEG129(Seena Jyothish)
 * Jul 16, 2015
 */
public class AlarmDataResponse implements Serializable{
	
    private static final long serialVersionUID = -3278205341199020310L;
	private String sourceId;
	private List<AlarmPointData> alarms;
	private List<AlarmMessage> alarmMessages;
	
	public String getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	public List<AlarmPointData> getAlarms() {
		return alarms;
	}
	
	public void setAlarms(List<AlarmPointData> alarmPointData) {
		this.alarms = alarmPointData;
	}
	
	public void addAlarm(AlarmPointData alarmPointData){
		alarms.add(alarmPointData);
	}

	public List<AlarmMessage> getAlarmMessages() {
		return alarmMessages;
	}

	public void setAlarmMessages(List<AlarmMessage> alarmMessages) {
		this.alarmMessages = alarmMessages;
	}
	

}
