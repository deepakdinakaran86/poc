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
package com.pcs.data.analyzer.storm.topology;

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

import com.pcs.data.analyzer.config.Configuration;
import com.pcs.data.analyzer.enums.TopologyMode;
import com.pcs.data.analyzer.storm.bolts.AlarmPublishBolt;
import com.pcs.data.analyzer.storm.bolts.LivePublishBolt;
import com.pcs.data.analyzer.storm.scheme.AlarmMessageScheme;
import com.pcs.data.analyzer.storm.scheme.LiveMessageScheme;
import com.pcs.data.analyzer.util.CustomTopologyBuilder;
import com.pcs.data.analyzer.util.YamlUtils;

/**
 * Class responsible for creating storm topology
 *
 * @author pcseg171 (Aneesh Haridasan)
 * @date Mar 29, 2015
 * @since galaxy-1.0.0
 */
public class MessageAnyalyzerTopology {

	private static String topologyConfFilePath;
	private static String stormConfFilePath;
	private static BrokerHosts brokerHosts;
	private static String zkRoot;
	private static String topologyReference;
	private static TopologyMode topologyMode;
	private static String livePublishSpoutName;
	private static String alarmPublishSpoutName;
	private static String liveDataPushBoltName;
	private static String alarmPushBoltName;
	//private static String alarmPersistBoltName;
	private static String liveFeedTopic;
	private static String alarmFeedTopic;
	private static String alarmPersistTopic;
	private static Long tupleEmitTimeout;
	private static int numberOfWorkers;
	private static int maxParallelism;
	private static long kafkaStartOffSet = -2l;
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageAnyalyzerTopology.class);
	private static String publisherConfFilePath;
	//private static String alarmPublisherConfFilePath;
	private static int offsetUpdateIntervalMs = 2000;

	/**
	 * To execute topology builder
	 *
	 * @return
	 */
	public static boolean execute() {
		initialize();
		boolean executionProgressing = false;
		try {
			CustomTopologyBuilder.build(stormConfFilePath, buildTopology(),topologyMode, topologyReference, 
					tupleEmitTimeout,numberOfWorkers, maxParallelism);
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
			LOGGER.info("Initializing application");

			HashMap<Object, Object> load = YamlUtils.copyYamlFromFile(HashMap.class, topologyConfFilePath);
			brokerHosts = new ZkHosts(load.get(Configuration.BROKER_HOSTS).toString());
			zkRoot = load.get(Configuration.ZK_ROOT) == null ? "" : load.get(Configuration.ZK_ROOT).toString();
			topologyReference = load.get(Configuration.TOPOLOGY_REFERENCE).toString();

			livePublishSpoutName = load.get(Configuration.LIVE_PUBLISH_SPOUT).toString();

			alarmPublishSpoutName = load.get(Configuration.ALARM_PUBLISH_SPOUT).toString();

			liveFeedTopic = load.get(Configuration.LIVE_FEED_STREAM).toString();

			alarmFeedTopic = load.get(Configuration.ALARM_FEED_STREAM).toString();
			
			alarmPersistTopic = load.get(Configuration.ALARM_PERSIST_TOPIC).toString();


			liveDataPushBoltName = load.get(Configuration.LIVE_DATA_PUBLISH_BOLT).toString();

			alarmPushBoltName = load.get(Configuration.ALARM_PUBLISH_BOLT).toString();

			Object topolgyMode = load.get(Configuration.TOPOLOGY_MODE);

			try {
				topologyMode = topolgyMode == null ? TopologyMode.LOCAL: TopologyMode.valueOf(topolgyMode.toString());
			} catch (Exception e) {
				LOGGER.error("Error in reading TopologyMode", e);
				throw new RuntimeException(e);
			}

			Object topologyEmitInterval = load.get(Configuration.TOPOLOGY_EMIT_INTERVAL_MILLIS);
			tupleEmitTimeout = topologyEmitInterval != null ? Long.parseLong(topologyEmitInterval.toString()) : null;

			Object noOfWorkers = load.get(Configuration.NO_OF_WORKERS);
			numberOfWorkers = noOfWorkers != null ? Integer.parseInt(noOfWorkers.toString()) : null;

			Object maxParallelismObj = load.get(Configuration.TOPOLOGY_MAX_PARALLELISM);
			maxParallelism = maxParallelismObj != null ? Integer.parseInt(maxParallelismObj.toString()) : null;

			Object kafkaStartOffSetObj = load.get(Configuration.KAFKA_START_OFFSET);
			kafkaStartOffSet = kafkaStartOffSetObj != null ? Long.parseLong(kafkaStartOffSetObj.toString()) : -2;
		} catch (Exception e) {
			LOGGER.error("Error in reading topology configuration", e);
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

		

		// Kafka Spout to receive data from live-feed-stream topic
		SpoutConfig liveMessageIdentifier = new SpoutConfig(brokerHosts, liveFeedTopic,zkRoot, topologyReference);
		liveMessageIdentifier.stateUpdateIntervalMs = offsetUpdateIntervalMs;
		builder.setSpout(livePublishSpoutName,buildLiveSpout(liveMessageIdentifier), maxParallelism);
		builder.setBolt(liveDataPushBoltName,new LivePublishBolt(publisherConfFilePath)).shuffleGrouping(livePublishSpoutName);

		// Kafka Spout to read data from alarm-message topic
		SpoutConfig spoutConfig2 = new SpoutConfig(brokerHosts, alarmFeedTopic,
				zkRoot, topologyReference);
		spoutConfig2.stateUpdateIntervalMs = offsetUpdateIntervalMs;
		builder.setSpout(alarmPublishSpoutName, buildAlarmSpout(spoutConfig2),
				maxParallelism);
		// Bolt to publish alarm to all datasource contexts
		builder.setBolt(alarmPushBoltName,
				new AlarmPublishBolt(publisherConfFilePath,alarmPersistTopic)).shuffleGrouping(
				alarmPublishSpoutName);
		
		/*builder.setBolt(alarmPersistBoltName,
				new AlarmPersistBolt(alarmPublisherConfFilePath)).shuffleGrouping(
				alarmPublishSpoutName);*/

		LOGGER.info("Topology Created with name {} with mode {}",
				topologyReference, topologyMode);
		return builder.createTopology();
	}


	private static KafkaSpout buildLiveSpout(SpoutConfig spoutConfig) {
		SpoutConfig kafkaConfig = spoutConfig;
		kafkaConfig.scheme = new SchemeAsMultiScheme(new LiveMessageScheme());
		return new KafkaSpout(kafkaConfig);
	}
	
	private static KafkaSpout buildAlarmSpout(SpoutConfig spoutConfig) {
		SpoutConfig kafkaConfig = spoutConfig;
		kafkaConfig.scheme = new SchemeAsMultiScheme(new AlarmMessageScheme());
		return new KafkaSpout(kafkaConfig);
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			throw new RuntimeException("Filepaths Not Found");
		}
		stormConfFilePath = args[0];
		topologyConfFilePath = args[1];
		publisherConfFilePath = args[2];
		//alarmPublisherConfFilePath = args[3]; 
		execute();
	}
}