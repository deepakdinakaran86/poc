package com.pcs.device.gateway.hobbies.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.hobbies.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.hobbies.config.HobbiesGatewayConfiguration;
import com.pcs.device.gateway.hobbies.message.HobbiesMessage;
import com.pcs.device.gateway.hobbies.message.HobbiesRegistrationResponse;
import com.pcs.device.gateway.hobbies.message.processor.DataMessageProcessor;
import com.pcs.device.gateway.hobbies.message.processor.RegistrationMessageProcessor;
import com.pcs.device.gateway.hobbies.message.type.HobbiesMessageType;
import com.pcs.device.gateway.hobbies.utils.SupportedDevices;
import com.pcs.device.gateway.hobbies.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.authentication.AuthenticationResponse;
import com.pcs.saffron.manager.bean.Asset;
import com.pcs.saffron.manager.bean.Device;
import com.pcs.saffron.manager.bean.Tag;
import com.pcs.saffron.manager.registration.bean.DeviceUser;

public class HobbiesHandler extends SimpleChannelInboundHandler<Object>{

	private static final Logger LOGGER = LoggerFactory.getLogger(HobbiesHandler.class);
	private static final DeviceManager hobbiesDeviceManager = DeviceManagerUtil.getHobbiesDeviceManager();
	private static Gateway hobbiesGateway = null;
	private InetSocketAddress remoteAddress = null;
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf messageBuf = (ByteBuf) msg;
		ByteBuf copy = messageBuf.copy();
		byte[] data = new byte[copy.readableBytes()];
		copy.getBytes(0, data);
		String message = new String(data);
		LOGGER.info("Data from device {}",message);
		ObjectMapper mapper = new ObjectMapper();
		HobbiesMessage hobbiesMessage = mapper.readValue(message, HobbiesMessage.class);
		processMessage(hobbiesMessage,ctx);
		messageBuf = null;
		msg = null;
		
	}
	

	private void processMessage(HobbiesMessage hobbiesMessage, ChannelHandlerContext ctx){
		
		HobbiesMessageType messageType = HobbiesMessageType.valueOfIndicator(hobbiesMessage.getMessageType());

		LOGGER.info("Message Type {}",messageType);

		if(hobbiesMessage.getDevice().getMake().equalsIgnoreCase(HobbiesGatewayConfiguration.APPLE_VENDOR)){
			hobbiesGateway = SupportedDevices.getGateway(Devices.IOS);
		}else{
			hobbiesGateway = SupportedDevices.getGateway(Devices.ANDROID);
		}
		remoteAddress = (InetSocketAddress)ctx.channel().remoteAddress();
		hobbiesGateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_TCP);

		

		AuthenticationResponse authenticationResponse = authenticateDevice(hobbiesMessage);
		switch (messageType) {
		case RESGISTRATION:
			LOGGER.info("Received registration message {}",hobbiesMessage.getEmail());
			HobbiesRegistrationResponse hobbiesRegistrationResponse = new HobbiesRegistrationResponse();
			hobbiesRegistrationResponse.setMessageType(HobbiesMessageType.REGISTRATION_RESPONSE.name());
			if(!authenticationResponse.isAuthenticated()){
				hobbiesRegistrationResponse.setStatus("failure");
				try {
					ctx.writeAndFlush(Unpooled.wrappedBuffer(new ObjectMapper().writeValueAsBytes(hobbiesRegistrationResponse)));
					ctx.writeAndFlush(Unpooled.wrappedBuffer("\n".getBytes()));
				} catch (JsonProcessingException e) {
					LOGGER.info("Invalid registration response",e);
				}
				LOGGER.error("Hobbies device :- {} is not authenticated, rejecting connection",hobbiesMessage.getDevice().getDeviceId());
				ctx.close();
				return;
			}else{
				LOGGER.info("Hobbies device :- {} is authenticated.",hobbiesMessage.getDevice().getDeviceId());
				hobbiesRegistrationResponse.setStatus("success");
				try {
					ctx.writeAndFlush(Unpooled.wrappedBuffer(new ObjectMapper().writeValueAsBytes(hobbiesRegistrationResponse)));
					ctx.writeAndFlush(Unpooled.wrappedBuffer("\n".getBytes()));
				} catch (JsonProcessingException e) {
					LOGGER.info("Invalid registration response",e);
				}
				RegistrationMessageProcessor messageProcessor = new RegistrationMessageProcessor();
				messageProcessor.processMessage(hobbiesMessage, hobbiesGateway);
				
			}
			break;
			
		case DATA:
			if(!authenticationResponse.isAuthenticated()){
				LOGGER.error("Hobbies device :- {} is not authenticated, rejecting connection",hobbiesMessage.getDevice().getDeviceId());
				ctx.close();
				return;
			}else{
				LOGGER.info("Processing data message");
				DataMessageProcessor dataMessageProcessor = new  DataMessageProcessor();
				dataMessageProcessor.processMessage(hobbiesMessage, hobbiesGateway);
				
			}

			break;

		default:
			break;
		}

	}

	private  AuthenticationResponse authenticateDevice(HobbiesMessage hobbiesMessage){
		LOGGER.info("Authenticating hobby device using manager {}",hobbiesDeviceManager);

		Device device = new Device();
		device.setSourceId(hobbiesMessage.getDevice().getDeviceId());
		List<Tag> tags = new ArrayList<Tag>(Arrays.asList(new Tag[10]));
		
		Tag superTenantTag = new Tag();
		superTenantTag.setName(hobbiesMessage.getManufacturer());
		tags.add(0,superTenantTag);
		
		Tag assetType = new Tag(hobbiesGateway.getType().toUpperCase());
		Tag assetName = new Tag(hobbiesMessage.getDevice().getDeviceId());
		Tag assetId = new Tag(hobbiesMessage.getDevice().getDeviceId());
		Tag serialNumber = new Tag(hobbiesMessage.getDevice().getOs()+hobbiesMessage.getDevice().getDeviceId());
		Tag make = new Tag(hobbiesGateway.getVendor());
		Tag model = new Tag(hobbiesGateway.getModel());
		Tag createAsset = new Tag("CREATEASSET");
		tags.add(4,make);
		tags.add(5,model);
		tags.add(6,assetId);
		tags.add(7,serialNumber);
		tags.add(8,assetType);
		tags.add(9,assetName);
		tags.add(10,createAsset);
		device.setTags(tags);
		
		DeviceUser deviceUser = new DeviceUser();
		deviceUser.setContactNumber(hobbiesMessage.getContactNumber());
		deviceUser.setEmail(hobbiesMessage.getEmail());
		deviceUser.setLastname(hobbiesMessage.getUserName());
		deviceUser.setUsername(hobbiesMessage.getUserName());
		deviceUser.setUserExisting(hobbiesMessage.isUserExisting());
		device.setDeviceUser(deviceUser);
		
		
		Asset asset = new Asset();
		asset.setAssetId(assetId.getName());
		asset.setAssetName(assetName.getName());
		asset.setSerialNumber(serialNumber.getName());
		asset.setTags(hobbiesMessage.getDevice().getTags());
		device.setAsset(asset);
		
		device.setLatitude(hobbiesMessage.getDevice().getLatitude()!=null?hobbiesMessage.getDevice().getLatitude().floatValue():null);
		device.setLongitude(hobbiesMessage.getDevice().getLongitude()!=null?hobbiesMessage.getDevice().getLongitude().floatValue():null);
		device.setIp(remoteAddress.getAddress().getHostAddress());
		device.setConnectedPort(remoteAddress.getPort());
		return hobbiesDeviceManager.authenticate(device, hobbiesGateway);

	}
}
