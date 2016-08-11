
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
package com.pcs.ccd.bean;

import java.io.Serializable;

import com.pcs.ccd.enums.EventSendStatus;
import com.pcs.ccd.enums.EventStatus;
import com.pcs.ccd.enums.RespReceiveStatus;

/**
 * This class is responsible for holding fault event related data
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 31, 2016
 */
public class FaultEventData implements Serializable{
	
    private static final long serialVersionUID = 2282452924815805849L;
	private String sourceId;
	private String assetName;
	private String FMI;
	private String SPN;
	private short OC;
	private EventStatus eventStatus;
	private long eventTimestamp;
	private String entityStatus;
	private EventSendStatus eventSendStatus;
	private RespReceiveStatus respReceiveStatus;
	private int ocCylcle;
	private String identifier;
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getFMI() {
		return FMI;
	}
	public void setFMI(String fMI) {
		FMI = fMI;
	}
	public String getSPN() {
		return SPN;
	}
	public void setSPN(String sPN) {
		SPN = sPN;
	}
	public short getOC() {
		return OC;
	}
	public void setOC(short oC) {
		OC = oC;
	}
	public EventStatus getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}
	public long getEventTimestamp() {
		return eventTimestamp;
	}
	public void setEventTimestamp(long eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}
	public String getEntityStatus() {
		return entityStatus;
	}
	public void setEntityStatus(String entityStatus) {
		this.entityStatus = entityStatus;
	}
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
	public int getOcCylcle() {
		return ocCylcle;
	}
	public void setOcCylcle(int ocCylcle) {
		this.ocCylcle = ocCylcle;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	

}
