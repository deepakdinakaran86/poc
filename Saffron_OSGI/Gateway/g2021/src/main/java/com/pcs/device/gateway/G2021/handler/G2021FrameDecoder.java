/**
 * 
 */
package com.pcs.device.gateway.G2021.handler;

import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import com.pcs.device.gateway.G2021.handler.state.DecoderState;

/**
 * @author pcseg171
 *
 */
public class G2021FrameDecoder extends ReplayingDecoder<DecoderState> {

	private static final Logger LOGGER = LoggerFactory.getLogger(G2021FrameDecoder.class);
	public G2021FrameDecoder() {
		super(DecoderState.WAITING_FOR_PACKET_SIZE);
	}

	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buff,
			List<Object> out) throws Exception {

		switch (state()) {
		case WAITING_FOR_PACKET_SIZE:
			try {
				if(!(buff.readableBytes() >0)){
					LOGGER.error("No data found in the buffer, possible connection close !!! Connection Termination request");
					//throw new Exception("Connection failure from remote!!");
				}
				String hostAddress = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
				LOGGER.info("Transaction notification from client from {} at {} ",hostAddress,Calendar.getInstance().getTime());
				Integer packetSize = 0;
				LOGGER.info("Going to read size");
				packetSize = buff.readUnsignedShort();
				LOGGER.info("Read size {}",packetSize);
				if(packetSize >0){
					LOGGER.info("readable packet size is {} from device {}",packetSize,buff.readableBytes(),hostAddress);
					ByteBuf byteBuf = buff.readBytes(packetSize);
					checkpoint(DecoderState.WAITING_FOR_PACKET_SIZE);
					out.add(byteBuf);
					break;
				}else{
					LOGGER.info("No sufficient bytes to read {}",packetSize);
				}
			} catch (Exception e) {
				LOGGER.error("Error while waiting for messages ",e);
			}
			

		default:
			break;
		}

	}

}
