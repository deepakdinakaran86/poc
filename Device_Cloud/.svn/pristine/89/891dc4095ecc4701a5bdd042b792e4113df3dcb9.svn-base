
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
package com.pcs.alarm.constants;

import java.util.HashMap;

import com.pcs.alarm.enums.AlarmStatus;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Aug 1, 2016
 */
public class AlarmTransitions {
	public static final HashMap<String, AlarmStatus> ALARM_STATUS_TRANSITIONS = new HashMap<String, AlarmStatus>();
	
	private static String ALARM_STATUS_TRANSITIONS_NO_ALARM_TO_ACTIVE = "NOALARM->ACTIVE";
	private static String ALARM_STATUS_TRANSITIONS_ACTIVE_TO_ACTIVE = "ACTIVE->ACTIVE";
	private static String ALARM_STATUS_TRANSITIONS_RECURING_TO_ACTIVE = "RECURING->ACTIVE";
	private static String ALARM_STATUS_TRANSITIONS_RECURING_TO_NOALARM = "RECURING->NOALARM";
	private static String ALARM_STATUS_TRANSITIONS_ACTIVE_TO_NOALARM = "ACTIVE->NOALARM";
	private static String ALARM_STATUS_TRANSITIONS_RESOLVED_TO_ACTIVE = "RESOLVED->ACTIVE";
	private static String ALARM_STATUS_TRANSITIONS_RESOLVED_TO_NOALARM = "RESOLVED->NOALARM";
	private static String ALARM_STATUS_TRANSITIONS_NO_ALARM_TO_NO_ALARM = "NOALARM->NOALARM";
	
	static{
		ALARM_STATUS_TRANSITIONS.put(ALARM_STATUS_TRANSITIONS_NO_ALARM_TO_ACTIVE, AlarmStatus.ACTIVE);
		ALARM_STATUS_TRANSITIONS.put(ALARM_STATUS_TRANSITIONS_RECURING_TO_ACTIVE, AlarmStatus.RECURING);
		ALARM_STATUS_TRANSITIONS.put(ALARM_STATUS_TRANSITIONS_ACTIVE_TO_ACTIVE, AlarmStatus.RECURING);
		ALARM_STATUS_TRANSITIONS.put(ALARM_STATUS_TRANSITIONS_RECURING_TO_NOALARM, AlarmStatus.RESOLVED);
		ALARM_STATUS_TRANSITIONS.put(ALARM_STATUS_TRANSITIONS_ACTIVE_TO_NOALARM, AlarmStatus.RESOLVED);
		ALARM_STATUS_TRANSITIONS.put(ALARM_STATUS_TRANSITIONS_RESOLVED_TO_ACTIVE, AlarmStatus.ACTIVE);
		ALARM_STATUS_TRANSITIONS.put(ALARM_STATUS_TRANSITIONS_RESOLVED_TO_NOALARM, AlarmStatus.NOALARM);
		ALARM_STATUS_TRANSITIONS.put(ALARM_STATUS_TRANSITIONS_NO_ALARM_TO_NO_ALARM, AlarmStatus.NOALARM);
	}

}
