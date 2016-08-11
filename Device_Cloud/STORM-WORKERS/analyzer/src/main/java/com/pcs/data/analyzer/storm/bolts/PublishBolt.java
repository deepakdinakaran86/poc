/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
 *
 * This software is the property of Pacific Controls  Software  Services LLC  and  its
 * suppliers. The intellectual and technical concepts contained herein are proprietary 
 * to PCSS. Dissemination of this information or  reproduction  of  this  material  is
 * strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
 * Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
 * MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.pcs.data.analyzer.storm.bolts;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.analyzer.distributors.Publisher;
import com.pcs.data.analyzer.distributors.PublisherConfig;
import com.pcs.data.analyzer.util.AnalyticsUtil;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.extension.PointExtension;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;

/**
 * This class is responsible for publishing to a JMS topic
 * 
 * @author pcseg129
 */
public class PublishBolt extends BaseBasicBolt {

	private static final long serialVersionUID = -3147510395523273491L;

	private static Logger LOGGER = LoggerFactory.getLogger(PublishBolt.class);

	private Publisher publisher;

	private String publisherConfFile;

	private final PublisherConfig publisherConfig;

	private final String datastoreTopic;

	public PublishBolt(String queueName) {
		this.publisherConfig = null;
		this.datastoreTopic = queueName;
	}

	public PublishBolt(String publisherConfFilePath, String datastoreTopic) {
		LOGGER.info("Starting publisher bolt {}, {}", publisherConfFilePath,
				datastoreTopic);
		this.publisherConfFile = publisherConfFilePath;
		this.publisherConfig = PublisherConfig
				.getNewInstance(publisherConfFile);
		this.datastoreTopic = datastoreTopic;
		LOGGER.info("Publisher ready, waiting for messages");
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		try {
			if (input.getValues().size() >= 1) {
				if (input.getValues().get(0) != null) {
					try {
						@SuppressWarnings("unchecked")
						List<Message> messages = (List<Message>) input.getValue(0);
						if (!messages.isEmpty()) {
							for (Message message : messages) {
								publishData(message.toString());
							}
						}
					} catch (Exception e) {
						LOGGER.error("***Error casting to message list****", e);
					}

				} else {
					LOGGER.error("Input tuple is null ");
				}
			}
		} catch (Exception e) {
			LOGGER.error("###### Error processing tuple ######",e);
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

	public void publishData(String message) {
		long inTime = System.currentTimeMillis();
		DateTime dateTime = new DateTime(DateTimeZone.UTC);
		if (!StringUtils.isBlank(datastoreTopic)) {
			if (publisher == null) {
				publisher = new Publisher(publisherConfig);
			}
			try {
				ObjectMapper mapper = new ObjectMapper();
				Message deviceMessage = mapper
						.readValue(message, Message.class);
				dateTime.withMillis(deviceMessage.getTime());
				LOGGER.info("device time received from {} with device time {}",deviceMessage.getSourceId(), dateTime);

				String deviceId = AnalyticsUtil.getDeviceUUID(deviceMessage.getSourceId());
				if(deviceId == null){
					LOGGER.info("device not found {}", deviceMessage.getSourceId());
					return;
				}
				UUID deviceUUID = UUID.fromString(deviceId);

				StringBuilder builder = null;
				long insertTimeMs = new DateTime(DateTimeZone.UTC).getMillis();
				for (Point point : deviceMessage.getPoints()) {
					builder = new StringBuilder();

					DateTime deviceDate = new DateTime(deviceMessage.getTime()).toDateTime(DateTimeZone.UTC);
					long deviceDateStartOfDay = deviceDate.withTimeAtStartOfDay().getMillis();

					String dataStore = AnalyticsUtil.getDataStoreFromCache(point.getPhysicalQuantity());
					if (dataStore != null) {
						Object data = null;

						switch (PointDataTypes.valueOf(point.getType())) {
						case STRING:
						case BOOLEAN:
							data = point.getData().toString();
							break;
						case SHORT:
						case INTEGER:
						case LONG:
						case DOUBLE:
						case FLOAT:
							data = Double
									.parseDouble((point.getData() != null && point
											.getData().toString().trim()
											.length() > 0) ? point.getData()
											.toString() : "0");
							break;
						case LATITUDE:
							data = Double
									.parseDouble((point.getData() != null && point
											.getData().toString().trim()
											.length() > 0) ? point.getData()
											.toString() : "0");
							break;
						case LONGITUDE:
							data = Double
									.parseDouble((point.getData() != null && point
											.getData().toString().trim()
											.length() > 0) ? point.getData()
											.toString() : "0");
							break;
						default:
							break;
						}
						builder.append("{").append("\"deviceId\":\"")
								.append(deviceUUID).append("\",")
								.append("\"deviceTime\":\"")
								.append(deviceDate.getMillis()).append("\",")
								.append("\"deviceDate\":\"")
								.append(deviceDateStartOfDay).append("\",")
								.append("\"insertTime\":\"")
								.append(insertTimeMs).append("\",")
								.append("\"displayName\":\"")
								.append(point.getDisplayName()).append("\",")
								.append("\"data\":\"").append(data)
								.append("\",").append("\"datastore\":\"")
								.append(dataStore).append("\",")
								.append("\"dataType\":\"")
								.append(point.getType()).append("\",")
								.append("\"extensions\":").append("[");
						if (!CollectionUtils.isEmpty(point.getExtensions())) {
							for (PointExtension extension : point
									.getExtensions()) {
								builder.append("{\"extensionName\":\"")
										.append(extension.getExtensionName())
										.append("\",");
								builder.append("\"extensionType\":\"")
										.append(extension.getExtensionType())
										.append("\"},");
							}
							builder.deleteCharAt(builder.length() - 1);
						}
						builder.append("],");
						builder = null;
						insertTimeMs++;
					} else {
						LOGGER.error(
								"Error finding the datastore for {}, returned value is null",
								point.getDisplayName());
					}
				}

			} catch (Exception e) {
				LOGGER.error("******Error publishing data for persistence******", e);
			}

		} else {
			LOGGER.error("Unable to publish , empty queue name");
		}
	}

	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("{").append("\"deviceId\":\"")
				.append("13d450bf-3fad-4669-8694-aebdabd4fefa").append("\",")
				.append("\"deviceTime\":\"").append("1463038800000")
				.append("\",").append("\"deviceDate\":\"")
				.append("1463038800000").append("\",")
				.append("\"insertTime\":\"").append("1463038800000")
				.append("\",").append("\"displayName\":\"").append("testpoint")
				.append("\",").append("\"data\":\"").append("33").append("\",")
				.append("\"datastore\":\"").append("status_float")
				.append("\",").append("\"dataType\":\"").append("string")
				.append("\",").append("\"extensions\":").append("[");
		builder.append("],");
		builder.append("\"isLatest\":\"").append("true").append("\",");

		builder.append("\"systemTag\":\"").append("").append("\"").append("}");

		System.out.println(builder.toString());
	}
}
