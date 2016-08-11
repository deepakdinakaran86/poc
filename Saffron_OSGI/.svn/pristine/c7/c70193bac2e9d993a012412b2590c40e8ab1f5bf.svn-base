package com.pcs.device.gateway.jace.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.jace.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.jace.message.AlarmMessage;
import com.pcs.device.gateway.jace.message.ConfigurationFeed;
import com.pcs.device.gateway.jace.message.ConfigurationUpdateMessage;
import com.pcs.device.gateway.jace.message.ConnectionMessage;
import com.pcs.device.gateway.jace.message.Header;
import com.pcs.device.gateway.jace.message.JaceMessage;
import com.pcs.device.gateway.jace.message.KeepAlive;
import com.pcs.device.gateway.jace.message.processor.AlarmProcessor;
import com.pcs.device.gateway.jace.message.processor.ConfigurationFeedUpdateProcessor;
import com.pcs.device.gateway.jace.message.processor.ConfigurationUpdateRequestProcessor;
import com.pcs.device.gateway.jace.message.processor.ConnectionRequestProcessor;
import com.pcs.device.gateway.jace.message.processor.KeepAliveProcessor;
import com.pcs.device.gateway.jace.message.type.DeviceAuthenticationStatus;
import com.pcs.device.gateway.jace.utils.KeyCypherUtils;
import com.pcs.device.gateway.jace.utils.PointConfigurationUtil;
import com.pcs.device.gateway.jace.utils.SupportedDevices;
import com.pcs.device.gateway.jace.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.data.point.utils.PointComparator;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.authentication.AuthenticationResponse;
import com.pcs.saffron.manager.bean.ConfigPoint;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;

public class JaceMessageHandler extends SimpleChannelInboundHandler<Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(JaceMessageHandler.class);
	private static final DeviceManager jaceDeviceManager = DeviceManagerUtil.getJaceDeviceManager();
	private static Gateway jaceGateway = SupportedDevices.getGateway(Devices.JACE_CONNECTOR);
	private InetSocketAddress remoteAddress = null;
	private Object sourceIdentifier = null;
	private String handlerMode = null;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {

		if (msg instanceof DatagramPacket) {
			DatagramPacket packet = (DatagramPacket)msg;
			remoteAddress = packet.sender();
			handlerMode = Connector.CONNECTOR_PROTOCOL_UDP;
			jaceGateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_UDP);
		}else{
			ByteBuf messageBuf = (ByteBuf) msg;
			processMessage(messageBuf, ctx);
			messageBuf = null;
		}
		msg = null;

	}

	private void processMessage(ByteBuf deviceMessage, ChannelHandlerContext ctx) throws Exception{
		try {

			if (!(ctx.channel() instanceof NioDatagramChannel)) {
				remoteAddress = (InetSocketAddress)ctx.channel().remoteAddress();
				handlerMode = Connector.CONNECTOR_PROTOCOL_TCP;
				jaceGateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_TCP);
			}

			Header header = Header.getInstance(deviceMessage);
			ByteBuf copy = deviceMessage.copy();
			byte[] data = new byte[copy.readableBytes()];
			copy.getBytes(0, data);
			String message = new String(data);
			LOGGER.info("Data from device {}, header :{}, ack requirement :{}",message,header.getType(),header.getRequireAck());

			ObjectMapper mapper = new ObjectMapper();
			AuthenticationResponse authenticateDevice = null;
			switch (header.getType()) {

			case CONNECTION_REQ:
				ConnectionMessage connectionMessage = mapper.readValue(message, ConnectionMessage.class);
				authenticateDevice = authenticateDevice(connectionMessage);
				if(!authenticateDevice.isAuthenticated()){
					updateDevicestatus(connectionMessage,DeviceAuthenticationStatus.REJECTED);
					LOGGER.info("Device not authenticate {}, will send rejection response!!",connectionMessage.getHostId());
				}else{
					updateDevicestatus(connectionMessage,DeviceAuthenticationStatus.ACCEPTED);
					connectionMessage.setHostId(sourceIdentifier.toString());
					LOGGER.info("Device is authenticated {}, will send positive response!!",sourceIdentifier.toString());
				}
				if(header.getRequireAck()){
					if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_PROTOCOL_TCP)){
						LOGGER.info("Handler mode {}",handlerMode);
						ctx.writeAndFlush(ConnectionRequestProcessor.getConnectionResponse(connectionMessage));
					}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_PROTOCOL_UDP)){
						LOGGER.info("Handler mode {}",handlerMode);
						DatagramPacket welcomePacket = new DatagramPacket(ConnectionRequestProcessor.getConnectionResponse(connectionMessage), remoteAddress);
						ctx.writeAndFlush(welcomePacket);
					}
				}
				break;

			case KEEP_ALIVE:
				KeepAlive keepAlive = mapper.readValue(message, KeepAlive.class);
				authenticateDevice = authenticateDevice(keepAlive);
				if(!authenticateDevice.isAuthenticated()){
					updateDevicestatus(keepAlive,DeviceAuthenticationStatus.REJECTED);
					LOGGER.info("Device not authenticate {}, will send rejection response!!",keepAlive.getUnitId());
				}else{
					updateDevicestatus(keepAlive,DeviceAuthenticationStatus.ACCEPTED);
					keepAlive.setHostId(sourceIdentifier.toString());
					LOGGER.info("Device is authenticated {}, will send positive response!!",sourceIdentifier.toString());
				}
				if(header.getRequireAck()){

					if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_PROTOCOL_TCP)){
						ctx.writeAndFlush(KeepAliveProcessor.getKeepAliveResponse(keepAlive));
					}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_PROTOCOL_UDP)){
						DatagramPacket welcomePacket = new DatagramPacket(KeepAliveProcessor.getKeepAliveResponse(keepAlive), remoteAddress);
						ctx.writeAndFlush(welcomePacket);
					}

				}
				break;

			case CONFIG_UPDATE_REQ:
				ConfigurationUpdateMessage configurationUpdateMessage = mapper.readValue(message, ConfigurationUpdateMessage.class);
				authenticateDevice = authenticateDevice(configurationUpdateMessage);
				if(!authenticateDevice.isAuthenticated()){
					updateDevicestatus(configurationUpdateMessage,DeviceAuthenticationStatus.REJECTED);
					LOGGER.info("Device not authenticate {}, will send rejection response!!",configurationUpdateMessage.getUnitId());
				}else{
					updateDevicestatus(configurationUpdateMessage,DeviceAuthenticationStatus.ACCEPTED);
					configurationUpdateMessage.setHostId(sourceIdentifier.toString());
					LOGGER.info("Device is authenticated {}, will send positive response!!",sourceIdentifier.toString());
				}
				if(header.getRequireAck()){

					if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_PROTOCOL_TCP)){
						ctx.writeAndFlush(ConfigurationUpdateRequestProcessor.getConfigurationUpdateResponse(configurationUpdateMessage));
					}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_PROTOCOL_UDP)){
						DatagramPacket welcomePacket = new DatagramPacket(ConfigurationUpdateRequestProcessor.getConfigurationUpdateResponse(configurationUpdateMessage), remoteAddress);
						ctx.writeAndFlush(welcomePacket);
					}

				}
				break;

			case CONFIG_FEED_UPDATE:
				ConfigurationFeed configurationFeed = mapper.readValue(message, ConfigurationFeed.class);

				LOGGER.info("Configuration feed update decoded {}",configurationFeed.getPointDetails());


				authenticateDevice = authenticateDevice(configurationFeed);
				if(!authenticateDevice.isAuthenticated()){
					updateDevicestatus(configurationFeed,DeviceAuthenticationStatus.REJECTED);
					LOGGER.info("Device not authenticate {}, will send rejection response!!",configurationFeed.getUnitId());
				}else{
					updateDevicestatus(configurationFeed,DeviceAuthenticationStatus.ACCEPTED);
					configurationFeed.setHostId(sourceIdentifier.toString());
					LOGGER.info("Device is authenticated {}, will send positive response!!",sourceIdentifier.toString());

					DefaultConfiguration configuration = (DefaultConfiguration) jaceDeviceManager.getConfiguration(sourceIdentifier,jaceGateway);
					List<ConfigPoint> deviceConfigurations = PointConfigurationUtil.extractPointConfigurations(configurationFeed.getPointDetails());
					
					if(!configuration.isConfigured()){
						LOGGER.info("Initital configuration received");
						
						if(!CollectionUtils.isEmpty(deviceConfigurations)){
							configuration.setConfigPoints(deviceConfigurations);
							configuration.addPointMappings(deviceConfigurations);
							configuration.setConfigured(true);
							jaceDeviceManager.setDeviceConfiguration(configuration,jaceGateway);
						}else{
							LOGGER.info("Empty configuration received");
						}
					}else{
						PointComparator pointComparator = new PointComparator();
						Collections.sort(configuration.getConfigPoints(),pointComparator);
						Collections.sort(deviceConfigurations,pointComparator);
						pointComparator = null;
						configuration.setUpdatedPointConfigurations(deviceConfigurations);
						
						LOGGER.info("App status {}",configuration.getConfigurationUpdateStatus());
						if((configuration != null) || !configuration.getConfigPoints().equals(deviceConfigurations)){
							configuration.setConfigPoints(deviceConfigurations);
							configuration.getPointMapping().clear();
							configuration.addPointMappings(configuration.getConfigPoints());
							LOGGER.info("Configuration updated :: New Configuration is :: "+configuration.toString());
							configuration.setConfigurationUpdateStatus(false);
							jaceDeviceManager.refreshDeviceConfiguration(configuration,jaceGateway);
						}
					}
					LOGGER.info("Message for point config {}",new ObjectMapper().writeValueAsString(deviceConfigurations));
					configuration.addPointMappings(deviceConfigurations);
					configuration.setConfigured(true);
					jaceDeviceManager.setDeviceConfiguration(configuration,jaceGateway);
				}
				if(header.getRequireAck()){

					if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_PROTOCOL_TCP)){
						ctx.writeAndFlush(ConfigurationFeedUpdateProcessor.getConfigurationFeedResponse(configurationFeed));
					}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_PROTOCOL_UDP)){
						DatagramPacket welcomePacket = new DatagramPacket(ConfigurationFeedUpdateProcessor.getConfigurationFeedResponse(configurationFeed), remoteAddress);
						ctx.writeAndFlush(welcomePacket);
					}

				}
				break;

			case ALARM:
				AlarmMessage alarmMessage = mapper.readValue(message, AlarmMessage.class);

				authenticateDevice = authenticateDevice(alarmMessage);
				if(!authenticateDevice.isAuthenticated()){
					updateDevicestatus(alarmMessage,DeviceAuthenticationStatus.REJECTED);
					LOGGER.info("Device not authenticate {}, will send rejection response!!",alarmMessage.getUnitId());
				}else{
					updateDevicestatus(alarmMessage,DeviceAuthenticationStatus.ACCEPTED);
					alarmMessage.setHostId(sourceIdentifier.toString());
					LOGGER.info("Device is authenticated {}, will send positive response!!",sourceIdentifier.toString());
				}
				if(header.getRequireAck()){

					if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_PROTOCOL_TCP)){
						ctx.writeAndFlush(AlarmProcessor.getAlarmResponse(alarmMessage));
					}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_PROTOCOL_UDP)){
						DatagramPacket welcomePacket = new DatagramPacket(AlarmProcessor.getAlarmResponse(alarmMessage), remoteAddress);
						ctx.writeAndFlush(welcomePacket);
					}

				}
				break;

			case DATA_FEED_ACK:
				break;

			default:
				LOGGER.info("Unidentified message type");
				break;
			}

		} catch (Exception e) {
			LOGGER.error("Exception processing messages from JACE",e);
			throw new Exception("Exception processing messages from JACE",e);
		}
	}

	private void updateDevicestatus(JaceMessage jaceMessage,DeviceAuthenticationStatus status) {
		jaceMessage.setStatus(status);
		switch (status) {
		case REJECTED:
			jaceMessage.setRemarks("Device is not authorized");
			break;
		case ACCEPTED:
			jaceMessage.setRemarks("Device is authorized");
			DefaultConfiguration configuration = (DefaultConfiguration) jaceDeviceManager.getConfiguration(sourceIdentifier, jaceGateway);
			jaceMessage.setUnitId(configuration.getUnitId());
			break;
		case ONHOLD:
			jaceMessage.setRemarks("Device is on hold");
			break;

		default:
			break;
		}
	}

	private  AuthenticationResponse authenticateDevice(JaceMessage jaceMessage){
		Device device = null;
		DefaultConfiguration configuration = (DefaultConfiguration) jaceDeviceManager.getConfiguration(jaceMessage.getHostId()!=null?jaceMessage.getHostId():jaceMessage.getUnitId(), jaceGateway);
		LOGGER.info("In authentication {},configuration is {}",jaceMessage.getHostId()!=null?jaceMessage.getHostId():jaceMessage.getUnitId(),configuration);
		if(configuration != null){
			device = configuration.getDevice(); 
			sourceIdentifier = device.getSourceId();
			LOGGER.info("Configuration found in cache for device {}",device.getSourceId());
		}else{
			device = new Device();
			device.setSourceId(jaceMessage.getHostId());
			sourceIdentifier = jaceMessage.getHostId();
			device.setUnitId(jaceMessage.getUnitId());
			device.setLatitude(0f);
			device.setLongitude(0f);
			device.setIp(remoteAddress.getAddress().getHostAddress());
			device.setTimezone(jaceMessage.getTimezone());
			device.setConnectedPort(remoteAddress.getPort());
			LOGGER.info("Configuration not found in cache for device {}",device.getSourceId());
		}
		try {
			if (jaceMessage instanceof ConnectionMessage) {
				LOGGER.info("Connection message identitified, setting device properties");
				ConnectionMessage connectionMessage = (ConnectionMessage) jaceMessage;
				device.setURL(connectionMessage.getURL());
				String token = connectionMessage.getKey();
				if(token != null && token.trim().length()>0){
					String decrypt = KeyCypherUtils.decrypt(token);
					device.setUserName(decrypt.split(":")[0]);
					device.setPassword(decrypt.split(":")[0]);
					device.setToken(token);
				}
			}
			AuthenticationResponse authenticationResponse = jaceDeviceManager.authenticate(device, jaceGateway);
			return authenticationResponse;
		} catch (Exception e) {
			LOGGER.info("Error",e);
			AuthenticationResponse authenticationResponse = new AuthenticationResponse();
			authenticationResponse.setAuthenticationStatus(false);
			return authenticationResponse;
		}



	}

}
