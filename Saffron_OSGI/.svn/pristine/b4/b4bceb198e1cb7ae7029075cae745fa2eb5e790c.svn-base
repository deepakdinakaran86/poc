package com.pcs.device.gateway.ruptela.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.ruptela.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.ruptela.event.notifier.MessageNotifier;
import com.pcs.device.gateway.ruptela.message.header.DeviceMessageHeader;
import com.pcs.device.gateway.ruptela.message.processor.DataProcessor;
import com.pcs.device.gateway.ruptela.message.processor.RuptelaDeviceMessageProcessor;
import com.pcs.device.gateway.ruptela.utils.SupportedDevices;
import com.pcs.device.gateway.ruptela.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.utils.ConversionUtils;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.authentication.AuthenticationResponse;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;

public class RuptelaHandler extends SimpleChannelInboundHandler<Object>{

	private static final Logger LOGGER = LoggerFactory.getLogger(RuptelaHandler.class);

	private Object sourceIdentifier = null;
	private String handlerMode = null;
	private InetSocketAddress remoteAddress = null;

	DeviceManager deviceManager = DeviceManagerUtil.getRuptelaDeviceManager();
	Gateway gateway = SupportedDevices.getGateway(Devices.FMXXX);
	
	
	
	public RuptelaHandler() {
	}
	
	
	public RuptelaHandler(Object[] handlerArgts){
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {

		if (msg == null) {
			return;
		}
		if (msg instanceof DatagramPacket) {
			DatagramPacket packet = (DatagramPacket)msg;
			remoteAddress = packet.sender();
			handlerMode = Connector.CONNECTOR_PROTOCOL_UDP;
			gateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_UDP);
		} else {
			ByteBuf byteBuf = (ByteBuf)msg;
			LOGGER.info("Actual readable bytes {}",byteBuf.readableBytes());
			ByteBuf copy = byteBuf.copy();
			LOGGER.info("Readable bytes channnel read size {}",
					copy.readableBytes());
			processCompleteMessage(ctx, copy);
			byteBuf = null;
		}

	}


	private void processCompleteMessage(ChannelHandlerContext ctx,
			ByteBuf deviceData) throws Exception {

		try {
			
			if (!(ctx.channel() instanceof NioDatagramChannel)) {
				remoteAddress = (InetSocketAddress)ctx.channel().remoteAddress();
				handlerMode = Connector.CONNECTOR_PROTOCOL_TCP;
				gateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_TCP);
			}
			
			ByteBuf copy = deviceData.copy();
			byte[] data = new byte[copy.readableBytes()];
			copy.getBytes(0, data);
			copy = null;
			
			DeviceMessageHeader deviceMessageHeader = DeviceMessageHeader.getInstance(deviceData);
			sourceIdentifier = deviceMessageHeader.getImei();
			
			
			String hexaData = ConversionUtils.getHex(data).toString();
			LOGGER.info("Raw hexaData-> FMXXX from {} is {}",remoteAddress.getAddress().getHostAddress(),hexaData);
			data = null;
			
			Device device = new Device();
			device.setLatitude(0F);
			device.setLongitude(0F);
			device.setGmtOffset(new Short("0"));
			device.setIp(remoteAddress.getAddress().getHostAddress());
			device.setIsPublishing(true);
			device.setSourceId(sourceIdentifier.toString());
			
			AuthenticationResponse authenticate = DeviceManagerUtil.getRuptelaDeviceManager().authenticate(device, gateway);
			if(!authenticate.isAuthenticated()){
				LOGGER.error("Device {} not authenticated, rejecting connection",deviceMessageHeader.getImei());
				ctx.close();
				return;
			}else{
				LOGGER.info("Device {} is authenticated,Trying to fetch configuration from cache...",deviceMessageHeader.getImei());

			}

			RuptelaDeviceMessageProcessor ruptelaDeviceMessageProcessor = null;
			switch (deviceMessageHeader.getMessageType()) {
			case DATA:
				ruptelaDeviceMessageProcessor = new DataProcessor();
				List<Message> messages = ruptelaDeviceMessageProcessor.processRuptelaMessage(hexaData, deviceMessageHeader);
				byte[] serverMessage = ruptelaDeviceMessageProcessor.getServerMessage(null, deviceMessageHeader);
				if (serverMessage != null) {
					ByteBuf ackBuf = Unpooled.wrappedBuffer(serverMessage);
					if (handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_TCP)) {
						ctx.writeAndFlush(ackBuf);
					} else if (handlerMode.equalsIgnoreCase(Connector.CONNECTOR_MODE_UDP)) {
						DatagramPacket ackPacket = new DatagramPacket(ackBuf, remoteAddress);
						ctx.writeAndFlush(ackPacket);
					}
				}
				MessageNotifier.getInstance().notifyMessageReception(messages,(DefaultConfiguration)deviceManager.getConfiguration(sourceIdentifier, gateway),gateway);
				break;

			case CONFIGURATIONRESPONSE:
				break;
			case DEVICEVERSIONRESPONSE:
				break;
			case DEVICEFIRMWAREUPDATERESPONSE:
				break;
			case SMARTCARDDATA:
				break;
			case SMARTCARDDATASIZEANDTIME:
				break;
			case DIAGNOSTICTROUBLECODES:
				break;
			case TACHOGRAPHCOMMUNICATION:
				break;
			case TACHOGRAPHDATAPACKET:
				break;
			case TACHOGRAPHINFORMATION:
				break;
			case GARMINSTATUSRESPONSE:
				break;
			case GARMINDATARESPONSE:
				break;

			default:
				LOGGER.error("Unrecoganized message type");
				break;
			}
		} catch (Exception e) {
			LOGGER.error("Error processing messages",e);
		}



	}

}
