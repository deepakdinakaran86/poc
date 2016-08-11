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
package com.pcs.data.transformer.storm.topology;

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

import com.pcs.data.transformer.config.Configuration;
import com.pcs.data.transformer.enums.TopologyMode;
import com.pcs.data.transformer.storm.bolts.AlarmDataTransformBolt;
import com.pcs.data.transformer.storm.bolts.DeviceDataTransformBolt;
import com.pcs.data.transformer.storm.bolts.DeviceDataTransformNPublishBolt;
import com.pcs.data.transformer.storm.bolts.LiveFeedBolt;
import com.pcs.data.transformer.storm.scheme.AlarmMessageScheme;
import com.pcs.data.transformer.storm.scheme.MessageScheme;
import com.pcs.data.transformer.util.CustomTopologyBuilder;
import com.pcs.data.transformer.util.YamlUtils;

/**
 * Class responsible for creating storm topology
 *
 * @author pcseg171 (Aneesh Haridasan)
 * @date Mar 29, 2015
 * @since galaxy-1.0.0
 */
public class MessageTransformerTopology {

	private static String topologyConfFilePath;
	private static String stormConfFilePath;
	private static BrokerHosts brokerHosts;
	private static String zkRoot;
	private static String topologyReference;
	private static TopologyMode topologyMode;
	private static String transformDataTopic;
	private static String transformAndPublishDataTopic;
	private static String transformedDataTopic;
	private static String alarmTransformTopic;
	private static String alarmTransformedTopic;
	private static String deviceDataTransSpout;
	private static String deviceDataTransNPublishSpout;
	private static String alarmTransSpout;
	private static String deviceDataTransBolt;
	private static String deviceDataTransNPublishBolt;
	private static String alarmTransTransBolt;
	private static String liveFeedPushBolt;
	private static String liveFeedTopic;
	private static Long tupleEmitTimeout;
	private static int numberOfWorkers;
	private static int maxParallelism;
	private static long kafkaStartOffSet = -2l;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageTransformerTopology.class);
	private static String publisherConfFilePath;
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
			transformDataTopic = load.get(Configuration.TRANSFORM_DATA_TOPIC)
					.toString();
			transformAndPublishDataTopic = load.get(
					Configuration.TRANSFORM_AND_PUBLISH_DATA_TOPIC).toString();
			transformedDataTopic = load.get(
					Configuration.TRANSFORMED_DATA_TOPIC).toString();

			alarmTransformTopic = load.get(Configuration.ALARM_TRANSFORM_TOPIC)
					.toString();

			alarmTransformedTopic = load.get(
					Configuration.ALARM_TRANSFORMED_TOPIC).toString();

			zkRoot = load.get(Configuration.ZK_ROOT) == null ? "" : load.get(
					Configuration.ZK_ROOT).toString();
			topologyReference = load.get(Configuration.TOPOLOGY_REFERENCE)
					.toString();

			deviceDataTransSpout = load.get(
					Configuration.DEVICE_DATA_TRANS_SPOUT).toString();
			
			deviceDataTransNPublishSpout = load.get(
					Configuration.DEVICE_DATA_TRANS_N_PUBLISH_SPOUT).toString();

			alarmTransSpout = load.get(Configuration.ALARM_TRANS_SPOUT)
					.toString();

			deviceDataTransBolt = load
					.get(Configuration.DEVICE_DATA_TRANS_BOLT).toString();
			
			deviceDataTransNPublishBolt = load
					.get(Configuration.DEVICE_DATA_TRANS_N_PUBLISH_BOLT).toString();

			alarmTransTransBolt = load.get(Configuration.ALARM_TRANS_BOLT)
					.toString();
			
			liveFeedPushBolt = load.get(Configuration.LIVE_FEED_PUSH_BOLT)
					.toString();
			
			liveFeedTopic = load.get(Configuration.LIVE_FEED_STREAM).toString();

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

		// Kafka Spout to receive data from device_mqtt_messages
		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts,
				transformDataTopic, zkRoot, topologyReference);
		spoutConfig.stateUpdateIntervalMs = offsetUpdateIntervalMs;
		builder.setSpout(deviceDataTransSpout,
				buildSpout(spoutConfig, deviceDataTransSpout), maxParallelism);
		// Bolt to transform device data to message
		builder.setBolt(
				deviceDataTransBolt,
				new DeviceDataTransformBolt(publisherConfFilePath,
						transformedDataTopic)).shuffleGrouping(
				deviceDataTransSpout);

		// Kafka Spout to receive and publish data from device_mqtt_messages
		SpoutConfig transAndPublish = new SpoutConfig(brokerHosts,
				transformAndPublishDataTopic, zkRoot, topologyReference);
		builder.setSpout(deviceDataTransNPublishSpout,
				buildSpout(transAndPublish, deviceDataTransNPublishSpout), maxParallelism);
		// Bolt to transform device data to message
		builder.setBolt(
				deviceDataTransNPublishBolt,
				new DeviceDataTransformNPublishBolt(publisherConfFilePath,
						transformedDataTopic)).shuffleGrouping(
								deviceDataTransNPublishSpout);
		// Bolt to publish to Kafka's live-feed-stream topic
		builder.setBolt(liveFeedPushBolt,
				new LiveFeedBolt(publisherConfFilePath, liveFeedTopic))
				.shuffleGrouping(deviceDataTransNPublishBolt);

		// Kafka Spout to receive data from device_alarm_mqtt_messages
		SpoutConfig spoutConfig1 = new SpoutConfig(brokerHosts,
				alarmTransformTopic, zkRoot, topologyReference);
		builder.setSpout(alarmTransSpout,
				buildAlarmTransSpout(spoutConfig1, alarmTransSpout),
				maxParallelism);

		// Bolt to transform alarm data to alarm message
		builder.setBolt(
				alarmTransTransBolt,
				new AlarmDataTransformBolt(publisherConfFilePath,
						alarmTransformedTopic))
				.shuffleGrouping(alarmTransSpout);

		LOGGER.info("Topology Created with name {} with mode {}",
				topologyReference, topologyMode);
		return builder.createTopology();
	}

	private static KafkaSpout buildSpout(SpoutConfig spoutConfig,
			String spoutName) {

		SpoutConfig kafkaConfig = spoutConfig;
		kafkaConfig.forceFromStart = true;
		kafkaConfig.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
		kafkaConfig.scheme = new SchemeAsMultiScheme(new MessageScheme(
				spoutName));
		return new KafkaSpout(kafkaConfig);
	}

	private static KafkaSpout buildAlarmTransSpout(SpoutConfig spoutConfig,
			String spoutName) {
		SpoutConfig kafkaConfig = spoutConfig;
		kafkaConfig.forceFromStart = true;
		kafkaConfig.startOffsetTime = kafka.api.OffsetRequest.LatestTime();
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
		execute();
	}
}