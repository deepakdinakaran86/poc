package com.pcs.gateway.teltonika.handler.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.gateway.teltonika.bundle.utils.DeviceManagerUtil;
import com.pcs.gateway.teltonika.constant.ProtocolConstants;
import com.pcs.gateway.teltonika.decoder.FMXXXDecoder;
import com.pcs.gateway.teltonika.message.header.ChannelHeader;
import com.pcs.gateway.teltonika.message.header.PacketHeader;
import com.pcs.gateway.teltonika.message.packet.type.PacketType;
import com.pcs.gateway.teltonika.utils.SupportedDevices;
import com.pcs.gateway.teltonika.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.manager.bean.Device;

public class TeltonikaUDPHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TeltonikaUDPHandler.class);
	private CompositeByteBuf fullData = ByteBufAllocator.DEFAULT.compositeBuffer();
	private InetSocketAddress remoteAddress = null;
	Gateway gateway = SupportedDevices.getGateway(Devices.FMXXX);
	
	public TeltonikaUDPHandler(){

	}

	public TeltonikaUDPHandler(Object[] handlerArgts){
		
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
		LOGGER.info("Data bytes in channel "+dataArray.length);
		for (int i = 0; i < fullData.capacity(); i++) {
			dataArray[i] = fullData.getByte(i);
		}	
		processCompleteMessage(ctx, Unpooled.wrappedBuffer(dataArray));
		resetDataBuffer();
	}

	private void processCompleteMessage(ChannelHandlerContext ctx, ByteBuf deviceData){
		gateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_UDP);
		LOGGER.info("Readable bytes = "+deviceData.readableBytes());
		try {
			ChannelHeader header = ChannelHeader.getInstance(deviceData);
			if(header.getReqAck()){
				ByteBuf ackBuf = Unpooled.wrappedBuffer(getServerChannelAck(header.getPacketId()));
				DatagramPacket ackPacket = new DatagramPacket(ackBuf, remoteAddress);
				ctx.writeAndFlush(ackPacket);
			}
			PacketHeader packetHeader = PacketHeader.getInstance(deviceData);
			if(packetHeader.getReqAck()){
				ByteBuf ackBuf = Unpooled.wrappedBuffer(getServerPacketAck(packetHeader.getPacketId(), packetHeader.getPacketCount()));
				DatagramPacket ackPacket = new DatagramPacket(ackBuf, remoteAddress);
				ctx.writeAndFlush(ackPacket);
			}
			
			Device device = new Device();
			String sourceId = packetHeader.getSourceId();
			device.setSourceId(sourceId);
			device.setDatasourceName(sourceId);
			device.setIsPublishing(true);
			device.setDeviceName(sourceId);
			device.setLatitude(0.0F);
			device.setLongitude(0.0F);
			
			if(DeviceManagerUtil.getTeltonikaDeviceManager().authenticate(device,gateway).isAuthenticated()){
				LOGGER.info("Device authorized to send data");
				FMXXXDecoder decoder = new FMXXXDecoder(remoteAddress.getHostString(), sourceId, Calendar.getInstance().getTime());
				decoder.setData(packetHeader.getData());
				LOGGER.info("{} messages decoded from the {} data packet for device {}",decoder.readMessage(),packetHeader.getData(),sourceId);
			}else{
				LOGGER.error("Device {} not authorized to send data, data will be discarded!!",sourceId);
			}
			device = null;
		} catch (Exception e) {
			LOGGER.error("Error processing message from device",e);
		}finally{
			resetDataBuffer();
		}
	}

	private void resetDataBuffer() {
		LOGGER.info("Cleaning residue buffers !!");
		fullData = ByteBufAllocator.DEFAULT.compositeBuffer();
		LOGGER.info("Residue buffers are clean !!");
	}

	
	private byte[] getServerChannelAck(Integer packetId){
		ByteBuffer ackResponse = ByteBuffer.allocate(ProtocolConstants.CHANNEL_ACK_LENGTH);
		ackResponse.putShort(ProtocolConstants.CHANNEL_ACK_DATA_LENGTH);
		ackResponse.putShort(packetId.shortValue());
		ackResponse.put(PacketType.IDENTIFIED_ACK.getType().byteValue());
		return ackResponse.array();
	
	}
	
	private byte[] getServerPacketAck(Integer packetId,Integer packetCount){
		ByteBuffer ackResponse = ByteBuffer.allocate(ProtocolConstants.PACKET_ACK_LENGTH);
		ackResponse.put(packetId.byteValue());
		ackResponse.put(packetCount.byteValue());
		return ackResponse.array();
	
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		LOGGER.error("Exception Caught ",cause);
	}

}
