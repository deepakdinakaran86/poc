
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
package com.pcs.device.activity.beans;

/**
 * This class is responsible for holding services config such as host,port,path etc
 * 
 * @author pcseg129(Seena Jyothish)
 * Apr 18, 2016
 */
public class ServicesConfig {
	
	private String registryHost;
	private int registryPort;
	private String registryName;
	private String host;
	private String port;
	private String scheme;
	private String getAllActiveDeviceApi;
	private String getAllDeviceApi;
	private String persistTransitionApi;
	
	public String getRegistryHost() {
		return registryHost;
	}
	public void setRegistryHost(String registryHost) {
		this.registryHost = registryHost;
	}
	public int getRegistryPort() {
		return registryPort;
	}
	public void setRegistryPort(int registryPort) {
		this.registryPort = registryPort;
	}
	public String getRegistryName() {
		return registryName;
	}
	public void setRegistryName(String registryName) {
		this.registryName = registryName;
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
	public String getGetAllActiveDeviceApi() {
		return getAllActiveDeviceApi;
	}
	public void setGetAllActiveDeviceApi(String getAllActiveDeviceApi) {
		this.getAllActiveDeviceApi = getAllActiveDeviceApi;
	}
	public String getGetAllDeviceApi() {
		return getAllDeviceApi;
	}
	public void setGetAllDeviceApi(String getAllDeviceApi) {
		this.getAllDeviceApi = getAllDeviceApi;
	}
	public String getPersistTransitionApi() {
		return persistTransitionApi;
	}
	public void setPersistTransitionApi(String persistTransitionApi) {
		this.persistTransitionApi = persistTransitionApi;
	}
	
}
