
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
package com.pcs.ccd.services;

import java.util.List;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.ccd.bean.FaultData;
import com.pcs.ccd.bean.FaultEventData;
import com.pcs.ccd.bean.FaultEventInfoReq;

/**
 * This class is responsible for defining all services related to fault event
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 31, 2016
 */
public interface FaultEventService {
	
	public FaultData getLatestFaultEventIfExists(FaultEventInfoReq eventInfoReq);
	
	public FaultData getFaultEvent(FaultEventInfoReq eventInfoReq);
	
	public StatusMessageDTO persistFaultEvent(FaultData faultData);
	
	public StatusMessageDTO updateFaultEvent(FaultData faultData);
	
	public List<FaultEventData> findAllFaultEvent(FaultEventInfoReq faultInfoReq);
	
	/**
	 * Method to check a fault event is already existing for a tenant
	 * @param faultEventIdentifier
	 * @return
	 */
	public boolean isFaultEventExists(String faultEventIdentifier);
	
	/**
	 * Method to get all fault events of a tenant
	 * @param tenant
	 * @return
	 */
	public List<FaultEventData> getAllFaultEvent(String tenantDomainName);
	
}
