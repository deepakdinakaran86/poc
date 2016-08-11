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
package com.pcs.data.store.topology.builder;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;

import com.hmsonline.trident.cql.CassandraCqlStateFactory;
import com.hmsonline.trident.cql.MapConfiguredCqlClientFactory;
import com.pcs.data.store.topology.config.StoreStormConfig;
import com.pcs.data.store.topology.mode.TopologyMode;

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
	private StoreStormConfig stormConfig;
	private Long tupleEmitTimeout;
	private int numberOfWorkers;
	private int maxParallelism;

	static CustomTopologyBuilder customTopologyBuilder;

	private CustomTopologyBuilder() {

	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomTopologyBuilder.class);

	public static void build(StoreStormConfig stormConfig,
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
		customTopologyBuilder.stormConfig = stormConfig;
		customTopologyBuilder.stormTopology = stormTopology;
		customTopologyBuilder.topologyMode = topologyMode;
		customTopologyBuilder.topologyRef = topologyRef;
		customTopologyBuilder.tupleEmitTimeout = tupleEmitTimeout;
		customTopologyBuilder.numberOfWorkers = numberOfWorkers;
		customTopologyBuilder.maxParallelism = maxParallelism;
		customTopologyBuilder.init();
	}

	private void init() throws Exception {
		Config config = createConfiguration();
		switch (topologyMode) {
		case LOCAL :
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(topologyRef, config, stormTopology);
			LOGGER.info("Local Topology Submitted with name : {}", topologyRef);
			break;

		case CLUSTER :
			StormSubmitter.submitTopology(topologyRef, config, stormTopology);
			LOGGER.info("Cluster Topology Submitted with name : {}", topologyRef);
			break;

		default:
			throw new Exception("Invalid Topology Mode!!!");
		}
	}


	

	private Config createConfiguration() {

		Config config = new Config();
		config.put(Config.TOPOLOGY_TRIDENT_BATCH_EMIT_INTERVAL_MILLIS,
				tupleEmitTimeout);
		config.put(CassandraCqlStateFactory.TRIDENT_CASSANDRA_MAX_BATCH_SIZE,
				stormConfig.getCassandraBatchSize());
		
		LOGGER.info("MapConfiguredCqlClientFactory.TRIDENT_CASSANDRA_CLUSTER_NAME = {}",stormConfig.getClusterName());
		LOGGER.info("MapConfiguredCqlClientFactory.TRIDENT_CASSANDRA_LOCAL_DATA_CENTER_NAME = {}",stormConfig.getDataCenter());
		LOGGER.info("MapConfiguredCqlClientFactory.TRIDENT_CASSANDRA_CQL_HOSTS = {}",stormConfig.getCassandraHosts());
		
		config.put(MapConfiguredCqlClientFactory.TRIDENT_CASSANDRA_CLUSTER_NAME, stormConfig.getClusterName());
		config.put(MapConfiguredCqlClientFactory.TRIDENT_CASSANDRA_LOCAL_DATA_CENTER_NAME, stormConfig.getDataCenter());
		config.put(MapConfiguredCqlClientFactory.TRIDENT_CASSANDRA_CQL_HOSTS, stormConfig.getCassandraHosts());
		config.setNumWorkers(numberOfWorkers);
		config.setMaxTaskParallelism(maxParallelism);
		
		switch (topologyMode) {
		case CLUSTER:

			config.put(Config.NIMBUS_HOST, stormConfig.getNimbusHost());
			config.put(Config.NIMBUS_THRIFT_PORT, stormConfig.getThriftPort());
			config.put(Config.STORM_ZOOKEEPER_PORT, stormConfig.getZookeeperPort());
			config.put(Config.STORM_ZOOKEEPER_SERVERS,Arrays.asList(stormConfig.getZookeeperServers()));
			config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING,stormConfig.getMaxSpoutSpending());
			config.put(Config.TOPOLOGY_MESSAGE_TIMEOUT_SECS, 120);

			break;
		case LOCAL:
			break;

		default:
			break;
		}
		return config;
	}
	
}
