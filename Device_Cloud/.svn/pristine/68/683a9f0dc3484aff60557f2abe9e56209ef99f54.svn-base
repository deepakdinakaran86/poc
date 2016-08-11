package com.pcs.data.store.topology;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.kafka.BrokerHosts;
import storm.kafka.ZkHosts;
import storm.kafka.trident.TransactionalTridentKafkaSpout;
import storm.kafka.trident.TridentKafkaConfig;
import storm.trident.Stream;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.tuple.Fields;

import com.datastax.driver.core.ConsistencyLevel;
import com.hmsonline.trident.cql.CassandraCqlStateFactory;
import com.hmsonline.trident.cql.CassandraCqlStateUpdater;
import com.pcs.data.store.bean.DeviceActivityRowMapper;
import com.pcs.data.store.bean.DeviceTransitionRowMapper;
import com.pcs.data.store.scheme.DeviceActivityScheme;
import com.pcs.data.store.scheme.HeartBeatMessageScheme;
import com.pcs.data.store.topology.builder.CustomTopologyBuilder;
import com.pcs.data.store.topology.config.CassandraConfig;
import com.pcs.data.store.topology.config.Configuration;
import com.pcs.data.store.topology.config.StoreStormConfig;
import com.pcs.data.store.topology.config.TopologyMode;
import com.pcs.data.store.utils.YamlUtils;

/**
 * 
 * @author pcseg369
 *
 */
public class DeviceActivityPersistTopology {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceActivityPersistTopology.class);

	private static String topologyConfFilePath;

	private static StoreStormConfig stormConfig;

	private static BrokerHosts brokerHosts;

	private static String topic;
	
	private static String deviceTransitionTopic;

	private static TopologyMode topologyMode;

	private static String keyspace;

	private static Long tupleEmitTimeout;

	private static int numberOfWorkers;

	private static int topologyMaxParallelism;

	private static long kafkaStartOffSet = -2l;

	// Time To live in minutes.
	private static int timeToLive = 15;


	public static boolean execute(){

		initialize();
		boolean executionProgressing = false;
		try {
			buildTopology();
		} catch (Exception e) {
			LOGGER.error("DeviceActivityPersistTopology | Error Executing Topology", e);
		}

		return executionProgressing;
	}


	@SuppressWarnings("unchecked")
	private static void initialize() {
		try {
			HashMap<Object, Object> topologyConfiguration = YamlUtils.copyYamlFromFile(
					HashMap.class, topologyConfFilePath);
			brokerHosts = new ZkHosts(topologyConfiguration.get(Configuration.BROKER_HOSTS)
					.toString());
			topic = (String) topologyConfiguration.get(Configuration.MESSSAGE_TOPIC);
			deviceTransitionTopic =  (String) topologyConfiguration.get(Configuration.DEVICE_TRANSITION_TOPIC);
			keyspace = topologyConfiguration.get(Configuration.KEYSPACE).toString();
			timeToLive = (int )  topologyConfiguration.get(Configuration.TIME_TO_LIVE);

			Object topologyMaxParallelismObj = topologyConfiguration.get(Configuration.TOPOLOGY_MAX_PARALLELISM);

			topologyMaxParallelism = topologyMaxParallelismObj != null
					? Integer.parseInt(topologyMaxParallelismObj.toString())
							: 1;

					Object topolgyMode = topologyConfiguration.get(Configuration.TOPOLOGY_MODE);
					try {
						topologyMode = topolgyMode == null
								? TopologyMode.LOCAL
										: TopologyMode.valueOf(topolgyMode.toString());
					} catch (Exception e) {
						LOGGER.error("Error in reading TopologyMode", e);
						throw new RuntimeException(e);
					}
					Object topologyEmitInterval = topologyConfiguration
							.get(Configuration.TOPOLOGY_EMIT_INTERVAL_MILLIS);
					tupleEmitTimeout = topologyEmitInterval != null ? Long
							.parseLong(topologyEmitInterval.toString()) : null;

							Object noOfWorkers = topologyConfiguration.get(Configuration.NO_OF_WORKERS);
							numberOfWorkers = noOfWorkers != null ? Integer
									.parseInt(noOfWorkers.toString()) : null;

									Object kafkaStartOffSetObj = topologyConfiguration
											.get(Configuration.KAFKA_START_OFFSET);
									kafkaStartOffSet = kafkaStartOffSetObj != null ? Long
											.parseLong(kafkaStartOffSetObj.toString()) : -2;

		} catch (Exception e) {
			LOGGER.error("DeviceActivityPersistTopology | Error in reading persistence topology configuration", e);
			throw new RuntimeException(e);
		}
	}


	private static void buildTopology(){

		try {
			TridentTopology topology = new TridentTopology();

			TransactionalTridentKafkaSpout dataMessageSpout = buildKafkaSpout(topic);
			TransactionalTridentKafkaSpout deviceStatusSpout = buildKafkaSpoutForDeviceStatus(deviceTransitionTopic);

			Stream deviceMessageStream = topology.newStream(HeartBeatMessageScheme.PERSIST_DATA, dataMessageSpout);

			CassandraCqlStateFactory CQLstateFactory = new CassandraCqlStateFactory(ConsistencyLevel.ONE);

			TridentState latestState = deviceMessageStream.partitionBy(new Fields(HeartBeatMessageScheme.SOURCE_ID))
					.partitionPersist(CQLstateFactory, new Fields(HeartBeatMessageScheme.PERSIST_DATA), 
							new CassandraCqlStateUpdater<>(new DeviceActivityRowMapper(keyspace, timeToLive)));
			
			
			/*Stream deviceTransitionStream = topology.newStream(DeviceActivityScheme.DEVICE_LATEST_STATUS, deviceStatusSpout);
			TridentState deviceTransition = deviceTransitionStream.partitionBy(new Fields(DeviceActivityScheme.SOURCE_ID))
					.partitionPersist(CQLstateFactory, new Fields(DeviceActivityScheme.DEVICE_LATEST_STATUS), 
							new CassandraCqlStateUpdater<>(new DeviceTransitionRowMapper(keyspace)));*/

			CustomTopologyBuilder.build(stormConfig, topology.build(), topologyMode, topic + "topology", tupleEmitTimeout,
					numberOfWorkers, topologyMaxParallelism);

		} catch (Exception e) {
			LOGGER.error(" buildTopology | Error in building topology",e);
		}

	}

	private static TransactionalTridentKafkaSpout buildKafkaSpout(String topic) {

		TridentKafkaConfig kafkaConfig = new TridentKafkaConfig(brokerHosts, topic);

		kafkaConfig.startOffsetTime = kafkaStartOffSet;
		LOGGER.info("DeviceActivityPersistTopology | KafkaConfig.startOffsetTime " + kafkaConfig.startOffsetTime);

		if (kafkaStartOffSet == -2) {
			kafkaConfig.forceFromStart = true;
		} else if (kafkaStartOffSet == -1) {
			kafkaConfig.forceFromStart = false;
		} else {
			throw new RuntimeException(
					"Invalid kafkaStartOffSet,Mention Either -1 or -2");
		}

		kafkaConfig.scheme = new SchemeAsMultiScheme(new HeartBeatMessageScheme());
		kafkaConfig.socketTimeoutMs = 60000;

		return new TransactionalTridentKafkaSpout(kafkaConfig);
	}
	
	private static TransactionalTridentKafkaSpout buildKafkaSpoutForDeviceStatus(String topic) {

		TridentKafkaConfig kafkaConfig = new TridentKafkaConfig(brokerHosts, topic);

		kafkaConfig.startOffsetTime = kafkaStartOffSet;

		if (kafkaStartOffSet == -2) {
			kafkaConfig.forceFromStart = true;
		} else if (kafkaStartOffSet == -1) {
			kafkaConfig.forceFromStart = false;
		} else {
			throw new RuntimeException(
					"Invalid kafkaStartOffSet,Mention Either -1 or -2");
		}

		kafkaConfig.scheme = new SchemeAsMultiScheme(new DeviceActivityScheme());
		kafkaConfig.socketTimeoutMs = 60000;

		return new TransactionalTridentKafkaSpout(kafkaConfig);
	}


	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			throw new RuntimeException("Filepaths Not Found");
		}

		stormConfig = StoreStormConfig.getInstance(args[0]);               //"Config//deviceactivitystorestormconfig.yaml";

		CassandraConfig.TRIDENT_CASSANDRA_CLUSTER_NAME = stormConfig.getClusterName();
		CassandraConfig.TRIDENT_CASSANDRA_LOCAL_DATA_CENTER_NAME = stormConfig.getDataCenter();
		CassandraConfig.TRIDENT_CASSANDRA_CQL_HOSTS = stormConfig.getCassandraHosts();

		topologyConfFilePath = args[1];                                    //"Config//deviceactivitypersisttopology.yaml";

		execute();
	}
}
