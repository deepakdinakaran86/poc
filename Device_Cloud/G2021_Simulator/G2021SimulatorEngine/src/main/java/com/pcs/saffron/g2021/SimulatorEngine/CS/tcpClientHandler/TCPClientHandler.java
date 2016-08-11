package com.pcs.saffron.g2021.SimulatorEngine.CS.tcpClientHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.exceptions.MessageProcessException;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.ChallengeMessage;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.PointDiscoveryAckMessage;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.ServerMessage;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.WelcomeMessage;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.header.Header;
import com.pcs.saffron.g2021.SimulatorEngine.CS.processor.G2021Processor;
import com.pcs.saffron.g2021.SimulatorEngine.CS.processor.PointDiscoveryAckProcessor;
import com.pcs.saffron.g2021.SimulatorEngine.CS.processor.SeverChallengeProcessor;
import com.pcs.saffron.g2021.SimulatorEngine.CS.processor.WelcomeProcessor;
import com.pcs.saffron.g2021.SimulatorEngine.CS.serverMessageHandler.ServerMessageNotifier;
import com.pcs.saffron.g2021.SimulatorEngine.CS.session.SessionInfo;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.LockUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author Santhosh
 *
 */

public class TCPClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TCPClientHandler.class);
	Header header = null;

	CompositeByteBuf reponseBuffer = ByteBufAllocator.DEFAULT.compositeBuffer();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		reponseBuffer.addComponent((ByteBuf) msg);

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//System.out.println("Message from server Received....");
		byte[] dataArray = new byte[reponseBuffer.capacity()];
		for (int i = 0; i < reponseBuffer.capacity(); i++) {
			dataArray[i] = reponseBuffer.getByte(i);
		}

		ByteBuf completeResonse = Unpooled.wrappedBuffer(dataArray);

		processCompleteMessage(ctx, completeResonse);
		resetDataBuffer();

	}

	private void processCompleteMessage(ChannelHandlerContext ctx, ByteBuf completeResonse) {

		//LOGGER.info("Readable bytes =  ", +completeResonse.readableBytes());
		ServerMessage G2021Message = null;
		G2021Processor messageProcessor = null;

		try {
			header = Header.getInstance(completeResonse.copy());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println(" message type is "+header.getMessageType());	
		
		if(header != null){
			switch (header.getMessageType()) {

			case HELLO:
				LOGGER.info("Hello ACK message received.... ");
				ServerMessageNotifier.getInstance().notifyAcknowledgements(header.getMessageType().name(),completeResonse.array());
				break;

			case CHALLENGE:
				LOGGER.info("Challenge message received.... ");
				ChallengeMessage challengeMsg = null;
				messageProcessor = new SeverChallengeProcessor();
				synchronized (LockUtil.LOCK) {
					try {	
						//LOGGER.info("locked from challenge ");	

						G2021Message = messageProcessor.processG2021Message(completeResonse);
						challengeMsg = (ChallengeMessage) G2021Message;
						//System.out.println("session Id " + challengeMsg.getSessionId());
						SessionInfo.getInstance().setSessionId(challengeMsg.getSessionId());
						SessionInfo.getInstance().setUnitId(challengeMsg.getUnitId());
						SessionInfo.getInstance().setSubscriptionKey(challengeMsg.getSubscriptionKey());
						ServerMessageNotifier.getInstance().notifyAcknowledgements(header.getMessageType().name(),completeResonse.array());		
						LockUtil.LOCK.notify();
						//LOGGER.info("unlocked from challenge  ");
						break;

					} catch (MessageProcessException e) {
						e.printStackTrace();
					} /*catch (InterruptedException e) {

					} */
				}		

			case AUTHENTICATE:			
				LOGGER.info("Authenticate ACK message received.... ");			
				ServerMessageNotifier.getInstance().notifyAcknowledgements(header.getMessageType().name(),completeResonse.array());
				break;


			case WELCOME:
				LOGGER.info("WELCOME message received.... ");
				WelcomeMessage welcomeMsg = null;
				messageProcessor = new WelcomeProcessor();
				synchronized (LockUtil.LOCK) {
					try {
						//LOGGER.info("locked from WELCOME ");									
						G2021Message = messageProcessor.processG2021Message(completeResonse);
						welcomeMsg = (WelcomeMessage) G2021Message;				
						SessionInfo.getInstance().setDport(welcomeMsg.getdPort());
						SessionInfo.getInstance().setDataServerHostType(welcomeMsg.getDataServerHostType());
						SessionInfo.getInstance().setDataServerIp(welcomeMsg.getDataServerIp());
						SessionInfo.getInstance().setDataServerDomainName(welcomeMsg.getDataServerDomainName());
						ServerMessageNotifier.getInstance().notifyAcknowledgements(header.getMessageType().name(),completeResonse.array());
						LockUtil.LOCK.notify();
						//LOGGER.info("unlocked from waiting WELCOME ");	
						break;

					} catch (MessageProcessException e) {
						e.printStackTrace();
					}
				}		


			case POINTDISCOVERYRESPONSE:			
				LOGGER.info("POINTDISCOVERYRESPONSE ACK message received.... ");			
				ServerMessageNotifier.getInstance().notifyAcknowledgements(header.getMessageType().name(),completeResonse.array());
				break;		

			case POINTDISCOVERYACK:
				LOGGER.info("POINTDISCOVERYACK message received.... ");
				PointDiscoveryAckMessage ackMsg = null;
				messageProcessor = new PointDiscoveryAckProcessor();
				synchronized (LockUtil.LOCK) {
					try {
						//LOGGER.info("unlocked from waiting POINTDISCOVERYACK ");		
						G2021Message = messageProcessor.processG2021Message(completeResonse);
						ackMsg = (PointDiscoveryAckMessage) G2021Message;
						//System.out.println("ackMsg.getPdRecordCount()  "+ackMsg.getPdRecordCount());
						SessionInfo.getInstance().setScoreCard(ackMsg.getPdRecordCount());
						ServerMessageNotifier.getInstance().notifyAcknowledgements(header.getMessageType().name(),completeResonse.array());		
						LockUtil.LOCK.notify();
						//LOGGER.info("unlocked from waiting POINTDISCOVERYACK ");
						break;

					} catch (MessageProcessException e) {
						e.printStackTrace();
					}
				}

			case POINTDISCOVERYSCORECARD:
				LOGGER.info("POINTDISCOVERYSCORECARD ACK message received.... ");		
				ServerMessageNotifier.getInstance().notifyAcknowledgements(header.getMessageType().name(),completeResonse.array());
				break;

			case POINTREALTIMEDATA:
				synchronized (LockUtil.LOCK) {
					LOGGER.info("POINTREALTIMEDATA ACK message received.... ");		
					ServerMessageNotifier.getInstance().notifyAcknowledgements(header.getMessageType().name(),completeResonse.array());
					LockUtil.LOCK.notify();
					break;
				}			

			case POINTALARM:
				synchronized (LockUtil.LOCK) {
					LOGGER.info("POINTALARM ACK message received.... ");		
					ServerMessageNotifier.getInstance().notifyAcknowledgements(header.getMessageType().name(),completeResonse.array());
					LockUtil.LOCK.notify();
					break;
				}
			default:
				break;
			}
		}

		

	}

	private void resetDataBuffer() {
		// LOGGER.info("Cleaning residue buffers !!");
		reponseBuffer = ByteBufAllocator.DEFAULT.compositeBuffer();
		// LOGGER.info("Residue buffers are clean !!");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}
