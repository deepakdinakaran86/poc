
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

/**
 * This class is responsible for holding services config such as host,port,path etc
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 24, 2016
 */
public class ServicesConfig {
	
	private String apiMgrClientId;
	private String apiMgrClientSecret;
	private String host;
	private String port;
	private String scheme;
	private String emailApi;
	private String getFaultEvtApi;
	private String persistFaultEvtApi;
	private String updateFaultEvtApi;
	private String getAttachedContactsApi;
	private String ccdUser;
	private String ccdUserPwd;
	
	public String getApiMgrClientId() {
		return apiMgrClientId;
	}
	public void setApiMgrClientId(String apiMgrClientId) {
		this.apiMgrClientId = apiMgrClientId;
	}
	public String getApiMgrClientSecret() {
		return apiMgrClientSecret;
	}
	public void setApiMgrClientSecret(String apiMgrClientSecret) {
		this.apiMgrClientSecret = apiMgrClientSecret;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getEmailApi() {
		return emailApi;
	}
	public void setEmailApi(String emailApi) {
		this.emailApi = emailApi;
	}
	public String getGetFaultEvtApi() {
		return getFaultEvtApi;
	}
	public void setGetFaultEvtApi(String getFaultEvtApi) {
		this.getFaultEvtApi = getFaultEvtApi;
	}
	public String getPersistFaultEvtApi() {
		return persistFaultEvtApi;
	}
	public void setPersistFaultEvtApi(String persistFaultEvtApi) {
		this.persistFaultEvtApi = persistFaultEvtApi;
	}
	public String getUpdateFaultEvtApi() {
		return updateFaultEvtApi;
	}
	public void setUpdateFaultEvtApi(String updateFaultEvtApi) {
		this.updateFaultEvtApi = updateFaultEvtApi;
	}
	public String getGetAttachedContactsApi() {
		return getAttachedContactsApi;
	}
	public void setGetAttachedContactsApi(String getAttachedContactsApi) {
		this.getAttachedContactsApi = getAttachedContactsApi;
	}
	public String getCcdUser() {
		return ccdUser;
	}
	public void setCcdUser(String ccdUser) {
		this.ccdUser = ccdUser;
	}
	public String getCcdUserPwd() {
		return ccdUserPwd;
	}
	public void setCcdUserPwd(String ccdUserPwd) {
		this.ccdUserPwd = ccdUserPwd;
	}
	
	
	
}
