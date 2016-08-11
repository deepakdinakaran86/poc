package com.pcs.device.gateway.G2021.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.beans.UnregisteredConfiguration;
import com.pcs.device.gateway.G2021.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.G2021.command.SyncCommandDispatcher;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.event.notifier.MessageNotifier;
import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.ChallengeMessage;
import com.pcs.device.gateway.G2021.message.ExceptionMessage;
import com.pcs.device.gateway.G2021.message.G2021WriteResponseMessage;
import com.pcs.device.gateway.G2021.message.HelloMessage;
import com.pcs.device.gateway.G2021.message.ScorecardMessage;
import com.pcs.device.gateway.G2021.message.WelcomeMessage;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.processor.AlarmProcessor;
import com.pcs.device.gateway.G2021.message.processor.AuthenticateProcessor;
import com.pcs.device.gateway.G2021.message.processor.ChallengeProcessor;
import com.pcs.device.gateway.G2021.message.processor.ExceptionProcessor;
import com.pcs.device.gateway.G2021.message.processor.G2021Processor;
import com.pcs.device.gateway.G2021.message.processor.HelloProcessor;
import com.pcs.device.gateway.G2021.message.processor.LocationProcessor;
import com.pcs.device.gateway.G2021.message.processor.PointDiscoveryProcessor;
import com.pcs.device.gateway.G2021.message.processor.PointDiscoveryResponseProcessor;
import com.pcs.device.gateway.G2021.message.processor.RealTimeDataProcessor;
import com.pcs.device.gateway.G2021.message.processor.ScorecardProcessor;
import com.pcs.device.gateway.G2021.message.processor.SnapshotProcessor;
import com.pcs.device.gateway.G2021.message.processor.SupervisionProcessor;
import com.pcs.device.gateway.G2021.message.processor.WelcomeProcessor;
import com.pcs.device.gateway.G2021.message.processor.WriteBackListener;
import com.pcs.device.gateway.G2021.message.processor.WriteResponseProcessor;
import com.pcs.device.gateway.G2021.message.type.Reason;
import com.pcs.device.gateway.G2021.utils.SupportedDevices;
import com.pcs.device.gateway.G2021.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.utils.ConversionUtils;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.authentication.AuthenticationResponse;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;
import com.pcs.saffron.manager.bean.WriteResponseMessage;
import com.pcs.saffron.manager.bean.WritebackConfiguration;

public class G2021Handler extends ChannelInboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021Handler.class);
	private CompositeByteBuf fullData = ByteBufAllocator.DEFAULT.compositeBuffer();
	private Boolean startWithDelay = false;
	private Integer dataserverPort = 95;
	private Object sourceIdentifier = null;
	private String handlerMode = null;
	private InetSocketAddress remoteAddress = null;
	private final DeviceManager g2021Manager = DeviceManagerUtil.getG2021DeviceManager(); 
	Header header = null;
	Gateway gateway = SupportedDevices.getGateway(Devices.EDCP);

	public G2021Handler(){

	}

	public G2021Handler(Object[] handlerArgts){

		if(handlerArgts != null && handlerArgts.length>=3){
			this.startWithDelay = (Boolean) handlerArgts[0];
			this.handlerMode = handlerArgts[1].toString();
		}else{
			LOGGER.error("Invalid handler configuration !! exiting");
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object obj)
			throws Exception {
		if (obj instanceof DatagramPacket) {
			DatagramPacket packet = (DatagramPacket) obj;
			fullData.addComponent(packet.content());
			remoteAddress = packet.sender();
		}else{
			ByteBuf byteBuf = (ByteBuf) obj;
			ByteBuf copy = byteBuf.copy();
			LOGGER.info("Readable bytes channnel read size {}",copy.readableBytes());
			fullData.addComponent(byteBuf);
		}
	}


	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		byte[] dataArray = new byte[fullData.capacity()];
		for (int i = 0; i < fullData.capacity(); i++) {
			dataArray[i] = fullData.getByte(i);
		}	
		processCompleteMessage(ctx, Unpooled.wrappedBuffer(dataArray));
		resetDataBuffer();
	}

	private void processCompleteMessage(ChannelHandlerContext ctx, ByteBuf deviceData){

		LOGGER.info("Readable bytes = {}, device manager {}",deviceData.readableBytes(),g2021Manager);

		gateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_UDP);
		ByteBuf copy = deviceData.copy();

		G2021Processor messageProcessor = null;
		Message G2021Message = null;

		try {
			header = Header.getInstance(deviceData);
		} catch (Exception e) {
			LOGGER.error("Unspecified message type received !!!",e);
		} 

		if(header == null)
			return;


		ChannelFuture writeAck = null;
		if (!(ctx.channel() instanceof NioDatagramChannel)) {
			remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
			gateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_TCP);
		}
		LOGGER.info("Message From device {}, Message Type :{}, Raw Data ::{} N/W Protocol :: {}!!",
				remoteAddress,header.getMessageType(),ConversionUtils.getHex(copy.array()),gateway.getCommunicationProtocol());
		copy = null;
		LOGGER.info("==============================================================================");


		//would be null only if not existing in the cache, in which case authentication would fetch the configuration during hello message
		String deviceIp = remoteAddress.getAddress().getHostAddress();
		int devicePort = remoteAddress.getPort();

		gateway.setConnectedPort(devicePort);
		gateway.setDeviceIP(deviceIp);
		gateway.setWritebackPort(Integer.valueOf(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.COMMAND_WRITE_PORT)));
		DefaultConfiguration deviceConfiguration = (DefaultConfiguration)g2021Manager.getConfiguration(header.getUnitId(),gateway);
		switch (header.getMessageType()) {

		case HELLO:
			HelloMessage helloMessage = null;
			try {
				messageProcessor = new HelloProcessor();
				G2021Message = messageProcessor.processG2021Message(deviceData);
				helloMessage = (HelloMessage) G2021Message;
				sourceIdentifier = helloMessage.getSourceId();
				LOGGER.info("Hello Message Reason : {} : source id : {}",helloMessage.getReason(),sourceIdentifier);

				if(header.getReqAck()){
					byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header); 
					ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);
					if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)){
						writeAck = ctx.writeAndFlush(ackBuf);
						if(startWithDelay){
							Thread.sleep(900);
						}
					}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_UDP)){
						DatagramPacket ackPacket = new DatagramPacket(ackBuf, remoteAddress);
						ctx.writeAndFlush(ackPacket);
					}
					LOGGER.info("Hello Ack Send For Device {} ",helloMessage.getSourceId());
				}
				if(deviceConfiguration == null || !deviceConfiguration.checkValidity(header.getUnitId(), header.getSessionId(), helloMessage.getCcKey())){
					Device device = new Device();
					device.setSourceId(helloMessage.getSourceId().toString());
					device.setLatitude(helloMessage.getLatitude());
					device.setLongitude(helloMessage.getLongitude());
					device.setGmtOffset(helloMessage.getGmtOffset());
					device.setTimezone(helloMessage.getTimezone());
					device.setTags(helloMessage.getClientInfo());
					AuthenticationResponse authenticate = g2021Manager.authenticate(device,gateway);
					if(authenticate.isAuthenticated()){
						LOGGER.error("Device is Authorized !!");
						Integer unitId = (Integer) authenticate.getSessionInfo().getPrincipal();
						Integer sessionId = authenticate.getSessionInfo().getSessionId();

						ChallengeProcessor challengeProcessor = new ChallengeProcessor();
						ChallengeMessage challengeMessage = new ChallengeMessage();
						challengeMessage.setSessionId(sessionId);
						challengeMessage.setUnitId(unitId);
						challengeMessage.setSubscriptionKey("Default");//authenticate.getSubscriptionKey() implementation changed for the hardware team
						challengeMessage.setSessionTimeout(authenticate.getSessionInfo().getSessionTimeOut().intValue());
						ByteBuf challengeData = Unpooled.wrappedBuffer(challengeProcessor.getChallengeMessage(challengeMessage));
						deviceConfiguration = (DefaultConfiguration)g2021Manager.getConfiguration(unitId,gateway);
						deviceConfiguration.setSessionId(sessionId);
						
						if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)){
							if(writeAck != null)
								writeAck.addListener(new WriteBackListener(challengeData, ctx));	
							else
								LOGGER.error("Error in connection !!! \n Connection error ");
						}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_UDP)){
							DatagramPacket challengePacket = new DatagramPacket(challengeData, remoteAddress);
							ctx.writeAndFlush(challengePacket);
						}
						
					}else{
						LOGGER.error("Device Not Authorized !!");
					}
					device = null;
				}else{// device already authenticated
					LOGGER.info("Inside pre authenticated unitid {}, sessionid{}",header.getUnitId(), header.getSessionId());
					ByteBuf welcomeBuf = null;
					WelcomeProcessor welcomeProcessor = new WelcomeProcessor();
					WelcomeMessage welcomeMessage = prepareWelcomeMessage();
					welcomeBuf = Unpooled.wrappedBuffer(welcomeProcessor.getWelcomeMessage(welcomeMessage));

					if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)){
						ctx.writeAndFlush(welcomeBuf);
					}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_UDP)){
						DatagramPacket welcomePacket = new DatagramPacket(welcomeBuf, remoteAddress);
						ctx.writeAndFlush(welcomePacket);
					}
					deviceConfiguration = (DefaultConfiguration)g2021Manager.getConfiguration(header.getUnitId(),gateway);
				}
			} catch (MessageProcessException | InterruptedException  m) {
				LOGGER.error("Exception processing hello message",m);
			}finally{
				resetDataBuffer();
			}
			if(deviceConfiguration != null){
				deviceConfiguration.setDeviceIP(deviceIp);
				deviceConfiguration.setDeviceConnectedPort(devicePort);
				deviceConfiguration.setCommunicationMode(handlerMode);
				
				if(helloMessage.getCause().equals(Reason.APP_CHANGED)){
					LOGGER.info("Application change detected");
					deviceConfiguration.setConfigurationUpdateStatus(true);
					g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
				}
				
				g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			}else{
				LOGGER.info("Device configuration not found, unauthorized !!");
			}

			break;

		case AUTHENTICATE:
			try {

				messageProcessor = new AuthenticateProcessor();
				G2021Message = messageProcessor.processG2021Message(deviceData);
				com.pcs.device.gateway.G2021.message.G2021Message authenticateMessage = new com.pcs.device.gateway.G2021.message.G2021Message();
				ByteBuf welcomeBuf = null;
				if(authenticateMessage.getAuthenticationStatus()){
					if(deviceConfiguration != null){
						deviceConfiguration.setRegistered(true);
					}
					WelcomeProcessor welcomeProcessor = new WelcomeProcessor();
					WelcomeMessage welcomeMessage = prepareWelcomeMessage();
					welcomeBuf = Unpooled.wrappedBuffer(welcomeProcessor.getWelcomeMessage(welcomeMessage));
				}

				if(header.getReqAck()){
					byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header);
					if(serverMessage != null){
						ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);

						if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)){
							writeAck = ctx.writeAndFlush(ackBuf);
							LOGGER.info("Authentication Ack Send");
							if(startWithDelay){
								Thread.sleep(900);
							}
							if(welcomeBuf != null && writeAck != null){
								writeAck.addListener(new WriteBackListener(welcomeBuf, ctx));	
							}
						}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_UDP)){
							DatagramPacket ackPacket = new DatagramPacket(ackBuf, remoteAddress);
							ctx.writeAndFlush(ackPacket);
							LOGGER.info("Authentication Ack Send");
							DatagramPacket welcomePacket = new DatagramPacket(welcomeBuf, remoteAddress);
							ctx.writeAndFlush(welcomePacket);
						}

					}

				}
			} catch (MessageProcessException | InterruptedException m) {
				LOGGER.error("Exception processing authenticate message",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection(gateway)){
				g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			}
			break;

		case POINTDISCOVERYRESPONSE:

			try {

				checkAndSyncConfig(header, deviceConfiguration,deviceIp);

				messageProcessor = new PointDiscoveryProcessor(header.getUnitId(), gateway);
				G2021Message = messageProcessor.processG2021Message(deviceData);

				PointDiscoveryResponseProcessor responseProcessor = new PointDiscoveryResponseProcessor();
				byte[] response = responseProcessor.getResponse(G2021Message);
				ByteBuf writeBackContent = Unpooled.wrappedBuffer(response);
				deviceConfiguration = (DefaultConfiguration)g2021Manager.getConfiguration(header.getUnitId(),gateway);
				if(header.getReqAck()){
					byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header);
					if(serverMessage != null){

						ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);

						if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)){
							writeAck = ctx.writeAndFlush(ackBuf);
							LOGGER.info("discovery ack sent");
							if(startWithDelay){
								Thread.sleep(900);
							}

							if(writeBackContent != null && writeAck != null) {
								writeAck.addListener(new WriteBackListener(writeBackContent, ctx));	
							}
						}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_UDP)){
							DatagramPacket ackPacket = new DatagramPacket(ackBuf, remoteAddress);
							ctx.writeAndFlush(ackPacket);
							LOGGER.info("discovery ack sent");

							DatagramPacket discoveryAck = new DatagramPacket(writeBackContent, remoteAddress);
							ctx.writeAndFlush(discoveryAck);
						}



						writeBackContent = null;
						response = null;
						responseProcessor = null;
					}
				}
			} catch (MessageProcessException | InterruptedException m) {
				LOGGER.error("Exception processing authenticate message",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection(gateway)){
				g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			}
			break;

		case POINTDISCOVERYSCORECARD:

			try {

				checkAndSyncConfig(header, deviceConfiguration,deviceIp);

				messageProcessor = new ScorecardProcessor(header.getUnitId(),sourceIdentifier,gateway);
				ScorecardMessage message = (ScorecardMessage) messageProcessor.processG2021Message(deviceData);
				LOGGER.info("Discovery Sync completed ");
				if(header.getReqAck()){
					byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header);
					if(serverMessage != null){

						ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);

						if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)){
							ctx.writeAndFlush(ackBuf);
							if(startWithDelay){
								Thread.sleep(900);
							}
						}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_UDP)){
							DatagramPacket ackPacket = new DatagramPacket(ackBuf, remoteAddress);
							ctx.writeAndFlush(ackPacket);
						}

						LOGGER.info("scorecard ack sent");

					}
				}
				if(message.isConfigurationChanged()){//configuration update detected and is triggered to manager
					deviceConfiguration = (DefaultConfiguration)g2021Manager.getConfiguration(header.getUnitId(),gateway);
					g2021Manager.setDeviceConfiguration(deviceConfiguration, gateway);
					if(deviceConfiguration.getDevice().getIsPublishing() && deviceConfiguration.getDevice().getDatasourceName() == null){
						g2021Manager.registerDatasource(deviceConfiguration,gateway);
					}
				}
			} catch (MessageProcessException | InterruptedException m) {
				LOGGER.error("Exception processing scorecard",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection( gateway)){
				g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			}
			break;

		case SUPERVISION:
			try {
				messageProcessor = new SupervisionProcessor();
				messageProcessor.processG2021Message(deviceData);
				//LOGGER.info("Processed Supervision...");
			} catch (MessageProcessException m) {
				LOGGER.error("Exception processing supervision",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection(gateway)){
				g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			}
			break;

		case EXCEPTIONMESSAGE:
			try {
				messageProcessor = new ExceptionProcessor();
				G2021Message = messageProcessor.processG2021Message(deviceData);
				ExceptionMessage exceptionMessage = (ExceptionMessage) G2021Message;

				switch (exceptionMessage.getExceptionType()) {
				case MANUAL_SESSION_TERMINATIION:
					LOGGER.info("Manual termination/close request identified...closing connection!!");
					ctx.close();
					ctx.disconnect();
					break;

				case POINT_DISCOVERY_FAILED:
					LOGGER.info("Point Discovery Failed...closing connection!!");
					ctx.close();
					ctx.disconnect();
					break;

				case POINT_DISCOVERY_DISABLED:
					LOGGER.info("Point Discovery Disabled (in the device)...closing connection!!");
					ctx.close();
					ctx.disconnect();
					break;

				default:
					break;
				}

			} catch (MessageProcessException m) {
				LOGGER.error("Exception processing supervision",m);
			}finally{
				resetDataBuffer();
			}
			break;

			// Data server communication starts here//

		case POINTREALTIMEDATA:
			try {

				checkAndSyncConfig(header, deviceConfiguration,deviceIp);

				messageProcessor = new RealTimeDataProcessor(deviceConfiguration);
				G2021Message = messageProcessor.processG2021Message(deviceData);

				if(header.getReqAck()){
					byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header);
					if(serverMessage != null){
						ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);

						if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)){
							ctx.writeAndFlush(ackBuf);
							LOGGER.info("realtime ack sent :: "+ConversionUtils.getHex(serverMessage));
							if(startWithDelay){
								Thread.sleep(900);
							}
						}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_UDP)){
							DatagramPacket ackPacket = new DatagramPacket(ackBuf, remoteAddress);
							ctx.writeAndFlush(ackPacket);
							LOGGER.info("realtime ack sent :: "+ConversionUtils.getHex(serverMessage));
						}

					}
				}

				if(G2021Message != null){
					G2021Message.setSourceId(deviceConfiguration.getDevice().getSourceId());
					List<Message> messages = new ArrayList<Message>();
					messages.add(G2021Message);
					MessageNotifier.getInstance().notifyMessageReception(messages,deviceConfiguration,gateway);
				}
			} catch (MessageProcessException | InterruptedException m) {
				LOGGER.error("Exception processing realtime message",m);
			}finally{
				resetDataBuffer();
			}


			if(isNewConnection(gateway)){
				g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			}
			break;

		case POINTALARM://single alarm message

			try {

				checkAndSyncConfig(header, deviceConfiguration,deviceIp);				
				messageProcessor = new AlarmProcessor(deviceConfiguration);
				G2021Message = messageProcessor.processG2021Message(deviceData);
				if(G2021Message != null){
					//G2021Message.setSourceId(deviceConfiguration.getDevice().getSourceId());//Enabling publisher to identify the device
					g2021Manager.notifyAlarm((AlarmMessage) G2021Message,gateway);
				}
				if(header.getReqAck()){
					byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header);
					if(serverMessage != null){
						ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);

						if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)){
							ctx.writeAndFlush(ackBuf);
							LOGGER.info("alarm ack sent :: "+ConversionUtils.getHex(serverMessage));
							if(startWithDelay){
								Thread.sleep(900);
							}
						}else if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_UDP)){
							DatagramPacket ackPacket = new DatagramPacket(ackBuf, remoteAddress);
							ctx.writeAndFlush(ackPacket);
							LOGGER.info("alarm ack sent :: "+ConversionUtils.getHex(serverMessage));
						}
					}
				}

			} catch (MessageProcessException | InterruptedException m) {
				LOGGER.error("Exception processing alarm message",m);
			}finally{
				resetDataBuffer();
			}


			if(isNewConnection(gateway)){
				g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			}
			break;

		case LOCATION:

			try {
				checkAndSyncConfig(header, deviceConfiguration,deviceIp);

				messageProcessor = new LocationProcessor();
				G2021Message = messageProcessor.processG2021Message(deviceData);
				if(G2021Message != null){
					//G2021Messag));
					List<Message> messages = new ArrayList<Message>();
					messages.add(G2021Message);
					MessageNotifier.getInstance().notifyMessageReception(messages,deviceConfiguration,gateway);
					/*RealtimeMessageNotifier.publish(G2021Message.toString());
					g2021Manager.publishData(G2021Message, deviceConfiguration);*/
				}
			} catch (MessageProcessException m) {
				LOGGER.error("Exception processing alarm message",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection(gateway)){
				g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			}
			break;

		case POINTSNAPSHOTUPLOAD:
			try {

				checkAndSyncConfig(header, deviceConfiguration,deviceIp);

				messageProcessor = new SnapshotProcessor(deviceConfiguration);
				G2021Message = messageProcessor.processG2021Message(deviceData);

				if(G2021Message != null){
					G2021Message.setSourceId(deviceConfiguration.getDevice().getSourceId());
					List<Message> messages = new ArrayList<Message>();
					messages.add(G2021Message);
					MessageNotifier.getInstance().notifyMessageReception(messages,deviceConfiguration,gateway);
					/*RealtimeMessageNotifier.publish(G2021Message.toString());
					g2021Manager.publishData(G2021Message, deviceConfiguration);*/
				}
			} catch (MessageProcessException m) {
				LOGGER.error("Exception processing snapshot upload message",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection(gateway)){
				g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			}
			break;

		case POINTWRITERESPONSE:

			try {

				checkAndSyncConfig(header, deviceConfiguration,deviceIp);

				messageProcessor = new WriteResponseProcessor();
				G2021Message = messageProcessor.processG2021Message(deviceData);
				G2021WriteResponseMessage responseMessage = (G2021WriteResponseMessage) G2021Message;
				WriteResponseMessage writeResponse = new WriteResponseMessage();
				writeResponse.setpId(responseMessage.getpId());
				writeResponse.setRemark(responseMessage.getReason());
				writeResponse.setReqId(responseMessage.getReqId());
				writeResponse.setSourceId(deviceConfiguration.getSourceIdentifier().toString());
				writeResponse.setStatus(responseMessage.getStatus().getStatusIndicator());
				writeResponse.setTime(responseMessage.getTime());

				LOGGER.info("WRITE RESPONSE FROM {}, THE RESPONSE IS {}, FOR POINT-ID {} !!",deviceConfiguration.getSourceIdentifier().toString()
						,responseMessage.getStatus().name(),responseMessage.getpId());
				LOGGER.info("Write response received!!!"+responseMessage.getStatus().name());

				g2021Manager.sendWriteResponse(writeResponse);
				writeResponse = null;
				responseMessage = null;
			} catch (MessageProcessException m) {
				LOGGER.error("Exception processing alarm message",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection(gateway)){
				g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			}
			break;

		default:
			break;
		}

	}

	private void checkAndSyncConfig(Header header,
			DefaultConfiguration deviceConfiguration,String deviceIP) {
		if(deviceConfiguration == null){
			UnregisteredConfiguration configuration = new UnregisteredConfiguration();
			configuration.setDeviceIP(deviceIP);
			configuration.setDeviceMode(handlerMode);
			configuration.setSessionId(header.getSessionId());
			configuration.setUnitId(header.getUnitId());
			LOGGER.info("Cannot find device configuration in cache, would require resync for unit:{} with session:{}",header.getUnitId(), header.getSessionId());
			SyncCommandDispatcher.releaseSyncCommand(configuration);
		}
	}

	private void resetDataBuffer() {
		//LOGGER.info("Cleaning residue buffers !!");
		fullData = ByteBufAllocator.DEFAULT.compositeBuffer();
		//LOGGER.info("Residue buffers are clean !!");
	}

	private WelcomeMessage prepareWelcomeMessage() {
		WelcomeMessage welcomeMessage = new WelcomeMessage();
		Integer dataserverType = 0;
		if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)){
			welcomeMessage.setDataserverDomainName(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_SERVER_DOMAIN));
			welcomeMessage.setDataserverIP(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_SERVER_IP));
			dataserverPort = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_SERVER_PORT));
			dataserverType = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_SERVER_TYPE));
		}else{
			welcomeMessage.setDataserverDomainName(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_SERVER_DOMAIN));
			welcomeMessage.setDataserverIP(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_SERVER_IP));
			dataserverPort = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_SERVER_PORT));
			dataserverType = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_SERVER_TYPE));
		}

		welcomeMessage.setDataserverPort(dataserverPort.shortValue());
		welcomeMessage.setDataserverType(dataserverType.byteValue());
		Long currentTimeMillis = System.currentTimeMillis()/1000;
		welcomeMessage.setServerTimestamp(currentTimeMillis.intValue());
		return welcomeMessage;
	}

	private boolean isNewConnection(Gateway gateway) {
		boolean newConnectionRequest = false;
		DefaultConfiguration deviceConfiguration = (DefaultConfiguration)g2021Manager.getConfiguration(header.getUnitId(),gateway);
		if(deviceConfiguration == null){
			LOGGER.info("No configuration received and hence cannot be updated !!!");
			return false;
		}
		if(deviceConfiguration.getDeviceIP() == null || deviceConfiguration.getDeviceConnectedPort() == null){

			LOGGER.info("New connection detected from device {} @ {} which had no previous connection history",gateway.getDeviceIP(),
					gateway.getConnectedPort());

			deviceConfiguration.setDeviceIP(gateway.getDeviceIP());
			deviceConfiguration.setDeviceConnectedPort(gateway.getConnectedPort());
			deviceConfiguration.setCommunicationMode(handlerMode);
			return true;
		}


		if( !(deviceConfiguration.getDeviceIP().equalsIgnoreCase(gateway.getDeviceIP()))||
				!(deviceConfiguration.getDeviceConnectedPort().equals(gateway.getConnectedPort()))){
			LOGGER.info("In because !deviceConfiguration.getDeviceIP().equalsIgnoreCase(gateway.getDeviceIP()) value = {}"
					+ "or {} != {} values ={}",!(deviceConfiguration.getDeviceIP().equalsIgnoreCase(gateway.getDeviceIP())),
					deviceConfiguration.getDeviceConnectedPort(),
					gateway.getConnectedPort(),
					deviceConfiguration.getDeviceConnectedPort() != gateway.getConnectedPort());
			LOGGER.info("New connection detected from device {} @ {} Existing Connection details IP:{},Port:{}",gateway.getDeviceIP(),
					gateway.getConnectedPort(),deviceConfiguration.getDeviceIP(),deviceConfiguration.getDeviceConnectedPort());
			deviceConfiguration.setDeviceIP(gateway.getDeviceIP());
			deviceConfiguration.setCommunicationMode(handlerMode);
			deviceConfiguration.setDeviceConnectedPort(gateway.getConnectedPort());
			newConnectionRequest = true;
		}
		if(newConnectionRequest && header != null){

			WritebackConfiguration writebackConfiguration = new WritebackConfiguration();
			String writebackPortId = G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.COMMAND_WRITE_PORT);
			Integer writebackPort = writebackPortId==null?0:Integer.parseInt(writebackPortId);
			writebackConfiguration.setWriteBackPort(writebackPort);
			writebackConfiguration.setConnectedPort(gateway.getConnectedPort());
			writebackConfiguration.setIp(gateway.getDeviceIP());
			writebackConfiguration.setSourceId(deviceConfiguration.getDevice().getSourceId());
			g2021Manager.updateDeviceWritebackConfig(writebackConfiguration,gateway);
			LOGGER.info("Updating writeback configutation of device {} to cache {}",header.getUnitId(),deviceConfiguration.getDeviceWritebackPort());
			g2021Manager.refreshDeviceConfiguration(deviceConfiguration,gateway);
			writebackPort = null;
			writebackPortId = null;
		}else{
			if(header != null)
				LOGGER.info("This is not a new connection");
			else
				LOGGER.info("Cannnot update new connection, header information not available (Some error in flow please do an inspection!!!)");
		}
		return newConnectionRequest;
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		LOGGER.info("Exception in connection to device {} , {}",sourceIdentifier,gateway.getDeviceIP());
		LOGGER.error("Exception Caught !!",cause);
		resetDataBuffer();
		if(sourceIdentifier != null)
			g2021Manager.invalidateSession(sourceIdentifier,gateway);
	}

}
