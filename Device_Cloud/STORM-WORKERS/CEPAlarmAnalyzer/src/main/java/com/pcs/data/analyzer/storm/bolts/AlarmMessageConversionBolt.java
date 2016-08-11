package com.pcs.data.analyzer.storm.bolts;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.data.analyzer.distributors.Publisher;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.data.analyzer.storm.model.CEPAlarmMessage;
import com.pcs.data.analyzer.storm.model.CEPMetaData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.analyzer.storm.topology.MessageAnalyzerTopology;
import com.pcs.saffron.cipher.data.message.AlarmMessage;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

/**
 * 
 * @author pcseg369
 *
 */
public class AlarmMessageConversionBolt extends BaseBasicBolt {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmMessageConversionBolt.class);

	// Topics
	private String alarmMessageTopic;
	private String publisherConfFilePath;

	// Publisher.
	private Publisher publisher;
	private final PublisherConfig publisherConfig;


	public AlarmMessageConversionBolt(String alarmMessageTopic, String publisherConfFilePath) {
		super();
		this.alarmMessageTopic = alarmMessageTopic;
		this.publisherConfFilePath = publisherConfFilePath;
		this.publisherConfig = PublisherConfig.getNewInstance(publisherConfFilePath);
		this.publisher = new Publisher(publisherConfig);
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector arg1) {
		CEPAlarmMessage cepAlarmMessage = (CEPAlarmMessage)input.getValue(1);
		LOGGER.info("AlarmMessageConversionBolt execute {}", cepAlarmMessage);

		if(cepAlarmMessage != null && cepAlarmMessage.getEvent() != null && 
				cepAlarmMessage.getEvent().getMetaData() != null) {
			CEPMetaData cepMetaData = cepAlarmMessage.getEvent().getMetaData();
			// Building AlarmMessage
			AlarmMessage alarmMessageToPublish = new AlarmMessage();
			alarmMessageToPublish.setDisplayName(cepMetaData.getPointName() + "-" + cepMetaData.getPointId());
			alarmMessageToPublish.setTime(cepMetaData.getTime());
			alarmMessageToPublish.setReceivedTime(new Date(cepMetaData.getReceivedTime()));
			alarmMessageToPublish.setAlarmMessage(cepMetaData.getAlarmMessage());
			alarmMessageToPublish.setAlarmType(cepMetaData.getAlarmType());
			alarmMessageToPublish.setAlarmName(cepMetaData.getAlarmName());
			alarmMessageToPublish.setData(cepMetaData.getData());
			alarmMessageToPublish.setUnit(cepMetaData.getUnit());
			alarmMessageToPublish.setType("Alarm");
			alarmMessageToPublish.setSourceId(cepMetaData.getSourceId());
			alarmMessageToPublish.setStatus(cepMetaData.isActive());
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				String jsonInString = mapper.writeValueAsString(alarmMessageToPublish);
				publisher.publishToKafkaTopic(alarmMessageTopic, jsonInString);

			} catch (Exception e) {
				LOGGER.error("AlarmMessageConversionBolt | Error in serializing in AlarmMessage {}", e);
				
			}

		}
		

	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub

	}

}
