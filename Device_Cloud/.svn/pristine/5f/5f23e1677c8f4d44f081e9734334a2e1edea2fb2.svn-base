
/**
 * 
 */
package com.pcs.deviceframework.decoder;

import java.util.List;

import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.exception.MessageDecodeException;

/**
 * Base class for the Decoder interface.
 * 
 * @author aneeshp
 * 
 * 
 * 
 */
public abstract class BaseDecoder {

	abstract public void setData(String hexaData);

	abstract public String getData() ;

	abstract public List<List<Point>> getPoints() throws MessageDecodeException;

	abstract public List<Message> readMessage() throws MessageDecodeException;
}
