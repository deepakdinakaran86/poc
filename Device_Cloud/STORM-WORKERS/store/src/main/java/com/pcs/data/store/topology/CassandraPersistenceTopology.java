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
import com.pcs.data.store.bean.CassandraConfig;
import com.pcs.data.store.bean.LatestDataRowMapper;
import com.pcs.data.store.bean.PhysicalQuantityMapper;
import com.pcs.data.store.scheme.CassandraMessagePersistScheme;
import com.pcs.data.store.topology.builder.CustomTopologyBuilder;
import com.pcs.data.store.topology.config.Configuration;
import com.pcs.data.store.topology.config.StoreStormConfig;
import com.pcs.data.store.topology.mode.TopologyMode;
import com.pcs.data.store.utils.YamlUtils;

public class CassandraPersistenceTopology {


	private static final Logger LOGGER = LoggerFactory.getLogger(CassandraPersistenceTopology.class);

	private static String topologyConfFilePath;
	private static StoreStormConfig stormConfig;
	private static BrokerHosts brokerHosts;
	private static List<String> topics;
	private static TopologyMode topologyMode;
	private static String keyspace;
	private static String udtName;

	private static Long tupleEmitTimeout;
	private static int numberOfWorkers;

	private static int topologyMaxParallelism;

	private static long kafkaStartOffSet = -2l;


	public static boolean execute(){

		initialize();
		boolean executionProgressing = false;
		try {
			buildTopology();
		} catch (Exception e) {
			LOGGER.error("Error Executing Topology", e);
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
			topics = (List<String>) topologyConfiguration.get(Configuration.MESSSAGE_TOPIC);
			keyspace = topologyConfiguration.get(Configuration.KEYSPACE).toString();
			udtName = topologyConfiguration.get(Configuration.UDTNAME).toString();
			
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
			LOGGER.error("Error in reading persistence topology configuration", e);
			throw new RuntimeException(e);
		}
	}


	private static void buildTopology(){
		
		try {
			for (String topic : topics) {
				TridentTopology topology = new TridentTopology();
				TransactionalTridentKafkaSpout dataMessageSpout = buildKafkaSpout(topic);
				Stream deviceMessageStream = topology.newStream(CassandraMessagePersistScheme.PERSIST_DATA, dataMessageSpout);
				CassandraCqlStateFactory CQLstateFactory = new CassandraCqlStateFactory(ConsistencyLevel.ONE);
//				SimpleStateFactory stateFactory = new SimpleStateFactory();
				
				TridentState state = deviceMessageStream.partitionBy(new Fields(CassandraMessagePersistScheme.DEVICE_MESSAGE_KEY)).
						partitionBy(new Fields(CassandraMessagePersistScheme.SOURCE_ID))
						.partitionBy(new Fields(CassandraMessagePersistScheme.DATE)).
						partitionPersist(CQLstateFactory, new Fields(CassandraMessagePersistScheme.PERSIST_DATA), new CassandraCqlStateUpdater<>(new PhysicalQuantityMapper(keyspace,udtName)));
				
				TridentState latestState = deviceMessageStream.partitionBy(new Fields(CassandraMessagePersistScheme.SOURCE_ID))
						.partitionBy(new Fields(CassandraMessagePersistScheme.DISPLAY_NAME))
						.partitionPersist(CQLstateFactory, new Fields(CassandraMessagePersistScheme.PERSIST_DATA), new CassandraCqlStateUpdater<>(new LatestDataRowMapper(keyspace)));
				
				CustomTopologyBuilder.build(stormConfig, topology.build(),
						topologyMode, topic+"topology", tupleEmitTimeout,
						numberOfWorkers, topologyMaxParallelism);
			}
		} catch (Exception e) {
			LOGGER.error("Error in building topology",e);
		}
		
		
		
	}



	private static TransactionalTridentKafkaSpout buildKafkaSpout(String topic) {
		TridentKafkaConfig kafkaConfig = new TridentKafkaConfig(brokerHosts, topic);

		kafkaConfig.startOffsetTime = kafkaStartOffSet;
		LOGGER.info("@@@@@@ .......KafkaConfig.startOffsetTime " + kafkaConfig.startOffsetTime);
		if (kafkaStartOffSet == -2) {
			kafkaConfig.forceFromStart = true;
		} else if (kafkaStartOffSet == -1) {
			kafkaConfig.forceFromStart = false;
		} else {
			throw new RuntimeException(
					"Invalid kafkaStartOffSet,Mention Either -1 or -2");
		}

		kafkaConfig.scheme = new SchemeAsMultiScheme(new CassandraMessagePersistScheme());
		kafkaConfig.socketTimeoutMs = 60000;

		return new TransactionalTridentKafkaSpout(kafkaConfig);
	}


	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			throw new RuntimeException("Filepaths Not Found");
		}
		stormConfig = StoreStormConfig.getInstance(args[0]);//"Config//config.yaml";
		CassandraConfig.TRIDENT_CASSANDRA_CLUSTER_NAME = stormConfig.getClusterName();
		CassandraConfig.TRIDENT_CASSANDRA_LOCAL_DATA_CENTER_NAME = stormConfig.getDataCenter();
		CassandraConfig.TRIDENT_CASSANDRA_CQL_HOSTS = stormConfig.getCassandraHosts();

		topologyConfFilePath = args[1];//"Config//persistencetopology.yaml";
		execute();
	}
}
