package com.pcs.saffron.teltonika.simulator.connector.tcp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.teltonika.simulator.connector.tcp.TCPSimulator;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TeltonikaSimulatorHandlerEx extends ChannelInboundHandlerAdapter   {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TeltonikaSimulatorHandlerEx.class);
	

	CompositeByteBuf reponseBuffer = ByteBufAllocator.DEFAULT.compositeBuffer();
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		reponseBuffer.addComponent((ByteBuf) msg);
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

		byte[] dataArray = new byte[reponseBuffer.capacity()];
		for (int i = 0; i < reponseBuffer.capacity(); i++) {
			dataArray[i] = reponseBuffer.getByte(i);
		}	
		
		ByteBuf completeResonse = Unpooled.wrappedBuffer(dataArray);
		
		switch (completeResonse.readableBytes()) {
		case 1:
			short imeiresponse = completeResonse.readUnsignedByte();
			if(imeiresponse != 1){
				LOGGER.info("Server says: Device not authorized!!");
				ctx.channel().close();
				System.exit(0);
				TCPSimulator.authorized = false;
			}else{
				LOGGER.info("Server says: Device is authorized,waiting for data");
				TCPSimulator.authorized = true;
			}
			break;
		case 4:
			long packetCountAtServer = completeResonse.readUnsignedInt();
			LOGGER.info("Server says: {} packets received ",packetCountAtServer);
			break;

		default:
			LOGGER.info("Response "+completeResonse.readableBytes());
			break;
		}
		reponseBuffer = ByteBufAllocator.DEFAULT.compositeBuffer();
		completeResonse = null;
	}
	
	
}
