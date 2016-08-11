
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
package com.pcs.deviceframework.datadist.core;

import java.rmi.Naming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.JmsBaseConsumer;
import com.pcs.deviceframework.datadist.consumer.KafkaBaseConsumer;
import com.pcs.deviceframework.datadist.enums.ConsumerType;
import com.pcs.deviceframework.datadist.enums.DistributorMode;
import com.pcs.deviceframework.datadist.enums.RegistryEntry;
import com.pcs.deviceframework.datadist.publisher.CorePublisher;
import com.pcs.deviceframework.datadist.publisher.JmsBasePublisher;
import com.pcs.deviceframework.datadist.publisher.KafkaBasePublisher;
import com.pcs.deviceframework.datadist.util.DistributionUtil;

/**
 * This class is responsible for providing a consumer,
 * based on the distribution mode and consumer type
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class DistributionManagerImpl implements DistributionManager{

	private static final Logger LOGGER = LoggerFactory.getLogger(DistributionManagerImpl.class);

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
			String name  = "rmi://" + ApplicationConfiguration.getDistributionConfig().getRmiServerHostName() + "/";
			name = "rmi://" + ApplicationConfiguration.getDistributionConfig().getRmiServerHostName() + ":" + ApplicationConfiguration.getDistributionConfig().getRmiPort() + "/";
			LOGGER.error("rmi registry name {}",name);
			switch(mode){
				case JMS:
					name = name + RegistryEntry.JMS_PUBLISHER.toString();
					corePublisher = (CorePublisher) Naming.lookup(name);
					return corePublisher;
				case KAFKA:
					name = name + RegistryEntry.KAFKA_PUBLISHER.toString();
					corePublisher = (CorePublisher) Naming.lookup(name);
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
