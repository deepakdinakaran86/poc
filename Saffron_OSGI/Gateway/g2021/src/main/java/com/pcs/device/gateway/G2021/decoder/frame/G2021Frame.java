package com.pcs.device.gateway.G2021.decoder.frame;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class G2021Frame extends ByteToMessageDecoder {
	

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021Frame.class);
	
	public G2021Frame(){
		setSingleDecode(true);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,List<Object> out) throws Exception {
		if(!buffer.isReadable()){
			LOGGER.info("No Message To Read!!!");
			return;
		}
		out.add(buffer);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelReadComplete();
	}



}
