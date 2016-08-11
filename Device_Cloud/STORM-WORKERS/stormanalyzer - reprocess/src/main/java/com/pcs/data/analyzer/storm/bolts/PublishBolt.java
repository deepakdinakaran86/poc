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

	private final String queueName;

	public PublishBolt(String queueName) {
		this.publisherConfig = null;
		this.queueName = queueName;
	}

	public PublishBolt(String publisherConfFilePath, String queueName) {
		LOGGER.info("Starting publisher bolt {}, {}", publisherConfFilePath,
				queueName);
		this.publisherConfFile = publisherConfFilePath;
		this.publisherConfig = PublisherConfig
				.getNewInstance(publisherConfFile);
		this.queueName = queueName;
		LOGGER.info("Publisher ready, waiting for messages");
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		if (input.getValues().size() >= 1) {
			if (input.getValues().get(1) != null
					&& input.getValues().get(0) != null) {
				LOGGER.info("publishing to datastore topic");
				publishData(String.valueOf(input.getValues().get(1)));
			} else {
				LOGGER.error("Input tuple is null ");
			}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

	public void publishData(String message) {
		if (!StringUtils.isBlank(queueName)) {
			if (publisher == null) {
				publisher = new Publisher(publisherConfig);
			}
			try {
				ObjectMapper mapper = new ObjectMapper();
				Message deviceMessage = mapper
						.readValue(message, Message.class);
				
				LOGGER.info("device time received {}",
						deviceMessage.getTime());

				String deviceId = AnalyticsUtil.getDeviceUUID(deviceMessage
						.getSourceId());
				UUID deviceUUID = UUID.fromString(deviceId);

				StringBuilder builder = null;
				long insertTimeMs = new DateTime(DateTimeZone.UTC).getMillis();
				for (Point point : deviceMessage.getPoints()) {
					builder = new StringBuilder();

					DateTime deviceDate = new DateTime(deviceMessage.getTime())
							.toDateTime(DateTimeZone.UTC);
					long deviceDateStartOfDay = deviceDate
							.withTimeAtStartOfDay().getMillis();

					String dataStore = AnalyticsUtil
							.getDataStoreFromCache(point.getPhysicalQuantity());
					if (dataStore != null) {
						LOGGER.info("dataStore for point {} for pq {} is {}",
								point.getDisplayName(),
								point.getPhysicalQuantity(), dataStore);
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
						builder.append("],").append("\"systemTag\":\"")
								.append("").append("\"").append("}");
						publisher.publishToKafkaTopic(queueName,
								builder.toString());
						builder = null;
						insertTimeMs++;
					} else {
						LOGGER.error(
								"Error finding the datastore for {}, returned value is null",
								point.getDisplayName());
					}
				}

			} catch (Exception e) {
				LOGGER.error("Error publishing data for persistence", e);
			}

		} else {
			LOGGER.error("Unable to publish , empty queue name");
		}
	}

	public static void main(String[] args) {
		System.out.println("start bolt");

		String a = "{\"sourceId\":\"fe0c5687-7c6a-4cac-9e6d-9386ae9a2266\",\"time\":\"1447635636000\",\"receivedTime\":\"null\",\"points\":[{\"pointId\":\"23\",\"pointName\":\"FAN SPEED\",\"displayName\":\"FAN SPEED\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"/0\",\"data\":\"1.0\",\"status\":\"OVERRIDEN_IN1\",\"extensions\":[{\"extensionName\":\"WRITEABLE\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"CHANGE OF VALUE BASED ACQUISITION, THRESHOLD AT 15.0\",\"extensionType\":\"Acquisition Mode\"},{\"extensionName\":\"OVERRIDEN_IN1\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]},{\"pointId\":\"25\",\"pointName\":\"SET POINT\",\"displayName\":\"SET POINT\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"/0\",\"data\":\"19.0\",\"status\":\"OVERRIDEN_IN1\",\"extensions\":[{\"extensionName\":\"WRITEABLE\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"CHANGE OF VALUE BASED ACQUISITION, THRESHOLD AT 15.0\",\"extensionType\":\"Acquisition Mode\"},{\"extensionName\":\"OVERRIDEN_IN1\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[{\"extensionName\":\"OUT OF RANGE ALARM\",\"extensionType\":\"ALARM\",\"state\":\"LOWER_THRESHOLD_ALARM\",\"type\":\"null\",\"alarmMessage\":\"\",\"criticality\":\"NORMAL\",\"normalMessage\":\"Normal\"}]}]}";
		PublishBolt bolt = new PublishBolt("Config//publisherconfig.yaml",
				"datastore");
		bolt.publishData(a);
	}
}
