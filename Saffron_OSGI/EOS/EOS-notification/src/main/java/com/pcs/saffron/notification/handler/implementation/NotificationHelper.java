package com.pcs.saffron.notification.handler.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.notification.broker.consumer.CoreConsumer;
import com.pcs.saffron.notification.broker.jms.consumer.JmsBaseConsumer;
import com.pcs.saffron.notification.broker.jms.publisher.JmsBasePublisher;
import com.pcs.saffron.notification.broker.kafka.consumer.base.KafkaBaseConsumer;
import com.pcs.saffron.notification.broker.kafka.publisher.KafkaBasePublisher;
import com.pcs.saffron.notification.broker.publisher.CorePublisher;
import com.pcs.saffron.notification.broker.util.DistributionUtil;
import com.pcs.saffron.notification.enums.ConsumerType;
import com.pcs.saffron.notification.enums.DistributorMode;
import com.pcs.saffron.notification.handler.NotificationHandler;

public class NotificationHelper implements NotificationHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationHelper.class);

	public CoreConsumer getConsumer(DistributorMode mode, ConsumerType type){
		return getConsumerByMode(mode,type);
	}

	private CoreConsumer getConsumerByMode(DistributorMode mode,ConsumerType type){
		CoreConsumer coreConsumer = null;
		switch(mode){
			case JMS:
				coreConsumer = new JmsBaseConsumer().getConsumer(type);
				return DistributionUtil.getJmsConsumerConfigured(coreConsumer);
			case KAFKA:
				coreConsumer =  new KafkaBaseConsumer().getConsumer(type);
				return DistributionUtil.getKafkaConsumerConfigured(coreConsumer, type);
			default:
				return null;
		}
	}

	public CorePublisher getPublisher(DistributorMode mode){
		try{ 
			CorePublisher corePublisher = null;
			switch(mode){
				case JMS:
					corePublisher = (CorePublisher) new JmsBasePublisher();
					return corePublisher;
				case KAFKA:
					corePublisher = (CorePublisher) new KafkaBasePublisher();
					return corePublisher;
				default:
					return null;
			}
		}catch(Exception ex){
			LOGGER.error("Error occurred while getting publisher remote object",ex);
		}
		return null;
	}

	public static CorePublisher getBasePublisher(DistributorMode mode){
		try{ 
			switch(mode){
				case JMS:
					return new JmsBasePublisher();
				case KAFKA:
					return new KafkaBasePublisher();
				default:
					return null;
			}
		}catch(Exception ex){
			LOGGER.error("Error occurred while getting publisher remote object",ex);
		}
		return null;
	}
	

}
