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
package com.pcs.data.analyzer.storm.configuratios;

import com.pcs.data.analyzer.util.YamlUtils;

/**
 * 
 * @author pcseg369
 *
 */
public class StormConfig {

	private String zookeeperRoot;
	private String nimbusHost;
	private Integer thriftPort;
	private Integer zookeeperPort;
	private String zookeeperServers;
	private String zookeeperHosts;
	private Long maxSpoutSpending;

	private static StormConfig stormConfig;

	private StormConfig() {

	}

	public static StormConfig getInstance(String filePath) {

		if (stormConfig == null) {
			return YamlUtils.copyYamlFromFile(StormConfig.class, filePath);
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
}
