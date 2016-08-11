package com.pcs.device.gateway.G2021.handler;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

import java.nio.ByteBuffer;

public class G2021DelimiterHandler extends DelimiterBasedFrameDecoder {
	
	private static final byte[] delimiters = new byte[]{(byte) 0xAF};

	private static ByteBuffer byteBuf = ByteBuffer.allocate(delimiters.length);
	static {
		for (int i = 0; i < delimiters.length; i++) {
			byteBuf.put(delimiters[i]);
		}
	}
	
	public G2021DelimiterHandler(Object[] args){
		super((int)args[0],(Boolean)args[1],(Boolean)args[2],Unpooled.wrappedBuffer(byteBuf.array()));
	}

}
