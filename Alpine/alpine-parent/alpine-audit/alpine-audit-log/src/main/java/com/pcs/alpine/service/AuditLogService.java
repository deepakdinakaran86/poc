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
package com.pcs.alpine.service;

import java.util.List;

import com.pcs.alpine.dto.ApplicationAuditDTO;
import com.pcs.alpine.dto.AuditDTO;

/**
 * 
 * This is an interface to Audit Log Service
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date July 2016
 * @since alpine-1.0.0
 */
public interface AuditLogService {

	public List<AuditDTO> getAuditLog();
	
	public List<ApplicationAuditDTO> getAuditLogForApp();

}
