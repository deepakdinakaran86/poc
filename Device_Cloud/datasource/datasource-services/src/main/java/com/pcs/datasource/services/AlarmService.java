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
package com.pcs.datasource.services;

import java.util.List;

import com.pcs.datasource.dto.AlarmDataResponse;
import com.pcs.datasource.dto.AlarmMessage;
import com.pcs.datasource.dto.SearchDTO;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * This class is responsible for defining all services related to alarm
 * 
 * @author PCSEG129(Seena Jyothish)
 * Jul 16, 2015
 */
public interface AlarmService {
	
	/**
	 * Method to insert alarm
	 * 
	 * @param alarmMessage
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO saveAlarm(AlarmMessage alarmMessage);
	
	/**
	 * Method to batch insert alarms
	 * 
	 * @param alarmMessages
	 * @return {@link StatusMessageDTO}
	 */
	public StatusMessageDTO batchSaveAlarm(List<AlarmMessage> alarmMessages);
	
	/**
	 * Method to get alarms of a device
	 * 
	 * @param searchDTO
	 * @return {@link AlarmDataResponse}
	 */
	public AlarmDataResponse getAlarms(SearchDTO searchDTO);
	
	/**
	 * Method to get all alarms of a device
	 * 
	 * @param searchDTO
	 * @return {@link AlarmDataResponse}
	 */
	public AlarmDataResponse getAllAlarms(SearchDTO searchDTO);
	
	public List<AlarmDataResponse> getAlarmsHistory(List<SearchDTO> searchDTOList);
	public List<AlarmDataResponse> getLatestAlarms(List<SearchDTO> searchDTOList);

}
