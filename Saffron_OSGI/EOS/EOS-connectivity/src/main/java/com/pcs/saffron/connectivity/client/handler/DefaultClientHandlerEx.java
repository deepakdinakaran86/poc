package com.pcs.saffron.connectivity.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DefaultClientHandlerEx extends ChannelInboundHandlerAdapter   {

	CompositeByteBuf reponseBuffer = ByteBufAllocator.DEFAULT.compositeBuffer();
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		reponseBuffer.addComponent((ByteBuf) msg);
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Message from server completed");
		byte[] dataArray = new byte[reponseBuffer.capacity()];
		for (int i = 0; i < reponseBuffer.capacity(); i++) {
			dataArray[i] = reponseBuffer.getByte(i);
		}	
		
		ByteBuf completeResonse = Unpooled.wrappedBuffer(dataArray);
		
		switch (completeResonse.readableBytes()) {
		case 1:
			System.out.println("Response 1 : "+completeResonse.readUnsignedByte());
			break;
		case 4:
			System.out.println("Response 4 : "+completeResonse.readUnsignedInt());
			break;

		default:
			System.out.println("Response "+completeResonse.readableBytes());
			break;
		}
		reponseBuffer = ByteBufAllocator.DEFAULT.compositeBuffer();
		completeResonse = null;
	}
	
	
}
