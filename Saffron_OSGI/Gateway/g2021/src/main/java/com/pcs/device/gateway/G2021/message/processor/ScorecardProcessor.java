package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.ScorecardMessage;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.utils.PointComparator;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.bean.ConfigPoint;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

public class ScorecardProcessor extends G2021Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScorecardProcessor.class);
	private final DeviceManager deviceManager = DeviceManagerUtil.getG2021DeviceManager();
	private DefaultConfiguration configuration;
	private Gateway gateway;


	public ScorecardProcessor(Integer unitId, Object sourceIdentifier,Gateway gateway) throws MessageProcessException {
		if(gateway == null){
			throw new MessageProcessException("Unknown device gateway");
		}
		this.gateway = gateway;
		configuration = (DefaultConfiguration) deviceManager.getConfiguration(unitId,gateway);
		if(configuration == null)
			throw new MessageProcessException("Configuration cannot be null !!!");
		else if(configuration.getSourceIdentifier() == null){
			if(sourceIdentifier != null)
				configuration.setSourceIdentifier(sourceIdentifier.toString());
			else
				throw new MessageProcessException("Device cannot be identified !!!");
		}

	}

	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException {
		ScorecardMessage message = new ScorecardMessage();
		ByteBuf scorecardBuf = (ByteBuf) G2021Data;
		LOGGER.info("Total points in sync : "+scorecardBuf.readShort());
		short scorecardResult = scorecardBuf.readUnsignedByte();
		LOGGER.info("Scorecard status : "+scorecardResult);
		if(scorecardResult == 1){

			if( !configuration.isConfigured()){
				if(configuration.getConfigPoints() != null && !configuration.getConfigPoints().isEmpty()){
					LOGGER.info("Initial Configuration Request From the Device !!");
					message.setConfigurationChanged(true);
					configuration.setConfigured(true);
				}else{
					LOGGER.info("Empty Configuration !!!");
					LOGGER.info("Initial Configuration Request From the Device !!");
					message.setConfigurationChanged(true);
					configuration.setConfigured(true);
					//throw new MessageProcessException("Empty Configuration Received !!!");
				}
			}else{

				if(configuration.getUpdatedPointConfigurations() != null && !configuration.getUpdatedPointConfigurations().isEmpty()){

					PointComparator pointComparator = new PointComparator();
					Collections.sort(configuration.getConfigPoints(),pointComparator);
					Collections.sort(configuration.getUpdatedPointConfigurations(),pointComparator);
					pointComparator = null;
					LOGGER.info("App status {}",configuration.getConfigurationUpdateStatus());
					if((configuration != null && configuration.getConfigurationUpdateStatus()) || !configuration.getConfigPoints().equals(configuration.getUpdatedPointConfigurations())){
						configuration.setConfigPoints(configuration.getUpdatedPointConfigurations());
						configuration.setUpdatedPointConfigurations(new ArrayList<ConfigPoint>());
						configuration.getPointMapping().clear();
						configuration.addPointMappings(configuration.getConfigPoints());
						LOGGER.info("Configuration updated :: New Configuration is :: "+configuration.toString());
						message.setConfigurationChanged(true);
						configuration.setConfigurationUpdateStatus(false);
						deviceManager.refreshDeviceConfiguration(configuration,gateway);
					}else{
						LOGGER.info("Received configuration indicates no change !!");
						configuration.setUpdatedPointConfigurations(new ArrayList<ConfigPoint>());
						deviceManager.refreshDeviceConfiguration(configuration,gateway);
					}
				}else{
					LOGGER.info("Invalid configuration update request, possibly an already updated configuration");
				}

			}
		}
		return message;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		return G2021ACKUtil.getServerMessage(message, header);
	}

}
