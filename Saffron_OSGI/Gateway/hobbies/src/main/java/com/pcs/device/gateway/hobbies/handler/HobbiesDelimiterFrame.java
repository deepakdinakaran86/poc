package com.pcs.device.gateway.hobbies.handler;

import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

public class HobbiesDelimiterFrame extends DelimiterBasedFrameDecoder{
	
	public HobbiesDelimiterFrame(){
		super(4096, Delimiters.lineDelimiter());
	}

}
