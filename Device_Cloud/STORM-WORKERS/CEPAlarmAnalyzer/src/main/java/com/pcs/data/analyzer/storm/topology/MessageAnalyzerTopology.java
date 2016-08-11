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

import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.KafkaUtils;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

import com.pcs.data.analyzer.config.Configuration;
import com.pcs.data.analyzer.enums.TopologyMode;
import com.pcs.data.analyzer.storm.bolts.AlarmMessageConversionBolt;
import com.pcs.data.analyzer.storm.scheme.MessageScheme;
import com.pcs.data.analyzer.util.CustomTopologyBuilder;
import com.pcs.data.analyzer.util.YamlUtils;

/**
 * Class responsible for creating storm topology
 *
 */
public class MessageAnalyzerTopology {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageAnalyzerTopology.class);

	// yaml file configuration.
	private static String topologyConfFilePath;
	private static String stormConfFilePath;
	private static String publisherConfFilePath;
	// zoo keeper references.
	private static BrokerHosts brokerHosts;
	private static String zkRoot;
	private static String topologyReference;
	private static TopologyMode topologyMode;
	// Spout and bolt name.
	private static String spoutName;
	private static String analyzerBoltName;
	// Topics
	private static String cepOutTopic;
	private static String alarmMessageTopic;

	private static Long tupleEmitTimeout;
	private static int numberOfWorkers;
	private static int maxParallelism;
	private static long kafkaStartOffSet = -2l;
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

			HashMap<Object, Object> load = YamlUtils.copyYamlFromFile(HashMap.class, topologyConfFilePath);

			brokerHosts = new ZkHosts(load.get(Configuration.BROKER_HOSTS).toString());

			zkRoot = load.get(Configuration.ZK_ROOT) == null ? "" : load.get(
					Configuration.ZK_ROOT).toString();

			topologyReference = load.get(Configuration.TOPOLOGY_REFERENCE)
					.toString();

			spoutName = load.get(Configuration.PARENT_SPOUT_NAME).toString();

			analyzerBoltName = load.get(Configuration.ANALYZER_BOLT_NAME_1).toString();

			cepOutTopic = load.get(Configuration.CEP_OUT_TOPIC).toString();

			alarmMessageTopic = load.get(Configuration.ALARM_OUTPUT_TOPIC).toString();

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

		// Kafka Spout to receive data from cep out topic.
		builder.setSpout(spoutName, buildKafkaSpout(), maxParallelism);
		// Bolt to Convert and Publish to Kafka's alarm-message Topic
		builder.setBolt(analyzerBoltName,
				new AlarmMessageConversionBolt(alarmMessageTopic,  publisherConfFilePath))
				.shuffleGrouping(spoutName);

		LOGGER.info("Topology Created with name {} with mode {}", topologyReference, topologyMode);
		return builder.createTopology();
	}

	/**
	 * To build KafkaSpout used in the topology
	 *
	 * @return
	 */
	private static KafkaSpout buildKafkaSpout() {
		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts,
				cepOutTopic, zkRoot, topologyReference);
		spoutConfig.startOffsetTime = kafkaStartOffSet;
		spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme(spoutName));
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
	private static KafkaSpout buildSpout(SpoutConfig spoutConfig, String spoutName) {
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

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			throw new RuntimeException("Filepaths Not Found");
		}
		stormConfFilePath = args[0];
		topologyConfFilePath = args[1];
		publisherConfFilePath = args[2];
		execute();
	}
}