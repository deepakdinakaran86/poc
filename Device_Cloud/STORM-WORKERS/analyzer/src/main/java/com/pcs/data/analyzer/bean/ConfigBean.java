
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
package com.pcs.data.analyzer.bean;

/**
 * This class is responsible for holding the configurations
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 30 2015
 */
public class ConfigBean {
	
	String cacheprovider;
	String cacheproviderConfigPath;
	String platformHostIp;
	String platformPort;
	String platformScheme;
	String deviceUrl;
	String contextUrl;
	String deviceConfigUrl;
	String phyQtyUrl;
	String alpineHostIp;
	String alpinePort;
	String alpineScheme;
	String heirarchyChildrenUrl;
	String apiMgrClientId;
	String apiMgrClientSecret;

	public String getCacheprovider() {
		return cacheprovider;
	}

	public void setCacheprovider(String cacheprovider) {
		this.cacheprovider = cacheprovider;
	}

	public String getCacheproviderConfigPath() {
		return cacheproviderConfigPath;
	}

	public void setCacheproviderConfigPath(String cacheproviderConfigPath) {
		this.cacheproviderConfigPath = cacheproviderConfigPath;
	}

	public String getPlatformHostIp() {
		return platformHostIp;
	}

	public void setPlatformHostIp(String platformHostIp) {
		this.platformHostIp = platformHostIp;
	}

	public String getPlatformPort() {
		return platformPort;
	}

	public void setPlatformPort(String platformPort) {
		this.platformPort = platformPort;
	}

	public String getPlatformScheme() {
		return platformScheme;
	}

	public void setPlatformScheme(String platformScheme) {
		this.platformScheme = platformScheme;
	}

	public String getDeviceUrl() {
		return deviceUrl;
	}

	public void setDeviceUrl(String deviceUrl) {
		this.deviceUrl = deviceUrl;
	}

	public String getContextUrl() {
		return contextUrl;
	}

	public void setContextUrl(String contextUrl) {
		this.contextUrl = contextUrl;
	}

	public String getDeviceConfigUrl() {
		return deviceConfigUrl;
	}

	public void setDeviceConfigUrl(String deviceConfigUrl) {
		this.deviceConfigUrl = deviceConfigUrl;
	}

	public String getPhyQtyUrl() {
		return phyQtyUrl;
	}

	public void setPhyQtyUrl(String phyQtyUrl) {
		this.phyQtyUrl = phyQtyUrl;
	}

	public String getAlpineHostIp() {
		return alpineHostIp;
	}

	public void setAlpineHostIp(String alpineHostIp) {
		this.alpineHostIp = alpineHostIp;
	}

	public String getAlpinePort() {
		return alpinePort;
	}

	public void setAlpinePort(String alpinePort) {
		this.alpinePort = alpinePort;
	}

	public String getAlpineScheme() {
		return alpineScheme;
	}

	public void setAlpineScheme(String alpineScheme) {
		this.alpineScheme = alpineScheme;
	}

	public String getHeirarchyChildrenUrl() {
		return heirarchyChildrenUrl;
	}

	public void setHeirarchyChildrenUrl(String heirarchyChildrenUrl) {
		this.heirarchyChildrenUrl = heirarchyChildrenUrl;
	}

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
	
	
	

}
