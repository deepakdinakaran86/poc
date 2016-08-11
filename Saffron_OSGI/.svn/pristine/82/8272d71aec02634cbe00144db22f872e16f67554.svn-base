package com.pcs.device.gateway.jace.message.processor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class WriteBackListener implements ChannelFutureListener {
	
	private ByteBuf writeBackContent = null;
	private ChannelHandlerContext context = null;
	
	public WriteBackListener(ByteBuf writeBackContent,ChannelHandlerContext context){
		this.writeBackContent = writeBackContent;
		this.context = context;
	}

	public void operationComplete(ChannelFuture future) throws Exception {
		context.writeAndFlush(writeBackContent);
	}

}
