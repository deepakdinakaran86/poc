package com.pcs.device.gateway.ruptela.handler.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.ruptela.message.state.RuptelaStates;

public class RuptelaTCPHandler  extends ReplayingDecoder<RuptelaStates> {
	
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RuptelaTCPHandler.class);

	public RuptelaTCPHandler() {
		super(RuptelaStates.INTIATED);
	}
	

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf,
			List<Object> out) throws Exception {

		short packetSize = 0;
		
		switch (state()) {

		case INTIATED:
			packetSize = buf.readShort();
			/*
			 * read the packet size which excludes the CRC hence the packetsize+2 while reading.
			 */
			if(packetSize > 0) {
				try {
					if(buf.readableBytes() >= packetSize) {
						out.add(buf.readBytes(packetSize+2));
						checkpoint(RuptelaStates.INTIATED);
					}else{
						return;
					}
					
				} catch (Exception e) {
					LOGGER.error("Error reading packet size",e);
				}
			}else {
				LOGGER.error("Zero bytes send from the device, Resetting buffers!!");
				return;
			}
			break;
			
		default:
			LOGGER.error("In Default state::{}",state());
			break;
		}
		return;
	
		
		
	}

}
