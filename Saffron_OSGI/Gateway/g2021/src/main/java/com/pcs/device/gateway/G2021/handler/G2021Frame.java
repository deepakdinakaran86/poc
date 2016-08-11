/**
 * 
 */
package com.pcs.device.gateway.G2021.handler;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import com.pcs.device.gateway.G2021.utils.G2021ProtocolConstants;

/**
 * @author pcseg171
 *
 */
public class G2021Frame extends LengthFieldBasedFrameDecoder {

	public G2021Frame() {
		super(G2021ProtocolConstants.MAX_FRAME_SIZE, G2021ProtocolConstants.FRAME_SIZE_OFFSET, G2021ProtocolConstants.FRAME_SIZE_LENGTH,0,G2021ProtocolConstants.FRAME_SIZE_LENGTH);
	}
}
