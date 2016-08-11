
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
package com.pcs.analytics.storm.persistence.beans;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129
 */
public class PersistConfig {

	private String jmsHostUrl ;
	private String queueName;
	private Integer queueSize;
	private String persistAPIHost;
	private Integer persistAPIPort;
	private String persistAPIBatchURL;

	public String getJmsHostUrl() {
		return jmsHostUrl;
	}
	
	public void setJmsHostUrl(String jmsHostUrl) {
		this.jmsHostUrl = jmsHostUrl;
	}
	
	public String getQueueName() {
		return queueName;
	}
	
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	public Integer getQueueSize() {
		return queueSize;
	}
	
	public void setQueueSize(Integer queueSize) {
		this.queueSize = queueSize;
	}
	
	public String getPersistAPIHost() {
		return persistAPIHost;
	}
	
	public void setPersistAPIHost(String persistAPIHost) {
		this.persistAPIHost = persistAPIHost;
	}
	
	public Integer getPersistAPIPort() {
		return persistAPIPort;
	}
	
	public void setPersistAPIPort(Integer persistAPIPort) {
		this.persistAPIPort = persistAPIPort;
	}
	
	public String getPersistAPIBatchURL() {
		return persistAPIBatchURL;
	}
	
	public void setPersistAPIBatchURL(String persistAPIBatchURL) {
		this.persistAPIBatchURL = persistAPIBatchURL;
	}



}
