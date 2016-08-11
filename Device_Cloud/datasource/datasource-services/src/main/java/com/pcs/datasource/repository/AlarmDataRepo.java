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
package com.pcs.datasource.repository;

import java.util.List;

import org.joda.time.DateTime;

import com.pcs.datasource.dto.AlarmDataResponse;
import com.pcs.datasource.dto.AlarmMessage;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for defining all services related to alarm repo
 * 
 * @author PCSEG129(Seena Jyothish) Jul 16, 2015
 */
public interface AlarmDataRepo {

	/**
	 * Method to insert the alarm to the datastore
	 * 
	 * @param alarmMessage
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO insert(AlarmMessage alarmMessage);

	/**
	 * Method to batch insert alarms to the datastore
	 *  
	 * @param alarmMessages
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO batchInsert(List<AlarmMessage> alarmMessages);

	/**
	 * Method to get all the alarms occurred on a set of points of a device
	 *  
	 * @param sourceId
	 * @param startDatetime
	 * @param endDatetime
	 * @param customTags
	 * @return {@link AlarmDataResponse}
	 */
	public AlarmDataResponse getAlarms(String sourceId,DateTime startDatetime,
	        DateTime endDatetime, List<String> customTags);
	
	/**
	 * Method to get all the alarms occurred on all the points of a device
	 * 
	 * @param sourceId
	 * @param startDatetime
	 * @param endDatetime
	 * @return {@link AlarmDataResponse}
	 */
	public AlarmDataResponse getAlarms(String sourceId,DateTime startDatetime,
	        DateTime endDatetime);

	public AlarmDataResponse getAlarmsHistory(String sourceId,
	        DateTime startDatetime, DateTime endDatetime,
	        List<String> customTags);

	public AlarmDataResponse getLiveAlarms(String sourceId,
	        List<String> customTags);
}
