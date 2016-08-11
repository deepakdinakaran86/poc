/**
 * 
 */
package com.pcs.device.gateway.ruptela.handler;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import com.pcs.device.gateway.ruptela.constants.RuptelaProtocolConstants;

/**
 * @author pcseg171
 *
 */
public class RuptelaFrame extends LengthFieldBasedFrameDecoder {

	public RuptelaFrame() {
		super(RuptelaProtocolConstants.MAX_FRAME_SIZE, RuptelaProtocolConstants.FRAME_SIZE_OFFSET, RuptelaProtocolConstants.FRAME_SIZE_LENGTH,0,RuptelaProtocolConstants.FRAME_SIZE_LENGTH);
	}
}
