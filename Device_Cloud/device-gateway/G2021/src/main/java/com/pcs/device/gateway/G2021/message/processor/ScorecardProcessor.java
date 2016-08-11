package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.devicemanager.G2021DeviceManager;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.data.point.utils.PointComparator;

public class ScorecardProcessor extends G2021Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScorecardProcessor.class);
	private final G2021DeviceManager deviceManager = G2021DeviceManager.instance();
	private G2021DeviceConfiguration configuration;
	private Integer unitId;



	public ScorecardProcessor(Integer unitId, Object sourceIdentifier) throws MessageProcessException {
		this.unitId = unitId;
		configuration = (G2021DeviceConfiguration) deviceManager.getConfiguration(unitId);
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
		ByteBuf scorecardBuf = (ByteBuf) G2021Data;
		LOGGER.info("Total points in sync : "+scorecardBuf.readShort());
		short scorecardResult = scorecardBuf.readUnsignedByte();
		LOGGER.info("Scorecard status : "+scorecardResult);
		if(scorecardResult == 1){

			if( !configuration.isConfigured()){
				if(configuration.getPointConfigurations() != null && !configuration.getPointConfigurations().isEmpty()){
					LOGGER.info("Initial Configuration Request From the Device !!");
					deviceManager.setDeviceConfiguration(unitId, configuration);
					configuration.setConfigured(true);
				}else{
					throw new MessageProcessException("Empty Configuration Received !!!");
				}
			}else{
				if(configuration.getUpdatedPointConfigurations() != null && !configuration.getUpdatedPointConfigurations().isEmpty()){
					
					PointComparator pointComparator = new PointComparator();
					Collections.sort(configuration.getPointConfigurations(),pointComparator);
					Collections.sort(configuration.getUpdatedPointConfigurations(),pointComparator);
					pointComparator = null;
					LOGGER.info("App status {}",configuration.appChanged());
					if(configuration.appChanged() || !configuration.getPointConfigurations().equals(configuration.getUpdatedPointConfigurations())){
						configuration.setPointConfigurations(configuration.getUpdatedPointConfigurations());
						configuration.setUpdatedPointConfigurations(new ArrayList<Point>());
						configuration.getPointMapping().clear();
						configuration.addPointMappings(configuration.getPointConfigurations());
						LOGGER.info("Configuration updated :: New Configuration is :: "+configuration.toString());
						deviceManager.setDeviceConfiguration(configuration.getUnitId(), configuration);
						configuration.setAppChanged(false);
					}else{
						LOGGER.info("Received configuration indicates no change !!");
						configuration.setUpdatedPointConfigurations(new ArrayList<Point>());
						deviceManager.refreshDeviceConfiguration(configuration.getUnitId(), configuration);
					}
				}else{
					throw new MessageProcessException("Empty Configuration Received !!!");
				}
			}
		}
		return null;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {

		ByteBuffer ackResponse = ByteBuffer.allocate(3);
		ackResponse.put(PacketType.ANONYMOUS_ACK.getType().byteValue());
		ackResponse.put(header.getMessageType().getType().byteValue());
		ackResponse.put(header.getSeqNumber().byteValue());
		return ackResponse.array();

	}

}
