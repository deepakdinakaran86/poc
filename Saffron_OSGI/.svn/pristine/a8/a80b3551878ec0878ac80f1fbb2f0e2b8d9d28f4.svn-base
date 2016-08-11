
/**
 * 
 */
package com.pcs.saffron.cipher;

import java.util.List;

import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.exception.MessageDecodeException;

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
