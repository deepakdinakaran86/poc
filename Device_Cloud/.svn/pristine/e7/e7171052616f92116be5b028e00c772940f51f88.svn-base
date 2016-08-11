
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
package com.pcs.deviceframework.datadist.beans;


/**
 * This class is responsible for providing data distribution configuration
 * 
 * @author pcseg129(Seena Jyothish)
 * @date March 18 2015
 */
public class DistributionConfig {
	String rmiServerHost;//example: localhost
	String rmiServerHostName;//example: PCSHQ-532
	Integer rmiPort;//example: 1099
	String jmsHostUrl;//example: tcp://localhost:61616
	String kafkaBrokers;//example: "10.234.25.12:9092,10.234.25.13:9092,10.234.25.14:9092"
	String kafkaBrokerSeeds;
	String kafkaPort;
	String zookeeper;//example: localhost:2181
	String zookeeperSessionTimeout;
	String zookeeperSyncTime;
	String kafkaAutoCommitInterval;
	String kafkaAutooffsetReset;
	String namenow;
	

	public DistributionConfig(){

	}

	public String getRmiServerHost() {
		return rmiServerHost;
	}

	public void setRmiServerHost(String rmiServerHost) {
		this.rmiServerHost = rmiServerHost;
	}

	public String getRmiServerHostName() {
		return rmiServerHostName;
	}

	public void setRmiServerHostName(String rmiServerHostName) {
		this.rmiServerHostName = rmiServerHostName;
	}

	public Integer getRmiPort() {
		return rmiPort;
	}

	public void setRmiPort(Integer rmiPort) {
		this.rmiPort = rmiPort;
	}

	public String getJmsHostUrl() {
		return jmsHostUrl;
	}

	public void setJmsHostUrl(String jmsHostUrl) {
		this.jmsHostUrl = jmsHostUrl;
	}

	public String getKafkaBrokers() {
		return kafkaBrokers;
	}

	public void setKafkaBrokers(String kafkaBrokers) {
		this.kafkaBrokers = kafkaBrokers;
	}

	public String getKafkaBrokerSeeds() {
		return kafkaBrokerSeeds;
	}

	public void setKafkaBrokerSeeds(String kafkaBrokerSeeds) {
		this.kafkaBrokerSeeds = kafkaBrokerSeeds;
	}

	public String getKafkaPort() {
		return kafkaPort;
	}

	public void setKafkaPort(String kafkaPort) {
		this.kafkaPort = kafkaPort;
	}

	public String getZookeeper() {
		return zookeeper;
	}

	/*
	 *  ZooKeeper connection string in the form hostname:port
	 *  To allow connecting through other ZooKeeper nodes when that host is down
	 *  you can also specify multiple hosts in the form 
	 *  hostname1:port1,hostname2:port2,hostname3:port3.
	 */
	public void setZookeeper(String zookeeper) {
		this.zookeeper = zookeeper;
	}

	public String getZookeeperSessionTimeout() {
		return zookeeperSessionTimeout;
	}

	/*
	 * ZooKeeper session timeout.
	 * If the server fails to heartbeat to ZooKeeper within this period of time
	 * it is considered dead.
	 */
	public void setZookeeperSessionTimeout(String zookeeperSessionTimeout) {
		this.zookeeperSessionTimeout = zookeeperSessionTimeout;
	}

	public String getZookeeperSyncTime() {
		return zookeeperSyncTime;
	}

	/*
	 * How far a zookeeper follower can be behind a zookeeper leader
	 */
	public void setZookeeperSyncTime(String zookeeperSyncTime) {
		this.zookeeperSyncTime = zookeeperSyncTime;
	}

	public String getKafkaAutoCommitInterval() {
		return kafkaAutoCommitInterval;
	}

	/*
	 * The frequency in ms that the consumer offsets are committed to zookeeper
	 */
	public void setKafkaAutoCommitInterval(String autoCommitInterval) {
		this.kafkaAutoCommitInterval = autoCommitInterval;
	}

	public String getKafkaAutooffsetReset() {
		return kafkaAutooffsetReset;
	}

	/*
	 * What to do when there is no initial offset in ZooKeeper or if an offset is out of range:
	 * smallest : automatically reset the offset to the smallest offset
	 * largest : automatically reset the offset to the largest offset
	 * anything else: throw exception to the consumer
	 */
	public void setKafkaAutooffsetReset(String autooffsetReset) {
		this.kafkaAutooffsetReset = autooffsetReset;
	}

	public String getNamenow() {
		return namenow;
	}

	public void setNamenow(String namenow) {
		this.namenow = namenow;
	}

}
