/**
 * 
 */
package com.pcs.saffron.cipher.data.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.point.types.PointDataTypes;

/**
 * @author pcseg171
 *
 */
public class NumericInteger extends Numeric {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3601288595220923332L;
	private PointDataTypes TYPE = PointDataTypes.INTEGER;
	private static final Logger LOGGER = LoggerFactory.getLogger(NumericInteger.class);
	
	/**
	 * Default constructor.
	 */
	protected NumericInteger(){
		super.setType(TYPE.name());
	}
	
	/**
	 * Named constructor
	 * @param name name of the point
	 * @throws Exception
	 */
	protected NumericInteger(String name) throws Exception{
		try {
			setPointName(name);
			super.setType(TYPE.name());
		} catch (Exception exe) {
			LOGGER.error("Error initalizing Analog Point under name : "+name,exe);
			throw new Exception("Error initalizing Analog Point under name : "+name,exe);
		}
	}
}
