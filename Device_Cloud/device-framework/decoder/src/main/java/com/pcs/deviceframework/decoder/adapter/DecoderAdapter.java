/**
 * 
 */
package com.pcs.deviceframework.decoder.adapter;

import java.util.List;

import com.pcs.deviceframework.decoder.BaseDecoder;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.exception.MessageDecodeException;

/**
 * @author pcseg171
 *
 */
public class DecoderAdapter extends BaseDecoder {

	/* (non-Javadoc)
	 * @see com.pcs.device.decoder.BaseDecoder#setData(java.lang.String)
	 */
	@Override
	public void setData(String hexaData) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.pcs.device.decoder.BaseDecoder#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.pcs.device.decoder.BaseDecoder#getPoints()
	 */
	@Override
	public List<List<Point>> getPoints() throws MessageDecodeException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.pcs.device.decoder.BaseDecoder#readMessage()
	 */
	@Override
	public List<Message> readMessage() throws MessageDecodeException {
		// TODO Auto-generated method stub
		return null;
	}

}
