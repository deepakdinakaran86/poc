/**
 * 
 */
package com.pcs.saffron.cipher.adapter;

import java.util.List;

import com.pcs.saffron.cipher.BaseDecoder;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.exception.MessageDecodeException;

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
