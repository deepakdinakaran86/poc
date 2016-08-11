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
package com.pcs.data.store.topology.config;

import com.pcs.data.store.utils.YamlUtils;

/**
 * 
 * @author pcseg369
 *
 */
public class StoreStormConfig {

	private String zookeeperRoot;
	private String nimbusHost;
	private Integer thriftPort;
	private Integer zookeeperPort;
	private String zookeeperServers;
	private String zookeeperHosts;
	private Long maxSpoutSpending;
	// Cassandra Details
	private String cassandraHosts;
	private String dataCenter = "datacenter1";
	private String clusterName = "DeviceCloud";
	private String cassandraBatchSize = "100";

	private static StoreStormConfig stormConfig;

	private StoreStormConfig() {

	}

	public static StoreStormConfig getInstance(String filePath) {

		if (stormConfig == null) {
			return YamlUtils.copyYamlFromFile(StoreStormConfig.class, filePath);
		}
		return stormConfig;
	}

	public String getZookeeperRoot() {
		if (zookeeperRoot == null) {
			return "";
		}
		return zookeeperRoot;
	}

	public void setZookeeperRoot(String zookeeperRoot) {
		this.zookeeperRoot = zookeeperRoot;
	}

	public String getNimbusHost() {
		return nimbusHost;
	}

	public void setNimbusHost(String nimbusHost) {
		this.nimbusHost = nimbusHost;
	}

	public Integer getThriftPort() {
		return thriftPort;
	}

	public void setThriftPort(Integer thriftPort) {
		this.thriftPort = thriftPort;
	}

	public Integer getZookeeperPort() {
		return zookeeperPort;
	}

	public void setZookeeperPort(Integer zookeeperPort) {
		this.zookeeperPort = zookeeperPort;
	}

	public String getZookeeperServers() {
		return zookeeperServers;
	}

	public void setZookeeperServers(String zookeeperServers) {
		this.zookeeperServers = zookeeperServers;
	}

	public String getZookeeperHosts() {
		return zookeeperHosts;
	}

	public void setZookeeperHosts(String zookeeperHosts) {
		this.zookeeperHosts = zookeeperHosts;
	}

	public Long getMaxSpoutSpending() {
		return maxSpoutSpending;
	}

	public void setMaxSpoutSpending(Long maxSpoutSpending) {
		this.maxSpoutSpending = maxSpoutSpending;
	}

	public String getCassandraHosts() {
		return cassandraHosts;
	}

	public void setCassandraHosts(String cassandraHosts) {
		this.cassandraHosts = cassandraHosts;
	}

	public String getDataCenter() {
		return dataCenter;
	}

	public void setDataCenter(String dataCenter) {
		this.dataCenter = dataCenter;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getCassandraBatchSize() {
		return cassandraBatchSize;
	}

	public void setCassandraBatchSize(String cassandraBatchSize) {
		this.cassandraBatchSize = cassandraBatchSize;
	}
}
