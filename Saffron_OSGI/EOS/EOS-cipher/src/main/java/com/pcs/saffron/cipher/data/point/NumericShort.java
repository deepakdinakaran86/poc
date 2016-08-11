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
public class NumericShort extends Numeric {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5010504676472252850L;
	private PointDataTypes TYPE = PointDataTypes.SHORT;
	private static final Logger LOGGER = LoggerFactory.getLogger(NumericShort.class);
	
	/**
	 * Default constructor.
	 */
	protected NumericShort(){
		super.setType(TYPE.name());
	}
	
	/**
	 * Named constructor
	 * @param name name of the point
	 * @throws Exception
	 */
	protected NumericShort(String name) throws Exception{
		try {
			setPointName(name);
			super.setType(TYPE.name());
		} catch (Exception exe) {
			LOGGER.error("Error initalizing Analog Point under name : "+name,exe);
			throw new Exception("Error initalizing Analog Point under name : "+name,exe);
		}
	}
}
