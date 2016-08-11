package com.pcs.event.scanner.publisher;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.event.scanner.config.ScannerConfiguration;
import com.pcs.jms.jmshelper.publishers.BasePublisher;

public class FormattedDataPublisher extends BasePublisher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FormattedDataPublisher.class);
	private String queue;
	private String URL;
	private static MessageProducer producer = null;
	
	public FormattedDataPublisher() {
		this.queue = ScannerConfiguration.getProperty(ScannerConfiguration.EVENT_SCAN_SUSPECTS);
		this.URL = ScannerConfiguration.getProperty(ScannerConfiguration.REALTIME_DATA_URL);
		if(producer == null){
			try {
				producer = getProducer(URL, queue);
			} catch (Exception e) {
				LOGGER.error("Error creating producer @ {},{}",URL,queue);
			}
		}
	}
	
	public FormattedDataPublisher(String queue,String url){
		this.queue = queue;
		this.URL = url;
		if(producer == null){
			try {
				producer = getProducer(URL, queue);
			} catch (Exception e) {
				LOGGER.error("Error creating producer @ {},{}",URL,queue);
			}
		}
	}

	@Override
	public void publish(Object message) throws JMSException {
		if(producer == null){
			LOGGER.error("Cannot publish message, producer is {}",producer);
		}else{
			TextMessage textMessage = getSession().createTextMessage(message.toString());
			producer.send(textMessage);
		}
	}

	@Override
	public void setPublisherURL(String url) {
		this.URL = url;
	}

	@Override
	public void setPublisherQueue(String queue) {
		this.queue = queue;
	}

	@Override
	public String getQueue() {
		return queue;
	}

	@Override
	public String getURL() {
		return URL;
	}

}
