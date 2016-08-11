/**
 * 
 */
package com.pcs.saffron.cipher.data.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.point.types.PointDataTypes;

/**
 * @author PCSEG171
 *
 */
public class Date extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5807983607577535022L;
	
	private PointDataTypes TYPE = PointDataTypes.DATE;
	private static final Logger LOGGER = LoggerFactory.getLogger(Numeric.class);
	
	/**
	 * Default constructor.
	 */
	public Date(){
		super.setType(TYPE.name());
	}
	
	/**
	 * Named constructor
	 * @param name name of the point
	 * @throws Exception
	 */
	public Date(String name) throws Exception{
		try {
			setPointName(name);
			super.setType(TYPE.name());
		} catch (Exception exe) {
			LOGGER.error("Error initalizing Analog Point under name : "+name,exe);
			throw new Exception("Error initalizing Analog Point under name : "+name,exe);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.pcs.driver.core.points.IPoint#getCalculatedValue(java.lang.String)
	 */
	@Override
	public Object getCalculatedValue(String formula)throws Exception {
		return super.getCalculatedValue(formula);
	}

}
