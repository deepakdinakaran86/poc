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
package com.pcs.fault.monitor.util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;

import com.pcs.fault.monitor.bean.StormConfig;
import com.pcs.fault.monitor.bean.TopologyConfig;
import com.pcs.fault.monitor.enums.TopologyMode;

/**
 * This class is responsible for ..(Short Description)
 *
 * @author pcseg199
 * @date Apr 1, 2015
 * @since galaxy-1.0.0
 */
public class CustomTopologyBuilder {

	private TopologyMode topologyMode;
	private StormTopology stormTopology;
	private String topologyRef;
	private StormConfig stormConfig;
	private Long tupleEmitTimeout;
	private int numberOfWorkers;
	private int maxParallelism;

	static CustomTopologyBuilder customTopologyBuilder;

	private CustomTopologyBuilder() {

	}

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(CustomTopologyBuilder.class);

	public static void build(String stormConfFilePath,
	        StormTopology stormTopology,TopologyConfig config) throws Exception{
		if(config.getTopologyMode().equals(TopologyMode.LOCAL.toString())){
			if(Integer.parseInt(config.getTopologyMaxParallelism()) < 1){
				throw new RuntimeException(
				        "maxParallelism cannot be less than 1");
			}
		}
		
		if (customTopologyBuilder == null) {
			customTopologyBuilder = new CustomTopologyBuilder();
		}
		
		customTopologyBuilder.stormConfig = StormConfig
		        .getInstance(stormConfFilePath);
		customTopologyBuilder.stormTopology = stormTopology;
		customTopologyBuilder.topologyMode = TopologyMode.valueOf(config.getTopologyMode());
		customTopologyBuilder.topologyRef = config.getTopologyName();
		customTopologyBuilder.tupleEmitTimeout = Long.parseLong(config.getTopologyEmitInterval());
		customTopologyBuilder.numberOfWorkers = Integer.parseInt(config.getNumberOfWorkers());
		customTopologyBuilder.maxParallelism = Integer.parseInt(config.getTopologyMaxParallelism());
		customTopologyBuilder.init();
	}
	
	public static void build(String stormConfFilePath,
	        StormTopology stormTopology, TopologyMode topologyMode,
	        String topologyRef, long tupleEmitTimeout, int numberOfWorkers,
	        int maxParallelism) throws Exception {

		if (topologyMode == TopologyMode.LOCAL) {
			if (maxParallelism < 1) {
				throw new RuntimeException(
				        "maxParallelism cannot be less than 1");
			}
			// TODO to check for mandatory fields
		}
		if (customTopologyBuilder == null) {
			customTopologyBuilder = new CustomTopologyBuilder();
		}
		customTopologyBuilder.stormConfig = StormConfig
		        .getInstance(stormConfFilePath);
		customTopologyBuilder.stormTopology = stormTopology;
		customTopologyBuilder.topologyMode = topologyMode;
		customTopologyBuilder.topologyRef = topologyRef;
		customTopologyBuilder.tupleEmitTimeout = tupleEmitTimeout;
		customTopologyBuilder.numberOfWorkers = numberOfWorkers;
		customTopologyBuilder.maxParallelism = maxParallelism;
		customTopologyBuilder.init();
	}

	private void init() throws Exception {
		switch (topologyMode) {
			case LOCAL :
				startLocalMode();
				break;

			case CLUSTER :
				startClusterMode();
				break;

			default:
				throw new Exception("Invalid Topology Mode!!!");
		}
	}

	private boolean startLocalMode() {
		boolean localModeIntiated = false;

		Config config = new Config();
		config.put(Config.TOPOLOGY_TRIDENT_BATCH_EMIT_INTERVAL_MILLIS,
		        tupleEmitTimeout);

		config.setNumWorkers(numberOfWorkers);
		config.setMaxTaskParallelism(maxParallelism);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology(topologyRef, config, stormTopology);

		LOGGER.info("Topology Submitted with name : {}", topologyRef);
		return localModeIntiated;
	}

	private boolean startClusterMode() throws Exception {

		boolean clusterModeIntiated = false;

		Config config = new Config();
		config.put(Config.TOPOLOGY_TRIDENT_BATCH_EMIT_INTERVAL_MILLIS,
		        tupleEmitTimeout);

		config.setNumWorkers(numberOfWorkers);
		config.setMaxTaskParallelism(maxParallelism);

		config.put(Config.NIMBUS_HOST, stormConfig.getNimbusHost());
		config.put(Config.NIMBUS_THRIFT_PORT, stormConfig.getThriftPort());
		config.put(Config.STORM_ZOOKEEPER_PORT, stormConfig.getZookeeperPort());
		config.put(Config.STORM_ZOOKEEPER_SERVERS,
		        Arrays.asList(stormConfig.getZookeeperServers()));

		config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING,
		        stormConfig.getMaxSpoutSpending());

		config.put(Config.TOPOLOGY_MESSAGE_TIMEOUT_SECS, 120);
		
		config.setDebug(true);
		StormSubmitter.submitTopology(topologyRef, config, stormTopology);
		return clusterModeIntiated;

	}

}
