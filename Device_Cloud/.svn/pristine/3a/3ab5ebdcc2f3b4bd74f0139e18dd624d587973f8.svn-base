package com.pcs.deviceframework.commandprocessor.publisher;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import com.pcs.deviceframework.commandprocessor.beans.Command;
import com.pcs.deviceframework.pubsub.publishers.BasePublisher;

public class CommandPublisher extends BasePublisher {

	private static final String COMMAND_POOL = "command_pool";
	private static final String TCP_LOCALHOST_61616 = "tcp://localhost:61616";

	public CommandPublisher() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getQueue() {
		// TODO Auto-generated method stub
		return COMMAND_POOL;
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return TCP_LOCALHOST_61616;
	}

	@Override
	public void setPublisherQueue(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPublisherURL(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void publish(Object message) throws JMSException {
		Command dataMessage = (Command) message;
		ObjectMessage objectMessage = getSession().createObjectMessage(dataMessage);
		getProducer(TCP_LOCALHOST_61616, COMMAND_POOL).send(objectMessage);
	}
}
