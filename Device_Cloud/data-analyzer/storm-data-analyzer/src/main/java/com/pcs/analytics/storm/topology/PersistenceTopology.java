
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
package com.pcs.analytics.storm.topology;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;

import com.pcs.analytics.configuration.Configuration;
import com.pcs.analytics.enums.TopologyMode;
import com.pcs.analytics.storm.bolts.DataPersistBolt;
import com.pcs.analytics.storm.bolts.PublishBolt;
import com.pcs.analytics.storm.scheme.MessageScheme;
import com.pcs.analytics.util.CustomTopologyBuilder;
import com.pcs.analytics.util.YamlUtils;

/**
 * This class is responsible for providing a topology for persisting device messages to a data store
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 17 Jun 2015
 */
public class PersistenceTopology {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(PersistenceTopology.class);
	private static String topologyConfFilePath;
	private static String stormConfFilePath;
	private static BrokerHosts brokerHosts;
	private static String topic;
	private static String zkRoot;
	private static String topologyReference;
	private static TopologyMode topologyMode;
	private static String parentSpoutName;
	private static String persistBoltName;


	private static Long tupleEmitTimeout;
	private static int numberOfWorkers;

	private static int parentSpoutMaxParallelism;
	private static int persistBoltMaxParallelism;
	private static int topologyMaxParallelism;
	
	private static long kafkaStartOffSet = -2l;
	private static String publisherConfFilePath;
	private static String applicationContextFilePath;
	

	/**
	 * To execute the topology builder
	 * 
	 * @return
	 */
	public static boolean execute() {
		initialize();
		boolean executionProgressing = false;
		try {
			CustomTopologyBuilder.build(stormConfFilePath, buildTopology(),
			        topologyMode, topologyReference, tupleEmitTimeout,
			        numberOfWorkers, topologyMaxParallelism);
			executionProgressing = true;
		} catch (Exception e) {
			LOGGER.error("Error Executing Topology", e);
		}

		return executionProgressing;
	}
	
	/**
	 * To initialize the configuration ,Any Exception will stop topology
	 * deployment
	 */
	@SuppressWarnings("unchecked")
	private static void initialize() {
		try {
			HashMap<Object, Object> load = YamlUtils.copyYamlFromFile(
			        HashMap.class, topologyConfFilePath);
			brokerHosts = new ZkHosts(load.get(Configuration.BROKER_HOSTS)
			        .toString());
			topic = load.get(Configuration.MESSSAGE_TOPIC).toString();
			zkRoot = load.get(Configuration.ZK_ROOT) == null ? "" : load.get(
			        Configuration.ZK_ROOT).toString();
			topologyReference = load.get(Configuration.TOPOLOGY_REFERENCE)
			        .toString();

			Object topologyMaxParallelismObj = load
			        .get(Configuration.TOPOLOGY_MAX_PARALLELISM);

			topologyMaxParallelism = topologyMaxParallelismObj != null
			        ? Integer.parseInt(topologyMaxParallelismObj.toString())
			        : 1;

			parentSpoutName = load.get(Configuration.PARENT_SPOUT_NAME)
			        .toString();

			Object spoutMaxParallelismObj = load
			        .get(Configuration.PARENT_SPOUT_MAX_PARALLELISM);

			parentSpoutMaxParallelism = spoutMaxParallelismObj != null
			        ? Integer.parseInt(spoutMaxParallelismObj.toString())
			        : 1;

			persistBoltName = load.get(Configuration.PERSIST_BOLT).toString();

			Object persistBoltMaxParallelismObj = load
			        .get(Configuration.PERSIST_BOLT_MAX_PARALLELISM);

			persistBoltMaxParallelism = persistBoltMaxParallelismObj != null
			        ? Integer.parseInt(persistBoltMaxParallelismObj.toString())
			        : 1;

			Object topolgyMode = load.get(Configuration.TOPOLOGY_MODE);
			try {
				topologyMode = topolgyMode == null
				        ? TopologyMode.LOCAL
				        : TopologyMode.valueOf(topolgyMode.toString());
			} catch (Exception e) {
				LOGGER.error("Error in reading TopologyMode", e);
				throw new RuntimeException(e);
			}
			Object topologyEmitInterval = load
			        .get(Configuration.TOPOLOGY_EMIT_INTERVAL_MILLIS);
			tupleEmitTimeout = topologyEmitInterval != null ? Long
			        .parseLong(topologyEmitInterval.toString()) : null;

			Object noOfWorkers = load.get(Configuration.NO_OF_WORKERS);
			numberOfWorkers = noOfWorkers != null ? Integer
			        .parseInt(noOfWorkers.toString()) : null;

			Object kafkaStartOffSetObj = load
			        .get(Configuration.KAFKA_START_OFFSET);
			kafkaStartOffSet = kafkaStartOffSetObj != null ? Long
			        .parseLong(kafkaStartOffSetObj.toString()) : -2;

		} catch (Exception e) {
			LOGGER.error("Error in reading persistence topology configuration", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * To create and return topology
	 * 
	 * @return
	 */
	private static StormTopology buildTopology() {

		TopologyBuilder builder = new TopologyBuilder();

		// Kafka Spout for receiving Analyzed Device Message
		builder.setSpout(parentSpoutName, buildKafkaSpout(),
		        parentSpoutMaxParallelism);

		// Bolt for invoking persisting API
		builder.setBolt(persistBoltName, new PublishBolt(publisherConfFilePath,"PERSIST_QUEUE"),
		        persistBoltMaxParallelism).shuffleGrouping(parentSpoutName);
		

		LOGGER.info("Topology Created !!!");
		return builder.createTopology();
	}
	
	/**
	 * To build KafkaSpout used in the topology
	 * 
	 * @return
	 */
	private static KafkaSpout buildKafkaSpout() {
		SpoutConfig kafkaConfig = new SpoutConfig(brokerHosts, topic, zkRoot,
		        topologyReference);

		kafkaConfig.startOffsetTime = kafkaStartOffSet;
		LOGGER.info("@@@@@@ .......afkaConfig.startOffsetTime " + kafkaConfig.startOffsetTime);
		if (kafkaStartOffSet == -2) {
			kafkaConfig.forceFromStart = true;
		} else if (kafkaStartOffSet == -1) {
			kafkaConfig.forceFromStart = false;
		} else {
			throw new RuntimeException(
			        "Invalid kafkaStartOffSet,Mention Either -1 or -2");
		}

		kafkaConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());
		kafkaConfig.socketTimeoutMs = 60000;
		return new KafkaSpout(kafkaConfig);
	}


	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			throw new RuntimeException("Filepaths Not Found");
		}
		stormConfFilePath = args[0];
		topologyConfFilePath = args[1];
		publisherConfFilePath = args[2];
		execute();
	}
}
