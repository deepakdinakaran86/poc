package com.pcs.analytics.flinktests.kafka.source;

import java.util.HashMap;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.GroupedDataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer082;
import org.apache.flink.streaming.connectors.kafka.KafkaSink;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.analytics.flinktests.beans.DeviceSnapshot;
import com.pcs.analytics.flinktests.beans.Parameter;
import com.pcs.analytics.flinktests.kafka.source.bean.DataIngestionBean;
import com.pcs.analytics.flinktests.kafka.source.schema.DeviceSnapshotSchema;
import com.pcs.analytics.flinktests.kafka.source.schema.IngestionSchema;

public class KafaStreamOne {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(KafaStreamOne.class);

	private static HashMap<String,String> deviceDataMap = new HashMap<String,String>();
	public static void main(String[] args) throws Exception {

		// create execution environment
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// parse user parameters
		final ParameterTool parameterTool = ParameterTool.fromArgs(args);

		final DataStream<DataIngestionBean> messageStream = env.addSource(new FlinkKafkaConsumer082<>(parameterTool.getRequired("topic"), 
				new IngestionSchema(), parameterTool.getProperties()));

		GroupedDataStream<DataIngestionBean> timeIndexed = messageStream.groupBy("deviceId").groupBy("deviceTime");
		timeIndexed.filter(new FilterFunction<DataIngestionBean>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean filter(DataIngestionBean bean) throws Exception {
				String deviceSnapshotStr = deviceDataMap.get(bean.getDeviceId());
				DeviceSnapshot deviceSnapshot = null;
				if(deviceSnapshotStr != null){
					deviceSnapshot = OBJECT_MAPPER.readValue(deviceSnapshotStr,DeviceSnapshot.class);	
					if(deviceSnapshot.getDeviceTime() <= bean.getDeviceTime()){
						deviceSnapshot.setDeviceTime(bean.getDeviceTime());
						Parameter parameter = new Parameter();
						parameter.setDisplayName(bean.getDisplayName());
						deviceSnapshot.getParameters().remove(parameter);
						
						parameter.setValue(bean.getData());
						deviceSnapshot.getParameters().add(parameter);
						deviceDataMap.put(bean.getDeviceId(), OBJECT_MAPPER.writeValueAsString(deviceSnapshot));	
					}else{
						return false;
					}
				}else{
					deviceSnapshot = new DeviceSnapshot();
					deviceSnapshot.setDeviceId(bean.getDeviceId());
					deviceSnapshot.setSourceId(bean.getSourceId());
					deviceSnapshot.setDeviceTime(bean.getDeviceTime());
					
					Parameter parameter = new Parameter();
					parameter.setDisplayName(bean.getDisplayName());
					parameter.setValue(bean.getData());
					deviceSnapshot.addParameter(parameter);
					
					deviceDataMap.put(bean.getDeviceId(), OBJECT_MAPPER.writeValueAsString(deviceSnapshot));		
				}
				return true;
			}
		}).flatMap(new FlatMapFunction<DataIngestionBean, DeviceSnapshot>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4757840672986024865L;

			@Override
			public void flatMap(DataIngestionBean bean,
					Collector<DeviceSnapshot> snapshots) throws Exception {

				LOGGER.info("Collected {}, {}",bean.getDisplayName(),bean.getDeviceTime());
				String deviceSnapshotStr = deviceDataMap.get(bean.getDeviceId());
				DeviceSnapshot deviceSnapshot = null;
				if(deviceSnapshotStr != null){
					deviceSnapshot = OBJECT_MAPPER.readValue(deviceSnapshotStr,DeviceSnapshot.class);
					snapshots.collect(deviceSnapshot);
					System.err.println("Data: "+deviceSnapshotStr);
				}
			}
		}).addSink(new KafkaSink<DeviceSnapshot>(
				  parameterTool.getRequired("bootstrap.servers"),
				  parameterTool.getRequired("alarmTopic"),
				  new DeviceSnapshotSchema()));

		env.execute();
	}
}
