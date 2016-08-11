/**
 * 
 */
package com.pcs.saffron.cipher.data.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.point.types.PointDataTypes;

/**
 * Interface to be implemented by all digital points.
 * @author PCSEG171
 *
 */
public  class Boolean extends Point{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4608262702193030584L;
	
	private PointDataTypes TYPE = PointDataTypes.BOOLEAN;
	private static final Logger LOGGER = LoggerFactory.getLogger(Boolean.class);
	
	public Boolean(){
		setType(TYPE.name());
	}

	public Boolean(String name) throws Exception{
		try {
			setPointName(name);
			setType(TYPE.name());
		} catch (Exception exec) {
			LOGGER.error("Error instantiating Digital Point under name : "+name, exec);
			throw new Exception("Error instantiating Digital Point under name : "+name, exec);
		}
	}
}
