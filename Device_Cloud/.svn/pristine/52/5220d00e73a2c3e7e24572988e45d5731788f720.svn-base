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
package com.pcs.analytics.storm.bolts;

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

import com.pcs.analytics.distributors.Publisher;
import com.pcs.analytics.distributors.PublisherConfig;
import com.pcs.analytics.util.AnalyticsUtil;
import com.pcs.deviceframework.decoder.data.message.Message;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date Mar 31, 2015
 * @since galaxy-1.0.0
 */
public class PushServicePublisherBolt extends BaseBasicBolt {

	private static Logger LOGGER = LoggerFactory
	        .getLogger(PushServicePublisherBolt.class);

	private Publisher publisher;

	private final PublisherConfig publisherConfig;

	public PushServicePublisherBolt(String pushServiceTopicName) {
		this.publisherConfig = PublisherConfig
		        .getNewInstance("publisherconfig.yaml");
	}
	private static final long serialVersionUID = -4567803324988369463L;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		Message message = (Message)input.getValue(1);
		publishData(message.getSourceId().toString(), String.valueOf(message));
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

	public void publishData(String datasourceName, String message) {
		if (!StringUtils.isBlank(datasourceName)) {
			if (publisher == null) {
				publisher = new Publisher(publisherConfig);
			}
			List<String> contexts = AnalyticsUtil
			        .getDeviceContext(datasourceName);
			if (CollectionUtils.isNotEmpty(contexts)) {
				for (String context : contexts) {
					publisher.publishToJMSTopic(context, message);
				}
			}
		} else {
			LOGGER.error("Unable to publish , empty datasource name");
		}
	}

	public static void main(String[] args) {
		PushServicePublisherBolt bolt = new PushServicePublisherBolt("topic");
		Message message = AnalyticsUtil.getMessageForTesting();
		bolt.publishData(message.getSourceId().toString(),
		        String.valueOf(message));
	}

}
