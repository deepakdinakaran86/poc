/**
 * 
 */
package com.pcs.deviceframework.decoder.data.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.decoder.data.point.types.PointDataTypes;

/**
 * @author pcseg171
 *
 */
public class NumericLong extends Numeric {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3919129524140217163L;
	private PointDataTypes TYPE = PointDataTypes.LONG;
	private static final Logger LOGGER = LoggerFactory.getLogger(NumericLong.class);
	
	/**
	 * Default constructor.
	 */
	protected NumericLong(){
		super.setType(TYPE.name());
	}
	
	/**
	 * Named constructor
	 * @param name name of the point
	 * @throws Exception
	 */
	protected NumericLong(String name) throws Exception{
		try {
			setPointName(name);
			super.setType(TYPE.name());
		} catch (Exception exe) {
			LOGGER.error("Error initalizing Analog Point under name : "+name,exe);
			throw new Exception("Error initalizing Analog Point under name : "+name,exe);
		}
	}
}
