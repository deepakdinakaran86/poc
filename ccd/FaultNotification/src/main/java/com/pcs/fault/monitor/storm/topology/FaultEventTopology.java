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
package com.pcs.fault.monitor.storm.topology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;

import com.pcs.fault.monitor.bean.TopologyConfig;
import com.pcs.fault.monitor.scheme.FaultResponseMessageScheme;
import com.pcs.fault.monitor.scheme.MessageScheme;
import com.pcs.fault.monitor.storm.bolts.EmailSender;
import com.pcs.fault.monitor.storm.bolts.FaultAnalyzeBolt;
import com.pcs.fault.monitor.storm.bolts.FaultEventPersistBolt;
import com.pcs.fault.monitor.storm.bolts.FaultRespNotifierBolt;
import com.pcs.fault.monitor.util.CustomTopologyBuilder;
import com.pcs.fault.monitor.util.YamlUtils;

/**
 * This class is responsible for running the fault monitor topology
 * 
 * @author pcseg129(Seena Jyothish) Jan 20, 2016
 */
public class FaultEventTopology {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(FaultEventTopology.class);

	private static String stormConfFilePath;
	private static String topologyConfFilePath;
	private static TopologyConfig tplgConfig;
	private static int offsetUpdateIntervalMs = 2000;

	public static boolean execute() {
		initialize();
		boolean initialized = false;
		try {
			TopologyConfig config = new TopologyConfig();
			CustomTopologyBuilder.build(stormConfFilePath, buildTopology(),
					tplgConfig);
			initialized = true;
		} catch (Exception e) {
			LOGGER.error("Error Executing Topology", e);
		}
		return initialized;
	}

	private static void initialize() {
		try {
			tplgConfig = YamlUtils.copyYamlFromFile(TopologyConfig.class,
					topologyConfFilePath);
		} catch (Exception e) {
			LOGGER.error("Error in reading topology configuration", e);
			throw new RuntimeException(e);
		}
	}

	private static StormTopology buildTopology() {
		TopologyBuilder builder = new TopologyBuilder();

		BrokerHosts brokerHosts = new ZkHosts(tplgConfig.getBrokerHosts());

		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts,
		        tplgConfig.getFaultMessageTopic(),
		        tplgConfig.getZookeeperRoot(), tplgConfig.getTopologyName());
		spoutConfig.stateUpdateIntervalMs = offsetUpdateIntervalMs;
		
		SpoutConfig faultRespSpoutConfig = new SpoutConfig(brokerHosts,
		        tplgConfig.getFaultResponseTopic(),
		        tplgConfig.getZookeeperRoot(), tplgConfig.getTopologyName());
		
		spoutConfig.stateUpdateIntervalMs = offsetUpdateIntervalMs;
		faultRespSpoutConfig.stateUpdateIntervalMs = offsetUpdateIntervalMs;
		
		builder.setSpout(tplgConfig.getFaultReadSpoutName(),
		        buildSpout(spoutConfig, tplgConfig.getFaultReadSpoutName()),
		        Integer.parseInt(tplgConfig.getTopologyMaxParallelism()));
		
		builder.setBolt(tplgConfig.getFaultSendBoltName(),
				new FaultAnalyzeBolt()).shuffleGrouping(tplgConfig.getFaultReadSpoutName());
		
		builder.setBolt(tplgConfig.getFaultPersistBoltName(),
				new FaultEventPersistBolt()).shuffleGrouping(tplgConfig.getFaultSendBoltName());
		
		builder.setSpout(tplgConfig.getFaultResponseSpoutName(),
				buildResponseSpout(faultRespSpoutConfig, tplgConfig.getFaultResponseSpoutName()),
		        Integer.parseInt(tplgConfig.getTopologyMaxParallelism()));
		
		builder.setBolt(tplgConfig.getFaultRespNotifierBoltName(),
				new FaultRespNotifierBolt()).shuffleGrouping(tplgConfig.getFaultResponseSpoutName());
		
		builder.setBolt(tplgConfig.getEmailSenderBoltName(), 
				new EmailSender()).
				shuffleGrouping(tplgConfig.getFaultSendBoltName(),"FaultNotificationStream").
				shuffleGrouping(tplgConfig.getFaultRespNotifierBoltName(),"FaultResponseNotificationStream");
		

		return builder.createTopology();
	}

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
	
	private static KafkaSpout buildResponseSpout(SpoutConfig spoutConfig,
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
		kafkaConfig.scheme = new SchemeAsMultiScheme(new FaultResponseMessageScheme(
		        spoutName));
		return new KafkaSpout(kafkaConfig);
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			throw new RuntimeException(
			        "Not enough arguments , expecting 2 found " + args.length);
		}
		stormConfFilePath = args[0];
		topologyConfFilePath = args[1];

		execute();
	}

}
