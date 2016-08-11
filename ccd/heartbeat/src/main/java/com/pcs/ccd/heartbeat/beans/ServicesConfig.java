
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
package com.pcs.ccd.heartbeat.beans;

/**
 * This class is responsible for holding services config such as host,port,path etc
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 24, 2016
 */
public class ServicesConfig {
	
	private String apiMgrClientId;
	private String apiMgrClientSecret;
	private String gxyApiMgrClientId;
	private String gxyApiMgrClientSecret;
	private String host;
	private String port;
	private String scheme;
	private String getAssetsApi;
	private String getLatestDataApi;
	private String getCcdAssetsApi;
	private String getAllCcdTenantsApi;
	private String ccdUser;
	private String ccdUserPwd;
	private String gxyUser;
	private String gxyUserPwd;
	private String pointTemplate;
	
	public String getApiMgrClientId() {
		return apiMgrClientId;
	}
	public void setApiMgrClientId(String apiMgrClientId) {
		this.apiMgrClientId = apiMgrClientId;
	}
	public String getApiMgrClientSecret() {
		return apiMgrClientSecret;
	}
	public String getGxyApiMgrClientId() {
		return gxyApiMgrClientId;
	}
	public void setGxyApiMgrClientId(String gxyApiMgrClientId) {
		this.gxyApiMgrClientId = gxyApiMgrClientId;
	}
	public String getGxyApiMgrClientSecret() {
		return gxyApiMgrClientSecret;
	}
	public void setGxyApiMgrClientSecret(String gxyApiMgrClientSecret) {
		this.gxyApiMgrClientSecret = gxyApiMgrClientSecret;
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
	public String getGxyUser() {
		return gxyUser;
	}
	public void setGxyUser(String gxyUser) {
		this.gxyUser = gxyUser;
	}
	public String getGxyUserPwd() {
		return gxyUserPwd;
	}
	public void setGxyUserPwd(String gxyUserPwd) {
		this.gxyUserPwd = gxyUserPwd;
	}
	public String getGetAssetsApi() {
		return getAssetsApi;
	}
	public void setGetAssetsApi(String getAssetsApi) {
		this.getAssetsApi = getAssetsApi;
	}
	public String getGetLatestDataApi() {
		return getLatestDataApi;
	}
	public void setGetLatestDataApi(String getLatestDataApi) {
		this.getLatestDataApi = getLatestDataApi;
	}
	public String getGetCcdAssetsApi() {
		return getCcdAssetsApi;
	}
	public void setGetCcdAssetsApi(String getCcdAssetsApi) {
		this.getCcdAssetsApi = getCcdAssetsApi;
	}
	public String getGetAllCcdTenantsApi() {
		return getAllCcdTenantsApi;
	}
	public void setGetAllCcdTenantsApi(String getAllCcdTenantsApi) {
		this.getAllCcdTenantsApi = getAllCcdTenantsApi;
	}
	public String getPointTemplate() {
		return pointTemplate;
	}
	public void setPointTemplate(String pointTemplate) {
		this.pointTemplate = pointTemplate;
	}
	
}
