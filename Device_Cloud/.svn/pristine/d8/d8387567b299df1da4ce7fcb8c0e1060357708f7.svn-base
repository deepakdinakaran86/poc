package com.pcs.data.analyzer.storm.bolts;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.analyzer.config.Configuration;
import com.pcs.data.analyzer.distributors.Publisher;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.data.analyzer.storm.mqtt.DeviceMqttMessage;
import com.pcs.data.analyzer.util.AnalyticsUtil;
import com.pcs.saffron.manager.api.datasource.bean.DatasourceDTO;
import com.pcs.saffron.manager.api.datasource.bean.Parameter;
import com.pcs.saffron.manager.bean.DataIngestionBean;
import com.pcs.saffron.manager.enums.MessageType;

public class LivePublishBolt extends BaseBasicBolt {

	private static final long serialVersionUID = -6251983796767532566L;

	private static Logger LOGGER = LoggerFactory.getLogger(LivePublishBolt.class);

	private Publisher publisher;

	private MqttMsgTransmitter mqttMsgTransmitter;

	private final PublisherConfig publisherConfig;

	public LivePublishBolt(String publisherConfFilePath) {
		this.publisherConfig = PublisherConfig.getNewInstance(publisherConfFilePath);
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		if (input.getValues().get(0) != null) {
			try {
				String liveMessageJSON = input.getValue(0).toString();
				ObjectMapper mapper = new ObjectMapper();
				List<DataIngestionBean> liveMessage = mapper.readValue(liveMessageJSON, new TypeReference<List<DataIngestionBean>>() {});
				if(liveMessage != null){
					publishData(liveMessage);
				}
			} catch (Exception e) {
				LOGGER.error("Error in LivePublishBolt Bolt Execution", e);
			}
		} else {
			LOGGER.error("Input tuple is null ");
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf,
			TopologyContext context) {
		this.publisher = new Publisher(publisherConfig);
		this.mqttMsgTransmitter = new MqttMsgTransmitter(publisherConfig);
		super.prepare(stormConf, context);
	}

	public void publishData( List<DataIngestionBean> liveMessages) {
		try {
			if(!CollectionUtils.isEmpty(liveMessages)){
				DatasourceDTO datasourceDTO = new DatasourceDTO();
				JsonFactory factory = new JsonFactory();
				factory.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
				ObjectMapper mapper = new ObjectMapper(factory);
				
				for (DataIngestionBean liveMessage : liveMessages) {
					LOGGER.info("In publishData received sourceid {} ", liveMessage.getSourceId());
					if (!StringUtils.isBlank(liveMessage.getDeviceId())) {
						if (publisher == null) {
							publisher = new Publisher(publisherConfig);
						}
						Long lastPublishTime = AnalyticsUtil.getDatasourcePublishTime(liveMessage.getSourceId(),liveMessage.getDisplayName());

						if (lastPublishTime == null || lastPublishTime <= liveMessage.getDeviceTime()) {
							LOGGER.info(" for device {} is lastPublishTime is {} , messagetime is {}",liveMessage.getSourceId(), lastPublishTime, liveMessage.getDeviceTime());
							Parameter parameter = new Parameter(liveMessage.getDisplayName(), liveMessage.getDataType());
							parameter.setValue(liveMessage.getData());
							parameter.setUnit(liveMessage.getUnit());
							parameter.setTime(liveMessage.getDeviceTime());
							datasourceDTO.addParameter(parameter);
							AnalyticsUtil.updateDatasourcePublishTime(liveMessage.getSourceId(),liveMessage.getDisplayName(),liveMessage.getDeviceTime());
							liveMessage.setIsLatest(true);
							datasourceDTO.setDatasourceName(liveMessage.getSourceId());
						}else{
							liveMessages.remove(liveMessage);
							LOGGER.info("History message from device {}, for parameter {} at {}",liveMessage.getSourceId(),liveMessage.getDisplayName(),liveMessage.getDeviceTime());
						}
					}
				}
				if(!CollectionUtils.isEmpty(datasourceDTO.getParameters())){
					//Publishing to insert latest information

					List<String> contexts = AnalyticsUtil.getDeviceContext("Publish bolt", datasourceDTO.getDatasourceName());

					for (String context : contexts) {
						datasourceDTO.setMessageType(MessageType.MESSAGE);
						
						String publishMsg = mapper.writeValueAsString(datasourceDTO);
						LOGGER.info("Publishing data of device {} to topic {}",context, datasourceDTO.getDatasourceName());
						publisher.publishToJMSTopic(context, publishMsg);

						LOGGER.info("publishing data of sourceid {} to mqtt ",datasourceDTO.getDatasourceName());
						DeviceMqttMessage mqttMessage = new DeviceMqttMessage();
						mqttMessage.setMessage(publishMsg);
						mqttMessage.setIdentity(context);
						mqttMsgTransmitter.publish(mqttMessage,MessageType.MESSAGE);
					}
					publisher.publishToKafkaTopic(Configuration.LATEST_DATA_STORE, mapper.writeValueAsString(liveMessages));
				}
			}

		} catch (Exception e) {
			LOGGER.error("Cannot publish message",e);
		}

	}

}
