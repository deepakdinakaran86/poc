package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.connectivity.utils.ConversionUtils;

public class WriteBackListener implements ChannelFutureListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WriteBackListener.class);
	
	private ByteBuf writeBackContent = null;
	private ChannelHandlerContext context = null;
	private byte[] writebackArray = null;
	
	public WriteBackListener(ByteBuf writeBackContent,ChannelHandlerContext context){
		this.writeBackContent = writeBackContent;
		this.context = context;
		this.writebackArray = writeBackContent.array();
	}

	public void operationComplete(ChannelFuture future) throws Exception {
		Integer contentLength = writebackArray.length;
		ByteBuffer buffer = ByteBuffer.allocate(contentLength+2);
		buffer.putShort(contentLength.shortValue());
		buffer.put(writebackArray);
		byte[] writebackData = buffer.array();
		LOGGER.info("Writeback listener ack {}",ConversionUtils.getHex(writebackData));
		writeBackContent = Unpooled.wrappedBuffer(writebackData);
		context.writeAndFlush(writeBackContent);
		
		writebackData = null;
		buffer = null;
	}

}
