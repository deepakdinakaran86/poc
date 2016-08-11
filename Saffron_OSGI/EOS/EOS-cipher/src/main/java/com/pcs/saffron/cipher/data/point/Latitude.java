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
public class Latitude extends NumericDouble {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6931201072096464084L;
	private PointDataTypes TYPE = PointDataTypes.LATITUDE;
	private static final Logger LOGGER = LoggerFactory.getLogger(Latitude.class);
	
	/**
	 * Default constructor.
	 */
	protected Latitude(){
		super.setType(TYPE.name());
	}
	
	/**
	 * Named constructor
	 * @param name name of the point
	 * @throws Exception
	 */
	protected Latitude(String name) throws Exception{
		try {
			setPointName(name);
			super.setType(TYPE.name());
		} catch (Exception exe) {
			LOGGER.error("Error initalizing Analog Point under name : "+name,exe);
			throw new Exception("Error initalizing Analog Point under name : "+name,exe);
		}
	}
}
