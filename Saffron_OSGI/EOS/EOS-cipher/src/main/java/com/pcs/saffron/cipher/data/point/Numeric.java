/**
 * 
 */
package com.pcs.saffron.cipher.data.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.point.types.PointDataTypes;


/**
 * Interface to be implemented by all analog points.
 * @author PCSEG171
 *
 */
public class Numeric extends Point {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6574856982113572926L;
	
	private PointDataTypes TYPE = PointDataTypes.NUMERIC;
	private static final Logger LOGGER = LoggerFactory.getLogger(Numeric.class);
	
	/**
	 * Default constructor.
	 */
	protected Numeric(){
		super.setType(TYPE.name());
	}
	
	/**
	 * Named constructor
	 * @param name name of the point
	 * @throws Exception
	 */
	protected Numeric(String name) throws Exception{
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
