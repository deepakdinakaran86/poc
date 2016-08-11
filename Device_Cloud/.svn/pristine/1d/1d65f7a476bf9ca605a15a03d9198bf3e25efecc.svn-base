/**
 * 
 */
package com.pcs.deviceframework.decoder.data.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.decoder.data.point.types.PointDataTypes;


/**
 * Interface to be implemented by all analog points.
 * @author PCSEG171
 *
 */
public class Text extends Point {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 81633524358104073L;
	
	private PointDataTypes TYPE = PointDataTypes.TEXT;
	private static final Logger LOGGER = LoggerFactory.getLogger(Text.class);
	
	/**
	 * Default constructor.
	 */
	protected Text(){
		super.setType(TYPE.name());
	}
	
	/**
	 * Named constructor
	 * @param name name of the point
	 * @throws Exception
	 */
	protected Text(String name) throws Exception{
		try {
			setPointName(name);
			super.setType(TYPE.name());
		} catch (Exception exe) {
			LOGGER.error("Error initalizing Text Point under name : "+name,exe);
			throw new Exception("Error initalizing Text Point under name : "+name,exe);
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
