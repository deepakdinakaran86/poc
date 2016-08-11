/**
 *
 */
package com.pcs.data.transformer.config;


/**
 * @author pcseg171
 *
 */
public abstract class Configuration {

	public static final String BROKER_HOSTS = "brokerHosts";
	public static final String ZK_ROOT = "zookeeperRoot";
	public static final String TOPOLOGY_REFERENCE = "topologyName";
	public static final String NO_OF_WORKERS = "numberOfWorkers";
	public static final String MAX_PARALLELISM = "maxParallelism";
	public static final String TOPOLOGY_MODE = "topologyMode";
	public static final String TOPOLOGY_EMIT_INTERVAL_MILLIS = "topologyEmitInterval";
	public static final String TRANSFORM_DATA_TOPIC = "transformDataTopic";
	public static final String TRANSFORM_AND_PUBLISH_DATA_TOPIC = "transformNPublishDataTopic";
	public static final String TRANSFORMED_DATA_TOPIC = "transformedDataTopic";
	public static final String ALARM_TRANSFORM_TOPIC = "alarmTransformTopic";
	public static final String ALARM_TRANSFORMED_TOPIC = "alarmTransformedTopic";
	public static final Object DEVICE_DATA_TRANS_SPOUT = "deviceDataTransSpout";
	public static final Object DEVICE_DATA_TRANS_N_PUBLISH_SPOUT = "deviceDataTransNPublishSpout";
	public static final Object ALARM_TRANS_SPOUT = "alarmTransSpout";
	public static final String DEVICE_DATA_TRANS_BOLT = "deviceDataTransBolt";
	public static final String DEVICE_DATA_TRANS_N_PUBLISH_BOLT = "deviceDataTransNPublishBolt";
	public static final String ALARM_TRANS_BOLT = "alarmTransBolt";
	public static final String LIVE_FEED_PUSH_BOLT = "livefeedPushBolt";
	public static final String LIVE_FEED_STREAM = "livefeedstream";
	public static final String DEVICE_DATA_PUBLISH_BOLT = "deviceDataPublishBolt";
	
	
	
	public static final String PERSIST_SPOUT_NAME = "persistSpoutName";
	
	
	
	
	public static final Object PARENT_SPOUT_MAX_PARALLELISM = "parentSpoutMaxParallelism";
	public static final Object PERSIST_BOLT_MAX_PARALLELISM = "persistBoltMaxParallelism";
	public static final Object TOPOLOGY_MAX_PARALLELISM = "topologyMaxParallelism";
	public static final Object KAFKA_START_OFFSET = "kafkaStartOffSet";
	
	
}
