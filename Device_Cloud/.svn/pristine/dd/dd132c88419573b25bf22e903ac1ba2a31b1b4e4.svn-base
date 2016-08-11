/**
 * 
 */
package com.pcs.analytics.util.test;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.transformer.storm.scheme.MessageScheme;
import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListenerAdapter;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;
import com.pcs.saffron.cipher.data.message.Message;

/**
 * @author pcseg129
 *
 */
public class KafkaAdvListener extends CoreListenerAdapter {

	private final Logger LOGGER = LoggerFactory
			.getLogger(KafkaAdvListener.class);

	private CoreConsumer consumer;

	private CorePublisher corePublisher;

	private static int count = 0;

	public CoreConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(CoreConsumer consumer) {
		consumer.setMessageListener(this);
		this.consumer = consumer;
	}

	public CorePublisher getCorePublisher() {
		return corePublisher;
	}

	public void setCorePublisher(CorePublisher corePublisher) {
		this.corePublisher = corePublisher;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(KafkaAdvListener.class);

	public void consumeData(byte[] bytes, long offset) {
		try {
			String msg = new String(bytes, "UTF-8");
			LOGGER.info("offset {}, Msg count {}, Msg-> {}",
					String.valueOf(offset), msg);
			insertToAnalyzedMsgReprocess(msg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void insertToAnalyzedMsgReprocess(String analyzedMsg) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			long reprocessDayTimestampFloor = 1459872000000l;
			long reprocessDayTimestampCeil = 1459962000000l;
			Message message = mapper.readValue(analyzedMsg, Message.class);
			long deviceTimestamp = message.getTime();

			LOGGER.info(" deviceTimestamp {} , is processing {}",
					deviceTimestamp,
					deviceTimestamp >= reprocessDayTimestampFloor
							&& deviceTimestamp <= reprocessDayTimestampCeil);

			if (deviceTimestamp >= reprocessDayTimestampFloor
					&& deviceTimestamp <= reprocessDayTimestampCeil) {
				List<Serializable> keyedMessages = new ArrayList<Serializable>();
				KeyedMessage<Object, Object> keyedMessage = new KeyedMessage<Object, Object>(
						"analyzed-message-reprocess", analyzedMsg);
				keyedMessages.add(keyedMessage);
				corePublisher.publish(keyedMessages);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
