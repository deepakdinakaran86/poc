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
package com.pcs.alpine.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO for Audit
 * 
 * @author pcseg288 Deepak Dinakaran
 */
@XmlRootElement(name = "audit")
public class ApplicationAuditDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String activityTimeStamp;
	private String eventDomain;
	private String affectedModule;
	private String auditSummary;
	private String ipAddress;
	private String eventLocale;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getActivityTimeStamp() {
		return activityTimeStamp;
	}
	public void setActivityTimeStamp(String activityTimeStamp) {
		this.activityTimeStamp = activityTimeStamp;
	}
	public String getEventDomain() {
		return eventDomain;
	}
	public void setEventDomain(String eventDomain) {
		this.eventDomain = eventDomain;
	}
	public String getAffectedModule() {
		return affectedModule;
	}
	public void setAffectedModule(String affectedModule) {
		this.affectedModule = affectedModule;
	}
	public String getAuditSummary() {
		return auditSummary;
	}
	public void setAuditSummary(String auditSummary) {
		this.auditSummary = auditSummary;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getEventLocale() {
		return eventLocale;
	}
	public void setEventLocale(String eventLocale) {
		this.eventLocale = eventLocale;
	}
	


}
