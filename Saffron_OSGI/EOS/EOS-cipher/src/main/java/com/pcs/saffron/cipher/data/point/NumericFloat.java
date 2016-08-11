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
public class NumericFloat extends Numeric {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2398250762435743519L;
	private PointDataTypes TYPE = PointDataTypes.FLOAT;
	private static final Logger LOGGER = LoggerFactory.getLogger(NumericFloat.class);
	
	/**
	 * Default constructor.
	 */
	protected NumericFloat(){
		super.setType(TYPE.name());
	}
	
	/**
	 * Named constructor
	 * @param name name of the point
	 * @throws Exception
	 */
	protected NumericFloat(String name) throws Exception{
		try {
			setPointName(name);
			super.setType(TYPE.name());
		} catch (Exception exe) {
			LOGGER.error("Error initalizing Analog Point under name : "+name,exe);
			throw new Exception("Error initalizing Analog Point under name : "+name,exe);
		}
	}
}
