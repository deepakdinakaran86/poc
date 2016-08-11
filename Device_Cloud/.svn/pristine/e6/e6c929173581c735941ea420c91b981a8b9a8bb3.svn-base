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
package com.pcs.data.transformer.storm.bolts;

import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.pcs.data.transformer.bean.AlarmData;
import com.pcs.data.transformer.distributors.Publisher;
import com.pcs.data.transformer.distributors.PublisherConfig;
import com.pcs.data.transformer.util.AnalyticsUtil;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.manager.bean.ConfigPoint;
import com.pcs.saffron.manager.bean.DeviceConfiguration;

/**
 * Storm Bolt Implementation for analyzing received data based on configuration
 *
 * @author pcseg129 (Seena Jyothish)
 * @date Apr 8, 2015
 * @since galaxy-1.0.0
 */
public class AlarmDataTransformBolt extends BaseBasicBolt {

	private static Logger LOGGER = LoggerFactory
			.getLogger(AlarmDataTransformBolt.class);
	
	private static final long serialVersionUID = 1032140424347578887L;

	private final String alarmTopicName;
	private Publisher publisher;
	private static final String HEALTHY = "HEALTHY";

	private final PublisherConfig publisherConfig;

	public AlarmDataTransformBolt(String publisherConfFilePath,
			String alarmTopicName) {
		this.publisherConfig = PublisherConfig
				.getNewInstance(publisherConfFilePath);
		this.alarmTopicName = alarmTopicName;
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		LOGGER.info("Invoking alarm mesage transformer");
		if (input.getValues().get(1) != null
				&& input.getValues().get(0) != null) {
			try {
				AlarmData alarmData = (AlarmData) input.getValue(1);
				LOGGER.info("To transform  alarm msg {}", alarmData.toString());
				AlarmMessage msg = constructMessage(alarmData);
				if (msg != null) {
					pushToAlarmMsgTopic(msg);
				}
			} catch (Exception e) {
				LOGGER.error("Error in Data Analyzer Bolt Execution", e);
			}
		} else {
			LOGGER.error("Input tuple is null ");
		}
	}

	public void test(Object data) {
		try {
			this.publisher = new Publisher(publisherConfig);
			AlarmData alarmData = (AlarmData) data;
			LOGGER.info("To transform  Msg {}", alarmData.toString());
			AlarmMessage msg = constructMessage(alarmData);
			if (msg != null) {
				pushToAlarmMsgTopic(msg);
			}
		} catch (Exception e) {
			LOGGER.error("Error in Data Analyzer Bolt Execution", e);
		}
	}

	private AlarmMessage constructMessage(AlarmData alarmData) {
		DeviceConfiguration configuration = AnalyticsUtil
				.getDeviceConfig(alarmData.getSourceId());
		if (configuration != null) {
			boolean flag = false;
			ConfigPoint config = null;
			for (ConfigPoint configPoint : configuration.getConfigPoints()) {
				if (configPoint.getDisplayName().equals(
						alarmData.getParameterName())) {
					flag = true;
					config = configPoint;
					break;
				}
			}
			if (!flag) {
				LOGGER.info(
						"Displayname {} not found in the configurations for device {}",
						alarmData.getParameterName(), alarmData.getSourceId());
				return null;
			}
			return buildDeviceMsg(alarmData, config);
		}
		LOGGER.error("Configuration not available for device {}",
				alarmData.getSourceId());
		return null;
	}

	private AlarmMessage buildDeviceMsg(AlarmData alarmData, ConfigPoint config) {
		AlarmMessage message = new AlarmMessage();
		try {
			message.setSourceId(alarmData.getSourceId());
			message.setTime(alarmData.getTimestamp());
			message.setReceivedTime(Calendar.getInstance().getTime());
			message.setAlarmMessage(alarmData.getMessage());
			message.setAlarmName(alarmData.getTitle());
			message.setAlarmType(alarmData.getTitle());
			message.setStatus(alarmData.getStatus());
			
			Point point = new Point();
			point.setPointId(config.getPointId());
			point.setPointName(config.getPointName());
			point.setDisplayName(alarmData.getParameterName());
			point.setUnit(config.getUnit());
			point.setData(alarmData.getValue());
			point.setType(config.getDataType());
			point.setStatus(HEALTHY);
			message.setPoints(Collections.singletonList(point));
		} catch (Exception e) {
			LOGGER.info("Exception in constructing alarm message for {} for {}",
					alarmData.getParameterName(), alarmData.getSourceId());
			message = null;
		}
		return message;
	}

	private void pushToAlarmMsgTopic(AlarmMessage message) {
		publisher.publishToKafkaTopic(alarmTopicName, message);
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

	public static void main(String[] args) throws Exception {

	}
}