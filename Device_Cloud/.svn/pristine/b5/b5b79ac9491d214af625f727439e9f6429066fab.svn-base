package com.pcs.data.analyzer.storm.bolts;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.pcs.data.analyzer.distributors.Publisher;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.data.analyzer.util.AnalyticsUtil;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.manager.api.datasource.bean.DatasourceDTO;
import com.pcs.saffron.manager.api.datasource.bean.Parameter;
import com.pcs.saffron.manager.enums.MessageType;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class LivePublishBolt extends BaseBasicBolt {

	private static final long serialVersionUID = -6251983796767532566L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(LivePublishBolt.class);

	private Publisher publisher;

	private final PublisherConfig publisherConfig;

	public LivePublishBolt(String publisherConfFilePath) {
		this.publisherConfig = PublisherConfig
				.getNewInstance(publisherConfFilePath);
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		if (input.getValues().get(1) != null
				&& input.getValues().get(0) != null) {
			Message message = (Message) input.getValue(1);
			LOGGER.info("In Publisher Bolt received msg {} for device {}",
					message.toString(), message.getSourceId().toString());
			publishData(message.getSourceId().toString(), message);
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
		super.prepare(stormConf, context);
	}

	public void publishData(String sourceId, Message message) {
		LOGGER.info("In publishData received sourceid {} ", sourceId);
		if (!StringUtils.isBlank(sourceId)) {
			if (publisher == null) {
				publisher = new Publisher(publisherConfig);
			}
			Long lastPublishTime = AnalyticsUtil
					.getDatasourcePublishTime(sourceId);
			LOGGER.info(" for device {} is lastPublishTime is {} , messagetime is {}",sourceId,lastPublishTime,message.getTime());
			if (lastPublishTime == null || lastPublishTime <= message.getTime()) {
				LOGGER.info("Publishing to JMS because lastPublishTime is less than message time for device {}",sourceId);
				List<String> contexts = AnalyticsUtil.getDeviceContext(
						"Publish bolt", sourceId);
				if (CollectionUtils.isNotEmpty(contexts)) {
					DatasourceDTO datasourceDTO = new DatasourceDTO();
					for (Point point : message.getPoints()) {
						Parameter parameter = new Parameter(
								point.getDisplayName(), point.getType());
						parameter.setValue(point.getData());
						parameter.setUnit(point.getUnit());
						parameter.setTime(message.getTime());
						datasourceDTO.addParameter(parameter);
					}

					Gson gson = new Gson();
					String dsName = contexts.get(0);
					for (String context : contexts) {
						datasourceDTO.setMessageType(MessageType.MESSAGE);
						datasourceDTO.setDatasourceName(dsName);
						String publishMsg = gson.toJson(datasourceDTO);
						LOGGER.info("Publishing data of device {} to topic {}",context,sourceId);
						publisher.publishToJMSTopic(context, publishMsg);
					}
				}
				LOGGER.info(" updating lastPublishTime for device {} to {}",sourceId,message.getTime());
				AnalyticsUtil.updateDatasourcePublishTime(sourceId,message.getTime());
			}
		} else {
			LOGGER.error("Unable to publish , empty datasource name");
		}
	}

}
