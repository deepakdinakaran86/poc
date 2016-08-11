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
package com.pcs.fault.monitor.bean;

/**
 * This class is responsible for topology configuration
 * 
 * @author pcseg129(Seena Jyothish) Jan 20, 2016
 */
public class TopologyConfig {
	
	String brokerHosts;
	String zookeeperRoot;
	String kafkaStartOffSet;
	
	String parentSpoutMaxParallelism;
	String topologyEmitInterval;
	String topologyMode;
	String topologyMaxParallelism;
	String numberOfWorkers;
	
	String topologyName;
	String faultMessageTopic;
	String faultResponseTopic;
	
	String faultReadSpoutName;
	String faultResponseSpoutName;
	
	String faultCheckBoltName;
	String faultReqBuildBoltName;
	String faultPersistBoltName;
	String faultSendBoltName;
	String faultAnalyzeBoltName;
	String faultRespNotifierBoltName;
	String emailSenderBoltName;
	

	public String getEmailSenderBoltName() {
		return emailSenderBoltName;
	}

	public void setEmailSenderBoltName(String emailSenderBoltName) {
		this.emailSenderBoltName = emailSenderBoltName;
	}

	public String getBrokerHosts() {
		return brokerHosts;
	}

	public void setBrokerHosts(String brokerHosts) {
		this.brokerHosts = brokerHosts;
	}

	public String getFaultMessageTopic() {
		return faultMessageTopic;
	}

	public void setFaultMessageTopic(String faultMessageTopic) {
		this.faultMessageTopic = faultMessageTopic;
	}

	public String getFaultResponseTopic() {
		return faultResponseTopic;
	}

	public void setFaultResponseTopic(String faultResponseTopic) {
		this.faultResponseTopic = faultResponseTopic;
	}

	public String getZookeeperRoot() {
		return zookeeperRoot;
	}

	public void setZookeeperRoot(String zookeeperRoot) {
		this.zookeeperRoot = zookeeperRoot;
	}

	public String getTopologyName() {
		return topologyName;
	}

	public void setTopologyName(String topologyName) {
		this.topologyName = topologyName;
	}

	public String getFaultReadSpoutName() {
		return faultReadSpoutName;
	}

	public void setFaultReadSpoutName(String faultReadSpoutName) {
		this.faultReadSpoutName = faultReadSpoutName;
	}

	public String getParentSpoutMaxParallelism() {
		return parentSpoutMaxParallelism;
	}

	public void setParentSpoutMaxParallelism(String parentSpoutMaxParallelism) {
		this.parentSpoutMaxParallelism = parentSpoutMaxParallelism;
	}

	public String getTopologyEmitInterval() {
		return topologyEmitInterval;
	}

	public void setTopologyEmitInterval(String topologyEmitInterval) {
		this.topologyEmitInterval = topologyEmitInterval;
	}

	public String getTopologyMode() {
		return topologyMode;
	}

	public void setTopologyMode(String topologyMode) {
		this.topologyMode = topologyMode;
	}

	public String getTopologyMaxParallelism() {
		return topologyMaxParallelism;
	}

	public void setTopologyMaxParallelism(String topologyMaxParallelism) {
		this.topologyMaxParallelism = topologyMaxParallelism;
	}

	public String getNumberOfWorkers() {
		return numberOfWorkers;
	}

	public void setNumberOfWorkers(String numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
	}

	public String getFaultCheckBoltName() {
		return faultCheckBoltName;
	}

	public void setFaultCheckBoltName(String faultCheckBoltName) {
		this.faultCheckBoltName = faultCheckBoltName;
	}

	public String getFaultReqBuildBoltName() {
		return faultReqBuildBoltName;
	}

	public void setFaultReqBuildBoltName(String faultReqBuildBoltName) {
		this.faultReqBuildBoltName = faultReqBuildBoltName;
	}

	public String getFaultSendBoltName() {
		return faultSendBoltName;
	}

	public void setFaultSendBoltName(String faultSendBoltName) {
		this.faultSendBoltName = faultSendBoltName;
	}

	public String getFaultPersistBoltName() {
		return faultPersistBoltName;
	}

	public void setFaultPersistBoltName(String faultPersistBoltName) {
		this.faultPersistBoltName = faultPersistBoltName;
	}

	public String getKafkaStartOffSet() {
		return kafkaStartOffSet;
	}

	public void setKafkaStartOffSet(String kafkaStartOffSet) {
		this.kafkaStartOffSet = kafkaStartOffSet;
	}

	public String getFaultResponseSpoutName() {
		return faultResponseSpoutName;
	}

	public void setFaultResponseSpoutName(String faultResponseSpoutName) {
		this.faultResponseSpoutName = faultResponseSpoutName;
	}

	public String getFaultRespNotifierBoltName() {
		return faultRespNotifierBoltName;
	}

	public void setFaultRespNotifierBoltName(String faultRespNotifierBoltName) {
		this.faultRespNotifierBoltName = faultRespNotifierBoltName;
	}

	public String getFaultAnalyzeBoltName() {
		return faultAnalyzeBoltName;
	}

	public void setFaultAnalyzeBoltName(String faultAnalyzeBoltName) {
		this.faultAnalyzeBoltName = faultAnalyzeBoltName;
	}
	
	

}
