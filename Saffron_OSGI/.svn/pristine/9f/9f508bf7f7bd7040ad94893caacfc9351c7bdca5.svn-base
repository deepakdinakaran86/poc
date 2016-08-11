/**
 * 
 */
package com.pcs.device.gateway.jace.handler;

import com.pcs.device.gateway.jace.enums.MessageElements;

import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * @author pcseg171
 *
 */
public class JaceDelimiterHandler extends DelimiterBasedFrameDecoder {
	
	public JaceDelimiterHandler(){
		super(100000, MessageElements.CARRIERRETURN);
	}

}
