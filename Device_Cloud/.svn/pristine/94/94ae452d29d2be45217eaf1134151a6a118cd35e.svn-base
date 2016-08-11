package com.pcs.device.gateway.meitrack.handler.tcp;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.meitrack.decoder.MeiTrackDecoder;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;



public class MeitrackTCPHandler extends MessageToMessageDecoder<ByteBuf>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MeitrackTCPHandler.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf,
			List<Object> out) throws Exception {
		LOGGER.info("{} bytes received :: ",buf.readableBytes());
		String data = new String(buf.readBytes(buf.readableBytes()).array());
		MeiTrackDecoder decoder = new MeiTrackDecoder("", Calendar.getInstance().getTime(),null);
		decoder.setData(data);
		List<Message> messages = decoder.readMessage();
		for (Message message : messages) {
			System.out.println(message.getTime());
			System.out.println(message.getSourceId());
			System.out.println(message.getReceivedTime());
			System.out.println(message.getReason());
			List<Point> Points = message.getPoints();
			for (Point Point : Points) {
				System.out.println(Point.getPointName() + " " + Point.getData());
			}
		}
	}

}
