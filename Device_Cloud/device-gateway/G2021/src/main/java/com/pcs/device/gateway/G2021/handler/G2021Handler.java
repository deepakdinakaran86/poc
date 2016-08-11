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

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.G2021DeviceManager;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.device.bean.WritebackConfiguration;
import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.ChallengeMessage;
import com.pcs.device.gateway.G2021.message.ExceptionMessage;
import com.pcs.device.gateway.G2021.message.HelloMessage;
import com.pcs.device.gateway.G2021.message.WelcomeMessage;
import com.pcs.device.gateway.G2021.message.WriteResponseMessage;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.notification.AlarmNotifier;
import com.pcs.device.gateway.G2021.message.notification.RealtimeMessageNotifier;
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
import com.pcs.deviceframework.connection.Connector;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.devicemanager.DeviceManager;
import com.pcs.deviceframework.devicemanager.authentication.AuthenticationResponse;

public class G2021Handler extends ChannelInboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021Handler.class);
	private CompositeByteBuf fullData = ByteBufAllocator.DEFAULT.compositeBuffer();
	private Boolean startWithDelay = false;
	private Integer dataserverPort = 95;
	private Object sourceIdentifier = null;
	private String handlerMode = null;
	private InetSocketAddress remoteAddress = null;
	private final DeviceManager g2021Manager = G2021DeviceManager.instance(); 

	public G2021Handler(){

	}

	public G2021Handler(Object[] handlerArgts){

		if(handlerArgts != null && handlerArgts.length>=3){
			this.startWithDelay = (Boolean) handlerArgts[0];
			this.handlerMode = handlerArgts[1].toString();
		}else{
			LOGGER.error("Invalid handler configuration !! exiting");
			System.exit(1);
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
			fullData.addComponent((ByteBuf) obj);
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

		LOGGER.info("Readable bytes = "+deviceData.readableBytes());
		ByteBuf copy = deviceData.copy();
		Header header = null;
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
		}
		LOGGER.info("Message From device {}, Message Type :{}, Raw Data ::{} !!",remoteAddress,header.getMessageType(),ConversionUtils.getHex(copy.array()));
		copy = null;
		LOGGER.info("==============================================================================");


		//would be null only if not existing in the cache, in which case authentication would fetch the configuration during hello message
		G2021DeviceConfiguration deviceConfiguration = (G2021DeviceConfiguration)g2021Manager.getConfiguration(header.getUnitId());
		String deviceIp = remoteAddress.getAddress().getHostAddress();
		int devicePort = remoteAddress.getPort();
		switch (header.getMessageType()) {

		case HELLO:
			HelloMessage helloMessage = null;
			try {
				messageProcessor = new HelloProcessor();
				G2021Message = messageProcessor.processG2021Message(deviceData);
				helloMessage = (HelloMessage) G2021Message;
				sourceIdentifier = helloMessage.getSourceId();
				LOGGER.info("Hello Message Reason : "+helloMessage.getCause()+" : source id : "+sourceIdentifier);
				
				
					

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
				}
				if(deviceConfiguration == null || !deviceConfiguration.checkValidity(header.getUnitId(), header.getSessionId(), helloMessage.getCcKey())){
					AuthenticationResponse authenticate = g2021Manager.authenticate(helloMessage.getSourceId().toString());
					if(authenticate.isAuthenticated()){
						
						// persist client info.
						if (CollectionUtils.isNotEmpty(helloMessage.getClientInfo())) {
							G2021DeviceManager.instance().updateDeviceTags(helloMessage.getClientInfo(),helloMessage.getSourceId().toString());
						}
						
						Integer unitId = (Integer) authenticate.getSessionInfo().getPrincipal();
						Integer sessionId = authenticate.getSessionInfo().getSessionId();

						ChallengeProcessor challengeProcessor = new ChallengeProcessor();
						ChallengeMessage challengeMessage = new ChallengeMessage();
						challengeMessage.setSessionId(sessionId);
						challengeMessage.setUnitId(unitId);
						challengeMessage.setSubscriptionKey(authenticate.getSubscriptionKey());
						challengeMessage.setSessionTimeout(authenticate.getSessionInfo().getSessionTimeOut().intValue());
						ByteBuf challengeData = Unpooled.wrappedBuffer(challengeProcessor.getChallengeMessage(challengeMessage));
						deviceConfiguration = (G2021DeviceConfiguration)g2021Manager.getConfiguration(unitId);
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
					deviceConfiguration = (G2021DeviceConfiguration)g2021Manager.getConfiguration(header.getUnitId());
				}
			} catch (MessageProcessException | InterruptedException m) {
				LOGGER.error("Exception processing hello message",m);
			}finally{
				resetDataBuffer();
			}

			deviceConfiguration.setClientIp(deviceIp);
			deviceConfiguration.setClientControlserverPort(devicePort);
			deviceConfiguration.setCommunicationMode(handlerMode);
			if(helloMessage.getCause().equals(Reason.APP_CHANGED)){
				LOGGER.info("Application change detected");
				deviceConfiguration.setAppChanged(true);
				((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(sourceIdentifier, deviceConfiguration);
			}
			((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);

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

			if(isNewConnection(deviceConfiguration, deviceIp, devicePort)){
				((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);
			}
			break;

		case POINTDISCOVERYRESPONSE:

			try {

				messageProcessor = new PointDiscoveryProcessor(header.getUnitId());
				G2021Message = messageProcessor.processG2021Message(deviceData);

				PointDiscoveryResponseProcessor responseProcessor = new PointDiscoveryResponseProcessor();
				byte[] response = responseProcessor.getResponse(G2021Message);
				ByteBuf writeBackContent = Unpooled.wrappedBuffer(response);

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

			if(isNewConnection(deviceConfiguration, deviceIp, devicePort)){
				((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);
			}
			break;

		case POINTDISCOVERYSCORECARD:

			try {
				messageProcessor = new ScorecardProcessor(header.getUnitId(),sourceIdentifier);
				G2021Message = messageProcessor.processG2021Message(deviceData);
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
				if(deviceConfiguration.getDevice().getDatasourceName() == null){
					((G2021DeviceManager)g2021Manager).registerDatasource(header.getUnitId(),deviceConfiguration);
				}
			} catch (MessageProcessException | InterruptedException m) {
				LOGGER.error("Exception processing scorecard",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection(deviceConfiguration, deviceIp, devicePort)){
				((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);
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

			if(isNewConnection(deviceConfiguration, deviceIp, devicePort)){
				((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);
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
					G2021Message.setSourceId(deviceConfiguration.getDevice().getDeviceId());
					((G2021DeviceManager)g2021Manager).publishData(G2021Message, deviceConfiguration);
					RealtimeMessageNotifier.publish(G2021Message.toString());
				}
			} catch (MessageProcessException | InterruptedException m) {
				LOGGER.error("Exception processing realtime message",m);
			}finally{
				resetDataBuffer();
			}


			if(isNewConnection(deviceConfiguration, deviceIp, devicePort)){
				((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);
			}
			break;

		case POINTALARM://single alarm message

			try {
				messageProcessor = new AlarmProcessor(deviceConfiguration);
				G2021Message = messageProcessor.processG2021Message(deviceData);
				if(G2021Message != null){
					G2021Message.setSourceId(deviceConfiguration.getDevice().getDeviceId());
					AlarmNotifier.publish(G2021Message.toString());
					((G2021DeviceManager)g2021Manager).publishAlarm(G2021Message, deviceConfiguration);
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


			if(isNewConnection(deviceConfiguration, deviceIp, devicePort)){
				((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);
			}
			break;

		case LOCATION:

			try {
				messageProcessor = new LocationProcessor();
				G2021Message = messageProcessor.processG2021Message(deviceData);
				if(G2021Message != null){
					G2021Message.setSourceId(deviceConfiguration.getDevice().getDeviceId());
					RealtimeMessageNotifier.publish(G2021Message.toString());
					((G2021DeviceManager)g2021Manager).publishData(G2021Message, deviceConfiguration);
				}
			} catch (MessageProcessException m) {
				LOGGER.error("Exception processing alarm message",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection(deviceConfiguration, deviceIp, devicePort)){
				((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);
			}
			break;

		case POINTSNAPSHOTUPLOAD:
			try {
				messageProcessor = new SnapshotProcessor(deviceConfiguration);
				G2021Message = messageProcessor.processG2021Message(deviceData);

				if(G2021Message != null){
					G2021Message.setSourceId(deviceConfiguration.getDevice().getDeviceId());
					RealtimeMessageNotifier.publish(G2021Message.toString());
					((G2021DeviceManager)g2021Manager).publishData(G2021Message, deviceConfiguration);
				}
			} catch (MessageProcessException m) {
				LOGGER.error("Exception processing snapshot upload message",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection(deviceConfiguration, deviceIp, devicePort)){
				((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);
			}
			break;

		case POINTWRITERESPONSE:

			try {
				messageProcessor = new WriteResponseProcessor();
				G2021Message = messageProcessor.processG2021Message(deviceData);
				WriteResponseMessage responseMessage = (WriteResponseMessage) G2021Message;
				LOGGER.info("WRITE RESPONSE FROM {}, THE RESPONSE IS {}, FOR POINT-ID {} !!",sourceIdentifier,responseMessage.getStatus().name(),responseMessage.getpId());
				LOGGER.info("Write response received!!!"+responseMessage.getStatus().name());
				responseMessage.setSourceId(deviceConfiguration.getSourceIdentifier());
				((G2021DeviceManager)g2021Manager).sendWriteResponse(responseMessage);
			} catch (MessageProcessException m) {
				LOGGER.error("Exception processing alarm message",m);
			}finally{
				resetDataBuffer();
			}

			if(isNewConnection(deviceConfiguration, deviceIp, devicePort)){
				((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);
			}
			break;

		default:
			break;
		}

	}

	private void resetDataBuffer() {
		//LOGGER.info("Cleaning residue buffers !!");
		fullData.release();
		fullData = ByteBufAllocator.DEFAULT.compositeBuffer();
		//LOGGER.info("Residue buffers are clean !!");
	}

	private WelcomeMessage prepareWelcomeMessage() {
		WelcomeMessage welcomeMessage = new WelcomeMessage();
		if(handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)){
			welcomeMessage.setDataserverDomainName(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_SERVER_DOMAIN));
			welcomeMessage.setDataserverIP(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_SERVER_IP));
			dataserverPort = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.TCP_DATA_SERVER_PORT));
		}else{
			welcomeMessage.setDataserverDomainName(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_SERVER_DOMAIN));
			welcomeMessage.setDataserverIP(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_SERVER_IP));
			dataserverPort = Integer.parseInt(G2021GatewayConfiguration.getProperty(G2021GatewayConfiguration.UDP_DATA_SERVER_PORT));
		}


		Integer dataserverType = 0;
		welcomeMessage.setDataserverPort(dataserverPort.shortValue());
		welcomeMessage.setDataserverType(dataserverType.byteValue());
		Long currentTimeMillis = System.currentTimeMillis()/1000;
		welcomeMessage.setServerTimestamp(currentTimeMillis.intValue());
		return welcomeMessage;
	}

	private boolean isNewConnection(G2021DeviceConfiguration deviceConfiguration, String deviceIp,int devicePort) {
		boolean newConnectionRequest = false;

		if(deviceConfiguration.getClientIp() == null || deviceConfiguration.getClientControlserverPort() == null){
			deviceConfiguration.setClientIp(deviceIp);
			deviceConfiguration.setClientControlserverPort(devicePort);
			deviceConfiguration.setCommunicationMode(handlerMode);
			return true;
		}


		if(deviceConfiguration.getClientIp() != null && !deviceConfiguration.getClientIp().equalsIgnoreCase(deviceIp)
				||
		   deviceConfiguration.getClientControlserverPort() != null && deviceConfiguration.getClientControlserverPort() != devicePort){
			deviceConfiguration.setClientIp(deviceIp);
			deviceConfiguration.setCommunicationMode(handlerMode);
			newConnectionRequest = true;
		}
		if(newConnectionRequest){
			WritebackConfiguration writebackConfiguration = new WritebackConfiguration();
			writebackConfiguration.setWritebackPort(deviceConfiguration.getDevice().getWriteBackPort());
			writebackConfiguration.setConnectedPort(devicePort);
			writebackConfiguration.setIp(remoteAddress.getHostName());
			writebackConfiguration.setSourceId(deviceConfiguration.getDevice().getSourceId());
			((G2021DeviceManager)g2021Manager).updateDeviceWritebackConfig(writebackConfiguration);
		}
		return newConnectionRequest;
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		LOGGER.error("Exception Caught !!",cause);
		resetDataBuffer();
		if(sourceIdentifier != null)
			g2021Manager.invalidateSession(sourceIdentifier);
	}

}
