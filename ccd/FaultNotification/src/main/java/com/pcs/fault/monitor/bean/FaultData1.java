
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
package com.pcs.fault.monitor.bean;

import java.io.Serializable;

import com.pcs.fault.monitor.enums.EventSendStatus;
import com.pcs.fault.monitor.enums.RespReceiveStatus;
import com.pcs.util.faultmonitor.ccd.fault.bean.FaultData;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Feb 15, 2016
 */
public class FaultData1 extends FaultData implements Serializable{
	
    private static final long serialVersionUID = 1557850213130119989L;

	private EventSendStatus eventSendStatus;

	private RespReceiveStatus respReceiveStatus;
	
	private Status entityStatus;
	
	private String readOnlyRawIdentifier;

	
	public EventSendStatus getEventSendStatus() {
		return eventSendStatus;
	}

	public void setEventSendStatus(EventSendStatus eventSendStatus) {
		this.eventSendStatus = eventSendStatus;
	}

	public RespReceiveStatus getRespReceiveStatus() {
		return respReceiveStatus;
	}

	public void setRespReceiveStatus(RespReceiveStatus respReceiveStatus) {
		this.respReceiveStatus = respReceiveStatus;
	}

	public Status getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(Status entityStatus) {
		this.entityStatus = entityStatus;
	}

	public String getReadOnlyRawIdentifier() {
		return readOnlyRawIdentifier;
	}

	public void setReadOnlyRawIdentifier(String readOnlyRawIdentifier) {
		this.readOnlyRawIdentifier = readOnlyRawIdentifier;
	}

	@Override
    public String toString() {
	    return "FaultData1 [eventSendStatus=" + eventSendStatus
	            + ", respReceiveStatus=" + respReceiveStatus
	            + ", entityStatus=" + entityStatus + ", readOnlyRawIdentifier="
	            + readOnlyRawIdentifier + "]";
    }
	
	

}
