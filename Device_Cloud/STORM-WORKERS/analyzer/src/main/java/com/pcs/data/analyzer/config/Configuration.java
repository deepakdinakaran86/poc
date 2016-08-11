/**
 *
 */
package com.pcs.data.analyzer.config;


/**
 * @author pcseg171
 *
 */
public abstract class Configuration {

	public static final String BROKER_HOSTS = "brokerHosts";
	public static final String MESSSAGE_TOPIC = "messageTopicName";
	public static final String ZK_ROOT = "zookeeperRoot";
	public static final String TOPOLOGY_REFERENCE = "topologyName";
	public static final String PARENT_SPOUT_NAME = "parentSpoutName";
	public static final String PERSIST_SPOUT_NAME = "persistSpoutName";
	public static final String TOPOLOGY_MODE = "topologyMode";
	public static final String TOPOLOGY_EMIT_INTERVAL_MILLIS = "topologyEmitInterval";
	public static final String NO_OF_WORKERS = "numberOfWorkers";
	public static final String MAX_PARALLELISM = "maxParallelism";
	public static final String OUTPUT_TOPIC_NAME = "outputTopicName";
	public static final String ANALYZER_BOLT_NAME = "analyzerBoltName";
	public static final String ANALYZER_BOLT_NAME_1 = "analyzerBoltName1";
	public static final String ANALYZER_BOLT_NAME_2 = "analyzerBoltName2";
	public static final String PERSIST_BOLT = "persistBolt";
	public static final String EVENT_CANDIDATE_BOLT="eventCandidateBolt";
	public static final String EVENT_ANALYZER_PUBLISHER_BOLT = "eventAnalyzerPublisherBolt";
	public static final String PUSH_SERVICE_PUBLISHER_BOLT = "pushServicePublisherBolt";
	public static final Object EVENT_ANALYZER_QUEUE_NAME = "eventAnalyzerQueueName";
	public static final Object PUSH_SERVICE_TOPIC_NAME = "pushServiceTopicName";
	public static final Object PARENT_SPOUT_MAX_PARALLELISM = "parentSpoutMaxParallelism";
	public static final Object PERSIST_BOLT_MAX_PARALLELISM = "persistBoltMaxParallelism";
	public static final Object EVENT_CANDIDATE_BOLT_MAX_PARALLELISM = "eventCandidateBoltMaxParallelism";
	public static final Object EVENT_ANALYZER_PUBLISHER_BOLT_MAX_PARALLELISM = "eventAnalyzerPublisherBoltMaxParallelism";
	public static final Object PUSH_SERVICE_PUBLISHER_BOLT_MAX_PARALLELISM = "pushServicePublisherBoltMaxParallelism";
	public static final Object TOPOLOGY_MAX_PARALLELISM = "topologyMaxParallelism";
	public static final Object KAFKA_START_OFFSET = "kafkaStartOffSet";
	public static final Object PUBLISH_ANALYZE_SPOUT = "publishAnalyzeSpout";
	public static final Object PUBLISHER_BOLT = "publisherBolt";
	public static final String ANALYZE_DATA_TOPIC = "analyzeDataTopic";
	public static final String ANALYZE_PUBLISH_DATA_TOPIC = "analyzePublishTopic";
	public static final String LIVE_FEED_STREAM = "livefeedstream";
	public static final String LIVE_PUBLISH_SPOUT = "livepublishSpout";
	public static final String LIVE_FEED_PUSH_BOLT = "livefeedPushBolt";
	public static final String LIVE_DATA_PUBLISH_BOLT = "livepublishBolt";
	public static final String ALARM_FEED_STREAM = "alarmFeedstream";
	public static final String ALARM_PUBLISH_SPOUT = "alarmPublishSpout";
	public static final String ALARM_PUBLISH_BOLT = "alarmPublishBolt";
	public static final String ALARM_PERSIST_BOLT = "alarmPersistBolt";
	public static final String ALARM_PERSIST_TOPIC = "alarmPersistTopic";
	public static final String DATASTORE_FEED_SPOUT = "datastoreFeedSpout";
	public static final String DATASTORE_FEED_STREAM = "datastoreFeedStream";
	public static final String DATASTORE_FEED_BOLT = "datastoreFeedBolt";
	public static final String LATEST_DATA_STORE = "latest-parameter-data";
}
