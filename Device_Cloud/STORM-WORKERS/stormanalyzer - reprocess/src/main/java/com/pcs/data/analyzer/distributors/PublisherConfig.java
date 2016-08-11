
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
package com.pcs.data.analyzer.distributors;

import java.io.Serializable;

import com.pcs.data.analyzer.util.YamlUtils;

/**
 * This class is responsible for ..(Short Description)
 *
 * @author pcseg199
 * @date Apr 1, 2015
 * @since galaxy-1.0.0
 */
public class PublisherConfig implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8261155347604143106L;

	private String registryHost;

	private int registryPort;

	private String registryName;
	
	private String mqttBrokerUrl;
	
	private String mqttUserName;
	
	private String mqttPassword;
	
	private String mqttQoS;
	
	private static PublisherConfig publisherConfig = null;
	
	private PublisherConfig() {

	}
	
	public static PublisherConfig getNewInstance(String filePath) {
		if(publisherConfig == null){
			publisherConfig = YamlUtils.copyYamlFromFile(PublisherConfig.class, filePath);
		}
		return publisherConfig;
	}

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

	public String getMqttBrokerUrl() {
		return mqttBrokerUrl;
	}

	public void setMqttBrokerUrl(String mqttBrokerUrl) {
		this.mqttBrokerUrl = mqttBrokerUrl;
	}

	public String getMqttUserName() {
		return mqttUserName;
	}

	public void setMqttUserName(String mqttUserName) {
		this.mqttUserName = mqttUserName;
	}

	public String getMqttPassword() {
		return mqttPassword;
	}

	public void setMqttPassword(String mqttPassword) {
		this.mqttPassword = mqttPassword;
	}

	public String getMqttQoS() {
		return mqttQoS;
	}

	public void setMqttQoS(String mqttQoS) {
		this.mqttQoS = mqttQoS;
	}

}
