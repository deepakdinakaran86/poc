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
public class NumericDouble extends Numeric {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6931201072096464084L;
	private PointDataTypes TYPE = PointDataTypes.DOUBLE;
	private static final Logger LOGGER = LoggerFactory.getLogger(Numeric.class);
	
	/**
	 * Default constructor.
	 */
	protected NumericDouble(){
		super.setType(TYPE.name());
	}
	
	/**
	 * Named constructor
	 * @param name name of the point
	 * @throws Exception
	 */
	protected NumericDouble(String name) throws Exception{
		try {
			setPointName(name);
			super.setType(TYPE.name());
		} catch (Exception exe) {
			LOGGER.error("Error initalizing Analog Point under name : "+name,exe);
			throw new Exception("Error initalizing Analog Point under name : "+name,exe);
		}
	}
}
