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
package com.pcs.alpine.services;

import java.util.List;

import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.email.dto.EmailDTO;
import com.pcs.alpine.commons.sms.dto.SMSDTO;
import com.pcs.alpine.commons.sms.dto.SMSStatsDTO;

/**
 * 
 * @description This class is responsible for the NotificationService interface
 * @author Daniela (PCSEG191)
 * @date 28 Sep 2015
 * @since galaxy-1.0.0
 */
public interface NotificationService {
	

	/**
	 * @Description Sends an email

	 * @param EmailDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO sendEmail(EmailDTO email);
	
	
	public StatusMessageDTO createUserAPIManager(String userName, String password);
	
	/**
	 * @Description Subscribe to SMS Gateway
	 * @param SMSDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO subscribe(SMSDTO smsDTO);
	
	/**
	 * @Description Sends an sms
	 * @param SMSDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO sendSMS(SMSDTO smsDTO);

	/**
	 * @Description Receive an sms
	 * @param SMSDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO receiveSMS(SMSDTO smsDTO);
	
	/**
	 * @Description Receive an sms
	 * @param SMSDTO
	 * @return SMSStatsDTO
	 */
	public List<SMSStatsDTO> getSMStatistics();
	

	
}
