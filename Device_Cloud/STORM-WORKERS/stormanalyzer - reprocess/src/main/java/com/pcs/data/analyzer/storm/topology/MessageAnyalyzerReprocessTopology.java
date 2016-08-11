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
import com.pcs.data.analyzer.storm.bolts.AlarmPersistBolt;
import com.pcs.data.analyzer.storm.bolts.AlarmPublishBolt;
import com.pcs.data.analyzer.storm.bolts.DataAnalyzeBolt;
import com.pcs.data.analyzer.storm.bolts.DataAnalyzeNPublishBolt;
import com.pcs.data.analyzer.storm.bolts.LiveFeedBolt;
import com.pcs.data.analyzer.storm.bolts.LivePublishBolt;
import com.pcs.data.analyzer.storm.bolts.PublishBolt;
import com.pcs.data.analyzer.storm.scheme.AlarmMessageScheme;
import com.pcs.data.analyzer.storm.scheme.MessageScheme;
import com.pcs.data.analyzer.util.CustomTopologyBuilder;
import com.pcs.data.analyzer.util.YamlUtils;

/**
 * Class responsible for creating storm topology
 *
 * @author pcseg171 (Aneesh Haridasan)
 * @date Mar 29, 2015
 * @since galaxy-1.0.0
 */
public class MessageAnyalyzerReprocessTopology {

	private static String topologyConfFilePath;
	private static String stormConfFilePath;
	private static BrokerHosts brokerHosts;
	private static String analyzeDataTopic;
	private static String analyzePublishDataTopic;
	private static String zkRoot;
	private static String topologyReference;
	private static TopologyMode topologyMode;
	private static String parentSpoutName;
	private static String pubAnalyzeSpoutName;
	private static String livePublishSpoutName;
	private static String alarmPublishSpoutName;
	private static String outputTopicName;
	private static String analyzerBoltName1;
	private static String analyzerBoltName2;
	private static String liveFeedPushBoltName;
	private static String liveDataPushBoltName;
	private static String alarmPushBoltName;
	private static String alarmPersistBoltName;
	private static String liveFeedTopic;
	private static String alarmFeedTopic;
	private static String datastoreFeedSpoutName;
	private static String datastoreFeedTopic;
	private static String datastoreFeedBoltName;
	private static Long tupleEmitTimeout;
	private static int numberOfWorkers;
	private static int maxParallelism;
	private static long kafkaStartOffSet = -2l;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageAnyalyzerReprocessTopology.class);
	private static String publisherConfFilePath;
	private static String alarmPublisherConfFilePath;
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
			CustomTopologyBuilder.build(stormConfFilePath, buildTopology(),
					topologyMode, topologyReference, tupleEmitTimeout,
					numberOfWorkers, maxParallelism);
			;
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
			analyzeDataTopic = load.get(Configuration.ANALYZE_DATA_TOPIC)
					.toString();
			analyzePublishDataTopic = load.get(
					Configuration.ANALYZE_PUBLISH_DATA_TOPIC).toString();
			zkRoot = load.get(Configuration.ZK_ROOT) == null ? "" : load.get(
					Configuration.ZK_ROOT).toString();
			topologyReference = load.get(Configuration.TOPOLOGY_REFERENCE)
					.toString();
			parentSpoutName = load.get(Configuration.PARENT_SPOUT_NAME)
					.toString();

			pubAnalyzeSpoutName = load.get(Configuration.PUBLISH_ANALYZE_SPOUT)
					.toString();

			livePublishSpoutName = load.get(Configuration.LIVE_PUBLISH_SPOUT)
					.toString();

			alarmPublishSpoutName = load.get(Configuration.ALARM_PUBLISH_SPOUT)
					.toString();

			outputTopicName = load.get(Configuration.OUTPUT_TOPIC_NAME)
					.toString();

			analyzerBoltName1 = load.get(Configuration.ANALYZER_BOLT_NAME_1)
					.toString();

			analyzerBoltName2 = load.get(Configuration.ANALYZER_BOLT_NAME_2)
					.toString();

			liveFeedTopic = load.get(Configuration.LIVE_FEED_STREAM).toString();

			alarmFeedTopic = load.get(Configuration.ALARM_FEED_STREAM)
					.toString();

			liveFeedPushBoltName = load.get(Configuration.LIVE_FEED_PUSH_BOLT)
					.toString();

			liveDataPushBoltName = load.get(
					Configuration.LIVE_DATA_PUBLISH_BOLT).toString();

			alarmPushBoltName = load.get(Configuration.ALARM_PUBLISH_BOLT)
					.toString();
			
			alarmPersistBoltName = load.get(Configuration.ALARM_PERSIST_BOLT)
					.toString();

			datastoreFeedSpoutName = load.get(
					Configuration.DATASTORE_FEED_SPOUT).toString();

			datastoreFeedTopic = load.get(Configuration.DATASTORE_FEED_STREAM)
					.toString();

			datastoreFeedBoltName = load.get(Configuration.DATASTORE_FEED_BOLT)
					.toString();

			Object topolgyMode = load.get(Configuration.TOPOLOGY_MODE);

			try {
				topologyMode = topolgyMode == null ? TopologyMode.LOCAL
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

			Object maxParallelismObj = load
					.get(Configuration.TOPOLOGY_MAX_PARALLELISM);
			maxParallelism = maxParallelismObj != null ? Integer
					.parseInt(maxParallelismObj.toString()) : null;

			Object kafkaStartOffSetObj = load
					.get(Configuration.KAFKA_START_OFFSET);
			kafkaStartOffSet = kafkaStartOffSetObj != null ? Long
					.parseLong(kafkaStartOffSetObj.toString()) : -2;
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

		/*
		// Kafka Spout to receive data from intermediate-message - No Publish
		builder.setSpout(parentSpoutName, buildKafkaSpout(), maxParallelism);
		// Bolt to Analyze and Publish to Kafka's analyzed-message Topic
		builder.setBolt(analyzerBoltName1,
				new DataAnalyzeBolt(publisherConfFilePath, outputTopicName))
				.shuffleGrouping(parentSpoutName);

		// Kafka Spout to receive data from intermediate-publish-message
		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts,
				analyzePublishDataTopic, zkRoot, topologyReference);
		spoutConfig.stateUpdateIntervalMs = offsetUpdateIntervalMs;
		builder.setSpout(pubAnalyzeSpoutName,
				buildSpout(spoutConfig, pubAnalyzeSpoutName), maxParallelism);
		// Bolt to analyze and Publish to Kafka's analyzed-message Topic
		builder.setBolt(
				analyzerBoltName2,
				new DataAnalyzeNPublishBolt(publisherConfFilePath,
						outputTopicName)).shuffleGrouping(pubAnalyzeSpoutName);
		// Bolt to publish to Kafka's live-feed-stream topic
		builder.setBolt(liveFeedPushBoltName,
				new LiveFeedBolt(publisherConfFilePath, liveFeedTopic))
				.shuffleGrouping(analyzerBoltName2);

		// Kafka Spout to receive data from live-feed-stream topic
		SpoutConfig spoutConfig1 = new SpoutConfig(brokerHosts, liveFeedTopic,
				zkRoot, topologyReference);
		spoutConfig1.stateUpdateIntervalMs = offsetUpdateIntervalMs;
		builder.setSpout(livePublishSpoutName,
				buildSpout(spoutConfig1, livePublishSpoutName), maxParallelism);
		// Bolt to publish live data to all datasource contexts
		builder.setBolt(liveDataPushBoltName,
				new LivePublishBolt(publisherConfFilePath)).shuffleGrouping(
				livePublishSpoutName);

		// Kafka Spout to read data from alarm-message topic
		SpoutConfig spoutConfig2 = new SpoutConfig(brokerHosts, alarmFeedTopic,
				zkRoot, topologyReference);
		spoutConfig2.stateUpdateIntervalMs = offsetUpdateIntervalMs;
		builder.setSpout(alarmPublishSpoutName, buildAlarmSpout(spoutConfig2),
				maxParallelism);
		// Bolt to publish alarm to all datasource contexts
		builder.setBolt(alarmPushBoltName,
				new AlarmPublishBolt(publisherConfFilePath)).shuffleGrouping(
				alarmPublishSpoutName);
		builder.setBolt(alarmPersistBoltName,
				new AlarmPersistBolt(alarmPublisherConfFilePath)).shuffleGrouping(
				alarmPublishSpoutName);
		*/
	
		// Kafka Spout to read data from analyzed-message topic
		SpoutConfig spoutConfig3 = new SpoutConfig(brokerHosts,
				outputTopicName, zkRoot, topologyReference);
		spoutConfig3.stateUpdateIntervalMs = offsetUpdateIntervalMs;
		// spoutConfig3.useStartOffsetTimeIfOffsetOutOfRange = false;
		builder.setSpout(datastoreFeedSpoutName,
				buildSpout(spoutConfig3, datastoreFeedSpoutName),
				maxParallelism);
		// Bolt to publish data to data-store kafka topic
		builder.setBolt(datastoreFeedBoltName,
				new PublishBolt(publisherConfFilePath, datastoreFeedTopic))
				.shuffleGrouping(datastoreFeedSpoutName);

		LOGGER.info("Topology Created with name {} with mode {}",
				topologyReference, topologyMode);
		return builder.createTopology();
	}

	/**
	 * To build KafkaSpout used in the topology
	 *
	 * @return
	 */
	private static KafkaSpout buildKafkaSpout() {
		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts,
				analyzeDataTopic, zkRoot, topologyReference);
		spoutConfig.startOffsetTime = kafkaStartOffSet;
		spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme(
				parentSpoutName));
		return new KafkaSpout(spoutConfig);
	}

	/**
	 * @param spoutConfig
	 * @param spoutName
	 * @return
	 */
	/**
	 * @param spoutConfig
	 * @param spoutName
	 * @return
	 */
	/**
	 * @param spoutConfig
	 * @param spoutName
	 * @return
	 */
	private static KafkaSpout buildSpout(SpoutConfig spoutConfig,
			String spoutName) {
		/*
		 * The behavior of the Kafka spout is to replay the failed tuples, and
		 * all tuples after the failed one. This is because the Kafka client API
		 * is essentially a batch API (download N bytes of messages starting
		 * with X). In order to replay only failed tuples, you would need to
		 * track (and store) the offsets of individual tuples that hadn't been
		 * acknowledged yet. This is technically possible, but hasn't ever been
		 * put into storm (to achieve without killing your storm throughput,
		 * offset tracking and management would need to be high performance)
		 */

		SpoutConfig kafkaConfig = spoutConfig;
		/*
		 * forceFromStart=true will tell the spout to start reading from
		 * whatever is set in startOffsetTime (available options are the
		 * earliest offset or the latest offset). If false, the spout will look
		 * for an offset in ZK (if any) and start from there. If there's no
		 * offset in ZK, it will start from the end
		 */

		kafkaConfig.forceFromStart = true;
		kafkaConfig.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
		kafkaConfig.scheme = new SchemeAsMultiScheme(new MessageScheme(
				spoutName));
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
		alarmPublisherConfFilePath = args[3]; 
		execute();
	}
}