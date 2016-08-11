package com.pcs.device.gateway.G2021.diagnosis.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.command.G2021Command;
import com.pcs.device.gateway.G2021.command.executor.CommandProcessorDiagnosisEx;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.AlarmProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.AuthenticateProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.ChallengeProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.ExceptionProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.G2021Processor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.HelloProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.LocationProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.PointDiscoveryProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.PointDiscoveryResponseProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.RealTimeDataProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.ScorecardProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.SnapshotProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.SupervisionProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.WelcomeProcessor;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.WriteBackListener;
import com.pcs.device.gateway.G2021.diagnosis.message.processor.WriteResponseProcessor;
import com.pcs.device.gateway.G2021.message.ChallengeMessage;
import com.pcs.device.gateway.G2021.message.ExceptionMessage;
import com.pcs.device.gateway.G2021.message.HelloMessage;
import com.pcs.device.gateway.G2021.message.WelcomeMessage;
import com.pcs.device.gateway.G2021.message.WriteResponseMessage;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.type.Reason;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.devicemanager.authentication.AuthenticationResponse;

/**
 * @author pcseg171
 *
 *
 *This handler would be used for diagnostic purpose alone. 
 *The handler bypasses all the DB interaction including the authentication mechanism.
 */
public class G2021DiagnosticHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021DiagnosticHandler.class);
	private CompositeByteBuf fullData = ByteBufAllocator.DEFAULT.compositeBuffer();
	private Boolean startWithDelay = false;
	private String dataserverIP = "10.234.60.13";
	private int dataserverPort = 95;
	private Object sourceIdentifier = null;
	public static G2021DeviceConfiguration deviceConfiguration = null;
	public G2021DiagnosticHandler(){

	}

	public G2021DiagnosticHandler(Object[] startWithDelay){

		if(startWithDelay != null && startWithDelay.length>=4){
			this.startWithDelay = (Boolean) startWithDelay[0];
			this.dataserverIP = startWithDelay[1].toString();
			this.dataserverPort = (int) startWithDelay[2];
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object obj)
			throws Exception {
		fullData.addComponent((ByteBuf) obj);
	}


	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		byte[] dataArray = new byte[fullData.capacity()];
		LOGGER.info("Data bytes in channel "+dataArray.length);
		for (int i = 0; i < fullData.capacity(); i++) {
			dataArray[i] = fullData.getByte(i);
		}	
		processCompleteMessage(ctx, Unpooled.wrappedBuffer(dataArray));
		fullData = ByteBufAllocator.DEFAULT.compositeBuffer();
	}

	private void processCompleteMessage(ChannelHandlerContext ctx, ByteBuf deviceData)
			throws Exception {

		LOGGER.info("Readable byte = "+deviceData.readableBytes());
		ByteBuf copy = deviceData.copy();


		Header header = Header.getInstance(deviceData); 
		G2021Processor messageProcessor = null;
		Message G2021Message = null;

		if(header == null)
			return;


		ChannelFuture writeAck = null;

		LOGGER.info(header.getMessageType() + " : "+ConversionUtils.getHex(copy.array()).toString());
		copy = null;
		LOGGER.info("==============================================================================");

		switch (header.getMessageType()) {

		case HELLO:
			messageProcessor = new HelloProcessor();

			G2021Message = messageProcessor.processG2021Message(deviceData);
			HelloMessage helloMessage = (HelloMessage) G2021Message;
			sourceIdentifier = helloMessage.getSourceId();
			LOGGER.info("Hello Message Reason : "+helloMessage.getReason());
			if(deviceConfiguration == null || helloMessage.getReason().equalsIgnoreCase(Reason.APP_CHANGED.name())){
				deviceConfiguration = new G2021DeviceConfiguration();
				deviceConfiguration.setSourceIdentifier(sourceIdentifier.toString());
				//((G2021DeviceManager)g2021Manager).refreshDeviceConfiguration(header.getUnitId(), deviceConfiguration);
			}
			if(header.getReqAck()){
				byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header); 
				ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);
				writeAck = ctx.writeAndFlush(ackBuf);
				LOGGER.info("Hello Message Acked!!");
				if(startWithDelay){
					Thread.sleep(900);
				}
			}
			if(deviceConfiguration == null || !deviceConfiguration.checkValidity(header.getUnitId(), header.getSessionId(), helloMessage.getCcKey())){

				AuthenticationResponse authenticate = mockAuthenticate(header.getUnitId()); //do local diagnositic authentication.//g2021Manager.authenticate(helloMessage.getSourceId().toString());
				if(authenticate.isAuthenticated()){
					Double randomU = Math.random();
					Integer unitId = randomU.intValue();
					Double random = Math.random();
					Integer sessionId = random.intValue();

					ChallengeProcessor challengeProcessor = new ChallengeProcessor();
					ChallengeMessage challengeMessage = new ChallengeMessage();
					challengeMessage.setSessionId(sessionId);
					challengeMessage.setUnitId(unitId);
					challengeMessage.setSubscriptionKey("New Key");
					challengeMessage.setSessionTimeout(15);
					ByteBuf challengeData = Unpooled.wrappedBuffer(challengeProcessor.getChallengeMessage(challengeMessage));
					if(writeAck != null)
						writeAck.addListener(new WriteBackListener(challengeData, ctx));	
				}
			}else{// device already authenticated
				ByteBuf welcomeBuf = null;
				WelcomeProcessor welcomeProcessor = new WelcomeProcessor();
				WelcomeMessage welcomeMessage = new WelcomeMessage();
				welcomeMessage.setDataserverDomainName("pcs.com");

				welcomeMessage.setDataserverIP(dataserverIP);
				Integer port = dataserverPort;
				Integer dataserverType = 0;
				welcomeMessage.setDataserverPort(port.shortValue());
				welcomeMessage.setDataserverType(dataserverType.byteValue());
				Long currentTimeMillis = System.currentTimeMillis()/1000;
				welcomeMessage.setServerTimestamp(currentTimeMillis.intValue());
				welcomeBuf = Unpooled.wrappedBuffer(welcomeProcessor.getWelcomeMessage(welcomeMessage));
				ctx.writeAndFlush(welcomeBuf);
				LOGGER.info("Welcome message sent!!");
			}
			break;

		case AUTHENTICATE:
			messageProcessor = new AuthenticateProcessor();
			G2021Message = messageProcessor.processG2021Message(deviceData);
			com.pcs.device.gateway.G2021.message.G2021Message authenticateMessage = new com.pcs.device.gateway.G2021.message.G2021Message();
			ByteBuf welcomeBuf = null;
			if(authenticateMessage.getAuthenticationStatus()){
				WelcomeProcessor welcomeProcessor = new WelcomeProcessor();
				WelcomeMessage welcomeMessage = new WelcomeMessage();
				welcomeMessage.setDataserverDomainName("pcs.com");

				welcomeMessage.setDataserverIP(dataserverIP);
				Integer port = dataserverPort;
				Integer dataserverType = 0;
				welcomeMessage.setDataserverPort(port.shortValue());
				welcomeMessage.setDataserverType(dataserverType.byteValue());
				Long currentTimeMillis = System.currentTimeMillis()/1000;
				welcomeMessage.setServerTimestamp(currentTimeMillis.intValue());
				welcomeBuf = Unpooled.wrappedBuffer(welcomeProcessor.getWelcomeMessage(welcomeMessage));
			}

			if(header.getReqAck()){
				byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header);
				if(serverMessage != null){
					ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);
					writeAck = ctx.writeAndFlush(ackBuf);
					if(startWithDelay){
						Thread.sleep(900);
					}
					if(welcomeBuf != null && writeAck != null){
						writeAck.addListener(new WriteBackListener(welcomeBuf, ctx));	
					}
				}

			}

			break;

		case POINTDISCOVERYRESPONSE:
			messageProcessor = new PointDiscoveryProcessor(header.getUnitId());
			G2021Message = messageProcessor.processG2021Message(deviceData);
			LOGGER.info("Point Discovery Processed "+G2021Message.getPoints().size());

			PointDiscoveryResponseProcessor responseProcessor = new PointDiscoveryResponseProcessor();
			byte[] response = responseProcessor.getResponse(G2021Message);
			ByteBuf writeBackContent = Unpooled.wrappedBuffer(response);

			if(header.getReqAck()){
				byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header);
				if(serverMessage != null){

					ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);
					writeAck = ctx.writeAndFlush(ackBuf);
					LOGGER.info("discovery ack sent");
					if(startWithDelay){
						Thread.sleep(900);
					}

					if(writeBackContent != null && writeAck != null) {
						writeAck.addListener(new WriteBackListener(writeBackContent, ctx));	
					}

					writeBackContent = null;
					response = null;
					responseProcessor = null;
				}
			}

			break;

		case POINTDISCOVERYSCORECARD:
			messageProcessor = new ScorecardProcessor(header.getUnitId(),sourceIdentifier);
			G2021Message = messageProcessor.processG2021Message(deviceData);
			LOGGER.info("Discovery Sync completed ");
			if(header.getReqAck()){
				byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header);
				if(serverMessage != null){

					ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);
					ctx.writeAndFlush(ackBuf);
					LOGGER.info("scorecard ack sent");
					if(startWithDelay){
						Thread.sleep(900);
					}
				}
			}

			break;

		case SUPERVISION:
			messageProcessor = new SupervisionProcessor();
			messageProcessor.processG2021Message(deviceData);
			LOGGER.info("Processed Supervision...");
			break;

		case EXCEPTIONMESSAGE:
			messageProcessor = new ExceptionProcessor();
			G2021Message = messageProcessor.processG2021Message(deviceData);
			ExceptionMessage exceptionMessage = (ExceptionMessage) G2021Message;

			switch (exceptionMessage.getExceptionType()) {
			case MANUAL_SESSION_TERMINATIION:
				LOGGER.info("Manual termination/close request identified...closing connection!!");
				ctx.close();
				break;

			case POINT_DISCOVERY_FAILED:
				LOGGER.info("Point Discovery Failed...closing connection!!");
				ctx.close();
				break;

			case POINT_DISCOVERY_DISABLED:
				LOGGER.info("Point Discovery Disabled (in the device)...closing connection!!");
				ctx.close();
				break;

			default:
				break;
			}

			break;

			// Data server communication starts here//


		case POINTREALTIMEDATA:
			try {
				messageProcessor = new RealTimeDataProcessor(deviceConfiguration);
				G2021Message = messageProcessor.processG2021Message(deviceData);
				if(G2021Message != null){
					G2021Message.setSourceId(header.getUnitId());
				}
				if(header.getReqAck()){
					byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header);
					if(serverMessage != null){
						ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);
						ctx.writeAndFlush(ackBuf);
						LOGGER.info("realtime ack sent");
						if(startWithDelay){
							Thread.sleep(900);
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("Exception in realtime message!!",e);
			}
			
			List<G2021Command> deviceCommands = getDeviceCommand();
			for (G2021Command command : deviceCommands) {
				command.setSessionId(header.getSessionId());
				command.setUnitId(header.getUnitId());
				ctx.writeAndFlush(Unpooled.wrappedBuffer(command.getServerMessage()));
			}
			
			clearDeviceCommand(deviceCommands);
			
			break;

		case POINTALARM://single alarm message
			try {
				messageProcessor = new AlarmProcessor(deviceConfiguration);
				G2021Message = messageProcessor.processG2021Message(deviceData);
				if(G2021Message != null){
					G2021Message.setSourceId(header.getUnitId());
				}
				if(header.getReqAck()){
					byte[] serverMessage = messageProcessor.getServerMessage(G2021Message, header);
					if(serverMessage != null){
						ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);
						ctx.writeAndFlush(ackBuf);
						LOGGER.info("alarm ack sent");
						if(startWithDelay){
							Thread.sleep(900);
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("Exception in realtime message!!",e);
			}

			
			List<G2021Command> deviceCommandsALR = getDeviceCommand();
			for (G2021Command command : deviceCommandsALR) {
				command.setSessionId(header.getSessionId());
				command.setUnitId(header.getUnitId());
				ctx.writeAndFlush(Unpooled.wrappedBuffer(command.getServerMessage()));
			}
			
			clearDeviceCommand(deviceCommandsALR);
			
			break;
			
		case LOCATION:
			try {
				messageProcessor = new LocationProcessor();
				G2021Message = messageProcessor.processG2021Message(deviceData);
				if(G2021Message != null){
					G2021Message.setSourceId(header.getUnitId());
				}
			} catch (Exception e) {
				LOGGER.error("Exception in realtime message!!",e);
			}
			
			List<G2021Command> deviceCommandsLOC = getDeviceCommand();
			for (G2021Command command : deviceCommandsLOC) {
				command.setSessionId(header.getSessionId());
				command.setUnitId(header.getUnitId());
				ctx.writeAndFlush(Unpooled.wrappedBuffer(command.getServerMessage()));
			}
			
			clearDeviceCommand(deviceCommandsLOC);
			
			break;

		case POINTSNAPSHOTUPLOAD:
			try {
				messageProcessor = new SnapshotProcessor(deviceConfiguration);
				G2021Message = messageProcessor.processG2021Message(deviceData);
				
				if(G2021Message != null){
					G2021Message.setSourceId(header.getUnitId());
					LOGGER.info("Message received from the device is :"+G2021Message.toString());
				}
				
			} catch (Exception e) {
				LOGGER.error("Exception in realtime message!!",e);
			}

			
			List<G2021Command> deviceCommandsSNP = getDeviceCommand();
			for (G2021Command command : deviceCommandsSNP) {
				command.setSessionId(header.getSessionId());
				command.setUnitId(header.getUnitId());
				ctx.writeAndFlush(Unpooled.wrappedBuffer(command.getServerMessage()));
			}
			
			clearDeviceCommand(deviceCommandsSNP);
			
			break;

		case POINTWRITERESPONSE:
			messageProcessor = new WriteResponseProcessor();
			G2021Message = messageProcessor.processG2021Message(deviceData);
			WriteResponseMessage responseMessage = (WriteResponseMessage) G2021Message;
			LOGGER.info("Write response received!!!"+responseMessage.getStatus().name());

			
			List<G2021Command> deviceCommandsWRT = getDeviceCommand();
			for (G2021Command command : deviceCommandsWRT) {
				command.setSessionId(header.getSessionId());
				command.setUnitId(header.getUnitId());
				ctx.writeAndFlush(Unpooled.wrappedBuffer(command.getServerMessage()));
			}
			
			clearDeviceCommand(deviceCommandsWRT);
			
			break;

		default:
			break;
		}
		
	}


	private void clearDeviceCommand(List<G2021Command> deviceCommands) {
		CommandProcessorDiagnosisEx.getCommands().removeAll(deviceCommands);
	}

	private AuthenticationResponse mockAuthenticate(Integer unitId) {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setAuthenticationMessage("Mock authentication done!");
		authenticationResponse.setAuthenticationStatus(true);
		return authenticationResponse;
	}

	private List<G2021Command> getDeviceCommand() throws Exception{
		try {
			return CommandProcessorDiagnosisEx.getCommands();
		} catch (Exception e) {
			LOGGER.error("Exception in getting device command",e);
			throw new Exception("Cannot get device commands");
		}
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		LOGGER.error("Exception Caught ",cause);
	}

}
