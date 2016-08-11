package com.pcs.device.gateway.meitrack.handler.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.meitrack.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.meitrack.decoder.mvt.MeiTrackDecoder;
import com.pcs.device.gateway.meitrack.utils.SupportedDevices;
import com.pcs.device.gateway.meitrack.utils.SupportedDevices.Devices;
import com.pcs.gateway.meitrack.event.notifier.MessageNotifier;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.manager.authentication.AuthenticationResponse;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;



public class MeitrackTCPHandler extends MessageToMessageDecoder<ByteBuf>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MeitrackTCPHandler.class);

	Gateway gateway = SupportedDevices.getGateway(Devices.MVTXXX);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf,
			List<Object> out) throws Exception {
		gateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_TCP);
		LOGGER.info("{} bytes received :: ",buf.readableBytes());
		String data = new String(buf.readBytes(buf.readableBytes()).array());
		InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		gateway.setDeviceIP(remoteAddress.getHostName());
		gateway.setConnectedPort(remoteAddress.getPort());
		gateway.setWritebackPort(remoteAddress.getPort());
		gateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_TCP);
		
		MeiTrackDecoder decoder = new MeiTrackDecoder(remoteAddress.getAddress().getHostAddress(), Calendar.getInstance().getTime());
		decoder.setData(data);
		
		Device device = new Device();
		device.setSourceId(decoder.getImei());
		device.setDatasourceName(decoder.getImei());
		device.setIsPublishing(true);
		device.setDeviceName(decoder.getImei());
		device.setLatitude(0.0F);
		device.setLongitude(0.0F);
		
		AuthenticationResponse authenticateResponse = DeviceManagerUtil.getMeitrackDeviceManager().authenticate(device, gateway);
		
		device = null;
		
		if(authenticateResponse.isAuthenticated()){
			List<Message> messages = decoder.readMessage();
			MessageNotifier.getInstance().notifyMessageReception(messages,(DefaultConfiguration)DeviceManagerUtil.getMeitrackDeviceManager().getConfiguration(decoder.getImei(), gateway),gateway);
		}else{
			LOGGER.error("Invalid device {} seeking connection from IP {} !! \n Terminating connection.",decoder.getImei(),remoteAddress.getAddress().getHostAddress());
			ctx.channel().disconnect(ctx.channel().newPromise());
		}
		
	}

}
